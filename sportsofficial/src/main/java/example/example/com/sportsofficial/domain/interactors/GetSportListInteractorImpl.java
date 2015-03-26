package example.example.com.sportsofficial.domain.interactors;

import java.util.List;

import javax.inject.Inject;

import example.example.com.sportsofficial.data.repositories.SportRepository;
import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.domain.executor.PostExecutionThread;
import example.example.com.sportsofficial.domain.executor.ThreadExecutor;
import example.example.com.sportsofficial.presentation.models.Sport;

public class GetSportListInteractorImpl implements GetSportListInteractor {
    private final SportRepository mSportRepository;
    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mPostExecutionThread;

    private Callback mCallback;

    @Inject
    public GetSportListInteractorImpl(SportRepository sportRepository,
                                  ThreadExecutor threadExecutor,
                                  PostExecutionThread postExecutionThread) {
        mSportRepository = sportRepository;
        mThreadExecutor = threadExecutor;
        mPostExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(Callback callback) {
        mCallback = callback;
        mThreadExecutor.execute(this);
    }

    @Override
    public void run() {
        mSportRepository.getSportList(mGetSportListCallback);
    }

    private SportRepository.GetSportListCallback mGetSportListCallback = new SportRepository.GetSportListCallback() {
        @Override
        public void onSportListLoaded(final List<Sport> sportList) {
            mPostExecutionThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onSportListLoaded(sportList);
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
