public class Rook extends Piece{
    public Rook(int x, int y, Player owner) {
        super(x, y, owner);
        this.owner=owner;
    }

    @Override
    public boolean isMoveAuthorized(Board board, Coordinates destination) {

        int dx = destination.getX();
        int dy = destination.getY();
        int ox = this.getX();
        int oy = this.getY();
        return dx == ox || dy == oy;
    }


    @Override
    public Type getType() {
        return Type.ROOK;
    }

    @Override
    public int getValue() {
        return 5;
    }
}
