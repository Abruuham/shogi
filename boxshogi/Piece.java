package boxshogi;

import boxshogi.Pieces.*;

import java.util.HashSet;
import java.util.Set;

public abstract class Piece {

    private Player owner;
    private char label;
    protected boolean isPromoted = false;
    protected Position position;


    public Piece(char label, Player owner) {
        this.label = owner.getLabel(label);
        this.owner = owner;
        this.position = owner.getPosition();
        isPromoted = false;
    }


    public char getLabel(){
        return label;
    }

    public Player getOwner(){
        return owner;
    }




    public abstract boolean isLegalDrop(int row, int col, Board board);


    public final void capture(Player p) {
        owner = p;
        label = p.getLabel(label);
        position = owner.getPosition();
        demote();
    }

    protected boolean canPromote(int startRow, int endRow, Board board) {
        if (isPromoted) return false;
        return (startRow == board.getPromoteRow(position) |
                endRow == board.getPromoteRow(position));
    }

    public abstract boolean promote(int startRow, int endRow, Board board);

    public Set<PieceMove> getAllValidMoves(int startRow, int startCol, Board board) {
        Set<PieceMove> rtn = new HashSet<>();
        for (int row = 0; row < board.getBoardSize(); row++) {
            for (int col = 0; col < board.getBoardSize(); col++) {
                if (isValidMove(startRow, startCol, row, col, board)) {
                    int deltaRow = row - startRow;
                    int deltaCol = col - startCol;
                    rtn.add(new PieceMove(deltaRow, deltaCol));
                }
            }
        }
        return rtn;
    }

    protected abstract boolean canPlayerMove(int startRow, int startCol, int endRow, int endCol, Board board);

    public void promote(){
    }

    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Board board) {
        if (startRow == endRow && startCol == endCol) return false;
        Piece p = board.getPiece(endRow, endCol);
        if (p != null && p.getOwner() == owner) return false;
        return canPlayerMove(startRow, startCol, endRow, endCol, board);
    }

    public void demote(){
    }

    public String toString() {
        if(isPromoted){
            return "+" + label;
        }
        return String.valueOf(label);
    }




    public static Piece produce(String symbol, Player upper, Player lower) {
        boolean promoted = false;
        if (symbol.length() == 2) {
            promoted = true;
            symbol = symbol.substring(1);
        }
        char charSymbol = symbol.charAt(0);
        Player owner;
        if (Character.isUpperCase(charSymbol)) {
            owner = upper;
        }
        else {
            owner = lower;
        }
        Piece p;
        switch (Character.toLowerCase(charSymbol)) {
            case 'g' :
                p = new BoxGovernancePiece(owner);
                break;
            case 'd' :
                p = new BoxDrivePiece(owner);
                break;
            case 'p' :
                p = new BoxPreviewPiece(owner);
                break;
            case 'n' :
                p = new BoxNotesPiece(owner);
                break;
            case 'r' :
                p = new BoxRelayPiece(owner);
                break;
            default :
                p = new BoxShieldPiece(owner);
        }
        if (promoted) {
            p.promote();
        }
        return p;
    }
}
