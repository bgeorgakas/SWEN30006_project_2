package thrones.game.playerpile.player;

import ch.aplu.jcardgame.Card;
import thrones.game.GameOfThrones;
import thrones.game.teampile.TeamPile;
import thrones.game.gotdeck.Suit;
import thrones.game.playerpile.PlayerPile;

import java.util.List;

public class RandomPlayer extends Player {
    public RandomPlayer(PlayerPile playerPile, GameOfThrones gameOfThrones) {
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
            card = null;
        }

        super.playEffectCard(card, teamPile);
    }
}
