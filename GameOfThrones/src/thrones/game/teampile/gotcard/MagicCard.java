package thrones.game.teampile.gotcard;

import thrones.game.gotdeck.Rank;
import thrones.game.gotdeck.Suit;

public class MagicCard extends GoTCardDecorator {
    private Suit suit;
    private Rank rank;
    public MagicCard(GoTCard wrappee, Suit suit, Rank rank) {
        super(wrappee);
        this.suit = suit;
        this.rank = rank;
    }

    public int getStackAttackRank() {
        if (getSuit().equals(Suit.CLUBS)) {
            if (getRank().equals(rank)) {
                return super.getStackAttackRank() - 2 * rank.getRankValue();
            }
            return super.getStackAttackRank() -  rank.getRankValue();
        }
        return super.getStackAttackRank();
    }

    public int getStackDefenceRank() {
        if (getSuit().equals(Suit.SPADES)) {
            if (getRank().equals(rank)) {
                return super.getStackDefenceRank() - 2 * rank.getRankValue();
            }
            return super.getStackDefenceRank() - rank.getRankValue();
        }
        return super.getStackDefenceRank();
    }

    public Suit getSuit() {
        return super.getSuit();
    }

    public Rank getRank() {
        return rank;
    }

    public int getStackSize() {
        return super.getStackSize() + 1;
    }
}
