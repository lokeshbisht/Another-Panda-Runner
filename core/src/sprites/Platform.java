package sprites;

import com.anotherpandarunner.AnotherPandaRunner;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by HP on 12-02-2018.
 */

public class Platform {

    private Texture platform;
    public Vector2 pos;
    private static final int fluctx = (int)(50 * AnotherPandaRunner.density) , flucty = (int)(60 * AnotherPandaRunner.density);
    private static final int gapx = (int)AnotherPandaRunner.width/8 + (int)Panda.movement/5 , heighty = (int)AnotherPandaRunner.height/6;
    private Rectangle bounds;
    public int number,amount,check = 100;
    private static final int minx = (int)(AnotherPandaRunner.width/8) , maxy = (int)(AnotherPandaRunner.height/6) , maxx = (int)AnotherPandaRunner.width/4;


    public Platform(AssetManager manager) {
        pos = new Vector2();
        platform = manager.get("textures/platform.png" , Texture.class);
    }

    public void make(float x , float y){
        amount();
        bounds = new Rectangle();
        bounds.setSize(AnotherPandaRunner.width/10,3 * AnotherPandaRunner.density);
        reposition(x , y);
    }

    public void reposition(float x,float y){
        float tempy,tempx;

        do {
            tempx = x + AnotherPandaRunner.rand.nextInt(fluctx) + gapx;
            tempy = AnotherPandaRunner.rand.nextInt(flucty) + heighty;
        }while (Math.abs(y-tempy) > maxy || Math.abs(x-tempx) < minx || Math.abs(x-tempx) > maxx|| tempy > (int)AnotherPandaRunner.height - ((int)AnotherPandaRunner.height/4 + 10 * AnotherPandaRunner.density));
        pos.set(tempx,tempy);
        amount();
        bounds.setPosition(pos.x,pos.y + 9 * AnotherPandaRunner.density);
    }

    public void setBounds(float x, float y) {
        this.bounds.setPosition(x,y);
    }

    public void amount(){
        AnotherPandaRunner.rand = new Random();
        number = AnotherPandaRunner.rand.nextInt(check);

        if (Panda.movement < 100 * AnotherPandaRunner.density) {
            if (number > 0 && number < 25)
                amount = 0;
            else if (number > 25 && number < 50)
                amount = 1;
            else if (number > 50 && number < 70)
                amount = 2;
            else if (number > 70 && number < 80)
                amount = 3;
            else if (number > 80 && number < 90)
                amount = 4;
            else if (number > 90)
                amount = 5;
        }

        else  {
            if (number > 0 && number < 20)
                amount = 0;
            else if (number > 20 && number < 30)
                amount = 1;
            else if (number > 30 && number < 45)
                amount = 2;
            else if (number > 45 && number < 60)
                amount = 3;
            else if (number > 60 && number < 75)
                amount = 4;
            else if (number > 75 && number < 90 )
                amount = 5;
            else if (number > 90)
                amount = 6;
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Texture getPlatform() {
        return platform;
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(float x ,float y) {
        this.pos.x = x;
        this.pos.y = y;

    }

    public void dispose(){
    }
}
