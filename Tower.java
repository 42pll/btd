import greenfoot.*;

public class Tower extends Entity {
    static GreenfootImage circle;
    private int radius;
    protected int level = 1;
    protected double damage = 1.0;
    protected int fire_rate = 1000; // milliseconds between shots
    protected Circle c;
    private SimpleTimer shootTimer = new SimpleTimer();
    
    public Tower() {
        circle = new GreenfootImage("67249d55-93dc-4a14-98d8-3bfcfe5c372f.png");
        shootTimer.mark();
    }
    
    public void act() {
        // Call parent act method
        super.act();
        
        // Check for upgrade click
        if (u != null && u.detectClick()) {
            upgradeTower();
            u.remove();
        }
        
        // Check for tower selection (display range and upgrade button)
        if (Greenfoot.mouseClicked(this) && !clicked) {
            clicked = true;
            displayCircle();
            displayUpgrade();
        } else if ((Greenfoot.mouseClicked(null) && !Greenfoot.mouseClicked(this)) || 
                  (Greenfoot.mouseClicked(this) && clicked)) {
            clicked = false;
            displayCircle();
            displayUpgrade();
        }
        
        // Check for enemies and shoot
        if (shootTimer.millisElapsed() > fire_rate) {
            Balloon target = checkClosest();
            if (target != null) {
                shootAt(target);
                shootTimer.mark();
            }
        }
    }
    
    public void shootAt(Balloon balloon) {
        Projectile projectile = null;
        
        // Create appropriate projectile based on tower type
        if (this instanceof ArcherTower) {
            projectile = new DartProjectile();
        } else if (this instanceof NinjaTower) {
            projectile = new RocketProjectile();
        } else if (this instanceof LaserGunnerTower) {
            projectile = new LaserBulletProjectile();
        }
        
        if (projectile != null) {
            projectile.damage = this.damage;
            projectile.tower = this;
            projectile.target = balloon;
            
            Game world = (Game)getWorld();
            world.addObject(projectile, getX(), getY());
            
            // Calculate angle to target
            double dx = balloon.getX() - getX();
            double dy = balloon.getY() - getY();
            int rotation = (int)Math.toDegrees(Math.atan2(dy, dx));
            projectile.setRotation(rotation);
        }
    }
    
    public boolean isInRange(Balloon b) {
        if (distanceFrom(b.getX(), b.getY()) <= radius) {
           return true;
        }
        return false;
    }
    
    public Balloon checkClosest() {
        Game gameWorld = (Game)getWorld();
        if (gameWorld == null || gameWorld.balloonList.isEmpty()) {
            return null;
        }
        
        Balloon closest = null;
        double closestDistance = Double.MAX_VALUE;
        
        for (Balloon b : gameWorld.balloonList) {
            double distance = distanceFrom(b.getX(), b.getY());
            if (distance <= radius && distance < closestDistance) {
                closest = b;
                closestDistance = distance;
            }
        }
        
        return closest;
    }
    
    public void displayCircle() {
        if (clicked) {
            c = new Circle();
            getWorld().addObject(c, getX(), getY());
            
            // Scale circle to match tower radius
            GreenfootImage img = c.getImage();
            int size = radius * 2;
            img.scale(size, size);
            c.setImage(img);
        } else if (c != null && c.getWorld() != null) {
            getWorld().removeObject(c);
            c = null;
        }
    }
    
    public void upgradeTower() {
        Game gameWorld = (Game)getWorld();
        int upgradeCost = 50 * level;
        
        if (gameWorld.spendCoins(upgradeCost)) {
            level++;
            
            // Upgrade properties based on tower type
            if (this instanceof ArcherTower) {
                radius += 25;
                damage += 0.5;
                fire_rate = Math.max(fire_rate - 100, 500); // Faster fire rate
            } else if (this instanceof NinjaTower) {
                radius += 15;
                damage += 1.0;
                fire_rate = Math.max(fire_rate - 100, 400);
            } else if (this instanceof LaserGunnerTower) {
                radius += 30;
                damage += 0.25;
                fire_rate = Math.max(fire_rate - 50, 200);
            }
            
            // Show upgrade effect
            Label upgradeMsg = new Label("Tower Upgraded to Level " + level, 20);
            gameWorld.addObject(upgradeMsg, getX(), getY() - 50);
            
            // Remove message after 1.5 seconds
            new Thread(() -> {
                try {
                    Thread.sleep(1500);
                    gameWorld.removeObject(upgradeMsg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            // Show "not enough coins" message
            Label errorMsg = new Label("Not enough coins for upgrade!", 20);
            gameWorld.addObject(errorMsg, gameWorld.getWidth() / 2, 100);
            
            // Remove message after 1.5 seconds
            new Thread(() -> {
                try {
                    Thread.sleep(1500);
                    gameWorld.removeObject(errorMsg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
    
    public int getRadius() {
        return this.radius;
    }
    
    public void setRadius(int r) {
        this.radius = r;
    }
}