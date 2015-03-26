package example.example.com.sportsofficial.presentation.presenters;

public interface MatchListPresenter {
    public void onCreateView();
    public void onResume();
    public void onPause();

    public void onRefreshView();

    public void setSportId(final int sportId);
    public void setLeagueId(final int leagueId);
    public void setTournamentId(final int tournamentId);

    public void onMatchClicked(int position);
    public void onRemoveMatchClicked(int position);
}
