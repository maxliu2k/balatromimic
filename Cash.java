import java.awt.*;
import java.util.*;

class Cash extends Screen {
	long init;
	int money;
	static Button back;
	static ArrayList<Button> list;
	static Button out;
	void initialize() {
		money = 0;
		init = Long.MAX_VALUE;
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
		new Thread(() -> {
			try {Thread.sleep(1000);} catch (InterruptedException e) {}
			for (int j = 255; j > 1; j-=1) {
				Sidebar.blindColor = new Color(Sidebar.blindColor.getRed(),Sidebar.blindColor.getGreen(),Sidebar.blindColor.getBlue(), j);
				try {Thread.sleep(3);} catch (InterruptedException e) {}
			}
			Sidebar.db = false;
			init = Panel.time;
		}).start();
	}
	void check(int x, int y) {
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
					new Thread(() -> {
						j.selected = true;
						j.override = true;
						j.border = Color.BLACK;
						try {Thread.sleep(250);} catch (InterruptedException e) {};
						j.border = j.original;
						j.selected = false;
						j.override = false;
					}).start();
					new Thread(() -> {
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
					}).start();
				}
				list.get(i).draw(g);
				g.setFont(Panel.fonts[20]);
				g.drawString(". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .", 420, b.y - 30);
			}
		}
		Panel.side.draw(g);
		if (Panel.time - init > 2*5 + 2*10*list.size())
			out.draw(g);
		if (Sidebar.id)
			Panel.side.draw(g);
		if (Sidebar.slot != null)
			Sidebar.slot.draw(g);
		if (Panel.time < 10) {
			g.setColor(new Color(80, 80, 80));
			g.fillRect(0, 0, 1280, 720);
		}
		if (alpha > 0) {
			alpha -= 10;
			if (alpha < 0) {
				alpha = 0;
				return;
			}
			if (Panel.s == 2)
				g.setColor(new Color(80, 80, 80, alpha));
			else
				g.setColor(new Color(200, 150, 0, alpha));
			g.fillRect(0, 0, 1280, 720);
		}
	}
}

