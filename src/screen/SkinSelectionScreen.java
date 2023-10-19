package screen;

import engine.Cooldown;
import engine.Core;
import java.awt.event.KeyEvent;

public class SkinSelectionScreen extends Screen{
    private static final int SELECTION_TIME = 100;
    private Cooldown selectionCooldown;
    private int skincode_1p=0;
    private int skincode_2p=0;
    public SkinSelectionScreen(final int width, final int height, final int fps) {

        super(width, height, fps);

        this.returnCode = 1;
        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();

    }
    public final int run() {
        super.run();
        return this.returnCode;
    }
    protected final void update() {
        super.update();
        draw();
        if (this.selectionCooldown.checkFinished() && this.inputDelay.checkFinished()) {
            if (inputManager.isKeyDown(KeyEvent.VK_UP) && skincode_1p> 0) {
                skincode_1p--;
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_DOWN) && skincode_1p < 5) {
                skincode_1p++;
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_W) && skincode_2p > 0) {
                skincode_2p--;
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_S) && skincode_2p < 5) {
                skincode_2p++;
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
                this.isRunning = false;
            }
        }
    }

    public void draw(){
        drawManager.initDrawing(this);
        drawManager.drawSkinSelectionMenu(this, skincode_1p,skincode_2p);
        drawManager.completeDrawing(this);
    }
}
