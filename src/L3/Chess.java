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

public class Chess
        extends JFrame
        implements GLEventListener
{

    private GLCanvas canvas;
    private Animator animator;
    private int aChess;

    // For specifying the positions of the clipping planes (increase/decrease the distance) modify this variable.
    // It is used by the glOrtho method.
    private double v_size = 1.0;

    // Application main entry point
    public static void main(String args[])
    {
        new Chess();
    }

    // Default constructor
    public Chess()
    {
        super("Java OpenGL");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(1200, 1000);

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
        aChess = gl.glGenLists(1);

        // Generate the Display List
        gl.glNewList(aChess, GL2.GL_COMPILE);
        drawChess(gl);
        gl.glEndList();

    }


    public void display(GLAutoDrawable canvas)
    {
        GL2 gl = canvas.getGL().getGL2();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glCallList(aChess);
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


   private void drawChess(GL2 gl){
        gl.glColor3f(1.f, 1.f, 1.f);
        for(int i =0 ;i <8;i++){
            for(int j =0 ;j <8;j++){
                if((i+j)%2!=0){
                    //white
                    gl.glCullFace(GL.GL_FRONT);
                    gl.glEnable(GL.GL_CULL_FACE);
                    gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL);
                }else {
                    //black
                    gl.glCullFace(GL.GL_BACK);
                    gl.glEnable(GL.GL_CULL_FACE);
                    gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL3.GL_FILL);
                }

                gl.glBegin(GL2.GL_POLYGON);
                gl.glVertex2f(-0.4f+i*0.1f, -0.4f+j*0.1f);
                gl.glVertex2f(-0.4f+i*0.1f, -0.3f+j*0.1f);
                gl.glVertex2f(-0.3f+i*0.1f, -0.3f+j*0.1f);
                gl.glVertex2f(-0.3f+i*0.1f, -0.4f+j*0.1f);
                gl.glEnd();


        }
    }





   }
}