package ch.epfl.cs107.play.game.actor.bike_levels;


import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.bike.Bike;
import ch.epfl.cs107.play.game.actor.bike.BikeLevel;
import ch.epfl.cs107.play.game.actor.bike.Coin;
import ch.epfl.cs107.play.game.actor.bike.Finish;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Vector;

public class JumpBikeLevel extends BikeLevel {
	
	// Actors used with contact listener
	private Bike myBike;
	private Finish finish; 

	// Initial position of myBike
	private Vector initialPosition = new Vector(0.0f, 0.0f);

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
		new Terrain(game, new Polyline(	-10.0f, -100.0f,
										-10.0f, 0.0f,
										10.0f, 0.0f,
										14.0f, -8.0f,
										15.0f, -9.5f,
										17.0f, -10.0f,
										24.0f, -10.0f,
										25.0f, -9.5f,
										26.0f, -9f,
										28.0f, -7.5f,
										28.0f, -100.0f	));
		
		new Terrain(game, new Polyline(	44.0f, -100.0f,
										44.0f, -7.5f,
										50.0f, -15.5f,
										52.0f, -17.0f,
										54.0f, -17.5f,
										61.0f, -17.5f,
										62.0f, -17.0f,
										63.0f, -16.5f,
										65.0f, -15.0f,
										65.0f, -100.0f	));
		
		new Terrain(game, new Polyline(	81.0f, -100.0f,
										81.0f, -15.0f,
										88.0f, -24.5f,
										90.0f, -26.0f,
										92.0f, -26.5f,
										99.0f, -26.5f,
										101.0f, -26.0f,
										102.0f, -25.5f,
										104.0f, -23.5f,
										104.0f, -100.0f	));
		
		new Terrain(game, new Polyline(	114.0f, -100.0f,
										114.0f, -22.0f,
										150.0f, -22.0f,
										150.0f, -100.0f	));

		//Adding a finish line
		finish = new Finish(game, new Vector(127.0f, -22.0f));
		
		// Adding coins to the game 
		new Coin(game, new Vector(35.0f, -2.0f), "coin.gold.png", 500);
		new Coin(game, new Vector(73.0f, -8.5f), "coin.gold.png", 500);
		new Coin(game, new Vector(109.0f, -16.7f), "coin.gold.png", 500);
		// Set the maximal score of the level
		this.setScoreMax(1500);
	
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
