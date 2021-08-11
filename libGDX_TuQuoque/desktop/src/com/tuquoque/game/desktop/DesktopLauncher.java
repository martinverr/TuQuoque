package com.tuquoque.game.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.tuquoque.game.GameStarter;


/*
* Launcher per ambiente desktop, separato dal codice del gioco.
*
* Configurazione desktop seguente:
*/
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

		config.setTitle("Tu Quoque");
		config.setWindowedMode(1200, 675); //16:9
		//config.setResizable(false);
		//framerate maggiore a 60 non necessario per box2d
		config.setForegroundFPS(60);

		new Lwjgl3Application(new GameStarter(), config);
	}
}
