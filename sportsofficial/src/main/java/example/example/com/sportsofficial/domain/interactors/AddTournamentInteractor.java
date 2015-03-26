package example.example.com.sportsofficial.domain.interactors;

import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.presentation.models.Tournament;

public interface AddTournamentInteractor extends Interactor {
    public interface Callback {
        public void onTournamentAdded();
        public void onError(ErrorBundle errorBundle);
    }

    public void execute(Tournament tournament, Callback callback);
}
