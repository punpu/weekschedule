package com.punpu.weekschedule;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class MainActivity extends Activity {

	public final static String EXTRA_TAG = "com.punpu.weekschedule.VIEWTAG";
	public final static String EXTRA_MESSAGE = "com.punpu.weekschedule.VIEWMESSAGE";
	private static final String LOG_TAG = "MainActivity";
	
	
	public final static int[] colorTable = { 
	     	0xFFffffff,   // default white
		    0xFF87cefa,   // light sky blue
		    0xFF98fb98,   // pale green 
		    0xFFffd700,   // gold
		    0xFFfa8072,   // salmon (red)
		    0xFFda70d6,   // orchid (violet)
		    0xFFeecbad,   // Peach Puff 2 (pastel)
		    0xFFfffacd,   // Lemon Chiffon (yellowish pastel)
		    0xFFcdc5bf    // Seashell 3 (grayish white)
		};
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        
        SharedPreferences sprefs = getSharedPreferences("com.punpu.weekschedule.sprefs", MODE_PRIVATE);
        
        // K‰yd‰‰n l‰pi taulukon jokainen alkio (item00 - item44)
        // ja asetetaan alkion tekstiksi tallennettu teksti
        int j = 0;
        for( int i = R.id.item00; i <= R.id.item44; ++i )
        {
        	// Etsit‰‰n sharedpreferenceist‰ itemin viesti ja asetetaan se
        	String message_tag = "message" + j;
        	String item_message = sprefs.getString( message_tag, "" );
        	
            TextView textview = (TextView) findViewById( i );
            textview.setText( item_message );
            
            textview.setOnTouchListener( new MyTouchListener() );
            
            // Etsit‰‰n sharedpreferenceist‰ itemin v‰ri ja asetetaan se
            
            String color_tag = "color" + j;
            int item_color_pos = Integer.parseInt (sprefs.getString( color_tag, "0" ) );
            
            
            textview.setBackgroundColor( colorTable[ item_color_pos ] );
            
            ++j;
        }
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    /** Called when the user clicks the Send button  */
    public void modifyItem(View view) {
    	
        Intent modify_intent = new Intent(this, ModifyScheduleItemActivity.class);
        
        String tag = view.getTag().toString();
        
        TextView textview = (TextView)view;
        String message = textview.getText().toString();
        
        modify_intent.putExtra(EXTRA_TAG, tag );
        modify_intent.putExtra(EXTRA_MESSAGE, message );
        
        startActivity( modify_intent );
    }
    
    
    private class MyTouchListener implements OnTouchListener {
    	
    	private Rect rect_;
    	private boolean pointer_inside_view_;
    	
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            
        	TextView tv = (TextView)v;
            
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                tv.setBackgroundColor( 0xFF7ccfff );
                Log.v(LOG_TAG, "ACTION_DOWN kutsu, view:" + v.getTag().toString() );
                
                rect_ = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                pointer_inside_view_ = true;
                
                return true;
            }
            
            else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            	
            	Log.v(LOG_TAG, "ACTION_MOVE kutsu, view:" + v.getTag().toString() );
            	
            	if( !rect_.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY()) )
            	{
                    // Kosketus rajojen ulkopuolella
            		tv.setBackgroundColor( 0xFFffffff );
            		pointer_inside_view_ = false;
                    return false;
                }
            } 
            
            else if (event.getAction() == MotionEvent.ACTION_UP) {
            	
            	tv.setBackgroundColor( 0xFFffffff );
            	if( pointer_inside_view_ )
            	{
            		tv.performClick();
            	}
                
            }
            return false;
        }
    }
    
}