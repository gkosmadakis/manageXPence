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
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.millennialmedia.InlineAd;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import uk.co.irokottaki.moneycontrol.Utils.NothingSelectedSpinnerAdapter;
import uk.co.irokottaki.moneycontrol.R;
import uk.co.irokottaki.moneycontrol.Utils.Utils;

import static uk.co.irokottaki.moneycontrol.Utils.Constants.*;


public class ReportActivity extends AppCompatActivity {
    private Spinner monthItems;
    private ArrayAdapter<String> spinnerAdapter;
    private ArrayList<String> monthsAddedToSpinner;
    private TextView reportView;
    private static String fileLine, fileLineOct15, fileLineNov15, fileLineDec15, fileLineJan16,
            fileLineFeb16, fileLineMar16, fileLineApr16,
            fileLineMay16, fileLineJun16, fileLineJul16, fileLineAug16, fileLineSep16,
            fileLineOct16, fileLineNov16, fileLineDec16,
            fileLineJan17, fileLineFeb17, fileLineMar17, fileLineApr17, fileLineMay17,
            fileLineJun17, fileLineJul17, fileLineAug17,
            fileLineSep17, fileLineOct17, fileLineNov17, fileLineDec17,
            fileLineJan, fileLineFeb, fileLineMar, fileLineApr, fileLineMay, fileLineJun,
            fileLineJul, fileLineAug, fileLineSep,
            fileLineOct, fileLineNov, fileLineDec;
    private StringBuilder shortLine;
    private Button annualChartButton, exportButton, importButton;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private int monthInt;
    private static final String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private final String TAG = "Millenial Media";
    private InlineAd inlineAd;
    private boolean adsDisabled;

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

        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
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

