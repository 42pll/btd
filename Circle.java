import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Circle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Circle extends Actor
{
    GreenfootImage image = new GreenfootImage("67249d55-93dc-4a14-98d8-3bfcfe5c372f.png");
    public void act()
    {
        setImage(image);
        image.scale(400,400);
    }
}
