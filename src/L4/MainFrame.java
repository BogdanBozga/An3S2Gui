package L4;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.Serial;
import javax.swing.JFrame;

import com.jogamp.opengl.util.Animator;


import javax.swing.JFrame;

import com.jogamp.opengl.util.Animator;

import javax.swing.JFrame;

import com.jogamp.opengl.util.Animator;

public class MainFrame
        extends JFrame
        implements GLEventListener {

    private GLCanvas canvas;
    private Animator animator;
    private double v_size = 1.0;
    private final int NO_TEXTURES = 2;

    private String path ="C:\\Users\\bobo274959\\Desktop\\Gui\\GUILabs-master\\src\\L4\\Texturi\\";

    private int texture[] = new int[NO_TEXTURES];
    TextureReader.Texture[] tex = new TextureReader.Texture[NO_TEXTURES];
    private GLU glu;

    public static void main(String args[]) {
        new MainFrame();
    }

    public MainFrame() {
        super("Java OpenGL");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(800, 600);

        this.initializeJogl();

        this.setVisible(true);
    }

    private void initializeJogl() {
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

    public void init(GLAutoDrawable canvas) {
        // Obtaining the GL instance associated with the canvas.
        GL2 gl = canvas.getGL().getGL2();

        // Setting the clear color -- the color which will be used to erase the canvas.
        gl.glClearColor(0, 0, 0, 0);

        // Selecting the modelview matrix.
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);

        glu = GLU.createGLU();

        // Generate a name (id) for the texture.
        // This is called once in init no matter how many textures we want to generate in the texture vector
        gl.glGenTextures(NO_TEXTURES, texture, 0);

        // Bind (select) the FIRST texture.
        gl.glBindTexture(GL.GL_TEXTURE_2D, texture[0]);

        // Read the texture from the image.
        try {
            tex[0] = TextureReader.readTexture(path+"textura1.jpg");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        // Define the filters used when the texture is scaled.
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);

        // Construct the texture and use mipmapping in the process.
        this.makeRGBTexture(gl, glu, tex[0], GL.GL_TEXTURE_2D, true);

        // Bind (select) the SECOND texture.
        gl.glBindTexture(GL.GL_TEXTURE_2D, texture[1]);

        // Read the texture from the image.
        try {
            tex[1] = TextureReader.readTexture(path+"textura3.jpg");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        // Define the filters used when the texture is scaled.
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);

        // Construct the texture and use mipmapping in the process.
        this.makeRGBTexture(gl, glu, tex[1], GL.GL_TEXTURE_2D, true);

        // Do not forget to enable texturing.
        gl.glEnable(GL.GL_TEXTURE_2D);

    }

    public void display(GLAutoDrawable canvas) {
        GL2 gl = canvas.getGL().getGL2();

        // Erasing the canvas -- filling it with the clear color.
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        // Add your scene code here
        // Bind (select) the texture
        gl.glBindTexture(GL.GL_TEXTURE_2D, texture[0]); // the pixel data for this texture is given by tex[0] in our example.
//        gl.glTexSubImage2D(GL.GL_TEXTURE_2D, 0, 0, 0, tex[1].getWidth(), tex[1].getHeight(), GL.GL_RGB, GL.GL_UNSIGNED_BYTE, tex[1].getPixels());

        gl.glDisable(GL.GL_BLEND);

        // Bind (select) the texture
        gl.glBindTexture(GL.GL_TEXTURE_2D, texture[0]);

        // Draw a square and apply a texture on it.
        gl.glBegin(GL2.GL_QUADS);
        // Lower left corner.
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex2f(0.1f, 0.1f);

        // Lower right corner.
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex2f(0.9f, 0.1f);

        // Upper right corner.
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex2f(0.9f, 0.9f);

        // Upper left corner.
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex2f(0.1f, 0.9f);
        gl.glEnd();

        // Enable blending for this texture.
        gl.glEnable(GL.GL_BLEND);

        // Set the blend function.
        gl.glBlendFunc(GL.GL_SRC_COLOR, GL.GL_DST_ALPHA);

        // Bind (select) the texture
        gl.glBindTexture(GL.GL_TEXTURE_2D, texture[1]);

        // Draw a square and apply a texture on it.
        gl.glBegin(GL2.GL_QUADS);
        // Lower left corner.
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex2f(0.1f, 0.1f);

        // Lower right corner.
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex2f(0.9f, 0.1f);

        // Upper right corner.
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex2f(0.9f, 0.9f);

        // Upper left corner.
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex2f(0.1f, 0.9f);
        gl.glEnd();
        // Forcing the scene to be rendered.
        gl.glFlush();
    }

    public void reshape(GLAutoDrawable canvas, int left, int top, int width, int height) {
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

    public void displayChanged(GLAutoDrawable canvas, boolean modeChanged, boolean deviceChanged) {
        return;
    }

    private void makeRGBTexture(GL gl, GLU glu, TextureReader.Texture img, int target, boolean mipmapped) {
        if (mipmapped) {
            glu.gluBuild2DMipmaps(target, GL.GL_RGB8, img.getWidth(), img.getHeight(), GL.GL_RGB, GL.GL_UNSIGNED_BYTE, img.getPixels());
        } else {
            gl.glTexImage2D(target, 0, GL.GL_RGB, img.getWidth(), img.getHeight(), 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, img.getPixels());
        }
    }
    @Override
    public void dispose(GLAutoDrawable arg0) {
        // TODO Auto-generated method stub
    }
}