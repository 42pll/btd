import greenfoot.*;
import java.util.*;

public class Balloon extends Entity {
    SimpleTimer time = new SimpleTimer();
    protected int[] destination;
    private int currentPathIndex = 0;
    private ArrayList<int[]> path;
    protected double max_health;
    protected double health;
    protected boolean reachedEnd = false;
    protected int goldValue = 1;
    
    public Balloon(int x, int y) {
        speed = 1.0; // Default speed
        max_health = 1.0; // Default health
        health = max_health;
        pos[0] = x;
        pos[1] = y;
    }   

    public void addedToWorld(World gameWorld) {
        if (gameWorld instanceof Game) {
            world = (Game) gameWorld;
            // Always use pathTwo (bottom path) based on requirements
            path = world.pathTwo;
            world.enemyCounter++;
            
            currentPathIndex = 0;
            if (path != null && !path.isEmpty()) {
                destination = new int[]{path.get(currentPathIndex)[0], path.get(currentPathIndex)[1]};
                setLocation(pos[0], pos[1]);
            }
        } else {
            destination = new int[2];
        }
    }     

    public void act() {
        if (health <= 0) {
            existing = false;
            return;
        }
        
        // Move balloon along path
        if (path != null && currentPathIndex < path.size()) {
            // Get current destination
            destination = new int[]{path.get(currentPathIndex)[0], path.get(currentPathIndex)[1]};
            
            // Move towards destination
            move(destination[0], destination[1]);
            
            // Check if reached destination
            if (distanceFrom(destination[0], destination[1]) < 5) {
                currentPathIndex++;
                
                // Check if reached end of path
                if (currentPathIndex >= path.size()) {
                    reachedEnd = true;
                    existing = false;
                    // Notify game that balloon reached end
                    ((Game)getWorld()).balloonReachedEnd();
                    return;
                }
            }
        }
    }

    public void remove() {
        if (getWorld() != null) {
            getWorld().removeObject(this);
        }
    }

    public void takeDamage(double damage) {
        health -= damage;
        
        // Health visual feedback could be added here
        // For example, changing color or showing damage number
    }

    public double getHealth() {
        return this.health;
    }

    public int[] getDestination() {
        return destination;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
