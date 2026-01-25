import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

@SuppressWarnings("serial")
class Panel extends JPanel implements KeyListener, MouseListener {
	static int s;
	static Screen[] screens = new Screen[4];
	static Font[] fonts = new Font[201];
	static long time = 0;
	static double renderTime = 0;
	static Sidebar side;
	static final int SCREEN_HOLD_TICKS = 30;
	static boolean transitionActive;
	static int transitionStage;
	static int transitionAlpha;
	static int transitionHold;
	static int transitionTarget;
	static Color transitionColor = Color.BLACK;
	static Runnable transitionInit;
	BufferedImage buffer;
	double renderScale;
    Panel() {
    	s = 0;
    	screens[0] = new Menu();
    	screens[0].initialize();
    	loadFonts(false);
        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);
        setIgnoreRepaint(true);
        renderScale = 2;
    }
    static void loadFonts(boolean secret) {
    	for (int i = 1; i <= 200; i++) {
    		Font customFont = null;
    		String font = "balatro.ttf";
    		if (secret)
    			font = "undertale-wingdings.otf";
    		try {
    			InputStream fontFile = Playing.class.getResourceAsStream("Important/" + font);
                if (secret)
                	customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont((float) i / 2);
                else
                	customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont((float) i * 4 / 3);
            } catch (Exception e) {}

    		fonts[i] = customFont;
    	}
    }
    void render(Graphics2D g2) {
        int internalW = (int) (1280 * renderScale);
        int internalH = (int) (720 * renderScale);

        if (buffer == null || buffer.getWidth() != internalW || buffer.getHeight() != internalH) {
            buffer = new BufferedImage(internalW, internalH, BufferedImage.TYPE_INT_RGB);
        }

        Graphics2D lg = buffer.createGraphics();
        double scaleDown = internalW / 1280.0;
        lg.scale(scaleDown, scaleDown);
        lg.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        lg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        lg.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        lg.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
        lg.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        screens[s].draw(lg);
        if (transitionAlpha > 0) {
            lg.setColor(new Color(transitionColor.getRed(), transitionColor.getGreen(), transitionColor.getBlue(), transitionAlpha));
            lg.fillRect(0, 0, 1280, 720);
        }
        lg.dispose();

        int w = getWidth();
        int h = getHeight();
        double scaleX = w / (double) internalW;
        double scaleY = h / (double) internalH;
        double scale = Math.min(scaleX, scaleY);

        int drawWidth = (int) Math.round(internalW * scale);
        int drawHeight = (int) Math.round(internalH * scale);
        int x = (w - drawWidth) / 2;
        int y = (h - drawHeight) / 2;

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, w, h);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(buffer, x, y, drawWidth, drawHeight, null);
    }
    public void mousePressed(MouseEvent e) {
        handleClick(e);
    }
    public void mouseReleased(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    private void handleClick(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        int w = getWidth();
        int h = getHeight();

        int internalW = (int) (1280 * renderScale);
        int internalH = (int) (720 * renderScale);

        double scaleX = w / (double) internalW;
        double scaleY = h / (double) internalH;
        double scale = Math.min(scaleX, scaleY);

        int drawWidth = (int) (internalW * scale);
        int drawHeight = (int) (internalH * scale);
        int offsetX = (w - drawWidth) / 2;
        int offsetY = (h - drawHeight) / 2;

        double normalizedX = (mouseX - offsetX) / scale;
        double normalizedY = (mouseY - offsetY) / scale;

        int virtualX = (int) (normalizedX / renderScale);
        int virtualY = (int) (normalizedY / renderScale);

        if (virtualX >= 0 && virtualX < 1280 &&
            virtualY >= 0 && virtualY < 720) {
            screens[s].check(virtualX, virtualY);
        }
    }
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void keyReleased(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {   
    }
    public void keyTyped(KeyEvent e) {}

    static void update() {
        time++;
        if (!transitionActive)
            return;
        if (transitionStage == 1) {
            transitionAlpha = Math.min(255, transitionAlpha + 15);
            if (transitionAlpha == 255) {
                transitionStage = 2;
                transitionHold = 0;
                if (transitionInit != null)
                    transitionInit.run();
                s = transitionTarget;
            }
        }
        else if (transitionStage == 2) {
            transitionHold++;
            if (transitionHold >= SCREEN_HOLD_TICKS)
                transitionStage = 3;
        }
        else if (transitionStage == 3) {
            transitionAlpha = Math.max(0, transitionAlpha - 15);
            if (transitionAlpha == 0)
                transitionActive = false;
        }
    }

    static void startTransition(int target, Color color, Runnable init) {
        transitionActive = true;
        transitionStage = 1;
        transitionAlpha = 0;
        transitionHold = 0;
        transitionTarget = target;
        transitionColor = color;
        transitionInit = init;
    }

    static boolean isTransitioning() {
        return transitionActive;
    }
}

