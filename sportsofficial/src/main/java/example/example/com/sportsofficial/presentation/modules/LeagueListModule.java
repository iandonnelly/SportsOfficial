package example.example.com.sportsofficial.presentation.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import example.example.com.sportsofficial.domain.interactors.AddLeagueInteractor;
import example.example.com.sportsofficial.domain.interactors.GetLeagueListInteractor;
import example.example.com.sportsofficial.domain.interactors.RemoveLeagueInteractor;
import example.example.com.sportsofficial.domain.interactors.UpdateLeagueInteractor;
import example.example.com.sportsofficial.presentation.models.LeagueListModel;
import example.example.com.sportsofficial.presentation.presenters.LeagueListPresenter;
import example.example.com.sportsofficial.presentation.presenters.LeagueListPresenterImpl;
import example.example.com.sportsofficial.presentation.views.LeagueListView;
import example.example.com.sportsofficial.presentation.views.fragments.LeagueListFragment;

@Module(
        injects = LeagueListFragment.class,
        addsTo = MainModule.class
)
public class LeagueListModule {
    private LeagueListView mView;

    public LeagueListModule(LeagueListView view) {
        mView = view;
    }

    @Provides @Singleton
    public LeagueListView provideView() {
        return mView;
    }

    @Provides @Singleton
    public LeagueListModel provideModel() {
        return new LeagueListModel();
    }

    @Provides @Singleton
    public LeagueListPresenter providePresenter(LeagueListModel model, LeagueListView view,
                                             GetLeagueListInteractor getLeaguesInteractor,
                                             AddLeagueInteractor addLeagueInteractor,
                                             RemoveLeagueInteractor removeLeagueInteractor,
                                             UpdateLeagueInteractor updateLeagueInteractor) {
        return new LeagueListPresenterImpl(model, view,
                getLeaguesInteractor,
                addLeagueInteractor,
                removeLeagueInteractor,
                updateLeagueInteractor);
    }
}
