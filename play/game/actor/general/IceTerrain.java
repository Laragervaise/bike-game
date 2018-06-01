package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.math.Polyline;

public class IceTerrain extends Terrain  {
	
	
	/**
	 * Creates a new terrain with zero friction
	 * @param game the ActorGame owner of this terrain.
	 * @param line a PolyLine that gives the shape of the terrain.
	 */
	public IceTerrain(ActorGame game, Polyline line) {
		
		// Call to the Terrain constructor
		super(game, line, 0.0f, 0.2f, Color.CYAN);
	}
}
