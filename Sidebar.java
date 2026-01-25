import java.awt.*;
import java.util.*;
import java.util.Queue;

class Sidebar {
	static int hands, discs, ante, money, chips, handSize, maxHands, maxDiscs;
	static double mult;
	static int[] levels;
	static String blind;
	static Color blindColor;
	static boolean db;
	static int objective;
	final static String[] pokers = {"High Card", "Pair", "Two Pair", "Three of a Kind", 
			"Straight", "Flush", "Full House", "Four of a Kind", "Straight Flush", 
			"Five of a Kind", "Flush House", "Flush Five"};
	static int baseChips;
	final static String[] suitsList = {"Spades", "Diamonds", "Hearts", "Clubs"};
	static int[] levelValues = {100, 300, 800, 2000, 5000, 11000, 20000, 35000, 50000};
	static boolean[] avail;
	static int[] counts;
	static String mode;
	static ArrayList<Playing> deck;
	static ArrayList<Joker> jokers;
	static int jslots;
	static ArrayList<Consumable> cons;
	static int cslots;
	static Button hd;
	static Button dd;
	static Button pd;
	static Button sd;
	static Button cc;
	static Button mc;
	static boolean cashTransitionActive;
	static boolean cashOverlayStarted;
	static double cashTransitionStart;
	static int sdBaseX;
	static int sdBaseY;
	static int sdBaseL;
	static int sdBaseW;
	static Button ad;
	static Button bd;
	static Button md;
	static Button rd;
	static Button info;
	static boolean id;
	static Button use, sell, move;
	static Card selected;
	static Consumable last;
	static String finalHand;
	static Consumable slot;
	static boolean sort;
	static long lastTime;
	static int wait;
	static double threshold;
	Sidebar() {
		hands = maxHands = 4;
		discs = maxDiscs = 3;
		ante = 1;
		money = 4;
		chips = 0;
		mult = 0;
		handSize = 8;
		levels = new int[12];
		avail = new boolean[12];
		for (int i = 0; i < 12; i++) {
			levels[i] = 1;
			if (i < 9)
				avail[i] = true;
		}
		counts = new int[12];
		objective = levelValues[ante];
		blind = "Small";
		blindColor = Color.BLUE;
		db = true;
		mode = "battle";
		deck = new ArrayList<>();
		for (int i = 2; i <= 10; i++) {
			deck.add(new Playing(Integer.toString(i), "Spades"));
			deck.add(new Playing(Integer.toString(i), "Hearts"));
			deck.add(new Playing(Integer.toString(i), "Diamonds"));
			deck.add(new Playing(Integer.toString(i), "Clubs"));
		}
		String[] faces = {"Jack", "Queen", "King"};
		for (String s : faces) {
			deck.add(new Playing(s, "Spades"));
			deck.add(new Playing(s, "Hearts"));
			deck.add(new Playing(s, "Diamonds"));
			deck.add(new Playing(s, "Clubs"));
		}
		deck.add(new Playing("Ace", "Spades"));
		deck.add(new Playing("Ace", "Hearts"));
		deck.add(new Playing("Ace", "Diamonds"));
		deck.add(new Playing("Ace", "Clubs"));
		jokers = new ArrayList<>();
		jslots = 5;
		cons = new ArrayList<>();
		cslots = 3;
		hd = new Button(150, 450, 80, 70, "", Integer.toString(hands), new Color(70, 150, 255));
		hd.setFormat(30, 45, 30, 0);
		hd.vib = 10;
		dd = new Button(235, 450, 80, 70, "", Integer.toString(discs), new Color(255, 0, 0));
		dd.setFormat(30, 45, 30, 0);
		dd.vib = 10;
		pd = new Button(25, 290, 290, 150, "", "", new Color(80, 80, 80));
		pd.setFormat(20, 40, 22, 0);
		sd = new Button(25, 220, 290, 60, "", "Score: 0", new Color(80, 80, 80));
		sd.setFormat(20, 40, 25, 0);
		sdBaseX = sd.x;
		sdBaseY = sd.y;
		sdBaseL = sd.l;
		sdBaseW = sd.w;
		cashTransitionActive = false;
		cashOverlayStarted = false;
		cc = new Button(40, 360, 100, 70, "", "", new Color(70, 150, 255));
		cc.setFormat(20, 47, 30, 0);
		cc.vib = 3 + 2*Math.random();
		mc = new Button(200, 360, 100, 70, "", "", new Color(255, 0, 0));
		mc.setFormat(20, 47, 30, 0);
		mc.vib = 3 + 2*Math.random();
		ad = new Button(150, 610, 165, 70, "", "Ante: " + ante + "/8", new Color(80, 80, 80));
		ad.setFormat(12, 48, 30, 0);
		bd = new Button(25, 20, 290, 60, "", blind + " Blind", Color.BLUE);
		bd.setFormat(80, 40, 25, 0);
		bd.wobble = 10;
		bd.vib = 20;
		md = new Button(150, 530, 165, 70, "", "$" + money, new Color(80, 80, 80));
		md.setFormat(20, 48, 30, 0);
		rd = new Button(25, 90, 290, 120, "", "Score to beat:", new Color(80, 80, 80));
		rd.setFormat(120, 35, 20, 0);
		info = new Button(25, 450, 120, 70, "info", "Info", new Color(200, 150, 0));
		info.setFormat(26, 48, 30, 0);
		info.vib = 20;
		id = false;
		use = new Button(25, 610, 75, 50, "use", "Use: $", Color.RED);
		use.setFormat(20, 35, 25, 0);
		sell = new Button(25, 530, 110, 50, "sell", "Sell: $", new Color(24, 214, 74));
		sell.setFormat(20, 35, 25, 0);
		move = new Button(420, 320, 95, 50, "shift", "Move", Color.DARK_GRAY);
		move.setFormat(20, 35, 25, 0);
		selected = null;
		last = null;
		finalHand = null;
		slot = null;
		sort = false;
		lastTime = 0;
		wait = 30;
		threshold = 1;
	}
	static void startCashTransition() {
		if (cashTransitionActive || Panel.isTransitioning())
			return;
		cashTransitionActive = true;
		cashOverlayStarted = false;
		cashTransitionStart = Panel.renderTime;
		sd.text = "";
		Battle.playing = true;
	}
	static void finishToCash() {
		Battle.shuffled = new LinkedList<>();
		for (Playing c : deck) {
			c.selected = false;
			c.override = false;
			c.border = c.original;
		}
		Panel.screens[2] = new Cash();
		Panel.screens[2].initialize();
		Battle.score = 0;
		sd.text = "Score: 0";
		sd.x = sdBaseX;
		sd.y = sdBaseY;
		sd.l = sdBaseL;
		sd.w = sdBaseW;
		sd.color = new Color(80, 80, 80);
		Battle.playing = false;
		Sidebar.info.color = new Color(200, 150, 0);
	}
	void check(int x, int y) {
		if (info.field(x, y))
			info.act();
		if (id)
			return;
		if (use.field(x, y)) {
			use.act();
			return;
		}
		if (sell.field(x, y)) {
			sell.act();
			return;
		}
		if (move.field(x, y)) {
			move.act();
			return;
		}
		if (!Battle.playing) { 
			for (Joker j : jokers) {
				if (j.field(x, y)) {
					if (j.selected) {
						j.act();
						selected = null;
					}
					else {
						for (Joker i : jokers)
							if (i.selected)
								i.act();
						for (Consumable i : cons)
							if (i.selected)
								i.act();
						if (Panel.s == 3) {
							for (Card i : Shop.slots)
								if (i != null && i.selected)
									i.act();
							Shop.selected = null;
						}
						j.act();
						selected = j;
					}
				}
			}
			for (Consumable j : cons) {
				if (j.field(x, y)) {
					if (j.selected) {
						j.act();
						selected = null;
					}
					else {
						for (Joker i : jokers)
							if (i.selected)
								i.act();
						for (Consumable i : cons)
							if (i.selected)
								i.act();
						if (Panel.s == 3) {
							for (Card i : Shop.slots)
								if (i != null && i.selected)
									i.act();
							Shop.selected = null;
						}
						j.act();
						selected = j;
					}
				}
			}
		}
		sort = false;
	}
	void draw(Graphics g) {
		threshold = 1 / Math.pow(2, Joker.num("Oops! All 6s"));
		if (Panel.s == 1 && !Planet.using) {
			int tempChips = 0; 
			double tempMult = 0;
			if (cc.text.length() > 0)
				tempChips = Integer.parseInt(cc.text);
			if (mc.text.length() > 0)
				tempMult = Double.parseDouble(mc.text);
			int temp = (int) (tempChips * tempMult);
			if (temp >= objective) {
				if (cc.wobble < 20 * temp / objective) 
					cc.wobble += 2;
				if (mc.wobble < 20 * temp / objective)
					mc.wobble += 2;
			}
		}
		hd.text = Integer.toString(hands);
		dd.text = Integer.toString(discs);
		md.text = "$" + money;
		if (hands == 1) {
			Animator.run(() -> {
				while (hands == 1 && hd.wobble < 50) {
					hd.wobble += Math.min(1,1/hd.wobble);
					try {Thread.sleep(100);} catch (Exception e) {}
				}
			});
		}
		else {
			Animator.run(() -> {
				while (hands != 1 && hd.wobble > 0) {
					hd.wobble -= 1;
					try {Thread.sleep(200);} catch (Exception e) {}
				}
				hd.wobble = 0;
			});
		}
		if (discs == 1) {
			Animator.run(() -> {
				while (discs == 1 && dd.wobble < 50) {
					dd.wobble += Math.min(1,1/dd.wobble);
					try {Thread.sleep(100);} catch (Exception e) {}
				}
			});
		}
		else {
			Animator.run(() -> {
				while (discs != 1 && dd.wobble > 0) {
					dd.wobble -= 1;
					try {Thread.sleep(200);} catch (Exception e) {}
				}
				dd.wobble = 0;
			});
		}
		g.setColor(new Color(25, 25, 25));
		g.fillRect(10, 0, 320, 720);
		g.setColor(new Color(50, 50, 50));
		g.fillRect(15, 0, 310, 720);
		if (Battle.playing && !Planet.using) {
			cc.text = Integer.toString(chips);
			if (Math.abs(mult - Math.round(mult)) < 10e-7)
				mc.text = Integer.toString((int) Math.round(mult));
			else if (mult < 100)
				mc.text = Double.toString(mult).substring(0, Math.min(Double.toString(mult).length(), 4));
			else 
				mc.text = Integer.toString((int) mult);
		}
		hd.draw(g);
		dd.draw(g);
		pd.draw(g);
		g.setColor(Color.WHITE);
		g.setFont(Panel.fonts[50]);
		g.drawString("X", 157, 418);
		ad.draw(g);
		if (Panel.s != 3) {
			bd.text = blind + " Blind";
			bd.draw(g);
			rd.draw(g);
			g.setFont(Panel.fonts[35]);
			g.setColor(Color.RED);
			g.drawString(Integer.toString(objective), 145, 165);
			g.setFont(Panel.fonts[20]);
			g.setColor(Color.WHITE);
			int reward = 0;
			if (blind.equals("Small"))
				reward = 3;
			else if (blind.equals("Big"))
				reward = 4;
			else if (blind.equals("Boss"))
				reward = 5;
			g.drawString("Reward: $" + reward, 145, 188);
			if (db) {
				g.setColor(blindColor.darker());
				g.fillOval(40, 105, 90, 90);
				g.setColor(blindColor);
				g.fillOval(45, 110, 80, 80);
				g.setFont(Panel.fonts[50]);
				g.setColor(new Color(255,255,255,blindColor.getAlpha()));
				if (blind.equals("Small"))
					g.drawString("SB", 58, 174);
				else if (blind.equals("Big"))
					g.drawString("BB", 58, 174);
				else if (blind.equals("Boss"))
					g.drawString("!!!", 68, 174);
			}
		}
		else {
			g.setFont(Panel.fonts[120]);
			(new Button(25, 20, 290, 190, "", "", new Color(24, 214, 74))).draw(g);
			g.setColor(Color.WHITE);
			g.drawString("S", 35, 170+Menu.amps[0].d(Menu.amps[0].amp));
			g.drawString("H", 105, 170+Menu.amps[1].d(Menu.amps[1].amp));
			g.drawString("O", 175, 170+Menu.amps[2].d(Menu.amps[2].amp));
			g.drawString("P", 245, 170+Menu.amps[3].d(Menu.amps[3].amp));
		}
		if (cc.text.length() > 0 && Double.parseDouble(cc.text) == 0)
			cc.text = "";
		if (mc.text.length() > 0 && Double.parseDouble(mc.text) == 0)
			mc.text = "";
		if (selected != null && Shop.selected == null) {
			sell.text = "Sell:$" + Integer.toString(selected instanceof Joker ? ((Joker) selected).sell : selected.cost / 2);
			sell.x = selected.x + 85;
			sell.y = selected.y + 18 + selected.d(3);
		}
		if (cons.contains(selected)) {
			use.text = "Use";
			if (((Consumable) selected).useable())
				use.color = Color.RED;
			else
				use.color = Color.DARK_GRAY;
			use.x = selected.x + 85;
			use.y = selected.y + 72 + selected.d(3);
		}
		else
			use.x = use.y = 0;
		if (jokers.contains(selected)) {
			move.text = "Move";
			if (selected != jokers.get(jokers.size() - 1))
				move.color = new Color(200, 150, 0);
			else
				move.color = Color.DARK_GRAY;
			move.x = selected.x + 85;
			move.y = selected.y + 72 + selected.d(3);
		}
		else
			move.x = move.y = 0;
		for (int i = jokers.size() - 1; i >= 0; i--) {
			int distance = (int) Math.round(448.0 / (Math.max(jokers.size(), 5) - 1));
			jokers.get(i).x = 352 + distance*i;
			jokers.get(i).y = 25;
			if (jokers.get(i) == selected) {
				sell.draw(g);
				move.draw(g);
			}
			jokers.get(i).draw(g);
		}
		for (int i = cons.size() - 1; i >= 0; i--) {
			int distance = (int) Math.round(224.0 / (Math.max(cons.size(), 3) - 1));
			cons.get(i).x = 912 + distance*i;
			cons.get(i).y = 25;
			if (cons.get(i) == selected) {
				sell.draw(g);
				use.draw(g);
			}
			cons.get(i).draw(g);
		}
		mc.draw(g);
		cc.draw(g);
		md.draw(g);
		g.setFont(Panel.fonts[25]);
		g.setColor(Color.WHITE);
		g.drawString(jokers.size() + "/" + jslots, 345, 200);
		g.drawString(cons.size() + "/" + cslots, 1210, 200);
		if (id) {
			Animator.run(() -> {
				while (id && info.wobble < 20) {
					info.wobble += Math.min(1,1/info.wobble);
					try {Thread.sleep(100);} catch (Exception e) {}
				}
			});
		}
		else {
			Animator.run(() -> {
				while (!id && info.wobble > 0) {
					info.wobble -= 1;
					try {Thread.sleep(200);} catch (Exception e) {}
				}
				info.wobble = 0;
			});
		}
		info.draw(g);
		if (cashTransitionActive && Panel.s == 1) {
			double elapsed = Panel.renderTime - cashTransitionStart;
			double buttonDuration = 15.0;
			double t = Math.min(1.0, elapsed / buttonDuration);
			double scale = 1.0 - 0.6 * t;
			int newL = (int) Math.round(sdBaseL * scale);
			int newW = (int) Math.round(sdBaseW * scale);
			sd.l = Math.max(1, newL);
			sd.w = Math.max(1, newW);
			sd.x = sdBaseX + (sdBaseL - sd.l) / 2;
			sd.y = sdBaseY + (sdBaseW - sd.w) / 2;
			int alpha = (int) Math.round(255 * (1.0 - t));
			sd.color = new Color(80, 80, 80, Math.max(0, Math.min(255, alpha)));
			sd.text = "";
			sd.draw(g);
			if (!cashOverlayStarted && elapsed >= buttonDuration) {
				cashOverlayStarted = true;
				cashTransitionActive = false;
				Panel.startTransition(2, new Color(80, 80, 80), Sidebar::finishToCash);
			}
		}
		else {
			sd.draw(g);
		}
		if (id) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, 10, 800);
			g.fillRect(330, 0, 1500, 800);
			g.setColor(Color.DARK_GRAY.darker());
			g.fillRect(400, 60, 800, 560);
			g.fillRect(405, 55, 790, 570);
			g.setColor(Color.DARK_GRAY);
			g.fillRect(405, 65, 790, 550);
			g.fillRect(410, 60, 780, 560);
			g.setFont(Panel.fonts[50]);
			g.setColor(Color.WHITE);
			g.drawString("Poker Hands", 640, 120);
			for (int i = 0; i < 12; i++) {
				int j = 11 - i;
				g.fillRect(415, 140 + 40*i, 550, 35);
				g.fillRect(410, 145 + 40*i, 560, 25);
				String poker = avail[j] ? pokers[j] : "????";
				g.setFont(Panel.fonts[20]);
				g.setColor(Color.BLACK);
				g.drawString(poker, 418, 165 + 40*i);
				g.drawString("lv. " + levels[j], 910, 165 + 40 * i);
				g.setColor(new Color(70, 150, 255));
				g.fillRect(980, 140 + 40*i, 50, 35);
				g.fillRect(975, 145 + 40*i, 60, 25);
				g.setColor(Color.RED);
				g.fillRect(1070, 140 + 40*i, 50, 35);
				g.fillRect(1065, 145 + 40*i, 60, 25);
				g.setColor(Color.BLACK);
				g.fillRect(1135, 140 + 40*i, 50, 35);
				g.fillRect(1130, 145 + 40*i, 60, 25);
				g.setColor(Color.WHITE);
				g.drawString(Integer.toString(getStats(pokers[j])[0]), 982, 165 + 40*i);
				g.drawString(Integer.toString(getStats(pokers[j])[1]), 1072, 165 + 40*i);
				g.drawString(Integer.toString(counts[j]), 1137, 165 + 40*i);
				g.setFont(Panel.fonts[30]);
				g.drawString("X", 1041, 171 + 40*i);
			}
		}
		if (slot != null)
			slot.draw(g);
	}
	static String getPoker(Collection<Playing> hand) {
		HashMap<String, Integer> suits = new HashMap<>();
		HashMap<String, Integer> ranks = new HashMap<>();
		for (Playing c : hand) {
			if (c.enhancement == 'T')
				continue;
			if (c.enhancement != 'W') {
				if (suits.keySet().contains(c.suit))
					suits.put(c.suit, suits.get(c.suit) + 1);
				else
					suits.put(c.suit, 1);
			}
			else {
				for (String suit : suitsList) {
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
		if (flush(suits) && ofAKind(ranks, 5))
			return "Flush Five";
		else if (flush(suits) && house(ranks, 3))
			return "Flush House";
		else if (ofAKind(ranks, 5))
			return "Five of a Kind";
		else if (flush(suits) && straight(hand))
			return "Straight Flush";
		else if (ofAKind(ranks, 4))
			return "Four of a Kind";
		else if (house(ranks, 3))
			return "Full House";
		else if (flush(suits))
			return "Flush";
		else if (straight(hand))
			return "Straight";
		else if (ofAKind(ranks, 3))
			return "Three of a Kind";
		else if (house(ranks, 2))
			return "Two Pair";
		else if (ofAKind(ranks, 2))
			return "Pair";
		else
			return "High Card";
	}
	static boolean flush(HashMap<String, Integer> suits) {
		int maximum = 0;
		for (String key : suits.keySet())
			if (suits.get(key) > maximum)
				maximum = suits.get(key);
		if (Joker.contains("Four Fingers"))
			return maximum >= 4;
		return maximum == 5;
	}
	static boolean ofAKind(HashMap<String, Integer> ranks, int num) {
		int maximum = 0;
		for (String key : ranks.keySet())
			if (ranks.get(key) > maximum)
				maximum = ranks.get(key);
		return maximum >= num;
	}
	static boolean house(HashMap<String, Integer> ranks, int num) {
		ArrayList<Integer> counts = new ArrayList<>();
		for (String key : ranks.keySet())
			counts.add(ranks.get(key));
		Collections.sort(counts, (a, b) -> b - a);
		if (counts.size() < 2)
			return false;
		return counts.get(0) >= num && counts.get(1) == 2;
	}
	static boolean straight(Collection<Playing> hand) {
		int[] rNums = new int[15];
		for (Playing c : hand) {
			if (c.enhancement == 'T')
				continue;
			rNums[c.rNum]++;
			if (c.rNum == 14)
				rNums[1]++;
		}
		int length = 5;
		if (Joker.contains("Four Fingers"))
			length = 4;
		for (int i = 1; i <= 15 - length; i++) {
			boolean flag = true;
			for (int j = i; j < i + length; j++) {
				if (rNums[j] == 0) {
					flag = false;
					break;
				}
			}
			if (flag)
				return true;
		}
		return false;
	}
	static int getIndex(String poker) {
		int n = 0;
		for (int i = 0; i < pokers.length; i++) {
			if (poker.equals(pokers[i])) {
				n = i;
				break;
			}
		}
		return n;
	}
	static int[] getStats(String poker) {
		int level = levels[getIndex(poker)] - 1;
		if (poker.equals("High Card"))
			return new int[] {5 + 10*level, 1 + level};
		else if (poker.equals("Pair"))
			return new int[] {10 + 15*level, 2 + level};
		else if (poker.equals("Two Pair"))
			return new int[] {20 + 20*level, 2 + level};
		else if (poker.equals("Three of a Kind"))
			return new int[] {30 + 20*level, 3 + 2*level};
		else if (poker.equals("Straight"))
			return new int[] {30 + 30*level, 4 + 3*level};
		else if (poker.equals("Flush"))
			return new int[] {35 + 15*level, 4 + 2*level};
		else if (poker.equals("Full House"))
			return new int[] {40 + 25*level, 4 + 2*level};
		else if (poker.equals("Four of a Kind"))
			return new int[] {60 + 30*level, 7 + 3*level};
		else if (poker.equals("Straight Flush"))
			return new int[] {100 + 40*level, 8 + 4*level};
		else if (poker.equals("Five of a Kind"))
			return new int[] {120 + 35*level, 12 + 3*level};
		else if (poker.equals("Flush House"))
			return new int[] {140 + 40*level, 14 + 4*level};
		else if (poker.equals("Flush Five"))
			return new int[] {160 + 50*level, 16 + 3*level};
		return new int[] {0, 0};
	}
	static boolean isScored(ArrayList<Playing> played, Playing c) {
		if (c.enhancement == 'T')
			return true;
		String poker = getPoker(played);
		if (!poker.equals("High Card") && !poker.equals("Pair") && !poker.equals("Two Pair") && !poker.equals("Three of a Kind")
				&& !poker.equals("Four of a Kind"))
			return true;
		ArrayList<Playing> temp = (ArrayList<Playing>) played.clone();
		HashMap<String, Integer> ranks = new HashMap<>();
		for (Playing card : played) {
			if (c.enhancement == 'T')
				continue;
			if (ranks.keySet().contains(card.rank))
				ranks.put(card.rank, ranks.get(card.rank) + 1);
			else
				ranks.put(card.rank, 1);
		}
		if (poker.equals("High Card")) {
			Collections.sort(temp, (a, b) -> b.rNum - a.rNum);
			int index = 0;
			while (temp.get(index).enhancement == 'T')
				index++;
			if (temp.get(index) == c)
				return true;
			else
				return false;
		}
		else if (poker.equals("Pair") || poker.equals("Three of a Kind") || poker.equals("Four of a Kind")) {
			String maxRank = "";
			int max = 0;
			for (String key : ranks.keySet()) {
				if (ranks.get(key) > max) {
					max = ranks.get(key);
					maxRank = key;
				}
			}
			if (c.rank.equals(maxRank))
				return true;
			else
				return false;
		}
		else if (poker.equals("Two Pair")) {
			String maxRank1 = "";
			String maxRank2 = "";
			int max = 0;
			for (String key : ranks.keySet()) {
				if (ranks.get(key) > max) {
					max = ranks.get(key);
					maxRank1 = key;
				}
			}
			for (String key : ranks.keySet())
				if (ranks.get(key) == max && !key.equals(maxRank1))
					maxRank2 = key;
			if (c.rank.equals(maxRank1) || c.rank.equals(maxRank2))
				return true;
			else
				return false;
		}
		else
			return true;
	}
	static boolean editionable() {
		for (Joker j : Sidebar.jokers)
			if (j.edition == ' ')
				return true;
		return false;
	}
	static void progress() {
		if (Battle.score >= objective) {
			Battle.playing = true;
			Animator.run(() -> {
				while (cc.wobble > 0 || mc.wobble > 0) {
					if (cc.wobble > 0)
						cc.wobble -= 0.5;
					if (mc.wobble > 0)
						mc.wobble -= 0.5;
					try {Thread.sleep(5);} catch (InterruptedException e) {}
				}
				cc.wobble = 0;
				mc.wobble = 0;
				cc.vib = 2*Math.random() + 3;
				mc.vib = 2*Math.random() + 3;
			});
			boolean flag = false;
			for (Playing c : Battle.hand)
				if (c.enhancement == 'G')
					flag = true;
			if (flag)
				try {Thread.sleep(250);} catch (InterruptedException e) {}
			Queue<Playing> hand = new LinkedList<>();
			for (Playing c : Battle.hand) {
				hand.add(c);
				for (int i = 0; i < Joker.num("Mime"); i++)
					hand.add(c);
				if (c.seal == 'R')
					hand.add(c);
			}
			while (!hand.isEmpty()) {
				Playing c = hand.poll();
				if (c.enhancement == 'G')
					addMoney(c, Color.YELLOW, 3);
				if (c.seal == 'B' && Sidebar.cons.size() < Sidebar.cslots) {
					c.override = true;
					c.border = new Color(36, 159, 212);
					Sidebar.cons.add(new Planet(Planet.planets[Sidebar.getIndex(Sidebar.finalHand)]));
					Animator.run(() -> {
						while (c.wobble > -50) {
							c.wobble -= 1;
							try {Thread.sleep(1);} catch (InterruptedException e) {};
						}
						while (c.wobble < 35) {
							c.wobble += 1;
							try {Thread.sleep(1);} catch (InterruptedException e) {};
						}
						while (c.wobble > 0) {
							c.wobble -= 0.5;
							try {Thread.sleep(1);} catch (InterruptedException e) {};
						}
					});
					try {Thread.sleep(250);} catch (InterruptedException e) {}
					c.border = c.original;
					c.override = false;
					try {Thread.sleep(250);} catch (InterruptedException e) {}
				}
			}
			try {Thread.sleep(250);} catch (InterruptedException e) {}
			for (int x = 0; x < Sidebar.jokers.size(); x++) {
				Joker j = Sidebar.jokers.get(x);
				flag = false;
				if (j.name.equals("Gros Michel")) {
					if (Math.random() < 0.166666666/threshold) {
						j.override = true;
						while (!j.original.equals(Color.BLACK) || !j.border.equals(Color.BLACK)) {
							j.original = j.original.darker();
							j.border = j.border.darker();
							try {Thread.sleep(50);} catch (InterruptedException e) {}
						}
						for (int i = 0; i < 255; i+=2) {
							j.original = new Color(i, 0, 0);
							j.border = j.original.darker();
							try {Thread.sleep(1);} catch (InterruptedException e) {}
						}
						for (int i = 0; i < 255; i+=2) {
							j.original = new Color(255, i, 0);
							j.border = j.original.darker();
							try {Thread.sleep(1);} catch (InterruptedException e) {}
						}
						for (int i = 255; i > 1; i-=2) {
							j.alpha = i;
							try {Thread.sleep(6);} catch (InterruptedException e) {}
						}
						Sidebar.jokers.remove(j);
						x--;
						Joker.commons.remove(Joker.banana1);
						j.sold();
						Joker.update();
						flag = true;
					}
					else {
						j.border = Color.GREEN;
						j.override = true;
						Animator.run(() -> {
							while (j.wobble > -50) {
								j.wobble -= 1;
								try {Thread.sleep(1);} catch (InterruptedException e) {};
							}
							while (j.wobble < 35) {
								j.wobble += 1;
								try {Thread.sleep(1);} catch (InterruptedException e) {};
							}
							while (j.wobble > 0) {
								j.wobble -= 0.5;
								try {Thread.sleep(1);} catch (InterruptedException e) {};
							}
						});
						try {Thread.sleep(250);} catch (InterruptedException e) {}
						j.border = j.original;
						j.override = false;
						flag = true;
					}
				}
				else if (j.name.equals("Egg")) {
					j.border = Color.ORANGE;
					j.override = true;
					Animator.run(() -> {
						while (j.wobble > -50) {
							j.wobble -= 1;
							try {Thread.sleep(1);} catch (InterruptedException e) {};
						}
						while (j.wobble < 35) {
							j.wobble += 1;
							try {Thread.sleep(1);} catch (InterruptedException e) {};
						}
						while (j.wobble > 0) {
							j.wobble -= 0.5;
							try {Thread.sleep(1);} catch (InterruptedException e) {};
						}
					});
					try {Thread.sleep(250);} catch (InterruptedException e) {}
					j.border = j.original;
					j.override = false;
					j.sell += 3;
					flag = true;
				}
				if (flag)
					try {Thread.sleep(250);} catch (InterruptedException e) {}
			}
			for (int i = Battle.hand.size() - 1; i >= 0; i--) {
				Battle.hand.remove(Battle.hand.get(i));
				try {Thread.sleep(80);} catch (InterruptedException e) {}
			}
			try {Thread.sleep(250);} catch (InterruptedException e) {}
			if (blind.equals("Boss")) {
				ante++;
				ad.text = "Ante: " + ante + "/8";
				ad.color = ad.color.brighter();
				try {Thread.sleep(250);} catch (InterruptedException e) {}
				ad.color = ad.color.darker();
			}
			info.color = Color.DARK_GRAY;
			Battle.sSuit.color = Color.DARK_GRAY;
			Battle.sRank.color = Color.DARK_GRAY;
			startCashTransition();
		}
		else
			Battle.createHand(Battle.hand.size());
	}
	static void addChips(Card c, Color color, int chips) {
		if (Joker.flag)
			wobble(Joker.current);
		c.override = true;
		c.selected = true;
		c.border = color;
		Animator.run(() -> {
			while (c.wobble > -50) {
				c.wobble -= 1;
				try {Thread.sleep(1);} catch (InterruptedException e) {};
			}
			while (c.wobble < 35) {
				c.wobble += 1;
				try {Thread.sleep(1);} catch (InterruptedException e) {};
			}
			while (c.wobble > 0) {
				c.wobble -= 0.5;
				try {Thread.sleep(1);} catch (InterruptedException e) {};
			}
		});
		Sidebar.chips += chips;
		Sidebar.cc.color = Sidebar.cc.color.darker();
		try {Thread.sleep(250);} catch (InterruptedException e) {}
		Sidebar.cc.color = Sidebar.cc.color.brighter();
		c.selected = false;
		c.override = false;
		c.border = c.original;
		try {Thread.sleep(250);} catch (InterruptedException e) {}
	}
	static void addMult(Card c, Color color, int mult) {
		if (Joker.flag)
			wobble(Joker.current);
		c.override = true;
		c.selected = true;
		c.border = color;
		Animator.run(() -> {
			while (c.wobble > -50) {
				c.wobble -= 1;
				try {Thread.sleep(1);} catch (InterruptedException e) {};
			}
			while (c.wobble < 35) {
				c.wobble += 1;
				try {Thread.sleep(1);} catch (InterruptedException e) {};
			}
			while (c.wobble > 0) {
				c.wobble -= 0.5;
				try {Thread.sleep(1);} catch (InterruptedException e) {};
			}
		});
		Sidebar.mult += mult;
		Sidebar.mc.color = Sidebar.mc.color.darker();
		try {Thread.sleep(250);} catch (InterruptedException e) {}
		Sidebar.mc.color = Sidebar.mc.color.brighter();
		c.selected = false;
		c.override = false;
		c.border = c.original;
		try {Thread.sleep(250);} catch (InterruptedException e) {}
	}
	static void timesMult(Card c, Color color, double times) {
		if (Joker.flag)
			wobble(Joker.current);
		c.override = true;
		c.selected = true;
		c.border = color;
		Animator.run(() -> {
			while (c.wobble > -50) {
				c.wobble -= 1;
				try {Thread.sleep(1);} catch (InterruptedException e) {};
			}
			while (c.wobble < 35) {
				c.wobble += 1;
				try {Thread.sleep(1);} catch (InterruptedException e) {};
			}
			while (c.wobble > 0) {
				c.wobble -= 0.5;
				try {Thread.sleep(1);} catch (InterruptedException e) {};
			}
		});
		Sidebar.mult *= times;
		Sidebar.mc.color = new Color(255, 100, 100);
		try {Thread.sleep(250);} catch (InterruptedException e) {}
		Sidebar.mc.color = new Color(255, 0, 0);
		c.selected = false;
		c.override = false;
		c.border = c.original;
		try {Thread.sleep(250);} catch (InterruptedException e) {}
	}
	static void addMoney(Card c, Color color, int cash) {
		if (Joker.flag)
			wobble(Joker.current);
		c.override = true;
		c.selected = true;
		c.border = color;
		Animator.run(() -> {
			while (c.wobble > -50) {
				c.wobble -= 1;
				try {Thread.sleep(1);} catch (InterruptedException e) {};
			}
			while (c.wobble < 35) {
				c.wobble += 1;
				try {Thread.sleep(1);} catch (InterruptedException e) {};
			}
			while (c.wobble > 0) {
				c.wobble -= 0.5;
				try {Thread.sleep(1);} catch (InterruptedException e) {};
			}
		});
		Sidebar.money += cash;
		Sidebar.md.color = Color.YELLOW;
		try {Thread.sleep(250);} catch (InterruptedException e) {}
		Sidebar.md.color = new Color(80, 80, 80);
		c.selected = false;
		c.override = false;
		c.border = c.original;
		try {Thread.sleep(250);} catch (InterruptedException e) {}
	}
	static void wobble(Card c) {
		Animator.run(() -> {
			final double amp = c instanceof Playing ? 35 : 70;
			final double freq = 10;
			final long durationMs = 700;
			long start = System.currentTimeMillis();
			while (System.currentTimeMillis() - start < durationMs) {
				double t = (System.currentTimeMillis() - start) / (double) durationMs;
				double envelope = Math.sin(Math.PI * t);
				double phase = (Panel.renderTime + c.var) / freq;
				c.wobble = amp * envelope * Math.sin(phase);
				try {Thread.sleep(10);} catch (InterruptedException e) {}
			}
			c.wobble = 0;
		});
	}
}


