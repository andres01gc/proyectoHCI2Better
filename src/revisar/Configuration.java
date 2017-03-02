package revisar;

import pantallas.PantallaJuego;
import processing.core.PApplet;
import root.Logica;
import root.server.MainServer;
import setup.Pantalla;

public class Configuration {
	static PApplet app;
	static Configuration c;
	public static Pantalla pantallaInicial= new PantallaJuego();

	public Configuration() {
		app = Logica.getApp();

	}

	public Configuration getInstance() {
		if (c == null) {
			c = new Configuration();
		}
		return c;
	}

	public static void pantalla() {

	}

	public static PApplet getApp() {
		return app;
	}

	public static void setApp(PApplet app) {
		Configuration.app = app;
	}

}
