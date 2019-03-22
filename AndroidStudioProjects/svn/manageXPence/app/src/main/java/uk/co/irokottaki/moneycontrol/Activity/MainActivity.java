package uk.co.irokottaki.moneycontrol.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import hod.api.hodclient.HODApps;
import hod.api.hodclient.HODClient;
import hod.api.hodclient.IHODClientCallback;
import uk.co.irokottaki.moneycontrol.DropboxClient;
import uk.co.irokottaki.moneycontrol.R;
import uk.co.irokottaki.moneycontrol.UploadTask;
import uk.co.irokottaki.moneycontrol.UserAccountTask;
import uk.co.irokottaki.moneycontrol.inappbilling.IabHelper;
import uk.co.irokottaki.moneycontrol.inappbilling.IabResult;
import uk.co.irokottaki.moneycontrol.inappbilling.Inventory;
import uk.co.irokottaki.moneycontrol.inappbilling.Purchase;
import uk.co.irokottaki.moneycontrol.model.AnyYear;
import uk.co.irokottaki.moneycontrol.utils.ChartsUtil;
import uk.co.irokottaki.moneycontrol.utils.MainActivityUtil;
import uk.co.irokottaki.moneycontrol.utils.NothingSelectedSpinnerAdapter;
import uk.co.irokottaki.moneycontrol.utils.ReminderJobService;
import uk.co.irokottaki.moneycontrol.utils.Utils;

import static android.content.DialogInterface.OnClickListener;
import static android.preference.PreferenceManager.getDefaultSharedPreferences;
import static uk.co.irokottaki.moneycontrol.utils.Constants.ADD;
import static uk.co.irokottaki.moneycontrol.utils.Constants.ADD_EXPENSES;
import static uk.co.irokottaki.moneycontrol.utils.Constants.ADSDISABLED;
import static uk.co.irokottaki.moneycontrol.utils.Constants.AMOUNT;
import static uk.co.irokottaki.moneycontrol.utils.Constants.BALANCE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.BUDGETWARNINGS;
import static uk.co.irokottaki.moneycontrol.utils.Constants.BUDGET_WARNING;
import static uk.co.irokottaki.moneycontrol.utils.Constants.CANCEL;
import static uk.co.irokottaki.moneycontrol.utils.Constants.CHART_EXPENSES;
import static uk.co.irokottaki.moneycontrol.utils.Constants.CLOSE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.COUNCIL_TAX;
import static uk.co.irokottaki.moneycontrol.utils.Constants.DATE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.DESCRIPTION;
import static uk.co.irokottaki.moneycontrol.utils.Constants.DESCRIPTIONS;
import static uk.co.irokottaki.moneycontrol.utils.Constants.DESCRIPTIONS_FILE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.EMPTY_FIELD;
import static uk.co.irokottaki.moneycontrol.utils.Constants.ENTERTAINMENT;
import static uk.co.irokottaki.moneycontrol.utils.Constants.ERROR_DURING_PURCHASE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.ERROR_OCCURED_DURING_PURCHASE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.EXCEPTION;
import static uk.co.irokottaki.moneycontrol.utils.Constants.EXPENSES_FILE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.EXPENSE_AMOUNT;
import static uk.co.irokottaki.moneycontrol.utils.Constants.EXPENSE_DESCRIPTION;
import static uk.co.irokottaki.moneycontrol.utils.Constants.EXPORT_PDF;
import static uk.co.irokottaki.moneycontrol.utils.Constants.EXPORT_TO_DROPBOX;
import static uk.co.irokottaki.moneycontrol.utils.Constants.EXPORT_TXT;
import static uk.co.irokottaki.moneycontrol.utils.Constants.HOME;
import static uk.co.irokottaki.moneycontrol.utils.Constants.HOUSE_BILLS;
import static uk.co.irokottaki.moneycontrol.utils.Constants.HOUSE_RENT;
import static uk.co.irokottaki.moneycontrol.utils.Constants.INCOME;
import static uk.co.irokottaki.moneycontrol.utils.Constants.INFORMATION;
import static uk.co.irokottaki.moneycontrol.utils.Constants.IOEXCEPTION;
import static uk.co.irokottaki.moneycontrol.utils.Constants.ISPAYMENTCIRCLE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.MANAGE_EXPENSES;
import static uk.co.irokottaki.moneycontrol.utils.Constants.MORTGAGE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.OK;
import static uk.co.irokottaki.moneycontrol.utils.Constants.PARSE_EXCEPTION;
import static uk.co.irokottaki.moneycontrol.utils.Constants.PREFERENCES;
import static uk.co.irokottaki.moneycontrol.utils.Constants.REMINDER_TO_TURN_OFF_NOTIFICATIONS;
import static uk.co.irokottaki.moneycontrol.utils.Constants.REMOVE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.RESET;
import static uk.co.irokottaki.moneycontrol.utils.Constants.SAVE_TO_FILE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.SELECT_DATE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.SET;
import static uk.co.irokottaki.moneycontrol.utils.Constants.SHOPPING;
import static uk.co.irokottaki.moneycontrol.utils.Constants.SUPERMARKET;
import static uk.co.irokottaki.moneycontrol.utils.Constants.TRAVEL;
import static uk.co.irokottaki.moneycontrol.utils.Constants.USERISPRO;
import static uk.co.irokottaki.moneycontrol.utils.Constants.VALUEFROMNUMPICKER1;
import static uk.co.irokottaki.moneycontrol.utils.Constants.YEARS_MAPPED_TO_OBJECT_YEARS_MAP;


public class MainActivity extends AppCompatActivity implements IHODClientCallback {

