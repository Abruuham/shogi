package boxshogi.Pieces;


import boxshogi.Board;
import boxshogi.CheckMoves;
import boxshogi.Piece;
import boxshogi.Player;

public class BoxShieldPiece extends Piece {
    private static final char LABEL = 'S';

    /**
     * Constructor for BoxShieldPiece
     * @param owner - the owner of the piece, either player 1 or player 2
     */
    public BoxShieldPiece(Player owner){
        super(LABEL, owner);
    }

    @Override
    protected boolean canPlayerMove(int startRow, int startCol, int endRow, int endCol, Board board) {
        return CheckMoves.checkBoxShieldPiece(startRow, startCol, endRow, endCol, position);
    }
    @Override
    public boolean isLegalDrop(int row, int col, Board board) {
        return true;
    }

    @Override
    public void promote() {
        // this piece cannot be promoted
    }

    @Override
    public boolean promote(int startRow, int endRow, Board board) {
        return false;
    }

    @Override
    public void demote() {
        // this piece cannot be demoted
    }


}
