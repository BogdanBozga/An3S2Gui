package L3;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import javax.swing.JFrame;

import com.jogamp.opengl.util.Animator;


import javax.swing.JFrame;

import com.jogamp.opengl.util.Animator;

public class MainFrame
        extends JFrame
        implements GLEventListener
{

    private GLCanvas canvas;
    private Animator animator;
    private int aCircle;
    // For specifying the positions of the clipping planes (increase/decrease the distance) modify this variable.
    // It is used by the glOrtho method.
    private double v_size = 1.0;

    // Application main entry point
    public static void main(String args[])
    {
        new MainFrame();
    }

    // Default constructor
    public MainFrame()
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

// Activate the GL_LINE_SMOOTH state variable. Other options include
        // GL_POINT_SMOOTH and GL_POLYGON_SMOOTH.
        gl.glEnable(GL.GL_LINE_SMOOTH);

        // Activate the GL_BLEND state variable. Means activating blending.
        gl.glEnable(GL.GL_BLEND);

        // Set the blend function. For antialiasing it is set to GL_SRC_ALPHA for the source
        // and GL_ONE_MINUS_SRC_ALPHA for the destination pixel.
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

        // Control GL_LINE_SMOOTH_HINT by applying the GL_DONT_CARE behavior.
        // Other behaviours include GL_FASTEST or GL_NICEST.
        gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_DONT_CARE);
        // Uncomment the following two lines in case of polygon antialiasing
        //gl.glEnable(GL.GL_POLYGON_SMOOTH);
        //glHint(GL_POLYGON_SMOOTH_HINT, GL_NICEST);


        // Selecting the modelview matrix.
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);


//        ------------------
        aCircle = gl.glGenLists(1);

        // Generate the Display List
        gl.glNewList(aCircle, GL2.GL_COMPILE);
        drawCircle(gl, 0.5f, 0.5f, 0.4f);
        gl.glEndList();
    }


    byte mask[] = {
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x03, (byte)0x80, 0x01, (byte)0xC0, 0x06, (byte)0xC0, 0x03, 0x60,

            0x04, 0x60, 0x06, 0x20, 0x04, 0x30, 0x0C, 0x20,
            0x04, 0x18, 0x18, 0x20, 0x04, 0x0C, 0x30, 0x20,
            0x04, 0x06, 0x60, 0x20, 0x44, 0x03, (byte)0xC0, 0x22,
            0x44, 0x01, (byte)0x80, 0x22, 0x44, 0x01, (byte)0x80, 0x22,
            0x44, 0x01, (byte)0x80, 0x22, 0x44, 0x01, (byte)0x80, 0x22,
            0x44, 0x01, (byte)0x80, 0x22, 0x44, 0x01, (byte)0x80, 0x22,
            0x66, 0x01, (byte)0x80, 0x66, 0x33, 0x01, (byte)0x80, (byte)0xCC,

            0x19, (byte)0x81, (byte)0x81, (byte)0x98, 0x0C, (byte)0xC1, (byte)0x83, 0x30,
            0x07, (byte)0xe1, (byte)0x87, (byte)0xe0, 0x03, 0x3f, (byte)0xfc, (byte)0xc0,
            0x03, 0x31, (byte)0x8c, (byte)0xc0, 0x03, 0x33, (byte)0xcc, (byte)0xc0,
            0x06, 0x64, 0x26, 0x60, 0x0c, (byte)0xcc, 0x33, 0x30,
            0x18, (byte)0xcc, 0x33, 0x18, 0x10, (byte)0xc4, 0x23, 0x08,
            0x10, 0x63, (byte)0xC6, 0x08, 0x10, 0x30, 0x0c, 0x08,
            0x10, 0x18, 0x18, 0x08, 0x10, 0x00, 0x00, 0x08};

    public void display(GLAutoDrawable canvas)
    {
        GL2 gl = canvas.getGL().getGL2();


        gl.glCallList(aCircle);

        // Do not render front-faced polygons.
        gl.glCullFace(GL.GL_FRONT);
        // Culling must be enabled in order to work.
        gl.glEnable(GL.GL_CULL_FACE);

        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL);

        // Define vertices in clockwise order (back-faced).
        gl.glBegin(GL2.GL_POLYGON);
        // Define normal for vertex 1
        gl.glNormal3f(0.f, 0.f, 1.f);
        gl.glColor3f(1.f, 0.f, 0.f);
        gl.glVertex2f(0.2f, 0.2f);

        // Define normal for vertex 2
        gl.glNormal3f(0.f, 0.f, 1.f);
        gl.glColor3f(0.f, 1.f, 0.f);
        gl.glVertex2f(0.2f, 0.4f);

        // Define normal for vertex 3
        gl.glNormal3f(0.f, 0.f, 1.f);
        gl.glColor3f(0.f, 0.f, 1.f);
        gl.glVertex2f(0.4f, 0.4f);

        // Define normal for vertex 4
        gl.glNormal3f(0.f, 0.f, 1.f);
        gl.glColor3f(1.f, 1.f, 1.f);
        gl.glVertex2f(0.4f, 0.2f);
        gl.glEnd();
        gl.glCallList(aCircle);

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



    private void drawCircle(GL2 gl, float xCenter, float yCenter, float radius) {

        double x,y, angle;

        gl.glBegin(GL2.GL_LINE_LOOP);
        for (int i=0; i<360; i++) {
            angle = Math.toRadians(i);
            x = radius * Math.cos(angle);
            y = radius * Math.sin(angle);
            gl.glVertex2d(xCenter + x, yCenter + y);
        }
        gl.glEnd();
    }
}