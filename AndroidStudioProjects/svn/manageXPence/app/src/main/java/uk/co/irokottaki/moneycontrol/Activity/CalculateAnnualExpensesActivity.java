package uk.co.irokottaki.moneycontrol.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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
import java.util.HashMap;

import uk.co.irokottaki.moneycontrol.model.AnyYear;
import uk.co.irokottaki.moneycontrol.R;
import uk.co.irokottaki.moneycontrol.utils.ChartsUtil;
import uk.co.irokottaki.moneycontrol.utils.MainActivityUtil;
import uk.co.irokottaki.moneycontrol.utils.NothingSelectedSpinnerAdapter;
import uk.co.irokottaki.moneycontrol.utils.Utils;

import static uk.co.irokottaki.moneycontrol.utils.Constants.*;

public class CalculateAnnualExpensesActivity extends AppCompatActivity {
    private Spinner expensesList;
    private Spinner yearList;
    private Double annualExpenseDouble;
    private ChartsUtil util;
    private int year;
    private HashMap yearsMappedToObjectYearsMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_annual_expenses);

        setTitle(CALCULATE_ANNUAL_EXPENSES);

        util = new ChartsUtil(this);
        Intent intent = getIntent();
        yearsMappedToObjectYearsMap = (HashMap<String, AnyYear> ) intent.getSerializableExtra(YEARS_MAPPED_TO_OBJECT_YEARS_MAP);
        //add it here to be used back in AnnualChartActivity


        //retrieve the spinner items from main activity so whatever is populated in the main
        // activity to be displayed here as well
        MainActivityUtil mainUtil = new MainActivityUtil(this);
        ArrayList itemsAddedByUser = (ArrayList) mainUtil.readDescriptionsFile();
        //retrieve the data for calculation

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_calculate_annual_expenses);

        //this is to change the background color of the activity when user changes it from settings
        Utils utils = new Utils(this);
        utils.setBackgroundAndAdjustLayout(layout, CalculateAnnualExpensesActivity.this);

        expensesList = (Spinner) findViewById(R.id.expensesSpinner);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
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

        //populate the spinner with the years found after reading the file
        util.populateYearSpinnerAndSetCurrentYear(yearsMappedToObjectYearsMap,year, yearList, CalculateAnnualExpensesActivity.this);

        //calculate Button
        Button calculateButton = (Button) findViewById(R.id.calculateButton);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calculateExpensesByYear();
                DecimalFormat df = new DecimalFormat("#.00");

                if (expensesList.getSelectedItem() != null && !expensesList.getSelectedItem()
                        .equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder
                            (CalculateAnnualExpensesActivity.this, R.style.Theme_AppCompat_Light_Dialog)
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
                    .this, R.style.Theme_AppCompat_Light_Dialog)
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
            // get the year selected from the year spinner
            year = Integer.parseInt(yearList.getSelectedItem().toString());
            util.calculateSelectedExpenses(yearsMappedToObjectYearsMap,year, expensesList, CalculateAnnualExpensesActivity.this);
        }
    }

    public Double getAnnualExpenseDouble() {
        return annualExpenseDouble;
    }

    public void setAnnualExpenseDouble(Double annualExpenseDouble) {
        this.annualExpenseDouble = annualExpenseDouble;
    }

    @Override
    public void onBackPressed() {
        /*Pass the map back to AnnualChartActivity when user presses the back button*/
        Intent intent = new Intent();
        intent.putExtra(YEARS_MAPPED_TO_OBJECT_YEARS_MAP, yearsMappedToObjectYearsMap);
        setResult(-1, intent);
        finish();
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
        /*Pass the map back to AnnualChartActivity when user presses the back button on the top of activity*/
        Intent intent = new Intent();
        intent.putExtra(YEARS_MAPPED_TO_OBJECT_YEARS_MAP, yearsMappedToObjectYearsMap);
        setResult(-1, intent);
        finish();

        return true;
    }
}
