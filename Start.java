import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Write a description of class Win here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Start extends World
{
    static int length=890;
    static int width=700;
    Button start_button;
    /**
     * Constructor for objects of class Win.
     * 
     */
    public Start()
    {    
        super(length, width, 1); 
        GreenfootImage image = new GreenfootImage(getWidth(),getHeight());
        image.setColor(new Color(255,0,0));
        image.fill();
        image.setColor(new Color(255,255,255));
        Font font = new Font("Arial",true,true,40);
        image.setFont(font);
        image.drawString("Welcome To Loon Towers Fortress",150,100);

        image.drawString("Play",380,400);
        setBackground(image);
    }
    public void display()
    {
        start_button = new Button(new GreenfootImage("6bdcff85-8f32-4afb-857c-1a0d3ac9c988.png"));
        start_button.scale(70, 70);
        addObject(start_button, 100, 150);
    }
}
