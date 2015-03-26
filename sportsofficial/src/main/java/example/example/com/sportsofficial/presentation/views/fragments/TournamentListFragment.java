package example.example.com.sportsofficial.presentation.views.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.Observable;

import javax.inject.Inject;

import dagger.ObjectGraph;
import example.example.com.sportsofficial.R;
import example.example.com.sportsofficial.presentation.models.Tournament;
import example.example.com.sportsofficial.presentation.modules.TournamentListModule;
import example.example.com.sportsofficial.presentation.presenters.TournamentListPresenter;
import example.example.com.sportsofficial.presentation.views.TournamentListView;
import example.example.com.sportsofficial.presentation.views.dialogs.CreateTournamentDialogFragment;

public class TournamentListFragment extends Fragment implements TournamentListView,
        CreateTournamentDialogFragment.DialogClickListener {
    public final static BaseTabsFragment.TabFactory TAB_FACTORY = new BaseTabsFragment.TabFactory() {
        @Override
        public Fragment newInstance(Bundle args) {
            Fragment fragment = new TournamentListFragment();
            fragment.setArguments(args);
            return fragment;
        }
    };

    @Inject
    TournamentListPresenter mPresenter;
    private ObjectGraph mObjectGraph;
    private boolean mIsFirstAttach = true;

    private ListView mListView;
    private ImageButton mImageButton;
    private TournamentsAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int sportId = getArguments().getInt(getString(R.string.sport), -1);

        if (sportId != -1) {
            mPresenter.setSportId(sportId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_view_fab, container, false);

        mListView = (ListView) view.findViewById(R.id.list);

        mImageButton = (ImageButton) view.findViewById(R.id.fab_image_button);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onAddTournamentClicked();
            }
        });

        mPresenter.onCreateView();

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Setup the dagger graph
        List<Object> modules = Arrays.<Object>asList(new TournamentListModule(this));

        FragmentManager manager = getActivity().getSupportFragmentManager();
        SportTabsFragment tabFragment = (SportTabsFragment) manager.findFragmentByTag("TabFragment");

        mObjectGraph = tabFragment.getObjectGraph().plus(modules.toArray());

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

    @Override
    public void showTournamentDetails(int tournamentId) {
        // Create the match tab fragment
        MatchListFragment fragment = new MatchListFragment();

        // Create and add the arguments to the fragment
        Bundle args = new Bundle();
        args.putInt(getString(R.string.tournament), tournamentId);
        fragment.setArguments(args);

        // Insert the fragment by replacing the existing fragment
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment, fragment, "MatchListFragment").commit();
    }

    @Override
    public void showAddTournamentDialog() {
        DialogFragment dialog = new CreateTournamentDialogFragment();
        dialog.setTargetFragment(this, 0);
        dialog.show(getActivity().getSupportFragmentManager(), "CreateTournamentDialogFragment");
    }

    @Override
    public void setTournamentsList(List<Tournament> TournamentsList) {
        mAdapter = new TournamentsAdapter(getActivity(), R.layout.tournament_row_layout, TournamentsList);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void update(Observable observable, Object data) {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPositiveClick(String name, String venue, String country, String city,
                                String address, int year, int month, int day) {
        mPresenter.onAddTournamentCreateClicked(name, venue, country, city, address, year, month,
                day);
    }

    private class TournamentsAdapter extends ArrayAdapter<Tournament> {
        private Activity mContext;
        private List<Tournament> mTournamentsList;

        public TournamentsAdapter (Activity context, int resource, List<Tournament> TournamentsList) {
            super(context, resource, TournamentsList);
            mContext = context;
            mTournamentsList = TournamentsList;
        }

        private class ViewHolder {
            TextView name;
            TextView date;
            TextView location;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;

            // If the row has not been instantiated, inflate the view and setup the view holder
            if (rowView == null) {
                LayoutInflater inflater = mContext.getLayoutInflater();
                rowView = inflater.inflate(R.layout.tournament_row_layout, null);

                ViewHolder viewHolder = new ViewHolder();
                viewHolder.name = (TextView) rowView.findViewById(R.id.name);
                viewHolder.date = (TextView) rowView.findViewById(R.id.date);
                viewHolder.location = (TextView) rowView.findViewById(R.id.location);
                rowView.setTag(viewHolder);
            }

            if (mTournamentsList.get(position) != null) {
                ViewHolder holder = (ViewHolder) rowView.getTag();

                holder.name.setText(mTournamentsList.get(position).getName());
                holder.date.setText(mTournamentsList.get(position).getDate().toString());
                holder.location.setText((mTournamentsList).get(position).getLocation().toString());
            }

            return rowView;
        }
    }
}
