package pantallas;

import processing.core.PImage;
import setup.AdministradorPantalla;
import setup.Pantalla;

public class PantallaInicial extends Pantalla {

    private PImage fondo;

    @Override
    public void iniciar() {
        // TODO Auto-generated method stub
        app.textSize(50);
        inicializarImgs();
        app.textAlign(app.CENTER, app.CENTER);
    }

    @Override
    public void pintar() {

        app.background(200);
        app.image(fondo, 0, 0);

        app.text("presiona cualquier tecla para continuar", 960, 905);
    }

    @Override
    public void finalizar() {

    }


    public void inicializarImgs() {
        fondo = app.loadImage("data/inicio/fondo.png");


    }

    @Override
    public void mousePressed() {
        // TODO Auto-generated method stub
        AdministradorPantalla.cambiarPantalla(new Instrucciones());
        super.mousePressed();
    }

    @Override
    public void KeyPressed() {
        super.KeyPressed();
        AdministradorPantalla.cambiarPantalla(new Instrucciones());
    }
}
