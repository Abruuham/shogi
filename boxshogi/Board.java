package boxshogi;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Class to represent Box Shogi board
 */
public class Board {

    static Piece[][] board;
    static final int BOARD_SIZE = 5;


    public Board() {
        board = new Piece[BOARD_SIZE][BOARD_SIZE];

    }

    public Piece getPiece(int row, int col){
        return board[row][col];
    }

    public static int getBoardSize() {
        return BOARD_SIZE;
    }

    private static int add2row(String address){
        int i = Integer.valueOf(address.substring(1,2));
        return BOARD_SIZE - i;
    }

    private static int add2col(String address){
        char c = address.charAt(0);
        return (int)c - (int)'a';
    }

    public void placePiece(Piece piece, int row, int col){
        board[row][col] = piece;
    }

    public boolean placePiece(Piece piece, String address){
        int row = add2row(address);
        int col = add2col(address);
        placePiece(piece, row, col);
        return true;
    }



    /* Print board */
    public String toString() {
        String[][] pieces = new String[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Piece curr = (Piece) board[col][row];
                pieces[col][row] = this.isOccupied(col, row) ? board[col][row].toString() : "";
            }
        }
        return stringifyBoard(pieces);
    }

    private boolean isOccupied(int col, int row) {
        return board[col][row] != null;
    }



    public static String stringifyBoard(String[][] board) {
        String str = "";

        for (int row = board.length - 1; row >= 0; row--) {

            str += Integer.toString(row + 1) + " |";
            for (int col = 0; col < board[row].length; col++) {
                str += stringifySquare(board[col][row]);
            }
            str += System.getProperty("line.separator");
        }

        str += "    a  b  c  d  e" + System.getProperty("line.separator");

        return str;
    }

    public String[][] getCurrentBoard(){
        String[][] currentBoard = new String[BOARD_SIZE][BOARD_SIZE];
        for(int row = 0; row < BOARD_SIZE; row++){
            for(int col = 0; col < BOARD_SIZE; col++){
                Piece piece = board[BOARD_SIZE - col - 1][row];
                if(piece != null){
                    currentBoard[row][col] = piece.toString();
                }
                else{
                    currentBoard[row][col] = "";
                }
            }
        }
        return currentBoard;

    }

    public void boardUpdate(String[][] board){
        System.out.println(stringifyBoard(board));
    }


    private static String stringifySquare(String sq) {
        switch (sq.length()) {
            case 0:
                return "__|";
            case 1:
                return " " + sq + "|";
            case 2:
                return sq + "|";
        }

        throw new IllegalArgumentException("Board must be an array of strings like \"\", \"P\", or \"+P\"");
    }
}

