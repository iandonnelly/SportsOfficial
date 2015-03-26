package example.example.com.sportsofficial.presentation.models;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

public class MatchListModel extends Observable {
    private Sport mSport;
    private List<Match> mMatchList;

    public MatchListModel() {
        mSport = null;
        mMatchList = new ArrayList<>();
    }

    public Sport getSport() {
        return mSport;
    }

    public List<Match> getMatchList() {
        return mMatchList;
    }

    public void setSport(Sport sport) {
        mSport = sport;
    }

    public boolean hasMatch(Match matchSearch) {
        for(Match match : mMatchList) {
            if (match.getMatchId() == matchSearch.getMatchId()) {
                return true;
            }
        }
        return false;
    }

    public synchronized void addMatch(Match match) {
        mMatchList.add(match);
        setChanged();
        notifyObservers();
    }

    public synchronized void removeMatch(int matchId) {
        Iterator<Match> matchIterator = mMatchList.iterator();

        while (matchIterator.hasNext()) {
            Match match = matchIterator.next();

            if (match.getMatchId() == matchId) {
                matchIterator.remove();
            }
        }
        setChanged();
        notifyObservers();
    }
}
