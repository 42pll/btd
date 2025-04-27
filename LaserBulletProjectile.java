import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.*;
import java.util.List;

public class LaserBulletProjectile extends Projectile {
    int w;
    int h;
    
    public LaserBulletProjectile() {
        String name = "download-removebg-preview (1)"+".png";
        GreenfootImage image = new GreenfootImage(name);
        w = 37;
        h = 50;
        image.scale(w, h);
        setImage(image);
        speed = 8; // Faster than dart
    }
    
    public void act() {
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
