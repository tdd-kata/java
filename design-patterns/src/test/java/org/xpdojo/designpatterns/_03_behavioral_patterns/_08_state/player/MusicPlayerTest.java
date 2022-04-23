package org.xpdojo.designpatterns._03_behavioral_patterns._08_state.player;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MusicPlayerTest {

    @Test
    void sut_music_player() {
        MusicPlayer player = new MusicPlayer();
        assertThat(player.isPlaying()).isTrue();
        assertThat(player.getState()).isInstanceOf(ReadyState.class);

        String play = player.getState().onPlay();
        assertThat(play).isEqualTo("Playing Track 1");
        assertThat(player.getState()).isInstanceOf(PlayingState.class);

        String previous = player.getState().onPrevious();
        assertThat(previous).isEqualTo("Playing Track 12");
        assertThat(player.getState()).isInstanceOf(PlayingState.class);

        String next = player.getState().onNext();
        assertThat(next).isEqualTo("Playing Track 1");
        assertThat(player.getState()).isInstanceOf(PlayingState.class);

        String locked = player.getState().onLock();
        assertThat(locked).isEqualTo("Stop playing");
        assertThat(player.getState()).isInstanceOf(LockedState.class);
    }

}
