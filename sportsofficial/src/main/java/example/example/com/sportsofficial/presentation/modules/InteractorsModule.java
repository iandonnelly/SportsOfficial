package example.example.com.sportsofficial.presentation.modules;

import dagger.Module;
import dagger.Provides;
import example.example.com.sportsofficial.data.repositories.LeagueRepository;
import example.example.com.sportsofficial.data.repositories.MatchRepository;
import example.example.com.sportsofficial.data.repositories.SportRepository;
import example.example.com.sportsofficial.data.repositories.TournamentRepository;
import example.example.com.sportsofficial.domain.executor.PostExecutionThread;
import example.example.com.sportsofficial.domain.executor.ThreadExecutor;
import example.example.com.sportsofficial.domain.interactors.AddLeagueInteractor;
import example.example.com.sportsofficial.domain.interactors.AddLeagueInteractorImpl;
import example.example.com.sportsofficial.domain.interactors.AddMatchInteractor;
import example.example.com.sportsofficial.domain.interactors.AddMatchInteractorImpl;
import example.example.com.sportsofficial.domain.interactors.AddSportInteractor;
import example.example.com.sportsofficial.domain.interactors.AddSportInteractorImpl;
import example.example.com.sportsofficial.domain.interactors.AddTournamentInteractor;
import example.example.com.sportsofficial.domain.interactors.AddTournamentInteractorImpl;
import example.example.com.sportsofficial.domain.interactors.GetLeagueListInteractor;
import example.example.com.sportsofficial.domain.interactors.GetLeagueListInteractorImpl;
import example.example.com.sportsofficial.domain.interactors.GetMatchInteractor;
import example.example.com.sportsofficial.domain.interactors.GetMatchInteractorImpl;
import example.example.com.sportsofficial.domain.interactors.GetMatchListInteractor;
import example.example.com.sportsofficial.domain.interactors.GetMatchListInteractorImpl;
import example.example.com.sportsofficial.domain.interactors.GetSportInteractor;
import example.example.com.sportsofficial.domain.interactors.GetSportInteractorImpl;
import example.example.com.sportsofficial.domain.interactors.GetSportListInteractor;
import example.example.com.sportsofficial.domain.interactors.GetSportListInteractorImpl;
import example.example.com.sportsofficial.domain.interactors.GetTournamentListInteractor;
import example.example.com.sportsofficial.domain.interactors.GetTournamentListInteractorImpl;
import example.example.com.sportsofficial.domain.interactors.RemoveLeagueInteractor;
import example.example.com.sportsofficial.domain.interactors.RemoveLeagueInteractorImpl;
import example.example.com.sportsofficial.domain.interactors.RemoveMatchInteractor;
import example.example.com.sportsofficial.domain.interactors.RemoveMatchInteractorImpl;
import example.example.com.sportsofficial.domain.interactors.RemoveSportInteractor;
import example.example.com.sportsofficial.domain.interactors.RemoveSportInteractorImpl;
import example.example.com.sportsofficial.domain.interactors.RemoveTournamentInteractor;
import example.example.com.sportsofficial.domain.interactors.RemoveTournamentInteractorImpl;
import example.example.com.sportsofficial.domain.interactors.UpdateLeagueInteractor;
import example.example.com.sportsofficial.domain.interactors.UpdateLeagueInteractorImpl;
import example.example.com.sportsofficial.domain.interactors.UpdateMatchInteractor;
import example.example.com.sportsofficial.domain.interactors.UpdateMatchInteractorImpl;
import example.example.com.sportsofficial.domain.interactors.UpdateSportInteractor;
import example.example.com.sportsofficial.domain.interactors.UpdateSportInteractorImpl;
import example.example.com.sportsofficial.domain.interactors.UpdateTournamentInteractor;
import example.example.com.sportsofficial.domain.interactors.UpdateTournamentInteractorImpl;

