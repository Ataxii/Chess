public class King extends Piece {

    public King(int x, int y, Player owner){
		super(x, y, owner);
		owner.setKing(this);
    }

    public boolean isMoveAuthorized(Board board, Coordinates destination){
		int dx = destination.getX();
		int dy = destination.getY();
		int ox = this.getX();
		int oy = this.getY();

		int deltaX = ox - dx;
		int deltaY = oy - dy;
		return Math.abs(deltaX) <= 1 && Math.abs(deltaY) <= 1;
	}

    @Override
    public Type getType() {
	return Type.KING;
    }

    @Override
    public int getValue() {
	return 0;
    }
    
}
