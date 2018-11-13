package uk.co.irokottaki.moneycontrol.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.Toast;

import com.millennialmedia.InlineAd;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import uk.co.irokottaki.moneycontrol.Utils.MultiMap;
import uk.co.irokottaki.moneycontrol.R;
import uk.co.irokottaki.moneycontrol.Utils.Utils;

import static uk.co.irokottaki.moneycontrol.Utils.Constants.*;

public class EditActivity extends AppCompatActivity {

    private EditText descField;
    private EditText amountField;
    private EditText resultsArea;
    private EditText singleLineEdit;
    private String lineBeforeEdit;
    private StringBuilder lineToEditMultiple;
    private ArrayList<String> singleLine = new ArrayList<>();
    private List<String> lineToEdit = new ArrayList<>();
    private final List<String> linesExtracted = new ArrayList<>();
    private int counter = 0;
    int mLayoutWidth;
    int mLayoutHeight;
    private int iteration = 1;
    private final String TAG = "Millenial Media";
    private InlineAd inlineAd;
    private boolean adsDisabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setTitle(EDIT_AN_EXPENSE);

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
        utils.setBackgroundAndAdjustLayout(layout, EditActivity.this);
        //this is to change the background color of the activity when user changes it from settings

        //Image Button for information
        ImageButton infoEditButton = new ImageButton(this);
        infoEditButton = (ImageButton) findViewById(R.id.infoEditButton);

        //Button for the search by description, search by Amount
        Button searchDescButton = new Button(this);
        searchDescButton = (Button) findViewById(R.id.searchByDescButton);
        Button searchAmountButton = new Button(this);
        searchAmountButton = (Button) findViewById(R.id.searchByAmountButton);

