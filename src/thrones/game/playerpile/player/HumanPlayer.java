package thrones.game.playerpile.player;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.CardAdapter;
import ch.aplu.jcardgame.Hand;
import thrones.game.GameOfThrones;
import thrones.game.gotdeck.Suit;
import thrones.game.playerpile.PlayerPile;
import thrones.game.teampile.TeamPile;

public class HumanPlayer extends Player {

    private enum PlayMode {
        CHARACTER, EFFECT
    }

    private PlayMode playMode = null;
    private Card selectedCard = null;

    public HumanPlayer(PlayerPile playerPile, GameOfThrones gameOfThrones) {
        super(playerPile, gameOfThrones);
    }

    public void selectCard(Card card) {
        switch (playMode) {
            case CHARACTER :
                selectCharacterCard(card);
                break;
            case EFFECT:
                selectEffectCard(card);
                break;
        }
    }
    private void selectCharacterCard(Card card) {
        if (card != null & card.getSuit().equals(Suit.HEARTS)) {
            super.playCharacterCard(card);
            playMode = null;
        } else {
            getHand().setTouchEnabled(true);
        }
    }
    private void selectEffectCard(Card card) {
        if (card == null) {
            super.playEffectCard(null, null);
            playMode = null;
        }
        else if (!card.getSuit().equals(Suit.HEARTS)) {
            selectedCard = card;
            getGame().setStatusText("Selected: " + GameOfThrones.canonical(card) + ". Player" + getPlayerIndex() + " select a pile to play the card.");
            getGame().setPileTouchEnabled(true);
        } else {
            getHand().setTouchEnabled(true);
        }
    }

    private void selectPile(Card card) {
        TeamPile selectedPile = getGame().getSelectedPile(card);
        if (selectedPile.isValidPlay(selectedCard)) {
            super.playEffectCard(selectedCard, selectedPile);
            playMode = null;
        } else {
            getHand().setTouchEnabled(true);
        }

    }

    public void playCharacterCard() {
        playMode = PlayMode.CHARACTER;
        getHand().setTouchEnabled(true);
        while (playMode != null) {
            getGame().delay(100);
        }
    }

    public void playEffectCard() {
        playMode = PlayMode.EFFECT;
        getHand().setTouchEnabled(true);
        while (playMode != null) {
            getGame().delay(100);
        }
    }

    @Override
    public void init() {
        final Hand currentHand = getHand();
        currentHand.addCardListener(new CardAdapter() {
            public void leftDoubleClicked(Card card) {
                currentHand.setTouchEnabled(false);
                selectCard(card);
            }
            public void rightClicked(Card card) {
                currentHand.setTouchEnabled(false);
                selectCard(null);
            }
        });
        for (TeamPile pile : getGame().getTeamPiles()) {
            final GameOfThrones game = getGame();
            pile.getHand().addCardListener(new CardAdapter() {
                public void leftClicked(Card card) {
                    game.setPileTouchEnabled(false);
                    selectPile(card);
                }
            });
        }
    }

}
