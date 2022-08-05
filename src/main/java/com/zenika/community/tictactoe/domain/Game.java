package com.zenika.community.tictactoe.domain;

import java.util.Objects;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class Game {

    private FieldState[] grid;
    private FieldState nextFieldState;
    private GameState state;

    public Game() {
        grid = new FieldState[9];
        nextFieldState = FieldState.X;
    }

    public void takeField(int col, int row) throws FieldAlreadyTakenException {
        if (elementAt(row, col) != null) {
            throw new FieldAlreadyTakenException();
        }
        grid[3 * row + col] = nextFieldState;
        endTurn();
    }

    public String[][] grid() {
        String[][] result = new String[3][3];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                FieldState fieldState = elementAt(row, col);
                result[row][col] = fieldState == null ? "" : fieldState.name();
            }
        }
        return result;
    }

    private void endTurn() {
        if (traverseGrid().mapToObj(this::getElementForRow).anyMatch(isLineTakenByOnePlayer())
                || traverseGrid().mapToObj(this::getElementForColumn).anyMatch(isLineTakenByOnePlayer())) {
            state = GameState.X_WON;
        }

        if (state == null) {
            nextFieldState = nextFieldState.next();
        }
    }

    private static IntStream traverseGrid() {
        return IntStream.rangeClosed(0, 2);
    }

    private static Predicate<IntFunction<FieldState>> isLineTakenByOnePlayer() {
        return elementResolver -> traverseGrid()
                .mapToObj(elementResolver)
                .filter(Objects::nonNull)
                .count() == 3;
    }

    private IntFunction<FieldState> getElementForColumn(final int column) {
        return row -> elementAt(row, column);
    }

    private IntFunction<FieldState> getElementForRow(final int row) {
        return col -> elementAt(row, col);
    }

    private FieldState elementAt(final int row, final int col) {
        return grid[3 * row + col];
    }

    public GameState state() {
        return state;
    }

    private enum FieldState {
        X, O;

        private FieldState next() {
            return this == FieldState.X
                    ? FieldState.O
                    : FieldState.X;
        }
    }
}
