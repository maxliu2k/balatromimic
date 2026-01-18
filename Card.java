import java.awt.*;

abstract class Card extends Interactable {
	boolean selected;
	Color border, original;
	int chips;
	boolean override;
	Color temp;
	boolean breaking;
	int cost;
	int alpha;
	Card() {
		super(100, 140);
		selected = false;
		var = (int) (Math.random() * 1000); 
		chips = 0;
		override = false;
		temp = original;
		breaking = false;
		alpha = 255;
		vib = 14 + 2*Math.random();
		wobble = 0;
	}
	void act() {
		selected = !selected;
		if (selected)
			border = Color.BLACK;
		else
			border = original;
	}
	void setCoords(int x0, int y0) {
		x = x0;
		y = y0;
	}
	void draw(Graphics g) {
		Color back = new Color(255,255,255,alpha);
		border = new Color(border.getRed(),border.getGreen(),border.getBlue(),alpha);
		draw(g, 3, border, back);
	}
	void draw(Graphics g, Color c1, Color c2) {
		c1 = new Color(c1.getRed(),c1.getGreen(),c1.getBlue(),alpha);
		c2 = new Color(c2.getRed(),c2.getGreen(),c2.getBlue(),alpha);
		draw(g, 3, c1, c2);
	}
}

