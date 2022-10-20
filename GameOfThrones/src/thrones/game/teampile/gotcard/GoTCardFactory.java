package thrones.game.teampile.gotcard;

import ch.aplu.jcardgame.Card;
import thrones.game.gotdeck.Rank;
import thrones.game.gotdeck.Suit;
import thrones.game.utils.BrokeRuleException;

public class GoTCardFactory {
    private static GoTCardFactory goTCardFactory = new GoTCardFactory();

    public static GoTCardFactory getInstance() {
        return goTCardFactory;
    }

    public GoTCard addCard(GoTCard pileStack, Card card) {
        switch((Suit) card.getSuit()) {
            case CLUBS:
                return new AttackCard(pileStack, (Suit) card.getSuit(), (Rank) card.getRank());
            case SPADES:
                return new DefenceCard(pileStack, (Suit) card.getSuit(), (Rank) card.getRank());
            case DIAMONDS:
                try {
                    if (!pileStack.canPlayMagic()) {
                        throw new BrokeRuleException("Cannot play magic card");
                    }
                    return new MagicCard(pileStack, (Suit) card.getSuit(), (Rank) card.getRank());
                } catch (BrokeRuleException e) {
                    e.printStackTrace();
                }
            case HEARTS:
                assert false : "Cannot add heart to non empty pile.";
        }
        return null;
    }

}
