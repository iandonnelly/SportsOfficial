package example.example.com.sportsofficial.presentation.views;


import java.util.List;
import java.util.Observer;

import example.example.com.sportsofficial.presentation.models.League;

public interface LeagueListView extends Observer {
    public void showLeagueDetails(int leagueId);
    public void showAddLeagueDialog();
    public void setLeagueList(List<League> leagueList);
}
