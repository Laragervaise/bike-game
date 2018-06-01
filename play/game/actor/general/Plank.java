package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Plank extends GameEntity implements Actor {
	
	// Categories and masks for the contact listener
	private final short CATEGORY_SCENERY = 0x00016;
	private final short MASK_SCENERY = -1;
	
	// Plank parameters
	private float plankWidth;
	private float plankHeight = 0.2f;
	private float lever;
	
	// Graphic part for the plank
	private ImageGraphics plankGraphics;

	/**
	 * Creates a new plank in the game
	 * @param game the owner of the plank
	 * @param fixed a boolean that indicates if it moves or not
     * @param position a Vector in absolute coordinate
	 * @param length a float for the length of the Rocker, greater than 0
	 * @param lever a float for the lever ratio, between 0 and 1
	 * @param name a String for the name of the image associated
	 */
	public Plank(ActorGame game, boolean fixed, Vector position, float length, float lever, String name) {
		
		// Call to the GameEntity constructor
		super(game, fixed, position);
		
		// Deals with incorrect parameters
		if(length <= 0 || lever < 0.0f || lever > 1.0f) {
			throw new IllegalArgumentException();
		} else if(name == null) {
			throw new NullPointerException();
		}
		
		// Initialization of the attributes
		this.plankWidth = length;
		this.lever = lever;
			
		//Creation of the plank
        Polygon plankPolygon = new Polygon( 
        		new Vector(0.0f, 0.0f), 
        		new Vector(plankWidth, 0.0f),
        		new Vector(plankWidth, plankHeight),
        		new Vector(0.0f, plankHeight) );
        buildPart(plankPolygon, false, CATEGORY_SCENERY, MASK_SCENERY).setFriction(0.8f);;
        
        // Graphic part
        plankGraphics = new ImageGraphics(name, plankWidth, plankHeight);
        plankGraphics.setParent(this.getEntity());
        
        // Adds this rocker to the game's Actor list
        game.addActor(this);
	}
	
	/**
	  * Creates a new wood plank in the game
	  * @param game the owner of the plank
	  * @param fixed a boolean that indicates if it moves or not
	  * @param position a Vector in absolute coordinate
	  * @param length a float for the length of the Rocker, greater than 0
	  * @param lever a float for the lever ratio, between 0 and 1
	  */
	public Plank(ActorGame game, boolean fixed, Vector position, float lever, float length) {
		this(game, fixed, position, length, lever, "wood.3.png");
	}
	
	
	/**
     * Creates a constraint between this plank and an other entity.
     * @param blck, the entity we want our plank attached to
     * @param achor a Vector in relative position that gives the point of attach
     */
	public void attach(Entity block, Vector anchor) {
		// Deals with incorrect parameters
		if(anchor == null) {
			throw new NullPointerException();
		} 
		
		// Creates a new RevoluteConstraintBuilder
        RevoluteConstraintBuilder revoluteConstraintBuilder = this.getOwner().addRevoluteConstraintBuilder();
        // Sets the parameters
        revoluteConstraintBuilder.setFirstEntity(block); 
        revoluteConstraintBuilder.setFirstAnchor(anchor);
        revoluteConstraintBuilder.setSecondEntity(this.getEntity()); 
        revoluteConstraintBuilder.setSecondAnchor(new Vector(lever*plankWidth, plankHeight/2));
        revoluteConstraintBuilder.setInternalCollision(true); 
        // Build the constraint
        revoluteConstraintBuilder.build();
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
		plankGraphics.draw(canvas);
	}
	
	/**
	 * Destroys the plank
	 */
	public void destroy() {
		super.destroy();
	}

}
