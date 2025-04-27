import greenfoot.*;
import java.util.List;

public class DartProjectile extends Projectile {
    int w;
    int h;
    int[] destination = new int[2];
    
    public DartProjectile() {
        String name = "download-removebg-preview (3)"+".png";
        GreenfootImage image = new GreenfootImage(name);
        w = 37;
        h = 50;
        image.scale(w, h);
        setImage(image);
        speed = 4;
    }
    
    public void act() {
        if (target != null && target.getWorld() != null) {
            // Target still exists, follow it
            turnTowards(target.getX(), target.getY());
            move(speed);
            
            // Check if hit target
            if (getIntersectingObjects(Balloon.class).contains(target)) {
                hitTarget(target);
                getWorld().removeObject(this);
            }
            
            // Check if out of range
            if (tower != null && distanceFrom(tower.getX(), tower.getY()) > tower.getRadius()) {
                getWorld().removeObject(this);
            }
        } else {
            // Target doesn't exist anymore, continue on straight path
            move(speed);
            
            // Check if hitting any balloon
            List<Balloon> hitBalloons = getIntersectingObjects(Balloon.class);
            if (!hitBalloons.isEmpty()) {
                hitTarget(hitBalloons.get(0));
                getWorld().removeObject(this);
            }
            
            // Remove if out of range or at edge
            if (tower != null && distanceFrom(tower.getX(), tower.getY()) > tower.getRadius() || isAtEdge()) {
                getWorld().removeObject(this);
            }
        }
    }
}
