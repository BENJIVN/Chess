package chess;

//Rook class

import java.util.ArrayList;

public class Rook {
    public static ArrayList<String> possibleMoves = new ArrayList<>();
    public static boolean move(int x, int y) {
//        System.out.println(x + "" + y);
        possibleMoves = new ArrayList<>();

        int i = 0;
        for (i = 1; i < 8; i++) {
            if (onBoard(x+i, y)) {
//                    System.out.println("x:" + (x + i) + " y:" + (y + i) + " i:" + i);
                if (Chess.pieceExistsAtLocation(Chess.toMove(x + i, y))) {
                    if (Chess.getPiece(Chess.toMove(x+i, y)).pieceType.name().charAt(0) != Chess.getPiece(Chess.toMove(x, y)).pieceType.name().charAt(0)) {
                        possibleMoves.add(Chess.toMove(x + i, y));
                        break;
                    }

                } else {
                    possibleMoves.add(Chess.toMove(x + i, y));
                }
            }
        }

        for (i = 1; i < 8; i++) {
            if (onBoard(x-i, y)) {
//                    System.out.println("x:" + (x + i) + " y:" + (y + i) + " i:" + i);
                if (Chess.pieceExistsAtLocation(Chess.toMove(x - i, y))) {
                    if (Chess.getPiece(Chess.toMove(x-i, y)).pieceType.name().charAt(0) != Chess.getPiece(Chess.toMove(x, y)).pieceType.name().charAt(0)) {
                        possibleMoves.add(Chess.toMove(x - i, y));
                        break;
                    }

                } else {
                    possibleMoves.add(Chess.toMove(x - i, y));
                }
            }
        }



        for (i = 1; i < 8; i++) {
            if (onBoard(x, y+i)) {
                if (Chess.pieceExistsAtLocation(Chess.toMove(x, y+i))) {
                    if (Chess.getPiece(Chess.toMove(x, y+i)).pieceType.name().charAt(0) != Chess.getPiece(Chess.toMove(x, y)).pieceType.name().charAt(0)) {
                        possibleMoves.add(Chess.toMove(x, y + i));
                        break;
                    }
                } else {
                    possibleMoves.add(Chess.toMove(x, y+i));
                }
            }
        }


        for (i = 1; i < 8; i++) {
            if (onBoard(x, y-i)) {
                if (Chess.pieceExistsAtLocation(Chess.toMove(x, y-i))) {
                    if (Chess.getPiece(Chess.toMove(x, y-i)).pieceType.name().charAt(0) != Chess.getPiece(Chess.toMove(x, y)).pieceType.name().charAt(0)) {
                        possibleMoves.add(Chess.toMove(x, y - i));
                        break;
                    }
                } else {
                    possibleMoves.add(Chess.toMove(x, y-i));
                }
            }
        }
        return true;
    }

    public static boolean onBoard(int x, int y) {
        if ((x>=0 && x<8) && (y>=0 && y<8)) {
            return true;
        }
        return false;
    }

    public static boolean checkMove(int nx, int ny){
        for (int i = 0; i < possibleMoves.size(); i++) {
            if (possibleMoves.get(i).equals(Chess.toMove(nx, ny))) {
                return true;
            }
        }

        return false;
    }
}
