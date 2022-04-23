package org.xpdojo.designpatterns._03_behavioral_patterns._08_state.player;

/**
 * Common interface for all states.
 */
public abstract class State {
    MusicPlayer player;

    /**
     * Context passes itself through the state constructor. This may help a
     * state to fetch some useful context data if needed.
     */
    State(MusicPlayer player) {
        this.player = player;
    }

    public abstract String onLock();

    public abstract String onPlay();

    public abstract String onNext();

    public abstract String onPrevious();
}
