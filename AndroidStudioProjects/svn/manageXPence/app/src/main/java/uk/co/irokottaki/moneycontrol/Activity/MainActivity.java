package uk.co.irokottaki.moneycontrol.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.android.vending.billing.IInAppBillingService;
import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import com.dropbox.core.v2.users.FullAccount;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.millennialmedia.InlineAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import hod.api.hodclient.HODApps;
import hod.api.hodclient.HODClient;
import hod.api.hodclient.IHODClientCallback;
import uk.co.irokottaki.moneycontrol.DropboxClient;
import uk.co.irokottaki.moneycontrol.IabHelper;
import uk.co.irokottaki.moneycontrol.IabResult;
import uk.co.irokottaki.moneycontrol.Inventory;
import uk.co.irokottaki.moneycontrol.Utils.NothingSelectedSpinnerAdapter;
import uk.co.irokottaki.moneycontrol.Purchase;
import uk.co.irokottaki.moneycontrol.R;
import uk.co.irokottaki.moneycontrol.UploadTask;
import uk.co.irokottaki.moneycontrol.UserAccountTask;
import uk.co.irokottaki.moneycontrol.Utils.Utils;

import static android.content.DialogInterface.OnClickListener;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.*;


public class MainActivity extends AppCompatActivity implements IHODClientCallback {

    private Button saveToFile, addExpensesButton, reportButton, dateButton, editButton,
            chartButton, barChartButton,
            addDescrButton, budgetButton, annualChartButton, exportButton, importButton,
            savingsButton, circleButton;
    private ImageButton infoDateRangeButton;
    ImageButton backUpInfoButton;
    ImageButton photoButton;
    EditText expensesField, dateText, addedDescriptionField = null, incomeField;
    private Spinner descriptionsItem, addExpensesByDescription;
    static ArrayList<Date> dates = new ArrayList<>();
    final ArrayList<Double> expenses = new ArrayList<>();
    private static String fileLine;
    private int year_x, month_x, day_x, valueFromNumPicker1, valueFromNumPicker2;
    static final int DIALOG_ID = 0;
    private TextView incomeLabel, balanceLabel, numberPickerLabel1, numberPickerLabel2;
    ArrayAdapter<String> spinnerAdapter;
    private static ArrayList<String> itemsAddedByUser;
    private String descriptionAddedByUser, ACCESS_TOKEN;
    public ArrayList<String> allDescriptions;//descriptions
    RelativeLayout layout;
    private static ArrayList<Float> arrayOfamountOct15, arrayOfamountNov15, arrayOfamountDec15,
            arrayOfamountJan16, arrayOfamountFeb16,
            arrayOfamountMar16, arrayOfamountApr16, arrayOfamountMay16, arrayOfamountJun16,
            arrayOfamountJul16, arrayOfamountAug16,
            arrayOfamountSep16, arrayOfamountOct16, arrayOfamountNov16, arrayOfamountDec16,
            arrayOfamountJan17, arrayOfamountFeb17, arrayOfamountMar17, arrayOfamountApr17,
            arrayOfamountMay17, arrayOfamountJun17,
            arrayOfamountJul17, arrayOfamountAug17, arrayOfamountSep17, arrayOfamountOct17,
            arrayOfamountNov17, arrayOfamountDec17,
            arrayOfamountJan, arrayOfamountFeb, arrayOfamountMar, arrayOfamountApr,
            arrayOfamountMay, arrayOfamountJun, arrayOfamountJul,
            arrayOfamountAug, arrayOfamountSep, arrayOfamountOct, arrayOfamountNov,
            arrayOfamountDec;//all the amounts for the months
    protected PreferenceManager mPreferenceManager;
    private LinkedHashSet<String> uniqueDescriptions;
    ArrayList<Float> uniqueAmounts;
    private float amountWithDuplicate;
    private double monthSum, balance, incomeDouble;
    TabHost tabHost;
    private final Handler handler = new Handler();
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    ImageView imageView;
    private final String TAG = "Millenial Media", TAG2 = "In App Billing";
    private InlineAd inlineAd;
    IabHelper mHelper;
    final String base64EncodedPublicKey =
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqcYpXYA3pWCTMjOYJNNC70rNhXmbwxI5i4sGCtmZWN" +
                    "+eVFvrvtBtlwm8Wxwab8wf4CyLUxthccmgSd2Wmb6lHYVHG9/F7VSn+u3f9tnu8x" +
                    "+Oh30fyiSr4Wdesz0yfTwflVipA4wNwcEjxJoO0t8CCEyswQZcAzLAMzkodlMVwcdWx0kJ39qJxxuT8LWFlqwDpUSlLm6sPr+XmbD/vhfmd1h+qNQTteVte2Q5vVLSAk1/hCsqLCzrDp0BJ30w4f0nzEBn3g/7KIn3KQQp+6JE+xJanavahcvAU//PTDmy8t/bYxiFtn8kquBCL9xcHa/2Nw8PTEhzeWx3hCRUAugruwIDAQAB";
    public boolean adsDisabled, userIsPro;
    IInAppBillingService mService;
    Bundle querySkus;
    final String sku = "android.test.purchased";
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener;
    private HorizontalBarChart mChart;
    private FrameLayout stackedBarLayout;
    private static Float incomeForJan, incomeForFeb, incomeForMar, incomeForApr, incomeForMay,
            incomeForJun, incomeForJul, incomeForAug,
            incomeForSep, incomeForOct, incomeForNov, incomeForDec;
    private NumberPicker numberPicker1, numberPicker2;
    HashMap<String, Float> amountsRelatedToDays, amountsRelatedToDaysAfterSalaryDay;
    private boolean isPaymentCircleSet, budgetWarningEnabled, invalidToken, expenseFound = false,
            dateFound = false, descFound = false;
    private final String apikey = "2990d40f-d7ca-4fdb-bacd-98d3cbe6eef5";
    private static final int TAKE_PICTURE = 1;
    private static byte[] byteArray = null;
    HODClient hodClient;
    final static private String APP_KEY = "h5x1321simc1oxm", APP_SECRET = "y2hrku7km5xdtvi";
    private static final int IMAGE_REQUEST_CODE = 101;
    private Uri imageUri;
    private static Map<String, String> DATE_FORMAT_REGEXPS;


