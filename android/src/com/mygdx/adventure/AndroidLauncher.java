package com.mygdx.adventure;

import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class AndroidLauncher extends AndroidApplication implements androidMethods {
	InterstitialAd ads;
	myGame gameStarter;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		makeToast("выходите через меню паузы! \n что бы сохранить игру!");
		gameStarter = new myGame();
		config.useAccelerometer = false;
		config.useCompass = false;
		config.useGyroscope = false;
		initialize(gameStarter, config);

		gameStarter.setAds(this);
		MobileAds.initialize(this,"ca-app-pub-4576810138516167~8462447481");
		ads = new InterstitialAd(this);
		ads.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
		AdRequest adRequest = new AdRequest.Builder().build();
		ads.loadAd(adRequest);

		myGame.Android = true;
	}
	public void makeToast(String text){
		Toast toast = Toast.makeText(getApplicationContext(),
				text, Toast.LENGTH_LONG);
		toast.show();
	}
	@Override
	public void showInter() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ads.show();
				AdRequest adRequest = new AdRequest.Builder().build();
				ads.loadAd(adRequest);
			}
		});
	}

	@Override
	public void showToast(String text) {
		makeToast(text);
	}
}
