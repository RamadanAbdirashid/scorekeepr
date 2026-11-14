import components.standard.Standard;

/**
 * A pair of nonnegative integer scores, one per team.
 * @mathmodel (home, away), each in [0, Integer.MAX_VALUE]
 * @initially both scores are zero
 */
public interface ScoreKeeperKernel extends Standard<ScoreKeeper> {
    /** Sides in a two-team game. */
    enum Team {
        /** Host team. */
        HOME,
        /** Visiting team. */
        AWAY
    }

    /**
     * Reads one side of the scoreboard.
     * @param team the side to read
     * @return points credited to the team
     * @requires team is non-null
     * @ensures score = this[team]
     */
    int score(Team team);

    /**
     * Credits points to one team.
     * @param team the side receiving points
     * @param points number of points to add
     * @updates this
     * @requires team is non-null and 0 <= points <= MAX_VALUE - this[team]
     * @ensures this[team] = #this[team] + points and the other score is unchanged
     */
    void award(Team team, int points);
}
