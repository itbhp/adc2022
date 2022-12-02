package it.twinsbrain.adc2022.day02;

import java.util.List;

public class Day02Solution {

    private static final int FIRST_PLAYER = 0;
    private static final int SECOND_PLAYER = 1;

    public static int score(List<String> input) {
        var moves = input.get(0).split(" ");
        var firstPlayerMove = adaptFirstPlayerMove(moves[FIRST_PLAYER]);
        var secondPlayerMove = adaptSecondPlayerMove(moves[SECOND_PLAYER]);

        return scoreSecondPlayer(firstPlayerMove, secondPlayerMove);
    }

    private static int scoreSecondPlayer(Move firstPlayerMove, Move secondPlayerMove) {
        return secondPlayerMove.winOver(firstPlayerMove).points() + secondPlayerMove.points();
    }

    private static Move adaptFirstPlayerMove(String value) {
        return switch (value) {
            case "A" -> new Rock();
            case "B" -> new Paper();
            default -> new Scissor(); // "C"
        };
    }

    private static Move adaptSecondPlayerMove(String value) {
        return switch (value) {
            case "X" -> new Rock();
            case "Y" -> new Paper();
            default -> new Scissor(); // "Z"
        };
    }
}

sealed abstract class Move permits Rock, Paper, Scissor {
    abstract Result winOver(Move other);

    abstract int points();
}

@SuppressWarnings("unused")
final class Rock extends Move {
    @Override
    Result winOver(Move other) {
        return switch (other) {
            case Rock r -> Result.Draw;
            case Paper p -> Result.Lost;
            case Scissor s -> Result.Won;
        };
    }

    @Override
    int points() {
        return 1;
    }
}

@SuppressWarnings("unused")
final class Paper extends Move {
    @Override
    Result winOver(Move other) {
        return switch (other) {
            case Rock r -> Result.Won;
            case Paper p -> Result.Draw;
            case Scissor s -> Result.Lost;
        };
    }

    @Override
    int points() {
        return 2;
    }
}

@SuppressWarnings("unused")
final class Scissor extends Move {
    @Override
    Result winOver(Move other) {
        return switch (other) {
            case Rock r -> Result.Lost;
            case Paper p -> Result.Won;
            case Scissor s -> Result.Draw;
        };
    }

    @Override
    int points() {
        return 3;
    }
}

enum Result {
    Won(6), Draw(3), Lost(0);
    private final int points;

    Result(int points) {

        this.points = points;
    }

    int points() {
        return points;
    }
}