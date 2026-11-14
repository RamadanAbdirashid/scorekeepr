import java.util.Scanner;

/** Live score entry. Enter H or A followed by points, or Q to finish. */
public final class GameNight {
    /** Prevents instantiation of the console entry point. */
    private GameNight() {
    }

    /**
     * Records scores until the operator quits.
     * @param args unused command-line arguments
     */
    public static void main(String[] args) {
        ScoreKeeper board = new ScoreKeeper1();
        System.out.println("Enter H 2 or A 1 to score. Enter Q to finish.");
        try (Scanner input = new Scanner(System.in)) {
            while (input.hasNextLine()) {
                String line = input.nextLine().trim();
                if (line.equalsIgnoreCase("Q")) {
                    break;
                }
                String[] fields = line.split("\\s+");
                if (fields.length != 2 || !fields[0].matches("[HhAa]")) {
                    System.out.println("Use H or A, a space, and a point value.");
                    continue;
                }
                ScoreKeeper.Team team = ScoreKeeper.Team.AWAY;
                if (fields[0].equalsIgnoreCase("H")) {
                    team = ScoreKeeper.Team.HOME;
                }
                try {
                    board.award(team, Integer.parseInt(fields[1]));
                    System.out.println(board);
                } catch (IllegalArgumentException | ArithmeticException ex) {
                    System.out.println("Points must fit the score limit.");
                }
            }
        }
        System.out.println("Final: " + board);
    }
}
