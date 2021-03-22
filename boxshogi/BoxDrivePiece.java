package boxshogi;

// Box Drive piece (King in real shogi)
public final class BoxDrivePiece extends Piece{
    private static final char LABEL = 'D';

    /**
     * Constructor for BoxDrivePiece
     * @param owner - the owner of the piece, either player 1 or player 2
     */
    public BoxDrivePiece(Player owner){
        super(LABEL, owner);
    }

    @Override
    protected boolean canPlayerMove(int startRow, int endRow, int startCol, int endCol, Board board) {
        return CheckMoves.checkBoxDriveMove(startRow, endRow, startCol, endCol);
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
