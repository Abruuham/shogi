package boxshogi;

import java.util.Map;

public class CheckMoves {

    private final Map<Player, Map<String, Integer>> boxDriveLocation;

    public CheckMoves(Map<Player, Map<String, Integer>> boxDriveLocation) {
        this.boxDriveLocation = boxDriveLocation;
    }

    public static boolean checkBoxDriveMove(int startRow, int endRow, int startCol, int endCol){
        int row = Math.abs(endRow - startRow);
        int col = Math.abs(endCol - startCol);
        return (row <= 1 && col <= 1);
    }

    public static boolean checkBoxGovernancePiece(int startRow, int endRow, int startCol, int endCol, Board board){
        int row = endRow - startRow;
        int col = endCol - startCol;
        int rowDelta, colDelta = 0;
        if(row < 0) {
            rowDelta = -1;
        } else{
            rowDelta = 1;
        }

        if(col < 0){
            colDelta = -1;
        } else{
            colDelta = 1;
        }

        if(Math.abs(row) != Math.abs(col)){
            return false;
        }

        for(int i = 1; i < board.BOARD_SIZE; i++){
            int newRow = row + rowDelta * i;
            int newCol = col + colDelta * i;
            if( newRow < 0 || newRow > 4 || newCol < 0 || newCol > 4){
                return false;
            }
            if(board.getPiece(newRow, newCol) != null){
                return false;
            }
        }

        return true;
    }

    public static boolean checkBoxNotesPiece(int startRow, int endRow, int startCol, int endCol, Board board){
        int row = endRow - startRow;
        int col = endCol - startCol;
        if(row != 0 || col != 0){
            return false;
        } else if(row == 0){
          int colDelta = 0;
          if(col < 0){
              colDelta = -1;
            } else{
              colDelta = 1;
          }
          for(int i = 1; i < Math.abs(col); i++){
              int newCol = startCol + colDelta * i;
              if(board.getPiece(startRow, newCol) != null) return false;
          }
        } else{
            int rowDelta = 0;
            if(row < 0){
                rowDelta = -1;
            } else{
                rowDelta = 1;
            }
            for(int i = 1; i < Math.abs(row); i++){
                int newRow = startRow + rowDelta * i;
                if(board.getPiece(newRow, startCol) != null) return false;
            }
        }
        return true;
    }

    public static boolean checkBoxShieldPiece(int startRow, int endRow, int startCol, int endCol){
        int row = endRow - startRow;
        int col = endCol - startCol;

        if(row == 0 && (col == 1 || col == -1)) return true;
        else if(col == 0 && (row == 1 || row == -1)) return true;
        else if(row ==  -1 && (col == 1 || col == -1)) return true;
        return true;
    }

    public static boolean checkBoxRelayPiece(int startRow, int endRow, int startCol, int endCol){
        int row = endRow - startRow;
        int col = endCol - startCol;

        if (col == 0 && row == 1) return true;
        else if(col == 1 && (row == 1 || row == -1)) return true;
        else if(col == -1 && (row == 1 || row == 1)) return true;
        return false;
    }

    public static boolean checkBoxPreviewPiece(int startRow, int endRow, int startCol, int endCol){
        int row = endRow - startRow;
        int col = endCol - startCol;

        if(col == 0 && row == -1) return true;
        else return (col == 0 && row == 1);
    }

    public boolean isMoveValid(int startRow, int endRow, int startCol, int endCol, boolean promote, Player player){

        return true;
    }

    private Player getOpponent(Player currentPlayer) {
        for (Map.Entry<Player, Map<String, Integer>> e : boxDriveLocation.entrySet()) {
            if (e.getKey() != currentPlayer) return e.getKey();
        }
        return null;
    }

    public Map<String, Integer> getOpponentBoxDriveLocation(Player currentPlayer) {
        return boxDriveLocation.get(getOpponent(currentPlayer));
    }

    boolean isCheck(Player player, Board board){
        for(int i = 0; i < Board.getBoardSize(); i++) {
            for(int j = 0; j < Board.getBoardSize(); j++) {
                Piece piece = Board.board[i][j];
                if(piece != null && piece.getOwner() == player) {
                    Map<String, Integer> boxDriveLocation = getOpponentBoxDriveLocation(player);
                    if(piece.isMoveValid(i, j, boxDriveLocation.get("row"), boxDriveLocation.get("col"), board)){
                        return true;
                    }
                }
            }
        }

        return false;
    }


}
