package hunter;

import model.GameStarter;

public class Hunter {
    
    private static final int DEFAULT_NUM_OF_GAMES = 4;
    private static final int DEFAULT_GAME_SIZE_DIFFERENCE = 2;

    /**
     * Új játékot indít az alapértelmezett értékkel.
     * Ha a paraméterben egy egész szám szerepel, akkor azzal az értékkel indítja a játékot.
     * @param args - opcionális: (int) játékok száma (1 és 5 között)
     * , (int) játék mérete (1 és 4 között)
     */
    public static void main(String[] args) {
        int num_of_games = DEFAULT_NUM_OF_GAMES;
        int game_size_difference = DEFAULT_GAME_SIZE_DIFFERENCE;
        try {
            if (args.length > 0) {
                num_of_games = Integer.parseInt(args[0]);
                if (num_of_games > 0 && num_of_games < 6) {
                    game_size_difference = num_of_games;
                } else {
                    num_of_games = DEFAULT_NUM_OF_GAMES;
                }
            }
            if (args.length > 1) {
                game_size_difference = Integer.parseInt(args[1]);
                if (game_size_difference <= 0 || game_size_difference >= 5) {
                    game_size_difference = DEFAULT_GAME_SIZE_DIFFERENCE;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Kérem számot adjon meg a program paramétereként"
                    + ", vagy semmit.");
        }
        GameStarter game = new GameStarter(num_of_games, game_size_difference);
        System.err.println("A játék elindult, játékok száma: " + num_of_games);
        System.err.println("A növekedés mértéke játékonként: " 
                + game_size_difference);
    }
    
}
