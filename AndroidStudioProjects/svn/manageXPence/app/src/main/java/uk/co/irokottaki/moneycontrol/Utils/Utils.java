package uk.co.irokottaki.moneycontrol.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import uk.co.irokottaki.moneycontrol.activity.AnnualChartActivity;
import uk.co.irokottaki.moneycontrol.activity.AnnualSavingsActivity;
import uk.co.irokottaki.moneycontrol.activity.BudgetActivity;
import uk.co.irokottaki.moneycontrol.activity.CalculateAnnualExpensesActivity;
import uk.co.irokottaki.moneycontrol.activity.ChartActivity;
import uk.co.irokottaki.moneycontrol.activity.EditActivity;
import uk.co.irokottaki.moneycontrol.activity.HelpActivity;
import uk.co.irokottaki.moneycontrol.activity.HorizontalBarChartActivity;
import uk.co.irokottaki.moneycontrol.activity.LoginActivity;
import uk.co.irokottaki.moneycontrol.activity.MainActivity;
import uk.co.irokottaki.moneycontrol.activity.ReportActivity;
import uk.co.irokottaki.moneycontrol.activity.SettingsActivity;
import uk.co.irokottaki.moneycontrol.R;

import static uk.co.irokottaki.moneycontrol.utils.Constants.*;

public class Utils {

    private Context context;

    public Utils(Context context) {

        this.context = context;
    }

    private void adjustScreenSizeToDevice (RelativeLayout layout) {

        float heightInPercentage;
        float widthInPercentage;
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        int deviceWidth = metrics.widthPixels;
        //on Nexus 5 deviceHeight= 1776, on Nexus 10 deviceHeight=800, on nexus one 3.7'
        // deviceHeight=480
        //on Nexus 6 deviceHeight= 2368, deviceWidth=1440
        int deviceHeight = metrics.heightPixels;
        // width:505/480, height: 889/800
        //first if is for devices like Nexus 5,6
        if ((deviceHeight > 1700 && deviceHeight < 1800) && (deviceWidth > 1050 &&
                deviceWidth < 1100)
                || (deviceHeight > 2300 && deviceHeight < 2400) && (deviceWidth > 1400 &&
                deviceWidth < 1500)) {
            widthInPercentage = ((float) 497 / 480) * 100;// 280 is the width of my
            // LinearLayout and 320 is device screen width as i know my current device
            // resolution are 320 x 480 so i'm calculating how much space (in percentage
            // my layout is covering so that it should cover same area (in percentage) on
            // any other device having different resolution
            heightInPercentage = ((float) 800 / 800) * 100; // same procedure 300 is the
            // height of the LinearLayout and i'm converting it into percentage
        }
        //this is the case of 7' table in portrait orientation deviceWidth=800
        // deviceHeight=1216
        else if ((deviceHeight > 1200 && deviceHeight < 1300) && (deviceWidth > 750 &&
                deviceWidth < 850)) {
            widthInPercentage = ((float) 490 / 480) * 100;
            heightInPercentage = ((float) 850 / 800) * 100;
        }
        //this is for the case of 8' tablet. deviceHeight=1836, deviceWidth=1080
        else if ((deviceHeight > 1800 && deviceHeight < 1850) && (deviceWidth > 1050 &&
                deviceWidth < 1100)) {
            widthInPercentage = ((float) 490 / 480) * 100;
            heightInPercentage = ((float) 800 / 800) * 100;
        }
        //this is the case of 9' tablet in vertical position. deviceWidth=1536,
        // deviceHeight=1952
        else if ((deviceHeight > 1900 && deviceHeight < 2000) && (deviceWidth > 1500 &&
                deviceWidth < 1550)) {
            widthInPercentage = ((float) 488 / 480) * 100;
            heightInPercentage = ((float) 800 / 800) * 100;
        } else {
            widthInPercentage = ((float) 499 / 480) * 100;
            heightInPercentage = ((float) 850 / 800) * 100;//was 800
        }

        int orientation = context.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            widthInPercentage = ((float) 490 / 480) * 100;
            heightInPercentage = ((float) 1380 / 800) * 100;//height was 1200

        }
        int mLayoutWidth = (int) ((widthInPercentage * deviceWidth) / 100);

        int mLayoutHeight = (int) ((heightInPercentage * deviceHeight) / 100);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (mLayoutWidth, mLayoutHeight);

