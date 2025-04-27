import greenfoot.*;

public class Balloon2 extends Balloon {
    public Balloon2(int x, int y) {
        super(x, y);
        String name = "14c6a013-d597-47db-a26d-fdc29664ab25"+".png";
        GreenfootImage image = new GreenfootImage(name);
        int w = 52;
        int h = 45;
        image.scale(w,h);
        setImage(image);
        
        // Balloon2 properties
        speed = 1.2;
        max_health = 2.0;
        health = max_health;
        goldValue = 2;
    }
    public void act() {
        super.act();
    }
}