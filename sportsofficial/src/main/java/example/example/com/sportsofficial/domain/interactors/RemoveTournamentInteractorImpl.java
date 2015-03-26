package example.example.com.sportsofficial.domain.interactors;

import javax.inject.Inject;

import example.example.com.sportsofficial.data.repositories.TournamentRepository;
import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.domain.executor.PostExecutionThread;
import example.example.com.sportsofficial.domain.executor.ThreadExecutor;

public class RemoveTournamentInteractorImpl implements RemoveTournamentInteractor {
    private final TournamentRepository mTournamentRepository;
    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mPostExecutionThread;

    private int mTournamentId;
    private RemoveTournamentInteractor.Callback mCallback;

    @Inject
    public RemoveTournamentInteractorImpl(TournamentRepository tournamentRepository,
                                          ThreadExecutor threadExecutor,
                                          PostExecutionThread postExecutionThread) {
        mTournamentRepository = tournamentRepository;
        mThreadExecutor = threadExecutor;
        mPostExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(int tournamentId, Callback callback) {
        mTournamentId = tournamentId;
        mCallback = callback;
        mThreadExecutor.execute(this);
    }

    @Override
    public void run() {
        mTournamentRepository.removeTournament(mTournamentId, mRemoveTournamentCallback);
    }

    private TournamentRepository.RemoveTournamentCallback mRemoveTournamentCallback =
            new TournamentRepository.RemoveTournamentCallback() {
        @Override
        public void onTournamentRemoved() {
            mPostExecutionThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onTournamentRemoved();
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
