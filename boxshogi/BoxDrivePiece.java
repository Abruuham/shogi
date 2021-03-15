package boxshogi;

// Box Drive piece (King in real shogi)
public class BoxDrivePiece extends Piece{
    private static final char SYMBOL = 'D';

    /**
     * Constructor for BoxDrivePiece
     * @param owner - the owner of the piece, either player 1 or player 2
     */
    public BoxDrivePiece(int owner){
        super(owner);
        setSymbol(SYMBOL);
    }

    @Override
    protected boolean canPlayerMove(int startRow, int endRow, int startCol, int endCol, Board board) {
        return CheckMoves.checkBoxDriveMove(startRow, endRow, startCol, endCol);
    }

    @Override
    public void promote() {
        // this piece cannot be promoted
    }

    @Override
    public void demote() {
        // this piece cannot be demoted
    }
}
