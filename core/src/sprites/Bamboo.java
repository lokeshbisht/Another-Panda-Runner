package sprites;

import com.anotherpandarunner.AnotherPandaRunner;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by HP on 12-02-2018.
 */

public class Bamboo {

    private static Texture bamboo;
    private Rectangle bounds;
    public Boolean taken;
    public Vector2 pos;


    public Bamboo(AssetManager manager) {
        bamboo = manager.get("textures/bamboo.png" , Texture.class);
        bounds = new Rectangle();
        bounds.setSize(AnotherPandaRunner.width/80 ,20 * AnotherPandaRunner.density);
        pos = new Vector2();
        taken = false;
    }


    public void setBounds(float x ,float y) {
        bounds.setPosition(x,y);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public static Texture getTexture() {
        return bamboo;
    }

    public Vector2 getPos() {
        return pos;
    }

    public boolean collides(Rectangle rect) {
        return rect.overlaps(getBounds());
    }

    public void setPos(float x , float y) {
        pos.x = x;
        pos.y = y;
    }

    public void dispose(){
        bamboo.dispose();
    }
}

