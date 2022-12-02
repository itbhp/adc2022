package it.twinsbrain.adc2022.day02;

import java.net.URISyntaxException;
import java.util.List;

import static it.twinsbrain.adc2022.FilesModule.read;
import static it.twinsbrain.adc2022.FilesModule.resource;

public class Day02Solution {

    public static void main(String[] args) throws URISyntaxException {
        var input = read(resource("/day02/input02.txt"));
        System.out.printf("Score part 1: %d", score(input));
        System.out.println();
        System.out.printf("Score part 1: %d", scoreWithExpectedOutcome(input));
    }

    private static final int FIRST_PLAYER = 0;
    private static final int SECOND_PLAYER = 1;

    public static int score(List<String> input) {
        return input.stream()
                .map(l -> l.split(" "))
                .map(Day02Solution::roundScore)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public static int scoreWithExpectedOutcome(List<String> input) {
        return input.stream()
                .map(l -> l.split(" "))
                .map(Day02Solution::fromExpectedResultToMove)
                .map(Day02Solution::roundScore)
                .mapToInt(Integer::intValue)
                .sum();
    }

    @SuppressWarnings("unused")
    private static String[] fromExpectedResultToMove(String[] input) {
        var firstPlayerMove = adaptFirstPlayerMove(input[FIRST_PLAYER]);
        var expectedResult = input[SECOND_PLAYER];
        var result = switch (expectedResult) {
            case "X" -> Result.Lost;
            case "Y" -> Result.Draw;
            default -> Result.Won;
        };
        var secondPlayerMove = switch (firstPlayerMove.opponentMoveGiven(result)) {
            case Rock r -> "X";
            case Paper p -> "Y";
            case Scissor s -> "Z";
        };

        return new String[]{input[FIRST_PLAYER], secondPlayerMove};
    }

    private static int roundScore(String[] moves) {
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

sealed interface Move {
    Result winOver(Move other);

    Move opponentMoveGiven(Result opponentResult);

    int points();
}

@SuppressWarnings("unused")
final class Rock implements Move {
    @Override
    public Result winOver(Move other) {
        return switch (other) {
            case Rock r -> Result.Draw;
            case Paper p -> Result.Lost;
            case Scissor s -> Result.Won;
        };
    }

    @Override
    public Move opponentMoveGiven(Result opponentResult) {
        return switch (opponentResult) {
            case Won -> new Paper();
            case Draw -> new Rock();
            case Lost -> new Scissor();
        };
    }

    @Override
    public int points() {
        return 1;
    }
}

@SuppressWarnings("unused")
final class Paper implements Move {
    @Override
    public Result winOver(Move other) {
        return switch (other) {
            case Rock r -> Result.Won;
            case Paper p -> Result.Draw;
            case Scissor s -> Result.Lost;
        };
    }

    @Override
    public Move opponentMoveGiven(Result opponentResult) {
        return switch (opponentResult) {
            case Won -> new Scissor();
            case Draw -> new Paper();
            case Lost -> new Rock();
        };
    }

    @Override
    public int points() {
        return 2;
    }
}

@SuppressWarnings("unused")
final class Scissor implements Move {
    @Override
    public Result winOver(Move other) {
        return switch (other) {
            case Rock r -> Result.Lost;
            case Paper p -> Result.Won;
            case Scissor s -> Result.Draw;
        };
    }

    @Override
    public Move opponentMoveGiven(Result opponentResult) {
        return switch (opponentResult) {
            case Won -> new Rock();
            case Draw -> new Scissor();
            case Lost -> new Paper();
        };
    }

    @Override
    public int points() {
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