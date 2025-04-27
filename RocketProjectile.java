import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.*;
import java.util.List;

public class RocketProjectile extends Projectile {
    int w;
    int h;

    public RocketProjectile() {
        String name = "depositphotos_751520114-stock-illustration-pixel-art-illustration-missile-pixelated-removebg-preview"+".png";
        GreenfootImage image = new GreenfootImage(name);
        w = 50;
        h = 70;
        image.scale(w, h);
        setImage(image);
        speed = 3.0; // Slower but more powerful
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
