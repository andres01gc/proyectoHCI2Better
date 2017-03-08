package info;

import processing.data.Table;
import processing.data.TableRow;
import root.Logica;
import setup.Pantalla;
import processing.core.*;

import java.util.ArrayList;

public class Info {
    static PApplet app = Pantalla.app;
    private static Info info;

    private final int pareja = 0;

    public static int vidaClient = 70;
    public static int vidaServer = 90;

    public static int rondaActual = 0;
    public static PImage PLAYER;
    public static PImage[] imasHexRandom;
    public static PImage[] imasHexDes;
    public static PImage[] imasHexLlaves;

    public static PImage[] imasLlavesMapa;

    public ArrayList<int[]> datosHexRandom;
    public ArrayList<int[]> datosHexDescision;
    public ArrayList<int[]> datosHexLlaves;


    public static PImage[] imasLlavesMenu;
    public ArrayList<int[]> datossLlavesMenu;
    public static PImage barraVida;
    public static int[] respuestasCorrectas;

    public static ArrayList<Integer> indicesConfianza = new ArrayList<>();

    public static Table table;
    public static TableRow newRow;


    public void iniDatosLlavesMenu() {
        //cada uno representa la combinacion de llaves, estas deberian de ser aleatorias pero sin repetir.
        //el tamano del arreglo representa la cantidad de turnos.
        datossLlavesMenu = new ArrayList<int[]>();
        datossLlavesMenu.add(new int[]{0, 1});
        datossLlavesMenu.add(new int[]{1, 0});
        datossLlavesMenu.add(new int[]{0, 1});
        datossLlavesMenu.add(new int[]{1, 0});
        respuestasCorrectas = new int[]{0, 1, 0, 1, 0};

    }


    public void cargarData() {
        table = app.loadTable("../data/saves/noborrar.csv", "header");


        newRow = table.addRow(table.addRow());

        newRow.setInt("id", table.getRowCount() - 1);


        int vida = 0;

        if (Logica.getTipoJ() == 0) {
            vida = Info.vidaClient;
        } else {
            vida = Info.vidaServer;
        }
        newRow.setInt("energia inicial", vida);
    }

    private Info() {
        PLAYER = app.loadImage("");
        iniciarHexRandom();
        iniDatosHexRandom();

        iniciarHexDes();
        iniDatosHexDecision();


        iniciarHexLlaves();
        iniDatosHexLlaves();

        iniDatosLlavesMenu();


        iniciarLlavesMapa();

        cargarData();
        barraVida = app.loadImage("../data/pantallaJuego/vida.png");

    }

    public void iniDatosHexRandom() {

        imasLlavesMenu = new PImage[]{
                app.loadImage("../data/pantallaJuego/menu/d1/llaves/llave0.png"),
                app.loadImage("../data/pantallaJuego/menu/d1/llaves/llave1.png"),
        };

        imasHexRandom = new PImage[]{
                app.loadImage("data/pantallaJuego/hexagonos/aleatorio/0.png"),
                app.loadImage("data/pantallaJuego/hexagonos/aleatorio/1.png"),
                app.loadImage("data/pantallaJuego/hexagonos/aleatorio/2.png")
        };

        // aquí se deben ingresar los datos de cada posicion del tunel, es decir
        // los lados por los que se puede salir.
        datosHexRandom = new ArrayList<int[]>();
        datosHexRandom.add(new int[]{2, 5});
        datosHexRandom.add(new int[]{0, 2});
        datosHexRandom.add(new int[]{1, 2});

    }

    public void iniDatosHexDecision() {
        // aquí se deben ingresar los datos de cada posicion del tunel, es decir
        // los lados por los que se puede salir.
        datosHexDescision = new ArrayList<int[]>();
        datosHexDescision.add(new int[]{0, 3, 5});
        datosHexDescision.add(new int[]{0, 1, 4});
        datosHexDescision.add(new int[]{0, 2, 3});
        datosHexDescision.add(new int[]{0, 3, 5});
    }

    public void iniDatosHexLlaves() {
        // aquí se deben ingresar los datos de cada posicion del tunel, es decir
        // los lados por los que se puede salir.
        datosHexLlaves = new ArrayList<int[]>();
        datosHexLlaves.add(new int[]{1, 3, 4});
        datosHexLlaves.add(new int[]{0, 3});
        datosHexLlaves.add(new int[]{2, 5});
        datosHexLlaves.add(new int[]{0, 2});
    }

    static public void iniciarHexRandom() {
        imasHexRandom = new PImage[]{
                app.loadImage("data/pantallaJuego/hexagonos/aleatorio/0.png"),
                app.loadImage("data/pantallaJuego/hexagonos/aleatorio/1.png"),
                app.loadImage("data/pantallaJuego/hexagonos/aleatorio/2.png")
        };

    }

    static public void iniciarHexDes() {
        imasHexDes = new PImage[]{
                app.loadImage("data/pantallaJuego/hexagonos/decision/des1.png"),
                app.loadImage("data/pantallaJuego/hexagonos/decision/des2.png"),
                app.loadImage("data/pantallaJuego/hexagonos/decision/des3.png"),
                app.loadImage("data/pantallaJuego/hexagonos/decision/des4.png")
        };
    }

    static public void iniciarLlavesMapa() {
        imasLlavesMapa = new PImage[]{
                app.loadImage("data/pantallaJuego/llavesMapa/llave_1.png"),
                app.loadImage("data/pantallaJuego/llavesMapa/llave_2.png"),
                app.loadImage("data/pantallaJuego/llavesMapa/llave_3.png"),
                //   app.loadImage("data/pantallaJuego/llavesMapa/llave_4.png")
        };
    }

    static public void iniciarHexLlaves() {
        imasHexLlaves = new PImage[]{
                app.loadImage("data/pantallaJuego/hexagonos/llaves/ll1.png"),
                app.loadImage("data/pantallaJuego/hexagonos/llaves/ll2.png"),
                app.loadImage("data/pantallaJuego/hexagonos/llaves/ll3.png"),
                app.loadImage("data/pantallaJuego/hexagonos/llaves/ll4.png")
        };

    }

    public static Info getInstance() {
        if (info == null) {
            info = new Info();
        }
        return info;
    }

    public void guardarDatos() {
        if (Logica.getTipoJ() == 0) {
            app.saveTable(table, app.sketchPath() + "/data/saves/datosS" + pareja + ".csv");
            System.out.println("se supone que guarda!!");
        } else {
            app.saveTable(table, app.sketchPath() + "/data/saves/datosC" + pareja + ".csv");
        }
    }
}
