package uk.co.irokottaki.moneycontrol.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;

import uk.co.irokottaki.moneycontrol.Model.AmountsFor2015;
import uk.co.irokottaki.moneycontrol.Model.AmountsFor2016;
import uk.co.irokottaki.moneycontrol.Model.AmountsFor2017;
import uk.co.irokottaki.moneycontrol.Model.AmountsFor2018;
import uk.co.irokottaki.moneycontrol.Utils.ChartsUtil;
import uk.co.irokottaki.moneycontrol.R;
import uk.co.irokottaki.moneycontrol.Utils.Utils;

import static uk.co.irokottaki.moneycontrol.Utils.Constants.*;

public class AnnualSavingsActivity extends AppCompatActivity implements OnChartGestureListener,
        OnChartValueSelectedListener {

    private LineChart mChart;
    ImageButton leftYearButton;
    ImageButton rightYearButton;
    private int year;
    private Float incomeForJan;
    private Float incomeForFeb;
    private Float incomeForMar;
    private Float incomeForApr;
    private Float incomeForMay;
    private Float incomeForJun;
    private Float incomeForJul;
    private Float incomeForAug;
    private Float incomeForSep;
    private Float incomeForOct;
    private Float incomeForNov;
    private Float incomeForDec;
    private TextView yearView;
    private ChartsUtil util;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annual_savings);
        setTitle(SAVINGS_CHART);

        //READ THE FILE AND GET THE AMOUNTS FOR EVERY MONTH/YEAR
        util = new ChartsUtil(this);
        util.readTheFile();

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.annualSavingsView);

        //this is to change the background color of the activity when user changes it from settings
        Utils utils = new Utils(this);
        utils.setBackgroundAndAdjustLayout(layout, AnnualSavingsActivity.this);

        mChart = (LineChart) findViewById(R.id.annualSavingsLayout);
        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // Arrow buttons
        leftYearButton = new ImageButton(this);
        leftYearButton = (ImageButton) findViewById(R.id.leftYearButton);

        rightYearButton = new ImageButton(this);
        rightYearButton = (ImageButton) findViewById(R.id.rigthYearButton);

        //Year Label
        yearView = (TextView) findViewById(R.id.yearlabelSavings);

        //retrieve the incomes from Main Activity
        incomeForJan = MainActivity.getIncomeForJan();
        incomeForFeb = MainActivity.getIncomeForFeb();
        incomeForMar = MainActivity.getIncomeForMar();
        incomeForApr = MainActivity.getIncomeForApr();
        incomeForMay = MainActivity.getIncomeForMay();
        incomeForJun = MainActivity.getIncomeForJun();
        incomeForJul = MainActivity.getIncomeForJul();
        incomeForAug = MainActivity.getIncomeForAug();
        incomeForSep = MainActivity.getIncomeForSep();
        incomeForOct = MainActivity.getIncomeForOct();
        incomeForNov = MainActivity.getIncomeForNov();
        incomeForDec = MainActivity.getIncomeForDec();

        XAxis xAxis = mChart.getXAxis();
        xAxis.setSpaceBetweenLabels(1);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines

        leftAxis.setAxisMaxValue(3000f);
        leftAxis.setAxisMinValue(-50f);
        leftAxis.setStartAtZero(false);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);// get the current year
        yearView.setText(YEAR + year);

        //i am just setting the data for 2017 that will be displayed on initializing the activity
        // . Obviously it should be changed every year
        setSavings(util.getObjectYear().getYear2017(), util.getObjectYear().getYear2018());

        //Listener to events on clicking the image arrows
        leftYearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year--;

                showSavings(util.getObjectYear().getYear2015(), util.getObjectYear().getYear2016(),
                        util.getObjectYear().getYear2017(), util.getObjectYear().getYear2018());
            }
        });

        rightYearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year++;

                showSavings(util.getObjectYear().getYear2015(), util.getObjectYear().getYear2016(),
                        util.getObjectYear().getYear2017(), util.getObjectYear().getYear2018());
            }
        });

        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend
        l.setForm(Legend.LegendForm.LINE);

    }// end of onCreate

    private void setSavings(AmountsFor2017 obj2017, AmountsFor2018 obj2018) {
        if (incomeForJan != null || incomeForFeb != null || incomeForMar != null || incomeForApr
                != null || incomeForMay != null
                || incomeForJun != null || incomeForJul != null || incomeForAug != null ||
                incomeForSep != null || incomeForOct != null ||
                incomeForNov != null || incomeForDec != null) {
            if (year == 2017) {
                setData(incomeForJan - obj2017.getAmountJan(), incomeForFeb - obj2017.getAmountFeb(), incomeForMar -
                                obj2017.getAmountMar(), incomeForApr - obj2017.getAmountApr(),
                        incomeForMay - obj2017.getAmountMay(), incomeForJun - obj2017.getAmountJun(), incomeForJul -
                                obj2017.getAmountJul(), incomeForAug - obj2017.getAmountAug(),
                        incomeForSep - obj2017.getAmountSep(), incomeForOct - obj2017.getAmountOct(), incomeForNov -
                                obj2017.getAmountNov(), incomeForDec - obj2017.getAmountDec());
            } else if (year == 2018) {
                setData(incomeForJan - obj2018.getAmountJan(), incomeForFeb - obj2018.getAmountFeb(), incomeForMar -
                                obj2018.getAmountMar(), incomeForApr - obj2018.getAmountApr(),
                        incomeForMay - obj2018.getAmountMay(), incomeForJun - obj2018.getAmountJun(), incomeForJul -
                                obj2018.getAmountJul(), incomeForAug - obj2018.getAmountAug(),
                        incomeForSep - obj2018.getAmountSep(), incomeForOct - obj2018.getAmountOct(), incomeForNov -
                                obj2018.getAmountNov(), incomeForDec - obj2018.getAmountDec());

            }
        } else {
            setData(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f);
        }
    }

    private void setData(Float jan, Float feb, Float mar, Float apr, Float may, Float jun, Float jul,
                         Float aug, Float sep, Float oct, Float nov, Float dec) {

        String [] monthData = new String[]{JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER,
                OCTOBER, NOVEMBER, DECEMBER};
        ArrayList<String> xVals = new ArrayList<>();
        for (String aMonthData : monthData) {
            xVals.add((aMonthData).substring(0, 3));
        }

        ArrayList<Entry> yVals = new ArrayList<>();

        ArrayList<Float> addAlltheMonths = new ArrayList<>();
        addAlltheMonths.add(jan);
        addAlltheMonths.add(feb);
        addAlltheMonths.add(mar);
        addAlltheMonths.add(apr);
        addAlltheMonths.add(may);
        addAlltheMonths.add(jun);
        addAlltheMonths.add(jul);
        addAlltheMonths.add(aug);
        addAlltheMonths.add(sep);
        addAlltheMonths.add(oct);
        addAlltheMonths.add(nov);
        addAlltheMonths.add(dec);

        for (int i = 0; i < monthData.length; i++) {
            float val = addAlltheMonths.get(i);
            yVals.add(new Entry(val, i));
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, ANNUAL_SAVINGS);

        // set the line to be drawn like this "- - - - - -"
        set1.enableDashedLine(10f, 5f, 0f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLUE);
        set1.setCircleColor(Color.BLUE);
        set1.setLineWidth(1f);
        set1.setCircleSize(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setFillAlpha(65);
        set1.setFillColor(Color.BLACK);

        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        mChart.setData(data);
        //update chart
        mChart.invalidate();
    }


    @SuppressLint("SetTextI18n")
    private void showSavings(AmountsFor2015 obj2015, AmountsFor2016 obj2016, AmountsFor2017 obj2017, AmountsFor2018 obj2018) {

        switch (year) {
            case 2018:
                yearView.setText(YEAR + year);
                if (incomeForJan != null || incomeForFeb != null || incomeForMar != null ||
                        incomeForApr != null || incomeForMay != null
                        || incomeForJun != null || incomeForJul != null || incomeForAug != null
                        || incomeForSep != null || incomeForOct != null ||
                        incomeForNov != null || incomeForDec != null) {
                    setData(incomeForJan - obj2018.getAmountJan(), incomeForFeb - obj2018.getAmountFeb(), incomeForMar -
                                    obj2018.getAmountMar(), incomeForApr - obj2018.getAmountApr(),
                            incomeForMay - obj2018.getAmountMay(), incomeForJun - obj2018.getAmountJun(), incomeForJul -
                                    obj2018.getAmountJul(), incomeForAug - obj2018.getAmountAug(),
                            incomeForSep - obj2018.getAmountSep(), incomeForOct - obj2018.getAmountOct(), incomeForNov -
                                    obj2018.getAmountNov(), incomeForDec - obj2018.getAmountDec());
                } else {
                    setData(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f);
                }

                break;

            case 2017:
                yearView.setText(YEAR + year);
                if (incomeForJan != null || incomeForFeb != null || incomeForMar != null ||
                        incomeForApr != null || incomeForMay != null
                        || incomeForJun != null || incomeForJul != null || incomeForAug != null
                        || incomeForSep != null || incomeForOct != null ||
                        incomeForNov != null || incomeForDec != null) {
                    setData(incomeForJan - obj2017.getAmountJan(), incomeForFeb - obj2017.getAmountFeb(), incomeForMar -
                                    obj2017.getAmountMar(), incomeForApr - obj2017.getAmountApr(),
                            incomeForMay - obj2017.getAmountMay(), incomeForJun - obj2017.getAmountJun(), incomeForJul -
                                    obj2017.getAmountJul(), incomeForAug - obj2017.getAmountAug(),
                            incomeForSep - obj2017.getAmountSep(), incomeForOct - obj2017.getAmountOct(), incomeForNov -
                                    obj2017.getAmountNov(), incomeForDec - obj2017.getAmountDec());
                } else {
                    setData(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f);
                }

                break;

            case 2016:
                yearView.setText(YEAR + year);
                if (incomeForJan != null || incomeForFeb != null || incomeForMar != null ||
                        incomeForApr != null || incomeForMay != null
                        || incomeForJun != null || incomeForJul != null || incomeForAug != null
                        || incomeForSep != null || incomeForOct != null ||
                        incomeForNov != null || incomeForDec != null) {
                    setData(incomeForJan - obj2016.getAmountJan(), incomeForFeb - obj2016.getAmountFeb(), incomeForMar
                                    - obj2016.getAmountMar(), incomeForApr - obj2016.getAmountApr(),
                            incomeForMay - obj2016.getAmountMay(), incomeForJun - obj2016.getAmountJun(), incomeForJul
                                    - obj2016.getAmountJul(), incomeForAug - obj2016.getAmountAug(),
                            incomeForSep - obj2016.getAmountSep(), incomeForOct - obj2016.getAmountOct(), incomeForNov
                                    - obj2016.getAmountNov(), incomeForDec - obj2016.getAmountDec());
                } else {
                    setData(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f);
                }

                break;

            case 2015:
                yearView.setText(YEAR + year);
                if (incomeForJan != null || incomeForFeb != null || incomeForMar != null ||
                        incomeForApr != null || incomeForMay != null
                        || incomeForJun != null || incomeForJul != null || incomeForAug != null
                        || incomeForSep != null || incomeForOct != null ||
                        incomeForNov != null || incomeForDec != null) {
                    setData(incomeForJan - obj2015.getAmountJan(), incomeForFeb - obj2015.getAmountFeb(), incomeForMar
                                    - obj2015.getAmountMar(), incomeForApr - obj2015.getAmountApr(),
                            incomeForMay - obj2015.getAmountMay(), incomeForJun - obj2015.getAmountJun(), incomeForJul
                                    - obj2015.getAmountJul(), incomeForAug - obj2015.getAmountAug(),
                            incomeForSep - obj2015.getAmountSep(), incomeForOct - obj2015.getAmountOct(), incomeForNov
                                    - obj2015.getAmountNov(), incomeForDec - obj2015.getAmountDec());
                } else {
                    setData(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f);
                }

                break;
        }
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        final Bundle bundle = new Bundle();
        final Intent intent = new Intent(this, MainActivity.class);

        bundle.putInt(SAVINGSCHART, 2);
        intent.putExtras(bundle);

        return intent;
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


    @Override
    public void onChartGestureStart(MotionEvent motionEvent, ChartTouchListener.ChartGesture
            chartGesture) {
        /*Not used*/
    }

    @Override
    public void onChartGestureEnd(MotionEvent motionEvent, ChartTouchListener.ChartGesture
            chartGesture) {
        /*Not used*/
    }

    @Override
    public void onChartLongPressed(MotionEvent motionEvent) {
        /*Not used*/
    }

    @Override
    public void onChartDoubleTapped(MotionEvent motionEvent) {
        /*Not used*/
    }

    @Override
    public void onChartSingleTapped(MotionEvent motionEvent) {
        /*Not used*/
    }

    @Override
    public void onChartFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        /*Not used*/
    }

    @Override
    public void onChartScale(MotionEvent motionEvent, float v, float v1) {
        /*Not used*/
    }

    @Override
    public void onChartTranslate(MotionEvent motionEvent, float v, float v1) {
        /*Not used*/
    }

    @Override
    public void onValueSelected(Entry entry, int i, Highlight highlight) {
        /*Not used*/
    }

    @Override
    public void onNothingSelected() {
        /*Not used*/
    }


}
