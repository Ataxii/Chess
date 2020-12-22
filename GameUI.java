import java.util.List;
import java.util.Stack;

public class GameUI {
    public Board board;
    private Player white;
    private Player black;
    private Player currentPlayer;
    private ChessUI ui;
    private Stack<Move> history;
    
    public GameUI(ChessUI ui, String boardConfigFileName, Player white, Player black){
        this.board = new Board(boardConfigFileName, white, black);
        this.white = white;
        this.black = black;
        this.currentPlayer = white;
        this.ui = ui;
	    this.history = new Stack<Move>();

        for(Piece p : board.getPieces()) {
            this.ui.placePiece(p.getType(), p.getColor(), p.getPosition());
        }
    }

    //undo donné par le prof sur le git

    public Board getBoard(){
	return board;
    }

    public boolean undo(){
        if(this.history.empty()) return false;
        Move move = this.history.pop();
        board.emptyCell(move.destination);
        ui.removePiece(move.destination);
        if(move.pieceAtDestination != null){
            move.pieceAtDestination.setPosition(move.destination);
            board.addPiece(move.pieceAtDestination);
            ui.placePiece(move.pieceAtDestination.getType(), move.pieceAtDestination.getColor(), move.pieceAtDestination.getPosition());
        }
        board.emptyCell(move.origin);
        ui.removePiece(move.origin);
        move.pieceAtOrigin.setPosition(move.origin);
        board.addPiece(move.pieceAtOrigin);
        ui.placePiece(move.pieceAtOrigin.getType(), move.pieceAtOrigin.getColor(), move.pieceAtOrigin.getPosition());

        currentPlayer = move.pieceAtOrigin.getOwner();
        //if(move.pieceAtDestination != null)
        //    currentPlayer.removeFromScore(move.pieceAtDestination.getValue());
        return true;
    }

    public Player getPlayer(ChessColor color){
        if(color.equals(getCurrentPlayer().color)){
            return currentPlayer;
        }
        else return getOpponent(currentPlayer);
    }
    
    public boolean isMovePlayable(Move gameMove) {

        //la case origine est vide de base
        if(board.isEmptyCell(gameMove.origin)){
            return false;
        }

        //la piece lui appartient pas
        if (!(board.getPiece(gameMove.origin).owner == currentPlayer)) {
            return false;
        }

        //la piece de joueur n'est pas deplacer sur une autre piece du joueur
        if (!board.isEmptyCell(gameMove.destination)){
            if (gameMove.pieceAtOrigin.owner == board.getPiece(gameMove.destination).owner) {
                return false;
            }
        }

        //si le deplacement est authorisé en tant que piece
        if (!gameMove.pieceAtOrigin.isMoveAuthorized(board, gameMove.destination)) {
            return false;
        }

        //si la piece n'a pas bougé
        if (gameMove.origin == gameMove.destination) {
            return false;
        }
        //il ne faut pas qu'il y est de pieces entre
        if(gameMove.origin.getX()== gameMove.destination.getX()){
            if (!board.sameColumnNothingBetween(gameMove.origin,gameMove.destination)) {
                return false;
            }
        }
        if(gameMove.origin.getY()== gameMove.destination.getY()){
            if (!board.sameRowNothingBetween(gameMove.origin,gameMove.destination)) {
                return false;
            }
        }
        if(Math.abs(gameMove.origin.getX() - gameMove.destination.getX()) == Math.abs(gameMove.origin.getY() - gameMove.destination.getY())){
            if (!board.sameDiagonalNothingBetween(gameMove.origin,gameMove.destination)) {
                return false;
            }
        }
        //la piece est un pion
        if (gameMove.pieceAtOrigin.getType() == Piece.Type.PAWN && gameMove.pieceAtOrigin.owner.color == ChessColor.BLACK){
            //si on veut faire un pas de 2 mais qu'on a deja fait un mouvement avec le pion
            if (gameMove.origin.getY() != 6 && gameMove.origin.getY()-gameMove.destination.getY()==2){
                return false;
            }
            //ne peut avancer en diagonal que pour manger
            if(gameMove.origin.getX()-gameMove.destination.getX() != 0 && board.isEmptyCell(gameMove.destination)){
                return false;
            }
            //le pion ne peut que avancer
            if (gameMove.origin.getY()<gameMove.destination.getY()){
                return false;
            }
            //si il y a une piece devant lui il ne peut rien faire contrairemant aux autres pieces
            if(gameMove.origin.getX() == gameMove.destination.getX() && !board.isEmptyCell(gameMove.destination)){
                return false;
            }
        }
        if (gameMove.pieceAtOrigin.getType() == Piece.Type.PAWN && gameMove.pieceAtOrigin.owner.color == ChessColor.WHITE){
            //si on veut faire un pas de 2 mais qu'on a deja fait un mouvement avec le pion
            if (gameMove.destination.getY()-gameMove.origin.getY()==2){
                if (gameMove.origin.getY() != 1 ){
                    return false;
                }
            }
            //ne peut avancer en diagonal que pour manger
            if(gameMove.origin.getX()-gameMove.destination.getX() != 0 && board.isEmptyCell(gameMove.destination)){
                return false;
            }
            //le pion ne peut que avancer
            if (gameMove.origin.getY()>gameMove.destination.getY()){
                return false;
            }
            //si il y a une piece devant lui il ne peut rien faire contrairemant aux autres pieces
            if(gameMove.origin.getX() == gameMove.destination.getX() && !board.isEmptyCell(gameMove.destination)){
                return false;
            }
        }
        applyMove(gameMove);
        switchPlayer();
        if (isPrey(getOpponent(currentPlayer).getKing())){
            undo();
            return false;
        }
        undo();
        return true;
    }
    public void applyMove(Move move){
        Piece piece = board.getPiece(move.origin);
        //il faut quil mette le changement de priece dans le array
        history.push(move);

        board.emptyCell(move.origin);
        //change les coodoné de la piece
        if(piece != null) {
            piece.setPosition(move.destination);
            board.addPiece(piece);
        }

        //gere l'interface graphique place piece, remove piece, wait for
        ui.placePiece(move.pieceAtOrigin.getType(),move.pieceAtOrigin.getColor(), move.destination);
        ui.removePiece(move.origin);
    }

