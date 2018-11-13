package uk.co.irokottaki.moneycontrol.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import uk.co.irokottaki.moneycontrol.R;
import uk.co.irokottaki.moneycontrol.Utils.Utils;

import static uk.co.irokottaki.moneycontrol.Utils.Constants.*;

public class ChartActivity extends AppCompatActivity {

    private FrameLayout chartLayout;
    private PieChart mChart;
    //my data
    private static ArrayList<Float> arrayOfamounts, arrayOfamountOct15, arrayOfamountNov15,
            arrayOfamountDec15, arrayOfamountJan16, arrayOfamountFeb16,
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
    private static LinkedHashSet<String> descriptionsNoDuplicate, descriptionsNoDuplicateOct15,
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
            descriptionsNoDuplicateDec;//all the descriptions for the months
    private static final Map<String, Float> storeAmounts = new HashMap<>();
    private static float amountWithDuplicate;
    private ImageButton arrowLeft, arrowRight;
    private ArrayList<Entry> yVals1;
    private ArrayList<String> xVals;
    private PieDataSet dataSet;
    private PieData data;
    private int monthInt;
    private Switch toggleAmount;
    private boolean stateSwitchButton;
    private RelativeLayout layout;
    private PreferenceManager mPreferenceManager;
    private TextView monthLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_v14);
        setTitle(PIE_CHART);

        readTheFile();
        chartLayout = (FrameLayout) findViewById(R.id.chartLayout);

        layout = (RelativeLayout) findViewById(R.id.chartView);

        //this is to change the background color of the activity when user changes it from settings
        Utils utils = new Utils(this);
        utils.setBackgroundAndAdjustLayout(layout, ChartActivity.this);

        mChart = new PieChart(this);
        //add pie chart to main layout

        chartLayout.addView(mChart);
        chartLayout.setBackgroundColor(Color.TRANSPARENT);

        //configure pie chart
        mChart.setUsePercentValues(true);
        // will be the descriptions from the array mChart.setDescription();
        mChart.setDescription("");

        //enable hole and configure
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(7);
        mChart.setTransparentCircleRadius(10);

        //enable rotation of the chart and touch
        mChart.setRotation(0);
        mChart.setRotationEnabled(true);

        arrowLeft = new ImageButton(this);
        arrowLeft = (ImageButton) findViewById(R.id.arrowLeft);

        monthLabel = (TextView) findViewById(R.id.month);

        arrowRight = new ImageButton(this);
        arrowRight = (ImageButton) findViewById(R.id.arrowRight);

        //initialize switch button
        toggleAmount = (Switch) findViewById(R.id.toggleButton);
        toggleAmount.setChecked(false);

        // switch button listener
        toggleAmount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //i set percent values to false
                    mChart.setUsePercentValues(false);
                    data.setValueFormatter(new ValueFormatter() {
                        //this returns the values as floats
                        @Override
                        public String getFormattedValue(float v, Entry entry, int i,
                                                        ViewPortHandler viewPortHandler) {
                            return Float.toString(v);
                        }
                    });
                    mChart.invalidate();//refresh the chart
                    stateSwitchButton = true;// i use this to know the state of the switch
                    //Toast.makeText(getApplicationContext(), "The switch is ON", Toast
                    // .LENGTH_SHORT).show();
                } else {

                    mChart.setUsePercentValues(true);//this calculates the percentages from my
                    // values
                    data.setValueFormatter(new PercentFormatter());//this enables the
                    // percentages, adds % after the number
                    mChart.invalidate();
                    stateSwitchButton = false;
                    //Toast.makeText(getApplicationContext(), "The switch is OFF", Toast
                    // .LENGTH_SHORT).show();
                }
            }
        });
        //set a chart value selected listener
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {
                //display msg when selected
                if (entry == null)
                    return;
                Toast.makeText(ChartActivity.this, xVals.get(entry.getXIndex()) + ": " + entry
                        .getVal(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        //add data
        addData();

        //customize legends
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);

        final Calendar calendar = Calendar.getInstance();//this gets the current month
        //SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String currentMonth = String.format(Locale.UK, "%tB", calendar);
        //month_date.format(calendar.getTime());
        monthLabel.setText(currentMonth);// and displays it on the month field

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
        arrowRight.performClick();//this shows the pie chart for the current month
        data.setValueFormatter(new PercentFormatter());

    }// end of create method


    private void addData() {
        yVals1 = new ArrayList<>();
        xVals = new ArrayList<>();
        int i = 0;
        for (Map.Entry<String, Float> entry : storeAmounts.entrySet()) {
            //i want to pass only the values that are >0, otherwise the expenses
            //that the user has added
            i++;
            if (entry.getValue() > 0) {
                //add getkey=description-getvalue=amount in the result set
                //yVals1.add(new Entry(entry.getValue(), i));
                //xVals.add(entry.getKey());
            }
        }
        //create pie data set
        dataSet = new PieDataSet(yVals1, EXPENSES);
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);
        addColors();


        //instantiate pie data object
        PieData data = new PieData(xVals, dataSet);
        //data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.GRAY);

        mChart.setData(data);
        //undo all highlights
        mChart.highlightValues(null);
        //update pie chart
        mChart.invalidate();
    }

    private void addColors() {
        //add many colors
        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
    }

    private void modifyData() {
        addColors();
        data = new PieData(xVals, dataSet);

        //this is called every time the user presses the arrows left or right
        if (stateSwitchButton) {
            mChart.setUsePercentValues(false);//if switch is ON it shows numbers not percentages
            data.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler
                        viewPortHandler) {
                    return Float.toString(v);
                }
            });
        } else {

            mChart.setUsePercentValues(true);//if switch is OFF it shows percentages
            data.setValueFormatter(new PercentFormatter());
        }

        data.setValueTextSize(12f);//this is the size of the percentages/numbers
        data.setValueTextColor(Color.GRAY);
        mChart.setDrawSliceText(false);//this removes the descriptions from every slice

        mChart.setData(data);
        //undo all highlights
        mChart.highlightValues(null);
        //update pie chart
        mChart.invalidate();
    }

    public static void readTheFile() {
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
        descriptionsNoDuplicateOct15 = new LinkedHashSet<>();
        descriptionsNoDuplicateNov15 = new LinkedHashSet<>();
        descriptionsNoDuplicateDec15 = new LinkedHashSet<>();
        descriptionsNoDuplicateJan16 = new LinkedHashSet<>();//store unique descriptions
        descriptionsNoDuplicateFeb16 = new LinkedHashSet<>();
        descriptionsNoDuplicateMar16 = new LinkedHashSet<>();
        descriptionsNoDuplicateApr16 = new LinkedHashSet<>();
        descriptionsNoDuplicateMay16 = new LinkedHashSet<>();//store unique descriptions
        descriptionsNoDuplicateJun16 = new LinkedHashSet<>();
        descriptionsNoDuplicateJul16 = new LinkedHashSet<>();
        descriptionsNoDuplicateAug16 = new LinkedHashSet<>();
        descriptionsNoDuplicateSep16 = new LinkedHashSet<>();//store unique descriptions
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
            Scanner in = new Scanner(inputStream);
            int lineIndex = 0;//this is to count the lines
            while (in.hasNextLine()) {

                String line = in.nextLine();
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

    private void showExpensesForMonth(String month, LinkedHashSet descriptions, ArrayList<Float>
            arrayAmount) {

        monthLabel.setText(month);
        dataSet.clear();
        xVals.clear();
        int x = 0;
        for (Iterator s = descriptions.iterator(); s.hasNext(); x++) {
            String desc = (String) s.next();
            yVals1.add(new Entry(arrayAmount.get(x), x));
            xVals.add(desc);
        }
        dataSet = new PieDataSet(yVals1, EXPENSES_FOR + month);
        modifyData();
    }

    private static void addAmountsWithDuplicates(LinkedHashSet descriptions, String desc, String
            amount, ArrayList<Float> arrayAmount) {
        if (descriptions.contains(desc)) {
            int i = 0;
            for (Iterator s = descriptions.iterator(); s.hasNext(); i++) {
                String descFound = (String) s.next();
                if (desc.equals(descFound)) {
                    amountWithDuplicate = arrayAmount.get(i) + Float.valueOf(amount);//add up the
                    // amounts if there are duplicates
                    arrayAmount.set(i, amountWithDuplicate);
                }
            }
        } else {
            descriptions.add(desc);
            arrayAmount.add(Float.valueOf(amount));
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
        getMenuInflater().inflate(R.menu.menu_chart, menu);
        return true;
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        final Bundle bundle = new Bundle();
        final Intent intent = new Intent(this, MainActivity.class);

        bundle.putInt(CHART, 2);
        intent.putExtras(bundle);

        return intent;
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

    public static LinkedHashSet getDescriptionsOfOct15() {
        return descriptionsNoDuplicateOct15;
    }

    public static LinkedHashSet getDescriptionsOfNov15() {
        return descriptionsNoDuplicateNov15;
    }

    public static LinkedHashSet getDescriptionsOfDec15() {
        return descriptionsNoDuplicateDec15;
    }

    public static LinkedHashSet getDescriptionsOfJan16() {
        return descriptionsNoDuplicateJan16;
    }

    public static LinkedHashSet getDescriptionsOfFeb16() {
        return descriptionsNoDuplicateFeb16;
    }

    public static LinkedHashSet getDescriptionsOfMar16() {
        return descriptionsNoDuplicateMar16;
    }

    public static LinkedHashSet getDescriptionsOfApr16() {
        return descriptionsNoDuplicateApr16;
    }

    public static LinkedHashSet getDescriptionsOfMay16() {
        return descriptionsNoDuplicateMay16;
    }

    public static LinkedHashSet getDescriptionsOfJun16() {
        return descriptionsNoDuplicateJun16;
    }

    public static LinkedHashSet getDescriptionsOfJul16() {
        return descriptionsNoDuplicateJul16;
    }

    public static LinkedHashSet getDescriptionsOfAug16() {
        return descriptionsNoDuplicateAug16;
    }

    public static LinkedHashSet getDescriptionsOfSep16() {
        return descriptionsNoDuplicateSep16;
    }

    public static LinkedHashSet getDescriptionsOfOct16() {
        return descriptionsNoDuplicateOct16;
    }

    public static LinkedHashSet getDescriptionsOfNov16() {
        return descriptionsNoDuplicateNov16;
    }

    public static LinkedHashSet getDescriptionsOfDec16() {
        return descriptionsNoDuplicateDec16;
    }

    public static LinkedHashSet getDescriptionsOfJan17() {
        return descriptionsNoDuplicateJan17;
    }

    public static LinkedHashSet getDescriptionsOfFeb17() {
        return descriptionsNoDuplicateFeb17;
    }

    public static LinkedHashSet getDescriptionsOfMar17() {
        return descriptionsNoDuplicateMar17;
    }

    public static LinkedHashSet getDescriptionsOfApr17() {
        return descriptionsNoDuplicateApr17;
    }

    public static LinkedHashSet getDescriptionsOfMay17() {
        return descriptionsNoDuplicateMay17;
    }

    public static LinkedHashSet getDescriptionsOfJun17() {
        return descriptionsNoDuplicateJun17;
    }

    public static LinkedHashSet getDescriptionsOfJul17() {
        return descriptionsNoDuplicateJul17;
    }

    public static LinkedHashSet getDescriptionsOfAug17() {
        return descriptionsNoDuplicateAug17;
    }

    public static LinkedHashSet getDescriptionsOfSep17() {
        return descriptionsNoDuplicateSep17;
    }

    public static LinkedHashSet getDescriptionsOfOct17() {
        return descriptionsNoDuplicateOct17;
    }

    public static LinkedHashSet getDescriptionsOfNov17() {
        return descriptionsNoDuplicateNov17;
    }

    public static LinkedHashSet getDescriptionsOfDec17() {
        return descriptionsNoDuplicateDec17;
    }

    public static LinkedHashSet getDescriptionsOfJan() {
        return descriptionsNoDuplicateJan;
    }

    public static LinkedHashSet getDescriptionsOfFeb() {
        return descriptionsNoDuplicateFeb;
    }

    public static LinkedHashSet getDescriptionsOfMar() {
        return descriptionsNoDuplicateMar;
    }

    public static LinkedHashSet getDescriptionsOfApr() {
        return descriptionsNoDuplicateApr;
    }

    public static LinkedHashSet getDescriptionsOfMay() {
        return descriptionsNoDuplicateMay;
    }

    public static LinkedHashSet getDescriptionsOfJun() {
        return descriptionsNoDuplicateJun;
    }

    public static LinkedHashSet getDescriptionsOfJul() {
        return descriptionsNoDuplicateJul;
    }

    public static LinkedHashSet getDescriptionsOfAug() {
        return descriptionsNoDuplicateAug;
    }

    public static LinkedHashSet getDescriptionsOfSep() {
        return descriptionsNoDuplicateSep;
    }

    public static LinkedHashSet getDescriptionsOfOct() {
        return descriptionsNoDuplicateOct;
    }

    public static LinkedHashSet getDescriptionsOfNov() {
        return descriptionsNoDuplicateNov;
    }

    public static LinkedHashSet getDescriptionsOfDec() {
        return descriptionsNoDuplicateDec;
    }

    public static ArrayList getAmountsOfOct15() {
        return arrayOfamountOct15;
    }

    public static ArrayList getAmountsOfNov15() {
        return arrayOfamountNov15;
    }

    public static ArrayList getAmountsOfDec15() {
        return arrayOfamountDec15;
    }

    public static ArrayList getAmountsOfJan16() {
        return arrayOfamountJan16;
    }

    public static ArrayList getAmountsOfFeb16() {
        return arrayOfamountFeb16;
    }

    public static ArrayList getAmountsOfMar16() {
        return arrayOfamountMar16;
    }

    public static ArrayList getAmountsOfApr16() {
        return arrayOfamountApr16;
    }

    public static ArrayList getAmountsOfMay16() {
        return arrayOfamountMay16;
    }

    public static ArrayList getAmountsOfJun16() {
        return arrayOfamountJun16;
    }

    public static ArrayList getAmountsOfJul16() {
        return arrayOfamountJul16;
    }

    public static ArrayList getAmountsOfAug16() {
        return arrayOfamountAug16;
    }

    public static ArrayList getAmountsOfSep16() {
        return arrayOfamountSep16;
    }

    public static ArrayList getAmountsOfOct16() {
        return arrayOfamountOct16;
    }

    public static ArrayList getAmountsOfNov16() {
        return arrayOfamountNov16;
    }

    public static ArrayList getAmountsOfDec16() {
        return arrayOfamountDec16;
    }

    public static ArrayList getAmountsOfJan17() {
        return arrayOfamountJan17;
    }

    public static ArrayList getAmountsOfFeb17() {
        return arrayOfamountFeb17;
    }

    public static ArrayList getAmountsOfMar17() {
        return arrayOfamountMar17;
    }

    public static ArrayList getAmountsOfApr17() {
        return arrayOfamountApr17;
    }

    public static ArrayList getAmountsOfMay17() {
        return arrayOfamountMay17;
    }

    public static ArrayList getAmountsOfJun17() {
        return arrayOfamountJun17;
    }

    public static ArrayList getAmountsOfJul17() {
        return arrayOfamountJul17;
    }

    public static ArrayList getAmountsOfAug17() {
        return arrayOfamountAug17;
    }

    public static ArrayList getAmountsOfSep17() {
        return arrayOfamountSep17;
    }

    public static ArrayList getAmountsOfOct17() {
        return arrayOfamountOct17;
    }

    public static ArrayList getAmountsOfNov17() {
        return arrayOfamountNov17;
    }

    public static ArrayList getAmountsOfDec17() {
        return arrayOfamountDec17;
    }

    public static ArrayList getAmountsOfJan() {
        return arrayOfamountJan;
    }

    public static ArrayList getAmountsOfFeb() {
        return arrayOfamountFeb;
    }

    public static ArrayList getAmountsOfMar() {
        return arrayOfamountMar;
    }

    public static ArrayList getAmountsOfApr() {
        return arrayOfamountApr;
    }

    public static ArrayList getAmountsOfMay() {
        return arrayOfamountMay;
    }

    public static ArrayList getAmountsOfJun() {
        return arrayOfamountJun;
    }

    public static ArrayList getAmountsOfJul() {
        return arrayOfamountJul;
    }

    public static ArrayList getAmountsOfAug() {
        return arrayOfamountAug;
    }

    public static ArrayList getAmountsOfSep() {
        return arrayOfamountSep;
    }

    public static ArrayList getAmountsOfOct() {
        return arrayOfamountOct;
    }

    public static ArrayList getAmountsOfNov() {
        return arrayOfamountNov;
    }

    public static ArrayList getAmountsOfDec() {
        return arrayOfamountDec;
    }


}
