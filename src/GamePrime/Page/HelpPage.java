package GamePrime.Page;

import java.awt.event.KeyEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.image.BufferedImage;
import EnginePrime.FileManager;
import EnginePrime.GManager;
import EnginePrime.GameManager;
import EnginePrime.SoundManager;
import GamePrime.Define.KeyDefine;

public class HelpPage implements GManager {
    SoundManager.PlayProp menuSoundProp;
    GameManager gm = GameManager.getInstance();
    JSONObject res = gm.GlobalData.get("Resource");
    Graphics graphic = gm.Rm.GetCurrentGraphic();
    FontMetrics matrix = gm.Rm.SetFont("Regular");
    private int itemCode;
    private boolean itemSelected;
    private Map<String, BufferedImage> img = new HashMap<>();
    String imgString[] = { "PlayScreen", "HardMode", "RemaningMagazine" };

    public void LateRender() {
    };

    public void Initialize() {
        itemCode = 0;
        itemSelected = false;
        JSONObject GuideImplements = (JSONObject) res.get("GuideImplements");
        JSONObject Entity = (JSONObject) res.get("Entity");
        FileManager fm = new FileManager();
        img.put("PlayScreen", fm.GetImage("Img" + File.separator + GuideImplements.get("PlayScreen")));
        img.put("HardMode", fm.GetImage("Img" + File.separator + GuideImplements.get("HardMode")));
        img.put("RemaningMagazine", fm.GetImage("Img" + File.separator + GuideImplements.get("RemaningMagazine")));
        img.put("Item", fm.GetImage("Img" + File.separator + Entity.get("Item")));
        JSONObject BGM = (JSONObject) res.get("BGM");
        JSONObject SFX = (JSONObject) res.get("SFX");
        menuSoundProp = gm.Sm.new PlayProp(
                "Sound" + File.separator + "SFX" + File.separator + (String) SFX.get("MenuSelect"), null);
        SoundManager.PlayProp BgmProp = gm.Sm.new PlayProp(
                "Sound" + File.separator + "BGM" + File.separator + (String) BGM.get("AchievePage"), "BGM");
        BgmProp.count = -1;
        gm.Sm.StopClip("BGM", 1);
        gm.Sm.playSound(BgmProp);
    };

    public void PreUpdate() {
        if (itemSelected) {
            switch (itemCode) {
            case 0:
                drawGameGuide();
                if (gm.Im.isKeyDown(KeyEvent.VK_ESCAPE)) {
                    itemSelected = false;
                    gm.Sm.playSound(menuSoundProp);
                }
                break;
            case 1:
                drawHowToPlay();
                if (gm.Im.isKeyDown(KeyEvent.VK_ESCAPE)) {
                    itemSelected = false;
                    gm.Sm.playSound(menuSoundProp);
                }
                break;
            case 2:
                drawItem();
                if (gm.Im.isKeyDown(KeyEvent.VK_ESCAPE)) {
                    itemSelected = false;
                    gm.Sm.playSound(menuSoundProp);
                }
                break;
            case 3:
                drawHardMode();
                if (gm.Im.isKeyDown(KeyEvent.VK_ESCAPE)) {
                    itemSelected = false;
                    gm.Sm.playSound(menuSoundProp);
                }
                break;
            default:
                break;
            }
        } else {
            if (gm.Im.isKeyDown(KeyEvent.VK_DOWN)) {
                itemCode = itemCode == 3 ? 0 : itemCode + 1;
            }
            if (gm.Im.isKeyDown(KeyEvent.VK_UP)) {
                itemCode = itemCode == 0 ? 3 : itemCode - 1;
            }
            if (gm.Im.isKeyDown(KeyEvent.VK_SPACE)) {
                itemSelected = true;
                gm.Sm.playSound(menuSoundProp);
            }
            if (gm.Im.isKeyDown(KeyEvent.VK_ESCAPE)) {
                gm.Sm.playSound(menuSoundProp);
                gm.SetInstance(new MenuPage());
            }
        }
    };

    public void LateUpdate() {
    };

    public void PreRender() {
        Draw();
    }

    public void Exit() {
    };

    private void Draw() {
        drawHelp();
    }

