package ch.epfl.cs107.play.game.actor.general;

import java.util.ArrayList;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public abstract class Level implements Actor {

	// The list of the Actors in the Level
	private ArrayList<Actor> actors = new ArrayList<Actor>();

	// All Levels must implement a method which creates all the actors of the Level
	public abstract void createAllActors(ActorGame game);
	
	// All Levels must implement a method which resets the Level
	public abstract void resetLevel();
	
	// All Levels must implement a method which resets the PayLoad of the Level
	public abstract void resetPayLoad();

	//Adds an actor to the list
    public void addActor(Actor newActor) {
    		actors.add(newActor);
    }
    
    
    /**
     * Removes an actor from the actors list
     * @param actor the Actor to remove
     */
    public void removeActor(Actor actor) {
    		actor.destroy();
    		actors.remove(actor);
    }

    
    /**
     * Draws every Actors in the canvas
     */
	@Override
	public void draw(Canvas canvas) {
		for(int i=0; i < actors.size(); i++) {
			actors.get(i).draw(canvas);
		}
	}

	
	 /**
     * Updates every Actors in the world
     * @param deltaTime elapsed time since last update, in seconds
     */
	@Override
	public void update(float deltaTime) {
		for(int i=0; i < actors.size(); i++) {
			actors.get(i).update(deltaTime);
		}
	}


	@Override
	public Transform getTransform() {
		return null;
	}

	@Override
	public Vector getVelocity() {
		return null;
	}
	
	
	 /**
     * Destroys every Actors in the world
     */
	@Override
    public void destroy() {
    		for(Actor a : actors) {
    			a.destroy();
    		}
    		actors.clear();
    }
	
}
