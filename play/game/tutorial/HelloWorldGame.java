package ch.epfl.cs107.play.game.tutorial;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

/**
 * Simple game, to show basic the basic architecture
 */
public class HelloWorldGame implements Game {

    // Store context
    private Window window;
    
    // We need our physics engine
    private World world;
    
    // And we need to keep references on our game objects
    private Entity body;
    
    // graphical representation of the body
    private ImageGraphics graphic1;
    
    // graphical representation of the body
    private ImageGraphics graphic2;
    

    // This event is raised when game has just started
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        
        // Store context
        this.window = window;
        
        // Create physics engine
        world = new World();
        
        // Note that you should use meters as unit
        world.setGravity(new Vector(0.0f, -9.81f));
        
        // To create an object, you need to use a builder
        EntityBuilder entityBuilder = world.createEntityBuilder();
        
        // Make sure this does not move
        entityBuilder.setFixed(true);
        
        // This helps you define properties, like its initial location
        entityBuilder.setPosition(new Vector(1.f, 1.f));
        
        // Once ready, the body can be built
        body = entityBuilder.build();
        
        graphic1 = new ImageGraphics("stone.broken.4.png", 1, 1);
        graphic2 = new ImageGraphics("bow.png", 1, 1);
        
        // Transparency can be chosen for each drawing (0.0 - transparent, 1.0 - opaque)
        graphic1.setAlpha(1.0f);
        graphic2.setAlpha(1.0f);
        
        // Additionally, you can choose a depth when drawing
        // Therefore, you could draw behind what you have already done
        graphic1.setDepth(0.0f);
        graphic2.setDepth(1.0f);
        
        graphic1.setParent(body);
        graphic2.setParent(body);
        
        // Successfully initiated
        return true;
    }

    // This event is called at each frame
    @Override
    public void update(float deltaTime) {  
    		// Game logic comes here 
    		// Nothing to do, yet
    	
    		// Simulate physics
    		// Our body is fixed, though, nothing will move
    		world.update(deltaTime);
    		
    		// we must place the camera where we want
    		// We will look at the origin (identity) and increase the view size a bit
    		window.setRelativeTransform(Transform.I.scaled(10.0f));
    		
    		// We can render our scene now,
    		graphic1.draw(window);
    		graphic2.draw(window);
    }

    // This event is raised after game ends, to release additional resources
    @Override
    public void end() {
        // Empty on purpose, no cleanup required yet
    }
    
}
