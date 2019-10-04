package com.anotherpandarunner;

/**
 * Created by HP on 12-02-2018.
 */

public interface AdControlAndShare {

    public void showBannerAd(int Screen);
    public void hideBannerAd(int Screen);
    public boolean isInternetConnected();
    public void showInterstitialAd (Runnable then);
    public boolean isbannershown(int Screen);
    public void ShareApp();
}
