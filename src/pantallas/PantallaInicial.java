package pantallas;

import processing.core.PImage;
import setup.AdministradorPantalla;
import setup.Pantalla;

public class PantallaInicial extends Pantalla {

    private PImage fondo;

    @Override
    public void iniciar() {
        app.textSize(50);
        inicializarImgs();
        app.textAlign(app.CENTER, app.CENTER);
    }

    @Override
    public void pintar() {
        app.background(200);
        app.image(fondo, 0, 0);
    }

    @Override
    public void finalizar() {
    }


    public void inicializarImgs() {
        fondo = app.loadImage("data/inicio/pantalla_inicio.png");
    }

    @Override
    public void mousePressed() {
        AdministradorPantalla.cambiarPantalla(new Instrucciones());
        super.mousePressed();
    }

    @Override
    public void KeyPressed() {
        super.KeyPressed();
        AdministradorPantalla.cambiarPantalla(new Instrucciones());
    }
}
