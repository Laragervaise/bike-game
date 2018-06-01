package ch.epfl.cs107.play.game.actor.bike_levels;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.bike.Bike;
import ch.epfl.cs107.play.game.actor.bike.BikeLevel;
import ch.epfl.cs107.play.game.actor.bike.Checkpoint;
import ch.epfl.cs107.play.game.actor.bike.Coin;
import ch.epfl.cs107.play.game.actor.bike.Finish;
import ch.epfl.cs107.play.game.actor.general.Crate;
import ch.epfl.cs107.play.game.actor.general.Rocker;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Vector;

public class RockerBikeLevel extends BikeLevel {
	
	// Actors used with contact listener
	private Bike myBike;
	private Finish finish; 
	private Checkpoint check;
	
	// Initial position of myBike
	private Vector initialPosition = new Vector(-23.f, -5.0f);
		
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
		
		// Adding terrains
		new Terrain(game, new Polyline(	-25.0f, -100.0f,
										-25.0f, -10.0f,
										-20.0f, -10.0f,
										-20.0f, -100f	));
		
		new Terrain(game, new Polyline(	-10.0f, -100.0f,
										-10.0f, 0.0f,
										20.0f, 0.0f,
										20.0f, -100f	));
		
		new Terrain(game, new Polyline(	65.0f, -100.0f,
										65.0f, 0.0f,
										110.0f, 0.0f,
										110.0f, -100f));
		
		
		// Adding a crate
		new Crate(game, false, new Vector(-30.0f, 25.0f), 3.0f, 3.0f, "stone.1.png");		
		
		// Adding rockers
		new Rocker(game, new Vector(-25.0f, -10f), 12.0f, 1/2f );
		new Rocker(game, new Vector(25.0f, -1.5f), 10.0f, 1/2f );
		new Rocker(game, new Vector(36.0f, -1.5f), 10.0f, 1/2f );
		new Rocker(game, new Vector(47.0f, -1.5f), 10.0f, 1/2f );
		new Rocker(game, new Vector(58.0f, -1.5f), 10.0f, 1/2f );
	
		// Adding a checkpoint
		check = new Checkpoint(game, new Vector(15.f, 0.0f));
		
		//Adding a finish line
		finish = new Finish(game, new Vector(90.0f, 0.0f));
		
		// Adding coins to the game 
		new Coin(game, new Vector(-13.0f, 20f), "coin.gold.png", 500);
		new Coin(game, new Vector(28f, 2f), "coin.bronze.png", 100);
		new Coin(game, new Vector(51f, 2f), "coin.bronze.png", 100);
		// Set the maximal score of the level
		this.setScoreMax(700);
		
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
