package thrones.game.playerpile.player;

import ch.aplu.jcardgame.Card;
import thrones.game.GameOfThrones;
import thrones.game.gotdeck.Rank;
import thrones.game.gotdeck.Suit;
import thrones.game.playerpile.PlayerPile;
import thrones.game.teampile.BattleOutcome;
import thrones.game.teampile.PileStack;
import thrones.game.teampile.TeamPile;

import java.util.ArrayList;
import java.util.List;

public class SmartPlayer extends Player {
    public SmartPlayer(PlayerPile playerPile, GameOfThrones gameOfThrones) {
        super(playerPile, gameOfThrones);
    }
    public void playCharacterCard() {
        List<Card> heartCards = getHand().getCardsWithSuit(Suit.HEARTS);
        int x = GameOfThrones.random.nextInt(heartCards.size());
        Card randomCard = heartCards.get(x);
        super.playCharacterCard(randomCard);
    }

    private boolean containsRank(ArrayList<Card> cards, Rank rank) {
        for (Card card : cards) {
            if (card.getRank().equals(rank)) {
                return true;
            }
        }
        return false;
    }

    private BattleOutcome battle(PileStack teamStack, PileStack oppositionStack) {
        BattleOutcome attackingOutcome = teamStack.battleRound(oppositionStack);
        BattleOutcome defendingOutcome = oppositionStack.battleRound(teamStack);
        return new BattleOutcome(attackingOutcome.attackingScore + defendingOutcome.defendingScore,
                attackingOutcome.defendingScore + defendingOutcome.attackingScore,0);
    }
    public void playEffectCard() {
        ArrayList<Card> playableCards = new ArrayList<>(), diamonds = new ArrayList<>();
        TeamPile teamPile = getGame().getPlayerTeamPile(getPlayerPile(), false);
        TeamPile oppositionPile = getGame().getPlayerTeamPile(getPlayerPile(), true);
        PileStack teamStack = teamPile.getPileStack();
        PileStack oppositionStack = oppositionPile.getPileStack();

        diamonds.addAll(getHand().getCardsWithSuit(Suit.DIAMONDS));
        diamonds.addAll(oppositionPile.getHand().getCardsWithSuit(Suit.DIAMONDS));
        diamonds.addAll(teamPile.getHand().getCardsWithSuit(Suit.DIAMONDS));

        playableCards.addAll(getHand().getCardsWithSuit(Suit.DIAMONDS));

        for (Suit suit : new Suit[] {Suit.CLUBS, Suit.SPADES}) {
            for (Card card : getHand().getCardsWithSuit(suit)) {
                if (containsRank(diamonds, (Rank) card.getRank()))
                    playableCards.add(card);
            }
        }

        BattleOutcome currentNetOutcome = battle(teamStack, oppositionStack);
        for (Card card : playableCards) {
            if (teamPile.isValidPlay(card) &&
                    currentNetOutcome.compare(battle(teamStack.addCard(card, false), oppositionStack)) > 0) {
                teamPile.playEffectCard(card);
                return;
            }
            if (oppositionPile.isValidPlay(card) &&
                    currentNetOutcome.compare(battle(teamStack, oppositionStack.addCard(card, false))) > 0) {
                oppositionPile.playEffectCard(card);
                return;
            }
        }

        super.playEffectCard(null,null);
    }
}
