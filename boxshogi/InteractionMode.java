package boxshogi;

import java.util.List;

final public class InteractionMode implements GameListener {
    @Override
    public void nextTurn(String player) {
        System.out.print(player + "> ");
    }

    @Override
    public void boardUpdate(String[][] board){
        System.out.println(Board.stringifyBoard(board));
    }
    @Override
    public void moveMade(String player, String from,  String to, boolean promote){
        if(from != null){
            System.out.println(player + " player action: move " + from + " " + to);
            if(promote) {
                System.out.println(" promote");
            } else{
                System.out.println();
            }
        }
    }
    @Override
    public void capturedPieces(List<String> upper, List<String> lower){
        System.out.print("Captures UPPER: ");
        StringBuilder pieces = new StringBuilder();
        for(String s : upper){
            pieces.append(" " + s);
        }
        String lowerCaptured = pieces.toString().trim();
        System.out.println(lowerCaptured);

        System.out.print("Captures lower: ");
        pieces = new StringBuilder();
        for(String s : lower){
            pieces.append(" " + s);
        }
        String upperCaptured = pieces.toString().trim();
        System.out.println(upperCaptured);
        System.out.println();
    }
    @Override
    public void dropMade(String player, String piece, String address){
        System.out.println(player + " player action: drop " + piece + " " + address);
    }

    @Override
    public void invalidMove(String winningPlayer) {
        System.out.println(winningPlayer + " player wins. Illegal move");
    }

    @Override
    public void check(String sadPerson, List<String> strategies) {
        System.out.println(sadPerson + " player is in check!");
        System.out.println("Available moves:");
        strategies.sort(String::compareTo);
        for (String s : strategies) {
            System.out.println(s);
        }
    }

    @Override
    public void checkMate(String winningPlayer) {
        System.out.println(winningPlayer + " player wins. Checkmate");
    }

    @Override
    public void tie() {
        System.out.println("Tie game. Too many moves.");
    }

}
