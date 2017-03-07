package juego;

import java.util.ArrayList;

import info.Info;
import pantallas.PantallaJuego;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import setup.Pantalla;

public class Hexagon {
    private int tipoHex;
    private PApplet app = Pantalla.app;

    float xReal, yReal;
    float x, y;

    private float radio;
    private float ancho;
    private boolean selected;
    private PantallaJuego j;
    private PVector rPos;
    private PImage image;
    private int tipoTunel;
    private int anguloTunel;
    private int ladoEntrada;

    private float opa;
    private ArrayList<int[]> datosHexSelected;
    private float vida = 255;
    private PImage pared;

    public Hexagon(PantallaJuego j, float x, float y, float radio, int tipoHex, int ladoEntrada) {
        this.j = j;
        this.xReal = x;
        this.yReal = y;
        this.rPos = new PVector(x, y);
        this.radio = radio;
        this.ancho = (app.sqrt(3) / 2 * (radio * 2));
        selectHex(tipoHex);
        this.tipoHex = tipoHex;
        this.ladoEntrada = ladoEntrada;
        this.ladoEntrada += 3;
        if (this.ladoEntrada > 6) {
            this.ladoEntrada -= 6;
        }

        selected = true;
        this.anguloTunel = nAnguloTunel();

        pared = app.loadImage("../data/pantallaJuego/hexagonos/pared.png");
    }


    private void selectHex(int tipoHex) {
        switch (tipoHex) {

            //tipo de hexagonos aleatorios
            case 0:
                System.out.println("inicio de un hexagono de desicion");
                this.tipoTunel = (int) app.random(2);
                image = Info.imasHexRandom[this.tipoTunel];
                datosHexSelected = Info.getInstance().datosHexRandom;
                break;

            //tpo de hexagonos de Desiciones
            case 1:
                System.out.println("inicio de un hexagono de desicion");
                this.tipoTunel = (int) app.random(4);
                System.out.println("se cogio el camino " + this.tipoTunel);
                datosHexSelected = Info.getInstance().datosHexDescision;
                image = Info.imasHexDes[this.tipoTunel];
                break;

            //tipo de Hexagonos Llaves
            case 2:
                System.out.println("inicio de un hexagono de desicion");
                this.tipoTunel = (int) app.random(4);
                System.out.println("se cogio el camino " + this.tipoTunel);
                datosHexSelected = Info.getInstance().datosHexLlaves;
                image = Info.imasHexLlaves[this.tipoTunel];
                break;
        }

    }

    private int nAnguloTunel() {
        for (int i = 0; i < 6; i++) {
            for (int var = 0; var < datosHexSelected.get(tipoTunel).length; var++) {
                int dt = datosHexSelected.get(tipoTunel)[var] + i;

                if (dt > 6) {
                    dt -= 6;
                }

                if (dt == ladoEntrada) {
                    //  System.out.println("sisaj!" + dt);
                    return i;
                }
            }
        }

        return 0;
    }


    public void pintarFront(float xCenter, float yCenter, PVector posJ) {
        // varia la opacidad de acuerdo a la posicion del jugador
        float d = app.dist(x, y, posJ.x, posJ.y);

        // System.out.println(d);
        opa = app.map(d, 0, 800, 200, 0);

        x = xReal - xCenter;
        y = yReal - yCenter;

        app.fill(255);
        //  dHexagon(x, y);

        app.imageMode(app.CENTER);
        app.pushMatrix();
        app.translate(x, y);
        app.rotate(((2 * app.PI) / 12) + ((2 * app.PI) / 6) * (anguloTunel + 0));

        app.tint(255, vida);
        app.image(pared, 0, 0);
        app.noTint();

        if (!selected) vida--;

        app.popMatrix();

    }

    public int getTipoHex() {
        return tipoHex;
    }

