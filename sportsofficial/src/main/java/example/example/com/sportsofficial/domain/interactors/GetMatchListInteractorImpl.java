package example.example.com.sportsofficial.domain.interactors;


import java.util.List;

import javax.inject.Inject;

import example.example.com.sportsofficial.data.repositories.MatchRepository;
import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.domain.executor.PostExecutionThread;
import example.example.com.sportsofficial.domain.executor.ThreadExecutor;
import example.example.com.sportsofficial.presentation.models.Match;


public class GetMatchListInteractorImpl implements GetMatchListInteractor {
    private final MatchRepository mMatchRepository;
    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mPostExecutionThread;

    private int mSportId;
    private int mTournamentId;
    private int mLeagueId;
    private boolean mIsSportExecution;
    private boolean mIsTournamentExecution;
    private boolean mIsLeagueExecution;

    private Callback mCallback;

    @Inject
    public GetMatchListInteractorImpl(MatchRepository matchRepository,
                                      ThreadExecutor threadExecutor,
                                      PostExecutionThread postExecutionThread) {
        mMatchRepository = matchRepository;
        mThreadExecutor = threadExecutor;
        mPostExecutionThread = postExecutionThread;
    }

    @Override
    public void executeSport(int sportId, Callback callback) {
        mSportId = sportId;
        mCallback = callback;

        mIsSportExecution = true;
        mIsTournamentExecution = false;
        mIsLeagueExecution = false;

        mThreadExecutor.execute(this);
    }

    @Override
    public void executeTournament(int tournamentId, Callback callback) {
        mTournamentId = tournamentId;
        mCallback = callback;

        mIsSportExecution = false;
        mIsTournamentExecution = true;
        mIsLeagueExecution = false;

        mThreadExecutor.execute(this);
    }

    @Override
    public void executeLeague(int leagueId, Callback callback) {
        mLeagueId = leagueId;
        mCallback = callback;

        mIsSportExecution = false;
        mIsTournamentExecution = false;
        mIsLeagueExecution = true;

        mThreadExecutor.execute(this);
    }

    @Override
    public void run() {
        if (mIsSportExecution) {
            mMatchRepository.getMatchListBySportId(mSportId, mGetMatchListCallback);
        } else if (mIsTournamentExecution) {
            mMatchRepository.getMatchListByLeagueId(mLeagueId, mGetMatchListCallback);
        } else if (mIsLeagueExecution) {
            mMatchRepository.getMatchListByTournamentID(mTournamentId, mGetMatchListCallback);
        }
    }

    private MatchRepository.GetMatchListCallback mGetMatchListCallback =
            new MatchRepository.GetMatchListCallback() {
        @Override
        public void onMatchListLoaded(final List<Match> matchList) {
            mPostExecutionThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onMatchListLoaded(matchList);
                }
            });
        }

        @Override
        public void onError(final ErrorBundle errorBundle) {
            mPostExecutionThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onError(errorBundle);
                }
            });
        }
    };
}
