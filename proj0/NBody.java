public class NBody{
	public static double readRadius(String filename){
		In in = new In(filename);
		in.readInt();
		double rediusInFile = in.readDouble();
		return rediusInFile;
	}

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

	public static void main(String[] args){
		double T = Double.parseDouble(args[0]),
			   dt = Double.parseDouble(args[1]);	   
		String filename = args[2];
		double radius = readRadius(filename);
		StdDraw.setScale(-1.0 * radius, radius);
		StdDraw.clear();
		StdDraw.picture(0, 0, "images/starfield.jpg");
		Body[] allPlanets = readBodies(filename);
		/**
		* use command javac --encoding utf-8 NBody.java.
		* Because StdDraw.java have unmappable character for encoding GBK.
		*/
		for(Body planet : allPlanets){
			planet.draw();
		}
	}
}