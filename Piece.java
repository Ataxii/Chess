import java.util.ArrayList;
import java.util.List;

public abstract class Piece{
    protected Coordinates position;
    protected Player owner;

    
    public Piece(int x, int y, Player owner){
        position = new Coordinates(x,y);
        this.owner = owner;
    }

    public enum Type {
        KING,
        QUEEN,
        ROOK,
        BISHOP,
        KNIGHT,
        PAWN
    }

    public void setPosition(Coordinates destination){
        position = destination;
    }
    
    public Player getOwner(){
	return this.owner;
    }

    public ChessColor getColor(){
	return owner.color;
    }

    public Coordinates getPosition(){
	return position;
    }

    public int getX(){
	return position.getX();
    }
    
    public int getY(){
	return position.getY();
    }

    public List<Move> getAllMoves(Board board) {
        List<Move> allMoves = new ArrayList();
        for (Coordinates coordinates : board.getAllCoordinates()){
            if (isMoveAuthorized(board, coordinates)&&coordinates !=null){
                allMoves.add(new Move(board, this.getPosition(),coordinates));
            }
        }
	    return allMoves;
    }

    public boolean sameColor(Piece piece){
	return this.getColor() == piece.getColor();
    }

    public abstract boolean isMoveAuthorized(Board board, Coordinates destination);

    public abstract Type getType();
    public abstract int getValue();
    

}
