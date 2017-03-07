package pantallas;

import java.util.ArrayList;
import java.util.Collections;

import info.Info;
import juego.Hexagon;
import juego.Jugador;
import juego.Llave;
import pantallas.visualInterface.Menu;
import red.ComunicacionS;
import red.ComunicacionC;
import root.Logica;
import setup.AdministradorPantalla;
import setup.Pantalla;

public class PantallaJuego extends Pantalla {
    int vida = 80;
    Jugador j;
    float angle;
    int cantidadAleatorio = 2;
    int cantidadRondas = 4;//VARIABLE INDEPENDIENTE
    int rondaActual = 1;

    ArrayList<Hexagon> hexagons = new ArrayList<>();

    // hexagono donde se va a estar parado;
    private Hexagon hexaganoSeleccionado = null;
    // posiciones que se usarán para pintar en el centro
    private float xCenter;
    private float yCenter;
    private Llave l;
    private boolean decidir = false;
    public int recomendacionOtroJugador = -1;
    private boolean decicion = false;
    private boolean esperandoDecision;
    private boolean vidaHilo = false;
    private boolean llaveCogida = false;
    public int cuartoSeleccionado = -1;

    public boolean isLlaveCogida() {
        return llaveCogida;
    }

    public void pintaBarra() {
        app.imageMode(app.CORNER);
        float bufferBarra = app.map(vida, 0, 100, 0, 304);

        if (vida <= 20) {
            app.fill(244, 55, 55);
        } else if (vida <= 60) {
            app.fill(247, 236, 52);
        } else if (vida <= 100) {
            app.fill(112, 108, 61);
        }
        app.rect(94, 67, bufferBarra, 71);
        app.image(Info.getInstance().barraVida, 0, 0);
    }

    public void iniciar() {
        app.saveStrings("../data/saves/server.txt", new String[]{"hey", "holi", "hey"});

        //inicializa la interfaz grafica de MENU!!!!

        Menu m = new Menu(this);

        if (Logica.getTipoJ() == 0) {
            //JUGADOR SERVER!
            //  MenuS m = new MenuS(this);
            ComunicacionC.getInstance().addObserver(m);
            AdministradorPantalla.cambiarInterfaz(m);
        }
        if (Logica.getTipoJ() == 1) {
            //JUGADOR CLIENT!
            // Menu m = new Menu(this);
            ComunicacionS.getInstance().addObserver(m);
            AdministradorPantalla.cambiarInterfaz(m);
        }

        crearMatrix(0, 0, 1, 1, 220);
        j = new Jugador(hexagons.get(0).getX(), hexagons.get(0).getY());
        hexaganoSeleccionado = hexagons.get(0);
        hexaganoSeleccionado.setSelected(true);
    }

    public void pintar() {
        app.background(45);
        fixCenter();
        pintarHexagonos();
        pintarJugador();

        //llegarAseleccion();
        pintarLLave();


        pintarHexagonosFront();
        pintaBarra();
    }


    public void pintarRecomendaciones() {
        if (j.llega() && hexaganoSeleccionado.getTipoHex() == 1 && recomendacionOtroJugador != -1) {
            hexaganoSeleccionado.drawRecomendaciones(rondaActual, recomendacionOtroJugador);
        }
    }

    private void pintarHexagonosFront() {
        for (int i = hexagons.size() - 1; i >= 0; i--) {
            Hexagon h = hexagons.get(i);
            h.pintarFront(xCenter, yCenter, j.getPos());
        }
    }

