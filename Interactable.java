package game;
import java.awt.*;
import java.awt.geom.*;

abstract class Interactable {
	int x, y, l, w;
	double vib;
	double wobble;
	double var;
	Interactable() {
		var = (int) (1000*Math.random());
	}
	Interactable(int l0, int w0) {
		l = l0;
		w = w0;
		var = 1000*Math.random();
	}
	Interactable(int x0, int y0, int l0, int w0) {
		x = x0;
		y = y0;
		l = l0;
		w = w0;
		var = 1000*Math.random();
	}
	boolean field(int mx, int my) {
		return mx >= x && mx <= x + l && my >= y && my <= y + w;
	}
	abstract void act();
	abstract void draw(Graphics g);
	void draw(Graphics g, double amp, Color c1, Color c2) {
		int d = d(amp);
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform old = g2d.getTransform();
		if (!(this instanceof Card)) {
			g2d.rotate((double) d(wobble, vib)/300, x + l/2.0, y + w/2.0);
			g2d.setColor(c1);
			g2d.fillRect(x+d, y+d + 5, l - 2*d, w - 10 - 2*d);
			g2d.fillRect(x+d + 5, y+d, l - 10 - 2*d, w - 2*d);
			g2d.setColor(c2);
			g2d.fillRect(x+d + 5, y+d + 10, l - 10 - 2*d, w - 20 - 2*d);
			g2d.fillRect(x+d + 10, y+d + 5, l - 20 - 2*d, w - 10 - 2*d);
		}
		else {
			g2d.rotate((double) wobble/600, x + l/2.0, y + w/2.0);
			g2d.setColor(c1);
			g2d.fillRect(x, y+d + 5, l, w - 10);
			g2d.fillRect(x + 5, y+d, l - 10, w);
			g2d.setColor(c2);
			g2d.fillRect(x + 5, y+d + 10, l - 10, w - 20);
			g2d.fillRect(x + 10, y+d + 5, l - 20, w - 10);
		}
		g2d.setTransform(old);
	}
	int d(double amp) {
		return (int) Math.ceil((amp*Math.sin((Panel.time + var)/((var % 3 + 5)))));
	}
	int d(double amp, double freq) {
		return (int) Math.ceil((amp*Math.sin((Panel.time + var)/freq)));
	}
}