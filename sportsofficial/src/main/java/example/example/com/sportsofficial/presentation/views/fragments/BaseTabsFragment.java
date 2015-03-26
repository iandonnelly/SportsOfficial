package example.example.com.sportsofficial.presentation.views.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import example.example.com.sportsofficial.R;
import example.example.com.sportsofficial.presentation.views.Injector;
import example.example.com.sportsofficial.presentation.views.ViewTabs;

public abstract class BaseTabsFragment extends  Fragment implements Injector {
    public  interface TabFactory<T extends Fragment> {
        public T newInstance(Bundle args);
    }

    protected ViewPager mViewPager;
    protected ViewTabs mViewTabs;
    protected List<Tab> mTabs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tabs, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Set up the view pager
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mViewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));

        // Set up the tabs
        mViewTabs = (ViewTabs) view.findViewById(R.id.view_tabs);
        mViewTabs.setTitleColor(Color.BLACK);
        mViewTabs.setFittingChildren(true);
        mViewTabs.setTabType(ViewTabs.TabType.TEXT);
        mViewTabs.setViewPager(mViewPager);
    }

    /**
     *
     */
    public static class Tab {
        private final TabFactory mTabFactory;
        private final CharSequence mTitle;
        private final Bundle mArgs;


        Tab(TabFactory tabFactory, CharSequence title, Bundle args) {
            mTabFactory = tabFactory;
            mTitle = title;
            mArgs = args;
        }

        Fragment createFragment() {
            return mTabFactory.newInstance(mArgs);
        }

        CharSequence getTitle() {
            return mTitle;
        }
    }

    /**
     *
     */
    private class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mTabs.get(position).createFragment();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabs.get(position).getTitle();
        }

        @Override
        public int getCount() {
            return mTabs.size();
        }

        @Override
        public int getItemPosition(Object object){
            return PagerAdapter.POSITION_NONE;
        }
    }
}
