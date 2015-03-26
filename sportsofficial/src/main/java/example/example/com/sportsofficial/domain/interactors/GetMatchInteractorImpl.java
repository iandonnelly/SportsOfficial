package example.example.com.sportsofficial.domain.interactors;

import javax.inject.Inject;

import example.example.com.sportsofficial.data.repositories.MatchRepository;
import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.domain.executor.PostExecutionThread;
import example.example.com.sportsofficial.domain.executor.ThreadExecutor;
import example.example.com.sportsofficial.presentation.models.Match;

public class GetMatchInteractorImpl implements GetMatchInteractor {
    private final MatchRepository mMatchRepository;
    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mPostExecutionThread;

    private int mMatchId;
    private Callback mCallback;

    @Inject
    public GetMatchInteractorImpl(MatchRepository matchRepository,
                                  ThreadExecutor threadExecutor,
                                  PostExecutionThread postExecutionThread) {
        mMatchRepository = matchRepository;
        mThreadExecutor = threadExecutor;
        mPostExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(final int matchId, final Callback callback) {
        mMatchId = matchId;
        mCallback = callback;
        mThreadExecutor.execute(this);
    }

    @Override
    public void run() {
        mMatchRepository.getMatchByID(mMatchId, mGetMatchCallback);
    }

    private MatchRepository.GetMatchCallback mGetMatchCallback =
            new MatchRepository.GetMatchCallback() {
        @Override
        public void onMatchLoaded(final Match match) {
            mPostExecutionThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onMatchLoaded(match);
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
