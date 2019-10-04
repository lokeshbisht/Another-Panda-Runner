package states;

import com.anotherpandarunner.AdControlAndShare;
import com.anotherpandarunner.AnotherPandaRunner;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import sprites.Bamboo;
import sprites.Panda;
import sprites.Platform;
import sprites.SpecialPoint;

import static com.anotherpandarunner.AnotherPandaRunner.gameState;

/**
 * Created by HP on 13-02-2018.
 */

public class PlayState extends State {

    private Panda character;
    private Array<Bamboo> bamboos;
    private Texture bcg1,bcg2;
    private int bambootaken;
    private Vector2 backgroundpos1,backgroundpos2;
    private Array<Platform> platforms;
    private SpecialPoint specialPoint;
    private Music gameOverMusic ,jumpingMusic , eatingMusic , selectmusic , specialeatingmusic;
    private Music sleepingmusic;
    private int numberofplatforms;
    public int characterState;
    private int numberofbamboos;
    private float check,increase;
    public static Music gamemusic;
    private int change;
    private Texture myTexture;
    private TextureRegion myTextureRegion;
    private TextureRegionDrawable myTexRegionDrawable;
    private ImageButton pausebutton;

    private Stage playstage,pauseStage,gameoverStage;
    private Music newhighscoremusic;
    
    private AssetLoader loadAssets;
    private int playing = 0;
    private int paused = 1;
    private int gameOver = 2;
    private int started = 3;
    private int resumed = 4;

    private Label scorelabel , beginlabel , continuelabel;
    private Image bambooimage , pointsimage;
    private BitmapFont bitmapFont1 , bitmapFont2;
    private Label.LabelStyle labelStyle;

