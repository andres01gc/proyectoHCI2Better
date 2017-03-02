package pantallas;

import java.util.ArrayList;
import java.util.Collections;

import juego.Hexagon;
import juego.Jugador;
import juego.Llave;
import pantallas.visualInterface.Menu;
import red.ComunicacionCliente;
import red.ComunicacionServidor;
import root.Logica;
import setup.AdministradorPantalla;
import setup.Pantalla;

public class PantallaJuego extends Pantalla {

    Jugador j;
    float angle;
    int cantidadAleatorio = 0;
    int cantidadRondas = 4;//VARIABLE INDEPENDIENTE
    int rondaActual = 1;

    ArrayList<Hexagon> hexagons = new ArrayList<>();
    private float fixNx;
    private float fixNy;

    // hexagono donde se va a estar parado;
    private Hexagon hexaganoSeleccionado = null;
    private int nPosX;
    private int nPosY;

    // posiciones que se usarán para pintar en el centro
    private float xCenter;
    private float yCenter;
    private boolean tomoDesicion;
    private Llave l;
    private boolean decidir = false;


    private int recomendacionOtroJugador = -1;


    public void iniciar() {

        //inicializa la interfaz grafica de MENU!!!!


        Menu m = new Menu(this);

        if (Logica.getTipoJ() == 0) {
            //JUGADOR SERVER!

            ComunicacionServidor.getInstance().addObserver(m);
            System.out.println("SERAAAAAAAAAAA NOTFICADO POR EL SERVDIDOR");
        }
        if (Logica.getTipoJ() == 1) {
            //JUGADOR CLIENT!
            ComunicacionCliente.getInstance().addObserver(m);
            System.out.println("SERAAAAAAAAAAA NOTFICADO POR EL CLIENTE");

        }


        AdministradorPantalla.cambiarInterfaz(m);


        crearMatrix(0, 0, 1, 1, 250);
        j = new Jugador(hexagons.get(0).getX(), hexagons.get(0).getY());
        hexaganoSeleccionado = hexagons.get(0);
        hexaganoSeleccionado.setSelected(true);
    }


    public void pintar() {
        app.background(45);
        fixCenter();
        pintarHexagonos();
        pintarJugador();

        if (decidir && recomendacionOtroJugador != -1)
            decisionRandom();

        pintarLLave();
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


    public void decisionRandom() {

        if (j.llego()) {
            if (cantidadAleatorio > 0) {
                cantidadAleatorio--;

                if (cantidadAleatorio == 0) {
                    System.out.println("llego a una decision!");
                    int type;
                    if (tomoDesicion) {
                        type = 2;
                        l = new Llave(0);

                        rondaActual++;

                        // tomoDesicion=false;
                    } else {
                        type = 1;
                        //   tomoDesicion=true;
                    }
                    tomoDesicion = !tomoDesicion;
                    hexaganoSeleccionado.seleccionAleatoria(type);
                } else {
                    hexaganoSeleccionado.seleccionAleatoria(0);
                }
            } else {
                hexaganoSeleccionado.drawRecomendaciones(rondaActual, recomendacionOtroJugador);
            }

        }

        if (rondaActual == cantidadRondas) {
            AdministradorPantalla.cambiarPantalla(new Explica());
        }
    }


    public void pintarLLave() {
        if (l != null) {
            l.pintar(hexaganoSeleccionado);

            if (app.dist(j.getX(), j.getY(), l.getX(), l.getY()) < 10) {
                System.out.println("se cogio la llave");
                l = null;
            }

        }

    }

    @Override
    public void mousePressed() {
        if (decidir && recomendacionOtroJugador != -1)
            selecnuevoCamino();
    }


    public void selecnuevoCamino() {
        //     System.out.println("hey!");
        //cuando se selecciona un camino nuevo.
        if (j.llego() && cantidadAleatorio == 0) {
            Hexagon h = hexaganoSeleccionado.seleccionarVecino();

            if (h != null) {
                cantidadAleatorio = 1;
                hexaganoSeleccionado = h;
                hexagons.add(hexaganoSeleccionado);
            }
        }
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
