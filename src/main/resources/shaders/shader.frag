#version 420 core

in vec2 pass_texture_cord;

uniform sampler2D textureSampler;

out vec4 out_color;

void main() {
		out_color = texture(textureSampler, pass_texture_cord);
}