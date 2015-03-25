package example.example.com.sportsofficial.domain.interactors;

import javax.inject.Inject;

import example.example.com.sportsofficial.data.repositories.LeagueRepository;
import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.domain.executor.PostExecutionThread;
import example.example.com.sportsofficial.domain.executor.ThreadExecutor;
import example.example.com.sportsofficial.presentation.models.League;


public class UpdateLeagueInteractorImpl implements UpdateLeagueInteractor {
    private final LeagueRepository mLeagueRepository;
    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mPostExecutionThread;

    private League mLeague;
    private Callback mCallback;

    @Inject
    public UpdateLeagueInteractorImpl(LeagueRepository leagueRepository,
                                      ThreadExecutor threadExecutor,
                                      PostExecutionThread postExecutionThread) {
        mLeagueRepository = leagueRepository;
        mThreadExecutor = threadExecutor;
        mPostExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(League league, Callback callback) {
        mLeague = league;
        mCallback = callback;
        mThreadExecutor.execute(this);
    }

    @Override
    public void run() {
        mLeagueRepository.updateLeague(mLeague, mUpdateLeagueCallback);
    }

    private LeagueRepository.UpdateLeagueCallback mUpdateLeagueCallback =
            new LeagueRepository.UpdateLeagueCallback() {
        @Override
        public void onLeagueUpdated() {
            mPostExecutionThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onLeagueUpdated();
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
