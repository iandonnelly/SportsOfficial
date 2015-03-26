package example.example.com.sportsofficial.data.repositories;


import java.util.List;

import javax.inject.Inject;

import example.example.com.sportsofficial.data.datasources.DatabaseSportStorage;
import example.example.com.sportsofficial.domain.exceptions.ErrorBundle;
import example.example.com.sportsofficial.presentation.models.Sport;

public class SportRepository {
    public interface  GetSportListCallback {
        public void onSportListLoaded(List <Sport> sportList);
        public void onError(ErrorBundle errorBundle);
    }

    public interface GetSportCallback {
        public void onSportLoaded(Sport sport);
        public void onError(ErrorBundle errorBundle);
    }

    public interface AddSportCallback {
        public void onSportAdded(Sport sport);
        public void onError(ErrorBundle errorBundle);
    }

    public interface RemoveSportCallback {
        public void onSportRemoved(int sportId);
        public void onError(ErrorBundle errorBundle);
    }

    public interface UpdateSportCallback {
        public void onSportUpdated(Sport sport);
        public void onError(ErrorBundle errorBundle);
    }

    private DatabaseSportStorage mDatabaseSportStorage;

    @Inject
    public SportRepository(DatabaseSportStorage databaseSportStorage) {
        mDatabaseSportStorage = databaseSportStorage;
    }

    public void getSportList(GetSportListCallback callback) {
        List<Sport> sportList = mDatabaseSportStorage.getSportList();
        callback.onSportListLoaded(sportList);
    }

    public void getSportById(final int sportId, GetSportCallback callback) {
        Sport sport = mDatabaseSportStorage.getSport(sportId);
        callback.onSportLoaded(sport);
    }

    public void addSport(Sport sport, AddSportCallback callback) {
        mDatabaseSportStorage.addSport(sport);
        callback.onSportAdded(sport);
    }

    public void removeSport(final int sportId, RemoveSportCallback callback) {
        mDatabaseSportStorage.removeSport(sportId);
        callback.onSportRemoved(sportId);
    }

    public void updateSport(Sport sport, UpdateSportCallback callback) {
        mDatabaseSportStorage.updateSport(sport);
        callback.onSportUpdated(sport);
    }
}
