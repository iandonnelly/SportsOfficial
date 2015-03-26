package example.example.com.sportsofficial.presentation.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import example.example.com.sportsofficial.domain.interactors.AddTournamentInteractor;
import example.example.com.sportsofficial.domain.interactors.GetTournamentListInteractor;
import example.example.com.sportsofficial.domain.interactors.RemoveTournamentInteractor;
import example.example.com.sportsofficial.domain.interactors.UpdateTournamentInteractor;
import example.example.com.sportsofficial.presentation.models.TournamentListModel;
import example.example.com.sportsofficial.presentation.presenters.TournamentListPresenter;
import example.example.com.sportsofficial.presentation.presenters.TournamentListPresenterImpl;
import example.example.com.sportsofficial.presentation.views.TournamentListView;
import example.example.com.sportsofficial.presentation.views.fragments.TournamentListFragment;

@Module(
        injects = TournamentListFragment.class,
        addsTo = MainModule.class
)
public class TournamentListModule {
    private TournamentListView mView;

    public TournamentListModule(TournamentListView view) {
        mView = view;
    }

    @Provides @Singleton
    public TournamentListView provideView() {
        return mView;
    }

    @Provides @Singleton
    public TournamentListModel provideModel() {
        return new TournamentListModel();
    }

    @Provides @Singleton
    public TournamentListPresenter providePresenter(TournamentListModel model, TournamentListView view,
                                                 GetTournamentListInteractor getTournamentListInteractor,
                                                 AddTournamentInteractor addTournamentInteractor,
                                                 RemoveTournamentInteractor removeTournamentInteractor,
                                                 UpdateTournamentInteractor editTournamentInteractor) {
        return new TournamentListPresenterImpl(model, view,
                getTournamentListInteractor,
                addTournamentInteractor,
                removeTournamentInteractor,
                editTournamentInteractor);
    }
}
