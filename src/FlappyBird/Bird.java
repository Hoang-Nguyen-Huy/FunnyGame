package FlappyBird;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Bird {
    public float x, y, vx, vy;
    public static final int RAD = 25;
    private Image img;

    public Bird () {
        x = flappyBird.WIDTH/2;
        y = flappyBird.HEIGHT/2;
        try {
            img = ImageIO.read(new File("src/dataFlappyBird/sticker,375x360.u2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void physics() {
        x += vx;
        y += vy;
        vy+= 0.5f;
    }
    public void update(Graphics g) {
        g.setColor(Color.black);
        g.drawImage(img,Math.round(x-RAD),Math.round(y-RAD),2*RAD,2*RAD, null);
    }

    public void jump() {
        vy = -10;
    }

    public void reset() {
        x = 640/2;
        y = 640/2;
        vx = vy = 0;
    }
}
