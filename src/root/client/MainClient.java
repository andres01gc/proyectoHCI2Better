package root.client;

import processing.core.PApplet;
import root.Logica;
import setup.Pantalla;
import setup.ProcessingEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainClient extends PApplet {
	private Logica logica;
	//protected static PApplet app;
	public static List<ProcessingEvent> processingEvents = Collections
			.synchronizedList(new ArrayList<ProcessingEvent>());

	// metodo para poder exportarse como una aplicaci�n, NO TOCAR
	static public void main(String[] passedArgs) {
		String[] appletArgs = new String[] { "root.client.MainClient" };
		if (passedArgs != null) {
			PApplet.main(concat(appletArgs, passedArgs));
		} else {
			PApplet.main(appletArgs);
		}
	}

	public void settings() {
		 fullScreen(FX2D);
		//size(1920, 1080,FX2D);
		smooth();

	}

	/**
	 * Configuraciones directas de processing con su lienzo.
	 */

	public void setup() {

		Pantalla.app = this;
	//	app = this;
		logica = new Logica(this,1);

	}

	public void draw() {
		background(255);
		logica.pintar();
	}

	public void mousePressed() {
		for (ProcessingEvent p : processingEvents) {
			p.mousePressed();
		}
		logica.mousePressed();
	}

	public void mouseReleased() {
		for (ProcessingEvent p : processingEvents) {
			p.mouseReleased();
		}
		logica.mouseReleased();
	}

	public void mouseDragged() {
		for (ProcessingEvent p : processingEvents) {
			p.mouseDragged();
		}
		logica.mouseDragged();
	}

	public void keyPressed() {
		for (ProcessingEvent p : processingEvents) {
			p.keyPressed();
		}
		logica.keyPressed();
	}
//
//	public static PApplet getApp() {
//		return app;
//	}
//
//	public static void setApp(PApplet app) {
//		MainClient.app = app;
//	}
}
