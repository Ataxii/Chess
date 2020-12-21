import java.io.*;
import java.util.Scanner;

public class TestChess{

	public static void main(String[] args) {
	    
	    boolean result;
		boolean result3;
		/* Test de déplacements autorisés selon les regles de pièces */
	    System.out.println("-----legal moves-----");

		// todo: faire des tests plus precis
		// test pour pion
		System.out.print("test pion : ");
	    result = testAuthorizedMove("boardConfigurationFiles/FullBoard.txt", new Coordinates(3,1), new Coordinates(3,2));
		result3 = testAuthorizedMove("boardConfigurationFiles/FullBoard.txt", new Coordinates(3, 1), new Coordinates(3, 4));
		if(result == true && result3 == false) System.out.println("pass"); else System.out.println("fail");


	    //test pour tour
	    System.out.print("test tour : ");
	    result = testAuthorizedMove("boardConfigurationFiles/FullBoard.txt", new Coordinates(0,0), new Coordinates(0,4));
		result3 = testAuthorizedMove("boardConfigurationFiles/FullBoard.txt", new Coordinates(0,0), new Coordinates(1,4));
		if(result == true && result3 == false) System.out.println("pass"); else System.out.println("fail");


		//test pour fou
		System.out.print("test fou : ");
		result = testAuthorizedMove("boardConfigurationFiles/FullBoard.txt", new Coordinates(2,0), new Coordinates(4,2));
		result3 = testAuthorizedMove("boardConfigurationFiles/FullBoard.txt", new Coordinates(2,0), new Coordinates(4,6));
		if(result == true && result3 == false) System.out.println("pass"); else System.out.println("fail");


		//test pour cavalier
		System.out.print("test cavalier : ");
		result = testAuthorizedMove("boardConfigurationFiles/FullBoard.txt", new Coordinates(1,0), new Coordinates(2,2));
		result3 = testAuthorizedMove("boardConfigurationFiles/FullBoard.txt", new Coordinates(1,0), new Coordinates(3,4));
		if(result == true && result3 == false) System.out.println("pass"); else System.out.println("fail");


		//test pour roi
		System.out.print("test roi : ");
		result = testAuthorizedMove("boardConfigurationFiles/FullBoard.txt", new Coordinates(3,0), new Coordinates(4,1));
		result3 = testAuthorizedMove("boardConfigurationFiles/FullBoard.txt", new Coordinates(3,0), new Coordinates(5,1));
		if(result == true && result3 == false) System.out.println("pass"); else System.out.println("fail");


		//test pour reine
		System.out.print("test reine : ");
		result = testAuthorizedMove("boardConfigurationFiles/FullBoard.txt", new Coordinates(4,0), new Coordinates(6,2));
		result3 = testAuthorizedMove("boardConfigurationFiles/FullBoard.txt", new Coordinates(4,0), new Coordinates(5,2));
		if(result == true && result3 == false) System.out.println("pass"); else System.out.println("fail");


	    
	    /*  Test de déplacements legal sur l'échiquier actuel, selon les regles du jeu*/
	    System.out.println("-----playable moves-----");
	    System.out.print("test sur pion blanc : ");
	    result = testPlayableMove("boardConfigurationFiles/FullBoard.txt",new Coordinates(3,1),new Coordinates(3,0));
		result3 = testPlayableMove("boardConfigurationFiles/FullBoard.txt",new Coordinates(3,1),new Coordinates(3,2));
		if(result == false && result3== true) System.out.println("pass"); else System.out.println("fail");

	    System.out.print("test 2 : ");
	    result = testPlayableMove("boardConfigurationFiles/FullBoard.txt",new Coordinates(3,0),new Coordinates(3,1));
	    if(result == true) System.out.println("pass"); else System.out.println("fail");

	    /*  Tests de la mise en echec */
	    
	    /*  Tests de la Echec et mat "isCheckMate()" */
	    
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

    public static boolean testIsCheck(String fileName, Player p) {    			
	ChessUI ui = new ChessUI();
	GameUI g = new GameUI(ui, fileName, new Human(ui, ChessColor.WHITE), new Human(ui, ChessColor.BLACK));
	return g.isCheck(p);
    }

    public static boolean testIsCheckMate(String fileName, Player p) {    			
	ChessUI ui = new ChessUI();
	GameUI g = new GameUI(ui, fileName, new Human(ui, ChessColor.WHITE), new Human(ui, ChessColor.BLACK));
	return g.isCheck(p) && g.isCheckMate(p);
    }

	
}

