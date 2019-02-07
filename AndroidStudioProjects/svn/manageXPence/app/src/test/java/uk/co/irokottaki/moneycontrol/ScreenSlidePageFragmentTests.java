package uk.co.irokottaki.moneycontrol;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import uk.co.irokottaki.moneycontrol.activity.HelpActivity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ScreenSlidePageFragmentTests {
    private HelpActivity helpActivity;
    private Fragment screenSlidePageFragment;
    private Fragment screenSlidePageFragment2;
    private Fragment screenSlidePageFragment3;
    private Fragment screenSlidePageFragment4;
    private Fragment screenSlidePageFragment5;
    private Menu menu;
    private Context context;

    @Before
    public void setUp() {

        helpActivity = Robolectric.buildActivity(HelpActivity.class)
                .create()
                .visible()
                .get();

        screenSlidePageFragment = new Fragment();
        screenSlidePageFragment2 = new Fragment();
        screenSlidePageFragment3 = new Fragment();
        screenSlidePageFragment4 = new Fragment();
        screenSlidePageFragment5 = new Fragment();

        helpActivity.getSupportFragmentManager().beginTransaction().add(R.id.pager,screenSlidePageFragment,null).commit();
        helpActivity.getSupportFragmentManager().beginTransaction().add(R.id.pager,screenSlidePageFragment2,null).commit();
        helpActivity.getSupportFragmentManager().beginTransaction().add(R.id.pager,screenSlidePageFragment3,null).commit();
        helpActivity.getSupportFragmentManager().beginTransaction().add(R.id.pager,screenSlidePageFragment4,null).commit();
        helpActivity.getSupportFragmentManager().beginTransaction().add(R.id.pager,screenSlidePageFragment5,null).commit();

    }

    @Test
    public void testScreenSlidePageFragment() {

        context = screenSlidePageFragment.getContext();
        menu = shadowOf(helpActivity).getOptionsMenu();
        menu.findItem(R.id.action_previous).setEnabled(true);
        screenSlidePageFragment.onCreateOptionsMenu(menu, new MenuInflater(context));
        assertTrue(menu.findItem(R.id.action_previous).isEnabled());
    }

    @Test
    public void testScreenSlidePageFragment2() {

        context = screenSlidePageFragment2.getContext();
        menu = shadowOf(helpActivity).getOptionsMenu();
        menu.findItem(R.id.action_previous).setEnabled(false);
        screenSlidePageFragment2.onCreateOptionsMenu(menu, new MenuInflater(context));
        assertFalse(menu.findItem(R.id.action_previous).isEnabled());
    }

    @Test
    public void testScreenSlidePageFragment3() {

        context = screenSlidePageFragment3.getContext();
        menu = shadowOf(helpActivity).getOptionsMenu();
        menu.findItem(R.id.action_previous).setEnabled(false);
        screenSlidePageFragment3.onCreateOptionsMenu(menu, new MenuInflater(context));
        assertFalse(menu.findItem(R.id.action_previous).isEnabled());
    }

    @Test
    public void testScreenSlidePageFragment4() {

        context = screenSlidePageFragment4.getContext();
        menu = shadowOf(helpActivity).getOptionsMenu();
        menu.findItem(R.id.action_previous).setEnabled(false);
        screenSlidePageFragment4.onCreateOptionsMenu(menu, new MenuInflater(context));
        assertFalse(menu.findItem(R.id.action_previous).isEnabled());
    }

    @Test
    public void testScreenSlidePageFragment5() {

        context = screenSlidePageFragment5.getContext();
        menu = shadowOf(helpActivity).getOptionsMenu();
        menu.findItem(R.id.action_previous).setEnabled(true);
        screenSlidePageFragment5.onCreateOptionsMenu(menu, new MenuInflater(context));
       assertTrue(menu.findItem(R.id.action_previous).isEnabled());
    }
}
