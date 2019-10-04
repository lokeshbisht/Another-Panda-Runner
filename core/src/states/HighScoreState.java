package states;

import com.anotherpandarunner.AdControlAndShare;
import com.anotherpandarunner.AnotherPandaRunner;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by HP on 12-02-2018.
 */

public class HighScoreState extends State {

    private Stage stage;
    private  int hscore;
    private Table table;
    private String message;
    private Label highscorelabel , scorelabel;
    private Texture reset , home;
    private TextureRegion resetregion,homeregion;
    private TextureRegionDrawable resetdraw,homedraw;
    private ImageButton resetbutton,homebutton;
    private BitmapFont bitmapFont;
    private Label.LabelStyle labelStyle;
    private Image background;
    private Music selectmusic;
    private Music backgroundmusic;

    public HighScoreState(final GameStateManager gsm , final AdControlAndShare adControl , final AssetManager manager) {
        super(gsm , adControl , manager);

        stage = new Stage(new ScreenViewport());

        if(adControl.isInternetConnected()){
            adControl.showBannerAd(3);
        }

        getscore();
        message = "H I G H S C O R E";

        bitmapFont = new BitmapFont(Gdx.files.internal("font-button-export.fnt"));
        bitmapFont.getData().setScale(AnotherPandaRunner.density * 0.75f);

        labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont;
        labelStyle.fontColor = Color.BLUE;

        highscorelabel = new Label(String.format("%s" , message) , labelStyle);

        bitmapFont = new BitmapFont(Gdx.files.internal("font-button-export.fnt"));
        bitmapFont.getData().setScale(AnotherPandaRunner.density * 0.75f);

        labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont;
        labelStyle.fontColor = Color.GREEN;

        scorelabel = new Label(String.format("%04d" , hscore), labelStyle);

        selectmusic = manager.get("music/selectmusic.ogg" , Music.class);
        table = new Table();
        table.setPosition(0,AnotherPandaRunner.height);
        table.setWidth(AnotherPandaRunner.width);
        table.align(Align.center|Align.top);
        table.padTop(AnotherPandaRunner.height/6);
        table.add(highscorelabel).expandX();
        table.row();
        table.add(scorelabel).expandX().padTop(AnotherPandaRunner.height/10);

        background = new Image(manager.get("backgrounds/backgroundhighscore.png" , Texture.class));
        background.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        backgroundmusic = manager.get("music/backgroundmusicmenu.ogg" , Music.class);
        backgroundmusic.setLooping(true);

        reset = manager.get("icons/retry.png" , Texture.class);
        resetregion = new TextureRegion(reset);
        resetdraw = new TextureRegionDrawable(resetregion);
        resetbutton = new ImageButton(resetdraw);
        resetbutton.setBounds(AnotherPandaRunner.width - (100 * AnotherPandaRunner.density + 30) , AnotherPandaRunner.height/2 - (50 * AnotherPandaRunner.density) , 100 * AnotherPandaRunner.density,100 * AnotherPandaRunner.density);

        home = manager.get("icons/home.png" , Texture.class);
        homeregion = new TextureRegion(home);
        homedraw = new TextureRegionDrawable(homeregion);
        homebutton = new ImageButton(homedraw);
        homebutton.setBounds(30 , AnotherPandaRunner.height/2 - (50 * AnotherPandaRunner.density) , 100 * AnotherPandaRunner.density,100 * AnotherPandaRunner.density);

        resetbutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(MainMenu.soundState == MainMenu.enabled)
                    selectmusic.play();
                Preferences preferences = Gdx.app.getPreferences("game");
                preferences.putInteger("highscore" , 0);
                preferences.flush();
                gsm.set(new HighScoreState(gsm , adControl , manager));
            }
        });

        homebutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(MainMenu.soundState == MainMenu.enabled)
                    selectmusic.play();
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                gsm.set(new MainMenu(gsm , adControl , manager));
                adControl.hideBannerAd(3);
                backgroundmusic.stop();
            }
        });

        stage.addActor(background);
        stage.addActor(homebutton);
        stage.addActor(resetbutton);
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);

    }

    public void getscore() {
        Preferences preferences = Gdx.app.getPreferences("game");
        hscore = preferences.getInteger("highscore" , 0);
    }



    @Override
    protected void handleinput() {
    }

    @Override
    public void update(float dt) {
        if(MainMenu.musicState == MainMenu.disabled)
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
