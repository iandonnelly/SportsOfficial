package example.example.com.sportsofficial.domain.interactors;


import javax.inject.Inject;

import example.example.com.sportsofficial.data.repositories.MatchRepository;
import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.domain.executor.PostExecutionThread;
import example.example.com.sportsofficial.domain.executor.ThreadExecutor;
import example.example.com.sportsofficial.presentation.models.Match;


public class UpdateMatchInteractorImpl implements UpdateMatchInteractor {
    private final MatchRepository mMatchRepository;
    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mPostExecutionThread;

    private Match mMatch;
    private UpdateMatchInteractor.Callback mCallback;

    @Inject
    public UpdateMatchInteractorImpl(MatchRepository matchRepository,
                                     ThreadExecutor threadExecutor,
                                     PostExecutionThread postExecutionThread) {
        mMatchRepository = matchRepository;
        mThreadExecutor = threadExecutor;
        mPostExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(Match match, UpdateMatchInteractor.Callback callback) {
        mMatch = match;
        mCallback = callback;
        mThreadExecutor.execute(this);
    }


    @Override
    public void run() {
       mMatchRepository.updateMatch(mMatch, mUpdateMatchCallback);
    }

    private MatchRepository.UpdateMatchCallback mUpdateMatchCallback =
            new MatchRepository.UpdateMatchCallback() {
        @Override
        public void onMatchUpdated(Match match) {
            mPostExecutionThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onMatchUpdated();
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
