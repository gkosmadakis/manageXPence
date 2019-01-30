package uk.co.irokottaki.moneycontrol.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.android.vending.billing.IInAppBillingService;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import uk.co.irokottaki.moneycontrol.IabHelper;
import uk.co.irokottaki.moneycontrol.IabResult;
import uk.co.irokottaki.moneycontrol.Inventory;
import uk.co.irokottaki.moneycontrol.Purchase;
import uk.co.irokottaki.moneycontrol.R;
import uk.co.irokottaki.moneycontrol.utils.Utils;

import static uk.co.irokottaki.moneycontrol.utils.Constants.*;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
// after this point Is MY CODE
public class SettingsActivity extends PreferenceActivity {

    private static final int PICK_IMAGE = 1;
    View view;
    private static ContentResolver cr = null;
    private static Context context;
    private static ListView lv;
    private static SharedPreferences prefs;
    private static SettingsActivity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new
                MyPreferenceFragment()).commit();

        SettingsActivity.context = getApplicationContext();

        activity = SettingsActivity.this;
        lv = getListView();
        cr = getContentResolver();
        setContentView(R.layout.activity_settings);

        RelativeLayout layout = findViewById(R.id.settingsView);

        prefs = getSharedPreferences(PREFERENCES, SettingsActivity.MODE_PRIVATE);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);//initialize
        // preference manager

        Utils utils = new Utils(this);
        utils.setBackgroundAndAdjustLayout(layout, SettingsActivity.this);

    }//end of onCreate


    private static Context getAppContext() {
        return SettingsActivity.context;
    }

    private static SettingsActivity getSettingsActivity() {
        return activity;
    }

    public static ListView getTheListView() {
        return SettingsActivity.lv;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (!MyPreferenceFragment.getmHelper().handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            Log.i("IN APP BILLING", "onActivityResult handled by IABUtil.");
        }
    }


    public static class MyPreferenceFragment extends PreferenceFragment {
        ListPreference list = null;
        static Bitmap bitmap;
        private static Drawable bitmapDrawable;
        boolean userIsPro;

        IInAppBillingService mService;
        Bundle querySkus;
        static final String SKU = "android.test.purchased";
        static IabHelper mHelper;
        static final String BASE_64_ENCODED_PUBLIC_KEY =
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqcYpXYA3pWCTMjOYJNNC70rNhXmbwxI5i4sGCtmZWN+eVFvrvtBtlwm8Wxwab8wf4CyLUxthccmgSd2Wmb6lHYVHG9/F7VSn+u3f9tnu8x+Oh30fyiSr4Wdesz0yfTwflVipA4wNwcEjxJoO0t8CCEyswQZcAzLAMzkodlMVwcdWx0kJ39qJxxuT8LWFlqwDpUSlLm6sPr+XmbD/vhfmd1h+qNQTteVte2Q5vVLSAk1/hCsqLCzrDp0BJ30w4f0nzEBn3g/7KIn3KQQp+6JE+xJanavahcvAU//PTDmy8t/bYxiFtn8kquBCL9xcHa/2Nw8PTEhzeWx3hCRUAugruwIDAQAB";
        private static final String TAG = "In App blling";
        IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener;


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

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);


            SharedPreferences sharedPrefs = getTheSharedPrefs();
            userIsPro = sharedPrefs.getBoolean(USERISPRO, false);//retrieve the boolean for the
            // pro user

            mHelper = new IabHelper(getSettingsActivity(), BASE_64_ENCODED_PUBLIC_KEY);
            mHelper.enableDebugLogging(true, TAG);

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
                    Log.d(TAG, "Setup successful. Querying inventory.");
                }
            });

            run(mServiceConn);

            //Query a purchase
            final ArrayList<String> skuList = new ArrayList<>();
            skuList.add(SKU);
            querySkus = new Bundle();
            querySkus.putStringArrayList("ITEM_ID_LIST", skuList);

            final IabHelper.QueryInventoryFinishedListener mQueryFinishedListener = new IabHelper
                    .QueryInventoryFinishedListener() {
                @Override
                public void onQueryInventoryFinished(IabResult result, Inventory inv) {
                    if (result.isFailure()) {
                        Log.d(TAG, "Querying Inventory Failed: " + result);
                        return;
                    }
                    Log.d(TAG, "Title: " + inv.getSkuDetails(SKU).getTitle());
                    Log.d(TAG, "Description: " + inv.getSkuDetails(SKU).getDescription());
                    Log.d(TAG, "Price = " + inv.getSkuDetails(SKU).getPrice());
                }
            };

            list = (ListPreference) findPreference(BACKGROUND_COLOR);
            list.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    int index = list.findIndexOfValue(newValue.toString());
                    if (index == 3) {

                        mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
                            public void onIabPurchaseFinished(IabResult result, Purchase purchase) {

                                if (result.isFailure()) {
                                    Toast.makeText(getAppContext(), "Purchase:Error occured " +
                                            "during purchase " + result, Toast.LENGTH_SHORT).show();

                                } else if (purchase.getSku().equals(SKU)) {
                                    consumeItem();
                                }
                            }
                        };

                        //check if the user has purchased the PRO version
                        //check if user is Pro by retrieving it from the Shared Preferences
                        //the user has PRO version so proceed to change color

                        askPermissions();
                        try {

                            Intent intent = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(Intent.createChooser(intent, "Select Picture")
                                    , PICK_IMAGE);

                        } catch (Exception e) {

                            Log.e(e.getClass().getName(), e.getMessage(), e);
                        }
                        // if the userIsPro is false the user has not purchased the PRO version
                        //so prompt to buy PRO version

                    }
                    return true;
                }
            });

        }//end of onCreate

        private void askPermissions() {

            if (ContextCompat.checkSelfPermission(SettingsActivity.getAppContext(), Manifest
                    .permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getSettingsActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                }
                ActivityCompat.requestPermissions(getSettingsActivity(), new String[]{Manifest
                                .permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        }

        private static void run(ServiceConnection serviceConn) {
            // Binding to IInAppBillingService
            Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
            serviceIntent.setPackage("com.android.vending");
            getAppContext().bindService(serviceIntent, serviceConn, Context.BIND_AUTO_CREATE);
        }

        static IabHelper getmHelper() {
            return mHelper;
        }

        void consumeItem() {

            mHelper.queryInventoryAsync(mReceivedInventoryListener);
        }

        final IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener = new IabHelper
                .QueryInventoryFinishedListener() {
            public void onQueryInventoryFinished(IabResult result, Inventory inventory) {

                if (result.isFailure()) {
                    Toast.makeText(getAppContext(), "Error occured during purchase " + result,
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
                    Toast.makeText(getAppContext(), "Congratulations you upgraded to PRO", Toast
                            .LENGTH_SHORT).show();
                    SharedPreferences sp = getTheSharedPrefs();
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean(USERISPRO, true);//store the boolean userIsPro in
                    // Preferences, true since the user just purchased PRO
                    editor.putBoolean(ADSDISABLED, true);//store the boolean for the ads in
                    // Preferences, true since the user is PRO
                    editor.apply();
                } else {
                    Toast.makeText(getAppContext(), "Error occured during purchase " + result,
                            Toast.LENGTH_SHORT).show();
                }
            }
        };

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

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


                if (resultCode == RESULT_OK) {
                    try {
                        JSONObject jo = new JSONObject(purchaseData);
                        String productId = jo.getString("productId");
                         new AlertDialog.Builder(getAppContext(),
                                 R.style.Theme_AppCompat_Light_Dialog)
                                .setTitle(INFORMATION)
                                .setMessage("You have bought the " + productId + ". Excellent choice, " +
                                        "adventurer!");
                    } catch (JSONException e) {
                        new AlertDialog.Builder(getAppContext(),
                                R.style.Theme_AppCompat_Light_Dialog)
                                .setTitle(INFORMATION)
                                .setMessage("Failed to parse purchase data.");
                        Log.e("JSONException", e.getMessage());
                    }
                }
            }

            if (resultCode == RESULT_OK) {

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getTheContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();

                final BitmapFactory.Options options = new BitmapFactory.Options();
                BitmapFactory.decodeFile(filePath, options);

                SharedPreferences sp = getTheSharedPrefs();
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(GALLERY_IMAGE, filePath);
                editor.apply();

            }

        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            if (mHelper != null) {
                mHelper.dispose();

            }
            if (mService != null) {
                getAppContext().unbindService(mServiceConn);
            }
        }

        private static SharedPreferences getTheSharedPrefs() {
            return SettingsActivity.prefs;
        }

        private static ContentResolver getTheContentResolver() {
            return cr;
        }

        public static Drawable getBitmapDrawable() {
            return MyPreferenceFragment.bitmapDrawable;
        }

    }// end of static Fragment

    // Old way of storing the images picked from a user gallery. This goes to change background
    // method
    //String encoded= prefers.getString("GalleryImage", "#00000000");
    //convert Base64 back to bitmap object
    //byte[] imageAsBytes = Base64.decode(encoded.getBytes(), Base64.DEFAULT);
    //Bitmap bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length,options);
    // And this goes on the onActivityResult method.
    /*bitmap = BitmapFactory.decodeStream(SettingsActivity.getAppContext().getContentResolver()
    .openInputStream(targetUri));

                    bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
                    SettingsActivity view = new SettingsActivity();
                    int sdk = android.os.Build.VERSION.SDK_INT;
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        SettingsActivity.getTheListView().setBackgroundDrawable(bitmapDrawable);
                    }
                    else {
                        SettingsActivity.getTheListView().setBackground(bitmapDrawable);
                    }*/
    //store bitmap in SharedPreferences as Base64 string
                   /* ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    String encoded = Base64.encodeToString(b, Base64.DEFAULT);
                    //put the string to preferences
                    SharedPreferences sp = SettingsActivity.getTheSharedPrefs();
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("GalleryImage", encoded);
                    editor.commit();*/
    // END OF MY CODE

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static final Preference.OnPreferenceChangeListener
            sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            } else if (preference instanceof RingtonePreference) {
                // For ringtone preferences, look up the correct display value
                // using RingtoneManager.
                if (TextUtils.isEmpty(stringValue)) {
                    // Empty values correspond to 'silent' (no ringtone).
                    preference.setSummary(R.string.pref_ringtone_silent);

                } else {
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse(stringValue));

                    if (ringtone == null) {
                        // Clear the summary if there was a lookup error.
                        preference.setSummary(null);
                    } else {
                        // Set the summary to reflect the new ringtone display
                        // name.
                        String name = ringtone.getTitle(preference.getContext());
                        preference.setSummary(name);
                    }
                }

            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue(findPreference("example_text"));
            bindPreferenceSummaryToValue(findPreference("example_list"));
        }
    }

    /**
     * This fragment shows notification preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class NotificationPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_notification);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue(findPreference("notifications_new_message_ringtone"));
        }
    }

    /**
     * This fragment shows data and sync preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class DataSyncPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_data_sync);
            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue(findPreference("sync_frequency"));
        }
    }


}
