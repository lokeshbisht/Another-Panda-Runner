package states;

import com.anotherpandarunner.AdControlAndShare;
import com.anotherpandarunner.AnotherPandaRunner;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

/**
 * Created by HP on 12-02-2018.
 */

public class LaunchState extends State {
    
    private Texture bcg1;
    private Music backgroundmusic;
    private Vector2 str1pos , str2pos , str3pos;
    private BitmapFont bitmapFont;
    private Label str1 , str2 , str3;
    private Label.LabelStyle labelStyle;
    private float delay;

    public LaunchState(GameStateManager gsm , AdControlAndShare adControl , AssetManager manager) {
        super(gsm , adControl , manager);

        delay = 0;

        cam.setToOrtho(false , Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        cam.viewportWidth = Gdx.graphics.getWidth()/2;
        cam.viewportHeight = Gdx.graphics.getHeight();
        AnotherPandaRunner.density = Gdx.graphics.getDensity();
        AnotherPandaRunner.width = Gdx.graphics.getWidth();
        AnotherPandaRunner.height = Gdx.graphics.getHeight();
        bitmapFont = new BitmapFont(Gdx.files.internal("font-button-export.fnt"));
        bitmapFont.getData().setScale(0.5f * AnotherPandaRunner.density);

        labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont;
        labelStyle.fontColor = Color.BLUE;

        str1pos = new Vector2(AnotherPandaRunner.width/4 , AnotherPandaRunner.height/2 + 20 * AnotherPandaRunner.density);
        str2pos = new Vector2(AnotherPandaRunner.width/2 , AnotherPandaRunner.height/5);
        str3pos = new Vector2( 0 , AnotherPandaRunner.height/5);

        str1 = new Label("A N O T H E R" , labelStyle);
        str2 = new Label("P A N D A" , labelStyle);
        str3 = new Label("R U N N E R" , labelStyle);

        str1.setPosition(str1pos.x , str1pos.y , Align.center);
        str2.setPosition(str2pos.x , str2pos.y , Align.center);
        str3.setPosition(str3pos.x , str3pos.y , Align.center);


        manager.load("backgrounds/backgroundday.png" , Texture.class);
        manager.load("backgrounds/backgroundevening.png" , Texture.class);
        manager.load("backgrounds/backgroundnight.png" , Texture.class);
        manager.load("backgrounds/backgroundhighscore.png" , Texture.class);
        manager.load("backgrounds/backgroundmenu.png" , Texture.class);

        manager.load("textures/bamboo.png" , Texture.class);
        manager.load("textures/platform.png" , Texture.class);
        manager.load("textures/specialbamboo.png" , Texture.class);

        manager.load("icons/play.png" , Texture.class);
        manager.load("icons/pause.png" , Texture.class);
        manager.load("icons/highscore.png" , Texture.class);
        manager.load("icons/quit.png" , Texture.class);
        manager.load("icons/retry.png" , Texture.class);
        manager.load("icons/home.png" , Texture.class);
        manager.load("icons/resume.png" , Texture.class);
        manager.load("icons/soundon.png" , Texture.class);
        manager.load("icons/soundoff.png" , Texture.class);
        manager.load("icons/musicon.png" , Texture.class);
        manager.load("icons/musicoff.png" , Texture.class);
        manager.load("icons/share.png" , Texture.class);

        manager.load("music/eatingmusic.ogg" , Music.class);
        manager.load("music/backgroundmusicmenu.ogg" , Music.class);
        manager.load("music/backgroundmusicload.ogg" , Music.class);
        manager.load("music/newhighscoresound.ogg" , Music.class);
        manager.load("music/selectmusic.ogg" , Music.class);
        manager.load("music/sleepingmusic.ogg" , Music.class);
        manager.load("music/jumpingmusic.ogg" , Music.class);
        manager.load("music/specialeatingmusic.ogg" , Music.class);
        manager.load("music/gameovermusic.ogg" , Music.class);
        manager.load("music/backgroundmusic.ogg" , Music.class);

        manager.finishLoading();
        switch(AnotherPandaRunner.time) {
            case 0 :
                bcg1 = manager.get("backgrounds/backgroundday.png", Texture.class);
                break;
            case 1 :
                bcg1 = manager.get("backgrounds/backgroundevening.png", Texture.class);
                break;
            case 2 :
                bcg1 = manager.get("backgrounds/backgroundnight.png", Texture.class);
                break;
            default: break;

        }
        backgroundmusic = manager.get("music/backgroundmusicload.ogg" , Music.class);
        Preferences preferences = Gdx.app.getPreferences("game");
        MainMenu.musicState = preferences.getInteger("music",1);
        if( MainMenu.musicState == MainMenu.enabled)
            backgroundmusic.play();
    }

    @Override
    protected void handleinput() {
    }

    @Override
    public void update(float dt) {
        manager.update();

        if(str3pos.x < cam.viewportWidth - 90 * AnotherPandaRunner.density) {
            str2pos.x -= (4 * AnotherPandaRunner.density);
            str3pos.x += (4 * AnotherPandaRunner.density);
        }
        else if(str1pos.y > AnotherPandaRunner.height/3)
            str1pos.y -= (4 * AnotherPandaRunner.density);
        else if (MainMenu.musicState == MainMenu.disabled) {
            delay += Gdx.graphics.getDeltaTime();
            if(delay > 1)
                gsm.push(new MainMenu(gsm, adControl, manager));
        }
        else if(MainMenu.musicState == MainMenu.enabled) {
            backgroundmusic.setOnCompletionListener(new Music.OnCompletionListener() {
                @Override
                public void onCompletion(Music music) {
                    gsm.push(new MainMenu(gsm, adControl, manager));
                }
            });
        }


        str1.setPosition(str1pos.x , str1pos.y , Align.center);
        str2.setPosition(str2pos.x , str2pos.y , Align.center);
        str3.setPosition(str3pos.x , str3pos.y , Align.center);
    }

    @Override
    public void render(SpriteBatch sb) {

        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bcg1,0,0, AnotherPandaRunner.width , AnotherPandaRunner.height/2);
        str1.draw(sb , 1f);
        str2.draw(sb , 1f);
        str3.draw(sb , 1f);
        sb.end();
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
    }
}
