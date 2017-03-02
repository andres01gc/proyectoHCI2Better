package setup;

import processing.core.PApplet;
import root.Logica;
import root.server.MainServer;

/**
 * Esta clase se encarga de tneer todos los metdoso necesarios para una
 * pantalla. cualquiera que herede de esta, se podr� visualizar. todas, puenden
 * observar y ser hilos.
 * 
 * @author Personal
 *
 */

public abstract class Interfaz extends Thread {

	public static PApplet app = Logica.getApp();

	protected boolean vivo = true;

	public abstract void iniciar();

	public abstract void pintar();

	public abstract void finalizar();

	public void mousePressed() {

	};

	public void mouseDragged() {
	};

	public void mouseReleased() {
	};

	public void KeyPressed() {
	};

}
