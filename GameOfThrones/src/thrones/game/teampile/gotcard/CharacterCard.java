package thrones.game.teampile.gotcard;

import thrones.game.gotdeck.Rank;
import thrones.game.gotdeck.Suit;

public class CharacterCard extends GoTCard{
    private Suit suit;
    private Rank rank;

    public CharacterCard(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public int getStackAttackRank() {
        return rank.getRankValue();
    }

    public int getStackDefenceRank() {
        return rank.getRankValue();
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }
    public int getStackSize() {
        return 1;
    }

    public boolean canPlayMagic() {
        return false;
    }
    public Rank getCharacterRank() {
        return rank;
    }

}
