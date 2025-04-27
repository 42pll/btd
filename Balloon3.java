import greenfoot.*;

public class Balloon3 extends Balloon {
    public Balloon3(int x, int y) {
        super(x, y);
        String name = "e9a78ca6-5594-4e38-a11f-97e9441b0e53"+".png";
        GreenfootImage image = new GreenfootImage(name);
        int w = 40;
        int h = 40;
        image.scale(w,h);
        setImage(image);
        
        // Balloon3 properties
        speed = 0.8;
        max_health = 3.0;
        health = max_health;
        goldValue = 3;
    }
    
    public void act() {
        super.act();
    }
}
