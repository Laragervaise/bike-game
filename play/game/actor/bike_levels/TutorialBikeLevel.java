package ch.epfl.cs107.play.game.actor.bike_levels;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.bike.Bike;
import ch.epfl.cs107.play.game.actor.bike.BikeGame;
import ch.epfl.cs107.play.game.actor.bike.BikeLevel;
import ch.epfl.cs107.play.game.actor.bike.Checkpoint;
import ch.epfl.cs107.play.game.actor.bike.Coin;
import ch.epfl.cs107.play.game.actor.bike.Finish;
import ch.epfl.cs107.play.game.actor.general.Ball;
import ch.epfl.cs107.play.game.actor.general.Crate;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Vector;

public class TutorialBikeLevel extends BikeLevel {
	
	// Actors used with contact listener
	private Bike myBike;
	private Finish finish; 
	private Checkpoint check;
	
	private int counter = 0;
	
	// Initial position of myBike
	private Vector initialPosition = new Vector(0.0f, 3.0f);
		
	// Used to stock the ActorGame owner of the world
	private ActorGame game;
	private BikeGame myBikeGame;
	
	/**
	 * Creates all the actors in the world.
	 * @param game the ActorGame owner of the world
	 */
	@Override
	public void createAllActors(ActorGame game) {
		
		// Initialize the attributes
		this.game = game;
		this.myBikeGame = (BikeGame)game;
		this.counter = 0;
		
		//Adding a bike to the game
		this.setBikePosition(initialPosition);
		myBike = new Bike(game, this.getBikePosition(), true);
		this.setBike(myBike);
		game.setViewCandidate(myBike);
		
		// Adding a terrain to the game
		new Terrain(game, new Polyline(	-10.0f, -100.0f,
										-10.0f, 0.0f,
										1000.0f, 0.0f,
										1000.0f, -100	));
		
		// Adding a checkpoint
		check = new Checkpoint(game, new Vector(30.f, 0.0f));
		
		// Adding crates 
		new Crate(game, false, new Vector(130.0f, 3.0f));
		
		//Adding a finish line
		finish = new Finish(game, new Vector(195.0f, 0f));
		
		// Adding coins to the game 
		new Coin(game, new Vector(15f, 1f), "coin.bronze.png", 100);
		new Coin(game, new Vector(45f, 1f), "coin.silver.png", 250);
		new Coin(game, new Vector(75f, 1f), "coin.gold.png", 500);
		new Coin(game, new Vector(132.5f, 2.5f), "coin.diamond.png", 1000);
		new Coin(game, new Vector(165f, 1f), "duck.png", 149);
		// Set the maximal score of the level
		this.setScoreMax(1999);	
	}
	
	
	/**
	 * Updates the level.
	 * @param deltaTime elapsed time since last update, in seconds
	 */
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		if(counter == 0) {
			myBikeGame.setTutorial("Hold UP to start");
		} else if (counter == 1){
			myBikeGame.setTutorial("Hold DOWN to stop");
		} else if(counter == 2) {
			myBikeGame.setTutorial("Press R to restart");
		} else if (counter == 3){
			myBikeGame.setTutorial("Press C to respawn");
		} else if (counter == 4){
			myBikeGame.setTutorial("Use LEFT & RIGHT");
		} else if(counter == 5) {
			myBikeGame.setTutorial("Hold SPACE to turn & stop");
		} else {
			myBikeGame.setTutorial("");
		}
		
		if(finish != null && finish.getTouch()) {
			this.setWon(true);
		}else if(check != null && check.getTouch()) {
			float x = check.getPosition().x;
			float y = check.getPosition().y;
			this.setBikePosition(new Vector(x, y + 3.0f));
			this.game.removeActor(check);
			check = new Checkpoint(game, new Vector(x + 30.f, y));
			counter ++;
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
