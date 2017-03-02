package juego;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import setup.Pantalla;

public class Jugador {

	private PVector pos, acel, vel;

	float x, y;
	float xReal, yReal;
PImage image;
	PApplet app = Pantalla.app;

	private float maxspeed = 3;
	private float maxforce = 0.1f;
	private float d;

	public Jugador(float x, float y) {
		pos = new PVector(x, y);
		vel = new PVector(0, 0);
		acel = new PVector(0, 0);
		image= app.loadImage("../data/pantallaJuego/jugador.png");

	}

	public void mover() {
		xReal += 0.2f;
	}

	public void pintar(float xCenter, float yCenter) {
		app.fill(200);

		app.pushMatrix();

		this.x=pos.x - xCenter;
		this.y=pos.y - yCenter;
		app.translate(pos.x - xCenter, pos.y - yCenter);
		app.rotate(app.atan2(vel.y, vel.x));
		app.image(image, 0, 0);

		app.popMatrix();

	}

	public void seguir(PVector target) {
		PVector desired = PVector.sub(target, pos); // A vector pointing from
		// the location to the
		// target
		 d = desired.mag();
		// Normalize desired and scale with arbitrary damping within 100 pixels
		desired.normalize();
		if (d < 150) {
			float m = app.map(d, 0, 100, 0, maxspeed);
			desired.mult(m);
		} else {
			desired.mult(maxspeed);
		}

		// Steering = Desired minus Velocity
		PVector steer = PVector.sub(desired, vel);
		steer.limit(maxforce); // Limit to maximum steering force
		applyForce(steer);
	}

	// Method to update location
	public void update() {
		// Update velocity
		vel.add(acel);
		// Limit speed
		vel.limit(maxspeed);
		pos.add(vel);
		// Reset accelerationelertion to 0 each cycle
		acel.mult(0);
	}

	public void applyForce(PVector force) {
		// We could add mass here if we want A = F / M
		acel.add(force);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public PVector getPos() {
		return pos;
	}

	public void setPos(PVector pos) {
		this.pos = pos;
	}

	public PVector getAcel() {
		return acel;
	}

	public void setAcel(PVector acel) {
		this.acel = acel;
	}

	public PVector getVel() {
		return vel;
	}

	public void setVel(PVector vel) {
		this.vel = vel;
	}

	public boolean llego() {
		return (d<10);
	}



}