    private Button dateButton;
    private Button exportButton;
    ImageButton backUpInfoButton;
    ImageButton photoButton;
    ImageButton addExpensesInfoBtn;
    EditText expensesField;
    EditText dateText;
    EditText addedDescriptionField;
    EditText incomeField;
    private Spinner descriptionsItem;
    private Spinner addExpensesByDescription;
    private int yearX;
    private int monthX;
    private int dayX;
    private int valueFromNumPicker1;
    static final int SELECT_DATE_DIALOG_ID = 0;
    static final int SELECT_END_DATE_DIALOG_ID = 1;
    private TextView balanceLabel;
    ArrayAdapter<String> spinnerAdapter;
    private List<String> itemsAddedByUser;
    private String descriptionAddedByUser;
    private String accessToken;
    private ArrayList<String> allDescriptions;//descriptions
    RelativeLayout layout;
    private double balance;
    TabHost tabHost;
    private final Handler handler = new Handler();
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    ImageView imageView;
    private static final  String TAG = "Millenial Media";
    private static final  String TAG2 = "In App Billing";
    IabHelper mHelper;
    static final String BASE_64_ENCODED_PUBLIC_KEY =
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqcYpXYA3pWCTMjOYJNNC70rNhXmbwxI5i4sGCtmZWN" +
                    "+eVFvrvtBtlwm8Wxwab8wf4CyLUxthccmgSd2Wmb6lHYVHG9/F7VSn+u3f9tnu8x" +
                    "+Oh30fyiSr4Wdesz0yfTwflVipA4wNwcEjxJoO0t8CCEyswQZcAzLAMzkodlMVwcdWx0kJ39qJxxuT8LWFlqwDpUSlLm6sPr+XmbD/vhfmd1h+qNQTteVte2Q5vVLSAk1/hCsqLCzrDp0BJ30w4f0nzEBn3g/7KIn3KQQp+6JE+xJanavahcvAU//PTDmy8t/bYxiFtn8kquBCL9xcHa/2Nw8PTEhzeWx3hCRUAugruwIDAQAB";
    IInAppBillingService mService;
    Bundle querySkus;
    static final String SKU = "android.test.purchased";
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener;
    private NumberPicker numberPicker1;
    private boolean isPaymentCircleSet;
    private boolean budgetWarningEnabled;
    private boolean invalidToken;
    private boolean expenseFound;
    private boolean dateFound;
    private boolean descFound;
    private boolean adsDisabled;
    private boolean userIsPro;
    /*private InlineAd inlineAd;
    private static final String APP_KEY = "h5x1321simc1oxm";
    private static final String APP_SECRET = "y2hrku7km5xdtvi";*/
    private static final String APIKEY = "2990d40f-d7ca-4fdb-bacd-98d3cbe6eef5";
    private static final int TAKE_PICTURE = 1;
    HODClient hodClient;
    private Uri imageUri;
    private ChartsUtil util;
    private HashMap<String, AnyYear> yearsMappedToObjectYearsMap;
    private MainActivityUtil mainUtil;
    Calendar calendar;
    private CheckBox repeatCheckBox;
    private Button endDateBtn;
    private ArrayList datesTheRepeatingExpenseOccurs;
    private Utils utils;

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
        calendar = Calendar.getInstance();
        yearX = calendar.get(Calendar.YEAR);
        monthX = calendar.get(Calendar.MONTH);
        dayX = calendar.get(Calendar.DAY_OF_MONTH);

        setContentView(R.layout.activity_main);
        setTitle(HOME);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams
                .SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        hodClient = new HODClient(APIKEY, this);

        SharedPreferences sharedprefs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        adsDisabled = sharedprefs.getBoolean(ADSDISABLED, false);/*retrieve the boolean value for ads*/

        userIsPro = sharedprefs.getBoolean(USERISPRO, false);/*retrieve the boolean for the pro user*/

        //get the value from settings for the budget warnings
        budgetWarningEnabled = sharedprefs.getBoolean(BUDGETWARNINGS, false);
        //get the boolean value for payment circle
        isPaymentCircleSet = sharedprefs.getBoolean(ISPAYMENTCIRCLE, false);

        //get the int value from number picker
        valueFromNumPicker1 = sharedprefs.getInt(VALUEFROMNUMPICKER1, valueFromNumPicker1);

        util = new ChartsUtil(this);
        mainUtil = new MainActivityUtil(this);

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
        utils = new Utils(this);
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

        // compute your public key and store it in BASE_64_ENCODED_PUBLIC_KEY
        mHelper = new IabHelper(MainActivity.this, BASE_64_ENCODED_PUBLIC_KEY);
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
        skuList.add(SKU);
        querySkus = new Bundle();
        querySkus.putStringArrayList("ITEM_ID_LIST", skuList);

        // START OF FIRST TAB
        /* Info button*/
        addExpensesInfoBtn = new ImageButton(this);
        addExpensesInfoBtn = (ImageButton) findViewById(R.id.addExpensesInfoBtn);

        //Expenses Label
        TextView expensesLabel = new TextView(this);
        expensesLabel.setText(EXPENSE_AMOUNT);

        //Expenses TextField
        expensesField = new EditText(this);
        expensesField = (EditText) findViewById(R.id.expenseText);

        //Repeat checkbox
        repeatCheckBox = new CheckBox(this);
        repeatCheckBox = (CheckBox) findViewById(R.id.repeatCheckBox);

        //end date button
        endDateBtn = new Button(this);
        endDateBtn.setVisibility(View.GONE);
        if(repeatCheckBox.isChecked()) {
            endDateBtn.setVisibility(View.VISIBLE);
        }
        else {
            endDateBtn.setVisibility(View.GONE);
        }

        //Description Label
        TextView descriptionLabel = new TextView(this);
        descriptionLabel.setText(EXPENSE_DESCRIPTION);

        //Description spinner
        descriptionsItem = (Spinner) findViewById(R.id.descriptionCombo);

