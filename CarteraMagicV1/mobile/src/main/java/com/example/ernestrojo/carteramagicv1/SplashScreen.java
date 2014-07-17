package com.example.ernestrojo.carteramagicv1;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;

public class SplashScreen extends Activity {

    private final long SPLASH_DISPLAY_LENGHT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(getBaseContext(), LoginSocial.class);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
    }
}