package example.example.com.sportsofficial.presentation.views;

import java.util.List;
import java.util.Observer;

import example.example.com.sportsofficial.presentation.models.Match;

public interface MatchListView extends Observer {
    public void refreshView();
    public void showMatchDetails(int matchId);
    public void setMatchList(List<Match> matchList);
}