        itemsAddedByUser = mainUtil.readDescriptionsFile();
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
        Button addDescrButton = new Button(this);
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
        Button saveToFile = new Button(this);
        saveToFile.setText(SAVE_TO_FILE);
        saveToFile.setBackgroundColor(Color.BLUE);
        saveToFile = (Button) findViewById(R.id.saveButton);

        //income field
        incomeField = new EditText(this);
        incomeField = (EditText) findViewById(R.id.incomeField);

        //get the stored income value from preferences and set it to the income field
        SharedPreferences prefers = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        String incomeStored = prefers.getString(INCOME, "");
        incomeField.setText(incomeStored);

        //circle Button
        Button circleButton = (Button) findViewById(R.id.circleButton);

        //Balance Label
        balanceLabel = new TextView(this);
        balanceLabel = (TextView) findViewById(R.id.balanceView);

        //Edit Button
        Button editButton = (Button) findViewById(R.id.editButton);

        // END OF FIRST TAB

        //Add expenses button
        Button addExpensesButton = (Button) findViewById(R.id.addExpensesButton);

        //add expenses by description Spinner
        addExpensesByDescription = (Spinner) findViewById(R.id.addExpensesByDescSpinner);
        //set the adapter for that spinner so that it will take the values as added on the first
        // spinner.
        addExpensesByDescription.setAdapter(spinnerAdapter);
        addExpensesByDescription.setAdapter(new NothingSelectedSpinnerAdapter(spinnerAdapter, R
                .layout.spinnernothingselected, this));

        //Information with example date range button
        ImageButton infoDateRangeButton = (ImageButton) findViewById(R.id.infoDateRange);

        //Button for the Budget Control
        Button budgetButton = (Button) findViewById(R.id.budgetButton);

        //Report button
        Button reportButton = (Button) findViewById(R.id.reportButton);

        //Chart button
        Button chartButton = (Button) findViewById(R.id.chartButton);

        //Bar Chart button
        Button barChartButton = (Button) findViewById(R.id.barChartbutton);

        //Annual Chart Button
        Button annualChartButton = (Button) findViewById(R.id.annualChartButton);

        //Savings Chart Button
        Button savingsButton = (Button) findViewById(R.id.annualSavingsButton);

        // Back up Info button
        backUpInfoButton = new ImageButton(this);
        backUpInfoButton = (ImageButton) findViewById(R.id.backUpInfoButton);

        //Export Button
        exportButton = new Button(this);
        exportButton = (Button) findViewById(R.id.exportButton);

        //import button
        Button importButton = (Button) findViewById(R.id.importButton);

        showDialogOnButtonClick();

        /*Call the readTheFile here to read the expenses file and populate the map with year as key and the object AnyYear that has all the necessary fields */
        yearsMappedToObjectYearsMap = new HashMap<>();
        yearsMappedToObjectYearsMap = (HashMap<String, AnyYear>) util.readTheFile();

        saveToFileClickListener(saveToFile);

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

        repeatCheckBoxChangeListener();

        incomeFieldTextChangedListener();


        addExpensesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText datesField = (EditText) findViewById(R.id.dateFromTo);
                String datesFromTo = datesField.getText().toString();

