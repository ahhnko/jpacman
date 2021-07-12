package nl.tudelft.jpacman.board;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import nl.tudelft.jpacman.level.Level.LevelObserver;

import static org.assertj.core.api.Assertions.assertThat;

public class GameTest {

    //    public static final String MAP = "/board.txt";
    public static final String EASY_WIN_MAP = "/easyWinBoard.txt";
    public static final String NO_WIN_NO_LOOSE_MAP = "/noWinNoLooseBoard.txt";
    public static final String EASY_LOOSE_MAP = "/easyLooseBoard.txt";
    public static final String WIN_THEN_LOOSE_MAP = "/winThenLooseBoard.txt";
    public static final String LOOSE_THEN_LOOSE_MAP = "/looseThenLooseBoard.txt";
    public static final String LOOSE_THEN_WIN_MAP = "/looseThenWinBoard.txt";


    private Launcher launcher;


    /**
     * Quit the user interface when we're done.
     */
    @AfterEach
    void tearDown() {
        launcher.dispose();
    }


    @Test
    void launchStartStart() {
        this.launcher = new Launcher().withMapFile(NO_WIN_NO_LOOSE_MAP);
        this.launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        Level level = game.getLevel();
        LevelObserver levelObserver = Mockito.mock(LevelObserver.class);
        level.addObserver(levelObserver);

        // start cleanly.
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isZero();

        // get points
        game.move(player, Direction.EAST);
        assertThat(player.getScore()).isEqualTo(10);

        Mockito.verify(levelObserver, Mockito.never()).levelLost();
        Mockito.verify(levelObserver, Mockito.never()).levelWon();
        assertThat(game.isInProgress()).isTrue();

    }


    @Test
    void launchStartPauseStart() {
        this.launcher = new Launcher().withMapFile(NO_WIN_NO_LOOSE_MAP);
        this.launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        Level level = game.getLevel();
        LevelObserver levelObserver = Mockito.mock(LevelObserver.class);
        level.addObserver(levelObserver);

        // start cleanly.
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isZero();

        // stop
        game.stop();
        assertThat(game.isInProgress()).isFalse();

        // stop
        game.start();
        assertThat(game.isInProgress()).isTrue();

        Mockito.verify(levelObserver, Mockito.never()).levelLost();
        Mockito.verify(levelObserver, Mockito.never()).levelWon();
        assertThat(game.isInProgress()).isTrue();

    }

    @Test
    void launchStartWin() {
        this.launcher = new Launcher().withMapFile(EASY_WIN_MAP);
        this.launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        Level level = game.getLevel();
        LevelObserver levelObserver = Mockito.mock(LevelObserver.class);
        level.addObserver(levelObserver);

        // start cleanly.
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isZero();

        // get points
        game.move(player, Direction.EAST);
        assertThat(player.getScore()).isEqualTo(10);

        Mockito.verify(levelObserver).levelWon();
        Mockito.verify(levelObserver, Mockito.never()).levelLost();
        assertThat(game.isInProgress()).isFalse();
    }


    @Test
    void launchStartLoose() {
        this.launcher = new Launcher().withMapFile(EASY_LOOSE_MAP);
        this.launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        Level level = game.getLevel();
        LevelObserver levelObserver = Mockito.mock(LevelObserver.class);
        level.addObserver(levelObserver);

        // start cleanly.
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isZero();

        // get points
        game.move(player, Direction.EAST);

        Mockito.verify(levelObserver).levelLost();
        Mockito.verify(levelObserver, Mockito.never()).levelWon();
        assertThat(game.isInProgress()).isFalse();
    }


//not valid

    @Test
    void launchStop() {
        this.launcher = new Launcher().withMapFile(NO_WIN_NO_LOOSE_MAP);
        this.launcher.launch();
        Game game = launcher.getGame();
        Level level = game.getLevel();
        LevelObserver levelObserver = Mockito.mock(LevelObserver.class);
        level.addObserver(levelObserver);

        // start cleanly.
        assertThat(game.isInProgress()).isFalse();
        game.start();

        // stop
        game.stop();
        assertThat(game.isInProgress()).isFalse();

        Mockito.verifyNoMoreInteractions(levelObserver);

    }

