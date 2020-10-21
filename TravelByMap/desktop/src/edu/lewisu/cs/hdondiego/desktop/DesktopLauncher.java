package edu.lewisu.cs.hdondiego.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;

import edu.lewisu.cs.hdondiego.TravelByMap;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.initialBackgroundColor = Color.GREEN; // making the background color green
		new LwjglApplication(new TravelByMap(), config);
	}
}
