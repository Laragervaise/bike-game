package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.math.Polyline;

public class ReboundTerrain extends Terrain {
	
	
	/**
	 * Creates a new terrain with a great force restitution coefficient
	 * @param game the ActorGame owner of this terrain.
	 * @param line a PolyLine that gives the shape of the terrain.
	 */
	public ReboundTerrain(ActorGame game, Polyline line) {
		
		// Call to the Terrain constructor
		super(game, line, 1.5f, 1.5f, Color.RED);
	}
}
