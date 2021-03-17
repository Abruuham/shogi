package boxshogi;

public class CheckMoves {
    static boolean checkBoxDriveMove(int startRow, int endRow, int startCol, int endCol){
        int row = Math.abs(endRow - startRow);
        int col = Math.abs(endCol - startCol);
        return (row <= 1 && col <= 1);
    }

    static boolean checkBoxGovernancePiece(int startRow, int endRow, int startCol, int endCol, Board board){
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

    static boolean checkBoxNotesPiece(int startRow, int endRow, int startCol, int endCol, Board board){
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
}
