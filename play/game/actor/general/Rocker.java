package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Rocker extends GameEntity implements Actor {

	// Categories and masks for the contact listener
	private final short CATEGORY_SCENERY = 0x00016;
	private final short MASK_SCENERY = -1;

	// Rocker parameters
	private static float blockWidth = 1.5f;
	private static float blockHeight = 1.0f;
	
	// The Plank associated to the rocker
	private Plank plank;

	// The graphics part of the block associated
	private ImageGraphics blockGraphics;

	/**
	 * Creates a new Lever in the game
	 * @param game the owner of the rocker
     * @param position a Vector in absolute coordinate
	 * @param length a float for the length of the Rocker, greater than 0
	 * @param lever a float for the lever ratio, between 0 and 1
	 */
	public Rocker(ActorGame game, Vector position, float length, float lever) {
		
		// Call to the GameEntity constructor
		super(game, true, position);
		
		// Deals with incorrect parameters
		if(length <= 0 || lever < 0.0f || lever > 1.0f) {
			throw new IllegalArgumentException();
		}

		//Creation of the block (Physical & Graphic)
		Polygon blockPolygon = new Polygon( 
				new Vector(0.0f, 0.0f), 
				new Vector(blockWidth, 0.0f),
				new Vector(blockWidth/2, blockHeight));
		buildPart(blockPolygon, false, CATEGORY_SCENERY, MASK_SCENERY);
		blockGraphics = new ImageGraphics("metal.10.png", blockWidth, blockHeight);
		blockGraphics.setParent(this.getEntity());

		// Creation of the plank by its own constructor 
		plank = new Plank(game, false, new Vector(position.x + lever*length, position.y + blockHeight), length, lever, "wood.3.png");
		
		// Creation of the constraint between the block and the plank
		plank.attach(this.getEntity(), new Vector(blockWidth/2, blockHeight));
		
		// Adds this rocker to the game's Actor list
		game.addActor(this);
	}

	@Override
	public Transform getTransform() {
		return this.getEntity().getTransform();
	}

	@Override
	public Vector getVelocity() {
		return this.getEntity().getVelocity();
	}

	
	/**
	 * Draws the rocker in the canvas
     * @param canvas the canvas in which we want to draw
	 */
	@Override
	public void draw(Canvas canvas) {
			blockGraphics.draw(canvas);	
			plank.draw(canvas);
	}

	
	/**
	 * Destroys the rocker(block & plank)
	 */
	public void destroy() {
		super.destroy();
		plank.destroy();
	}

}
