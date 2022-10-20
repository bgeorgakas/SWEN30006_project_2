package thrones.game.playerpile.player;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import thrones.game.GameOfThrones;
import thrones.game.teampile.TeamPile;
import thrones.game.playerpile.PlayerPile;

public abstract class Player {
    private PlayerPile playerPile;
    private GameOfThrones gameOfThrones;
    public Player(PlayerPile playerPile, GameOfThrones gameOfThrones) {
        this.playerPile = playerPile;
        this.gameOfThrones = gameOfThrones;
    }

    public GameOfThrones getGame() {
        return gameOfThrones;
    }

    public Hand getHand() {
        return playerPile.getHand();
    }
    public PlayerPile getPlayerPile() {
        return playerPile;
    }
    public int getPlayerIndex() {
        return playerPile.getPlayerIndex();
    }
    public abstract void playCharacterCard();
    public abstract void playEffectCard();
    public void playCharacterCard(Card card) {
        TeamPile teamPile = gameOfThrones.getPlayerTeamPile(playerPile, false);
        System.out.println("Player " + playerPile.getPlayerIndex() + " plays " + GameOfThrones.canonical(card) + " on pile " + teamPile.getPileIndex());
        teamPile.playCharacterCard(card);
    }
    public void playEffectCard(Card card, TeamPile teamPile) {
        if (card == null) {
            System.out.println("Player " + playerPile.getPlayerIndex() + " passes.");
        }
        else {
            System.out.println("Player " + playerPile.getPlayerIndex() + " plays " + GameOfThrones.canonical(card) + " on pile " + teamPile.getPileIndex());
            teamPile.playEffectCard(card);
        }
    }

    public void init() {}
}
