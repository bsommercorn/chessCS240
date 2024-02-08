package chess;

import java.util.ArrayList;
import java.util.Collection;

public class King extends ChessPiece{
    public King(ChessGame.TeamColor teamColor) {
        super(teamColor, PieceType.KING);
        System.out.println("King was created");
    }

    public boolean kingCheck(ChessBoard board, ChessPosition myPosition) { //might need to make this public
        //apparently, we get a stack overflow if we try to check all enemy pieces for king-capturing moves
        return false;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> myMoves = new ArrayList<ChessMove>();

        int myColumn = myPosition.getColumn() + 1;
        int myRow = myPosition.getRow() + 1;

        board.removePiece(myPosition); //take the king out so we can check the board properly for threats

        //while we haven't hit the board or another piece, go right
        int goright = myColumn + 1;
        ChessPosition newPossible = new ChessPosition(myRow, goright);
        if (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            if (!newkingCheck(board, newPossible)) {
            myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
            }
        }

        int goleft = myColumn - 1;
        newPossible = new ChessPosition(myRow, goleft);
        if (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            if (!newkingCheck(board, newPossible)) {
            myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
            }
        }

        int goup = myRow + 1;
        newPossible = new ChessPosition(goup, myColumn);
        if (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            if (!newkingCheck(board, newPossible)) {
            myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
            }
        }

        int godown = myRow - 1;
        newPossible = new ChessPosition(godown, myColumn);
        if (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            if (!newkingCheck(board, newPossible)) {
            myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
            }
        }

        goright = myColumn + 1;
        goup = myRow + 1;
        newPossible = new ChessPosition(goup, goright);
        if (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            if (!newkingCheck(board, newPossible)) {
            myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
            }
        }

        goleft = myColumn - 1;
        goup = myRow + 1;
        newPossible = new ChessPosition(goup, goleft);
        if (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            if (!newkingCheck(board, newPossible)) {
            myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
            }
        }

        goleft = myColumn - 1;
        godown = myRow - 1;
        newPossible = new ChessPosition(godown, goleft);
        if (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            if (!newkingCheck(board, newPossible)) {
            myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
            }
        }

        godown = myRow - 1;
        goright = myColumn + 1;
        newPossible = new ChessPosition(godown, goright);
        if (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            if (!newkingCheck(board, newPossible)) {
            myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
            }
        }
        //before adding a move, check for threats
        //check the knight squares for knights (at that destination)
        //check the straightways for rooks and queens
        //check the diagonals for bishops and queens
        //check nearby diagonals up/down based on the kings color for pawns
        //if any of these return true, you can't move there.
        //myMoves = protectKing(board, myMoves);

        board.addPiece(myPosition, this);

        System.out.println("Number of moves for this King is " + myMoves.size());
        for (int i = 0; i < myMoves.size(); i++) {
            System.out.print(myMoves.get(i).getEndPosition().toString() + " ");
        }
        return myMoves;
    }

