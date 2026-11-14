import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

/** Contract checks using an initially empty board per invocation. */
class ScoreKeeperTest {
    /** Board under test. */
    private final ScoreKeeper board = new ScoreKeeper1();
    /** Host side. */
    private static final ScoreKeeper.Team HOME = ScoreKeeper.Team.HOME;
    /** Visitor side. */
    private static final ScoreKeeper.Team AWAY = ScoreKeeper.Team.AWAY;

    /**
     * Exercises symmetric scoring and margins for both teams.
     * @param team side receiving points
     */
    @ParameterizedTest
    @EnumSource(ScoreKeeper.Team.class)
    void scoringChangesOnlySelectedTeam(ScoreKeeper.Team team) {
        assertEquals(0, board.score(team));
        assertTrue(board.isTied());
        board.award(team, 0);
        board.award(team, 2);
        board.award(team, 2);
        assertEquals(2 + 2, board.score(team));
        assertEquals(2 + 2, board.margin(team));
        ScoreKeeper.Team opponent = HOME;
        if (team == HOME) {
            opponent = AWAY;
        }
        assertEquals(-(2 + 2), board.margin(opponent));
        assertFalse(board.isTied());
    }

    /** Checks failure atomicity at both numeric bounds. */
    @Test
    void rejectsInvalidAwardsWithoutChangingScore() {
        board.award(HOME, Integer.MAX_VALUE);
        assertThrows(ArithmeticException.class, () -> board.award(HOME, 1));
        assertThrows(IllegalArgumentException.class, () -> board.award(HOME, -1));
        assertEquals(Integer.MAX_VALUE, board.score(HOME));
    }

    /** Checks replacement, reset, and independence after transfer. */
    @Test
    void transferReplacesDestinationAndResetsSource() {
        ScoreKeeper next = board.newInstance();
        next.award(HOME, 2);
        board.award(AWAY, 2);
        next.transferFrom(board);
        board.award(AWAY, 1);
        assertEquals(1, board.score(AWAY));
        assertEquals(2, next.score(AWAY));
        assertEquals(0, next.score(HOME));
        next.clear();
        assertEquals(0, next.score(AWAY));
        assertTrue(next.isTied());
    }

    /** Checks value semantics on equal and differing boards. */
    @Test
    void equalScoresDescribeEqualBoards() {
        ScoreKeeper other = board.newInstance();
        for (ScoreKeeper.Team team : ScoreKeeper.Team.values()) {
            board.award(team, 2);
            other.award(team, 2);
        }
        assertTrue(board.isTied());
        assertEquals(board, other);
        assertEquals(board.hashCode(), other.hashCode());
        assertEquals("Home 2 - Away 2", board.toString());
        other.award(AWAY, 1);
        assertNotEquals(board, other);
        assertNotEquals(board, null);
    }

    /** Checks clearing and fresh-instance creation with a populated receiver. */
    @Test
    void freshGamesAreIndependent() {
        board.award(HOME, 1);
        board.award(AWAY, 2);
        ScoreKeeper fresh = board.newInstance();
        assertEquals(0, fresh.score(HOME));
        assertEquals(0, fresh.score(AWAY));
        assertEquals(1, board.score(HOME));
        assertEquals(2, board.score(AWAY));
        board.clear();
        board.clear();
        assertEquals(0, board.score(HOME));
        assertEquals(0, board.score(AWAY));
    }

    /** Tests transfer from an empty board and rejected self-transfer. */
    @Test
    void transferEdgeCases() {
        ScoreKeeper empty = board.newInstance();
        board.award(HOME, 1);
        assertThrows(IllegalArgumentException.class, () -> board.transferFrom(board));
        assertThrows(IllegalArgumentException.class, () -> board.transferFrom(null));
        assertEquals(1, board.score(HOME));
        board.transferFrom(empty);
        assertEquals(empty, board);
        assertEquals(0, board.score(HOME));
    }

    /** Tests maximum opposing margins and different home scores. */
    @Test
    void maximumLeadAndValueComparisons() {
        ScoreKeeper empty = board.newInstance();
        board.award(HOME, Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, board.margin(HOME));
        assertEquals(-Integer.MAX_VALUE, board.margin(AWAY));
        assertNotEquals(board, empty);
        assertNotEquals(board, "score");
        assertEquals(board, board);
        board.award(AWAY, Integer.MAX_VALUE);
        assertTrue(board.isTied());
    }
}
