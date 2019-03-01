package uk.co.irokottaki.moneycontrol.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import uk.co.irokottaki.moneycontrol.R;
import uk.co.irokottaki.moneycontrol.model.AnyYear;
import uk.co.irokottaki.moneycontrol.utils.ChartsUtil;
import uk.co.irokottaki.moneycontrol.utils.CustomAdapter;
import uk.co.irokottaki.moneycontrol.utils.MultiMap;
import uk.co.irokottaki.moneycontrol.utils.Utils;

import static uk.co.irokottaki.moneycontrol.utils.Constants.CLOSE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.EDIT_AN_EXPENSE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.EMPTY_FIELD;
import static uk.co.irokottaki.moneycontrol.utils.Constants.EMPTY_SPACE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.EXPENSES_FILE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.INFORMATION;
import static uk.co.irokottaki.moneycontrol.utils.Constants.OK;

public class EditActivity extends AppCompatActivity {

    private EditText descField;
    private EditText amountField;
    private ArrayList<String> allTheLinesInFile = new ArrayList<>();
    /*private static final  String TAG = "Millenial Media";
    private InlineAd inlineAd;*/
    private HashMap<String, AnyYear> yearsMappedToObjectYearsMap;
    private ChartsUtil util;
    private TextView resultsLabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setTitle(EDIT_AN_EXPENSE);

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
                adsLayout = (FrameLayout) findViewById(R.id.adEditView);
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

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.editView);

        Utils utils = new Utils(this);
        utils.setBackgroundAndAdjustLayout(layout, EditActivity.this);//this is to change the background color of the activity when user changes it from settings

        util = new ChartsUtil(this);

        //Image Button for information
        ImageButton infoEditButton = (ImageButton) findViewById(R.id.infoEditButton);

        //Button for the search
        Button searchButton = (Button) findViewById(R.id.searchButton);

        //EditText for the search fields
        descField = new EditText(this);
        amountField = new EditText(this);
        descField = (EditText) findViewById(R.id.searchByDescText);
        amountField = (EditText) findViewById(R.id.searchByAmountText);

        /* Results label. Hide it on creating the activity*/
        resultsLabel = new TextView(this);
        resultsLabel = (TextView) findViewById(R.id.resultsLabel);
        resultsLabel.setVisibility(View.INVISIBLE);

        infoEditButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showAlertOnEmptyFields(INFORMATION, "You can search for an expense by its description, its " +
                        "amount or both. When results will be displayed " +
                        "you can press the Edit button that will show you the selected expense." +
                        ".\n 1.Edit the expense by changing amount description or date," +
                        " and press save.\n 2.To " +
                        "delete an expense, just press the delete button." +
                        "If you search with both description and amount you will " +
                        "get refined results.", CLOSE);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                readTheLines();

                /* if both description and amount fields are empty show a message to the user */
                if (descField.getText().toString().equals("") && amountField.getText().toString().equals("")) {

                    showAlertOnEmptyFields(EMPTY_FIELD + EMPTY_SPACE, "Description and amount fields are empty. Fill at least one and search again.", OK);
                }

                if(descField.getText().toString().length() > 0 || amountField.getText().toString().length() > 0) {

                    executeSearch();
                }

            }
        });

    }//end of onCreate method

    @TargetApi(19)
    private void readTheLines() {

        try (InputStream inputStream = openFileInput(EXPENSES_FILE);
             Scanner in = new Scanner(inputStream);){

            int lineIndex = 0;
            allTheLinesInFile = new ArrayList<>();
            while (in.hasNextLine()) {
                String line = in.nextLine();
                if (++lineIndex > 2 && !line.equals("")) {

                    allTheLinesInFile.add(line.trim().replaceAll("\\s+", " "));
                }
            }
        } catch (IOException e) {
            Log.i("EDITACTIVITY", "File not found");

        }
    }

    @SuppressWarnings("StringConcatenationInLoop")
    private void executeSearch() {

        String linefound = null;
        String descFound = null;
        String amountPart = null;

        MultiMap descriptionFoundMap = new MultiMap();
        MultiMap amountFoundMap = new MultiMap();
        ArrayList resultsList = new ArrayList<>();

        for (int i = 0; i < allTheLinesInFile.size(); i++) {
                linefound = allTheLinesInFile.get(i);
                int index = linefound.lastIndexOf(' ');
                descFound = linefound.substring(linefound.indexOf(' '), index);
                descriptionFoundMap.put(descFound.trim(), linefound);

                amountPart = linefound.substring(0, linefound.indexOf(' '));
                amountFoundMap.put(amountPart, linefound);
        }
            if (linefound == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this,
                        R.style.Theme_AppCompat_Light_Dialog)
                        .setTitle("No expenses found")
                        .setMessage("It seems your expenses file is empty! Please supply expenses" +
                                " and try again.");
                builder.setPositiveButton(OK,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert1 = builder.create();
                alert1.show();

            } else if (!linefound.equals("")) {
                /* Here the user has added both amount and description so produce refined results */
                if (amountFoundMap.containsKey(amountField.getText().toString()) && descriptionFoundMap.containsKey(descField.getText().toString())) {

                    descriptionFoundMap.get(descField.getText().toString()).retainAll(amountFoundMap.get(amountField.getText().toString()));
                    resultsList.addAll(descriptionFoundMap.get(descField.getText().toString()));
                }
                /* if the user has added only amount then produce results based on amountFoundMap */
                else if (amountFoundMap.containsKey(amountField.getText().toString()) && descField.getText().toString().length() == 0) {

                    resultsList.addAll(amountFoundMap.get(amountField.getText().toString()));
                }
                /* if the user has added only description then produce results based on descriptionFoundMap */
                else if (descriptionFoundMap.containsKey(descField.getText().toString()) && amountField.getText().toString().length() == 0) {

                    resultsList.addAll(descriptionFoundMap.get(descField.getText().toString()));
                }
            }

            /* Finally if the results list has elements that means there are results then show them */
            if (!resultsList.isEmpty()) {
                resultsLabel.setVisibility(View.VISIBLE);
                CustomAdapter adapter = new CustomAdapter(resultsList, this);
                ListView lView = (ListView) findViewById(R.id.listView);
                lView.setAdapter(adapter);
                yearsMappedToObjectYearsMap = (HashMap<String, AnyYear>) adapter.getYearsMappedToObjectYearsMap();
            }

            else {
                showAlertOnEmptyFields("No expenses", "No expense found! Try again with different description/amount.", OK);
            }
    }

    private void showAlertOnEmptyFields(String title, String message, String buttonText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this, R.style.Theme_AppCompat_Light_Dialog)
                .setTitle(title)
                .setMessage(message);
        AlertDialog alert1;
        builder.setPositiveButton(buttonText,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        alert1 = builder.create();
        alert1.show();
    }

    @Override
    public void onBackPressed() {
        // send the updated map back to MainActivity when user presses the back button
        //here i need to send the updated map that holds as key the year and as value the anyYear object
        if (yearsMappedToObjectYearsMap == null){
            yearsMappedToObjectYearsMap = (HashMap<String, AnyYear>) util.readTheFile();
        }
        Intent intent = new Intent();
        intent.putExtra("yearsMappedToObjectYearsMap", yearsMappedToObjectYearsMap);
        setResult(1, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        /* send the updated map back to MainActivity when user presses the back button on the top of activity and avoid the call to onCreate of mainActivity that
        will trigger the readTheFile again*/
        int id = item.getItemId();// This is added to avoid the call on the onCreate method of MainActivity because it will read again the file and iterate the map
        if (id == android.R.id.home) {//here i need to send the updated map that holds as key the year and as value the anyYear object
            if (yearsMappedToObjectYearsMap == null) {
                yearsMappedToObjectYearsMap = (HashMap<String, AnyYear>) util.readTheFile();
            }
            Intent intent = new Intent();
            intent.putExtra("yearsMappedToObjectYearsMap", yearsMappedToObjectYearsMap);
            setResult(1, intent);
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
