public class NBody{
	/**
	* read the radius from File.
	*/
	public static double readRadius(String filename){
		In in = new In(filename);
		in.readInt();
		double radiusInFile = in.readDouble();
		return radiusInFile;
	}
	/**
	* read the the information about the bodies from File and construct a Body array.
	*/	
	public static Body[] readBodies(String filename){
		In in = new In(filename);
		int num = in.readInt();
		in.readDouble();
		Body[] planets = new Body[num];
		/**
		* Can not use enhanced for loop, it seems that enhanced for loop 
		* can not change the element in array.
		*/
		for(int i = 0; i < num; i = i + 1){
			double xPos = in.readDouble(),
				   yPos = in.readDouble(),
				   xVel = in.readDouble(),
				   yVel = in.readDouble(),
				   m = in.readDouble();
			String img = in.readString();
			planets[i] = new Body(xPos, yPos, xVel, yVel, m, img);
		}
		return planets;
	}

	public static void drawAllBodies(Body [] bodies){
		for(Body planet : bodies){
			planet.draw();
		}
	}
	public static void main(String[] args){
		double T = Double.parseDouble(args[0]),
			   dt = Double.parseDouble(args[1]);	   
		String fileName = args[2];
		double radius = readRadius(fileName);
		Body[] allPlanets = readBodies(fileName);
		int lengthBodies = allPlanets.length;
		
		/**
		* draw the background and each body in Body array.
		* use command javac --encoding utf-8 NBody.java.
		* Because StdDraw.java have unmappable character for encoding GBK.
		*/
		StdDraw.setScale(-1.0 * radius, radius);
		StdDraw.clear();
		StdDraw.picture(0, 0, "images/starfield.jpg");
		drawAllBodies(allPlanets);

		/**
		* creat a animation.
		*/
		StdDraw.enableDoubleBuffering();
		for (double t = 0; t < T; t = t + dt){
			double[] xForces = new double[lengthBodies],
					 yForces = new double[lengthBodies];
			for (int i = 0 ; i < lengthBodies; i = i + 1){
				xForces[i] = allPlanets[i].calcNetForceExertedByX(allPlanets);
				yForces[i] = allPlanets[i].calcNetForceExertedByY(allPlanets);
			}
			for (int j = 0; j < lengthBodies; j = j + 1){
				allPlanets[j].update(dt, xForces[j], yForces[j]);
			}
			/**
			* need to draw a new background or it will overlapping
			*/
			StdDraw.clear();
			StdDraw.picture(0, 0, "images/starfield.jpg");
			drawAllBodies(allPlanets);
			StdDraw.show();
			StdDraw.pause(10);
		}
		System.out.println(lengthBodies);
		System.out.println(radius);
		for(int k = 0; k < lengthBodies; k = k + 1){
			System.out.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
							allPlanets[k].xxPos, allPlanets[k].yyPos, allPlanets[k].xxVel,
							allPlanets[k].yyVel, allPlanets[k].mass, allPlanets[k].imgFileName);
		}
	}
}