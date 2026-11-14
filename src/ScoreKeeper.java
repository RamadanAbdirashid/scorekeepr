/** A scoreboard that can compare the two teams. */
public interface ScoreKeeper extends ScoreKeeperKernel {
    /**
     * Compares the two scores.
     * @return whether the game is tied
     * @ensures isTied = (home = away)
     */
    boolean isTied();

    /**
     * Computes the lead from one side's perspective.
     * @param team side whose lead is requested
     * @return signed lead in points
     * @requires team is non-null
     * @ensures margin = this[team] - this[opponent]
     */
    int margin(Team team);
}