        //EditText for the search fields
        descField = new EditText(this);
        amountField = new EditText(this);
        //EditText for the results area
        resultsArea = new EditText(this);
        resultsArea = (EditText) findViewById(R.id.editLine);
        resultsArea.setScroller(new Scroller(getApplicationContext()));
        resultsArea.setMaxLines(3);//the max lines in results area, after the third are scrolled
        resultsArea.setVerticalScrollBarEnabled(true);
        resultsArea.setMovementMethod(new ScrollingMovementMethod());//this is for scrolling
        resultsArea.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.editLine) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);//this listener is for
                    // scrolling even when i have
                    if(MotionEvent.ACTION_UP == MotionEvent.ACTION_MASK || MotionEvent.ACTION_UP == event.getAction()){
                    //switch (event.getAction() & MotionEvent.ACTION_MASK) {
                       // case MotionEvent.ACTION_UP://the results area inside a scrollview.
                            // Otherwise android:fadeScrollbars="false"
                            v.getParent().requestDisallowInterceptTouchEvent(false);// and
                            // android:scrollbars="vertical" do the scroll
                           // break;
                    }
                }
                return false;
            }
        });

        //EditText for the single line edit
        singleLineEdit = new EditText(this);
        singleLineEdit = (EditText) findViewById(R.id.SingleEditText);
        //this is the line before the user set focus on the singleLineEdit
        lineBeforeEdit = "";

        //Button for the select
        Button selectButton = new Button(this);
        selectButton = (Button) findViewById(R.id.selectButton);

        //Button for the edit
        ImageButton editButton = new ImageButton(this);
        editButton = (ImageButton) findViewById(R.id.editButton);

        //Button for the delete
        ImageButton deleteButton = new ImageButton(this);
        deleteButton = (ImageButton) findViewById(R.id.deleteButton);

        readTheLines();

        infoEditButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showAlertOnEmptyFields(INFORMATION, "You can search for an expense by its description or its " +
                        "amount. When results will be displayed " +
                        "you can press the Select button that can go through all of them" +
                        ".\n 1. To edit an expense tap twice on it," +
                        " edit it and when you're done press the Tick button.\n 2.To " +
                        "delete an expense, after you have pressed " +
                        "the Select button tap once on the expense and then press the " +
                        "Delete button.", CLOSE);
            }
        });

        searchDescButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                executeSearchByDescription();
            }
        });

        searchAmountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                executeSearchByAmount();
            }
        });

        selectButton.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("CollectionAddedToSelf")
            @Override
            public void onClick(View view) {
                if (resultsArea.getText().toString().equals("")) {
                    showAlertOnEmptyFields(EMPTY_RESULTS_FIELD,"Results area is empty! Search first for an expense and " +
                            "try again", OK);
                } else {
                    linesExtracted.removeAll(linesExtracted);
                    selectAlineFromMultiline();
                    int i = 0;
                    for (i = counter; i < iteration; i++) {
                        singleLineEdit.setText(linesExtracted.get(counter++));

                    }

                    iteration++;
                    if (counter == linesExtracted.size()) {
                        counter = 0;
                        iteration = 1;
                    }
                }
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (singleLineEdit.getText().toString().equals("")) {
                    showAlertOnEmptyFields("Empty Line to Edit", "The line to edit an expense is empty. Please press " +
                            "select button first", OK);
                } else {
                    processEdit();
                }
            }
        });

        singleLineEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    singleLineEdit = (EditText) findViewById(R.id.SingleEditText);
                    lineBeforeEdit = singleLineEdit.getText().toString();

                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processDeletion();
            }

        });

    }//end of onCreate method

    private void readTheLines() {
        try {
            InputStream inputStream = openFileInput(EXPENSES_FILE);
            Scanner in = new Scanner(inputStream);
            int lineIndex = 0;
            singleLine = new ArrayList<>();
            while (in.hasNextLine()) {
                String line = in.nextLine();
                if (++lineIndex > 2 && !line.equals("")) {

                    singleLine.add(line.trim().replaceAll("\\s+", " "));
                }
            }
            inputStream.close();
        } catch (IOException e) {
            Log.i("EDITACTIVITY", "File not found");

        }
    }

    @SuppressWarnings("StringConcatenationInLoop")
    private void executeSearchByDescription() {

        descField = (EditText) findViewById(R.id.searchByDescText);
        String linefound = null;
        String descFound = null;
        lineToEditMultiple = new StringBuilder();
        MultiMap descriptionFoundMap = new MultiMap();

        if (descField.getText().toString().equals("")) {
            showAlertOnEmptyFields(EMPTY_FIELD+EMPTY_SPACE, "Description field is empty!", OK);
        } else {
            for (int i = 0; i < singleLine.size(); i++) {
                linefound = singleLine.get(i);
                int index = linefound.lastIndexOf(" ");
                descFound = linefound.substring(linefound.indexOf(" "), index);
                descriptionFoundMap.put(descFound.trim(), linefound);
            }
            if (linefound == null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this,
                        AlertDialog.THEME_HOLO_LIGHT)
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
                if (descriptionFoundMap.containsKey(descField.getText().toString())) {
                    lineToEdit = descriptionFoundMap.get(descField.getText().toString());

                    if (lineToEdit.size() > 1) {
                        for (int i = 0; i < lineToEdit.size(); i++) {
                            lineToEditMultiple.append(lineToEdit.get(i) + "\n");

                        }
                        resultsArea.setText(lineToEditMultiple);
                    } else {
                        resultsArea.setText(lineToEdit.toString().replaceAll("\\[", "")
                                .replaceAll("]", ""));
                    }
                } else if (!descriptionFoundMap.containsKey(descField.getText().toString())) {
                    showAlertOnEmptyFields("No expenses", "No expense found! Try again with different description.", OK);
                }
            }
        }
    }

    private void showAlertOnEmptyFields(String title, String message, String buttonText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this, AlertDialog
                .THEME_HOLO_LIGHT)
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

    @SuppressWarnings("StringConcatenationInLoop")
    private void executeSearchByAmount() {

        amountField = (EditText) findViewById(R.id.searchByAmountText);
        String amountPart = null;
        String linefound = "";
        lineToEdit.clear();
        lineToEditMultiple =  new StringBuilder();;
        MultiMap amountFoundMap = new MultiMap();
        if (amountField.getText().toString().equals("")) {
            showAlertOnEmptyFields("Empty Field ", "Amount field is empty!", OK);
        } else {
            for (int i = 0; i < singleLine.size(); i++) {
                linefound = singleLine.get(i);
                amountPart = linefound.substring(0, linefound.indexOf(" "));
                amountFoundMap.put(amountPart, linefound);
            }
            if (amountFoundMap.containsKey(amountField.getText().toString())) {
                lineToEdit = amountFoundMap.get(amountField.getText().toString());

                if (lineToEdit.size() > 1) {
                    for (int i = 0; i < lineToEdit.size(); i++) {
                        lineToEditMultiple.append(lineToEdit.get(i) + "\n");

                    }
                    resultsArea.setText(lineToEditMultiple);
                } else {
                    resultsArea.setText(lineToEdit.toString().replaceAll("\\[", "").replaceAll
                            ("]", ""));
                }
            } else if (!amountFoundMap.containsKey(amountField.getText().toString())) {
                showAlertOnEmptyFields("No expenses", "No expense found! Try again with different amount.", OK);
            }
        }
    }

    private void selectAlineFromMultiline() {
        String[] multiLineContent = lineToEditMultiple.toString().split("\n");
        for (int i = 0; i < multiLineContent.length; i++) {
            int startPos = resultsArea.getLayout().getLineStart(i);
            int endPos = resultsArea.getLayout().getLineEnd(i);
            String singleMultiLine = resultsArea.getText().toString().substring(startPos, endPos);
            Log.i("Single Multiline is ", singleMultiLine);
            linesExtracted.add(singleMultiLine);
        }
    }

    private void processEdit() {

        String editedText = singleLineEdit.getText().toString();
        String amountEdited = editedText.substring(0, editedText.indexOf(" "));
        int lengthTillDate = editedText.lastIndexOf(" ");
        String descriptionEdited = editedText.substring(editedText.indexOf(" "), lengthTillDate)
                .trim();
        String dateEdited = editedText.substring(lengthTillDate, editedText.length()).trim();

        if (resultsArea.getText().toString().equals("")) {
            showAlertOnEmptyFields("Empty Results Field", "Results area is empty! Search first for an expense and try again", OK);
        } else if (editedText.equals("")) {
            showAlertOnEmptyFields("Empty Select Field", "Select field is empty! Select first an expense and try again", OK);
        }
        //if the user has made changes and the edited line is more than 34 characters
        else if (amountEdited.length() > 7 || descriptionEdited.length() > 15 || dateEdited
                .length() != 10) {
            showAlertOnEmptyFields("Maximum input characters exceeded", "Some of the fields you edited exceed the maximum input " +
                    "characters. \n" +
                    "***Remember amount field has 7 characters limit, description has 15 " +
                    "characters limit and date must be equal to 10 characters.\n " +
                    "Please edit again your expense", OK);

        }
        // finally if the user has not exceeded the text limiters we process the Edit
        else if (amountEdited.length() <= 7 && descriptionEdited.length() <= 15 && dateEdited
                .length() == 10) {

            try {
                FileInputStream fstream = this.openFileInput(EXPENSES_FILE);
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
                String strLine;
                StringBuilder fileContent = new StringBuilder();
                String amount = "";
                String desc = "";
                String date = "";
                String formatStr = "%-8s%-15s%-10s";//formats the columns
                int lineIndex = 0;
                while ((strLine = br.readLine()) != null) {
                    lineIndex++;
                    if (strLine.replaceAll("\\s+", " ").equals(lineBeforeEdit.trim()) &&
                            ++lineIndex > 2) {
                        amount = editedText.substring(0, editedText.indexOf(" "));//prints the
                        // amount
                        int index = editedText.lastIndexOf(" ");
                        desc = editedText.substring(editedText.indexOf(" "), index).trim();
                        //prints the description
                        date = editedText.substring(index, editedText.length()).trim();//prints
                        // the date
                        fileContent.append(String.format(formatStr, amount, desc, date).trim());
                        //write edited line in the file
                        fileContent.append("\r\n");//write a line
                    } else {
                        // update content as it is
                        fileContent.append(strLine);
                        fileContent.append("\r\n");
                    }
                }
                PrintWriter out = new PrintWriter(openFileOutput(EXPENSES_FILE, MODE_PRIVATE));
                out.write(fileContent.toString());

                out.close();
                fstream.close();
                Toast.makeText(this, "You edited successfully your expenses.", Toast.LENGTH_LONG)
                        .show();

            } catch (FileNotFoundException e) {
                Log.e("File not found ", e.toString());
            } catch (IOException e) {
                Log.e("IOException: ", e.toString());
            }
        }
    }

    private void deleteAnExpense() {
        String lineToCompare = "";//i use this because i want to check whether the user has set
        // focus on the singleLineEdit
        if (lineBeforeEdit.equals(""))//if he/she has not set focus that means lineBeforeEdit=""
        {
            lineToCompare = singleLineEdit.getText().toString().trim();
        } else {
            lineToCompare = lineBeforeEdit;//if he/she has set focus that means lineBeforeEdit
            // has taken value, i pass this value
        }                                   // in lineToCompare
        try {
            FileInputStream fstream = this.openFileInput(EXPENSES_FILE);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            StringBuilder fileContent = new StringBuilder();
            while ((strLine = br.readLine()) != null) {

                //if the line in the file is not the same with the one selected by the user
                if (!strLine.replaceAll("\\s+", " ").equals(lineToCompare.trim())) {
                    fileContent.append(strLine);
                    fileContent.append("\r\n");//then write it to the file.
                    //So the line that will be the same as the lineBeforeEdit, the line selected
                    // to the
                    //singleLineEdit will not be written to the file, it will be deleted.
                }
            }
            PrintWriter out = new PrintWriter(openFileOutput(EXPENSES_FILE, MODE_PRIVATE));
            if (fileContent.toString().replaceAll("\\s+", " ").equals("Amount Description Date ")) {
                out.write(fileContent.toString().trim());
                out.write("\r\n");
                out.write("\r\n");//if the expense deleted is the last one that means the file
                // contains only the header.
                out.close();//So i add the header and two lines after it.
                Toast.makeText(this, "You deleted the selected expense", Toast.LENGTH_LONG).show();
            } else {
                out.write(fileContent.toString().trim());
                out.write("\r\n");//if the expense is not the last in the file i add one line
                out.close();//because the next expense is added will go straight into that line
                Toast.makeText(this, "You deleted the selected expense", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {//Catch exception if any
            Log.e("Error: ", e.getMessage());
        }
    }

    private void processDeletion() {
        if (resultsArea.getText().toString().equals("")) {
            showAlertOnEmptyFields("Empty Results Field", "Results area is empty! Search first for an expense and try again", OK);
        } else if (singleLineEdit.getText().toString().equals("")) {
            showAlertOnEmptyFields("Empty Line to Delete", "The line to edit/Delete is empty! Please press select button " +
                    "first and try again", OK);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this, AlertDialog
                    .THEME_HOLO_LIGHT)
                    .setTitle("Delete the expense")
                    .setMessage("Do you really want to delete this expense?");
            builder.setPositiveButton(OK, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    deleteAnExpense();
                    Log.i("Info: ","You pressed OK");
                }
            });
            builder.setNegativeButton(CANCEL, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    Log.i("Info: ","You pressed cancel");
                }
            });
            builder.show();
        }
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
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}