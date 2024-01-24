package chess;

import java.util.Objects;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {

    ChessPosition start;
    ChessPosition end;
    ChessPiece.PieceType mypromotion = null;

    public ChessMove(ChessPosition startPosition, ChessPosition endPosition,
                     ChessPiece.PieceType promotionPiece) {
        start = startPosition;
        end = endPosition;
        mypromotion = promotionPiece;
    }
    public ChessMove(ChessPosition startPosition, ChessPosition endPosition) {
        start = startPosition;
        end = endPosition;
        mypromotion = null;
    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {
        return start;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {
        return end;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece() {
        return mypromotion;
        //throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean equals(Object o) { //works
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessMove move = (ChessMove) o;
        return Objects.equals(start, move.start) && Objects.equals(end, move.end) && mypromotion == move.mypromotion;
    }

    @Override
    public int hashCode() { //why doesn't this work???
        return Objects.hash(start, end, mypromotion);
    }
}
