package example.example.com.sportsofficial.presentation.presenters;

import android.content.Context;
import android.util.Log;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;

import java.util.List;
import java.util.UUID;

import example.example.com.sportsofficial.R;
import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.domain.interactors.AddMatchInteractor;
import example.example.com.sportsofficial.domain.interactors.AddSportInteractor;
import example.example.com.sportsofficial.domain.interactors.GetSportListInteractor;
import example.example.com.sportsofficial.domain.interactors.RemoveSportInteractor;
import example.example.com.sportsofficial.domain.interactors.UpdateSportInteractor;
import example.example.com.sportsofficial.presentation.App;
import example.example.com.sportsofficial.presentation.models.MainModel;
import example.example.com.sportsofficial.presentation.models.Match;
import example.example.com.sportsofficial.presentation.models.Sport;
import example.example.com.sportsofficial.presentation.views.MainView;
import example.example.com.sportsofficial.presentation.views.Navigator;

public class MainPresenterImpl implements MainPresenter {
    private final static UUID PEBBLE_APP_UUID = UUID.fromString("432e9210-e61d-49e2-874f-e899af0472e5");
    private final static int SYNC_KEY = 0;
    private final static int HOME_SCORE_KEY = 1;
    private final static int AWAY_SCORE_KEY = 2;
    private final static int NUM_SCORES_KEY = 3;
    private final static int SINGLE_CLICK_KEY = 10;
    private final static int DOUBLE_CLICK_KEY = 11;
    private final static int LONG_CLICK_KEY = 12;
    private final static int DETAIL_POINTS_START = 100;

    private App mApp;
    private MainModel mModel;
    private MainView mView;

    private AddMatchInteractor mAddMatchInteractor;
    private GetSportListInteractor mGetSportListInteractor;
    private AddSportInteractor mAddSportInteractor;
    private RemoveSportInteractor mRemoveSportInteractor;
    private UpdateSportInteractor mUpdateSportInteractor;

    private Match mTempMatch;

    public MainPresenterImpl(App app, MainModel model, MainView view,
                             AddMatchInteractor addMatchInteractor,
                             GetSportListInteractor getSportListInteractor,
                             AddSportInteractor addSportInteractor,
                             RemoveSportInteractor removeSportInteractor,
                             UpdateSportInteractor updateSportInteractor) {
        mApp = app;
        mModel = model;
        mView = view;

        mAddMatchInteractor = addMatchInteractor;
        mGetSportListInteractor = getSportListInteractor;
        mAddSportInteractor = addSportInteractor;
        mRemoveSportInteractor = removeSportInteractor;
        mUpdateSportInteractor = updateSportInteractor;

        mTempMatch = null;
    }

    /**
     *
     */
    @Override
    public void onCreate() {
        mGetSportListInteractor.execute(mGetSportListCallback);
        mView.navigateToHome();
    }

    @Override
    public void onResume() {
        PebbleKit.registerReceivedDataHandler(mApp, dataHandler);
    }

    @Override
    public void onPause() {
        try {
            if (dataHandler != null) {
                mApp.unregisterReceiver(dataHandler);
                dataHandler = null;
            }
        } catch (IllegalArgumentException e) {

        }
    }

    /**
     *
     * @param navItemType
     * @param sportTitle
     */
    @Override
    public void onNavigationItemClicked(Navigator.NavItemType navItemType, String sportTitle) {
        switch (navItemType) {
            case HOME:
                mView.setActionBarTitle(mApp.getString(R.string.app_name));
                mView.navigateToHome();
                break;
            case SPORT:
                mView.setActionBarTitle(sportTitle);
                Log.i("TAG", Integer.toString(mModel.getSport(sportTitle).getId()));
                mView.navigateToSportView(mModel.getSport(sportTitle).getId());
                break;
            case ADD_SPORT:
                mView.showAddSportDialog();
                break;
            case REMOVE_SPORT:
                mView.showRemoveSportDialog(mModel.getSportsTitles());
                break;
            case SETTINGS:
                mView.navigateToSettings();
                break;
            case ABOUT:
                mView.navigateToAbout();
                break;
            case HELP:
                mView.navigateToHelp();
                break;
            default:
                break;
        }
    }

    @Override
    public void onSyncClicked() {
        PebbleDictionary sync = new PebbleDictionary();
        sync.addInt32(SYNC_KEY, 0);
        PebbleKit.sendDataToPebble(mApp, PEBBLE_APP_UUID, sync);
        mView.showPebbleSyncProgressDialog();
    }


