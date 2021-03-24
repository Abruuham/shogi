package boxshogi.Pieces;

import boxshogi.Board;
import boxshogi.CheckMoves;
import boxshogi.Piece;
import boxshogi.Player;

public class BoxGovernancePiece extends Piece {
    private static final char LABEL = 'G';


    /**
     * Constructor for BoxGovernancePiece
     * @param owner - the owner of the piece, either player 1 or player 2
     */
    public BoxGovernancePiece(Player owner){
        super(LABEL, owner);
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
    public boolean promote(int startRow, int endRow, Board board ) {
        if (!canPromote(startRow, endRow, board)) {
            return false;
        }
        promote();
        return true;
    }

    @Override
    protected boolean canPlayerMove(int startRow, int startCol, int endRow, int endCol, Board board) {
        if (isPromoted) {
            return (CheckMoves.checkBoxGovernancePiece(startRow, startCol, endRow, endCol, board) ||
                    CheckMoves.checkBoxDriveMove(startRow, startCol, endRow, endCol));
        } else {
            return CheckMoves.checkBoxGovernancePiece(startRow, startCol, endRow, endCol, board);
        }
    }
}
