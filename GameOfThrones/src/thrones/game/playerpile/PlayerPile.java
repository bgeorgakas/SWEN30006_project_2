package thrones.game.playerpile;

import ch.aplu.jcardgame.Hand;
import ch.aplu.jcardgame.RowLayout;
import ch.aplu.jgamegrid.Location;
import ch.aplu.jgamegrid.TextActor;
import thrones.game.GameOfThrones;
import thrones.game.gotdeck.Suit;
import thrones.game.playerpile.player.Player;
import thrones.game.playerpile.player.PlayerFactory;

import java.awt.*;

public class PlayerPile {
    private String scoreText;
    private int playerIndex;
    private Hand hand;
    private RowLayout layout;
    private TextActor textActor;
    private GameOfThrones game;
    private Location handLocation;
    private Location scoreLocation;
    private Player player;
    public PlayerPile(int playerIndex, String playerType, Location handLocation, Location scoreLocation, int rotationAngle, GameOfThrones gameOfThrones) {
        this.handLocation = handLocation;
        this.scoreLocation = scoreLocation;
        this.game = gameOfThrones;
        this.playerIndex = playerIndex;
        this.scoreText = "P" + playerIndex + "-" + 0;
        this.layout = new RowLayout(handLocation, game.handWidth);
        this.player = PlayerFactory.getInstance().getPlayer(playerType, this, gameOfThrones);

        textActor = new TextActor(scoreText, Color.WHITE, gameOfThrones.bgColor, gameOfThrones.bigFont);
        gameOfThrones.addActor(textActor, scoreLocation);
        layout.setRotationAngle(rotationAngle);
    }
    public int getPlayerIndex() {
        return playerIndex;
    }
    public Hand getHand() {
        return hand;
    }

    public GameOfThrones getGame() {
        return game;
    }
    public void setHand(Hand hand) {
        System.out.println("hands[" + playerIndex + "]: " + GameOfThrones.canonical(hand));
        this.hand = hand;
        player.init();
    }
    public void setView() {
        hand.setView(game, layout);
    }

    public void setScore(int score) {
        game.removeActor(textActor);
        textActor = new TextActor("P" + playerIndex + "-" + score, Color.WHITE, game.bgColor, game.bigFont);
        game.addActor(textActor, scoreLocation);
    }
    public void draw() {
        hand.draw();
    }
    public boolean hasCharacterCard() {
        return hand.getNumberOfCardsWithSuit(Suit.HEARTS) > 0;
    }
    public void playCharacterCard() {
        player.playCharacterCard();
    }
    public void playEffectCard() {
        if (hand.getNumberOfCards() == 0) {
            System.out.println("Player " + playerIndex + " passes.");
            return;
        }
        player.playEffectCard();
    }
}
