import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Animator {
	private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool(r -> {
		Thread t = new Thread(r, "Animator");
		t.setDaemon(true);
		return t;
	});

	static void run(Runnable task) {
		EXECUTOR.execute(task);
	}
}


