import greenfoot.*;

public class TowerSpot extends Actor {
    private boolean occupied = false;
    private Tower tower = null;
    private Game world;
    private int posX, posY;
    
    public TowerSpot(int x, int y) {
        posX = x;
        posY = y;
        GreenfootImage img = new GreenfootImage(30, 30);
        img.setColor(new Color(0, 255, 0, 100));
        img.fillOval(0, 0, 30, 30);
        setImage(img);
    }
    
    public void addedToWorld(World w) {
        if (w instanceof Game) {
            world = (Game)w;
        }
    }
    
    public void act() {
        if (!occupied && Greenfoot.mouseClicked(this)) {
            showTowerMenu();
        }
    }
    
    private void showTowerMenu() {
        // Create tower selection buttons
        TowerButton archerButton = new TowerButton("Archer", 50);
        TowerButton ninjaButton = new TowerButton("Ninja", 75);
        TowerButton laserButton = new TowerButton("Laser", 100);
        
        world.addObject(archerButton, posX - 50, posY - 50);
        world.addObject(ninjaButton, posX, posY - 50);
        world.addObject(laserButton, posX + 50, posY - 50);
    }
    
    public void placeTower(Tower newTower) {
        if (!occupied) {
            tower = newTower;
            world.addObject(tower, posX, posY);
            occupied = true;
            
            // Make the tower spot invisible once occupied
            GreenfootImage img = new GreenfootImage(1, 1);
            setImage(img);
        }
    }
    
    public boolean isOccupied() {
        return occupied;
    }
}