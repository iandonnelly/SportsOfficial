package example.example.com.sportsofficial.data.repositories;


import java.util.List;

import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.presentation.models.League;

public class LeagueRepository {
    public interface GetLeagueListCallback {
        public void onLeagueListLoaded(List<League> leagueList);
        public void onError(ErrorBundle errorBundle);
    }

    public interface GetLeagueCallback {
        public void onLeagueLoaded(League league);
        public void onError(ErrorBundle errorBundle);
    }

    public interface AddLeagueCallback {
        public void onLeagueAdded();
        public void onError(ErrorBundle errorBundle);
    }

    public interface RemoveLeagueCallback {
        public void onLeagueRemoved();
        public void onError(ErrorBundle errorBundle);
    }

    public interface UpdateLeagueCallback {
        public void onLeagueUpdated();
        public void onError(ErrorBundle errorBundle);
    }

    public void getLeagueList(final int sportId, GetLeagueListCallback callback) {

    }

    public void getLeagueById(final int leagueId, GetLeagueCallback callback) {

    }

    public void addLeague(League league, AddLeagueCallback callback) {

    }

    public void removeLeague(final int leagueId, RemoveLeagueCallback callback) {

    }

    public void updateLeague(League league, UpdateLeagueCallback callback) {

    }
}
