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

public class Squares
        extends JFrame
        implements GLEventListener
{

    private GLCanvas canvas;
    private Animator animator;

    // For specifying the positions of the clipping planes (increase/decrease the distance) modify this variable.
    // It is used by the glOrtho method.
    private double v_size = 1.0;

    // Application main entry point
    public static void main(String args[])
    {
        new Squares();
    }

    // Default constructor
    public Squares()
    {
        super("Java OpenGL");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(800, 600);

        this.initializeJogl();

        this.setVisible(true);
    }

    private void initializeJogl()
    {
        // Creating a new GL profile.
        GLProfile glprofile = GLProfile.getDefault();
        // Creating an object to manipulate OpenGL parameters.
        GLCapabilities capabilities = new GLCapabilities(glprofile);

        // Setting some OpenGL parameters.
        capabilities.setHardwareAccelerated(true);
        capabilities.setDoubleBuffered(true);

        // Try to enable 2x anti aliasing. It should be supported on most hardware.
        capabilities.setNumSamples(2);
        capabilities.setSampleBuffers(true);

        // Creating an OpenGL display widget -- canvas.
        this.canvas = new GLCanvas(capabilities);

        // Adding the canvas in the center of the frame.
        this.getContentPane().add(this.canvas);

        // Adding an OpenGL event listener to the canvas.
        this.canvas.addGLEventListener(this);

        // Creating an animator that will redraw the scene 40 times per second.
        this.animator = new Animator(this.canvas);

        // Starting the animator.
        this.animator.start();
    }

    public void init(GLAutoDrawable canvas)
    {
        // Obtaining the GL instance associated with the canvas.
        GL2 gl = canvas.getGL().getGL2();

        // Setting the clear color -- the color which will be used to erase the canvas.
        gl.glClearColor(0, 0, 0, 0);

        // Selecting the modelview matrix.
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);

    }

    public void display(GLAutoDrawable canvas)
    {
        GL2 gl = canvas.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        gl.glLineStipple(1, (short) 0x3F07);
        gl.glEnable(GL2.GL_LINE_STIPPLE);
        gl.glBegin(GL2.GL_LINES);
        gl.glColor3f(255, 0, 0);
        gl.glVertex2f(0.1f, 0.1f);
        gl.glVertex2f(0.3f, 0.1f);

        gl.glColor3f(0, 255, 0);
        gl.glVertex2f(0.3f, 0.1f);
        gl.glVertex2f(0.3f, 0.3f);

        gl.glColor3f(0, 0, 255);
        gl.glVertex2f(0.3f, 0.3f);
        gl.glVertex2f(0.1f, 0.3f);

        gl.glColor3f(255, 255, 0);
        gl.glVertex2f(0.1f, 0.3f);
        gl.glVertex2f(0.1f, 0.1f);


        gl.glEnd();
        gl.glDisable(GL2.GL_LINE_STIPPLE);


        gl.glBegin(GL2.GL_LINES);
        gl.glColor3f(0, 255, 0);
        gl.glVertex2f(0.4f, 0.1f);
        gl.glVertex2f(0.6f, 0.1f);

        gl.glColor3f(0, 0, 255);
        gl.glVertex2f(0.6f, 0.1f);
        gl.glVertex2f(0.6f, 0.3f);

        gl.glColor3f(255, 0, 0);
        gl.glVertex2f(0.6f, 0.3f);
        gl.glVertex2f(0.4f, 0.3f);

        gl.glColor3f(0, 255, 255);
        gl.glVertex2f(0.4f, 0.3f);
        gl.glVertex2f(0.4f, 0.1f);
        gl.glEnd();

        gl.glBegin(GL.GL_LINE_LOOP); // Begin drawing a line loop

        gl.glColor3f(255, 255, 255);
        gl.glVertex2f(0.7f, 0.1f);
        gl.glVertex2f(0.9f, 0.1f);
        gl.glVertex2f(0.9f, 0.3f);
        gl.glVertex2f(0.7f, 0.3f);

        gl.glEnd(); // End drawing the line loop

        // Forcing the scene to be rendered.
        gl.glFlush();
    }

    public void reshape(GLAutoDrawable canvas, int left, int top, int width, int height)
    {
        GL2 gl = canvas.getGL().getGL2();

        // Selecting the viewport -- the display area -- to be the entire widget.
        gl.glViewport(0, 0, width, height);

        // Determining the width to height ratio of the widget.
        double ratio = (double) width / (double) height;

        // Selecting the projection matrix.
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);

        gl.glLoadIdentity();

        // Selecting the view volume to be x from 0 to 1, y from 0 to 1, z from -1 to 1.
        // But we are careful to keep the aspect ratio and enlarging the width or the height.
        if (ratio < 1)
            gl.glOrtho(-v_size, v_size, -v_size, v_size / ratio, -1, 1);
        else
            gl.glOrtho(-v_size, v_size * ratio, -v_size, v_size, -1, 1);

        // Selecting the modelview matrix.
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
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
