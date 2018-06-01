package ch.epfl.cs107.play.game.actor.bike;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.actor.bike_levels.BasicBikeLevel;
import ch.epfl.cs107.play.game.actor.bike_levels.GravityWellBikeLevel;
import ch.epfl.cs107.play.game.actor.bike_levels.JumpBikeLevel;
import ch.epfl.cs107.play.game.actor.bike_levels.PendulumBikeLevel;
import ch.epfl.cs107.play.game.actor.bike_levels.RockerBikeLevel;
import ch.epfl.cs107.play.game.actor.bike_levels.TutorialBikeLevel;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Window;

public class BikeGame extends ActorGame {
	
	// A message which will be associated to the window 
	private TextGraphics message = new TextGraphics("", 0.2f, Color.BLACK, Color.BLACK, 0.02f, true, false, new Vector(0.5f, 0.5f), 1.0f, 100.0f);
	// A message which will be associated to the window 
	private TextGraphics tutorial = new TextGraphics("", 0.1f, Color.BLACK, Color.BLACK, 0.02f, true, false, new Vector(0.5f, 0.5f), 1.0f, 100.0f);
	// The score displayed in the up left corner
	private TextGraphics score = new TextGraphics("", 0.1f, Color.BLACK, Color.BLACK, 0.02f, false, true, new Vector(0.5f, 0.5f));
	
	// Images displayed in the window at some point of a BikeGame
	private ImageGraphics heart = new ImageGraphics("", .1f, .1f);
	private ImageGraphics star1 = new ImageGraphics("", .2f, .2f);
	private ImageGraphics star2 = new ImageGraphics("", .2f, .2f);
	private ImageGraphics star3 = new ImageGraphics("", .2f, .2f); 
	
	// Counter associated to the score in the level
	private int scoreCount;
	// Counter of the current Level in the BikeGame
	private int levelCount;
	
	// The list of all the Levels of the BikeGame
	private ArrayList<BikeLevel> levels = new ArrayList<BikeLevel>();
	
	// Used to stock the current Level in the BikeGame
	private BikeLevel myLevel;

	// Indicates if the Level is won or not
	private boolean isWon;
	// Indicates if the BikeGame is over or not
	private boolean end;
	
	// Used to stock the FileSystem that we are working with in Program.java
	private FileSystem fileSystem;

	
	/**
	 * Begin the BikeGame.
	 * @param window, the canvas in which we are working
	 * @param fileSystem, the FileSystem that we are working with
	 */
	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		
		// Calling the method begin() in ActorGame
		super.begin(window, fileSystem);
		
		
		// Initialization of levels, the list of the BikeGame levels
		this.levels.add(new TutorialBikeLevel());
		this.levels.add(new BasicBikeLevel());
		this.levels.add(new JumpBikeLevel());
		this.levels.add(new GravityWellBikeLevel());	
		this.levels.add(new RockerBikeLevel());
		this.levels.add(new PendulumBikeLevel());
		
		// Initialization of the attributes
		scoreCount = 0;
		levelCount = 0;
		this.isWon = false;
		this.end = false;
		this.fileSystem = fileSystem;
		this.setLevel(levels.get(levelCount));
		this.myLevel = (BikeLevel)getLevel();
		
		// Initialization of the message
		message.setParent(window); 
		message.setRelativeTransform(Transform.I.translated(0.0f, -1.0f));
		setMessage("");
		
		// Initialization of the message
		tutorial.setParent(window); 
		tutorial.setRelativeTransform(Transform.I.translated(0.0f, -0.6f));
		setTutorial("");
		
		// Initialization of the score counter
		score.setParent(window); 
		score.setRelativeTransform(Transform.I.translated(-0.8f, -0.4f));
		score.setText("" + scoreCount);
		score.draw(window);
		
		// Initialization of the heart in the up right corner
		heart.setParent(window); 
		heart.setRelativeTransform(Transform.I.translated(0.8f, 0.55f));
		heart.setName("heart.full.png");
		heart.draw(window);
		
		// Initialization of the stars 
		star1.setParent(window); 
		star1.setRelativeTransform(Transform.I.translated(-.4f, 0.2f));
		star2.setParent(window); 
		star2.setRelativeTransform(Transform.I.translated(-0.1f, 0.2f));
		star3.setParent(window); 
		star3.setRelativeTransform(Transform.I.translated(0.2f, 0.2f));
	
