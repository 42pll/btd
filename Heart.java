import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Heart extends Actor
{
    public Heart()
    {
        // Create heart image
        GreenfootImage heartImage = new GreenfootImage("51a6f709-4c0b-4824-9e62-a7fa65cc22d8.png");
        heartImage.scale(20, 20);
        setImage(heartImage);
    }
    
    public void act()
    {
        // Hearts don't need to do anything in act method
    }
}