    public final void drawHelp() {
        String helpString = "Help";
        String instructionsString1 = "Move with UP, DOWN / Select with SPACE";
        String instructionsString2 = "Press ESC to return";
        Graphics graphic = gm.Rm.GetCurrentGraphic();
        FontMetrics matrix = gm.Rm.SetFont("Big");
        graphic.setColor(Color.GREEN);
        graphic.drawString(helpString, gm.frame.getWidth() / 2 - matrix.stringWidth(helpString) / 2,
                gm.frame.getHeight() / 8);
        matrix = gm.Rm.SetFont("Regular");
        graphic.setColor(Color.GRAY);
        graphic.drawString(instructionsString1, gm.frame.getWidth() / 2 - matrix.stringWidth(instructionsString1) / 2,
                gm.frame.getHeight() / 5 - matrix.getHeight() / 2);
        graphic.drawString(instructionsString2, gm.frame.getWidth() / 2 - matrix.stringWidth(instructionsString2) / 2,
                gm.frame.getHeight() / 5 + matrix.getHeight() / 2);
        String[] guideList = { "Space Invaders", "How to play", "Item", "Mode" };
        for (int i = 0; i < 4; i++) {
            if (itemCode == i) {
                graphic.setColor(Color.GREEN);
                if (itemSelected) {
                    graphic.drawString("*", gm.frame.getWidth() / 5 - matrix.stringWidth(guideList[i]) / 2 - 16,
                            gm.frame.getHeight() / 4 + matrix.getHeight() * 2 * (i + 1));
                }
            } else {
                graphic.setColor(Color.WHITE);
            }
            graphic.drawString(guideList[i], gm.frame.getWidth() / 5 - matrix.stringWidth(guideList[i]) / 2,
                    gm.frame.getHeight() / 4 + matrix.getHeight() * 2 * (i + 1));
        }
        graphic.setColor(Color.GREEN);
        graphic.drawLine(gm.frame.getWidth() / 5 * 2 - 60, gm.frame.getHeight() / 4, gm.frame.getWidth() / 5 * 2 - 60,
                gm.frame.getHeight() / 10 * 9);
        graphic.drawLine(gm.frame.getWidth() / 5 * 2 - 59, gm.frame.getHeight() / 4, gm.frame.getWidth() / 5 * 2 - 59,
                gm.frame.getHeight() / 10 * 9);
    }

    public final void drawGameGuide() {
        Graphics graphic = gm.Rm.GetCurrentGraphic();
        BufferedImage curimg = img.get(imgString[0]);
        final String[] story = { "Space Invaders is a vertical scroll shooting game.",
                "Currently, terrifying enemies from another demention",
                "have invaded space. Defeat them and make a name for yourself." };
        graphic.drawImage(curimg, gm.frame.getWidth() / 2 - 80, gm.frame.getHeight() / 2 - 200,
                curimg.getWidth() / 2 + 80, curimg.getHeight() / 2 + 80, null);
        FontMetrics matrix = gm.Rm.SetFont("Small");
        graphic.setColor(Color.WHITE);
        for (int i = 0; i < story.length; i++) {
            graphic.drawString(story[i], gm.frame.getWidth() / 2 - 125, gm.frame.getHeight() / 2 + 200 + 30 * (i + 1));
        }
    }

    public final void drawHowToPlay() {
        Graphics graphic = gm.Rm.GetCurrentGraphic();
        final String[] manualInfo = { "The above manual shows how to control your character.",
                "You can view the  manual again by pressing Ctrl in the game.",
                "You can also change key setting on the 'setting' menu." };
        final String reloadInfo = "Reload can use in the hard mode only!";
        Graphics graphics = gm.Rm.GetCurrentGraphic();
        FontMetrics fontmatrix = gm.Rm.SetFont("Regular");
        JSONArray key1 = (JSONArray) gm.GlobalData.get("Setting").get("KeySetting_1p");
        JSONArray key2 = (JSONArray) gm.GlobalData.get("Setting").get("KeySetting_2p");
        graphics.drawString("Play manual", gm.frame.getWidth() / 2 - fontmatrix.stringWidth("Play manual") / 2 + 130,
                gm.frame.getHeight() / 2 - 105);
        graphics.drawString("Player1", gm.frame.getWidth() / 2 - 30, gm.frame.getHeight() / 2 - 60);
        graphics.drawString("Player2", gm.frame.getWidth() / 2 + 185, gm.frame.getHeight() / 2 - 60);
        graphics.setColor(Color.WHITE);
        int y = gm.frame.getHeight() / 2 - 30;
        int x1 = gm.frame.getWidth() / 2 - 30; // player1_manual
        int x2 = gm.frame.getWidth() / 2 + 70; // player1_setting
        int x3 = gm.frame.getWidth() / 2 + 170; // player2
        int x4 = gm.frame.getWidth() / 2 + 270; // player2_setting
        for (int i = 0; i < KeyDefine.KeyFunc.length; i++) {
            graphics.drawString(KeyDefine.KeyFunc[i], x1 - fontmatrix.stringWidth(KeyDefine.KeyFunc[i]) / 2,
                    y + 20 * i);
            String key = KeyEvent.getKeyText(((Number) key1.get(i)).intValue());
            graphics.drawString(key, x2 - fontmatrix.stringWidth(key) / 2, y + 20 * i);
            graphics.drawString(KeyDefine.KeyFunc[i], x3 - fontmatrix.stringWidth(KeyDefine.KeyFunc[i]) / 2,
                    y + 20 * i);
            key = KeyEvent.getKeyText(((Number) key2.get(i)).intValue());
            graphics.drawString(key, x4 - fontmatrix.stringWidth(key) / 2, y + 20 * i);
        }
        FontMetrics matrix = gm.Rm.SetFont("Small");
        graphic.setColor(Color.GRAY);
        graphic.drawString(reloadInfo, gm.frame.getWidth() / 2 - 35, gm.frame.getHeight() / 2 + 100);
        graphic.setColor(Color.WHITE);
        for (int i = 0; i < manualInfo.length; i++) {
            graphic.drawString(manualInfo[i], gm.frame.getWidth() / 2 - 125,
                    gm.frame.getHeight() / 2 + 200 + 30 * (i + 1));
        }
    }

