package example.example.com.sportsofficial.presentation.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import example.example.com.sportsofficial.data.datasources.DatabaseMatchStorage;
import example.example.com.sportsofficial.data.datasources.DatabaseSportStorage;
import example.example.com.sportsofficial.data.repositories.LeagueRepository;
import example.example.com.sportsofficial.data.repositories.MatchRepository;
import example.example.com.sportsofficial.data.repositories.SportRepository;
import example.example.com.sportsofficial.data.repositories.TournamentRepository;
import example.example.com.sportsofficial.domain.executor.PostExecutionThread;
import example.example.com.sportsofficial.domain.executor.ThreadExecutor;
import example.example.com.sportsofficial.presentation.App;
import example.example.com.sportsofficial.presentation.thread.JobExecutor;
import example.example.com.sportsofficial.presentation.thread.UIThread;

@Module(
        injects = App.class,
        includes = {
                LocalDatabaseModule.class,
                InteractorsModule.class
        }
)
public class AppModule {
    private App mApp;

    public AppModule(App app) {
        mApp = app;
    }

    @Provides @Singleton
    public App provideApplication() {
        return mApp;
    }

    @Provides @Singleton
    public ThreadExecutor provideThreadExecutor() {
        return new JobExecutor();
    }

    @Provides @Singleton
    public PostExecutionThread providePostExecutionThread() {
        return new UIThread();
    }

    @Provides @Singleton
    public MatchRepository provideMatchRepository(DatabaseMatchStorage databaseMatchStorage) {
        return new MatchRepository(databaseMatchStorage);
    }

    @Provides @Singleton
    public  LeagueRepository provideLeagueRepository() {
        return new LeagueRepository();
    }

    @Provides @Singleton
    public TournamentRepository provideTournamentRepository() {
        return new TournamentRepository();
    }

    @Provides @Singleton
    public SportRepository provideSportRepository(DatabaseSportStorage databaseSportStorage) {
        return new SportRepository(databaseSportStorage);
    }
}
