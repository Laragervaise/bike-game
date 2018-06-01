package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Trigger extends GameEntity implements Actor{
	
	// Default categories and masks for the contact listener
	private final static short CATEGORY_TRIGGER = 0x0012;
	private final static short MASK_TRIGGER = 0b001;
	//Default parameters of the trigger
	private final static float DETECTION_RADIUS = 1.6f;
	
	// Graphic image for the trigger
	private ImageGraphics triggerGraphics;
	
	// Indicates if there is contact with the bike
	private boolean touch;
	
	// Create a new contact listener for the trigger
	ContactListener listener = new ContactListener() { 
		@Override
		public void beginContact(Contact contact) { 
			touch = true;
		}
		
		@Override
		public void endContact(Contact contact) {
			touch = false;
		}
	
	};
	
	/**
	 * Creates a new Trigger
	 * @param game the ActorGame owner of the trigger
	 * @param position a Vector in absolute coordinate
	 * @param detectionRadius a float that gives the radius of detection around the trigger, greater than 0
	 * @param name a String giving the name of the image associated to the trigger
	 * @param category the category for the contact listener
	 * @param mask the mask for the contact listener
	 */
	public Trigger(ActorGame game, Vector position, float detectionRadius, String name, short category, short mask) {
		
		// Call to the GameEntity constructor
		super(game, true, position);
		
		// Deals with incorrect parameters
		if(detectionRadius <= 0) {
			throw new IllegalArgumentException();
		} else if(name == null) {
			throw new NullPointerException();
		}
		
		//Creation of the detection circle
		Circle circle = new Circle(detectionRadius);
		buildPart(circle, true, category, mask);
		
		//Add contact listener
		this.getEntity().addContactListener(listener);
        
        // Graphic part
        triggerGraphics = new ImageGraphics(name, 1.0f, 1.0f, new Vector(0.0f, 0.0f));
        triggerGraphics.setParent(this.getEntity());
        
        // Adds this trigger to the game's Actor list
		game.addActor(this);
	}
	
	
	/**
	 * Creates a new trigger with default parameters
	 * @param game the ActorGame owner of the trigger
	 * @param position a Vector in absolute coordinate
	 * @param name a String giving the name of the image associated to the trigger
	 */
	public Trigger(ActorGame game, Vector position, String name) {
		this(game, position, DETECTION_RADIUS, name, CATEGORY_TRIGGER, MASK_TRIGGER);
	}


	/** 
	 * @return the transform of the trigger in absolute coordinates
	 */
	@Override
	public Transform getTransform() {
		return this.getEntity().getTransform();
	}
	

	/** 
	 * @return the velocity of the trigger
	 */
	@Override
	public Vector getVelocity() {
		return this.getEntity().getVelocity();
	}

	
	/**
     * Draws the trigger with the associated image
     * @param canvas the canvas in which we want to draw
     */
	@Override
	public void draw(Canvas canvas) {
		triggerGraphics.draw(canvas);
	}
	
	
	/**
	 * @return touch, the boolean that is used for the contact listener
	 */
	public boolean getTouch() {
		return touch;
	}
	
	
	/**
	 * Set the image of a trigger
	 * @param name gives the name of the new image associated
	 */
	public void setImage(String name) {
		triggerGraphics.setName(name);
	}

}
