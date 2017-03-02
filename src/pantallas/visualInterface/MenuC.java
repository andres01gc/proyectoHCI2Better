package pantallas.visualInterface;

import info.Info;
import pantallas.PantallaJuego;
import processing.core.PImage;
import red.ComunicacionCliente;
import setup.Interfaz;

import java.util.Observable;
import java.util.Observer;

public class MenuC extends Interfaz implements Observer {

    PantallaJuego j;
    private PImage dUno;
    private int pDecision = -1;
    private int dUnoSeleccionado = 0;
    private PImage[] imLlaves;
    private int turnoActual = 0;
    private boolean ready;
    private ComunicacionCliente c;
    private boolean recomendado = false;
    private PImage d2;
    private int decisionRecomendado;

    public MenuC(PantallaJuego pantallaJuego) {
        this.j = pantallaJuego;
    }

    @Override
    public void iniciar() {
        iniImgs();
        app.imageMode(app.CORNER);
        c = ComunicacionCliente.getInstance();
    }

    public void iniImgs() {
        dUno = app.loadImage("../data/pantallaJuego/menu/d1/d1.png");
        d2 = app.loadImage("../data/pantallaJuego/menu/d1/d2.png");

    }

    @Override
    public void pintar() {
        app.translate(0, 0);
        app.imageMode(app.CORNER);

        switch (pDecision) {
            case -1:
                app.text("sincronizando con el otro jugador", 800, 50);
                System.out.println();
                c.enviar("ready");
                break;

            case 0:
                app.text("aqui va una explicacion de la interaccion", 800, 50);
                break;

            case 1:
                elegirRecomendacion();
                break;

            case 2:
                app.text("esperando sugerencia del companero aparentemente", 800, 50);
                if (recomendado) pDecision++;
                break;

            case 3:
                pintarRecomendacion();
                break;

            case 4:
                break;

        }
    }

    private void pintarRecomendacion() {
        app.imageMode(app.CENTER);
        app.image(d2, app.width / 2, 150);

        app.ellipse((app.width / 2), 150 + 23, 110, 110);
        app.image(Info.imasLlavesMenu[Info.getInstance().datossLlavesMenu.get(turnoActual)[decisionRecomendado]], (app.width / 2), 150 + 23);
    }

    /**
     * Se encarga de Tomar la primera desicion
     */
    public void elegirRecomendacion() {
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
                ComunicacionCliente.getInstance().enviar("recomendacion:" + i);
                //  j.setRecomendacionOtroJugador(i);

                pDecision++;
                j.setDecidir(true);
                break;
            }
        }
    }

    @Override
    public void mousePressed() {
        //   System.out.println("se ha presionado el mouseMenu");
        switch (pDecision) {

            case 0:
                pDecision++;
                break;

            case 1:
                clickdUno();
                break;

            case 2:

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
        System.out.println("respC!");
        String res = (String) arg;
        String[] resp = res.split(":");

        switch (resp[0]) {
            case "ready":
                if (!ready) {
                    ready = true;
                    pDecision++;
                }
                break;

            case "recomendacion":
                recomendado = true;

                decisionRecomendado = Integer.parseInt(resp[1]);
                j.setRecomendacionOtroJugador(decisionRecomendado);
                break;
        }
    }
}