    public boolean newkingCheck(ChessBoard board, ChessPosition myPosition) {
        int myColumn = myPosition.getColumn() + 1;
        int myRow = myPosition.getRow() + 1;
        //while we haven't hit the board or another piece, go right
        int goright = myColumn + 1;
        ChessPosition newPossible = new ChessPosition(myRow, goright);
        ChessPiece threat = null;
        if (onBoard(newPossible)) {
            threat = board.getPiece(newPossible);
            if (threat != null && (threat.getPieceType() == PieceType.KING)) { //there was an enemy piece
                return true;
            }
        }
        while (onBoard(newPossible) && pieceCheck(board, newPossible)) { //friendly pieces return false for pieceCheck
            //myMoves.add(new Move((Position) myPosition,newPossible));
            threat = board.getPiece(newPossible);
            if (threat != null && (threat.getPieceType() == PieceType.QUEEN || threat.getPieceType() == PieceType.ROOK)) { //there was an enemy piece
                return true;
            }
            else {
                goright = goright + 1;
                newPossible = new ChessPosition(myRow, goright);
            }
        }
        int goleft = myColumn - 1;
        newPossible = new ChessPosition(myRow, goleft);
        if (onBoard(newPossible)) {
            threat = board.getPiece(newPossible);
            if (threat != null && (threat.getPieceType() == PieceType.KING)) { //there was an enemy piece
                return true;
            }
        }
        while (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            threat = board.getPiece(newPossible);
            if (threat != null && (threat.getPieceType() == PieceType.QUEEN || threat.getPieceType() == PieceType.ROOK)) { //there was an enemy piece
                return true;
            }
            else {
                goleft = goleft - 1;
                newPossible = new ChessPosition(myRow, goleft);
            }
        }

        int goup = myRow + 1;
        newPossible = new ChessPosition(goup, myColumn);
        if (onBoard(newPossible)) {
            threat = board.getPiece(newPossible);
            if (threat != null && (threat.getPieceType() == PieceType.KING)) { //there was an enemy piece
                return true;
            }
        }
        while (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            threat = board.getPiece(newPossible);
            if (threat != null && (threat.getPieceType() == PieceType.QUEEN || threat.getPieceType() == PieceType.ROOK)) { //there was an enemy piece
                return true;
            }
            else {
                goup = goup + 1;
                newPossible = new ChessPosition(goup, myColumn);
            }
        }

        int godown = myRow - 1;
        newPossible = new ChessPosition(godown, myColumn);
        if (onBoard(newPossible)) {
            threat = board.getPiece(newPossible);
            if (threat != null && (threat.getPieceType() == PieceType.KING)) { //there was an enemy piece
                return true;
            }
        }
        while (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            threat = board.getPiece(newPossible);
            if (threat != null && (threat.getPieceType() == PieceType.QUEEN || threat.getPieceType() == PieceType.ROOK)) { //there was an enemy piece
                return true;
            }
            else {
                godown = godown - 1;
                newPossible = new ChessPosition(godown, myColumn);
            }
        }

        goright = myColumn + 1;
        goup = myRow + 1;
        newPossible = new ChessPosition(goup, goright);
        if (onBoard(newPossible)) {
            threat = board.getPiece(newPossible);
            if (threat != null && (threat.getPieceType() == PieceType.KING)) { //there was an enemy piece
                return true;
            }
        }
        while (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            threat = board.getPiece(newPossible);
            if (threat != null && (threat.getPieceType() == PieceType.QUEEN || threat.getPieceType() == PieceType.BISHOP)) { //there was an enemy piece
                return true;
            }
            else {
                goright = goright + 1;
                goup = goup + 1;
                newPossible = new ChessPosition(goup, goright);
            }
        }

        goleft = myColumn - 1;
        goup = myRow + 1;
        newPossible = new ChessPosition(goup, goleft);
        if (onBoard(newPossible)) {
            threat = board.getPiece(newPossible);
            if (threat != null && (threat.getPieceType() == PieceType.KING)) { //there was an enemy piece
                return true;
            }
        }
        while (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            threat = board.getPiece(newPossible);
            if (threat != null && (threat.getPieceType() == PieceType.QUEEN || threat.getPieceType() == PieceType.BISHOP)) { //there was an enemy piece
                return true;
            }
            else {
                goleft = goleft - 1;
                goup = goup + 1;
                newPossible = new ChessPosition(goup, goleft);
            }
        }

        goleft = myColumn - 1;
        godown = myRow - 1;
        newPossible = new ChessPosition(godown, goleft);
        if (onBoard(newPossible)) {
            threat = board.getPiece(newPossible);
            if (threat != null && (threat.getPieceType() == PieceType.KING)) { //there was an enemy piece
                return true;
            }
        }
        while (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            threat = board.getPiece(newPossible);
            if (threat != null && (threat.getPieceType() == PieceType.QUEEN || threat.getPieceType() == PieceType.BISHOP)) { //there was an enemy piece
                return true;
            }
            else {
                godown = godown - 1;
                goleft = goleft - 1;
                newPossible = new ChessPosition(godown, goleft);
            }
        }

        godown = myRow - 1;
        goright = myColumn + 1;
        newPossible = new ChessPosition(godown, goright);
        if (onBoard(newPossible)) {
            threat = board.getPiece(newPossible);
            if (threat != null && (threat.getPieceType() == PieceType.KING)) { //there was an enemy piece
                return true;
            }
        }
        while (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            threat = board.getPiece(newPossible);
            if (threat != null && (threat.getPieceType() == PieceType.QUEEN || threat.getPieceType() == PieceType.BISHOP)) { //there was an enemy piece
                return true;
            }
            else {
                godown = godown - 1;
                goright = goright + 1;
                newPossible = new ChessPosition(godown, goright);
            }
        }

        newPossible = new ChessPosition(myRow + 1, myColumn + 2); //right upper
        if (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            threat = board.getPiece(newPossible);
            if (threat != null && threat.getPieceType() == PieceType.KNIGHT) { //there was an enemy piece
                return true;
            }
        }
        newPossible = new ChessPosition(myRow + 2, myColumn + 1);
        if (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            threat = board.getPiece(newPossible);
            if (threat != null && threat.getPieceType() == PieceType.KNIGHT) { //there was an enemy piece
                return true;
            }
        }
        newPossible = new ChessPosition(myRow + 2, myColumn - 1); //upper left
        if (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            threat = board.getPiece(newPossible);
            if (threat != null && threat.getPieceType() == PieceType.KNIGHT) { //there was an enemy piece
                return true;
            }
        }
        newPossible = new ChessPosition(myRow + 1, myColumn - 2);
        if (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            threat = board.getPiece(newPossible);
            if (threat != null && threat.getPieceType() == PieceType.KNIGHT) { //there was an enemy piece
                return true;
            }
        }
        newPossible = new ChessPosition(myRow - 2, myColumn - 1); //left lower
        if (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            threat = board.getPiece(newPossible);
            if (threat != null && threat.getPieceType() == PieceType.KNIGHT) { //there was an enemy piece
                return true;
            }
        }
        newPossible = new ChessPosition(myRow - 1, myColumn -2);
        if (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            threat = board.getPiece(newPossible);
            if (threat != null && threat.getPieceType() == PieceType.KNIGHT) { //there was an enemy piece
                return true;
            }
        }
        newPossible = new ChessPosition(myRow - 2, myColumn + 1); //lower right
        if (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            threat = board.getPiece(newPossible);
            if (threat != null && threat.getPieceType() == PieceType.KNIGHT) { //there was an enemy piece
                return true;
            }
        }
        newPossible = new ChessPosition(myRow - 1, myColumn + 2);
        if (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            threat = board.getPiece(newPossible);
            if (threat != null && threat.getPieceType() == PieceType.KNIGHT) { //there was an enemy piece
                return true;
            }
        }

        if (myColor == ChessGame.TeamColor.BLACK) {
            ChessPosition pawnthreat = new ChessPosition(myRow - 1, myColumn - 1);
            if (onBoard(pawnthreat)) {
                threat = board.getPiece(pawnthreat);
                if (threat != null && threat.getPieceType() == PieceType.PAWN && threat.getTeamColor() != ChessGame.TeamColor.BLACK) {
                    return true;
                }
            }
            pawnthreat = new ChessPosition(myRow - 1, myColumn + 1);
            if (onBoard(pawnthreat)) {
                threat = board.getPiece(pawnthreat);
                if (threat != null && threat.getPieceType() == PieceType.PAWN && threat.getTeamColor() != ChessGame.TeamColor.BLACK) {
                    return true;
                }
            }
        }
        if (myColor == ChessGame.TeamColor.WHITE) {
            ChessPosition pawnthreat = new ChessPosition(myRow + 1, myColumn - 1);
            if (onBoard(pawnthreat)) {
                threat = board.getPiece(pawnthreat);
                if (threat != null && threat.getPieceType() == PieceType.PAWN && threat.getTeamColor() != ChessGame.TeamColor.WHITE) {
                    return true;
                }
            }
            pawnthreat = new ChessPosition(myRow + 1, myColumn + 1);
            if (onBoard(pawnthreat)) {
                threat = board.getPiece(pawnthreat);
                if (threat != null && threat.getPieceType() == PieceType.PAWN && threat.getTeamColor() != ChessGame.TeamColor.WHITE) {
                    return true;
                }
            }
        }
        return false;
    }

    /*

    */
}
