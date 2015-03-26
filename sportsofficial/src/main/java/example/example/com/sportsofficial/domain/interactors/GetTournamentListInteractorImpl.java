package example.example.com.sportsofficial.domain.interactors;

import java.util.List;

import javax.inject.Inject;

import example.example.com.sportsofficial.data.repositories.TournamentRepository;
import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.domain.executor.PostExecutionThread;
import example.example.com.sportsofficial.domain.executor.ThreadExecutor;
import example.example.com.sportsofficial.presentation.models.Tournament;


public class GetTournamentListInteractorImpl implements GetTournamentListInteractor {
    private final TournamentRepository mTournamentRepository;
    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mPostExecutionThread;

    private int mSportId;
    private Callback mCallback;

    @Inject
    public GetTournamentListInteractorImpl(TournamentRepository tournamentRepository,
                                           ThreadExecutor threadExecutor,
                                           PostExecutionThread postExecutionThread) {
        mTournamentRepository = tournamentRepository;
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
        mTournamentRepository.getTournamentList(mSportId, mGetTournamentListCallback);
    }

    private TournamentRepository.GetTournamentListCallback mGetTournamentListCallback =
            new TournamentRepository.GetTournamentListCallback() {
        @Override
        public void onTournamentListLoaded(final List<Tournament> tournamentList) {
            mPostExecutionThread.post(new Runnable() {
                @Override
                public void run() {
                   mCallback.onTournamentListLoaded(tournamentList);
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