    public PlayState(final GameStateManager gsm , final AdControlAndShare adControl , AssetManager manager) {
        super(gsm , adControl , manager);
        bambootaken=0;

        bitmapFont1 = new BitmapFont(Gdx.files.internal("font-button-export.fnt"));
        bitmapFont1.getData().setScale(AnotherPandaRunner.density * 0.75f);

        labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont1;
        labelStyle.fontColor = Color.GREEN;

        scorelabel = new Label(String.format("%04d" , bambootaken) , labelStyle);
        scorelabel.setPosition(AnotherPandaRunner.width/2 + 20 * AnotherPandaRunner.density , AnotherPandaRunner.height - 50 * AnotherPandaRunner.density);

        bitmapFont2 = new BitmapFont(Gdx.files.internal("font-button-export.fnt"));
        bitmapFont2.getData().setScale((AnotherPandaRunner.density * 0.75f));

        labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont2;
        labelStyle.fontColor = Color.BLUE;

        beginlabel = new Label("TOUCH TO BEGIN" , labelStyle);
        beginlabel.setPosition(AnotherPandaRunner.width/2 , AnotherPandaRunner.height/4 , Align.center);

        bitmapFont2 = new BitmapFont(Gdx.files.internal("font-button-export.fnt"));
        bitmapFont2.getData().setScale((AnotherPandaRunner.density * 0.75f));

        labelStyle = new Label.LabelStyle();
        labelStyle.font = bitmapFont2;
        labelStyle.fontColor = Color.BLUE;

        continuelabel = new Label("TOUCH TO CONTINUE", labelStyle);
        continuelabel.setPosition(AnotherPandaRunner.width/2 , AnotherPandaRunner.height/4 , Align.center);
        playstage = new Stage(new ScreenViewport());
        pauseStage = new Stage(new ScreenViewport());
        gameoverStage = new Stage(new ScreenViewport());

        loadAssets = new AssetLoader(gsm , adControl , manager , gameState);
        loadAssets.initgameovergroup();
        loadAssets.initpausegroup();

        pointsimage = new Image(manager.get("backgrounds/backgroundhighscore.png" , Texture.class));
        pointsimage.setSize(125 * AnotherPandaRunner.density ,50 * AnotherPandaRunner.density);
        pointsimage.setPosition(AnotherPandaRunner.width/2 - 25 * AnotherPandaRunner.density , AnotherPandaRunner.height - 55 * AnotherPandaRunner.density);
        pointsimage.setColor(0,0,0,0.1f);

        numberofplatforms = (int)AnotherPandaRunner.width/200;
        numberofbamboos = numberofplatforms * 6;
        check = 1;
        increase = 1;
        gameState = started;

        newhighscoremusic = manager.get("music/newhighscoresound.ogg" , Music.class);
        change = AnotherPandaRunner.rand.nextInt(100);
        character = new Panda((int)AnotherPandaRunner.width/2 + change,(int)(AnotherPandaRunner.height/8) + change , manager);
        gameOverMusic = manager.get("music/gameovermusic.ogg" , Music.class);
        gamemusic = manager.get("music/backgroundmusic.ogg" , Music.class);
        gamemusic.setLooping(true);
        jumpingMusic = manager.get("music/jumpingmusic.ogg" , Music.class);
        selectmusic = manager.get("music/selectmusic.ogg" , Music.class);
        eatingMusic = manager.get("music/eatingmusic.ogg" , Music.class);
        specialeatingmusic = manager.get("music/specialeatingmusic.ogg", Music.class);
        specialeatingmusic.setVolume(2f);
        sleepingmusic = manager.get("music/sleepingmusic.ogg" , Music.class);
        if(MainMenu.soundState == MainMenu.enabled)
            sleepingmusic.play();
        sleepingmusic.setLooping(true);
        cam.setToOrtho(false , AnotherPandaRunner.width/2 , AnotherPandaRunner.height/2);
        cam.position.x = character.position.x + AnotherPandaRunner.width/6;
        cam.update();
        switch(AnotherPandaRunner.time) {
            case 0 :
                bcg1 = new Texture("backgrounds/backgroundday.png");
                bcg2 = new Texture("backgrounds/backgroundday.png");
                break;
            case 1 :
                bcg1 = new Texture("backgrounds/backgroundevening.png");
                bcg2 = new Texture("backgrounds/backgroundevening.png");
                break;
            case 2 :
                bcg1 = new Texture("backgrounds/backgroundnight.png");
                bcg2 = new Texture("backgrounds/backgroundnight.png");
                break;
            default: break;

        }

        bambooimage = new Image(manager.get("textures/bamboo.png" , Texture.class));
        bambooimage.setBounds(AnotherPandaRunner.width/2 - 20 * AnotherPandaRunner.density , AnotherPandaRunner.height - 50 * AnotherPandaRunner.density , 20 * AnotherPandaRunner.density , 40 * AnotherPandaRunner.density);

        myTexture = new Texture(Gdx.files.internal("icons/pause.png"));
        myTextureRegion = new TextureRegion(myTexture);
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        pausebutton = new ImageButton(myTexRegionDrawable);
        pausebutton.setBounds( AnotherPandaRunner.width - 50 * AnotherPandaRunner.density, AnotherPandaRunner.height - 50 * AnotherPandaRunner.density, 40 * AnotherPandaRunner.density,40 * AnotherPandaRunner.density);
        pausebutton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (characterState != 0 && gameState != resumed) {
                    gameState = paused;
                }
                if(MainMenu.soundState == MainMenu.enabled)
                    selectmusic.play();
                return true;
            }
        });

        backgroundpos1 = new Vector2(0,0);
        backgroundpos2 = new Vector2(AnotherPandaRunner.width,0);
        characterState = 0;
        platforms = new Array<Platform>();
        bamboos = new Array<Bamboo>();
        specialPoint = new SpecialPoint();
        specialPoint.taken = true;
        for(int i=0;i< numberofbamboos;i++) {
            bamboos.add(new Bamboo(manager));
        }

        for(int i=0;i< numberofplatforms;i++) {
            platforms.add(new Platform(manager));
        }

        Platform firstplatform = platforms.get(0);
        firstplatform.make(0,0);
        firstplatform.pos.set((int)AnotherPandaRunner.width/2 + change, AnotherPandaRunner.height/8 + change);
        firstplatform.setBounds((int)AnotherPandaRunner.width/2 + change, AnotherPandaRunner.height/8 + change);
        firstplatform.amount = 0;
        character.position.y = firstplatform.getPos().y + (10 * AnotherPandaRunner.density) - AnotherPandaRunner.height/45;

        for(int i=1;i< numberofplatforms;i++){
            Platform platform = platforms.get(i);
            Platform platform1 = platforms.get(i-1);
            platform.make(platform1.getPos().x,platform1.getPos().y);
            setpositon(platform,i);

        }
        playstage.addActor(pausebutton);
        playstage.addActor(bambooimage);
        playstage.addActor(scorelabel);
        playstage.addActor(beginlabel);
        playstage.addActor(pointsimage);
        playstage.addActor(continuelabel);
        playstage.getActors().get(continuelabel.getZIndex()).setVisible(false);

        Gdx.input.setInputProcessor(playstage);

    }

    @Override
    protected void handleinput() {
        if(Gdx.input.justTouched()) {
            if(gameState == started) {
                playstage.getActors().get(beginlabel.getZIndex()).setVisible(false);
                gameState = playing;
            }
            else if(gameState == playing) {
                if (characterState == 1 || characterState == 4) {
                    if(MainMenu.soundState == MainMenu.enabled)
                        jumpingMusic.play();
                    character.velocity.y = 150 * AnotherPandaRunner.density;
                    characterState = 2;
                }
                else if (characterState == 2) {
                    if(MainMenu.soundState == MainMenu.enabled)
                        jumpingMusic.play();
                    character.velocity.y = 150 * AnotherPandaRunner.density;
                    characterState = 3;
                }
            }
            else if(gameState == resumed) {
                playstage.getActors().get(continuelabel.getZIndex()).setVisible(false);
                gameState = playing;
            }
        }
    }

    @Override
    public void update(float dt) {
        if(gameState == playing) {

            if(characterState == 0) {
                character.update(dt , characterState);
                if(character.getframe() == 4) {
                    sleepingmusic.stop();
                    characterState = 1;
                }
            }
            updatebackground();

            updateincrease();

            checkcollision();

            reposition();

            if (MainMenu.musicState == MainMenu.enabled)
                gamemusic.play();


            character.update(dt, characterState);
            cam.position.x = character.getPosition().x + AnotherPandaRunner.width/6;
            cam.update();

            if (character.getPosition().y == 0) {

                if (MainMenu.musicState == MainMenu.enabled) {
                    gamemusic.stop();
                }

                if (MainMenu.soundState == MainMenu.enabled) {
                    if(loadAssets.checkscore(bambootaken)) {
                        newhighscoremusic.play();
                    }
                    else {
                        if(MainMenu.soundState == MainMenu.enabled) {
                            gameOverMusic.play();
                            gameOverMusic.setOnCompletionListener(new Music.OnCompletionListener() {
                                @Override
                                public void onCompletion(Music music) {
                                    if(bambootaken > 20 && !loadAssets.checkscore(bambootaken)) {
                                        if (adControl.isInternetConnected()) {
                                            adControl.showInterstitialAd(new Runnable() {
                                                @Override
                                                public void run() {
                                                }
                                            });
                                        }
                                    }
                                }
                            });
                        }
                    }
                }
                else {
                    if(bambootaken > 20 && !loadAssets.checkscore(bambootaken)) {
                        if (adControl.isInternetConnected()) {
                            adControl.showInterstitialAd(new Runnable() {
                                @Override
                                public void run() {
                                }
                            });
                        }
                    }
                }
                gameState = gameOver;
            }
        }

        else if(gameState == gameOver) {
            gamemusic.stop();
            if(!adControl.isbannershown(2)) {
                if (adControl.isInternetConnected()) {
                    adControl.showBannerAd(2);
                }
            }
            loadAssets.setgameoverstage(bambootaken);
            gameoverStage.addActor(loadAssets.gameovergroup);
            Gdx.input.setInputProcessor(gameoverStage);
        }

        else if(gameState == paused){
            gamemusic.pause();
            if(!adControl.isbannershown(1)) {
                if (adControl.isInternetConnected()) {
                    adControl.showBannerAd(1);
                }
            }
            pauseStage.addActor(loadAssets.pausegroup);
            Gdx.input.setInputProcessor(pauseStage);
        }

        else if(gameState == resumed) {
            pauseStage.clear();
            playstage.getActors().get(continuelabel.getZIndex()).setVisible(true);
            Gdx.input.setInputProcessor(playstage);
        }


        handleinput();
    }

    @Override
    public void render(SpriteBatch sb) {

        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bcg1, backgroundpos1.x, backgroundpos1.y, AnotherPandaRunner.width, AnotherPandaRunner.height / 2);
        sb.draw(bcg2, backgroundpos2.x, backgroundpos2.y, AnotherPandaRunner.width, AnotherPandaRunner.height / 2);
        for (int i = 0; i < platforms.size; i++) {
            Platform platform = platforms.get(i);
            sb.draw(platform.getPlatform(), platform.getPos().x, platform.getPos().y, AnotherPandaRunner.width/10, 10 * AnotherPandaRunner.density);

            if (platform.amount == 6) {
                if (specialPoint.taken == false)
                    sb.draw(specialPoint.getTexture(), specialPoint.getPos().x, specialPoint.getPos().y + increase, AnotherPandaRunner.width/80, 20 * AnotherPandaRunner.density);
            } else {
                for (int j = 0; j < platform.amount; j++) {
                    Bamboo bamboo = bamboos.get((i * 5) + j);
                    if (bamboo.taken == false)
                        sb.draw(bamboo.getTexture(), bamboo.getPos().x, bamboo.getPos().y + increase, AnotherPandaRunner.width/80, 20 * AnotherPandaRunner.density);
                }
            }
        }
        if(gameState != gameOver) {
            if (characterState == 0)
                sb.draw(character.getTexture(characterState), character.getPosition().x, character.getPosition().y, AnotherPandaRunner.width / 12, AnotherPandaRunner.height / 6);
            else
                sb.draw(character.getTexture(characterState), character.getPosition().x, character.getPosition().y, AnotherPandaRunner.width / 14, AnotherPandaRunner.height / 6);
        }


        sb.end();

        playstage.act(Gdx.graphics.getDeltaTime());
        playstage.draw();

        if(gameState == paused) {
            pauseStage.draw();
        }

        else if(gameState == gameOver) {
            gameoverStage.draw();
        }
    }

    public void updatebackground() {
        if(cam.position.x-cam.viewportWidth/2  > backgroundpos1.x + AnotherPandaRunner.width)
            backgroundpos1.add(2 * AnotherPandaRunner.width,0);
        if(cam.position.x-cam.viewportWidth/2  > backgroundpos2.x + AnotherPandaRunner.width)
            backgroundpos2.add(2 * AnotherPandaRunner.width,0);
    }

    public void reposition() {

        for(int i = 0;i<platforms.size;i++) {
            Platform platform = platforms.get(i);
            if (i == 0) {
                Platform platform1 = platforms.get(platforms.size - 1);
                if (cam.position.x - (cam.viewportWidth / 2) > platform.getPos().x + AnotherPandaRunner.width/10) {
                    platform.reposition(platform1.getPos().x,platform1.getPos().y);
                    setpositon(platform,i);
                }
            }
            else {
                Platform platform1 = platforms.get(i - 1);
                if (cam.position.x - (cam.viewportWidth / 2) > platform.getPos().x + AnotherPandaRunner.width/10) {
                    platform.reposition(platform1.getPos().x,platform1.getPos().y);
                    setpositon(platform,i);
                }
            }
        }
    }



    public void setpositon(Platform platform,int i) {

        switch (platform.amount) {
            case 1 : Bamboo  bamboo1 = bamboos.get(i*5);
                bamboo1.setPos(platform.getPos().x + (3.5f * AnotherPandaRunner.width)/80,platform.getPos().y + 10 * AnotherPandaRunner.density);
                bamboo1.taken = false;
                bamboo1.setBounds(bamboo1.getPos().x , bamboo1.getPos().y);
                break;
            case 2 : Bamboo bamboo21 = bamboos.get(i*5);
                bamboo21.setPos(platform.getPos().x + (2.75f * AnotherPandaRunner.width)/80,platform.getPos().y + 10 * AnotherPandaRunner.density);
                Bamboo bamboo22 = bamboos.get(i*5 + 1);
                bamboo22.setPos(platform.getPos().x + (4.25f * AnotherPandaRunner.width)/80,platform.getPos().y + 10 * AnotherPandaRunner.density);
                bamboo21.taken = false;
                bamboo21.setBounds(bamboo21.getPos().x , bamboo21.getPos().y);
                bamboo22.taken = false;
                bamboo22.setBounds(bamboo22.getPos().x , bamboo22.getPos().y);
                break;
            case 3 : Bamboo bamboo31 = bamboos.get(i*5);
                bamboo31.setPos(platform.getPos().x + (2 * AnotherPandaRunner.width)/80,platform.getPos().y + 10 * AnotherPandaRunner.density);
                Bamboo bamboo32 = bamboos.get(i*5 + 1);
                bamboo32.setPos(platform.getPos().x + (3.5f * AnotherPandaRunner.width)/80,platform.getPos().y + 10 * AnotherPandaRunner.density);
                Bamboo bamboo33 = bamboos.get(i*5 + 2);
                bamboo33.setPos(platform.getPos().x + (5 * AnotherPandaRunner.width)/80,platform.getPos().y + 10 * AnotherPandaRunner.density);
                bamboo31.taken = false;
                bamboo31.setBounds(bamboo31.getPos().x , bamboo31.getPos().y);
                bamboo32.taken = false;
                bamboo32.setBounds(bamboo32.getPos().x , bamboo32.getPos().y);
                bamboo33.taken = false;
                bamboo33.setBounds(bamboo33.getPos().x , bamboo33.getPos().y);
                break;
            case 4 : Bamboo bamboo41 = bamboos.get(i*5);
                bamboo41.setPos(platform.getPos().x + (1.25f * AnotherPandaRunner.width)/80,platform.getPos().y + 10 * AnotherPandaRunner.density);
                Bamboo bamboo42 = bamboos.get(i*5 + 1);
                bamboo42.setPos(platform.getPos().x + (2.75f * AnotherPandaRunner.width)/80,platform.getPos().y + 10 * AnotherPandaRunner.density);
                Bamboo bamboo43 = bamboos.get(i*5 + 2);
                bamboo43.setPos(platform.getPos().x + (4.25f * AnotherPandaRunner.width)/80,platform.getPos().y + 10 * AnotherPandaRunner.density);
                Bamboo bamboo44 = bamboos.get(i*5 + 3);
                bamboo44.setPos(platform.getPos().x + (5.75f * AnotherPandaRunner.width)/80,platform.getPos().y + 10 * AnotherPandaRunner.density);
                bamboo41.taken = false;
                bamboo41.setBounds(bamboo41.getPos().x , bamboo41.getPos().y);
                bamboo42.taken = false;
                bamboo42.setBounds(bamboo42.getPos().x , bamboo42.getPos().y);
                bamboo43.taken = false;
                bamboo43.setBounds(bamboo43.getPos().x , bamboo43.getPos().y);
                bamboo44.taken = false;
                bamboo44.setBounds(bamboo44.getPos().x , bamboo44.getPos().y);
                break;
            case 5 : Bamboo bamboo51 = bamboos.get(i*5);
                bamboo51.setPos(platform.getPos().x +  (0.5f * AnotherPandaRunner.width/80),platform.getPos().y + 10 * AnotherPandaRunner.density);
                Bamboo bamboo52 = bamboos.get(i*5 + 1);
                bamboo52.setPos(platform.getPos().x + (2 * AnotherPandaRunner.width)/80,platform.getPos().y + 10 * AnotherPandaRunner.density);
                Bamboo bamboo53 = bamboos.get(i*5 + 2);
                bamboo53.setPos(platform.getPos().x + (3.5f * AnotherPandaRunner.width)/80,platform.getPos().y + 10 * AnotherPandaRunner.density);
                Bamboo bamboo54 = bamboos.get(i*5 + 3);
                bamboo54.setPos(platform.getPos().x + (5 * AnotherPandaRunner.width)/80,platform.getPos().y + 10 * AnotherPandaRunner.density);
                Bamboo bamboo55 = bamboos.get(i*5 + 4);
                bamboo55.setPos(platform.getPos().x + (6.5f * AnotherPandaRunner.width)/80,platform.getPos().y + 10 * AnotherPandaRunner.density);
                bamboo51.taken = false;
                bamboo51.setBounds(bamboo51.getPos().x , bamboo51.getPos().y);
                bamboo52.taken = false;
                bamboo52.setBounds(bamboo52.getPos().x , bamboo52.getPos().y);
                bamboo53.taken = false;
                bamboo53.setBounds(bamboo53.getPos().x , bamboo53.getPos().y);
                bamboo54.taken = false;
                bamboo54.setBounds(bamboo54.getPos().x , bamboo54.getPos().y);
                bamboo55.taken = false;
                bamboo55.setBounds(bamboo55.getPos().x , bamboo55.getPos().y);
                break;
            case 6 :
                if(specialPoint.taken == true) {
                    specialPoint.setPos(platform.getPos().x + (3.5f * AnotherPandaRunner.width) / 80, platform.getPos().y + 10 * AnotherPandaRunner.density);
                    specialPoint.taken = false;
                    specialPoint.setBounds(specialPoint.getPos().x, specialPoint.getPos().y);
                }
                break;
            default: break;

        }
    }

    public void checkcollision() {

        for(int i=0;i<platforms.size;i++) {
            Platform platform = platforms.get(i);
            if(character.velocity.y <= 0) {
                if(character.position.y == platform.getPos().y + (10 * AnotherPandaRunner.density) - AnotherPandaRunner.height/45 && character.position.x + AnotherPandaRunner.width/40 > platform.getPos().x + AnotherPandaRunner.width/10)
                    characterState = 4;
                else if (character.collides(platform.getBounds())) {
                    characterState = 1;
                    character.velocity.y = 0;
                    character.position.y = platform.getPos().y + (10 * AnotherPandaRunner.density) - AnotherPandaRunner.height/45;
                }
            }


            for(int j=0;j<platform.amount;j++) {
                Bamboo bamboo = bamboos.get((i*5)+j);
                if(bamboo.collides(character.getBounds2()) && !bamboo.taken) {
                    bamboo.taken = true;
                    if(MainMenu.soundState == MainMenu.enabled)
                        eatingMusic.play();

                    bambootaken++;
                    scorelabel.setText(String.format("%04d" , bambootaken));
                }
            }
            if(specialPoint.collides(character.getBounds2()) && !specialPoint.taken) {
                specialPoint.taken = true;
                if(MainMenu.soundState == MainMenu.enabled)
                    specialeatingmusic.play();
                bambootaken += 10;
                scorelabel.setText(String.format("%04d" , bambootaken));
            }
        }
    }

    public void updateincrease() {
        if(check == 1) {
            increase++;
            if(increase>10)
                check = 0;
        }
        else if(check == 0) {
            increase--;
            if(increase<1)
                check = 1;
        }
    }

    @Override
    public void pause() {
        gameState = paused;
    }

    @Override
    public void resume() {
        gameState = resumed;
    }

    @Override
    public void dispose() {
        bcg1.dispose();
        character.dispose();
        bcg2.dispose();

    }
}
