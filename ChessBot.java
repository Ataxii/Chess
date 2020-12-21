public class ChessBot extends Player{

    //resonnement du bot:
    //regarde tout les coup possible, sur cela, il regarde si il peut prendre des pieces, si il peut en prendre, il regarde si il perd
    //plus de point quil b'en gagne avec le coup de l'adversaire
    //si condition d'auddessus non validé, joue un coup aleatoire.
    public ChessBot(ChessColor color) {
        super(color);
    }

    @Override
    public FromTo getFromTo() {
        return null;
    }
}
