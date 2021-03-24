package boxshogi;

import boxshogi.Pieces.BoxDrivePiece;
import boxshogi.Pieces.BoxPreviewPiece;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Class to represent Box Shogi board
 */
public class Board {

    static Piece[][] board;
    static final int BOARD_SIZE = 5;
    private static final String ADDRESS_PATTERN = "[a-e][1-5]";
    private final Map<Player, Map<String, Integer>> boxDriveLocations;



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


    public Board() {
        board = new Piece[BOARD_SIZE][BOARD_SIZE];
        boxDriveLocations = new HashMap<>();

    }

    public void placePiece(Piece piece, int row, int col){
        if (piece instanceof BoxDrivePiece) updateBoxDriveLocation(piece, row, col);
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



    private static String index2addr(int row, int col) {
        char a = (char)(((int)'a') + col);
        int b = BOARD_SIZE - row;
        return a + String.valueOf(b);
    }

    private static boolean isValidAddr(String address) {
        return address.matches(ADDRESS_PATTERN);
    }


    boolean isValidMove(int startRow, int startCol, int endRow, int endCol, boolean promote, Player currentPlayer) {
        Piece p = getPiece(startRow, startCol);
        if (p == null) return false;
        if (p.getOwner() != currentPlayer) return false;
        if (!p.isValidMove(startRow, startCol, endRow, endCol, this)) return false;
        if (promote) {
            if (!p.promote(startRow, endRow, this)) return false;
        }
        Piece pAtEndAddr = getPiece(endRow, endCol);
        if (pAtEndAddr != null && pAtEndAddr.getOwner() == currentPlayer) return false;
        return true;
    }

    public void removePiece(int row, int col) {
        board[row][col] = null;
    }



    public Map<String, Integer> getOpponentBoxDriveLocation(Player currentPlayer) {
        return boxDriveLocations.get(getOpponent(currentPlayer));
    }

    boolean makeDrop(Piece p, String address, Player currentPlayer) {
        if (!isValidAddr(address)) return false;
        int row = add2row(address);
        int col = add2col(address);
        if (getPiece(row, col) != null) return false;
        if (!p.isLegalDrop(row, col, this)) return false;
        placePiece(p, row, col);
        return true;
    }

    public boolean inBound(int row, int col) {
        if (row < 0 || row >= BOARD_SIZE) return false;
        if (col < 0 || col >= BOARD_SIZE) return false;
        return true;
    }
    boolean makeMove(String fromAddr, String toAddr, boolean promote, Player currentPlayer) {
        //Invalid Address
        if (!isValidAddr(fromAddr) || !isValidAddr(toAddr)) return false;

        int startRow = add2row(fromAddr);
        int startCol = add2col(fromAddr);
        int endRow = add2row(toAddr);
        int endCol = add2col(toAddr);

        if (!isValidMove(startRow, startCol, endRow, endCol, promote, currentPlayer)) {
            return false;
        }


        //Finish checking, make the move
        Piece p = getPiece(startRow, startCol);
        Piece pAtEndAddr = getPiece(endRow, endCol);
        removePiece(startRow, startCol);
        placePiece(p, endRow, endCol);
        if (p instanceof BoxPreviewPiece) p.promote(startRow, endRow, this);
        //Cannot move into a check position
        Player opponent = getOpponent(currentPlayer);
        if (isCheck(opponent)) {
            removePiece(endRow, endCol);
            placePiece(p, startRow, startCol);
            return false;
        }
        if (pAtEndAddr != null) {
            //Capture the piece
            pAtEndAddr.capture(currentPlayer);
            currentPlayer.addCapturedPiece(pAtEndAddr);
        }
        return true;
    }


    public boolean isCheck(Player currentPlayer) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Piece p = board[row][col];
                if (p != null && p.getOwner() == currentPlayer) {
                   //System.out.println(p.toString());
                    Map<String, Integer> DriveBoxLocation = getOpponentBoxDriveLocation(currentPlayer);
                    //System.out.println("row: " + row + " col: " + col + " king row: " + kingLoc.get("row") + " king col: " + kingLoc.get("col"));
                    if (p.isValidMove(row, col, DriveBoxLocation.get("row"), DriveBoxLocation.get("col"), this)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int getPromoteRow(Position position) {
        if (position == Position.LOWER) return 0;
        else return BOARD_SIZE - 1;
    }
    public boolean isCheckMate(Player currentPlayer) {
        if (!isCheck(currentPlayer)) return false;
        Player opponent = getOpponent(currentPlayer);
        List<String> strategies = unCheckStrategies(opponent);
        return strategies.isEmpty();
    }



    List<String> unCheckStrategies(Player currentPlayer) {
        List<String> strategies = new LinkedList<>();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Piece p = board[row][col];
                if (p != null && p.getOwner() == currentPlayer) {
                    for (PieceMove m : p.getAllValidMoves(row, col, this)) {
                        int endRow = row + m.getDeltaRow();
                        int endCol = col + m.getDeltaCol();
                        if (canUncheckMove(row, col, endRow, endCol, currentPlayer)) {
                            String from  = index2addr(row, col);
                            String to = index2addr(endRow, endCol);
                            strategies.add(stringifyMove(from, to));
                        }
                    }
                }
            }
        }
        List<Piece> capturedPieces = new ArrayList<>(currentPlayer.returnCapturedPieces());
        for (Piece p : capturedPieces) {
            for (int row = 0; row < BOARD_SIZE; row++) {
                for (int col = 0; col < BOARD_SIZE; col++) {
                    if (canUncheckDrop(p, row, col, currentPlayer)) {
                        String to = index2addr(row, col);
                        strategies.add(stringifyDrop(p, to));
                    }
                }
            }
        }
        return strategies;
    }

    private boolean canUncheckDrop(Piece p, int row, int col, Player currentPlayer) {
        if (getPiece(row, col) != null) return false;
        if (!p.isLegalDrop(row, col, this)) return false;
        Player opponent = getOpponent(currentPlayer);
        placePiece(p, row, col);
        boolean succeed = !isCheck(opponent);
        removePiece(row, col);
        return succeed;
    }

    private boolean canUncheckMove(int startRow, int startCol, int endRow, int endCol, Player currentPlayer) {
        if (!isValidMove(startRow, startCol, endRow, endCol, false, currentPlayer)) {
            return false;
        }
        Player opponent = getOpponent(currentPlayer);
        Piece p = getPiece(startRow, startCol);
        Piece pAtEndAddr = getPiece(endRow, endCol);
        if (pAtEndAddr != null) {
            removePiece(endRow, endCol);
        }
        removePiece(startRow, startCol);
        placePiece(p, endRow, endCol);
        boolean succeed = !isCheck(opponent);
        placePiece(p, startRow, startCol);
        placePiece(pAtEndAddr, endRow, endCol);
        return succeed;
    }


    public static String stringifyDrop(Piece p, String to) {
        return "drop " + Character.toLowerCase(p.getLabel()) + " " + to;
    }

    public static String stringifyMove(String from, String to) {
        return "move " + from + " " + to;
    }





    private Player getOpponent(Player currentPlayer) {
        for (Map.Entry<Player, Map<String, Integer>> e : boxDriveLocations.entrySet()) {
            if (e.getKey() != currentPlayer) return e.getKey();
        }
        return null;
    }



    private void updateBoxDriveLocation(Piece p, int row, int col) {
        Player owner = p.getOwner();
        Map<String, Integer> loc;
        if (boxDriveLocations.containsKey(owner)) {
            loc = boxDriveLocations.get(owner);
        }
        else {
            loc = new HashMap<>();
        }
        loc.put("row", row);
        loc.put("col", col);
        boxDriveLocations.put(owner, loc);
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

