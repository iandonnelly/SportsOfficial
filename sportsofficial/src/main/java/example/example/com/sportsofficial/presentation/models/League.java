package example.example.com.sportsofficial.presentation.models;

import java.util.ArrayList;
import java.util.List;

public class League {
    private int mSportId;
    private int mLeagueId;
    private String mName;
    private Location mLocation;
    private List<Match> mMatchList;

    public League() {
        mMatchList = new ArrayList<>();
    }

    public int getSportId() {
        return mSportId;
    }

    public int getLeagueId() {
        return mLeagueId;
    }

    public String getName() {
        return mName;
    }

    public Location getLocation() {
        return mLocation;
    }

    public List<Match> getMatchList() {
        return mMatchList;
    }

    public void setSport(int sportId) {
        mSportId = sportId;
    }

    public void setLeagueId(int leagueId) {
        mLeagueId = leagueId;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setLocation(Location location) {
        mLocation = location;
    }

    public void setMatchList(List<Match> matchList) {
        mMatchList = matchList;
    }
}