                double sumOfExpenses = mainUtil.addTheExpenses(MainActivity.this, datesFromTo,addExpensesByDescription);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_Light_Dialog);

                if (addExpensesByDescription.getSelectedItemPosition() > 0) {
                    builder.setTitle(addExpensesByDescription.getSelectedItem().toString() + " expenses for: " + datesFromTo);
                }
                else {
                    builder.setTitle("Expenses for: " + datesFromTo);
                }

                builder.setMessage("You have spent: " + String.format(Locale.ENGLISH, "%.2f", sumOfExpenses));
                AlertDialog alert1;
                builder.setPositiveButton(OK,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                alert1 = builder.create();
                if(sumOfExpenses > 0) {
                    alert1.show();
                }
            }
        });

        photoBtnClickListener();

        infoDateRangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,
                        R.style.Theme_AppCompat_Light_Dialog)
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
                Log.d(TAG2, "Title: " + inv.getSkuDetails(SKU).getTitle());
                Log.d(TAG2, "Description: " + inv.getSkuDetails(SKU).getDescription());
                Log.d(TAG2, "Price = " + inv.getSkuDetails(SKU).getPrice());

            }
        };

        budgetBtnClickListener(budgetButton);

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), ReportActivity.class);
                intent.putExtra(YEARS_MAPPED_TO_OBJECT_YEARS_MAP, yearsMappedToObjectYearsMap);
                startActivity(intent);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentEdit = new Intent(view.getContext(), EditActivity.class);
                startActivityForResult(intentEdit,2);
            }
        });

        chartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentEdit = new Intent(view.getContext(), ChartActivity.class);
                intentEdit.putExtra(DESCRIPTIONS, allDescriptions);
                intentEdit.putExtra(YEARS_MAPPED_TO_OBJECT_YEARS_MAP, yearsMappedToObjectYearsMap);
                startActivity(intentEdit);
            }
        });

        barChartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentEdit = new Intent(view.getContext(), HorizontalBarChartActivity.class);

                intentEdit.putExtra(YEARS_MAPPED_TO_OBJECT_YEARS_MAP, yearsMappedToObjectYearsMap);
                startActivity(intentEdit);
            }
        });

        annualChartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentAnnualChart = new Intent(view.getContext(), AnnualChartActivity.class);
                intentAnnualChart.putExtra(YEARS_MAPPED_TO_OBJECT_YEARS_MAP, yearsMappedToObjectYearsMap);
                startActivity(intentAnnualChart);
            }
        });

        savingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentSavingsChart = new Intent(view.getContext(), AnnualSavingsActivity.class);
                intentSavingsChart.putExtra(YEARS_MAPPED_TO_OBJECT_YEARS_MAP, yearsMappedToObjectYearsMap);
                startActivity(intentSavingsChart);
            }
        });

        addExpensesInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,
                        R.style.Theme_AppCompat_Light_Dialog)
                        .setTitle(INFORMATION)
                        .setMessage("To save an expense you need to add: " +
                                "\n 1. The expense amount. " +
                                "\n 2. Optional: if it is a repeating expense then check the Repeat tickbox and then select the end date of the expense. " +
                                "\n 3. The description of the expense, if you don't find it in the dropdown then you can " +
                                "add or remove your own description by pressing the ADD button" +
                                "\n 4. The date of the expense. This is the current date.");
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

        backUpInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,
                        R.style.Theme_AppCompat_Light_Dialog)
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

        exportBtnClickListener();

        importBtnClickListener(importButton);

        sendNotificationsEverySecondDay();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


    }// end of onCreate method

    private void sendNotificationsEverySecondDay() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            JobInfo.Builder builder = new JobInfo.Builder( 613, new ComponentName(getPackageName(), ReminderJobService.class.getName()));
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
                    .setRequiresDeviceIdle(false)
                    .setRequiresCharging(false)
                    .setMinimumLatency(86400000)//set it to fire every day 86400000 ms is one day
                    .setOverrideDeadline(86401000)
                    .setPersisted(true);
            JobScheduler jobScheduler = (JobScheduler) getSystemService( Context.JOB_SCHEDULER_SERVICE );
            if (jobScheduler.schedule(builder.build()) == JobScheduler.RESULT_FAILURE){
                Log.w("MainActivity", "Something went wrong when scheduling the reminders");
            }
        }
        else {

            //this is for the Notifications for API<26 before the Oreo
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
            notificationIntent.addCategory("android.intent.category.DEFAULT");

            PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, + AlarmManager.INTERVAL_DAY * 2,
                    AlarmManager.INTERVAL_DAY * 2, broadcast);
        }
    }

    private void saveToFileClickListener(Button saveToFile) {
        saveToFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (expensesField.getText().toString().equals("") || expensesField.getText().toString().equals(" ") || descriptionsItem.getSelectedItem
                        () == null || dateText.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_Light_Dialog)
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
                }
                else {
                    writeToFile(expensesField.getText().toString(), descriptionsItem.getSelectedItem().toString(), dateText.getText().toString());
                    /* Is it a repeating expense */
                    mainUtil.processExpenseRepeatingAmount(expensesField.getText().toString(), descriptionsItem.getSelectedItem().toString(), repeatCheckBox, datesTheRepeatingExpenseOccurs, MainActivity.this);
                }
            }
        });
    }

    private void repeatCheckBoxChangeListener() {
        repeatCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    endDateBtn.setVisibility(View.VISIBLE);
                }
                else {
                    endDateBtn.setVisibility(View.GONE);
                }
            }
        });
    }

    private void incomeFieldTextChangedListener() {
        incomeField.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                /* Not used */
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /* Not used */
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
                                String currentMonth = String.format(Locale.UK, "%tB", calendar);
                                balance = mainUtil.processBalance(incomeField, yearsMappedToObjectYearsMap,isPaymentCircleSet, currentMonth, yearX);
                                DecimalFormat df = new DecimalFormat("#.0");
                                balanceLabel.setText(BALANCE + df.format(balance));
                                showStackedBar();
                            }
                        }
                    }, 3000);//this is a delay of 3 seconds that starts when the user finished
                    // typing.
                }
            }
        });
    }

    private void photoBtnClickListener() {
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Request for permission if not granted
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest
                        .permission.CAMERA};
                if (!hasPermissions(getApplicationContext(), permissions)) {
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
    }

    private void budgetBtnClickListener(Button budgetButton) {

        budgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
                    public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
                        if (result.isFailure()) {
                            Toast.makeText(MainActivity.this, ERROR_DURING_PURCHASE +
                                    result, Toast.LENGTH_SHORT).show();

                        } else if (purchase.getSku().equals(SKU)) {
                            //used to call the consume item method here
                        }
                    }
                };
                //check if the user has purchased the PRO version
                // If the request is successful, the returned Bundle has a response code of 0.
                //check if user id Pro retrieve boolean from SharedPreferences
                //the user has PRO version so start budget activity.
                Intent intentBudget = new Intent(view.getContext(), BudgetActivity.class);
                startActivity(intentBudget);
                // if the userIsPro is false the user has not purchased the PRO version
            }
        });
    }

    private void exportBtnClickListener() {

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

                            processExportToDropbox();

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
    }

    private void processExportToDropbox() {

        accessToken = mainUtil.retrieveAccessToken();

        getUserAccount();
        if (!mainUtil.tokenExists() || invalidToken) {
            //No token Back to LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent
                    .FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        //Image URI received
        File file = new File("/data/data/uk.co.irokottaki" +
                ".moneycontrol/files/expenses.txt");//get the directory of the file stored
        if (!invalidToken && accessToken!= null) {
            //Initialize UploadTask
            new UploadTask(DropboxClient.getClient(accessToken), file,
                    getApplicationContext()).execute();
        }
    }

    private void importBtnClickListener(Button importButton) {
        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
                    public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
                        if (result.isFailure()) {
                            Toast.makeText(MainActivity.this, ERROR_OCCURED_DURING_PURCHASE +
                                    result, Toast.LENGTH_SHORT).show();
                        } else if (purchase.getSku().equals(SKU)) {
                            //used to call the consume item method here
                        }
                    }
                };
                //check if the user has purchased the PRO version
                //check if the user is Pro retrieve boolean from SharedPreferences
                //the user has PRO version so make the import
                readFileFromSdCard();
                // if userIsPro is false the user has not purchased the PRO version
            }
        });
    }

    public MainActivity() {
    /* Not used*/
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
                mHelper.consumeAsync(inventory.getPurchase(SKU), mConsumeFinishedListener);
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

        SharedPreferences prefs = getDefaultSharedPreferences(this);
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
        /*call again the methods to calculate the balance and show the stacked bar on the main activity. That would be triggered when user is changing/adding expenses*/
        if (isPaymentCircleSet) {
            processDateCircle();

        } else {
            String currentMonth = String.format(Locale.UK, "%tB", calendar);
            balance = mainUtil.processBalance(incomeField, yearsMappedToObjectYearsMap,isPaymentCircleSet, currentMonth, yearX);
            DecimalFormat df = new DecimalFormat("#.0");
            balanceLabel.setText(BALANCE + df.format(balance));
            showStackedBar();
        }

        // Binding to IInAppBillingService
        Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);

        if(repeatCheckBox.isChecked()) {
            endDateBtn = (Button) findViewById(R.id.endDateButton);
        }
        else {
            endDateBtn.setVisibility(View.GONE);
        }
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

        handlePurchaseResponse(requestCode, resultCode, data);

        if (requestCode == 1) {
            String imageurl = null;
            if (resultCode == Activity.RESULT_OK) {

                    try {
                        Bitmap thumbnail = MediaStore.Images.Media.getBitmap
                                (getContentResolver(), imageUri);
                        imageurl = getRealPathFromURI(imageUri);
                    } catch (Exception e) {
                        Log.e(EXCEPTION, e.getMessage());
                    }
            }
            String hodApp = HODApps.OCR_DOCUMENT;
            Map<String, Object> params = new HashMap<>();
            params.put("file", imageurl);
            params.put("mode", "document_photo");
            hodClient.PostRequest(params, hodApp, HODClient.REQ_MODE.ASYNC);
        }

        if (requestCode == 2 && resultCode == RESULT_FIRST_USER) {

            yearsMappedToObjectYearsMap = (HashMap<String, AnyYear> ) data.getSerializableExtra("yearsMappedToObjectYearsMap");

            Log.i("MAP is updated and ", "returned from EditActivity");

        }
    }

    private void handlePurchaseResponse(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1001) {
            int responseCode = data.getIntExtra("RESPONSE_CODE", 0);
            String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
            String dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");

            if (resultCode == RESULT_OK) {
                try {
                    JSONObject jo = new JSONObject(purchaseData);
                    String sku = jo.getString("productId");
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,
                            R.style.Theme_AppCompat_Light_Dialog)
                            .setTitle(INFORMATION)
                            .setMessage("You have bought the " + sku + ". Excellent choice, " +
                                    "adventurer!");
                    AlertDialog alert1;
                    builder.setPositiveButton(OK,
                            new OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    alert1 = builder.create();
                    alert1.show();
                } catch (JSONException e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,
                            R.style.Theme_AppCompat_Light_Dialog)
                            .setTitle(INFORMATION)
                            .setMessage("Failed to parse purchase data.");
                    AlertDialog alert1;
                    builder.setPositiveButton(OK,
                            new OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    alert1 = builder.create();
                    alert1.show();
                    Log.e("Exception purchase data",e.getMessage());
                }

            }
        }
    }

    @SuppressWarnings("StringConcatenationInLoop")
    @Override
    public void requestCompletedWithContent(String response) {
        Log.i("SUCCESS!", response);

        //response = "text Greenpoint\nCoffeehouse\n195 Frann Street\n1 718-349-6635\nServer:
        // Keith 09/03/2009\nTable 9/1 1:53 PM\nGuests: 3 20021\nEggs Avocado 9.00\nGrill Chix
        // Sandnich 8.00\nGrill Chix Sandwich 9.00\nGreen SaIad\nIced Blood 2.00\n4 Items\nSub
        // Total 28.00\nTax 2.18\nTotal 30•20\nPECEIS;com,left:1,top:11,width:299,height:357";
        StringBuilder textFound = new StringBuilder();
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
                            textFound.append(texts.getString("text"));
                        }
                    }
                }
            }
        } catch (JSONException e) {
            Log.e("JSONException",e.getMessage());
        }
        mainUtil.setExpenseFieldsFromCameraExtractedText(textFound.toString(), this);

        if (mainUtil.isExpenseFound() && mainUtil.isDateFound() && mainUtil.isDescFound()) {
            showDialogsWithDataFoundFromCapture(mainUtil.getExpense(), mainUtil.getDescription(),mainUtil.getDate());
            writeToFile(mainUtil.getExpense(), mainUtil.getDescription(),mainUtil.getDate());
        }
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
            Log.e("Exception",ex.getMessage());
        }
        Log.i("response",response);

    }

    @Override
    public void onErrorOccurred(String errorMessage) {
        /* Not used */
    }

    protected void showInputDialog() {
        //this method handles the popup window to add/remove descriptions in the spinner.
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this,
                R.style.Theme_AppCompat_Light_Dialog);
        alertDialogBuilder.setView(promptView);

        addedDescriptionField = new EditText(this);
        addedDescriptionField = promptView.findViewById(R.id.edittext);
        InputFilter[] filterArray = new InputFilter[1];//this is to set a limit
        filterArray[0] = new InputFilter.LengthFilter(14);//on characters entered by user
        addedDescriptionField.setFilters(filterArray);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton(ADD, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        descriptionAddedByUser = addedDescriptionField.getText().toString();
                        if (itemsAddedByUser.contains(descriptionAddedByUser)) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity
                                    .this, R.style.Theme_AppCompat_Light_Dialog)
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
                                            (MainActivity.this, R.style.Theme_AppCompat_Light_Dialog)
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

                                else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder
                                            (MainActivity.this, R.style.Theme_AppCompat_Light_Dialog)
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

    @TargetApi(19)
    private void writeDescriptionsToFile() {

        try (PrintWriter out = new PrintWriter(openFileOutput(DESCRIPTIONS_FILE, MODE_APPEND));){

            String descriptionItem = descriptionAddedByUser;
            out.append(descriptionItem);
            out.write("\r\n");

            Toast.makeText(this, descriptionItem + " added in your list", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(this, EXCEPTION + e.toString(), Toast.LENGTH_LONG).show();
            Log.e(EXCEPTION, e.getMessage());
        }
    }

    @TargetApi(19)
    private void deleteDescriptionFromFile() {
        String checkedDescription = addedDescriptionField.getText().toString();

        try (FileInputStream fstream = this.openFileInput(DESCRIPTIONS_FILE);
             BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
             PrintWriter out = new PrintWriter(openFileOutput(DESCRIPTIONS_FILE, MODE_PRIVATE));){

            String strLine;
            StringBuilder fileContent = new StringBuilder();
            while ((strLine = br.readLine()) != null) {
                if (!strLine.equals(checkedDescription)) {
                    fileContent.append(strLine);
                    fileContent.append("\r\n");
                }
            }

            out.write(fileContent.toString());
            Toast.makeText(this, checkedDescription + " removed from your list", Toast
                    .LENGTH_LONG).show();
        } catch (Exception e) {            //Catch exception if any
            Log.e(EXCEPTION, e.getMessage());
        }

    }

    public void showDialogOnButtonClick() {
        dateButton = (Button) findViewById(R.id.dateButton);
        dateButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(SELECT_DATE_DIALOG_ID);
                    }
                }
        );

        endDateBtn = (Button)findViewById(R.id.endDateButton);
        endDateBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(SELECT_END_DATE_DIALOG_ID);
                    }
                }
        );
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == SELECT_DATE_DIALOG_ID)
            return new DatePickerDialog(this, dpickerListener, yearX, monthX, dayX);

        if (id == SELECT_END_DATE_DIALOG_ID)
            return new DatePickerDialog(this, dpickerListenerForEndDate, yearX, monthX, dayX);
        return null;
    }

    private final DatePickerDialog.OnDateSetListener dpickerListener
            = new DatePickerDialog.OnDateSetListener() {
        @Override

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            yearX = year;
            monthX = monthOfYear;
            dayX = dayOfMonth;

            Calendar datePickerCalendar = Calendar.getInstance();
            datePickerCalendar.set(yearX, monthX, dayX);

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            String strDate = format.format(datePickerCalendar.getTime());

            Toast.makeText(MainActivity.this, strDate, Toast.LENGTH_LONG).show();
            TextView dateTextField = (TextView) findViewById(R.id.dateText);
            dateTextField.setText(strDate);

        }
    };

    private final DatePickerDialog.OnDateSetListener dpickerListenerForEndDate
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int yearToEnd = year;
            int monthToEnd = monthOfYear;
            int dayToEnd = dayOfMonth;

            datesTheRepeatingExpenseOccurs = (ArrayList) mainUtil.populateDatesTheRepeatingExpenseOccursList(yearToEnd, monthToEnd, dayToEnd, yearX, monthX);
        }
    };



    protected void showNumberPickerDialogOnButtonClick() {

        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View npView = inflater.inflate(R.layout.number_picker_dialog_layout, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this,
                R.style.Theme_AppCompat_Light_Dialog);
        //number pickers
        numberPicker1 = npView.findViewById(R.id.numberPicker1);
        numberPicker1.setMaxValue(31);
        numberPicker1.setMinValue(1);
        numberPicker1.setWrapSelectorWheel(true);

        if ((valueFromNumPicker1 != 0)) {
            numberPicker1.setValue(valueFromNumPicker1);
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
                                processDateCircle();

                                // store in preferences the boolean to set the circle and the
                                // values from the number pickers.
                                SharedPreferences prefers = getSharedPreferences(PREFERENCES,
                                        Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefers.edit();
                                editor.putBoolean(ISPAYMENTCIRCLE, true);
                                editor.putInt(VALUEFROMNUMPICKER1, valueFromNumPicker1);
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

                                // process again the balance
                                String currentMonth = String.format(Locale.UK, "%tB", calendar);
                                balance = mainUtil.processBalance(incomeField, yearsMappedToObjectYearsMap,isPaymentCircleSet, currentMonth, yearX);
                                DecimalFormat df = new DecimalFormat("#.0");
                                balanceLabel.setText(BALANCE + df.format(balance));
                                // redraw the graph with the balance
                                showStackedBar();

                                // store in preferences the boolean to set the circle and the
                                // values from the number pickers.
                                SharedPreferences prefers = getSharedPreferences(PREFERENCES,
                                        Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefers.edit();
                                editor.putInt(VALUEFROMNUMPICKER1, valueFromNumPicker1);
                                editor.putBoolean(ISPAYMENTCIRCLE, false);
                                editor.apply();
                                dialog.cancel();
                            }
                        });
        alertDialogBuilder.create();
        alertDialogBuilder.show();

    }

    private void processDateCircle() {

        Log.i("First Date: ", String.valueOf(valueFromNumPicker1));

        String currentMonth = String.format(Locale.UK, "%tB", calendar);
        int year = calendar.get(Calendar.YEAR);
        AnyYear currentYear = yearsMappedToObjectYearsMap.get(String.valueOf(year));

        if (currentYear != null) {

            try {
                /*Set to zero the expenses sums of the current and next month*/
                int monthInt = calendar.get(Calendar.MONTH) + 1;
                util.resetExpenseOfCurrentMonth(monthInt - 1, currentYear.getYear());
                util.resetExpenseOfCurrentMonth(monthInt, currentYear.getYear());
                util.resetExpenseOfCurrentMonth(monthInt + 1, currentYear.getYear());
                util.readTheFileToRecalculateMonthExpensesDueToIncomeChangeCircle(valueFromNumPicker1, currentMonth, currentYear.getYear());
                //the user has clicked SET on the dialog
                isPaymentCircleSet = true;
            } catch (ParseException e) {
                Log.e(PARSE_EXCEPTION, e.getMessage());
            }

            /* Here if the current day is greater than the day set as salary day by the user then i add one month to the current month so that the balance
            * will be calculated and shown based on the monthSum of the next month */
            int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
            if (currentDay > valueFromNumPicker1) {
                int month = calendar.get(Calendar.MONTH);
                month++;
                DateFormatSymbols dfs = new DateFormatSymbols();
                String[] months = dfs.getMonths();
                if (month >= 0 && month <= 11 ) {
                    currentMonth = months[month];
                }
            }
            // process again the balance
            balance = mainUtil.processBalance(incomeField, yearsMappedToObjectYearsMap,isPaymentCircleSet, currentMonth, yearX);
            DecimalFormat df = new DecimalFormat("#.0");
            balanceLabel.setText(BALANCE + df.format(balance));
        }
        // redraw the graph with the balance
        showStackedBar();
    }

    private void showStackedBar() {
        //this is for the stacked bar appeared after the balance
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager
                .LayoutParams.FLAG_FULLSCREEN);

        HorizontalBarChart mChart = new HorizontalBarChart(this);
        FrameLayout stackedBarLayout = (FrameLayout) findViewById(R.id.stackedBar);
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
        yValues.add(new BarEntry(new float[]{(float) mainUtil.getBalance(), (float) (mainUtil.getIncomeDouble() - mainUtil.getBalance())},
                0));

        BarDataSet set = new BarDataSet(yValues, "");
        set.setBarSpacePercent(40f);//the height of the bar
        set.setDrawValues(false);//hide the y values appeared inside the bar

        if (mainUtil.getBalance() < 0) {

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

    @TargetApi(19)
    public void writeToFile(String amountText, String descriptionText, String dateString) {

        try (PrintWriter out = new PrintWriter(openFileOutput(EXPENSES_FILE, MODE_APPEND));
             BufferedReader br = new BufferedReader(new FileReader("/data/data/uk.co.irokottaki.moneycontrol/files/expenses.txt"));){

            String formatStr = "%-8s%-15s%-10s";

            /*That means the first line of the file is empty so write the header*/
            String line = br.readLine();
            if (line == null){
                out.append(String.format(formatStr, "Amount", "Description", "Date"));
                out.write("\r\n");//write two new lines
                out.write("\r\n");
            }

            out.append(String.format(formatStr, amountText, descriptionText, dateString));
            //write the expense
            out.write("\r\n");//write a new line
            Toast.makeText(this, "The expense is saved in the file.", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, EXCEPTION + e.toString(), Toast.LENGTH_LONG).show();
            Log.e(EXCEPTION, e.getMessage());
        }
        /*Need to call again here the readTheFile method in order to take into account the new expense that was added*/
        /*if the map is null that means there is no expense for the current year. the user is adding the first expense of the year */
        if (yearsMappedToObjectYearsMap.get(String.valueOf(yearX))==null){
            yearsMappedToObjectYearsMap = (HashMap<String, AnyYear>) util.readTheFile();
        }
        else {
            util.updateMapWithNewExpense(amountText, descriptionText, dateString, yearX, yearsMappedToObjectYearsMap);
        }
        if (budgetWarningEnabled) {
            //since the expense is written in the file call the budget method if budget warning is enabled
            String currentMonth = String.format(Locale.UK, "%tB", calendar);
            double percentWarning = mainUtil.checkBudgetWarning(yearsMappedToObjectYearsMap, currentMonth, yearX);
            getDialogForBudgetWarning(percentWarning, this);
        }

    }

    public static void getDialogForBudgetWarning(Double percentWarning, Context mContext) {
        if (percentWarning > 0.75 && percentWarning < 0.85) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.Theme_AppCompat_Light_Dialog)
                    .setTitle(BUDGET_WARNING)
                    .setMessage("You have reached the 80% of your budget as set in Budget Control" +
                            ". \n\n" +REMINDER_TO_TURN_OFF_NOTIFICATIONS);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.Theme_AppCompat_Light_Dialog)
                    .setTitle(BUDGET_WARNING)
                    .setMessage("You have reached the 90% of your budget as set in Budget Control" +
                            ". \n\n" +REMINDER_TO_TURN_OFF_NOTIFICATIONS);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.Theme_AppCompat_Light_Dialog)
                    .setTitle(BUDGET_WARNING)
                    .setMessage("You have exceeded the 100% of your budget as set in Budget " +
                            "Control. Too many expenses this month? You can increase the budget " +
                            "warning " +
                            "amount.\n\n" +REMINDER_TO_TURN_OFF_NOTIFICATIONS);
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

    @TargetApi(19)
    private void exportExpensesFileToSdCard() {
        verifyStoragePermissions(MainActivity.this);
        File root2 = new File(getString(R.string.PATH_TO_EXPORT_EXPENSES));
        //get the directory of the file stored

        File dirAndFolder = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DOWNLOADS), "");

        if (!dirAndFolder.exists()) {
            dirAndFolder.mkdir();
        }
        File file = new File(dirAndFolder, "expenses" + ".txt");
        try (FileOutputStream f = new FileOutputStream(file);
             InputStream inputStream = new FileInputStream(root2);){

            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = inputStream.read(buffer)) > 0) {
                f.write(buffer, 0, len1);
            }
        } catch (Exception e) {
            Log.e("Exception ", e.getMessage());
        }
    }

    @TargetApi(19)
    public void readFileFromSdCard() {
        verifyStoragePermissions(MainActivity.this);
        String amount = "";
        String desc = "";
        String date = "";
        String formatStr = "%-7s %-15s %-10s";
        StringBuilder myData = new StringBuilder();
        File myExternalFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath
                () + "/Download/", EXPENSES_FILE);

        try (FileInputStream fis = new FileInputStream(myExternalFile);
             DataInputStream in = new DataInputStream(fis);
             BufferedReader br = new BufferedReader(new InputStreamReader(in));){

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
                            int index = fileLineArrayOneSpace.lastIndexOf(' ');
                            desc = fileLineArrayOneSpace.substring(aFileLineArray.indexOf(' '),
                                    index).trim();
                            date = fileLineArrayOneSpace.substring(fileLineArrayOneSpace
                                    .lastIndexOf(' '), fileLineArrayOneSpace.length()).trim();
                            myData.append(String.format(formatStr, amount, desc, date + "\n"));
                        }
                    } //this is the case where the file has lines of length<34, probably 33. do
                    // the same as above
                    else if (strLine.length() <= 34 && !strLine.contains(AMOUNT)) {

                        myData.append(strLine);
                        myData.append("\n");
                    }
                }
            }// end of While
        } catch (IOException e) {
            Log.e(IOEXCEPTION,e.getMessage());
        }

        writeFileFromSDCardToExpensesFile(myData);
    }

    @TargetApi(19)
    private void writeFileFromSDCardToExpensesFile(StringBuilder myData) {
        //if the file in the Download folder is empty or does not exist
        if (myData.toString().equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_Light_Dialog)
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
            File myInternalFile = new File(directory, EXPENSES_FILE);

            try (FileOutputStream fos = new FileOutputStream(myInternalFile);){

                fos.write(myData.toString().getBytes());
                Toast.makeText(MainActivity.this, "You imported the file", Toast.LENGTH_SHORT)
                        .show();
            } catch (IOException e) {
                Log.e(IOEXCEPTION, e.getMessage());
            }
        }
        /* update the map with the new data */
        yearsMappedToObjectYearsMap = (HashMap<String, AnyYear>) util.readTheFile();
    }

    private void showDialogsWithDataFoundFromCapture(final String expenseAmount, final String
            description, final String date) {

        if (expenseFound && dateFound && descFound) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog)
                    .setTitle("Expense, Date and Description Found")
                    .setMessage("Expense Amount: " + expenseAmount + "\n" +
                            "Description: " + description + "\n" +
                            "Date: " + date + "\n" + "\n" + "\n" +
                            "Is that correct?");
            AlertDialog alert1;

            builder.setPositiveButton("Yes, Set them and save to file the Expense",
                    new OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            setFields(expenseAmount, date, description);
                            //write the expenses to the file
                            writeToFile(expenseAmount, description, date);
                        }
                    });

            builder.setNegativeButton("No, I want to edit one of the fields", new OnClickListener
                    () {
                public void onClick(DialogInterface dialog, int id) {
                    setFields(expenseAmount, date, description);
                    dialog.cancel();
                }
            });
            alert1 = builder.create();
            alert1.show();
        }

        // something was not detected from the receipt
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog)
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
                    if (!expenseAmount.equals("")) {
                        expensesField.setText(expenseAmount);
                    }

                    if (!description.equals("")) {
                        int spinnerPosition = spinnerAdapter.getPosition(description);
                        descriptionsItem.setSelection(spinnerPosition + 1);
                    }

                    if (!date.equals("")) {
                        dateText.setText(date);
                    }
                    dialog.cancel();
                }
            });
            alert1 = builder.create();
            alert1.show();
        }
    }

    private void setFields(String expenseAmount, String date, String description) {
        expensesField.setText(expenseAmount);
        dateText.setText(date);

        if (description != null) {
            int spinnerPosition = spinnerAdapter.getPosition(description);
            descriptionsItem.setSelection(spinnerPosition + 1);
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

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_Light_Dialog)
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
            Log.e("Exception" , e.getMessage());
        }
    }

    @TargetApi(19)
    private static void addContent(Document document) throws DocumentException {

        Paragraph preface = new Paragraph();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("/data/data/uk.co.irokottaki" +
                ".moneycontrol/files/expenses.txt")));){

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
                        table.addCell(new Phrase(lineTrimmed.substring(0, lineTrimmed.indexOf(' ' +
                                "")), f1));
                        table.addCell(new Phrase(lineTrimmed.substring(lineTrimmed.indexOf(' '),
                                lineTrimmed.lastIndexOf(' ')).trim(), f1));
                        table.addCell(new Phrase(lineTrimmed.substring(lineTrimmed.lastIndexOf(' ' +
                                ""), lineTrimmed.length()).trim(), f1));

                    } else {
                        // add all the columns of the file amount description and date in a table
                        // to be formatted correctly
                        table.addCell(line.substring(0, line.indexOf(' ')));
                        table.addCell(line.substring(line.indexOf(' '), line.lastIndexOf(' '))
                                .trim());
                        table.addCell(line.substring(line.lastIndexOf(' '), line.length()).trim());
                    }
                    document.add(table);
                }

                addEmptyLine(preface);

            }// end of while

        } catch (FileNotFoundException e) {
            Log.e("FileNotFoundException", e.getMessage());
        } catch (IOException e) {
            Log.e(IOEXCEPTION, e.getMessage());
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
        if (accessToken == null) return;
        new UserAccountTask(DropboxClient.getClient(accessToken), new UserAccountTask
                .TaskDelegate() {
            @Override
            public void onAccountReceived(FullAccount account) {
                //Print account's info
                Log.d("User", account.getEmail());
                Log.d("User", account.getName().getDisplayName());
                Log.d("User", account.getAccountType().name());
            }

            @Override
            public void onError() {
                Log.d("User", "Error receiving account details.");
                invalidToken = true;
                Log.i("invalidToken", String.valueOf(invalidToken));
            }
        }).execute();
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int columnIndex = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(columnIndex);
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
