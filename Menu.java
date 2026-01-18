package game;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import javax.imageio.*;

class Menu extends Screen {
	static boolean first;
	static Image back, gray;
	static int count;
	static Playing ace;
	static Button battle, secret, quit;
	static Interactable[] quad;
	static Button vert, hori;
	static Button[] amps;
	void initialize() {
		//initialize cards
		Playing.initialize();
		Planet.initialize();
		Tarot.initialize();
		Joker.initialize();
		first = false;
		try {
			BufferedImage temp = ImageIO.read(Menu.class.getResourceAsStream("Important\\menu.png"));
			back = temp.getScaledInstance(1500, 1500, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Menu.class.getResourceAsStream("Important\\gray.png"));
			gray = temp.getScaledInstance(1500, 1500, Image.SCALE_SMOOTH);
		} catch (Exception e) {}
		count = 0;
		ace = new Playing("Ace", "Spades");
		ace.setCoords(590, 150);
		ace.temp = Color.WHITE;
		battle = new Button(250, 450, 300, 120, "battle", "PLAY", new Color(70, 150, 255, 0));
		battle.setFormat(105, 65, 40, 0);
		battle.wobble = 10;
		battle.vib = 20;
		secret = new Button(580, 470, 120, 80, "secret", "0", new Color(200, 150, 0, 0));
		secret.setFormat(43, 52, 30, 10);
		quit = new Button(730, 450, 300, 120, "quit", "QUIT", Color.RED);
		quit.setFormat(105, 65, 40, 0);
		quad = new Interactable[4];
		quad[0] = ace;
		quad[1] = battle;
		quad[2] = secret;
		quad[3] = quit;
		hori = new Button(85, 177, 1110, 80, "", "", new Color(0, 0, 255));
		hori.setFormat(0, 0, 0, 0);
		vert = new Button(600, 30, 80, 380, "", "", new Color(255, 0, 0));
		vert.setFormat(0, 0, 0, 0);
		amps = new Button[6];
		//random oscillations for letters
		for (int i = 0; i < 6; i++) {
			amps[i] = new Button();
			amps[i].amp = (int) (20*Math.random()) + 15;
		}
	}
	void check(int x, int y) {
		//checks interactions for the four cards
		if (!first) {
			for (Interactable i : quad)
				if (i != null && i.field(x, y))
					i.act();
			if ((secret.field(x, y) || ace.field(x, y)) && count == 20 && ace.selected) {
				Panel.loadFonts(true);
			}
		}
	}
	void draw(Graphics g) {
		//draw background
		AffineTransform tx = new AffineTransform();
		tx.translate(640, 360); // move origin to screen center
		tx.rotate(0.002 * Panel.time); // rotate around that point
		tx.translate(-780, -720); // center the image
		((Graphics2D) g).drawImage(back, tx, null);
		//draw rects with color oscillation
		int hFluct = (int) (128*Math.sin((double) Panel.time/(hori.var % 10 + 10))) + 128;
		hori.color = new Color(hFluct, 0, 255 - hFluct, (int) Math.min(2*Panel.time, 255));
		hori.draw(g);
		int vFluct = (int) (128*Math.sin((double) Panel.time/(vert.var % 10 + 10))) + 128;
		vert.color = new Color(255 - vFluct, 0, vFluct, (int) Math.min(2*Panel.time, 255));
		vert.draw(g);
		//draw letters and buttons delayed
		g.setFont(Panel.fonts[180]);
		if (Panel.time > 4*15) g.setColor(new Color(255,255,255,(int) Math.min(3*(Panel.time-60), 255)));
		if (Panel.time > 4*15) g.drawString("B", 200, 307+amps[0].d(amps[0].amp));
		if (Panel.time > 4*20) g.setColor(new Color(255,255,255,(int) Math.min(3*(Panel.time-80), 255)));
		if (Panel.time > 4*20) g.drawString("A", 320, 307+amps[1].d(amps[1].amp));
		if (Panel.time > 4*25) g.setColor(new Color(255,255,255,(int) Math.min(3*(Panel.time-100), 255)));
		if (Panel.time > 4*25) g.drawString("L", 440, 307+amps[2].d(amps[2].amp));
		if (Panel.time < 4*55) ace.breaking = true;
		else ace.breaking = false;
		if (Panel.time > 4*30) ace.alpha = (int) Math.min(3*(Panel.time-120), 255);
		if (Panel.time > 4*30) ace.draw(g);
		g.setFont(Panel.fonts[180]);
		if (Panel.time > 4*35) g.setColor(new Color(255,255,255,(int) Math.min(3*(Panel.time-140), 255)));
		if (Panel.time > 4*35) g.drawString("T", 761, 307+amps[3].d(amps[3].amp));
		if (Panel.time > 4*40) g.setColor(new Color(255,255,255,(int) Math.min(3*(Panel.time-160), 255)));
		if (Panel.time > 4*40) g.drawString("R", 880, 307+amps[4].d(amps[4].amp));
		if (Panel.time > 4*45) g.setColor(new Color(255,255,255,(int) Math.min(3*(Panel.time-180), 255)));
		if (Panel.time > 4*45) g.drawString("O", 1000, 307+amps[5].d(amps[5].amp));
		if (Panel.time > 4*55) battle.color = new Color(70, 150, 255, (int) Math.min(5*(Panel.time-220), 255));
		if (Panel.time > 4*55 && !first) battle.draw(g);
		if (Panel.time > 4*65) secret.color = new Color(200, 150, 0, (int) Math.min(5*(Panel.time-260), 255));
		if (Panel.time > 4*65) secret.draw(g);
		if (Panel.time > 4*75) quit.color = new Color(255, 0, 0, (int) Math.min(5*(Panel.time-300), 255));
		if (Panel.time > 4*75) quit.draw(g);
		if (Panel.time > 4*55 && first) battle.draw(g);
	}
}