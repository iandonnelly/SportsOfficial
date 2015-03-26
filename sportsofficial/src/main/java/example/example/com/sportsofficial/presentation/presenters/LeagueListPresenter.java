package example.example.com.sportsofficial.presentation.presenters;


public interface LeagueListPresenter {
    public void setSportId(int sportId);

    public void onCreateView();
    public void onResume();
    public void onPause();

    public void onLeagueClicked(int position);
    public void onAddLeagueClicked();
    public void onAddLeagueCreateClicked(String name, String country, String city, String address);
    public void onRemoveLeagueClicked(int position);
    public void onUpdateLeagueClicked(int position);
}
