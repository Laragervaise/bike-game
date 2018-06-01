package ch.epfl.cs107.play.game.actor.bike;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.general.Trigger;
import ch.epfl.cs107.play.math.Vector;

public class Finish extends Trigger implements Actor{
	
	// Categories and masks for the contact listener
	private final static short CATEGORY_FINISH = 0x0008;
	private final static short MASK_FINISH = 0b001;
	
	// The detection radius for
	private final static float DETECTION_RADIUS = 2.3f;

	
	/**
	 * Creates a finish line.
	 * @param game the owner of the finish line
     * @param position a Vector in absolute coordinate
	 */
	public Finish(ActorGame game, Vector position) {
		// Calling the Trigger constructor
		super(game, position, DETECTION_RADIUS, "flag.red.png", CATEGORY_FINISH, MASK_FINISH);
	}
}
