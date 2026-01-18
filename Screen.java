package game;
import java.awt.*;

abstract class Screen {
	static int alpha = 255;
	abstract void initialize();
	abstract void check(int x, int y);
	abstract void draw(Graphics g);
}