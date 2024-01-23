package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    ChessGame.TeamColor myColor;
    ChessPiece.PieceType myType;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        myColor = pieceColor;
        myType = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return myColor;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return myType;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
        //throw new RuntimeException("Not implemented");
    }

    protected boolean onBoard(ChessPosition possible) { //all pieces should have this actually
        if (possible.getColumn() < 1 || possible.getColumn() > 8) {
            return false;
        }
        if (possible.getRow() < 1 || possible.getRow() > 8) {
            return false;
        }
        return true;
    }

    protected boolean pieceCheck (ChessBoard board, ChessPosition myPosition) {
        ChessPiece pieceHere = board.getPiece(myPosition);
        if (pieceHere == null) { //pawn needs an override here, can't move diagonal without enemy pieces present
            return true;
        }
        else if (pieceHere.getTeamColor() != myColor) { //there was an enemy piece, potential capture
            return true;
        }
        //System.out.println("Movement blocked by: " + pieceHere.toString());
        return false;
    }

    @Override
    public String toString() {
        String myoutput = "";
        if (myColor == ChessGame.TeamColor.WHITE) {
            switch (myType) {
                case KING:
                    return "K";
                case QUEEN:
                    return "Q";
                case KNIGHT:
                    return "N";
                case BISHOP:
                    return "B";
                case ROOK:
                    return "R";
                case PAWN:
                    return "P";
            }
        }
        else {
            switch (myType) {
                case KING:
                    return "k";
                case QUEEN:
                    return "q";
                case KNIGHT:
                    return "n";
                case BISHOP:
                    return "b";
                case ROOK:
                    return "r";
                case PAWN:
                    return "p";
            }
        }
        return myoutput;
    }
}
