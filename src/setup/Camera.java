package setup;

import processing.core.PApplet;
import processing.core.PVector;
import root.Logica;
import root.server.MainServer;

public class Camera {

	static Seguible target;
	static PApplet app = Logica.getApp();

	public static void update(PVector pos) {
		app.translate(pos.x, pos.y);
	}
	
}
