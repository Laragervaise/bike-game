package ch.epfl.cs107.play.game.actor.bike;

import ch.epfl.cs107.play.game.actor.ActorGame;
import  ch.epfl.cs107.play.game.actor.general.Pickup;
import ch.epfl.cs107.play.math.Vector;

public class Coin extends Pickup{
	
	// Categories and masks for the contact listener
	private final static short CATEGORY_COIN = 0x4;
	private final static short MASK_COIN = 0b011;	
	
	// Used to stock the coin's associated reward
	private int reward;
	

	/**
	 * Creates a coin.
	 * @param game the owner of the coin
     * @param position a Vector in absolute coordinate
     * @param reward the int to increase game's score with
	 */
	public Coin(ActorGame game, Vector position, String name, int reward) {
		// Calling the Pickup constructor
		super(game, position, name, CATEGORY_COIN, MASK_COIN);
		// Initialize the attribute
		this.reward = Math.abs(reward);
	}
	
	
	/**
	 * Updates the checkpoint.
	 * @param deltaTime elapsed time since last update, in seconds
	 */
	public void update(float deltaTime) {
		// When the coin is touched by the bike, we update the score in the BikeGame and we destroy the coin
		if(super.getTouch()) {
			BikeGame game = (BikeGame)this.getOwner();
			game.score(reward);	
			this.getOwner().removeActor(this);
		}	
	}

}