    @Test
    void launchRegular() {
        this.launcher = new Launcher().withMapFile(NO_WIN_NO_LOOSE_MAP);
        this.launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        Level level = game.getLevel();
        LevelObserver levelObserver = Mockito.mock(LevelObserver.class);
        level.addObserver(levelObserver);

        assertThat(game.isInProgress()).isFalse();
        Square initialSquare = player.getSquare();

        // get points
        game.move(player, Direction.EAST);
        Square finalSquare = player.getSquare();

        assertThat(player.getScore()).isZero();
        assertThat(game.isInProgress()).isFalse();
        assertThat(initialSquare.equals(finalSquare)).isTrue();
        Mockito.verifyNoMoreInteractions(levelObserver);
    }

    @Test
    void launchWin() {
        this.launcher = new Launcher().withMapFile(EASY_WIN_MAP);
        this.launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        Level level = game.getLevel();
        LevelObserver levelObserver = Mockito.mock(LevelObserver.class);
        level.addObserver(levelObserver);

        assertThat(game.isInProgress()).isFalse();


        // get points
        game.move(player, Direction.EAST);
        assertThat(player.getScore()).isZero();

        assertThat(game.isInProgress()).isFalse();

        Mockito.verifyNoMoreInteractions(levelObserver);

    }

    @Test
    void launchLoose() {
        this.launcher = new Launcher().withMapFile(EASY_LOOSE_MAP);
        this.launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        Level level = game.getLevel();
        LevelObserver levelObserver = Mockito.mock(LevelObserver.class);
        level.addObserver(levelObserver);

        assertThat(game.isInProgress()).isFalse();


        // get points
        game.move(player, Direction.EAST);
        assertThat(player.getScore()).isZero();

        assertThat(game.isInProgress()).isFalse();

        Mockito.verifyNoMoreInteractions(levelObserver);

    }

    @Test
    void startStart() {
        this.launcher = new Launcher().withMapFile(NO_WIN_NO_LOOSE_MAP);
        this.launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        Level level = game.getLevel();
        LevelObserver levelObserver = Mockito.mock(LevelObserver.class);
        level.addObserver(levelObserver);

        // start cleanly.
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isZero();

        // start cleanly.
        game.start();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isZero();

        Mockito.verifyNoMoreInteractions(levelObserver);
    }

    @Test
    void stopStop() {
        this.launcher = new Launcher().withMapFile(NO_WIN_NO_LOOSE_MAP);
        this.launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        Level level = game.getLevel();
        LevelObserver levelObserver = Mockito.mock(LevelObserver.class);
        level.addObserver(levelObserver);

        // start cleanly.
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isZero();

        // stop
        game.stop();
        assertThat(game.isInProgress()).isFalse();

        // stop
        game.stop();
        assertThat(game.isInProgress()).isFalse();

        Mockito.verifyNoMoreInteractions(levelObserver);
    }

    @Test
    void stopRegular() {
        this.launcher = new Launcher().withMapFile(NO_WIN_NO_LOOSE_MAP);
        this.launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        Level level = game.getLevel();
        LevelObserver levelObserver = Mockito.mock(LevelObserver.class);
        level.addObserver(levelObserver);

        // start cleanly.
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isZero();

        // stop
        game.stop();
        assertThat(game.isInProgress()).isFalse();
        Square initialSquare = player.getSquare();

        // get points
        game.move(player, Direction.EAST);
        Square finalSquare = player.getSquare();

        assertThat(player.getScore()).isZero();
        assertThat(initialSquare.equals(finalSquare)).isTrue();
        assertThat(game.isInProgress()).isFalse();
        Mockito.verifyNoMoreInteractions(levelObserver);
    }

