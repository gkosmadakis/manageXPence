package uk.co.irokottaki.moneycontrol.activity;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.List;

import uk.co.irokottaki.moneycontrol.R;
import uk.co.irokottaki.moneycontrol.utils.ChartsUtil;
import uk.co.irokottaki.moneycontrol.utils.Utils;

import static uk.co.irokottaki.moneycontrol.utils.Constants.EXPENSES;
import static uk.co.irokottaki.moneycontrol.utils.Constants.PIE_CHART;

public class ChartActivity extends AppCompatActivity {

    private PieChart mChart;
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
        // will be the descriptions from the array
        mChart.setDescription("");

        //enable hole and configure
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(7);
        mChart.setTransparentCircleRadius(10);

        //enable rotation of the chart and touch
        mChart.setRotation(0);
        mChart.setRotationEnabled(true);

        ImageButton arrowLeft = (ImageButton) findViewById(R.id.arrowLeft);

        monthLabel = (TextView) findViewById(R.id.month);

        ImageButton arrowRight = (ImageButton) findViewById(R.id.arrowRight);

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
                    if (data != null) {
                        data.setValueFormatter(new ValueFormatter() {
                            //this returns the values as floats
                            @Override
                            public String getFormattedValue(float v, Entry entry, int i,
                                                            ViewPortHandler viewPortHandler) {
                                return Float.toString(v);
                            }
                        });
                    }
                    mChart.invalidate();//refresh the chart
                    stateSwitchButton = true;// i use this to know the state of the switch

                } else {

                    mChart.setUsePercentValues(true);//this calculates the percentages from my
                    // values
                    if (data != null) {
                        data.setValueFormatter(new PercentFormatter());//this enables the
                        // percentages, adds % after the number
                    }
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
                /* Not used */
            }
        });
        //add data
        initializeChart();

        //customize legends
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);

        util.displayMonthToLabel(this);

        arrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.clickRightOrLeft(ChartActivity.this, true);
            }
        });

        arrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.clickRightOrLeft(ChartActivity.this, false);
            }
        });
        arrowRight.performClick();//this shows the pie chart for the current month
        if (data!= null) {
            data.setValueFormatter(new PercentFormatter());
        }
    }// end of create method


    private void initializeChart() {

        yVals1 = new ArrayList<>();
        xVals = new ArrayList<>();

        //create pie data set
        dataSet = new PieDataSet(yVals1, EXPENSES);
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        //instantiate pie data object
        PieData pieData = new PieData(xVals, dataSet);
        pieData.setValueTextSize(15f);
        pieData.setValueTextColor(Color.GRAY);

        mChart.setData(pieData);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();// This is added to avoid the call on the onCreate method of MainActivity because it will read again the file and iterate the map
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public List<Entry> getyVals1() {
        return yVals1;
    }

    public List<String> getxVals() {
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

    public int getMonthInt() {
        return monthInt;
    }

    public int getYearInt() {
        return year;
    }

    public void setMonthInt(int monthInt) {
        this.monthInt = monthInt;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
