package boxshogi.Pieces;

import boxshogi.Board;
import boxshogi.CheckMoves;
import boxshogi.Piece;
import boxshogi.Player;

public class BoxNotesPiece extends Piece {
    private static final char LABEL = 'N';

    /**
     * Constructor for BoxNotesPiece
     * @param owner - the owner of the piece, either player 1 or player 2
     */
    public BoxNotesPiece(Player owner){
        super(LABEL, owner);
    }

    @Override
    protected boolean canPlayerMove(int startRow, int startCol, int endRow, int endCol, Board board) {
        if(isPromoted) {
            return CheckMoves.checkBoxNotesPiece(startRow, startCol, endRow, endCol, board) ||
                    CheckMoves.checkBoxDriveMove(startRow, startCol, endRow, endCol);
        }
        return CheckMoves.checkBoxNotesPiece(startRow, startCol, endRow, endCol, board);
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
    public boolean isLegalDrop(int row, int col, Board board) {
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
}
