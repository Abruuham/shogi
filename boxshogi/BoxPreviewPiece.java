package boxshogi;

public class BoxPreviewPiece extends Piece{
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
    public void promote() {
        isPromoted = true;
    }

    @Override
    public void demote() {
        isPromoted = false;
    }
}
