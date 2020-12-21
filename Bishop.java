import java.util.List;
import java.lang.Math;

public class Bishop extends Piece{
    public Bishop(int x, int y, Player owner) {
        super(x, y, owner);

    }

    @Override
    public boolean isMoveAuthorized(Board board, Coordinates destination) {
        int dx = destination.getX();
        int dy = destination.getY();
        int ox = this.getX();
        int oy = this.getY();
        return Math.abs(dx - ox) == Math.abs(dy - oy);
    }

    @Override
    public Type getType() {
        return Type.BISHOP;
    }

    @Override
    public int getValue() {
        return 3;
    }

}
