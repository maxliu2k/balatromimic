package game;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;

class Button extends Interactable {
	String job;
	String text;
	Color color;
	int xOff, yOff;
	int size;
	double amp;
	Joker hold;
	static boolean tracker = true;
	Button() {}
	Button(int x0, int y0, int l0, int w0, String j, String t, Color c) {
		super(x0, y0, l0, w0);
		job = j;
		text = t;
		color = c;
		hold = null;
	}
	void setFormat(int x0, int y0, int s, int a) {
		xOff = x0;
		yOff = y0;
		size = s;
		amp = a;
		hold = null;
	}
	void act() {
		if (job.equals("battle")) {
			Menu.first = true;
			Panel.time = Math.max(Panel.time, 220);
			new Thread(() -> {
				Menu.battle.text = "";
				while (Menu.battle.l < 2200) {
					Menu.battle.x -= 3200/Menu.battle.l;
					Menu.battle.y -= 3200/Menu.battle.l;
					Menu.battle.l += 6400/Menu.battle.l;
					Menu.battle.w += 6400/Menu.battle.l;
					try {Thread.sleep(5);} catch (InterruptedException e) {}
				}
				Panel.screens[1] = new Battle();
				Panel.screens[1].initialize();
				Panel.s = 1;
				//debug
				//Sidebar.jokers.add(new Joker("Runner"));
				//Sidebar.cons.add(new Tarot("Sun"));
			}).start();
		}
		else if (job.equals("secret")) {
			text = Integer.toString(++Menu.count);
			Panel.time += 300;
			new Thread(() -> {
				Menu.secret.color = Menu.secret.color.darker();
				try {Thread.sleep(250);} catch (InterruptedException e) {}
				Menu.secret.color = Menu.secret.color.brighter();
				try {Thread.sleep(250);} catch (InterruptedException e) {}
			}).start();
		}
		else if (job.equals("quit")) {
			Panel.time += 300;
			new Thread(() -> {
				Menu.quit.text = "";
				while (Menu.quit.l < 2200) {
					Menu.quit.x -= 3200/Menu.quit.l;
					Menu.quit.y -= 3200/Menu.quit.l;
					Menu.quit.l += 6400/Menu.quit.l;
					Menu.quit.w += 6400/Menu.quit.l;
					try {Thread.sleep(5);} catch (InterruptedException e) {}
				}
				//Panel.timer.stop();
				Balatro.j.setVisible(false);
			}).start();
		}
		else if (job.equals("sSuit")) {
			Collections.sort(Battle.hand, (b, a) -> a.suit.equals(b.suit) ? a.rNum - b.rNum : a.suit.compareTo(b.suit));
			new Thread(() -> {
				Battle.sSuit.color = Battle.sSuit.color.darker();
				try {Thread.sleep(250);} catch (InterruptedException e) {}
				Battle.sSuit.color = Battle.sSuit.color.brighter();
				try {Thread.sleep(250);} catch (InterruptedException e) {}
			}).start();
		}
		else if (job.equals("sRank")) {
			Collections.sort(Battle.hand, (b, a) -> a.rNum == b.rNum ? a.suit.compareTo(b.suit) : a.rNum - b.rNum);
			new Thread(() -> {
				Battle.sRank.color = Battle.sRank.color.darker();
				try {Thread.sleep(250);} catch (InterruptedException e) {}
				Battle.sRank.color = Battle.sRank.color.brighter();
				try {Thread.sleep(250);} catch (InterruptedException e) {}
			}).start();
		}
		else if (job.equals("sLeft")) {
			if (Battle.selected == 1) {
				int index = 0;
				for (int i = 0; i < Battle.hand.size(); i++) {
					if (Battle.hand.get(i).selected) {
						index = i;
						break;
					}
				}
				if (index == 0) {
					index = Battle.hand.size() - 1;
					Playing temp = Battle.hand.get(0);
					Battle.hand.set(0, Battle.hand.get(index));
					Battle.hand.set(index, temp);
				}
				else {
					Playing temp = Battle.hand.get(index);
					Battle.hand.set(index, Battle.hand.get(index - 1));
					Battle.hand.set(index - 1, temp);
				}
				new Thread(() -> {
					Battle.sLeft.color = Battle.sLeft.color.darker();
					try {Thread.sleep(250);} catch (InterruptedException e) {}
					Battle.sLeft.color = Battle.sLeft.color.brighter();
					try {Thread.sleep(250);} catch (InterruptedException e) {}
				}).start();
			}
		}
		else if (job.equals("sRight")) {
			if (Battle.selected == 1) {
				int index = 0;
				for (int i = 0; i < Battle.hand.size(); i++) {
					if (Battle.hand.get(i).selected) {
						index = i;
						break;
					}
				}
				if (index == Battle.hand.size() - 1) {
					Playing temp = Battle.hand.get(0);
					Battle.hand.set(0, Battle.hand.get(index));
					Battle.hand.set(index, temp);
				}
				else {
					Playing temp = Battle.hand.get(index);
					Battle.hand.set(index, Battle.hand.get(index + 1));
					Battle.hand.set(index + 1, temp);
				}
				new Thread(() -> {
					Battle.sRight.color = Battle.sRight.color.darker();
					try {Thread.sleep(250);} catch (InterruptedException e) {}
					Battle.sRight.color = Battle.sRight.color.brighter();
					try {Thread.sleep(250);} catch (InterruptedException e) {}
				}).start();
			}
		}
		else if (job.equals("discard") && Battle.selected > 0 && Sidebar.discs > 0) {
			Battle.playing = true;
			new Thread(() -> {
				for (int i = 0; i < Battle.hand.size(); i++) {
					Playing c = Battle.hand.get(i);
					if (c.selected) {
						if (c.seal == 'P' && Sidebar.cons.size() < Sidebar.cslots) {
							c.override = true;
							c.selected = false;
							c.border = new Color(143, 67, 230);
							Sidebar.cons.add(Tarot.get());
							new Thread(() -> {
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
							}).start();
							try {Thread.sleep(250);} catch (InterruptedException e) {}
							c.border = c.original;
							c.override = false;
							try {Thread.sleep(250);} catch (InterruptedException e) {}
						}
						try {Thread.sleep(80);} catch (InterruptedException e) {}
						Battle.hand.remove(c);
						i--;
					}
				}
				try {Thread.sleep(320);} catch (InterruptedException e) {}
				Battle.createHand(Battle.hand.size());
				Battle.selected = 0;
				Battle.playing = false;
				Panel.screens[1].check(-1, -1);
				Battle.sSuit.color = new Color(200, 150, 0);
				Battle.sRank.color = new Color(200, 150, 0);
			}).start();
			Sidebar.discs--;
			if (Sidebar.discs > 0) {
				new Thread(() -> {
					Sidebar.dd.color = Sidebar.dd.color.darker();
					try {Thread.sleep(250);} catch (InterruptedException e) {}
					Sidebar.dd.color = Sidebar.dd.color.brighter();
					try {Thread.sleep(250);} catch (InterruptedException e) {}
				}).start();
			}
			else
				Sidebar.dd.color = Color.DARK_GRAY;
		}
		else if (job.equals("play hand") && Battle.selected > 0 && Sidebar.hands > 0) {
			Sidebar.selected = null;
			Battle.playing = true;
			String[] words = Sidebar.pd.text.split(" ");
			String poker = "";
			for (int i = 0; i < words.length - 2; i++)
				poker += words[i] + " ";
			poker = poker.trim();
			if (poker.equals("Five of a Kind"))
				Sidebar.avail[9] = true;
			else if (poker.equals("Flush House"))
				Sidebar.avail[10] = true;
			else if (poker.equals("Flush Five"))
				Sidebar.avail[11] = true;
			Sidebar.counts[Sidebar.getIndex(poker)]++;
			Sidebar.chips = Integer.parseInt(Sidebar.cc.text);
			Sidebar.mult = Integer.parseInt(Sidebar.mc.text);
			Sidebar.finalHand = poker;
			new Thread(() -> {
				for (Joker j : Sidebar.jokers) {
					j.selected = false;
					j.border = j.original;
				}
				for (Consumable j : Sidebar.cons) {
					j.selected = false;
					j.border = j.original;
				}
				for (int i = 0; i < Battle.hand.size(); i++)
					if (Battle.hand.get(i).selected)
						Battle.initSize++;
				for (int i = 0; i < Battle.hand.size(); i++) {
					if (Battle.hand.get(i).selected) {
						try {Thread.sleep(80);} catch (InterruptedException e) {}
						Battle.hand.get(i).selected = false;
						Battle.hand.get(i).border = Color.LIGHT_GRAY;
						Battle.played.add(Battle.hand.get(i));
						Battle.hand.remove(Battle.hand.get(i--));
					}
				}
				try {Thread.sleep(500);} catch (InterruptedException e) {}
				Queue<Playing> played = new LinkedList<>();
				for (Playing c : Battle.played) {
					if (Sidebar.isScored(Battle.played, c)) {
						played.add(c);
						if (c.seal == 'R')
							played.add(c);
						if (c.rNum >= 2 && c.rNum <= 5)
							for (int i = 0; i < Joker.get("Hack").size(); i++)
								played.add(c);
						if (Sidebar.hands == 0)
							for (int i = 0; i < Joker.get("Dusk").size(); i++)
								played.add(c);
					}
				}
				int size = played.size();
				boolean faces = false;
				for (Playing c : played)
					if (c.enhancement != 'T' && (c.rank.equals("Jack") || c.rank.equals("Queen") || c.rank.equals("King")))
						faces = true;
				if (Joker.contains("Pareidolia"))
					faces = true;
				for (Joker p : Joker.get("Spaceman")) {
					if (Math.random() < 0.25/Sidebar.threshold) {
						Planet.using = true;
						Planet c = new Planet(Planet.planets[Sidebar.getIndex(Sidebar.finalHand)]);
						Sidebar.pd.text = Sidebar.finalHand + " lv. " + Sidebar.levels[Sidebar.getIndex(Sidebar.finalHand)];
						Sidebar.cc.text = Integer.toString(Sidebar.getStats(Sidebar.finalHand)[0]);
						Sidebar.mc.text = Integer.toString(Sidebar.getStats(Sidebar.finalHand)[1]);
						try {Thread.sleep(250);} catch (InterruptedException e) {}
						c.use();
						p.selected = true;
						new Thread(() -> {
							new Thread(() -> {
								while (p.wobble > -50) {
									p.wobble -= 1;
									try {Thread.sleep(1);} catch (InterruptedException e) {};
								}
								while (p.wobble < 35) {
									p.wobble += 1;
									try {Thread.sleep(1);} catch (InterruptedException e) {};
								}
								while (p.wobble > 0) {
									p.wobble -= 0.5;
									try {Thread.sleep(1);} catch (InterruptedException e) {};
								}
							}).start();
						}).start();
						Sidebar.cc.text = Integer.toString(Sidebar.getStats(Sidebar.finalHand)[0]);
						Sidebar.cc.color = Sidebar.cc.color.darker();
						try {Thread.sleep(250);} catch (InterruptedException e) {}
						p.selected = false;
						Sidebar.cc.color = Sidebar.cc.color.brighter();
						try {Thread.sleep(250);} catch (InterruptedException e) {}
						p.selected = true;
						new Thread(() -> {
							while (p.wobble > -50) {
								p.wobble -= 1;
								try {Thread.sleep(1);} catch (InterruptedException e) {};
							}
							while (p.wobble < 35) {
								p.wobble += 1;
								try {Thread.sleep(1);} catch (InterruptedException e) {};
							}
							while (p.wobble > 0) {
								p.wobble -= 0.5;
								try {Thread.sleep(1);} catch (InterruptedException e) {};
							}
						}).start();
						Sidebar.mc.text = Integer.toString(Sidebar.getStats(Sidebar.finalHand)[1]);
						Sidebar.mc.color = Sidebar.mc.color.darker();
						try {Thread.sleep(250);} catch (InterruptedException e) {}
						p.selected = false;
						Sidebar.mc.color = Sidebar.mc.color.brighter();
						try {Thread.sleep(250);} catch (InterruptedException e) {}
						p.selected = true;
						new Thread(() -> {
							while (p.wobble > -50) {
								p.wobble -= 1;
								try {Thread.sleep(1);} catch (InterruptedException e) {};
							}
							while (p.wobble < 35) {
								p.wobble += 1;
								try {Thread.sleep(1);} catch (InterruptedException e) {};
							}
							while (p.wobble > 0) {
								p.wobble -= 0.5;
								try {Thread.sleep(1);} catch (InterruptedException e) {};
							}
						}).start();
						Sidebar.pd.text = Sidebar.finalHand + " lv. " + Sidebar.levels[Sidebar.getIndex(Sidebar.finalHand)];
						Sidebar.pd.color = Sidebar.pd.color.darker();
						try {Thread.sleep(250);} catch (InterruptedException e) {}
						p.selected = false;
						Sidebar.pd.color = Sidebar.pd.color.brighter();
						try {Thread.sleep(500);} catch (InterruptedException e) {}
						Planet.using = false;
						Sidebar.chips = Sidebar.getStats(Sidebar.finalHand)[0];
						Sidebar.mult = Sidebar.getStats(Sidebar.finalHand)[1];
					}
				}
				if (!faces) {
					for (Joker j : Joker.get("Ride the Bus")) {
						j.count++;
						j.override = true;
						j.selected = true;
						j.border = Color.ORANGE;
						new Thread(() -> {
							while (j.wobble > -50) {
								j.wobble -= 1;
								try {Thread.sleep(1);} catch (InterruptedException e) {};
							}
							while (j.wobble < 30) {       // change here
								j.wobble += 1;
								try {Thread.sleep(1);} catch (InterruptedException e) {};
							}
							while (j.wobble > 0) {        // and here
								j.wobble -= 1;
								try {Thread.sleep(1);} catch (InterruptedException e) {};
							}
						}).start();
						try {Thread.sleep(250);} catch (InterruptedException e) {}
						j.selected = false;
						j.override = false;
						j.border = j.original;
						try {Thread.sleep(250);} catch (InterruptedException e) {}
					}
				}
				else {
					for (Joker j : Joker.get("Ride the Bus")) {
						j.count = 0;
						j.override = true;
						j.selected = true;
						j.border = Color.DARK_GRAY;
						new Thread(() -> {
							while (j.wobble > -50) {
								j.wobble -= 1;
								try {Thread.sleep(1);} catch (InterruptedException e) {};
							}
							while (j.wobble < 30) {       // change here
								j.wobble += 1;
								try {Thread.sleep(1);} catch (InterruptedException e) {};
							}
							while (j.wobble > 0) {        // and here
								j.wobble -= 1;
								try {Thread.sleep(1);} catch (InterruptedException e) {};
							}
						}).start();
						try {Thread.sleep(250);} catch (InterruptedException e) {}
						j.selected = false;
						j.override = false;
						j.border = j.original;
						try {Thread.sleep(250);} catch (InterruptedException e) {}
					}
				}
				for (Joker j : Joker.get("Loyalty Card")) {
					j.count++;
					j.override = true;
					j.selected = true;
					if (j.count != 5)
						j.border = Color.ORANGE;
					else
						j.border = new Color(140, 0, 200);
					new Thread(() -> {
						while (j.wobble > -50) {
							j.wobble -= 1;
							try {Thread.sleep(1);} catch (InterruptedException e) {};
						}
						while (j.wobble < 30) {       // change here
							j.wobble += 1;
							try {Thread.sleep(1);} catch (InterruptedException e) {};
						}
						while (j.wobble > 0) {        // and here
							j.wobble -= 1;
							try {Thread.sleep(1);} catch (InterruptedException e) {};
						}
					}).start();
					try {Thread.sleep(250);} catch (InterruptedException e) {}
					j.selected = false;
					j.override = false;
					j.border = j.original;
					try {Thread.sleep(250);} catch (InterruptedException e) {}
				}
				for (int i = 0; i < size; i++) {
					Playing c = played.poll();
					Sidebar.addChips(c, Color.BLACK, c.enhancement == 'T' ? 50 : c.chips);
					if (c.enhancement == 'B')
						Sidebar.addChips(c, new Color(12, 134, 204), 30);
					else if (c.enhancement == 'M')
						Sidebar.addMult(c, Color.RED, 4);
					else if (c.enhancement == 'A')
						Sidebar.timesMult(c, new Color(140, 0, 200), 2);					
					if (c.enhancement == 'L' && Math.random() < 0.2/Sidebar.threshold)
						Sidebar.addMult(c, Color.RED, 20);
					if (c.enhancement == 'L' && Math.random() < 0.06666666/Sidebar.threshold)
						Sidebar.addMoney(c, Color.YELLOW, 20);
					if (c.seal == 'G')
						Sidebar.addMoney(c, Color.YELLOW, 3);
					if (c.edition == 'F')
						Sidebar.addChips(c, new Color(12, 134, 204), 50);
					else if (c.edition == 'H')
						Sidebar.addMult(c, Color.RED, 10);
					else if (c.edition == 'P')
						Sidebar.timesMult(c, new Color(140, 0, 200), 1.5);
					for (Joker j : Sidebar.jokers)
						j.performPlayed(c);
				}
				Queue<Playing> hand = new LinkedList<>();
				for (Playing c : Battle.hand) {
					hand.add(c);
					for (int i = 0; i < Joker.num("Mime"); i++)
						hand.add(c);
					if (c.seal == 'R')
						hand.add(c);
				}
				for (Playing c : hand) {
					if (c.enhancement == 'S')
						Sidebar.timesMult(c, new Color(140, 0, 200), 1.5);
					for (Joker j : Sidebar.jokers)
						j.performHand(c);
				}
				for (Joker j : Sidebar.jokers)
					j.performNull();
				int score = (int) (Sidebar.chips * Sidebar.mult);
				try {Thread.sleep(500);} catch (InterruptedException e) {}
				Sidebar.pd.text = Integer.toString(score);
				Sidebar.pd.setFormat(20, 45, 30, 0);
				Battle.score += score;
				try {Thread.sleep(1000);} catch (InterruptedException e) {}
				Sidebar.pd.text = "";
				Sidebar.cc.text = "";
				Sidebar.mc.text = "";
				Sidebar.sd.text = "Score: " + Battle.score;
				Sidebar.pd.setFormat(20, 40, 22, 0);
				Sidebar.sd.color = Sidebar.sd.color.darker();
				try {Thread.sleep(250);} catch (InterruptedException e) {}
				Sidebar.sd.color = Sidebar.sd.color.brighter();
				try {Thread.sleep(250);} catch (InterruptedException e) {}
				for (int i = Battle.played.size() - 1; i >= 0; i--) {
					Playing c = Battle.played.get(i);
					if (c.enhancement == 'A' && Math.random() < 0.25/Sidebar.threshold) {
						c.temp = Color.WHITE;
						c.breaking = true;
						while (!c.temp.equals(Color.BLACK) || !c.border.equals(Color.BLACK)) {
							c.temp = c.temp.darker();
							c.border = c.border.darker();
							try {Thread.sleep(50);} catch (InterruptedException e) {}
						}
						for (int j = 0; j < 255; j+=2) {
							c.temp = new Color(j, 0, 0);
							c.border = c.temp.darker();
							try {Thread.sleep(1);} catch (InterruptedException e) {}
						}
						for (int j = 0; j < 255; j+=2) {
							c.temp = new Color(255, j, 0);
							c.border = c.temp.darker();
							try {Thread.sleep(1);} catch (InterruptedException e) {}
						}
						for (int j = 255; j > 1; j-=2) {
							c.alpha = j;
							try {Thread.sleep(6);} catch (InterruptedException e) {}
						}
						Sidebar.deck.remove(c);
					}
					Battle.played.remove(i);
					try {Thread.sleep(80);} catch (InterruptedException e) {}
				}
				Sidebar.chips = 0;
				Sidebar.mult = 0;
				Sidebar.cc.text = "";
				Sidebar.mc.text = "";
				Battle.initSize = 0;
				Battle.checkLoss();
				if (!Battle.lose)
					Sidebar.progress();
				Battle.selected = 0;
				if (Battle.score < Sidebar.objective)
					Battle.playing  = false;
				Panel.screens[1].check(-1, -1);
				Battle.sSuit.color = new Color(200, 150, 0);
				Battle.sRank.color = new Color(200, 150, 0);
			}).start();
			Sidebar.hands--;
			if (Sidebar.hands != 0) {
				new Thread(() -> {
					Sidebar.hd.color = Sidebar.hd.color.darker();
					try {Thread.sleep(250);} catch (InterruptedException e) {}
					Sidebar.hd.color = Sidebar.hd.color.brighter();
					try {Thread.sleep(250);} catch (InterruptedException e) {}
				}).start();
			}
			else
				Sidebar.hd.color = Color.DARK_GRAY;
		}
		else if (job.equals("cash")) {
			Sidebar.money += ((Cash) Panel.screens[2]).money;
			new Thread(() -> {
				Sidebar.md.color = Color.YELLOW;
				try {Thread.sleep(250);} catch (InterruptedException e) {}
				Sidebar.md.color = new Color(80, 80, 80);
			}).start();
			new Thread(() -> {
				Battle.playing = true;
				try {Thread.sleep(500);} catch (InterruptedException e) {}
				Cash.out.text = "";
				Cash.out.amp = 0;
				try {Thread.sleep(10);} catch (InterruptedException e) {}
				while (Cash.out.l < 1800) {
					try {Thread.sleep(1);} catch (InterruptedException e) {}
					Cash.out.x -= 4800/Cash.out.l;
					Cash.out.y -= 4800/Cash.out.l;
					Cash.out.l += 9600/Cash.out.l;
					Cash.out.w += 9600/Cash.out.l;
					try {Thread.sleep(5);} catch (InterruptedException e) {}
					if (Cash.out.w > 1280 && !Sidebar.db) {
						Panel.screens[0].initialize();
						Panel.screens[3] = new Shop();
						Panel.screens[3].initialize();
						Sidebar.db = true;
						Sidebar.hands = Sidebar.maxHands;
						Sidebar.discs = Sidebar.maxDiscs;
						Sidebar.hd.color = new Color(70, 150, 255);
						Sidebar.dd.color = Color.RED;
						Panel.screens[3].check(-1, -1);
					}
				}
				Panel.s = 3;	
				try {Thread.sleep(10);} catch (InterruptedException e) {}
				Shop.alpha = 255;
				Battle.playing = false;
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
			}).start();
		}
		else if (job.equals("info")) {
			Sidebar.id = !Sidebar.id;
			new Thread(() -> {
				Sidebar.info.color = Sidebar.info.color.darker();
				try {Thread.sleep(250);} catch (InterruptedException e) {}
				Sidebar.info.color = Sidebar.info.color.brighter();
				try {Thread.sleep(250);} catch (InterruptedException e) {}
			}).start();
		}
		else if (job.equals("sell")) {
			if (Sidebar.selected == null || Shop.selected != null)
				return;
			Card c = Sidebar.selected;
			c.override = false;
			c.selected = false;
			if (c instanceof Joker)
				c.border = ((Joker) c).edition == ' ' ? c.original.darker() : ((Joker) c).getEdition();
			Sidebar.selected = null;
			new Thread(() -> {
				Battle.playing = true;
				if (c instanceof Joker) {
					((Joker) c).sold();
					Joker.update();
				}
				c.selected = false;
				if (c instanceof Tarot)
					c.temp = new Color(217, 175, 76);
				else if (c instanceof Planet)
					c.temp = new Color(52, 161, 184);
				for (int j = 255; j > 1; j-=2) {
					c.alpha = j;
					try {Thread.sleep(6);} catch (InterruptedException e) {}
				}
				if (c instanceof Joker)
					Sidebar.jokers.remove(c);
				else 
					Sidebar.cons.remove(c);
				Battle.playing = false;
				Battle.sSuit.color = new Color(200, 150, 0);
				Battle.sRank.color = new Color(200, 150, 0);
				Sidebar.addMoney(new Playing(), Color.BLACK, c instanceof Joker ? ((Joker) c).sell : c.cost / 2);
			}).start();
		}
		else if (job.equals("use")) {
			if (Sidebar.selected == null)
				return;
			Card temp = Sidebar.selected == null ? Shop.selected : Sidebar.selected;
			Consumable c = temp instanceof Consumable ? (Consumable) temp : null;
			if (c == null || !c.useable())
				return;
			Sidebar.selected = null;
			new Thread(() -> {
				Battle.playing = true;
				c.selected = false;
				Sidebar.use.text = "Use";
				if (!Sidebar.cons.contains(c)) {
					new Thread(() -> {
						Sidebar.money -= c.cost;
						Sidebar.md.color = Sidebar.md.color.darker();
						try {Thread.sleep(250);} catch (InterruptedException e) {}
						Sidebar.md.color = Sidebar.md.color.brighter();
						try {Thread.sleep(750);} catch (InterruptedException e) {}
					}).start();
				}
				Sidebar.cons.remove(c);
				if (Panel.s == 3 && Shop.selected != null) {
					int index = 0;
					for (int i = 0; i < Shop.slots.length; i++) {
						Card o = Shop.slots[i];
						if (o != null && c == o) {
							index = i;
							break;
						}
					}
					Shop.slots[index] = null;
					Shop.selected = null;
				}
				c.x = 753;
				c.y = 245;
				Sidebar.slot = c;
				if (c instanceof Planet) {
					Planet.using = true;
					Planet p = (Planet) c;
					Sidebar.pd.text = p.poker + " lv. " + Sidebar.levels[Sidebar.getIndex(p.poker)];
					Sidebar.cc.text = Integer.toString(Sidebar.getStats(p.poker)[0]);
					Sidebar.mc.text = Integer.toString(Sidebar.getStats(p.poker)[1]);
					try {Thread.sleep(1000);} catch (InterruptedException e) {}
					c.use();
					p.selected = true;
					new Thread(() -> {
						new Thread(() -> {
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
						}).start();
					}).start();
					Sidebar.cc.text = Integer.toString(Sidebar.getStats(p.poker)[0]);
					Sidebar.cc.color = Sidebar.cc.color.darker();
					try {Thread.sleep(250);} catch (InterruptedException e) {}
					p.selected = false;
					Sidebar.cc.color = Sidebar.cc.color.brighter();
					try {Thread.sleep(250);} catch (InterruptedException e) {}
					p.selected = true;
					new Thread(() -> {
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
					}).start();
					Sidebar.mc.text = Integer.toString(Sidebar.getStats(p.poker)[1]);
					Sidebar.mc.color = Sidebar.mc.color.darker();
					try {Thread.sleep(250);} catch (InterruptedException e) {}
					p.selected = false;
					Sidebar.mc.color = Sidebar.mc.color.brighter();
					try {Thread.sleep(250);} catch (InterruptedException e) {}
					p.selected = true;
					new Thread(() -> {
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
					}).start();
					Sidebar.pd.text = p.poker + " lv. " + Sidebar.levels[Sidebar.getIndex(p.poker)];
					Sidebar.pd.color = Sidebar.pd.color.darker();
					try {Thread.sleep(250);} catch (InterruptedException e) {}
					p.selected = false;
					Sidebar.pd.color = Sidebar.pd.color.brighter();
					try {Thread.sleep(500);} catch (InterruptedException e) {}
					Sidebar.pd.text = "";
					Sidebar.cc.text = "";
					Sidebar.mc.text = "";
					Sidebar.last = new Planet(((Planet) c).name);
					c.temp = new Color(52, 161, 184);
				}
				if (c instanceof Tarot) {
					c.use();
					Sidebar.last = new Tarot(((Tarot) c).name);
					c.temp = new Color(217, 175, 76);
				}
				for (int j = 255; j > 1; j-=2) {
					c.alpha = j;
					try {Thread.sleep(6);} catch (InterruptedException e) {}
				}
				Battle.playing = false;
				Planet.using = false;
				Sidebar.slot = null;
				Battle.sSuit.color = new Color(200, 150, 0);
				Battle.sRank.color = new Color(200, 150, 0);
			}).start();
		}
		else if (job.equals("buy")) {
			if (Shop.selected != null) {
				Card buy = null;
				int index = 0;
				for (int i = 0; i < Shop.slots.length; i++) {
					Card c = Shop.slots[i];
					if (c != null && c.selected) {
						buy = c;
						index = i;
						break;
					}
				}
				if (buy instanceof Joker && Sidebar.jokers.size() >= Sidebar.jslots && ((Joker) buy).edition != 'N')
					return;
				if (buy instanceof Consumable && Sidebar.cons.size() >= Sidebar.cslots)
					return;
				if (buy instanceof Playing && Sidebar.money >= buy.cost) {
					Shop.selected = null;
					Sidebar.selected = null;
					new Thread(() -> {
						try {Thread.sleep(10);} catch (InterruptedException e) {}
						Panel.side.check(-1, -1);
					}).start();
					Shop.slots[index] = null;
					Sidebar.deck.add((Playing) buy);
					Sidebar.money -= buy.cost;
					new Thread(() -> {
						Sidebar.md.color = Sidebar.md.color.darker();
						try {Thread.sleep(250);} catch (InterruptedException e) {}
						Sidebar.md.color = Sidebar.md.color.brighter();
						try {Thread.sleep(250);} catch (InterruptedException e) {}
					}).start();
					Joker.update();
					return;
				}
				if (Sidebar.money >= buy.cost) { 
					Shop.selected = null;
					new Thread(() -> {
						try {Thread.sleep(10);} catch (InterruptedException e) {}
						Panel.side.check(-1, -1);
					}).start();
					Sidebar.selected = buy;
					Shop.slots[index] = null;
					Sidebar.money -= buy.cost;
					new Thread(() -> {
						Sidebar.md.color = Sidebar.md.color.darker();
						try {Thread.sleep(250);} catch (InterruptedException e) {}
						Sidebar.md.color = Sidebar.md.color.brighter();
						try {Thread.sleep(250);} catch (InterruptedException e) {}
					}).start();
					if (buy instanceof Joker) {
						Sidebar.jokers.add((Joker) buy);
						((Joker) buy).bought();
					}
					else
						Sidebar.cons.add((Consumable) buy);
					Joker.update();
				}
			}
		}
		else if (job.equals("reroll")) {
			if (Sidebar.money >= Shop.temproll) {
				if (Sidebar.money >= 2*Shop.temproll + 1) {
					new Thread(() -> {
						Shop.roll.color = new Color(24, 214, 74).darker();
						try {Thread.sleep(250);} catch (InterruptedException e) {}
						Shop.roll.color = new Color(24, 214, 74);
						try {Thread.sleep(250);} catch (InterruptedException e) {}
					}).start();
					new Thread(() -> {
						double oldw = Shop.roll.wobble;
						while (Shop.roll.wobble > -50) {
							Shop.roll.wobble -= 1;
							try {Thread.sleep(1);} catch (InterruptedException e) {};
						}
						while (Shop.roll.wobble < oldw) {
							Shop.roll.wobble += 1;
							try {Thread.sleep(1);} catch (InterruptedException e) {};
						}
					}).start();
				}
				if (Shop.selected != null)
					Sidebar.selected = null;
				Shop.selected = null;
				Panel.screens[3] = new Shop();
				Sidebar.money -= Shop.temproll;
				new Thread(() -> {
					Sidebar.md.color = Sidebar.md.color.darker();
					try {Thread.sleep(250);} catch (InterruptedException e) {}
					Sidebar.md.color = Sidebar.md.color.brighter();
					try {Thread.sleep(250);} catch (InterruptedException e) {}
				}).start();
				if (Shop.temproll == 5 && Joker.contains("Chaos")) {
					new Thread(() -> {
						try {Thread.sleep(500);} catch (InterruptedException e) {}
						Sidebar.addMoney(Joker.get("Chaos").get(0), Color.BLACK, 5);
					}).start();
				}
				Shop.temproll++;
				Shop.roll.text = "Reroll-$" + Shop.temproll;
			}
		}
		else if (job.equals("next")) {
			if (!Battle.playing) { 
				new Thread(() -> {
					Battle.playing = true;
					Shop.next.text = "";
					tracker = true;
					while (Shop.next.l < 2200) {
						try {Thread.sleep(1);} catch (InterruptedException e) {}
						Shop.next.x -= 3200/Shop.next.l;
						Shop.next.y -= 3200/Shop.next.l;
						Shop.next.l += 6400/Shop.next.l;
						Shop.next.w += 6400/Shop.next.l;
						try {Thread.sleep(5);} catch (InterruptedException e) {}
						if (Shop.next.w > 1670 && tracker) {
							tracker = false;
							if (Shop.selected != null) {
								Shop.selected = null;
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
									new Thread(() -> {
										try {Thread.sleep(1500);} catch (InterruptedException e) {}
										c.override = false;
										c.selected = false;
										c.border = ((Joker) c).edition == ' ' ? c.original.darker() : ((Joker) c).getEdition();
										((Joker) c).sold();
										Joker.update();
										dagger.count += 2 * c.sell;
										new Thread(() -> {
											dagger.override = true;
											dagger.selected = true;
											dagger.border = Color.ORANGE;
											new Thread(() -> {
												while (dagger.wobble > -50) {
													dagger.wobble -= 1;
													try {Thread.sleep(1);} catch (InterruptedException e) {};
												}
												while (dagger.wobble < 30) {       // change here
													dagger.wobble += 1;
													try {Thread.sleep(1);} catch (InterruptedException e) {};
												}
												while (dagger.wobble > 0) {        // and here
													dagger.wobble -= 1;
													try {Thread.sleep(1);} catch (InterruptedException e) {};
												}
											}).start();
											try {Thread.sleep(250);} catch (InterruptedException e) {}
											dagger.selected = false;
											dagger.override = false;
											dagger.border = dagger.original;
										}).start();
										for (int j = 255; j > 1; j-=2) {
											c.alpha = j;
											try {Thread.sleep(6);} catch (InterruptedException e) {}
										}
										Sidebar.jokers.remove(c);
									}).start();
								}
								if (Sidebar.jokers.get(i).name.equals("Marble")) {
									Joker marble = Sidebar.jokers.get(i);
									Playing stone = new Playing();
									stone.enhancement = 'T';
									Sidebar.deck.add(stone);
									new Thread(() -> {
										try {Thread.sleep(1500);} catch (InterruptedException e) {}
										marble.override = true;
										marble.selected = true;
										marble.border = Color.BLACK;
										new Thread(() -> {
											while (marble.wobble > -50) {
												marble.wobble -= 1;
												try {Thread.sleep(1);} catch (InterruptedException e) {};
											}
											while (marble.wobble < 30) {       // change here
												marble.wobble += 1;
												try {Thread.sleep(1);} catch (InterruptedException e) {};
											}
											while (marble.wobble > 0) {        // and here
												marble.wobble -= 1;
												try {Thread.sleep(1);} catch (InterruptedException e) {};
											}
										}).start();
										try {Thread.sleep(250);} catch (InterruptedException e) {}
										marble.selected = false;
										marble.override = false;
										marble.border = marble.original;
									}).start();
								}
								if (Sidebar.jokers.get(i).name.equals("Burglar")) {
									Joker burglar = Sidebar.jokers.get(i);
									new Thread(() -> {
										try {Thread.sleep(1500);} catch (InterruptedException e) {}
										burglar.override = true;
										burglar.selected = true;
										burglar.border = Color.BLACK;
										new Thread(() -> {
											while (burglar.wobble > -50) {
												burglar.wobble -= 1;
												try {Thread.sleep(1);} catch (InterruptedException e) {};
											}
											while (burglar.wobble < 30) {       // change here
												burglar.wobble += 1;
												try {Thread.sleep(1);} catch (InterruptedException e) {};
											}
											while (burglar.wobble > 0) {        // and here
												burglar.wobble -= 1;
												try {Thread.sleep(1);} catch (InterruptedException e) {};
											}
										}).start();
										try {Thread.sleep(250);} catch (InterruptedException e) {}
										burglar.selected = false;
										burglar.override = false;
										burglar.border = burglar.original;
									}).start();
									new Thread(() -> {
										try {Thread.sleep(1500);} catch (InterruptedException e) {}
										Sidebar.hands += 3;
										Sidebar.discs = 0;
										Sidebar.hd.color = Sidebar.hd.color.darker();
										Sidebar.dd.color = Color.DARK_GRAY;
										try {Thread.sleep(250);} catch (InterruptedException e) {}
										Sidebar.hd.color = Sidebar.hd.color.brighter();
										try {Thread.sleep(250);} catch (InterruptedException e) {}
									}).start();
								}
							}
							ArrayList<Playing> temp = (ArrayList<Playing>) Sidebar.deck.clone();
							Collections.shuffle(temp);
							for (Playing c : temp) {
								c.selected = false;
								c.override = false;
								Battle.shuffled.offer(c);
							}
							Battle.selected = 0;
						}
					}
					Panel.s = 1;
					Battle.alpha = 255;
					Battle.playing = false;
					Battle.sSuit.color = new Color(200, 150, 0);
					Battle.sRank.color = new Color(200, 150, 0);
					try {Thread.sleep(1000);} catch (InterruptedException e) {}
					for (Joker j : Sidebar.jokers) {
						if (j.name.equals("Dagger")) {
							int index = Sidebar.jokers.indexOf(j);
							if (index + 1 < Sidebar.jokers.size()) {
								
							}
						}
					}
					Battle.createHand(0);
				}).start();
			}
		}
		else if (job.equals("shift")) {
			boolean flag = false;
			for (Joker j : Sidebar.jokers)
				if (j.selected)
					flag = true;
			if (flag) {
				int index = 0;
				for (int i = 0; i < Sidebar.jokers.size(); i++) {
					if (Sidebar.jokers.get(i).selected) {
						index = i;
						break;
					}
				}
				if (index == Sidebar.jokers.size() - 1)
					return;
				else {
					Joker temp = Sidebar.jokers.get(index);
					Sidebar.jokers.set(index, Sidebar.jokers.get(index + 1));
					Sidebar.jokers.set(index + 1, temp);
				}
				new Thread(() -> {
					Sidebar.move.color = Sidebar.move.color.darker();
					try {Thread.sleep(250);} catch (InterruptedException e) {}
					Sidebar.move.color = Sidebar.move.color.brighter();
					try {Thread.sleep(250);} catch (InterruptedException e) {}
				}).start();
			}
		}
	}
	void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform old = g2d.getTransform();
		draw(g2d, amp, color.darker(), color);
		g2d.rotate((double) d(wobble, vib)/300, x + l/2.0, y + w/2.0);
		g2d.setFont(Panel.fonts[size]);
		g2d.setColor(Color.WHITE);
		g2d.drawString(text, x + xOff, y + yOff);
		g2d.setTransform(old);
	}
}