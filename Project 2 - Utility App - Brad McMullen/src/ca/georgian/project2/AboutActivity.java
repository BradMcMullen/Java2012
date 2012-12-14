//AboutActivity.java
//Brad McMullen
//Last Modified: December 14, 2012.
//Purpose: Just provides some instructions for the application.
//Very simple activity.

package ca.georgian.project2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AboutActivity extends Activity{

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_activity);
	
		Button backbutton = (Button) findViewById(R.id.backbtn);
		
		//Go back to options activity. 
		backbutton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent i = new Intent(AboutActivity.this, SecondaryActivity.class);
   		      startActivity(i);
			}
		});
		
	}
	
}