@Module(
        library = true,
        complete = false
)
public class InteractorsModule {
    @Provides
    public AddLeagueInteractor provideAddLeagueInteractor(LeagueRepository leagueRepository,
                                                          ThreadExecutor threadExecutor,
                                                          PostExecutionThread postExecutionThread) {
        return new AddLeagueInteractorImpl(leagueRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    public AddMatchInteractor provideAddMatchInteractor(MatchRepository matchRepository,
                                                        ThreadExecutor threadExecutor,
                                                        PostExecutionThread postExecutionThread) {
        return new AddMatchInteractorImpl(matchRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    public AddSportInteractor provideAddSportInteractor(SportRepository sportRepository,
                                                        ThreadExecutor threadExecutor,
                                                        PostExecutionThread postExecutionThread) {
        return new AddSportInteractorImpl(sportRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    public GetSportListInteractor provideGetSportListInteractor(SportRepository sportRepository,
                                                                ThreadExecutor threadExecutor,
                                                                PostExecutionThread postExecutionThread) {
        return new GetSportListInteractorImpl(sportRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    public AddTournamentInteractor provideAddTournamentInteractor(TournamentRepository tournamentRepository,
                                                                  ThreadExecutor threadExecutor,
                                                                  PostExecutionThread postExecutionThread) {
        return new AddTournamentInteractorImpl(tournamentRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    public GetLeagueListInteractor provideGetLeagueListInteractor(LeagueRepository leagueRepository,
                                                                  ThreadExecutor threadExecutor,
                                                                  PostExecutionThread postExecutionThread) {
        return new GetLeagueListInteractorImpl(leagueRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    public GetMatchInteractor provideGetMatchInteractor(MatchRepository matchRepository,
                                                        ThreadExecutor threadExecutor,
                                                        PostExecutionThread postExecutionThread) {
        return new GetMatchInteractorImpl(matchRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    public GetMatchListInteractor provideGetMatchListInteractor(MatchRepository matchRepository,
                                                                ThreadExecutor threadExecutor,
                                                                PostExecutionThread postExecutionThread) {
        return new GetMatchListInteractorImpl(matchRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    public GetSportInteractor provideGetSportInteractor(SportRepository sportRepository,
                                                        ThreadExecutor threadExecutor,
                                                        PostExecutionThread postExecutionThread) {
        return new GetSportInteractorImpl(sportRepository, threadExecutor, postExecutionThread);
    }



    @Provides
    public GetTournamentListInteractor provideGetTournamentListInteractor(TournamentRepository tournamentRepository,
                                                                          ThreadExecutor threadExecutor,
                                                                          PostExecutionThread postExecutionThread) {
        return new GetTournamentListInteractorImpl(tournamentRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    public RemoveLeagueInteractor provideRemoveLeagueInteractor(LeagueRepository leagueRepository,
                                                                ThreadExecutor threadExecutor,
                                                                PostExecutionThread postExecutionThread) {
        return new RemoveLeagueInteractorImpl(leagueRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    public RemoveMatchInteractor provideRemoveMatchInteractor(MatchRepository matchRepository,
                                                              ThreadExecutor threadExecutor,
                                                              PostExecutionThread postExecutionThread) {
        return new RemoveMatchInteractorImpl(matchRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    public RemoveSportInteractor provideRemoveSportInteractor(SportRepository sportRepository,
                                                              ThreadExecutor threadExecutor,
                                                              PostExecutionThread postExecutionThread) {
        return new RemoveSportInteractorImpl(sportRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    public RemoveTournamentInteractor provideRemoveTournamentInteractor(TournamentRepository tournamentRepository,
                                                                        ThreadExecutor threadExecutor,
                                                                        PostExecutionThread postExecutionThread) {
        return new RemoveTournamentInteractorImpl(tournamentRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    public UpdateLeagueInteractor provideUpdateLeagueInteractor(LeagueRepository leagueRepository,
                                                                ThreadExecutor threadExecutor,
                                                                PostExecutionThread postExecutionThread) {
        return new UpdateLeagueInteractorImpl(leagueRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    public UpdateMatchInteractor provideUpdateMatchInteractor(MatchRepository matchRepository,
                                                              ThreadExecutor threadExecutor,
                                                              PostExecutionThread postExecutionThread) {
        return new UpdateMatchInteractorImpl(matchRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    public UpdateSportInteractor provideUpdateSportInteractor(SportRepository sportRepository,
                                                              ThreadExecutor threadExecutor,
                                                              PostExecutionThread postExecutionThread) {
        return new UpdateSportInteractorImpl(sportRepository, threadExecutor, postExecutionThread);
    }

    @Provides
    public UpdateTournamentInteractor provideUpdateTournamentInteractor(TournamentRepository tournamentRepository,
                                                                        ThreadExecutor threadExecutor,
                                                                        PostExecutionThread postExecutionThread) {
        return new UpdateTournamentInteractorImpl(tournamentRepository, threadExecutor, postExecutionThread);
    }
}