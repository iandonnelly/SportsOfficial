package example.example.com.sportsofficial.presentation.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import example.example.com.sportsofficial.domain.interactors.GetMatchInteractor;
import example.example.com.sportsofficial.domain.interactors.RemoveMatchInteractor;
import example.example.com.sportsofficial.domain.interactors.UpdateMatchInteractor;
import example.example.com.sportsofficial.presentation.models.MatchModel;
import example.example.com.sportsofficial.presentation.presenters.MatchPresenter;
import example.example.com.sportsofficial.presentation.presenters.MatchPresenterImpl;
import example.example.com.sportsofficial.presentation.views.MatchView;
import example.example.com.sportsofficial.presentation.views.fragments.MatchFragment;

@Module(
        injects = MatchFragment.class,
        addsTo = MainModule.class
)
public class MatchModule {
    private MatchView mView;

    public MatchModule(MatchView view) {
        mView = view;
    }

    @Provides
    @Singleton
    public MatchView provideView() {
        return mView;
    }

    @Provides @Singleton
    public MatchModel provideModel() {
        return new MatchModel();
    }

    @Provides @Singleton
    public MatchPresenter providePresenter(MatchModel model, MatchView view,
                                               GetMatchInteractor getMatchInteractor,
                                               RemoveMatchInteractor removeMatchInteractor,
                                               UpdateMatchInteractor editMatchInteractor) {
        return new MatchPresenterImpl(model, view,
                getMatchInteractor,
                removeMatchInteractor,
                editMatchInteractor);
    }
}