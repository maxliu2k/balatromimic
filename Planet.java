package game;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import javax.imageio.*;

class Planet extends Consumable {
	static boolean using;
	String poker;
	static final String[] planets = {"Pluto", "Mercury", "Uranus", "Venus", "Saturn", "Jupiter", "Earth", "Mars", "Neptune",
			"Planet X", "Ceres", "Eris"};
	static Image[] images;
	Image image;
	static void initialize() {
		using = false;
		images = new Image[12];
		try {
			BufferedImage temp = ImageIO.read(Planet.class.getResourceAsStream("Important\\pluto.png"));
			images[0] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Planet.class.getResourceAsStream("Important\\mercury.png"));
			images[1] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Planet.class.getResourceAsStream("Important\\uranus.png"));
			images[2] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Planet.class.getResourceAsStream("Important\\venus.png"));
			images[3] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Planet.class.getResourceAsStream("Important\\saturn.png"));
			images[4] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Planet.class.getResourceAsStream("Important\\jupiter.png"));
			images[5] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Planet.class.getResourceAsStream("Important\\earth.png"));
			images[6] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Planet.class.getResourceAsStream("Important\\mars.png"));
			images[7] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Planet.class.getResourceAsStream("Important\\neptune.png"));
			images[8] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Planet.class.getResourceAsStream("Important\\planetx.png"));
			images[9] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Planet.class.getResourceAsStream("Important\\ceres.png"));
			images[10] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Planet.class.getResourceAsStream("Important\\eris.png"));
			images[11] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
		} catch (Exception e) {}
	}
	Planet(String n) {
		name = n;
		temp = new Color(52, 161, 184);
		for (int i = 0; i < planets.length; i++) {
			if (planets[i].equals(n)) {
				poker = Sidebar.pokers[i];
				image = images[i];
				break;
			}
		}
		cost = 3;
	}
	static Planet get() {
		int random = (int) (12*Math.random());
		while (!Sidebar.avail[random])
			random = (int) (12*Math.random());
		return new Planet(planets[random]);
	}
	void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform old = g2d.getTransform();
		g2d.rotate((double) wobble/300, x + l/2.0, y + w/2.0);
		super.draw(g, selected ? Color.BLACK : temp.darker(), temp);
		g2d.setColor(new Color(52, 161, 184).darker());
		g2d.rotate((double) 0.5*wobble/300, x + l/2.0, y + w/2.0);
		Composite oldc = g2d.getComposite();
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) alpha/255);
	    g2d.setComposite(ac);
		g2d.drawLine(x + 10, y+d(3) + 125, x + 90, y+d(3) + 125);
		g2d.drawLine(x + 10, y+d(3) + 120, x + 90, y+d(3) + 120);
		g2d.drawLine(x + 10, y+d(3) + 115, x + 90, y+d(3) + 115);
		g2d.drawLine(x + 10, y+d(3) + 25, x + 10, y+d(3) + 125);
		g2d.drawLine(x + 90, y+d(3) + 25, x + 90, y+d(3) + 125);
		g2d.drawLine(x + 10, y+d(3) + 25, x + 90, y+d(3) + 25);
		g2d.setColor(Color.WHITE);
		g2d.setFont(Panel.fonts[12]);
		if (name.equals("Pluto"))
			g2d.drawString("Pluto", x + 35, y+d(3) + 20);
		else if (name.equals("Mercury"))
			g2d.drawString("Mercury", x + 24, y+d(3) + 20);
		else if (name.equals("Uranus"))
			g2d.drawString("Uranus", x + 28, y+d(3) + 20);
		else if (name.equals("Venus"))
			g2d.drawString("Venus", x + 32, y+d(3) + 20);
		else if (name.equals("Saturn"))
			g2d.drawString("Saturn", x + 28, y+d(3) + 20);
		else if (name.equals("Jupiter"))
			g2d.drawString("Jupiter", x + 26, y+d(3) + 20);
		else if (name.equals("Earth"))
			g2d.drawString("Earth", x + 32, y+d(3) + 20);
		else if (name.equals("Mars"))
			g2d.drawString("Mars", x + 34, y+d(3) + 20);
		else if (name.equals("Neptune"))
			g2d.drawString("Neptune", x + 26, y+d(3) + 20);
		else if (name.equals("Planet X"))
			g2d.drawString("Planet X", x + 24, y+d(3) + 20);
		else if (name.equals("Ceres"))
			g2d.drawString("Ceres", x + 32, y+d(3) + 20);
		else if (name.equals("Eris"))
			g2d.drawString("Eris", x + 38, y+d(3) + 20);
		g2d.drawImage(image, x + 15, y + 35 + d(3), null);
		g2d.setComposite(oldc);
		g2d.setTransform(old);
	}
	void use() {
		Sidebar.levels[Sidebar.getIndex(poker)]++;
	}
	boolean useable() {
		if (Panel.s == 1)
			return Battle.selected == 0;
		else
			return true;
	}
}