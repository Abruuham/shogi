package boxshogi;

public class BoxNotesPiece extends Piece{
    private static final char LABEL = 'N';

    /**
     * Constructor for BoxNotesPiece
     * @param owner - the owner of the piece, either player 1 or player 2
     */
    public BoxNotesPiece(Player owner){
        super(owner);
        setLabel(LABEL);
    }

    @Override
    protected boolean canPlayerMove(int startRow, int startCol, int endRow, int endCol, Board board) {
        return super.canPlayerMove(startRow, startCol, endRow, endCol, board);
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
