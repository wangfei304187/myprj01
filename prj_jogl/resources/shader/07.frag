#version 330

in vec2 TexCoord;

uniform sampler2D ourTexture1;
uniform float wc;
uniform float ww;
uniform float rescaleSlope;
uniform float rescaleIntercept;

void main()
{
	// gl_FragColor = texture(ourTexture1, TexCoord);
    
    float HU = texture(ourTexture1, TexCoord).x * 65535.0;
    float red = HU * rescaleSlope + rescaleIntercept;
    float low = wc - 0.5 * ww;
    float high = wc + 0.5 * ww;
    
    if(red < low)
    {
        red = 0.0;
    }
    else if(red > high)
    {
        red = 1.0;
    }
    else
    {
        red = (red - low) / (high - low);
    }
    
    gl_FragColor = vec4(red, red, red, 1.0);
}