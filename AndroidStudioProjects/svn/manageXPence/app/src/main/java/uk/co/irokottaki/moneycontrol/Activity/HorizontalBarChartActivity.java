package uk.co.irokottaki.moneycontrol.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.co.irokottaki.moneycontrol.R;
import uk.co.irokottaki.moneycontrol.model.AnyYear;
import uk.co.irokottaki.moneycontrol.utils.ChartsUtil;
import uk.co.irokottaki.moneycontrol.utils.Utils;

import static uk.co.irokottaki.moneycontrol.utils.Constants.BAR_CHART;
import static uk.co.irokottaki.moneycontrol.utils.Constants.EXPENSE;

public class HorizontalBarChartActivity extends AppCompatActivity {

    private ArrayList<BarDataSet> dataSets;
    private HorizontalBarChart chart;
    private BarData data;
    private ArrayList<BarEntry> valueSet1;
    private ArrayList<String> xAxis;
    private BarDataSet barDataSet1;
    private int monthInt;
    private int year;
    private boolean stateSwitchButton;
    private TextView monthLabel;
    private ChartsUtil util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_bar_chart);
        setTitle(BAR_CHART);

        util = new ChartsUtil(this);

        RelativeLayout barLayout = (RelativeLayout) findViewById(R.id.barChartView);

        //this is to change the background color of the activity when user changes it from settings
        Utils utils = new Utils(this);
        utils.setBackgroundAndAdjustLayout(barLayout, HorizontalBarChartActivity.this);

        ImageButton  arrowLeft = (ImageButton) findViewById(R.id.arrowLeft);

        monthLabel = (TextView) findViewById(R.id.month);

        ImageButton  arrowRight = (ImageButton) findViewById(R.id.arrowRight);

        chart = (HorizontalBarChart) findViewById(R.id.chart);
        valueSet1 = new ArrayList<>();
        xAxis = new ArrayList<>();

        Switch toggleAmount = (Switch) findViewById(R.id.switchButton);
        toggleAmount.setChecked(false);

        dataSets = new ArrayList<>();
        data = new BarData(xAxis, dataSets);// initialize data to avoid null pointers

        util.displayMonthToLabel(this);

        // switch button listener
        toggleAmount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (monthInt > 12) {
                    year++;
                    monthInt = 1;
                }
                if (monthInt < 1) {
                    year--;
                    monthInt = 12;
                }
                Intent intent = getIntent();
                HashMap<String, AnyYear> yearsMappedToObjectYearsMap = (HashMap<String, AnyYear> ) intent.getSerializableExtra("yearsMappedToObjectYearsMap");

                showBarChartAccordingToTheSwitchBtn(isChecked, yearsMappedToObjectYearsMap);
            }
        });


        barDataSet1 = new BarDataSet(valueSet1, EXPENSE);//i initialize it here in order to avoid getting null pointer inside the switch

        arrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.clickRightOrLeft(HorizontalBarChartActivity.this, true);

            }
        });

        arrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.clickRightOrLeft( HorizontalBarChartActivity.this, false);
            }
        });
        arrowRight.performClick();
        data = new BarData(xAxis, dataSets);

    }//end of create method

    private void showBarChartAccordingToTheSwitchBtn(boolean isChecked, HashMap<String, AnyYear> yearsMappedToObjectYearsMap) {
        if (isChecked) {
            //depending on the month the user is i need to show the percentages for that
            // month

            if (!valueSet1.isEmpty()) {
                util.calculatePercentagesAndModifyYAxis(yearsMappedToObjectYearsMap,monthInt, year, HorizontalBarChartActivity.this, dataSets);
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
                util.revertToNumbersAndModifyYAxis(yearsMappedToObjectYearsMap, monthInt, year, HorizontalBarChartActivity.this, dataSets);
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


    public HorizontalBarChart getChart() {
        return chart;
    }

    public BarData getData() {
        return data;
    }

    public List<BarEntry> getValueSet1() {
        return valueSet1;
    }

    public boolean isStateSwitchButton() {
        return stateSwitchButton;
    }

    public BarDataSet getBarDataSet1() {
        return barDataSet1;
    }

    public void setBarDataSet1(BarDataSet barDataSet1) {
        this.barDataSet1 = barDataSet1;
    }

    public List<String> getxAxis() {
        return xAxis;
    }

    public void setData(BarData data) {
        this.data = data;
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

    public void setDataSets(ArrayList<BarDataSet> dataSets) {
        this.dataSets = dataSets;
    }

    public TextView getMonthLabel() {
        return monthLabel;
    }

    public ArrayList<BarDataSet> getDataSets() {
        return dataSets;
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
}
