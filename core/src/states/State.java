package states;

import com.anotherpandarunner.AdControlAndShare;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by HP on 12-02-2018.
 */

public abstract class State extends ApplicationAdapter {

    protected OrthographicCamera cam;
    protected Vector3 mouse;
    protected GameStateManager gsm;
    protected AdControlAndShare adControl;
    protected AssetManager manager;

    protected State(GameStateManager gsm , AdControlAndShare adControl , AssetManager manager) {
        this.gsm = gsm;
        this.adControl = adControl;
        this.manager = manager;
        cam = new OrthographicCamera();
        mouse = new Vector3();
    }

    protected abstract void handleinput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void resume();
    public abstract void dispose();
}
