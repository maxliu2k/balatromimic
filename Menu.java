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
	static boolean transitionActive;
	static boolean transitionOverlayStarted;
	static double transitionStart;
	static int battleBaseX;
	static int battleBaseY;
	static int battleBaseL;
	static int battleBaseW;
	void initialize() {
		Playing.initialize();
		Planet.initialize();
		Tarot.initialize();
		Joker.initialize();
		if (Panel.screens[1] == null) {
			Screen battle = new Battle();
			battle.initialize();
			Panel.screens[1] = battle;
		}
		first = false;
		transitionActive = false;
		transitionOverlayStarted = false;
		try {
			BufferedImage temp = ImageIO.read(Menu.class.getResourceAsStream("Important/menu.png"));
			back = temp.getScaledInstance(1500, 1500, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Menu.class.getResourceAsStream("Important/gray.png"));
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
		battleBaseX = battle.x;
		battleBaseY = battle.y;
		battleBaseL = battle.l;
		battleBaseW = battle.w;
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
		for (int i = 0; i < 6; i++) {
			amps[i] = new Button();
			amps[i].amp = (int) (20*Math.random()) + 15;
		}
	}
	static void startBattleTransition() {
		if (transitionActive || Panel.isTransitioning())
			return;
		first = true;
		transitionActive = true;
		transitionOverlayStarted = false;
		transitionStart = Panel.renderTime;
		battle.text = "";
	}
	void check(int x, int y) {
		if (transitionActive || Panel.isTransitioning())
			return;
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
		AffineTransform tx = new AffineTransform();
		tx.translate(640, 360);
		tx.rotate(0.002 * Panel.renderTime);
		tx.translate(-780, -720);
		((Graphics2D) g).drawImage(back, tx, null);
		int hFluct = (int) (128*Math.sin((double) Panel.renderTime/(hori.var % 10 + 10))) + 128;
		hori.color = new Color(hFluct, 0, 255 - hFluct, (int) Math.min(2*Panel.time, 255));
		hori.draw(g);
		int vFluct = (int) (128*Math.sin((double) Panel.renderTime/(vert.var % 10 + 10))) + 128;
		vert.color = new Color(255 - vFluct, 0, vFluct, (int) Math.min(2*Panel.time, 255));
		vert.draw(g);
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
		if (Panel.time > 4*55 && first && !transitionActive) battle.draw(g);

		if (transitionActive) {
			double elapsed = Panel.renderTime - transitionStart;
			double buttonDuration = 15.0;
			double t = Math.min(1.0, elapsed / buttonDuration);
			double scale = 1.0 - 0.6 * t;
			int newL = (int) Math.round(battleBaseL * scale);
			int newW = (int) Math.round(battleBaseW * scale);
			battle.l = Math.max(1, newL);
			battle.w = Math.max(1, newW);
			battle.x = battleBaseX + (battleBaseL - battle.l) / 2;
			battle.y = battleBaseY + (battleBaseW - battle.w) / 2;
			int alpha = (int) Math.round(255 * (1.0 - t));
			battle.color = new Color(70, 150, 255, Math.max(0, Math.min(255, alpha)));
			battle.text = "";
			battle.draw(g);
			if (!transitionOverlayStarted && elapsed >= buttonDuration) {
				transitionOverlayStarted = true;
				Panel.startTransition(1, new Color(70, 150, 255), () -> {
					Battle.dealInitialHand(Battle.DEAL_DELAY_MS);
				});
			}
		}
	}
}