    public void pintar(float xCenter, float yCenter, PVector posJ) {

        // varia la opacidad de acuerdo a la posicion del jugador
        float d = app.dist(x, y, posJ.x, posJ.y);

        // System.out.println(d);
        opa = app.map(d, 0, 800, 200, 0);

        x = xReal - xCenter;
        y = yReal - yCenter;

        app.fill(255);
        //  dHexagon(x, y);

        app.imageMode(app.CENTER);
        app.pushMatrix();
        app.translate(x, y);
        app.rotate(((2 * app.PI) / 12) + ((2 * app.PI) / 6) * (anguloTunel + 0));

        app.tint(255, vida);
        app.image(image, 0, 0);
        app.noTint();

        if (!selected) vida--;

        app.popMatrix();

    }

    public void dHexagon(float x, float y) {

        app.beginShape();
        float angle = app.TWO_PI / 6;

        for (float a = app.PI / 6; a < app.TWO_PI; a += angle) {
            float vx = x + app.cos(a) * radio;
            float vy = y + app.sin(a) * radio;
            app.vertex(vx, vy);
        }

        app.endShape(app.CLOSE);

    }

    public void drawRecomendaciones(int turno, int recomendacion) {
        float angle = app.TWO_PI / 6;


        for (int var = 0, numero = 0; var < datosHexSelected.get(tipoTunel).length; var++) {
            int i = datosHexSelected.get(tipoTunel)[var] + anguloTunel;
            if (i > 6)
                i -= 6;


            // bloquea seleccionar las misma por la que entraste.
            if (i == ladoEntrada)
                continue;

            float xVecino = app.cos(angle * i) * ancho;
            float yVecino = app.sin(angle * i) * ancho;


            if (app.dist(app.mouseX, app.mouseY, x + xVecino, y + yVecino) < ancho / 2) {
                app.fill(255, 20);
            } else {
                app.fill(255, 10);
            }
            app.noStroke();
            dHexagon(x + xVecino, y + yVecino);
            app.fill(0);
            app.image(Info.imasLlavesMenu[Info.getInstance().datossLlavesMenu.get(j.getRondaActual())[numero]], x + xVecino, y + yVecino);
            app.text(numero, x + xVecino + 50, y + yVecino);
            numero++;
        }
    }

    public void setTipoHex(int tipoHex) {
        this.tipoHex = tipoHex;
    }

    public Hexagon seleccionarVecino() {
        float angle = app.TWO_PI / 6;

        for (int var = 0, numero = 0; var < datosHexSelected.get(tipoTunel).length; var++) {
            int i = datosHexSelected.get(tipoTunel)[var] + anguloTunel;
            if (i > 6)
                i -= 6;


            // bloquea seleccionar las misma por la que entraste.
            if (i == ladoEntrada)
                continue;

            float xVecino = app.cos(angle * i) * ancho;
            float yVecino = app.sin(angle * i) * ancho;


            if (app.dist(app.mouseX, app.mouseY, x + xVecino, y + yVecino) < ancho / 2) {
                this.selected = false;

                Hexagon h = new Hexagon(j, xReal + xVecino, yReal + yVecino, radio, 0, i);
                System.out.println("se ha seleccionado la puerta " + numero);
                j.recomendacionOtroJugador = numero;
                return h;
            }

            numero++;
        }
        return null;
    }

    public void seleccionAleatoria(int tipoT) {
        float angle = app.TWO_PI / 6;

        for (int var = 0; var < datosHexSelected.get(tipoTunel).length; var++) {
            int i = datosHexSelected.get(tipoTunel)[var] + anguloTunel;
            if (i > 6)
                i -= 6;


            float xVecino = app.cos(angle * i) * ancho;
            float yVecino = app.sin(angle * i) * ancho;

            // bloquea seleccionar las misma por la que entraste.
            if (i == ladoEntrada)
                continue;

            this.selected = false;
            Hexagon h = new Hexagon(j, xReal + xVecino, yReal + yVecino, radio, tipoT, i);

            j.sethSelected(h);
            j.getHexagons().add(h);
            break;
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public PVector getrPos() {
        return rPos;
    }

    public float getVida() {
        return vida;
    }


}

