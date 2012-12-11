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
	//Used to get fastest laps and slowest laps if I implement it.
	double totalloops = 0;
	double totalloopshigh = 0;
	//Arbitrary starting point so every lap will be lower than this.
	double totalloopslow = 1000000000;
	////////////////////////////////////////////////////////////////
    Integer minutes = 0;
    Integer hours = 0;
    String timeStr = "";
    private Handler mHandler = new Handler();
    TextView timeDisplay;
    long mStartTime = 0L;
    String secondsstr;
    String minutestr;
    String hourstr;
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		timeDisplay = (TextView) findViewById(R.id.timeText);
		startbutton = (Button) findViewById(R.id.startbtn);
		stopbutton = (Button) findViewById(R.id.stopbtn);
		lapbutton = (Button) findViewById(R.id.lapbtn);
		resetbutton = (Button) findViewById(R.id.resetbtn);
	
		//Stop Button
		stopbutton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				mHandler.removeCallbacks(mUpdateTimeTask);
				mStartTime = 0L;
				startbutton.setClickable(true);
				resetbutton.setClickable(true);
			}
		});
		
		resetbutton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				timeDisplay.setText("0:00:00.0");
				seconds = 0;
				minutes = 0;
				hours = 0;
			}
		});
		
		//Start Button
		startbutton.setOnClickListener(new OnClickListener() {
	
			public void onClick(View v) {
				if (mStartTime == 0L) {
		            mStartTime = System.currentTimeMillis();
		            //mHandler.removeCallbacks(mUpdateTimeTask);
		            mHandler.postDelayed(mUpdateTimeTask, 100);
		            startbutton.setClickable(false);
		            resetbutton.setClickable(false);
		       }
			}
				

		});//End of Start Button
		
    }
    
    private Runnable mUpdateTimeTask = new Runnable() {
		   public void run() {
			   seconds = seconds + 0.1;
			   totalloops++;
			   //Decimal Formatting, to tenths of a second
			   DecimalFormat onedeci = new DecimalFormat("#0.0");
			   secondsstr = onedeci.format(seconds);
			   minutestr = Integer.toString(minutes);
			   hourstr = Integer.toString(hours);
			   
			   
			   if(seconds > 59.9){
				   seconds = 0;
				   minutes++;
			   }
			   
			   if(minutes > 59){
				   minutes = 0;
				   hours++;
			   }
			   	//Label Updating
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
					timeDisplay.setText(hours + ":" + "0" + minutes + ":" + "0" + seconds);
				}
				else if (seconds > 9.9 && minutes >= 0 && minutes <= 9 && hours <= 9){
					timeDisplay.setText(hours + ":" + "0" + minutes + ":" + seconds);
				}
				else if (seconds <= 9.9 && minutes > 9 && hours <= 9){
					timeDisplay.setText(hours + ":" + minutes + ":" + "0" + seconds);
				}
				else{
					timeDisplay.setText(hours + ":" + minutes + ":" + seconds);
				}
				mHandler.postDelayed(mUpdateTimeTask, 50);
		   }
		};//
		
}
