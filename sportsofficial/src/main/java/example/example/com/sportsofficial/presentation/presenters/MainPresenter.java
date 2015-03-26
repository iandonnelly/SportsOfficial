package example.example.com.sportsofficial.presentation.presenters;

import example.example.com.sportsofficial.presentation.views.Navigator;

public interface MainPresenter {
    public void onCreate();
    public void onResume();
    public void onPause();

    public void onNavigationItemClicked(Navigator.NavItemType navItem, String sportTitle);
    public void onSyncClicked();

    public void onPebbleSportClicked();
    public void onChangePebbleSportPositiveClicked(String sport);

    public void onAddMatchDialogPositiveClicked(String homeTeam, String awayTeam);
    public void onAddSportDialogPositiveClicked(String sportTitle, int singleClick, int doubleClick,
                                                int longClick);
    public void onRemoveSportDialogPositiveClicked(String sportTitle);
}
