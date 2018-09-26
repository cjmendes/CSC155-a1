#version 430

uniform float xbr;
uniform float xbl;
uniform float xt;
uniform float ybr;
uniform float ybl;
uniform float yt;
uniform float scale;
uniform float theta;
uniform int moveCirc;
uniform int gradient;

out vec4 incolor;

void main(void) {
	if (gl_VertexID == 0) {
		if(moveCirc == 0)
			gl_Position = vec4(scale * (0.5 + xbr), scale * (-0.5 + ybr), 0.0, 1.0); //bottom right
		else
			gl_Position = vec4(scale * (0.5 + (0.5 * cos( radians(90 + theta)))), scale * (-0.5 + (0.5 * sin( radians(90 + theta)))), 0.0, 1.0);
		if (gradient == 1)
			incolor = vec4(1.0, 0.0, 0.0, 0.0);
		else
			incolor = vec4(1.0, 1.0, 1.0, 0.0);
	}
	else if (gl_VertexID == 1) {
		if(moveCirc == 0)
			gl_Position = vec4(scale * (-0.5 + xbl), scale * (-0.5 + ybl), 0.0, 1.0); //bottom left
		else
			gl_Position = vec4(scale * (-0.5 + (0.5 * cos( radians(90 + theta)))), scale * (-0.5 + (0.5 * sin( radians(90 + theta)))), 0.0, 1.0);
		if (gradient == 1)
			incolor = vec4(0.0, 1.0, 0.0, 0.0);
		else
			incolor = vec4(1.0, 1.0, 1.0, 0.0);
	}
	else {
		if(moveCirc == 0)
			gl_Position = vec4(scale * (0.5 + xt), scale * (0.5 + yt), 0.0, 1.0); //top
		else
			gl_Position = vec4(scale * (0.5 + (0.5 * cos( radians(90 + theta)))), scale * (0.5 + (0.5 * sin( radians(90 + theta)))), 0.0, 1.0);
		if (gradient == 1)
			incolor = vec4(0.0, 0.0, 1.0, 0.0);
		else
			incolor = vec4(1.0, 1.0, 1.0, 0.0);
	}
}