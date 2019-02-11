package uk.co.irokottaki.moneycontrol.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import uk.co.irokottaki.moneycontrol.R;
import uk.co.irokottaki.moneycontrol.fragment.ScreenSlidePageFragment;
import uk.co.irokottaki.moneycontrol.fragment.ScreenSlidePageFragment2;
import uk.co.irokottaki.moneycontrol.fragment.ScreenSlidePageFragment3;
import uk.co.irokottaki.moneycontrol.fragment.ScreenSlidePageFragment4;
import uk.co.irokottaki.moneycontrol.fragment.ScreenSlidePageFragment5;
import uk.co.irokottaki.moneycontrol.utils.Utils;

import static uk.co.irokottaki.moneycontrol.utils.Constants.HELP;

public class HelpActivity extends AppCompatActivity {
    RelativeLayout layout;
    protected PreferenceManager mPreferenceManager;
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private static ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private static PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);
        setTitle(HELP);

        layout = (RelativeLayout) findViewById(R.id.helpActivityLayout);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        Utils utils = new Utils(this);
        utils.setBackgroundAndAdjustLayout(layout, HelpActivity.this);


    }//end of onCreate

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    public static ViewPager getmPager() {
        return mPager;
    }

    public static PagerAdapter getmPagerAdapter() {
        return mPagerAdapter;
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 1:
                    fragment = new ScreenSlidePageFragment2();
                    break;
                case 2:
                    fragment = new ScreenSlidePageFragment3();
                    break;
                case 3:
                    fragment = new ScreenSlidePageFragment4();
                    break;
                case 4:
                    fragment = new ScreenSlidePageFragment5();
                    break;
                default:
                    fragment = new ScreenSlidePageFragment();
                    break;
            }
            return fragment;

        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_previous:
                // Go to the previous step in the wizard. If there is no previous step,
                // setCurrentItem will do nothing.
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
                return true;

            case R.id.action_next:
                // Advance to the next step in the wizard. If there is no next step, setCurrentItem
                // will do nothing.
                mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                return true;

            default:
                // Navigate "up" the demo structure to the launchpad activity.
                // See http://developer.android.com/design/patterns/navigation.html for more.
                NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
                return true;
        }
    }


}
