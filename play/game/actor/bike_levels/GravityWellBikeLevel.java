package ch.epfl.cs107.play.game.actor.bike_levels;


import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.bike.Bike;
import ch.epfl.cs107.play.game.actor.bike.BikeLevel;
import ch.epfl.cs107.play.game.actor.bike.Checkpoint;
import ch.epfl.cs107.play.game.actor.bike.Coin;
import ch.epfl.cs107.play.game.actor.bike.Finish;
import ch.epfl.cs107.play.game.actor.general.GravityWell;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Vector;

public class GravityWellBikeLevel extends BikeLevel {
	
	// Actors used with contact listener
	private Bike myBike;
	private Finish finish; 
	private Checkpoint check;

	// Initial position of myBike
	private Vector initialPosition = new Vector(-4.0f, 0.0f);

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
		new Terrain(game, new Polyline(	-30.0f, -100.0f,
										-30.0f, 0.0f,
										28.0f, 0.0f,
										28.0f, -100.0f	));
		
		new Terrain(game, new Polyline(	-85.0f, -100.0f,
										-85.0f, 17.0f,
										-50.0f, 17.0f,
										-50.0f, -100.0f	));
		
		new Terrain(game, new Polyline(	-250.0f, -100.0f,
										-250.0f, 100.0f,
										-230.0f, 100.0f,
										-230.0f, -25.0f,
										-220.0f, -35.0f,
										-120.0f, -35.0f,
										-120.0f, -100.0f	));
		
		// Adding a checkpoint
		check = new Checkpoint(game, new Vector(-70.f, 17.0f));
			
		// Adding a finish line
		finish = new Finish(game, new Vector(-125.0f, -35.0f));
		
		// Adding gravity wells
		new GravityWell(game, new Vector(-2.0f, 0.0f), 10.0f, 12.0f, new Vector(0.0f, 40.0f));
		new GravityWell(game, new Vector(-35.0f, 6.0f), 35.0f, 20.0f, new Vector(-40f, 15.5f));
		new GravityWell(game, new Vector(-105.0f, 0.0f), 15.0f, 17.0f, new Vector(0.0f, -60.f));
		new GravityWell(game, new Vector(-110.0f, -12.0f), 20.0f, 12.0f, new Vector(-60f, 80.f));
		
		// Adding coins to the game 
		new Coin(game, new Vector(.0f, 7.0f), "coin.bronze.png", 100);
		new Coin(game, new Vector(24f, 1.0f), "duck.png", 500);
		new Coin(game, new Vector(-35.5f, 24.0f), "coin.gold.png", 500);
		new Coin(game, new Vector(-229f, -24.0f), "coin.diamond.png", 1000);
		// Set the maximal score of the level
		this.setScoreMax(1600);
	
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
			this.setBikePosition(check.getPosition());
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
