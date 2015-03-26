package example.example.com.sportsofficial.domain.interactors;

import java.util.List;

import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.presentation.models.Match;

public interface GetMatchListInteractor extends Interactor {
    public interface Callback {
        public void onMatchListLoaded(List<Match> matchList);
        public void onError(ErrorBundle errorBundle);
    }

    public void executeSport(int sportId, Callback callback);
    public void executeTournament(int tournamentId, Callback callback);
    public void executeLeague(int leagueId, Callback callback);
}
