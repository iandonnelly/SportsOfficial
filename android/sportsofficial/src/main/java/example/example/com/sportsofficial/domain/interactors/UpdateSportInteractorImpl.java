package example.example.com.sportsofficial.domain.interactors;

import javax.inject.Inject;

import example.example.com.sportsofficial.data.repositories.SportRepository;
import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.domain.executor.PostExecutionThread;
import example.example.com.sportsofficial.domain.executor.ThreadExecutor;
import example.example.com.sportsofficial.presentation.models.Sport;


public class UpdateSportInteractorImpl implements UpdateSportInteractor {
    private final SportRepository mSportRepository;
    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mPostExecutionThread;

    private Sport mSport;
    private Callback mCallback;

    @Inject
    public UpdateSportInteractorImpl(SportRepository sportRepository,
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
        mSportRepository.updateSport(mSport, mUpdateSportCallback);
    }

    private SportRepository.UpdateSportCallback mUpdateSportCallback =
            new SportRepository.UpdateSportCallback() {
        @Override
        public void onSportUpdated() {
            mPostExecutionThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onSportUpdated();
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
