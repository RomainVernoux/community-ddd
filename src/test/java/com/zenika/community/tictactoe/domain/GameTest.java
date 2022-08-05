package com.zenika.community.tictactoe.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.zenika.community.tictactoe.domain.GameState.X_WON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class GameTest {

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
    void playersTakeTurnTakingFields() throws FieldAlreadyTakenException {
        Game game = new Game();

        game.takeField(0, 0);
        game.takeField(0, 1);

        assertThat(game.grid()).isEqualTo(new String[][]{
                {"X", "", ""},
                {"O", "", ""},
                {"", "", ""},
        });
    }

    @Test
    void aPlayerCannotTakeAFieldAlreadyTaken() throws FieldAlreadyTakenException {
        Game game = new Game();
        game.takeField(0, 0);

        var thrown = catchThrowable(() -> {
            game.takeField(0, 0);
        });

        assertThat(thrown).isInstanceOf(FieldAlreadyTakenException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void aGameIsOverWhenAllFieldsInARowAreTakenByAPlayer(final int takenRow) throws FieldAlreadyTakenException {
        final var game = new Game();
        final var anotherRow = (takenRow + 1) % 3;
        game.takeField(0, takenRow);
        game.takeField(0, anotherRow);
        game.takeField(1, takenRow);
        game.takeField(1, anotherRow);

        game.takeField(2, takenRow);

        assertThat(game.state()).isEqualTo(X_WON);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void aGameIsOverWhenAllFieldsInAColumnAreTakenByAPlayer(final int takenColumn) throws FieldAlreadyTakenException {
        final var game = new Game();
        final var anotherColumn = (takenColumn + 1) % 3;
        game.takeField(takenColumn, 0);
        game.takeField(anotherColumn, 0);
        game.takeField(takenColumn, 1);
        game.takeField(anotherColumn, 1);
        game.takeField(takenColumn, 2);

        assertThat(game.state()).isEqualTo(X_WON);
    }
}
