import java.io.*;
import java.util.Scanner;

public class TestChess{

	public static void main(String[] args) {
	    
	    boolean result;
	    boolean result1;
		boolean result3;
		boolean result2;
		boolean result4;
		Player black = new Human(new ChessUI(),ChessColor.BLACK);


		/* Test de déplacements autorisés selon les regles de pièces */
	    System.out.println("-----legal moves-----");

		// todo: faire des tests plus precis
		// test pour pion
		System.out.print("test pion : ");
	    result = testAuthorizedMove("boardConfigurationFiles/FullBoard.txt", new Coordinates(3,1), new Coordinates(3,2));
		result3 = testAuthorizedMove("boardConfigurationFiles/FullBoard.txt", new Coordinates(3, 1), new Coordinates(3, 4));
		if(result && !result3) System.out.println("pass"); else System.out.println("fail");


	    //test pour tour
	    System.out.print("test tour : ");
	    result = testAuthorizedMove("boardConfigurationFiles/FullBoard.txt", new Coordinates(0,0), new Coordinates(0,4));
		result3 = testAuthorizedMove("boardConfigurationFiles/FullBoard.txt", new Coordinates(0,0), new Coordinates(1,4));
		if(result && !result3) System.out.println("pass"); else System.out.println("fail");


		//test pour fou
		System.out.print("test fou : ");
		result = testAuthorizedMove("boardConfigurationFiles/FullBoard.txt", new Coordinates(2,0), new Coordinates(4,2));
		result3 = testAuthorizedMove("boardConfigurationFiles/FullBoard.txt", new Coordinates(2,0), new Coordinates(4,6));
		if(result && !result3) System.out.println("pass"); else System.out.println("fail");


		//test pour cavalier
		System.out.print("test cavalier : ");
		result = testAuthorizedMove("boardConfigurationFiles/FullBoard.txt", new Coordinates(1,0), new Coordinates(2,2));
		result3 = testAuthorizedMove("boardConfigurationFiles/FullBoard.txt", new Coordinates(1,0), new Coordinates(3,4));
		if(result && !result3) System.out.println("pass"); else System.out.println("fail");


		//test pour roi
		System.out.print("test roi : ");
		result = testAuthorizedMove("boardConfigurationFiles/FullBoard.txt", new Coordinates(3,0), new Coordinates(4,1));
		result3 = testAuthorizedMove("boardConfigurationFiles/FullBoard.txt", new Coordinates(3,0), new Coordinates(5,1));
		if(result && !result3) System.out.println("pass"); else System.out.println("fail");


		//test pour reine
		System.out.print("test reine : ");
		result = testAuthorizedMove("boardConfigurationFiles/FullBoard.txt", new Coordinates(4,0), new Coordinates(6,2));
		result3 = testAuthorizedMove("boardConfigurationFiles/FullBoard.txt", new Coordinates(4,0), new Coordinates(5,2));
		if(result && !result3) System.out.println("pass"); else System.out.println("fail");

	    //on va tester qu'avec la tour car les pieces utilise la meme fonction donc si ca marche pour la tour ca marche
		// pour tout les autres
	    /*  Test de déplacements legal sur l'échiquier actuel, selon les regles du jeu*/
	    System.out.println("-----playable moves-----");
	    System.out.print("test sur pion : ");
	    //prise du'une piece en diagonal et non devant et ne peut reculer et peut faire un pas de deux au debut
	    result = testPlayableMove("boardConfigurationFiles/testPlayableMove.txt",new Coordinates(0,1),new Coordinates(1,2));
		result3 = testPlayableMove("boardConfigurationFiles/testPlayableMove.txt",new Coordinates(0,1),new Coordinates(1,1));
		result2 = testPlayableMove("boardConfigurationFiles/testPlayableMove.txt",new Coordinates(0,1),new Coordinates(1,1));
		result1 = testPlayableMove("boardConfigurationFiles/testPlayableMove.txt",new Coordinates(3,1),new Coordinates(3,3));
		if(result && !result3 && !result2 && result1) System.out.println("pass"); else System.out.println("fail");

	    System.out.print("test sur tour et tout les autres: ");
	    //elle ne peut pas passer au dessus d'une autre piece, aller sur la meme case qu'une piece de sa couleur
		// et ne pas bouger et ne peut pas faire un deplcement qui met son roi en echec directement
		result2 = testPlayableMove("boardConfigurationFiles/testPlayableMove.txt",new Coordinates(0,2),new Coordinates(0,0));
		result1 = testPlayableMove("boardConfigurationFiles/testPlayableMove.txt",new Coordinates(0,2),new Coordinates(1,2));
		result3 = testPlayableMove("boardConfigurationFiles/testPlayableMove.txt",new Coordinates(0,2),new Coordinates(0,2));
		result = testPlayableMove("boardConfigurationFiles/testPlayableMove.txt",new Coordinates(0,2),new Coordinates(0,1));
		result4 = testPlayableMove("boardConfigurationFiles/testPlayableMove.txt",new Coordinates(3,7),new Coordinates(3,6));
		if(!result2 && result1 && !result && !result3 && !result4) System.out.println("pass"); else System.out.println("fail");


		System.out.print("test quelque chose entre debut et arrivé: ");
		result2 = testPlayableMove("boardConfigurationFiles/testPlayableMove.txt",new Coordinates(2,1),new Coordinates(0,3));
		result1 = testPlayableMove("boardConfigurationFiles/testPlayableMove.txt",new Coordinates(2,1),new Coordinates(4,1));
		result3 = testPlayableMove("boardConfigurationFiles/testPlayableMove.txt",new Coordinates(2,1),new Coordinates(2,4));
		if(!result2 && !result1 && !result3 ) System.out.println("pass"); else System.out.println("fail");

		/*  Tests de la mise en echec */
		System.out.println("-----mise en echec-----");
		result2 = testIsCheck("boardConfigurationFiles/TestEchec.txt", ChessColor.BLACK);
		if(result2) System.out.println("pass"); else System.out.println("fail");
		/*  Tests de la Echec et mat "isCheckMate()" */
		System.out.println("-----mise en echec et mat-----");
		result = testIsCheckMate("boardConfigurationFiles/TestIsCheckMate.txt", ChessColor.BLACK);
		if(result) System.out.println("pass"); else System.out.println("fail");
		/*  Tests pours le calcul des points en fin de partie */
    }

