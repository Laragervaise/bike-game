package ch.epfl.cs107.play.game.tutorial;

import java.awt.event.KeyEvent;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

public class ScaleGame implements Game{
	
	// Store context
    private Window window;
    
    // We need our physics engine
    private World world;
    
    // And we need to keep references on our game objects
    private Entity block;
    private Entity plank;
    private Entity ball;
    private float blockWidth = 10.0f;
    private float blockHeight = 1.0f;
    private float plankWidth = 5.0f;
    private float plankHeight = 0.2f;
    private float ballRadius = 0.5f;
   
    // graphical representation of the bodies
    private ImageGraphics blockGraphics;
    private ImageGraphics plankGraphics;
    private ImageGraphics ballGraphics;
    
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
        partBuilder.setFriction(0.8f);
        partBuilder.build();
        
        //Creation of the plank
        entityBuilder.setFixed(false);
        entityBuilder.setPosition(new Vector(-2.5f, 0.8f));
        plank = entityBuilder.build();
        partBuilder = plank.createPartBuilder();
        Polygon plankPolygon = new Polygon( 
        		new Vector(0.0f, 0.0f), 
        		new Vector(plankWidth, 0.0f),
        		new Vector(plankWidth, plankHeight),
        		new Vector(0.0f, plankHeight) );
        partBuilder.setShape(plankPolygon);
        partBuilder.setFriction(1f);
        partBuilder.build();
        
        //Revolute constraint creation
        RevoluteConstraintBuilder revoluteConstraintBuilder = world.createRevoluteConstraintBuilder();
        revoluteConstraintBuilder.setFirstEntity(block); 
        revoluteConstraintBuilder.setFirstAnchor(new Vector(blockWidth/2, (blockHeight*7)/4));
        revoluteConstraintBuilder.setSecondEntity(plank); 
        revoluteConstraintBuilder.setSecondAnchor(new Vector(plankWidth/2, plankHeight/2));
        revoluteConstraintBuilder.setInternalCollision(true); 
        revoluteConstraintBuilder.build();
        
        //Creation of the ball
        entityBuilder.setFixed(false);
        entityBuilder.setPosition(new Vector(0.5f, 4.f));
        ball = entityBuilder.build();
        partBuilder = ball.createPartBuilder();
        Circle circle = new Circle(ballRadius);
        partBuilder.setShape(circle);
        partBuilder.build();
        
        //Graphics creation
        blockGraphics = new ImageGraphics("stone.broken.4.png", blockWidth, blockHeight);
        plankGraphics = new ImageGraphics("wood.3.png", plankWidth, plankHeight);
        ballGraphics = new ImageGraphics("explosive.11.png", 2*ballRadius, 2*ballRadius, new Vector(0.5f, 0.5f));
        blockGraphics.setParent(block);
        plankGraphics.setParent(plank);
        ballGraphics.setParent(ball);
        
        // Successfully initiated
        return true;
    }

    // This event is called at each frame
    @Override
    public void update(float deltaTime) {  
    		// Game logic
    		if (window.getKeyboard().get(KeyEvent.VK_LEFT).isDown()) { 
    			ball.applyAngularForce(5.0f);
    		} else if (window.getKeyboard().get(KeyEvent.VK_RIGHT).isDown()) {
    			ball.applyAngularForce(-5.0f);
    		}
    	
    		// Simulate physics
    		world.update(deltaTime);
    		
    		// we must place the camera where we want
    		// We will look at the origin (identity) and increase the view size a bit
    		window.setRelativeTransform(Transform.I.scaled(10.0f));
    		
    		// We can render our scene now,
    		blockGraphics.draw(window);
    		plankGraphics.draw(window);
    		ballGraphics.draw(window);
    }

    // This event is raised after game ends, to release additional resources
    @Override
    public void end() {
        // Empty on purpose, no cleanup required yet
    }
    
}
