package pantallas.visualInterface;

import info.Info;
import pantallas.PantallaJuego;
import processing.core.PImage;
import processing.core.PVector;
import red.ComunicacionCliente;
import red.ComunicacionServidor;
import root.Logica;
import setup.Interfaz;

import java.util.Observable;
import java.util.Observer;

public class Menu extends Interfaz implements Observer {

    PantallaJuego j;
    private PImage dUno;
    private int pantalla = -1;
    private int dUnoSeleccionado = 0;
    private PImage[] imLlaves;
    private int turnoActual = 0;
    private boolean ready;
    private boolean recomendado = false;
    private PImage d2;
    private int decisionRecomendado;
    private PVector posLlaveCogida;

    private PImage fondoPruebaSeleccion;
    private PImage llave;
    private boolean llaveCogida;
    PImage[] resultadoRonda;
    private PImage esperando;

    public Menu(PantallaJuego pantallaJuego) {
        this.j = pantallaJuego;
    }


    @Override
    public void iniciar() {
        iniImgs();
        app.imageMode(app.CORNER);

        posLlaveCogida = new PVector(1373, 765);

    }

    public void iniImgs() {
        dUno = app.loadImage("../data/pantallaJuego/menu/d1/d1.png");
        d2 = app.loadImage("../data/pantallaJuego/menu/d1/d2.png");

        fondoPruebaSeleccion = app.loadImage("../data/pantallaJuego/menu/pruebaDeLlave/atinarSel.png");

        llave = app.loadImage("../data/pantallaJuego/menu/pruebaDeLlave/llave.png");

        resultadoRonda = new PImage[]{
                app.loadImage("../data/pantallaJuego/menu/mala.png"),
                app.loadImage("../data/pantallaJuego/menu/buena.png")
        };

        esperando = app.loadImage("../data/pantallaJuego/esperando_conexion.png");
    }

    @Override
    public void pintar() {
        app.translate(0, 0);
        app.imageMode(app.CORNER);

        switch (pantalla) {
            case -1:
                app.image(esperando, 0, 0);


                if (Logica.getTipoJ() == 1) {
                    ComunicacionCliente.getInstance().enviar("ready");
                }
                break;

            case 0:
                app.text("¿aqui va una explicacion de la interaccion?", 800, 50);
                break;

            case 1:
                elegirRecomendacion();
                break;

            case 2:
                app.text("esperando sugerencia del companero aparentemente", 800, 50);
                if (recomendado) pantalla++;
                break;

            case 3:
                pintarRecomendacion();


                break;

            case 4:
                app.text("esperando a que llegue", 800, 50);
                if (j.isLlaveCogida()) pantalla++;
                break;

            case 5:
                app.imageMode(app.CORNER);
                app.image(fondoPruebaSeleccion, 0, 0);
                app.imageMode(app.CENTER);

                PVector p = posLlaveCogida;
                if (llaveCogida) p = new PVector(app.mouseX, app.mouseY);
                app.image(llave, p.x, p.y);
                break;

            //cuando gana
            case 6:
                app.imageMode(app.CORNER);
                app.image(resultadoRonda[j.recomendacionOtroJugador], 0, 0);
                break;

            //cuando pierde
            case 7:
                break;
        }
    }

    private void pintarRecomendacion() {
        app.imageMode(app.CENTER);
        app.image(d2, app.width / 2, 150);

        app.ellipse((app.width / 2), 150 + 23, 110, 110);
        app.image(Info.imasLlavesMenu[Info.getInstance().datossLlavesMenu.get(j.getRondaActual())[decisionRecomendado]], (app.width / 2), 150 + 23);

        j.pintarRecomendaciones();

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
            app.image(Info.imasLlavesMenu[Info.getInstance().datossLlavesMenu.get(j.getRondaActual())[i]], x, y);
        }
    }


    void clickdUno() {
        for (int i = 0; i < 2; i++) {
            int x = 792 + (i * 336), y = 593;

            if (app.dist(app.mouseX, app.mouseY, x, y) < 168) {
                System.out.println(" se ha seleccionado la llave " + i);
                dUnoSeleccionado = i;

                //PRUEBA!
                if (Logica.getTipoJ() == 1)
                    ComunicacionCliente.getInstance().enviar("recomendacion:" + i);

                if (Logica.getTipoJ() == 0)
                    ComunicacionServidor.getInstance().enviar("recomendacion:" + i);

                //  j.setRecomendacionOtroJugador(i);

                pantalla++;
                j.setDecidir(true);
                break;
            }
        }
    }

    @Override
    public void mousePressed() {
        //   System.out.println("se ha presionado el mouseMenu");
        switch (pantalla) {

            case 0:
                pantalla++;
                break;

            case 1:
                clickdUno();
                break;

            case 2:

                break;

            case 3:
                if (j.selecnuevoCamino()) pantalla++;
                break;
            case 4:
                pantalla++;
                break;

            case 5:
                //  pantalla++;
                break;
        }
    }

    @Override
    public void mouseDragged() {
        switch (pantalla) {

            case 0:
                break;

            case 1:
                break;

            case 2:

                break;

            case 3:
                break;
            case 4:
                break;

            case 5:
                if (app.dist(app.mouseX, app.mouseY, posLlaveCogida.x, posLlaveCogida.y) < 100) {
                    llaveCogida = true;
                }
                break;


        }
    }


    @Override
    public void mouseReleased() {
        switch (pantalla) {

            case 0:
                break;

            case 1:
                break;

            case 2:

                break;

            case 3:
                break;
            case 4:
                break;

            case 5:
                if (llaveCogida) {
                    if (app.dist(app.mouseX, app.mouseY, 616, 616) < 100) {
                        pantalla = 6;
                    }
                }
                llaveCogida = false;
                break;
        }


    }


    @Override
    public void KeyPressed() {
        // turnoActual++;
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

                    if (Logica.getTipoJ() == 0) {
                        ComunicacionServidor.getInstance().enviar("ready");
                    }
                    pantalla++;
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