        readTheFile();

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
                        formatReportArea(fileLineOct15);
                        reportView.setText(shortLine.toString());
                        break;
                    case 2:
                        formatReportArea(fileLineNov15);
                        reportView.setText(shortLine.toString());
                        break;
                    case 3:
                        formatReportArea(fileLineDec15);
                        reportView.setText(shortLine.toString());
                        break;
                    case 4:
                        formatReportArea(fileLineJan16);
                        reportView.setText(shortLine.toString());
                        break;
                    case 5:
                        formatReportArea(fileLineFeb16);
                        reportView.setText(shortLine.toString());
                        break;
                    case 6:
                        formatReportArea(fileLineMar16);
                        reportView.setText(shortLine.toString());
                        break;
                    case 7:
                        formatReportArea(fileLineApr16);
                        reportView.setText(shortLine.toString());
                        break;
                    case 8:
                        formatReportArea(fileLineMay16);
                        reportView.setText(shortLine.toString());
                        break;
                    case 9:
                        formatReportArea(fileLineJun16);
                        reportView.setText(shortLine.toString());
                        break;
                    case 10:
                        formatReportArea(fileLineJul16);
                        reportView.setText(shortLine.toString());
                        break;
                    case 11:
                        formatReportArea(fileLineAug16);
                        reportView.setText(shortLine.toString());
                        break;
                    case 12:
                        formatReportArea(fileLineSep16);
                        reportView.setText(shortLine.toString());
                        break;
                    case 13:
                        formatReportArea(fileLineOct16);
                        reportView.setText(shortLine.toString());
                        break;
                    case 14:
                        formatReportArea(fileLineNov16);
                        reportView.setText(shortLine.toString());
                        break;
                    case 15:
                        formatReportArea(fileLineDec16);
                        reportView.setText(shortLine.toString());
                        break;
                    case 16:
                        formatReportArea(fileLineJan17);
                        reportView.setText(shortLine.toString());
                        break;
                    case 17:
                        formatReportArea(fileLineFeb17);
                        reportView.setText(shortLine.toString());
                        break;
                    case 18:
                        formatReportArea(fileLineMar17);
                        reportView.setText(shortLine.toString());
                        break;
                    case 19:
                        formatReportArea(fileLineApr17);
                        reportView.setText(shortLine.toString());
                        break;
                    case 20:
                        formatReportArea(fileLineMay17);
                        reportView.setText(shortLine.toString());
                        break;
                    case 21:
                        formatReportArea(fileLineJun17);
                        reportView.setText(shortLine.toString());
                        break;
                    case 22:
                        formatReportArea(fileLineJul17);
                        reportView.setText(shortLine.toString());
                        break;
                    case 23:
                        formatReportArea(fileLineAug17);
                        reportView.setText(shortLine.toString());
                        break;
                    case 24:
                        formatReportArea(fileLineSep17);
                        reportView.setText(shortLine.toString());
                        break;
                    case 25:
                        formatReportArea(fileLineOct17);
                        reportView.setText(shortLine.toString());
                        break;
                    case 26:
                        formatReportArea(fileLineNov17);
                        reportView.setText(shortLine.toString());
                        break;
                    case 27:
                        formatReportArea(fileLineDec17);
                        reportView.setText(shortLine.toString());
                        break;
                    case 28:
                        formatReportArea(fileLineJan);
                        reportView.setText(shortLine.toString());
                        break;
                    case 29:
                        formatReportArea(fileLineFeb);
                        reportView.setText(shortLine.toString());
                        break;
                    case 30:
                        formatReportArea(fileLineMar);
                        reportView.setText(shortLine.toString());
                        break;
                    case 31:
                        formatReportArea(fileLineApr);
                        reportView.setText(shortLine.toString());
                        break;
                    case 32:
                        formatReportArea(fileLineMay);
                        reportView.setText(shortLine.toString());
                        break;
                    case 33:
                        formatReportArea(fileLineJun);
                        reportView.setText(shortLine.toString());
                        break;
                    case 34:
                        formatReportArea(fileLineJul);
                        reportView.setText(shortLine.toString());
                        break;
                    case 35:
                        formatReportArea(fileLineAug);
                        reportView.setText(shortLine.toString());
                        break;
                    case 36:
                        formatReportArea(fileLineSep);
                        reportView.setText(shortLine.toString());
                        break;
                    case 37:
                        formatReportArea(fileLineOct);
                        reportView.setText(shortLine.toString());
                        break;
                    case 38:
                        formatReportArea(fileLineNov);
                        reportView.setText(shortLine.toString());
                        break;
                    case 39:
                        formatReportArea(fileLineDec);
                        reportView.setText(shortLine.toString());
                        break;
                    default:
                        formatReportArea(fileLine);
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

