package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.RopeConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Ball extends GameEntity implements Actor {
	
	// Categories and masks for the contact listener
	private final short CATEGORY_SCENERY = 0x00016;
	private final short MASK_SCENERY = -1;
	
	// Graphic part for the ball
	private ShapeGraphics ballGraphics;

	/**
	 * Creates a new ball in the game
	 * @param game the owner of the ball
     * @param position a Vector in absolute coordinate
	 * @param radius a float that gives the radius of the ball, greater than 0
	 */
	public Ball(ActorGame game, Vector position, float radius) {
		super(game, false, position);
		
		// Deals with incorrect parameters
		if(radius <= 0) {
			throw new IllegalArgumentException();
		}

		//Creation of the ball
		Circle circle = new Circle(radius);
		 buildPart(circle, false, CATEGORY_SCENERY, MASK_SCENERY);

		// Graphic part
		ballGraphics = new ShapeGraphics(circle, Color.GRAY, Color.BLACK, .05f, 1.0f, 0.0f);
		ballGraphics.setParent(this.getEntity());
		game.addActor(this);
	}

	/**
     * Creates a constraint between this ball and an other entity
     * @param block the entity we want our ball attached to
     * @param anchor a Vector in relative position that gives the point of attach
     * @param length a float that gives the maximal distance between the entity and the ball
     */
	public void attach(Entity block, Vector anchor, float length) {
		
		// Deals with incorrect parameters
		if(anchor == null) {
			throw new NullPointerException();
		} else if(length <= 0) {
			throw new IllegalArgumentException();
		}
		
		// Creates a new RopeConstraintBuilder
		RopeConstraintBuilder ropeConstraintBuilder = this.getOwner().addRopeConstraintBuilder();
		//Sets the parameters
		ropeConstraintBuilder.setFirstEntity(block); 
		ropeConstraintBuilder.setFirstAnchor(anchor); 
		ropeConstraintBuilder.setSecondEntity(this.getEntity()); 
		ropeConstraintBuilder.setSecondAnchor(Vector.ZERO); 
		ropeConstraintBuilder.setMaxLength(length); 
		ropeConstraintBuilder.setInternalCollision(true); 
		// Build the constraint
		ropeConstraintBuilder.build();
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
	 * Draws the ball
	 * @param canvas the canvas in which we want to draw
	 */
	@Override
	public void draw(Canvas canvas) {
		ballGraphics.draw(canvas);

	}
	
	/**
	 * Destroys the ball
	 */
	public void destroy() {
		super.destroy();
	}

}
