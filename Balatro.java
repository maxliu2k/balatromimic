import java.awt.*;
import java.awt.image.BufferStrategy;
import javax.swing.*;

class Balatro {
    static JFrame j;
    static Panel m;
    static BufferStrategy bs;
    static final int TARGET_FPS = 60;
    static final int TARGET_UPS = 30;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            j = new JFrame("Balatro");
            j.setUndecorated(true);
            j.setIgnoreRepaint(true);
            j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            GraphicsDevice gd = GraphicsEnvironment
                    .getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice();
            gd.setFullScreenWindow(j);

            m = new Panel();
            j.add(m);
            j.setVisible(true);
            m.requestFocusInWindow();

            j.createBufferStrategy(3);
            bs = j.getBufferStrategy();

            new Thread(Balatro::runGameLoop, "GameLoop").start();
        });
    }

    private static void runGameLoop() {
        final long nsPerUpdate = (long) (1_000_000_000.0 / TARGET_UPS);
        final long nsPerFrame = (long) (1_000_000_000.0 / TARGET_FPS);

        long nextUpdate = System.nanoTime();
        long nextFrame = nextUpdate;

        while (true) {
            long now = System.nanoTime();

            while (now >= nextUpdate) {
                Panel.update();
                nextUpdate += nsPerUpdate;
            }

            if (now >= nextFrame) {
                long lastUpdate = nextUpdate - nsPerUpdate;
                double alpha = (now - lastUpdate) / (double) nsPerUpdate;
                if (alpha < 0) alpha = 0;
                if (alpha > 1) alpha = 1;
                Panel.renderTime = Panel.time + alpha;
                Graphics2D g = (Graphics2D) bs.getDrawGraphics();
                m.render(g);
                g.dispose();
                bs.show();
                Toolkit.getDefaultToolkit().sync();
                nextFrame += nsPerFrame;
            }

            long nextEvent = Math.min(nextUpdate, nextFrame);
            long sleepNs = nextEvent - System.nanoTime();
            if (sleepNs > 0) {
                if (sleepNs > 2_000_000) {
                    try {
                        Thread.sleep((sleepNs / 1_000_000) - 1);
                    } catch (InterruptedException ignored) {}
                }
                while (System.nanoTime() < nextEvent) {
                    Thread.onSpinWait();
                }
            }
        }
    }
}

