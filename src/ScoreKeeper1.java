/**
 * Array implementation of a two-team scoreboard.
 * @convention scores has one nonnegative entry per Team, in enum order
 * @correspondence this = (scores[HOME.ordinal()], scores[AWAY.ordinal()])
 */
public final class ScoreKeeper1 extends ScoreKeeperSecondary {
    /** Current points indexed by team. */
    private int[] scores;

    /** Constructs a new game with both scores at zero. */
    public ScoreKeeper1() {
        this.scores = new int[Team.values().length];
    }

    @Override
    public int score(Team team) {
        return this.scores[team.ordinal()];
    }

    @Override
    public void award(Team team, int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Points must be nonnegative");
        }
        this.scores[team.ordinal()] = Math.addExact(this.score(team), points);
    }

    @Override
    public ScoreKeeper newInstance() {
        return new ScoreKeeper1();
    }

    @Override
    public void clear() {
        this.scores = new int[Team.values().length];
    }

    @Override
    public void transferFrom(ScoreKeeper source) {
        if (!(source instanceof ScoreKeeper1 other) || source == this) {
            throw new IllegalArgumentException("Expected another ScoreKeeper1");
        }
        this.scores = other.scores;
        other.clear();
    }
}
