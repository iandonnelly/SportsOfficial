package example.example.com.sportsofficial.presentation.views.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;
import example.example.com.sportsofficial.R;
import example.example.com.sportsofficial.presentation.models.Match;
import example.example.com.sportsofficial.presentation.modules.MatchModule;
import example.example.com.sportsofficial.presentation.presenters.MatchPresenter;
import example.example.com.sportsofficial.presentation.views.Injector;
import example.example.com.sportsofficial.presentation.views.MatchView;

public class MatchFragment extends Fragment implements MatchView {
    @Inject
    MatchPresenter mPresenter;
    private ObjectGraph mObjectGraph;

    private TextView mDateView;
    private ImageView mHomeTeamImage;
    private ImageView mAwayTeamImage;
    private TextView mScoreView;
    private TextView mHomeTeamName;
    private TextView mAwayTeamName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int matchId = getArguments().getInt(getString(R.string.match_id));

        // Setup dagger graph
        List<Object> modules = Arrays.<Object>asList(new MatchModule(this));
        mObjectGraph = ((Injector) getActivity()).getObjectGraph().plus(modules.toArray());
        mObjectGraph.inject(this);

        mPresenter.setMatchId(matchId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.match_fragment, container, false);

        mDateView = (TextView) view.findViewById(R.id.date);
        mHomeTeamImage = (ImageView) view.findViewById(R.id.home_team_image);
        mAwayTeamImage = (ImageView) view.findViewById(R.id.away_team_image);
        mScoreView = (TextView) view.findViewById(R.id.score);
        mHomeTeamName = (TextView) view.findViewById(R.id.home_team_name);
        mAwayTeamName = (TextView) view.findViewById(R.id.away_team_name);

        mPresenter.onCreateView();
        return view;
    }

    @Override
    public void setMatch(final Match match) {
        mDateView.setText(match.getDate().toString());
        //mHomeTeamImage.setImageResource();
        //mAwayTeamImage.setImageResource();
        mHomeTeamName.setText(match.getHomeTeamName());
        mAwayTeamName.setText(match.getAwayTeamName());
        mScoreView.setText(Integer.toString(match.getHomeTeamScore())
                + " - " + Integer.toString(match.getAwayTeamScore()));
}
}
