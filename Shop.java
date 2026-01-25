import java.awt.*;
import java.util.*;

class Shop extends Screen {
	static Button buy, roll, next;
	static Card[] slots;
	static int reroll = 5;
	static int temproll;
	static Card selected;
	static boolean transitionActive;
	static boolean transitionOverlayStarted;
	static double transitionStart;
	static int nextBaseX;
	static int nextBaseY;
	static int nextBaseL;
	static int nextBaseW;
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
		transitionActive = false;
		transitionOverlayStarted = false;
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
		nextBaseX = next.x;
		nextBaseY = next.y;
		nextBaseL = next.l;
		nextBaseW = next.w;
		temproll = reroll;
		selected = null;
	}
	static void startNextTransition() {
		if (transitionActive || Panel.isTransitioning() || Battle.playing)
			return;
		transitionActive = true;
		transitionOverlayStarted = false;
		transitionStart = Panel.renderTime;
		next.text = "";
		Battle.playing = true;
	}
	static void prepareNextBattle() {
		if (selected != null) {
			selected = null;
			Sidebar.selected = null;
		}
		if (Sidebar.selected == null) {
			Sidebar.use.color = Color.DARK_GRAY;
			Sidebar.use.text = "Use";
		}
		Battle.hand = new ArrayList<>();
		for (int i = 0; i < Sidebar.jokers.size(); i++) {
			if (Sidebar.jokers.get(i).name.equals("Dagger") && i < Sidebar.jokers.size() - 1) {
				Joker dagger = Sidebar.jokers.get(i);
				Joker c = Sidebar.jokers.get(i + 1);
				Animator.run(() -> {
					try {Thread.sleep(1500);} catch (InterruptedException e) {}
					c.override = false;
					c.selected = false;
					c.border = ((Joker) c).edition == ' ' ? c.original.darker() : ((Joker) c).getEdition();
					((Joker) c).sold();
					Joker.update();
					dagger.count += 2 * c.sell;
					Animator.run(() -> {
						dagger.override = true;
						dagger.selected = true;
						dagger.border = Color.ORANGE;
						Animator.run(() -> {
							while (dagger.wobble > -50) {
								dagger.wobble -= 1;
								try {Thread.sleep(1);} catch (InterruptedException e) {};
							}
							while (dagger.wobble < 30) {
								dagger.wobble += 1;
								try {Thread.sleep(1);} catch (InterruptedException e) {};
							}
							while (dagger.wobble > 0) {
								dagger.wobble -= 1;
								try {Thread.sleep(1);} catch (InterruptedException e) {};
							}
						});
						try {Thread.sleep(250);} catch (InterruptedException e) {}
						dagger.selected = false;
						dagger.override = false;
						dagger.border = dagger.original;
					});
					for (int j = 255; j > 1; j-=2) {
						c.alpha = j;
						try {Thread.sleep(6);} catch (InterruptedException e) {}
					}
					Sidebar.jokers.remove(c);
				});
			}
			if (Sidebar.jokers.get(i).name.equals("Marble")) {
				Joker marble = Sidebar.jokers.get(i);
				Playing stone = new Playing();
				stone.enhancement = 'T';
				Sidebar.deck.add(stone);
				Animator.run(() -> {
					try {Thread.sleep(1500);} catch (InterruptedException e) {}
					marble.override = true;
					marble.selected = true;
					marble.border = Color.BLACK;
					Animator.run(() -> {
						while (marble.wobble > -50) {
							marble.wobble -= 1;
							try {Thread.sleep(1);} catch (InterruptedException e) {};
						}
						while (marble.wobble < 30) {
							marble.wobble += 1;
							try {Thread.sleep(1);} catch (InterruptedException e) {};
						}
						while (marble.wobble > 0) {
							marble.wobble -= 1;
							try {Thread.sleep(1);} catch (InterruptedException e) {};
						}
					});
					try {Thread.sleep(250);} catch (InterruptedException e) {}
					marble.selected = false;
					marble.override = false;
					marble.border = marble.original;
				});
			}
			if (Sidebar.jokers.get(i).name.equals("Burglar")) {
				Joker burglar = Sidebar.jokers.get(i);
				Animator.run(() -> {
					try {Thread.sleep(1500);} catch (InterruptedException e) {}
					burglar.override = true;
					burglar.selected = true;
					burglar.border = Color.BLACK;
					Animator.run(() -> {
						while (burglar.wobble > -50) {
							burglar.wobble -= 1;
							try {Thread.sleep(1);} catch (InterruptedException e) {};
						}
						while (burglar.wobble < 30) {
							burglar.wobble += 1;
							try {Thread.sleep(1);} catch (InterruptedException e) {};
						}
						while (burglar.wobble > 0) {
							burglar.wobble -= 1;
							try {Thread.sleep(1);} catch (InterruptedException e) {};
						}
					});
					try {Thread.sleep(250);} catch (InterruptedException e) {}
					burglar.selected = false;
					burglar.override = false;
					burglar.border = burglar.original;
				});
				Animator.run(() -> {
					try {Thread.sleep(1500);} catch (InterruptedException e) {}
					Sidebar.hands += 3;
					Sidebar.discs = 0;
					Sidebar.dd.color = Color.DARK_GRAY;
					Button.flash(Sidebar.hd);
				});
			}
		}
		ArrayList<Playing> temp = (ArrayList<Playing>) Sidebar.deck.clone();
		Collections.shuffle(temp);
		Battle.shuffled = new LinkedList<>();
		for (Playing c : temp) {
			c.selected = false;
			c.override = false;
			Battle.shuffled.offer(c);
		}
		Battle.selected = 0;
		Battle.playing = false;
		Battle.sSuit.color = new Color(200, 150, 0);
		Battle.sRank.color = new Color(200, 150, 0);
		Battle.dealInitialHand(Battle.DEAL_DELAY_MS);
	}
	void check(int x, int y) {
		if (transitionActive || Panel.isTransitioning())
			return;
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
				slots[i].draw(g);
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
		if (!transitionActive)
			next.draw(g);
		if (Sidebar.id)
			Panel.side.draw(g);
		if (transitionActive) {
			double elapsed = Panel.renderTime - transitionStart;
			double buttonDuration = 15.0;
			double t = Math.min(1.0, elapsed / buttonDuration);
			double scale = 1.0 - 0.6 * t;
			int newL = (int) Math.round(nextBaseL * scale);
			int newW = (int) Math.round(nextBaseW * scale);
			next.l = Math.max(1, newL);
			next.w = Math.max(1, newW);
			next.x = nextBaseX + (nextBaseL - next.l) / 2;
			next.y = nextBaseY + (nextBaseW - next.w) / 2;
			int alpha = (int) Math.round(255 * (1.0 - t));
			next.color = new Color(70, 150, 255, Math.max(0, Math.min(255, alpha)));
			next.text = "";
			next.draw(g);
			if (!transitionOverlayStarted && elapsed >= buttonDuration) {
				transitionOverlayStarted = true;
				transitionActive = false;
				Panel.startTransition(1, new Color(70, 150, 255), Shop::prepareNextBattle);
			}
		}
	}
}

