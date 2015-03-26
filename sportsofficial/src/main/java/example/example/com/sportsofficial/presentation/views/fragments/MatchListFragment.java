package example.example.com.sportsofficial.presentation.views.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.Observable;

import javax.inject.Inject;

import dagger.ObjectGraph;
import example.example.com.sportsofficial.R;
import example.example.com.sportsofficial.presentation.models.Match;
import example.example.com.sportsofficial.presentation.modules.MatchListModule;
import example.example.com.sportsofficial.presentation.presenters.MatchListPresenter;
import example.example.com.sportsofficial.presentation.views.Injector;
import example.example.com.sportsofficial.presentation.views.MatchListView;

public class MatchListFragment extends Fragment implements MatchListView, ListView.OnItemClickListener {
    public final static BaseTabsFragment.TabFactory TAB_FACTORY = new BaseTabsFragment.TabFactory() {
        @Override
        public Fragment newInstance(Bundle args) {
            Fragment fragment = new MatchListFragment();
            fragment.setArguments(args);
            return fragment;
        }
    };

    @Inject
    MatchListPresenter mPresenter;
    private ObjectGraph mObjectGraph;
    private boolean mIsFirstAttach = true;

    private ListView mListView;
    private MatchListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.setSportId(getArguments().getInt(getString(R.string.sport), -1));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_view, container, false);

        // Setup the list view
        mListView = (ListView) view.findViewById(R.id.list);
        mListView.setOnItemClickListener(this);

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent,
                                           final View view, final int position, long id) {
                removeRow(view, position);
                return true;
            }
        });

        // Call the presenter to allow him to do his list setup
        mPresenter.onCreateView();
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Setup the dagger graph
        List<Object> modules = Arrays.<Object>asList(new MatchListModule(this));

        Injector injector = (Injector) activity;
        mObjectGraph = injector.getObjectGraph().plus(modules.toArray());

        // Ensure this is the first time creating the graph
        if (mIsFirstAttach) {
            mObjectGraph.inject(this);
            mIsFirstAttach = false;
        }
    }

    @Override
    public void onDestroy() {
        mObjectGraph = null;
        super.onDestroy();
    }

    /**
     *
     * @param matchList
     */
    @Override
    public void setMatchList(final List<Match> matchList) {
        mAdapter = new MatchListAdapter(getActivity(), R.layout.match_row_layout, matchList);
        mListView.setAdapter(mAdapter);
    }

    /**
     *
     * @param observable
     * @param data
     */
    @Override
    public void update(Observable observable, Object data) {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshView() {
        mPresenter.onRefreshView();
    }

    /**
     *
     * @param matchId
     */
    @Override
    public void showMatchDetails(int matchId) {
        // Create the match tab fragment
        MatchFragment fragment = new MatchFragment();

        // Create and add the arguments to the fragment
        Bundle args = new Bundle();
        args.putInt(getString(R.string.match_id), matchId);
        fragment.setArguments(args);

        // Insert the fragment by replacing the existing fragment
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment, fragment, "MatchFragment").commit();
    }

    /**
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //mPresenter.onRemoveMatchClicked(position);
    }

    private void removeRow(final View row, final int position) {
        final int initialHeight = row.getHeight();
        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime,
                                               Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                int newHeight = (int) (initialHeight * (1 - interpolatedTime));
                if (newHeight > 0) {
                    row.getLayoutParams().height = newHeight;
                    row.requestLayout();
                }
            }
        };
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                row.getLayoutParams().height = initialHeight;
                row.requestLayout();
                mPresenter.onRemoveMatchClicked(position);
            }
        });
        animation.setDuration(300);
        row.startAnimation(animation);
    }

     /**
     *
     */
    private class MatchListAdapter extends ArrayAdapter<Match> {
        private Activity mContext;
        private List<Match> mMatchList;

        public MatchListAdapter (Activity context, int resource, List<Match> matchList) {
            super(context, resource, matchList);
            mContext = context;
            mMatchList = matchList;
        }

        private class ViewHolder {
            TextView game;
            TextView date;
            TextView time;
        }

        @Override
        public View getView(int position, View rowView, ViewGroup parent) {
            // If the row has not been instantiated, inflate the view and setup the view holder
            if (rowView == null) {
                LayoutInflater inflater = mContext.getLayoutInflater();
                rowView = inflater.inflate(R.layout.match_row_layout, null);

                ViewHolder viewHolder = new ViewHolder();
                viewHolder.game = (TextView) rowView.findViewById(R.id.game);
                viewHolder.date = (TextView) rowView.findViewById(R.id.date);
                viewHolder.time = (TextView) rowView.findViewById(R.id.time);

                rowView.setTag(viewHolder);
            }

            // If there is a match at the requested location
            if (mMatchList.get(position) != null) {
                ViewHolder holder = (ViewHolder) rowView.getTag();

                holder.game.setText(mMatchList.get(position).toString());
                //holder.date.setText(mMatchList.get(position).getDate().toString());
               // holder.time.setText(mMatchList.get(position).getTime().toString());
            }

            return rowView;
        }
    }
}
