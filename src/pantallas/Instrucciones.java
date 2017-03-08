package pantallas;

import processing.core.PImage;
import setup.AdministradorPantalla;
import setup.Pantalla;

public class Instrucciones extends Pantalla {

    private PImage fondo;
    private int pantalla;
    private PImage fondo2;
    private PImage fondo3;

    @Override
    public void iniciar() {
        // TODO Auto-generated method stub
        inicializarImgs();
      //  System.out.println("hey");
    }

    @Override
    public void pintar() {
        app.fill(0);

        switch (pantalla) {
            case 0:
                app.image(fondo, 0, 0);

                break;

            case 1:
                app.image(fondo2, 0, 0);

                break;
            case 2:
                app.image(fondo3, 0, 0);

                break;


        }
        // TODO Auto-generated method stub
    }

    @Override
    public void finalizar() {
        // TODO Auto-generated method stub
    }

    public void inicializarImgs() {
        fondo = app.loadImage("../data/instrucciones/fondoins.png");
        fondo2 = app.loadImage("../data/instrucciones/fondoins2.png");
        fondo3 = app.loadImage("../data/instrucciones/fondoins3.png");

    }

    @Override
    public void mousePressed() {
        switch (pantalla) {
            case 0:
                pantalla++;
                break;

            case 1:
                pantalla++;
                break;

            case 2:
                AdministradorPantalla.cambiarPantalla(new PantallaJuego());

                break;


        }
    }
}