        layout.setLayoutParams(layoutParams);
    }

    public void setBackgroundAndAdjustLayout(RelativeLayout layout, Activity activity) {

        if (PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext()).getString
                (BACKGROUND_COLOR, COLOR_WHITE).equals(COLOR_WHITE)) {
            layout.setBackgroundResource(R.drawable.backgroundimg);//need to call it somewhere to
            // get the wood style displayed
            if (activity instanceof MainActivity || activity instanceof EditActivity) {
                ImageView imageView =  activity.findViewById(R.id.ImageView);
                imageView.setImageResource(android.R.color.transparent);//need to clear the
                // background here because both gallery image and color are displayed
                adjustScreenSizeToDevice(layout);
            }
        }

        //the case where the user has selected for a background on image from the device gallery
        else if (PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext())
                .getString(BACKGROUND_COLOR, COLOR_WHITE).equals(COLOR_TRANSPARENT)) {

            SharedPreferences prefers = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
            //retrieve the file path from preferences
            String filePath = prefers.getString(GALLERY_IMAGE, COLOR_TRANSPARENT);
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            ImageView imageView =  activity.findViewById(R.id.ImageView);

            imageView.setImageBitmap(bitmap);
            //adjust the width and height to the layout
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            float widthInPercentage = 0f;
            float heightInPercentage = 0f;
            int deviceWidth = metrics.widthPixels;
            int deviceHeight = metrics.heightPixels;
            RelativeLayout.LayoutParams marginParams = new RelativeLayout.LayoutParams(imageView.getLayoutParams());

            if (activity instanceof MainActivity) {
                if (deviceHeight > 1280) {
                    widthInPercentage = ((float) 505 / 480) * 100;
                    heightInPercentage = ((float) 800 / 800) * 100;
                    marginParams.setMargins(-125, -130, 0, 0);
                    imageView.setLayoutParams(marginParams);
                }
                //this is for big tablet 7', 10'
                else {
                    widthInPercentage = ((float) 490 / 480) * 100;
                    heightInPercentage = ((float) 850 / 800) * 100;//was 800
                }
            }

            if (activity instanceof ReportActivity || activity instanceof BudgetActivity
                    || activity instanceof ChartActivity || activity instanceof HorizontalBarChartActivity
                    || activity instanceof AnnualChartActivity || activity instanceof AnnualSavingsActivity
                    || activity instanceof CalculateAnnualExpensesActivity) {
                widthInPercentage = ((float) 625 / 600) * 100;
                heightInPercentage = ((float) 940 / 1024) * 100;
            }

            if (activity instanceof EditActivity || activity instanceof LoginActivity) {
                widthInPercentage = ((float) 625 / 600) * 100;
                heightInPercentage = ((float) 1380 / 1024) * 100;//height was 980
            }

            if (activity instanceof HelpActivity || activity instanceof SettingsActivity) {
                widthInPercentage = ((float) 625 / 600) * 100;
                heightInPercentage = ((float) 1200 / 1024) * 100;//was 800
            }
                //ONLY IN LANDSCAPE VIEW this is for big tablets 7',10'
            int orientation = context.getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

                if (activity instanceof MainActivity) {
                    widthInPercentage = ((float) 495 / 480) * 100;
                    heightInPercentage = ((float) 1380 / 800) * 100;//height was 1200
                    marginParams.setMargins(-125, -130, -90, 0);
                    imageView.setLayoutParams(marginParams);
                }
                else if (activity instanceof LoginActivity){
                    marginParams.setMargins(-170, -130, -90, 0);
                    imageView.setLayoutParams(marginParams);
                    heightInPercentage = ((float) 2245 / 1024) * 100;//height was 980
                }
                else {
                    marginParams.setMargins(-170, -130, -90, 0);
                    imageView.setLayoutParams(marginParams);
                }
                layout.setBackground(imageView.getDrawable());
            }

            imageView.getLayoutParams().height = (int) ((heightInPercentage * deviceHeight) / 100);
            imageView.getLayoutParams().width = (int) ((widthInPercentage * deviceWidth) / 100);
            int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                if (activity instanceof LoginActivity) {
                    marginParams.setMargins(-50, -40, 0, 0);//i override here the Top and Bottom
                    // margin that is set on the xml view file
                    imageView.setLayoutParams(marginParams);
                }
                layout.setBackgroundDrawable(imageView.getDrawable());
            } else {
                layout.setBackground(imageView.getDrawable());
            }

        } else {
            layout.setBackgroundColor(Color.parseColor(PreferenceManager
                    .getDefaultSharedPreferences(activity.getApplicationContext()).getString
                            (BACKGROUND_COLOR, COLOR_WHITE)));

            if (activity instanceof MainActivity || activity instanceof EditActivity) {
                adjustScreenSizeToDevice(layout);
            }
        }
    }


}
