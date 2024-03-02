package chess;

import java.sql.Array;
import java.util.ArrayList;

//King Class
public class King {
    public static ArrayList<String> possibleMoves = new ArrayList<>();

    public static boolean move(int x, int y) {
        possibleMoves = new ArrayList<>();

        if (onBoard(x + 1, y)) {
            if (Chess.pieceExistsAtLocation(Chess.toMove(x + 1, y))) {
                if (Chess.getPiece(Chess.toMove(x + 1, y)).pieceType.name().charAt(0) != Chess.getPiece(Chess.toMove(x, y)).pieceType.name().charAt(0)) {
                    possibleMoves.add(Chess.toMove(x + 1, y));
                }

            } else {
                possibleMoves.add(Chess.toMove(x + 1, y));
            }
        }
        if (onBoard(x - 1, y)) {
            if (Chess.pieceExistsAtLocation(Chess.toMove(x - 1, y))) {
                if (Chess.getPiece(Chess.toMove(x - 1, y)).pieceType.name().charAt(0) != Chess.getPiece(Chess.toMove(x, y)).pieceType.name().charAt(0)) {
                    possibleMoves.add(Chess.toMove(x - 1, y));
                }

            } else {
                possibleMoves.add(Chess.toMove(x - 1, y));
            }
        }

        if (onBoard(x, y + 1)) {
            if (Chess.pieceExistsAtLocation(Chess.toMove(x, y + 1))) {
                if (Chess.getPiece(Chess.toMove(x, y + 1)).pieceType.name().charAt(0) != Chess.getPiece(Chess.toMove(x, y)).pieceType.name().charAt(0)) {
                    possibleMoves.add(Chess.toMove(x, y + 1));
                }

            } else {
                possibleMoves.add(Chess.toMove(x, y + 1));
            }
        }

        if (onBoard(x, y - 1)) {
            if (Chess.pieceExistsAtLocation(Chess.toMove(x, y - 1))) {
                if (Chess.getPiece(Chess.toMove(x, y - 1)).pieceType.name().charAt(0) != Chess.getPiece(Chess.toMove(x, y)).pieceType.name().charAt(0)) {
                    possibleMoves.add(Chess.toMove(x, y - 1));
                }

            } else {
                possibleMoves.add(Chess.toMove(x, y - 1));
            }
        }

        if (onBoard(x + 1, y + 1)) {
            if (Chess.pieceExistsAtLocation(Chess.toMove(x + 1, y + 1))) {
                if (Chess.getPiece(Chess.toMove(x + 1, y + 1)).pieceType.name().charAt(0) != Chess.getPiece(Chess.toMove(x, y)).pieceType.name().charAt(0)) {
                    possibleMoves.add(Chess.toMove(x + 1, y + 1));
                }

            } else {
                possibleMoves.add(Chess.toMove(x + 1, y + 1));
            }
        }

        if (onBoard(x - 1, y + 1)) {
            if (Chess.pieceExistsAtLocation(Chess.toMove(x - 1, y + 1))) {
                if (Chess.getPiece(Chess.toMove(x - 1, y + 1)).pieceType.name().charAt(0) != Chess.getPiece(Chess.toMove(x, y)).pieceType.name().charAt(0)) {
                    possibleMoves.add(Chess.toMove(x - 1, y + 1));
                }

            } else {
                possibleMoves.add(Chess.toMove(x - 1, y + 1));
            }
        }
        if (onBoard(x + 1, y - 1)) {
            if (Chess.pieceExistsAtLocation(Chess.toMove(x + 1, y - 1))) {
                if (Chess.getPiece(Chess.toMove(x + 1, y - 1)).pieceType.name().charAt(0) != Chess.getPiece(Chess.toMove(x, y)).pieceType.name().charAt(0)) {
                    possibleMoves.add(Chess.toMove(x + 1, y - 1));
                }

            } else {
                possibleMoves.add(Chess.toMove(x + 1, y - 1));
            }
        }

        if (onBoard(x - 1, y - 1)) {
            if (Chess.pieceExistsAtLocation(Chess.toMove(x - 1, y - 1))) {
                if (Chess.getPiece(Chess.toMove(x - 1, y - 1)).pieceType.name().charAt(0) != Chess.getPiece(Chess.toMove(x, y)).pieceType.name().charAt(0)) {
                    possibleMoves.add(Chess.toMove(x - 1, y - 1));
                }

            } else {
                possibleMoves.add(Chess.toMove(x - 1, y - 1));
            }
        }

        canCastle(Chess.toMove(x, y));
//        possibleMoves.forEach(move-> System.out.println(move));
//        System.out.println();
        return true;
    }


    public static boolean onBoard(int x, int y) {
        if ((x >= 0 && x < 8) && (y >= 0 && y < 8)) {
            return true;
        }
        return false;
    }

    public static boolean checkMove(int nx, int ny) {
        for (int i = 0; i < possibleMoves.size(); i++) {
            if (possibleMoves.get(i).equals(Chess.toMove(nx, ny))) {
                return true;
            }
        }

        return false;
    }
    public static ArrayList<String> canCastle(String move) {
        ArrayList<String> castle = new ArrayList<>();
//        System.out.println("move: " + move);

        if (Chess.KingHasMoved_WHITE && Chess.currentPlayer == Chess.Player.white) {
            return null;
        }
        if (Chess.KingHasMoved_BLACK && Chess.currentPlayer == Chess.Player.black) {
            return null;
        }
        if (Chess.pieceExistsAtLocation("e1") && (move.contains("e1"))) {
            if (Chess.getPiece("e1").pieceType == ReturnPiece.PieceType.WK
                    && !Chess.pieceExistsAtLocation("f1")
                    && !Chess.pieceExistsAtLocation("g1")
                    && Chess.getPiece("h1").pieceType == ReturnPiece.PieceType.WR) {
                possibleMoves.add("g1");
                castle.add("f1");
            }

            if (Chess.getPiece("e1").pieceType == ReturnPiece.PieceType.WK
                    && !Chess.pieceExistsAtLocation("d1")
                    && !Chess.pieceExistsAtLocation("c1")
                    && !Chess.pieceExistsAtLocation("b1")
                    && Chess.getPiece("a1").pieceType == ReturnPiece.PieceType.WR) {
                possibleMoves.add("c1");
                castle.add("d1");
            }
        }

        if (Chess.pieceExistsAtLocation("e8") && (move.contains("e8"))) {
            if (Chess.getPiece("e8").pieceType == ReturnPiece.PieceType.BK
                    && !Chess.pieceExistsAtLocation("f8")
                    && !Chess.pieceExistsAtLocation("g8")
                    && Chess.getPiece("h8").pieceType == ReturnPiece.PieceType.BR) {
                possibleMoves.add("g8");
                castle.add("f8");
            }

            if (Chess.getPiece("e8").pieceType == ReturnPiece.PieceType.BK
                    && !Chess.pieceExistsAtLocation("d8")
                    && !Chess.pieceExistsAtLocation("c8")
                    && !Chess.pieceExistsAtLocation("b8")
                    && Chess.getPiece("a8").pieceType == ReturnPiece.PieceType.BR) {
                possibleMoves.add("c8");
                castle.add("d8");
            }
        }
        return castle;
    }
}