    @Test
    void stopWin() {
        this.launcher = new Launcher().withMapFile(EASY_WIN_MAP);
        this.launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        Level level = game.getLevel();
        LevelObserver levelObserver = Mockito.mock(LevelObserver.class);
        level.addObserver(levelObserver);

        // start cleanly.
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isZero();

        // stop
        game.stop();
        assertThat(game.isInProgress()).isFalse();

        // get points
        game.move(player, Direction.EAST);
        assertThat(player.getScore()).isZero();

        assertThat(game.isInProgress()).isFalse();

        Mockito.verifyNoMoreInteractions(levelObserver);
    }

    @Test
    void stopLoose() {
        this.launcher = new Launcher().withMapFile(EASY_LOOSE_MAP);
        this.launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        Level level = game.getLevel();
        LevelObserver levelObserver = Mockito.mock(LevelObserver.class);
        level.addObserver(levelObserver);

        // start cleanly.
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isZero();

        // stop
        game.stop();
        assertThat(game.isInProgress()).isFalse();

        // get points
        game.move(player, Direction.EAST);
        assertThat(player.getScore()).isZero();

        assertThat(game.isInProgress()).isFalse();

        Mockito.verifyNoMoreInteractions(levelObserver);
    }

    @Test
    void winStart() {
        this.launcher = new Launcher().withMapFile(EASY_WIN_MAP);
        this.launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        Level level = game.getLevel();
        LevelObserver levelObserver = Mockito.mock(LevelObserver.class);
        level.addObserver(levelObserver);

        // start cleanly.
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isZero();

        // get points
        game.move(player, Direction.EAST);
        assertThat(player.getScore()).isEqualTo(10);

        Mockito.verify(levelObserver).levelWon();
        Mockito.verify(levelObserver, Mockito.never()).levelLost();
        assertThat(game.isInProgress()).isFalse();

        game.start();
        assertThat(game.isInProgress()).isFalse();
        Mockito.verifyNoMoreInteractions(levelObserver);
    }

    @Test
    void winStop() {
        this.launcher = new Launcher().withMapFile(EASY_WIN_MAP);
        this.launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        Level level = game.getLevel();
        LevelObserver levelObserver = Mockito.mock(LevelObserver.class);
        level.addObserver(levelObserver);

        // start cleanly.
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isZero();

        // get points
        game.move(player, Direction.EAST);
        assertThat(player.getScore()).isEqualTo(10);

        Mockito.verify(levelObserver).levelWon();
        Mockito.verify(levelObserver, Mockito.never()).levelLost();
        assertThat(game.isInProgress()).isFalse();

        game.stop();
        assertThat(game.isInProgress()).isFalse();
        Mockito.verifyNoMoreInteractions(levelObserver);
    }

    @Test
    void winRegular() {
        this.launcher = new Launcher().withMapFile(EASY_WIN_MAP);
        this.launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        Level level = game.getLevel();
        LevelObserver levelObserver = Mockito.mock(LevelObserver.class);
        level.addObserver(levelObserver);

        // start cleanly.
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isZero();

        // get points
        game.move(player, Direction.EAST);

        assertThat(player.getScore()).isEqualTo(10);
        Mockito.verify(levelObserver).levelWon();
        Mockito.verify(levelObserver, Mockito.never()).levelLost();
        assertThat(game.isInProgress()).isFalse();

        Square initialSquare = player.getSquare();
        game.move(player, Direction.EAST);
        Square finalSquare = player.getSquare();

        assertThat(game.isInProgress()).isFalse();
        assertThat(initialSquare.equals(finalSquare)).isTrue();
        Mockito.verifyNoMoreInteractions(levelObserver);
    }

    @Test
    void winWin() {
        this.launcher = new Launcher().withMapFile(EASY_WIN_MAP);
        this.launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        Level level = game.getLevel();
        LevelObserver levelObserver = Mockito.mock(LevelObserver.class);
        level.addObserver(levelObserver);

        // start cleanly.
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isZero();

        // get points
        game.move(player, Direction.EAST);
        assertThat(player.getScore()).isEqualTo(10);

        Mockito.verify(levelObserver).levelWon();
        Mockito.verify(levelObserver, Mockito.never()).levelLost();
        assertThat(game.isInProgress()).isFalse();

        Mockito.verifyNoMoreInteractions(levelObserver);
    }

