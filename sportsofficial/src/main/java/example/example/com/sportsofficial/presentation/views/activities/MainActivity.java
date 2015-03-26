package example.example.com.sportsofficial.presentation.views.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;
import example.example.com.sportsofficial.R;
import example.example.com.sportsofficial.presentation.App;
import example.example.com.sportsofficial.presentation.models.Sport;
import example.example.com.sportsofficial.presentation.modules.MainModule;
import example.example.com.sportsofficial.presentation.presenters.MainPresenter;
import example.example.com.sportsofficial.presentation.views.Injector;
import example.example.com.sportsofficial.presentation.views.MainView;
import example.example.com.sportsofficial.presentation.views.Navigator;
import example.example.com.sportsofficial.presentation.views.dialogs.AddSportDialogFragment;
import example.example.com.sportsofficial.presentation.views.dialogs.ChangePebbleSportDialogFragment;
import example.example.com.sportsofficial.presentation.views.dialogs.CreateMatchDialogFragment;
import example.example.com.sportsofficial.presentation.views.dialogs.RemoveSportDialogFragment;
import example.example.com.sportsofficial.presentation.views.fragments.HomeFragment;
import example.example.com.sportsofficial.presentation.views.fragments.MatchListFragment;


/**
 *
 */
public class MainActivity extends ActionBarActivity implements MainView, Injector,
        CreateMatchDialogFragment.CreateMatchDialogListener,
        AddSportDialogFragment.AddSportDialogListener,
        RemoveSportDialogFragment.RemoveSportDialogListener,
        ChangePebbleSportDialogFragment.ChangePebbleSportDialogListener {
    public static int NAV_ITEM_COUNT = 8;
    public static String[] NAV_ITEM_TITLES = {
            "Home",
            null,
            "Add sport",
            "Remove sport",
            null,
            "Settings",
            "About",
            "Help"
    };

    public static Navigator.NavItemType[] NAV_ITEM_TYPES = {
            Navigator.NavItemType.HOME,
            Navigator.NavItemType.DIVIDER,
            Navigator.NavItemType.ADD_SPORT,
            Navigator.NavItemType.REMOVE_SPORT,
            Navigator.NavItemType.DIVIDER,
            Navigator.NavItemType.SETTINGS,
            Navigator.NavItemType.ABOUT,
            Navigator.NavItemType.HELP
    };

    public static int[] NAV_ITEM_ICONS = {
            R.mipmap.ic_action_home,
            -1,
            R.mipmap.ic_placeholder,
            R.mipmap.ic_placeholder,
            -1,
            R.mipmap.ic_action_settings,
            R.mipmap.ic_placeholder,
            R.mipmap.ic_placeholder
    };

    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerListView;
    private NavDrawerListAdapter mDrawerAdapter;
    private List<NavDrawerItem> mDrawerItems;

    @Inject
    MainPresenter mPresenter;
    private ObjectGraph mObjectGraph;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup the dagger graph
        List<Object> modules = Arrays.<Object>asList(new MainModule(this));
        mObjectGraph = ((App) getApplication()).getObjectGraph().plus(modules.toArray());
        mObjectGraph.inject(this);

        // Setup the action bar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // Setup drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Set up drawer list view
        mDrawerListView = (ListView) findViewById(R.id.drawer_list_view);

        // Set up drawer item click listener
        mDrawerListView.setOnItemClickListener(new DrawerItemListener());

        // Set up the action bar drawer toggle
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawer.setDrawerListener(mDrawerToggle);

        // Create default navigation drawer items
        mDrawerItems = new ArrayList<>();
        for (int i = 0; i < NAV_ITEM_COUNT; i++) {
           NavDrawerItem navItem = new NavDrawerItem(NAV_ITEM_TITLES[i], NAV_ITEM_ICONS[i],
                   NAV_ITEM_TYPES[i]);
           mDrawerItems.add(navItem);
        }

        // Set up the navigation drawer adapter
        mDrawerAdapter = new NavDrawerListAdapter(this, mDrawerItems);
        mDrawerListView.setAdapter(mDrawerAdapter);

        mPresenter.onCreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        mPresenter.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        // Clear object graph reference to allow garbage collector to clean it up
        mObjectGraph = null;
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            int itemId = item.getItemId();

            if (itemId == R.id.action_sync) {
                mPresenter.onSyncClicked();
            } else if (itemId == R.id.action_pebble_sport) {
                mPresenter.onPebbleSportClicked();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     *
     * @param title
     */
    @Override
    public void setActionBarTitle(String title) {
        setTitle(title);
    }

    @Override
    public void showChangePebbleSportDialog(List<String> sports) {
        DialogFragment dialog = ChangePebbleSportDialogFragment.newInstance((ArrayList) sports);
        dialog.show(getSupportFragmentManager(), "ChangePebbleSportDialogFragment");
    }

    /**
     *
     * @param sport
     */
    @Override
    public void onChangePebbleSportPositiveClicked(String sport) {
        mPresenter.onChangePebbleSportPositiveClicked(sport);
    }

    /**
     *
     */
    @Override
    public void showPebbleSyncProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Syncing with Pebble...");
        mProgressDialog.setMessage("Sync in progress...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
    }

    /**
     *
     */
    @Override
    public void closePebbleSyncProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.hide();
        }
    }

    /**
     *
     */
    @Override
    public void showCreateMatchDialog() {
        DialogFragment dialog = new CreateMatchDialogFragment();
        dialog.show(getSupportFragmentManager(), "CreateMatchDialogFragment");
    }

    /**
     *
     * @param homeTeam
     * @param awayTeam
     */
    @Override
    public void onCreateMatchDialogPositiveClicked(String homeTeam, String awayTeam) {
        mPresenter.onAddMatchDialogPositiveClicked(homeTeam, awayTeam);
    }

    /**
     *
     */
    @Override
    public void showAddSportDialog() {
        DialogFragment dialog = new AddSportDialogFragment();
        dialog.show(getSupportFragmentManager(), "AddSportDialogFragment");
    }

    /**
     *
     * @param sportTitle
     */
    @Override
    public void onAddSportDialogPositiveClicked(String sportTitle, int singleClick,
                                                int doubleClick, int longClick) {
        mPresenter.onAddSportDialogPositiveClicked(sportTitle, singleClick, doubleClick, longClick);
    }

    /**
     *
     */
    @Override
    public void showRemoveSportDialog(List<String> sports) {
        DialogFragment dialog = RemoveSportDialogFragment.newInstance((ArrayList) sports);
        dialog.show(getSupportFragmentManager(), "RemoveSportDialogFragment");
    }

    /**
     *
     * @param sportTitle
     */
    @Override
    public void onRemoveSportDialogPositiveClicked(String sportTitle) {
        mPresenter.onRemoveSportDialogPositiveClicked(sportTitle);
    }

    /**
     *
     * @param sport
     */
    @Override
    public void addSportNav(Sport sport) {
        NavDrawerItem navItem = new NavDrawerItem(sport.getName(), sport.getIcon(),
                Navigator.NavItemType.SPORT);

        mDrawerItems.add(mDrawerItems.size() - 6, navItem);
        mDrawerAdapter.notifyDataSetChanged();
    }

    /**
     *
     * @param sport
     */
    @Override
    public void removeSportNav(Sport sport) {
        for (int i = 2; i < mDrawerItems.size() - 6; i ++) {
            if (mDrawerItems.get(i).getTitle().equals(sport.getName())) {
                mDrawerItems.remove(i);
                mDrawerAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    /**
     *
     * @param sportId
     */
    @Override
    public void navigateToSportView(int sportId) {
        MatchListFragment fragment = new MatchListFragment();

        // Create and add the arguments to the fragment
        Bundle args = new Bundle();
        args.putInt(getString(R.string.sport), sportId);
        fragment.setArguments(args);

        // Insert the fragment by replacing the existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment, fragment, "Fragment").commit();

        /*
        // Create the sports fragment
        SportTabsFragment fragment = new SportTabsFragment();

        // Create and add the arguments to the fragment
        Bundle args = new Bundle();
        args.putInt(getString(R.string.sport), sportId);
        fragment.setArguments(args);

        // Insert the fragment by replacing the existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment, fragment, "TabFragment").commit();
        */
    }

    /**
     *
     */
    @Override
    public void navigateToHome() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment, new HomeFragment()).commit();
    }

    /**
     *
     */
    @Override
    public void navigateToSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    /**
     *
     */
    @Override
    public void navigateToAbout() {
        //Intent intent = new Intent(this, AboutActivity.class);
        //startActivity(intent);
    }

    /**
     *
     */
    @Override
    public void navigateToHelp() {
        //Intent intent = new Intent(this, HelpActivity.class);
        //startActivity(intent);
    }

    @Override
    public void refreshView() {
        FragmentManager manager = getSupportFragmentManager();
        MatchListFragment fragment = (MatchListFragment) manager.findFragmentByTag("Fragment");

        if (fragment != null) {
            fragment.refreshView();
        }
    }

    /**
     * Gets this Activity's object graph.
     *
     * @return the object graph
     */
    @Override
    public final ObjectGraph getObjectGraph() {
        return mObjectGraph;
    }
    /**
     * Injects a target object using this Activity's object graph.
     *
     * @param object the target object
     */
    @Override
    public void inject(Object object) {
        mObjectGraph.inject(object);
    }

    /**
     *
     */
    private class DrawerItemListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Notify presenter
            Navigator.NavItemType navItemType = mDrawerItems.get(position).getItemType();
            String sportTitle = (navItemType == Navigator.NavItemType.SPORT) ? mDrawerItems.get(position).getTitle() : null;

            mPresenter.onNavigationItemClicked(navItemType, sportTitle);

            // Handle drawer
            mDrawerListView.setItemChecked(position, true);
            mDrawer.closeDrawer(mDrawerListView);
        }
    }

    /**
     *
     */
    private class NavDrawerItem {
        private String mTitle;
        private int mIcon;
        private Navigator.NavItemType mItemType;

        public NavDrawerItem(String title, int icon, Navigator.NavItemType itemType){
            mTitle = title;
            mIcon = icon;
            mItemType = itemType;
        }

        public String getTitle(){
            return this.mTitle;
        }

        public int getIcon(){
            return this.mIcon;
        }

        public Navigator.NavItemType getItemType() {
            return mItemType;
        }

        public void setTitle(String title){
            mTitle = title;
        }

        public void setIcon(int icon){
            mIcon = icon;
        }

        public void setItemType(Navigator.NavItemType itemType) {
            mItemType = itemType;
        }
    }

    /**
     *
     */
    private class NavDrawerListAdapter extends BaseAdapter {
        private Context mContext;
        private List<NavDrawerItem> mNavItems;

        public NavDrawerListAdapter(Context context, List<NavDrawerItem> navItems) {
            mContext = context;
            mNavItems = navItems;
        }

        private class ViewHolder {
            Navigator.NavItemType itemType;
            ImageView icon;
            TextView title;
        }


        @Override
        public int getCount() {
            return mNavItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mNavItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View rowView, ViewGroup parent) {
            if (rowView == null ||
                    ((ViewHolder) rowView.getTag()).itemType != mNavItems.get(position).getItemType()) {
                ViewHolder viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(mContext);

                switch (mNavItems.get(position).getItemType()) {
                    case HOME:
                        rowView = inflater.inflate(R.layout.drawer_nav_item, null);
                        viewHolder.title = (TextView) rowView.findViewById(R.id.title);
                        //viewHolder.icon = (ImageView) rowView.findViewById(R.id.icon);
                        viewHolder.itemType = Navigator.NavItemType.HOME;
                        break;
                    case SPORT:
                        rowView = inflater.inflate(R.layout.drawer_nav_item, null);
                        viewHolder.title = (TextView) rowView.findViewById(R.id.title);
                        //viewHolder.icon = (ImageView) rowView.findViewById(R.id.icon);
                        viewHolder.itemType = Navigator.NavItemType.SPORT;
                        break;
                    case ADD_SPORT:
                        rowView = inflater.inflate(R.layout.drawer_nav_item, null);
                        viewHolder.title = (TextView) rowView.findViewById(R.id.title);
                        //viewHolder.icon = (ImageView) rowView.findViewById(R.id.icon);
                        viewHolder.itemType = Navigator.NavItemType.ADD_SPORT;
                        break;
                    case REMOVE_SPORT:
                        rowView = inflater.inflate(R.layout.drawer_nav_item, null);
                        viewHolder.title = (TextView) rowView.findViewById(R.id.title);
                        //viewHolder.icon = (ImageView) rowView.findViewById(R.id.icon);
                        viewHolder.itemType = Navigator.NavItemType.REMOVE_SPORT;
                        break;
                    case SETTINGS:
                        rowView = inflater.inflate(R.layout.drawer_nav_item, null);
                        viewHolder.title = (TextView) rowView.findViewById(R.id.title);
                        //viewHolder.icon = (ImageView) rowView.findViewById(R.id.icon);
                        viewHolder.itemType = Navigator.NavItemType.SETTINGS;
                        break;
                    case ABOUT:
                        rowView = inflater.inflate(R.layout.drawer_nav_item, null);
                        viewHolder.title = (TextView) rowView.findViewById(R.id.title);
                        //viewHolder.icon = (ImageView) rowView.findViewById(R.id.icon);
                        viewHolder.itemType = Navigator.NavItemType.ABOUT;
                        break;
                    case HELP:
                        rowView = inflater.inflate(R.layout.drawer_nav_item, null);
                        viewHolder.title = (TextView) rowView.findViewById(R.id.title);
                        //viewHolder.icon = (ImageView) rowView.findViewById(R.id.icon);
                        viewHolder.itemType = Navigator.NavItemType.HELP;
                        break;
                    case DIVIDER:
                        rowView = inflater.inflate(R.layout.drawer_list_divider, null);
                        viewHolder.itemType = Navigator.NavItemType.DIVIDER;
                        break;
                }
                rowView.setTag(viewHolder);
            }

            ViewHolder viewHolder = (ViewHolder) rowView.getTag();
            switch (mNavItems.get(position).getItemType()) {
                case HOME:
                case SPORT:
                case ADD_SPORT:
                case REMOVE_SPORT:
                case SETTINGS:
                case ABOUT:
                case HELP:
                    viewHolder.title.setText(mNavItems.get(position).getTitle());
                   // viewHolder.icon.setImageResource(mNavItems.get(position).getIcon());
                    break;
                case DIVIDER:
                    break;
            }
            return rowView;
        }
    }
}
