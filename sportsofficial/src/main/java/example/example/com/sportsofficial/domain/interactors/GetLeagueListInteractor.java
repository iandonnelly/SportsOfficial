package example.example.com.sportsofficial.domain.interactors;

import java.util.List;

import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.presentation.models.League;

public interface GetLeagueListInteractor extends Interactor {
    public interface Callback {
        public void onLeagueListLoaded(List<League> leagueList);
        public void onError(ErrorBundle errorBundle);
    }

    public void execute(int sportId, Callback callback);
}
