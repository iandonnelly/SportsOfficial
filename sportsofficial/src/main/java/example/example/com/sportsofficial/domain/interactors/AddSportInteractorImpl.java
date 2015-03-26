package example.example.com.sportsofficial.domain.interactors;


import javax.inject.Inject;

import example.example.com.sportsofficial.data.repositories.SportRepository;
import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.domain.executor.PostExecutionThread;
import example.example.com.sportsofficial.domain.executor.ThreadExecutor;
import example.example.com.sportsofficial.presentation.models.Sport;

public class AddSportInteractorImpl implements AddSportInteractor {
    private final SportRepository mSportRepository;
    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mPostExecutionThread;

    private Sport mSport;
    private Callback mCallback;

    @Inject
    public AddSportInteractorImpl(SportRepository sportRepository,
                                ThreadExecutor threadExecutor,
                                PostExecutionThread postExecutionThread) {
        mSportRepository = sportRepository;
        mThreadExecutor = threadExecutor;
        mPostExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(Sport sport, Callback callback) {
        mSport = sport;
        mCallback = callback;
        mThreadExecutor.execute(this);
    }

    @Override
    public void run() {
        mSportRepository.addSport(mSport, mAddSportCallback);
    }

    private SportRepository.AddSportCallback mAddSportCallback =
            new SportRepository.AddSportCallback() {
        @Override
        public void onSportAdded(final Sport sport) {
            mPostExecutionThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onSportAdded(sport);
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
