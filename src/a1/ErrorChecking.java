package a1;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.glu.GLU;

public class ErrorChecking {

	// initialize Variables
	private static GL4 gl;
	
	// Class Constructor
	public ErrorChecking(GL4 gl) {
		ErrorChecking.gl = gl;
	}
	
	// Checks for errors in the shader and returns a log of the errors
	@SuppressWarnings("static-access")
	public void printShaderLog(int shader) {
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
	
	// Checks for errors in the linking and returns a log of the errors
	@SuppressWarnings("static-access")
	public void printProgramLog(int prog) {
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
	
	// Checks for OpenGL errors and returns a log of the errors
	@SuppressWarnings("static-access")
	public boolean checkOpenGLError() {
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
}
