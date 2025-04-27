import greenfoot.*;

public class TowerButton extends Actor {
    private String towerType;
    private int cost;
    private Game world;
    private TowerSpot towerSpot;
    
    public TowerButton(String type, int cost) {
        this.towerType = type;
        this.cost = cost;
        
        // Create button image
        GreenfootImage img = new GreenfootImage(80, 25);
        img.setColor(new Color(255, 255, 255, 200));
        img.fillRect(0, 0, 80, 25);
        img.setColor(Color.BLACK);
        img.drawString(type + " $" + cost, 5, 15);
        setImage(img);
    }
    
    public void addedToWorld(World w) {
        if (w instanceof Game) {
            world = (Game)w;
            
            // Find the closest tower spot
            java.util.List<TowerSpot> spots = w.getObjects(TowerSpot.class);
            double minDist = Double.MAX_VALUE;
            
            for (TowerSpot spot : spots) {
                double dist = Math.sqrt(Math.pow(getX() - spot.getX(), 2) + Math.pow(getY() - spot.getY(), 2));
                if (dist < minDist) {
                    minDist = dist;
                    towerSpot = spot;
                }
            }
        }
    }
    
    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            purchaseTower();
        } else if (Greenfoot.mouseClicked(null) && !Greenfoot.mouseClicked(this)) {
            // Remove buttons if clicked elsewhere
            getWorld().removeObject(this);
        }
    }
    
    private void purchaseTower() {
        // Check if player has enough coins
        if (world.spendCoins(cost)) {
            Tower newTower = null;
            
            // Create appropriate tower
            switch (towerType) {
                case "Archer":
                    newTower = new ArcherTower();
                    newTower.setRadius(150);
                    newTower.damage = 1;
                    newTower.fire_rate = 1500; // milliseconds between shots
                    break;
                case "Ninja":
                    newTower = new NinjaTower();
                    newTower.setRadius(100);
                    newTower.damage = 2;
                    newTower.fire_rate = 1000; // faster fire rate
                    break;
                case "Laser":
                    newTower = new LaserGunnerTower();
                    newTower.setRadius(200);
                    newTower.damage = 0.5;
                    newTower.fire_rate = 500; // very fast fire rate
                    break;
            }
            
            if (newTower != null && towerSpot != null) {
                towerSpot.placeTower(newTower);
                
                // Remove all tower buttons
                java.util.List<TowerButton> buttons = world.getObjects(TowerButton.class);
                for (TowerButton button : buttons) {
                    world.removeObject(button);
                }
            }
        } else {
            // Show "not enough coins" message
            Label errorMsg = new Label("Not enough coins!", 20);
            world.addObject(errorMsg, world.getWidth() / 2, 100);
            
            // Remove message after 1.5 seconds
            new Thread(() -> {
                try {
                    Thread.sleep(1500);
                    world.removeObject(errorMsg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}

