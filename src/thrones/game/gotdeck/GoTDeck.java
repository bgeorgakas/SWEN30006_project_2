package thrones.game.gotdeck;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Deck;
import ch.aplu.jcardgame.Hand;
import thrones.game.GameOfThrones;
import thrones.game.playerpile.PlayerPile;

import java.util.List;

public class GoTDeck {
    private int nbCardsPerPlayer = 9;
    private Deck deck = new Deck(Suit.values(), Rank.values(), "cover");

    public void dealingOut(PlayerPile players[]) {
        Hand pack = deck.toHand(false);

        assert pack.getNumberOfCards() == 52 : " Starting pack is not 52 cards.";
        // Remove 4 Aces
        List<Card> aceCards = pack.getCardsWithRank(Rank.ACE);
        for (Card card : aceCards) {
            card.removeFromHand(false);
        }
        assert pack.getNumberOfCards() == 48 : " Pack without aces is not 48 cards.";

        assert players.length == 4: " Number of players must be 4";

        Hand[] hands = new Hand[players.length];
        for (int i = 0; i < players.length; i++) {
            hands[i] = new Hand(deck);
        }


        for (int i = 0; i < players.length; i++) {
            for (int j = 0; j < 3; j++) {
                List<Card> heartCards = pack.getCardsWithSuit(Suit.HEARTS);
                int x = GameOfThrones.random.nextInt(heartCards.size());
                Card randomCard = heartCards.get(x);
                randomCard.removeFromHand(false);
                hands[i].insert(randomCard, false);
            }
        }
        assert pack.getNumberOfCards() == 36 : " Pack without aces and hearts is not 36 cards.";


        for (int i = 0; i < nbCardsPerPlayer; i++) {
            for (int j = 0; j < players.length; j++) {
                assert !pack.isEmpty() : " Pack has prematurely run out of cards.";
                int x = GameOfThrones.random.nextInt(pack.getNumberOfCards());
                Card dealt = pack.get(x);
                dealt.removeFromHand(false);
                hands[j].insert(dealt, false);
            }
        }


        for (int i = 0; i < players.length; i ++) {
            assert hands[i].getNumberOfCards() == 12 : " Hand does not have twelve cards.";
            hands[i].sort(Hand.SortType.SUITPRIORITY, true);
            players[i].setHand(hands[i]);
        }
    }

    public Deck getDeck() {
        return deck;
    }
}
