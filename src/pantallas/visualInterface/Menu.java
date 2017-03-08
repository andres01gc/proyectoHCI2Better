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

    private PImage imaFin;
    private boolean otroJ;
    private boolean resultadoYo;

    private PImage fondoBarras;

    private PImage[] imageConfianza;
    private PImage[] imageEnergia;

    private PImage imaGanas,
            imaPierdes;


    boolean finJuego = false;

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

        fondoBarras = app.loadImage("../data/pantallaJuego/barra/fondo.png");

        imageConfianza = new PImage[]{
                app.loadImage("../data/pantallaJuego/barra/c25.png"),
                app.loadImage("../data/pantallaJuego/barra/c50.png"),
                app.loadImage("../data/pantallaJuego/barra/c75.png"),
                app.loadImage("../data/pantallaJuego/barra/c100.png"),
        }


        ;
        imageEnergia = new PImage[]{
                app.loadImage("../data/pantallaJuego/barra/e25.png"),
                app.loadImage("../data/pantallaJuego/barra/e50.png"),
                app.loadImage("../data/pantallaJuego/barra/e75.png"),
                app.loadImage("../data/pantallaJuego/barra/e100.png"),
        };

        imaGanas = app.loadImage("../data/pantallaJuego/menu/ganas.png");
        imaPierdes = app.loadImage("../data/pantallaJuego/menu/pierdes.png");
    }


    @Override
    public void pintar() {
        app.textSize(15);
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

                break;

            case 6:
                app.background(56, 68, 102);
                app.imageMode(app.CORNER);
                if (resultadoYo) {
                    app.image(resultadoRonda[0], 0, 0);
                    app.textAlign(app.CENTER);

                } else {
                    app.image(resultadoRonda[1], 0, 0);
                }

                app.rectMode(app.CENTER);
                app.noStroke();

                if (resultadoYo && otroJ) {
                    app.text("¡Tu compañero también escogió la llave correcta!, el beneficio se dividirá /n +10", app.width / 2, 950);
                } else if (resultadoYo && !otroJ) {
                    app.text("Solo tu tienes la llave correcta. Has ganado el total del beneficio +20", app.width / 2, 950);
                    app.text("", app.width, 800);

                } else if (!resultadoYo && otroJ) {
                    app.text("Solo tu colega ha logrado obtener el beneficio +20 para él", app.width / 2, 950);
                } else if (!resultadoYo && !otroJ) {
                    app.text("Nadie tiene la llave correcta, el beneficio se destruirá ", app.width / 2, 950);
                }

                if (app.frameCount % 200 == 0) pantalla++;

                break;

            //pintar Barras de confianza
            case 7:
                pintarBarrasDeconfianza();
                break;

            case 8:
                app.imageMode(app.CORNER);
                app.image(imaFin, 0, 0);
                break;
//ganas
            case 9:
                app.imageMode(app.CORNER);
                app.image(imaGanas, 0, 0);
                break;
//pierdes
            case 10:
                app.imageMode(app.CORNER);
                app.image(imaPierdes, 0, 0);
                break;


        }

        int vida = 0;

        if (Logica.getTipoJ() == 0) {
            vida = Info.vidaClient;
        } else {
            vida = Info.vidaServer;
        }

        if (vida <= 0 && !finJuego) {
            pantalla = 10;
            envio("ganas:D");

            finJuego = true;
            Info.getInstance().guardarDatos();
        }

    }

    private void pintarBarrasDeconfianza() {

        app.imageMode(app.CORNER);
        app.image(fondoBarras, 0, 0);

        switch (indiceConfianza) {
            case 0:
                app.image(imageConfianza[0], 0, 0);
                break;

            case 25:
                app.image(imageConfianza[0], 0, 0);
                break;

            case 50:
                app.image(imageConfianza[1], 0, 0);
                break;

            case 75:
                app.image(imageConfianza[2], 0, 0);
                break;
            case 100:
                app.image(imageConfianza[3], 0, 0);
                break;
        }

        int vida = 0;

        if (Logica.getTipoJ() == 0) {
            vida = Info.vidaClient;
        } else {
            vida = Info.vidaServer;
        }


        if (vida <= 25) {
            app.image(imageEnergia[0], 0, 0);
        } else if (vida > 25) {
            app.image(imageEnergia[1], 0, 0);
        } else if (vida > 50) {
            app.image(imageEnergia[2], 0, 0);
        } else if (vida > 75) {
            app.image(imageEnergia[3], 0, 0);
        }

        app.fill(54, 51, 121);
        app.textSize(100);
        app.textAlign(app.CENTER);

        app.text(indiceConfianza + "%", 1350, 619);
        app.text(vida + "%", 580, 619);

//        app.rectMode(app.CENTER);
//        app.noStroke();
//
//        for (int i = 0; i < (indiceConfianza / 25); i++) {
//            switch (i) {
//                case 0:
//                    app.fill(206, 39, 0);
//                    break;
//
//                case 1:
//                    app.fill(206, 171, 0);
//                    break;
//
//                case 2:
//                    app.fill(206, 206, 0);
//                    break;
//
//                case 3:
//                    app.fill(141, 206, 0);
//                    break;
//            }
//            app.rect(303, 780 - (i * 125), 48, 112);
//        }
    }

    private void pintarRecomendacion() {
        System.out.println(app.mouseX + "     " + app.mouseY);

        app.fill(0, 30);
        app.imageMode(app.CENTER);
        app.image(d2, 915, 140);

        app.ellipse((app.width / 2), 150 + 23, 110, 110);
        app.image(Info.imasLlavesMenu[Info.getInstance().datossLlavesMenu.get(Info.rondaActual)[decisionRecomendado]], (app.width / 2), 150 + 23);

        j.pintarRecomendaciones();
        app.fill(255);
        app.text("¿Quieres confiar en lo que dijo tu colega o deseas valerte por tu cuenta? ", (app.width / 2), 900);

    }


    /**
     * Se encarga de Tomar la primera desicion
     */
    public void elegirRecomendacion() {
        app.noStroke();
        
        app.image(dUno, 543, 437);
        for (int i = 0; i < 2; i++) {
            int x = 792 + (i * 336), y = 593;
            int val = Info.getInstance().datossLlavesMenu.get(Info.rondaActual)[i];
            app.fill(255);
            app.strokeWeight(5);
            if (val == Info.getInstance().respuestasCorrectas[Info.rondaActual]) {
                app.stroke(0, 255, 0);
            } else {
                app.stroke(255, 0, 0);
            }
            if (app.dist(app.mouseX, app.mouseY, x, y) < 168) {
                app.fill(45);
                app.ellipse(x, y, 160, 160);
                app.fill(255);
                switch (i) {
                    case 0:
                        app.text("Deseo No Mentirle a mi colega", (app.width / 2), 900);
                        break;
                    case 1:
                        app.text("Quiero Mentirle a mi colega", (app.width / 2), 900);
                        break;
                }
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


                if (i == Info.respuestasCorrectas[Info.rondaActual]) {
                    envio("confianza:" + 25);
                    Info.newRow.setInt("Honestidad R" + Info.rondaActual, 25);
                    indiceConfianza += 25;
                } else {
                    Info.newRow.setInt("Honestidad R" + Info.rondaActual, 0);
                    envio("confianza:" + 0);
                }

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


    @Override
    public void mousePressed() {
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
                    Info.newRow.setInt("conviccion R" + Info.rondaActual, 25);
                    indiceConfianza += 25;
                } else {
                    Info.newRow.setInt("conviccion R" + Info.rondaActual, 0);
                    envio("confianza:" + 0);
                }


                if (j.cuartoSeleccionado == Info.respuestasCorrectas[Info.rondaActual]) {
                    envio("resultadoJugador:" + 1);
                } else {
                    envio("resultadoJugador:" + 0);
                }


                break;
            case 4:
                pantalla++;
                break;

            case 5:

                break;


            case 6:

                //    pantalla++;

                break;
            case 7:

                if (Info.rondaActual < 3) {
                    Info.rondaActual++;
                    Info.indicesConfianza.add(indiceConfianza);
                    AdministradorPantalla.cambiarPantalla(new PantallaJuego());
                } else {
                    pantalla = 8;
                    Info.getInstance().guardarDatos();

                }
                break;


            case 8:
                app.exit();
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
                    resultadoYo = (j.cuartoSeleccionado == Info.respuestasCorrectas[Info.rondaActual]);
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
                        Info.newRow.setInt("indiceConfianza R" + Info.rondaActual, indiceConfianza);


                        if (resultadoYo && otroJ) {
                            if (Logica.getTipoJ() == 0) {
                                Info.vidaClient += 20;
                            } else {
                                Info.vidaServer += 20;
                            }
                        } else if (resultadoYo) {
                            if (Logica.getTipoJ() == 0) {
                                Info.vidaClient -= 10;
                            } else {
                                Info.vidaServer -= 10;
                            }
                        }


                        if (resultadoYo && otroJ) {
                            if (Logica.getTipoJ() == 0) {
                                Info.vidaClient += 5;
                            } else {
                                Info.vidaServer += 5;
                            }
                        } else if (resultadoYo && !otroJ) {
                            if (Logica.getTipoJ() == 0) {
                                Info.vidaClient += 10;
                            } else {
                                Info.vidaServer += 10;
                            }
                        } else if (!resultadoYo && otroJ) {
                            if (Logica.getTipoJ() == 0) {
                                Info.vidaClient += -10;
                            } else {
                                Info.vidaServer += -10;
                            }
                        } else if (!resultadoYo && !otroJ) {

                            if (Logica.getTipoJ() == 0) {
                                Info.vidaClient += -10;
                            } else {
                                Info.vidaServer += -10;
                            }
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


            case "resultadoJugador":
                if (Integer.parseInt(resp[1]) == 1) {
                    otroJ = true;
                }
                break;

            case "ganas":
                if (Integer.parseInt(resp[1]) == 1) {
                    //otroJ = true;
                    pantalla = 9;
                }
                break;

        }
    }
}
