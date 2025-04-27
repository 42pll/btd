import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.*;
/**
 * It is the button that leads players into the game
 * 
 * @author (Benny Wu) 
 * Last edited (Jan 20, 2021)
 */
public class StartButton extends Button
{
    public StartButton()
    {
        super(new GreenfootImage("6bdcff85-8f32-4afb-857c-1a0d3ac9c988.png"));;
        
    }

    SimpleTimer timer = new SimpleTimer(); 
    public void act() 
    {
 
        if(timer.millisElapsed()>1000)
        {
        }
        detectClick();
        checkHover();
    }  
    
    public void detectClick()
    {
        if(Greenfoot.mouseClicked(this))
        {
            Game game = new Game();
            Greenfoot.setWorld(game);
            
        }
            
    }
    
    private boolean hovering = false;
    public void checkHover()
    {
        if(Greenfoot.mouseMoved(this))
        {
            hovering = true;
            
        }
        if (Greenfoot.mouseMoved(null) && !Greenfoot.mouseMoved(this))
        {
            hovering = false;
        }
        if (hovering)
        {
            sizeOfButton = 1.375;
            increasing = false;
            button.scale(203,110);
        }
        else
        {
            changeSize();
        }

    }
}
