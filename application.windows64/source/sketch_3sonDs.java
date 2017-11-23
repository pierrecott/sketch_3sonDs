import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import toxi.physics.*; 
import toxi.physics.behaviors.*; 
import toxi.physics.constraints.*; 
import toxi.geom.*; 
import toxi.geom.mesh.*; 
import toxi.geom.mesh.subdiv.*; 
import toxi.geom.mesh2d.*; 
import toxi.math.*; 
import toxi.math.conversion.*; 
import toxi.math.noise.*; 
import toxi.math.waves.*; 
import toxi.util.*; 
import toxi.util.datatypes.*; 
import toxi.util.events.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class sketch_3sonDs extends PApplet {


















int cols = 40;
int rows = 40;

Particle[][] particles= new Particle [cols][rows];
ArrayList<Spring>springs; 

float w = 12;

VerletPhysics physics;

public void setup() {
  
  springs = new ArrayList<Spring>();

  physics = new VerletPhysics();
  Vec3D gravity= new Vec3D(0, 1, 0);
  GravityBehavior gb = new GravityBehavior(gravity);
  physics.addBehavior (gb);


  float x = -200; 
  for (int i = 0; i< cols; i++ ) {
    float z = -50; 
    for (int j = 0; j< rows; j++ ) {
      Particle p = new Particle(x, -100, z);
      particles[i] [j] = p;
      physics.addParticle(p);
      z= z+w;
    }      
    x= x + w;
  }
  for (int i = 0; i< cols-1; i++ ) {
    for (int j = 0; j< rows-1; j++ ) {
      Particle a = particles[i][j];
      Particle b1 = particles[i+1][j];
      Particle b2 = particles[i][j+1];
      Particle b3 = particles[i+1][j+1];
      Spring s1 = new Spring (a, b1);
      springs.add(s1);
      physics.addSpring(s1);
      Spring s2 = new Spring (a, b2);
      springs.add(s2);
      physics.addSpring(s2);
      Spring s3 = new Spring (a, b3);
      springs.add(s3);
      physics.addSpring(s3);
    }
  }

  particles[0][0].lock();
  particles[cols-1][0].lock();
   particles[0][rows-1].lock();
  particles[cols-1][rows-1].lock();
}


float a =0 ;
public void draw () {
  background(51);
  translate(width/2, height/2);
  a +=0.01f;
  physics.update(); 

  for (int i = 0; i< cols; i++ ) {
    for (int j = 0; j< rows; j++ ) {
      // particles[i][j].display();
    }
  }
  for (Spring s : springs) {
    s.display();
  }
}
class Particle extends VerletParticle {
Particle(float x , float y, float z ){
super (x,y,z  ); 
 } 
public void display(){
pushMatrix();
  translate(x,y,z); 
  fill(255);
ellipse(x,y,10,10);
popMatrix();
}
}
class Spring extends VerletSpring  {

  Spring(Particle a, Particle b) {
    super(a, b, w, 1);
  }
  
  public void display() {
    stroke(255);
    strokeWeight(2);
    line(a.x, a.y,a.z, b.x, b.y,b.z );
  } 
}
  public void settings() {  size (600, 1200, P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "sketch_3sonDs" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
