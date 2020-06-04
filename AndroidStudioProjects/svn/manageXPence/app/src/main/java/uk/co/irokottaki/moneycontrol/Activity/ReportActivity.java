package uk.co.irokottaki.moneycontrol.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import uk.co.irokottaki.moneycontrol.R;
import uk.co.irokottaki.moneycontrol.model.AnyYear;
import uk.co.irokottaki.moneycontrol.utils.ChartsUtil;
import uk.co.irokottaki.moneycontrol.utils.NothingSelectedSpinnerAdapter;
import uk.co.irokottaki.moneycontrol.utils.Utils;

import static uk.co.irokottaki.moneycontrol.utils.Constants.APRIL;
import static uk.co.irokottaki.moneycontrol.utils.Constants.AUGUST;
import static uk.co.irokottaki.moneycontrol.utils.Constants.DECEMBER;
import static uk.co.irokottaki.moneycontrol.utils.Constants.EXPENSES_REPORT;
import static uk.co.irokottaki.moneycontrol.utils.Constants.FEBRUARY;
import static uk.co.irokottaki.moneycontrol.utils.Constants.JANUARY;
import static uk.co.irokottaki.moneycontrol.utils.Constants.JULY;
import static uk.co.irokottaki.moneycontrol.utils.Constants.JUNE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.MARCH;
import static uk.co.irokottaki.moneycontrol.utils.Constants.MAY;
import static uk.co.irokottaki.moneycontrol.utils.Constants.NOVEMBER;
import static uk.co.irokottaki.moneycontrol.utils.Constants.OCTOBER;
import static uk.co.irokottaki.moneycontrol.utils.Constants.SEPTEMBER;
import static uk.co.irokottaki.moneycontrol.utils.Constants.TOTAL;


public class ReportActivity extends AppCompatActivity {
    private Spinner monthItems;
    private ArrayList<String> monthsAddedToSpinner;
    private TextView reportView;
    /*private static final String TAG = "Millenial Media";
    private InlineAd inlineAd;*/
    private ChartsUtil util;
    private Spinner yearList;
    private int yearSelected;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        setTitle(EXPENSES_REPORT);

        /*SharedPreferences sharedprefs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        boolean adsDisabled = sharedprefs.getBoolean(ADSDISABLED, false);//retrieve the boolean value
        // for ads

        if (adsDisabled==false) {
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
        int id = item.getItemId();// This is added to avoid the call on the onCreate method of MainActivity because it will read again the file and iterate the map
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
