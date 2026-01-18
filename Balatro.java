package game;
import java.awt.*;
import java.awt.image.BufferStrategy;
import javax.swing.*;

class Balatro {
    static JFrame j;
    static Panel m;
    static BufferStrategy bs;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create the frame
            j = new JFrame("Balatro");
            j.setUndecorated(true);
            j.setIgnoreRepaint(true);
            j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Set fullscreen *before* adding anything
            GraphicsDevice gd = GraphicsEnvironment
                    .getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice();
            gd.setFullScreenWindow(j);

            // Add the main game panel
            m = new Panel();
            j.add(m);
            j.setVisible(true);

            // Important: create the buffer AFTER fullscreen is applied
            j.createBufferStrategy(3);
            bs = j.getBufferStrategy();

            // Start the main game loop
            new Thread(Balatro::runGameLoop, "GameLoop").start();
        });
    }

    private static void runGameLoop() {
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1_000_000_000 / TARGET_FPS;

        while (true) {
            long startTime = System.nanoTime();

            Graphics2D g = (Graphics2D) bs.getDrawGraphics();
            m.paint(g);
            g.dispose();
            bs.show();
            Toolkit.getDefaultToolkit().sync();

            long elapsed = System.nanoTime() - startTime;
            long sleepTime = (OPTIMAL_TIME - elapsed) / 1_000_000;
            if (sleepTime > 0) {
                try { Thread.sleep(sleepTime); } catch (InterruptedException ignored) {}
            }
        }
    }
}