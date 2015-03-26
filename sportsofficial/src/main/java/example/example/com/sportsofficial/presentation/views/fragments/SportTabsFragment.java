package example.example.com.sportsofficial.presentation.views.fragments;

import android.os.Bundle;

import java.util.ArrayList;

import dagger.ObjectGraph;
import example.example.com.sportsofficial.R;
import example.example.com.sportsofficial.presentation.views.Injector;

public class SportTabsFragment extends BaseTabsFragment {
    private ObjectGraph mObjectGraph;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Creates tabs
        mTabs = new ArrayList<>();
        mTabs.add(new Tab(LeagueListFragment.TAB_FACTORY, getString(R.string.leagues), getArguments()));
        mTabs.add(new Tab(TournamentListFragment.TAB_FACTORY, getString(R.string.tournaments), getArguments()));
        mTabs.add(new Tab(MatchListFragment.TAB_FACTORY, getString(R.string.matches), getArguments()));
    }

    /**
     * Gets this Fragments's object graph.
     *
     * @return the object graph
     */
    @Override
    public final ObjectGraph getObjectGraph() {
        return ((Injector)getActivity()).getObjectGraph();
    }

    /**
     * Injects a target object using this Fragment's object graph.
     *
     * @param object the target object
     */
    @Override
    public void inject(Object object) {
        mObjectGraph.inject(object);
    }
}
