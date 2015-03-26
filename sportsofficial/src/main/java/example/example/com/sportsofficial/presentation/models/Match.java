package example.example.com.sportsofficial.presentation.models;

public class Match {
    private int mSportId;
    private int mLeagueId;
    private int mTournamentId;
    private int mMatchId;
    private String mHomeTeamName;
    private String mAwayTeamName;
    private int mHomeTeamScore;
    private int mAwayTeamScore;
    private Date mDate;
    private Time mTime;

    public Match() {
        mSportId = -1;
        mLeagueId = -1;
        mTournamentId = -1;
        mMatchId = -1;
        mHomeTeamName = null;
        mAwayTeamName = null;
        mHomeTeamScore = -1;
        mAwayTeamScore = -1;
        mDate = null;
        mTime = null;
    }

    public int getSportId() {
        return mSportId;
    }

    public int getLeagueId() {
        return mLeagueId;
    }

    public int getTournamentId() {
        return mTournamentId;
    }

    public int getMatchId() {
        return  mMatchId;
    }

    public String getHomeTeamName() {
        return mHomeTeamName;
    }

    public String getAwayTeamName() {
        return mAwayTeamName;
    }

    public int getHomeTeamScore() {
        return mHomeTeamScore;
    }

    public int getAwayTeamScore() {
        return mAwayTeamScore;
    }

    public Date getDate() {
        return mDate;
    }

    public Time getTime() {
        return mTime;
    }

    public void setSportId(int sportId) {
        mSportId = sportId;
    }

    public void setLeagueId(int leagueId) {
        mLeagueId = leagueId;
    }

    public void setTournamentId(int tournamentId) {
        mTournamentId = tournamentId;
    }

    public void setMatchId(int matchId) {
        mMatchId = matchId;
    }

    public void setHomeTeamName(String name) {
        mHomeTeamName = name;
    }

    public void setAwayTeamName(String name) {
        mAwayTeamName = name;
    }

    public void setHomeTeamScore(int score) {
        mHomeTeamScore = score;
    }

    public void setAwayTeamScore(int score) {
        mAwayTeamScore = score;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public void setTime(Time time) {
        mTime = time;
    }

    @Override
    public String toString() {
        return mHomeTeamName + " - " + mAwayTeamName + " (" + mHomeTeamScore + " - " + mAwayTeamScore + ")";
    }
}
