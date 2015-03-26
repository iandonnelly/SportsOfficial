package example.example.com.sportsofficial.presentation.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import example.example.com.sportsofficial.data.datasources.DatabaseLeagueStorage;
import example.example.com.sportsofficial.data.datasources.DatabaseMatchStorage;
import example.example.com.sportsofficial.data.datasources.DatabaseSportStorage;
import example.example.com.sportsofficial.data.datasources.DatabaseTournamentStorage;
import example.example.com.sportsofficial.presentation.App;

@Module(
        library = true,
        complete = false
)
public class LocalDatabaseModule {
    @Provides @Singleton
    public DatabaseMatchStorage provideDatabaseMatchStorage(App app) {
        return new DatabaseMatchStorage(app);
    }

    @Provides @Singleton
    public DatabaseSportStorage provideDatabaseSportStorage(App app) {
        return new DatabaseSportStorage(app);
    }

    @Provides @Singleton
    public DatabaseLeagueStorage provideDatabaseLeagueStorage(App app) {
        return new DatabaseLeagueStorage(app);
    }

    @Provides @Singleton
    public DatabaseTournamentStorage provideDatabaseTournamentStorage(App app) {
        return new DatabaseTournamentStorage(app);
    }
}
