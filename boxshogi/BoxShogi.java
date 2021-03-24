package boxshogi;

public interface BoxShogi {


    void newGame() throws Exception;

    boolean isGameOver();


    boolean move(String fromAddr, String toAddr, boolean promote);


    boolean drop(char piece, String address);


    void registerGameListener(GameListener gameListener);


    void clearGameListener();
}