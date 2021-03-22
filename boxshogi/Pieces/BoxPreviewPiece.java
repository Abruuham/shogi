package boxshogi.Pieces;

import boxshogi.Board;
import boxshogi.CheckMoves;
import boxshogi.Piece;
import boxshogi.Player;

public class BoxPreviewPiece extends Piece {
    private static final char LABEL = 'P';


    /**
     * Constructor for BoxPreviewPice
     * @param owner - the owner of the piece, either player 1 or player 2
     */
    public BoxPreviewPiece(Player owner){
        super(LABEL, owner);
    }

    @Override
    protected boolean canPlayerMove(int startRow, int startCol, int endRow, int endCol, Board board) {
        return CheckMoves.checkBoxPreviewPiece(startRow,startCol,endRow,endCol);
    }

    @Override
    public boolean isLegalDrop(int row, int col, Board board) {
        Player owner = getOwner();
        for (int i = 0; i < board.getBoardSize(); i++) {
            Piece p = board.getPiece(i, col);
            if (p != null && p.getOwner() == owner && (p.getLabel() == 'p' || p.getLabel() == 'P')) {
                return false;
            }
        }
        //Cannot be dropped into the promotion zone
        if (board.getPromoteRow(owner.getPosition()) == row) return false;
        //Cannot lead to an immediate checkMate
        int index = owner.getCapturedPieceIndex(getLabel());
        Piece p = owner.getPiece(getLabel());
        board.placePiece(this, row, col);
        boolean checkMate = board.isCheckMate(owner);
        board.removePiece(row, col);
        if (p != null) owner.addCapturedPieceToIndex(p, index);
        return !checkMate;
    }

    @Override
    public void promote() {
        isPromoted = true;
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
    public void demote() {
        isPromoted = false;
    }
}
