package example.example.com.sportsofficial.presentation.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class LeagueListModel extends Observable {
    private int mSportId;
    private List<League> mLeagueList;

    public LeagueListModel() {
        mSportId = -1;
        mLeagueList = new ArrayList<>();
    }

    public int getSportId() {
        return mSportId;
    }

    public List<League> getLeagueList() {
        return mLeagueList;
    }

    public void setSport(int sportId) {
        mSportId = sportId;
    }

    public void addLeague(League league) {
        mLeagueList.add(league);
        setChanged();
        notifyObservers();
    }

    public void removeLeague(int position) {
        mLeagueList.remove(position);
        setChanged();
        notifyObservers();
    }

    public void setLeagueList(List<League> leagueList) {
        mLeagueList = leagueList;
        setChanged();
        notifyObservers(leagueList);
    }
}
