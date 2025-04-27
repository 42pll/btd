import greenfoot.*;

public class Balloon1 extends Balloon {
    public Balloon1(int x, int y) {
        super(x, y);
        String name = "7c7e2e1a-0197-4ada-9630-858bfbf5f551"+".png";
        GreenfootImage image = new GreenfootImage(name);
        int w = 37;
        int h = 50;
        image.scale(w,h);
        setImage(image);
        
        // Balloon1 properties
        speed = 1.5;
        max_health = 1.0;
        health = max_health;
        goldValue = 1;
    }
    
    public void act() {
        super.act();
    }
}

