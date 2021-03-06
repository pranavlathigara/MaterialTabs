package io.karim.materialtabs.sample;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.karim.MaterialTabs;


public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.material_tabs)
    MaterialTabs mMaterialTabs;

    @InjectView(R.id.view_pager)
    ViewPager mViewPager;

    private final TabsSettingsFragment mTabsSettingsFragment = new TabsSettingsFragment();
    private final RippleSettingsFragment mRippleSettingsFragment = new RippleSettingsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.title_activity_main));

        // Apply background tinting to the Android system UI when using KitKat translucent modes.
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);

        MainActivityPagerAdapter adapter = new MainActivityPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);

        mMaterialTabs.setViewPager(mViewPager);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        mViewPager.setPageMargin(pageMargin);
        mViewPager.setCurrentItem(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_me:
                DialogFragment newFragment = new MeDialogFragment();
                newFragment.show(getSupportFragmentManager(), "dialog");
                return true;
            case R.id.action_reset:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("Reset everything to default?")
                                  .setCancelable(false)
                                  .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                      public void onClick(DialogInterface dialog, int id) {
                                          resetDefaults();
                                      }
                                  })
                                  .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                      public void onClick(DialogInterface dialog, int id) {
                                          dialog.cancel();
                                      }
                                  });
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
                return true;
            case R.id.action_go:
                goToTabsActivityButtonClicked();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void resetDefaults() {
        mTabsSettingsFragment.setupAndReset();
        mRippleSettingsFragment.setupAndReset();
    }

    public void goToTabsActivityButtonClicked() {
        Intent intent = new Intent(this, TabsActivity.class);

        // Indicator Color
        String key = TabsSettingsFragment.INDICATOR_COLOR;
        switch (mTabsSettingsFragment.indicatorColorRadioGroup.getCheckedRadioButtonId()) {
            case R.id.indicatorColorButtonFireEngineRed:
                intent.putExtra(key, R.color.fire_engine_red);
                break;
            case R.id.indicatorColorButtonGorse:
                intent.putExtra(key, R.color.gorse);
                break;
            case R.id.indicatorColorButtonIrisBlue:
                intent.putExtra(key, R.color.iris_blue);
                break;
            case R.id.indicatorColorButtonSafetyOrange:
                intent.putExtra(key, R.color.safety_orange);
                break;
            case R.id.indicatorColorButtonWhite:
                intent.putExtra(key, R.color.white);
                break;
            case R.id.indicatorColorButtonBlack:
                intent.putExtra(key, R.color.black);
                break;
            case R.id.indicatorColorButtonMantis:
            default:
                intent.putExtra(key, R.color.mantis);
                break;
        }

        // Underline Color
        key = TabsSettingsFragment.UNDERLINE_COLOR;
        switch (mTabsSettingsFragment.underlineColorRadioGroup.getCheckedRadioButtonId()) {
            case R.id.underlineColorButtonFireEngineRed:
                intent.putExtra(key, R.color.fire_engine_red);
                break;
            case R.id.underlineColorButtonGorse:
                intent.putExtra(key, R.color.gorse);
                break;
            case R.id.underlineColorButtonIrisBlue:
                intent.putExtra(key, R.color.iris_blue);
                break;
            case R.id.underlineColorButtonSafetyOrange:
                intent.putExtra(key, R.color.safety_orange);
                break;
            case R.id.underlineColorButtonWhite:
                intent.putExtra(key, R.color.white);
                break;
            case R.id.underlineColorButtonBlack:
                intent.putExtra(key, R.color.black);
                break;
            case R.id.underlineColorButtonMantis:
            default:
                intent.putExtra(key, R.color.mantis);
                break;
        }

        intent.putExtra(TabsSettingsFragment.INDICATOR_HEIGHT, mTabsSettingsFragment.indicatorHeightDp);
        intent.putExtra(TabsSettingsFragment.UNDERLINE_HEIGHT, mTabsSettingsFragment.underlineHeightDp);

        intent.putExtra(TabsSettingsFragment.TAB_PADDING, mTabsSettingsFragment.tabPaddingDp);

        intent.putExtra(TabsSettingsFragment.SAME_WEIGHT_TABS, mTabsSettingsFragment.sameWeightTabsCheckBox.isChecked());
        intent.putExtra(TabsSettingsFragment.TEXT_ALL_CAPS, mTabsSettingsFragment.textAllCapsCheckBox.isChecked());
        intent.putExtra(TabsSettingsFragment.PADDING_MIDDLE, mTabsSettingsFragment.paddingMiddleCheckBox.isChecked());

        intent.putExtra(TabsSettingsFragment.SHOW_TOOLBAR, mTabsSettingsFragment.showToolbarCheckBox.isChecked());

        // Tab Background Color
        key = TabsSettingsFragment.TAB_BACKGROUND;
        switch (mTabsSettingsFragment.tabBackgroundColorRadioGroup.getCheckedRadioButtonId()) {
            case R.id.tabBackgroundColorButtonFireEngineRed:
                intent.putExtra(key, R.color.fire_engine_red);
                break;
            case R.id.tabBackgroundColorButtonGorse:
                intent.putExtra(key, R.color.gorse);
                break;
            case R.id.tabBackgroundColorButtonIrisBlue:
                intent.putExtra(key, R.color.iris_blue);
                break;
            case R.id.tabBackgroundColorButtonSafetyOrange:
                intent.putExtra(key, R.color.safety_orange);
                break;
            case R.id.tabBackgroundColorButtonWhite:
                intent.putExtra(key, R.color.white);
                break;
            case R.id.tabBackgroundColorButtonBlack:
                intent.putExtra(key, R.color.black);
                break;
            case R.id.tabBackgroundColorButtonMantis:
            default:
                intent.putExtra(key, R.color.mantis);
                break;
        }

        // Toolbar Background Color
        key = TabsSettingsFragment.TOOLBAR_BACKGROUND;
        switch (mTabsSettingsFragment.toolbarColorRadioGroup.getCheckedRadioButtonId()) {
            case R.id.toolbarColorButtonFireEngineRed:
                intent.putExtra(key, R.color.fire_engine_red);
                break;
            case R.id.toolbarColorButtonGorse:
                intent.putExtra(key, R.color.gorse);
                break;
            case R.id.toolbarColorButtonIrisBlue:
                intent.putExtra(key, R.color.iris_blue);
                break;
            case R.id.toolbarColorButtonSafetyOrange:
                intent.putExtra(key, R.color.safety_orange);
                break;
            case R.id.toolbarColorButtonWhite:
                intent.putExtra(key, R.color.white);
                break;
            case R.id.toolbarColorButtonBlack:
                intent.putExtra(key, R.color.black);
                break;
            case R.id.toolbarColorButtonMantis:
            default:
                intent.putExtra(key, R.color.mantis);
                break;
        }

        // Text Color
        key = TabsSettingsFragment.TEXT_COLOR_UNSELECTED;
        switch (mTabsSettingsFragment.tabTextColorRadioGroup.getCheckedRadioButtonId()) {
            case R.id.tabTextColorButtonFireEngineRed:
                intent.putExtra(key, R.color.fire_engine_red);
                break;
            case R.id.tabTextColorButtonGorse:
                intent.putExtra(key, R.color.gorse);
                break;
            case R.id.tabTextColorButtonIrisBlue:
                intent.putExtra(key, R.color.iris_blue);
                break;
            case R.id.tabTextColorButtonSafetyOrange:
                intent.putExtra(key, R.color.safety_orange);
                break;
            case R.id.tabTextColorButtonWhite:
                intent.putExtra(key, R.color.white);
                break;
            case R.id.tabTextColorButtonBlack:
                intent.putExtra(key, R.color.black);
                break;
            case R.id.tabTextColorButtonMantis:
            default:
                intent.putExtra(key, R.color.mantis);
                break;
        }

        // Text Color Selected
        key = TabsSettingsFragment.TEXT_COLOR_SELECTED;
        switch (mTabsSettingsFragment.tabTextSelectedColorRadioGroup.getCheckedRadioButtonId()) {
            case R.id.tabTextSelectedColorButtonFireEngineRed:
                intent.putExtra(key, R.color.fire_engine_red);
                break;
            case R.id.tabTextSelectedColorButtonGorse:
                intent.putExtra(key, R.color.gorse);
                break;
            case R.id.tabTextSelectedColorButtonIrisBlue:
                intent.putExtra(key, R.color.iris_blue);
                break;
            case R.id.tabTextSelectedColorButtonSafetyOrange:
                intent.putExtra(key, R.color.safety_orange);
                break;
            case R.id.tabTextSelectedColorButtonWhite:
                intent.putExtra(key, R.color.white);
                break;
            case R.id.tabTextSelectedColorButtonBlack:
                intent.putExtra(key, R.color.black);
                break;
            case R.id.tabTextSelectedColorButtonMantis:
            default:
                intent.putExtra(key, R.color.mantis);
                break;
        }

        // Text Style Selected
        key = TabsSettingsFragment.TEXT_STYLE_SELECTED;
        switch (mTabsSettingsFragment.selectedTextStyleRadioGroup.getCheckedRadioButtonId()) {
            case R.id.selectedTextStyleButtonNormal:
                intent.putExtra(key, Typeface.NORMAL);
                break;
            case R.id.selectedTextStyleButtonItallic:
                intent.putExtra(key, Typeface.ITALIC);
                break;
            case R.id.selectedTextStyleButtonBold:
            default:
                intent.putExtra(key, Typeface.BOLD);
                break;
        }

        // Text Style Unselected
        key = TabsSettingsFragment.TEXT_STYLE_UNSELECTED;
        switch (mTabsSettingsFragment.unselectedTextStyleRadioGroup.getCheckedRadioButtonId()) {
            case R.id.unselectedTextStyleButtonNormal:
                intent.putExtra(key, Typeface.NORMAL);
                break;
            case R.id.unselectedTextStyleButtonItallic:
                intent.putExtra(key, Typeface.ITALIC);
                break;
            case R.id.unselectedTextStyleButtonBold:
            default:
                intent.putExtra(key, Typeface.BOLD);
                break;
        }

        intent.putExtra(RippleSettingsFragment.RIPPLE_DURATION, mRippleSettingsFragment.rippleDurationMs);
        intent.putExtra(RippleSettingsFragment.RIPPLE_ALPHA_FLOAT, mRippleSettingsFragment.rippleAlphaFloat);

        // Ripple Color
        key = RippleSettingsFragment.RIPPLE_COLOR;
        switch (mRippleSettingsFragment.rippleColorRadioGroup.getCheckedRadioButtonId()) {
            case R.id.rippleColorButtonFireEngineRed:
                intent.putExtra(key, R.color.fire_engine_red);
                break;
            case R.id.rippleColorButtonGorse:
                intent.putExtra(key, R.color.gorse);
                break;
            case R.id.rippleColorButtonIrisBlue:
                intent.putExtra(key, R.color.iris_blue);
                break;
            case R.id.rippleColorButtonSafetyOrange:
                intent.putExtra(key, R.color.safety_orange);
                break;
            case R.id.rippleColorButtonWhite:
                intent.putExtra(key, R.color.white);
                break;
            case R.id.rippleColorButtonBlack:
                intent.putExtra(key, R.color.black);
                break;
            case R.id.rippleColorButtonMantis:
            default:
                intent.putExtra(key, R.color.mantis);
                break;
        }

        intent.putExtra(RippleSettingsFragment.RIPPLE_DELAY_CLICK, mRippleSettingsFragment.rippleDelayClickCheckBox.isChecked());
        intent.putExtra(RippleSettingsFragment.RIPPLE_DIAMETER, mRippleSettingsFragment.rippleDiameterDp);

        intent.putExtra(RippleSettingsFragment.RIPPLE_FADE_DURATION, mRippleSettingsFragment.rippleFadeDurationMs);

        // Ripple Highlight Color
        key = RippleSettingsFragment.RIPPLE_HIGHLIGHT_COLOR;
        switch (mRippleSettingsFragment.rippleHighlightColorRadioGroup.getCheckedRadioButtonId()) {
            case R.id.rippleHighlightColorButtonFireEngineRed:
                intent.putExtra(key, R.color.fire_engine_red_75);
                break;
            case R.id.rippleHighlightColorButtonGorse:
                intent.putExtra(key, R.color.gorse_75);
                break;
            case R.id.rippleHighlightColorButtonIrisBlue:
                intent.putExtra(key, R.color.iris_blue_75);
                break;
            case R.id.rippleHighlightColorButtonSafetyOrange:
                intent.putExtra(key, R.color.safety_orange_75);
                break;
            case R.id.rippleHighlightColorButtonWhite:
                intent.putExtra(key, R.color.white_75);
                break;
            case R.id.rippleHighlightColorButtonBlack:
                intent.putExtra(key, R.color.black_75);
                break;
            case R.id.rippleHighlightColorButtonMantis:
            default:
                intent.putExtra(key, R.color.mantis_75);
                break;
        }

        intent.putExtra(RippleSettingsFragment.RIPPLE_OVERLAY, mRippleSettingsFragment.rippleOverlayCheckBox.isChecked());
        intent.putExtra(RippleSettingsFragment.RIPPLE_PERSISTENT, mRippleSettingsFragment.ripplePersistentCheckBox.isChecked());

        intent.putExtra(RippleSettingsFragment.RIPPLE_ROUNDED_CORNERS_RADIUS, mRippleSettingsFragment.rippleRoundedCornersRadiusDp);

        startActivity(intent);
    }


    public class MainActivityPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"Tabs", "Ripple"};

        public MainActivityPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                default:
                    return mTabsSettingsFragment;
                case 1:
                    return mRippleSettingsFragment;
            }
        }
    }


    public static class MeDialogFragment extends DialogFragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.dialog_me, container, false);
            TextView githubTextView = (TextView) view.findViewById(R.id.github_text_view);
            TextView twitterTextView = (TextView) view.findViewById(R.id.twitter_text_view);

            String link = getString(R.string.github);
            Spannable spannable = new SpannableString(link);
            spannable.setSpan(new URLSpan(getString(R.string.github_link)), 0, link.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            githubTextView.setText(spannable, TextView.BufferType.SPANNABLE);
            githubTextView.setMovementMethod(LinkMovementMethod.getInstance());

            link = getString(R.string.twitter);
            spannable = new SpannableString(link);
            spannable.setSpan(new URLSpan(getString(R.string.twitter_link)), 0, link.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            twitterTextView.setText(spannable, TextView.BufferType.SPANNABLE);
            twitterTextView.setMovementMethod(LinkMovementMethod.getInstance());

            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            return view;
        }
    }
}
