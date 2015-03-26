package example.example.com.sportsofficial.domain.interactors;

import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.presentation.models.League;

public interface UpdateLeagueInteractor extends Interactor {
    public interface Callback {
        public void onLeagueUpdated();
        public void onError(ErrorBundle errorBundle);
    }

    public void execute(League league, Callback callback);
}
