package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Knight extends ChessPiece{
    public Knight(ChessGame.TeamColor teamColor) {
        super(teamColor, PieceType.KNIGHT);
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        //so, we know the board, and we can make moves based on where we are and the other pieces.
        //first let's start with getting to a place on the board that the knight could move
        //go to each of the 8 knight positions

        //how to store the moves? the order will never matter, but the number will vary for any piece
        ArrayList<ChessMove> myMoves = new ArrayList<ChessMove>();

        int myColumn = myPosition.getColumn();
        int myRow = myPosition.getRow();
        System.out.println("Accessing knight at (" + myRow + "," + myColumn + ")");

        ChessPosition newPossible = new ChessPosition(myRow + 1, myColumn + 2); //right upper
        if (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
            //System.out.println("Valid upper right move found at (" + newPossible.toString());
        }
        newPossible = new ChessPosition(myRow + 2, myColumn + 1);
        if (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
            //System.out.println("Valid upper right move found at (" + newPossible.toString());
        }

        newPossible = new ChessPosition(myRow + 2, myColumn - 1); //upper left
        if (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
            //System.out.println("Valid upper left move found at (" + newPossible.toString());
        }
        newPossible = new ChessPosition(myRow + 1, myColumn - 2);
        if (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
            //System.out.println("Valid upper left move found at (" + newPossible.toString());
        }

        newPossible = new ChessPosition(myRow - 2, myColumn - 1); //left lower
        if (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
            //System.out.println("Valid lower left move found at (" + newPossible.toString());
        }
        newPossible = new ChessPosition(myRow - 1, myColumn -2);
        if (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
            //System.out.println("Valid lower left move found at (" + newPossible.toString());
        }

        newPossible = new ChessPosition(myRow - 2, myColumn + 1); //lower right
        if (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
            //System.out.println("Valid lower right move found at (" + newPossible.toString());
        }
        newPossible = new ChessPosition(myRow - 1, myColumn + 2);
        if (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
            //System.out.println("Valid lower right move found at (" + newPossible.toString());
        }
        /*
        System.out.println("Number of moves for this Knight is " + myMoves.size());
        for (int i = 0; i < myMoves.size(); i++) {
            System.out.print(myMoves.get(i).getEndPosition().toString() + " ");
        }
        System.out.println(" ");
         */


        //myMoves = protectKing(board, myMoves);
        return myMoves;
    }
}
