
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SpaceInvadersGame extends JPanel implements KeyListener {

    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static final int PLAYER_WIDTH = 50;
    private static final int PLAYER_HEIGHT = 30;
    private static final int PLAYER_SPEED = 5;
    private static final int ENEMY_WIDTH = 30;
    private static final int ENEMY_HEIGHT = 30;
    private static final int ENEMY_ROWS = 3;
    private static final int ENEMY_COLS = 10;
    private static final int ENEMY_GAP = 10;
    private static final int BULLET_WIDTH = 3;
    private static final int BULLET_HEIGHT = 10;
    private static final int BULLET_SPEED = 5;
    private static final int SCORE_INCREMENT = 10;

    private int playerX;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private boolean isGameRunning;
    private int score;
    private int level;
    private boolean isStartScreenVisible;

    private Connection connection;
    private String dbUrl = "jdbc:mysql://localhost/game";
    private String dbUsername = "root";
    private String dbPassword = "";

    public SpaceInvadersGame() {
        initGame();
        setFocusable(true);
        addKeyListener(this);
        isStartScreenVisible = true;
    }

    private void initGame() {
        playerX = WINDOW_WIDTH / 2 - PLAYER_WIDTH / 2;
        enemies = createEnemies();
        bullets = new ArrayList<>();
        isGameRunning = false;
        score = 0;
        level = 1;
    }

    private void recordScore(int score) {
        try {
            // Register the JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Open the connection
            Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

            // Prepare the SQL query
            String query = "INSERT INTO score (score) VALUES (?)";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                // Set the parameter value
                statement.setInt(1, score);

                // Execute the query
                statement.executeUpdate();
            }

            // Close the connection
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void retrieveTopScores() {
        try {
            // Register the JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Open the connection
            Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

            // Prepare the SQL query
            String query = "SELECT score FROM score ORDER BY score DESC LIMIT 10";

            try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

                // Iterate over the result set
                while (resultSet.next()) {
                    int score = resultSet.getInt("score");

                    // Process the retrieved scores
                    // e.g., display them on the screen or store them in a data structure
                    System.out.println("Score: " + score);
                }
            }

            // Close the connection
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayTopScores() {
        retrieveTopScores();

        // Process the retrieved scores and display them on the screen
        Graphics g = getGraphics();
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));

        // Retrieve and display the scores
        try {
            // Register the JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Open the connection
            Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

            // Prepare the SQL query
            String query = "SELECT score FROM score ORDER BY score DESC LIMIT 10";

            try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

                int position = 1;
                int yPos = 60;

                // Iterate over the result set
                while (resultSet.next()) {
                    int score = resultSet.getInt("score");

                    // Display the score on the screen
                    String scoreText = position + ". " + score;
                    g.drawString(scoreText, 10, yPos);
                    yPos += 20;
                    position++;
                }
            }

            // Close the connection
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Enemy> createEnemies() {
        List<Enemy> enemyList = new ArrayList<>();

        int startX = (WINDOW_WIDTH - ENEMY_COLS * (ENEMY_WIDTH + ENEMY_GAP)) / 2;
        int startY = 50;

        for (int row = 0; row < ENEMY_ROWS; row++) {
            for (int col = 0; col < ENEMY_COLS; col++) {
                int x = startX + col * (ENEMY_WIDTH + ENEMY_GAP);
                int y = startY + row * (ENEMY_HEIGHT + ENEMY_GAP);
                Enemy enemy = new Enemy(x, y);
                enemyList.add(enemy);
            }
        }

        return enemyList;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Clear the screen
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        if (isStartScreenVisible) {
            // Draw the start screen
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 32));
            g.drawString("Press Enter to Play", WINDOW_WIDTH / 2 - 140, WINDOW_HEIGHT / 2);
            displayTopScores(); // Invoke displayTopScores when the start screen is visible
        } else if (isGameRunning) {
            // Draw the player ship
            g.setColor(Color.GREEN);
            g.fillRect(playerX, WINDOW_HEIGHT - PLAYER_HEIGHT - 10, PLAYER_WIDTH, PLAYER_HEIGHT);

            // Draw the enemies
            g.setColor(Color.RED);
            for (Enemy enemy : enemies) {
                g.fillRect(enemy.getX(), enemy.getY(), ENEMY_WIDTH, ENEMY_HEIGHT);
            }

            // Draw the bullets
            g.setColor(Color.WHITE);
            for (Bullet bullet : bullets) {
                g.fillRect(bullet.getX(), bullet.getY(), BULLET_WIDTH, BULLET_HEIGHT);
            }

            // Draw the score and level
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("Score: " + score, 10, 20);
            g.drawString("Level: " + level, 10, 40);
        } else {
            // Game over screen
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 32));
            g.drawString("Game Over", WINDOW_WIDTH / 2 - 100, WINDOW_HEIGHT / 2);
            g.setFont(new Font("Arial", Font.PLAIN, 16));
            g.drawString("Final Score: " + score, WINDOW_WIDTH / 2 - 70, WINDOW_HEIGHT / 2 + 30);
            displayTopScores(); // Invoke displayTopScores when the game is over
        }
    }

    private void movePlayerLeft() {
        if (playerX > 0) {
            playerX -= PLAYER_SPEED;
        }
    }

    private void movePlayerRight() {
        if (playerX < WINDOW_WIDTH - PLAYER_WIDTH) {
            playerX += PLAYER_SPEED;
        }
    }

    private void shootBullet() {
        int bulletX = playerX + PLAYER_WIDTH / 2 - BULLET_WIDTH / 2;
        int bulletY = WINDOW_HEIGHT - PLAYER_HEIGHT - 20;
        Bullet bullet = new Bullet(bulletX, bulletY);
        bullets.add(bullet);
    }

    private void moveBullets() {
        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            bullet.setY(bullet.getY() - BULLET_SPEED);

            if (bullet.getY() < 0) {
                bullets.remove(i);
            }
        }
    }

    private void moveEnemies() {
        for (Enemy enemy : enemies) {
            enemy.setX(enemy.getX() + enemy.getDirection() * ENEMY_WIDTH);

            if (enemy.getX() <= 0 || enemy.getX() >= WINDOW_WIDTH - ENEMY_WIDTH) {
                enemy.reverseDirection();
                enemy.setY(enemy.getY() + ENEMY_HEIGHT);
            }

            if (enemy.getY() > WINDOW_HEIGHT - PLAYER_HEIGHT - ENEMY_HEIGHT) {
                isGameRunning = false; // Game over if any enemy reaches player's level
            }
        }
    }

    private void checkCollisions() {
        Rectangle playerBounds = new Rectangle(playerX, WINDOW_HEIGHT - PLAYER_HEIGHT - 10, PLAYER_WIDTH, PLAYER_HEIGHT);

        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            Rectangle bulletBounds = new Rectangle(bullet.getX(), bullet.getY(), BULLET_WIDTH, BULLET_HEIGHT);

            if (bulletBounds.intersects(playerBounds)) {
                bullets.remove(i);
                isGameRunning = false; // Game over if player is hit
                recordScore(score); // Invoke recordScore when the game is over
                break;
            }

            for (int j = enemies.size() - 1; j >= 0; j--) {
                Enemy enemy = enemies.get(j);
                Rectangle enemyBounds = new Rectangle(enemy.getX(), enemy.getY(), ENEMY_WIDTH, ENEMY_HEIGHT);

                if (bulletBounds.intersects(enemyBounds)) {
                    bullets.remove(i);
                    enemies.remove(j);
                    score += SCORE_INCREMENT;
                    break;
                }
            }
        }
    }

    private void updateLevel() {
        if (enemies.isEmpty()) {
            level++;
            enemies = createEnemies();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (isStartScreenVisible && keyCode == KeyEvent.VK_ENTER) {
            isStartScreenVisible = false;
            isGameRunning = true;
        } else if (isGameRunning) {
            if (keyCode == KeyEvent.VK_LEFT) {
                movePlayerLeft();
            } else if (keyCode == KeyEvent.VK_RIGHT) {
                movePlayerRight();
            } else if (keyCode == KeyEvent.VK_SPACE) {
                shootBullet();
            }
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used
    }

    public void startGame() {
        JFrame frame = new JFrame("Space Invaders");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        frame.add(this);
        frame.setVisible(true);

        while (true) {
            if (!isStartScreenVisible && isGameRunning) {
                moveBullets();
                moveEnemies();
                checkCollisions();
                updateLevel();
            }

            repaint();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SpaceInvadersGame game = new SpaceInvadersGame();
        game.startGame();
    }
}

class Enemy {

    private int x;
    private int y;
    private int direction;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
        this.direction = 1;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirection() {
        return direction;
    }

    public void reverseDirection() {
        direction *= -1;
    }
}

class Bullet {

    private int x;
    private int y;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
