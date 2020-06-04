package uk.co.irokottaki.moneycontrol.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import uk.co.irokottaki.moneycontrol.R;
import uk.co.irokottaki.moneycontrol.activity.HelpActivity;

public class ScreenSlidePageFragment3 extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(
                R.layout.fragment_screen_slide_page3, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.activity_screen_slide, menu);

        menu.findItem(R.id.action_previous).setEnabled(HelpActivity.getmPager().getCurrentItem() > 0);

        // Add a "next" and "previous" button to the action bar
        MenuItem item = menu.add(Menu.NONE, R.id.action_previous, Menu.NONE, R.string
                .action_previous);
        MenuItem item2 = menu.add(Menu.NONE, R.id.action_next, Menu.NONE, R.string.action_next);

        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        item2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

    }

}
