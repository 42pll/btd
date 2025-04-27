import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ArcherTower here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LaserGunnerTower extends Tower
{
    int w;
    int h;
    public LaserGunnerTower() {
        String name = "DeWatermark.ai_1745203510017-removebg-preview"+".png";
        GreenfootImage image = new GreenfootImage(name);
        int w = 75;
        int h = 100;
        image.scale(w,h);
        setImage(image);
    }
    public void act()
    {
        // Add your action code here.
    }
}
