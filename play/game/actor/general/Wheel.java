package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.WheelConstraint;
import ch.epfl.cs107.play.math.WheelConstraintBuilder;
import ch.epfl.cs107.play.window.Canvas;

public class Wheel extends GameEntity implements Actor  {
	
	// Categories and masks for the contact listener
	private final short CATEGORY_WHEEL = 0x0002;
	private final short MASK_WHEEL = -1;
	
	// Constants for a wheel
	private final float WHEEL_FRICTION = 30.f;
	
	// Graphic part for the wheel
	private ImageGraphics wheelGraphics;
	// Used to stock the constraint associated to the wheel
	private WheelConstraint constraint;

	
	/**
     * Creates a new wheel
     * @param game the owner of the wheel
     * @param position a Vector in absolute coordinate
     * @param radius the radius of the wheel, not negative
     * @param name a string that indicates the image which we'll draw for the wheel
     */
	public Wheel(ActorGame game, Vector position, float radius, String name) {
		
		// Call to the GameEntity constructor
		super(game, false, position);
		
		// Deals with incorrect parameters
		if(radius <= 0) {
			throw new IllegalArgumentException();
		} else if(name == null) {
			throw new NullPointerException();
		}
		
		//Creation of the wheel (physical & graphic)
		Circle circle = new Circle(radius);
        buildPart(circle, false, CATEGORY_WHEEL, MASK_WHEEL).setFriction(WHEEL_FRICTION);;
        wheelGraphics = new ImageGraphics(name, 2*radius, 2*radius, new Vector(0.5f, 0.5f));
        wheelGraphics.setParent(this.getEntity());
        
        // Adds this wheel to the game's Actor list
        game.addActor(this);
	}
	
	
	/**
     * Creates a constraint between this wheel and an other entity
     * @param vehicle, the entity we want our wheel attached to
     * @param achor a Vector in relative position that gives the point of attach
     * @param axis a Vector that gives the axis of the constraint
     */
	public void attach(Entity vehicle, Vector anchor, Vector axis) {
		// Create a WheelConstraintBuilder
		WheelConstraintBuilder constraintBuilder = this.getOwner().addWheelConstraintBuilder();
		// Set the entities and the anchors
		constraintBuilder.setFirstEntity(vehicle);
		constraintBuilder.setFirstAnchor(anchor);
		constraintBuilder.setSecondEntity(this.getEntity());
		constraintBuilder.setSecondAnchor(Vector.ZERO);
		// Set parameters of the constraint builder
		constraintBuilder.setAxis(axis);
		constraintBuilder.setFrequency(3.0f);
		constraintBuilder.setDamping(1.0f);
		constraintBuilder.setMotorMaxTorque(15.0f);
		constraintBuilder.setInternalCollision(false);
		// Build the Constraint
		this.constraint = constraintBuilder.build();
	}

	
	/**
	 * Powers this wheel
	 * @param speed a float that gives the speed of the constraint's motor
	 */
	public void power(float speed) {
		this.constraint.setMotorEnabled(true);
		this.constraint.setMotorSpeed(speed);
	}
	
	
	/**
	 * Disable the constraint's motor
	 */
	public void relax() {
		constraint.setMotorEnabled(false);
	}
	
	
	/**
	 * Destroy the constraint associated to the wheel
	 */
	public void detach() {
		constraint.destroy();
	}
	
	
	/**
	 * @return relative rotation speed, in radians per second 
	 */
	public float getSpeed() {
		return constraint.getSecondBody().getAngularVelocity()
						- constraint.getFirstBody().getAngularVelocity();
	}

	/** 
	 * @return the transform of the wheel in absolute coordinates
	 */
	@Override
	public Transform getTransform() {
		return this.getEntity().getTransform();
	}
	

	/** 
	 * @return the velocity of the wheel
	 */
	@Override
	public Vector getVelocity() {
		return this.getEntity().getVelocity();
	}
	
	/**
	 * Draws the image associated to the wheel
	 * @param canvas the canvas in which we want to draw
	 */
	@Override
	public void draw(Canvas canvas) {
		if(this.getEntity().isAlive()) {
			wheelGraphics.draw(canvas);
		}
	}
	
	/**
	 * Destroys the entity associated to the wheel
	 */
	public void destroy() {
		this.detach();
		super.destroy();	
	}

}
