package com.zenika.community.tictactoe.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameTest {

    @Test
    void aGameHas9FieldsInA3By3Grid() {
        Game game = new Game();

        assertThat(game.grid()).isEqualTo(new String[][]{
                {"", "", ""},
                {"", "", ""},
                {"", "", ""},
        });
    }

    @Test
    void playersTakeTurnTakingFields() {
        Game game = new Game();

        game.takeField(0, 0);
        game.takeField(0, 1);

        assertThat(game.grid()).isEqualTo(new String[][]{
                {"X", "", ""},
                {"O", "", ""},
                {"", "", ""},
        });
    }

//    @Test
//    void nextPlayerCannotTakeAFieldAlreadyTaken() {
//        Game game = new Game();
//        game.takeField(0, 0);
//
//        game.takeField(0, 0);
//
//        assertThatThrownBy()
//    }
}
