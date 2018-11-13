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
import java.util.Iterator;
import java.util.LinkedHashSet;

import uk.co.irokottaki.moneycontrol.Utils.NothingSelectedSpinnerAdapter;
import uk.co.irokottaki.moneycontrol.R;
import uk.co.irokottaki.moneycontrol.Utils.Utils;

import static uk.co.irokottaki.moneycontrol.Utils.Constants.CALCULATE_ANNUAL_EXPENSES;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.CLOSE;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.EMPTY_FIELD;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.OK;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.TWOTHOUSANDEIGHTEEN;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.TWOTHOUSANDFIFTEEN;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.TWOTHOUSANDSEVENTEEN;
import static uk.co.irokottaki.moneycontrol.Utils.Constants.TWOTHOUSANDSIXTEEN;

public class CalculateAnnualExpensesActivity extends AppCompatActivity {
    private Spinner expensesList, yearList;
    private Button calculateButton;
    private ArrayAdapter<String> spinnerAdapter;
    private RelativeLayout layout;
    private ArrayList itemsAddedByUser = null;
    private ArrayList arrayOfamountOct15, arrayOfamountNov15, arrayOfamountDec15,
            arrayOfamountJan16, arrayOfamountFeb16,
            arrayOfamountMar16, arrayOfamountApr16, arrayOfamountMay16, arrayOfamountJun16,
            arrayOfamountJul16, arrayOfamountAug16, arrayOfamountSep16,
            arrayOfamountOct16, arrayOfamountNov16, arrayOfamountDec16,
            arrayOfamountJan17, arrayOfamountFeb17, arrayOfamountMar17, arrayOfamountApr17,
            arrayOfamountMay17, arrayOfamountJun17,
            arrayOfamountJul17, arrayOfamountAug17, arrayOfamountSep17, arrayOfamountOct17,
            arrayOfamountNov17, arrayOfamountDec17,
            arrayOfamountJan, arrayOfamountFeb, arrayOfamountMar, arrayOfamountApr,
            arrayOfamountMay, arrayOfamountJun, arrayOfamountJul,
            arrayOfamountAug, arrayOfamountSep, arrayOfamountOct, arrayOfamountNov,
            arrayOfamountDec;//all the amounts for the months
    //unique descriptions
    private LinkedHashSet descriptionsNoDuplicateOct15;
    private LinkedHashSet descriptionsNoDuplicateNov15;
    private LinkedHashSet descriptionsNoDuplicateDec15;
    private LinkedHashSet descriptionsNoDuplicateJan16;
    private LinkedHashSet descriptionsNoDuplicateFeb16;
    private LinkedHashSet descriptionsNoDuplicateMar16;
    private LinkedHashSet descriptionsNoDuplicateApr16;
    private LinkedHashSet descriptionsNoDuplicateMay16;
    private LinkedHashSet descriptionsNoDuplicateJun16;
    private LinkedHashSet descriptionsNoDuplicateJul16;
    private LinkedHashSet descriptionsNoDuplicateAug16;
    private LinkedHashSet descriptionsNoDuplicateSep16;
    private LinkedHashSet descriptionsNoDuplicateOct16;
    private LinkedHashSet descriptionsNoDuplicateNov16;
    private LinkedHashSet descriptionsNoDuplicateDec16;
    private LinkedHashSet descriptionsNoDuplicateJan17;
    private LinkedHashSet descriptionsNoDuplicateFeb17;
    private LinkedHashSet descriptionsNoDuplicateMar17;
    private LinkedHashSet descriptionsNoDuplicateApr17;
    private LinkedHashSet descriptionsNoDuplicateMay17;
    private LinkedHashSet descriptionsNoDuplicateJun17;
    private LinkedHashSet descriptionsNoDuplicateJul17;
    private LinkedHashSet descriptionsNoDuplicateAug17;
    private LinkedHashSet descriptionsNoDuplicateSep17;
    private LinkedHashSet descriptionsNoDuplicateOct17;
    private LinkedHashSet descriptionsNoDuplicateNov17;
    private LinkedHashSet descriptionsNoDuplicateDec17;
    private LinkedHashSet descriptionsNoDuplicateJan;
    private LinkedHashSet descriptionsNoDuplicateFeb;
    private LinkedHashSet descriptionsNoDuplicateMar;
    private LinkedHashSet descriptionsNoDuplicateApr;
    private LinkedHashSet descriptionsNoDuplicateMay;
    private LinkedHashSet descriptionsNoDuplicateJun;
    private LinkedHashSet descriptionsNoDuplicateJul;
    private LinkedHashSet descriptionsNoDuplicateAug;
    private LinkedHashSet descriptionsNoDuplicateSep;
    private LinkedHashSet descriptionsNoDuplicateOct;
    private LinkedHashSet descriptionsNoDuplicateNov;
    private LinkedHashSet descriptionsNoDuplicateDec;
    private Double annualExpenseDouble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_annual_expenses);

        setTitle(CALCULATE_ANNUAL_EXPENSES);

        //retrieve the spinner items from main activity so whatever is populated in the main
        // activity to be displayed here as well
        itemsAddedByUser = MainActivity.getitemsAddedByUser();
        //retrieve the data for calculation
        retrieveDataFromChartActivity();

        layout = (RelativeLayout) findViewById(R.id.activity_calculate_annual_expenses);

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
        int year = Calendar.getInstance().get(Calendar.YEAR);
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

    public void retrieveDataFromChartActivity() {
        ChartActivity.readTheFile();
        descriptionsNoDuplicateOct15 = ChartActivity.getDescriptionsOfOct15();
        descriptionsNoDuplicateNov15 = ChartActivity.getDescriptionsOfNov15();
        descriptionsNoDuplicateDec15 = ChartActivity.getDescriptionsOfDec15();
        descriptionsNoDuplicateJan16 = ChartActivity.getDescriptionsOfJan16();
        descriptionsNoDuplicateFeb16 = ChartActivity.getDescriptionsOfFeb16();
        descriptionsNoDuplicateMar16 = ChartActivity.getDescriptionsOfMar16();
        descriptionsNoDuplicateApr16 = ChartActivity.getDescriptionsOfApr16();
        descriptionsNoDuplicateMay16 = ChartActivity.getDescriptionsOfMay16();
        descriptionsNoDuplicateJun16 = ChartActivity.getDescriptionsOfJun16();
        descriptionsNoDuplicateJul16 = ChartActivity.getDescriptionsOfJul16();
        descriptionsNoDuplicateAug16 = ChartActivity.getDescriptionsOfAug16();
        descriptionsNoDuplicateSep16 = ChartActivity.getDescriptionsOfSep16();
        descriptionsNoDuplicateOct16 = ChartActivity.getDescriptionsOfOct16();
        descriptionsNoDuplicateNov16 = ChartActivity.getDescriptionsOfNov16();
        descriptionsNoDuplicateDec16 = ChartActivity.getDescriptionsOfDec16();
        descriptionsNoDuplicateJan17 = ChartActivity.getDescriptionsOfJan17();
        descriptionsNoDuplicateFeb17 = ChartActivity.getDescriptionsOfFeb17();
        descriptionsNoDuplicateMar17 = ChartActivity.getDescriptionsOfMar17();
        descriptionsNoDuplicateApr17 = ChartActivity.getDescriptionsOfApr17();
        descriptionsNoDuplicateMay17 = ChartActivity.getDescriptionsOfMay17();
        descriptionsNoDuplicateJun17 = ChartActivity.getDescriptionsOfJun17();
        descriptionsNoDuplicateJul17 = ChartActivity.getDescriptionsOfJul17();
        descriptionsNoDuplicateAug17 = ChartActivity.getDescriptionsOfAug17();
        descriptionsNoDuplicateSep17 = ChartActivity.getDescriptionsOfSep17();
        descriptionsNoDuplicateOct17 = ChartActivity.getDescriptionsOfOct17();
        descriptionsNoDuplicateNov17 = ChartActivity.getDescriptionsOfNov17();
        descriptionsNoDuplicateDec17 = ChartActivity.getDescriptionsOfDec17();
        descriptionsNoDuplicateJan = ChartActivity.getDescriptionsOfJan();
        descriptionsNoDuplicateFeb = ChartActivity.getDescriptionsOfFeb();
        descriptionsNoDuplicateMar = ChartActivity.getDescriptionsOfMar();
        descriptionsNoDuplicateApr = ChartActivity.getDescriptionsOfApr();
        descriptionsNoDuplicateMay = ChartActivity.getDescriptionsOfMay();
        descriptionsNoDuplicateJun = ChartActivity.getDescriptionsOfJun();
        descriptionsNoDuplicateJul = ChartActivity.getDescriptionsOfJul();
        descriptionsNoDuplicateAug = ChartActivity.getDescriptionsOfAug();
        descriptionsNoDuplicateSep = ChartActivity.getDescriptionsOfSep();
        descriptionsNoDuplicateOct = ChartActivity.getDescriptionsOfOct();
        descriptionsNoDuplicateNov = ChartActivity.getDescriptionsOfNov();
        descriptionsNoDuplicateDec = ChartActivity.getDescriptionsOfDec();

        arrayOfamountOct15 = ChartActivity.getAmountsOfOct15();
        arrayOfamountNov15 = ChartActivity.getAmountsOfNov15();
        arrayOfamountDec15 = ChartActivity.getAmountsOfDec15();
        arrayOfamountJan16 = ChartActivity.getAmountsOfJan16();
        arrayOfamountFeb16 = ChartActivity.getAmountsOfFeb16();
        arrayOfamountMar16 = ChartActivity.getAmountsOfMar16();
        arrayOfamountApr16 = ChartActivity.getAmountsOfApr16();
        arrayOfamountMay16 = ChartActivity.getAmountsOfMay16();
        arrayOfamountJun16 = ChartActivity.getAmountsOfJun16();
        arrayOfamountJul16 = ChartActivity.getAmountsOfJul16();
        arrayOfamountAug16 = ChartActivity.getAmountsOfAug16();
        arrayOfamountSep16 = ChartActivity.getAmountsOfSep16();
        arrayOfamountOct16 = ChartActivity.getAmountsOfOct16();
        arrayOfamountNov16 = ChartActivity.getAmountsOfNov16();
        arrayOfamountDec16 = ChartActivity.getAmountsOfDec16();
        arrayOfamountJan17 = ChartActivity.getAmountsOfJan17();
        arrayOfamountFeb17 = ChartActivity.getAmountsOfFeb17();
        arrayOfamountMar17 = ChartActivity.getAmountsOfMar17();
        arrayOfamountApr17 = ChartActivity.getAmountsOfApr17();
        arrayOfamountMay17 = ChartActivity.getAmountsOfMay17();
        arrayOfamountJun17 = ChartActivity.getAmountsOfJun17();
        arrayOfamountJul17 = ChartActivity.getAmountsOfJul17();
        arrayOfamountAug17 = ChartActivity.getAmountsOfAug17();
        arrayOfamountSep17 = ChartActivity.getAmountsOfSep17();
        arrayOfamountOct17 = ChartActivity.getAmountsOfOct17();
        arrayOfamountNov17 = ChartActivity.getAmountsOfNov17();
        arrayOfamountDec17 = ChartActivity.getAmountsOfDec17();
        arrayOfamountJan = ChartActivity.getAmountsOfJan();
        arrayOfamountFeb = ChartActivity.getAmountsOfFeb();
        arrayOfamountMar = ChartActivity.getAmountsOfMar();
        arrayOfamountApr = ChartActivity.getAmountsOfApr();
        arrayOfamountMay = ChartActivity.getAmountsOfMay();
        arrayOfamountJun = ChartActivity.getAmountsOfJun();
        arrayOfamountJul = ChartActivity.getAmountsOfJul();
        arrayOfamountAug = ChartActivity.getAmountsOfAug();
        arrayOfamountSep = ChartActivity.getAmountsOfSep();
        arrayOfamountOct = ChartActivity.getAmountsOfOct();
        arrayOfamountNov = ChartActivity.getAmountsOfNov();
        arrayOfamountDec = ChartActivity.getAmountsOfDec();

    }

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
            String selectedExpense = expensesList.getSelectedItem().toString();

            if (yearList.getSelectedItem().equals(TWOTHOUSANDEIGHTEEN)) {

                calculateExpensesByMonth(descriptionsNoDuplicateJan, selectedExpense,
                        arrayOfamountJan);
                calculateExpensesByMonth(descriptionsNoDuplicateFeb, selectedExpense,
                        arrayOfamountFeb);
                calculateExpensesByMonth(descriptionsNoDuplicateMar, selectedExpense,
                        arrayOfamountMar);
                calculateExpensesByMonth(descriptionsNoDuplicateApr, selectedExpense,
                        arrayOfamountApr);
                calculateExpensesByMonth(descriptionsNoDuplicateMay, selectedExpense,
                        arrayOfamountMay);
                calculateExpensesByMonth(descriptionsNoDuplicateJun, selectedExpense,
                        arrayOfamountJun);
                calculateExpensesByMonth(descriptionsNoDuplicateJul, selectedExpense,
                        arrayOfamountJul);
                calculateExpensesByMonth(descriptionsNoDuplicateAug, selectedExpense,
                        arrayOfamountAug);
                calculateExpensesByMonth(descriptionsNoDuplicateSep, selectedExpense,
                        arrayOfamountSep);
                calculateExpensesByMonth(descriptionsNoDuplicateOct, selectedExpense,
                        arrayOfamountOct);
                calculateExpensesByMonth(descriptionsNoDuplicateNov, selectedExpense,
                        arrayOfamountNov);
                calculateExpensesByMonth(descriptionsNoDuplicateDec, selectedExpense,
                        arrayOfamountDec);
            }

            if (yearList.getSelectedItem().equals(TWOTHOUSANDSEVENTEEN)) {

                calculateExpensesByMonth(descriptionsNoDuplicateJan17, selectedExpense,
                        arrayOfamountJan17);
                calculateExpensesByMonth(descriptionsNoDuplicateFeb17, selectedExpense,
                        arrayOfamountFeb17);
                calculateExpensesByMonth(descriptionsNoDuplicateMar17, selectedExpense,
                        arrayOfamountMar17);
                calculateExpensesByMonth(descriptionsNoDuplicateApr17, selectedExpense,
                        arrayOfamountApr17);
                calculateExpensesByMonth(descriptionsNoDuplicateMay17, selectedExpense,
                        arrayOfamountMay17);
                calculateExpensesByMonth(descriptionsNoDuplicateJun17, selectedExpense,
                        arrayOfamountJun17);
                calculateExpensesByMonth(descriptionsNoDuplicateJul17, selectedExpense,
                        arrayOfamountJul17);
                calculateExpensesByMonth(descriptionsNoDuplicateAug17, selectedExpense,
                        arrayOfamountAug17);
                calculateExpensesByMonth(descriptionsNoDuplicateSep17, selectedExpense,
                        arrayOfamountSep17);
                calculateExpensesByMonth(descriptionsNoDuplicateOct17, selectedExpense,
                        arrayOfamountOct17);
                calculateExpensesByMonth(descriptionsNoDuplicateNov17, selectedExpense,
                        arrayOfamountNov17);
                calculateExpensesByMonth(descriptionsNoDuplicateDec17, selectedExpense,
                        arrayOfamountDec17);
            } else if (yearList.getSelectedItem().equals(TWOTHOUSANDSIXTEEN)) {

                calculateExpensesByMonth(descriptionsNoDuplicateJan16, selectedExpense,
                        arrayOfamountJan16);
                calculateExpensesByMonth(descriptionsNoDuplicateFeb16, selectedExpense,
                        arrayOfamountFeb16);
                calculateExpensesByMonth(descriptionsNoDuplicateMar16, selectedExpense,
                        arrayOfamountMar16);
                calculateExpensesByMonth(descriptionsNoDuplicateApr16, selectedExpense,
                        arrayOfamountApr16);
                calculateExpensesByMonth(descriptionsNoDuplicateMay16, selectedExpense,
                        arrayOfamountMay16);
                calculateExpensesByMonth(descriptionsNoDuplicateJun16, selectedExpense,
                        arrayOfamountJun16);
                calculateExpensesByMonth(descriptionsNoDuplicateJul16, selectedExpense,
                        arrayOfamountJul16);
                calculateExpensesByMonth(descriptionsNoDuplicateAug16, selectedExpense,
                        arrayOfamountAug16);
                calculateExpensesByMonth(descriptionsNoDuplicateSep16, selectedExpense,
                        arrayOfamountSep16);
                calculateExpensesByMonth(descriptionsNoDuplicateOct16, selectedExpense,
                        arrayOfamountOct16);
                calculateExpensesByMonth(descriptionsNoDuplicateNov16, selectedExpense,
                        arrayOfamountNov16);
                calculateExpensesByMonth(descriptionsNoDuplicateDec16, selectedExpense,
                        arrayOfamountDec16);
            } else if (yearList.getSelectedItem().equals(TWOTHOUSANDFIFTEEN)) {

                calculateExpensesByMonth(descriptionsNoDuplicateOct15, selectedExpense,
                        arrayOfamountOct15);
                calculateExpensesByMonth(descriptionsNoDuplicateNov15, selectedExpense,
                        arrayOfamountNov15);
                calculateExpensesByMonth(descriptionsNoDuplicateDec15, selectedExpense,
                        arrayOfamountDec15);

            }
        }
    }

    private void calculateExpensesByMonth(LinkedHashSet descriptions, String selectedExpense,
                                          ArrayList<Float> arrayAmount) {

        Iterator Itr = descriptions.iterator();
        int i = 0;
        while (Itr.hasNext()) {
            String descFound = Itr.next().toString();
            if (selectedExpense.equals(descFound)) {
                annualExpenseDouble += (double) arrayAmount.get(i);
            }
            i++;
        }
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
