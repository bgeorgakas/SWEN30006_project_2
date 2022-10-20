package thrones.game.teampile.gotcard;

import thrones.game.gotdeck.Rank;
import thrones.game.gotdeck.Suit;

public abstract class GoTCard {
    public abstract Rank getRank();
    public abstract Suit getSuit();
    public abstract int getStackAttackRank();
    public abstract int getStackDefenceRank();
    public abstract int getStackSize();
    public abstract boolean canPlayMagic();
    public abstract Rank getCharacterRank();
}