    public final void drawItem() {
        Graphics graphic = gm.Rm.GetCurrentGraphic();
        BufferedImage itemBox = img.get("Item");
        final String[] itemBoxInfo = { "When killing a enemy, item box is dropped ",
                "with a certain probability. if you obtain a item box,", "you can use one of four items randomly." };
        final String[] itemInfo = { "Ghost: you become a ghost and dodge enemies' attacks.",
                "Auxiliary: you can shot auxiliary bullets.", "Bomb: your next shot becomes ranged attack.",
                "Speed Up: your speed increases." };
        graphic.drawImage(itemBox, gm.frame.getWidth() / 2 - 120, gm.frame.getHeight() / 2 - 170,
                itemBox.getWidth() / 32, itemBox.getHeight() / 32, null);
        graphic.setColor(Color.CYAN); // Ghost
        graphic.drawRect(gm.frame.getWidth() / 2 - 110, gm.frame.getHeight() - 500, 5, 5);
        graphic.setColor(Color.green); // Auxiliary
        graphic.drawRect(gm.frame.getWidth() / 2 - 110, gm.frame.getHeight() - 450, 5, 5);
        graphic.setColor(Color.red); // Bomb
        graphic.drawRect(gm.frame.getWidth() / 2 - 110, gm.frame.getHeight() - 400, 5, 5);
        graphic.setColor(Color.YELLOW); // SpeedUp
        graphic.drawRect(gm.frame.getWidth() / 2 - 110, gm.frame.getHeight() - 350, 5, 5);
        FontMetrics matrix = gm.Rm.SetFont("Small");
        graphic.setColor(Color.WHITE);
        for (int i = 0; i < itemBoxInfo.length; i++) {
            graphic.drawString(itemBoxInfo[i], gm.frame.getWidth() / 2 - 80, gm.frame.getHeight() - 680 + 30 * (i + 1));
        }
        for (int i = 0; i < itemInfo.length; i++) {
            graphic.drawString(itemInfo[i], gm.frame.getWidth() / 2 - 80, gm.frame.getHeight() - 490 + 50 * i);
        }
    }

    public final void drawHardMode() {
        Graphics graphic = gm.Rm.GetCurrentGraphic();
        BufferedImage hardModeImg = img.get(imgString[1]);
        BufferedImage megazineImg = img.get(imgString[2]);
        final String[] hardModeInfo = { "In the hard mode, reloding system exists.", "So you must shot carefully.",
                "If you don't have any magazines,", "you can't clear current stage." };
        graphic.drawImage(hardModeImg, gm.frame.getWidth() / 2 - 80, gm.frame.getHeight() / 2 - 200,
                hardModeImg.getWidth() / 2, hardModeImg.getHeight() / 2, null);
        graphic.drawImage(megazineImg, gm.frame.getWidth() / 2 - 90, gm.frame.getHeight() / 2 - 100,
                megazineImg.getWidth() + 50, megazineImg.getHeight() + 50, null);
        FontMetrics matrix = gm.Rm.SetFont("Small");
        graphic.setColor(Color.WHITE);
        for (int i = 0; i < hardModeInfo.length; i++) {
            graphic.drawString(hardModeInfo[i], gm.frame.getWidth() / 2 - 105, gm.frame.getHeight() / 2 + 40 * (i + 1));
        }
    }
}
