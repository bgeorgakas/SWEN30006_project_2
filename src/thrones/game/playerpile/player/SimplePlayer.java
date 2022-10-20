package thrones.game.playerpile.player;

import ch.aplu.jcardgame.Card;
import thrones.game.GameOfThrones;
import thrones.game.gotdeck.Suit;
import thrones.game.playerpile.PlayerPile;
import thrones.game.teampile.PileStack;
import thrones.game.teampile.TeamPile;

import java.util.List;

public class SimplePlayer extends Player {
    public SimplePlayer(PlayerPile playerPile, GameOfThrones gameOfThrones) {
        super(playerPile, gameOfThrones);
    }
    public void playCharacterCard() {
        List<Card> heartCards = getHand().getCardsWithSuit(Suit.HEARTS);
        int x = GameOfThrones.random.nextInt(heartCards.size());
        Card randomCard = heartCards.get(x);
        super.playCharacterCard(randomCard);
    }


    public void playEffectCard() {
        Card card = getHand().get(GameOfThrones.random.nextInt(getHand().getNumberOfCards()));
        TeamPile teamPile = getGame().getRandomTeamPile();

        if (!teamPile.isValidPlay(card)) {
            super.playEffectCard(null, null);
            return;
        }

        PileStack current = teamPile.getPileStack();
        PileStack added = teamPile.getPileStack().addCard(card, false);

        if (teamPile.equals(getGame().getPlayerTeamPile(getPlayerPile(), false)) && (current.compare(added) > 0)) {
            super.playEffectCard(card, teamPile);
        }
        else if (teamPile.equals(getGame().getPlayerTeamPile(getPlayerPile(), true)) && (current.compare(added) < 0)){
            super.playEffectCard(card, teamPile);
        } else {
            super.playEffectCard(null, null);
        }
    }
}
