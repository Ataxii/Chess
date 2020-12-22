import java.awt.*;

public class Pawn extends Piece{
    public Pawn(int x, int y, Player owner) {
        super(x, y, owner);

    }

    @Override
    public boolean isMoveAuthorized(Board board, Coordinates destination) {
        int dx = destination.getX();
        int dy = destination.getY();
        int ox = this.getX();
        int oy = this.getY();

        int deltaX = ox - dx;
        int deltaY = oy - dy;
        if (Math.abs(deltaY)==1 && Math.abs(deltaX)==1){
            return true;
        }
        if (Math.abs(deltaY)==1 && Math.abs(deltaX) == 0){
            return true;
        }
        if (Math.abs(deltaY) == 2 && deltaX == 0) {
            return true;
        }
        return false;
    }

    @Override
    public Type getType() {
        return Type.PAWN;
    }

    @Override
    public int getValue() {
        return 1;
    }
}
