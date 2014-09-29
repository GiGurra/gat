precision 	mediump float;
precision 	mediump int;

uniform 	mat4    uniform_Transformation;
attribute 	vec4  	attribute_Position;
attribute 	vec4  	attribute_Color;
varying 	vec4    varying_Color;

void main(void) {
	varying_Color = attribute_Color;
	gl_Position = uniform_Transformation * attribute_Position;
}
