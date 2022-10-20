package thrones.game.teampile;

public class BattleOutcome {
    public int attackingScore, defendingScore, outcome;
    public BattleOutcome(int attackingScore, int defendingScore, int outcome) {
        this.attackingScore = attackingScore;
        this.defendingScore = defendingScore;
        this.outcome = outcome;
    }


    public int compare(BattleOutcome outcome) {
        int diff = (outcome.attackingScore - attackingScore) - (outcome.defendingScore - defendingScore);
        if (diff > 0) {
            return 1;
        }
        if (diff < 0) {
            return -1;
        }
        return 0;
    }

    public String toString() {
        return "attackingScore: " + attackingScore + " defendingScore: " + defendingScore + " outcome: " + outcome;
    }
}
