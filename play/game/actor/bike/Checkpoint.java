package ch.epfl.cs107.play.game.actor.bike;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.general.Trigger;
import ch.epfl.cs107.play.math.Vector;

public class Checkpoint extends Trigger implements Actor{
	
	// Categories and masks for the contact listener
	private final static short CATEGORY_CHECK = 0x00016;
	private final static short MASK_CHECK = 0b001;

	// The detection radius for
	private final static float DETECTION_RADIUS = 2.3f;
	

	/**
	 * Creates a checkpoint.
	 * @param game the owner of the checkpoint
     * @param position a Vector in absolute coordinate
	 */
	public Checkpoint(ActorGame game, Vector position) {
		// Calling the Trigger constructor
		super(game, position, DETECTION_RADIUS, "flag.blue.png", CATEGORY_CHECK, MASK_CHECK);
	}
	
	
	/**
	 * Updates the checkpoint.
	 * @param deltaTime elapsed time since last update, in seconds
	 */
	public void update(float deltaTime) {
		// Turns green if it is touched by the bike
		if(super.getTouch()) {
			super.setImage("flag.green.png");
		}
	}

}