    // crea una matriz de hexagonos
    public void crearMatrix(int x, int y, int ancho, int alto, float radio) {
        float width = (app.sqrt(3) / 2 * (radio * 2));
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                float nx = x + (i * width);
                float ny = y + (j * (radio + radio / 2));
                if (j % 2 == 0) {
                    nx += width / 2;
                }
                hexagons.add(new Hexagon(this, nx, ny, radio, 1, 0));
            }
        }
        Collections.shuffle(hexagons);
    }

    /**
     * metodo encargado de seguir al personaje en el centro
     */
    public void fixCenter() {
        // el lienzo siempre se va a centrar de acuerdo al hexagono seleccionado
        xCenter = j.getPos().x - (app.width / 2);
        yCenter = j.getPos().y - (app.height / 2);
    }

    public void llegarAllave(Hexagon hEscogida) {
        if (!vidaHilo) {
            vidaHilo = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean first = true;
                    while (vidaHilo) {
                        if (j.llega()) {

                            if (cantidadAleatorio >= 0) {
                                if (cantidadAleatorio > 0) {
                                    if (first) {
                                        hexaganoSeleccionado.setSelected(false);
                                        hexagons.add(hEscogida);
                                        hexaganoSeleccionado = hEscogida;
                                        first = false;
                                    } else {
                                        hexaganoSeleccionado.seleccionAleatoria(0);
                                    }
                                }
                                if (cantidadAleatorio == 0) {
                                    l = new Llave(0);
                                    hexaganoSeleccionado.seleccionAleatoria(2);
                                }
                                cantidadAleatorio--;
                            } else {
                                vidaHilo = false;
                            }
                        }
                        try {
                            sleep(33);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

    public void llegarAseleccion() {
        if (!vidaHilo) {
            vidaHilo = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (vidaHilo) {
                        if (j.llega()) {
                            if (cantidadAleatorio >= -0) {
                                if (cantidadAleatorio > 0)
                                    hexaganoSeleccionado.seleccionAleatoria(0);
                                if (cantidadAleatorio == 0)
                                    hexaganoSeleccionado.seleccionAleatoria(1);
                                cantidadAleatorio--;
                            }
                        }
                        try {
                            sleep(33);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

    public void pintarLLave() {
        if (l != null && hexaganoSeleccionado.getTipoHex() == 2) {
            l.pintar(hexaganoSeleccionado);

            if (app.dist(j.getX(), j.getY(), l.getX(), l.getY()) < 10) {
                System.out.println("se cogio la llave");
                llaveCogida = true;
                l = null;
            }
        }
    }

    @Override
    public void mousePressed() {



    }
    //  cantidadAleatorio = 2;

    //vidaHilo = true;
    //llegarAllave();
    public boolean selecnuevoCamino() {
        //     System.out.println("hey!");
        //cuando se selecciona un camino nuevo.
        if (j.llega() && hexaganoSeleccionado.getTipoHex() == 1 && recomendacionOtroJugador != -1) {
            Hexagon h = hexaganoSeleccionado.seleccionarVecino();
            if (h != null) {
                cantidadAleatorio = 2;
                llegarAllave(h);
                //  recomendacionOtroJugador = -1;
                return true;
            }
        }
        return false;
    }

    public void pintarJugador() {
        j.seguir(hexaganoSeleccionado.getrPos());
        j.update();
        j.pintar(xCenter, yCenter);
    }

    public void pintarHexagonos() {
        for (int i = hexagons.size() - 1; i >= 0; i--) {
            Hexagon h = hexagons.get(i);
            h.pintar(xCenter, yCenter, j.getPos());

            if (h.getVida() < 0)
                hexagons.remove(h);
        }
    }

    public int getRondaActual() {
        return rondaActual;
    }

    public Llave getL() {
        return l;
    }

    public void setL(Llave l) {
        this.l = l;
    }

    public void finalizar() {

    }

    public void centerCamera() {

    }

    public ArrayList<Hexagon> getHexagons() {
        return hexagons;
    }

    public void setHexagons(ArrayList<Hexagon> hexagons) {
        this.hexagons = hexagons;
    }

    public Hexagon gethSelected() {
        return hexaganoSeleccionado;
    }

    public void sethSelected(Hexagon hSelected) {
        this.hexaganoSeleccionado = hSelected;
    }


    public int getCantidadAleatorio() {
        return cantidadAleatorio;
    }

    public void setCantidadAleatorio(int cantidadAleatorio) {
        this.cantidadAleatorio = cantidadAleatorio;
    }

    public void setDecidir(boolean decidir) {
        this.decidir = decidir;
    }

    public boolean isDecidir() {
        return decidir;
    }

    public int getRecomendacionOtroJugador() {
        return recomendacionOtroJugador;
    }

    public void setRecomendacionOtroJugador(int recomendacionOtroJugador) {
        this.recomendacionOtroJugador = recomendacionOtroJugador;
    }

}
