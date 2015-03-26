package example.example.com.sportsofficial.presentation.views;

import java.util.List;

import example.example.com.sportsofficial.presentation.models.Sport;

public interface MainView {
    public void setActionBarTitle(String title);

    public void navigateToHome();
    public void navigateToSportView(int sportId);
    public void navigateToSettings();
    public void navigateToAbout();
    public void navigateToHelp();

    public void refreshView();

    public void showPebbleSyncProgressDialog();
    public void closePebbleSyncProgressDialog();

    public void showCreateMatchDialog();

    public void showChangePebbleSportDialog(List<String> sports);

    public void showAddSportDialog();
    public void showRemoveSportDialog(List<String> sports);

    public void addSportNav(Sport sport);
    public void removeSportNav(Sport sport);
}