    @Override
    public void onPebbleSportClicked() {
        mView.showChangePebbleSportDialog(mModel.getSportsTitles());
    }

    @Override
    public void onChangePebbleSportPositiveClicked(String sportTitle) {
        Sport sport = mModel.getSport(sportTitle);

        mModel.setCurrentSport(sport);

        PebbleKit.startAppOnPebble(mApp, PEBBLE_APP_UUID);
        PebbleDictionary click_settings = new PebbleDictionary();

        click_settings.addInt32(SINGLE_CLICK_KEY, sport.getSingleClick());
        click_settings.addInt32(DOUBLE_CLICK_KEY, sport.getDoubleClick());
        click_settings.addInt32(LONG_CLICK_KEY, sport.getLongClick());

        PebbleKit.sendDataToPebble(mApp, PEBBLE_APP_UUID, click_settings);
    }

    @Override
    public void onAddMatchDialogPositiveClicked(String homeTeam, String awayTeam) {
        mTempMatch.setHomeTeamName(homeTeam);
        mTempMatch.setAwayTeamName(awayTeam);

        mAddMatchInteractor.execute(mTempMatch, mAddMatchCallback);
    }

    @Override
    public void onAddSportDialogPositiveClicked(String sportTitle, int singleClick,
                                                int doubleClick, int longClick) {

        Sport sport = new Sport(sportTitle, -1, true, true, true);
        sport.setSingleClick(singleClick);
        sport.setDoubleClick(doubleClick);
        sport.setLongClick(longClick);

        mAddSportInteractor.execute(sport, mAddSportCallback);
    }

    @Override
    public void onRemoveSportDialogPositiveClicked(String sportTitle) {
        mRemoveSportInteractor.execute(mModel.getSport(sportTitle).getId(), mRemoveSportCallback);
    }

    PebbleKit.PebbleDataReceiver dataHandler = new PebbleKit.PebbleDataReceiver(PEBBLE_APP_UUID){
        public void receiveData(final Context context, final int transactionID, final PebbleDictionary data) {
            //ACK
            PebbleKit.sendAckToPebble(context, transactionID);

            // Get data
            Long homeScore = data.getInteger(HOME_SCORE_KEY);
            Long awayScore = data.getInteger(AWAY_SCORE_KEY);
            Long numScores = data.getInteger(NUM_SCORES_KEY);

            String detailedScore;
            for(int i = DETAIL_POINTS_START; i < numScores + 100; i++){
                detailedScore = data.getString(i);
            }

            if (mModel.getCurrentSport() == null) {
                mView.closePebbleSyncProgressDialog();
                return;
            }

            mTempMatch = new Match();
            mTempMatch.setSportId(mModel.getCurrentSport().getId());
            mTempMatch.setHomeTeamScore((int) (long) homeScore);
            mTempMatch.setAwayTeamScore((int) (long) awayScore);

            mView.closePebbleSyncProgressDialog();
            mView.showCreateMatchDialog();
        }
    };

    private AddMatchInteractor.Callback mAddMatchCallback = new AddMatchInteractor.Callback() {
        @Override
        public void onMatchAdded(Match match) {
            mView.refreshView();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {

        }
    };

    private GetSportListInteractor.Callback mGetSportListCallback =
            new GetSportListInteractor.Callback() {
        @Override
        public void onSportListLoaded(List<Sport> sportList) {
            for (Sport sport : sportList) {
                if (!mModel.hasSport(sport)) {
                    mModel.addSport(sport);
                    mView.addSportNav(sport);
                }
            }

            if (mModel.getSports().size() > 0) {
                mModel.setCurrentSport(mModel.getSports().get(0));
            }
        }

        @Override
        public void onError(ErrorBundle errorBundle) {

        }
    };

    private AddSportInteractor.Callback mAddSportCallback =
            new AddSportInteractor.Callback() {
        @Override
        public void onSportAdded(Sport sport) {
            mGetSportListInteractor.execute(mGetSportListCallback);
        }

        @Override
        public void onError(ErrorBundle errorBundle) {

        }
    };

    private RemoveSportInteractor.Callback mRemoveSportCallback =
            new RemoveSportInteractor.Callback() {
        @Override
        public void onSportRemoved(int sportId) {
            mView.removeSportNav(mModel.getSport(sportId));
            mModel.removeSport(sportId);
        }

        @Override
        public void onError(ErrorBundle errorBundle) {

        }
    };
}
