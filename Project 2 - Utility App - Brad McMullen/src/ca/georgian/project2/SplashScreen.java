//SplashScreen.java
//Brad McMullen
//Last Modified: December 14, 2012.
//Purpose: This is the splash screen for the application.

package ca.georgian.project2;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.content.Intent;

public class SplashScreen extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        
        countDownTimer.start();
    }
        //After 5 seconds, load the main activity.
        CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {

    		@Override
    		public void onTick(long millisUntilFinished) {

    		} // end onTick

    		@Override
    		public void onFinish() {
    			//Call the main activity.
    			Intent i = new Intent(SplashScreen.this, MainActivity.class);
       		      startActivity(i);
    		}// end onFinish
        };
}
