package ch.epfl.cs107.play.game.actor.bike;

import ch.epfl.cs107.play.game.actor.general.Level;
import ch.epfl.cs107.play.math.Vector;

public abstract class BikeLevel extends Level{
	
	// The bike of the BikeLevel and its position
	private Bike myBike;
	private Vector bikePosition;
	
	// The default maximal score of the BikeLevel
	private int scoreMax = 150;
	
	// Indicates if the BikeLevel is won
	private boolean won;
	

	// All BikeLevel must implement a method to reset the bike
	public abstract void resetBike();
	
	/**
	 * Return myBike.
	 */
	public Bike getBike() {
		return myBike;
	}

	
	/**
	 * Set myBike.
	 * @param bike, the new bike
	 */
	protected void setBike(Bike bike){
		myBike = bike;
	}

	
	/**
	 * Return won.
	 */
	public boolean isWon() {
		return won;
	}
	
	
	/**
	 * Set won.
	 * @param isWon, the new boolean value
	 */
	protected void setWon(boolean isWon) {
		this.won = isWon;
	}

	
	/**
	 * Return myBike's position.
	 */
	protected Vector getBikePosition() {
		return bikePosition;
	}

	
	/**
	 * Set myBike's position.
	 * @param position, the new Vector position
	 */
	protected void setBikePosition(Vector position) {
		// Deals with incorrect parameters
		if(position == null) {
			throw new NullPointerException();
		}
		bikePosition = position;
	}
	
	
	/**
	 * Return scoreMax.
	 */
	public int getScoreMax() {
		return scoreMax;
	}

	/**
	 * Set scoreMax.
	 * @param scoreMax, the new integer scoreMax
	 */
	protected void setScoreMax(int scoreMax) {
		// Deals with incorrect parameters
		if(scoreMax < 0) {
			throw new IllegalArgumentException();
		}
		this.scoreMax = scoreMax;
	}
	
	/**
	 * Reset the bike which is the payLoad in a BikeGame.
	 */
	@Override
	public void resetPayLoad() {
		this.resetBike();	
	}
}
