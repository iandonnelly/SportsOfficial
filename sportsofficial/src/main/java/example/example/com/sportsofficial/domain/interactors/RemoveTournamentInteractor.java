package example.example.com.sportsofficial.domain.interactors;

import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;

public interface RemoveTournamentInteractor extends Interactor {
    public interface Callback {
        public void onTournamentRemoved();
        public void onError(ErrorBundle errorBundle);
    }

    public void execute(int id, Callback callback);
}
