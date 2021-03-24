package boxshogi;

import java.util.List;

public interface GameListener {

    void nextTurn(String player);

    void boardUpdate(String[][] board);


    void moveMade(String player, String fromAddr, String toAddr, boolean promote);


    void dropMade(String player, String piece, String address);


    void invalidMove(String winner);

    void check(String sadPerson, List<String> strategies);


    void checkMate(String winner);


    void capturedPieces(List<String> upper, List<String> lower);


    void tie();

}