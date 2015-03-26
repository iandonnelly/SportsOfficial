package example.example.com.sportsofficial.presentation.presenters;


import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.domain.interactors.GetMatchInteractor;
import example.example.com.sportsofficial.domain.interactors.RemoveMatchInteractor;
import example.example.com.sportsofficial.domain.interactors.UpdateMatchInteractor;
import example.example.com.sportsofficial.presentation.models.Match;
import example.example.com.sportsofficial.presentation.models.MatchModel;
import example.example.com.sportsofficial.presentation.views.MatchView;

public class MatchPresenterImpl implements  MatchPresenter {
    private MatchModel mModel;
    private MatchView mView;

    private GetMatchInteractor mGetMatchInteractor;
    private RemoveMatchInteractor mRemoveMatchInteractor;
    private UpdateMatchInteractor mUpdateMatchInteractor;

    public MatchPresenterImpl(MatchModel model, MatchView view,
                              GetMatchInteractor getMatchInteractor,
                              RemoveMatchInteractor removeMatchInteractor,
                              UpdateMatchInteractor updateMatchInteractor) {
        mModel = model;
        mView = view;

        mGetMatchInteractor = getMatchInteractor;
        mRemoveMatchInteractor = removeMatchInteractor;
        mUpdateMatchInteractor = updateMatchInteractor;
    }

    @Override
    public void setMatchId(int matchId) {

    }

    @Override
    public void onCreateView() {
        mGetMatchInteractor.execute(mModel.getMatch().getMatchId(), mGetMatchCallback);
    }


    private GetMatchInteractor.Callback mGetMatchCallback = new GetMatchInteractor.Callback() {
        @Override
        public void onMatchLoaded(Match match) {
            mModel.setMatch(match);
            mView.setMatch(match);
        }

        @Override
        public void onError(ErrorBundle errorBundle) {

        }
    };
}
