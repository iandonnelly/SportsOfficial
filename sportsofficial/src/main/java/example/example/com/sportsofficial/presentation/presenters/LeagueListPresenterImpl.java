package example.example.com.sportsofficial.presentation.presenters;

import java.util.List;

import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.domain.interactors.AddLeagueInteractor;
import example.example.com.sportsofficial.domain.interactors.GetLeagueListInteractor;
import example.example.com.sportsofficial.domain.interactors.RemoveLeagueInteractor;
import example.example.com.sportsofficial.domain.interactors.UpdateLeagueInteractor;
import example.example.com.sportsofficial.presentation.models.League;
import example.example.com.sportsofficial.presentation.models.LeagueListModel;
import example.example.com.sportsofficial.presentation.models.Location;
import example.example.com.sportsofficial.presentation.views.LeagueListView;

public class LeagueListPresenterImpl implements LeagueListPresenter {
    private LeagueListModel mModel;
    private LeagueListView mView;

    private GetLeagueListInteractor mGetLeagueListInteractor;
    private AddLeagueInteractor mAddLeagueInteractor;
    private RemoveLeagueInteractor mRemoveLeagueInteractor;
    private UpdateLeagueInteractor mUpdateLeagueInteractor;

    public LeagueListPresenterImpl(LeagueListModel model, LeagueListView view,
                                   GetLeagueListInteractor getLeagueListInteractor,
                                   AddLeagueInteractor addLeagueInteractor,
                                   RemoveLeagueInteractor removeLeagueInteractor,
                                   UpdateLeagueInteractor updateLeagueInteractor) {
        mModel = model;
        mView = view;

        mGetLeagueListInteractor = getLeagueListInteractor;
        mAddLeagueInteractor = addLeagueInteractor;
        mRemoveLeagueInteractor = removeLeagueInteractor;
        mUpdateLeagueInteractor = updateLeagueInteractor;
    }

    @Override
    public void setSportId(int sportId) {
        mModel.setSport(sportId);
    }

    @Override
    public void onCreateView() {
        mModel.addObserver(mView);
        mGetLeagueListInteractor.execute(mModel.getSportId(), mGetLeagueListCallback);
    }

    @Override
    public void onResume() {
        mView.setLeagueList(mModel.getLeagueList());
        mModel.addObserver(mView);
    }

    @Override
    public void onPause() {
        mModel.deleteObserver(mView);
    }

    @Override
    public void onLeagueClicked(int position) {
        mView.showLeagueDetails(position);
    }

    @Override
    public void onAddLeagueClicked() {
        mView.showAddLeagueDialog();
    }

    @Override
    public void onAddLeagueCreateClicked(String name, String country, String city, String address) {
        League league = new League();
        Location location = new Location(country, city);

        league.setName(name);
        league.setLocation(location);

        mAddLeagueInteractor.execute(league, mAddLeagueCallback);
    }

    @Override
    public void onRemoveLeagueClicked(final int position) {
        mModel.removeLeague(position);
        mRemoveLeagueInteractor.execute(mModel.getLeagueList().get(position).getLeagueId(), mRemoveLeagueCallback);
    }

    @Override
    public void onUpdateLeagueClicked(int position) {
        mUpdateLeagueInteractor.execute(mModel.getLeagueList().get(position), mUpdateLeagueCallback);;
    }

    private GetLeagueListInteractor.Callback mGetLeagueListCallback = new GetLeagueListInteractor.Callback() {
        @Override
        public void onLeagueListLoaded(List<League> leagueList) {

        }

        @Override
        public void onError(ErrorBundle errorBundle) {

        }
    };

    private AddLeagueInteractor.Callback mAddLeagueCallback = new AddLeagueInteractor.Callback() {
        @Override
        public void onLeagueAdded() {

        }

        @Override
        public void onError(ErrorBundle errorBundle) {

        }
    };


    private RemoveLeagueInteractor.Callback mRemoveLeagueCallback = new RemoveLeagueInteractor.Callback() {
        @Override
        public void onLeagueRemoved() {

        }

        @Override
        public void onError(ErrorBundle errorBundle) {

        }
    };

    private UpdateLeagueInteractor.Callback mUpdateLeagueCallback = new UpdateLeagueInteractor.Callback() {
        @Override
        public void onLeagueUpdated() {

        }

        @Override
        public void onError(ErrorBundle errorBundle) {

        }
    };
}
