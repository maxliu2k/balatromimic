import java.awt.*;

class Shop extends Screen {
	static Button buy, roll, next;
	static Card[] slots;
	static int reroll = 5;
	static int temproll;
	static Card selected;
	Shop() {
		slots = new Card[6];
		for (int i = 0; i < 6; i++) {
			int random = (int) (15*Math.random());
			if (random < 5)
				slots[i] = Joker.get();
			else if (random < 8)
				slots[i] = Planet.get();
			else if (random < 11)
				slots[i] = Tarot.get();
			else
				slots[i] = new Playing(Playing.ranks[(int) (13*Math.random())], Playing.suits[(int) (4*Math.random())], "111");
		}
	}
	void initialize() {
		buy = new Button(420, 240, 150, 70, "buy", "Buy", Color.DARK_GRAY);
		buy.setFormat(52, 45, 25, 0);
		buy.vib = 20;
		roll = new Button(580, 320, 150, 70, "reroll", "Reroll-$5", new Color(24, 214, 74));
		roll.setFormat(15, 45, 25, 0);
		roll.vib = 20;
		next = new Button(580, 240, 150, 70, "next", "Next", new Color(70, 150, 255));
		next.setFormat(45, 45, 25, 0);
		next.wobble = 10;
		next.vib = 20;
		temproll = reroll;
		selected = null;
	}
	void check(int x, int y) {
		Panel.side.check(x, y);
		if (Sidebar.id || Battle.playing)
			return;
		for (Card j : slots) {
			if (j != null && j.field(x, y)) {
				if (j.selected) {
					j.act();
					if (j instanceof Playing)
						j.override = false;
					Sidebar.selected = null;
					selected = null;
				}
				else {
					for (Joker i : Sidebar.jokers)
						if (i.selected)
							i.act();
					for (Consumable i : Sidebar.cons)
						if (i.selected)
							i.act();
					for (Card i : slots) {
						if (i != null && i.selected) {
							if (i instanceof Playing)
								i.override = false;
							i.act();
						}
					}
					j.act();
					if (j instanceof Playing)
						j.override = true;
					Sidebar.selected = j;
					Sidebar.sell.text = "Sell";
					selected = j;
				}
			}
		}
		if (buy.field(x, y))
			buy.act();
		else if (roll.field(x, y))
			roll.act();
		else if (next.field(x, y))
			next.act();
		if (selected != null && selected instanceof Consumable && ((Consumable) selected).useable())
			Sidebar.use.text = "$" + Shop.selected.cost;
		else
			Sidebar.use.text = "Use";
	}
	void draw(Graphics g) {
		g.setColor(new Color(21, 138, 85));
		g.fillRect(0, 0, 1280, 720);
		Battle.drawBack(g);
		Cash.back.draw(g);
		g.setColor(Color.WHITE);
		g.setFont(Panel.fonts[40]);
		g.drawString("Improve", 925, 300);
		g.drawString("Your Run!", 915, 350);
		for (int i = 0; i < slots.length; i++) {
			if (slots[i] != null) {
				slots[i].x = 465 + 112*i;
				slots[i].y = 450;
				slots[i].draw(g);;
			}
		}
		Card select = null;
		for (Card slot : slots) {
			if (slot != null && slot.selected) {
				select = slot;
				break;
			}
		}
		if (select == null || Planet.using) {
			buy.color = Color.DARK_GRAY;
			buy.text = "Buy";
		}
		else if (select instanceof Joker && Sidebar.jokers.size() == Sidebar.jslots && ((Joker) select).edition != 'N' 
				|| select instanceof Consumable && Sidebar.cons.size() == Sidebar.cslots || select.cost > Sidebar.money) {
			buy.color = Color.DARK_GRAY;
			buy.text = "$" + select.cost;
		}
		else {
			buy.color = new Color(24, 214, 74);
			buy.text = "$" + select.cost;
		}
		g.setFont(Panel.fonts[20]);
		g.setColor(Color.WHITE);
		g.drawString(". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .", 420, 410);
		buy.draw(g);
		int count = 0;
		for (Card c : slots)
			if (c == null)
				count++;
		if (temproll > Sidebar.money) {
			roll.color = Color.DARK_GRAY;
			roll.wobble = 0;
		}
		else {
			roll.color = new Color(24, 214, 74);
			roll.wobble = 5*count;
		}
		roll.draw(g);
		Panel.side.draw(g);
		next.draw(g);
		if (Sidebar.id)
			Panel.side.draw(g);
		if (alpha > 0) {
			alpha -= 10;
			if (alpha < 0) {
				alpha = 0;
				return;
			}
			if (Panel.s == 3)
				g.setColor(new Color(200, 150, 0, alpha));
			else
				g.setColor(new Color(70, 150, 255, alpha));
			g.fillRect(0, 0, 1280, 720);
		}
	}
}

