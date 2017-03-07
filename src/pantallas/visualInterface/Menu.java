package pantallas.visualInterface;

import info.Info;
import pantallas.PantallaJuego;
import processing.core.PImage;
import processing.core.PVector;
import processing.data.Table;
import processing.data.TableRow;
import red.ComunicacionS;
import red.ComunicacionC;
import root.Logica;
import setup.AdministradorPantalla;
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
    private int indiceConfianza = 0;
    private Table table;
    private TableRow newRow;
    private PImage imaFin;

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
                app.loadImage("../data/pantallaJuego/menu/buena.png"),
                app.loadImage("../data/pantallaJuego/menu/mala.png")
        };

        esperando = app.loadImage("../data/pantallaJuego/esperando_conexion.png");


        imaFin = app.loadImage("../data/pantallaJuego/final.png");
        loadTable();
    }


    public void loadTable() {
        if (Logica.getTipoJ() == 0) {
            table = app.loadTable("../data/saves/datosS.csv", "header");

        } else {
            table = app.loadTable("../data/saves/datosC.csv", "header");
        }

        newRow = table.addRow(table.addRow());

        newRow.setInt("id", table.getRowCount() - 1);
        newRow.setInt("energia inicial", j.energia);


//        pantalla = 5;
//        indiceConfianza = 100;
    }

    @Override
    public void pintar() {
        app.translate(0, 0);
        app.imageMode(app.CORNER);

        switch (pantalla) {
            case -1:
                app.image(esperando, 0, 0);
                if (Logica.getTipoJ() == 1) {
                    ComunicacionS.getInstance().enviar("ready");
                }

                break;

            case 0:
                //    app.text("¿aqui va una explicacion de la interaccion?", 800, 50);
                pantalla++;
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
                //      app.text("esperando a que llegue", 800, 50);
                if (j.isLlaveCogida()) pantalla++;
                break;

            case 5:
                app.background(56, 68, 102);

                app.imageMode(app.CORNER);
                app.image(fondoPruebaSeleccion, 0, 0);
                app.imageMode(app.CENTER);

                PVector p = posLlaveCogida;
                if (llaveCogida) p = new PVector(app.mouseX, app.mouseY);
                app.image(llave, p.x, p.y);


                app.text("el indice de confianza fue de: " + indiceConfianza, 100, 100);
                //pintar barra de


                pintarBarra();

                break;

            //cuando gana
            case 6:
                app.background(56, 68, 102);
                app.imageMode(app.CORNER);
                // System.out.println(j.cuartoSeleccionado + "       " + Info.respuestasCorrectas[j.getRondaActual()]);
                if (j.cuartoSeleccionado == Info.respuestasCorrectas[j.getRondaActual()]) {
                    System.out.println("respuesta correcta");
                    app.image(resultadoRonda[0], 0, 0);
                } else {
                    app.image(resultadoRonda[1], 0, 0);
                }
                app.rectMode(app.CENTER);
                app.noStroke();

                pintarBarra();

                break;

            //pintar Barras de confianza
            case 7:
                app.imageMode(app.CORNER);
                app.image(imaFin, 0, 0);

                break;

            case 8:

                break;
        }

    }

    private void pintarBarra() {
        app.rectMode(app.CENTER);
        app.noStroke();

        for (int i = 0; i < (indiceConfianza / 25); i++) {

            switch (i) {
                case 0:
                    app.fill(206, 39, 0);
                    break;

                case 1:
                    app.fill(206, 171, 0);
                    break;

                case 2:
                    app.fill(206, 206, 0);
                    break;

                case 3:
                    app.fill(141, 206, 0);
                    break;
            }

            app.rect(303, 780 - (i * 125), 48, 112);
        }
    }

    private void pintarRecomendacion() {
        System.out.println(app.mouseX + "     " + app.mouseY);

        app.fill(0, 30);
        app.imageMode(app.CENTER);
        app.image(d2, 915, 140);

        app.ellipse((app.width / 2), 150 + 23, 110, 110);
        app.image(Info.imasLlavesMenu[Info.getInstance().datossLlavesMenu.get(j.getRondaActual())[decisionRecomendado]], (app.width / 2), 150 + 23);

        j.pintarRecomendaciones();

    }

    /**
     * Se encarga de Tomar la primera desicion
     */
    public void elegirRecomendacion() {
        app.noStroke();
        app.image(dUno, 543, 437);

        for (int i = 0; i < 2; i++) {

            int x = 792 + (i * 336), y = 593;

            int val = Info.getInstance().datossLlavesMenu.get(j.getRondaActual())[i];

            app.strokeWeight(5);
            if (val == Info.getInstance().respuestasCorrectas[j.getRondaActual()]) {
                app.stroke(0, 255, 0);
            } else {
                app.stroke(255, 0, 0);
            }

            if (app.dist(app.mouseX, app.mouseY, x, y) < 168) {
                app.fill(45);
                app.ellipse(x, y, 160, 160);
            }

            app.imageMode(app.CENTER);
            app.image(Info.imasLlavesMenu[val], x, y);
        }
    }

    public void envio(String res) {
        if (Logica.getTipoJ() == 1)
            ComunicacionS.getInstance().enviar(res);

        if (Logica.getTipoJ() == 0)
            ComunicacionC.getInstance().enviar(res);

    }

    void clickdUno() {
        for (int i = 0; i < 2; i++) {
            int x = 792 + (i * 336), y = 593;

            if (app.dist(app.mouseX, app.mouseY, x, y) < 168) {
                System.out.println(" se ha seleccionado la llave " + i);
                dUnoSeleccionado = i;

                envio("recomendacion:" + i);


                if (i == Info.respuestasCorrectas[j.getRondaActual()]) {
                    envio("confianza:" + 25);
                    newRow.setInt("Honestidad R" + j.getRondaActual(), 25);

                    indiceConfianza += 25;
                } else {
                    newRow.setInt("Honestidad R" + j.getRondaActual(), 0);
                    envio("confianza:" + 0);
                }


                //  j.setRecomendacionOtroJugador(i);

                pantalla++;
                j.setDecidir(true);


                if (Logica.getTipoJ() == 0) {
                    Info.vidaClient -= 10;
                } else {
                    Info.vidaServer -= 10;
                }

                break;
            }
        }
    }

