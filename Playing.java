import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import javax.imageio.*;

class Playing extends Editionable {
	String rank, suit;
	String abbrev;
	char enhancement, seal;
	int rNum, oNum;
	static Image spade, heart, diamond, club, king, queen, jack;
	static final String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};
	static final String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
	static void initialize() {
		//load suit images
		try {
			BufferedImage temp = ImageIO.read(Playing.class.getResourceAsStream("Important/heart.png"));
			heart = temp.getScaledInstance(66, 62, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Playing.class.getResourceAsStream("Important/club.png"));
			club = temp.getScaledInstance(66, 68, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Playing.class.getResourceAsStream("Important/diamond.png"));
			diamond = temp.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Playing.class.getResourceAsStream("Important/spade.png"));
			spade = temp.getScaledInstance(66, 76, Image.SCALE_SMOOTH);
			temp = ImageIO.read(Playing.class.getResourceAsStream("Important/king.png"));
			king = temp.getScaledInstance(35, 35, Image.SCALE_DEFAULT);
			temp = ImageIO.read(Playing.class.getResourceAsStream("Important/queen.png"));
			queen = temp.getScaledInstance(35, 35, Image.SCALE_DEFAULT);
			temp = ImageIO.read(Playing.class.getResourceAsStream("Important/jack.png"));
			jack = temp.getScaledInstance(35, 35, Image.SCALE_DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	Playing () {
		super();
		rank = ranks[(int) (ranks.length*Math.random())];
		suit = suits[(int) (4*Math.random())];
		border = Color.LIGHT_GRAY;
		original = Color.LIGHT_GRAY;
		edition = ' ';
		cost = 1;
	}
	Playing(String r, String s) {
		super();
		rank = r;
		//initialize card values
		if (rank.equals("Ace")) {
			abbrev = "A";
			rNum = 14;
			chips = 11;
		}
		else if (rank.equals("King")) {
			abbrev = "K";
			rNum = 13;
			chips = 10;
		}
		else if (rank.equals("Queen")) {
			abbrev = "Q";
			rNum = 12;
			chips = 10;
		}
		else if (rank.equals("Jack")) {
			abbrev = "J";
			rNum = 11;
			chips = 10;
		}
		else {
			abbrev = rank;
			rNum = Integer.parseInt(rank);
			chips = Integer.parseInt(rank);
		}
		suit = s;
		border = Color.LIGHT_GRAY;
		original = Color.LIGHT_GRAY;
		edition = ' ';
		cost = 1;
		oNum = rNum;
		//seal = 'P';
	}
	Playing(String r, String s, String add) {
		this(r, s);
		if (add.charAt(0) == '1') {
			int whichEnhance = (int) (20*Math.random());
			if (whichEnhance == 0)
				enhancement = 'B';
			else if (whichEnhance == 1)
				enhancement = 'M';
			else if (whichEnhance == 2)
				enhancement = 'W';
			else if (whichEnhance == 3)
				enhancement = 'A';
			else if (whichEnhance == 4)
				enhancement = 'S';
			else if (whichEnhance == 5)
				enhancement = 'G';
			else if (whichEnhance == 6)
				enhancement = 'T';
			else if (whichEnhance == 7)
				enhancement = 'L';
			else
				enhancement = ' ';
		}
		if (add.charAt(1) == '1') {
			int rollChance = (int) (20*Math.random());
			if (rollChance == 0)
				seal = 'R';
			else if (rollChance == 1)
				seal = 'G';
			else if (rollChance == 2)
				seal = 'P';
			else if (rollChance == 3)
				seal = 'B';
		}
		if (add.charAt(2) == '1') {
			int rollChance = (int) (1000*Math.random());
			if (rollChance < 12) {
				edition = 'P';
				cost += 5;
			}
			else if (rollChance < 40) {
				edition = 'H';
				cost += 3;
			}
			else if (rollChance < 80) {
				edition = 'F';
				cost += 2;
			}
		}
		else
			edition = ' ';
	}	
	void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform old = g2d.getTransform();
		g2d.rotate((double) 1.5*wobble/300, x + l/2.0, y + w/2.0);
		double scale = wobbleScale();
		double cx = x + l/2.0;
		double cy = y + w/2.0;
		g2d.translate(cx, cy);
		g2d.scale(scale, scale);
		g2d.translate(-cx, -cy);
		//draws basic card
		if (breaking)
			draw(g, 3, new Color(border.getRed(), border.getGreen(), border.getBlue(), alpha), 
					new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), alpha));
		else {
			if (enhancement != 'A' && enhancement != 'L' && enhancement != 'S' && enhancement != 'G' && enhancement != 'T') {
				original = edition == ' ' ? Color.LIGHT_GRAY : getEdition();
				if (!override && !selected)
					border = original;
				draw(g, 3, border, Color.WHITE);
			}
			//enhanced cards with overrided colors
			else if (enhancement == 'A') {
				if (!breaking) {
					int d = d(3);
					original = edition == ' ' ? Color.LIGHT_GRAY : getEdition();
					if (!override && !selected)
						border = original;
					g2d.setColor(border);
					g2d.fillRect(x + 5, y+d, l - 10, 5);
					g2d.fillRect(x + 5, y+d + 5, 5, 5);
					g2d.fillRect(x, y+d + 5, 5, w - 10);
					g2d.fillRect(x + 5, y+d + w - 10, 5, 5);
					g2d.fillRect(x + 5, y+d + w - 5, l - 10, 5);
					g2d.fillRect(x + 100 - 10, y+d + 5, 5, 5);
					g2d.fillRect(x + 100 - 5, y+d + 5, 5, w - 10);
					g2d.fillRect(x + 100 - 10, y+d + w - 10, 5, 5);
					g2d.setColor(new Color(200, 200, 200, 100 + d(25)));
					g2d.fillRect(x + 5, y+d, l - 10, 5);
					g2d.fillRect(x, y+d + 5, 5, w - 10);
					g2d.fillRect(x + 5, y+d + w - 5, l - 10, 5);
					g2d.fillRect(x + 100 - 5, y+d + 5, 5, w - 10);
					g2d.fillRect(x + 5, y+d + 5, l - 10, w - 10);
					if (selected || override) {
						g2d.setColor(override ? border : Color.BLACK);
						g2d.fillRect(x + 5, y+d, l - 10, 5);
						g2d.fillRect(x + 5, y+d + 5, 5, 5);
						g2d.fillRect(x, y+d + 5, 5, w - 10);
						g2d.fillRect(x + 5, y+d + w - 10, 5, 5);
						g2d.fillRect(x + 5, y+d + w - 5, l - 10, 5);
						g2d.fillRect(x + l - 10, y+d + 5, 5, 5);
						g2d.fillRect(x + l - 5, y+d + 5, 5, w - 10);
						g2d.fillRect(x + l - 10, y+d + w - 10, 5, 5);
					}
				}
			}
			else if (enhancement == 'G') {
				original = edition == ' ' ? new Color(227 + d(25), 161 + d(25), 28 + d(25)) : getEdition();
				if (!override && !selected)
					border = original;
				draw(g, 3, border, new Color(240 + d(15), 201 + d(25), 88 + d(25)));
			}
			else if (enhancement == 'S') {
				original = edition == ' ' ? new Color(120 + d(20), 120 + d(20), 120 + d(20)) : getEdition();
				if (!override && !selected)
					border = original;
				draw(g, 3, border, new Color(180 + d(20), 180 + d(20), 180 + d(20)));
			}
			else if (enhancement == 'T') {
				original = edition == ' ' ? Color.DARK_GRAY : getEdition();
				if (!override && !selected)
					border = original;
				draw(g, 3, border, Color.GRAY);
			}
			else if (enhancement == 'L') {
				original = new Color(255, 226, 143).darker();
				if (!override && !selected)
					border = edition == ' ' ? original : getEdition();
				draw(g, 3, border, original.brighter());
			}
		}
		g2d.rotate((double) 0.33*wobble/300, x + l/2.0, y + w/2.0);
		Composite oldc = g2d.getComposite();
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) alpha/255);
	    g2d.setComposite(ac);
		Color color = null;
		if (suit.equals("Spades"))
			color = new Color(70, 80, 80);
		else if (suit.equals("Hearts"))
			color = new Color(232, 37, 23);
		else if (suit.equals("Diamonds"))
			color = new Color(232, 162, 23);
		else if (suit.equals("Clubs"))
			color = new Color(12, 134, 204);
		g2d.setColor(color);
		g2d.setFont(Panel.fonts[25]);
		int d = d(3);
		//draw suit and wild symbol
		if (enhancement != 'T') {
			g2d.drawString(abbrev, x + 10, y+d(3) + 35);
			if (rank.equals("King") || rank.equals("Queen") || rank.equals("Jack")) {
				g2d.drawLine(x + 20, y+d(3) + 115, x + 80, y+d(3) + 115);
				g2d.drawLine(x + 20, y+d(3) + 120, x + 80, y+d(3) + 120);
				g2d.drawLine(x + 20, y+d(3) + 45, x + 20, y+d(3) + 120);
				g2d.drawLine(x + 80, y+d(3) + 45, x + 80, y+d(3) + 120);
				g2d.drawLine(x + 25, y+d(3) + 45, x + 25, y+d(3) + 120);
				g2d.drawLine(x + 75, y+d(3) + 45, x + 75, y+d(3) + 120);
				g2d.drawLine(x + 20, y+d(3) + 45, x + 80, y+d(3) + 45);
				g2d.drawLine(x + 20, y+d(3) + 50, x + 80, y+d(3) + 50);
			}
			if (enhancement != 'W') {
				Image drawn = null;
				if (rank.equals("Ace")) {
					if (suit.equals("Spades"))
						drawn = spade;
					else if (suit.equals("Diamonds"))
						drawn = diamond;
					else if (suit.equals("Hearts"))
						drawn = heart;
					else
						drawn = club;
					g2d.drawImage(drawn, suit.equals("Spades") ? x + 18 : suit.equals("Diamonds") 
							? x + 16 : x + 17, suit.equals("Hearts") ? y + 47 + d : y + 40 + d, null);
				}
				else {
					if (rank.equals("King") || rank.equals("Queen") || rank.equals("Jack"))
						d += 20;
					if (suit.equals("Spades"))
						drawn = spade.getScaledInstance(33, 38, Image.SCALE_DEFAULT);
					else if (suit.equals("Diamonds"))
						drawn = diamond.getScaledInstance(35, 35, Image.SCALE_DEFAULT);
					else if (suit.equals("Hearts"))
						drawn = heart.getScaledInstance(33, 31, Image.SCALE_DEFAULT);
					else
						drawn = club.getScaledInstance(33, 34, Image.SCALE_DEFAULT);
					g2d.drawImage(drawn, suit.equals("Spades") ? x + 34 : suit.equals("Diamonds") 
							? x + 33 : x + 34, suit.equals("Hearts") ? y + 61 + d : y + 57 + d, null);
					if (rank.equals("King") || rank.equals("Queen") || rank.equals("Jack"))
						d -= 20;
				}
			}
			else {
				if (rank.equals("Ace")) {
					g2d.setColor(new Color(40 + d(40), 40 + d(40), 40 + d(40)));
					g2d.fillOval(x + 20, y + 45 + d, 30, 30);
					g2d.setColor(new Color(112 + d(112), 18 + d(18), 11 + d(11)));
					g2d.fillOval(x + 50, y + 45 + d, 30, 30);
					g2d.setColor(new Color(112 + d(112), 81 + d(81), 11 + d(11)));
					g2d.fillOval(x + 20, y + 75 + d, 30, 30);
					g2d.setColor(new Color(6 + d(6), 67 + d(67), 102 + d(102)));
					g2d.fillOval(x + 50, y + 75 + d, 30, 30);
					g2d.setColor(new Color(255, 255, 255));
					g2d.fillOval(x + 35, y + 60 + d, 30, 30);
				}
				else {
					if (rank.equals("King") || rank.equals("Queen") || rank.equals("Jack"))
						d += 20;
					g2d.setColor(new Color(40 + d(40), 40 + d(40), 40 + d(40)));
					g2d.fillOval(x + 35, y + 60 + d, 15, 15);
					g2d.setColor(new Color(112 + d(112), 18 + d(18), 11 + d(11)));
					g2d.fillOval(x + 50, y + 60 + d, 15, 15);
					g2d.setColor(new Color(112 + d(112), 81 + d(81), 11 + d(11)));
					g2d.fillOval(x + 35, y + 75 + d, 15, 15);
					g2d.setColor(new Color(6 + d(6), 67 + d(67), 102 + d(102)));
					g2d.fillOval(x + 50, y + 75 + d, 15, 15);
					g2d.setColor(new Color(255, 255, 255));
					g2d.fillOval(x + 42, y + 67 + d, 16, 16);
					if (rank.equals("King") || rank.equals("Queen") || rank.equals("Jack"))
						d -= 20;
				}
			}
			if (rank.equals("King"))
				g2d.drawImage(king, x + 33, y + 48 + d, null);
			else if (rank.equals("Queen"))
				g2d.drawImage(queen, x + 33, y + 48 + d, null);
			else if (rank.equals("Jack"))
				g2d.drawImage(jack, x + 33, y + 48 + d, null);
		}
		//draw stone texture
		else {
			g2d.setFont(Panel.fonts[20]);
			g2d.setColor(Color.DARK_GRAY);
			g2d.drawString("-", x + 10, y+d(3) + 32);
			g2d.drawString("-", x + 79, y+d(3) + 32);
			g2d.drawString("-", x + 10, y+d(3) + 128);
			g2d.drawString("-", x + 79, y+d(3) + 128);
			g2d.setFont(Panel.fonts[40]);
			g2d.drawString("+", x + 39, y + 88 + d);
		}
		g2d.setComposite(oldc);
		//draw bonus and mult symbols
		if(enhancement == 'B') {
			g2d.setFont(Panel.fonts[25]);
			g2d.setColor(new Color(12, 134, 204));
			g2d.drawString("+", x + 75, y+d(3) + 34);
		}
		else if(enhancement == 'M') {
			g2d.setFont(Panel.fonts[22]);
			g2d.setColor(Color.RED);
			g2d.drawString("X", x + 75, y+d(3) + 33);
		}
		if (seal != 0) {
			Color sealColor = Color.WHITE;
			if (seal == 'R')
				sealColor = Color.RED;
			else if (seal == 'G')
				sealColor = new Color(242+d(13), 190+d(25), 73+d(20));
			else if (seal == 'P')
				sealColor = new Color(143, 67, 230);
			else if (seal == 'B')
				sealColor = new Color(36, 159, 212);
			g2d.setColor(sealColor.darker());
			g2d.fillOval(x + 25, y + 55+d, 35, 35);
			g2d.setColor(sealColor);
			g2d.fillOval(x + 28, y + 58+d, 29, 29);
		}
		g2d.setTransform(old);
	}
}


