package example.example.com.sportsofficial.domain.interactors;

import javax.inject.Inject;

import example.example.com.sportsofficial.data.repositories.MatchRepository;
import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.domain.executor.PostExecutionThread;
import example.example.com.sportsofficial.domain.executor.ThreadExecutor;
import example.example.com.sportsofficial.presentation.models.Match;


public class AddMatchInteractorImpl implements AddMatchInteractor {
    private final MatchRepository mMatchRepository;
    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mPostExecutionThread;

    private Match mMatch;
    private Callback mCallback;

    @Inject
    public AddMatchInteractorImpl(MatchRepository matchRepository,
                                  ThreadExecutor threadExecutor,
                                  PostExecutionThread postExecutionThread) {
        mMatchRepository = matchRepository;
        mThreadExecutor = threadExecutor;
        mPostExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(Match match, Callback callback) {
        mMatch = match;
        mCallback = callback;
        mThreadExecutor.execute(this);
    }

    @Override
    public void run() {
        mMatchRepository.addMatch(mMatch, mAddMatchCallback);
    }

    private  MatchRepository.AddMatchCallback mAddMatchCallback =
            new MatchRepository.AddMatchCallback() {
        @Override
        public void onMatchAdded(final Match match) {
            mPostExecutionThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onMatchAdded(match);
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
