package pantallas.visualInterface;

import info.Info;
import pantallas.PantallaJuego;
import processing.core.PImage;
import setup.Interfaz;

import java.util.Observable;
import java.util.Observer;

public class Menu extends Interfaz implements Observer{

    PantallaJuego j;
    private PImage dUno;
    private int pDecision = 0;
    private int dUnoSeleccionado = 0;
    private PImage[] imLlaves;
    private int turnoActual = 0;

    public Menu(PantallaJuego pantallaJuego) {
        this.j = pantallaJuego;
    }

    @Override
    public void iniciar() {
        iniImgs();
        app.imageMode(app.CORNER);
    }

    public void iniImgs() {
        dUno = app.loadImage("data/pantallaJuego/menu/d1/d1.png");


    }

    @Override
    public void pintar() {

        app.translate(0, 0);
        app.imageMode(app.CORNER);

        switch (pDecision) {
            case 0:
                app.text("aqui va una explicacion de la interaccion", 800, 50);
                break;

            case 1:
                dUno();

                break;

            case 2:

//                if (la informacion del otro jugador ya llego){
//                pDecision++
                //}

                app.text("esperando sugerencia del companero aparentemente", 800, 50);

                break;


            case 3:
                break;
        }

    }

    /**
     * Se encarga de Tomar la primera desicion
     */
    public void dUno() {
        app.noStroke();
        app.image(dUno, 0, 0);
        for (int i = 0; i < 2; i++) {

            int x = 792 + (i * 336), y = 593;

            if (app.dist(app.mouseX, app.mouseY, x, y) < 168) {
                app.fill(45);
                app.ellipse(x, y, 160, 160);
            }

            app.imageMode(app.CENTER);
            app.image(Info.imasLlavesMenu[Info.getInstance().datossLlavesMenu.get(turnoActual)[i]], x, y);
        }
    }


    void clickdUno() {
        for (int i = 0; i < 2; i++) {
            int x = 792 + (i * 336), y = 593;

            if (app.dist(app.mouseX, app.mouseY, x, y) < 168) {


                System.out.println(" se ha seleccionado la llave " + i);
                dUnoSeleccionado = i;

                //PRUEBA!
                j.setRecomendacionOtroJugador(i);

                pDecision++;
                j.setDecidir(true);
                break;

            }
        }
    }

    @Override
    public void mousePressed() {
        System.out.println("se ha presionado el mouseMenu");
        switch (pDecision) {

            case 0:
                pDecision++;
                break;

            case 1:
                clickdUno();
                break;

            case 2:
                pDecision++;
                break;

            case 3:
                break;

        }
    }

    @Override
    public void KeyPressed() {

        turnoActual++;
    }

    @Override
    public void finalizar() {

    }

    @Override
    public void update(Observable o, Object arg) {



    }
}
