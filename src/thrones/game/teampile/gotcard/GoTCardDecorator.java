package thrones.game.teampile.gotcard;

import thrones.game.gotdeck.Rank;
import thrones.game.gotdeck.Suit;

public abstract class GoTCardDecorator extends GoTCard {
    private GoTCard wrappee;
    public GoTCardDecorator(GoTCard wrappee) {
        this.wrappee = wrappee;
    }

    @Override
    public int getStackAttackRank() {
        return wrappee.getStackAttackRank();
    }

    @Override
    public int getStackDefenceRank() {
        return wrappee.getStackDefenceRank();
    }

    @Override
    public int getStackSize() {
        return wrappee.getStackSize();
    }

    @Override
    public Suit getSuit() {
        return wrappee.getSuit();
    }
    @Override
    public Rank getRank() {
        return wrappee.getRank();
    }

    public Rank getCharacterRank() {
        return wrappee.getCharacterRank();
    }

    public boolean canPlayMagic() {
        return true;
    }
}
