package main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import entity.Player;

public class Utility {
	
	public static boolean coolDown(int lastUse, int coolDownTime) {
		return GamePanel.timeMilli > lastUse + coolDownTime;
	}
	
	public static BufferedImage scaleImage(BufferedImage original, int width, int height) {
		BufferedImage image = new BufferedImage(width, height, original.getType());
		Graphics2D g = image.createGraphics();
		g.drawImage(original, 0, 0, width, height, null);
		g.dispose();
		return image;
	}
	
	public static boolean isInScreen(GamePanel gp, Body b, Player player) {
		//number of tiles to load out of screen
		//min 1 so it doesn't lag, put it to the round int of the highest image / tilesize
		int extendScreen = 2;
		
		if (b.worldX + gp.tileSize * extendScreen > player.worldX - player.screenX &&
				b.worldX - gp.tileSize * extendScreen < player.worldX + player.screenX &&
				b.worldY + gp.tileSize * extendScreen > player.worldY - player.screenY &&
				b.worldY - gp.tileSize * extendScreen < player.worldY + player.screenY) {
			return true;
		}
		return false;
	}
	
	public static BufferedImage setUpImage(String imagePath) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(Utility.class.getResourceAsStream(imagePath));
			image = Utility.scaleImage(image, image.getWidth() * GamePanel.scale, image.getHeight() * GamePanel.scale);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	public static void drawLine(Graphics g, double xc, double yc, double xt, double yt, int blockSize, int r) {
		//out / in circle set new target logic
		double xr = xt;
		double yr = yt;
		if (Math.pow(xt - xc, 2) + Math.pow(yt - yc, 2) > Math.pow(r, 2)) {
    		double a;
    		if (xt == xc) {
    			xr = xt;
    			if (yt > yc) {
    				yr = yc + r;
    			} else {
    				yr = yc - r;
    			}
    		} else{
    			a = Math.atan2(yc - yt, xc - xt);
    			xr = r * Math.cos(a);
    			yr = r * Math.sin(a);
    			if (xr > xc) {
    				xr = xr - xc;
        			yr = yr - yc;
    			} else {
    				xr = xc - xr;
        			yr = yc - yr;
    			}
    		}
    	}
    	xt = (int) xr;
    	yt = (int) yr;
		
    	//draw to new target logic
        int scaledCenterX = (int) (xc / blockSize);
        int scaledCenterY = (int) (yc / blockSize);
        int scaledTargetX = (int) (xt / blockSize);
        int scaledTargetY = (int) (yt / blockSize);
        int dx = scaledTargetX - scaledCenterX;
        int dy = scaledTargetY - scaledCenterY;
        int stepX = Integer.signum(dx);
        int stepY = Integer.signum(dy);
        dx = Math.abs(dx);
        dy = Math.abs(dy);
        int dx2 = dx << 1;
        int dy2 = dy << 1;
        int x = scaledCenterX;
        int y = scaledCenterY;
        int error;
        if (dx >= dy) {
            error = dy2 - dx;
            do {
            	plot(g, x, y, blockSize);
                if (error > 0) {
                    y += stepY;
                    error -= dx2;
                }
                error += dy2;
                x += stepX;
            } while (x != scaledTargetX);
        } else {
            error = dx2 - dy;
            do {
            	plot(g, x, y, blockSize);
                if (error > 0) {
                    x += stepX;
                    error -= dy2;
                }
                error += dx2;
                y += stepY;
            } while (y != scaledTargetY);
        }
    }
	
	//draw the pixel
    static void plot(Graphics g, int x, int y, int blockSize) {
        int x0 = x * blockSize;
        int y0 = y * blockSize;
        int w = blockSize;
        int h = blockSize;
        g.fillRect(x0, y0, w, h);
    }
    
    public static boolean chance(double percentage) {
    	if (new Random().nextDouble(100) >= percentage) {
    		return true;
    	}
		return false;
    }
    
    public static double randomNumber(double max) {
    	return new Random().nextDouble(max);
    }
    
    public static void drawCenteredString(Graphics2D g, String text, double x, double y) {
    	int textLength = (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
    	int textHeight = (int) g.getFontMetrics().getStringBounds(text, g).getHeight();
    	g.drawString(text, (int) x - textLength / 2, (int) y - textHeight / 2);
    }
    
    public static Point getPlayerCenter(GamePanel gp, Player p) {
    	return new Point((int) p.screenX + gp.tileSize / 2, (int) p.screenY + gp.tileSize / 2);
    }
}
