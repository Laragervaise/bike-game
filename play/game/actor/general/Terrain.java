package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.Part;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Terrain extends GameEntity implements Actor {
	
	// Categories and masks for the contact listener
	private final short CATEGORY_SCENERY = 0x0016;
	private final short MASK_SCENERY = -1;
	
	// Graphic part for the terrain
	private ShapeGraphics terrainGraphics;
	
	/**
	 * Creates a new Terrain in the game
	 * @param game the ActorGame owner of this terrain
	 * @param line a PolyLine that gives the shape of the terrain
	 * @param friction a float that gives the friction of the terrain
	 * @param rebound a float that gives the restitution coefficient of the terrain
	 * @param color a Color for the terrain
	 */
	public Terrain(ActorGame game, Polyline line, float friction, float rebound, Color color) {
		
		// Call to the GameEntity constructor
		super(game, true, new Vector(0.0f, 0.0f));
		
		// Deals with incorrect parameters
		if(color == null) {
			throw new NullPointerException();
		}
		
		//Creation of the terrain
		Part part = buildPart(line, false, CATEGORY_SCENERY, MASK_SCENERY);
        part.setFriction(friction);
        part.setRestitution(rebound);
        
        // Graphic part
        terrainGraphics = new ShapeGraphics(line, Color.GRAY, color, 0.15f);
        terrainGraphics.setParent(this.getEntity());
        
        // Adds this terrain to the game's Actor list
        game.addActor(this);
	}
	
	/**
	 * Creates a new Terrain with default parameters
	 * @param game the ActorGame owner of this terrain
	 * @param line a PolyLine that gives the shape of the terrain
	 */
	public Terrain(ActorGame game, Polyline line) {
		this(game, line, 30f, 0.1f , Color.BLACK);
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
     * Draws the terrain in the canvas
     * @param canvas the canvas in which we want to draw
     */
	@Override
	public void draw(Canvas canvas) {
		terrainGraphics.draw(canvas);	
	}

}
