package thrones.game.teampile;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import ch.aplu.jcardgame.RowLayout;
import ch.aplu.jgamegrid.Location;
import ch.aplu.jgamegrid.TextActor;
import thrones.game.GameOfThrones;
import thrones.game.teampile.gotcard.*;
import thrones.game.gotdeck.Rank;
import thrones.game.gotdeck.Suit;
import thrones.game.utils.BrokeRuleException;

import java.awt.*;

public class TeamPile {
    private String scoreText;
    private String name;
    private int pileIndex = 0;
    private TextActor textActor;
    private GameOfThrones game;
    private RowLayout layout;
    private Location handLocation;
    private Location scoreLocation;
    private final Hand hand;
    private PileStack pileStack = null;
    public TeamPile(int pileIndex, String name, Location handLocation, Location scoreLocation, GameOfThrones gameOfThrones) {
        this.pileIndex = pileIndex;
        this.handLocation = handLocation;
        this.scoreLocation = scoreLocation;
        this.game = gameOfThrones;
        this.name = name;
        this.scoreText = name + "Attack: 0 - Defence: 0";
        this.layout = new RowLayout(handLocation, 8*game.pileWidth);

        textActor = new TextActor(scoreText, Color.WHITE, gameOfThrones.bgColor, gameOfThrones.smallFont);
        gameOfThrones.addActor(textActor, scoreLocation);
        hand = new Hand(game.getGoTDeck().getDeck());
    }


    public void reset() {
        hand.removeAll(true);
        hand.setView(game, layout);
        hand.draw();
        updatePileRank();
    }

    private void updatePileRank() {
        String text;
        game.removeActor(textActor);
        if (pileStack == null) {
            text = name + " Attack: 0 - Defence: 0";
        } else {
            text = name + " Attack: " + pileStack.getAttackRank() + " - Defence: " + pileStack.getDefenceRank();
        }
        textActor = new TextActor(text, Color.WHITE, game.bgColor, game.smallFont);
        game.addActor(textActor, scoreLocation);
    }

    public Hand getHand() {
        return hand;
    }
    public int getPileIndex() {
        return pileIndex;
    }
    public int getAttackRank() {
        return pileStack.getAttackRank();
    }
    public int getDefenceRank() {
        return pileStack.getDefenceRank();
    }

    public void playCharacterCard(Card card) {
        card.transfer(hand, true);
        pileStack = new PileStack(card);
        updatePileRank();
    }

    public void playEffectCard(Card card) {
        card.transfer(hand, true);
        pileStack.addCard(card);
        updatePileRank();
    }

    public boolean isValidPlay(Card card) {
        if (pileStack == null) {
            return card.getSuit().equals(Suit.HEARTS);
        } else {
            if (pileStack.getSize() == 1) {
                return (card.getSuit().equals(Suit.CLUBS) || card.getSuit().equals(Suit.SPADES));
            }

            return (!card.getSuit().equals(Suit.HEARTS));
        }
    }

    public PileStack getPileStack() {
        return pileStack;
    }

    public boolean equals(TeamPile teamPile) {
        return pileIndex == teamPile.getPileIndex();
    }
}
