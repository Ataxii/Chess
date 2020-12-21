import java.util.ArrayList;
import java.util.List;

public abstract class Player{
    protected ChessColor color;
    private int score;
    private King king;
    public boolean isCheck;
    public boolean isCheckMate;

    public Player(ChessColor color){
        this.color = color;
        isCheck = false;
        isCheckMate = false;
        score = 0;


    }

    public ChessColor getColor(){
	return color;
    }

    public int getScore(){
	return score;
    }

    public void addToScore(int value){
        score = score + value;
    }
    
    public void removeFromScore(int value){
        score = score - value;
    }
    
    public abstract FromTo getFromTo();

    public Piece getKing(){
	return king;
    }
    
    public void setKing(King king){
        this.king = king;
    }
    
    public boolean isCheckMate(Board board){
	return false;
    }

    public void setCheck(){
        isCheck = true;
    }

    public void setCheckMate() {
        isCheckMate = true;
    }

    public void unSetCheck(){
        isCheck = false;
    }
    
    public List<Move> getAllMoves(Board board) {
        List<Move> list = new ArrayList<>();
        for (Piece piece : board.getPieces(this)) {
            list.addAll(piece.getAllMoves(board));
        }
	    return list;
    }

    @Override
    public String toString(){
	return null;
    }
}
