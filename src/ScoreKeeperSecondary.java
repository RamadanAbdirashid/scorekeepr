/** Operations shared by all implementations, expressed in terms of score(). */
public abstract class ScoreKeeperSecondary implements ScoreKeeper {
    @Override
    public final boolean isTied() {
        return this.margin(Team.HOME) == 0;
    }

    @Override
    public final int margin(Team team) {
        Team opponent = Team.HOME;
        if (team == Team.HOME) {
            opponent = Team.AWAY;
        }
        return this.score(team) - this.score(opponent);
    }

    @Override
    public final boolean equals(Object other) {
        return other instanceof ScoreKeeper board
                && this.score(Team.HOME) == board.score(Team.HOME)
                && this.score(Team.AWAY) == board.score(Team.AWAY);
    }

    @Override
    public final int hashCode() {
        return java.util.Objects.hash(this.score(Team.HOME), this.score(Team.AWAY));
    }

    @Override
    public final String toString() {
        return "Home " + this.score(Team.HOME) + " - Away " + this.score(Team.AWAY);
    }
}
