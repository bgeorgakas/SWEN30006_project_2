package thrones.game;

// Oh_Heaven.java

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import thrones.game.gotdeck.GoTDeck;
import thrones.game.teampile.BattleOutcome;
import thrones.game.teampile.TeamPile;
import thrones.game.gotdeck.Rank;
import thrones.game.gotdeck.Suit;
import thrones.game.playerpile.PlayerPile;
import thrones.game.utils.PropertiesLoader;

import java.awt.Font;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class GameOfThrones extends CardGame {
    /*
    Canonical String representations of Suit, Rank, Card, and Hand
    */
    public static String canonical(Suit s) { return s.toString().substring(0, 1); }

    public static String canonical(Rank r) {
        switch (r) {
            case KING: case QUEEN: case JACK: case TEN:
                return r.toString().substring(0, 1);
            default:
                return String.valueOf(r.getRankValue());
        }
    }

    public static String canonical(Card c) { return canonical((Rank) c.getRank()) + canonical((Suit) c.getSuit()); }

    public static String canonical(Hand h) {
        return "[" + h.getCardList().stream().map(GameOfThrones::canonical).collect(Collectors.joining(",")) + "]";
    }
    static public int seed;
    public static Random random;


    private final String version = "1.0";
    public final int nbPlayers = 4;
    public final int nbTeams = 2;
	public final int nbPlays = 6;
    public final int handWidth = 400;
    public final int pileWidth = 40;
    private final Location[] handLocations = {
            new Location(350, 625),
            new Location(75, 350),
            new Location(350, 75),
            new Location(625, 350)
    };

    private final Location[] scoreLocations = {
            new Location(575, 675),
            new Location(25, 575),
            new Location(25, 25),
            new Location(575, 125)
    };
    private final Location[] pileLocations = {
            new Location(350, 280),
            new Location(350, 430)
    };
    private final Location[] pileStatusLocations = {
            new Location(250, 200),
            new Location(250, 520)
    };

    private final int watchingTime;


    private int startingPlayer;

    public static final Font bigFont = new Font("Arial", Font.BOLD, 36);
    public static final Font smallFont = new Font("Arial", Font.PLAIN, 10);
    String[] playerTypes = new String[nbPlayers];

    private PlayerPile[] playerPiles = new PlayerPile[nbPlayers];
    private int[] scores = {0, 0};
    private final String[] teamNames = { "[Players 0 & 2]", "[Players 1 & 3]"};
    private TeamPile[] playerTeamPiles = new TeamPile[nbPlayers], teamPiles = new TeamPile[nbTeams];

    private GoTDeck goTDeck = new GoTDeck();
    public GoTDeck getGoTDeck() {return goTDeck;}

    public TeamPile getPlayerTeamPile(PlayerPile playerPile, boolean isOpposition) {
        if (isOpposition) {
            return playerTeamPiles[playerPile.getPlayerIndex()];
        } else {
            return playerTeamPiles[(playerPile.getPlayerIndex() + 1)%nbPlayers];
        }
    }

    public void setPileTouchEnabled(boolean isEnabled) {
        for (TeamPile teamPile : teamPiles) {
            teamPile.getHand().setTouchEnabled(isEnabled);
        }
    }
    public TeamPile getSelectedPile(Card card) {
        for (TeamPile teamPile : teamPiles) {
            if (teamPile.getHand().contains(card)) {
                return teamPile;
            }
        }
        return null;
    }
    public TeamPile[] getTeamPiles() {return teamPiles;}
    public TeamPile getRandomTeamPile() {
        return teamPiles[random.nextInt(2)];
    }
    private int currentPlayerIndex;

    private void nextStartingPlayer() {
        startingPlayer  = (startingPlayer + 1) % nbPlayers;
    }
    private void nextPlayer() {
        currentPlayerIndex  = (currentPlayerIndex + 1) % nbPlayers;
    }
    private void setupGame() {
        for (int i = 0; i < nbPlayers; i++) {
            playerPiles[i] = new PlayerPile(i, playerTypes[i], handLocations[i], scoreLocations[i], 90 * i, this);
        }

        for (int i = 0; i < nbTeams; i++) {
            teamPiles[i] = new TeamPile(i, teamNames[i], pileLocations[i], pileStatusLocations[i], this);
        }
        playerTeamPiles = new TeamPile[] {teamPiles[0], teamPiles[1], teamPiles[0], teamPiles[1]};

        goTDeck.dealingOut(playerPiles);

        for (int i = 0; i < nbPlayers; i++) {
            playerPiles[i].setView();
            playerPiles[i].draw();
        }
    }

    private void play() {
        for (TeamPile teamPile : teamPiles) {
            teamPile.reset();
        }

        currentPlayerIndex = startingPlayer;

        for (int i = 0; i < 2 ; i++) {
            while (playerPiles[currentPlayerIndex].getHand().getNumberOfCardsWithSuit(Suit.HEARTS) == 0) {
                nextPlayer();
            }
            setStatusText("Player " + currentPlayerIndex + " select a Heart card to play");
            playerPiles[currentPlayerIndex].playCharacterCard();
            nextPlayer();
        }

        for (int i = 0; i < nbPlayers * 3 - 2; i ++) {

            setStatusText("Player" + currentPlayerIndex + " select a non-Heart card to play.");
            playerPiles[currentPlayerIndex].playEffectCard();
            nextPlayer();
        }
    }

    public void score() {
        for (int i = 0; i < nbTeams; i++) {
            System.out.println("piles[" + i + "]: " + canonical(teamPiles[i].getHand()));
            System.out.println("piles["+ i +"] is " + "Attack: " + teamPiles[i].getAttackRank() + " - Defence: " + teamPiles[i].getDefenceRank());
        }
        battle(0, 1);
        battle(1,0);

        System.out.println(teamNames[0] +" score = " + scores[0] + "; " + teamNames[1] + " score = " + scores[1]);
        playerPiles[0].setScore(scores[0]);
        playerPiles[1].setScore(scores[1]);
        playerPiles[2].setScore(scores[0]);
        playerPiles[3].setScore(scores[1]);
    }
    private void battle(int attackingIndex, int defendingIndex) {
        BattleOutcome outcome;
        outcome = teamPiles[attackingIndex].getPileStack().battleRound(teamPiles[defendingIndex].getPileStack());

        System.out.print("Character "+ attackingIndex +" attack on character " + defendingIndex);
        if (outcome.outcome > 0) {
            System.out.println(" succeeded.");
            scores[attackingIndex] += outcome.attackingScore;
        } else if (teamPiles[attackingIndex].getAttackRank() < teamPiles[defendingIndex].getDefenceRank()) {
            System.out.println(" failed.");
            scores[defendingIndex] += outcome.defendingScore;
        }
    }


    public GameOfThrones(Properties properties) {
        super(700, 700, 30);

        seed = Integer.parseInt(properties.getProperty("seed", "130006"));
        random = new Random(seed);

        startingPlayer = random.nextInt(nbPlayers);
        for (int i = 0; i < nbPlayers; i++) {
            playerTypes[i] = properties.getProperty("players."+i, "random");
        }
        watchingTime = Integer.parseInt(properties.getProperty("watchingTime", "5000"));

        setTitle("Game of Thrones (V" + version + ") Constructed for UofM SWEN30006 with JGameGrid (www.aplu.ch)");
        setStatusText("Initializing...");

        setupGame();
        setStatusText("");
        for (int i = 0; i < nbPlays; i++) {
            play();
            score();
            nextStartingPlayer();
            delay(watchingTime);
        }

        String result;
        if (scores[0] > scores[1]) {
            result = "Result: " + teamNames[0] + " won.";
        } else if (scores[1] > scores[0]) {
            result = "Result: " + teamNames[1] + " won.";
        } else {
            result = "Draw.";
        }
        setStatusText(result);
        System.out.println(result);
    }

    public static void main(String[] args) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        String propertiesPath = "properties\\original.properties";

        if (args != null && args.length > 0) {
            propertiesPath = args[0];
        }

        //GameOfThrones.seed = 130006;
        new GameOfThrones(PropertiesLoader.loadPropertiesFile(propertiesPath));
    }

}
