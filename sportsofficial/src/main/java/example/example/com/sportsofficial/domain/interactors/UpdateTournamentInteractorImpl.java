package example.example.com.sportsofficial.domain.interactors;


import javax.inject.Inject;

import example.example.com.sportsofficial.data.repositories.TournamentRepository;
import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.domain.executor.PostExecutionThread;
import example.example.com.sportsofficial.domain.executor.ThreadExecutor;
import example.example.com.sportsofficial.presentation.models.Tournament;

public class UpdateTournamentInteractorImpl implements UpdateTournamentInteractor {
    private final TournamentRepository mTournamentRepository;
    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mPostExecutionThread;

    private Tournament mTournament;
    private Callback mCallback;

    @Inject
    public UpdateTournamentInteractorImpl(TournamentRepository tournamentRepository,
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
        mTournamentRepository.updateTournament(mTournament, mUpdateTournamentCallback);
    }

    private TournamentRepository.UpdateTournamentCallback mUpdateTournamentCallback =
            new TournamentRepository.UpdateTournamentCallback() {
        @Override
        public void onTournamentUpdated() {
            mPostExecutionThread.post(new Runnable() {
                @Override
                public void run() {
                    mCallback.onTournamentUpdated();
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
