/** Summarizes a season from recorded pairs of home and away scores. */
public final class SeasonReport {
    /** Each row contains the final home and away scores for one game. */
    private static final int[][] GAMES = {{3, 1}, {2, 2}, {0, 1}, {4, 2}};

    /** Prevents instantiation of the reporting entry point. */
    private SeasonReport() {
    }

    /**
     * Prints the season record from the home team's perspective.
     * @param args unused command-line arguments
     */
    public static void main(String[] args) {

        int homeWins = 0;
        int ties = 0;
        ScoreKeeper board = new ScoreKeeper1();
        for (int[] game : GAMES) {
            board.clear();
            board.award(ScoreKeeper.Team.HOME, game[0]);
            board.award(ScoreKeeper.Team.AWAY, game[1]);
            if (board.isTied()) {
                ties++;
            } else if (board.margin(ScoreKeeper.Team.HOME) > 0) {
                homeWins++;
            }
        }
        System.out.printf("Home record (W-L-T): %d-%d-%d%n",
                homeWins, GAMES.length - homeWins - ties, ties);
    }
}
