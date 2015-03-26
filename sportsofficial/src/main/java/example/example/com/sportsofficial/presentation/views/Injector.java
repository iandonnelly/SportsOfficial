package example.example.com.sportsofficial.presentation.views;


import dagger.ObjectGraph;

public interface Injector {
    /**
     * Gets the object graph for this component.
     * @return the object graph
     */
    public ObjectGraph getObjectGraph();
    /**
     * Injects a target object using this component's object graph.
     * @param object the target object
     */
    public void inject(Object object);
}
