package hunter;

import model.GameStarter;

public class Hunter {

    /**
     * Starts new game with the default value.
     * If an integer argument is provided, it starts the game with the
     * value of the argument.
     * @param args - optional: (int) number of games, (int) game size
     */
    public static void main(String[] args) {
        int num_of_games = 8;
        int game_size_difference = 16;
        try {
            if (args.length > 0) {
                num_of_games = Integer.parseInt(args[0]);
                game_size_difference = num_of_games;
            }
            if (args.length > 1) {
                game_size_difference = Integer.parseInt(args[0]);
            }
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
        }
        new GameStarter(num_of_games, game_size_difference);
        new GameStarter(4);
        new GameStarter(16);
        new GameStarter(12);
    }
    
}
