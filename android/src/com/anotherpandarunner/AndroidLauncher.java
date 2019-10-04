package com.anotherpandarunner;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class AndroidLauncher extends AndroidApplication implements AdControlAndShare{
	protected AdView PauseBanner , GameOverBanner , HighScoreBanner;
	InterstitialAd interstitialAd;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RelativeLayout relativeLayout = new RelativeLayout(this);

		MobileAds.initialize(this , getString(R.string.APP_ID));
		setupAds();

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		View gameView = initializeForView(new AnotherPandaRunner(this), config);

		RelativeLayout layout = new RelativeLayout(this);
		layout.addView(gameView, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		layout.addView(PauseBanner, params);
		layout.addView(GameOverBanner, params);
		layout.addView(HighScoreBanner, params);

		setContentView(layout);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public boolean isInternetConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return (ni != null && ni.isConnected());
	}

	public void setupAds() {
		PauseBanner = new AdView(this);
		PauseBanner.setVisibility(View.INVISIBLE);
		PauseBanner.setAdUnitId(getString(R.string.PAUSE_BANNER_AD_UNIT_ID));
		PauseBanner.setAdSize(AdSize.FULL_BANNER);

		GameOverBanner = new AdView(this);
		GameOverBanner.setVisibility(View.INVISIBLE);
		GameOverBanner.setAdUnitId(getString(R.string.GAMEOVER_BANNER_AD_UNIT_ID));
		GameOverBanner.setAdSize(AdSize.FULL_BANNER);

		HighScoreBanner = new AdView(this);
		HighScoreBanner.setVisibility(View.INVISIBLE);
		HighScoreBanner.setAdUnitId(getString(R.string.HIGHSCORE_BANNER_AD_UNIT_ID));
		HighScoreBanner.setAdSize(AdSize.FULL_BANNER);

		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(getString(R.string.GAMEOVER_INTERSTITIAL_AD_UNIT_ID));

		AdRequest.Builder builder = new AdRequest.Builder();
		AdRequest ad = builder.build();
		interstitialAd.loadAd(ad);
	}


	@Override
	public void showInterstitialAd(final Runnable then) {

		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (then != null) {
					interstitialAd.setAdListener(new AdListener() {
						@Override
						public void onAdClosed() {
							Gdx.app.postRunnable(then);
							AdRequest.Builder builder = new AdRequest.Builder();
							AdRequest ad = builder.build();
							interstitialAd.loadAd(ad);
						}
					});
				}
				interstitialAd.show();
			}
		});
	}

	@Override
	public void showBannerAd(int Screen) {
		switch (Screen) {
			case 1 :runOnUiThread(new Runnable() {
				@Override
				public void run() {
					PauseBanner.setVisibility(View.VISIBLE);
					AdRequest.Builder builder = new AdRequest.Builder();
					AdRequest ad = builder.build();
					PauseBanner.loadAd(ad);
				}
			});
				break;
			case 2 :runOnUiThread(new Runnable() {
				@Override
				public void run() {
					GameOverBanner.setVisibility(View.VISIBLE);
					AdRequest.Builder builder = new AdRequest.Builder();
					AdRequest ad = builder.build();
					GameOverBanner.loadAd(ad);
				}
			});
				break;
			case 3 :runOnUiThread(new Runnable() {
				@Override
				public void run() {
					HighScoreBanner.setVisibility(View.VISIBLE);
					AdRequest.Builder builder = new AdRequest.Builder();
					AdRequest ad = builder.build();
					HighScoreBanner.loadAd(ad);
				}
			});
				break;
			default: break;
		}
	}

	@Override
	public void hideBannerAd(int Screen) {
		switch (Screen) {
			case 1:
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						PauseBanner.setVisibility(View.INVISIBLE);
					}
				});
				break;
			case 2:
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						GameOverBanner.setVisibility(View.INVISIBLE);
					}
				});
				break;
			case 3:
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						HighScoreBanner.setVisibility(View.INVISIBLE);
					}
				});
				break;
			default: break;
		}
	}

	@Override
	public boolean isbannershown(int Screen) {
		boolean value = true;
		switch (Screen) {
			case 1 : value = (PauseBanner.getVisibility() == View.VISIBLE);
				break;
			case 2 : value = (GameOverBanner.getVisibility() == View.VISIBLE);
				break;
			case 3 : value =  (HighScoreBanner.getVisibility() == View.VISIBLE);
				break;
			default: break;
		}
		return value;
	}

	@Override
	public void ShareApp() {
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out this easy and fun to play Panda Game now!!!\nhttps://play.google.com/store/apps/details?id=com.anotherpandarunner");
		sendIntent.setType("text/plain");
		startActivity(Intent.createChooser(sendIntent, "Share Using"));
	}

}