//    public void reiniciar(){
//
//
//    }

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

                if (j.cuartoSeleccionado == j.recomendacionOtroJugador) {
                    envio("confianza:" + 25);
                    newRow.setInt("conviccion R" + j.getRondaActual(), 25);
                    indiceConfianza += 25;
                } else {
                    newRow.setInt("conviccion R" + j.getRondaActual(), 0);
                    envio("confianza:" + 0);
                }

                break;
            case 4:
                pantalla++;
                break;

            case 5:
                //  pantalla++;
                break;


            case 6:


                if (Info.rondaActual < 3) {
                    Info.rondaActual++;

                    AdministradorPantalla.cambiarPantalla(new PantallaJuego());
                } else {
                    pantalla = 7;
                }

                break;
            case 7:
                app.exit();
                //  pantalla++;
                break;


            case 8:

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
                        newRow.setInt("indiceConfianza R" + j.getRondaActual(), indiceConfianza);

                        //prueba de la primera ronda

                        if (Logica.getTipoJ() == 0) {
                            app.saveTable(table, "../data/saves/datosS.csv");

                            System.out.println("se supone que guarda!!");
                        } else {
                            app.saveTable(table, "../data/saves/datosC.csv");
                        }
                    }
                }
                llaveCogida = false;
                break;
        }
    }


    @Override
    public void KeyPressed() {

    }

    @Override
    public void finalizar() {

    }

    @Override
    public void update(Observable o, Object arg) {
        String res = (String) arg;
        String[] resp = res.split(":");

        switch (resp[0]) {
            case "ready":
                if (!ready) {
                    ready = true;

                    if (Logica.getTipoJ() == 0) {
                        ComunicacionC.getInstance().enviar("ready");
                    }
                    pantalla++;
                }
                break;

            case "recomendacion":
                recomendado = true;
                decisionRecomendado = Integer.parseInt(resp[1]);
                j.setRecomendacionOtroJugador(decisionRecomendado);
                break;

            case "confianza":

                indiceConfianza += Integer.parseInt(resp[1]);

                break;
        }
    }
}
