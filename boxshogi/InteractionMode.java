package boxshogi;

import java.util.List;

final public class InteractionMode implements GameListener {
    @Override
    public void nextTurn(String player) {
        System.out.println(player + "> ");
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
        System.out.println("Captures UPPER: ");
        StringBuilder pieces = new StringBuilder();
        for(String s : upper){
            pieces.append(" " + s);
        }
        System.out.println(pieces.toString().trim());
        System.out.println("Captures lower: ");
        pieces = new StringBuilder();
        for(String s : lower){
            pieces.append(" " + s);
        }
        System.out.println(pieces.toString().trim());
        System.out.println();
    }
    @Override
    public void dropMade(String player, String piece, String address){
        System.out.println(player + " player action: drop " + piece + " " + address);
    }

    @Override
    public void invalidMove(String winner) {

    }

    @Override
    public void check(String sadPerson, List<String> strategies) {

    }

    @Override
    public void checkMate(String winner) {

    }

    @Override
    public void tie() {

    }

}
