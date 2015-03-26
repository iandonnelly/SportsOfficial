package example.example.com.sportsofficial.presentation.views;

import java.util.List;
import java.util.Observer;

import example.example.com.sportsofficial.presentation.models.Tournament;

public interface TournamentListView extends Observer {
    public void showTournamentDetails(int tournamentId);
    public void showAddTournamentDialog();
    public void setTournamentsList(List<Tournament> tournamentsList);
}
