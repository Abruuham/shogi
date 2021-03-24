package boxshogi;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.time.Period;
import java.util.*;

public class BoxShogiGame implements BoxShogi {


    private Board board;
    private int turn;
    private int bottomTurn;
    private boolean gameOver = true;
    private Player currentPlayer;
    private Player topPlayer;
    private Player bottomPlayer;
    private Queue<Player> playerList;
    private List<GameListener> gameListeners;

    private void nextTurn(){

        if(currentPlayer == topPlayer){
            turn++;
        }else {
            bottomTurn++;
        }
        if ((turn >= 200 && bottomTurn >= 200) && !gameOver){
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


    private void placePiece(String symbol, String address, Board board, Player upper, Player lower) {
        Piece p = Piece.produce(symbol, upper, lower);
        board.placePiece(p, address);
    }

    public BoxShogiGame(Utils.TestCase tc) {
        turn = 0;
        bottomTurn = 0;
        gameOver = false;
        gameListeners = new ArrayList<>();

        //Initialize Players and the queue
        playerList = new LinkedList<>();
        topPlayer = new Player(true);
        bottomPlayer = new Player(false);
        playerList.add(bottomPlayer);
        playerList.add(topPlayer);

        //The board
        board = new Board();
        List<Utils.InitialPosition> ips = tc.initialPieces;
        for (Utils.InitialPosition ip : ips) {
            String symbol = ip.piece;
            String address = ip.position;
            placePiece(symbol, address, board, topPlayer, bottomPlayer);
        }

        //The captured Pieces
        List<String> upperCaptures = tc.upperCaptures;
        for (String symbol : upperCaptures) {
            if (symbol.length() < 1) continue;
            Piece p = Piece.produce(symbol, topPlayer, bottomPlayer);
            topPlayer.addCapturedPiece(p);
        }
        List<String> lowerCaptures = tc.lowerCaptures;
        for (String symbol : lowerCaptures) {
            if (symbol.length() < 1) continue;
            Piece p = Piece.produce(symbol, topPlayer, bottomPlayer);
            bottomPlayer.addCapturedPiece(p);
        }
        nextTurn();
    }



    public void newGame() throws FileNotFoundException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        turn = 0;
        bottomTurn = 0;
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

    public boolean drop(char piece, String address) {
        if (gameOver) return false;
        int index = currentPlayer.getCapturedPieceIndex(piece);
        Piece p = currentPlayer.getPiece(piece);
        boolean legalDrop = true;
        if (p == null) {
            //The player does not have the piece
            gameOver = true;
            legalDrop = false;
        }
        else {
            legalDrop = board.makeDrop(p, address, currentPlayer);
        }
        if (!legalDrop && p != null) {
            currentPlayer.addCapturedPieceToIndex(p, index);
        }

        List<String> strategies = new LinkedList<>();
        Player opponent = getOpponent();
        boolean isCheck = false;
        if (legalDrop) {
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
            gl.dropMade(currentPlayer.toString(), String.valueOf(piece), address);
        }
        broadcastGameState();
        boardcastMoveResult(legalDrop, isCheck, strategies);

        //Move on
        nextTurn();
        return legalDrop;
    }

    @Override
    public void registerGameListener(GameListener gameListener) {
        gameListeners.add(gameListener);

    }

    @Override
    public void clearGameListener() {
        gameListeners = new ArrayList<>();

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
