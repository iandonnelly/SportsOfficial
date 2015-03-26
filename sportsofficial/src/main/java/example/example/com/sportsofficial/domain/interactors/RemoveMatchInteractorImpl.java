package example.example.com.sportsofficial.domain.interactors;

import javax.inject.Inject;

import example.example.com.sportsofficial.data.repositories.MatchRepository;
import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.domain.executor.PostExecutionThread;
import example.example.com.sportsofficial.domain.executor.ThreadExecutor;

public class RemoveMatchInteractorImpl implements RemoveMatchInteractor {
    private MatchRepository mMatchRepository;
    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mPostExecutionThread;

    private Callback mCallback;
    private int mMatchId;

    @Inject
    public RemoveMatchInteractorImpl(MatchRepository matchRepository,
                                     ThreadExecutor threadExecutor,
                                     PostExecutionThread postExecutionThread) {
        mMatchRepository = matchRepository;
        mThreadExecutor = threadExecutor;
        mPostExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(int matchId, RemoveMatchInteractor.Callback callback) {
        mCallback = callback;
        mMatchId = matchId;
        mThreadExecutor.execute(this);
    }

    @Override
    public void run() {
        mMatchRepository.removeMatch(mMatchId, mRemoveMatchCallback);
    }

    private MatchRepository.RemoveMatchCallback mRemoveMatchCallback =
            new MatchRepository.RemoveMatchCallback() {
        @Override
        public void onMatchRemoved(final int matchId) {
            mPostExecutionThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onMatchRemoved(matchId);
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
