package example.example.com.sportsofficial.domain.interactors;

import java.util.List;

import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.presentation.models.Sport;

public interface GetSportListInteractor extends Interactor {
    public interface Callback {
        public void onSportListLoaded(List<Sport> sportList);
        public void onError(ErrorBundle errorBundle);
    }

    public void execute(Callback callback);
}
