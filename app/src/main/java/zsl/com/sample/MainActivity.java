package zsl.com.sample;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.astuetz.PagerSlidingTabStrip;

public class MainActivity extends AppCompatActivity {
    private DiscoverScrollView root;
    private View layoutHead;
    private View layoutTab;
    private View headPlaceHolder;
    private View mBack;
    private PagerSlidingTabStrip mPagerSlidingTabStrip;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        root = (DiscoverScrollView) findViewById(R.id.root);
        root.setScrollListener(new ScrollListener());
        root.setOverScrollMode(View.OVER_SCROLL_NEVER);

        layoutHead = findViewById(R.id.header);
        layoutTab  = findViewById(R.id.layout_tab);
        headPlaceHolder = findViewById(R.id.head_place_holder);

        mBack = findViewById(R.id.back);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBack.setVisibility(View.GONE);
                root.enableScroll(true);
                mPagerAdapter.enableListScroll(false);
            }
        });

        mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setOffscreenPageLimit(4);

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mPagerAdapter);

        mPagerSlidingTabStrip.setViewPager(mViewPager);

        mViewPager.post(new Runnable() {
            @Override
            public void run() {
                int placeHolderHeight = layoutHead.getHeight() - getNavHeight();
                headPlaceHolder.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, placeHolderHeight));

                int viewPagerHeight = root.getHeight() - layoutTab.getHeight();
                mViewPager.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, viewPagerHeight));
            }
        });
    }

    private int getNavHeight() {
        return getResources().getDimensionPixelOffset(R.dimen.nav_height);
    }

    private class ScrollListener implements DiscoverScrollView.ScrollListener {
        @Override
        public void onScrollChanged(int scrollX, int scrollY) {
            int maxScrollY = layoutHead.getHeight() - layoutTab.getHeight() - getNavHeight();
            Log.d("scroll", String.format("scrollY:%d, maxScrollY:%d", scrollY, maxScrollY));
            if (scrollY >= maxScrollY) {
                root.enableScroll(false);
                mPagerAdapter.enableListScroll(true);
                mBack.setVisibility(View.VISIBLE);
            }
        }
    }

    public class PagerAdapter extends FragmentPagerAdapter {

        private SparseArrayCompat<SampleListFragment> mScrollTabHolders;
        private final String[] TITLES = { "Page 1", "Page 2", "Page 3", "Page 4"};

        public PagerAdapter(FragmentManager fm) {
            super(fm);
            mScrollTabHolders = new SparseArrayCompat<>();
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
            SampleListFragment fragment =  SampleListFragment.newInstance(position);

            mScrollTabHolders.put(position, fragment);

            return fragment;
        }

        public void enableListScroll(boolean enable) {
            for (int i = 0; i < mScrollTabHolders.size(); ++i) {
                SampleListFragment fragment = mScrollTabHolders.get(i);
                fragment.enableListScroll(enable);
            }
        }

    }

}
