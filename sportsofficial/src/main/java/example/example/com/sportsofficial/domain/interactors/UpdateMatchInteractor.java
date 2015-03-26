package example.example.com.sportsofficial.domain.interactors;


import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.presentation.models.Match;

public interface UpdateMatchInteractor extends Interactor {
    public interface Callback {
        public void onMatchUpdated();
        public void onError(ErrorBundle errorBundle);
    }

    public void execute(Match match, Callback callback);
}
