package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.math.Positionable;

public interface Actor extends Positionable, Graphics {

	/**
	* Simulates a single time step for an Actor, by default
	* @param deltaTime elapsed time since last update, in seconds
	*/
	public default void update(float deltaTime) {
		// By default, actors have nothing to update
	}
	
	/**
	 * Destroys an Actor, by default
	 */
	public default void destroy(){
		// By default, actors have nothing to destroy
	}
}
