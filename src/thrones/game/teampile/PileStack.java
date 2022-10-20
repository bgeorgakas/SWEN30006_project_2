package thrones.game.teampile;

import ch.aplu.jcardgame.Card;
import thrones.game.gotdeck.Rank;
import thrones.game.gotdeck.Suit;
import thrones.game.teampile.gotcard.CharacterCard;
import thrones.game.teampile.gotcard.GoTCard;
import thrones.game.teampile.gotcard.GoTCardFactory;

public class PileStack {
    private GoTCard stack;
    private int attackRank, defenceRank;
    public PileStack(GoTCard stack) {
        this.stack = stack;
        attackRank = stack.getStackAttackRank();
        defenceRank = stack.getStackDefenceRank();
    }

    public PileStack(Card card) {
        stack = new CharacterCard((Suit) card.getSuit(), (Rank) card.getRank());
        attackRank = stack.getStackAttackRank();
        defenceRank = stack.getStackDefenceRank();
    }

    public void addCard(Card card) {
        stack = GoTCardFactory.getInstance().addCard(stack, card);
        attackRank = stack.getStackAttackRank();
        defenceRank = stack.getStackDefenceRank();
    }
    public PileStack addCard(Card card, boolean inPlace) {
        if (inPlace) {
            addCard(card);
            return null;
        } else {
            PileStack newPileStack = new PileStack(stack);
            newPileStack.addCard(card);
            return newPileStack;
        }
    }

    public int getSize() {
        if (stack == null) {
            return 0;
        }
        return stack.getStackSize();
    }

    public int getAttackRank() {
        return attackRank;
    }

    public int getDefenceRank() {
        return defenceRank;
    }

    public int compare(PileStack pileStack) {
        if (attackRank < pileStack.attackRank | defenceRank < pileStack.defenceRank) {
            return 1;
        } else if (attackRank > pileStack.attackRank | defenceRank > pileStack.defenceRank){
            return -1;
        }
        return 0;
    }

    public BattleOutcome battleRound(PileStack pileStack) {
        int score = pileStack.stack.getCharacterRank().getRankValue();
        if (attackRank > pileStack.defenceRank) {
            return new BattleOutcome(score, 0, 1);
        } else if (attackRank < pileStack.defenceRank){
            return new BattleOutcome(0, score, -1);
        }
        return new BattleOutcome(0,0,0);
    }

    public String toString() {
        return "Attack: " + attackRank + " ,Defence: " + defenceRank;
    }
}
