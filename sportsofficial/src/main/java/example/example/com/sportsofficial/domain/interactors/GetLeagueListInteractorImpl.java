package example.example.com.sportsofficial.domain.interactors;


import java.util.List;

import javax.inject.Inject;

import example.example.com.sportsofficial.data.repositories.LeagueRepository;
import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.domain.executor.PostExecutionThread;
import example.example.com.sportsofficial.domain.executor.ThreadExecutor;
import example.example.com.sportsofficial.presentation.models.League;

public class GetLeagueListInteractorImpl implements GetLeagueListInteractor {
    private final LeagueRepository mLeagueRepository;
    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mPostExecutionThread;

    private int mSportId;
    private Callback mCallback;

    @Inject
    public GetLeagueListInteractorImpl(LeagueRepository leagueRepository,
                                       ThreadExecutor threadExecutor,
                                       PostExecutionThread postExecutionThread) {
       mLeagueRepository = leagueRepository;
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
        mLeagueRepository.getLeagueList(mSportId, mLeagueListCallback);
    }

    private LeagueRepository.GetLeagueListCallback mLeagueListCallback =
            new LeagueRepository.GetLeagueListCallback() {
        @Override
        public void onLeagueListLoaded(final List<League> leagueList) {
            mPostExecutionThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onLeagueListLoaded(leagueList);
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
