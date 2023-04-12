package L2;



import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.util.Animator;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import javax.swing.JFrame;

import com.jogamp.opengl.util.Animator;

import javax.swing.JFrame;

import com.jogamp.opengl.util.Animator;

public class House
        extends JFrame
        implements GLEventListener
{

    private GLCanvas canvas;
    private Animator animator;

    private float sunX = -1.2f;
    private float sunSpeed = 0.007f;

    private double v_size = 1.0;

    // Application main entry point
    public static void main(String args[])
    {
        GLCanvas canvas = new GLCanvas();
        canvas.addGLEventListener(new House());
    }

    // Default constructor
    public House()
    {
        super("Java OpenGL");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(800, 600);

        this.initializeJogl();

        this.setVisible(true);
    }

    private void initializeJogl()
    {
        GLProfile glprofile = GLProfile.getDefault();
        GLCapabilities capabilities = new GLCapabilities(glprofile);


        capabilities.setHardwareAccelerated(true);
        capabilities.setDoubleBuffered(true);

        capabilities.setNumSamples(2);
        capabilities.setSampleBuffers(true);

        this.canvas = new GLCanvas(capabilities);



        this.getContentPane().add(this.canvas);

        // Adding an OpenGL event listener to the canvas.
        this.canvas.addGLEventListener(this);
        this.animator = new Animator();
        this.animator.add(this.canvas);
        this.animator.start();

//        this.getContentPane().add(this.canvas);
//        this.canvas.addGLEventListener(this);
//        this.animator = new Animator(this.canvas);
//        this.animator.start();
    }

    public void init(GLAutoDrawable canvas)
    {
        GL2 gl = canvas.getGL().getGL2();
        gl.glClearColor(0, 0, 0, 0);
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
    }

    public void square(GL2 gl,float x, float y, float length){
//        GL2 gl = canvas.getGL().getGL2();
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glColor3f(255, 255, 255);

        gl.glVertex2f(x, y);
        gl.glVertex2f(x+length, y);
        gl.glVertex2f(x+length, y+length);
        gl.glVertex2f(x, y+length);

        gl.glEnd(); // End drawing the line loop
    }
    public void triangle(GL2 gl,float x, float y, float length){
        gl.glBegin(GL.GL_LINE_LOOP);
        gl.glColor3f(255, 255, 255);

        gl.glVertex2f(x, y);
        gl.glVertex2f(x+length/2, y+length/2);
        gl.glVertex2f(x+length, y);
        gl.glVertex2f(x, y);

        gl.glEnd(); // End drawing the line loop
    }

    public void display(GLAutoDrawable canvas)
    {
        GL2 gl = canvas.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLineWidth(2.0f);

        square(gl, -0.5f, -0.5f, 0.8f);
        square(gl, -0.4f, 0.0f,0.2f);
        square(gl, -0.0f, 0.0f,0.2f);
        triangle(gl, -0.5f,0.3f,0.8f);

        DrawCircle sun = new DrawCircle(sunX,0.8f, 50, 0.2f);
//        sun(gl, 0.8f,0.8f);
        sun.draw(gl);

        sunX += sunSpeed;
        if(sunX >= 1.2f || sunX <= -1.2f){
            sunSpeed *= -1;
        }
        // Forcing the scene to be rendered.
        gl.glFlush();
    }

    public void reshape(GLAutoDrawable canvas, int left, int top, int width, int height)
    {
        GL2 gl = canvas.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
        double ratio = (double) width / (double) height;
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();

        if (ratio < 1)
            gl.glOrtho(-v_size, v_size, -v_size, v_size / ratio, -1, 1);
        else
            gl.glOrtho(-v_size, v_size * ratio, -v_size, v_size, -1, 1);

        // Selecting the modelview matrix.
//        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void displayChanged(GLAutoDrawable canvas, boolean modeChanged, boolean deviceChanged)
    {
        return;
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
        // TODO Auto-generated method stub
    }
}
