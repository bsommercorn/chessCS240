package chess;

import java.util.Arrays;

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
        //myboard[position.getRow()][position.getColumn()] = piece;
        myboard[position.getColumn()][position.getRow()] = piece;
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
        //return myboard[position.getRow()][position.getColumn()];
        return myboard[position.getColumn()][position.getRow()];
        //do nothing yet
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        for (int i = 0; i < 8; i++) {
            myboard[i][1] = new Pawn(ChessGame.TeamColor.WHITE);

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
        //myWhiteKing = new ChessPosition(1,5);

        myboard[0][7] = new Rook(ChessGame.TeamColor.BLACK);
        myboard[7][7] = new Rook(ChessGame.TeamColor.BLACK);

        myboard[1][7] = new Knight(ChessGame.TeamColor.BLACK);
        myboard[6][7] = new Knight(ChessGame.TeamColor.BLACK);

        myboard[2][7] = new Bishop(ChessGame.TeamColor.BLACK);
        myboard[5][7] = new Bishop(ChessGame.TeamColor.BLACK);

        myboard[3][7] = new Queen(ChessGame.TeamColor.BLACK);
        myboard[4][7] = new King(ChessGame.TeamColor.BLACK);
        //myBlackKing = new ChessPosition(8,5);

        pieceCount = 32;
        //System.out.println(this.toString());
        //specific starting game chess setup
        //do nothing
        //throw new RuntimeException("Not implemented");
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        String myoutput = "chess.Board is: \n";
        boolean whiteSquare = true;
        for (int i = 7; i >= 0; i--) { //for every rank down from 8
            for (int j = 0; j < 8; j++) { //go across every file
                if (whiteSquare) {
                    //myoutput = myoutput + "\u001b[48;5;15m";
                    whiteSquare = false;
                }
                else {
                    //myoutput = myoutput + "\u001b[48;5;43m"; //fix this color
                    whiteSquare = true;
                }
                if (myboard[j][i] != null) {
                    //now do a black/white test with the real pieces
                    if (myboard[j][i].getTeamColor() == ChessGame.TeamColor.WHITE) { //0m
                        myoutput = myoutput + " W" + myboard[j][i].toString() + " ";
                    }
                    else {
                        myoutput = myoutput + " B" + myboard[j][i].toString() + " ";
                    }
                }
                else {
                    myoutput = myoutput + "    ";
                }
            }
            //myoutput = myoutput + "\u001b[48;5;235m\n"; //here is where we reset the background color so the board doesn't extend forever
            myoutput = myoutput + "\n";
            whiteSquare = !whiteSquare; //\e[0m
        }
        for (int j = 0; j < 8; j++) {
            myoutput = myoutput + (j + 1) + "   ";
        }
        myoutput = myoutput + "\n";
        /*
        for (int i = 0; i <= 7; i++) { //for every rank up from 0
            for (int j = 7; j >= 0; j--) { //go across every file
                if (whiteSquare) {
                    //myoutput = myoutput + "\u001b[48;5;15m";
                    whiteSquare = false;
                }
                else {
                    //myoutput = myoutput + "\u001b[48;5;43m"; //fix this color
                    whiteSquare = true;
                }
                if (myboard[j][i] != null) {
                    //now do a black/white test with the real pieces
                    if (myboard[j][i].getTeamColor() == ChessGame.TeamColor.WHITE) { //0m
                        myoutput = myoutput + " W" + myboard[j][i].toString() + " ";
                    }
                    else {
                        myoutput = myoutput + " B" + myboard[j][i].toString() + " ";
                    }
                }
                else {
                    myoutput = myoutput + "    ";
                }
            }
            //myoutput = myoutput + "\u001b[48;5;235m\n"; //here is where we reset the background color so the board doesn't extend forever
            myoutput = myoutput + "\n";
            whiteSquare = !whiteSquare; //\e[0m
        }
        myoutput = myoutput + " ";
        for (int j = 0; j < 8; j++) {
            myoutput = myoutput + (j + 1) + "   ";
        }
         */
        return myoutput;
    }
}
