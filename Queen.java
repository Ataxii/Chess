public class Queen extends Piece{
    public Queen(int x, int y, Player owner) {
        super(x, y, owner);
    }

    @Override
    public boolean isMoveAuthorized(Board board, Coordinates destination) {
      /*  if (board.sameDiagonalNothingBetween(this.position,destination)&& board.isEmptyCell(destination) && board.sameColumnNothingBetween(this.position,destination)&&board.sameRowNothingBetween(this.position,destination)){
            return true;
        }
        return false;*/
        int dx = destination.getX();
        int dy = destination.getY();
        int ox = this.getX();
        int oy = this.getY();
        if (dx == ox || dy == oy){
            return true;
        }
        return Math.abs(dx - ox) == Math.abs(dy - oy);
    }

    @Override
    public Type getType() {
        return Type.QUEEN;
    }

    @Override
    public int getValue() {
        return 9;
    }
}
