package example.example.com.sportsofficial.domain.interactors;

import javax.inject.Inject;

import example.example.com.sportsofficial.data.repositories.SportRepository;
import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.domain.executor.PostExecutionThread;
import example.example.com.sportsofficial.domain.executor.ThreadExecutor;

public class RemoveSportInteractorImpl implements RemoveSportInteractor {
    private final SportRepository mSportRepository;
    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mPostExecutionThread;

    private int mSportId;
    private Callback mCallback;

    @Inject
    public RemoveSportInteractorImpl(SportRepository sportRepository,
                                  ThreadExecutor threadExecutor,
                                  PostExecutionThread postExecutionThread) {
        mSportRepository = sportRepository;
        mThreadExecutor = threadExecutor;
        mPostExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(int sportId, Callback callback) {
        mSportId = sportId;
        mCallback = callback;
        mThreadExecutor.execute(this);
    }

    @Override
    public void run() {
        mSportRepository.removeSport(mSportId, mRemoveSportCallback);
    }

    private SportRepository.RemoveSportCallback mRemoveSportCallback =
            new SportRepository.RemoveSportCallback() {
        @Override
        public void onSportRemoved(final int sportId) {
            mPostExecutionThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onSportRemoved(sportId);
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
