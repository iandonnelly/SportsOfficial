package com.example.domain.interactors;

import com.example.domain.exceptions.ErrorBundle;
import com.example.domain.models.Match;

import java.util.List;

public interface GetMatchListInteractor extends Interactor {
    public interface Callback {
        public void onMatchListLoaded(List<Match> matchList);
        public void onError(ErrorBundle errorBundle);
    }

    public void execute(int sportId, Callback callback);
}