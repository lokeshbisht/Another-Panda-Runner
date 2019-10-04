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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

/**
 * Created by HP on 12-02-2018.
 */

public class AssetLoader {

    public int hscore;
    private String msg;
    private Image pauseimage , gameoverimage;
    public Group pausegroup , gameovergroup;

    private Texture retrypause,homepause,resume;
    private TextureRegion retryregionpause,homeregionpause,resumeregion;
    private TextureRegionDrawable retrydrawpause,homedrawpause,resumedraw;
    private ImageButton retrybuttonpause,homebuttonpause,resumebutton;
    private Texture retrygameover,homegameover;
    private TextureRegion retryregiongameover,homeregiongameover;
    private TextureRegionDrawable retrydrawgameover,homedrawgameover;
    private ImageButton retrybuttongameover,homebuttongameover;

    private Label gameover , newhighscore , score , pausedlabel;
    private Label.LabelStyle labelStyle;
    private BitmapFont font;
    private AssetManager manager;
    private Table gameovertable , pausetable;
    private Music selectmusic;
    private Preferences preferences;

    public AssetLoader(final GameStateManager gsm , final AdControlAndShare adControl , final AssetManager manager , int gameState) {



        font = new BitmapFont(Gdx.files.internal("font-button-export.fnt"));
        font.getData().setScale(AnotherPandaRunner.density * 0.75f);

        labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.RED;

        gameover = new Label("G A M E O V E R", labelStyle);

        labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.CYAN;

        newhighscore = new Label("" , labelStyle);
        score = new Label("" , labelStyle);

        labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.CYAN;

        pausedlabel = new Label("GAME  PAUSED" , labelStyle);

        gameovertable = new Table();
        gameovertable.setPosition(0,AnotherPandaRunner.height);
        gameovertable.setWidth(AnotherPandaRunner.width);
        gameovertable.align(Align.center|Align.top);
        gameovertable.padTop(AnotherPandaRunner.height/7);
        gameovertable.add(gameover).expandX();
        gameovertable.row();
        gameovertable.add(newhighscore).expandX();
        gameovertable.row();
        gameovertable.add(score).expandX();


        pausetable = new Table();
        pausetable.setPosition(0,AnotherPandaRunner.height);
        pausetable.setWidth(AnotherPandaRunner.width);
        pausetable.align(Align.center|Align.top);
        pausetable.padTop(AnotherPandaRunner.height/7);
        pausetable.add(pausedlabel).expandX();

        this.manager = manager;
        selectmusic = manager.get("music/selectmusic.ogg" , Music.class);

        retrypause = manager.get("icons/retry.png" , Texture.class);
        retryregionpause = new TextureRegion(retrypause);
        retrydrawpause = new TextureRegionDrawable(retryregionpause);
        retrybuttonpause = new ImageButton(retrydrawpause);
        retrybuttonpause.setBounds(AnotherPandaRunner.width/2 - 50 * AnotherPandaRunner.density , AnotherPandaRunner.height/2 - 50 * AnotherPandaRunner.density ,100 * AnotherPandaRunner.density , 100 * AnotherPandaRunner.density);

        homepause = manager.get("icons/home.png" , Texture.class);
        homeregionpause = new TextureRegion(homepause);
        homedrawpause = new TextureRegionDrawable(homeregionpause);
        homebuttonpause = new ImageButton(homedrawpause);
        homebuttonpause.setBounds(AnotherPandaRunner.width/2 + 70 * AnotherPandaRunner.density , AnotherPandaRunner.height/2 - 50 * AnotherPandaRunner.density ,100 * AnotherPandaRunner.density , 100 * AnotherPandaRunner.density);

        resume = manager.get("icons/resume.png" , Texture.class);
        resumeregion = new TextureRegion(resume);
        resumedraw = new TextureRegionDrawable(resumeregion);
        resumebutton = new ImageButton(resumedraw);
        resumebutton.setBounds(AnotherPandaRunner.width/2 - 170 * AnotherPandaRunner.density , AnotherPandaRunner.height/2 - 50 * AnotherPandaRunner.density , 100 * AnotherPandaRunner.density , 100 * AnotherPandaRunner.density);

        resumebutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(MainMenu.soundState == MainMenu.enabled)
                    selectmusic.play();
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                adControl.hideBannerAd(1);
                AnotherPandaRunner.gameState = 4;
            }
        });

        retrybuttonpause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(MainMenu.soundState == MainMenu.enabled)
                    selectmusic.play();
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                if(MainMenu.musicState == MainMenu.enabled)
                    PlayState.gamemusic.stop();
                gsm.set(new PlayState(gsm , adControl , manager));
                adControl.hideBannerAd(1);
            }
        });

        homebuttonpause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(MainMenu.soundState == MainMenu.enabled)
                    selectmusic.play();
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                if(MainMenu.musicState == MainMenu.enabled)
                    PlayState.gamemusic.stop();
                gsm.set(new MainMenu(gsm , adControl , manager));
                adControl.hideBannerAd(1);
            }
        });

        retrygameover = manager.get("icons/retry.png" , Texture.class);
        retryregiongameover = new TextureRegion(retrygameover);
        retrydrawgameover = new TextureRegionDrawable(retryregiongameover);
        retrybuttongameover = new ImageButton(retrydrawgameover);
        retrybuttongameover.setBounds(AnotherPandaRunner.width/2 - 130 * AnotherPandaRunner.density , AnotherPandaRunner.height/2 - 100 * AnotherPandaRunner.density ,100 * AnotherPandaRunner.density , 100 * AnotherPandaRunner.density);

        homegameover = manager.get("icons/home.png" , Texture.class);
        homeregiongameover = new TextureRegion(homegameover);
        homedrawgameover = new TextureRegionDrawable(homeregiongameover);
        homebuttongameover = new ImageButton(homedrawgameover);
        homebuttongameover.setBounds(AnotherPandaRunner.width/2 + 30 * AnotherPandaRunner.density , AnotherPandaRunner.height/2 - 100 * AnotherPandaRunner.density ,100 * AnotherPandaRunner.density , 100 * AnotherPandaRunner.density);

        retrybuttongameover.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(MainMenu.soundState == MainMenu.enabled)
                    selectmusic.play();
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                preferences = Gdx.app.getPreferences("game");
                preferences.putInteger("highscore", hscore);
                preferences.flush();
                gsm.set(new PlayState(gsm , adControl , manager));
                adControl.hideBannerAd(2);
            }
        });

        homebuttongameover.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(MainMenu.soundState == MainMenu.enabled)
                    selectmusic.play();
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                preferences = Gdx.app.getPreferences("game");
                preferences.putInteger("highscore", hscore);
                preferences.flush();
                gsm.set(new MainMenu(gsm , adControl , manager));
                adControl.hideBannerAd(2);
            }
        });
    }

    public void initpausegroup() {
        pauseimage = new Image(manager.get("backgrounds/backgroundhighscore.png" , Texture.class));
        pauseimage.setSize(Gdx.graphics.getWidth() * 0.6f , Gdx.graphics.getHeight() - 100 * AnotherPandaRunner.density);
        pauseimage.setPosition(Gdx.graphics.getWidth() * 0.2f ,50 * AnotherPandaRunner.density);
        pauseimage.setColor(0,0,0,0.2f);
        pausegroup = new Group();
        pausegroup.addActor(pauseimage);
        pausegroup.addActor(resumebutton);
        pausegroup.addActor(retrybuttonpause);
        pausegroup.addActor(homebuttonpause);
        pausegroup.addActor(pausetable);

    }

    public void initgameovergroup() {
        gameoverimage = new Image(manager.get("backgrounds/backgroundhighscore.png" , Texture.class));
        gameoverimage.setSize(Gdx.graphics.getWidth() * 0.6f ,Gdx.graphics.getHeight() - 100 * AnotherPandaRunner.density);
        gameoverimage.setPosition(Gdx.graphics.getWidth() * 0.2f , 50 * AnotherPandaRunner.density);
        gameoverimage.setColor(0,0,0,0.2f);
        gameovergroup = new Group();
        gameovergroup.addActor(gameoverimage);
        gameovergroup.addActor(retrybuttongameover);
        gameovergroup.addActor(homebuttongameover);
    }

    public void setgameoverstage(int points) {
        if(!checkscore(points))
            msg = "Y O U R  S C O R E";
        else {
            msg = "NEW  HIGHSCORE";
        }
        newhighscore.setText(msg);
        score.setText(String.format("%04d" , points));
        gameovergroup.addActor(gameovertable);
    }

    public boolean checkscore(int score) {
        preferences = Gdx.app.getPreferences("game");
        hscore = preferences.getInteger("highscore",0);
        if(score > hscore) {
            hscore = score;
            return true;
        }
        else
            return false;
    }
}
