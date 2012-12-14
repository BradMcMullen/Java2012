package ca.georgian.project2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SecondaryActivity extends Activity {
	
	Editable laptext;
	String lapstr;
	final Context context = this;
	Button savebutton;
	Button cancelbutton;
	EditText numoflapsedit;
	//int lapint = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.secondary_activity);
		
		numoflapsedit = (EditText) findViewById(R.id.numoflaps);
		savebutton = (Button) findViewById(R.id.savebtn);
		cancelbutton = (Button) findViewById(R.id.cancelbtn);
	
		cancelbutton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent i = new Intent(SecondaryActivity.this, MainActivity.class);
			      startActivity(i);
			}
			
		});
	
		savebutton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				//laptext = numoflapsedit.getText();
				lapstr = numoflapsedit.getText().toString();
				int lapint = 0;
				
				if (lapstr.equals("")){
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
		    		builder.setTitle("Something went wrong");
		    		builder.setMessage(String.format("%s",
		    			"Enter a valid number"));
		    		builder.setCancelable(false);
		    		builder.setPositiveButton("Continue", null);
		    		builder.show();
				}
				else{
					lapint = Integer.parseInt(lapstr);
					Intent i = new Intent(SecondaryActivity.this, MainActivity.class);
					i.putExtra("numoflaps1", lapint);
					startActivity(i);
				}//
			}
			
		});
	
	}

}
