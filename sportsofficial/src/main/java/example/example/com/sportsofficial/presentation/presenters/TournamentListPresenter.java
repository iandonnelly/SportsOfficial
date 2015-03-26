package example.example.com.sportsofficial.presentation.presenters;

public interface TournamentListPresenter {
    public void setSportId(int sportId);
    public void onCreateView();
    public void onResume();
    public void onPause();

    public void onAddTournamentClicked();
    public void onAddTournamentCreateClicked(String name, String venue, String country, String city,
                                             String address, int year, int month, int day);
    public void onRemoveTournamentClicked(int position);
    public void onEditTournamentClicked(int position);
}
