/**
 *
 * @author limdan8vq
 */
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
public class Vulture extends JPanel{
    public boolean died;
    public Eagle eagle;
    public Prey player;
    public ArrayList<Predator> predator = new ArrayList<>();
    public int score;
    public int high;
    public int phase;
    public boolean go;
    public boolean add;
    public String direction;
    public int lives;
    public int p;
    public static int WIDTH = 1275;
    public static int HEIGHT = 750;
    public boolean pause;
    
    public Vulture() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        add = false;
        died = false;
        score = 1;
        high = 0;
        phase = 0;
        direction = "";
        go = false;
        pause = false;
        eagle = new Eagle((int)(Math.random() * 100) + (WIDTH/2), (int)(Math.random() * 100) + (HEIGHT/2));
        player = new Prey((int)(Math.random() * 100), (int)(Math.random() * 100) + (HEIGHT/2));
        predator = new ArrayList<Predator>();
        predator.add(new Predator((int)(Math.random() * 100) + WIDTH, (int)(Math.random() * 100) + (HEIGHT/2)));
        lives = 3;
        p = 3;
        
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (phase == 0 && (e.getKeyCode() >= 32 && e.getKeyCode()<= 40)) {
                    go = true;
                    if (!pause) {
                        if (e.getKeyCode() == 37) {
                            direction = "left";
                        }
                        else if (e.getKeyCode() == 38) {
                            direction = "up";
                        }
                        else if (e.getKeyCode() == 39) {
                            direction = "right";
                        }
                        else if (e.getKeyCode() == 40) {
                            direction = "down";
                        }
                    }
                    
                    if (e.getKeyCode() == 32) {
                        pause = !pause;
                    }
                }
                
                if (phase == 1 && e.getKeyCode() == 32) {
                    phase = 0;
                    player = new Prey((int)(Math.random() * 100), (int)(Math.random() * 100) + (HEIGHT/2));
                    predator = new ArrayList<Predator>();
                    predator.add(new Predator((int)(Math.random() * 100) + WIDTH, (int)(Math.random() * 100) + (HEIGHT/2)));
                    score = 1;
                    lives = 3;
                    pause = false;
                    died = false;
                    p = 3;
                }
                
                if (!pause) {
                    repaint();
                }
            }
        });
    }
    
    public void paint(Graphics window) {
        if (lives % 2 == 1) {
            window.setColor(Color.BLACK);
            window.fillRect(1, 1, WIDTH - 2, HEIGHT - 2);
        }
        else if (lives % 2 == 0) {
            window.setColor(Color.GRAY);
            window.fillRect(1, 1, WIDTH - 2, HEIGHT - 2);
        }
        if (lives <= 1) {
            window.setColor(Color.BLACK);
            window.fillRect(1, 1, WIDTH - 2, HEIGHT - 2);
        }
        
        
        window.setColor(Color.RED);
        window.drawRect(1, 1, WIDTH - 2, HEIGHT - 2);
        window.setColor(Color.WHITE);
        if (lives % 2 == 0) {
            window.setColor(Color.BLACK);
        }
        window.drawString("High Score: " + high, 11, 16);
        window.drawString("Score: " + score, 11, 26);
        window.drawString("Lives: " + lives, 11, 36);
        window.drawString("Press SPACE to pause", 11, 46);
        window.setColor(Color.BLUE);
        window.fillOval(player.x, player.y, 10, 10);
        window.setColor(Color.RED);
        for (int i = 0; i < predator.size(); i++) {
            window.fillRect(predator.get(i).x, predator.get(i).y, 10, 10);
        }
        window.setColor(Color.GREEN);
        window.fillOval(eagle.x, eagle.y, 10, 10);
        
        if (go) {
            if (direction.equals("left") && player.x >= 2) {
                player.setCoord(player.x - 1, player.y);
            }
            else if (direction.equals("right") && player.x + 10 < (WIDTH - 2)) {
                player.setCoord(player.x + 1, player.y);
            }
            else if (direction.equals("up") && player.y >= 2) {
                player.setCoord(player.x, player.y - 1);
            }            
            else if (direction.equals("down") && player.y + 10 < (HEIGHT - 2)) {
                player.setCoord(player.x, player.y + 1);
            }
            
            for (int i = 0; i < predator.size(); i++) {
                if (predator.get(i).x < player.x) {
                    int r = (int)(Math.random() * 2);
                    predator.get(i).setX(predator.get(i).x + r);
                }
                else if (predator.get(i).x > player.x) {
                    int r = (int)(Math.random() * 2);
                    predator.get(i).setX(predator.get(i).x - r);
                }
                if (predator.get(i).y < player.y) {
                    int r = (int)(Math.random() * 2);
                    predator.get(i).setY(predator.get(i).y + r);
                }
                else if (predator.get(i).y > player.y) {
                    int r = (int)(Math.random() * 2);
                    predator.get(i).setY(predator.get(i).y - r);
                }
                
                if (predator.get(i).y == player.y && predator.get(i).x == player.x) {
                    died = true;
                    lives--;
                    if (lives == 0) {
                        go = false;
                        phase = 1;
                    }
                    else {
                        predator.remove(i);
                    }
                }
            }
            
            
            int r = (int)(Math.random() * (500 - score * 4));
            if (score >= 122) {
                r = (int)(Math.random() * 10);

            }
            if (r == 0) { 
                for (int i = -1; i < 0; i++) {
                    int xp = (int)(Math.random() * WIDTH);
                    int yp = (int)(Math.random() * HEIGHT);
                    while (((Math.abs(xp - player.getX())) + (Math.abs(yp - player.getY()))) <= 400) {
                        xp -= 100;
                        yp -= 100;
                    }
                    predator.add(new Predator(xp, yp));
                    score++;
                }
            }
            if (predator.size() > 0) {
                if (eagle.x < predator.get(0).x) {
                    int w = (int)(Math.random() * 2);
                    eagle.setX(eagle.x + w);
                }
                else if (eagle.x > predator.get(0).x) {
                    int w = (int)(Math.random() * 2);
                    eagle.setX(eagle.x - w);
                }   
                if (eagle.y < predator.get(0).y) {
                    int w = (int)(Math.random() * 2);
                    eagle.setY(eagle.y + w);
                }
                else if (eagle.y > predator.get(0).y) {
                    int w = (int)(Math.random() * 2);
                    eagle.setY(eagle.y - w);
                }
                
                if (eagle.x == predator.get(0).x && eagle.y == predator.get(0).y) {
                    predator.remove(0);
                }
            }
            
        }
       
        if (phase == 1) {
            window.setColor(Color.WHITE);
            window.drawString("You have been Vultured: " + score, (WIDTH / 2) + 50, (HEIGHT / 2));
            window.drawString("Press SPACE to restart", (WIDTH / 2) + 50, (HEIGHT / 2) + 25);
            if (score > high) {
                high = score;
                window.setColor(Color.WHITE);
                window.drawString("NEW HIGHSCORE: " + high, (WIDTH / 2) + 50, (HEIGHT / 2) + 50);
            }
            pause = true;
        }
        
        if (!pause) {
            repaint();
        }
    }
}

