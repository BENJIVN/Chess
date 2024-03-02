package chess;

//Knight Class

import java.util.ArrayList;

public class Knight {
    public static ArrayList<String> possibleMoves = new ArrayList<>();
    public static boolean move(int x, int y) {
//      System.out.println(x + "" + y);
        possibleMoves = new ArrayList<>();

        //TOP AND BOTTOM
        if (onBoard((x+1), (y+2))) {
            if (Chess.pieceExistsAtLocation(Chess.toMove((x+1), (y+2)))) {
                if (Chess.getPiece(Chess.toMove((x+1), (y+2))).pieceType.name().charAt(0) != Chess.getPiece(Chess.toMove(x, y)).pieceType.name().charAt(0)) {
                    possibleMoves.add(Chess.toMove((x+1), (y+2)));
                }
            } else {
                possibleMoves.add(Chess.toMove((x+1), (y+2)));
            }
        }
        if (onBoard((x-1), (y+2))) {
            if (Chess.pieceExistsAtLocation(Chess.toMove((x-1), (y+2)))) {
                if (Chess.getPiece(Chess.toMove((x-1), (y+2))).pieceType.name().charAt(0) != Chess.getPiece(Chess.toMove(x, y)).pieceType.name().charAt(0)) {
                    possibleMoves.add(Chess.toMove((x-1), (y+2)));
                }
            } else {
                possibleMoves.add(Chess.toMove((x-1), (y+2)));
            }
        }
        if (onBoard((x+1), (y-2))) {
            if (Chess.pieceExistsAtLocation(Chess.toMove((x+1), (y-2)))) {
                if (Chess.getPiece(Chess.toMove((x+1), (y-2))).pieceType.name().charAt(0) != Chess.getPiece(Chess.toMove(x, y)).pieceType.name().charAt(0)) {
                    possibleMoves.add(Chess.toMove((x+1), (y-2)));
                }
            } else {
                possibleMoves.add(Chess.toMove((x+1), (y-2)));
            }
        }
        if (onBoard((x-1), (y-2))) {
            if (Chess.pieceExistsAtLocation(Chess.toMove((x-1), (y-2)))) {
                if (Chess.getPiece(Chess.toMove((x-1), (y-2))).pieceType.name().charAt(0) != Chess.getPiece(Chess.toMove(x, y)).pieceType.name().charAt(0)) {
                    possibleMoves.add(Chess.toMove((x-1), (y-2)));
                }
            } else {
                possibleMoves.add(Chess.toMove((x-1), (y-2)));
            }
        }

        //LEFT AND RIGHT
        if (onBoard((x+2), (y+1))) {
            if (Chess.pieceExistsAtLocation(Chess.toMove((x+2), (y+1)))) {
                if (Chess.getPiece(Chess.toMove((x+2), (y+1))).pieceType.name().charAt(0) != Chess.getPiece(Chess.toMove(x, y)).pieceType.name().charAt(0)) {
                    possibleMoves.add(Chess.toMove((x+2), (y+1)));
                }
            } else {
                possibleMoves.add(Chess.toMove((x+2), (y+1)));
            }
        }
        if (onBoard((x+2), (y-1))) {
            if (Chess.pieceExistsAtLocation(Chess.toMove((x+2), (y-1)))) {
                if (Chess.getPiece(Chess.toMove((x+2), (y-1))).pieceType.name().charAt(0) != Chess.getPiece(Chess.toMove(x, y)).pieceType.name().charAt(0)) {
                    possibleMoves.add(Chess.toMove((x+2), (y-1)));
                }
            } else {
                possibleMoves.add(Chess.toMove((x+2), (y-1)));
            }
        }
        if (onBoard((x-2), (y+1))) {
            if (Chess.pieceExistsAtLocation(Chess.toMove((x-2), (y+1)))) {
                if (Chess.getPiece(Chess.toMove((x-2), (y+1))).pieceType.name().charAt(0) != Chess.getPiece(Chess.toMove(x, y)).pieceType.name().charAt(0)) {
                    possibleMoves.add(Chess.toMove((x-2), (y+1)));
                }
            } else {
                possibleMoves.add(Chess.toMove((x-2), (y+1)));
            }
        }
        if (onBoard((x-2), (y-1))) {
            if (Chess.pieceExistsAtLocation(Chess.toMove((x-2), (y-1)))) {
                if (Chess.getPiece(Chess.toMove((x-2), (y-1))).pieceType.name().charAt(0) != Chess.getPiece(Chess.toMove(x, y)).pieceType.name().charAt(0)) {
                    possibleMoves.add(Chess.toMove((x-2), (y-1)));
                }
            } else {
                possibleMoves.add(Chess.toMove((x-2), (y-1)));
            }
        }

//        possibleMoves.forEach(move-> System.out.println(move));
        return true;
    }

    public static boolean onBoard(int x, int y) {
        if ((x>=0 && x<8) && (y>=0 && y<8)) {
            return true;
        }
        return false;
    }

    public static boolean checkMove(int nx, int ny){
//        System.out.println("in checkmove\n\n");
//        possibleMoves.forEach(move-> System.out.println(move));
        for (int i = 0; i < possibleMoves.size(); i++) {
            if (possibleMoves.get(i).equals(Chess.toMove(nx, ny))) {
                return true;
            }
        }
        return false;
    }
}
