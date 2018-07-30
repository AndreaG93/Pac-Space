package graziani.andrea.pacspace.game.logic;

import graziani.andrea.pacspace.game.states.Paused;
import graziani.andrea.pacspace.game.states.Running;
import graziani.andrea.pacspace.game.states.Starting;

/**
 * This class keeps reference to instances of the various states of the game.
 *
 * @author Andrea Graziani
 * @version 1.0
 */
public class GameStatusHolder {

    public static Paused pausedStatus = new Paused();
    public static Running runningStatus = new Running();
    public static Starting startingStatus = new Starting();
}