#version 430

uniform float xbr;
uniform float xbl;
uniform float xt;
uniform float ybr;
uniform float ybl;
uniform float yt;
uniform int gradient;

out vec4 incolor;

void main(void) {
	if (gl_VertexID == 0) {
		gl_Position = vec4(0.5 + xbr, -0.5 + ybr, 0.0, 1.0); //bottom right
		if (gradient == 1)
			incolor = vec4(1.0, 0.0, 0.0, 0.0);
		else
			incolor = vec4(1.0, 1.0, 1.0, 0.0);
	}
	else if (gl_VertexID == 1) {
		gl_Position = vec4(-0.5 + xbl, -0.5 + ybl, 0.0, 1.0); //bottom left
		if (gradient == 1)
			incolor = vec4(0.0, 1.0, 0.0, 0.0);
		else
			incolor = vec4(1.0, 1.0, 1.0, 0.0);
	}
	else {
		gl_Position = vec4(0.5 + xt, 0.5 + yt, 0.0, 1.0); //top
		if (gradient == 1)
			incolor = vec4(0.0, 0.0, 1.0, 0.0);
		else
			incolor = vec4(1.0, 1.0, 1.0, 0.0);
	}
}