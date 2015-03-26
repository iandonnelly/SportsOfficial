package example.example.com.sportsofficial.data.repositories;


import java.util.List;

import javax.inject.Inject;

import example.example.com.sportsofficial.data.datasources.DatabaseMatchStorage;
import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.presentation.models.Match;

public class MatchRepository {
    public interface GetMatchCallback {
        public void onMatchLoaded(Match match);
        public void onError(ErrorBundle errorBundle);
    }

    public interface GetMatchListCallback {
        public void onMatchListLoaded(List<Match> matchList);
        public void onError(ErrorBundle errorBundle);
    }

    public interface AddMatchCallback {
        public void onMatchAdded(Match match);
        public void onError(ErrorBundle errorBundle);
    }

    public interface RemoveMatchCallback {
        public void onMatchRemoved(int matchId);
        public void onError(ErrorBundle errorBundle);
    }

    public interface UpdateMatchCallback {
        public void onMatchUpdated(Match match);
        public void onError(ErrorBundle errorBundle);
    }

    private DatabaseMatchStorage mDatabaseMatchStorage;

    @Inject
    public MatchRepository(DatabaseMatchStorage databaseMatchStorage) {
        mDatabaseMatchStorage = databaseMatchStorage;
    }

    public void getMatchByID(final int matchId, GetMatchCallback callback) {
        Match match = mDatabaseMatchStorage.getMatch(matchId);

        callback.onMatchLoaded(match);
    }

    public void getMatchListBySportId(final int sportId, GetMatchListCallback callback) {
        List<Match> matchList = mDatabaseMatchStorage.getMatchList(sportId);

        callback.onMatchListLoaded(matchList);
    }

    public void getMatchListByLeagueId(final int leagueId, GetMatchListCallback callback) {
        //List<Match> matchList = mDatabaseMatchStorage.getMatchList();

        //callback.onMatchListLoaded(matchList);
    }

    public void getMatchListByTournamentID(final int tournamentId, GetMatchListCallback callback) {
        //List<Match> matchList = mDatabaseMatchStorage.getMatchList();

        //callback.onMatchListLoaded(matchList);
    }

    public void addMatch(Match match, AddMatchCallback callback) {
        mDatabaseMatchStorage.addMatch(match);
        callback.onMatchAdded(match);
    }

    public void removeMatch(final int matchId, RemoveMatchCallback callback) {
        mDatabaseMatchStorage.removeMatch(matchId);
        callback.onMatchRemoved(matchId);
    }

    public void updateMatch(Match match, UpdateMatchCallback callback) {
        mDatabaseMatchStorage.updateMatch(match);
        callback.onMatchUpdated(match);
    }
}
