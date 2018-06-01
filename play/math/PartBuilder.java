package ch.epfl.cs107.play.math;

import org.jbox2d.dynamics.FixtureDef;

/**
 * Helps build a entity from scratch.
 */
public class PartBuilder {
    
    private FixtureDef fixtureDef;
    private Shape shape;
    private Entity entity;

    // Created by Body only
    PartBuilder(Entity entity) {
        this.entity = entity;
        fixtureDef = new FixtureDef();
        fixtureDef.density = 1.0f;
    }
    
    /**
     * Sets whether this part is hidden and act only as a sensor.
     * @param ghost new state
     */
    public void setGhost(boolean ghost) {
        fixtureDef.isSensor = ghost;
    }
    
    /** @return whether this part is hidden and act only as a sensor */
    public boolean isGhost() {
        return fixtureDef.isSensor;
    }
    
    /**
     * Sets the friction coefficient.
     * @param friction any non negative value
     */
    public void setFriction(float friction) {
        fixtureDef.friction = friction;
    }
    
    /** @return the friction coefficient, non negative */
    public float getFriction() {
        return fixtureDef.friction;
    }
    
    /**
     * Sets the restitution coefficient.
     * @param restitution any non negative value
     */
    public void setRestitution(float restitution) {
        fixtureDef.restitution = restitution;
    }
    
    /** @return the restitution coefficient, non negative */
    public float getRestitution() {
        return fixtureDef.restitution;
    }
    
    /**
     * Sets density, as final mass depends on the actual shape.
     * @param density any non negative value
     */
    public void setDensity(float density) {
        fixtureDef.density = density;
    }
    
    /** @return density */
    public float getDensity() {
        return fixtureDef.density;
    }
    
    /**
     * Sets the shape.
     * @param shape any shape
     */
    public void setShape(Shape shape) {
        this.shape = shape;
    }

    /** @return the shape */
    public Shape getShape() {
        return shape;
    }
    
    /**
     * Selects which collision categories this part reacts to.
     * Usually, just one bit is set (by default, the first one, i.e. <code>0x1</code>).
     * @param bits bitfield specifying which categories affect this part
     */
    public void setCollisionSignature(int bits) {
        fixtureDef.filter.categoryBits = bits;
    }
    
    /** @return collision categories of this part */
    public int getCollisionSignature() {
        return fixtureDef.filter.categoryBits;
    }
    
    /**
     * Selects which collision categories this part affects.
     * By default, all groups are affected, i.e. <code>0xffff</code>.
     * @param bits bitfield specifying which categories affect this part
     */
    public void setCollisionEffect(int bits) {
        fixtureDef.filter.maskBits = bits;
    }
    
    /** @return collision categories affected by this part */
    public int getCollisionEffect() {
        return fixtureDef.filter.maskBits;
    }
    
    /**
     * Selects which collision group this part has.
     * Parts in the same negative group never collide.
     * Parts in the same positive group always collide.
     * By default, the group 0 is used, which disables collision group for this part.
     * Collision group always overrides collision categories.
     * @param index group index
     */
    public void setCollisionGroup(int index) {
        fixtureDef.filter.groupIndex = index;
    }
    
    /** @return selected group index */
    public int getCollisionGroup() {
        return fixtureDef.filter.groupIndex;
    }
    
    /**
     * Creates a new part.
     * @return the newly created part
     */
    public Part build() {
        Part part = shape.build(fixtureDef, entity);
        entity.parts.add(part);
        return part;
    }
    
}
