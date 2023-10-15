package screen;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import engine.Core;
import engine.Score;


public class AchievementScreen extends Screen {
    /**
	 * Constructor, establishes the properties of the screen.
	 * 
	 * @param width
	 *            Screen width.
	 * @param height
	 *            Screen height.
	 * @param fps
	 *            Frames per second, frame rate at which the game is run.
	 */

	private List<Score> Scores;

	public AchievementScreen(final int width, final int height, final int fps) {
		super(width, height, fps);
        this.returnCode = 1;
		try {
			this.Scores = Core.getFileManager().loadScores();
		} catch (NumberFormatException | IOException e) {
			logger.warning("Couldn't load scores!");
		}
	}
    public final int run() {
        super.run();
        return this.returnCode;
    }

	protected final void update() {
		super.update();
		draw();
		if (inputManager.isKeyDown(KeyEvent.VK_ESCAPE)
				&& this.inputDelay.checkFinished())
			this.isRunning = false;
	}

	private void draw() {
		drawManager.initDrawing(this);

		drawManager.AchievementScreenDrawing(this);
		drawManager.drawScores(this,Scores);
		drawManager.completeDrawing(this);
	}
}
