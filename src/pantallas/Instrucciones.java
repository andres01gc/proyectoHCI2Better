package pantallas;

import processing.core.PImage;
import setup.AdministradorPantalla;
import setup.Pantalla;

public class Instrucciones extends Pantalla {

    private PImage fondo;

    @Override
    public void iniciar() {
        // TODO Auto-generated method stub
        inicializarImgs();
        System.out.println("hey");
    }

    @Override
    public void pintar() {
        app.fill(0);
        app.image(fondo, 0, 0);
        //	app.text("aqui deberian de ir las instrucciones", 50, 50);
        // TODO Auto-generated method stub

    }

    @Override
    public void finalizar() {

        // TODO Auto-generated method stub

    }

    public void inicializarImgs() {
        fondo = app.loadImage("../data/instrucciones/fondoins.png");


    }

    @Override
    public void mousePressed() {
        AdministradorPantalla.cambiarPantalla(new PantallaJuego());

    }
}
