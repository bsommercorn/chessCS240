package chess.Pieces;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import chess.Pieces.ChessPiece;

import java.util.ArrayList;
import java.util.Collection;

public class Rook extends ChessPiece {
    public Rook(ChessGame.TeamColor teamColor) {
        super(teamColor, PieceType.ROOK);
        System.out.println("Rook was created");
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> myMoves = new ArrayList<ChessMove>();

        int myColumn = myPosition.getColumn() + 1;
        int myRow = myPosition.getRow() + 1;
        System.out.println("Accessing rook at (" + myRow + "," + myColumn + ")");
        //while we haven't hit the board or another piece, go right
        int goright = myColumn + 1;
        ChessPosition newPossible = new ChessPosition(myRow, goright);
        while (onBoard(newPossible) && pieceCheck(board, newPossible)) { //are move addresses incorrect???
            myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
            if (board.getPiece(newPossible) != null) {
                break;
            }
            else {
                goright = goright + 1;
                newPossible = new ChessPosition(myRow, goright);
            }
        }

        int goleft = myColumn - 1;
        newPossible = new ChessPosition(myRow, goleft);
        while (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
            if (board.getPiece(newPossible) != null) {
                break;
            }
            else {
                goleft = goleft - 1;
                newPossible = new ChessPosition(myRow, goleft);
            }
        }

        int goup = myRow + 1;
        newPossible = new ChessPosition(goup, myColumn);
        while (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
            if (board.getPiece(newPossible) != null) {
                break;
            }
            else {
                goup = goup + 1;
                newPossible = new ChessPosition(goup, myColumn);
            }
        }

        int godown = myRow - 1;
        newPossible = new ChessPosition(godown, myColumn);
        while (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
            if (board.getPiece(newPossible) != null) {
                break;
            }
            else {
                godown = godown - 1;
                newPossible = new ChessPosition(godown, myColumn);
            }
        }
        myMoves = protectKing(board, myMoves);

        System.out.println("Number of moves for this Rook is " + myMoves.size());
        for (int i = 0; i < myMoves.size(); i++) {
            System.out.print(myMoves.get(i).getEndPosition().toString() + " ");
        }
        System.out.println(" ");

        return myMoves;
    }
}