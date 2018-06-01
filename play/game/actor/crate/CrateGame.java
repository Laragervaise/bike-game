package ch.epfl.cs107.play.game.actor.crate;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.general.Crate;
import ch.epfl.cs107.play.game.actor.general.Level;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Window;

public class CrateGame extends ActorGame{
	
	// We need a level for the CrateGame
	private Level myLevel = new Level() { 
		
		// Used to stock the ActorGame owner of the world
		private ActorGame game;

		/**
		 * Creates all the actors in the world.
		 * @param game the ActorGame owner of the world
		 */
		@Override
		public void createAllActors(ActorGame game) {
			this.game = game;
			
			new Crate(game, false, new Vector(0.0f, 5.0f));
			new Crate(game, false, new Vector(0.2f, 7.0f));
			new Crate(game, false, new Vector(2.0f, 6.0f));
			
		}

		
		/**
		 * Reset the level.
		 */
		@Override
		public void resetLevel() {
			this.destroy();
			this.createAllActors(this.game);;	
		}
		
		
		/**
		 * Reset the payLoad of the game.
		 */
		@Override
		public void resetPayLoad() {
			// Nothing to do	 for now
		}
		
	};

	
	/**
	 * Begin the CrateGame.
	 * @param window, the canvas in which we are working
	 * @param fileSystem, the FileSystem that we are working with
	 */
	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		super.begin(window, fileSystem);
		this.setLevel(myLevel);
		return true;
	}

	
	/**
	 * End the CrateGame.
	 */
	@Override
	public void end() {}

}
