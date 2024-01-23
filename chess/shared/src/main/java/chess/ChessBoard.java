package chess;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    ChessPiece myboard[][] = new ChessPiece[8][8];
    ChessPosition myWhiteKing = null;
    ChessPosition myBlackKing = null;
    int pieceCount = 0;

    public ChessBoard() {
        
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        //do nothing
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return null;
        //do nothing yet
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        for (int i = 0; i < 8; i++) {
            myboard[i][1] = new Pawn(ChessGame.TeamColor.WHITE); //later do new pawns instead of pieces

            myboard[i][6] = new Pawn(ChessGame.TeamColor.BLACK);
            //myboard[i][1] = pawn. //what is piecetype??
            if (i > 1 && i < 6) { //might not be working right, double check later to fix plz
                for (int j = 0; j < 8; j++) {
                    myboard[j][i] = null;
                }
            }
        }
        myboard[0][0] = new Rook(ChessGame.TeamColor.WHITE);
        myboard[7][0] = new Rook(ChessGame.TeamColor.WHITE);

        myboard[1][0] = new Knight(ChessGame.TeamColor.WHITE);
        myboard[6][0] = new Knight(ChessGame.TeamColor.WHITE);

        myboard[2][0] = new Bishop(ChessGame.TeamColor.WHITE);
        myboard[5][0] = new Bishop(ChessGame.TeamColor.WHITE);

        myboard[3][0] = new Queen(ChessGame.TeamColor.WHITE);
        myboard[4][0] = new King(ChessGame.TeamColor.WHITE);
        myWhiteKing = new ChessPosition(1,5);

        myboard[0][7] = new Rook(ChessGame.TeamColor.BLACK);
        myboard[7][7] = new Rook(ChessGame.TeamColor.BLACK);

        myboard[1][7] = new Knight(ChessGame.TeamColor.BLACK);
        myboard[6][7] = new Knight(ChessGame.TeamColor.BLACK);

        myboard[2][7] = new Bishop(ChessGame.TeamColor.BLACK);
        myboard[5][7] = new Bishop(ChessGame.TeamColor.BLACK);

        myboard[3][7] = new Queen(ChessGame.TeamColor.BLACK);
        myboard[4][7] = new King(ChessGame.TeamColor.BLACK);
        myBlackKing = new ChessPosition(8,5);

        pieceCount = 32;
        //System.out.println(this.toString());
        //specific starting game chess setup
        //do nothing
        //throw new RuntimeException("Not implemented");
    }
}
