package states;

import com.anotherpandarunner.AdControlAndShare;
import com.anotherpandarunner.AnotherPandaRunner;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by HP on 12-02-2018.
 */

public class MainMenu extends State {

    private Stage stage;

    private Texture musicon , musicoff;
    private TextureRegion musiconregion,musicoffregion;
    private TextureRegionDrawable musicondrawable,musicoffdrawable;
    private ImageButton musiconbutton , musicoffbutton;

    private Texture soundon , soundoff;
    private TextureRegion soundonregion,soundoffregion;
    private TextureRegionDrawable soundondrawable,soundoffdrawable;
    private ImageButton soundonbutton , soundoffbutton;
    private Preferences preferences;

    private Texture share,play,highscore,quit;
    private TextureRegion shareregion,playregion,highscoreregion,quitregion;
    private TextureRegionDrawable sharedraw,playdraw,highscoredraw,quitdraw;
    private ImageButton sharebutton,playbutton,highscorebutton,quitbutton;

    public static int musicState;
    public static int soundState;
    public static int enabled = 1 , disabled = 0;
    private Music backgroundmusic , selectMusic;


    public MainMenu(final GameStateManager gsm , AdControlAndShare adControl , AssetManager manager) {
        super(gsm , adControl , manager);

        preferences = Gdx.app.getPreferences("game");
        stage = new Stage(new ScreenViewport());

        getSounds();

        selectMusic = manager.get("music/selectmusic.ogg" , Music.class);
        backgroundmusic = manager.get("music/backgroundmusicmenu.ogg" , Music.class);
        backgroundmusic.setLooping(true);

        Image image1 = new Image(manager.get("backgrounds/backgroundmenu.png" , Texture.class));
        image1.setSize(AnotherPandaRunner.width,AnotherPandaRunner.height);

        initbuttons();

        musicon = manager.get("icons/musicon.png" , Texture.class);
        musicoff = manager.get("icons/musicoff.png" , Texture.class);
        musiconregion = new TextureRegion(musicon);
        musicoffregion = new TextureRegion(musicoff);
        musicondrawable = new TextureRegionDrawable(musiconregion);
        musicoffdrawable = new TextureRegionDrawable(musicoffregion);
        musiconbutton = new ImageButton(musicondrawable);
        musiconbutton.setBounds(AnotherPandaRunner.width - 55 * AnotherPandaRunner.density , AnotherPandaRunner.height - 55 * AnotherPandaRunner.density ,50 * AnotherPandaRunner.density,50 * AnotherPandaRunner.density);
        musicoffbutton = new ImageButton(musicoffdrawable);
        musicoffbutton.setBounds(AnotherPandaRunner.width - 55 * AnotherPandaRunner.density , AnotherPandaRunner.height - 55 * AnotherPandaRunner.density,50 * AnotherPandaRunner.density,50 * AnotherPandaRunner.density);

        soundon = manager.get("icons/soundon.png" , Texture.class);
        soundoff = manager.get("icons/soundoff.png" , Texture.class);
        soundonregion = new TextureRegion(soundon);
        soundoffregion = new TextureRegion(soundoff);
        soundondrawable = new TextureRegionDrawable(soundonregion);
        soundoffdrawable = new TextureRegionDrawable(soundoffregion);
        soundonbutton = new ImageButton(soundondrawable);
        soundoffbutton = new ImageButton(soundoffdrawable);
        soundoffbutton.setBounds(AnotherPandaRunner.width - 110 * AnotherPandaRunner.density , AnotherPandaRunner.height - 55 * AnotherPandaRunner.density ,50 * AnotherPandaRunner.density,50 * AnotherPandaRunner.density);
        soundonbutton.setBounds(AnotherPandaRunner.width - 110 * AnotherPandaRunner.density , AnotherPandaRunner.height - 55 * AnotherPandaRunner.density ,50 * AnotherPandaRunner.density,50 * AnotherPandaRunner.density);

        addListeners();

        stage.addActor(image1);

        stage.addActor(musiconbutton);
        stage.addActor(musicoffbutton);
        if(musicState == enabled)
            stage.getActors().get(musicoffbutton.getZIndex()).setVisible(false);
        else if (musicState == disabled)
            stage.getActors().get(musiconbutton.getZIndex()).setVisible(false);

        stage.addActor(soundonbutton);
        stage.addActor(soundoffbutton);
        stage.addActor(playbutton);
        stage.addActor(highscorebutton);
        stage.addActor(sharebutton);
        stage.addActor(quitbutton);
        if(soundState == enabled)
            stage.getActors().get(soundoffbutton.getZIndex()).setVisible(false);
        else if (soundState == disabled)
            stage.getActors().get(soundonbutton.getZIndex()).setVisible(false);

        Gdx.input.setInputProcessor(stage);
    }

