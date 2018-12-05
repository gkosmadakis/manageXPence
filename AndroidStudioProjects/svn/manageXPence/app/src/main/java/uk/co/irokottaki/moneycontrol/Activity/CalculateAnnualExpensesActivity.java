package uk.co.irokottaki.moneycontrol.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import uk.co.irokottaki.moneycontrol.R;
import uk.co.irokottaki.moneycontrol.Utils.ChartsUtil;
import uk.co.irokottaki.moneycontrol.Utils.NothingSelectedSpinnerAdapter;
import uk.co.irokottaki.moneycontrol.Utils.Utils;

import static uk.co.irokottaki.moneycontrol.Utils.Constants.*;

public class CalculateAnnualExpensesActivity extends AppCompatActivity {
    private Spinner expensesList, yearList;
    private Button calculateButton;
    private ArrayAdapter<String> spinnerAdapter;
    private ArrayList itemsAddedByUser = null;
    private Double annualExpenseDouble;
    private ChartsUtil util;
    private int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_annual_expenses);

        setTitle(CALCULATE_ANNUAL_EXPENSES);

        util = new ChartsUtil(this);

        //retrieve the spinner items from main activity so whatever is populated in the main
        // activity to be displayed here as well
        itemsAddedByUser = MainActivity.getitemsAddedByUser();
        //retrieve the data for calculation

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_calculate_annual_expenses);

        //this is to change the background color of the activity when user changes it from settings
        Utils utils = new Utils(this);
        utils.setBackgroundAndAdjustLayout(layout, CalculateAnnualExpensesActivity.this);

        expensesList = (Spinner) findViewById(R.id.expensesSpinner);

        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                itemsAddedByUser);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expensesList.setAdapter(spinnerAdapter);

        expensesList.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        spinnerAdapter,
                        R.layout.spinnernothingselected,
                        this));

        yearList = (Spinner) findViewById(R.id.yearSpinner);
        year = Calendar.getInstance().get(Calendar.YEAR);
        //get the year items from the array
        Resources res = getResources();
        String[] yearInTheList = res.getStringArray(R.array.yearItems);
        int length = yearInTheList.length;
        String yearInArray = null;
        for (int i = 0; i < length; i++) {
            yearInArray = yearInTheList[i];

            if (year == Integer.parseInt(yearInArray)) {
                yearList.setSelection(i);
            }
        }
        //calculate Button
        calculateButton = new Button(this);
        calculateButton = (Button) findViewById(R.id.calculateButton);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calculateExpensesByYear();
                DecimalFormat df = new DecimalFormat("#.00");

                if (expensesList.getSelectedItem() != null && !expensesList.getSelectedItem()
                        .equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder
                            (CalculateAnnualExpensesActivity.this, AlertDialog.THEME_HOLO_LIGHT)
                            .setTitle("Annual Expenses for year: " + yearList.getSelectedItem())
                            .setMessage("You have spent: " + df.format(annualExpenseDouble) + " " +
                                    "for " + expensesList.getSelectedItem());
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
            }
        });

    }// end of onCreate method


    private void calculateExpensesByYear() {

        annualExpenseDouble = 0.0;

        if (expensesList.getSelectedItem() == null || expensesList.getSelectedItem().equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(CalculateAnnualExpensesActivity
                    .this, AlertDialog.THEME_HOLO_LIGHT)
                    .setTitle(EMPTY_FIELD)
                    .setMessage("Expenses Field is empty, select first an expense and try again");
            AlertDialog alert1;
            builder.setPositiveButton(OK,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            alert1 = builder.create();
            alert1.show();

        } else {

            util.readTheFile();
            util.calculateSelectedExpenses(year, expensesList,yearList, CalculateAnnualExpensesActivity.this);
        }
    }

    public Double getAnnualExpenseDouble() {
        return annualExpenseDouble;
    }

    public Double setAnnualExpenseDouble(double expense) {
        return this.annualExpenseDouble = expense;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_annual_chart, menu);
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
