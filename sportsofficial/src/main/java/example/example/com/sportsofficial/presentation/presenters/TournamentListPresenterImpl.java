package example.example.com.sportsofficial.presentation.presenters;

import java.util.List;

import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.domain.interactors.AddTournamentInteractor;
import example.example.com.sportsofficial.domain.interactors.GetTournamentListInteractor;
import example.example.com.sportsofficial.domain.interactors.RemoveTournamentInteractor;
import example.example.com.sportsofficial.domain.interactors.UpdateTournamentInteractor;
import example.example.com.sportsofficial.presentation.models.Date;
import example.example.com.sportsofficial.presentation.models.Location;
import example.example.com.sportsofficial.presentation.models.Tournament;
import example.example.com.sportsofficial.presentation.models.TournamentListModel;
import example.example.com.sportsofficial.presentation.views.TournamentListView;

public class TournamentListPresenterImpl implements TournamentListPresenter {
    private TournamentListModel mModel;
    private TournamentListView mView;
    
    private GetTournamentListInteractor mGetTournamentListInteractor;
    private AddTournamentInteractor mAddTournamentInteractor;
    private RemoveTournamentInteractor mRemoveTournamentInteractor;
    private UpdateTournamentInteractor mUpdateTournamentInteractor;

    public TournamentListPresenterImpl(TournamentListModel model, TournamentListView view,
                                       GetTournamentListInteractor getTournamentListInteractor,
                                       AddTournamentInteractor addTournamentInteractor,
                                       RemoveTournamentInteractor removeTournamentInteractor,
                                       UpdateTournamentInteractor updateTournamentInteractor) {
        mModel = model;
        mView = view;

        mGetTournamentListInteractor = getTournamentListInteractor;
        mAddTournamentInteractor = addTournamentInteractor;
        mRemoveTournamentInteractor = removeTournamentInteractor;
        mUpdateTournamentInteractor = updateTournamentInteractor;
    }

    @Override
    public void setSportId(int sportId) {
        mModel.setSportId(sportId);
    }

    @Override
    public void onCreateView() {
        mModel.addObserver(mView);
        mGetTournamentListInteractor.execute(mModel.getSportId(), mGetTournamentsCallback);
    }

    @Override
    public void onResume() {
        mView.setTournamentsList(mModel.getTournamentList());
        mModel.addObserver(mView);
    }

    @Override
    public void onPause() {
        mModel.deleteObserver(mView);
    }

    @Override
    public void onAddTournamentClicked() {
        mView.showAddTournamentDialog();
    }

    @Override
    public void onAddTournamentCreateClicked(String name, String venue, String country, String city,
                                             String address, int year, int month, int day) {

        Tournament tournament = new Tournament();
        Location location = new Location(country, city);
        Date date = new Date();

        tournament.setName(name);
        tournament.setVenue(venue);
        tournament.setLocation(location);
        tournament.setDate(date);

        mAddTournamentInteractor.execute(tournament, mAddTournamentCallback);
    }

    @Override
    public void onRemoveTournamentClicked(final int position) {
        mRemoveTournamentInteractor.execute(mModel.getTournamentList().get(position)
                        .getTournamentId(), mRemoveTournamentCallback);
    }

    @Override
    public void onEditTournamentClicked(final int position) {
        mUpdateTournamentInteractor.execute(mModel.getTournamentList().get(position), mUpdateTournamentCallback);
    }

    private GetTournamentListInteractor.Callback mGetTournamentsCallback = new GetTournamentListInteractor.Callback() {

        @Override
        public void onTournamentListLoaded(List<Tournament> tournamentList) {

        }

        @Override
        public void onError(ErrorBundle errorBundle) {

        }
    };

    private AddTournamentInteractor.Callback mAddTournamentCallback = new AddTournamentInteractor.Callback() {
        @Override
        public void onTournamentAdded() {

        }

        @Override
        public void onError(ErrorBundle errorBundle) {

        }
    };


    private RemoveTournamentInteractor.Callback mRemoveTournamentCallback = new RemoveTournamentInteractor.Callback() {
        @Override
        public void onTournamentRemoved() {

        }

        @Override
        public void onError(ErrorBundle errorBundle) {

        }
    };

    private UpdateTournamentInteractor.Callback mUpdateTournamentCallback = new UpdateTournamentInteractor.Callback() {
        @Override
        public void onTournamentUpdated() {

        }

        @Override
        public void onError(ErrorBundle errorBundle) {

        }
    };
}
