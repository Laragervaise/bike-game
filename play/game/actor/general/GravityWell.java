package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Shape;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class GravityWell extends GameEntity implements Actor{
	
	// Categories and masks for the contact listener
	private final short CATEGORY_WELL = 0x0016;
	private final short MASK_WELL = 0b1;
	
	// Graphics of the well
	private ShapeGraphics wellGraphics;
	
	// Well parameters
	private Vector force;
	
	// Indicates if there is a collision with the biker
	private boolean touch;
	
	// Entity of the biker;
	private Entity other;
	
	ContactListener listener = new ContactListener() { 
		@Override
		public void beginContact(Contact contact) { 
			touch = true;
			other = contact.getOther().getEntity();
		}
		
		@Override
		public void endContact(Contact contact) {
			touch = false;
		}
	
	};
	
	
	/**
	 * Creates a new gravity well
	 * @param game the owner of the rocker
     * @param position a Vector in absolute coordinate
	 * @param shape a Shape to assign to the well
	 * @param force the Vector force to apply to the other entity
	 */
	public GravityWell(ActorGame game, Vector position, Shape shape, Vector force) {
		
		// Call to the GameEntity constructor
		super(game, true, position);
		
		// Deals with incorrect parameters
		if(shape == null || force == null) {
			throw new NullPointerException();
		}
		
		// Initialize the attributes
		this.force = force;
		
		// Build the entity of the shape
		buildPart(shape, true, CATEGORY_WELL, MASK_WELL);
		
		//Add contact listener
		this.getEntity().addContactListener(listener);
        
        // Graphic part
		wellGraphics = new ShapeGraphics(shape, Color.GRAY, Color.BLACK, .03f, 0.5f, -1.0f);
		wellGraphics.setParent(this.getEntity());
		
		// Adds this rocker to the game's Actor list
        game.addActor(this);
	}
	
	
	/**
	 * Creates a new rectangle gravity well.
	 * @param game the owner of the rocker
     * @param position a Vector in absolute coordinates
	 * @param width the width of the rectangle, different than 0
	 * @param height the height of the rectangle, different than 0
	 * @param force the Vector force to apply to the other entity
	 */
	public GravityWell(ActorGame game, Vector position, float width, float height, Vector force) {
		this(game, position, new Polygon( new Vector(0.0f, 0.0f), 
        									 new Vector(width, 0.0f),
        									 new Vector(width, height),
        									 new Vector(0.0f, height) ), force);	
		// Deals with incorrect parameters
		if(width == 0.0f || height == 0.0f) {
			throw new IllegalArgumentException();
		}
	}
	

	/**
	 * Updates the gravity well.
	 * @param deltaTime elapsed time since last update, in seconds
	 */
	public void update(float deltaTime) {
		if(touch) {
			other.applyForce(force, null);
		}
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
	 * Draws the Gravity Well in the canvas
     * @param canvas the canvas in which we want to draw
	 */
	@Override
	public void draw(Canvas canvas) {
		if(this.getEntity().isAlive()) {
			wellGraphics.draw(canvas);
		}
	}
	
	/**
	 * Destroys the gravity well
	 */
	public void destroy() {
		super.destroy();
	}
	
}
