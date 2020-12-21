import jdk.internal.jimage.ImageStrings;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

public class Board {
    private Piece[][] array;

    public Board(String fileName, Player white, Player black) {
        int pieceType;
        int col;
        int row;
        String nextWord;
        Player owner;

        this.array = new Piece[8][8];
        try {
            File file = new File(fileName);
            if (file.exists() == false) {
                System.err.println("Error: Cannot find file " + fileName);
                System.exit(1);
            }

            Scanner in = new Scanner(file);
            while (in.hasNext()) {
                if ((nextWord = in.nextLine()).length() > 2) {
                    pieceType = nextWord.charAt(0);
                    col = nextWord.charAt(1) - '0';
                    row = nextWord.charAt(2) - '0';

                    owner = black;
                    if (pieceType >= 'a' && pieceType <= 'z') {
                        owner = white;
                    }
                    switch (pieceType) {
                        case 'K': case 'k': this.addPiece(new King(col, row, owner)); break;
                        case 'B': case 'b': this.addPiece(new Bishop(col, row, owner)); break;
                        case 'N': case 'n':  this.addPiece(new Knight(col, row, owner)); break;
                        case 'Q': case 'q':  this.addPiece(new Queen(col, row, owner)); break;
                        case 'R': case 'r':  this.addPiece(new Rook(col, row, owner)); break;
                        case 'P': case 'p':  this.addPiece(new Pawn(col, row, owner)); break;
                    }
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error file not found : " + e);
            System.exit(1);
        }
    }

    public List<Coordinates> getAllCoordinates() {
        List<Coordinates> coordi = new ArrayList();
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++) {
                coordi.add(new Coordinates(i,j));
            }
        }
        return coordi;
    }

    public List<Piece> getPieces(Player player) {
        List<Piece> list = new ArrayList<>();
        for (Piece piece : getPieces()){
            if (piece.sameColor(player.getKing())){
                list.add(piece);
            }
        }
        return list;
    }

    public List<Piece> getPieces() {
        List<Piece> list = new ArrayList<>();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (!isEmptyCell(x,y)) {
                    list.add(this.array[x][y]);
                }
            }
        }
        return list;
    }

    public void addPiece(Piece piece) {
        int x = piece.getX();
        int y = piece.getY();
        this.array[x][y] = piece;
    }

    public Piece getPiece(Coordinates coordinates) {
        int x = coordinates.getX();
        int y = coordinates.getY();
        return getPiece(x, y);
    }

    public Piece getPiece(int x, int y) {
        return this.array[x][y];
    }

    public void emptyCell(Coordinates coordinates) {
        int x = coordinates.getX();
        int y = coordinates.getY();
        if (!isEmptyCell(coordinates)) {
            array[x][y] = null;
        }
    }

    public boolean isEmptyCell(Coordinates coordinates) {
        int x = coordinates.getX();
        int y = coordinates.getY();
        return isEmptyCell(x, y);
    }

    public boolean isEmptyCell(int x, int y) {
        if (array[x][y] == null) {
            return true;
        }
        return false;
    }

    public boolean sameColumnNothingBetween(Coordinates origin, Coordinates destination) {
        int supY;
        int infX;
        int infY;
        if(origin.getY() < destination.getY()){
            infX = origin.getX();
            infY = origin.getY();
            supY = destination.getY();
        }else {
            infX = destination.getX();
            infY = destination.getY();
            supY = origin.getY();
        }
        for(int y = infY+1; y < supY ; y++){
            if (!isEmptyCell(infX,y)){
                return false;
            }
        }
        return true;
    }

    public boolean sameRowNothingBetween(Coordinates origin, Coordinates destination) {
        int supX;
        int infX;
        int infY;
        if(origin.getX() < destination.getX()){
            infX = origin.getX();
            infY = origin.getY();
            supX = destination.getX();
        }else {
            supX = origin.getX();
            infX = destination.getX();
            infY = destination.getY();
        }
        for(int x = infX+1; x < supX ; x++){
            if (!isEmptyCell(x,infY)){
                return false;
            }
        }
        return true;
    }

    public boolean sameDiagonalNothingBetween(Coordinates origin, Coordinates destination) {
        //on regarde d'abord le quel des deux a le plus petit y, pour par la suite limiter les actions a 2.
        //on regarde ensuite celui qui a le plus petit x pour voir si on fait une diagonal qui descend a droite ou a
        //gauche
        int supX;
        int infX;
        int infY;
        int supY;
        if(origin.getY() < destination.getY()){
            infX = origin.getX();
            infY = origin.getY();
            supX = destination.getX();
            supY = destination.getY();
        }else {
            supY = origin.getY();
            supX = origin.getX();
            infX = destination.getX();
            infY = destination.getY();
        }

        if (infX < supX){
            for (int ite = 1; ite < supY-infY; ite++){
                if (!isEmptyCell(ite+infX, infY+ite)){
                    return false;
                }
            }
        }else {
            for (int ite = 1; ite < supY-infY; ite++){
                if (!isEmptyCell(infX-ite, infY+ite)){
                    return false;
                }
            }
        }
        return true;
    }
}
