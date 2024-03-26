package chess.Pieces;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import chess.Pieces.ChessPiece;

import java.util.ArrayList;
import java.util.Collection;

public class Knight extends ChessPiece {
    public Knight(ChessGame.TeamColor teamColor) {
        super(teamColor, PieceType.KNIGHT);
        //System.out.println("Knight was created");
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> myMoves = new ArrayList<ChessMove>();

        int myColumn = myPosition.getColumn() + 1;
        int myRow = myPosition.getRow() + 1;
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
            myMoves.add(new ChessMove((ChessPosition) myPosition, newPossible));
            //System.out.println("Valid lower right move found at (" + newPossible.toString());
        }
        //myMoves = protectKing(board, myMoves);
        return myMoves;
    }
}
