package GamePrime.Ship;

import java.awt.event.KeyEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.awt.Graphics;
import EnginePrime.Component;
import EnginePrime.GameManager;
import EnginePrime.Message;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import EnginePrime.FileManager;
import EnginePrime.GManager;
import EnginePrime.GameManager;
import EnginePrime.SoundManager;
import GamePrime.Define.KeyDefine;
import GamePrime.ETC.Image;
import GamePrime.Page.GamePage;
import java.awt.geom.Point2D;

public class Item extends Component {
    public final int DropSpeed = 400;
    GameManager gm = GameManager.getInstance();
    JSONObject res = gm.GlobalData.get("Resource");
    GamePage gp;
    public Point2D pos;
    public int size = 30;
    public int itemIndex;

    public void SetVector(Message m) {
        pos = (Point2D) m.obj.get("pos");
        itemIndex = ((Number) m.obj.get("Item")).intValue();
    }

    public void Awake() {
        this.CustomEvent.put("SetVector", this::SetVector);
        gp = (GamePage) gm.CustomInstance;
    }

    public void Start() {
    }

    public void Update() {
        if (((Number) gp.PlayData.get("ScreenIndex")).intValue() != 0) {
            return;
        }
        pos = new Point2D.Double(pos.getX(), pos.getY() + DropSpeed * gm.Et.GetElapsedSeconds());
    }

    public void Render() {
        Image img = gp.ImgRes.get("Item");
        img.RenderFixedHeight((int) Math.round(pos.getX()), (int) Math.round(pos.getY()), size);
    }
}
