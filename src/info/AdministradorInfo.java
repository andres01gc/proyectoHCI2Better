package info;

import processing.core.PApplet;
import processing.core.PImage;
import setup.Pantalla;

class AdministradorInfo {
	public static PApplet app = Pantalla.app;

	public static PImage PLAYER;
	public static PImage[] tuneles;

	AdministradorInfo() {
		PLAYER = app.loadImage("");

	}

	static public void inicializarTuneles() {
		tuneles = new PImage[2];
		tuneles[0] = app.loadImage("../data/pantallaJuego/0.png");
		tuneles[1] = app.loadImage("../data/pantallaJuego/1.png");
	}

}
