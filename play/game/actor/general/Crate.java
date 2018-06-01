package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Crate extends GameEntity implements Actor {
	
	// Categories and masks for the contact listener
	private final short CATEGORY_SCENERY = 0x00016;
	private final short MASK_SCENERY = -1;
	private final float CRATE_FRICTION = 30f;
	
	// Crate parameters
	private float crateWidth;
	private float crateHeight;
	
	// Graphic part for the ball
	private ImageGraphics crateGraphics;

	/**
	 * Creates a new crate in the game
	 * @param game the owner of the crate
	 * @param fixed a boolean that indicates if it moves or not
     * @param position a Vector in absolute coordinate
	 * @param width a float for the width of the crate, different than 0
	 * @param height a float for the height of the crate, different than 0
	 * @param name a String for the name of the image associated
	 */
	public Crate(ActorGame game, boolean fixed, Vector position, float width, float height, String name) {
		super(game, fixed, position);
		
		// Deals with incorrect parameters
		if(width == 0.0f || height == 0.0f) {
			throw new IllegalArgumentException();
		} else if(name == null) {
			throw new NullPointerException();
		}
		
		this.crateWidth = Math.abs(width);
		this.crateHeight = Math.abs(height);
			
		//Creation of the crate
		Polygon cratePolygon = new Polygon( 
        		new Vector(0.0f, 0.0f), 
        		new Vector(crateWidth, 0.0f),
        		new Vector(crateWidth, crateHeight),
        		new Vector(0.0f, crateHeight) );
		buildPart(cratePolygon, false, CATEGORY_SCENERY, MASK_SCENERY).setFriction(CRATE_FRICTION);
        
        // Graphic part
        crateGraphics = new ImageGraphics(name, crateWidth, crateHeight);
        crateGraphics.setParent(this.getEntity());
        game.addActor(this);
	}
	
	/**
	  * Creates a new wood crate in the game
	  * @param game the owner of the plank
	  * @param fixed a boolean that indicates if it moves or not
	  * @param position a Vector in absolute coordinate
	  */
	public Crate(ActorGame game, boolean fixed, Vector position) {
		this(game, fixed, position, 1.0f, 1.0f, "box.4.png");
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
	 * Draws the plank in the canvas
     * @param canvas the canvas in which we want to draw
	 */
	@Override
	public void draw(Canvas canvas) {
		if(getEntity().isAlive()) {
			crateGraphics.draw(canvas);
		}
	}
	
	/**
	 * Destroys the plank
	 */
	public void destroy() {
		super.destroy();
	}

}
