package uk.co.irokottaki.moneycontrol.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

import uk.co.irokottaki.moneycontrol.R;
import uk.co.irokottaki.moneycontrol.utils.Utils;

import static uk.co.irokottaki.moneycontrol.utils.Constants.CLOSE;
import static uk.co.irokottaki.moneycontrol.utils.Constants.INFORMATION;
import static uk.co.irokottaki.moneycontrol.utils.Constants.OK;
import static uk.co.irokottaki.moneycontrol.utils.Constants.PREFERENCES;

public class BudgetActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private TextView warningDisplay;
    private TextView budgetWarningValue;
    private static final int STEP = 1;
    private static final int MAX = 2000;
    private static final int MIN = 0;
    private int progress = 0;
    private String currentMonth;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.budgetView);

        //this is to change the background color of the activity when user changes it from settings
        Utils utils = new Utils(this);
        utils.setBackgroundAndAdjustLayout(layout, BudgetActivity.this);

        //Button for information
        ImageButton infoBudgetButton = (ImageButton) findViewById(R.id.infoBudgetButton);

        //Seek bar
        SeekBar budgetSeekBar = (SeekBar) findViewById(R.id.budgetBar);
        budgetSeekBar.setOnSeekBarChangeListener(this);

        //Warning textview
        warningDisplay = new TextView(this);
        warningDisplay = (TextView) findViewById(R.id.warningDisplay);

        //Budget Warning Value
        budgetWarningValue = (TextView) findViewById(R.id.budgetWarningValue);
        SharedPreferences prefs = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        //get the current budget value as set by the user
        progress = prefs.getInt("budgetValue", 0);
        budgetWarningValue.setText("Your current warning budget is: " +progress);


        infoBudgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BudgetActivity.this,
                        R.style.Theme_AppCompat_Light_Dialog)
                        .setTitle(INFORMATION)
                        .setMessage("Here you can set a budget warning using the bar displayed."
                                + "\n" +
                                "You can move the bar and set it as it goes from 0 to 2000. The " +
                                "amount that you set is stored " +
                                "for every month and does not change unless you do so. The " +
                                "application will notify you on the 80% " +
                                "of the warning you set here as well as on the 90% and 100%. The " +
                                "notification will be shown after " +
                                "you add an expense." + "\n" + "\n" + "***Remember to turn on the" +
                                " notifications from the settings.");
                AlertDialog alert1;
                builder.setPositiveButton(CLOSE,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                alert1 = builder.create();
                alert1.show();
            }
        });


        budgetSeekBar.setMax((MAX - MIN) / STEP);

        warningDisplay.setText("Warning set on: " + budgetSeekBar.getProgress() + "/" +
                budgetSeekBar.getMax());

        final Calendar calendar = Calendar.getInstance();//this gets the current month

        currentMonth = String.format(Locale.UK, "%tB", calendar);

        budgetSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                /*Not used*/
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                warningDisplay.setText("Warning set on: " + progress + "/" + seekBar.getMax() + "" +
                        " for month " + currentMonth);

                //store the progress to the budget warning value so that stays there until the
                // user changes it.
                budgetWarningValue.setText("Your current warning budget is: " + progress);

                SharedPreferences sp = getSharedPreferences(PREFERENCES, BudgetActivity
                        .MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("budgetValue", progress);
                editor.apply();
                AlertDialog.Builder builder = new AlertDialog.Builder(BudgetActivity.this,
                        R.style.Theme_AppCompat_Light_Dialog)
                        .setTitle("Budget Warning Set")
                        .setMessage("You set your warning budget. Now go to Settings of the Home " +
                                "Screen to turn them on.");
                AlertDialog alert1;
                builder.setPositiveButton(OK,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                alert1 = builder.create();
                alert1.show();
            }
        });

    }// end of onCreate method

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
        /*Not used*/
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        /*Not used*/
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        /*Not used*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) { // This is added to avoid the call on the onCreate method of MainActivity because it will read again the file and iterate the map
            case android.R.id.home: finish();
                return true;
        }
        //this code is for storing the value of progress in the budgetWarningValue when going
        // back to main activity
        budgetWarningValue = (TextView) findViewById(R.id.budgetWarningValue);

        return super.onOptionsItemSelected(item);

    }


}
