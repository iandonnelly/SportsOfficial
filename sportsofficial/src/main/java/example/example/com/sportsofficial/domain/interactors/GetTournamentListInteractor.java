package example.example.com.sportsofficial.domain.interactors;

import java.util.List;

import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.presentation.models.Tournament;

public interface GetTournamentListInteractor extends Interactor {
    public interface Callback {
        public void onTournamentListLoaded(List<Tournament> tournamentList);
        public void onError(ErrorBundle errorBundle);
    }

    public void execute(int id, Callback callback);
}
