package game;
import java.awt.*;
import javax.swing.*; 
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

@SuppressWarnings("serial")
class Panel extends JPanel implements ActionListener, KeyListener, MouseListener {
	static Timer timer;
	static int s;
	static Screen[] screens = new Screen[4];
	static Font[] fonts = new Font[201];
	static long time = 0;
	static Sidebar side;
	BufferedImage buffer;
	double renderScale;
    Panel() {
    	//one frame every 50 milliseconds
    	timer = new Timer(30, this);
    	timer.start();
    	s = 0;
    	//initialize menu
    	screens[0] = new Menu();
    	screens[0].initialize();
    	loadFonts(false);
        addMouseListener(this);  
        addKeyListener(this);  
        setFocusable(true);
        setIgnoreRepaint(true); // We’ll handle repaint manually
        renderScale = 1;
    }
    static void loadFonts(boolean secret) {
    	//if secret then activate wingdings
    	for (int i = 1; i <= 200; i++) {
    		Font customFont = null; 
    		String font = "balatro.ttf";
    		if (secret)
    			font = "undertale-wingdings.otf"; 
    		try {
    			InputStream fontFile = Playing.class.getResourceAsStream("Important\\" + font);
                if (secret)
                	customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont((float) i / 2);
                else
                	customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont((float) i * 4 / 3);
            } catch (Exception e) {}
    		
    		fonts[i] = customFont;
    	}
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // --- Configurable internal render scale ---
        // 1.0 = full 1280x720 (no change)
        // 0.5 = 640x360 (half resolution)
        // 0.25 = 320x180 (quarter resolution, super fast)

        int internalW = (int) (1280 * renderScale);
        int internalH = (int) (720 * renderScale);

        // --- Create a low-res offscreen buffer ---
        BufferedImage lowRes = new BufferedImage(internalW, internalH, BufferedImage.TYPE_INT_RGB);
        Graphics2D lg = lowRes.createGraphics();

        // Match game drawing scale (so your existing draw code stays 1280x720)
        double scaleDown = internalW / 1280.0;
        lg.scale(scaleDown, scaleDown);

        // Use low-cost rendering hints for speed
        // You can still apply some lightweight rendering hints
        lg.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        lg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        lg.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); // Images fast
        lg.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
        lg.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);

        // Draw your game scene normally (no other code changes!)
        screens[s].draw(lg);
        lg.dispose();

        // --- Scale that buffer up to fill the window ---
        int w = getWidth();
        int h = getHeight();
        double scaleX = w / (double) internalW;
        double scaleY = h / (double) internalH;
        double scale = Math.min(scaleX, scaleY);

        int drawWidth = (int) (internalW * scale);
        int drawHeight = (int) (internalH * scale);
        int x = (w - drawWidth) / 2;
        int y = (h - drawHeight) / 2;

        // Use nearest-neighbor to preserve the crisp pixel look
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(lowRes, x, y, drawWidth, drawHeight, null);
    }
    public void mousePressed(MouseEvent e) {}  
    public void mouseReleased(MouseEvent e) {}  
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        int w = getWidth();
        int h = getHeight();

        // Same render scale as used in paintComponent
        double renderScale = 0.8; // make sure this matches your paintComponent value
        int internalW = (int) (1280 * renderScale);
        int internalH = (int) (720 * renderScale);

        // Compute scale used to draw the scaled-up image to the window
        double scaleX = w / (double) internalW;
        double scaleY = h / (double) internalH;
        double scale = Math.min(scaleX, scaleY);

        // Compute offset to center image
        int drawWidth = (int) (internalW * scale);
        int drawHeight = (int) (internalH * scale);
        int offsetX = (w - drawWidth) / 2;
        int offsetY = (h - drawHeight) / 2;

        // Convert mouse coords into the 1280x720 virtual game space
        double normalizedX = (mouseX - offsetX) / scale;
        double normalizedY = (mouseY - offsetY) / scale;

        // Scale up to match original 1280x720 coordinate space
        int virtualX = (int) (normalizedX / renderScale);
        int virtualY = (int) (normalizedY / renderScale);

        // Register only if inside the game area
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
	public void actionPerformed(ActionEvent e) {
		time++;
	}
}