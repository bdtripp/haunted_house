public class GameCliRunner
{
    private Parser parser;
    private Game game = new Game();

    System.out.println(game.play());

    // Enter the main command loop.  Here we repeatedly read commands and
    // execute them until the game is over.

    boolean finished = false;
    while(! finished) {
        Command command = parser.getCommand();
        finished = game.processCommand(command);
    }

    System.out.println("Thank you for playing.  Good bye.");
}