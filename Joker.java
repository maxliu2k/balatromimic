package game;
import java.util.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;

class Joker extends Editionable {
	String name;
	char rarity;
	int sell;
	int count;
	static boolean flag;
	static Card current;
	static Image[] jimbos;
	static ArrayList<Joker> commons;
	static ArrayList<Joker> uncommons;
	static ArrayList<Joker> rares;
	static ArrayList<Joker> legendaries;
	static Joker banana1;
	static void initialize() {
		jimbos = new Image[100];
		try {
			BufferedImage temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\joker.png"));
			jimbos[0] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\basicmult.png"));
			jimbos[1] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\basicchip.png"));
			jimbos[2] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\half.png"));
			jimbos[3] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\stencil.png"));
			jimbos[4] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\fingers.png"));
			jimbos[5] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\mime.png"));
			jimbos[6] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\credit.png"));
			jimbos[7] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\lust.png"));
			jimbos[8] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\greed.png"));
			jimbos[9] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\glut.png"));
			jimbos[10] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\wrath.png"));
			jimbos[11] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\dagger.png"));
			jimbos[12] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\fingersback.png"));
			jimbos[13] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\banner.png"));
			jimbos[14] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\summit.png"));
			jimbos[15] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\marble.png"));
			jimbos[16] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\loyalty.png"));
			jimbos[17] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\8ball.png"));
			jimbos[18] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\misprint.png"));
			jimbos[19] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\dusk.png"));
			jimbos[20] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\fist.png"));
			jimbos[21] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\chaos.png"));
			jimbos[22] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\fibbi.png"));
			jimbos[23] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\steel.png"));
			jimbos[24] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\scary.png"));
			jimbos[25] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\abstract.png"));
			jimbos[26] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\delayed.png"));
			jimbos[27] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\delayedback.png"));
			jimbos[28] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\hack.png"));
			jimbos[29] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\parei.png"));
			jimbos[30] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\banana1.png"));
			jimbos[31] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\scholar.png"));
			jimbos[32] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\business.png"));
			jimbos[33] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\supernova.png"));
			jimbos[34] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\bus.png"));
			jimbos[35] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\spaceman.png"));
			jimbos[36] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\egg.png"));
			jimbos[37] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\burglar.png"));
			jimbos[38] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\blackboard.png"));
			jimbos[39] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Joker.class.getResourceAsStream("Jokers\\runner.png"));
			jimbos[40] = temp.getScaledInstance(70, 100, Image.SCALE_SMOOTH);
		} catch (Exception e) {
			e.printStackTrace();
		}
		commons = new ArrayList<>();
		uncommons = new ArrayList<>();
		commons.add(new Joker("Joker", 'c', 2, Color.WHITE));
		commons.add(new Joker("Lusty", 'c', 5, new Color(245, 96, 96)));
		commons.add(new Joker("Greedy", 'c', 5, new Color(230, 91, 0)));
		commons.add(new Joker("Gluttonous", 'c', 5, new Color(68, 186, 212)));
		commons.add(new Joker("Wrathful", 'c', 5, new Color(114, 67, 235)));
		commons.add(new Joker("Jolly", 'c', 3, Color.WHITE));
		commons.add(new Joker("Zany", 'c', 4, Color.WHITE));
		commons.add(new Joker("Mad", 'c', 4, Color.WHITE));
		commons.add(new Joker("Crazy", 'c', 4, Color.WHITE));
		commons.add(new Joker("Droll", 'c', 4, Color.WHITE));
		commons.add(new Joker("Sly", 'c', 3, Color.WHITE));
		commons.add(new Joker("Wily", 'c', 4, Color.WHITE));
		commons.add(new Joker("Clever", 'c', 4, Color.WHITE));
		commons.add(new Joker("Devious", 'c', 4, Color.WHITE));
		commons.add(new Joker("Crafty", 'c', 4, Color.WHITE));
		commons.add(new Joker("Half", 'c', 5, Color.WHITE));
		uncommons.add(new Joker("Stencil", 'u', 8, Color.WHITE));
		uncommons.add(new Joker("Four Fingers", 'u', 7, new Color(150, 52, 192)));
		uncommons.add(new Joker("Mime", 'u', 5, Color.WHITE));
		commons.add(new Joker("Credit Card", 'c', 1, new Color(186, 22, 13)));
		uncommons.add(new Joker("Dagger", 'u', 5, new Color(21, 59, 47)));
		commons.add(new Joker("Banner", 'c', 5, Color.WHITE));
		commons.add(new Joker("Summit", 'c', 5, new Color(195, 195, 195)));
		uncommons.add(new Joker("Marble", 'u', 6, Color.WHITE));
		uncommons.add(new Joker("Loyalty Card", 'u', 5, Color.WHITE));
		commons.add(new Joker("8 Ball", 'c', 5, new Color(199, 135, 222)));
		commons.add(new Joker("Misprint", 'c', 4, Color.WHITE));
		uncommons.add(new Joker("Dusk", 'u', 5, new Color(81, 84, 110)));
		commons.add(new Joker("Raised Fist", 'c', 5, new Color(153, 2, 2)));
		commons.add(new Joker("Chaos", 'c', 4, Color.WHITE));
		uncommons.add(new Joker("Fibbonacci", 'u', 8, new Color(37, 117, 230)));
		uncommons.add(new Joker("Steel", 'u', 7, Color.WHITE));
		commons.add(new Joker("Scary Face", 'c', 4, Color.RED));
		commons.add(new Joker("Abstract", 'c', 4, Color.WHITE));
		commons.add(new Joker("Gratification", 'c', 4, Color.WHITE));
		uncommons.add(new Joker("Hack", 'u', 6, Color.RED.darker()));
		uncommons.add(new Joker("Pareidolia", 'u', 5, new Color(35, 168, 102)));
		banana1 = new Joker("Gros Michel", 'c', 5, new Color(248, 252, 129));
		commons.add(banana1);
		commons.add(new Joker("Even Steven", 'c', 4, Color.WHITE));
		commons.add(new Joker("Odd Todd", 'c', 4, Color.WHITE));
		commons.add(new Joker("Scholar", 'c', 4, Color.WHITE));
		commons.add(new Joker("Business", 'c', 4, Color.WHITE));
		commons.add(new Joker("Supernova", 'c', 5, new Color(19, 34, 84)));
		commons.add(new Joker("Ride the Bus", 'c', 6, Color.YELLOW));
		uncommons.add(new Joker("Spaceman", 'u', 5, Color.WHITE));
		commons.add(new Joker("Egg", 'c', 4, Color.WHITE));
		uncommons.add(new Joker("Burglar", 'u', 6, Color.WHITE));
		uncommons.add(new Joker("Blackboard", 'u', 6, new Color(51, 61, 52)));
		commons.add(new Joker("Runner", 'c', 5, Color.WHITE));
	}
	Joker(String n) {
		name = n;
		Joker j = find(n);
		rarity = j.rarity;
		cost = j.cost;
		temp = j.temp;
		border = j.border;
		original = j.original;
		count = 0;
		int rollChance = (int) (1000*Math.random());
		if (rollChance < 5) {
			edition = 'P';
			cost += 5;
		}
		else if (rollChance < 20) {
			edition = 'H';
			cost += 3;
		}
		else if (rollChance < 40) {
			edition = 'F';
			cost += 2;
		}
		else if (rollChance > 1000 - 3) {
			edition = 'N';
			cost += 5;
		}
		else
			edition = ' ';
		sell = Math.max(cost/2, 1);
	}
	Joker(String n, char r, int c, Color t) {
		name = n;
		rarity = r;
		cost = c;
		temp = t;
		sell = Math.max(c/2, 1);
		border = t.darker();
		original = t;
		count = 0;
		edition = ' ';
	}
	static Joker get() {
		Joker j;
		if (Math.random() < 0.75)
			j = new Joker(commons.get((int) (commons.size()*Math.random())).name);
		else
			j = new Joker(uncommons.get((int) (uncommons.size()*Math.random())).name);
		return j;
	}
	void act() {
		super.act();
	}
	void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform old = g2d.getTransform();
		g2d.rotate((double) wobble/300, x + l/2.0, y + w/2.0);
		if (original == null)
			original = Color.WHITE;
		original = new Color(original.getRed(),original.getGreen(),original.getBlue(),alpha);
		if (!override)
			border = selected ? Color.BLACK : new Color(original.getRed(),original.getGreen(),original.getBlue(),alpha).darker();
		if (!(name.equals("Steel")))
			if (edition != 'N')
				draw(g, 3, override || selected ? border : edition == ' ' ? border : getEdition(), original);
			else
				draw(g, 3, original, border);
		g2d.rotate((double) 0.5*wobble/300, x + l/2.0, y + w/2.0);
		Composite oldc = g2d.getComposite();
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) alpha/255);
	    g2d.setComposite(ac);
		if (name.equals("Joker"))
			g2d.drawImage(jimbos[0], x + 15, y + 22 + d(3), null);
		else if (name.equals("Lusty")) {
			g2d.drawImage(Playing.heart, x + 18, y + 62 + d(3), null);
			g2d.drawImage(jimbos[8], x + 15, y + 22 + d(3), null);
		}
		else if (name.equals("Greedy")) {
			g2d.drawImage(Playing.diamond, x + 16, y + 54 + d(3), null);
			g2d.drawImage(jimbos[9], x + 15, y + 22 + d(3), null);
		}
		else if (name.equals("Gluttonous")) {
			g2d.drawImage(Playing.club, x + 18, y + 54 + d(3), null);
			g2d.drawImage(jimbos[10], x + 15, y + 15 + d(3), null);
		}
		else if (name.equals("Wrathful")) {
			g2d.drawImage(Playing.spade, x + 18, y + 50 + d(3), null);
			g2d.drawImage(jimbos[11], x + 15, y + 15 + d(3), null);
		}
		else if (name.equals("Jolly")) {
			g2d.drawImage(jimbos[1], x + 15, y + 15 + d(3), null);
			g2d.setColor(edition == 'N' ? Color.WHITE : Color.BLACK);
			g2d.setFont(Panel.fonts[12]);
			g2d.drawString("A", x + 30, y + 110 + d(3));
			g2d.drawString("A", x + 66, y + 110 + d(3));
		}
		else if (name.equals("Zany")) {
			g2d.drawImage(jimbos[1], x + 15, y + 15 + d(3), null);
			g2d.setColor(edition == 'N' ? Color.WHITE : Color.BLACK);
			g2d.setFont(Panel.fonts[12]);
			g2d.drawString("A", x + 30, y + 110 + d(3));
			g2d.drawString("A", x + 66, y + 110 + d(3));
			g2d.drawString("A", x + 48, y + 120 + d(3));
		}
		else if (name.equals("Mad")) {
			g2d.drawImage(jimbos[1], x + 15, y + 15 + d(3), null);
			g2d.setColor(edition == 'N' ? Color.WHITE : Color.BLACK);
			g2d.setFont(Panel.fonts[12]);
			g2d.drawString("K", x + 20, y + 95 + d(3));
			g2d.drawString("K", x + 30, y + 110 + d(3));
			g2d.drawString("2", x + 66, y + 110 + d(3));
			g2d.drawString("2", x + 76, y + 95 + d(3));
		}
		else if (name.equals("Crazy")) {
			g2d.drawImage(jimbos[1], x + 15, y + 15 + d(3), null);
			g2d.setColor(edition == 'N' ? Color.WHITE : Color.BLACK);
			g2d.setFont(Panel.fonts[12]);
			g2d.drawString("3", x + 20, y + 95 + d(3));
			g2d.drawString("4", x + 30, y + 110 + d(3));
			g2d.drawString("5", x + 47, y + 120 + d(3));
			g2d.drawString("6", x + 66, y + 110 + d(3));
			g2d.drawString("7", x + 76, y + 95 + d(3));
		}
		else if (name.equals("Droll")) {
			g2d.drawImage(jimbos[1], x + 15, y + 15 + d(3), null);
			Image suit = Playing.heart.getScaledInstance(10, 10, Image.SCALE_DEFAULT);
			g2d.drawImage(suit, x + 18, y + 88 + d(3), null);
			g2d.drawImage(suit, x + 28, y + 103 + d(3), null);
			g2d.drawImage(suit, x + 45, y + 113 + d(3), null);
			g2d.drawImage(suit, x + 64, y + 103 + d(3), null);
			g2d.drawImage(suit, x + 74, y + 88 + d(3), null);
		}
		else if (name.equals("Sly")) {
			g2d.drawImage(jimbos[2], x + 15, y + 15 + d(3), null);
			g2d.setColor(edition == 'N' ? Color.WHITE : Color.BLACK);
			g2d.setFont(Panel.fonts[12]);
			g2d.drawString("A", x + 30, y + 110 + d(3));
			g2d.drawString("A", x + 66, y + 110 + d(3));
		}
		else if (name.equals("Wily")) {
			g2d.drawImage(jimbos[2], x + 15, y + 15 + d(3), null);
			g2d.setColor(edition == 'N' ? Color.WHITE : Color.BLACK);
			g2d.setFont(Panel.fonts[12]);
			g2d.drawString("A", x + 30, y + 110 + d(3));
			g2d.drawString("A", x + 66, y + 110 + d(3));
			g2d.drawString("A", x + 48, y + 120 + d(3));
		}
		else if (name.equals("Clever")) {
			g2d.drawImage(jimbos[2], x + 15, y + 15 + d(3), null);
			g2d.setColor(edition == 'N' ? Color.WHITE : Color.BLACK);
			g2d.setFont(Panel.fonts[12]);
			g2d.drawString("K", x + 20, y + 95 + d(3));
			g2d.drawString("K", x + 30, y + 110 + d(3));
			g2d.drawString("2", x + 66, y + 110 + d(3));
			g2d.drawString("2", x + 76, y + 95 + d(3));
		}
		else if (name.equals("Devious")) {
			g2d.drawImage(jimbos[2], x + 15, y + 15 + d(3), null);
			g2d.setColor(edition == 'N' ? Color.WHITE : Color.BLACK);
			g2d.setFont(Panel.fonts[12]);
			g2d.drawString("3", x + 20, y + 95 + d(3));
			g2d.drawString("4", x + 30, y + 110 + d(3));
			g2d.drawString("5", x + 47, y + 120 + d(3));
			g2d.drawString("6", x + 66, y + 110 + d(3));
			g2d.drawString("7", x + 76, y + 95 + d(3));
		}
		else if (name.equals("Crafty")) {
			g2d.drawImage(jimbos[2], x + 15, y + 15 + d(3), null);
			Image suit = Playing.heart.getScaledInstance(10, 10, Image.SCALE_DEFAULT);
			g2d.drawImage(suit, x + 18, y + 88 + d(3), null);
			g2d.drawImage(suit, x + 28, y + 103 + d(3), null);
			g2d.drawImage(suit, x + 45, y + 113 + d(3), null);
			g2d.drawImage(suit, x + 64, y + 103 + d(3), null);
			g2d.drawImage(suit, x + 74, y + 88 + d(3), null);
		}
		else if (name.equals("Half"))
			g2d.drawImage(jimbos[3], x + 15, y + 22 + d(3), null);
		else if (name.equals("Stencil"))
			g2d.drawImage(jimbos[4], x + 15, y + 22 + d(3), null);
		else if (name.equals("Four Fingers")) {
			g2d.drawImage(jimbos[13], x + 15, y + 28 + d(3), null);
			g2d.drawImage(jimbos[5], x + 15, y + 23 + d(3), null);
		}
		else if (name.equals("Mime"))
			g2d.drawImage(jimbos[6], x + 15, y + 22 + d(3), null);
		else if (name.equals("Credit Card"))
			g2d.drawImage(jimbos[7], x + 22, y + 30 + d(3), null);
		else if (name.equals("Dagger")) {
			g2d.drawImage(jimbos[12], x + 15, y + 25 + d(3), null);
			g2d.setColor(Color.WHITE);
			g2d.setFont(Panel.fonts[12]);
			g2d.drawString("+" + count, x + 10, y + 130 + d(3));
		}
		else if (name.equals("Banner"))
			g2d.drawImage(jimbos[14], x + 15, y + 36 + d(3), null);
		else if (name.equals("Summit"))
			g2d.drawImage(jimbos[15], x + 15, y + 26 + d(3), null);
		else if (name.equals("Marble"))
			g2d.drawImage(jimbos[16], x + 15, y + 22 + d(3), null);
		else if (name.equals("Loyalty Card")) {
			g2d.drawImage(jimbos[17], x + 20, y + 28 + d(3), null);
			g2d.setColor(Color.LIGHT_GRAY);
			for (int i = 0; i < 5; i++) {
				g2d.fillRect(x + 25, y + 44 + d(3) + 15*i, 6, 10);
				g2d.fillRect(x + 23, y + 46 + d(3) + 15*i, 10, 6);
			}
			g2d.setColor(count == 5 ? new Color(140, 0, 200) : Color.BLACK);
			for (int i = 0; i < count; i++) {
				g2d.fillRect(x + 25, y + 44 + d(3) + 15*i, 6, 10);
				g2d.fillRect(x + 23, y + 46 + d(3) + 15*i, 10, 6);
			}
		}
		else if (name.equals("8 Ball"))
			g2d.drawImage(jimbos[18], x + 15, y + 25 + d(3), null);
		else if (name.equals("Misprint"))
			g2d.drawImage(jimbos[19], x + 15, y + 22 + d(3), null);
		else if (name.equals("Dusk"))
			g2d.drawImage(jimbos[20], x + 15, y + 26 + d(3), null);
		else if (name.equals("Raised Fist"))
			g2d.drawImage(jimbos[21], x + 15, y + 36 + d(3), null);
		else if (name.equals("Chaos"))
			g2d.drawImage(jimbos[22], x + 15, y + 22 + d(3), null);
		else if (name.equals("Fibbonacci"))
			g2d.drawImage(jimbos[23], x + 15, y + 26 + d(3), null);
		else if (name.equals("Steel")) {
			original = edition == ' ' ? new Color(120 + d(20), 120 + d(20), 120 + d(20)) : getEdition();
			AffineTransform newOld = g2d.getTransform();
			g2d.rotate(-0.6 * (double) wobble/300, x + l/2.0, y + w/2.0);
			if (edition != 'N')
				draw(g, 3, !override && !selected ? new Color(180 + d(20), 180 + d(20), 180 + d(20)).darker() : Color.BLACK, 
						new Color(180 + d(20), 180 + d(20), 180 + d(20)));
			else
				draw(g, 3, new Color(180 + d(20), 180 + d(20), 180 + d(20)),
						!override && !selected ? new Color(180 + d(20), 180 + d(20), 180 + d(20)).darker() : Color.BLACK);
			g2d.setTransform(newOld);
			g2d.drawImage(jimbos[24], x + 15, y + 22 + d(3), null);
			g2d.setColor(edition == 'N' ? Color.WHITE : Color.BLACK);
			g2d.setFont(Panel.fonts[12]);
			g2d.drawString("X" + (1 + count*0.2), x + 10, y + 130 + d(3));
		}
		else if (name.equals("Scary Face"))
			g2d.drawImage(jimbos[25], x + 15, y + 26 + d(3), null);
		else if (name.equals("Abstract"))
			g2d.drawImage(jimbos[26], x + 15, y + 22 + d(3), null);
		else if (name.equals("Gratification")) {
			g2d.drawImage(jimbos[28], x + 15, y + 25 + d(3), null);
			g2d.drawImage(jimbos[27], x + 15, y + 25 + d(3), null);
		}
		else if (name.equals("Hack"))
			g2d.drawImage(jimbos[29], x + 15, y + 26 + d(3), null);
		else if (name.equals("Pareidolia"))
			g2d.drawImage(jimbos[30], x + 15, y + 23 + d(3), null);
		else if (name.equals("Gros Michel"))
			g2d.drawImage(jimbos[31], x + 15, y + 28 + d(3), null);
		else if (name.equals("Even Steven")) {
			g2d.drawImage(jimbos[1], x + 15, y + 15 + d(3), null);
			g2d.setColor(edition == 'N' ? Color.WHITE : Color.BLACK);
			g2d.setFont(Panel.fonts[12]);
			g2d.drawString("2", x + 20, y + 95 + d(3));
			g2d.drawString("4", x + 30, y + 110 + d(3));
			g2d.drawString("6", x + 48, y + 120 + d(3));
			g2d.drawString("8", x + 66, y + 110 + d(3));
			g2d.drawString("10", x + 72, y + 95 + d(3));
			//g2d.drawString("0", x + 78, y + 95 + d(3));
		}
		else if (name.equals("Odd Todd")) {
			g2d.drawImage(jimbos[2], x + 15, y + 15 + d(3), null);
			g2d.setColor(edition == 'N' ? Color.WHITE : Color.BLACK);
			g2d.setFont(Panel.fonts[12]);
			g2d.drawString("3", x + 20, y + 95 + d(3));
			g2d.drawString("5", x + 30, y + 110 + d(3));
			g2d.drawString("7", x + 48, y + 120 + d(3));
			g2d.drawString("9", x + 66, y + 110 + d(3));
			g2d.drawString("A", x + 76, y + 95 + d(3));
		}
		else if (name.equals("Scholar"))
			g2d.drawImage(jimbos[32], x + 15, y + 22 + d(3), null);
		else if (name.equals("Business"))
			g2d.drawImage(jimbos[33], x + 12, y + 27 + d(3), null);
		else if (name.equals("Supernova"))
			g2d.drawImage(jimbos[34], x + 15, y + 26 + d(3), null);
		else if (name.equals("Ride the Bus")) {
			g2d.drawImage(jimbos[35], x + 15, y + 25 + d(3), null);
			g2d.setColor(Color.BLACK);
			g2d.setFont(Panel.fonts[12]);
			g2d.drawString("+" + count, x + 10, y + 130 + d(3));
		}
		else if (name.equals("Spaceman"))
			g2d.drawImage(jimbos[36], x + 15, y + 30 + d(3), null);
		else if (name.equals("Egg"))
			g2d.drawImage(jimbos[37], x + 15, y + 30 + d(3), null);
		else if (name.equals("Burglar"))
			g2d.drawImage(jimbos[38], x + 15, y + 22 + d(3), null);
		else if (name.equals("Blackboard"))
			g2d.drawImage(jimbos[39], x + 15, y + 22 + d(3), null);
		else if (name.equals("Runner"))
			g2d.drawImage(jimbos[40], x + 15, y + 22 + d(3), null);
		g2d.setFont(Panel.fonts[12]);
		if (original == null)
			original = Color.WHITE;
		g2d.setColor(299 * original.getRed() + 587 * original.getGreen() + 114 * original.getBlue() > 128000 ? Color.BLACK : Color.WHITE);
		if (name.equals("Steel"))
			g2d.setColor(Color.BLACK);
		if (edition == 'N')
			g2d.setColor(Color.WHITE);
		g2d.drawString(name, x + 10, y+d(3) + 22);
		g2d.setComposite(oldc);
		g2d.setTransform(old);
	}
	void performNull() {
		if (edition == 'F')
			Sidebar.addChips(this, new Color(12, 134, 204), 50);
		else if (edition == 'H')
			Sidebar.addMult(this, Color.RED, 10);
		HashMap<String, Integer> suits = new HashMap<>();
		HashMap<String, Integer> ranks = new HashMap<>();
		for (Playing c : Battle.played) {
			if (c.enhancement == 'T')
				continue;
			if (c.enhancement != 'W') {
				if (suits.keySet().contains(c.suit))
					suits.put(c.suit, suits.get(c.suit) + 1);
				else
					suits.put(c.suit, 1);
			}
			else {
				for (String suit : Sidebar.suitsList) {
					if (suits.keySet().contains(suit))
						suits.put(suit, suits.get(suit) + 1);
					else
						suits.put(suit, 1);
				}
			}
			if (ranks.keySet().contains(c.rank))
				ranks.put(c.rank, ranks.get(c.rank) + 1);
			else
				ranks.put(c.rank, 1);
		}
		boolean black = true;
		for (Playing c : Battle.hand)
			if ((c.enhancement != 'W' && (c.suit.equals("Hearts") || c.suit.equals("Diamonds"))) || c.enhancement == 'T')
				black = false;
		if (name.equals("Joker"))
			Sidebar.addMult(this, Color.BLACK, 4);
		else if (name.equals("Jolly") && Sidebar.ofAKind(ranks, 2))
			Sidebar.addMult(this, Color.BLACK, 8);
		else if (name.equals("Zany") && Sidebar.ofAKind(ranks, 3))
			Sidebar.addMult(this, Color.BLACK, 12);
		else if (name.equals("Mad") && Sidebar.house(ranks, 2))
			Sidebar.addMult(this, Color.BLACK, 10);
		else if (name.equals("Crazy") && Sidebar.straight(Battle.played))
			Sidebar.addMult(this, Color.BLACK, 12);
		else if (name.equals("Droll") && Sidebar.flush(suits))
			Sidebar.addMult(this, Color.BLACK, 10);
		else if (name.equals("Sly") && Sidebar.ofAKind(ranks, 2))
			Sidebar.addChips(this, Color.BLACK, 50);
		else if (name.equals("Wily") && Sidebar.ofAKind(ranks, 3))
			Sidebar.addChips(this, Color.BLACK, 100);
		else if (name.equals("Clever") && Sidebar.house(ranks, 2))
			Sidebar.addChips(this, Color.BLACK, 80);
		else if (name.equals("Devious") && Sidebar.straight(Battle.played))
			Sidebar.addChips(this, Color.BLACK, 100);
		else if (name.equals("Crafty") && Sidebar.flush(suits))
			Sidebar.addChips(this, Color.BLACK, 80);
		else if (name.equals("Half") && Battle.played.size() <= 3)
			Sidebar.addMult(this, Color.BLACK, 20);
		else if (name.equals("Stencil") && Sidebar.jokers.size() < Sidebar.jslots)
			Sidebar.timesMult(this, Color.BLACK, Sidebar.jslots - Sidebar.jokers.size() + num("Stencil"));
		else if (name.equals("Dagger") && count > 0)
			Sidebar.addMult(this, Color.BLACK, count);
		else if (name.equals("Banner") && Sidebar.discs > 0)
			Sidebar.addChips(this, Color.BLACK, 30 * Sidebar.discs);
		else if (name.equals("Summit") && Sidebar.discs == 0)
			Sidebar.addMult(this, Color.BLACK, 15);
		else if (name.equals("Loyalty Card") && count == 5) {
			count = 0;
			Sidebar.timesMult(this, Color.BLACK, 4);
		}
		else if (name.equals("Misprint"))
			Sidebar.addMult(this, Color.BLACK, (int) (24*Math.random()));
		else if (name.equals("Steel") && count > 0)
			Sidebar.timesMult(this, Color.BLACK, 1 + count*0.2);
		else if (name.equals("Abstract"))
			Sidebar.addMult(this, Color.BLACK, Sidebar.jokers.size() * 3);
		else if (name.equals("Gros Michel"))
			Sidebar.addMult(this, Color.BLACK, 15);
		else if (name.equals("Supernova"))
			Sidebar.addMult(this, Color.BLACK, Sidebar.counts[Sidebar.getIndex(Sidebar.finalHand)]);
		else if (name.equals("Ride the Bus") && count > 0)
			Sidebar.addMult(this, Color.BLACK, count);
		else if (name.equals("Blackboard") && black)
			Sidebar.timesMult(this, Color.BLACK, 3);
		if (edition == 'P')
			Sidebar.timesMult(this, new Color(140, 0, 200), 1.5);
	}
	void performPlayed(Playing c) {
		if (c.enhancement == 'T')
			return;
		flag = true;
		current = c;
		if (name.equals("Lusty") && (c.suit.equals("Hearts") || c.enhancement == 'W'))
			Sidebar.addMult(this, Color.BLACK, 3);
		else if (name.equals("Greedy") && (c.suit.equals("Diamonds") || c.enhancement == 'W'))
			Sidebar.addMult(this, Color.BLACK, 3);
		else if (name.equals("Gluttonous") && (c.suit.equals("Clubs") || c.enhancement == 'W'))
			Sidebar.addMult(this, Color.BLACK, 3);
		else if (name.equals("Wrathful") && (c.suit.equals("Spades") || c.enhancement == 'W'))
			Sidebar.addMult(this, Color.BLACK, 3);
		else if (name.equals("8 Ball") && c.rank.equals("8") && Sidebar.cons.size() < Sidebar.cslots && 
				Math.random() < 0.25/Sidebar.threshold) {
			Sidebar.cons.add(Tarot.get());
			selected = true;
			border = Color.BLACK;
			Sidebar.wobble(c);;
			new Thread(() -> {
				while (wobble > -50) {
					wobble -= 1;
					try {Thread.sleep(1);} catch (InterruptedException e) {};
				}
				while (wobble < 35) {
					wobble += 1;
					try {Thread.sleep(1);} catch (InterruptedException e) {};
				}
				while (wobble > 0) {
					wobble -= 0.5;
					try {Thread.sleep(1);} catch (InterruptedException e) {};
				}
			}).start();
			try {Thread.sleep(250);} catch (InterruptedException e) {}
			selected = false;
			border = original;
			try {Thread.sleep(250);} catch (InterruptedException e) {}
		}
		else if (name.equals("Fibbonacci") && (c.rank.equals("2") || c.rank.equals("3") || c.rank.equals("5") || c.rank.equals("8")
				|| c.rank.equals("Ace")))
			Sidebar.addMult(this, Color.BLACK, 8);
		else if (name.equals("Scary Face") && (c.rank.equals("Jack") || c.rank.equals("Queen") || c.rank.equals("King") 
				|| contains("Pareidolia")))
			Sidebar.addChips(this, Color.BLACK, 30);
		else if (name.equals("Even Steven") && (c.rank.equals("2") || c.rank.equals("4") || c.rank.equals("6") || c.rank.equals("8")
				|| c.rank.equals("10")))
			Sidebar.addMult(this, Color.BLACK, 4);
		else if (name.equals("Odd Todd") && (c.rank.equals("3") || c.rank.equals("5") || c.rank.equals("7") || c.rank.equals("9")
				|| c.rank.equals("Ace")))
			Sidebar.addChips(this, Color.BLACK, 31);
		else if (name.equals("Scholar") && c.rank.equals("Ace")) {
			Sidebar.addChips(this, Color.BLACK, 20);
			Sidebar.addMult(this, Color.BLACK, 4);
		}
		else if (name.equals("Business") && Math.random() < 0.5 && (c.rank.equals("Jack") || c.rank.equals("Queen") || c.rank.equals("King") 
				|| contains("Pareidolia")))
			Sidebar.addMoney(this, Color.BLACK, 2);
		flag = false;
	}
	void performHand(Playing c) {
		flag = true;
		current = c;
		if (name.equals("Raised Fist") && Battle.hand.size() > 0) {
			ArrayList<Playing> temp = (ArrayList<Playing>) Battle.hand.clone();
			for (Playing t : temp)
				if (t.enhancement == 'T')
					return;
			Collections.sort(temp, (a, b) -> a.chips - b.chips);
			if (c.equals(temp.get(0))) {
				Sidebar.addMult(this, Color.BLACK, c.chips * 2);
				flag = true;
			}
		}
		flag = false;
	}
	void sold() {
		if (edition == 'N')
			Sidebar.jslots--;
		if (name.equals("Credit Card")) {
			Sidebar.money -= 20;
			new Thread(() -> {
				Sidebar.md.color = Sidebar.md.color.darker();
				try {Thread.sleep(250);} catch (InterruptedException e) {}
				Sidebar.md.color = Sidebar.md.color.brighter();
				try {Thread.sleep(250);} catch (InterruptedException e) {}
			}).start();
		}
	}
	void bought() {
		if (edition == 'N')
			Sidebar.jslots++;
		if (name.equals("Credit Card")) {
			new Thread(() -> {
				try {Thread.sleep(500);} catch (InterruptedException e) {}
				Sidebar.money += 20;
				Sidebar.md.color = Sidebar.md.color.darker();
				try {Thread.sleep(250);} catch (InterruptedException e) {}
				Sidebar.md.color = Sidebar.md.color.brighter();
				try {Thread.sleep(250);} catch (InterruptedException e) {}
			}).start();
		}
	}
	static void update() {
		int steels = 0;
		for (Playing c : Sidebar.deck) {
			if (c.enhancement == 'S')
				steels++;
		}
		for (Joker j : Joker.get("Steel"))
			j.count = steels;
	}
	static Joker find(String n) {
		for (Joker j : commons)
			if (j.name.equals(n))
				return j;
		for (Joker j : uncommons)
			if (j.name.equals(n))
				return j;
		for (Joker j : rares)
			if (j.name.equals(n))
				return j;
		for (Joker j : legendaries)
			if (j.name.equals(n))
				return j;
		return null;
	}
	static boolean contains(String n) {
		for (Joker j : Sidebar.jokers)
			if (j.name.equals(n))
				return true;
		return false;
	}
	static int num(String n) {
		int count = 0;
		for (Joker j : Sidebar.jokers)
			if (j.name.equals(n))
				count++;
		return count;
	}
	static ArrayList<Joker> get(String n) {
		ArrayList<Joker> js = new ArrayList<>();
		for (Joker j : Sidebar.jokers)
			if (j.name.equals(n))
				js.add(j);
		return js;
	}
}