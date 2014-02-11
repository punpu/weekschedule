package com.punpu.weekschedule;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class ModifyScheduleItemActivity extends Activity
{

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);

	    setContentView(R.layout.activity_modify_schedule_item);
	    
	    SharedPreferences sprefs = getSharedPreferences("com.punpu.weekschedule.sprefs", MODE_PRIVATE);
	    Intent intent = getIntent();
	    
	    int itemTag = Integer.parseInt (intent.getStringExtra( MainActivity.EXTRA_TAG ) );
	    
	    // asetetaan viestin editointikenttään kalenterimerkinnän teksti 
        String message_tag = "message" + itemTag;
    	String item_message = sprefs.getString( message_tag, "" );
        
	    EditText editText = (EditText) findViewById(R.id.edit_message);
	    editText.setText( item_message );
	    
	    
	    setupSpinner();
	    
	    // asetetaan viestin värivalintaan kalenterimerkinnän väri 
	    Spinner color_spinner = (Spinner) findViewById(R.id.colorspinner);
	    
	    String color_tag = "color" + itemTag;
        int item_color_pos = Integer.parseInt (sprefs.getString( color_tag, "0" ) );
        
        color_spinner.setSelection( item_color_pos );
        color_spinner.setBackgroundColor( MainActivity.colorTable[ item_color_pos ] );
        
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() 
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	
	private void setupSpinner()
	{
		
		Spinner color_spinner = (Spinner) findViewById(R.id.colorspinner); 
		
        ArrayList<String> colorArray = new ArrayList<String>();
        colorArray.add("Default white");
        colorArray.add("Light sky blue");
        colorArray.add("Pale green");
        colorArray.add("Gold");
        colorArray.add("Salmon");
        colorArray.add("Orchid");
        colorArray.add("Peach Puff");
        colorArray.add("Lemon Chiffon");
        colorArray.add("Seashell");
        
        
        ArrayAdapter<CharSequence> MyAdapter =
                new ArrayAdapter(this, android.R.layout.simple_spinner_item, colorArray) 
        {
        	@Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) 
            {
                View view = super.getDropDownView(position, convertView, parent);
                switch (position) {
                case 0:
                    view.setBackgroundColor( MainActivity.colorTable[0] );  
                    break; 
                case 1:  
                    view.setBackgroundColor( MainActivity.colorTable[1] );
                    break;
                case 2:
                    view.setBackgroundColor( MainActivity.colorTable[2] );
                    break;
                case 3:
                    view.setBackgroundColor( MainActivity.colorTable[3] );
                    break;
                case 4:
                    view.setBackgroundColor( MainActivity.colorTable[4] );
                    break;    
                case 5:
                    view.setBackgroundColor( MainActivity.colorTable[5] );
                    break;    
                case 6:
                    view.setBackgroundColor( MainActivity.colorTable[6] );
                    break;    
                case 7:
                    view.setBackgroundColor( MainActivity.colorTable[7] );
                    break; 
                case 8:
                    view.setBackgroundColor( MainActivity.colorTable[8] );
                    break; 
                   
                default:
                    view.setBackgroundColor( MainActivity.colorTable[0] );
                    break;
              }
                return view;
            }
        };
        
        color_spinner.setAdapter( MyAdapter );
        
	}
	
	
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
    public void saveItemAndReturn(View view) 
    {
    	
    	SharedPreferences sprefs = getSharedPreferences("com.punpu.weekschedule.sprefs", MODE_PRIVATE);
    	SharedPreferences.Editor editor = sprefs.edit();
    	
    	// Tallennetaan syötetty viesti sharedpreferenceihin
    	EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
    	
        Intent intent = getIntent();
        String message_item_tag = "message" + intent.getStringExtra( MainActivity.EXTRA_TAG );
        
    	editor.putString( message_item_tag, message );
    	
    	
    	// tallennetaan valittu väri sharedpreferenceihin
    	Spinner color_spinner = (Spinner) findViewById(R.id.colorspinner); 
    	int spinner_pos = color_spinner.getSelectedItemPosition();
    	
    	String color_item_tag = "color" + intent.getStringExtra( MainActivity.EXTRA_TAG );
    	
    	editor.putString( color_item_tag, spinner_pos + "" );
    	
    	editor.commit();
    	
    	NavUtils.navigateUpFromSameTask(this);
    }
    
    
    public void cancelAndReturn(View view)
    {
    	NavUtils.navigateUpFromSameTask(this);
    }
    
}
