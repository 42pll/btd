import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class cGold here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Gold extends Actor
{
    int w;
    int h;
    public Gold() {
        String name = "4c6c9470-0902-48cf-a59b-084ea849d0a1"+".png";
        GreenfootImage image = new GreenfootImage(name);
        int w = 160;
        int h = 160;
        image.scale(w,h);
        setImage(image);
    }
    public void act()
    {
        // Add your action code here.
    }
}
