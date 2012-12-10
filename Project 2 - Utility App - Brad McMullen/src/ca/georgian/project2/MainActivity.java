package ca.georgian.project2;

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
    double minutes = 0;
    double hours = 0;
    String timeStr = "";
    private Handler mHandler = new Handler();
    TextView timeDisplay;
    long mStartTime = 0L;
    String secondsstr;
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		timeDisplay = (TextView) findViewById(R.id.timeText);
		startbutton = (Button) findViewById(R.id.startbtn);
		stopbutton = (Button) findViewById(R.id.stopbtn);
		lapbutton = (Button) findViewById(R.id.lapbtn);
		resetbutton = (Button) findViewById(R.id.resetbtn);
	
		//Buttons
		stopbutton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				mHandler.removeCallbacks(mUpdateTimeTask);
			}
		});
		
		//Start Button
		startbutton.setOnClickListener(new OnClickListener() {
	
			public void onClick(View v) {
				if (mStartTime == 0L) {
		            mStartTime = System.currentTimeMillis();
		            //mHandler.removeCallbacks(mUpdateTimeTask);
		            mHandler.postDelayed(mUpdateTimeTask, 100);
		       }
			}
				

		});//End of Start Button
		
    }
    
    private Runnable mUpdateTimeTask = new Runnable() {
		   public void run() {
			   seconds = seconds + 0.1;
			   secondsstr = Double.toString(seconds);
			   secondsstr = String.format("%.1g%", secondsstr);
				if(seconds > 9.9){
					timeDisplay.setText("0:00:" + secondsstr);
				}
				else{
					timeDisplay.setText("0:00:0" + secondsstr);
				}
				mHandler.postDelayed(mUpdateTimeTask, 100);
		   }
		};//
		
}
