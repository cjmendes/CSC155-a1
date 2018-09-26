package a1;

import java.lang.Math;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.nio.FloatBuffer;

import javax.swing.JFrame;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

import graphicslib3D.GLSLUtils;

import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class Starter extends JFrame implements GLEventListener, MouseWheelListener{

	// Initial Variables
	private GLCanvas canvas;
	private GL4 gl;
	private int rendering_program;
	private int vao[] = new int[1];
	private GLSLUtils util = new GLSLUtils();
	
	private float x = 0.0f;
	private float y = 1.0f;
	private float xr = 0.0f;
	private float yr = 0.0f;
	private float inc = 0.01f;
	private int moveCircle = 0;
	private float theta_total = 0.0f;
	private float theta = 5.0f;
	
	private VertexReading vRead = new VertexReading(gl);
	private int verticalCheck = 0;
	private int changeColor = 0;
	
	public Starter() {
		commands.CircleCommand circMoveCommand = new commands.CircleCommand(this);
		commands.VertMoveCommand vertMoveCommand = new commands.VertMoveCommand(this);
		commands.ColorCommand colorCommand = new commands.ColorCommand(this);
		
		JButton vertButton = new JButton ("Vertical");
		vertButton.addActionListener(vertMoveCommand);
		
		JButton circButton = new JButton ("Circle");
		circButton.addActionListener(circMoveCommand);
		
		JPanel topPanel = new JPanel();
		this.add(topPanel,BorderLayout.NORTH);
		
		topPanel.add(vertButton);
		topPanel.add(circButton);

		// Listen for mouse events
		this.addMouseWheelListener(this);
		
		// get the content pane of the JFrame (this)
		JComponent contentPane = (JComponent) this.getContentPane();
		// get the "focus is in the window" input map for the content pane
		int mapName = JComponent.WHEN_IN_FOCUSED_WINDOW;
		InputMap imap = contentPane.getInputMap(mapName);
		// create a keystroke object to represent the "c" key
		KeyStroke cKey = KeyStroke.getKeyStroke('c');
		// put the "cKey" keystroke object into the content pane’s "when focus is
		// in the window" input map under the identifier name "color“
		imap.put(cKey, "color");
		// get the action map for the content pane
		ActionMap amap = contentPane.getActionMap();
		// put the "myCommand" command object into the content pane's action map
		amap.put("color", colorCommand);
		//have the JFrame request keyboard focus
		this.requestFocus();
		
		
		setTitle("Assignment #1");
		setSize(600, 600);
		setLocation(200, 200);
		canvas = new GLCanvas();
		canvas.addGLEventListener(this);
		getContentPane().add(canvas);
		setVisible(true);
		FPSAnimator animator = new FPSAnimator(canvas, 30);
		animator.start();
	}
	
	// Changes the flag for the vertical function
	public void setVerticalCheck() {
		verticalCheck = (verticalCheck == 1) ? 0 : 1;
		moveCircle = 0;
	}
	
	// Changes the flag for the circle function
	public void setCircleCheck() {
		moveCircle = (moveCircle == 1) ? 0 : 1;
		verticalCheck = 0;
	}
	
	//Changes the flag for the color function
	public void switchColorPub() {
		changeColor = (changeColor == 0) ? 1 : 0;
	}
	
	// Paints the object and the background 
	@SuppressWarnings("static-access")
	@Override
	public void display(GLAutoDrawable arg0) {
		gl = (GL4) GLContext.getCurrentGL();
		gl.glUseProgram(rendering_program);
		
		float bkg[] = {0.0f, 0.0f, 0.0f, 1.0f};
		FloatBuffer bkgBuffer = Buffers.newDirectFloatBuffer(bkg);
		gl.glClearBufferfv(gl.GL_COLOR, 0, bkgBuffer);
		
		if(verticalCheck == 1)
			vertMove();
		if(moveCircle == 1)
			circMove();
		resize();
		switchColor();

		gl.glDrawArrays(gl.GL_TRIANGLES, 0, 3);
	}
	
	// Function for the circular movement of the object
	private void circMove() {
		
		int offset_circ = gl.glGetUniformLocation(rendering_program, "moveCirc");
		gl.glProgramUniform1i(rendering_program, offset_circ, moveCircle);
		
		theta_total += theta;
		
		int offset_theta = gl.glGetUniformLocation(rendering_program, "theta");
		gl.glProgramUniform1f(rendering_program, offset_theta, theta_total);
	}
	
	// Function for the vertical movement of the object
	private void vertMove() {
		
		int offset_circ = gl.glGetUniformLocation(rendering_program, "moveCirc");
		gl.glProgramUniform1i(rendering_program, offset_circ, moveCircle);
		
		int offset_loc_ybr = gl.glGetUniformLocation(rendering_program, "ybr");
		int offset_loc_ybl = gl.glGetUniformLocation(rendering_program, "ybl");
		int offset_loc_yt = gl.glGetUniformLocation(rendering_program, "yt");
		
		x += inc;
		if(x > 0.5f)
			inc = -0.01f;
		if(x < -0.5f)
			inc = 0.01f;
		
		gl.glProgramUniform1f(rendering_program, offset_loc_ybr, x);	
		gl.glProgramUniform1f(rendering_program, offset_loc_ybl, x);
		gl.glProgramUniform1f(rendering_program, offset_loc_yt, x);
	}
	
	//Function for the color change of the object
	public void switchColor() {
		int offset_loc = gl.glGetUniformLocation(rendering_program, "gradient");
		gl.glProgramUniform1i(rendering_program, offset_loc, changeColor);
	}
	
	// Function for the resizing of the object
	private void resize() {
		int offset_loc = gl.glGetUniformLocation(rendering_program, "scale");
		gl.glProgramUniform1f(rendering_program, offset_loc, y);
	}
	
	// Prints information about the machine, and then reads in the shader program
	@SuppressWarnings({ "deprecation", "static-access" })
	@Override
	public void init(GLAutoDrawable arg0) {
		gl = (GL4) GLContext.getCurrentGL();
		
		System.out.println("OpenGL Version: " + gl.glGetString(gl.GL_VERSION));
		System.out.println("JOGL Version: " + Package.getPackage("com.jogamp.opengl").getImplementationVersion());
		System.out.println("Java Version: " + System.getProperty("java.version"));
		
		rendering_program = vRead.createShaderProgram();
		gl.glGenVertexArrays(vao.length, vao, 0);
		gl.glBindVertexArray(vao[0]);
	}
	
	// Mouse Wheel Event Function. Increases and decreases the size of the rendered object
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
		if(e.getWheelRotation() > 0  && y < 1.9999998f)
			y += inc;
		else if (e.getWheelRotation() < 0 && y > 0.00999999f)
			y -= inc;
	}
	
	// Main function that starts the program
	public static void main(String[] args) {
		new Starter();
	}
	
	@Override
	public void dispose(GLAutoDrawable arg0) {}
	
	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {}

}
