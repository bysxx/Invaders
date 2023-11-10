package GamePrime.Page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONObject;

import EnginePrime.FileManager;
import EnginePrime.GManager;
import EnginePrime.GameManager;
import GamePrime.Score;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.Color;

import java.awt.FontMetrics;

public class HightScorePage implements GManager {

    public static final int MaxNum = 10;
    public void PreRender(){};
    
    public void LateRender(){};
    GameManager gm = GameManager.getInstance();

    List<Score> scoreList[] = new List[] { new ArrayList<>(), new ArrayList<>() };

    public void Initialize() {

        FileManager fm = new FileManager();
        JSONObject database = fm.LoadJsonObject("DataBase");
        JSONObject scores1 = (JSONObject) ((JSONObject) database.get("Scores")).get("Scores_1p");
        JSONObject scores2 = (JSONObject) ((JSONObject) database.get("Scores")).get("Scores_2p");

        for (Iterator iterator = scores1.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            scoreList[0].add(new Score(key, ((Number) scores1.get(key)).intValue()));
        }
        Collections.sort(scoreList[0]);

        for (Iterator iterator = scores2.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            scoreList[1].add(new Score(key, ((Number) scores2.get(key)).intValue()));
        }
        Collections.sort(scoreList[1]);
    };

    public void PreUpdate() {
        Draw();

    };

    public void LateUpdate() {
        if (gm.Im.isKeyDown(KeyEvent.VK_ESCAPE)) {
            gm.SetInstance(new MenuPage());
        }

    };

    private void Draw() {

        String highScoreString = "High Scores";
        String instructionsString = "Press Space to return";
        String gameMode_1 = "1P_Mode";
        String gameMode_2 = "2P_Mode";

        Graphics grpahics = gm.Rm.GetCurrentGraphic();
        FontMetrics fontmatrix = gm.Rm.SetFont("Big");
        grpahics.setColor(Color.GREEN);
        grpahics.drawString(highScoreString, gm.frame.getWidth() / 2
                - fontmatrix.stringWidth(highScoreString) / 2, gm.frame.getHeight() / 8);

        fontmatrix = gm.Rm.SetFont("Regular");

        grpahics.setColor(Color.GRAY);
        grpahics.drawString(instructionsString, gm.frame.getWidth() / 2
                - fontmatrix.stringWidth(instructionsString) / 2, gm.frame.getHeight() / 5);

        grpahics.drawString(gameMode_1, gm.frame.getWidth() * 3 / 13
                - fontmatrix.stringWidth(gameMode_1) / 2, gm.frame.getHeight() * 4 / 15);

        grpahics.drawString(gameMode_2, gm.frame.getWidth() * 10 / 13
                - fontmatrix.stringWidth(gameMode_2) / 2, gm.frame.getHeight() * 4 / 15);

        grpahics.setColor(Color.WHITE);


        String scoreString = "";

        for (int i = 0; i < scoreList.length; i++) {
            for (int j = 0; j < scoreList[i].size(); j++) {
                Score score =  scoreList[i].get(j);
                scoreString = String.format("%s        %04d", score.name,
                        score.value);
                grpahics.drawString(scoreString, gm.frame.getWidth() * (3+i*7) / 13
                        - fontmatrix.stringWidth(scoreString) / 2,
                        gm.frame.getHeight() / 4 + fontmatrix.getHeight() * (j + 1) * 2);
            }
        }
    }
}
