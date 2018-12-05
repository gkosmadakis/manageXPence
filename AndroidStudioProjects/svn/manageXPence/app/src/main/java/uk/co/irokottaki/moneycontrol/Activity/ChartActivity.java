package uk.co.irokottaki.moneycontrol.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import uk.co.irokottaki.moneycontrol.R;
import uk.co.irokottaki.moneycontrol.Utils.ChartsUtil;
import uk.co.irokottaki.moneycontrol.Utils.Utils;

import static uk.co.irokottaki.moneycontrol.Utils.Constants.*;

public class ChartActivity extends AppCompatActivity {

    private PieChart mChart;
    private static final Map<String, Float> storeAmounts = new HashMap<>();
    private ArrayList<Entry> yVals1;
    private ArrayList<String> xVals;
    private PieDataSet dataSet;
    private PieData data;
    private int monthInt;
    private int year;
    private boolean stateSwitchButton;
    private TextView monthLabel;
    private ChartsUtil util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_v14);
        setTitle(PIE_CHART);

        //READ THE FILE AND GET THE AMOUNTS FOR EVERY MONTH/YEAR
        util = new ChartsUtil(this);
        util.readTheFile();

        FrameLayout chartLayout = (FrameLayout) findViewById(R.id.chartLayout);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.chartView);

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

        ImageButton arrowLeft = new ImageButton(this);
        arrowLeft = (ImageButton) findViewById(R.id.arrowLeft);

        monthLabel = (TextView) findViewById(R.id.month);

        ImageButton arrowRight = new ImageButton(this);
        arrowRight = (ImageButton) findViewById(R.id.arrowRight);

        //initialize switch button
        Switch toggleAmount = (Switch) findViewById(R.id.toggleButton);
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

                } else {

                    mChart.setUsePercentValues(true);//this calculates the percentages from my
                    // values
                    data.setValueFormatter(new PercentFormatter());//this enables the
                    // percentages, adds % after the number
                    mChart.invalidate();
                    stateSwitchButton = false;
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
        InitializeChart();

        //customize legends
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);

        final Calendar calendar = Calendar.getInstance();//this gets the current month
        String currentMonth = String.format(Locale.UK, "%tB", calendar);
        year = calendar.get(Calendar.YEAR);// get the current year
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
                if (monthInt > 12) {
                    year++;
                    monthInt = 1;
                }
                if (monthInt < 1) {
                    year--;
                    monthInt = 12;
                }
                util.casesToShowExpensesForMonth(monthInt, year, ChartActivity.this);

            }
        });

        arrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                monthInt++;
                if (monthInt > 12) {
                    year++;
                    monthInt = 1;
                }
                if (monthInt < 1) {
                    year--;
                    monthInt = 12;
                }
                util.casesToShowExpensesForMonth(monthInt, year, ChartActivity.this);

            }
        });
        arrowRight.performClick();//this shows the pie chart for the current month
        data.setValueFormatter(new PercentFormatter());

    }// end of create method


    private void InitializeChart() {

        yVals1 = new ArrayList<>();
        xVals = new ArrayList<>();

        //create pie data set
        dataSet = new PieDataSet(yVals1, EXPENSES);
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        //instantiate pie data object
        PieData data = new PieData(xVals, dataSet);
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.GRAY);

        mChart.setData(data);
        //undo all highlights
        mChart.highlightValues(null);
        //update pie chart
        mChart.invalidate();
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

    public ArrayList<Entry> getyVals1() {
        return yVals1;
    }

    public ArrayList<String> getxVals() {
        return xVals;
    }

    public void setDataSet(PieDataSet dataSet) {
        this.dataSet = dataSet;
    }

    public PieDataSet getDataSet() {
        return dataSet;
    }

    public TextView getMonthLabel() {
        return monthLabel;
    }

    public PieData getData() {
        return data;
    }

    public boolean isStateSwitchButton() {
        return stateSwitchButton;
    }

    public void setData(PieData data) {
        this.data = data;
    }

    public PieChart getmChart() {
        return mChart;
    }


}
