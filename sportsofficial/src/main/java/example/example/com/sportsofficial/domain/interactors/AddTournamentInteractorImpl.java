package example.example.com.sportsofficial.domain.interactors;

import javax.inject.Inject;

import example.example.com.sportsofficial.data.repositories.TournamentRepository;
import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.domain.executor.PostExecutionThread;
import example.example.com.sportsofficial.domain.executor.ThreadExecutor;
import example.example.com.sportsofficial.presentation.models.Tournament;

public class AddTournamentInteractorImpl implements AddTournamentInteractor {
    private final TournamentRepository mTournamentRepository;
    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mPostExecutionThread;

    private Tournament mTournament;
    private Callback mCallback;

    @Inject
    public AddTournamentInteractorImpl(TournamentRepository tournamentRepository,
                                       ThreadExecutor threadExecutor,
                                       PostExecutionThread postExecutionThread) {
       mTournamentRepository = tournamentRepository;
       mThreadExecutor = threadExecutor;
       mPostExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(Tournament tournament, Callback callback) {
        mTournament = tournament;
        mCallback = callback;
        mThreadExecutor.execute(this);
    }

    @Override
    public void run() {
        mTournamentRepository.addTournament(mTournament, mAddTournamentCallback);
    }

    private TournamentRepository.AddTournamentCallback mAddTournamentCallback =
            new TournamentRepository.AddTournamentCallback() {
        @Override
        public void onTournamentAdded() {
            mPostExecutionThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onTournamentAdded();
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