    @Test
    void winLoose() {
        this.launcher = new Launcher().withMapFile(WIN_THEN_LOOSE_MAP);
        this.launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        Level level = game.getLevel();
        LevelObserver levelObserver = Mockito.mock(LevelObserver.class);
        level.addObserver(levelObserver);

        // start cleanly.
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isZero();

        // get points
        game.move(player, Direction.EAST);

        Mockito.verify(levelObserver).levelWon();
        assertThat(game.isInProgress()).isFalse();

        game.move(player, Direction.EAST);
        assertThat(game.isInProgress()).isFalse();
        Mockito.verifyNoMoreInteractions(levelObserver);
        Mockito.verify(levelObserver, Mockito.never()).levelLost();
    }

    @Test
    void looseStart() {
        this.launcher = new Launcher().withMapFile(EASY_LOOSE_MAP);
        this.launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        Level level = game.getLevel();
        LevelObserver levelObserver = Mockito.mock(LevelObserver.class);
        level.addObserver(levelObserver);

        // start cleanly.
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isZero();

        // get points
        game.move(player, Direction.EAST);

        Mockito.verify(levelObserver).levelLost();
        assertThat(game.isInProgress()).isFalse();

        game.start();
        assertThat(game.isInProgress()).isFalse();
        Mockito.verifyNoMoreInteractions(levelObserver);
    }

    @Test
    void looseStop() {
        this.launcher = new Launcher().withMapFile(LOOSE_THEN_LOOSE_MAP);
        this.launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        Level level = game.getLevel();
        LevelObserver levelObserver = Mockito.mock(LevelObserver.class);
        level.addObserver(levelObserver);

        // start cleanly.
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isZero();

        // get points
        game.move(player, Direction.EAST);

        Mockito.verify(levelObserver).levelLost();
        assertThat(game.isInProgress()).isFalse();

        game.stop();
        assertThat(game.isInProgress()).isFalse();
        Mockito.verifyNoMoreInteractions(levelObserver);
    }

    @Test
    void looseRegular() {
        this.launcher = new Launcher().withMapFile(EASY_LOOSE_MAP);
        this.launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        Level level = game.getLevel();
        LevelObserver levelObserver = Mockito.mock(LevelObserver.class);
        level.addObserver(levelObserver);

        // start cleanly.
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isZero();

        // get points
        game.move(player, Direction.EAST);

        Mockito.verify(levelObserver).levelLost();
        assertThat(game.isInProgress()).isFalse();
        Square initialSquare = player.getSquare();

        game.move(player, Direction.EAST);

        Square finalSquare = player.getSquare();
        assertThat(game.isInProgress()).isFalse();
        assertThat(initialSquare.equals(finalSquare)).isTrue();
        Mockito.verifyNoMoreInteractions(levelObserver);
    }

    @Test
    void looseWin() {
        this.launcher = new Launcher().withMapFile(LOOSE_THEN_WIN_MAP);
        this.launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        Level level = game.getLevel();
        LevelObserver levelObserver = Mockito.mock(LevelObserver.class);
        level.addObserver(levelObserver);

        // start cleanly.
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isZero();

        // get points
        game.move(player, Direction.EAST);

        Mockito.verify(levelObserver).levelLost();
        assertThat(game.isInProgress()).isFalse();

        // get points
        game.move(player, Direction.EAST);

        assertThat(game.isInProgress()).isFalse();
        Mockito.verifyNoMoreInteractions(levelObserver);
    }

    @Test
    void looseLoose() {
        this.launcher = new Launcher().withMapFile(LOOSE_THEN_LOOSE_MAP);
        this.launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        Level level = game.getLevel();
        LevelObserver levelObserver = Mockito.mock(LevelObserver.class);
        level.addObserver(levelObserver);

        // start cleanly.
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isZero();

        // get points
        game.move(player, Direction.EAST);

        Mockito.verify(levelObserver).levelLost();
        assertThat(game.isInProgress()).isFalse();

        // get points
        game.move(player, Direction.EAST);

        assertThat(game.isInProgress()).isFalse();
        Mockito.verifyNoMoreInteractions(levelObserver);
    }


}


