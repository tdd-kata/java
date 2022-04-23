package org.xpdojo.designpatterns._03_behavioral_patterns._06_memento;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GameSaveTest {

    @Test
    void sut_game_save() {
        FPS game = new FPS();
        game.setBlueTeamScore(10);
        game.setRedTeamScore(20);

        // 현재 게임 상태 저장
        game.save();

        // 상태 변경
        game.setBlueTeamScore(12);
        game.setRedTeamScore(22);

        assertThat(game.getBlueTeamScore()).isEqualTo(12);
        assertThat(game.getRedTeamScore()).isEqualTo(22);

        // 저장된 게임 불러오기
        game.restore();

        assertThat(game.getBlueTeamScore()).isEqualTo(10);
        assertThat(game.getRedTeamScore()).isEqualTo(20);
    }

}
