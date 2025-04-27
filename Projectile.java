import greenfoot.*;
import java.util.*;

public class Projectile extends Entity {
    List<GreenfootImage> images;
    public double imageIndex = 0;
    double damage = 1.0;
    GreenfootSound sound;
    Tower tower;
    Balloon target;
    
    public void act() {
        // Base projectile movement (overridden by specific projectiles)
        move(5);
        
        // Remove if at edge of world
        if (isAtEdge()) {
            getWorld().removeObject(this);
        }
    }
    
    public void setDamage(double damage) {
        this.damage = damage;
    }
    
    public void move(int distance) {
        move((double)distance);
    }
    
    public void hitTarget(Balloon balloon) {
        if (balloon != null && balloon.getWorld() != null) {
            balloon.takeDamage(damage);
            
            // Check if balloon is destroyed
            if (balloon.getHealth() <= 0) {
                balloon.existing = false;
            }
        }
    }
}
