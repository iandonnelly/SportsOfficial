package example.example.com.sportsofficial.data.repositories;


import java.util.List;

import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.presentation.models.Tournament;

public class TournamentRepository {
    public interface GetTournamentListCallback {
        public void onTournamentListLoaded(List<Tournament> tournamentList);
        public void onError(ErrorBundle errorBundle);
    }

    public interface GetTournamentCallback {
        public void onTournamentLoaded(Tournament league);
        public void onError(ErrorBundle errorBundle);
    }

    public interface AddTournamentCallback {
        public void onTournamentAdded();
        public void onError(ErrorBundle errorBundle);
    }

    public interface RemoveTournamentCallback {
        public void onTournamentRemoved();
        public void onError(ErrorBundle errorBundle);
    }

    public interface UpdateTournamentCallback {
        public void onTournamentUpdated();
        public void onError(ErrorBundle errorBundle);
    }

    public void getTournamentList(final int sportId, GetTournamentListCallback callback) {

    }

    public void getTournamentById(final int tournamentId, GetTournamentCallback callback) {

    }

    public void addTournament(Tournament tournament, AddTournamentCallback callback) {

    }

    public void removeTournament(final int tournamentId, RemoveTournamentCallback callback) {

    }

    public void updateTournament(Tournament tournament, UpdateTournamentCallback callback) {

    }
}
