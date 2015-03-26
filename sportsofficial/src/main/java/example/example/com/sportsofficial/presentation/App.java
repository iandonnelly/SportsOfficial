package example.example.com.sportsofficial.presentation;

import android.app.Application;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;
import example.example.com.sportsofficial.presentation.modules.AppModule;

public class App extends Application {
    private ObjectGraph mObjectGraph;

    @Override public void onCreate() {
        super.onCreate();
        mObjectGraph = ObjectGraph.create(getModules().toArray());
        mObjectGraph.inject(this);
    }

    private List<Object> getModules() {
        return Arrays.<Object>asList(new AppModule(this));
    }

    public ObjectGraph getObjectGraph() {
        return mObjectGraph;
    }
}
