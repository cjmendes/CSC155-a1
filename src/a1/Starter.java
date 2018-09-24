package a1;

import java.awt.BorderLayout;
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

	private GLCanvas canvas;
	private GL4 gl;
	private int rendering_program;
	private int vao[] = new int[1];
	private GLSLUtils util = new GLSLUtils();
	
	private float x = 0.0f;
	private float inc = 0.01f;
	
	public Starter() {
		commands.CircleCommand circCommand = new commands.CircleCommand();
		commands.VertMoveCommand vertMoveCommand = new commands.VertMoveCommand();
		
		JPanel topPanel = new JPanel();
		this.add(topPanel,BorderLayout.NORTH);
		JButton circButton = new JButton ("Circle");
		//circButton.setAction(circCommand);
		topPanel.add(circButton);
		
		JButton vertButton = new JButton ("Verticle");
		vertButton.setAction(vertMoveCommand);
		topPanel.add(vertButton);
		
		
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
		amap.put("color", vertMoveCommand);
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
	
	@SuppressWarnings("static-access")
	@Override
	public void display(GLAutoDrawable arg0) {
		gl = (GL4) GLContext.getCurrentGL();
		gl.glUseProgram(rendering_program);
		
		float bkg[] = {0.0f, 0.0f, 0.0f, 1.0f};
		FloatBuffer bkgBuffer = Buffers.newDirectFloatBuffer(bkg);
		gl.glClearBufferfv(gl.GL_COLOR, 0, bkgBuffer);
		
		
		vertMove();
		//resize();
		//******** Resize Function *********
		/*int offset_loc = gl.glGetUniformLocation(rendering_program, "xbr");
		gl.glProgramUniform1f(rendering_program, offset_loc, x);
		
		offset_loc = gl.glGetUniformLocation(rendering_program, "ybr");
		gl.glProgramUniform1f(rendering_program, offset_loc, -x);
		
		offset_loc = gl.glGetUniformLocation(rendering_program, "xbl");
		gl.glProgramUniform1f(rendering_program, offset_loc, -x);
		
		offset_loc = gl.glGetUniformLocation(rendering_program, "ybl");
		gl.glProgramUniform1f(rendering_program, offset_loc, -x);
		
		offset_loc = gl.glGetUniformLocation(rendering_program, "xt");
		gl.glProgramUniform1f(rendering_program, offset_loc, x);
		
		offset_loc = gl.glGetUniformLocation(rendering_program, "yt");
		gl.glProgramUniform1f(rendering_program, offset_loc, x);*/
		
		
		gl.glDrawArrays(gl.GL_TRIANGLES, 0, 3);
	}

	private void circleMove() {
		System.out.println(x);
		int offset_loc = gl.glGetUniformLocation(rendering_program, "xbr");
		gl.glProgramUniform1f(rendering_program, offset_loc, x);
		
		offset_loc = gl.glGetUniformLocation(rendering_program, "ybr");
		gl.glProgramUniform1f(rendering_program, offset_loc, -x);
		
		offset_loc = gl.glGetUniformLocation(rendering_program, "xbl");
		gl.glProgramUniform1f(rendering_program, offset_loc, -x);
		
		offset_loc = gl.glGetUniformLocation(rendering_program, "ybl");
		gl.glProgramUniform1f(rendering_program, offset_loc, -x);
		
		offset_loc = gl.glGetUniformLocation(rendering_program, "xt");
		gl.glProgramUniform1f(rendering_program, offset_loc, x);
		
		offset_loc = gl.glGetUniformLocation(rendering_program, "yt");
		gl.glProgramUniform1f(rendering_program, offset_loc, x);
	}
	
	private void vertMove() {
		x += inc;
		if(x > 0.5f)
			inc = -0.01f;
		if(x < -0.5f)
			inc = 0.01f;
		
		int offset_loc = gl.glGetUniformLocation(rendering_program, "ybr");
		gl.glProgramUniform1f(rendering_program, offset_loc, x);
		
		offset_loc = gl.glGetUniformLocation(rendering_program, "ybl");
		gl.glProgramUniform1f(rendering_program, offset_loc, x);
		
		offset_loc = gl.glGetUniformLocation(rendering_program, "yt");
		gl.glProgramUniform1f(rendering_program, offset_loc, x);
	}
	
	private void resize() {
		int offset_loc = gl.glGetUniformLocation(rendering_program, "xbr");
		gl.glProgramUniform1f(rendering_program, offset_loc, x);
		
		offset_loc = gl.glGetUniformLocation(rendering_program, "ybr");
		gl.glProgramUniform1f(rendering_program, offset_loc, -x);
		
		offset_loc = gl.glGetUniformLocation(rendering_program, "xbl");
		gl.glProgramUniform1f(rendering_program, offset_loc, -x);
		
		offset_loc = gl.glGetUniformLocation(rendering_program, "ybl");
		gl.glProgramUniform1f(rendering_program, offset_loc, -x);
		
		offset_loc = gl.glGetUniformLocation(rendering_program, "xt");
		gl.glProgramUniform1f(rendering_program, offset_loc, x);
		
		offset_loc = gl.glGetUniformLocation(rendering_program, "yt");
		gl.glProgramUniform1f(rendering_program, offset_loc, x);
	}
	@SuppressWarnings({ "deprecation", "static-access" })
	@Override
	public void init(GLAutoDrawable arg0) {
		gl = (GL4) GLContext.getCurrentGL();
		
		System.out.println("OpenGL Version: " + gl.glGetString(gl.GL_VERSION));
		System.out.println("JOGL Version: " + Package.getPackage("com.jogamp.opengl").getImplementationVersion());
		System.out.println("Java Version: " + System.getProperty("java.version"));
		
		rendering_program = createShaderProgram();
		gl.glGenVertexArrays(vao.length, vao, 0);
		gl.glBindVertexArray(vao[0]);
	}
	
	public static void main(String[] args) {
		new Starter();
	}

	@SuppressWarnings("static-access")
	private int createShaderProgram() {
		gl = (GL4) GLContext.getCurrentGL();
		int[] vertCompiled = new int[1];
		int[] fragCompiled = new int[1];
		int[] linked = new int[1];
		
		String vShaderSource[] = util.readShaderSource("src/assets/vert.shader");
		String fShaderSource[] = util.readShaderSource("src/assets/frag.shader");
		
		int vShader = gl.glCreateShader(gl.GL_VERTEX_SHADER);
		int fShader = gl.glCreateShader(gl.GL_FRAGMENT_SHADER);
		
		gl.glShaderSource(vShader, vShaderSource.length, vShaderSource, null, 0);
		gl.glCompileShader(vShader);
		
		checkOpenGLError();
		gl.glGetShaderiv(vShader, gl.GL_COMPILE_STATUS, vertCompiled, 0);
		if(vertCompiled[0] == 1)
			System.out.println("Vertex Compilation Succeeded");
		else {
			System.out.println("Vertex Compilation Failed");
			printShaderLog(vShader);
		}
		
		gl.glShaderSource(fShader, fShaderSource.length, fShaderSource, null, 0);
		gl.glCompileShader(fShader);
		
		checkOpenGLError();
		gl.glGetShaderiv(fShader, gl.GL_COMPILE_STATUS, fragCompiled, 0);
		if(fragCompiled[0] == 1)
			System.out.println("Fragment Compilation Succeeded");
		else {
			System.out.println("Fragment Compilation Failed");
			printShaderLog(fShader);
		}
		
		int vfprogram = gl.glCreateProgram();
		gl.glAttachShader(vfprogram, vShader);
		gl.glAttachShader(vfprogram, fShader);
		gl.glLinkProgram(vfprogram);
		
		checkOpenGLError();
		gl.glGetProgramiv(vfprogram, gl.GL_LINK_STATUS, linked, 0);
		if(linked[0] == 1)
			System.out.println("Linking Succeeded");
		else {
			System.out.println("Linking Failed");
			printProgramLog(vfprogram);
		}
		
		gl.glDeleteShader(vShader);
		gl.glDeleteShader(fShader);
		return vfprogram;
	}
	
	@Override
	public void dispose(GLAutoDrawable arg0) {}
	
	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {}
	
	private void printShaderLog(int shader) {
		gl = (GL4) GLContext.getCurrentGL();
		int[] len = new int[1];
		int[] chWritten = new int[1];
		byte[] log = null;
		
		//Determine the length of the shader compilation log
		gl.glGetShaderiv(shader, gl.GL_INFO_LOG_LENGTH, len, 0);
		if(len[0] > 0) {
			log = new byte[len[0]];
			gl.glGetShaderInfoLog(shader, len[0], chWritten, 0, log, 0);
			System.out.println("Shader Info Log: ");
			for(int i = 0; i < log.length; i++)
				System.out.print((char) log[i]);
		}
	}
	
	private void printProgramLog(int prog) {
		gl = (GL4) GLContext.getCurrentGL();
		int[] len = new int[1];
		int[] chWritten = new int[1];
		byte[] log = null;
		
		//Determine the length of the program compilation log
		gl.glGetProgramiv(prog, gl.GL_INFO_LOG_LENGTH, len, 0);
		if(len[0] > 0) {
			log = new byte[len[0]];
			gl.glGetProgramInfoLog(prog, len[0], chWritten, 0, log, 0);
			System.out.println("Program Info Log: ");
			for(int i = 0; i < log.length; i++)
				System.out.print((char) log[i]);
		}
	}
	
	private boolean checkOpenGLError() {
		gl = (GL4) GLContext.getCurrentGL();
		boolean foundError = false;
		GLU glu = new GLU();
		int glErr = gl.glGetError();
		
		while(glErr != gl.GL_NO_ERROR) {
			System.err.println("glError: " + glu.gluErrorString(glErr));
			foundError = true;
			glErr = gl.glGetError();
		}
		
		return foundError;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
		if(e.getWheelRotation() > 0  && x < 0.4999998f)
			x += inc;
		else if (e.getWheelRotation() < 0 && x > -0.4999998f)
			x -= inc;
	}
	
}
