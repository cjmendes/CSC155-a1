package a1;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;

import graphicslib3D.GLSLUtils;

public class VertexReading {
	
	// Initializes Variables
	private static GL4 gl;
	private GLSLUtils util = new GLSLUtils();
	private ErrorChecking eCheck;
	
	// Class Constructor
	public VertexReading(GL4 gl) {
		VertexReading.gl = gl;
		eCheck = new ErrorChecking(VertexReading.gl);
	}
	
	// Function creates the shader program and checks for errors
	@SuppressWarnings("static-access")
	public int createShaderProgram() {
		gl = (GL4) GLContext.getCurrentGL();
		int[] vertCompiled = new int[1];
		int[] fragCompiled = new int[1];
		int[] linked = new int[1];
		
		String vShaderSource[] = util.readShaderSource("src/assets/vert.shader");
		String fShaderSource[] = util.readShaderSource("src/assets/frag.shader");
		
		int vShader = gl.glCreateShader(gl.GL_VERTEX_SHADER);
		int fShader = gl.glCreateShader(gl.GL_FRAGMENT_SHADER);
		
		// Vertex compilation and error checking
		gl.glShaderSource(vShader, vShaderSource.length, vShaderSource, null, 0);
		gl.glCompileShader(vShader);
		
		eCheck.checkOpenGLError();
		gl.glGetShaderiv(vShader, gl.GL_COMPILE_STATUS, vertCompiled, 0);
		if(vertCompiled[0] == 1)
			System.out.println("Vertex Compilation Succeeded");
		else {
			System.out.println("Vertex Compilation Failed");
			eCheck.printShaderLog(vShader);
		}
		
		// Fragment compilation and error checking
		gl.glShaderSource(fShader, fShaderSource.length, fShaderSource, null, 0);
		gl.glCompileShader(fShader);
		
		eCheck.checkOpenGLError();
		gl.glGetShaderiv(fShader, gl.GL_COMPILE_STATUS, fragCompiled, 0);
		if(fragCompiled[0] == 1)
			System.out.println("Fragment Compilation Succeeded");
		else {
			System.out.println("Fragment Compilation Failed");
			eCheck.printShaderLog(fShader);
		}
		
		// Linking and error checking
		int vfprogram = gl.glCreateProgram();
		gl.glAttachShader(vfprogram, vShader);
		gl.glAttachShader(vfprogram, fShader);
		gl.glLinkProgram(vfprogram);
		
		eCheck.checkOpenGLError();
		gl.glGetProgramiv(vfprogram, gl.GL_LINK_STATUS, linked, 0);
		if(linked[0] == 1)
			System.out.println("Linking Succeeded");
		else {
			System.out.println("Linking Failed");
			eCheck.printProgramLog(vfprogram);
		}
		
		gl.glDeleteShader(vShader);
		gl.glDeleteShader(fShader);
		return vfprogram;
	}

}