    private void readTheFile() {
        fileLine = "";
        fileLineOct15 = "";
        fileLineNov15 = "";
        fileLineDec15 = "";
        fileLineJan16 = "";
        fileLineFeb16 = "";
        fileLineMar16 = "";
        fileLineApr16 = "";
        fileLineMay16 = "";
        fileLineJun16 = "";
        fileLineJul16 = "";
        fileLineAug16 = "";
        fileLineSep16 = "";
        fileLineOct16 = "";
        fileLineNov16 = "";
        fileLineDec16 = "";
        fileLineJan17 = "";
        fileLineFeb17 = "";
        fileLineMar17 = "";
        fileLineApr17 = "";
        fileLineMay17 = "";
        fileLineJun17 = "";
        fileLineJul17 = "";
        fileLineAug17 = "";
        fileLineSep17 = "";
        fileLineOct17 = "";
        fileLineNov17 = "";
        fileLineDec17 = "";
        fileLineJan = "";
        fileLineFeb = "";
        fileLineMar = "";
        fileLineApr = "";
        fileLineMay = "";
        fileLineJun = "";
        fileLineJul = "";
        fileLineAug = "";
        fileLineSep = "";
        fileLineOct = "";
        fileLineNov = "";
        fileLineDec = "";


        String desc = "";
        String date;

        try {
            InputStream inputStream = new FileInputStream("/data/data/uk.co.irokottaki" +
                    ".moneycontrol/files/expenses.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            int lineIndex = 0;//this is to count the lines
            while ((line = br.readLine()) != null) {

                if (lineIndex == 0) {
                    fileLine += line + "\n" + "\n";// get the contents of the file after the header
                }
                if (line.startsWith("Amount")) {
                    fileLineOct15 += line + "\n" + "\n";
                    fileLineNov15 += line + "\n" + "\n";
                    fileLineDec15 += line + "\n" + "\n";

                    fileLineJan16 += line + "\n" + "\n";
                    fileLineFeb16 += line + "\n" + "\n";
                    fileLineMar16 += line + "\n" + "\n";
                    fileLineApr16 += line + "\n" + "\n";
                    fileLineMay16 += line + "\n" + "\n";
                    fileLineJun16 += line + "\n" + "\n";
                    fileLineJul16 += line + "\n" + "\n";
                    fileLineAug16 += line + "\n" + "\n";
                    fileLineSep16 += line + "\n" + "\n";
                    fileLineOct16 += line + "\n" + "\n";
                    fileLineNov16 += line + "\n" + "\n";
                    fileLineDec16 += line + "\n" + "\n";

                    fileLineJan17 += line + "\n" + "\n";
                    fileLineFeb17 += line + "\n" + "\n";
                    fileLineMar17 += line + "\n" + "\n";
                    fileLineApr17 += line + "\n" + "\n";
                    fileLineMay17 += line + "\n" + "\n";
                    fileLineJun17 += line + "\n" + "\n";
                    fileLineJul17 += line + "\n" + "\n";
                    fileLineAug17 += line + "\n" + "\n";
                    fileLineSep17 += line + "\n" + "\n";
                    fileLineOct17 += line + "\n" + "\n";
                    fileLineNov17 += line + "\n" + "\n";
                    fileLineDec17 += line + "\n" + "\n";

                    fileLineJan += line + "\n" + "\n";
                    fileLineFeb += line + "\n" + "\n";
                    fileLineMar += line + "\n" + "\n";
                    fileLineApr += line + "\n" + "\n";
                    fileLineMay += line + "\n" + "\n";
                    fileLineJun += line + "\n" + "\n";
                    fileLineJul += line + "\n" + "\n";
                    fileLineAug += line + "\n" + "\n";
                    fileLineSep += line + "\n" + "\n";
                    fileLineOct += line + "\n" + "\n";
                    fileLineNov += line + "\n" + "\n";
                    fileLineDec += line + "\n" + "\n";

                }
                if (++lineIndex > 2 && !line.equals("")) {
                    fileLine += line + "\n";
                    int index = line.lastIndexOf(" ");
                    desc = line.substring(line.indexOf(" "), index).trim();
                    date = line.substring(index, line.length());
                    String extractMonthFromDate = date.substring(date.indexOf("/") + 1, date
                            .lastIndexOf("/"));
                    if (extractMonthFromDate.startsWith("0")) {
                        extractMonthFromDate = extractMonthFromDate.replace("0", "");
                    }
                    String extractYearFromDate = date.substring(date.lastIndexOf("/") + 1, date
                            .length());

                    if (extractMonthFromDate.equals(TEN) && extractYearFromDate.equals(TWOTHOUSANDFIFTEEN)) {
                        fileLineOct15 += line + "\n";
                    }
                    if (extractMonthFromDate.equals(ELEVEN) && extractYearFromDate.equals(TWOTHOUSANDFIFTEEN)) {
                        fileLineNov15 += line + "\n";
                    }
                    if (extractMonthFromDate.equals(TWELVE) && extractYearFromDate.equals(TWOTHOUSANDFIFTEEN)) {
                        fileLineDec15 += line + "\n";
                    }

                    if (extractMonthFromDate.equals(ONE) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {
                        fileLineJan16 += line + "\n";
                    }
                    if (extractMonthFromDate.equals(TWO) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {
                        fileLineFeb16 += line + "\n";
                    }
                    if (extractMonthFromDate.equals(THREE) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {
                        fileLineMar16 += line + "\n";
                    }
                    if (extractMonthFromDate.equals(FOUR) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {
                        fileLineApr16 += line + "\n";
                    }
                    if (extractMonthFromDate.equals(FIVE) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {
                        fileLineMay16 += line + "\n";
                    }
                    if (extractMonthFromDate.equals(SIX) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {
                        fileLineJun16 += line + "\n";
                    }
                    if (extractMonthFromDate.equals(SEVEN) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {
                        fileLineJul16 += line + "\n";
                    }
                    if (extractMonthFromDate.equals(EIGHT) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {
                        fileLineAug16 += line + "\n";
                    }
                    if (extractMonthFromDate.equals(NINE) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {
                        fileLineSep16 += line + "\n";
                    }
                    if (extractMonthFromDate.equals(TEN) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {
                        fileLineOct16 += line + "\n";
                    }
                    if (extractMonthFromDate.equals(ELEVEN) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {
                        fileLineNov16 += line + "\n";
                    }
                    if (extractMonthFromDate.equals(TWELVE) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {
                        fileLineDec16 += line + "\n";
                    }

                    if (extractMonthFromDate.equals(ONE) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {
                        fileLineJan17 += line + "\n";
                    }
                    if (extractMonthFromDate.equals(TWO) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {
                        fileLineFeb17 += line + "\n";
                    }
                    if (extractMonthFromDate.equals(THREE) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {
                        fileLineMar17 += line + "\n";
                    }
                    if (extractMonthFromDate.equals(FOUR) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {
                        fileLineApr17 += line + "\n";
                    }
                    if (extractMonthFromDate.equals(FIVE) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {
                        fileLineMay17 += line + "\n";
                    }
                    if (extractMonthFromDate.equals(SIX) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {
                        fileLineJun17 += line + "\n";
                    }
                    if (extractMonthFromDate.equals(SEVEN) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {
                        fileLineJul17 += line + "\n";
                    }
                    if (extractMonthFromDate.equals(EIGHT) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {
                        fileLineAug17 += line + "\n";
                    }
                    if (extractMonthFromDate.equals(NINE) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {
                        fileLineSep17 += line + "\n";
                    }
                    if (extractMonthFromDate.equals(TEN) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {
                        fileLineOct17 += line + "\n";
                    }
                    if (extractMonthFromDate.equals(ELEVEN) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {
                        fileLineNov17 += line + "\n";
                    }
                    if (extractMonthFromDate.equals(TWELVE) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {
                        fileLineDec17 += line + "\n";
                    }

                    if (extractMonthFromDate.equals(ONE) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {
                        fileLineJan += line + "\n";
                    }
                    if (extractMonthFromDate.equals(TWO) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {
                        fileLineFeb += line + "\n";
                    }
                    if (extractMonthFromDate.equals(THREE) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {
                        fileLineMar += line + "\n";
                    }
                    if (extractMonthFromDate.equals(FOUR) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {
                        fileLineApr += line + "\n";
                    }
                    if (extractMonthFromDate.equals(FIVE) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {
                        fileLineMay += line + "\n";
                    }
                    if (extractMonthFromDate.equals(SIX) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {
                        fileLineJun += line + "\n";
                    }
                    if (extractMonthFromDate.equals(SEVEN) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {
                        fileLineJul += line + "\n";
                    }
                    if (extractMonthFromDate.equals(EIGHT) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {
                        fileLineAug += line + "\n";
                    }
                    if (extractMonthFromDate.equals(NINE) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {
                        fileLineSep += line + "\n";
                    }
                    if (extractMonthFromDate.equals(TEN) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {
                        fileLineOct += line + "\n";
                    }
                    if (extractMonthFromDate.equals(ELEVEN) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {
                        fileLineNov += line + "\n";
                    }
                    if (extractMonthFromDate.equals(TWELVE) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {
                        fileLineDec += line + "\n";
                    }
                }
            }// end of while
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
