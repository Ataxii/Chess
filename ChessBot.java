import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChessBot extends Player{

    //resonnement du bot:
    //regarde tout les coup possible, sur cela, il regarde si il peut prendre des pieces, si il peut en prendre, il regarde si il perd
    //plus de point quil n'en gagne avec tout les coups de l'adversaire
    //si condition d'au dessus non validé, joue un coup aleatoire.

    public ChessBot(ChessColor color) {
        super(color);
    }

    @Override
    public FromTo getFromTo(GameUI ui) {
        //tout les mouvements quil peut jouer
        Player bot = this;
        Player humain = ui.getOpponent(bot);

        try{
            Thread.sleep(1500);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        Move bestMove = null;
        int bestScore = 0;
        List<Move> myMove = new ArrayList<>();
        for (Move move : bot.getAllMoves(ui.getBoard())){
            if (ui.isMovePlayable(move)){
                myMove.add(move);
            }
        }
        //voir si le bot nest pas deja en echec au quel cas il faut trouver un moyen precis de s'ensortir et non de faire
        // des coups aleatoires

        if(ui.isCheck(bot)){
            List<Move> allmoves = bot.getAllMoves(ui.getBoard());
            for (Move move : allmoves){
                ui.switchPlayer();
                if (ui.isMovePlayable(move)){
                    ui.applyMove(move);
                    if (!ui.isCheck(bot)){
                        ui.undo();
                        ui.switchPlayer();
                        return new FromTo(move.origin.getX(),move.origin.getY(),move.destination.getX(),move.destination.getY());
                    }
                    ui.undo();
                    ui.switchPlayer();
                }
            }

        }


        //pour chaque coup jouable de sa part on va voir le quel lui rapporte de plus de point, cest a dire si il joue le
        //coup est ce que l'adversaire peut lui prendre une piece qui vaut plus et du coup etre en negatif

        for (Move move : myMove){
            if(move.pieceAtDestination != null && move.pieceAtDestination.getValue() > bestScore){
                bestMove = move;
                bestScore = move.pieceAtDestination.getValue();
                //on regarde ici la reponse de l'adversaire
                ui.applyMove(bestMove);
               //avdversaire
                for (Move moveAdvers : humain.getAllMoves(ui.getBoard())){
                    if(ui.isMovePlayable(moveAdvers)){
                        if (moveAdvers.pieceAtDestination != null && moveAdvers.pieceAtDestination.getValue() > bestScore){
                            bestScore = 0;
                            bestMove = null;
                        }
                    }
                }
                ui.undo();
            }
        }
        if (bestScore>0){
            return new FromTo(bestMove.origin.getX(), bestMove.origin.getY(), bestMove.destination.getX(), bestMove.destination.getY());
        }
        //si aucun mouvement possible dans les 2 cas precedents, faire un mouvement aleatoire
        Random random = new Random();
        bestMove = myMove.get(random.nextInt(myMove.size()));
        return new FromTo(bestMove.origin.getX(),bestMove.origin.getY(),bestMove.destination.getX(),bestMove.destination.getY());
    }
}
