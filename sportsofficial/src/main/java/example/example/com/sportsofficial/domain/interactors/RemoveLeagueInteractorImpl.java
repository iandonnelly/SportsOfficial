package example.example.com.sportsofficial.domain.interactors;

import javax.inject.Inject;

import example.example.com.sportsofficial.data.repositories.LeagueRepository;
import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.domain.executor.PostExecutionThread;
import example.example.com.sportsofficial.domain.executor.ThreadExecutor;

public class RemoveLeagueInteractorImpl implements RemoveLeagueInteractor {
    private final LeagueRepository mLeagueRepository;
    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mPostExecutionThread;

    private int mLeagueId;
    private Callback mCallback;

    @Inject
    public RemoveLeagueInteractorImpl(LeagueRepository leagueRepository,
                                      ThreadExecutor threadExecutor,
                                      PostExecutionThread postExecutionThread) {
        mLeagueRepository = leagueRepository;
        mThreadExecutor = threadExecutor;
        mPostExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(int leagueId, Callback callback) {
        mLeagueId = leagueId;
        mCallback = callback;
        mThreadExecutor.execute(this);
    }

    @Override
    public void run() {
        mLeagueRepository.removeLeague(mLeagueId, mRemoveLeagueCallback);
    }

    private LeagueRepository.RemoveLeagueCallback mRemoveLeagueCallback =
            new LeagueRepository.RemoveLeagueCallback() {
        @Override
        public void onLeagueRemoved() {
            mPostExecutionThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onLeagueRemoved();
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
