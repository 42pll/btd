import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Win here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Loss extends World
{

    /**
     * Constructor for objects of class Win.
     * 
     */
    public Loss()
    {    
        super(890, 700, 1, true);
        GreenfootImage image = new GreenfootImage(getWidth(),getHeight());
        image.setColor(new Color(255,0,0));
        image.fill();
        image.setColor(new Color(255,255,255));
        Font font = new Font("Arial",true,true,40);
        image.setFont(font);
        image.drawString("You Lose!",350,100);
        
        image.drawString("Play Again",350,400);
        image.drawString("Exit",350,450);
        setBackground(image);
    }
}
