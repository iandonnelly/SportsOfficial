package example.example.com.sportsofficial.domain.interactors;


import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;

public interface RemoveLeagueInteractor extends Interactor {
    public interface Callback {
        public void onLeagueRemoved();
        public void onError(ErrorBundle errorBundle);
    }

    public void execute(int leagueId, Callback callback);
}
