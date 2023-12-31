package Snake;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.Key;
import java.util.Random;
import GameMenu.Menu;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    boolean showPlayText = true;
    boolean showAgainText = false;
    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame() {
        newApple();
        running = false;
        showPlayText = true;
        timer = new Timer(DELAY, this);
    }
    public void playAgain() {
        newApple();
        bodyParts = 6;
        applesEaten = 0;
        direction = 'R';
        for (int i = 0; i < bodyParts; i++) {
            x[i] = 0;
            y[i] = 0;
        }
        running = true;
        showAgainText = false;
        timer.start();
        repaint();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        if (running) {
            /*
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            */
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.pink);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
        } else {
            if (showPlayText) {
                drawStartText(g);
                drawInstructions(g);
            } else if (showAgainText) {
                gameOver(g);
                BackToMenu(g);
            }
        }
    }
    public void newApple() {
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }
    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }
    public void checkCollisions() {
        //checks if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
                showAgainText = true;
            }
        }
        // check if head touches left boreder
        if (x[0] < 0) {
            running = false;
            showAgainText = true;
        }
        // check if head touches right boreder
        if (x[0] > SCREEN_WIDTH) {
            running = false;
            showAgainText = true;
        }
        // check if head touches top boreder
        if (y[0] < 0) {
            running = false;
            showAgainText = true;
        }
        // check if head touches bottom boreder
        if (y[0] > SCREEN_HEIGHT) {
            running = false;
            showAgainText = true;
        }
        if (!running) {
            timer.stop();
        }
    }
    public void gameOver(Graphics g) {
        //Score
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
        //Game Over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
        //Play Again text
        g.setColor(Color.yellow);
        g.setFont(new Font("Ink Free", Font.BOLD, 20));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Press Enter to Play Again", (SCREEN_WIDTH - metrics2.stringWidth("Press Enter to Play Again")) / 2, SCREEN_HEIGHT / 2 + 50);
        //Exit text
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 19));
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        g.drawString("Press Esc to Exit", (SCREEN_WIDTH - metrics3.stringWidth("Press Esc to Exit")) / 2, SCREEN_HEIGHT / 2 + 80);
    }
    public void BackToMenu(Graphics g) {
        g.setColor(Color.yellow);
        g.setFont(new Font("Ink Free", Font.BOLD, 25));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Back", SCREEN_WIDTH - 70, 50);
    }
    public void drawStartText(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Play", (SCREEN_WIDTH - metrics.stringWidth("Play")) / 2, SCREEN_HEIGHT / 2);
    }
    public void drawInstructions(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free", Font.PLAIN, 20));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Press Space to Play", (SCREEN_WIDTH - metrics.stringWidth("Press Space to Play")) / 2, SCREEN_HEIGHT / 2 + 50);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE && showPlayText) {
                running = true;
                showPlayText = false;
                timer.start();
            }
            if (e.getKeyCode() == KeyEvent.VK_ENTER && showAgainText) {
                running = true;
                playAgain();
                showAgainText = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE && !running) {
                System.exit(0); // Thoát game khi ấn phím Esc sau khi kết thúc
            }
            if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && !running) {
                SwingUtilities.getWindowAncestor(GamePanel.this).dispose();
                new Menu();
            }
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
