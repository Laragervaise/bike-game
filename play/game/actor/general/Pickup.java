package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.math.Vector;

public class Pickup extends Trigger{
	
	// Pickup parameters
	private final static float DETECTION_RADIUS = 0.4f;
	
	/**
	 * Creates a new Trigger
	 * @param game the ActorGame owner of the trigger
	 * @param position a Vector in absolute coordinates
	 * @param name a String giving the name of the image associated to the trigger
	 * @param category the category for the contact listener
	 * @param mask the mask for the contact listener
	 */
	public Pickup(ActorGame game, Vector position, String name, short category, short mask) {
		super(game, position, DETECTION_RADIUS, name, category, mask);
	}

	
	/**
	 * Updates the pickup
	 * @param deltaTime elapsed time since last update, in seconds
	 */
	public void update(float deltaTime) {
		if(super.getTouch()) {
			this.getOwner().removeActor(this);
		}
	}
	
	
	/**
	 * Destroys the pickup
	 */
	public void destroy() {
		super.destroy();
	}

}
