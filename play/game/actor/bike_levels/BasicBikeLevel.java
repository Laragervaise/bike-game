package ch.epfl.cs107.play.game.actor.bike_levels;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.bike.Bike;
import ch.epfl.cs107.play.game.actor.bike.BikeLevel;
import ch.epfl.cs107.play.game.actor.bike.Checkpoint;
import ch.epfl.cs107.play.game.actor.bike.Coin;
import ch.epfl.cs107.play.game.actor.bike.Finish;
import ch.epfl.cs107.play.game.actor.general.Crate;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class BasicBikeLevel extends BikeLevel {
	
	// Actors used with contact listener
	private Bike myBike;
	private Finish finish; 
	private Checkpoint check;
	
	// Initial position of myBike
	private Vector initialPosition = new Vector(-15.f, 8.0f);
		
	// Used to stock the ActorGame owner of the world
	private ActorGame game;

	
	/**
	 * Creates all the actors in the world.
	 * @param game the ActorGame owner of the world
	 */
	@Override
	public void createAllActors(ActorGame game) {
		
		// Initialize the attributes
		this.game = game;
		
		//Adding a bike to the game
		this.setBikePosition(initialPosition);
		myBike = new Bike(game, this.getBikePosition(), true);
		this.setBike(myBike);
		game.setViewCandidate(myBike);
		
		// Adding a terrain to the game
		new Terrain(game, new Polyline(	-100.0f, -100.0f,
										-100.0f, 0.0f,
										0.0f, 0.0f,
										3.0f, 1.0f,
										8.0f, 1.0f,
										15.0f, 3.0f,
										16.0f, 3.0f,
										25.0f, 0.0f,
										35.0f, -5.0f,
										50.0f, -5.0f,
										55.0f, -4.0f,
										65.0f, 0.0f,
										650.0f, -100	));
		
		// Adding a checkpoint
		check = new Checkpoint(game, new Vector(16.f, 3.0f));
		
		//Adding a finish line
		finish = new Finish(game, new Vector(120.0f, -9.3f));
		
		// Adding coins to the game 
		new Coin(game, new Vector(30.0f, -2.5f), "coin.gold.png", 500);
		new Coin(game, new Vector(15.5f, 3.2f), "coin.bronze.png", 100);
		new Coin(game, new Vector(67f, 3f), "coin.bronze.png", 100);
		// Set the maximal score of the level
		this.setScoreMax(700);
		
		// Adding crates 
		new Crate(game, false, new Vector(0.0f, 5.0f));
		new Crate(game, false, new Vector(1.0f, 7.0f));
		new Crate(game, false, new Vector(2.0f, 6.0f));
	}
	
	
	/**
	 * Updates the level.
	 * @param deltaTime elapsed time since last update, in seconds
	 */
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		if(finish != null && finish.getTouch()) {
			this.setWon(true);
		}else if(check != null && check.getTouch()) {
			this.setBikePosition(new Vector(check.getPosition().x, check.getPosition().y + 3.0f));
		}
	}

	
	/**
	 * Reset the level.
	 */
	@Override
	public void resetLevel() {
		this.destroy();
		this.setBike(null);
		this.setWon(false);
		createAllActors(game);	
	}

	
	/**
	 * Reset only the bike.
	 */
	@Override
	public void resetBike() {
		this.removeActor(myBike);
		myBike = new Bike(game, this.getBikePosition(), true);
		this.setBike(myBike);
		game.setViewCandidate(myBike);
	}
	
	/**
	 * Destroy the level.
	 */
	@Override
	public void destroy() {
		super.destroy();
		this.setBike(null);
		this.setWon(false);
	}
	
}
