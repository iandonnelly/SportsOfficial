package example.example.com.sportsofficial.domain.interactors;


import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.presentation.models.Match;

public interface GetMatchInteractor extends Interactor {
    public interface Callback {
        public void onMatchLoaded(Match match);
        public void onError(ErrorBundle errorBundle);
    }

    public void execute(int matchId, Callback callback);
}
