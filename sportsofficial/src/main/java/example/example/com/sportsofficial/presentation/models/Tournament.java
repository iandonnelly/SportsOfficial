package example.example.com.sportsofficial.presentation.models;

import java.util.ArrayList;
import java.util.List;

public class Tournament {
    private Sport mSport;
    private int mTournamentId;
    private String mName;
    private String mVenue;
    private Date mDate;
    private Location mLocation;
    private List<Match> mMatchList;
    // statistics

    public Tournament() {
        mMatchList = new ArrayList<>();
    }

    public Sport getSport() {
        return mSport;
    }

    public int getTournamentId() {
        return mTournamentId;
    }

    public String getName() {
        return mName;
    }

    public String getVenue() {
        return mVenue;
    }

    public Date getDate() {
        return mDate;
    }

    public Location getLocation() {
        return mLocation;
    }

    public List<Match> getMatchList() {
        return mMatchList;
    }

    public void setSport(Sport sport) {
        mSport = sport;
    }

    public void setTournamentId(int tournamentId) {
        mTournamentId = tournamentId;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setVenue(String venue) {
        mVenue = venue;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public void setLocation(Location location) {
        mLocation = location;
    }

    public void setMatchList(List<Match> matchList) {
        mMatchList = matchList;
    }
}
