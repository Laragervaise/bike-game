package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.general.Level;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import ch.epfl.cs107.play.math.RopeConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.WheelConstraintBuilder;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

public abstract class ActorGame implements Game{
    
	// Physics engine
    private World world;
    
    // File system
 	private FileSystem fileSystem ;

 	// Window
 	private Window window;
 	
 	private Level level;

    
    // Viewport properties
    private Vector viewCenter;
    private Vector viewTarget;
    private Positionable viewCandidate;
    private static final float VIEW_TARGET_VELOCITY_COMPENSATION = 0.2f;
    private static final float VIEW_INTERPOLATION_RATIO_PER_SECOND = 0.1f;
    private static final float VIEW_SCALE = 15.0f;
    
	/**
     * Initializes game state
     * @param window context to use, not null
     * @param fileSystem file system to use, not null
     * @return whether the game was successfully started
     */
    public boolean begin(Window window, FileSystem fileSystem) {
    	
	    	// Deals with incorrect parameters
	    	if(window == null || fileSystem == null) {
	    		throw new NullPointerException();
	    	}
    		
    		// Use Swing display
    		this.window = window;
        
        // File system definition
        this.fileSystem = fileSystem;
        
        // Create physics engine
        world = new World();
        
        // Note that you should use meters as unit
        world.setGravity(new Vector(0.0f, -9.81f));
        
        //Window positioning
        viewCenter = Vector.ZERO;
        viewTarget = Vector.ZERO;
        
        // Successfully initiated
        return true;
    }
    
    /**
     * Simulates a single time step.
     * @param deltaTime elapsed time since last update, in seconds
     */
    public void update(float deltaTime) {
    		
    		// Simulate physics
		world.update(deltaTime);
		
		// Updates the current Level
		level.update(deltaTime);
		
		// Update expected viewport center
		if (viewCandidate != null) {
			viewTarget = viewCandidate.getPosition().add(viewCandidate.getVelocity().mul(VIEW_TARGET_VELOCITY_COMPENSATION));
		}
		
		// Interpolate with previous location
		float ratio = (float) Math.pow(VIEW_INTERPOLATION_RATIO_PER_SECOND, deltaTime);
		viewCenter = viewCenter .mixed( viewTarget , 1.0f - ratio ) ;
		
		// Compute new viewport
		Transform viewTransform = Transform.I.scaled(VIEW_SCALE).translated(viewCenter);
		window.setRelativeTransform(viewTransform);
		
		// Draws the current Level in the window
		level.draw(window);
		
    }
    
    /** 
     * Cleans up things, called even if initialization failed
     */
    public abstract void end();
    
    /** 
     * Creates an entity
	 * @param fixed a boolean that indicates if it moves or not
     * @param position a Vector in absolute coordinate
     * @return the entity builded
     */
    public Entity createEntity(boolean fixed, Vector position) {
    	
        // To create an object, you need to create a builder and set the parameters
        EntityBuilder entityBuilder = world.createEntityBuilder();   
        entityBuilder.setFixed(fixed);
        entityBuilder.setPosition(position);
      
        // Once ready, the entity can be built and returned
        Entity entity = entityBuilder.build();
        return entity;
    }
    
    /** 
     * Creates an entity in a default position
	 * @param fixed a boolean that indicates if it moves or not
     * @return the entity builded
     */
    public Entity createEntity(boolean fixed) {
		// To create an object, you need to create a builder and set the parameters
    		EntityBuilder entityBuilder = world.createEntityBuilder();   
    		entityBuilder.setFixed(fixed);
  
    		// Once ready, the entity can be built and returned
    		Entity entity = entityBuilder.build();
    		return entity;
    }
    
    /** 
     * @return a WheelConstraintBuilder associated to the world's game
     */
    public WheelConstraintBuilder addWheelConstraintBuilder() {
    		return world.createWheelConstraintBuilder();
    }
    
    /** 
     * @return a RopeConstraintBuilder associated to the world's game
     */
    public RopeConstraintBuilder addRopeConstraintBuilder() {
    		return world.createRopeConstraintBuilder();
    }
    
    /** 
     * @return a RevoluteConstraintBuilder associated to the world's game
     */
    public RevoluteConstraintBuilder addRevoluteConstraintBuilder() {
    		return world.createRevoluteConstraintBuilder();
    }
    
    /** 
     * Adds an actor to the list
     * @param actor an Actor of the game
   	 */
    public void addActor(Actor newActor) {
    		level.addActor(newActor);
    	}
    
    /** 
     * Deletes an actor of the list
     * @param actor an Actor of the game
   	 * @return the access to the keyboard
   	 */
    public void removeActor(Actor actor) {
    		level.removeActor(actor);
    }
    
    /** 
   	 * @return the  access to the keyboard
   	 */
    public Keyboard getKeyboard(){ 
    		return window.getKeyboard();
    }
    
    /** 
	 * @return the access to the window used
	 */
    public Canvas getCanvas(){
    		return window;
    }
    
    /**
   	 * Destroys the entity associated to the wheel.
   	 */
    public void setViewCandidate(Positionable newCandidate) {
    		viewCandidate = newCandidate;
    }
    
    /** 
     * Sets the new level
	 * @param level next level
	 */
    public void setLevel(Level level) {
    		if(this.level != null) {
    			this.level.destroy();
    		}
    		this.level = level;
    		level.createAllActors(this);
    }
    
	/** 
	 * @return the level in which we are
	 */
    protected Level getLevel() {
    		return this.level;
    }
    
    /**
	 * Destroys the level
	 */
    public void destroy() {
    		level.destroy();
    		level = null;
    }
}
