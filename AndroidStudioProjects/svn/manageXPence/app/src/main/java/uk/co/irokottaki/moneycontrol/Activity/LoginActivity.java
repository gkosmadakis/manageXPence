package uk.co.irokottaki.moneycontrol.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.dropbox.core.android.Auth;

import uk.co.irokottaki.moneycontrol.R;
import uk.co.irokottaki.moneycontrol.Utils.Utils;

/**
 * A login screen that offers login via email/password.
 */


public class LoginActivity extends AppCompatActivity {
    private RelativeLayout layout;
    private int mLayoutWidth;
    private int mLayoutHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button signInButton = (Button) findViewById(R.id.sign_in_button);

        layout = (RelativeLayout) findViewById(R.id.loginActivityView);
        layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
                .OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {

                DisplayMetrics metrics = getResources().getDisplayMetrics();

                int deviceWidth = metrics.widthPixels;

                int deviceHeight = metrics.heightPixels;

                float widthInPercentage = ((float) 600 / 600) * 100; // 280 is the width of my
                // LinearLayout and 320 is device screen width as i know my current device
                // resolution are 320 x 480 so i'm calculating how much space (in percentage my
                // layout is covering so that it should cover same area (in percentage) on any
                // other device having different resolution
                //height:935
                float heightInPercentage = ((float) 1150 / 1024) * 100; // same procedure 300 is
                // the height of the LinearLayout and i'm converting it into percentage

                mLayoutWidth = (int) ((widthInPercentage * deviceWidth) / 100);

                mLayoutHeight = (int) ((heightInPercentage * deviceHeight) / 100);

                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams
                        (mLayoutWidth, mLayoutHeight);

                layout.setLayoutParams(layoutParams);
            }
        });

        Utils utils = new Utils(this);
        utils.setBackgroundAndAdjustLayout(layout, LoginActivity.this);

        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Auth.startOAuth2Authentication(getApplicationContext(), getString(R.string
                        .APP_KEY));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAccessToken();
    }

    public void getAccessToken() {
        String accessToken = Auth.getOAuth2Token(); //generate Access Token
        if (accessToken != null) {
            //Store accessToken in SharedPreferences
            SharedPreferences prefs = getSharedPreferences("com.example.valdio" +
                    ".dropboxintegration", Context.MODE_PRIVATE);
            prefs.edit().putString("access-token", accessToken).apply();

            //Proceed to MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}




