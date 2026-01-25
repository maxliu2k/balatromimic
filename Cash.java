import java.awt.*;
import java.util.*;

class Cash extends Screen {
	long init;
	int money;
	static Button back;
	static ArrayList<Button> list;
	static Button out;
	static boolean transitionActive;
	static boolean transitionOverlayStarted;
	static double transitionStart;
	static int outBaseX;
	static int outBaseY;
	static int outBaseL;
	static int outBaseW;
	void initialize() {
		money = 0;
		init = Long.MAX_VALUE;
		transitionActive = false;
		transitionOverlayStarted = false;
		back = new Button(400, 220, 800, 800, "", "", Color.DARK_GRAY);
		list = new ArrayList<>();
		int reward = 0;
		if (Sidebar.blind.equals("Small"))
			reward = 3;
		else if (Sidebar.blind.equals("Big"))
			reward = 4;
		else if (Sidebar.blind.equals("Boss"))
			reward = 5;
		Button other = new Button(420, 320, 0, 0, "", Sidebar.blind + " Blind defeated: $" + reward, Color.WHITE);
		other.setFormat(0, 0, 25, 0);
		list.add(other);
		money += reward;
		if (Sidebar.hands > 0) {
			Button temp = new Button(420, 320, 0, 0, "", "Remaining hands ($1 each): $" + Sidebar.hands, Color.WHITE);
			temp.setFormat(0, 0, 25, 0);
			list.add(temp);
			money += Sidebar.hands;
		}
		if (Sidebar.money >= 5 && !Joker.contains("Credit Card")) {
			int interest = Math.min(Sidebar.money/5, 5);
			Button temp = new Button(420, 320, 0, 0, "", "$1 interest for every $5 (max $5): $" + interest, Color.WHITE);
			temp.setFormat(0, 0, 25, 0);
			list.add(temp);
			money += interest;
		}
		for (Joker j : Joker.get("Gratification")) {
			if (Sidebar.discs == Sidebar.maxDiscs) {
				Button temp = new Button(420, 320, 0, 0, "", "$2 per unused discard: $" + Sidebar.discs*2, Color.WHITE);
				temp.setFormat(0, 0, 25, 0);
				temp.hold = j;
				list.add(temp);
				money += Sidebar.discs*2;
			}
		}
		for (Button b : list) {
			b.vib = 15;
			b.wobble = 1;
		}
		out = new Button(520, 240, 560, 100, "cash", "Cash Out: $" + money, new Color(200, 150, 0));
		out.setFormat(100, 70, 50, 0);
		out.wobble = 10;
		out.vib = 20;
		outBaseX = out.x;
		outBaseY = out.y;
		outBaseL = out.l;
		outBaseW = out.w;
		Animator.run(() -> {
			try {Thread.sleep(1000);} catch (InterruptedException e) {}
			for (int j = 255; j > 1; j-=1) {
				Sidebar.blindColor = new Color(Sidebar.blindColor.getRed(),Sidebar.blindColor.getGreen(),Sidebar.blindColor.getBlue(), j);
				try {Thread.sleep(3);} catch (InterruptedException e) {}
			}
			Sidebar.db = false;
			init = Panel.time;
		});
	}
	static void startCashTransition() {
		if (transitionActive || Panel.isTransitioning())
			return;
		Battle.playing = true;
		transitionActive = true;
		transitionOverlayStarted = false;
		transitionStart = Panel.renderTime;
		out.text = "";
	}
	static void finishToShop() {
		Panel.screens[3] = new Shop();
		Panel.screens[3].initialize();
		Sidebar.db = true;
		Sidebar.hands = Sidebar.maxHands;
		Sidebar.discs = Sidebar.maxDiscs;
		Sidebar.hd.color = new Color(70, 150, 255);
		Sidebar.dd.color = Color.RED;
		if (Sidebar.blind.equals("Small")) {
			Sidebar.objective = (int) (Sidebar.levelValues[Sidebar.ante] * 1.5);
			Sidebar.bd.color = new Color(200, 150, 0);
			Sidebar.blindColor = new Color(200, 150, 0);
			Sidebar.bd.setFormat(95, 40, 25, 0);
			Sidebar.blind = "Big";
		}
		else if (Sidebar.blind.equals("Big")) {
			Sidebar.objective = (int) (Sidebar.levelValues[Sidebar.ante] * 2);
			Sidebar.bd.color = Color.RED;
			Sidebar.blindColor = Color.RED;
			Sidebar.bd.setFormat(85, 40, 25, 0);
			Sidebar.blind = "Boss";
		}
		else if (Sidebar.blind.equals("Boss")) {
			Sidebar.objective = (int) (Sidebar.levelValues[Sidebar.ante]);
			Sidebar.bd.color = Color.BLUE;
			Sidebar.blindColor = Color.BLUE;
			Sidebar.bd.setFormat(80, 40, 25, 0);
			Sidebar.blind = "Small";
		}
		Battle.playing = false;
	}
	void check(int x, int y) {
		if (transitionActive || Panel.isTransitioning())
			return;
		if (Panel.time - init > 2*5 + 2*10*list.size())
			Panel.side.check(x, y);
		if (Sidebar.id)
			return;
		if (out.field(x, y) && !Battle.playing)
			out.act();
	}
	void draw(Graphics g) {
		g.setColor(new Color(21, 138, 85));
		g.fillRect(0, 0, 1280, 720);
		Battle.drawBack(g);
		back.draw(g);
		for (int i = 0; i < list.size(); i++) {
			Button b = list.get(i);
			b.y = 400 + 50*i + b.d(1);
			if (Panel.time - init >= 2*5 + 2*10*i) {
				if (list.get(i).hold != null && Panel.time - init == 2*5 + 2*10*i) {
					Joker j = list.get(i).hold;
					Animator.run(() -> {
						j.selected = true;
						j.override = true;
						j.border = Color.BLACK;
						try {Thread.sleep(250);} catch (InterruptedException e) {};
						j.border = j.original;
						j.selected = false;
						j.override = false;
					});
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
				}
				list.get(i).draw(g);
				g.setFont(Panel.fonts[20]);
				g.drawString(". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .", 420, b.y - 30);
			}
		}
		Panel.side.draw(g);
		if (Panel.time - init > 2*5 + 2*10*list.size() && !transitionActive)
			out.draw(g);
		if (Sidebar.id)
			Panel.side.draw(g);
		if (Sidebar.slot != null)
			Sidebar.slot.draw(g);
		if (Panel.time < 10) {
			g.setColor(new Color(80, 80, 80));
			g.fillRect(0, 0, 1280, 720);
		}
		if (transitionActive) {
			double elapsed = Panel.renderTime - transitionStart;
			double buttonDuration = 15.0;
			double t = Math.min(1.0, elapsed / buttonDuration);
			double scale = 1.0 - 0.6 * t;
			int newL = (int) Math.round(outBaseL * scale);
			int newW = (int) Math.round(outBaseW * scale);
			out.l = Math.max(1, newL);
			out.w = Math.max(1, newW);
			out.x = outBaseX + (outBaseL - out.l) / 2;
			out.y = outBaseY + (outBaseW - out.w) / 2;
			int alpha = (int) Math.round(255 * (1.0 - t));
			out.color = new Color(200, 150, 0, Math.max(0, Math.min(255, alpha)));
			out.text = "";
			out.draw(g);
			if (!transitionOverlayStarted && elapsed >= buttonDuration) {
				transitionOverlayStarted = true;
				transitionActive = false;
				Panel.startTransition(3, new Color(200, 150, 0), Cash::finishToShop);
			}
		}
	}
}

