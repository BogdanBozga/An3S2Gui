package L2;

import com.jogamp.opengl.GL2;
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

public class DrawCircle {
    private final Integer verticsNumber;
    private final Float radius;

    private float x;
    private float y;

    public DrawCircle(float x, float y, int nrVertics, float radius){
        this.verticsNumber = nrVertics;
        this.radius = radius;
        this.x = x;
        this.y =y;
    }

    public void draw(GL2 gl){



        double x,y, angle;

        gl.glBegin(GL2.GL_LINE_LOOP);
        for (int i=0; i<360; i++) {
            angle = Math.toRadians(i);
            x = radius * Math.cos(angle);
            y = radius * Math.sin(angle);
            gl.glVertex2d(this.x + x, this.y + y);
        }
        gl.glEnd();

    }

    public void drawFill(GL2 gl) {
        double x,y, angle;

        gl.glBegin(GL2.GL_POLYGON);
        for (int i=0; i<360; i++) {
            angle = Math.toRadians(i);
            x = radius * Math.cos(angle);
            y = radius * Math.sin(angle);
            gl.glVertex2d(this.x + x, this.y + y);
        }
        gl.glEnd();
    }
}

