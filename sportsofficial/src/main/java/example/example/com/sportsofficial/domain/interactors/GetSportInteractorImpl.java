package example.example.com.sportsofficial.domain.interactors;


import javax.inject.Inject;

import example.example.com.sportsofficial.data.repositories.SportRepository;
import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.domain.executor.PostExecutionThread;
import example.example.com.sportsofficial.domain.executor.ThreadExecutor;
import example.example.com.sportsofficial.presentation.models.Sport;


public class GetSportInteractorImpl implements GetSportInteractor {
    private final SportRepository mSportRepository;
    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mPostExecutionThread;

    private int mSportId;
    private Callback mCallback;

    @Inject
    public GetSportInteractorImpl(SportRepository sportRepository,
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
        mSportRepository.getSportById(mSportId, mGetSportCallback);
    }

    private SportRepository.GetSportCallback mGetSportCallback = new SportRepository.GetSportCallback() {
        @Override
        public void onSportLoaded(final Sport sport) {
            mPostExecutionThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onSportLoaded(sport);
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
