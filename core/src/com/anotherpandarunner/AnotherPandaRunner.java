package com.anotherpandarunner;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import states.GameStateManager;
import states.LaunchState;

public class AnotherPandaRunner extends ApplicationAdapter {

	private AdControlAndShare adsController;
	private SpriteBatch batch;
	private GameStateManager gsm;
	private AssetManager manager;
	public static float width , height , density;
	public static int time;
	private Date date;
	public static int hours;
	public static Random rand;
	private GregorianCalendar calendar;
	public static int gameState;

	public AnotherPandaRunner(AdControlAndShare adsController){
		manager = new AssetManager();
		this.adsController = adsController;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();

		rand = new Random();
		date = new Date();
		calendar = new GregorianCalendar();
		calendar.setTime(date);

		hours = calendar.get(Calendar.HOUR_OF_DAY);
		if( hours >= 6 && hours <= 15)
			time = 0;
		else if( hours > 15  && hours <= 18)
			time = 1;
		else if( hours > 18 || ( hours >=0 && hours <6))
			time = 2;

		gsm = new GameStateManager();
		Gdx.gl.glClearColor(0,0,1,1);
		gsm.push(new LaunchState(gsm,adsController,manager));
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		density = Gdx.graphics.getDensity();
	}


	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}

	@Override
	public void dispose () {
		batch.dispose();
		manager.dispose();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
}
