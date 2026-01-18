package game;
import java.awt.*;

abstract class Editionable extends Card {
	char edition;
	Color getEdition() {
		if (override)
			return Color.BLACK;
		if (edition == 'F')
			return new Color(120 + d(32), 120 + d(32), 200 + d(32));
		else if (edition == 'H')
			return new Color(160, 120, 80 + d(80));
		else if (edition == 'P') {
			int current = 10 * (int) (Panel.time + (int) var);
			if (current % 1530 < 255)
				return new Color(255, current%255, 0);
			else if (current % 1530 < 510)
				return new Color(255 - current%255, 255, 0);
			else if (current % 1530 < 765)
				return new Color(0, 255, current%255);
			else if (current % 1530 < 1020)
				return new Color(0, 255 - current%255, 255);
			else if (current % 1530 < 1275)
				return new Color(current%255, 0, 255);
			else
				return new Color(255, 0, 255 - current%255);
		}
		return null;
	}
}