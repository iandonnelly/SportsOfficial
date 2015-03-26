package example.example.com.sportsofficial.domain.interactors;


import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.presentation.models.Match;

public interface AddMatchInteractor extends Interactor {
    public interface Callback {
        public void onMatchAdded(Match match);
        public void onError(ErrorBundle errorBundle);
    }

    public void execute(Match match, Callback callback);
}
