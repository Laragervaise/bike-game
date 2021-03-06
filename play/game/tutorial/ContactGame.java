package ch.epfl.cs107.play.game.tutorial;

import java.awt.Color;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.BasicContactListener;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

public class ContactGame implements Game{
	
	// Store context
    private Window window;
    
    // We need our physics engine
    private World world;
    
    // And we need to keep references on our game objects
    private Entity block;
    private Entity ball;
    private float blockWidth = 10.0f;
    private float blockHeight = 1.0f;
    private float ballRadius = 0.5f;
   
    // graphical representation of the bodies
    private ImageGraphics blockGraphics;
    private ShapeGraphics ballGraphics;
    
    //We need to use a Contact Listener
    private BasicContactListener contactListener;	
    
    // This event is raised when game has just started
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        
        // Store context
        this.window = window;
        
        // Create physics engine
        world = new World();
        
        // Note that you should use meters as unit
        world.setGravity(new Vector(0.0f, -9.81f));
        
        // To create an object, you need to use builders
        EntityBuilder entityBuilder = world.createEntityBuilder();
        PartBuilder partBuilder;
        
        //Creation of the block
        entityBuilder.setFixed(true);
        entityBuilder.setPosition(new Vector(-5.0f, -1.0f));
        block = entityBuilder.build();
        partBuilder = block.createPartBuilder();
        Polygon blockPolygon = new Polygon( 
        		new Vector(0.0f, 0.0f), 
        		new Vector(blockWidth, 0.0f),
        		new Vector(blockWidth, blockHeight),
        		new Vector(0.0f, blockHeight) );
        partBuilder.setShape(blockPolygon);
        partBuilder.setFriction(0.5f);
        partBuilder.build();
        
        //Creation of the ball
        entityBuilder.setFixed(false);
        entityBuilder.setPosition(new Vector(0.0f, 2.0f));
        ball = entityBuilder.build();
        partBuilder = ball.createPartBuilder();
        Circle circle = new Circle(ballRadius);
        partBuilder.setShape(circle);
        partBuilder.build();
        
        //To know if there is contact with the ball
        contactListener = new BasicContactListener(); 
        ball.addContactListener(contactListener);

        //Graphics creation
        blockGraphics = new ImageGraphics("stone.broken.4.png", blockWidth, blockHeight);
        ballGraphics = new ShapeGraphics(circle,Color.BLUE, Color.BLUE, .1f, 1, 0);
        blockGraphics.setParent(block);
        ballGraphics.setParent(ball);
        
        // Successfully initiated
        return true;
    }

    // This event is called at each frame
    @Override
    public void update(float deltaTime) {  
    		// contactListener.getEntities() returns the list of entities in	collision with ball
    		int numberOfCollisions = contactListener.getEntities().size();
    		if (numberOfCollisions > 0){
    			ballGraphics.setFillColor(Color.WHITE);
    		} 
    		
    		ballGraphics.draw(window);

    	
    		// Simulate physics
    		// Our body is fixed, though, nothing will move
    		world.update(deltaTime);
    		
    		// we must place the camera where we want
    		// We will look at the origin (identity) and increase the view size a bit
    		window.setRelativeTransform(Transform.I.scaled(10.0f));
    		
    		// We can render our scene now,
    		blockGraphics.draw(window);
    		ballGraphics.draw(window);
    }

    // This event is raised after game ends, to release additional resources
    @Override
    public void end() {
        // Empty on purpose, no cleanup required yet
    }
    
}
