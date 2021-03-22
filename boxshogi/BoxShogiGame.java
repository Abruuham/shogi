package boxshogi;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class BoxShogiGame {


    private Board board;
    private int turn;
    private boolean gameOver = true;
    private Player currentPlayer;
    private Player topPlayer;
    private Player bottomPlayer;
    private Queue<Player> playerList;
    private List<GameListener> gameListeners;

    public BoxShogiGame(GameListener gl) throws FileNotFoundException, IllegalArgumentException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException {
        gameListeners = new ArrayList<>();
        gameListeners.add(gl);
        newGame();
    }

    private void nextTurn(){
        turn++;
        if (turn > 400 && !gameOver){
            gameOver = true;
            for(GameListener g1 : gameListeners){
                g1.tie();
            }
        }
        if(gameOver) return;
        currentPlayer = playerList.poll();
        playerList.add(currentPlayer);
        for(GameListener g1: gameListeners){
            g1.nextTurn(currentPlayer.toString());
        }
    }

    public void newGame() throws FileNotFoundException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        turn = 0;
        gameOver = false;

        playerList = new LinkedList<>();
        topPlayer = new Player(true);
        bottomPlayer = new Player(false);

        playerList.add(bottomPlayer);
        playerList.add(topPlayer);

        board = new Board();
        Scanner scanner = new Scanner(new File("boxshogi/newgame.init"));
        while(scanner.hasNextLine()) {
            String str = scanner.nextLine();
            String[] line = str.split("\\s+");
            boolean isTopPlayer = line[1].equals("TOP");
            String address = line[2];
            Player owner = bottomPlayer;
            if(isTopPlayer) {
                owner = topPlayer;
            }
            Piece piece = (Piece)Class.forName("boxshogi." +line[0]).getDeclaredConstructor(boxshogi.Player.class).newInstance(owner);
            board.placePiece(piece, address);
        }
        scanner.close();
        broadcastGameState();
        nextTurn();
    }

    public boolean move(String from, String to, boolean promote){
        return true;
    }

    public boolean drop(char piece, String address){
        return true;
    }
    private void broadcastGameState(){
        for(GameListener g1 : gameListeners){
            g1.boardUpdate(board.getCurrentBoard());
            List<String> top = topPlayer.getCapturedSnapshot();
            List<String> bottom = bottomPlayer.getCapturedSnapshot();
            g1.capturedPieces(top, bottom);
        }
    }

    public boolean isGameOver(){
        return gameOver;
    }
}
