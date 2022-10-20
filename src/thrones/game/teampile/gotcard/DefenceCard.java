package thrones.game.teampile.gotcard;

import thrones.game.gotdeck.Rank;
import thrones.game.gotdeck.Suit;

public class DefenceCard extends GoTCardDecorator {
    private Suit suit;
    private Rank rank;
    public DefenceCard(GoTCard wrappee, Suit suit, Rank rank) {
        super(wrappee);
        this.suit = suit;
        this.rank = rank;
    }

    public int getStackAttackRank() {
        return super.getStackAttackRank();
    }

    public int getStackDefenceRank() {
        if (getRank().equals(rank)) {
            return super.getStackDefenceRank() + 2*rank.getRankValue();
        } else {
            return super.getStackDefenceRank() + rank.getRankValue();
        }
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    public int getStackSize() {
        return super.getStackSize() + 1;
    }
}
