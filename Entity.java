import greenfoot.*; 
public class Entity extends Actor
{
    Game world; 
    public boolean existing = true;
    public int[] pos = new int[2];
    public double speed = 0; 
    SimpleTimer time = new SimpleTimer();
    protected boolean clicked = false;
    protected UpgradeButton u;
    public Entity() {

    }

    public void act() {

    }

    public double distanceFrom(int x, int y)
    {
        double distance = Math.sqrt(Math.pow(x-getX(), 2) + Math.pow(y-getY(), 2));
        return distance;
    }  
    public void move(int x, int y)
    {
        double d = distanceFrom(x, y);
        double blocks = d/speed;
        double xd = (x - getX())/blocks;
        double yd = (y - getY())/blocks;
        setLocation(getX()+(int)xd, getY()+(int)(yd+0.5));
    }
    public void displayUpgrade()
    {
        
        if (clicked)
        {
            u = new UpgradeButton(this);
            getWorld().addObject(u, this.getX()+28, this.getY()+20);
        }
        else if(u!=null)
        {
            u.remove();
        }
    }
}
