package sprites;

import com.anotherpandarunner.AnotherPandaRunner;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Panda {

    public static int gravity = (int)(-5 * AnotherPandaRunner.density);
    public static float movement;
    public Vector3 position;
    public Vector3 velocity;
    private PandaAnimation runanimation , sleepanimation , jumpanimation , fallanimation;
    private Rectangle bounds1,bounds2;
    private Texture runtexture , sleeptexture , jumpfalltexture;
    private AssetManager manager;

    public Panda(int x, int y , AssetManager manager) {
        position = new Vector3(x, y,0);
        velocity = new Vector3(0,0,0);
        this.manager = manager;
        runtexture = new Texture("textures/walk.png" );
        sleeptexture = new Texture("textures/sleep.png");
        jumpfalltexture = new Texture("textures/jumpfall.png");

        runanimation = new PandaAnimation(new TextureRegion(runtexture),0.4f ,true,false,0);
        sleepanimation = new PandaAnimation(new TextureRegion(sleeptexture),0.5f ,true,true,0);
        jumpanimation = new PandaAnimation(new TextureRegion(jumpfalltexture),0.5f,false,false,3);
        fallanimation = new PandaAnimation(new TextureRegion(jumpfalltexture),0.5f,false,false,2);

        bounds1 = new Rectangle(x + AnotherPandaRunner.width/40,y + AnotherPandaRunner.height/50,AnotherPandaRunner.width/14 - AnotherPandaRunner.width/20,3 * AnotherPandaRunner.density);
        bounds2 = new Rectangle(x + AnotherPandaRunner.width/30,y + AnotherPandaRunner.height/50,AnotherPandaRunner.width/14 - AnotherPandaRunner.width/20,AnotherPandaRunner.height/6 - AnotherPandaRunner.height/45);
        movement = 50 * AnotherPandaRunner.density;
    }

    public void update(float dt ,int characterState){

        if(movement < 200 * AnotherPandaRunner.density)
            movement += (4 * AnotherPandaRunner.density/movement);
        switch (characterState){
            case 0 : Sleeping(dt);
                break;
            case 1 : Running(dt);
                break;
            case 2 : Jumping(dt);
                break;
            case 3 : DoubleJump(dt);
                break;
            case 4 : Falling(dt);
                break;
            default: break;
        }

    }

    public void Sleeping(float dt) {
        sleepanimation.update(dt);
    }

    public void Running(float dt) {
        runanimation.update(dt);
        if (position.y<0)
            position.y=0;
        position.add(movement * dt, 0 ,0);
        bounds1.setPosition(position.x + AnotherPandaRunner.width/40,position.y);
        bounds2.setPosition(position.x +AnotherPandaRunner.width/30,position.y);
    }

    public void Jumping(float dt) {
        if(velocity.y > 0)
            jumpanimation.update(dt);
        else if(velocity.y < 0)
            fallanimation.update(dt);
        else
            runanimation.update(dt);
        velocity.add(0,gravity,0);
        velocity.scl(dt);
        position.add(movement * dt,velocity.y,0);
        if (position.y<0)
            position.y=0;
        velocity.scl(1/dt);
        bounds1.setPosition(position.x + AnotherPandaRunner.width/40,position.y);
        bounds2.setPosition(position.x + AnotherPandaRunner.width/30,position.y);
    }

    public void DoubleJump(float dt) {
        if(velocity.y > 0)
            jumpanimation.update(dt);
        else if(velocity.y < 0)
            fallanimation.update(dt);
        else
            runanimation.update(dt);
        velocity.add(0,gravity,0);
        velocity.scl(dt);
        position.add(movement * dt,velocity.y,0);
        if (position.y<0)
            position.y=0;
        velocity.scl(1/dt);
        bounds1.setPosition(position.x + AnotherPandaRunner.width/40,position.y);
        bounds2.setPosition(position.x + AnotherPandaRunner.width/30,position.y);
    }

    public void Falling(float dt) {
        runanimation.update(dt);
        if(position.y>0)
            velocity.add(0,gravity,0);
        velocity.scl(dt);
        position.add(movement * dt,velocity.y,0);

        if(position.y > AnotherPandaRunner.height + 50){
            velocity.add(0,gravity/2,0);
            position.y=250;
        }
        if (position.y<0)
            position.y=0;
        velocity.scl(1/dt);
        bounds1.setPosition(position.x + AnotherPandaRunner.width/40,position.y);
        bounds2.setPosition(position.x + AnotherPandaRunner.width/30,position.y);
    }

    public Rectangle getBounds1(){
        return bounds1;
    }

    public Rectangle getBounds2(){
        return bounds2;
    }

    public boolean collides(Rectangle rect) {
        return rect.overlaps(getBounds1());
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture(int characterState) {
        switch (characterState) {
            case 0:
                return sleepanimation.getframe();
            case 1:
                return runanimation.getframe();
            case 2:
                if (velocity.y > 0)
                    return jumpanimation.getframe();
                else if (velocity.y < 0)
                    return fallanimation.getframe();
            case 3:
                if (velocity.y > 0)
                    return jumpanimation.getframe();
                else if (velocity.y < 0)
                    return fallanimation.getframe();
            case 4:
                return fallanimation.getframe();
            default:
                break;
        }
        return runanimation.getframe();
    }

    public int getframe() {
        return sleepanimation.getFramecount();
    }

    public void dispose(){

    }
}