		return true;
	}
	
	
	/**
	 * Updates the BikeGame.
	 * @param deltaTime elapsed time since last update, in seconds
	 */
	@Override
	public void update(float deltaTime) {
		
		// Calling the method update() in ActorGame
		super.update(deltaTime);
		
		// In case of a collision from the bike contact listener, we lose half a life each time
		if (!isWon && myLevel.getBike() != null && myLevel.getBike().getHit()) {
			if (this.heart.getName() == "heart.full.png") {
				this.loseHalfLife();
				myLevel.resetBike();
			} else if (this.heart.getName() == "heart.half.png"){
				this.loseHalfLife();
			}	
		
		// When the current Level is won, we set the stars
		} else if (myLevel.isWon()) {
			this.isWon = true;
			if (scoreCount == 0) {
				this.setStars(0);
			} else if (scoreCount < 0.5*myLevel.getScoreMax()){
				this.setStars(1);
			} else if (scoreCount >= 0.5*myLevel.getScoreMax() && scoreCount < myLevel.getScoreMax()){
				this.setStars(2);
			} else if (scoreCount >= myLevel.getScoreMax()){
				this.setStars(3);
			}
		// By default, the Level isn't won
		} else {
			isWon = false;
		}
		
		
		// Reset the level from the beginning if the key R is pressed and the whole game at the end.
		if (getKeyboard().get(KeyEvent.VK_R).isPressed()) {
			if (end) {
				this.resetGame();
			} else {
				myLevel.resetLevel();
			}			
			scoreCount = 0;
			this.score.setText("" + scoreCount);
			this.heart.setName("heart.full.png");
			setMessage("");
			
		// Reset the game from the last checkpoint if the key C is pressed, but loose half a life each time
		} else if (getKeyboard().get(KeyEvent.VK_C).isPressed() && !isWon && !end) {
			if (this.heart.getName() == "heart.empty.png") {
				return;
			} else {
				if (this.heart.getName() == "heart.full.png") {
					myLevel.resetBike();
				} else {
					myLevel.removeActor(myLevel.getBike());
				}
				this.loseHalfLife();
			}
		
		// Change the current Level to the next one if the key ENTER is pressed
		} else if (getKeyboard().get(KeyEvent.VK_ENTER).isPressed() && isWon && !end) {
			this.nextLevel();
		}
		
		// Draw the displayed informations in the window
		this.score.draw(getCanvas());
		this.heart.draw(getCanvas());
		this.message.draw(getCanvas());
		this.tutorial.draw(getCanvas());
	}

	/**
	 * End the BikeGame.
	 */
	@Override
	public void end() {}
	
	
	/**
	 * Reset the whole BikeGame
	 */
	public void resetGame() {
		// Reset attributes
		isWon = false;
		myLevel.setWon(false);	
		setStars(-1);
		// Destroy the current Game
		this.destroy();
		// Begin the game once again
		this.begin((Window)getCanvas(), fileSystem);	
	}
	
	
	/**
	 * Updates the current level to the next one
	 */
	private void nextLevel() {
		// Update the level counter
		levelCount ++;
		
		// Reset attributes
		isWon = false;
		scoreCount = 0;
		score.setText("" + scoreCount);
		setMessage("");
		heart.setName("heart.full.png");
		
		// Destroy the current Level
		myLevel.destroy();
		
		// If it was the last level, end game.
		if(levelCount == levels.size()) {
			setMessage("The end.");
			this.score.setText("");
			end = true;
		// Set the current level as the next one
		} else {
			this.setLevel(levels.get(levelCount));
			this.myLevel = (BikeLevel)getLevel();
		} 
	}
	
	
	/**
	 * Updates the score of the player in the level
	 * @param i an integer added to the score
	 */
	public void score(int i) {
		this.scoreCount = this.scoreCount + i;
		this.score.setText(""+ scoreCount);
		this.score.draw(getCanvas());
	}
	
	
	/**
	 * Deals with the display of the heart in the top right corner when the player 
	 * loses half a life.
	 */
	public void loseHalfLife(){
		if(this.heart.getName() == "heart.full.png") {
			this.heart.setName("heart.half.png");
		} else if (this.heart.getName() == "heart.half.png"){
			this.heart.setName("heart.empty.png");
			setMessage("Try again");
		}

		this.heart.draw(getCanvas());
	}
	
	
	/**
	 * Set the display of the stars and the text of the message at the end of each level 
	 * @param nb the number of stars to display, (nothing if nb < 0 & nb > 3)
	 */
	private void setStars(int nb) {
		if(nb == 0) {
			star1.setName("star.silver.png");
			star2.setName("star.silver.png");
			star3.setName("star.silver.png");
		} else if(nb == 1) {
			star1.setName("star.gold.png");
			star2.setName("star.silver.png");
			star3.setName("star.silver.png");
		} else if(nb == 2) {
			star1.setName("star.gold.png");
			star2.setName("star.gold.png");
			star3.setName("star.silver.png");
		} else if(nb ==3) {
			star1.setName("star.gold.png");
			star2.setName("star.gold.png");
			star3.setName("star.gold.png");
		} else {
			star1.setName("");
			star2.setName("");
			star3.setName("");
		}
		
		if(isWon && !end) {
			setMessage("Press Enter");
		}
	
		star1.draw(getCanvas());
		star2.draw(getCanvas());
		star3.draw(getCanvas());	
	}
	
	
	/**
	 * Set the game's message.
	 * @param text the new string to display
	 */
	private void setMessage(String text) {
		message.setText(text);
		message.draw(getCanvas());
	}
	
	
	/**
	 * Set the game's tutorial message.
	 * @param text the new string to display
	 */
	public void setTutorial(String text) {
		tutorial.setText(text);
		tutorial.draw(getCanvas());
	}

}
