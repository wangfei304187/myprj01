#version 330

in vec3 fragPosition;

uniform sampler3D tex3D;

uniform float wc;
uniform float ww;
uniform float rescaleSlope;
uniform float rescaleIntercept;

uniform float texSliceLocation;
uniform float texSliceThickness;
uniform vec3 cameraDirection;
uniform vec3 scale;

float average(vec3 position, int stepNum, float step)
{
	// parameters for loops
	int count = 0;
	float colorIndex = 0;
	vec3 point = vec3(0.0, 0.0, 0.0);
	
	// for the current slice location
	point = position + texSliceLocation * cameraDirection;
	point = point / scale;
	// point = point + vec3(0.5, 0.5, 0.5);
	
	if(point.x >= 0 && point.x <= 1 && point.y >= 0 && point.y <=1 && point.z >= 0 && point.z <= 1)
	{
		count = count + 1;
    	colorIndex = texture(tex3D, point).x;
    }
	
	// loops for other
	for(int i = 1; i < stepNum; i++)
	{		
		// right point
		point =  position + (texSliceLocation + i * step) * cameraDirection;
		point = point / scale;
		//point = point + vec3(0.5, 0.5, 0.5);
		
		if(point.x >= 0 && point.x <= 1 && point.y >= 0 && point.y <=1 && point.z >= 0 && point.z <= 1)
		{
			count = count + 1;
    		colorIndex = colorIndex + texture(tex3D, point).x;
    	}

		// left point
		point =  position + (texSliceLocation - i * step) * cameraDirection;
		point = point / scale;
		//point = point + vec3(0.5, 0.5, 0.5);
		
		if(point.x >= 0 && point.x <= 1 && point.y >= 0 && point.y <=1 && point.z >= 0 && point.z <= 1)
		{
			count = count + 1;
			colorIndex = colorIndex + texture(tex3D, point).x;
    	}
	}

	if(count > 0)
	{
		colorIndex = colorIndex / count;
	}
    else
    {
        discard;
    }
	
	return colorIndex;
}

float mip(vec3 position, int stepNum, float step)
{
	// parameters for loops
	int count = 0;
	float temp = 0;
	float colorIndex = 0;
	vec3 point = vec3(0.0, 0.0, 0.0);
	
	// for the current slice location
	point =  position + texSliceLocation * cameraDirection;
	point = point / scale;
		
	if(point.x >= 0 && point.x <= 1 && point.y >= 0 && point.y <=1 && point.z >= 0 && point.z <= 1)
	{
	    count = count + 1;
    	colorIndex = texture(tex3D, point).x;
    }
	
	// loops for other
	for(int i = 1; i < stepNum; i++)
	{		
		// right point
		point =  position + (texSliceLocation + i * step) * cameraDirection;
		point = point / scale;
		
		if(point.x >= 0 && point.x <= 1 && point.y >= 0 && point.y <=1 && point.z >= 0 && point.z <= 1)
		{
			temp = texture(tex3D, point).x;
			
			if(colorIndex < temp)
			{
			    count = count + 1;
				colorIndex = temp;
			}
    	}

		// left point
		point =  position + (texSliceLocation - i * step) * cameraDirection;
		point = point / scale;
		
		if(point.x >= 0 && point.x <= 1 && point.y >= 0 && point.y <=1 && point.z >= 0 && point.z <= 1)
		{
			temp = texture(tex3D, point).x;
			
			if(colorIndex < temp)
			{
			    count = count + 1;
				colorIndex = temp;
			}
    	}
	}
	
	if(count == 0)
    {
        discard;
    }
	
	//return color;
	return colorIndex;
}

void main(void)
{

	// position at center slice
    vec3 position = fragPosition; // - vec3(0.5, 0.5, 0.5);
    position = position * scale;
	
	// position = position - dot(position, cameraDirection) * cameraDirection;
	
	// step and its number
	int totalStep = int(512 * texSliceThickness * 0.5 + 0.5) + 1;
	float step = 0.5 * texSliceThickness / totalStep;
	
	// float colorIndex = average(position, totalStep, step);
	float colorIndex = mip(position, totalStep, step);
	
	float HU = colorIndex * 65535.0;
    float grey = HU * rescaleSlope + rescaleIntercept;
    float low = wc - 0.5 * ww;
    float high = wc + 0.5 * ww;
    
    if(grey < low)
    {
        grey = 0.0;
    }
    else if(grey > high)
    {
        grey = 1.0;
    }
    else
    {
        grey = (grey - low) / (high - low);
    }
    
    gl_FragColor = vec4(grey, grey, grey, 1.0);


    /*
    vec3 position = fragPosition;
    position = position * scale;
	
	// vec3 point = position - dot(position, cameraDirection) * cameraDirection + texSliceLocation * cameraDirection;
	vec3 point = position + texSliceLocation * cameraDirection;
	point = point / scale;
	
	
	if(point.x < 0 || point.y < 0 || point.z < 0)
	{
    	gl_FragColor = vec4(0.0, 0, 0, 1.0);
    }
    else if(point.x > 1 || point.y > 1 || point.z > 1)
    {
    	gl_FragColor = vec4(0, 0.0, 0, 1.0);
    }
    else
    {
    	float HU = texture(tex3D, point).x  * 65535.0;
    	float grey = HU * rescaleSlope + rescaleIntercept;
	    float low = wc - 0.5 * ww;
	    float high = wc + 0.5 * ww; 
	    
	    if(grey < low)
	    {
	        grey = 0.0;
	    }
	    else if(grey > high)
	    {
	        grey = 1.0;
	    }
	    else
	    {
	        grey = (grey - low) / (high - low);
	    }
	    
	    gl_FragColor = vec4(grey, grey, grey, 1.0);
    }
    */
}	