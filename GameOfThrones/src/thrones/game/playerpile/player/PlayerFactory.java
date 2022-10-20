package thrones.game.playerpile.player;

import thrones.game.GameOfThrones;
import thrones.game.playerpile.PlayerPile;

public class PlayerFactory {
    private static final PlayerFactory playerFactory = new PlayerFactory();
    public static PlayerFactory getInstance() {
        return playerFactory;
    }

    public Player getPlayer(String playerType, PlayerPile playerPile, GameOfThrones gameOfThrones) {
        if (playerType.equals("random")) {
            return new RandomPlayer(playerPile, gameOfThrones);
        } else if (playerType.equals("human")){
            return new HumanPlayer(playerPile, gameOfThrones);
        } else if (playerType.equals("simple")) {
            return new SimplePlayer(playerPile, gameOfThrones);
        } else if (playerType.equals("smart")){
            return new SmartPlayer(playerPile, gameOfThrones);
        }
        assert false : "Invalid player type in properties file.";
        return null;
    }
}
