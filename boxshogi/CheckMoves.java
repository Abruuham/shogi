package boxshogi;

public class CheckMoves {


    public static boolean checkBoxDriveMove(int startRow, int startCol, int endRow, int endCol){
        int row = Math.abs(endRow - startRow);
        int col = Math.abs(endCol - startCol);
        return (row <= 1 && col <= 1);
    }

    public static boolean checkBoxGovernancePiece(int startRow, int startCol, int endRow, int endCol, Board board){
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

    public static boolean checkBoxNotesPiece(int startRow, int startCol, int endRow, int endCol, Board board){
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

    public static boolean checkBoxShieldPiece(int startRow, int startCol, int endRow, int endCol, Position position){
        int deltaRow = endRow - startRow;
        int deltaCol = endCol - startCol;
        if (deltaRow == 0 && (deltaCol == 1 || deltaCol == -1)) return true;
        else if (deltaCol == 0 && (deltaRow == 1 || deltaRow == -1)) return true;
        else if (position == Position.UPPER) return (deltaRow == 1 && (deltaCol == -1 || deltaCol == 1));
        else return (deltaRow == -1 && (deltaCol == -1 || deltaCol == 1));
    }

    public static boolean checkBoxRelayPiece(int startRow, int startCol, int endRow, int endCol){
        int row = endRow - startRow;
        int col = endCol - startCol;

        if (col == 0 && row == 1) return true;
        else if(col == 1 && (row == 1 || row == -1)) return true;
        else if(col == -1 && (row == 1 || row == 1)) return true;
        return false;
    }

    public static boolean checkBoxPreviewPiece(int startRow, int startCol, int endRow, int endCol){
        int row = endRow - startRow;
        int col = endCol - startCol;

        if(col == 0 && row == -1) return true;
        else return (col == 0 && row == 1);
    }



}
