package boxshogi.Pieces;


import boxshogi.Board;
import boxshogi.CheckMoves;
import boxshogi.Piece;
import boxshogi.Player;

public class BoxRelayPiece extends Piece {
    private static final char LABEL = 'R';

    /**
     * Constructor for BoxRelayPiece
     * @param owner - the owner of the piece, either player 1 or player 2
     */
    public BoxRelayPiece(Player owner){
        super(LABEL, owner);
    }


    @Override
    public boolean promote(int startRow, int endRow, Board board) {
        if (!canPromote(startRow, endRow, board)) {
            return false;
        }
        promote();
        return true;
    }
    @Override
    public void promote() {
        isPromoted = true;
    }

    @Override
    public void demote() {
        isPromoted = false;
    }

    @Override
    public boolean isLegalDrop(int row, int col, Board board) {
        return true;
    }

    @Override
    protected boolean canPlayerMove(int startRow, int startCol, int endRow, int endCol, Board board) {
        if(isPromoted){
            return CheckMoves.checkBoxShieldPiece(startRow, startCol, endRow, endCol, position);
        } else {
            return CheckMoves.checkBoxRelayPiece(startRow, startCol, endRow, endCol, position);
        }
    }
}
