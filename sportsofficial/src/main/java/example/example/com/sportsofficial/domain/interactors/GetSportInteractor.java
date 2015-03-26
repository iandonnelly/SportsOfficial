package example.example.com.sportsofficial.domain.interactors;


import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.presentation.models.Sport;

public interface GetSportInteractor extends Interactor {
    public interface Callback {
        public void onSportLoaded(Sport sport);
        public void onError(ErrorBundle errorBundle);
    }

    public void execute(int sportId, Callback callback);
}
