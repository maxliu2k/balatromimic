package game;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import javax.imageio.*;
import java.util.*;

class Tarot extends Consumable{
	Image image;
	static final String[] tarots = {"Fool", "Magician", "High Priestess", "Empress", "Emperor", "Hierophant", "Lovers", "Chariot",
			"Strength", "Hermit", "Wheel of Fortune", "Justice", "Hanged Man", "Death", "Temperance", "Devil", "Tower", "Star", 
			"Moon", "Sun", "Judgement", "World"};
	static Image[] images;
	static void initialize() {
		images = new Image[22];
		try {
			BufferedImage temp = ImageIO.read(Tarot.class.getResourceAsStream("Important\\fool.png"));
			images[0] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Tarot.class.getResourceAsStream("Important\\magician.png"));
			images[1] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Tarot.class.getResourceAsStream("Important\\high-priestess.png"));
			images[2] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Tarot.class.getResourceAsStream("Important\\empress.png"));
			images[3] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Tarot.class.getResourceAsStream("Important\\emperor.png"));
			images[4] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Tarot.class.getResourceAsStream("Important\\hierophant.png"));
			images[5] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Tarot.class.getResourceAsStream("Important\\lovers.png"));
			images[6] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Tarot.class.getResourceAsStream("Important\\chariot.png"));
			images[7] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Tarot.class.getResourceAsStream("Important\\strength.png"));
			images[8] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Tarot.class.getResourceAsStream("Important\\hermit.png"));
			images[9] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Tarot.class.getResourceAsStream("Important\\wheel.png"));
			images[10] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Tarot.class.getResourceAsStream("Important\\justice.png"));
			images[11] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Tarot.class.getResourceAsStream("Important\\hanged-man.png"));
			images[12] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Tarot.class.getResourceAsStream("Important\\death.png"));
			images[13] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Tarot.class.getResourceAsStream("Important\\temperance.png"));
			images[14] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Tarot.class.getResourceAsStream("Important\\devil.png"));
			images[15] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Tarot.class.getResourceAsStream("Important\\tower.png"));
			images[16] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Tarot.class.getResourceAsStream("Important\\star.png"));
			images[17] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Tarot.class.getResourceAsStream("Important\\moon.png"));
			images[18] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Tarot.class.getResourceAsStream("Important\\sun.png"));
			images[19] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Tarot.class.getResourceAsStream("Important\\judgement.png"));
			images[20] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Tarot.class.getResourceAsStream("Important\\world.png"));
			images[21] = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
		} catch (Exception e) {}
	}
	Tarot(String n) {
		name = n;
		temp = new Color(217, 175, 76);
		for (int i = 0; i < tarots.length; i++) {
			if (tarots[i].equals(n)) {
				image = images[i];
				break;
			}
		}
		cost = 3;
	}
	static Tarot get() {
		return new Tarot(tarots[(int) (22*Math.random())]);
	}
	void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform old = g2d.getTransform();
		g2d.rotate((double) wobble/300, x + l/2.0, y + w/2.0);
		super.draw(g, override ? border : selected ? Color.BLACK : temp.darker(), temp);
		g2d.rotate((double) 0.4*wobble/300, x + l/2.0, y + w/2.0);
		Composite oldc = g2d.getComposite();
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) alpha/255);
	    g2d.setComposite(ac);
	    g2d.setColor(temp.darker());
		g2d.drawLine(x + 10, y+d(3) + 115, x + 90, y+d(3) + 115);
		g2d.drawLine(x + 10, y+d(3) + 25, x + 90, y+d(3) + 25);
		g2d.setFont(Panel.fonts[10]);
		g2d.setColor(Color.WHITE);
		if (name.equals("Fool")) {
			g2d.drawString("0", x + 48, y+d(3) + 20);
			g2d.drawString("Fool", x + 42, y+d(3) + 130);
		}
		else if (name.equals("Magician")) {
			g2d.drawString("I", x + 48, y+d(3) + 20);
			g2d.drawString("Magician", x + 30, y+d(3) + 130);
		}
		else if (name.equals("High Priestess")) {
			g2d.drawString("II", x + 46, y+d(3) + 20);
			g2d.drawString("High Priestess", x + 16, y+d(3) + 130);
		}
		else if (name.equals("Empress")) {
			g2d.drawString("III", x + 43, y+d(3) + 20);
			g2d.drawString("Empress", x + 30, y+d(3) + 130);
		}
		else if (name.equals("Emperor")) {
			g2d.drawString("IV", x + 46, y+d(3) + 20);
			g2d.drawString("Emperor", x + 30, y+d(3) + 130);
		}
		else if (name.equals("Hierophant")) {
			g2d.drawString("V", x + 48, y+d(3) + 20);
			g2d.drawString("Hierophant", x + 24, y+d(3) + 130);
		}
		else if (name.equals("Lovers")) {
			g2d.drawString("VI", x + 46, y+d(3) + 20);
			g2d.drawString("Lovers", x + 35, y+d(3) + 130);
		}
		else if (name.equals("Chariot")) {
			g2d.drawString("VII", x + 43, y+d(3) + 20);
			g2d.drawString("Chariot", x + 32, y+d(3) + 130);
		}
		else if (name.equals("Strength")) {
			g2d.drawString("VIII", x + 41, y+d(3) + 20);
			g2d.drawString("Strength", x + 29, y+d(3) + 130);
		}
		else if (name.equals("Hermit")) {
			g2d.drawString("IX", x + 46, y+d(3) + 20);
			g2d.drawString("Hermit", x + 34, y+d(3) + 130);
		}
		else if (name.equals("Wheel of Fortune")) {
			g2d.drawString("X", x + 48, y+d(3) + 20);
			g2d.drawString("Wheel", x + 37, y+d(3) + 130);
		}
		else if (name.equals("Justice")) {
			g2d.drawString("XI", x + 45, y+d(3) + 20);
			g2d.drawString("Justice", x + 31, y+d(3) + 130);
		}
		else if (name.equals("Hanged Man")) {
			g2d.drawString("XII", x + 42, y+d(3) + 20);
			g2d.drawString("Hanged Man", x + 22, y+d(3) + 130);
		}
		else if (name.equals("Death")) {
			g2d.drawString("XIII", x + 40, y+d(3) + 20);
			g2d.drawString("Death", x + 37, y+d(3) + 130);
		}
		else if (name.equals("Temperance")) {
			g2d.drawString("XIV", x + 42, y+d(3) + 20);
			g2d.drawString("Temperance", x + 20, y+d(3) + 130);
		}
		else if (name.equals("Devil")) {
			g2d.drawString("XV", x + 44, y+d(3) + 20);
			g2d.drawString("Devil", x + 39, y+d(3) + 130);
		}
		else if (name.equals("Tower")) {
			g2d.drawString("XVI", x + 42, y+d(3) + 20);
			g2d.drawString("Tower", x + 36, y+d(3) + 130);
		}
		else if (name.equals("Star")) {
			g2d.drawString("XVII", x + 40, y+d(3) + 20);
			g2d.drawString("Star", x + 40, y+d(3) + 130);
		}
		else if (name.equals("Moon")) {
			g2d.drawString("XVIII", x + 37, y+d(3) + 20);
			g2d.drawString("Moon", x + 38, y+d(3) + 130);
		}
		else if (name.equals("Sun")) {
			g2d.drawString("XIX", x + 42, y+d(3) + 20);
			g2d.drawString("Sun", x + 42, y+d(3) + 130);
		}
		else if (name.equals("Judgement")) {
			g2d.drawString("XX", x + 44, y+d(3) + 20);
			g2d.drawString("Judgement", x + 24, y+d(3) + 130);
		}
		else if (name.equals("World")) {
			g2d.drawString("XXI", x + 42, y+d(3) + 20);
			g2d.drawString("World", x + 37, y+d(3) + 130);
		}
		g2d.drawImage(image, x + 15, y + 35+d(3), null);
		g2d.setComposite(oldc);
		g2d.setTransform(old);
	}
	boolean useable() {
		if (name.equals("Fool"))
			return Sidebar.last != null && !Sidebar.last.name.equals("Fool");
		else if (name.equals("Magician") || name.equals("Empress") || name.equals("Hierophant") || name.equals("Strength")
				|| name.equals("Hanged Man"))
			return Battle.selected > 0 && Battle.selected <= 2;
		else if (name.equals("High Priestess") || name.equals("Emperor"))
			return Sidebar.cons.contains(this) || Sidebar.cons.size() < Sidebar.cslots;
		else if (name.equals("Hermit") || name.equals("Temperance"))
			return true;
		else if (name.equals("Wheel of Fortune"))
			return Battle.selected == 0 && Sidebar.editionable();
		else if (name.equals("Lovers") || name.equals("Chariot") || name.equals("Justice") || name.equals("Devil") 
				|| name.equals("Tower"))
			return Battle.selected == 1;
		else if (name.equals("Death"))
			return Battle.selected == 2;
		else if (name.equals("Star") || name.equals("Moon") || name.equals("Sun") || name.equals("World"))
			return Battle.selected > 0 && Battle.selected <= 3;
		else if (name.equals("Judgement"))
			return Sidebar.jokers.size() < Sidebar.jslots;
		return false;
	}
	void use() {
		HashSet<Playing> selset = new HashSet<>();
		for (Playing c : Battle.hand)
			if (c.selected)
				selset.add(c);
		deselect();
		try {Thread.sleep(1000);} catch (InterruptedException e) {}
		if (name.equals("Fool")) {
			selected = true;
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
			Sidebar.cons.add(Sidebar.last);
			try {Thread.sleep(250);} catch (InterruptedException e) {}
			selected = false;
			try {Thread.sleep(250);} catch (InterruptedException e) {}
		}
		else if (name.equals("Magician")) {
			for (Playing c : selset) {
				selected = true;
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
				c.enhancement = 'L';
				try {Thread.sleep(250);} catch (InterruptedException e) {}
				selected = false;
				try {Thread.sleep(250);} catch (InterruptedException e) {}
			}
		}
		else if (name.equals("High Priestess")) {
			for (int i = 0; i < 2; i++) {
				if (Sidebar.cslots > Sidebar.cons.size()) {
					selected = true;
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
					Sidebar.cons.add(Planet.get());
					try {Thread.sleep(250);} catch (InterruptedException e) {}
					selected = false;
					try {Thread.sleep(250);} catch (InterruptedException e) {}
				}
			}
		}
		else if (name.equals("Empress")) {
			for (Playing c : selset) {
				selected = true;
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
				c.enhancement = 'M';
				try {Thread.sleep(250);} catch (InterruptedException e) {}
				selected = false;
				try {Thread.sleep(250);} catch (InterruptedException e) {}
			}
		}
		else if (name.equals("Emperor")) {
			for (int i = 0; i < 2; i++) {
				if (Sidebar.cslots > Sidebar.cons.size()) {
					selected = true;
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
					Sidebar.cons.add(Tarot.get());
					try {Thread.sleep(250);} catch (InterruptedException e) {}
					selected = false;
					try {Thread.sleep(250);} catch (InterruptedException e) {}
				}
			}
		}
		else if (name.equals("Hierophant")) {
			for (Playing c : selset) {
				selected = true;
				Sidebar.wobble(this);
				c.enhancement = 'B';
				try {Thread.sleep(250);} catch (InterruptedException e) {}
				selected = false;
				try {Thread.sleep(250);} catch (InterruptedException e) {}	
			}
		}
		else if (name.equals("Lovers")) {
			for (Playing c : selset) {
				selected = true;
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
				c.enhancement = 'W';
				try {Thread.sleep(250);} catch (InterruptedException e) {}
				selected = false;
				try {Thread.sleep(250);} catch (InterruptedException e) {}	
			}			 
		}
		else if (name.equals("Chariot")) {
			for (Playing c : selset) {
				selected = true;
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
				c.enhancement = 'S';
				try {Thread.sleep(250);} catch (InterruptedException e) {}
				selected = false;
				try {Thread.sleep(250);} catch (InterruptedException e) {}	
			}
		}
		else if (name.equals("Strength")) {
			for (Playing c : selset) {
				selected = true;
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
				int index = 0;
				for (int i = 0; i < Playing.ranks.length; i++)
					if (Playing.ranks[i].equals(c.rank))
						index = i;
				c.rank = Playing.ranks[index == Playing.ranks.length - 1 ? 0 : index + 1];
				if (c.rank.equals("Ace")) {
					c.abbrev = "A";
					c.rNum = 14;
					c.chips = 11;
				}
				else if (c.rank.equals("King")) {
					c.abbrev = "K";
					c.rNum = 13;
					c.chips = 10;
				}
				else if (c.rank.equals("Queen")) {
					c.abbrev = "Q";
					c.rNum = 12;
					c.chips = 10;
				}
				else if (c.rank.equals("Jack")) {
					c.abbrev = "J";
					c.rNum = 11;
					c.chips = 10;
				}
				else {
					c.abbrev = c.rank;
					c.rNum = Integer.parseInt(c.rank);
					c.chips = Integer.parseInt(c.rank);
				}
				try {Thread.sleep(250);} catch (InterruptedException e) {}
				selected = false;
				try {Thread.sleep(250);} catch (InterruptedException e) {}
			}
		}
		else if (name.equals("Hermit"))
			Sidebar.addMoney(this, Color.BLACK, Math.min(Math.max(0, Sidebar.money), 20));
		else if (name.equals("Wheel of Fortune")) {
			override = true;
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
			if (Math.random() < 0.25/Sidebar.threshold) {
				border = Color.GREEN;
				Joker j = Sidebar.jokers.get((int) (Sidebar.jokers.size()*Math.random()));
				while (j.edition != ' ')
					j = Sidebar.jokers.get((int) (Sidebar.jokers.size()*Math.random()));
				int random = (int) (20*Math.random());
				if (random < 3)
					j.edition = 'P';
				else if (random < 10)
					j.edition = 'H';
				else
					j.edition = 'F';
			}
			else
				border = Color.RED;
			try {Thread.sleep(250);} catch (InterruptedException e) {}
			override = false;
			try {Thread.sleep(250);} catch (InterruptedException e) {}
		}
		else if (name.equals("Justice")) {
			for (Playing c : selset) {
				selected = true;
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
				c.enhancement = 'A';
				try {Thread.sleep(250);} catch (InterruptedException e) {}
				selected = false;
				try {Thread.sleep(250);} catch (InterruptedException e) {}
			}
		}
		else if (name.equals("Hanged Man")) {
			for (Playing c : selset) {
				selected = true;
				new Thread(() -> {
					while (wobble > -50) {
						wobble -= 1;
						try {Thread.sleep(1);} catch (InterruptedException e) {};
					}
					while (wobble < 30) {       // change here
						wobble += 1;
						try {Thread.sleep(1);} catch (InterruptedException e) {};
					}
					while (wobble > 0) {        // and here
						wobble -= 1;
						try {Thread.sleep(1);} catch (InterruptedException e) {};
					}
				}).start();
				new Thread(() -> {
					c.override = true;
					c.temp = Color.WHITE;
					c.breaking = true;
					for (int j = 255; j > 1; j-=2) {
						c.alpha = j;
						try {Thread.sleep(3);} catch (InterruptedException e) {}
					}
					Battle.hand.remove(c);
					Sidebar.deck.remove(c);
				}).start();
				try {Thread.sleep(250);} catch (InterruptedException e) {}
				selected = false;
				try {Thread.sleep(250);} catch (InterruptedException e) {}
			}			 
		}
		else if (name.equals("Death")) {
			selected = true;
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
			ArrayList<Playing> deaths = new ArrayList<>(selset);
			Playing first = Battle.hand.get(Math.min(Battle.hand.indexOf(deaths.get(0)), Battle.hand.indexOf(deaths.get(1))));
			Playing second = Battle.hand.get(Math.max(Battle.hand.indexOf(deaths.get(0)), Battle.hand.indexOf(deaths.get(1))));
			first.abbrev = second.abbrev;
			first.border = second.border;
			first.chips = second.chips;
			first.cost = second.cost;
			first.edition = second.edition;
			first.enhancement = second.enhancement;
			first.original = second.original;
			first.rank = second.rank;
			first.rNum = second.rNum;
			first.seal = second.seal;
			first.suit = second.suit;
			try {Thread.sleep(250);} catch (InterruptedException e) {}
			selected = false;
			try {Thread.sleep(250);} catch (InterruptedException e) {}
		}
		else if (name.equals("Temperance")) {
			int count = 0;
			for (Joker j : Sidebar.jokers)
				count += j.sell;
			Sidebar.addMoney(this, Color.BLACK, Math.min(count, 50));
		}
		else if (name.equals("Devil")) {
			for (Playing c : selset) {
				selected = true;
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
				c.enhancement = 'G';
				try {Thread.sleep(250);} catch (InterruptedException e) {}
				selected = false;
				try {Thread.sleep(250);} catch (InterruptedException e) {}
			}			 
		}
		else if (name.equals("Tower")) {
			for (Playing c : selset) {
				selected = true;
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
				c.enhancement = 'T';
				try {Thread.sleep(250);} catch (InterruptedException e) {}
				selected = false;
				try {Thread.sleep(250);} catch (InterruptedException e) {}
			}			 
		}
		else if (name.equals("Star")) {
			for (Playing c : selset) {
				selected = true;
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
				c.suit = "Diamonds";
				try {Thread.sleep(250);} catch (InterruptedException e) {}
				selected = false;
				try {Thread.sleep(250);} catch (InterruptedException e) {}	
			}
		}
		else if (name.equals("Moon")) {
			for (Playing c : selset) {
				selected = true;
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
				c.suit = "Clubs";
				try {Thread.sleep(250);} catch (InterruptedException e) {}
				selected = false;
				try {Thread.sleep(250);} catch (InterruptedException e) {}	
			}			 
		}
		else if (name.equals("Sun")) {
			for (Playing c : selset) {
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
				c.suit = "Hearts";
				try {Thread.sleep(250);} catch (InterruptedException e) {}
				selected = false;
				try {Thread.sleep(250);} catch (InterruptedException e) {}	
			}		 
		}
		else if (name.equals("Judgement")) {
			selected = true;
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
			Joker j = Joker.get();
			Sidebar.jokers.add(j);
			j.bought();
			try {Thread.sleep(250);} catch (InterruptedException e) {}
			selected = false;
			try {Thread.sleep(250);} catch (InterruptedException e) {}	
		}
		else if (name.equals("World")) {
			for (Playing c : selset) {
				selected = true;
				new Thread(() -> {
					while (wobble > -50) {
						wobble -= 1;
						try {Thread.sleep(1);} catch (InterruptedException e) {};
					}
					while (wobble < 30) {       // change here
						wobble += 1;
						try {Thread.sleep(1);} catch (InterruptedException e) {};
					}
					while (wobble > 0) {        // and here
						wobble -= 1;
						try {Thread.sleep(1);} catch (InterruptedException e) {};
					}
				}).start();
				c.suit = "Spades";
				try {Thread.sleep(250);} catch (InterruptedException e) {}
				selected = false;
				try {Thread.sleep(250);} catch (InterruptedException e) {}	
			}			 
		}
		temp = new Color(217, 175, 76);
		Joker.update();
		try {Thread.sleep(500);} catch (InterruptedException e) {}
	}
	static void deselect() {
		for (Playing c : Battle.hand) {
			if (c.selected) {
				c.act();
				c.override = false;
			}
		}
		Battle.selected = 0;
		Sidebar.mc.text = "";
		Sidebar.cc.text = "";
		Sidebar.pd.text = "";
	}
}