    public static boolean testAuthorizedMove(String filename, Coordinates origin, Coordinates destination) {
	ChessUI ui = new ChessUI();
	Board testBoard = new Board(filename, new Human(ui, ChessColor.WHITE), new Human(ui, ChessColor.BLACK));
	Piece testPiece = testBoard.getPiece(origin);

	if(testPiece == null) {
	    System.out.println("No Piece at :"+origin); 
	    return false;
	}
	return testPiece.isMoveAuthorized(testBoard, destination);
    }

    public static boolean testPlayableMove(String fileName, Coordinates origin, Coordinates destination) {    			
	ChessUI ui = new ChessUI();

	GameUI g = new GameUI(ui, fileName, new Human(ui, ChessColor.WHITE), new Human(ui, ChessColor.BLACK));

	Piece testPiece = g.getBoard().getPiece(origin);

	if(testPiece == null) {
	    System.out.println("No Piece at :"+origin); 
	    return false;
	}
	return g.isMovePlayable(new Move(g.getBoard(), origin, destination));
    }

    public static boolean testIsCheck(String fileName, ChessColor color) {
	ChessUI ui = new ChessUI();
	GameUI g = new GameUI(ui, fileName, new Human(ui, ChessColor.WHITE), new Human(ui, ChessColor.BLACK));
	return g.isCheck(g.getPlayer(color));
    }

    public static boolean testIsCheckMate(String fileName, ChessColor color) {
	ChessUI ui = new ChessUI();
	GameUI g = new GameUI(ui, fileName, new Human(ui, ChessColor.WHITE), new Human(ui, ChessColor.BLACK));
	return g.isCheck(g.getPlayer(color)) && g.isCheckMate(g.getPlayer(color));
    }
}

