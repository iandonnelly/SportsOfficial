package example.example.com.sportsofficial.domain.interactors;


import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;

public interface RemoveMatchInteractor extends Interactor {
    public interface Callback {
        public void onMatchRemoved(int matchId);
        public void onError(ErrorBundle errorBundle);
    }

    public void execute(int id, Callback callback);
}
