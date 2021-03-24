package boxshogi;

import java.util.List;


import static boxshogi.Board.stringifyBoard;


final class FileMode implements GameListener {
    private final int totalMoves;
    private int currentMove = 0;
    private final BoxShogi game;

    FileMode(BoxShogi game, int totalMoves) {
        this.totalMoves = totalMoves;
        this.game = game;
    }


    @Override
    public void nextTurn(String player) {
        currentMove++;
        if (currentMove == totalMoves && !game.isGameOver()) {
            System.out.println(player + "> ");
        }
    }



    @Override
    public void boardUpdate(String[][] board) {
        if (currentMove == totalMoves - 1 || game.isGameOver()) {
            System.out.println(stringifyBoard(board));
        }
    }

    @Override
    public void moveMade(String player, String fromAddr, String toAddr, boolean promote) {
        if (currentMove == totalMoves - 1 || game.isGameOver()) {
            System.out.print(player + " player action: move " + fromAddr + " " + toAddr);
            if (promote) {
                System.out.println(" promote");
            }
            else {
                System.out.println();
            }
        }
    }

    @Override
    public void capturedPieces(List<String> upper, List<String> lower) {
        if (currentMove == totalMoves - 1 || game.isGameOver()) {
            System.out.print("Captures UPPER: ");
            StringBuilder pieces = new StringBuilder();
            for (String s : upper) {
                pieces.append(" " + s);
            }
            System.out.println(pieces.toString().trim());
            System.out.print("Captures lower: ");
            pieces = new StringBuilder();
            for (String s : lower) {
                pieces.append(" " + s);
            }
            System.out.println(pieces.toString().trim());
            System.out.println();
        }
    }

    @Override
    public void dropMade(String player, String piece, String address) {
        if (currentMove == totalMoves - 1 || game.isGameOver()) {
            System.out.println(player + " player action: drop " + piece + " " + address);
        }
    }

    @Override
    public void invalidMove(String winner) {
        System.out.println(winner + " player wins.  Illegal move.");
    }

    @Override
    public void check(String sadPerson, List<String> strategies) {
        if (currentMove == totalMoves - 1 || game.isGameOver()) {
            System.out.println(sadPerson + " player is in check!");
            System.out.println("Available moves:");
            strategies.sort(String::compareToIgnoreCase);
            for (String s : strategies) {
                System.out.println(s);
            }
        }
    }


    @Override
    public void checkMate(String winner) {
        System.out.println(winner + " player wins.  Checkmate.");
    }


    @Override
    public void tie() {
        System.out.println("Tie game.  Too many moves.");
    }
}
