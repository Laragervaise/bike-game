package ch.epfl.cs107.play.game.actor.bike;

import java.awt.Color;
import java.awt.event.KeyEvent;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.game.actor.general.Wheel;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Bike extends GameEntity implements Actor {

	// Constants speeds
	private static final float MAX_WHEEL_SPEED = 25.0f;
	private static final float MAX_BIKE_ANGULAR_SPEED = 23.0f;

	// Constants used to draw the biker
	private static final float HEAD_RADIUS = 0.2f;
	private static final float THICKNESS = 0.1f;
	private static final float ALPHA_HITBOX = 0.0f; // Increase if we want to see the hitbox
	private static final Color COLOR_BIKER = Color.BLACK;
	private static final float PEDAL_RADIUS = 0.15f;

	// Categories and masks for the contact listener
	private final static short CATEGORY_BIKER = 0x0001;
	private final static short MASK_BIKER = -1;
	private final static short CATEGORY_FINISH = 0x0008;

	// Indicates the bike orientation : true if the bike is facing right
	private boolean right;
	// Indicates when the bike is hit by a non-ghost entity
	private boolean hit;
	// Indicates if the level is won (for victory sign)
	private boolean win;
	// Indicates if the breaks are on or not
	private boolean breaks = true;

	// Wheels and their parameters
	private Wheel leftWheel;
	private Wheel rightWheel;
	private static final float WHEEL_RADIUS = 0.55f;
	
	// Graphic part for the hitbox
	private ShapeGraphics hitboxGraphics;

	// Create a new contact listener for the hitbox
	ContactListener listener = new ContactListener() { 
		
		@Override
		public void beginContact(Contact contact) { 
			
			// Contact is with a finish line
			if(contact.getOther().isGhost() && contact.getOther().getCollisionSignature() == CATEGORY_FINISH) {
				win = true;	
			// Contact is with a ghost entity
			} else if (contact.getOther().isGhost()) {
				return ;
			} else {
				hit = true;
			}
		}

		@Override
		public void endContact(Contact contact) {}
		
	};
	
	
	/**
     * Creates a new bike.
     * @param game the owner of the bike
     * @param position a Vector in absolute coordinate
     * @param right a boolean that indicates the orientation of the bike
     */
	public Bike(ActorGame game, Vector position, boolean right) {
		
		// Call to the GameEntity constructor
		super(game, false, position);

		// Initialization of the attributes
		this.right = right;
		hit = false;
		win = false;

		// Creation of the hitbox (physical & graphic)
		Polygon polygon = new Polygon( 	0.0f, 0.5f,
										0.5f, 1.0f,
										0.0f, 2.0f,
										-0.5f, 1.0f	);	
		buildPart(polygon, true, CATEGORY_BIKER, MASK_BIKER);
		hitboxGraphics = new ShapeGraphics(polygon, Color.RED, Color.RED, 0.0f, ALPHA_HITBOX, 0.0f);
		hitboxGraphics.setParent(this.getEntity());
		
		// Adds the contact listener to the hitbox
		this.getEntity().addContactListener(listener);
		
		// Creation of both wheels by their own constructor 
		leftWheel = new Wheel(game, new Vector(position.x - 1.0f, position.y + 0.f), WHEEL_RADIUS, "explosive.11.png");
		rightWheel = new Wheel(game, new Vector(position.x + 1.0f, position.y + 0.f), WHEEL_RADIUS, "explosive.11.png");
		
		// Creation of the constraint between the wheels and the hitbox
		leftWheel.attach(this.getEntity(), new Vector(-1.0f, 0.0f), new Vector(-0.5f, -1.0f));
		rightWheel.attach(this.getEntity(), new Vector(1.0f, 0.0f), new Vector(0.5f, -1.0f));

		// Adds this bike to the game's Actor list
		game.addActor(this);
	}
	
	
	/**
     * Updates the bike.
     * @param deltaTime elapsed time since last update, in seconds
     */
	@Override
	public void update(float deltaTime) {

		// No update if the game is won and the bike has to break
		if(win)	{
			if(right) {
				leftWheel.power(0);
			} else {
				rightWheel.power(0);
			}
			breaks = true;
			return;
		}

		// A collision with a scenery entity leads to the removal of the bike
		if(hit) {
			this.getOwner().removeActor(this);
		}

		/** Reverse the bike orientation if the key SPACE is pressed
		 * 	and stop the bike if it is held down.
		 */
		if (getOwner().getKeyboard().get(KeyEvent.VK_SPACE).isPressed()) {
			right= !right;
		} else if (getOwner().getKeyboard().get(KeyEvent.VK_SPACE).isDown()) {
			leftWheel.power(0);
			rightWheel.power(0);
		
		// Stop the bike if the key DOWN is held down
		} else if (getOwner().getKeyboard().get(KeyEvent.VK_DOWN).isDown()) { 
			leftWheel.power(0);
			rightWheel.power(0);
			breaks = true;
			
		// Speed up the bike if the key UP is held down (depends of the orientation)
		} else if (getOwner().getKeyboard().get(KeyEvent.VK_UP).isDown()) {
			breaks = false;
			if(right) {
				if(leftWheel.getSpeed() > - MAX_WHEEL_SPEED) {
					leftWheel.power(-MAX_WHEEL_SPEED);		
				}
			} else {
				if(rightWheel.getSpeed() < MAX_WHEEL_SPEED) {
					rightWheel.power(MAX_WHEEL_SPEED);
				}
			}
			
		// By default, the bike (wheels) has to relax
		} else {
			leftWheel.relax();
			rightWheel.relax();
		}

		// Make the right wheel go up if the key LEFT is held down
		if (getOwner().getKeyboard().get(KeyEvent.VK_LEFT).isDown()) { 
			getEntity().applyAngularForce(MAX_BIKE_ANGULAR_SPEED);
			
		// Make the right wheel go up if the key LEFT is held down
		} else if (getOwner().getKeyboard().get(KeyEvent.VK_RIGHT).isDown()) {
			getEntity().applyAngularForce(-MAX_BIKE_ANGULAR_SPEED);
		}	
	}
	
	
	/** 
	 * @return the transform of the bike in absolute coordinates
	 */
	@Override
	public Transform getTransform() {
		return this.getEntity().getTransform();
	}
	

	/** 
	 * @return the velocity of the bike
	 */
	@Override
	public Vector getVelocity() {
		return this.getEntity().getVelocity();
	}
	
	
	/**
     * Draws the biker and the hitbox in the canvas
     * @param canvas the canvas in which we want to draw
     */
	@Override
	public void draw(Canvas canvas) {
		hitboxGraphics.draw(canvas);
		drawBiker(canvas);		
	}
	
	
	/**
     * Draws the biker's body parts in the canvas using each part's location
     * @param canvas the canvas in which we want to draw
     */
	public void drawBiker(Canvas canvas) {
		// The transform of the bike in absolute coordinates
		Transform t = this.getTransform();
		
		// Draws the biker's head as a circle with HEAD_RADIUS as radius
		Circle head = new Circle(HEAD_RADIUS, getHeadLocation());
		canvas.drawShape(head, t, COLOR_BIKER, COLOR_BIKER, THICKNESS, 1.0f, 0.0f);
		
		// Draws body parts using THICKNESS and location ofs pecific points
		Polyline arm = new Polyline(getShoulderLocation(), getHandLocation());
		canvas.drawShape(arm, t, COLOR_BIKER, COLOR_BIKER, THICKNESS, 1.0f, 0.0f);
		
		if (win) {
			Polyline secondArm = new Polyline(getShoulderLocation(), getSecondHandLocation());
			canvas.drawShape(secondArm, t, COLOR_BIKER, COLOR_BIKER, THICKNESS, 1.0f, 0.0f);
		}
		
		Polyline body = new Polyline(getShoulderLocation(), getButtLocation());
		canvas.drawShape(body, t, COLOR_BIKER, COLOR_BIKER, THICKNESS, 1.0f, 0.0f);
		
		Polyline leftThigh = new Polyline(getButtLocation(), getLeftKneeLocation());
		canvas.drawShape(leftThigh, t, COLOR_BIKER, COLOR_BIKER, THICKNESS, 1.0f, 0.0f);
		
		Polyline rightThigh = new Polyline(getButtLocation(), getRightKneeLocation());
		canvas.drawShape(rightThigh, t, COLOR_BIKER, COLOR_BIKER, THICKNESS, 1.0f, 0.0f);
		
		Polyline leftLeg = new Polyline(getLeftKneeLocation(), getLeftFootLocation());
		canvas.drawShape(leftLeg, t, COLOR_BIKER, COLOR_BIKER, THICKNESS, 1.0f, 0.0f);
		
		Polyline rightLeg = new Polyline(getRightKneeLocation(), getRightFootLocation());	
		canvas.drawShape(rightLeg, t, COLOR_BIKER, COLOR_BIKER,THICKNESS, 1.0f, 0.0f);	
	}
	

	/**
     * @return the head's location to draw the biker
     */
	private Vector getHeadLocation() {
		if (right) {
			if (win) {
				return new Vector(-0.25f, 1.85f); 
			} else {
				return new Vector(0.0f, 1.75f); 
			}
		} else {
			if (win) {
				return new Vector(0.25f, 1.85f); 
			} else {
				return new Vector(0.0f, 1.75f); 
			}
		}
	}
	
	
	/**
     * @return the shoulder's location to draw the biker
     */
	private Vector getShoulderLocation() {
		if (right) {
			if (win) {
				return new Vector(-0.3f, 1.55f); 
			} else {
				return new Vector(-0.1f, 1.55f); 
			}
		}
		else {
			if (win) {
				return new Vector(0.3f, 1.55f); 
			} else {
				return new Vector(0.1f, 1.55f); 
			}
		}
	}
	
	
	/**
	 * @return the hand's location to draw the biker
	 */
	private Vector getHandLocation() {
		if (right) {
			return new Vector(0.4f, 1.0f); 
		} else {
			return new Vector(-0.4f, 1.0f); 
		}
	}

	
	/**
     * @return the second hand's location to draw the biker when he wins
     */
	private Vector getSecondHandLocation() {
		if (right) {
			return new Vector(-0.2f, 2.4f);
		} else {
			return new Vector(0.2f, 2.4f); 	
		}
	}
	
	
	/**
     * @return the butt's location to draw the biker
     */
	private Vector getButtLocation() {
		if (right) {
			return new Vector(-0.5f, 1.0f); 
		} else {
			return new Vector(0.5f, 1.0f); 
		}
	}
	
	
	/**
     * @return the angular speed of the active wheel to draw the biker's feet
     */
	private float getAngularSpeed() {
		if (breaks) {
			return 0.f;
		} 
		
		if (right) {									
			return -leftWheel.getSpeed();
		} else {
			return -rightWheel.getSpeed();
		}
	}

	
	/**
     * @return the left knee's location to draw the biker
     */
	private Vector getLeftKneeLocation() {
		return new Vector(PEDAL_RADIUS*(float)Math.cos(getAngularSpeed()), 
				0.75f + PEDAL_RADIUS*(float)Math.sin(getAngularSpeed())); 
	}

	
	/**
     * @return the right knee's location to draw the biker
     */
	private Vector getRightKneeLocation() {
		return new Vector(PEDAL_RADIUS*(float)Math.cos(getAngularSpeed() + Math.PI), 
				0.75f + PEDAL_RADIUS*(float)Math.sin(getAngularSpeed() + Math.PI));
	}
	
	
	/**
     * @return the left foot's location to draw the biker
     */
	private Vector getLeftFootLocation() {

		return new Vector(PEDAL_RADIUS*(float)Math.cos(getAngularSpeed()), 
				PEDAL_RADIUS*(float)Math.sin(getAngularSpeed())); 
	}
	
	
	/**
     * @return the right foot's location to draw the biker
     */
	private Vector getRightFootLocation() {
		return new Vector(PEDAL_RADIUS*(float)Math.cos(getAngularSpeed() + Math.PI), 
				PEDAL_RADIUS*(float)Math.sin(getAngularSpeed()+ Math.PI));
	}
	
	/** 
	 * @return the boolean hit, attribute of the bike
	 */
	public boolean getHit() {
		return hit;
	}
	
	
	/** 
	 * Sets the boolean hit, attribute of the bike
	 * @param hit, the new boolean value to assign
	 */
	protected void setHit(boolean hit) {
		this.hit = hit;
	}	
	

	/** 
	 * Destroys the associated entities
	 */
	public void destroy() {
		super.destroy();
		leftWheel.destroy();
		rightWheel.destroy();
	}

}
