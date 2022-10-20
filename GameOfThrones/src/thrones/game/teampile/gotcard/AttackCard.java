package thrones.game.teampile.gotcard;

import thrones.game.gotdeck.Rank;
import thrones.game.gotdeck.Suit;

public class AttackCard extends GoTCardDecorator {
    private Suit suit;
    private Rank rank;
    public AttackCard(GoTCard wrappee, Suit suit, Rank rank) {
        super(wrappee);
        this.suit = suit;
        this.rank = rank;
    }

    public int getStackAttackRank() {
        if (getRank().equals(rank)) {
            return super.getStackAttackRank() + 2*rank.getRankValue();
        } else {
            return super.getStackAttackRank() + rank.getRankValue();
        }
    }

    public int getStackDefenceRank() {
        return super.getStackDefenceRank();
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
