package example.example.com.sportsofficial.presentation.models;

public class MatchModel {
    private Sport mSport;
    private Match mMatch;

    public MatchModel() {
        mSport = null;
        mMatch = null;
    }

    public Sport getSport() {
        return mSport;
    }

    public Match getMatch() {
        return mMatch;
    }

    public void setSport(Sport sport) {
        mSport = sport;
    }

    public void setMatch(Match match) {
        mMatch = match;
    }
}
