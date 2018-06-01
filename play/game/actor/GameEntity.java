package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.Part;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Shape;
import ch.epfl.cs107.play.math.Vector;

public abstract class GameEntity {
	
	private Entity entity;
	private ActorGame actorGame;
	
	/**
	 * Creates a new GameEntity
	 * @param game the owner of the entity
	 * @param fixed a boolean that indicates if it moves or not
     * @param position a Vector in absolute coordinate
	 */
	public GameEntity(ActorGame game, boolean fixed, Vector position) {
		if (game == null) {
			throw new NullPointerException();
		}
		if (fixed != true && fixed != false) {
			throw new IllegalArgumentException();
		}
		this.actorGame = game;
		this.entity = this.actorGame.createEntity(fixed, position);	
	}
	
	/**
	 * Creates a new GameEntity with a default position
	 * @param game the owner of the entity
	 * @param fixed a boolean that indicates if it moves or not
	 */
	public GameEntity(ActorGame game, boolean fixed) {
		if (game == null) {
			throw new NullPointerException();
		}
		if (fixed != true && fixed != false) {
			throw new IllegalArgumentException();
		}
		this.actorGame = game;
		this.entity = this.actorGame.createEntity(fixed);	
	}
	
	/**
	 * Destroys the entity
	 */
	public void destroy() {
		this.entity.destroy();
	}
	
	/** 
	 * @return the entity associated
	 */
	protected Entity getEntity() {
		return this.entity;
	}
	
	/** 
	 * @return the owner, an actor
	 */
	protected ActorGame getOwner() {		
		return this.actorGame;
	}
	
	/** 
	 * Builds a Part with a shape
	 * @param shape the shape of the Part we want to build
	 * @return the Part built
	 */
	public Part buildPart(Shape shape) {
		PartBuilder partBuilder = getEntity().createPartBuilder();
		
		partBuilder.setShape(shape);
		
		return partBuilder.build();
	}
	
	/** 
	 * Builds a Part with a shape and parameters.
	 * @param shape the shape of the object we want to build
	 * @param isGhost a boolean which is true if we want to not detect the contacts with this object
	 * @param category a short used for categorize the collisions between entities
	 * @param mask a short used to consider only collisions between specific entities
	 * @return the Part built
	 */
	// Builds a Part with a shape, friction and a collision filter
	public Part buildPart(Shape shape, boolean isGhost, short category, short mask) {
		PartBuilder partBuilder = getEntity().createPartBuilder();
		
		partBuilder.setShape(shape);
		partBuilder.setGhost(isGhost);
		partBuilder.setCollisionSignature(category);
		partBuilder.setCollisionEffect(mask);
		
		return partBuilder.build();
	}
}
