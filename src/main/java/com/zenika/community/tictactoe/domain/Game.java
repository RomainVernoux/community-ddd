package com.zenika.community.tictactoe.domain;

public class Game {

    private FieldState[] grid;
    private FieldState nextFieldState;

    public Game() {
        grid = new FieldState[9];
        nextFieldState = FieldState.X;
    }

    public void takeField(int col, int row) {
        grid[3 * row + col] = nextFieldState;
        nextTurn();
    }

    public String[][] grid() {
        String[][] result = new String[3][3];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                FieldState fieldState = grid[3 * row + col];
                result[row][col] = fieldState == null ? "" : fieldState.representation;
            }
        }
        return result;
    }

    private void nextTurn() {
        nextFieldState = nextFieldState.next();
    }

    private static enum FieldState {
        X("X"),
        O("O");

        private String representation;

        FieldState(String representation) {
            this.representation = representation;
        }

        private FieldState next() {
            if (this == FieldState.X) {
                return FieldState.O;
            } else {
                return FieldState.X;
            }
        }
    }
}