    public void switchPlayer(){
        if (currentPlayer == white){
            currentPlayer = black;
        }else currentPlayer = white;
    }

    public Player getOpponent(Player player){
        if(player == black){
            return white;
        }
	    return black ;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public boolean isPrey(Piece prey){
        if (prey == null) return false;
        //est ce que cette piece PEUT ETRE prise ou pas
        // recuperer toute les mouvement de sont opposant
        //si jamais le mouvement a pour destination la piece prey alors true
        for (Move move : getOpponent(prey.owner).getAllMoves(board)){
            if (move.destination.equals(prey.getPosition())&& isMovePlayable(move)){
                return true;
            }
        }
	    return false;
    }

    public boolean isCheck(Player player){
	    return isPrey(player.getKing());
    }

    public boolean isCheckMate(Player player){
        //on regarde si en bougant une piece il sort du check
        List<Move> allmoves = player.getAllMoves(board);
        for (Move move : allmoves){
            switchPlayer();
            if (isMovePlayable(move)){
                applyMove(move);
                if (!isCheck(player)){
                    undo();
                    switchPlayer();
                    return false;
                }
                undo();
                switchPlayer();
            }
        }
        return true;
    }
    
    public void determineWinner(int hit){
        if (white.getScore() > black.getScore() && hit>48){
            System.out.println("la partie est fini, les "+ white.getColor()+ " on gagné avec un score de "+ white.getScore()+ " contre "+ black.getScore());
        }
        if (white.getScore() < black.getScore() && hit>48) {
            System.out.println("la partie est fini, les " + black.getColor() + " on gagné avec un score de " + black.getScore() + " contre " + white.getScore());
        }
        if (black.isCheckMate){
            System.out.println("FIN DE LA PARTIE :\n les " + white.getColor()+ " on gagné par echec et mat");
        }
        if (white.isCheckMate){
            System.out.println("FIN DE LA PARTIE :\n les " + black.getColor()+ " on gagné par echec et mat");
        }
    }
    
    public void play(){
        int numberOfHits = 0;
        while(numberOfHits < 50){
            //si il ne reste que les rois au deux adversaires, fin de partie
            if (getBoard().getPieces().size() == 2){
                continue;
            }
            Move move = new Move(board,currentPlayer.getFromTo(this));
            if(!isMovePlayable(move)) continue;
            applyMove(move);
            //mettre le joueur adverse en check et tester directement si il est check mate
            if(isCheck(getOpponent(getCurrentPlayer()))){
                if (isCheckMate(getOpponent(getCurrentPlayer()))){
                    getOpponent(getCurrentPlayer()).setCheckMate();
                    determineWinner(numberOfHits);
                    break;
                }
                getOpponent(getCurrentPlayer()).setCheck();
                System.out.println(getOpponent(getCurrentPlayer()).getColor()+" is check !!!");
            }
            switchPlayer();
            if (move.pieceAtDestination!=null){
                getOpponent(getCurrentPlayer()).addToScore(move.pieceAtDestination.getValue());
            }
            numberOfHits ++;
        }
        if (numberOfHits < 50){
            determineWinner(numberOfHits);
        }
    }
}