    private void initbuttons() {

        play = manager.get("icons/play.png" , Texture.class);
        playregion = new TextureRegion(play);
        playdraw = new TextureRegionDrawable(playregion);
        playbutton = new ImageButton(playdraw);
        playbutton.setBounds(AnotherPandaRunner.width/2 - (65 * AnotherPandaRunner.density), AnotherPandaRunner.height * 0.4f, 130 * AnotherPandaRunner.density , 130 * AnotherPandaRunner.density);

        highscore = manager.get("icons/highscore.png" , Texture.class);
        highscoreregion = new TextureRegion(highscore);
        highscoredraw = new TextureRegionDrawable(highscoreregion);
        highscorebutton = new ImageButton(highscoredraw);
        highscorebutton.setBounds(AnotherPandaRunner.width/2 - 130 * AnotherPandaRunner.density, AnotherPandaRunner.height * 0.03f , 100 * AnotherPandaRunner.density , 100 * AnotherPandaRunner.density);

        quit = manager.get("icons/quit.png" , Texture.class);
        quitregion = new TextureRegion(quit);
        quitdraw = new TextureRegionDrawable(quitregion);
        quitbutton = new ImageButton(quitdraw);
        quitbutton.setBounds(5 * AnotherPandaRunner.density , AnotherPandaRunner.height - (55 * AnotherPandaRunner.density),50 * AnotherPandaRunner.density,50 * AnotherPandaRunner.density);

        share = manager.get("icons/share.png" , Texture.class);
        shareregion = new TextureRegion(share);
        sharedraw = new TextureRegionDrawable(shareregion);
        sharebutton = new ImageButton(sharedraw);
        sharebutton.setClip(true);
        sharebutton.setBounds(AnotherPandaRunner.width/2 + (30 * AnotherPandaRunner.density) , AnotherPandaRunner.height * 0.03f , 100 * AnotherPandaRunner.density , 100 * AnotherPandaRunner.density);
    }
    public void addListeners() {
        musiconbutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(soundState == enabled)
                    selectMusic.play();
                musicState = disabled;
                preferences.putInteger("music",0);
                preferences.flush();
                stage.getActors().get(musiconbutton.getZIndex()).setVisible(false);
                stage.getActors().get(musicoffbutton.getZIndex()).setVisible(true);
                stage.draw();
            }
        });
        musicoffbutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                musicState = enabled;
                preferences.putInteger("music", 1);
                preferences.flush();
                stage.getActors().get(musiconbutton.getZIndex()).setVisible(true);
                stage.getActors().get(musicoffbutton.getZIndex()).setVisible(false);
                stage.draw();
            }
        });


        soundonbutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(soundState == enabled)
                    selectMusic.play();
                soundState = disabled;
                preferences.putInteger("sound", 0);
                preferences.flush();
                stage.getActors().get(soundonbutton.getZIndex()).setVisible(false);
                stage.getActors().get(soundoffbutton.getZIndex()).setVisible(true);
                stage.draw();
            }
        });
        soundoffbutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundState = enabled;
                preferences.putInteger("sound", 1);
                preferences.flush();
                stage.getActors().get(soundonbutton.getZIndex()).setVisible(true);
                stage.getActors().get(soundoffbutton.getZIndex()).setVisible(false);
                stage.draw();
            }
        });

        playbutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(soundState == enabled)
                    selectMusic.play();
                backgroundmusic.stop();
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                gsm.set(new PlayState(gsm , adControl , manager));
            }
        });

        quitbutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(soundState == enabled)
                    selectMusic.play();
                backgroundmusic.stop();
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                Gdx.app.exit();
            }
        });

        highscorebutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(soundState == enabled)
                    selectMusic.play();
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                gsm.set(new HighScoreState(gsm , adControl , manager));
            }
        });

        sharebutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(soundState == enabled)
                    selectMusic.play();
                adControl.ShareApp();
            }
        });
    }

    public void getSounds() {
        musicState = preferences.getInteger("music",1);
        soundState = preferences.getInteger("sound", 1);
    }

    @Override
    protected void handleinput() {
    }

    @Override
    public void update(float dt) {
        if(musicState == enabled)
            backgroundmusic.play();
        if(musicState == disabled)
            backgroundmusic.stop();

    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
    }
}
