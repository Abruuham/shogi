package boxshogi;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.time.Period;
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


    public BoxShogiGame(GameListener gl) throws FileNotFoundException, IllegalArgumentException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException {
        gameListeners = new ArrayList<>();
        gameListeners.add(gl);
        newGame();
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
            Piece piece = (Piece)Class.forName("boxshogi.Pieces." +line[0]).getDeclaredConstructor(boxshogi.Player.class).newInstance(owner);
            board.placePiece(piece, address);
        }
        scanner.close();
        broadcastGameState();
        nextTurn();
    }

    public boolean move(String from, String to, boolean promote){
        if (gameOver) return false;
        boolean legalMove = board.makeMove(from, to, promote, currentPlayer);
        List<String> strategies = new LinkedList<>();
        Player opponent = getOpponent();
        boolean isCheck = false;
        if (legalMove) {
            if (board.isCheck(currentPlayer)) {
                isCheck = true;
                strategies = board.unCheckStrategies(opponent);
                if (strategies.size() == 0) {
                    gameOver = true;
                }
            }
        }
        else {
            gameOver = true;
        }

        //Inform the observer
        for (GameListener gl : gameListeners) {
            gl.moveMade(currentPlayer.toString(), from, to, promote);
        }
        broadcastGameState();
        boardcastMoveResult(legalMove, isCheck, strategies);

        //Move on
        nextTurn();
        return legalMove;
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

    private void boardcastMoveResult(boolean isLegal, boolean isCheck, List<String> strategies) {
        Player opponent = getOpponent();
        if (isLegal) {
            if (isCheck && !gameOver) {
                for (GameListener gl : gameListeners) {
                    gl.check(opponent.toString(), strategies);
                }
            }
            else if (isCheck && gameOver) {
                for (GameListener gl : gameListeners) {
                    gl.checkMate(currentPlayer.toString());
                }
            }
        }
        else {
            for (GameListener gl : gameListeners) {
                gl.invalidMove(getOpponent().toString());
            }
        }
    }

    public boolean isGameOver(){
        return gameOver;
    }

    private Player getOpponent() {
        return playerList.peek();
    }
}
