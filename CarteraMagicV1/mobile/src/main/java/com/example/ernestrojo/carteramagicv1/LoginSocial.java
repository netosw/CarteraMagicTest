package com.example.ernestrojo.carteramagicv1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.entities.Profile.Properties;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnLogoutListener;
import com.sromku.simple.fb.listeners.OnProfileListener;


public class LoginSocial extends Activity {

    protected static final String TAG ="";
    private Button mButtonLogin;
    private Button mButtonLogout;
    private TextView mTextStatus;
    private SimpleFacebook simplef;
    Permission[] permissions = new Permission[] {
            Permission.USER_PHOTOS,
            Permission.EMAIL,
            Permission.PUBLISH_ACTION,
            Permission.USER_FRIENDS,
            Permission.USER_ABOUT_ME,
            Permission.PUBLIC_PROFILE,

    };

    SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
            .setAppId("1567149823512582")
            .setNamespace("magicwallet")
            .setPermissions(permissions)
            .build();

    Profile.Properties properties = new Profile.Properties.Builder()
            .add(Properties.ID)
            .add(Properties.FIRST_NAME)
            .add(Properties.COVER)
            .add(Properties.WORK)
            .add(Properties.EDUCATION)
            .add(Properties.PICTURE)
            .add(Properties.BIRTHDAY)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_social);


        SimpleFacebook.setConfiguration(configuration);
        simplef = SimpleFacebook.getInstance(this);

        mButtonLogin = (Button) findViewById(R.id.login);
        mButtonLogout = (Button) findViewById(R.id.exit);
        mTextStatus = (TextView) findViewById(R.id.progress);





        setLogin();
        setLogout();
        setUIState();

    }


    @Override
    public void onResume() {
        super.onResume();
        setTitle("Cartera Magic");
        simplef = SimpleFacebook.getInstance(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        simplef.onActivityResult(this, requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void setLogin() {
        // Login listener
        final OnLoginListener onLoginListener = new OnLoginListener() {

            @Override
            public void onFail(String reason) {
                mTextStatus.setText(reason);
                Log.w(TAG, "Failed to login");
            }

            @Override
            public void onException(Throwable throwable) {
                mTextStatus.setText("Exception: " + throwable.getMessage());
                Log.e(TAG, "Bad thing happened", throwable);
            }

            @Override
            public void onThinking() {
                // show progress bar or something to the user while login is
                // happening
                mTextStatus.setText("Thinking...");
            }

            @Override
            public void onLogin() {
                // change the state of the button or do whatever you want
                mTextStatus.setText("Logged in");
                loggedInUIState();
            }

            @Override
            public void onNotAcceptingPermissions(Permission.Type type) {
                //				toast(String.format("You didn't accept %s permissions", type.name()));
            }
        };


        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                simplef.login(onLoginListener);


            }
        });
    }

    /**
     * Logout example
     */
    private void setLogout() {
        final OnLogoutListener onLogoutListener = new OnLogoutListener() {

            @Override
            public void onFail(String reason) {
                mTextStatus.setText(reason);
                Log.w(TAG, "Failed to login");
            }

            @Override
            public void onException(Throwable throwable) {
                mTextStatus.setText("Exception: " + throwable.getMessage());
                Log.e(TAG, "Bad thing happened", throwable);
            }



            @Override
            public void onThinking() {
                // show progress bar or something to the user while login is
                // happening
                mTextStatus.setText("Thinking...");
            }

            @Override
            public void onLogout() {
                // change the state of the button or do whatever you want
                mTextStatus.setText("Logged out");
                loggedOutUIState();
            }

        };

        mButtonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                simplef.logout(onLogoutListener);
            }
        });
    }

    private void setUIState() {
        if (simplef.isLogin()) {
            loggedInUIState();
        }
        else {
            loggedOutUIState();
        }
    }

    private void loggedInUIState() {
        mButtonLogin.setEnabled(false);
        mButtonLogout.setEnabled(true);

        mTextStatus.setText("Logged in");
        oListener();


    }

    private void loggedOutUIState() {
        mButtonLogin.setEnabled(true);
        mButtonLogout.setEnabled(false);

        mTextStatus.setText("Logged out");

    }


    public OnProfileListener oListener(){



        OnProfileListener onProfileListener = new OnProfileListener() {
            @Override
            public void onComplete(Profile profile) {
                Log.i(TAG,"Mi nombre: " + profile.getFirstName() + " " + "Cumpleaños: " + profile.getBirthday() + " " + "Profile Pic: " + profile.getPicture());
                Toast.makeText(LoginSocial.this, "Mi nombre: " + profile.getFirstName() + " " + "Cumpleaños: " + profile.getBirthday() + " " + "Profile Pic: " + profile.getPicture(), Toast.LENGTH_LONG).show();

            }

		    /*
		     * You can override other methods here:
		     * onThinking(), onFail(String reason), onException(Throwable throwable)
		     */
        };

        simplef.getProfile(properties,onProfileListener);

        return onProfileListener;

    }





}
