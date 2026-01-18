package game;
import java.awt.*;

abstract class Consumable extends Card {
	String name;
	abstract void draw(Graphics g);
	abstract boolean useable();
	abstract void use();
}