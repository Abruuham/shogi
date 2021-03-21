package boxshogi;

public class BoxPreviewPice extends Piece{
    private static final char LABEL = 'P';


    /**
     * Constructor for BoxPreviewPice
     * @param owner - the owner of the piece, either player 1 or player 2
     */
    public BoxPreviewPice(Player owner){
        super(owner);
        setLabel(LABEL);
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