    final ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {

            mService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            mService = IInAppBillingService.Stub.asInterface(service);
            Log.e("Service In app:", "Service Connected");
        }
    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);

        setContentView(R.layout.activity_main);
        setTitle(HOME);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams
                .SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        hodClient = new HODClient(apikey, this);

        SharedPreferences sharedprefs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        adsDisabled = sharedprefs.getBoolean(ADSDISABLED, false);//retrieve the boolean value
        // for ads

        userIsPro = sharedprefs.getBoolean(USERISPRO, false);//retrieve the boolean for the pro
        // user

        //get the value from settings for the budget warnings
        budgetWarningEnabled = sharedprefs.getBoolean(BUDGETWARNINGS, false);
        //get the boolean value for payment circle
        isPaymentCircleSet = sharedprefs.getBoolean(ISPAYMENTCIRCLE, false);

        //get the int values from number pickers
        valueFromNumPicker1 = sharedprefs.getInt(VALUEFROMNUMPICKER1, valueFromNumPicker1);
        valueFromNumPicker2 = sharedprefs.getInt(VALUEFROMNUMPICKER2, valueFromNumPicker2);


        /*if (adsDisabled==false) {
            //this is for the ads Millenial Media
            MMSDK.initialize(this); // pass in current activity instance

            //Create the inline placement instance and set the listeners.
            try {
                // NOTE: The ad container argument passed to the createInstance call should be the
                // view container that the ad content will be injected into.
                FrameLayout adsLayout = new FrameLayout(this);
                adsLayout = (FrameLayout) findViewById(R.id.adView);
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
                final InlineAd.InlineAdMetadata inlineAdMetadata = new InlineAd.InlineAdMetadata
                ().setAdSize(InlineAd.AdSize.BANNER);
                inlineAd.request(inlineAdMetadata);
            }
        }*/

        layout = (RelativeLayout) findViewById(R.id.mainActivityView);

        imageView = (ImageView) this.findViewById(R.id.ImageView);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        Utils utils = new Utils(this);
        utils.setBackgroundAndAdjustLayout(layout, MainActivity.this);

        //add the tabs
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        tabHost.getTabWidget().setShowDividers(TabWidget.SHOW_DIVIDER_MIDDLE);

        TabHost.TabSpec tabSpec = tabHost.newTabSpec(ADD_EXPENSES);
        tabSpec.setContent(R.id.addExpenses);
        tabSpec.setIndicator(ADD_EXPENSES);

        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec(MANAGE_EXPENSES);
        tabSpec.setContent(R.id.ManageExpenses);
        tabSpec.setIndicator(MANAGE_EXPENSES);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec(CHART_EXPENSES);
        tabSpec.setContent(R.id.ChartExpenses);
        tabSpec.setIndicator(CHART_EXPENSES);
        tabHost.addTab(tabSpec);
        TextView x = tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
        x.setTextSize(10);
        TextView y = tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
        y.setTextSize(10);
        TextView z = tabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title);
        z.setTextSize(10);

        // compute your public key and store it in base64EncodedPublicKey
        mHelper = new IabHelper(MainActivity.this, base64EncodedPublicKey);
        mHelper.enableDebugLogging(true);

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    Log.d("In app Billing", "Problem setting up In-app Billing: " + result);
                    return;
                }
                // Hooray, IAB is fully set up!
                if (mHelper == null)
                    return;
                Log.d(TAG2, "Setup successful. Querying inventory.");
            }
        });
        // Binding to IInAppBillingService
        Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);

        //Query a purchase
        final ArrayList<String> skuList = new ArrayList<>();
        skuList.add(sku);
        querySkus = new Bundle();
        querySkus.putStringArrayList("ITEM_ID_LIST", skuList);

        // START OF FIRST TAB
        //Expenses Label
        TextView expensesLabel = new TextView(this);
        expensesLabel.setText(EXPENSE_AMOUNT);

        //Expenses TextField
        expensesField = new EditText(this);
        expensesField = (EditText) findViewById(R.id.expenseText);

        //Description Label
        TextView descriptionLabel = new TextView(this);
        descriptionLabel.setText(EXPENSE_DESCRIPTION);

        //Description spinner
        descriptionsItem = (Spinner) findViewById(R.id.descriptionCombo);

        itemsAddedByUser = new ArrayList<>();
        itemsAddedByUser.add(HOUSE_RENT);
        itemsAddedByUser.add(SHOPPING);
        itemsAddedByUser.add(SUPERMARKET);
        itemsAddedByUser.add(TRAVEL);
        itemsAddedByUser.add(MORTGAGE);
        itemsAddedByUser.add(COUNCIL_TAX);
        itemsAddedByUser.add(HOUSE_BILLS);
        itemsAddedByUser.add(ENTERTAINMENT);
        descriptionsItem = (Spinner) findViewById(R.id.descriptionCombo);
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                itemsAddedByUser);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        descriptionsItem.setAdapter(spinnerAdapter);

        descriptionsItem.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        spinnerAdapter,
                        R.layout.spinnernothingselected,
                        this));

        //Date Label
        TextView dateLabel = new TextView(this);
        dateLabel.setText(DATE);

        //Button for the add description
        addDescrButton = new Button(this);
        addDescrButton.setText(ADD);
        addDescrButton = (Button) findViewById(R.id.addDescButton);

        //Button for the camera
        photoButton = new ImageButton(this);
        photoButton = (ImageButton) findViewById(R.id.photoButton);

        //Button for the date
        dateButton = new Button(this);
        dateButton.setText(SELECT_DATE);

        //Date textfield
        dateText = new EditText(this);
        dateText = (EditText) findViewById(R.id.dateText);

        //Save to File Button
        saveToFile = new Button(this);
        saveToFile.setText(SAVE_TO_FILE);
        saveToFile.setBackgroundColor(Color.BLUE);
        saveToFile = (Button) findViewById(R.id.saveButton);

        //Income label, income field
        incomeLabel = new TextView(this);
        incomeLabel = (TextView) findViewById(R.id.incomeLabel);

        incomeField = new EditText(this);
        incomeField = (EditText) findViewById(R.id.incomeField);

        //get the stored income value from preferences and set it to the income field
        SharedPreferences prefers = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        String incomeStored = prefers.getString(INCOME, "");
        incomeField.setText(incomeStored);

        //cicle Button
        circleButton = new Button(this);
        circleButton = (Button) findViewById(R.id.circleButton);

        //Balance Label
        balanceLabel = new TextView(this);
        balanceLabel = (TextView) findViewById(R.id.balanceView);

        //Edit Button
        editButton = new Button(this);
        editButton = (Button) findViewById(R.id.editButton);

        // END OF FIRST TAB

        //Add expenses button
        addExpensesButton = new Button(this);
        addExpensesButton = (Button) findViewById(R.id.addExpensesButton);

        //add expenses by description Spinner
        addExpensesByDescription = (Spinner) findViewById(R.id.addExpensesByDescSpinner);
        //set the adapter for that spinner so that it will take the values as added on the first
        // spinner.
        addExpensesByDescription.setAdapter(spinnerAdapter);
        addExpensesByDescription.setAdapter(new NothingSelectedSpinnerAdapter(spinnerAdapter, R
                .layout.spinnernothingselected, this));

        //Information with example date range button
        infoDateRangeButton = new ImageButton(this);
        infoDateRangeButton = (ImageButton) findViewById(R.id.infoDateRange);

        //Button for the Budget Control
        budgetButton = new Button(this);
        budgetButton = (Button) findViewById(R.id.budgetButton);

        //Report button
        reportButton = new Button(this);
        reportButton = (Button) findViewById(R.id.reportButton);

        //Chart button
        chartButton = new Button(this);
        chartButton = (Button) findViewById(R.id.chartButton);

        //Bar Chart button
        barChartButton = new Button(this);
        barChartButton = (Button) findViewById(R.id.barChartbutton);

        //Annual Chart Button
        annualChartButton = new Button(this);
        annualChartButton = (Button) findViewById(R.id.annualChartButton);

        //Savings Chart Button
        savingsButton = new Button(this);
        savingsButton = (Button) findViewById(R.id.annualSavingsButton);

        // Back up Info button
        backUpInfoButton = new ImageButton(this);
        backUpInfoButton = (ImageButton) findViewById(R.id.backUpInfoButton);

        //Export Button
        exportButton = new Button(this);
        exportButton = (Button) findViewById(R.id.exportButton);

        //import button
        importButton = new Button(this);
        importButton = (Button) findViewById(R.id.importButton);

        showDialogOnButtonClick();
        try {
            readTheFile(0, 0, null);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        readDescriptionsFile();

        saveToFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                WriteToFile("","","");
            }
        });

        addDescrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showInputDialog();
            }
        });

        circleButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showNumberPickerDialogOnButtonClick();
            }
        });

        incomeField.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(final Editable s) {
                if (s.length() >= 2) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //call the methods to update balance and the graph
                            if (isPaymentCircleSet) {
                                processDateCircle();

                            } else {
                                processBalance();
                                showStackedBar();
                            }
                        }
                    }, 3000);//this is a delay of 3 seconds that starts when the user finished
                    // typing.
                }
            }
        });


        addExpensesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddTheExpenses();
            }
        });

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Request for permission if not granted
                String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest
                        .permission.CAMERA};
                if (!hasPermissions(getApplicationContext(), PERMISSIONS)) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission
                            .CAMERA}, 1);
                }

                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PICTURE);
            }
        });

        infoDateRangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,
                        AlertDialog.THEME_HOLO_LIGHT)
                        .setTitle(INFORMATION)
                        .setMessage("You can enter a date range and the application will add the " +
                                "expenses you made for that period." +
                                "An example of a date range is 05/01/2017-15/01/2017." + "\n" +
                                "Also if you select a description on the " +
                                "dropdown menu above the Add Expenses button, the application " +
                                "will sum all the expenses you made " +
                                "on that period only for the description you selected on the " +
                                "dropdown menu.");
                AlertDialog alert1;
                builder.setPositiveButton(CLOSE,
                        new OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                alert1 = builder.create();
                alert1.show();
            }
        });

        final IabHelper.QueryInventoryFinishedListener mQueryFinishedListener = new IabHelper
                .QueryInventoryFinishedListener() {
            @Override
            public void onQueryInventoryFinished(IabResult result, Inventory inv) {
                if (result.isFailure()) {
                    Log.d(TAG2, "Querying Inventory Failed: " + result);
                    return;
                }
                Log.d(TAG2, "Title: " + inv.getSkuDetails(sku).getTitle());
                Log.d(TAG2, "Description: " + inv.getSkuDetails(sku).getDescription());
                Log.d(TAG2, "Price = " + inv.getSkuDetails(sku).getPrice());

            }
        };

        budgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
                    public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
                        if (result.isFailure()) {
                            Toast.makeText(MainActivity.this, "Error occured during purchase " +
                                    result, Toast.LENGTH_SHORT).show();

                        } else if (purchase.getSku().equals(sku)) {
                            //consumeItem();
                        }
                    }
                };
                try {
                    JSONObject object = null;
                        try {
                            //object = new JSONObject(thisResponse);
                            String sku = object.getString("productId");
                            String price = object.getString("price");
                            if (sku.equals("premiumUpgrade")) {
                                String mPremiumUpgradePrice = price;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    //}Remote
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //check if the user has purchased the PRO version
                // If the request is successful, the returned Bundle has a response code of 0.

                //check if user id Pro retriecve boolean from SharedPreferences

                //the user has PRO version so start budget activity.
                Intent intentBudget = new Intent(view.getContext(), BudgetActivity.class);
                startActivity(intentBudget);

                // if the userIsPro is false the user has not purchased the PRO version
            }
        });

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(view.getContext(), ReportActivity.class);
                startActivity(intent1);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentEdit = new Intent(view.getContext(), EditActivity.class);
                startActivity(intentEdit);
            }
        });

        chartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentEdit = new Intent(view.getContext(), ChartActivity.class);
                intentEdit.putExtra(DESCRIPTIONS, allDescriptions);
                startActivity(intentEdit);
            }
        });

        barChartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentEdit = new Intent(view.getContext(), HorizontalBarChartActivity.class);
                System.out.println(BAR_CHART);
                startActivity(intentEdit);
            }
        });

        annualChartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentAnnualChart = new Intent(view.getContext(), AnnualChartActivity.class);
                startActivity(intentAnnualChart);
            }
        });

        savingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentSavingsChart = new Intent(view.getContext(), AnnualSavingsActivity
                        .class);
                startActivity(intentSavingsChart);
            }
        });

        backUpInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,
                        AlertDialog.THEME_HOLO_LIGHT)
                        .setTitle(INFORMATION)
                        .setMessage("You can save your expenses file to your device internal " +
                                "storage, edit it and then " +
                                "import it back in the application.\n 1. You can export it as a " +
                                "txt or as a pdf file. It " +
                                "is stored in your device Download folder. 2.You can edit the txt" +
                                " file but remember to \n" +
                                "keep the initial structure with the spaces. 3.When you finish " +
                                "editing save it, go back \n" +
                                "to the application and press Import. Import feature is only " +
                                "available on PRO version.");
                AlertDialog alert1;
                builder.setPositiveButton(CLOSE,
                        new OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                alert1 = builder.create();
                alert1.show();
            }
        });

        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(MainActivity.this, exportButton);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.export_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getTitle().equals(EXPORT_TO_DROPBOX)) {

                            ACCESS_TOKEN = retrieveAccessToken();

                            getUserAccount();
                            if (!tokenExists() || invalidToken) {
                                //No token Back to LoginActivity
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent
                                        .FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                            //Image URI received
                            File file = new File("/data/data/uk.co.irokottaki" +
                                    ".moneycontrol/files/expenses.txt");//get the directory of
                            // the file
                            // stored
                            if (!invalidToken) {
                                //Initialize UploadTask
                                new UploadTask(DropboxClient.getClient(ACCESS_TOKEN), file,
                                        getApplicationContext()).execute();
                            }

                        }
                        if (item.getTitle().equals(EXPORT_TXT)) {
                            exportExpensesFileToSdCard();
                            Toast.makeText(MainActivity.this, "You exported to Txt file", Toast
                                    .LENGTH_SHORT).show();
                        }
                        if (item.getTitle().equals(EXPORT_PDF)) {
                            exportFileToPDF();
                            Toast.makeText(MainActivity.this, "You exported to Pdf file", Toast
                                    .LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        });

        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
                    public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
                        if (result.isFailure()) {
                            Toast.makeText(MainActivity.this, "Error occured during purchase " +
                                    result, Toast.LENGTH_SHORT).show();
                            return;
                        } else if (purchase.getSku().equals(sku)) {
                            //consumeItem();
                        }
                    }
                };
                try {
                    JSONObject object = null;
                        try {
                            //object = new JSONObject(thisResponse);
                            String sku = object.getString("productId");
                            String price = object.getString("price");
                            if (sku.equals("premiumUpgrade")) {
                                String mPremiumUpgradePrice = price;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    //}Remote
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //check if the user has purchased the PRO version
                //check if the user is Pro retrieve boolean from SharedPreferences
                //the user has PRO version so make the import
                readFileFromSdCard();
                // if userIsPro is false the user has not purchased the PRO version

            }
        });

        // Get on the current tab when pressing the back button on the top of the activity
        final Intent intent = getIntent();

        if (intent.hasExtra(CHART)) {
            final int tab = intent.getExtras().getInt(CHART);
            switchToTab(tab); // switch to tab 3 from Chart Expenses
        } else if (intent.hasExtra(BARCHART)) {
            final int tab = intent.getExtras().getInt(BARCHART);
            switchToTab(tab); // switch to tab 3 from BarChart Expenses
        } else if (intent.hasExtra(ANNUALCHART)) {
            final int tab = intent.getExtras().getInt(ANNUALCHART);
            switchToTab(tab); // switch to tab 3 from Annual Chart Expenses
        } else if (intent.hasExtra(SAVINGSCHART)) {
            final int tab = intent.getExtras().getInt(SAVINGSCHART);
            switchToTab(tab);//switch to tab 3 savings chart
        } else if (intent.hasExtra(BUDGET)) {
            final int tab = intent.getExtras().getInt(BUDGET);
            switchToTab(tab); // switch to tab 2 from the Budget
        } else if (intent.hasExtra(REPORT)) {
            final int tab = intent.getExtras().getInt(REPORT);
            switchToTab(tab); // switch to tab 2 from the Report
        }

        //this is for the Notifications
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notificationIntent.addCategory("android.intent.category.DEFAULT");

        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + AlarmManager.INTERVAL_DAY * 2, AlarmManager.INTERVAL_DAY * 2, broadcast);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


    }// end of onCreate method



    public MainActivity() {

    }

    private void switchToTab(int tab) {
        tabHost.setCurrentTab(tab);
    }

    public void consumeItem() {

        mHelper.queryInventoryAsync(mReceivedInventoryListener);
    }

    final IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener = new IabHelper
            .QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {

            if (result.isFailure()) {
                Toast.makeText(MainActivity.this, "Error occured during purchase " + result,
                        Toast.LENGTH_SHORT).show();
            } else {
                mHelper.consumeAsync(inventory.getPurchase(sku), mConsumeFinishedListener);
            }
        }
    };
    final IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper
            .OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {

            if (result.isSuccess()) {
                Toast.makeText(MainActivity.this, "Congratulations you upgraded to PRO", Toast
                        .LENGTH_SHORT).show();
                SharedPreferences sp = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean(USERISPRO, true);//store the boolean userIsPro in
                // Preferences, true since the user just purchased PRO

                adsDisabled = true;//disable ads
                editor.putBoolean(ADSDISABLED, true);//store the boolean for the ads in
                // Preferences, true since the user is PRO
                editor.apply();
            } else {
                Toast.makeText(MainActivity.this, "Error occured during purchase " + result,
                        Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        Utils utils = new Utils(this);
        utils.setBackgroundAndAdjustLayout(layout, MainActivity.this);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        budgetWarningEnabled = prefs.getBoolean(BUDGETWARNINGS, false);

        //get the stored income value from preferences and set it to the income field
        SharedPreferences prefers = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        String incomeStored = prefers.getString(INCOME, "");
        incomeField.setText(incomeStored);

        SharedPreferences sharedprefs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        adsDisabled = sharedprefs.getBoolean(ADSDISABLED, false);//retrieve the boolean value
        // for ads

        userIsPro = sharedprefs.getBoolean(USERISPRO, false);//retrieve the boolean for the pro
        // user

        //get the boolean value for payment circle
        isPaymentCircleSet = prefs.getBoolean(ISPAYMENTCIRCLE, isPaymentCircleSet);
        //get the int values from number pickers
        valueFromNumPicker1 = prefs.getInt(VALUEFROMNUMPICKER1, valueFromNumPicker1);
        valueFromNumPicker2 = prefs.getInt(VALUEFROMNUMPICKER2, valueFromNumPicker2);

        //call the methods to update balance and the graph
        if (isPaymentCircleSet) {

        }

        // Binding to IInAppBillingService
        Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) {
            mHelper.dispose();
            mHelper = null;
        }
        if (mService != null) {
            unbindService(mServiceConn);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            Log.i(TAG, "onActivityResult handled by IABUtil.");
        }
        if (requestCode == 1001) {
            int responseCode = data.getIntExtra("RESPONSE_CODE", 0);
            String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
            String dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");

            if (resultCode == RESULT_OK) {
                try {
                    JSONObject jo = new JSONObject(purchaseData);
                    String sku = jo.getString("productId");
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,
                            AlertDialog.THEME_HOLO_LIGHT)
                            .setTitle(INFORMATION)
                            .setMessage("You have bought the " + sku + ". Excellent choice, " +
                                    "adventurer!");
                } catch (JSONException e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,
                            AlertDialog.THEME_HOLO_LIGHT)
                            .setTitle(INFORMATION)
                            .setMessage("Failed to parse purchase data.");
                    e.printStackTrace();
                }
            }
        }

        String imageurl = null;
        switch (requestCode) {
            case TAKE_PICTURE:
                if (requestCode == TAKE_PICTURE) {
                    if (resultCode == Activity.RESULT_OK) {

                        try {
                            Bitmap thumbnail = MediaStore.Images.Media.getBitmap
                                    (getContentResolver(), imageUri);
                            //imgView.setImageBitmap(thumbnail);
                            imageurl = getRealPathFromURI(imageUri);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        }
        String hodApp = HODApps.OCR_DOCUMENT;
        Map<String, Object> params = new HashMap<>();
        params.put("file", imageurl);
        params.put("mode", "document_photo");
        hodClient.PostRequest(params, hodApp, HODClient.REQ_MODE.ASYNC);
    }

    @SuppressWarnings("StringConcatenationInLoop")
    @Override
    public void requestCompletedWithContent(String response) {
        System.out.println("SUCCESS!" + response);

        //response = "text Greenpoint\nCoffeehouse\n195 Frann Street\n1 718-349-6635\nServer:
        // Keith 09/03/2009\nTable 9/1 1:53 PM\nGuests: 3 20021\nEggs Avocado 9.00\nGrill Chix
        // Sandnich 8.00\nGrill Chix Sandwich 9.00\nGreen SaIad\nIced Blood 2.00\n4 Items\nSub
        // Total 28.00\nTax 2.18\nTotal 30•20\nPECEIS;com,left:1,top:11,width:299,height:357";
        String textFound = "";
        try {
            JSONObject mainObject = new JSONObject(response);
            JSONArray textBlockArray = mainObject.getJSONArray("actions");
            if (textBlockArray.length() > 0) {
                for (int i = 0; i < textBlockArray.length(); i++) {
                    JSONObject actions = textBlockArray.getJSONObject(i);
                    JSONObject result = actions.getJSONObject("result");
                    JSONArray textArray = result.getJSONArray("text_block");
                    int count = textArray.length();
                    if (count > 0) {
                        for (int n = 0; n < count; n++) {
                            JSONObject texts = textArray.getJSONObject(n);
                            textFound += texts.getString("text");
                        }
                    }
                }
            }
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }
        setExpenseFieldsFromCameraExtractedText(textFound);
    }

    @Override
    public void requestCompletedWithJobID(String response) {
        try {
            JSONObject mainObject = new JSONObject(response);
            if (!mainObject.isNull("jobID")) {
                String jobID = mainObject.getString("jobID");
                hodClient.GetJobResult(jobID);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(response);

    }

    @Override
    public void onErrorOccurred(String errorMessage) {
        //System.out.println(errorMessage);
    }

    @SuppressWarnings("StringConcatenationInLoop")
    public void readTheFile(int lastDayOfPreviousMonth, int lastDayOfCurrentMonth, String
            currentMonth) throws ParseException {
        fileLine = "";
        arrayOfamountOct15 = new ArrayList<>();
        arrayOfamountNov15 = new ArrayList<>();
        arrayOfamountDec15 = new ArrayList<>();

        arrayOfamountJan16 = new ArrayList<>();
        arrayOfamountFeb16 = new ArrayList<>();
        arrayOfamountMar16 = new ArrayList<>();
        arrayOfamountApr16 = new ArrayList<>();
        arrayOfamountMay16 = new ArrayList<>();
        arrayOfamountJun16 = new ArrayList<>();
        arrayOfamountJul16 = new ArrayList<>();
        arrayOfamountAug16 = new ArrayList<>();
        arrayOfamountSep16 = new ArrayList<>();
        arrayOfamountOct16 = new ArrayList<>();
        arrayOfamountNov16 = new ArrayList<>();
        arrayOfamountDec16 = new ArrayList<>();

        arrayOfamountJan17 = new ArrayList<>();
        arrayOfamountFeb17 = new ArrayList<>();
        arrayOfamountMar17 = new ArrayList<>();
        arrayOfamountApr17 = new ArrayList<>();
        arrayOfamountMay17 = new ArrayList<>();
        arrayOfamountJun17 = new ArrayList<>();
        arrayOfamountJul17 = new ArrayList<>();
        arrayOfamountAug17 = new ArrayList<>();
        arrayOfamountSep17 = new ArrayList<>();
        arrayOfamountOct17 = new ArrayList<>();
        arrayOfamountNov17 = new ArrayList<>();
        arrayOfamountDec17 = new ArrayList<>();

        arrayOfamountJan = new ArrayList<>();
        arrayOfamountFeb = new ArrayList<>();
        arrayOfamountMar = new ArrayList<>();
        arrayOfamountApr = new ArrayList<>();
        arrayOfamountMay = new ArrayList<>();
        arrayOfamountJun = new ArrayList<>();
        arrayOfamountJul = new ArrayList<>();
        arrayOfamountAug = new ArrayList<>();
        arrayOfamountSep = new ArrayList<>();
        arrayOfamountOct = new ArrayList<>();
        arrayOfamountNov = new ArrayList<>();
        arrayOfamountDec = new ArrayList<>();
        amountsRelatedToDays = new HashMap<>();
        amountsRelatedToDaysAfterSalaryDay = new HashMap<>();
        String amount = "";
        String date = "";

        try {
            InputStream inputStream = new FileInputStream("/data/data/uk.co.irokottaki" +
                    ".moneycontrol/files/expenses.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            int lineIndex = 0;//this is to count the lines
            while ((line = br.readLine()) != null) {

                if (lineIndex == 0) {
                    fileLine = line + "\n" + "\n";// need to catch the space and write only one
                    // space after the header.
                }

                if (++lineIndex > 2 && !line.equals("") && !line.equals("r")) {
                    fileLine += line + "\n";
                    int index = line.lastIndexOf(" ");
                    amount = line.substring(0, line.indexOf(" "));
                    date = line.substring(index, line.length());
                    String extractDayFromDate = date.substring(0, date.indexOf("/"));
                    //convert String to int but first take the second character e.g. take 5 from 05
                    int extractDayFromDateInt = Integer.parseInt(extractDayFromDate.trim()
                            .replaceFirst("^0+(?!$)", ""));
                    String extractMonthFromDate = date.substring(date.indexOf("/") + 1, date
                            .lastIndexOf("/"));
                    if (extractMonthFromDate.startsWith("0")) {
                        extractMonthFromDate = extractMonthFromDate.replace("0", "");
                    }
                    String extractYearFromDate = date.substring(date.lastIndexOf("/") + 1, date
                            .length());

                    if (extractMonthFromDate.equals(TEN) && extractYearFromDate.equals(TWOTHOUSANDFIFTEEN)) {
                        arrayOfamountOct15.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(ELEVEN) && extractYearFromDate.equals(TWOTHOUSANDFIFTEEN)) {
                        arrayOfamountNov15.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(TWELVE) && extractYearFromDate.equals(TWOTHOUSANDFIFTEEN)) {
                        arrayOfamountDec15.add(Float.valueOf(amount));
                    }

                    if (extractMonthFromDate.equals(ONE) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {
                        arrayOfamountJan16.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(TWO) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {
                        arrayOfamountFeb16.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(THREE) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {
                        arrayOfamountMar16.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(FOUR) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {
                        arrayOfamountApr16.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(FIVE) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {
                        arrayOfamountMay16.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(SIX) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {
                        arrayOfamountJun16.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(SEVEN) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {
                        arrayOfamountJul16.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(EIGHT) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {
                        arrayOfamountAug16.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(NINE) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {
                        arrayOfamountSep16.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(TEN) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {
                        arrayOfamountOct16.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(ELEVEN) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {
                        arrayOfamountNov16.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(TWELVE) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {
                        arrayOfamountDec16.add(Float.valueOf(amount));
                    }

                    if (extractMonthFromDate.equals(ONE) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {
                        arrayOfamountJan17.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(TWO) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {
                        arrayOfamountFeb17.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(THREE) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {
                        arrayOfamountMar17.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(FOUR) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {
                        arrayOfamountApr17.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(FIVE) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {
                        arrayOfamountMay17.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(SIX) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {
                        arrayOfamountJun17.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(SEVEN) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {
                        arrayOfamountJul17.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(EIGHT) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {
                        arrayOfamountAug17.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(NINE) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {
                        arrayOfamountSep17.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(TEN) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {
                        arrayOfamountOct17.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(ELEVEN) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {
                        arrayOfamountNov17.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(TWELVE) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {
                        arrayOfamountDec17.add(Float.valueOf(amount));
                    }

                    if (extractMonthFromDate.equals(ONE) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {
                        arrayOfamountJan.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(TWO) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {
                        arrayOfamountFeb.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(THREE) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {
                        arrayOfamountMar.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(FOUR) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {
                        arrayOfamountApr.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(FIVE) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {
                        arrayOfamountMay.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(SIX) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {
                        arrayOfamountJun.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(SEVEN) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {
                        arrayOfamountJul.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(EIGHT) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {
                        arrayOfamountAug.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(NINE) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {
                        arrayOfamountSep.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(TEN) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {
                        arrayOfamountOct.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(ELEVEN) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {
                        arrayOfamountNov.add(Float.valueOf(amount));
                    }
                    if (extractMonthFromDate.equals(TWELVE) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {
                        arrayOfamountDec.add(Float.valueOf(amount));
                    }


                    if (lastDayOfCurrentMonth != 0 && lastDayOfPreviousMonth != 0 && currentMonth
                            != null) {
                        final Calendar calendar = Calendar.getInstance();
                        java.util.Date currentDate = new SimpleDateFormat("MMM", Locale.ENGLISH)
                                .parse(currentMonth);
                        calendar.setTime(currentDate);// here i convert the String month in an
                        // integer to be used on the switch-case
                        int monthInt = calendar.get(Calendar.MONTH) + 1;
                        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                        //the case of the current month
                        if (extractMonthFromDate.equals(String.valueOf(monthInt)) &&
                                currentYear == Integer.parseInt(extractYearFromDate) &&
                                lastDayOfCurrentMonth > extractDayFromDateInt) {

                            if (amountsRelatedToDays.containsKey(String.valueOf
                                    (extractDayFromDateInt))) {
                                amountsRelatedToDays.put(String.valueOf(extractDayFromDateInt),
                                        amountsRelatedToDays.get(String.valueOf
                                                (extractDayFromDateInt)) +
                                                Float.valueOf(amount));
                            } else {
                                amountsRelatedToDays.put(String.valueOf(extractDayFromDateInt),
                                        Float.valueOf(amount));
                            }
                        }
                        //the case of the previous month
                        if (extractMonthFromDate.equals(String.valueOf(monthInt - 1)) &&
                                currentYear == Integer.parseInt(extractYearFromDate) &&
                                lastDayOfPreviousMonth <= extractDayFromDateInt) {

                            if (amountsRelatedToDays.containsKey(String.valueOf
                                    (extractDayFromDateInt))) {
                                amountsRelatedToDays.put(String.valueOf(extractDayFromDateInt),
                                        amountsRelatedToDays.get(String.valueOf
                                                (extractDayFromDateInt)) +
                                                Float.valueOf(amount));
                            } else {
                                amountsRelatedToDays.put(String.valueOf(extractDayFromDateInt),
                                        Float.valueOf(amount));
                            }
                        }
                        if (extractMonthFromDate.equals(String.valueOf(monthInt)) &&
                                currentYear == Integer.parseInt(extractYearFromDate) &&
                                lastDayOfCurrentMonth <= extractDayFromDateInt) {

                            if (amountsRelatedToDaysAfterSalaryDay.containsKey(String.valueOf
                                    (extractDayFromDateInt))) {
                                amountsRelatedToDaysAfterSalaryDay.put(String.valueOf
                                        (extractDayFromDateInt),
                                        amountsRelatedToDaysAfterSalaryDay.get
                                        (String.valueOf(extractDayFromDateInt)) + Float.valueOf
                                                (amount));
                            } else {
                                amountsRelatedToDaysAfterSalaryDay.put(String.valueOf
                                        (extractDayFromDateInt), Float.valueOf(amount));
                            }
                        }
                    }
                }
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void showInputDialog() {
        //this method handles the popup window to add/remove descriptions in the spinner.
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this,
                AlertDialog.THEME_HOLO_LIGHT);
        alertDialogBuilder.setView(promptView);

        addedDescriptionField = new EditText(this);
        addedDescriptionField = promptView.findViewById(R.id.edittext);
        InputFilter[] FilterArray = new InputFilter[1];//this is to set a limit
        FilterArray[0] = new InputFilter.LengthFilter(14);//on characters entered by user
        addedDescriptionField.setFilters(FilterArray);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton(ADD, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        descriptionAddedByUser = addedDescriptionField.getText().toString();
                        if (itemsAddedByUser.contains(descriptionAddedByUser)) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity
                                    .this, AlertDialog.THEME_HOLO_LIGHT)
                                    .setTitle("Description exists!")
                                    .setMessage("This description exists already on your list");
                            AlertDialog alert1;
                            builder.setPositiveButton(OK,
                                    new OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            alert1 = builder.create();
                            alert1.show();
                        } else if (!addedDescriptionField.getText().toString().equals("") &&
                                !addedDescriptionField.getText().toString().equals(" ")) {
                            spinnerAdapter.add(descriptionAddedByUser);
                            spinnerAdapter.notifyDataSetChanged();
                            writeDescriptionsToFile();//write new description in the file
                        }
                    }
                })
                .setNeutralButton(REMOVE,
                        new OnClickListener() {
                            final String[] fixedDescriptions = new String[]{HOUSE_RENT,
                                    SHOPPING, SUPERMARKET, TRAVEL, MORTGAGE, COUNCIL_TAX,
                                    HOUSE_BILLS, ENTERTAINMENT};

                            public void onClick(DialogInterface dialog, int id) {
                                if (Arrays.asList(fixedDescriptions).contains
                                        (addedDescriptionField.getText().toString())) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder
                                            (MainActivity.this, AlertDialog.THEME_HOLO_LIGHT)
                                            .setTitle("Can not remove this Description!")
                                            .setMessage("This description is preinstalled in the " +
                                                    "application and for operating reasons is not" +
                                                    " recommended to delete it");
                                    AlertDialog alert1;
                                    builder.setPositiveButton(OK,
                                            new OnClickListener() {
                                                public void onClick(DialogInterface dialog, int
                                                        id) {
                                                    dialog.cancel();
                                                }
                                            });
                                    alert1 = builder.create();
                                    alert1.show();
                                } else if (itemsAddedByUser.contains(addedDescriptionField
                                        .getText().toString())) {
                                    spinnerAdapter.remove(addedDescriptionField.getText()
                                            .toString());
                                    deleteDescriptionFromFile();
                                }
                                //dialog.cancel();
                                else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder
                                            (MainActivity.this, AlertDialog.THEME_HOLO_LIGHT)
                                            .setTitle("Description does not exist!")
                                            .setMessage("This description does not exist on your " +
                                                    "list");
                                    AlertDialog alert1;
                                    builder.setPositiveButton("OK",
                                            new OnClickListener() {
                                                public void onClick(DialogInterface dialog, int
                                                        id) {
                                                    dialog.cancel();
                                                }
                                            });
                                    alert1 = builder.create();
                                    alert1.show();
                                }
                            }
                        })
                .setNegativeButton(CANCEL,
                        new OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private void writeDescriptionsToFile() {
        try {
            PrintWriter out = new PrintWriter(openFileOutput("descriptions.txt", MODE_APPEND));
            String descriptionItem = descriptionAddedByUser;
            out.append(descriptionItem);
            out.write("\r\n");
            out.close();
            Toast.makeText(this, descriptionItem + " added in your list", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(this, "Exception: " + e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void readDescriptionsFile() {
        try {
            InputStream inputStream = openFileInput("descriptions.txt");
            Scanner in = new Scanner(inputStream);

            while (in.hasNextLine()) {
                String descriptionItem = in.nextLine();
                itemsAddedByUser.add(descriptionItem);
            }
            inputStream.close();
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    private void deleteDescriptionFromFile() {
        String checkedDescription = addedDescriptionField.getText().toString();
        try {

            FileInputStream fstream = this.openFileInput("descriptions.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            StringBuilder fileContent = new StringBuilder();
            while ((strLine = br.readLine()) != null) {
                if (!strLine.equals(checkedDescription)) {
                    fileContent.append(strLine);
                    fileContent.append("\r\n");
                }
            }
            PrintWriter out = new PrintWriter(openFileOutput("descriptions.txt", MODE_PRIVATE));
            out.write(fileContent.toString());
            out.close();
            Toast.makeText(this, checkedDescription + " removed from your list", Toast
                    .LENGTH_LONG).show();
        } catch (Exception e) {            //Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void showDialogOnButtonClick() {
        dateButton = (Button) findViewById(R.id.dateButton);
        dateButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(DIALOG_ID);
                    }
                }
        );
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID)
            return new DatePickerDialog(this, dpickerListener, year_x, month_x, day_x);
        return null;
    }

    private final DatePickerDialog.OnDateSetListener dpickerListener
            = new DatePickerDialog.OnDateSetListener() {
        @Override

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_x = year;
            month_x = monthOfYear;
            day_x = dayOfMonth;

            Calendar calendar = Calendar.getInstance();
            calendar.set(year_x, month_x, day_x);

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            String strDate = format.format(calendar.getTime());

            Toast.makeText(MainActivity.this, strDate, Toast.LENGTH_LONG).show();
            TextView dateTextField = (TextView) findViewById(R.id.dateText);
            dateTextField.setText(strDate);
        }
    };

    protected void showNumberPickerDialogOnButtonClick() {

        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View npView = inflater.inflate(R.layout.number_picker_dialog_layout, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this,
                AlertDialog.THEME_HOLO_LIGHT);
        //number pickers
        numberPicker1 = npView.findViewById(R.id.numberPicker1);
        numberPicker1.setMaxValue(31);
        numberPicker1.setMinValue(1);
        numberPicker1.setWrapSelectorWheel(true);

        numberPicker2 = npView.findViewById(R.id.numberPicker2);
        numberPicker2.setMaxValue(31);
        numberPicker2.setMinValue(1);
        numberPicker2.setWrapSelectorWheel(true);
        //Labels before the number pickers
        numberPickerLabel1 = new TextView(this);
        numberPickerLabel1 = npView.findViewById(R.id.numberPickerLabel1);
        numberPickerLabel2 = new TextView(this);
        numberPickerLabel2 = npView.findViewById(R.id.numberPickerLabel2);

        if ((valueFromNumPicker1 != 0 && valueFromNumPicker2 != 0)) {
            numberPicker1.setValue(valueFromNumPicker1);
            numberPicker2.setValue(valueFromNumPicker2);
        }

        //if the user has set number picker values then set them to appear when user presses the
        // circle button

        alertDialogBuilder.setCancelable(false)
                .setTitle("Set your payment circle")
                .setMessage("Set the day your salary comes in." + "\n" +
                        "It is applied only in the Balance shown." + "\n" +
                        "Change back to calendar month with the Reset button.")
                .setView(npView)
                .setPositiveButton(SET,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                valueFromNumPicker1 = numberPicker1.getValue();
                                valueFromNumPicker2 = numberPicker2.getValue();
                                processDateCircle();

                                // store in preferences the boolean to set the circle and the
                                // values from the number pickers.
                                SharedPreferences prefers = getSharedPreferences(PREFERENCES,
                                        Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefers.edit();
                                editor.putBoolean(ISPAYMENTCIRCLE, true);
                                editor.putInt(VALUEFROMNUMPICKER1, valueFromNumPicker1);
                                editor.putInt(VALUEFROMNUMPICKER2, valueFromNumPicker2);
                                editor.apply();
                            }
                        })
                .setNegativeButton(CANCEL,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        })
                .setNeutralButton(RESET,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                isPaymentCircleSet = false;
                                valueFromNumPicker1 = 0;
                                valueFromNumPicker2 = 0;

                                // process again the balance
                                processBalance();
                                // redraw the graph with the balance
                                showStackedBar();

                                // store in preferences the boolean to set the circle and the
                                // values from the number pickers.
                                SharedPreferences prefers = getSharedPreferences(PREFERENCES,
                                        Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefers.edit();
                                editor.putInt(VALUEFROMNUMPICKER1, valueFromNumPicker1);
                                editor.putInt(VALUEFROMNUMPICKER2, valueFromNumPicker2);
                                editor.apply();
                                dialog.cancel();
                            }
                        });
        AlertDialog alertBox = alertDialogBuilder.create();
        alertDialogBuilder.show();

    }

    private void processDateCircle() {

        System.out.println("First Date: " + valueFromNumPicker1 + " Second Date: " +
                valueFromNumPicker2);

        final Calendar calendar = Calendar.getInstance();//this gets the current month
        String currentMonth = String.format(Locale.UK, "%tB", calendar);

        try {
            readTheFile(valueFromNumPicker1, valueFromNumPicker2, currentMonth);
            //the user has clicked SET on the dialog
            isPaymentCircleSet = true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // process again the balance
        processBalance();
        // redraw the graph with the balance
        showStackedBar();
    }

    @SuppressLint("SetTextI18n")
    private void processBalance() {

        if (incomeField != null && incomeField.getText().toString().matches("\\d+")) {
            String incomeValue = incomeField.getText().toString();
            incomeForJan = Float.parseFloat(incomeValue);//i initialize the incomes for every
            // month to avoid null values
            incomeForFeb = Float.parseFloat(incomeValue);// returned to AnnualSavingsActivity. So
            // if the user will not
            incomeForMar = Float.parseFloat(incomeValue);//change the income then i use the
            // current that has been entered
            incomeForApr = Float.parseFloat(incomeValue);// if on a month the user changes the
            // income then this will be
            incomeForMay = Float.parseFloat(incomeValue);// changed above on the if statements.
            incomeForJun = Float.parseFloat(incomeValue);
            incomeForJul = Float.parseFloat(incomeValue);
            incomeForAug = Float.parseFloat(incomeValue);
            incomeForSep = Float.parseFloat(incomeValue);
            incomeForOct = Float.parseFloat(incomeValue);
            incomeForNov = Float.parseFloat(incomeValue);
            incomeForDec = Float.parseFloat(incomeValue);
            Calendar c = Calendar.getInstance();
            String currentMonth = String.format(Locale.UK, "%tB", c);//get the current month
            int year = c.getInstance().get(Calendar.YEAR);

            if (currentMonth.equals(JANUARY) && year == 2018) {
                incomeForJan = Float.parseFloat(incomeValue);
                SumExpensesForBalance(arrayOfamountJan);
            }
            if (currentMonth.equals(FEBRUARY) && year == 2018) {
                incomeForFeb = Float.parseFloat(incomeValue);
                SumExpensesForBalance(arrayOfamountFeb);
            }
            if (currentMonth.equals(MARCH) && year == 2018) {
                incomeForMar = Float.parseFloat(incomeValue);
                SumExpensesForBalance(arrayOfamountMar);
            }
            if (currentMonth.equals(APRIL) && year == 2018) {
                incomeForApr = Float.parseFloat(incomeValue);
                SumExpensesForBalance(arrayOfamountApr);
            }
            if (currentMonth.equals(MAY) && year == 2018) {
                incomeForMay = Float.parseFloat(incomeValue);
                SumExpensesForBalance(arrayOfamountMay);
            }
            if (currentMonth.equals(JUNE) && year == 2018) {
                incomeForJun = Float.parseFloat(incomeValue);
                SumExpensesForBalance(arrayOfamountJun);
            }
            if (currentMonth.equals(JULY) && year == 2018) {
                incomeForJul = Float.parseFloat(incomeValue);
                SumExpensesForBalance(arrayOfamountJul);
            }
            if (currentMonth.equals(AUGUST) && year == 2018) {
                incomeForAug = Float.parseFloat(incomeValue);
                SumExpensesForBalance(arrayOfamountAug);
            }
            if (currentMonth.equals(SEPTEMBER) && year == 2018) {
                incomeForSep = Float.parseFloat(incomeValue);
                SumExpensesForBalance(arrayOfamountSep);
            }
            if (currentMonth.equals(OCTOBER) && year == 2018) {
                incomeForOct = Float.parseFloat(incomeValue);
                SumExpensesForBalance(arrayOfamountOct);
            }
            if (currentMonth.equals(NOVEMBER) && year == 2018) {
                incomeForNov = Float.parseFloat(incomeValue);
                SumExpensesForBalance(arrayOfamountNov);
            }
            if (currentMonth.equals(DECEMBER) && year == 2018) {
                incomeForDec = Float.parseFloat(incomeValue);
                SumExpensesForBalance(arrayOfamountDec);
            }

            if (currentMonth.equals(JANUARY) && year == 2017) {
                incomeForJan = Float.parseFloat(incomeValue);
                SumExpensesForBalance(arrayOfamountJan17);
            }
            if (currentMonth.equals(FEBRUARY) && year == 2017) {
                incomeForFeb = Float.parseFloat(incomeValue);
                SumExpensesForBalance(arrayOfamountFeb17);
            }
            if (currentMonth.equals(MARCH) && year == 2017) {
                incomeForMar = Float.parseFloat(incomeValue);
                SumExpensesForBalance(arrayOfamountMar17);
            }
            if (currentMonth.equals(APRIL) && year == 2017) {
                incomeForApr = Float.parseFloat(incomeValue);
                SumExpensesForBalance(arrayOfamountApr17);
            }
            if (currentMonth.equals(MAY) && year == 2017) {
                incomeForMay = Float.parseFloat(incomeValue);
                SumExpensesForBalance(arrayOfamountMay17);
            }
            if (currentMonth.equals(JUNE) && year == 2017) {
                incomeForJun = Float.parseFloat(incomeValue);
                SumExpensesForBalance(arrayOfamountJun17);
            }
            if (currentMonth.equals(JULY) && year == 2017) {
                incomeForJul = Float.parseFloat(incomeValue);
                SumExpensesForBalance(arrayOfamountJul17);
            }
            if (currentMonth.equals(AUGUST) && year == 2017) {
                incomeForAug = Float.parseFloat(incomeValue);
                SumExpensesForBalance(arrayOfamountAug17);
            }
            if (currentMonth.equals(SEPTEMBER) && year == 2017) {
                incomeForSep = Float.parseFloat(incomeValue);
                SumExpensesForBalance(arrayOfamountSep17);
            }
            if (currentMonth.equals(OCTOBER) && year == 2017) {
                incomeForOct = Float.parseFloat(incomeValue);
                SumExpensesForBalance(arrayOfamountOct17);
            }
            if (currentMonth.equals(NOVEMBER) && year == 2017) {
                incomeForNov = Float.parseFloat(incomeValue);
                SumExpensesForBalance(arrayOfamountNov17);
            }
            if (currentMonth.equals(DECEMBER) && year == 2017) {
                incomeForDec = Float.parseFloat(incomeValue);
                SumExpensesForBalance(arrayOfamountDec17);
            }
            if (currentMonth.equals(DECEMBER) && year == 2016) {
                incomeForDec = Float.parseFloat(incomeValue);
                SumExpensesForBalance(arrayOfamountDec16);
            }
            // this is to avoid invalid double thrown on initial state where income is not added
            // yet by the user
            incomeDouble = Double.valueOf(incomeValue);
            balance = incomeDouble - monthSum;
            DecimalFormat df = new DecimalFormat("#.0");
            balanceLabel.setText("Balance: " + df.format(balance));

            //store it in preferences
            SharedPreferences sp = getSharedPreferences(PREFERENCES, MainActivity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(INCOME, incomeValue);
            editor.apply();
        }
    }

    private void showStackedBar() {
        //this is for the stacked bar appeared after the balance
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager
                .LayoutParams.FLAG_FULLSCREEN);

        mChart = new HorizontalBarChart(this);
        stackedBarLayout = (FrameLayout) findViewById(R.id.stackedBar);
        stackedBarLayout.addView(mChart);
        mChart.setDrawGridBackground(false);

        mChart.setDescription("");

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(false);

        mChart.getAxisLeft().setEnabled(false);//hide the values up from the bar
        mChart.getAxisRight().setEnabled(false);//hide the values above the bar
        mChart.getLegend().setEnabled(false);//hide the legend

        mChart.getAxisRight().setDrawGridLines(false);
        //this is to set the chart's position to always (positive and negative balance)start from
        // the same point
        mChart.getAxisLeft().setAxisMinValue(0f);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setDrawAxisLine(false);

        // IMPORTANT: When using negative values in stacked bars, always make sure the negative
        // values are in the array first
        ArrayList<BarEntry> yValues = new ArrayList<>();
        yValues.add(new BarEntry(new float[]{(float) balance, (float) (incomeDouble - balance)},
                0));

        BarDataSet set = new BarDataSet(yValues, "");
        set.setBarSpacePercent(40f);//the height of the bar
        set.setDrawValues(false);//hide the y values appeared inside the bar

        if (balance < 0) {

            set.setColors(new int[]{Color.rgb(205, 0, 0)});//, Color.rgb(91, 57, 198)
        } else {

            set.setColors(new int[]{Color.rgb(0, 131, 0), Color.rgb(205, 0, 0)});//the colors
            // green and red
        }

        String[] xVals = new String[]{""};

        BarData data = new BarData(xVals, set);
        mChart.setData(data);
        mChart.invalidate();
    }

    private void SumExpensesForBalance(ArrayList<Float> arrayOfamount) {
        Float monthSumFloat = 0f;
        //the user has set a payment circle
        if (isPaymentCircleSet) {
            Calendar cal = Calendar.getInstance();
            int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
            /*Show the balance in the graph taking into account the expenses made after the
            valueFromNumPicker2 day and the
             * next month*/
            if (valueFromNumPicker2 < dayOfMonth) {

                for (Map.Entry<String, Float> entry : amountsRelatedToDaysAfterSalaryDay.entrySet
                        ()) {
                    if (Integer.parseInt(entry.getKey()) >= valueFromNumPicker2) {
                        monthSumFloat += entry.getValue();
                    }
                }
            } else {

                for (Float amountsFloat : amountsRelatedToDays.values()) {
                    monthSumFloat += amountsFloat;
                }
            }
        }
        // the user has not set a payment circle or has reset it.
        else if (!isPaymentCircleSet) {
            for (int i = 0; i < arrayOfamount.size(); i++) {
                monthSumFloat += arrayOfamount.get(i);
            }
        }
        monthSum = monthSumFloat;
    }

    public void WriteToFile(String amountText, String descriptionText, String dateText) {
        EditText amount = (EditText) findViewById(R.id.expenseText);
        EditText date = (EditText) findViewById(R.id.dateText);
        String amountField = amount.getText().toString();
        String dateField = date.getText().toString();

        if (amountField.equals("") || amountField.equals(" ") || descriptionsItem.getSelectedItem
                () == null || dateField.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, AlertDialog
                    .THEME_HOLO_LIGHT)
                    .setTitle(EMPTY_FIELD)
                    .setMessage("Some of the fields are empty, fill them all and try again");
            AlertDialog alert1;
            builder.setPositiveButton(OK,
                    new OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            alert1 = builder.create();
            alert1.show();
        } else {
            try {
                PrintWriter out = new PrintWriter(openFileOutput("expenses.txt", MODE_APPEND));
                Spinner dateSpinner = (Spinner) findViewById(R.id.descriptionCombo);
                date = (EditText) findViewById(R.id.dateText);
                amountText = amount.getText().toString();
                descriptionText = dateSpinner.getSelectedItem().toString();
                dateText = date.getText().toString();
                int length = 22;
                String formatStr = "%-8s%-15s%-10s";
                if (!fileLine.contains(AMOUNT)) {
                    fileLine = "Amount Description Date";
                    out.printf("%-" + length + "s %s%n", "Amount  Description", "Date");//write
                    // the header
                    out.write("\r\n");//write two new lines
                    out.write("\r\n");
                }

                out.append(String.format(formatStr, amountText, descriptionText, dateText));
                //write the expense
                out.write("\r\n");//write a new line
                out.close();
                Toast.makeText(this, "The expense is saved in the file.", Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                Toast.makeText(this, "Exception: " + e.toString(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            checkBudgetWarning();//since the expense is written in the file call the budget
            // warning method
        }
    }

    public void checkBudgetWarning() {
        //call to retrieve amounts from months
        try {
            readTheFile(0, 0, null);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int sum = 0;
        //get the budget value as stored in Preferences in Budget Activity
        SharedPreferences sp = getSharedPreferences(PREFERENCES, BudgetActivity.MODE_PRIVATE);
        int progressValue = sp.getInt(BUDGETVALUE, 0);

        // i get the current month
        final Calendar calendar = Calendar.getInstance();//this gets the current month
        String currentMonth = String.format(Locale.UK, "%tB", calendar);

        //1. Check if the user has enabled this feature from settings.2.if yes find the expenses
        // that have been added so far
        if (budgetWarningEnabled && progressValue > 0) {
            if (currentMonth.equals(JANUARY)) {
                for (int i = 0; i < arrayOfamountJan.size(); i++) {
                    sum += arrayOfamountJan.get(i);
                }
                double percentWarning = (double) sum / (double) progressValue;
                // show the dialog window
                getDialogForBudgetWarning(percentWarning, MainActivity.this);
            }
            if (currentMonth.equals(FEBRUARY)) {
                for (int i = 0; i < arrayOfamountFeb.size(); i++) {
                    sum += arrayOfamountFeb.get(i);
                }
                double percentWarning = (double) sum / (double) progressValue;
                // show the dialog window
                getDialogForBudgetWarning(percentWarning, MainActivity.this);
            }
            if (currentMonth.equals(MARCH)) {
                for (int i = 0; i < arrayOfamountMar.size(); i++) {
                    sum += arrayOfamountMar.get(i);
                }
                double percentWarning = (double) sum / (double) progressValue;
                // show the dialog window
                getDialogForBudgetWarning(percentWarning, MainActivity.this);
            }
            if (currentMonth.equals(APRIL)) {
                for (int i = 0; i < arrayOfamountApr.size(); i++) {
                    sum += arrayOfamountApr.get(i);
                }
                double percentWarning = (double) sum / (double) progressValue;
                // show the dialog window
                getDialogForBudgetWarning(percentWarning, MainActivity.this);
            }
            if (currentMonth.equals(MAY)) {
                for (int i = 0; i < arrayOfamountMay.size(); i++) {
                    sum += arrayOfamountMay.get(i);
                }
                double percentWarning = (double) sum / (double) progressValue;
                // show the dialog window
                getDialogForBudgetWarning(percentWarning, MainActivity.this);
            }
            if (currentMonth.equals(JUNE)) {
                for (int i = 0; i < arrayOfamountJun.size(); i++) {
                    sum += arrayOfamountJun.get(i);
                }
                double percentWarning = (double) sum / (double) progressValue;
                // show the dialog window
                getDialogForBudgetWarning(percentWarning, MainActivity.this);
            }
            if (currentMonth.equals(JULY)) {
                for (int i = 0; i < arrayOfamountJul.size(); i++) {
                    sum += arrayOfamountJul.get(i);
                }
                double percentWarning = (double) sum / (double) progressValue;
                // show the dialog window
                getDialogForBudgetWarning(percentWarning, MainActivity.this);
            }
            if (currentMonth.equals(AUGUST)) {
                for (int i = 0; i < arrayOfamountAug.size(); i++) {
                    sum += arrayOfamountAug.get(i);
                }
                double percentWarning = (double) sum / (double) progressValue;
                // show the dialog window
                getDialogForBudgetWarning(percentWarning, MainActivity.this);
            }
            if (currentMonth.equals(SEPTEMBER)) {
                for (int i = 0; i < arrayOfamountSep.size(); i++) {
                    sum += arrayOfamountSep.get(i);
                }
                double percentWarning = (double) sum / (double) progressValue;
                // show the dialog window
                getDialogForBudgetWarning(percentWarning, MainActivity.this);
            }
            if (currentMonth.equals(OCTOBER)) {
                for (int i = 0; i < arrayOfamountOct.size(); i++) {
                    sum += arrayOfamountOct.get(i);
                }
                double percentWarning = (double) sum / (double) progressValue;
                // show the dialog window
                getDialogForBudgetWarning(percentWarning, MainActivity.this);
            }
            if (currentMonth.equals(NOVEMBER)) {
                for (int i = 0; i < arrayOfamountNov.size(); i++) {
                    sum += arrayOfamountNov.get(i);
                }
                double percentWarning = (double) sum / (double) progressValue;
                // show the dialog window
                getDialogForBudgetWarning(percentWarning, MainActivity.this);
            }
            if (currentMonth.equals(DECEMBER)) {
                for (int i = 0; i < arrayOfamountDec.size(); i++) {
                    sum += arrayOfamountDec.get(i);
                }
                double percentWarning = (double) sum / (double) progressValue;
                // show the dialog window
                getDialogForBudgetWarning(percentWarning, MainActivity.this);
            }
        }
    }

    public static void getDialogForBudgetWarning(Double percentWarning, Context mContext) {
        if (percentWarning > 0.75 && percentWarning < 0.85) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext, AlertDialog
                    .THEME_HOLO_LIGHT)
                    .setTitle("Budget Warning!")
                    .setMessage("You have reached the 80% of your budget as set in Budget Control" +
                            ". \n\n" +
                            "*** Remember you can always turn off these notifications from the " +
                            "Settings.");
            AlertDialog alert1;
            builder.setPositiveButton(CLOSE,
                    new OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            alert1 = builder.create();
            alert1.show();
        } else if (percentWarning > 0.85 && percentWarning < 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext, AlertDialog
                    .THEME_HOLO_LIGHT)
                    .setTitle("Budget Warning!")
                    .setMessage("You have reached the 90% of your budget as set in Budget Control" +
                            ". \n\n" +
                            "*** Remember you can always turn off these notifications from the " +
                            "Settings.");
            AlertDialog alert1;
            builder.setPositiveButton(CLOSE,
                    new OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            alert1 = builder.create();
            alert1.show();
        } else if (percentWarning >= 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext, AlertDialog
                    .THEME_HOLO_LIGHT)
                    .setTitle("Budget Warning!")
                    .setMessage("You have exceeded the 100% of your budget as set in Budget " +
                            "Control. Too many expenses this month? You can increase the budget " +
                            "warning " +
                            "amount.\n\n" +
                            "*** Remember you can always turn off these notifications from the " +
                            "Settings.");
            AlertDialog alert1;
            builder.setPositiveButton(CLOSE,
                    new OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            alert1 = builder.create();
            alert1.show();
        }
    }

    public void AddTheExpenses() {

        getDaysBetweenDates();
        if (dates.isEmpty()) {
            return;
        }
        EditText datesField = (EditText) findViewById(R.id.dateFromTo);
        String datesFromTo = datesField.getText().toString();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy", Locale.US);
        Date dateIntheFile = null;
        Set<Date> datesMatchedUserInput = new TreeSet<>();
        String expenseAmount = null;
        expenses.clear();
        uniqueDescriptions = new LinkedHashSet<>();
        uniqueAmounts = new ArrayList<>();

        String desc = "";
        String date = "";
        try {
            InputStream inputStream = openFileInput("expenses.txt");
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                Scanner in = new Scanner(inputStreamReader);//read the file
                int lineIndex = 0;//this is to count the lines
                while (in.hasNextLine()) {
                    String line = in.nextLine();
                    if (++lineIndex > 2 && !line.equals(""))//i need to read after the first line
                    {

                        expenseAmount = line.substring(0, line.indexOf(" "));//take the amount
                        // from the file
                        int index = line.lastIndexOf(" ");
                        desc = line.substring(line.indexOf(" "), index).trim();//take the
                        // description
                        date = line.substring(index, line.length());//take the date
                        try {
                            dateIntheFile = format.parse(date);//convert the date into Date

                        } catch (ParseException e) {

                            e.printStackTrace();
                        }
                        double firstDateAmountNumber = 0.0;
                        if (dates.contains(dateIntheFile)) {
                            datesMatchedUserInput.add(dateIntheFile);//add the Date in a list
                            // that will maintain them as the while loop checks all the dates.
                            String firstDateAmount = expenseAmount;//if the dates taken from the
                            // input are the same
                            firstDateAmountNumber = Double.parseDouble(firstDateAmount);//with
                            // those in the file
                            expenses.add(firstDateAmountNumber);//then look each line and find
                            // the amount given. Add the amount in the list of expenses.

                            //process the addition of expenses for a description
                            if (uniqueDescriptions.contains(desc)) {
                                int i = 0;
                                for (Iterator<String> s = uniqueDescriptions.iterator(); s
                                        .hasNext(); i++) {
                                    String descFound = s.next();
                                    if (desc.equals(descFound)) {
                                        amountWithDuplicate = uniqueAmounts.get(i) + Float
                                                .valueOf(expenseAmount);//add up the amounts if
                                        // there are
                                        // duplicates
                                        uniqueAmounts.set(i, amountWithDuplicate);
                                    }
                                }
                            } else {
                                uniqueDescriptions.add(desc);
                                uniqueAmounts.add(Float.valueOf(expenseAmount));
                            }
                        }
                    }
                }// end of while
                Collections.sort(dates);
                if (datesMatchedUserInput.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,
                            AlertDialog.THEME_HOLO_LIGHT)
                            .setTitle("No matches found")
                            .setMessage("The are no expenses in the dates you entered! Please try" +
                                    " with different dates.");
                    builder.setPositiveButton(OK,
                            new OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    alert1.show();
                }
                Date nextValue = null;
                for (Date aDatesMatchedUserInput : datesMatchedUserInput) {
                    nextValue = aDatesMatchedUserInput;
                }
                if (dates.contains(nextValue) && addExpensesByDescription.getSelectedItemPosition
                        () == 0) {
                    sumTheExpenses();//this sums up all the expenses for the date range. it is
                    // not inside the other if
                   // because it would pop up all the
                    // times the while loop is counting
                } else if (dates.contains(nextValue) && addExpensesByDescription
                        .getSelectedItemPosition() > 0) {
                    String descSelected = addExpensesByDescription.getSelectedItem().toString();
                    sumTheExpensesByDescription(descSelected);
                }
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
    }

    public void sumTheExpensesByDescription(String desc) {
        EditText datesField = (EditText) findViewById(R.id.dateFromTo);
        String datesFromTo = datesField.getText().toString();
        Float sumFloat = 0f;
        if (desc.equals(addExpensesByDescription.getSelectedItem().toString())) {
            Iterator<String> itr = uniqueDescriptions.iterator();
            int i = 0;
            while (itr.hasNext()) {
                String descIterated = itr.next();
                if (desc.equals(descIterated)) {
                    sumFloat += uniqueAmounts.get(i);
                }
                i++;
            }
        }

        double sum = sumFloat;
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, AlertDialog
                .THEME_HOLO_LIGHT)
                .setTitle(desc + " expenses for: " + datesFromTo)
                .setMessage("You have spent: " + String.format(Locale.ENGLISH, "%.2f", sum));
        AlertDialog alert1;
        builder.setPositiveButton(OK,
                new OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        alert1 = builder.create();
        alert1.show();

    }

    public void sumTheExpenses() {

        EditText datesField = (EditText) findViewById(R.id.dateFromTo);
        String datesFromTo = datesField.getText().toString();
        double sum = 0;
        for (int i = 0; i < expenses.size(); i++) {
            sum += expenses.get(i);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, AlertDialog
                .THEME_HOLO_LIGHT)
                .setTitle("Expenses for: " + datesFromTo)
                .setMessage("You have spent: " + String.format(Locale.ENGLISH, "%.2f", sum));
        AlertDialog alert1;
        builder.setPositiveButton(OK,
                new OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        alert1 = builder.create();
        alert1.show();
    }

    public void getDaysBetweenDates() {
        EditText datesField = (EditText) findViewById(R.id.dateFromTo);
        String datesFromTo = datesField.getText().toString();
        dates = new ArrayList<>();//the arraylist where i store the dates

        if (datesFromTo.equals("") || datesFromTo.equals(null)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, AlertDialog
                    .THEME_HOLO_LIGHT)
                    .setTitle("Empty Field!")
                    .setMessage("Add expenses field is empty.Please supply a date range");
            builder.setPositiveButton(OK,
                    new OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert1 = builder.create();
            alert1.show();
        } else if (!datesFromTo.matches("^[0-9].*") || !datesFromTo.contains("-")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, AlertDialog
                    .THEME_HOLO_LIGHT)
                    .setTitle("Wrong Date!")
                    .setMessage("You entered words for a date or a single date. " +
                            "Please supply a correct date range in the format dd/MM/yyyy");
            builder.setPositiveButton(OK,
                    new OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert1 = builder.create();
            alert1.show();
        } else {
            String[] token = datesFromTo.split("-");//store dates splitted by -
            String firstdate = token[0];//take the first date entered by the user
            String lastdate = token[1];//take the second date entered by the user

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
            Date startdate;
            startdate = null;

            if (!firstdate.matches("^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$")
                    || !lastdate.matches("^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,
                        AlertDialog.THEME_HOLO_LIGHT)
                        .setTitle("Wrong Format!")
                        .setMessage("Dates should be entered in the dd/MM/yyyy format.Please " +
                                "supply a correct date range");
                builder.setPositiveButton(OK,
                        new OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert1 = builder.create();
                alert1.show();
            } else {
                try {
                    startdate = format.parse(firstdate);
                } catch (ParseException e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,
                            AlertDialog.THEME_HOLO_LIGHT)
                            .setTitle("Wrong Format!")
                            .setMessage("Please supply a correct date range.");
                    builder.setPositiveButton(OK,
                            new OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    alert1.show();
                    e.printStackTrace();
                }
                Date enddate;
                enddate = null;
                try {
                    enddate = format.parse(lastdate);//convert the second date into Date

                } catch (ParseException e) {

                    e.printStackTrace();
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startdate);

                while (calendar.getTime().getTime() <= enddate.getTime()) {
                    Date result = calendar.getTime();//take the date
                    dates.add(result);//add it to dates arraylist
                    calendar.add(Calendar.DATE, 1);
                }

            }// end of second else
        }
        // 15/09/2015-17/09/2015 dates will store 15/09/2015 16/09/2015 17/09/2015
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission
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

    private void exportExpensesFileToSdCard() {
        verifyStoragePermissions(MainActivity.this);
        try {

            File root2 = new File("/data/data/uk.co.irokottaki.moneycontrol/files/expenses.txt");
            //get the directory of the file stored

            File dirAndFolder = new File(Environment.getExternalStoragePublicDirectory
                    (Environment.DIRECTORY_DOWNLOADS), "");

            if (!dirAndFolder.exists()) {
                dirAndFolder.mkdir();
            }
            File file = new File(dirAndFolder, "expenses" + ".txt");
            FileOutputStream f = new FileOutputStream(file);//pass the directory of the SD card
            // with the name file in a FileOutputStream

            InputStream inputStream = new FileInputStream(root2);

            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = inputStream.read(buffer)) > 0) {
                f.write(buffer, 0, len1);
            }
            f.close();
        } catch (Exception e) {
            Log.e("Exception ", e.getMessage().toString());
        }
    }

    public void readFileFromSdCard() {
        verifyStoragePermissions(MainActivity.this);
        String amount = "";
        String desc = "";
        String date = "";
        String formatStr = "%-7s %-15s %-10s";
        StringBuilder myData = new StringBuilder();
        File myExternalFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath
                () + "/Download/", "expenses.txt");
        try {
            FileInputStream fis = new FileInputStream(myExternalFile);
            DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            int lineIndex = 0;
            while ((strLine = br.readLine()) != null) {
                lineIndex++;
                if (strLine.replaceAll("\\s+", " ").contains("Amount Description Date")) {
                    myData.append(String.format(formatStr, "Amount", "Description", "Date"))
                            .append("\n").append("\n");

                }//if it reads the first line i want to add two empty lines so we have the header
                // enter, enter, and the third line is the first amount
                //if the line is 2 so we are in the first expense
                else if (lineIndex > 2) {
                    //this is the case where the file is one very big line. Current line expense
                    // length is 33.
                    if (strLine.length() > 34 && !strLine.contains(AMOUNT)) {
                        String[] fileLineArray = strLine.split("(?<=\\G.{33})");

                        for (String aFileLineArray : fileLineArray) {
                            String fileLineArrayOneSpace = aFileLineArray.replaceAll("\\s+", " ");
                            amount = fileLineArrayOneSpace.substring(0, aFileLineArray.indexOf(" " +
                                    ""));
                            int index = fileLineArrayOneSpace.lastIndexOf(" ");
                            desc = fileLineArrayOneSpace.substring(aFileLineArray.indexOf(" "),
                                    index).trim();
                            date = fileLineArrayOneSpace.substring(fileLineArrayOneSpace
                                    .lastIndexOf(" "), fileLineArrayOneSpace.length()).trim();
                            myData.append(String.format(formatStr, amount, desc, date + "\n"));
                        }
                    } //this is the case where the file has lines of length<34, probably 33. do
                    // the same as above
                    else if (strLine.length() <= 34 && !strLine.contains(AMOUNT)) {
                        if (strLine.replaceAll("\\s+", " ").contains(TWOTHOUSANDFIFTEEN) || (strLine
                                .replaceAll("\\s+", " ").contains(TWOTHOUSANDSIXTEEN))) {
                            amount = strLine.replaceAll("\\s+", " ").substring(0, strLine.indexOf
                                    (" "));
                            int index = strLine.replaceAll("\\s+", " ").lastIndexOf(" ");
                            desc = strLine.replaceAll("\\s+", " ").substring(strLine.indexOf(" ")
                                    , index).trim();
                            date = strLine.substring(strLine.lastIndexOf(" "), strLine.length())
                                    .trim();

                            myData.append(String.format(formatStr, amount, desc, date + "\n"));
                        }//all the other cases like if line=""
                        else {
                            myData.append(strLine);
                        }
                    }
                }
            }// end of While

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //if the file in the Download folder is empty or does not exist
        if (myData.toString().equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, AlertDialog
                    .THEME_HOLO_LIGHT)
                    .setTitle("No expenses file!")
                    .setMessage("There is no expenses file in the Download folder. Please export " +
                            "expenses first.");
            builder.setPositiveButton(CLOSE,
                    new OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert1 = builder.create();
            alert1.show();
        } else {
            //now save the file from Sd card to internal storage data/data/....
            ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
            String directory = contextWrapper.getFilesDir().getPath();
            File myInternalFile = new File(directory, "expenses.txt");

            try {
                FileOutputStream fos = new FileOutputStream(myInternalFile);
                fos.write(myData.toString().getBytes());
                fos.close();
                Toast.makeText(MainActivity.this, "You imported the file", Toast.LENGTH_SHORT)
                        .show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setExpenseFieldsFromCameraExtractedText(String textResponseFromHaven) {

        String exp = "";
        String date = "";
        String desc = "";

        if (textResponseFromHaven == null || textResponseFromHaven.isEmpty()
                || !textResponseFromHaven.replaceAll("\\s+", "").matches(".*[a-zA-Z]+.*")) {
            // response is null so do nothing, print a message to user to take
            // another picture
            AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog
                    .THEME_HOLO_LIGHT)
                    .setTitle("Did not capture that. Try again!")
                    .setMessage("There was nothing captured from your image or the characters are" +
                            " not recognisable. " +
                            "Please take a better resolution photo!");
            AlertDialog alert1;
            builder.setPositiveButton(CLOSE,
                    new OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            alert1 = builder.create();
            alert1.show();
        }
        // at this point check if the output contains text characters. Remove all whitespaces and
        // new line and check
        else if (textResponseFromHaven.replaceAll("\\s+", " ").matches(".*[a-zA-Z]+.*")) {
            String[] wordForExpenses = new String[]{"total", "amount", "subtotal", "Visa Debit " +
                    "£", "total sale:", "total gbp"};

            for (String wordForExpense : wordForExpenses) {
                if (textResponseFromHaven.toLowerCase(Locale.ENGLISH).contains(wordForExpense)) {
                    // get the expense
                    //it is the substring of TOTAL plus one char and tha next space found in the
                    // response
                    String[] split = textResponseFromHaven.toLowerCase(Locale.ENGLISH).split
                            (wordForExpense.toLowerCase(Locale.US));
                    String amount = split[1].trim();
                    String amountTillFirstSpace = amount.substring(0, amount.indexOf("\n"));
                    //check that the first character is number
                    if (Character.isDigit(amount.trim().charAt(0))) {
                        int j = 0;
                        for (int k = 0; k < amountTillFirstSpace.length(); k++) {
                            Character charToCheck = amountTillFirstSpace.charAt(k);
                            //if the character is not a number and is one of . - space
                            if (!Character.isDigit(charToCheck) &&
                                    charToCheck.toString().equals(".") || charToCheck.toString()
                                    .equals("-")
                                    || charToCheck.toString().equals(" ")) {
                                exp = exp.concat(".");
                                j++;
                            } else if (Character.isDigit(charToCheck)) {
                                exp = exp.concat(String.valueOf(Character.getNumericValue(amount
                                        .trim().charAt(j))));
                                expenseFound = true;
                                j++;
                            }
                        }
                    }
                }
            }
            String[] wordForDates = new String[]{"Date:", DATE};
            for (String wordForDate : wordForDates) {
                //check if the word date exists in the receipt
                if (textResponseFromHaven.toLowerCase(Locale.ENGLISH).contains(wordForDate
                        .toLowerCase(Locale.US))) {
                    //check if the next word after the word Date is a valid date like mm/dd/yyyy
                    String[] split = textResponseFromHaven.toLowerCase(Locale.ENGLISH).split
                            (wordForDate.toLowerCase(Locale.US).trim());
                    String potentialDate = split[1].trim().substring(0, split[1].trim().indexOf("" +
                            " "));


                    // if the date string checked is null then date not found in the Map
                    if (findTheDateFormat(potentialDate) == null) {
                        date = null;
                    }
                    // if it is a valid date then return it. Check
                    // substring after the word Date and count the length of the string returned
                    // from the findTheDateFormat
                    else {

                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                        date = formatter.format(potentialDate);
                        dateFound = true;
                        break;
                    }
                    Log.e("Date Found in RECEIPT: ", date);
                    // get the date
                    //NEED TO MAKE SURE THAT THE WORDSFORDATES IS THE ACTUAL DATE AND NOT THE
                    // WORD DATE/

                } else {
                    String[] splitResponseToStrings = textResponseFromHaven.replaceAll("\n", "")
                            .split(" ");
                    for (int j = 0; j < splitResponseToStrings.length; j++) {
                        if (splitResponseToStrings[j].contains("/")) {
                            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy", Locale
                                    .US);
                            splitResponseToStrings[j] = formatter.format(splitResponseToStrings[j]);
                        }
                        if (findTheDateFormat(splitResponseToStrings[j]) != null) {
                            date = splitResponseToStrings[j];
                        }
                    }
                }
            }

            String[] wordForSuperMarket = new String[]{"Tesco", "Groceries", "Morrisons", "Asda",
                    "Lidl", "Waitrose", "Sandwich"};
            String[] wordForEntertainment = new String[]{"Course", "Table", "Drink",
                    "Restaurants", "Coffee"};
            String[] wordForShopping = new String[]{"Jacket", "Trouser", "Cycles", "Medium",
                    "Large", "Small",};
            String[] wordForPetrol = new String[]{"Unleaded", "Petrol", "Petroleum"};
            for (String aWordForSuperMarket : wordForSuperMarket) {
                if (textResponseFromHaven.contains(aWordForSuperMarket)) {
                    // get the description
                    desc = "Supermarket";
                    descFound = true;
                    break;
                }
            }
            for (String aWordForEntertainment : wordForEntertainment) {
                if (textResponseFromHaven.contains(aWordForEntertainment)) {
                    desc = ENTERTAINMENT;
                    descFound = true;
                    break;
                }
            }
            for (String aWordForShopping : wordForShopping) {
                if (textResponseFromHaven.contains(aWordForShopping)) {
                    desc = SHOPPING;
                    descFound = true;
                    break;
                }
            }
            for (String aWordForPetrol : wordForPetrol) {
                if (textResponseFromHaven.contains(aWordForPetrol)) {
                    desc = "Petrol";
                    descFound = true;
                    break;
                }
            }
        }
        showDialogsWithDataFoundFromCapture(exp, desc, date);
    }

    private void showDialogsWithDataFoundFromCapture(final String expenseAmount, final String
            description, final String date) {

        if (expenseFound && dateFound && descFound) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog
                    .THEME_HOLO_LIGHT)
                    .setTitle("Expense, Date and Description Found")
                    .setMessage("Expense Amount: " + expenseAmount + "\n" +
                            "Description: " + description + "\n" +
                            "Date: " + date + "\n" + "\n" + "\n" +
                            "Is that correct?");
            AlertDialog alert1;

            builder.setPositiveButton("Yes, Set them and save to file the Expense",
                    new OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            expensesField.setText(expenseAmount);
                            dateText.setText(date);

                            if (!description.equals(null)) {
                                int spinnerPosition = spinnerAdapter.getPosition(description);
                                descriptionsItem.setSelection(spinnerPosition + 1);
                            }
                            //write the expenses to the file
                            WriteToFile("","","");
                        }
                    });

            builder.setNegativeButton("No, I want to edit one of the fields", new OnClickListener
                    () {
                public void onClick(DialogInterface dialog, int id) {
                    expensesField.setText(expenseAmount);
                    dateText.setText(date);
                    if (!description.equals(null)) {
                        int spinnerPosition = spinnerAdapter.getPosition(description);
                        descriptionsItem.setSelection(spinnerPosition + 1);
                    }
                    dialog.cancel();
                }
            });
            alert1 = builder.create();
            alert1.show();
        }

        // something was not detected from the receipt
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog
                    .THEME_HOLO_LIGHT)
                    .setTitle("Found")
                    .setMessage( EXPENSE_AMOUNT + ": " + expenseAmount + "\n" +
                            DESCRIPTION +": " + description + "\n" +
                            DATE +": " + date);
            AlertDialog alert1;

            builder.setNegativeButton(CANCEL, new OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });

            builder.setPositiveButton("Add or Edit the fields needed", new OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (expenseAmount != null && !expenseAmount.equals("")) {
                        expensesField.setText(expenseAmount);
                    }

                    if (description != null && !description.equals("")) {
                        int spinnerPosition = spinnerAdapter.getPosition(description);
                        descriptionsItem.setSelection(spinnerPosition + 1);
                    }

                    if (date != null && !date.equals("")) {
                        dateText.setText(date);
                    }
                    dialog.cancel();
                }
            });
            alert1 = builder.create();
            alert1.show();
        }
    }


    public void showHelp() {

        Intent intent1 = new Intent(this, HelpActivity.class);
        startActivity(intent1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
            return true;
        }

        if (id == R.id.removeAds) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, AlertDialog
                    .THEME_HOLO_LIGHT)
                    .setTitle("Ads are Removed")
                    .setMessage("You have already purchased Pro version so ads have been removed.");
            builder.setPositiveButton(CLOSE,
                    new OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert1 = builder.create();
            alert1.show();

        }

        if (id == R.id.action_help) {
            showHelp();
        }

        return super.onOptionsItemSelected(item);
    }

    public void clear()//this method resets the shared preferences
    {
        SharedPreferences prefs = mPreferenceManager.getDefaultSharedPreferences
                (getApplicationContext());
        //SharedPreferences prefs; // here you get your prefrences by either of two methods
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }

    public static Float getIncomeForJan() {
        return incomeForJan;
    }

    public static Float getIncomeForFeb() {
        return incomeForFeb;
    }

    public static Float getIncomeForMar() {
        return incomeForMar;
    }

    public static Float getIncomeForApr() {
        return incomeForApr;
    }

    public static Float getIncomeForMay() {
        return incomeForMay;
    }

    public static Float getIncomeForJun() {
        return incomeForJun;
    }

    public static Float getIncomeForJul() {
        return incomeForJul;
    }

    public static Float getIncomeForAug() {
        return incomeForAug;
    }

    public static Float getIncomeForSep() {
        return incomeForSep;
    }

    public static Float getIncomeForOct() {
        return incomeForOct;
    }

    public static Float getIncomeForNov() {
        return incomeForNov;
    }

    public static Float getIncomeForDec() {
        return incomeForDec;
    }

    public static ArrayList<String> getitemsAddedByUser() {
        return itemsAddedByUser;
    }

    public void exportFileToPDF() {

        verifyStoragePermissions(MainActivity.this);

        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment
                .DIRECTORY_DOWNLOADS), "");

        if (!dir.exists()) {
            dir.mkdir();
        }

        String file = dir + "/expenses.pdf";

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            addTitlePage(document);
            addContent(document);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addContent(Document document) throws DocumentException {

        Paragraph preface = new Paragraph();

        try {
            InputStream inputStream = new FileInputStream("/data/data/uk.co.irokottaki" +
                    ".moneycontrol/files/expenses.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = br.readLine()) != null) {

                if (!line.equals("")) {

                    PdfPTable table = new PdfPTable(3);
                    table.setWidthPercentage(50);
                    table.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

                    Font f1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD);
                    f1.setColor(BaseColor.BLUE);

                    if (line.startsWith(AMOUNT)) {
                        //this is for a case that the line might have spaces at the end
                        String lineTrimmed = line.trim();

                        // add a new font color for the header.
                        table.addCell(new Phrase(lineTrimmed.substring(0, lineTrimmed.indexOf(" " +
                                "")), f1));
                        table.addCell(new Phrase(lineTrimmed.substring(lineTrimmed.indexOf(" "),
                                lineTrimmed.lastIndexOf(" ")).trim(), f1));
                        table.addCell(new Phrase(lineTrimmed.substring(lineTrimmed.lastIndexOf(" " +
                                ""), lineTrimmed.length()).trim(), f1));

                    } else {
                        // add all the columns of the file amount description and date in a table
                        // to be formatted correctly
                        table.addCell(line.substring(0, line.indexOf(" ")));
                        table.addCell(line.substring(line.indexOf(" "), line.lastIndexOf(" "))
                                .trim());
                        table.addCell(line.substring(line.lastIndexOf(" "), line.length()).trim());
                    }
                    document.add(table);
                }

                addEmptyLine(preface);

            }// end of while
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addTitlePage(Document document)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        preface.setAlignment(Element.ALIGN_CENTER);

        // Lets write a big header
        preface.add(new Paragraph("Total Expenses", new Font(Font.FontFamily.TIMES_ROMAN, 18,
                Font.BOLD)));

        addEmptyLine(preface);
        document.add(preface);
    }


    private static void addEmptyLine(Paragraph paragraph) {
        for (int i = 0; i < 1; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(),
                inImage, "Title", null);
        return Uri.parse(path);
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null &&
                permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager
                        .PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    protected void getUserAccount() {
        if (ACCESS_TOKEN == null) return;
        new UserAccountTask(DropboxClient.getClient(ACCESS_TOKEN), new UserAccountTask
                .TaskDelegate() {
            @Override
            public void onAccountReceived(FullAccount account) {
                //Print account's info
                Log.d("User", account.getEmail());
                Log.d("User", account.getName().getDisplayName());
                Log.d("User", account.getAccountType().name());
                //updateUI(account);
            }

            @Override
            public void onError() {
                Log.d("User", "Error receiving account details.");
                invalidToken = true;
                System.out.println(invalidToken);
            }
        }).execute();
    }

    public boolean tokenExists() {

        SharedPreferences prefs = getSharedPreferences("com.example.valdio.dropboxintegration",
                Context.MODE_PRIVATE);
        String accessToken = prefs.getString("access-token", null);

        return accessToken != null;
    }

    private boolean checkValidToken(String accessToken) {
        boolean validToken = false;
        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);
        DbxAuthFinish authFinish = null;
        try {
            DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0", Locale.getDefault
                    ().toString());
            DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);
            String authorizeUrl = webAuth.start();
            String code = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
            authFinish = webAuth.finish(code);
            validToken = true;
        } catch (DbxException e) {
            e.printStackTrace();
            validToken = false;
        } catch (IOException j) {
            j.printStackTrace();
        }

        accessToken = authFinish.getAccessToken();

        return validToken;
    }

    private String retrieveAccessToken() {
        //check if ACCESS_TOKEN is stored on previous app launches
        SharedPreferences prefs = getSharedPreferences("com.example.valdio.dropboxintegration",
                Context.MODE_PRIVATE);
        String accessToken = prefs.getString("access-token", null);
        if (accessToken == null) {
            Log.d("AccessToken Status", "No token found");
            return null;
        } else {
            //accessToken already exists
            Log.d("AccessToken Status", "Token exists");
            return accessToken;
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public static String findTheDateFormat(String dateString) {

        String dateStringFound = "";

        DATE_FORMAT_REGEXPS = new HashMap<String, String>() {{
            put("^\\d{8}$", "yyyyMMdd");
            put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
            put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
            put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy");
            put("^\\d{1,2}/\\d{1,2}/\\d{2}$", "dd/MM/yy");
            put("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd");
            put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
            put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
            put("^\\d{12}$", "yyyyMMddHHmm");
            put("^\\d{8}\\s\\d{4}$", "yyyyMMdd HHmm");
            put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$", "dd-MM-yyyy HH:mm");
            put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy-MM-dd HH:mm");
            put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$", "MM/dd/yyyy HH:mm");
            put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy/MM/dd HH:mm");
            put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMM yyyy HH:mm");
            put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMMM yyyy HH:mm");
            put("^\\d{14}$", "yyyyMMddHHmmss");
            put("^\\d{8}\\s\\d{6}$", "yyyyMMdd HHmmss");
            put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd-MM-yyyy HH:mm:ss");
            put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss");
            put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "MM/dd/yyyy HH:mm:ss");
            put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss");
            put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMM yyyy HH:mm:ss");
            put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMMM yyyy " +
                    "HH:mm:ss");
        }};

        for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
            if (dateString.toLowerCase(Locale.ENGLISH).matches(regexp)) {

                dateStringFound = DATE_FORMAT_REGEXPS.get(regexp);
                break;
            } else {
                dateStringFound = null;// unknown format so handle it in the call

            }
        }

        return dateStringFound;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page")
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    //CODE TO IMPLEMENT THE TESSERACT API FOT TAKING IMAGES O RECEIPTS AND EXTRACT TEXT
    //ON THE ONACTIVITYRESULT METHOD:
    //if (requestCode == SELECT_PICTURE || requestCode == SELECT_PICTURE) {
    //if (resultCode == RESULT_OK && null != data) {
    /*String _path = Environment.getExternalStorageDirectory() + "/test.jpg";
                ExifInterface exif = null;
                try {
                    exif = new ExifInterface(_path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL);
                int rotate = 0;

                switch (exifOrientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotate = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotate = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotate = 270;
                        break;
                }
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 4;

                    Bitmap btmp = BitmapFactory.decodeFile( _path, options );
                    //_image.setImageBitmap(bitmap);
                //BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                //Bitmap btmp = BitmapFactory.decodeFile(mImageFullPathAndName,bmOptions);
                if (rotate != 0) {
                    int w = btmp.getWidth();
                    int h = btmp.getHeight();
                    // Setting pre rotate
                    Matrix mtx = new Matrix();
                    mtx.preRotate(rotate);
                    // Rotating Bitmap & convert to ARGB_8888, required by tess
                    btmp = Bitmap.createBitmap(btmp, 0, 0, w, h, mtx, false);
                }

                btmp = btmp.copy(Bitmap.Config.ARGB_8888, true);
                String DATA_PATH =Environment.getExternalStorageDirectory()+  "/tesseract/";*/
                /*File dir = new File(DATA_PATH + "tessdata/eng.traineddata");
                    if (!dir.exists()) {
                        //dir.mkdirs();
                    }*/

    //make sure training data has been copied
    //checkFile(new File(DATA_PATH + "tessdata/"));
    //TessBaseAPI baseApi = new TessBaseAPI();
    //String lang = "eng";
    //baseApi.init(DATA_PATH, lang);
    // Eg. baseApi.init("/mnt/sdcard/tesseract/tessdata/eng.traineddata", "eng");
    //baseApi.setImage(btmp);
    //String recognizedText = getUTF8Text();
    //baseApi.end();
    //System.out.println(recognizedText);
    //}
/*private void copyFile() {
        try {
            //location we want the file to be at
            String filepath = Environment.getExternalStorageDirectory() +
            "/tesseract/tessdata/eng.traineddata";
            //get access to AssetManager
            AssetManager assetManager = getAssets();
            //open byte streams for reading/writing
            InputStream instream = assetManager.open("tessdata/eng.traineddata");
            OutputStream outstream = new FileOutputStream(filepath);
            //copy the file to the location specified by filepath
            byte[] buffer = new byte[1024];
            int read;
            while ((read = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, read);
            }
            outstream.flush();
            outstream.close();
            instream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkFile(File dir) {
        //directory does not exist, but we can successfully create it
        if (!dir.exists()&& dir.mkdirs()){
            copyFile();
        }
        //The directory exists, but there is no data file in it
        if(dir.exists()) {
            String datafilepath = Environment.getExternalStorageDirectory()+
            "/tesseract/tessdata/eng.traineddata";
            File datafile = new File(datafilepath);
            if (!datafile.exists()) {
                copyFile();
            }
        }
    }*/

}