package uk.co.irokottaki.moneycontrol.activity;

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
import android.util.Log;
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
import java.util.HashMap;
import java.util.Locale;

import uk.co.irokottaki.moneycontrol.model.AnyYear;
import uk.co.irokottaki.moneycontrol.R;
import uk.co.irokottaki.moneycontrol.utils.ChartsUtil;
import uk.co.irokottaki.moneycontrol.utils.NothingSelectedSpinnerAdapter;
import uk.co.irokottaki.moneycontrol.utils.Utils;

import static uk.co.irokottaki.moneycontrol.utils.Constants.*;


public class ReportActivity extends AppCompatActivity {
    private Spinner monthItems;
    private ArrayList<String> monthsAddedToSpinner;
    private TextView reportView;
    private StringBuilder shortLine;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private final String TAG = "Millenial Media";
    private InlineAd inlineAd;
    private boolean adsDisabled;
    private ChartsUtil util;
    private Spinner yearList;
    private int yearSelected;



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

        // Text view for year and month
        TextView yearView = (TextView) findViewById(R.id.yearView);
        TextView monthView = (TextView) findViewById(R.id.monthView);

        // Spinner with the months
        monthItems = (Spinner) findViewById(R.id.monthSpinner);

        /*Spinner to hold the years*/
        yearList = (Spinner) findViewById(R.id.yearSpinnerReport);

        monthsAddedToSpinner = new ArrayList<>();
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

        Intent intent = getIntent();
        HashMap<String, AnyYear> yearsMappedToObjectYearsMap = (HashMap<String, AnyYear> ) intent.getSerializableExtra("yearsMappedToObjectYearsMap");

        //Get the current month
        final Calendar calendar = Calendar.getInstance();//this gets the current month
        String currentMonth = String.format(Locale.UK, "%tB", calendar);
        int year = calendar.get(Calendar.YEAR);// get the current year

        //convert month String to integer
        int monthInt = 0;
        try {
            java.util.Date date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(currentMonth);
            calendar.setTime(date);// here i convert the String month in an integer
            monthInt = calendar.get(Calendar.MONTH);

        } catch (ParseException e) {
            Log.e("ParseException", e.getMessage());
        }

        util.populateYearSpinnerAndSetCurrentYear(yearsMappedToObjectYearsMap, year, yearList, ReportActivity.this);

        //set the spinner to the current month
        monthItems.setSelection(findIndexForCurrentMonth(currentMonth));

        yearList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                yearList = (Spinner) findViewById(R.id.yearSpinnerReport);

                yearSelected = Integer.parseInt(yearList.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                /* Not used */
            }
        });

        monthItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = getIntent();
                HashMap<String, AnyYear> yearsMappedToObjectYearsMap = (HashMap<String, AnyYear> ) intent.getSerializableExtra("yearsMappedToObjectYearsMap");

                monthItems = (Spinner) findViewById(R.id.monthSpinner);

                int getMonthSelection = monthItems.getSelectedItemPosition();

                util.switchMonthsReport(yearsMappedToObjectYearsMap,getMonthSelection, yearSelected, ReportActivity.this);
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

    public TextView getReportView() {
        return reportView;
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
        switch (item.getItemId()) { // This is added to avoid the call on the onCreate method of MainActivity because it will read again the file and iterate the map
             case android.R.id.home: finish();
             return true;
        }
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
