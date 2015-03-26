package example.example.com.sportsofficial.presentation.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import example.example.com.sportsofficial.domain.interactors.GetMatchListInteractor;
import example.example.com.sportsofficial.domain.interactors.RemoveMatchInteractor;
import example.example.com.sportsofficial.presentation.models.MatchListModel;
import example.example.com.sportsofficial.presentation.presenters.MatchListPresenter;
import example.example.com.sportsofficial.presentation.presenters.MatchListPresenterImpl;
import example.example.com.sportsofficial.presentation.views.MatchListView;
import example.example.com.sportsofficial.presentation.views.fragments.MatchListFragment;

@Module(
        injects = MatchListFragment.class,
        addsTo = MainModule.class,
        complete = false
)
public class MatchListModule {
    private MatchListView mView;

    public MatchListModule(MatchListView view) {
        mView = view;
    }

    @Provides @Singleton
    public MatchListView provideView() {
        return mView;
    }

    @Provides @Singleton
    public MatchListModel provideModel() {
        return new MatchListModel();
    }

    @Provides @Singleton
    public MatchListPresenter providePresenter(MatchListModel model, MatchListView view,
                                               GetMatchListInteractor getMatchListInteractor,
                                               RemoveMatchInteractor removeMatchInteractor) {
        return new MatchListPresenterImpl(model, view,
                getMatchListInteractor,
                removeMatchInteractor);
    }
}

