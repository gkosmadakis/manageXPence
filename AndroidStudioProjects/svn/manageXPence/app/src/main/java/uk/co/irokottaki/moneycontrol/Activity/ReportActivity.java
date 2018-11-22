package uk.co.irokottaki.moneycontrol.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.millennialmedia.InlineAd;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import uk.co.irokottaki.moneycontrol.R;
import uk.co.irokottaki.moneycontrol.Utils.ChartsUtil;
import uk.co.irokottaki.moneycontrol.Utils.NothingSelectedSpinnerAdapter;
import uk.co.irokottaki.moneycontrol.Utils.Utils;

import static uk.co.irokottaki.moneycontrol.Utils.Constants.*;


public class ReportActivity extends AppCompatActivity {
    private Spinner monthItems;
    private ArrayList<String> monthsAddedToSpinner;
    private TextView reportView;
    private StringBuilder shortLine;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private int monthInt;
    private static final String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private final String TAG = "Millenial Media";
    private InlineAd inlineAd;
    private boolean adsDisabled;
    private ChartsUtil util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        setTitle(EXPENSES_REPORT);

        SharedPreferences sharedprefs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        adsDisabled = sharedprefs.getBoolean(ADSDISABLED, false);//retrieve the boolean value
        // for ads

        /*if (adsDisabled==false) {
            //this is for the ads Millenial Media
            MMSDK.initialize(this); // pass in current activity instance

            //Create the inline placement instance and set the listeners.
            try {
                // NOTE: The ad container argument passed to the createInstance call should be the
                // view container that the ad content will be injected into.

                FrameLayout adsLayout = new FrameLayout(this);
                adsLayout = (FrameLayout) findViewById(R.id.adReportView);
                inlineAd = InlineAd.createInstance("220118", (ViewGroup) adsLayout);

                inlineAd.setListener(new InlineAd.InlineListener() {
                    @Override
                    public void onRequestSucceeded(InlineAd inlineAd) {

                        Log.i(TAG, "Inline Ad loaded.");
                    }

                    @Override
                    public void onRequestFailed(InlineAd inlineAd, InlineAd.InlineErrorStatus
                    errorStatus) {

                        Log.i(TAG, errorStatus.toString());
                    }

                    @Override
                    public void onClicked(InlineAd inlineAd) {

                        Log.i(TAG, "Inline Ad clicked.");
                    }

                    @Override
                    public void onResize(InlineAd inlineAd, int width, int height) {

                        Log.i(TAG, "Inline Ad starting resize.");
                    }

                    @Override
                    public void onResized(InlineAd inlineAd, int width, int height, boolean
                    toOriginalSize) {

                        Log.i(TAG, "Inline Ad resized.");
                    }

                    @Override
                    public void onExpanded(InlineAd inlineAd) {

                        Log.i(TAG, "Inline Ad expanded.");
                    }

                    @Override
                    public void onCollapsed(InlineAd inlineAd) {

                        Log.i(TAG, "Inline Ad collapsed.");
                    }

                    @Override
                    public void onAdLeftApplication(InlineAd inlineAd) {

                        Log.i(TAG, "Inline Ad left application.");
                    }
                });

            } catch (MMException e) {
                Log.e(TAG, "Error creating inline ad", e);
                // abort loading ad
            }

            //Set the metadata and request the ad.
            if (inlineAd != null) {
                // set a refresh rate of 30 seconds that will be applied after the first request
                inlineAd.setRefreshInterval(30000);

                // The InlineAdMetadata instance is used to pass additional metadata to the
                server to
                // improve ad selection
                final InlineAd.InlineAdMetadata inlineAdMetadata = new InlineAd.InlineAdMetadata().
                        setAdSize(InlineAd.AdSize.BANNER);

                inlineAd.request(inlineAdMetadata);
            }
        }*/

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.reportView);

        //this is to change the background color of the activity when user changes it from settings
        Utils utils = new Utils(this);
        utils.setBackgroundAndAdjustLayout(layout, ReportActivity.this);



        // Spinner with the months
        monthItems = (Spinner) findViewById(R.id.monthSpinner);

        /*String monthData [] = new String[] {"Month", "January","February","March","April",
                "May","June","July","August","September","October","November","December",
                "Total"};*/

        monthsAddedToSpinner = new ArrayList<>();
        monthsAddedToSpinner.add(OCTOBER+EMPTY_SPACE+TWOTHOUSANDFIFTEEN);
        monthsAddedToSpinner.add(NOVEMBER+EMPTY_SPACE+TWOTHOUSANDFIFTEEN);
        monthsAddedToSpinner.add(DECEMBER+EMPTY_SPACE+TWOTHOUSANDFIFTEEN);
        monthsAddedToSpinner.add(JANUARY+EMPTY_SPACE+TWOTHOUSANDSIXTEEN);
        monthsAddedToSpinner.add(FEBRUARY+EMPTY_SPACE+TWOTHOUSANDSIXTEEN);
        monthsAddedToSpinner.add(MARCH+EMPTY_SPACE+TWOTHOUSANDSIXTEEN);
        monthsAddedToSpinner.add(APRIL+EMPTY_SPACE+TWOTHOUSANDSIXTEEN);
        monthsAddedToSpinner.add(MAY+EMPTY_SPACE+TWOTHOUSANDSIXTEEN);
        monthsAddedToSpinner.add(JUNE+EMPTY_SPACE+TWOTHOUSANDSIXTEEN);
        monthsAddedToSpinner.add(JULY+EMPTY_SPACE+TWOTHOUSANDSIXTEEN);
        monthsAddedToSpinner.add(AUGUST+EMPTY_SPACE+TWOTHOUSANDSIXTEEN);
        monthsAddedToSpinner.add(SEPTEMBER+EMPTY_SPACE+TWOTHOUSANDSIXTEEN);
        monthsAddedToSpinner.add(OCTOBER+EMPTY_SPACE+TWOTHOUSANDSIXTEEN);
        monthsAddedToSpinner.add(NOVEMBER+EMPTY_SPACE+TWOTHOUSANDSIXTEEN);
        monthsAddedToSpinner.add(DECEMBER+EMPTY_SPACE+TWOTHOUSANDSIXTEEN);
        monthsAddedToSpinner.add(JANUARY+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN);
        monthsAddedToSpinner.add(FEBRUARY+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN);
        monthsAddedToSpinner.add(MARCH+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN);
        monthsAddedToSpinner.add(APRIL+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN);
        monthsAddedToSpinner.add(MAY+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN);
        monthsAddedToSpinner.add(JUNE+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN);
        monthsAddedToSpinner.add(JULY+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN);
        monthsAddedToSpinner.add(AUGUST+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN);
        monthsAddedToSpinner.add(SEPTEMBER+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN);
        monthsAddedToSpinner.add(OCTOBER+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN);
        monthsAddedToSpinner.add(NOVEMBER+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN);
        monthsAddedToSpinner.add(DECEMBER+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN);
        monthsAddedToSpinner.add(JANUARY);
        monthsAddedToSpinner.add(FEBRUARY);
        monthsAddedToSpinner.add(MARCH);
        monthsAddedToSpinner.add(APRIL);
        monthsAddedToSpinner.add(MAY);
        monthsAddedToSpinner.add(JUNE);
        monthsAddedToSpinner.add(JULY);
        monthsAddedToSpinner.add(AUGUST);
        monthsAddedToSpinner.add(SEPTEMBER);
        monthsAddedToSpinner.add(OCTOBER);
        monthsAddedToSpinner.add(NOVEMBER);
        monthsAddedToSpinner.add(DECEMBER);
        monthsAddedToSpinner.add(TOTAL);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                monthsAddedToSpinner);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthItems.setAdapter(spinnerAdapter);

        monthItems.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        spinnerAdapter,
                        R.layout.spinnernothingselected,
                        this));

        // TextView of the report
        reportView = new TextView(this);
        reportView = (TextView) findViewById(R.id.reportTextView);
        reportView.setMovementMethod(new ScrollingMovementMethod());
        reportView.setTypeface(Typeface.MONOSPACE);

        util = new ChartsUtil(this);
        util.readTheFile();
        
        //readTheFile();

        //Get the current month
        final Calendar calendar = Calendar.getInstance();//this gets the current month
        String currentMonth = String.format(Locale.UK, "%tB", calendar);

        //convert month String to integer
        monthInt = 0;
        try {
            java.util.Date date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(currentMonth);
            calendar.setTime(date);// here i convert the String month in an integer
            monthInt = calendar.get(Calendar.MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //set the spinner to the current month
        monthItems.setSelection(findIndexForCurrentMonth(currentMonth));

        monthItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                monthItems = (Spinner) findViewById(R.id.monthSpinner);

                int getMonthSelection = monthItems.getSelectedItemPosition();

                switch (getMonthSelection) {

                    case 1:
                        formatReportArea(util.getObjectYear().getYear2015().getFileLineOct15());
                        reportView.setText(shortLine.toString());
                        break;
                    case 2:
                        formatReportArea(util.getObjectYear().getYear2015().getFileLineNov15());
                        reportView.setText(shortLine.toString());
                        break;
                    case 3:
                        formatReportArea(util.getObjectYear().getYear2015().getFileLineDec15());
                        reportView.setText(shortLine.toString());
                        break;
                    case 4:
                        formatReportArea(util.getObjectYear().getYear2016().getFileLineJan16());
                        reportView.setText(shortLine.toString());
                        break;
                    case 5:
                        formatReportArea(util.getObjectYear().getYear2016().getFileLineFeb16());
                        reportView.setText(shortLine.toString());
                        break;
                    case 6:
                        formatReportArea(util.getObjectYear().getYear2016().getFileLineMar16());
                        reportView.setText(shortLine.toString());
                        break;
                    case 7:
                        formatReportArea(util.getObjectYear().getYear2016().getFileLineApr16());
                        reportView.setText(shortLine.toString());
                        break;
                    case 8:
                        formatReportArea(util.getObjectYear().getYear2016().getFileLineMay16());
                        reportView.setText(shortLine.toString());
                        break;
                    case 9:
                        formatReportArea(util.getObjectYear().getYear2016().getFileLineJun16());
                        reportView.setText(shortLine.toString());
                        break;
                    case 10:
                        formatReportArea(util.getObjectYear().getYear2016().getFileLineJul16());
                        reportView.setText(shortLine.toString());
                        break;
                    case 11:
                        formatReportArea(util.getObjectYear().getYear2016().getFileLineAug16());
                        reportView.setText(shortLine.toString());
                        break;
                    case 12:
                        formatReportArea(util.getObjectYear().getYear2016().getFileLineSep16());
                        reportView.setText(shortLine.toString());
                        break;
                    case 13:
                        formatReportArea(util.getObjectYear().getYear2016().getFileLineOct16());
                        reportView.setText(shortLine.toString());
                        break;
                    case 14:
                        formatReportArea(util.getObjectYear().getYear2016().getFileLineNov16());
                        reportView.setText(shortLine.toString());
                        break;
                    case 15:
                        formatReportArea(util.getObjectYear().getYear2016().getFileLineDec16());
                        reportView.setText(shortLine.toString());
                        break;
                    case 16:
                        formatReportArea(util.getObjectYear().getYear2017().getFileLineJan17());
                        reportView.setText(shortLine.toString());
                        break;
                    case 17:
                        formatReportArea(util.getObjectYear().getYear2017().getFileLineFeb17());
                        reportView.setText(shortLine.toString());
                        break;
                    case 18:
                        formatReportArea(util.getObjectYear().getYear2017().getFileLineMar17());
                        reportView.setText(shortLine.toString());
                        break;
                    case 19:
                        formatReportArea(util.getObjectYear().getYear2017().getFileLineApr17());
                        reportView.setText(shortLine.toString());
                        break;
                    case 20:
                        formatReportArea(util.getObjectYear().getYear2017().getFileLineMay17());
                        reportView.setText(shortLine.toString());
                        break;
                    case 21:
                        formatReportArea(util.getObjectYear().getYear2017().getFileLineJun17());
                        reportView.setText(shortLine.toString());
                        break;
                    case 22:
                        formatReportArea(util.getObjectYear().getYear2017().getFileLineJul17());
                        reportView.setText(shortLine.toString());
                        break;
                    case 23:
                        formatReportArea(util.getObjectYear().getYear2017().getFileLineAug17());
                        reportView.setText(shortLine.toString());
                        break;
                    case 24:
                        formatReportArea(util.getObjectYear().getYear2017().getFileLineSep17());
                        reportView.setText(shortLine.toString());
                        break;
                    case 25:
                        formatReportArea(util.getObjectYear().getYear2017().getFileLineOct17());
                        reportView.setText(shortLine.toString());
                        break;
                    case 26:
                        formatReportArea(util.getObjectYear().getYear2017().getFileLineNov17());
                        reportView.setText(shortLine.toString());
                        break;
                    case 27:
                        formatReportArea(util.getObjectYear().getYear2017().getFileLineDec17());
                        reportView.setText(shortLine.toString());
                        break;
                    case 28:
                        formatReportArea(util.getObjectYear().getYear2018().getFileLineJan());
                        reportView.setText(shortLine.toString());
                        break;
                    case 29:
                        formatReportArea(util.getObjectYear().getYear2018().getFileLineFeb());
                        reportView.setText(shortLine.toString());
                        break;
                    case 30:
                        formatReportArea(util.getObjectYear().getYear2018().getFileLineMar());
                        reportView.setText(shortLine.toString());
                        break;
                    case 31:
                        formatReportArea(util.getObjectYear().getYear2018().getFileLineApr());
                        reportView.setText(shortLine.toString());
                        break;
                    case 32:
                        formatReportArea(util.getObjectYear().getYear2018().getFileLineMay());
                        reportView.setText(shortLine.toString());
                        break;
                    case 33:
                        formatReportArea(util.getObjectYear().getYear2018().getFileLineJun());
                        reportView.setText(shortLine.toString());
                        break;
                    case 34:
                        formatReportArea(util.getObjectYear().getYear2018().getFileLineJul());
                        reportView.setText(shortLine.toString());
                        break;
                    case 35:
                        formatReportArea(util.getObjectYear().getYear2018().getFileLineAug());
                        reportView.setText(shortLine.toString());
                        break;
                    case 36:
                        formatReportArea(util.getObjectYear().getYear2018().getFileLineSep());
                        reportView.setText(shortLine.toString());
                        break;
                    case 37:
                        formatReportArea(util.getObjectYear().getYear2018().getFileLineOct());
                        reportView.setText(shortLine.toString());
                        break;
                    case 38:
                        formatReportArea(util.getObjectYear().getYear2018().getFileLineNov());
                        reportView.setText(shortLine.toString());
                        break;
                    case 39:
                        formatReportArea(util.getObjectYear().getYear2018().getFileLineDec());
                        reportView.setText(shortLine.toString());
                        break;
                    default:
                        formatReportArea(util.getObjectYear().getAllLinesInFile());
                        reportView.setText(shortLine.toString());
                        break;
                }//end of switch
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                /*Not used*/

            }
        });

    }// end of create method



    private int findIndexForCurrentMonth(String currentMonth) {
        int index = 1;
        for (String monthIndex : monthsAddedToSpinner) {

            if (monthIndex.equals(currentMonth)) {
                break;
            }
            index++;
        }
        return index;
    }

    /**
     * Checks if the app has permission to write to device storage
     * <p>
     * If the app does not has permission then the user will be prompted to grant permissions
     * <p>
     * /* @param activity
     */
    private static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, android.Manifest.permission
                .WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }



    private void formatReportArea(String fileLine) {
        shortLine = null;
        shortLine = new StringBuilder();
        String formatStr = "%-8s%-15s%-10s";

        String[] lines = fileLine.split("\n");

        for (int i = 0; i < lines.length; i++) {
            if (i == 0) {
                String amount = AMOUNT;
                String shortDesc = DESCRIPTION;
                String date = DATE;
                shortLine.append(String.format(formatStr, amount, shortDesc, date)).trimToSize();
                shortLine.append("\n");
            } else if (i > 1 && !lines[i].equals("")) {
                String amount = lines[i].substring(0, lines[i].indexOf(" "));
                String shortDesc = lines[i].substring(lines[i].indexOf(" "), lines[i].lastIndexOf
                        (" ")).trim();
                String date = lines[i].substring(lines[i].lastIndexOf(" "), lines[i].length())
                        .trim();
                shortLine.append(String.format(formatStr, amount, shortDesc, date)).trimToSize();
                shortLine.append("\n");
            } else {
                shortLine.append(lines[i]);//this is to write the header Amount Description Date
                // and a new line
                shortLine.append("\n");
            }
        }

    }

    @Override
    public Intent getSupportParentActivityIntent() {
        final Bundle bundle = new Bundle();
        final Intent intent = new Intent(this, MainActivity.class);

        bundle.putInt(REPORT, 1);
        intent.putExtras(bundle);

        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_report, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
