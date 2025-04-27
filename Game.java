import greenfoot.*;
import java.util.*;

public class Game extends World {
    public ArrayList<Balloon> balloonList = new ArrayList<Balloon>();
    public ArrayList<int[]> pathOne = new ArrayList<int[]>(); 
    public ArrayList<int[]> pathTwo = new ArrayList<int[]>();
    public ArrayList<int[]> towerSpots = new ArrayList<int[]>(); // For tower placement spots
    public ArrayList<Marker> markers = new ArrayList<Marker>(); // Visual markers for tower spots
    
    SimpleTimer gameTimer = new SimpleTimer(); // Overall game timer (3 minutes)
    SimpleTimer waveTimer = new SimpleTimer(); // Timer for spawning waves
    SimpleTimer spawnTimer = new SimpleTimer(); // Timer for spawning individual balloons
    
    Label coinsLabel;
    int lives;
    int totalCoins;
    public int enemyCounter;
    Heart[] hearts;
    
    // Wave control variables
    private int currentWave = 1;
    private int balloonsPerWave = 5; // Starting balloons per wave
    private int spawnInterval = 2000; // Starting spawn interval in milliseconds
    private int waveInterval = 15000; // Time between waves in milliseconds
    private int balloonsToSpawn; // Counter for balloons in current wave
    private int waveBalloonsLeft; // Counter for balloons left in current wave
    
    public Game() {    
        super(800, 600, 1, false);
        this.enemyCounter = 0;
        
        // Set background
        GreenfootImage bg = new GreenfootImage("blank-background.png");
        bg.scale(800, 600);
        setBackground(bg);
        
        lives = 25;
        hearts = new Heart[lives];
        storeHealth();
        displayHealth(100, 30);
        
        totalCoins = 100; // Starting coins
        coinsLabel = new Label("Coins: " + totalCoins, 30);
        addObject(coinsLabel, 700, 30);
        
        // Add a Gold icon next to the coins label
        cGold goldIcon = new cGold();
        addObject(goldIcon, 650, 30);
        
        // Add a Health icon next to the hearts
        cHealth healthIcon = new cHealth();
        addObject(healthIcon, 30, 30);
        
        // Initialize paths
        initializePaths();
        
        // Initialize tower spots
        initializeTowerSpots();
        
        // Draw paths (for visual reference during development)
        drawPaths();
        
        // Start timers
        gameTimer.mark();
        waveTimer.mark();
        spawnTimer.mark();
        
        // Initialize first wave
        startNewWave();
    }

    public void act() {
        // Check if game is over
        if (gameTimer.millisElapsed() > 180000) { // 3 minutes = 180000 milliseconds
            Greenfoot.setWorld(new Win());
            return;
        }
        
        // Check if it's time for a new wave
        if (waveTimer.millisElapsed() > waveInterval && waveBalloonsLeft <= 0) {
            currentWave++;
            startNewWave();
            waveTimer.mark();
        }
        
        // Spawn balloons based on timer
        if (spawnTimer.millisElapsed() > spawnInterval && balloonsToSpawn > 0) {
            spawnBalloon();
            balloonsToSpawn--;
            spawnTimer.mark();
        }
        
        // Check balloon status (remove destroyed or out of screen balloons)
        checkBalloonStatus();
        
        // Update UI
        coinsLabel.setValue("Coins: " + totalCoins);
    }
    
