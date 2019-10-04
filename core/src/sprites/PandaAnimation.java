package sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by HP on 12-02-2018.
 */

public class PandaAnimation {

    private Array<TextureRegion> frames;
    private float maxframetime;
    private float currentframetime;
    private int framecount = 7;
    private int frame;

    public PandaAnimation(TextureRegion region,float cycletime , boolean isanimation , boolean isreversed , int framenum) {
        frames = new Array<TextureRegion>();
        int frameheight = region.getRegionHeight() / framecount;
        if (isanimation == true) {
            framecount = 7;
            if(isreversed == true) {
                for (int i = framecount - 1; i >= 0; i--)
                    frames.add(new TextureRegion(region, 0, i * frameheight, region.getRegionWidth(), frameheight));
            }
            else if(isreversed == false) {
                for (int i = 0; i < framecount; i++)
                    frames.add(new TextureRegion(region, 0, i * frameheight, region.getRegionWidth(), frameheight));
            }
        }
        else if(isanimation == false) {
            framecount = 1;
            frames.add(new TextureRegion(region , 0 , (framenum - 1) * frameheight,region.getRegionWidth(),frameheight));
        }
        maxframetime = cycletime/framecount;
        frame = 0;
    }

    public void update(float dt) {
        currentframetime += dt;
        if(currentframetime > maxframetime) {
            frame++;
            currentframetime = 0;
        }
        if(frame >= framecount)
            frame = 0;
    }

    public int getFramecount() {
        return frame;
    }

    public TextureRegion getframe() {
        return frames.get(frame);
    }
}
