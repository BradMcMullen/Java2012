package ca.georgian.project2;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	Button startbutton;
	Button stopbutton;
	Button lapbutton;
	Button resetbutton;
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
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		timeDisplay = (TextView) findViewById(R.id.timeText);
		currLapDisplay = (TextView) findViewById(R.id.currentlaptext);
		prevLapDisplay = (TextView) findViewById(R.id.previouslaptext);
		fastLapDisplay = (TextView) findViewById(R.id.fastlaptext);
		startbutton = (Button) findViewById(R.id.startbtn);
		stopbutton = (Button) findViewById(R.id.stopbtn);
		lapbutton = (Button) findViewById(R.id.lapbtn);
		resetbutton = (Button) findViewById(R.id.resetbtn);
		
		stopbutton.setClickable(false);
		lapbutton.setClickable(false);
		resetbutton.setClickable(false);
		
		//Stop Button
		stopbutton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				mHandler.removeCallbacks(mUpdateTimeTask);
				mStartTime = 0L;
				startbutton.setClickable(true);
				resetbutton.setClickable(true);
				lapbutton.setClickable(false);
			}
		});
		
		//Reset Button
		resetbutton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
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
			}
		});
		
		//Lap Button
		lapbutton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				if(totalloops <= totalloopslow){
					totalloopslow = totalloops;
					fastLapDisplay.setText(currLapDisplay.getText());
				}
				prevLapDisplay.setText(currLapDisplay.getText());
				currsec = 0;
				currmin = 0;
				currhour = 0;
				currLapDisplay.setText("0:00:00.0");
				totalloops = 0;
			}
		});
		
		
		//Start Button
		startbutton.setOnClickListener(new OnClickListener() {
	
			public void onClick(View v) {
				if (mStartTime == 0L) {
		            mStartTime = System.currentTimeMillis();
		            mHandler.removeCallbacks(mUpdateTimeTask);
		            mHandler.postDelayed(mUpdateTimeTask, 100);
		            startbutton.setClickable(false);
		            resetbutton.setClickable(false);
		            lapbutton.setClickable(true);
		       }
			}
				

		});//End of Start Button
		
    }
    
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
				mHandler.postDelayed(mUpdateTimeTask, 10);
		   }
		};//
		
}
