package uk.co.irokottaki.moneycontrol;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import uk.co.irokottaki.moneycontrol.activity.EditActivity;
import uk.co.irokottaki.moneycontrol.activity.LoginActivity;
import uk.co.irokottaki.moneycontrol.utils.Utils;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static uk.co.irokottaki.moneycontrol.utils.Constants.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class UtilsTests {
    private LoginActivity loginActivity;
    private EditActivity editActivity;
    RelativeLayout layout;

    @Before
    public void setUp() throws Exception {
        editActivity = Robolectric.buildActivity(EditActivity.class)
                .create()
                .resume()
                .get();
        loginActivity = Robolectric.buildActivity(LoginActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void testAdjustScreenSizeToDevice(){
        final Context context = mock(Context.class);
        Utils util = new Utils(context);

        Resources resources = mock(Resources.class);
        when(context.getResources()).thenReturn(resources);

        Configuration configuration = mock(Configuration.class);
        configuration.orientation = Configuration.ORIENTATION_LANDSCAPE;
        when(context.getResources().getConfiguration()).thenReturn(configuration);

        DisplayMetrics metrics = mock(DisplayMetrics.class);
        metrics.heightPixels = 1210;
        metrics.widthPixels = 770;
        when(context.getResources().getDisplayMetrics()).thenReturn(metrics);

        layout = (RelativeLayout) editActivity.findViewById(R.id.editView);

        util.adjustScreenSizeToDevice(layout);
        assertEquals(2087, layout.getLayoutParams().height);
        assertEquals(786, layout.getLayoutParams().width);

        metrics.heightPixels = 1810;
        metrics.widthPixels = 1060;
        util.adjustScreenSizeToDevice(layout);
        assertEquals(3122, layout.getLayoutParams().height);
        assertEquals(1082, layout.getLayoutParams().width);

        metrics.heightPixels = 1950;
        metrics.widthPixels = 1510;
        util.adjustScreenSizeToDevice(layout);
        assertEquals(3363, layout.getLayoutParams().height);
        assertEquals(1541, layout.getLayoutParams().width);

        metrics.heightPixels = 1400;
        metrics.widthPixels = 1200;
        util.adjustScreenSizeToDevice(layout);
        assertEquals(2415, layout.getLayoutParams().height);
        assertEquals(1225, layout.getLayoutParams().width);
    }

    @Test
    public void testSetBackgroundAndAdjustLayout(){

        final Context context = editActivity.getApplicationContext();
        Utils util = new Utils(context);

        Resources resources = editActivity.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.orientation = Configuration.ORIENTATION_LANDSCAPE;
        DisplayMetrics metrics = resources.getDisplayMetrics();
        metrics.heightPixels = 1210;
        metrics.widthPixels = 770;

        SharedPreferences defaultSharedPrefs = PreferenceManager.getDefaultSharedPreferences(editActivity.getApplicationContext());
        SharedPreferences.Editor editor = defaultSharedPrefs.edit();
        editor.putString(BACKGROUND_COLOR, COLOR_TRANSPARENT);
        editor.apply();

        SharedPreferences sharedPrefs = editActivity.getSharedPreferences(PREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPrefs.edit();
        editor2.putString(GALLERY_IMAGE, "/storage/emulated/0/DCIM/download.jpeg");
        editor2.apply();
        layout = (RelativeLayout) editActivity.findViewById(R.id.editView);

        ImageView editActivityImageView = (ImageView) editActivity.findViewById(R.id.ImageView);
        util.setBackgroundAndAdjustLayout(layout, editActivity);
        RelativeLayout.LayoutParams editActivityParams = (RelativeLayout.LayoutParams) editActivityImageView.getLayoutParams();
        assertEquals(-170, editActivityParams.leftMargin);
        assertEquals(-130, editActivityParams.topMargin);
        assertEquals(-90, editActivityParams.rightMargin);
        assertEquals(0, editActivityParams.bottomMargin);

        ImageView loginActivityImageView = (ImageView) loginActivity.findViewById(R.id.ImageView);
        util.setBackgroundAndAdjustLayout(layout, loginActivity);
        RelativeLayout.LayoutParams loginActivityParams = (RelativeLayout.LayoutParams) loginActivityImageView.getLayoutParams();
        assertEquals(-170, loginActivityParams.leftMargin);
        assertEquals(-130, loginActivityParams.topMargin);
        assertEquals(-90, loginActivityParams.rightMargin);
        assertEquals(0, loginActivityParams.bottomMargin);

        try {
            setFinalStatic(Build.VERSION.class.getField("SDK_INT"),15);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageView loginActivityImageViewLowSdk = (ImageView) loginActivity.findViewById(R.id.ImageView);
        util.setBackgroundAndAdjustLayout(layout, loginActivity);
        RelativeLayout.LayoutParams loginActivityParamsLowSdk = (RelativeLayout.LayoutParams) loginActivityImageViewLowSdk.getLayoutParams();
        assertEquals(-50, loginActivityParamsLowSdk.leftMargin);
        assertEquals(-40, loginActivityParamsLowSdk.topMargin);
        assertEquals(0, loginActivityParamsLowSdk.rightMargin);
        assertEquals(0, loginActivityParamsLowSdk.bottomMargin);

    }

    static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }

}
