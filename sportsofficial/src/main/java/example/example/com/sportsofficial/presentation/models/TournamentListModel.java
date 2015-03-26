package example.example.com.sportsofficial.presentation.models;


import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class TournamentListModel extends Observable {
    private int mSportId;
    private List<Tournament> mTournamentList;

    public TournamentListModel() {
        mSportId = -1;
        mTournamentList = new ArrayList<>();
    }

    public int getSportId() {
        return mSportId;
    }

    public List<Tournament> getTournamentList() {
        return mTournamentList;
    }

    public void setSportId(int sportId) {
        mSportId = sportId;
    }

    public void setTournamentList(List<Tournament> tournamentList) {
        mTournamentList = tournamentList;
        setChanged();
        notifyObservers();
    }

    public void addTournament(Tournament tournament) {
        mTournamentList.add(tournament);
        setChanged();
        notifyObservers();
    }
}
