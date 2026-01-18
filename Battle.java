import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;

class Battle extends Screen {
	static Button play;
	static Button discard;
	static Button sSuit;
	static Button sRank;
	static Button sLeft;
	static Button sRight;
	static LinkedList<Playing> shuffled;
	static ArrayList<Playing> hand;
	static int selected;
	static Button[] hex;
	static boolean playing;
	static ArrayList<Playing> played;
	static int initSize;
	static int score;
	static boolean lose;
	void initialize() {
		//create buttons
		play = new Button(350, 560, 200, 105, "play hand", "Play Hand", Color.DARK_GRAY);
		play.setFormat(30, 50, 30, 0);
		discard = new Button(900, 560, 200, 105, "discard", "Discard", Color.DARK_GRAY);
		discard.setFormat(45, 50, 30, 0);
		sSuit = new Button(570, 560, 150, 50, "sSuit", "Sort by Suit", new Color(200, 150, 0));
		sSuit.setFormat(12, 30, 20, 0);
		sRank = new Button(730, 560, 150, 50, "sRank", "Sort by Rank", new Color(200, 150, 0));
		sRank.setFormat(8, 30, 20, 0);
		sLeft = new Button(570, 615, 150, 50, "sLeft", "Move Left", Color.DARK_GRAY);
		sLeft.setFormat(21, 30, 20, 0);
		sRight = new Button(730, 615, 150, 50, "sRight", "Move Right", Color.DARK_GRAY);
		sRight.setFormat(19, 30, 20, 0);
		Panel.side = new Sidebar();
		shuffled = new LinkedList<>();
		hand = new ArrayList<>();
		selected = 0;
		ArrayList<Playing> temp = (ArrayList<Playing>) Sidebar.deck.clone();
		Collections.shuffle(temp);
		for (Playing c : temp)
			shuffled.offer(c);
		new Thread(() -> {
			playing = true;
			try {Thread.sleep(500);} catch (InterruptedException e) {}
			createHand(0);
			playing = false;
			sSuit.color = new Color(200, 150, 0);
			sRank.color = new Color(200, 150, 0);
		}).start();
		hex = new Button[6];
		hex[0] = play;
		hex[1] = discard;
		hex[2] = sSuit;
		hex[3] = sRank;
		hex[4] = sRight;
		hex[5] = sLeft;
		playing = false;
		played = new ArrayList<>();
		score = 0;
		lose = false;
	}
	void check(int x, int y) {
		//prevent buggy interactions
		if (playing || lose)
			return;
		Panel.side.check(x, y);
		if (Sidebar.id)
			return;
		//checks each card to see if it was selected
		for (Playing c : hand) {
			if (c.field(x, y)) {
				c.act();
				if (!c.selected)
					selected--;
				else
					selected++;
				//cap selection at 5 cards max
				if (selected > 5) {
					c.act();
					selected--;
				}
				HashSet<Playing> poker = new HashSet<>();
				for (Playing card : hand)
					if (card.selected)
						poker.add(card);
				if (selected > 0) {
					Sidebar.pd.text = Sidebar.getPoker(poker) + " lv. " + Sidebar.levels[Sidebar.getIndex(Sidebar.getPoker(poker))];
					Sidebar.cc.text = Integer.toString(Sidebar.getStats(Sidebar.getPoker(poker))[0]);
					Sidebar.mc.text = Integer.toString(Sidebar.getStats(Sidebar.getPoker(poker))[1]);
				}
				else {
					Sidebar.pd.text = ""; 
					Sidebar.cc.text = "";
					Sidebar.mc.text = "";
				}
			}
		}
		//check every button
		for (Button b : hex)
			if (b.field(x, y))
				b.act();
		//reset button colors
		if (selected == 0) {
			play.color = Color.DARK_GRAY;
			discard.color = Color.DARK_GRAY;
		}
		else {
			play.color = new Color(70, 150, 255);
			discard.color = new Color(255, 0, 0);
		}
		if (Sidebar.hands == 0)
			play.color = Color.DARK_GRAY;
		if (Sidebar.discs == 0)
			discard.color = Color.DARK_GRAY;
		if (selected == 1) {
			sLeft.color = new Color(200, 150, 0);
			sRight.color = new Color(200, 150, 0);
		}
		else {
			sLeft.color = Color.DARK_GRAY;
			sRight.color = Color.DARK_GRAY;
		}
		if (playing)
			Sidebar.info.color = Color.DARK_GRAY;
		else
			Sidebar.info.color = new Color(200, 150, 0);
	}
	void draw(Graphics g) {
		if (Sidebar.ante == 8 && Sidebar.blind.equals("Boss"))
			g.drawImage(Menu.back, 0, 0, null);
		else if (Sidebar.blind.equals("Boss")) {
			g.setColor(new Color(156, 23, 23));
			g.fillRect(0, 0, 1280, 720);
		}
		else {
			g.setColor(new Color(21, 138, 85));
			g.fillRect(0, 0, 1280, 720);
		}
		drawBack(g);
		//draws each card in hand
		for (int i = 0; i < hand.size(); i++) {
			//int distance = hand.size() - 1 == 0 ? 0 : 780/(hand.size() - 1);
			int distance = (int) Math.round(784.0 / (Math.max(Sidebar.handSize, Battle.hand.size()) - 1));
			hand.get(i).x = 352 + distance*i;
			hand.get(i).y = 395;
			hand.get(i).draw(g);
		}
		//draws each card played
		for (int i = 0; i < played.size(); i++) {
			int distance = 150;
			played.get(i).x = 450 + distance*i + (5 - initSize)*75;
			played.get(i).y = 220;
			played.get(i).draw(g);
		}
		if (playing) {
			play.color = Color.DARK_GRAY;
			discard.color = Color.DARK_GRAY;
			sLeft.color = Color.DARK_GRAY;
			sRight.color = Color.DARK_GRAY;
			sRank.color = Color.DARK_GRAY;
			sSuit.color = Color.DARK_GRAY;
		}
		for (Button b : hex)
			b.draw(g);
		//draws deck size and how many cards left
		g.setFont(Panel.fonts[25]);
		g.setColor(Color.WHITE);
		g.drawString(hand.size() + "/" + Sidebar.handSize, 1120, 585);
		g.drawString(shuffled.size() + "/" + Sidebar.deck.size(), 1170, 660);
		Panel.side.draw(g);
		if (lose) {
			g.setColor(Color.RED);
			g.setFont(Panel.fonts[150]);
			g.drawString("GAME OVER", 400, 350);
			new Thread(() -> {
				try {Thread.sleep(5000);} catch (InterruptedException e) {}
				//Panel.timer.stop();
				Balatro.j.setVisible(false);
			}).start();
		}
		if (alpha > 0) {
			if (Panel.s == 1)
				g.setColor(new Color(70, 150, 255, alpha));
			else
				g.setColor(new Color(80, 80, 80, alpha));
			g.fillRect(0, 0, 1280, 720);
			alpha -= 10;
			if (alpha < 0) 
				alpha = 0;
		}
	}
	static void createHand(int n) {
		Sidebar.pd.text = "";
		Sidebar.cc.text = "";
		Sidebar.mc.text = "";
		//delayed shuffling cards to hand
		new Thread(() -> {
		    for (int i = n; i < Sidebar.handSize; i++) {
				if (!shuffled.isEmpty()) {
					try {Thread.sleep(80);} catch (InterruptedException e) {}
					Playing c = shuffled.poll();
					if (c.enhancement == 'T')
						c.rNum = -2;
					else
						c.rNum = c.oNum;
					hand.add(c);
					if (!Sidebar.sort)
						Collections.sort(Battle.hand, (b, a) -> a.rNum == b.rNum ? a.suit.compareTo(b.suit) : a.rNum - b.rNum);
					else
						Collections.sort(Battle.hand, (b, a) -> a.suit.equals(b.suit) ? a.rNum - b.rNum : a.suit.compareTo(b.suit));
				}
		    }
		}).start();
	}
	static void checkLoss() {
		//checks if the game is lost if on last hand
		if (score < Sidebar.objective && Sidebar.hands == 0)
			lose = true;
	}
	static void drawBack(Graphics g) {
		/*
		for (Circle c : Sidebar.backs) {
			g.setColor(new Color(0, 0, 0, c.alpha));
			g.fillOval(c.x, c.y, c.radius, c.radius);
			if (c.radius < 600) {
				c.x -= 1;
				c.y -= 1;
				c.radius += 2;
				if (c.radius > 200)
					c.alpha = (600 - c.radius)/20;
			}
		}
		for (int i = 0; i < Sidebar.backs.size(); i++)
			if (Sidebar.backs.get(i).alpha == 0)
				Sidebar.backs.remove(i--);
		*/
		AffineTransform tx = new AffineTransform();
		tx.translate(640, 360); // move origin to screen center
		tx.rotate(0.002 * Panel.time); // rotate around that point
		tx.translate(-780, -720); // center the image
		Graphics2D g2d = (Graphics2D) g;
		Composite oldc = g2d.getComposite();
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 0.2);
	    g2d.setComposite(ac);
		g2d.drawImage(Menu.gray, tx, null);
		g2d.setComposite(oldc);
	}
}

