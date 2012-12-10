package ca.georgian.project2;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class SplashScreen extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        
        countDownTimer.start();
    }
        
        CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {

    		@Override
    		public void onTick(long millisUntilFinished) {

    		} // end onTick

    		@Override
    		public void onFinish() {
    			Intent i = new Intent(SplashScreen.this, MainActivity.class);
       		      startActivity(i);
    		}// end onFinish
        };
}
