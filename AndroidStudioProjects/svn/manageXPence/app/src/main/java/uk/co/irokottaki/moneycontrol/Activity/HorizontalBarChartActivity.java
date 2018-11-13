package uk.co.irokottaki.moneycontrol.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;

import uk.co.irokottaki.moneycontrol.R;
import uk.co.irokottaki.moneycontrol.Utils.Utils;

import static uk.co.irokottaki.moneycontrol.Utils.Constants.*;

public class HorizontalBarChartActivity extends ActionBarActivity {

    private static ArrayList<Float> arrayOfamounts;
    private static ArrayList<Float> arrayOfamountOct15, arrayOfamountNov15, arrayOfamountDec15,
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
    private ArrayList<BarDataSet> dataSets;
    private static LinkedHashSet<String> descriptionsNoDuplicate; //unique descriptions
    private static LinkedHashSet<String> descriptionsNoDuplicateOct15,
            descriptionsNoDuplicateNov15, descriptionsNoDuplicateDec15,
            descriptionsNoDuplicateJan16, descriptionsNoDuplicateFeb16,
            descriptionsNoDuplicateMar16, descriptionsNoDuplicateApr16,
            descriptionsNoDuplicateMay16, descriptionsNoDuplicateJun16,
            descriptionsNoDuplicateJul16, descriptionsNoDuplicateAug16,
            descriptionsNoDuplicateSep16, descriptionsNoDuplicateOct16,
            descriptionsNoDuplicateNov16, descriptionsNoDuplicateDec16,
            descriptionsNoDuplicateJan17, descriptionsNoDuplicateFeb17,
            descriptionsNoDuplicateMar17, descriptionsNoDuplicateApr17,
            descriptionsNoDuplicateMay17, descriptionsNoDuplicateJun17,
            descriptionsNoDuplicateJul17, descriptionsNoDuplicateAug17,
            descriptionsNoDuplicateSep17, descriptionsNoDuplicateOct17,
            descriptionsNoDuplicateNov17, descriptionsNoDuplicateDec17,
            descriptionsNoDuplicateJan, descriptionsNoDuplicateFeb, descriptionsNoDuplicateMar,
            descriptionsNoDuplicateApr,
            descriptionsNoDuplicateMay, descriptionsNoDuplicateJun, descriptionsNoDuplicateJul,
            descriptionsNoDuplicateAug,
            descriptionsNoDuplicateSep, descriptionsNoDuplicateOct, descriptionsNoDuplicateNov,
            descriptionsNoDuplicateDec;
    //all the descriptions for the months
    private static float amountWithDuplicate;
    private static final Map<String, Float> storeAmounts = new HashMap<>();
    private ImageButton arrowLeft, arrowRight;
    private HorizontalBarChart chart;
    private BarData data;
    private ArrayList<BarEntry> valueSet1;
    private ArrayList<String> xAxis;
    private BarDataSet barDataSet1;
    private int monthInt;
    private Switch toggleAmount;
    private boolean stateSwitchButton;
    private FrameLayout layout;
    private RelativeLayout barLayout;
    private PreferenceManager mPreferenceManager;
    private TextView monthLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_bar_chart);
        setTitle(BAR_CHART);
        readTheFile();

        layout = (FrameLayout) findViewById(R.id.chartLayout);

        barLayout = (RelativeLayout) findViewById(R.id.barChartView);

        //this is to change the background color of the activity when user changes it from settings
        Utils utils = new Utils(this);
        utils.setBackgroundAndAdjustLayout(barLayout, HorizontalBarChartActivity.this);

        arrowLeft = new ImageButton(this);
        arrowLeft = (ImageButton) findViewById(R.id.arrowLeft);

        monthLabel = (TextView) findViewById(R.id.month);

        arrowRight = new ImageButton(this);
        arrowRight = (ImageButton) findViewById(R.id.arrowRight);

        chart = (HorizontalBarChart) findViewById(R.id.chart);
        valueSet1 = new ArrayList<>();
        xAxis = new ArrayList<>();

        toggleAmount = (Switch) findViewById(R.id.switchButton);
        toggleAmount.setChecked(false);

        data = new BarData(xAxis, dataSets);// initialize data to avoid null pointers

        final Calendar calendar = Calendar.getInstance();
        //SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String currentMonth = String.format(Locale.UK, "%tB", calendar);
        //month_date.format(calendar.getTime());
        monthLabel.setText(currentMonth);

        String getCurrentMonthDisplayed = monthLabel.getText().toString();
        monthInt = 0;
        try {
            java.util.Date date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse
                    (getCurrentMonthDisplayed);
            calendar.setTime(date);// here i convert the String month in an integer to be used on
            // the switch-case
            monthInt = calendar.get(Calendar.MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // switch button listener
        toggleAmount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //depending on the month the user is i need to show the percentages for that
                    // month
                    if (!valueSet1.isEmpty()) {
                        calculatePercentagesAndModifyYAxis();
                    }
                    if (dataSets != null) {
                        data.setValueFormatter(new PercentFormatter());//this adds % in the
                        // percentage
                    }
                    chart.invalidate();
                    stateSwitchButton = true;//this is to know that the switch is ON
                } else {
                    //here i revert back to the actual numbers
                    if (!valueSet1.isEmpty()) {
                        revertToNumbersAndModifyYAxis();
                    }
                    if (dataSets != null) {
                        data.setValueFormatter(new ValueFormatter() {
                            @Override
                            public String getFormattedValue(float v, Entry entry, int i,
                                                            ViewPortHandler viewPortHandler) {
                                return Float.toString(v);//this returns the value as a float as
                                // it used to be
                            }
                        });
                    }
                    chart.invalidate();
                    stateSwitchButton = false;//this is to know that the switch is OFF
                }
            }
        });


        barDataSet1 = new BarDataSet(valueSet1, EXPENSE);//i initialize it here in order to
        // avoid getting null pointer inside the switch

        arrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthInt--;
                casesToShowExpensesForMonth();

            }
        });

        arrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthInt++;
                casesToShowExpensesForMonth();

            }
        });
        arrowRight.performClick();
        data = new BarData(xAxis, dataSets);

    }//end of create method


    //this method calculates percentages from the values on the arrayOfamounts
    private void calculatePercentages(ArrayList<Float> arrayOfamount) {

        int total = 0;
        double percentage = 0;
        for (int i = 0; i < arrayOfamount.size(); i++) {
            total += Math.round(arrayOfamount.get(i));
        }
        for (int j = 0; j < arrayOfamount.size(); j++) {
            percentage = Math.round((arrayOfamount.get(j) * 100.0) / (double) total);
            float percentageInFloat = (float) percentage;
            //i replace all the items on the valueSet1 with percentages
            valueSet1.set(j, new BarEntry(percentageInFloat, j));
        }
    }

    //this method converts back to numbers after they were made percentages
    private void revertPercentagesToNumbers(ArrayList<Float> arrayOfamount) {

        for (int j = 0; j < arrayOfamount.size(); j++) {
            valueSet1.set(j, new BarEntry(arrayOfamount.get(j), j));//i replace all the items
            // with actual numbers as they used to be
        }
    }

    private void modifyYAxis() {
        data = new BarData(xAxis, dataSets);
        if (dataSets != null) {
            chart.setData(data);
            chart.animateXY(2000, 2000);
            chart.invalidate();
        }
    }

    private void modifyData() {
        if (valueSet1.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(HorizontalBarChartActivity.this)
                    .setTitle("No data to present")
                    .setMessage("There are no expenses to display on this month.");
            builder.setPositiveButton(OK,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert1 = builder.create();
            alert1.show();
            chart.clear();
        } else {
            barDataSet1 = new BarDataSet(valueSet1, EXPENSE);
            barDataSet1.setColor(Color.rgb(0, 153, 204));

            dataSets = new ArrayList<>();
            dataSets.add(barDataSet1);

            data = new BarData(xAxis, dataSets);
            if (stateSwitchButton) {
                calculatePercentagesAndModifyYAxis();

                data.setValueFormatter(new PercentFormatter());

            } else {
                revertToNumbersAndModifyYAxis();

                data.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler
                            viewPortHandler) {
                        return Float.toString(v);
                    }
                });
            }
            chart.setData(data);
            chart.setDescription("");
            chart.animateXY(2000, 2000);
            chart.invalidate();
        }
    }


    private static void readTheFile() {
        String amount;
        String date;
        String desc = "";
        arrayOfamounts = new ArrayList<>();
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

        descriptionsNoDuplicate = new LinkedHashSet<>();
        descriptionsNoDuplicateOct15 = new LinkedHashSet<>();//store unique descriptions
        descriptionsNoDuplicateNov15 = new LinkedHashSet<>();
        descriptionsNoDuplicateDec15 = new LinkedHashSet<>();
        descriptionsNoDuplicateJan16 = new LinkedHashSet<>();
        descriptionsNoDuplicateFeb16 = new LinkedHashSet<>();
        descriptionsNoDuplicateMar16 = new LinkedHashSet<>();
        descriptionsNoDuplicateApr16 = new LinkedHashSet<>();
        descriptionsNoDuplicateMay16 = new LinkedHashSet<>();
        descriptionsNoDuplicateJun16 = new LinkedHashSet<>();
        descriptionsNoDuplicateJul16 = new LinkedHashSet<>();
        descriptionsNoDuplicateAug16 = new LinkedHashSet<>();
        descriptionsNoDuplicateSep16 = new LinkedHashSet<>();
        descriptionsNoDuplicateOct16 = new LinkedHashSet<>();
        descriptionsNoDuplicateNov16 = new LinkedHashSet<>();
        descriptionsNoDuplicateDec16 = new LinkedHashSet<>();
        descriptionsNoDuplicateJan17 = new LinkedHashSet<>();//store unique descriptions
        descriptionsNoDuplicateFeb17 = new LinkedHashSet<>();
        descriptionsNoDuplicateMar17 = new LinkedHashSet<>();
        descriptionsNoDuplicateApr17 = new LinkedHashSet<>();
        descriptionsNoDuplicateMay17 = new LinkedHashSet<>();//store unique descriptions
        descriptionsNoDuplicateJun17 = new LinkedHashSet<>();
        descriptionsNoDuplicateJul17 = new LinkedHashSet<>();
        descriptionsNoDuplicateAug17 = new LinkedHashSet<>();
        descriptionsNoDuplicateSep17 = new LinkedHashSet<>();//store unique descriptions
        descriptionsNoDuplicateOct17 = new LinkedHashSet<>();
        descriptionsNoDuplicateNov17 = new LinkedHashSet<>();
        descriptionsNoDuplicateDec17 = new LinkedHashSet<>();
        descriptionsNoDuplicateJan = new LinkedHashSet<>();//store unique descriptions
        descriptionsNoDuplicateFeb = new LinkedHashSet<>();
        descriptionsNoDuplicateMar = new LinkedHashSet<>();
        descriptionsNoDuplicateApr = new LinkedHashSet<>();
        descriptionsNoDuplicateMay = new LinkedHashSet<>();//store unique descriptions
        descriptionsNoDuplicateJun = new LinkedHashSet<>();
        descriptionsNoDuplicateJul = new LinkedHashSet<>();
        descriptionsNoDuplicateAug = new LinkedHashSet<>();
        descriptionsNoDuplicateSep = new LinkedHashSet<>();//store unique descriptions
        descriptionsNoDuplicateOct = new LinkedHashSet<>();
        descriptionsNoDuplicateNov = new LinkedHashSet<>();
        descriptionsNoDuplicateDec = new LinkedHashSet<>();


        try {
            InputStream inputStream = new FileInputStream("/data/data/uk.co.irokottaki" +
                    ".moneycontrol/files/expenses.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            int lineIndex = 0;//this is to count the lines
            while ((line = br.readLine()) != null) {

                if (++lineIndex > 2 && !line.equals("")) {
                    int index = line.lastIndexOf(" ");

                    amount = line.substring(0, line.indexOf(" "));
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

                        addAmountsWithDuplicates(descriptionsNoDuplicateOct15, desc, amount,
                                arrayOfamountOct15);
                    }

                    if (extractMonthFromDate.equals(ELEVEN) && extractYearFromDate.equals(TWOTHOUSANDFIFTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateNov15, desc, amount,
                                arrayOfamountNov15);
                    }

                    if (extractMonthFromDate.equals(TWELVE) && extractYearFromDate.equals(TWOTHOUSANDFIFTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateDec15, desc, amount,
                                arrayOfamountDec15);
                    }

                    if (extractMonthFromDate.equals(ONE) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateJan16, desc, amount,
                                arrayOfamountJan16);
                    }

                    if (extractMonthFromDate.equals(TWO) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateFeb16, desc, amount,
                                arrayOfamountFeb16);
                    }

                    if (extractMonthFromDate.equals(THREE) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateMar16, desc, amount,
                                arrayOfamountMar16);
                    }

                    if (extractMonthFromDate.equals(FOUR) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateApr16, desc, amount,
                                arrayOfamountApr16);
                    }

                    if (extractMonthFromDate.equals(FIVE) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateMay16, desc, amount,
                                arrayOfamountMay16);
                    }

                    if (extractMonthFromDate.equals(SIX) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateJun16, desc, amount,
                                arrayOfamountJun16);
                    }

                    if (extractMonthFromDate.equals(SEVEN) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateJul16, desc, amount,
                                arrayOfamountJul16);
                    }

                    if (extractMonthFromDate.equals(EIGHT) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateAug16, desc, amount,
                                arrayOfamountAug16);
                    }

                    if (extractMonthFromDate.equals(NINE) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateSep16, desc, amount,
                                arrayOfamountSep16);
                    }

                    if (extractMonthFromDate.equals(TEN) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateOct16, desc, amount,
                                arrayOfamountOct16);
                    }

                    if (extractMonthFromDate.equals(ELEVEN) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateNov16, desc, amount,
                                arrayOfamountNov16);
                    }

                    if (extractMonthFromDate.equals(TWELVE) && extractYearFromDate.equals(TWOTHOUSANDSIXTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateDec16, desc, amount,
                                arrayOfamountDec16);
                    }

                    if (extractMonthFromDate.equals(ONE) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateJan17, desc, amount,
                                arrayOfamountJan17);
                    }

                    if (extractMonthFromDate.equals(TWO) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateFeb17, desc, amount,
                                arrayOfamountFeb17);
                    }

                    if (extractMonthFromDate.equals(THREE) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateMar17, desc, amount,
                                arrayOfamountMar17);
                    }

                    if (extractMonthFromDate.equals(FOUR) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateApr17, desc, amount,
                                arrayOfamountApr17);
                    }

                    if (extractMonthFromDate.equals(FIVE) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateMay17, desc, amount,
                                arrayOfamountMay17);
                    }

                    if (extractMonthFromDate.equals(SIX) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateJun17, desc, amount,
                                arrayOfamountJun17);
                    }

                    if (extractMonthFromDate.equals(SEVEN) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateJul17, desc, amount,
                                arrayOfamountJul17);
                    }

                    if (extractMonthFromDate.equals(EIGHT) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateAug17, desc, amount,
                                arrayOfamountAug17);
                    }

                    if (extractMonthFromDate.equals(NINE) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateSep17, desc, amount,
                                arrayOfamountSep17);
                    }

                    if (extractMonthFromDate.equals(TEN) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateOct17, desc, amount,
                                arrayOfamountOct17);
                    }

                    if (extractMonthFromDate.equals(ELEVEN) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateNov17, desc, amount,
                                arrayOfamountNov17);
                    }

                    if (extractMonthFromDate.equals(TWELVE) && extractYearFromDate.equals(TWOTHOUSANDSEVENTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateDec17, desc, amount,
                                arrayOfamountDec17);
                    }

                    if (extractMonthFromDate.equals(ONE) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateJan, desc, amount,
                                arrayOfamountJan);
                    }

                    if (extractMonthFromDate.equals(TWO) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateFeb, desc, amount,
                                arrayOfamountFeb);
                    }

                    if (extractMonthFromDate.equals(THREE) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateMar, desc, amount,
                                arrayOfamountMar);
                    }

                    if (extractMonthFromDate.equals(FOUR) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateApr, desc, amount,
                                arrayOfamountApr);
                    }

                    if (extractMonthFromDate.equals(FIVE) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateMay, desc, amount,
                                arrayOfamountMay);
                    }

                    if (extractMonthFromDate.equals(SIX) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateJun, desc, amount,
                                arrayOfamountJun);
                    }

                    if (extractMonthFromDate.equals(SEVEN) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateJul, desc, amount,
                                arrayOfamountJul);
                    }

                    if (extractMonthFromDate.equals(EIGHT) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateAug, desc, amount,
                                arrayOfamountAug);
                    }

                    if (extractMonthFromDate.equals(NINE) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateSep, desc, amount,
                                arrayOfamountSep);
                    }

                    if (extractMonthFromDate.equals(TEN) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateOct, desc, amount,
                                arrayOfamountOct);
                    }

                    if (extractMonthFromDate.equals(ELEVEN) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateNov, desc, amount,
                                arrayOfamountNov);
                    }

                    if (extractMonthFromDate.equals(TWELVE) && extractYearFromDate.equals(TWOTHOUSANDEIGHTEEN)) {

                        addAmountsWithDuplicates(descriptionsNoDuplicateDec, desc, amount,
                                arrayOfamountDec);
                    }

                    if (descriptionsNoDuplicate.contains(desc)) {
                        int i = 0;
                        for (Iterator<String> s = descriptionsNoDuplicate.iterator(); s.hasNext()
                                ; i++) {
                            String descFound = s.next();
                            if (desc.equals(descFound)) {
                                amount = line.substring(0, line.indexOf(" "));//get the amount
                                String item = line.substring(line.indexOf(" "), index).trim();
                                //get the item
                                amountWithDuplicate = arrayOfamounts.get(i) + Float.valueOf
                                        (amount);//add up the amounts if there are duplicates
                                arrayOfamounts.set(i, amountWithDuplicate);
                                storeAmounts.put(item, amountWithDuplicate);//store the amount to
                                // the item
                            }
                        }
                    } else {
                        descriptionsNoDuplicate.add(desc);
                        arrayOfamounts.add(Float.valueOf(amount));
                        storeAmounts.put(desc, Float.valueOf(amount));
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

    @Override
    public Intent getSupportParentActivityIntent() { // getParentActivityIntent() if you are not
        // using the Support Library
        final Bundle bundle = new Bundle();
        final Intent intent = new Intent(this, MainActivity.class);

        bundle.putInt(BARCHART, 2); // Both constants are defined in your code
        intent.putExtras(bundle);

        return intent;
    }

    private void showExpensesForMonth(String month, LinkedHashSet descriptions, ArrayList<Float>
            arrayAmount) {

        monthLabel.setText(month);
        barDataSet1.clear();
        xAxis.clear();
        int z = 0;
        for (Iterator s = descriptions.iterator(); s.hasNext(); z++) {
            String desc = (String) s.next();
            valueSet1.add(new BarEntry(arrayAmount.get(z), z));
            xAxis.add(desc);
        }
        modifyData();
    }

    private static void addAmountsWithDuplicates(LinkedHashSet descriptions, String desc, String amount,
                                          ArrayList<Float> arrayAmount) {
        if (descriptions.contains(desc)) {
            int i = 0;
            for (Iterator s = descriptions.iterator(); s.hasNext(); i++) {
                String descFound = (String) s.next();
                if (desc.equals(descFound)) {
                    amountWithDuplicate = arrayAmount.get(i) + Float.valueOf(amount);//add up the
                    // amounts if there are duplicates
                    arrayAmount.set(i, amountWithDuplicate);
                    storeAmounts.put(desc, amountWithDuplicate);//store the amount to the item
                }
            }
        } else {
            descriptions.add(desc);
            arrayAmount.add(Float.valueOf(amount));
            storeAmounts.put(desc, Float.valueOf(amount));
        }
    }

    private void calculatePercentagesAndModifyYAxis() {

        switch (monthInt) {
            case -26:
                calculatePercentages(arrayOfamountOct15);
                modifyYAxis();
                break;
            case -25:
                calculatePercentages(arrayOfamountNov15);
                modifyYAxis();
                break;
            case -24:
                calculatePercentages(arrayOfamountDec15);
                modifyYAxis();
                break;
            case -23:
                calculatePercentages(arrayOfamountJan16);
                modifyYAxis();
                break;
            case -22:
                calculatePercentages(arrayOfamountFeb16);
                modifyYAxis();
                break;
            case -21:
                calculatePercentages(arrayOfamountMar16);
                modifyYAxis();
                break;
            case -20:
                calculatePercentages(arrayOfamountApr16);
                modifyYAxis();
                break;
            case -19:
                calculatePercentages(arrayOfamountMay16);
                modifyYAxis();
                break;
            case -18:
                calculatePercentages(arrayOfamountJun16);
                modifyYAxis();
                break;
            case -17:
                calculatePercentages(arrayOfamountJul16);
                modifyYAxis();
                break;
            case -16:
                calculatePercentages(arrayOfamountAug16);
                modifyYAxis();
                break;
            case -15:
                calculatePercentages(arrayOfamountSep16);
                modifyYAxis();
                break;
            case -14:
                calculatePercentages(arrayOfamountOct16);
                modifyYAxis();
                break;
            case -13:
                calculatePercentages(arrayOfamountNov16);
                modifyYAxis();
                break;
            case -12:
                calculatePercentages(arrayOfamountDec16);
                modifyYAxis();
                break;
            case -11:
                calculatePercentages(arrayOfamountJan17);
                modifyYAxis();
                break;
            case -10:
                calculatePercentages(arrayOfamountFeb17);
                modifyYAxis();
                break;
            case -9:
                calculatePercentages(arrayOfamountMar17);
                modifyYAxis();
                break;
            case -8:
                calculatePercentages(arrayOfamountApr17);
                modifyYAxis();
                break;
            case -7:
                calculatePercentages(arrayOfamountMay17);
                modifyYAxis();
                break;
            case -6:
                calculatePercentages(arrayOfamountJun17);
                modifyYAxis();
                break;
            case -5:
                calculatePercentages(arrayOfamountJul17);
                modifyYAxis();
                break;
            case -4:
                calculatePercentages(arrayOfamountAug17);
                modifyYAxis();
                break;
            case -3:
                calculatePercentages(arrayOfamountSep17);
                modifyYAxis();
                break;
            case -2:
                calculatePercentages(arrayOfamountOct17);
                modifyYAxis();
                break;
            case -1:
                calculatePercentages(arrayOfamountNov17);
                modifyYAxis();
                break;
            case 0:
                calculatePercentages(arrayOfamountDec17);
                modifyYAxis();
                break;
            case 1:
                calculatePercentages(arrayOfamountJan);
                modifyYAxis();
                break;
            case 2:
                calculatePercentages(arrayOfamountFeb);
                modifyYAxis();
                break;
            case 3:
                calculatePercentages(arrayOfamountMar);
                modifyYAxis();
                break;
            case 4:
                calculatePercentages(arrayOfamountApr);
                modifyYAxis();
                break;
            case 5:
                calculatePercentages(arrayOfamountMay);
                modifyYAxis();
                break;
            case 6:
                calculatePercentages(arrayOfamountJun);
                modifyYAxis();
                break;
            case 7:
                calculatePercentages(arrayOfamountJul);
                modifyYAxis();
                break;
            case 8:
                calculatePercentages(arrayOfamountAug);
                modifyYAxis();
                break;
            case 9:
                calculatePercentages(arrayOfamountSep);
                modifyYAxis();
                break;
            case 10:
                calculatePercentages(arrayOfamountOct);
                modifyYAxis();
                break;
            case 11:
                calculatePercentages(arrayOfamountNov);
                modifyYAxis();
                break;
            case 12:
                calculatePercentages(arrayOfamountDec);
                modifyYAxis();
                break;
        }
    }

    private void revertToNumbersAndModifyYAxis() {

        switch (monthInt) {
            case -26:
                revertPercentagesToNumbers(arrayOfamountOct15);
                modifyYAxis();
                break;
            case -25:
                revertPercentagesToNumbers(arrayOfamountNov15);
                modifyYAxis();
                break;
            case -24:
                revertPercentagesToNumbers(arrayOfamountDec15);
                modifyYAxis();
                break;
            case -23:
                revertPercentagesToNumbers(arrayOfamountJan16);
                modifyYAxis();
                break;
            case -22:
                revertPercentagesToNumbers(arrayOfamountFeb16);
                modifyYAxis();
                break;
            case -21:
                revertPercentagesToNumbers(arrayOfamountMar16);
                modifyYAxis();
                break;
            case -20:
                revertPercentagesToNumbers(arrayOfamountApr16);
                modifyYAxis();
                break;
            case -19:
                revertPercentagesToNumbers(arrayOfamountMay16);
                modifyYAxis();
                break;
            case -18:
                revertPercentagesToNumbers(arrayOfamountJun16);
                modifyYAxis();
                break;
            case -17:
                revertPercentagesToNumbers(arrayOfamountJul16);
                modifyYAxis();
                break;
            case -16:
                revertPercentagesToNumbers(arrayOfamountAug16);
                modifyYAxis();
                break;
            case -15:
                revertPercentagesToNumbers(arrayOfamountSep16);
                modifyYAxis();
                break;
            case -14:
                revertPercentagesToNumbers(arrayOfamountOct16);
                modifyYAxis();
                break;
            case -13:
                revertPercentagesToNumbers(arrayOfamountNov16);
                modifyYAxis();
                break;
            case -12:
                revertPercentagesToNumbers(arrayOfamountDec16);
                modifyYAxis();
                break;
            case -11:
                revertPercentagesToNumbers(arrayOfamountJan17);
                modifyYAxis();
                break;
            case -10:
                revertPercentagesToNumbers(arrayOfamountFeb17);
                modifyYAxis();
                break;
            case -9:
                revertPercentagesToNumbers(arrayOfamountMar17);
                modifyYAxis();
                break;
            case -8:
                revertPercentagesToNumbers(arrayOfamountApr17);
                modifyYAxis();
                break;
            case -7:
                revertPercentagesToNumbers(arrayOfamountMay17);
                modifyYAxis();
                break;
            case -6:
                revertPercentagesToNumbers(arrayOfamountJun17);
                modifyYAxis();
                break;
            case -5:
                revertPercentagesToNumbers(arrayOfamountJul17);
                modifyYAxis();
                break;
            case -4:
                revertPercentagesToNumbers(arrayOfamountAug17);
                modifyYAxis();
                break;
            case -3:
                revertPercentagesToNumbers(arrayOfamountSep17);
                modifyYAxis();
                break;
            case -2:
                revertPercentagesToNumbers(arrayOfamountOct17);
                modifyYAxis();
                break;
            case -1:
                revertPercentagesToNumbers(arrayOfamountNov17);
                modifyYAxis();
                break;
            case 0:
                revertPercentagesToNumbers(arrayOfamountDec17);
                modifyYAxis();
                break;
            case 1:
                revertPercentagesToNumbers(arrayOfamountJan);
                modifyYAxis();
                break;
            case 2:
                revertPercentagesToNumbers(arrayOfamountFeb);
                modifyYAxis();
                break;
            case 3:
                revertPercentagesToNumbers(arrayOfamountMar);
                modifyYAxis();
                break;
            case 4:
                revertPercentagesToNumbers(arrayOfamountApr);
                modifyYAxis();
                break;
            case 5:
                revertPercentagesToNumbers(arrayOfamountMay);
                modifyYAxis();
                break;
            case 6:
                revertPercentagesToNumbers(arrayOfamountJun);
                modifyYAxis();
                break;
            case 7:
                revertPercentagesToNumbers(arrayOfamountJul);
                modifyYAxis();
                break;
            case 8:
                revertPercentagesToNumbers(arrayOfamountAug);
                modifyYAxis();
                break;
            case 9:
                revertPercentagesToNumbers(arrayOfamountSep);
                modifyYAxis();
                break;
            case 10:
                revertPercentagesToNumbers(arrayOfamountOct);
                modifyYAxis();
                break;
            case 11:
                revertPercentagesToNumbers(arrayOfamountNov);
                modifyYAxis();
                break;
            case 12:
                revertPercentagesToNumbers(arrayOfamountDec);
                modifyYAxis();
                break;
        }
    }

    private void casesToShowExpensesForMonth() {

        switch (monthInt) {
            case -26:
                showExpensesForMonth(OCTOBER+EMPTY_SPACE+TWOTHOUSANDFIFTEEN, descriptionsNoDuplicateOct15,
                        arrayOfamountOct15);
                break;
            case -25:
                showExpensesForMonth(NOVEMBER+EMPTY_SPACE+TWOTHOUSANDFIFTEEN, descriptionsNoDuplicateNov15,
                        arrayOfamountNov15);
                break;
            case -24:
                showExpensesForMonth(DECEMBER+EMPTY_SPACE+TWOTHOUSANDFIFTEEN, descriptionsNoDuplicateDec15,
                        arrayOfamountDec15);
                break;
            case -23:
                showExpensesForMonth(JANUARY+EMPTY_SPACE+TWOTHOUSANDSIXTEEN, descriptionsNoDuplicateJan16,
                        arrayOfamountJan16);
                break;
            case -22:
                showExpensesForMonth(FEBRUARY+EMPTY_SPACE+TWOTHOUSANDSIXTEEN, descriptionsNoDuplicateFeb16,
                        arrayOfamountFeb16);
                break;
            case -21:
                showExpensesForMonth(MARCH+EMPTY_SPACE+TWOTHOUSANDSIXTEEN, descriptionsNoDuplicateMar16,
                        arrayOfamountMar16);
                break;
            case -20:
                showExpensesForMonth(APRIL+EMPTY_SPACE+TWOTHOUSANDSIXTEEN, descriptionsNoDuplicateApr16,
                        arrayOfamountApr16);
                break;
            case -19:
                showExpensesForMonth(MAY+EMPTY_SPACE+TWOTHOUSANDSIXTEEN, descriptionsNoDuplicateMay16, arrayOfamountMay16);
                break;
            case -18:
                showExpensesForMonth(JUNE+EMPTY_SPACE+TWOTHOUSANDSIXTEEN, descriptionsNoDuplicateJun16, arrayOfamountJun16);
                break;
            case -17:
                showExpensesForMonth(JULY+EMPTY_SPACE+TWOTHOUSANDSIXTEEN, descriptionsNoDuplicateJul16, arrayOfamountJul16);
                break;
            case -16:
                showExpensesForMonth(AUGUST+EMPTY_SPACE+TWOTHOUSANDSIXTEEN, descriptionsNoDuplicateAug16,
                        arrayOfamountAug16);
                break;
            case -15:
                showExpensesForMonth(SEPTEMBER+EMPTY_SPACE+TWOTHOUSANDSIXTEEN, descriptionsNoDuplicateSep16,
                        arrayOfamountSep16);
                break;
            case -14:
                showExpensesForMonth(OCTOBER+EMPTY_SPACE+TWOTHOUSANDSIXTEEN, descriptionsNoDuplicateOct16,
                        arrayOfamountOct16);
                break;
            case -13:
                showExpensesForMonth(NOVEMBER+EMPTY_SPACE+TWOTHOUSANDSIXTEEN, descriptionsNoDuplicateNov16,
                        arrayOfamountNov16);
                break;
            case -12:
                showExpensesForMonth(DECEMBER+EMPTY_SPACE+TWOTHOUSANDSIXTEEN, descriptionsNoDuplicateDec16,
                        arrayOfamountDec16);
                break;
            case -11:
                showExpensesForMonth(JANUARY+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN, descriptionsNoDuplicateJan17,
                        arrayOfamountJan17);
                break;
            case -10:
                showExpensesForMonth(FEBRUARY+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN, descriptionsNoDuplicateFeb17,
                        arrayOfamountFeb17);
                break;
            case -9:
                showExpensesForMonth(MARCH+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN, descriptionsNoDuplicateMar17,
                        arrayOfamountMar17);
                break;
            case -8:
                showExpensesForMonth(APRIL+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN, descriptionsNoDuplicateApr17,
                        arrayOfamountApr17);
                break;
            case -7:
                showExpensesForMonth(MAY+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN, descriptionsNoDuplicateMay17, arrayOfamountMay17);
                break;
            case -6:
                showExpensesForMonth(JUNE+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN, descriptionsNoDuplicateJun17, arrayOfamountJun17);
                break;
            case -5:
                showExpensesForMonth(JULY+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN, descriptionsNoDuplicateJul17, arrayOfamountJul17);
                break;
            case -4:
                showExpensesForMonth(AUGUST+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN, descriptionsNoDuplicateAug17,
                        arrayOfamountAug17);
                break;
            case -3:
                showExpensesForMonth(SEPTEMBER+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN, descriptionsNoDuplicateSep17,
                        arrayOfamountSep17);
                break;
            case -2:
                showExpensesForMonth(OCTOBER+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN, descriptionsNoDuplicateOct17,
                        arrayOfamountOct17);
                break;
            case -1:
                showExpensesForMonth(NOVEMBER+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN, descriptionsNoDuplicateNov17,
                        arrayOfamountNov17);
                break;
            case 0:
                showExpensesForMonth(DECEMBER+EMPTY_SPACE+TWOTHOUSANDSEVENTEEN, descriptionsNoDuplicateDec17,
                        arrayOfamountDec17);
                break;
            case 1:
                showExpensesForMonth(JANUARY, descriptionsNoDuplicateJan, arrayOfamountJan);
                break;
            case 2:
                showExpensesForMonth(FEBRUARY, descriptionsNoDuplicateFeb, arrayOfamountFeb);
                break;
            case 3:
                showExpensesForMonth(MARCH, descriptionsNoDuplicateMar, arrayOfamountMar);
                break;
            case 4:
                showExpensesForMonth(APRIL, descriptionsNoDuplicateApr, arrayOfamountApr);
                break;
            case 5:
                showExpensesForMonth(MAY, descriptionsNoDuplicateMay, arrayOfamountMay);
                break;
            case 6:
                showExpensesForMonth(JUNE, descriptionsNoDuplicateJun, arrayOfamountJun);
                break;
            case 7:
                showExpensesForMonth(JULY, descriptionsNoDuplicateJul, arrayOfamountJul);
                break;
            case 8:
                showExpensesForMonth(AUGUST, descriptionsNoDuplicateAug, arrayOfamountAug);
                break;
            case 9:
                showExpensesForMonth(SEPTEMBER, descriptionsNoDuplicateSep, arrayOfamountSep);
                break;
            case 10:
                showExpensesForMonth(OCTOBER, descriptionsNoDuplicateOct, arrayOfamountOct);
                break;
            case 11:
                showExpensesForMonth(NOVEMBER, descriptionsNoDuplicateNov, arrayOfamountNov);
                break;
            case 12:
                showExpensesForMonth(DECEMBER, descriptionsNoDuplicateDec, arrayOfamountDec);
                break;
        }// end of switch
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bar_chart, menu);
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
