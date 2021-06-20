package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class PlayerCollisionsTest {

    @Test
    public void playerCollidesGhost() {
        DefaultPointCalculator defaultPointCalculator = Mockito.mock(DefaultPointCalculator.class);
        PlayerCollisions playerCollisions = new PlayerCollisions(defaultPointCalculator);
        Player player = Mockito.mock(Player.class);
        Ghost ghost = Mockito.mock(Ghost.class);

        playerCollisions.collide(player, ghost);

        Mockito.verify(defaultPointCalculator).collidedWithAGhost(player, ghost);
        Mockito.verify(player).setAlive(false);
        Mockito.verify(player).setKiller(ghost);
    }

    @Test
    public void playerCollidesPellet() {
        DefaultPointCalculator defaultPointCalculator = Mockito.mock(DefaultPointCalculator.class);
        PlayerCollisions playerCollisions = new PlayerCollisions(defaultPointCalculator);
        Player player = Mockito.mock(Player.class);
        Pellet pellet = Mockito.mock(Pellet.class);

        playerCollisions.collide(player, pellet);

        Mockito.verify(defaultPointCalculator).consumedAPellet(player, pellet);
        Mockito.verify(pellet).leaveSquare();
    }

    @Test
    public void ghostCollidesPellet() {
        DefaultPointCalculator defaultPointCalculator = Mockito.mock(DefaultPointCalculator.class);
        PlayerCollisions playerCollisions = new PlayerCollisions(defaultPointCalculator);
        Ghost ghost = Mockito.mock(Ghost.class);
        Pellet pellet = Mockito.mock(Pellet.class);

        playerCollisions.collide(ghost, pellet);

        Mockito.verifyNoMoreInteractions(defaultPointCalculator);
    }

    @Test
    public void ghostCollidesPlayer() {
        DefaultPointCalculator defaultPointCalculator = Mockito.mock(DefaultPointCalculator.class);
        PlayerCollisions playerCollisions = new PlayerCollisions(defaultPointCalculator);
        Ghost ghost = Mockito.mock(Ghost.class);
        Player player = Mockito.mock(Player.class);

        playerCollisions.collide(ghost, player);

        Mockito.verify(defaultPointCalculator).collidedWithAGhost(player, ghost);
        Mockito.verify(player).setAlive(false);
        Mockito.verify(player).setKiller(ghost);
    }

    @Test
    public void pelletCollidesPlayer() {
        DefaultPointCalculator defaultPointCalculator = Mockito.mock(DefaultPointCalculator.class);
        PlayerCollisions playerCollisions = new PlayerCollisions(defaultPointCalculator);
        Pellet pellet = Mockito.mock(Pellet.class);
        Player player = Mockito.mock(Player.class);

        playerCollisions.collide(pellet, player);

        Mockito.verify(defaultPointCalculator).consumedAPellet(player, pellet);
        Mockito.verify(pellet).leaveSquare();
    }

    @Test
    public void pelletCollidesGhost() {
        DefaultPointCalculator defaultPointCalculator = Mockito.mock(DefaultPointCalculator.class);
        PlayerCollisions playerCollisions = new PlayerCollisions(defaultPointCalculator);
        Pellet pellet = Mockito.mock(Pellet.class);
        Ghost ghost = Mockito.mock(Ghost.class);

        playerCollisions.collide(pellet, ghost);

        Mockito.verifyNoMoreInteractions(defaultPointCalculator);
    }

}