    private void startNewWave() {
        balloonsToSpawn = balloonsPerWave + (currentWave - 1) * 2; // Increase balloons per wave
        waveBalloonsLeft = balloonsToSpawn;
        
        // Decrease spawn interval as waves progress (make it faster)
        spawnInterval = Math.max(500, 2000 - (currentWave - 1) * 150);
        
        // Show wave notification
        Label waveLabel = new Label("Wave " + currentWave, 40);
        addObject(waveLabel, getWidth() / 2, getHeight() / 2);
        
        // Remove wave notification after 2 seconds
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                removeObject(waveLabel);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    private void spawnBalloon() {
        int randomBalloonType = Greenfoot.getRandomNumber(3) + 1; // Random balloon type 1-3
        Balloon balloon = null;
        
        // Always use pathTwo (bottom path) based on your requirements
        int startX = pathTwo.get(0)[0];
        int startY = pathTwo.get(0)[1];
        
        switch (randomBalloonType) {
            case 1:
                balloon = new Balloon1(startX, startY);
                break;
            case 2:
                balloon = new Balloon2(startX, startY);
                break;
            case 3:
                balloon = new Balloon3(startX, startY);
                break;
        }
        
        if (balloon != null) {
            createBalloon(balloon);
        }
    }
    
    public void createBalloon(Balloon b) {
        balloonList.add(b);
        addObject(b, b.pos[0], b.pos[1]);
    }
    
    public void balloonReachedEnd() {
        // Reduce lives when balloon reaches end
        lives--;
        displayHealth(100, 30);
        
        // Check if player has lost
        if (lives <= 0) {
            Greenfoot.setWorld(new Loss());
        }
    }
    
    public void checkBalloonStatus() {
        for (int i = 0; i < balloonList.size(); i++) {
            Balloon balloon = balloonList.get(i);
            
            if (!balloon.existing || balloon.getHealth() <= 0) {
                // Balloon was destroyed, give player coins
                if (balloon.getHealth() <= 0) {
                    updateCoins(balloon.goldValue); // Give gold based on balloon value
                }
                
                balloon.remove();
                balloonList.remove(i);
                i--;
                waveBalloonsLeft--;
            }
        }
    }
    
    public void updateCoins(int coin) {
        totalCoins += coin;
        coinsLabel.setValue("Coins: " + totalCoins);
    }
    
    public boolean spendCoins(int amount) {
        if (totalCoins >= amount) {
            totalCoins -= amount;
            coinsLabel.setValue("Coins: " + totalCoins);
            return true;
        }
        return false;
    }
    
    public void storeHealth() {
        for (int i = 0; i < lives; i++) {
            hearts[i] = new Heart();
        }
    }
    
    public void displayHealth(int x, int y) {
        // First remove any existing hearts
        for (int i = 0; i < hearts.length; i++) {
            if (hearts[i] != null && hearts[i].getWorld() != null) {
                removeObject(hearts[i]);
            }
        }
        
        // Display only the current number of hearts
        int displayedHearts = Math.min(lives, 5); // Display max 5 hearts at a time
        for (int i = 0; i < displayedHearts; i++) {
            addObject(hearts[i], x + i * 30, y);
        }
        
        // Display heart count if more than 5
        if (lives > 5) {
            Label heartCount = new Label("x" + lives, 20);
            addObject(heartCount, x + 5 * 30 + 15, y);
        }
    }
    
    private void initializePaths() {
        // Based on your map image, defining paths
        
        // Path One (top path) - we'll keep this as an option
        pathOne.add(new int[]{0, 100});
        pathOne.add(new int[]{100, 100});
        pathOne.add(new int[]{100, 200});
        pathOne.add(new int[]{300, 200});
        pathOne.add(new int[]{300, 300});
        pathOne.add(new int[]{500, 300});
        pathOne.add(new int[]{500, 400});
        pathOne.add(new int[]{700, 400});
        pathOne.add(new int[]{700, 500});
        pathOne.add(new int[]{800, 500});
        
        // Path Two (bottom path) - the main path balloons will follow
        pathTwo.add(new int[]{0, 500});   // Start from left edge
        pathTwo.add(new int[]{200, 500}); // First horizontal segment
        pathTwo.add(new int[]{200, 400}); // First upward turn
        pathTwo.add(new int[]{400, 400}); // Second horizontal segment
        pathTwo.add(new int[]{400, 300}); // Second upward turn
        pathTwo.add(new int[]{600, 300}); // Third horizontal segment
        pathTwo.add(new int[]{600, 200}); // Third upward turn
        pathTwo.add(new int[]{800, 200}); // Exit at right edge
    }
    
    // Helper method to draw paths for debugging
    private void drawPaths() {
        // Draw pathTwo (main path)
        for (int i = 0; i < pathTwo.size(); i++) {
            Marker marker = new Marker(pathTwo.get(i)[0], pathTwo.get(i)[1]);
            addObject(marker, pathTwo.get(i)[0], pathTwo.get(i)[1]);
            markers.add(marker);
        }
    }
    
    private void initializeTowerSpots() {
        // Create spots for tower placement, avoiding paths
        int[][] spots = {
            {150, 150}, {250, 150}, {350, 150}, {450, 150}, {550, 150},
            {150, 250}, {250, 250}, {350, 250}, {450, 250}, {550, 250},
            {150, 350}, {250, 350}, {350, 350}, {450, 350}, {550, 350},
            {150, 450}, {250, 450}, {350, 450}, {450, 450}, {550, 450}
        };
        
        for (int[] spot : spots) {
            towerSpots.add(spot);
            TowerSpot towerSpot = new TowerSpot(spot[0], spot[1]);
            addObject(towerSpot, spot[0], spot[1]);
        }
    }
}
