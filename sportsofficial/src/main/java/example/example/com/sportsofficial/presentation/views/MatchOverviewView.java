package example.example.com.sportsofficial.presentation.views;

public interface MatchOverviewView {
    public void setDate(String date);
    public void setHomeTeamImage();
    public void setAwayTeamImage();
    public void setScores(int homeScore, int awayScore);
    public void setHomeTeamName(String name);
    public void setAwayTeamName(String name);
}
