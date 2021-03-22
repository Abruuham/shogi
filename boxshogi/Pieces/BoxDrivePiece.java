package boxshogi.Pieces;

import boxshogi.Board;
import boxshogi.CheckMoves;
import boxshogi.Piece;
import boxshogi.Player;

// Box Drive piece (King in real shogi)
public final class BoxDrivePiece extends Piece {
    private static final char LABEL = 'D';

    /**
     * Constructor for BoxDrivePiece
     * @param owner - the owner of the piece, either player 1 or player 2
     */
    public BoxDrivePiece(Player owner){
        super(LABEL, owner);
    }

    @Override
    public boolean promote(int startRow, int endRow, Board board) {
        return false;
    }

    @Override
    protected boolean canPlayerMove(int startRow, int startCol, int endRow, int endCol, Board board) {
        return CheckMoves.checkBoxDriveMove(startRow, startCol, endRow, endCol);
    }

    @Override
    public boolean isLegalDrop(int row, int col, Board board) {
        return true;
    }
    // this piece cannot be promoted
    @Override
    public void promote() {
    }

    // this piece cannot be demoted
    @Override
    public void demote() {
    }
}
