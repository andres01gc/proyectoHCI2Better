package juego;

import info.Info;
import processing.core.PApplet;
import root.Logica;
import root.server.MainServer;

/**
 * Created by andre on 3/1/2017.
 */
public class Llave {
    private PApplet app = Logica.getApp();

    private final int tipoLlave;
    private float x, y;

    public Llave(int tipoLlave) {
        this.tipoLlave = tipoLlave;
    }


    public void pintar(Hexagon hSelected) {
        //app.image(,);
        //System.out.println("asdlvjaksdjvb.asdvañskdvnsjdv.kajsdbv.jkasdv");


        this.x = hSelected.getX();
        this.y = hSelected.getY();
        hSelected.setSelected(true);
        app.fill(app.random(255), app.random(255), app.random(255));
        app.imageMode(app.CENTER);
        app.image(Info.imasLlavesMapa[tipoLlave], x, y);
    //    app.ellipse(x, y, 50, 50);


    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
