//MainActivity.java
//Brad McMullen
//Last Modified: December 11, 2012.
//Purpose: This is the main area of my stop-watch application.  This is where the timer runs
//updates the textviews, starts/stops/resets, etc.

package ca.georgian.project2;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	//Lots of variables.
	Button startbutton;
	Button stopbutton;
	Button lapbutton;
	Button resetbutton;
	Button optionbutton;
	double seconds = 0;
	//Used to get fastest laps..
	double totalloops = 0;
	//Arbitrary starting point so every lap will be faster than this.
	double totalloopslow = 1000000000;
	////////////////////////////////////////////////////////////////
    Integer minutes = 0;
    Integer hours = 0;
    String timeStr = "";
    private Handler mHandler = new Handler();
    TextView timeDisplay;
    TextView currLapDisplay;
    TextView prevLapDisplay;
    TextView fastLapDisplay;
    long mStartTime = 0L;
    String secondsstr;
    String minutestr;
    String hourstr;
    double currsec = 0;
    String currsecstr;
    int currmin = 0;
    String currminstr;
    int currhour = 0;
    String currhourstr;
    private TableLayout mytable;
    int lapnum = 1;
    int maxlaps = 1000;
    boolean stop = false;
    boolean lap = false;
    boolean resumed = false;
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		//Find GUI elements.
		timeDisplay = (TextView) findViewById(R.id.timeText);
		currLapDisplay = (TextView) findViewById(R.id.currentlaptext);
		prevLapDisplay = (TextView) findViewById(R.id.previouslaptext);
		fastLapDisplay = (TextView) findViewById(R.id.fastlaptext);
		startbutton = (Button) findViewById(R.id.startbtn);
		stopbutton = (Button) findViewById(R.id.stopbtn);
		lapbutton = (Button) findViewById(R.id.lapbtn);
		resetbutton = (Button) findViewById(R.id.resetbtn);
		optionbutton = (Button) findViewById(R.id.optionbtn);
		
		mytable = (TableLayout) findViewById(R.id.lapLayout);
		
		//Buttons that should not be clickable at launch.
		stopbutton.setClickable(false);
		lapbutton.setClickable(false);
		resetbutton.setClickable(false);
		
		//Stop Button
		stopbutton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				stop = true;
				inflateLaps("", 0);
				mHandler.removeCallbacks(mUpdateTimeTask);
				//Reset starting time to 0.
				mStartTime = 0L;
				resetbutton.setClickable(true);
				//Lap button only clickable when timer is running.
				lapbutton.setClickable(false);
				startbutton.setClickable(false);
				stopbutton.setClickable(false);
				lapnum++;
			}
		});
		
		//Reset Button
		resetbutton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				//Reset all textviews and timer related variables.
				timeDisplay.setText("0:00:00.0");
				currLapDisplay.setText("0:00:00.0");
				prevLapDisplay.setText("0:00:00.0");
				fastLapDisplay.setText("0:00:00.0");
				seconds = 0;
				currsec = 0;
				minutes = 0;
				currmin = 0;
				hours = 0;
				currhour = 0;
				totalloopslow = 1000000000;
				startbutton.setClickable(true);
				optionbutton.setClickable(true);
				stopbutton.setClickable(false);
				resetbutton.setClickable(false);
				lapbutton.setClickable(false);
				for(int i = (lapnum - 2); i >= 0; i--){
					mytable.removeViewAt(i);
				}
				lapnum = 1;
			}
		});
		
		//Lap Button
		lapbutton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				if(lapnum == maxlaps){
					mHandler.removeCallbacks(mUpdateTimeTask);
					//Reset starting time to 0.
					mStartTime = 0L;
					resetbutton.setClickable(true);
					//Lap button only clickable when timer is running.
					lapbutton.setClickable(false);
				}
				
				inflateLaps("", 0);
				lapnum++;
				//Checking if current lap took more or less cycles than the fastest to determine
				//whether or not this lap was faster than the other.
				if(totalloops <= totalloopslow){
					totalloopslow = totalloops;
					//Get current laptime if it is faster.
					fastLapDisplay.setText(currLapDisplay.getText());
				}
				//Get and set previous lap.
				prevLapDisplay.setText(currLapDisplay.getText());
				//Reset current lap and variables.
				currsec = 0;
				currmin = 0;
				currhour = 0;
				currLapDisplay.setText("0:00:00.0");
				totalloops = 0;
			}
		});
		
		
		//Start Button
		startbutton.setOnClickListener(new OnClickListener() {
			
			//Handler
			public void onClick(View v) {
				if (mStartTime == 0L) {
		            mStartTime = System.currentTimeMillis();
		            mHandler.removeCallbacks(mUpdateTimeTask);
		            mHandler.postDelayed(mUpdateTimeTask, 100);
		            startbutton.setClickable(false);
		            resetbutton.setClickable(false);
		            optionbutton.setClickable(false);
		            //Only lap button and stop button clickable when timer running.
		            lapbutton.setClickable(true);
		            stopbutton.setClickable(true);
		       }
			}
				

		});//End of Start Button
		
		//Option Butotn
		optionbutton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent i = new Intent(MainActivity.this, SecondaryActivity.class);
     		      startActivity(i);
			}
		});
		
    }
    
    //The process that runs every time the timer ticks.
    private Runnable mUpdateTimeTask = new Runnable() {
		   public void run() {
			   totalloops++;
			   seconds = seconds + 0.1;
			   currsec = currsec + 0.1;
			   totalloops++;
			   
			   //Main Display
			   if(seconds > 59.9){
				   seconds = 0;
				   minutes++;
			   }
			   
			   if(minutes > 59){
				   minutes = 0;
				   hours++;
			   }
			   //Sub-Display
			   if(currsec > 59.9){
				   currsec = 0;
				   currmin++;
			   }   
			   if(currmin > 59){
				   currmin = 0;
				   currhour++;
			   }
			   
			   
			   //Decimal Formatting, to tenths of a second
			   DecimalFormat onedeci = new DecimalFormat("#0.0");
			   secondsstr = onedeci.format(seconds);
			   currsecstr = onedeci.format(currsec);
			   
			   //Converting to strings.
			   minutestr = Integer.toString(minutes);
			   currminstr = Integer.toString(currmin);
			   hourstr = Integer.toString(hours);
			   currhourstr = Integer.toString(currhour);
			   
			   
			   	//Label Updating - Main Display
			   	//Seconds Only
				if(seconds > 9.9 && minutes == 0){
					timeDisplay.setText("0:00:" + secondsstr);
				}
				else if (seconds <= 9.9 && minutes == 0){
					timeDisplay.setText("0:00:0" + secondsstr);
				}
				//Seconds and minutes
				else if (seconds <= 9.9 && minutes >= 0 && minutes <= 10){
					timeDisplay.setText("0:0" + minutestr + ":" + "0" + secondsstr);
				}
				else if (seconds > 9.9 && minutes >= 0 && minutes <= 10){
					timeDisplay.setText("0:0" + minutestr + ":" + secondsstr);
				}
				else if (seconds <= 9.9 && minutes > 9){
					timeDisplay.setText("0:" + minutestr + ":" + "0" + secondsstr);
				}
				else if (seconds > 9.9 && minutes > 9){
					timeDisplay.setText("0:" + minutestr + ":" + secondsstr);
				}
				//Seconds, Minutes and Hours
				else if (seconds <= 9.9 && minutes >= 0 && minutes <= 9 && hours <= 9){
					timeDisplay.setText(hourstr + ":" + "0" + minutestr + ":" + "0" + secondsstr);
				}
				else if (seconds > 9.9 && minutes >= 0 && minutes <= 9 && hours <= 9){
					timeDisplay.setText(hourstr + ":" + "0" + minutestr + ":" + secondsstr);
				}
				else if (seconds <= 9.9 && minutes > 9 && hours <= 9){
					timeDisplay.setText(hourstr + ":" + minutestr + ":" + "0" + secondsstr);
				}
				else{
					timeDisplay.setText(hourstr + ":" + minutestr + ":" + secondsstr);
				}//End of main display.
				
				
				//Start of sub-displays
				//Seconds Only
				if(currsec > 9.9 && currmin == 0){
					currLapDisplay.setText("0:00:" + currsecstr);
				}
				else if (currsec <= 9.9 && currmin == 0){
					currLapDisplay.setText("0:00:0" + currsecstr);
				}
				//Seconds and minutes
				else if (currsec <= 9.9 && currmin >= 0 && currmin <= 10){
					currLapDisplay.setText("0:0" + currminstr + ":" + "0" + currsecstr);
				}
				else if (currsec > 9.9 && currmin >= 0 && currmin <= 10){
					currLapDisplay.setText("0:0" + currminstr + ":" + currsecstr);
				}
				else if (currsec <= 9.9 && currmin > 9){
					currLapDisplay.setText("0:" + currminstr + ":" + "0" + currsecstr);
				}
				else if (currsec > 9.9 && currmin > 9){
					currLapDisplay.setText("0:" + currminstr + ":" + currsecstr);
				}
				//Seconds, Minutes and Hours
				else if (currsec <= 9.9 && currmin >= 0 && currmin <= 9 && currhour <= 9){
					currLapDisplay.setText(currhourstr + ":" + "0" + currminstr + ":" + "0" + currsecstr);
				}
				else if (currsec > 9.9 && currmin >= 0 && currmin <= 9 && currhour <= 9){
					currLapDisplay.setText(currhourstr + ":" + "0" + currminstr + ":" + currsecstr);
				}
				else if (currsec <= 9.9 && currmin > 9 && currhour <= 9){
					currLapDisplay.setText(currhourstr + ":" + currminstr + ":" + "0" + currsecstr);
				}
				else{
					currLapDisplay.setText(currhourstr + ":" + currminstr + ":" + currsecstr);
				}
				   
				//End of sub-displays
				//Handler/Timer iteration speed control.
				mHandler.postDelayed(mUpdateTimeTask, 10);
		   }
		};//
		
		//Layout inflater for lap times with assistance from FavouriteTwitterSearches
		private void inflateLaps(String tag, int index){
		      // get a reference to the LayoutInflater service
		      LayoutInflater inflater = (LayoutInflater) getSystemService(
		         Context.LAYOUT_INFLATER_SERVICE);

		      // Inflate inflater_activity.xml to create new lap and time textviews.
		      View newTagView = inflater.inflate(R.layout.inflater_activity, null);
		       
		      // Get inflaptv and set its text.
		      TextView inflaptv = 
		         (TextView) newTagView.findViewById(R.id.lapnumtv);
		      inflaptv.setText("   Lap: " + lapnum); 

		      // Get inftimetv and set its text.
		      TextView inftimetv = 
		         (TextView) newTagView.findViewById(R.id.laptimetv); 
		      inftimetv.setText(currLapDisplay.getText());

		      // Add new textviews to lapLayout.
		      mytable.addView(newTagView, index);
		} // end makeTagGUI
		
		public void onResume(){
			super.onResume();
			Bundle extras = getIntent().getExtras();
			if(resumed = true){
				if (extras != null) {
				    int value = extras.getInt("numoflaps1");
				    maxlaps = value;
				}
			}
			stopbutton.setClickable(false);
			resetbutton.setClickable(false);
			lapbutton.setClickable(false);
			resumed = true;
		}
		
}
