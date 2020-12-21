public class Knight extends Piece{
    public Knight(int x, int y, Player owner) {
        super(x, y, owner);

    }

    @Override
    public boolean isMoveAuthorized(Board board, Coordinates destination) {
        int dx = destination.getX();
        int dy = destination.getY();
        int ox = this.getX();
        int oy = this.getY();

        if(dx == ox + 2 || dx == ox - 2){
            if (dy == oy + 1 || dy == oy - 1 ){
                return true;
            }
        }
        if(dx == ox + 1 || dx == ox - 1){
            return dy == oy + 2 || dy == oy - 2;
        }
        return false;
    }

    @Override
    public Type getType() {
        return Type.KNIGHT;
    }

    @Override
    public int getValue() {
        return 3;
    }
}
