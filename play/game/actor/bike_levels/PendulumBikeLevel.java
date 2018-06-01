package ch.epfl.cs107.play.game.actor.bike_levels;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.bike.Bike;
import ch.epfl.cs107.play.game.actor.bike.BikeLevel;
import ch.epfl.cs107.play.game.actor.bike.Checkpoint;
import ch.epfl.cs107.play.game.actor.bike.Coin;
import ch.epfl.cs107.play.game.actor.bike.Finish;
import ch.epfl.cs107.play.game.actor.general.Pendulum;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Vector;

public class PendulumBikeLevel extends BikeLevel {
	
	// Actors used with contact listener
	private Bike myBike;
	private Finish finish; 
	private Checkpoint check;
	
	// Initial position of myBike
	private Vector initialPosition = new Vector(-6.f, 8.0f);
		
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
		new Terrain(game, new Polyline(	-10.0f, -100.0f,
										-10.0f, 0.0f,
										156.0f, 0.0f,
										156.5f, 0.25f,
										157.0f, .5f,
										158.0f, 1.0f,
										159.0f, 2.0f,
										160.0f, 3.0f,
										160.0f, -100	 ));
		
		new Terrain(game, new Polyline(	170.0f, -100.0f,
										170.0f, 7.0f,
										190.0f, 7.0f,
										190.0f, -100	));
		
		// Adding a checkpoint
		check = new Checkpoint(game, new Vector(70.f, 0.0f));
		
		//Adding a finish line
		finish = new Finish(game, new Vector(180.0f, 7.0f));
		
		// Adding coins to the game 
		new Coin(game, new Vector(35.0f, 1.0f), "coin.gold.png", 500);
		new Coin(game, new Vector(110f, 1.0f), "coin.gold.png", 500);
		new Coin(game, new Vector(165f, 10f), "coin.gold.png", 500);
		// Set the maximal score of the level
		this.setScoreMax(1500);
		
		new Pendulum(game, new Vector(10f, 8.0f), 7.0f, 1f);
		new Pendulum(game, new Vector(25f, 8.0f), 7.0f, 1f);
		new Pendulum(game, new Vector(40f, 8.0f), 7.0f, 1f);
		new Pendulum(game, new Vector(55f, 8.0f), 7.0f, 1f);
		
		new Pendulum(game, new Vector(85f, 10.0f), 9.0f, 1.3f);
		new Pendulum(game, new Vector(100f, 10.0f), 9.0f, 1.3f);
		new Pendulum(game, new Vector(115f, 10.0f), 9.0f, 1.3f);
		new Pendulum(game, new Vector(130f, 10.0f), 9.0f, 1.3f);
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
			this.setBikePosition(new Vector(check.getPosition().x, check.getPosition().y + 7.0f));
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

