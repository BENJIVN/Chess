package chess;
import java.util.ArrayList;

//Pawn Class

public class Pawn {
    public static ArrayList<String> possibleMoves = new ArrayList<>();
    public static boolean move(int x, int y, String color) {
        possibleMoves = new ArrayList<>();
        int direction = color.equals("white") ? 1 : -1;
        //single move forward
        if(onBoard(x, y + direction) && !Chess.pieceExistsAtLocation(Chess.toMove(x, y+direction))){
            possibleMoves.add(Chess.toMove(x,y +direction));
        }
        //double move forward from starting position
        if((color.equals("white") && y == 1 || color.equals("black") && y == 6)
        && !Chess.pieceExistsAtLocation(Chess.toMove(x,y + 2 * direction))) {
            possibleMoves.add(Chess.toMove(x, y + 2 * direction));
        }

        if(Chess.canEnPassant(x, y, color)) {
            if(color.equals("white") && y ==4){
                addEnPassantMoves(x, y, direction);
            } else if (color.equals("black") && y == 3){
                addEnPassantMoves(x, y, direction);
            }
        }

            return !possibleMoves.isEmpty();
        }

    public static boolean onBoard(int x, int y) {
        if ((x>=0 && x<8) && (y>=0 && y<8)) {
            return true;
        }
        return false;
    }

    public static boolean checkMove(int nx, int ny, int x, int y, String color){
        String targetMove = Chess.toMove(nx, ny);
        if (possibleMoves.contains(targetMove)) {
            return true;
        }
        
        int direction = color.equals("white") ? 1 : -1;
        int captureY = y + direction; 

        if (Chess.canEnPassant(x, y, color)) {
            if ((color.equals("white") && y == 4 && ny == 5) || (color.equals("black") && y == 3 && ny == 2)) {
                if (nx == x + 1 || nx == x - 1) {
                    return true;
                }
            }
    }

    return false; // The move is not valid
        // for (int i = 0; i < possibleMoves.size(); i++) {
        //     if (possibleMoves.get(i).equals(Chess.toMove(Integer.toString(nx)+Integer.toString(ny)))) {
        //         return true;
        //     }
        // } return false;

    }


    public static boolean isPromotionPossible(int  y, String color){
        return (color.equals("white") && y == 7) || (color.equals("black") && y == 0);
    }

    public static void addEnPassantMoves(int x, int y, int direction){
        if (Chess.pieceExistsAtLocation(Chess.toMove(x - 1, y)) && Chess.lastMove.substring(2, 3).equals(Chess.toMove(x - 1, y + (direction * 2)) + " " + Chess.toMove(x - 1, y))) {
            possibleMoves.add(Chess.toMove(x - 1, y + direction)); // Capture left
        }
        if (Chess.pieceExistsAtLocation(Chess.toMove(x + 1, y)) && Chess.lastMove.substring(2, 3).equals(Chess.toMove(x + 1, y + (direction * 2)) + " " + Chess.toMove(x + 1, y))) {
            possibleMoves.add(Chess.toMove(x + 1, y + direction)); // Capture right
        }
    }

//    public static void printPossibleMoves(){
//        System.out.println("PossibleMove:");
//        for (String move : possibleMoves){
//            System.out.println(move);
//        }
//    }

}


