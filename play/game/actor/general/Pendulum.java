package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Pendulum extends GameEntity implements Actor {

	// Categories and masks for the contact listener
	private final short CATEGORY_SCENERY = 0x00016;
	private final short MASK_SCENERY = -1;

	// Graphics of the block associated
	private ImageGraphics blockGraphics;

	// Ball associated to the pendulum
	private Ball ball;

	/**
	 * Creates a new pendulum in the game
	 * @param game the owner of the wheel
     * @param position a Vector in absolute coordinate
	 * @param length a float that gives the length of the rope, greater than 0
	 * @param ballRadius a float that gives the radius of the ball associated, greater than 0
	 */
	public Pendulum(ActorGame game, Vector position, float length, float ballRadius) {
		
		// Call to the GameEntity constructor
		super(game, true, position);
		
		// Deals with incorrect parameters
		if(length <= 0 || ballRadius <=0) {
			throw new IllegalArgumentException();
		}

		//Creation of the block (Physical & graphics)
		Polygon blockPolygon = new Polygon( 
				new Vector(0.0f, 0.0f), 
				new Vector(1.0f, 0.0f),
				new Vector(1.0f, 1.0f),
				new Vector(0.0f, 1.0f) );
		buildPart(blockPolygon, false, CATEGORY_SCENERY, MASK_SCENERY);
		blockGraphics = new ImageGraphics("stone.1.png", 1.0f, 1.0f);
		blockGraphics.setParent(this.getEntity());

		// Creation of the ball by its own constructor 
		ball = new Ball(game, new Vector(position.x - length, position.y), ballRadius);

		// Creation of the constraint between the block and the plank
		ball.attach(this.getEntity(), new Vector(0.5f, 0.5f), length);

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
	 * Draws the pendulum (block, ball & rope)
	 * @param canvas the canvas in which we want to draw
	 */
	@Override
	public void draw(Canvas canvas) {
		if(getEntity().isAlive()) {
			blockGraphics.draw(canvas);
			ball.draw(canvas);
			Polyline rope = new Polyline(this.getTransform().onPoint(0.5f, 0.5f), ball.getPosition());
			canvas.drawShape(rope, Transform.I, Color.GRAY, Color.GRAY, 0.1f, 1.0f, -1.0f);	
		}
	}
	
	
	/**
	 * Destroys the pendulum (block, ball & rope)
	 */
	public void destroy() {
		super.destroy();
		ball.destroy();
	}

}
