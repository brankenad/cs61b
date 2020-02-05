public class Body{
	public double xxPos,
				  yyPos,
				  xxVel,
				  yyVel,
				  mass;
	public String imgFileName;
	public static double gravi_constant = 6.67e-11;

	public Body(double xP, double yP, double xV, double yV, double m, String img){
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Body(Body b){
		xxPos = b.xxPos;
		yyPos = b.yyPos;
		xxVel = b.xxVel;
		yyVel = b.yyVel;
		mass = b.mass;
		imgFileName = b.imgFileName;
	}

	public void draw(){
		StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
	}

	/**
	* calcuate the dx,dy between this body and body n.
	* use x(n) - x(this) because we need to consider the direction of force later.
	*/
	public double[] calcDistanceDxy(Body n){
		double dx = n.xxPos - this.xxPos,
			   dy = n.yyPos - this.yyPos;
		double[] distancexy = new double[2];
		distancexy[0] = dx;
		distancexy[1] = dy;
		return distancexy;
	}

	public double calcDistanceDx(Body n){
		return calcDistanceDxy(n)[0];
	}

	public double calcDistanceDy(Body n){
		return calcDistanceDxy(n)[1];
	}

	/**
	* calculate the distance of this body and body n.
	*/
	public double calcDistance(Body n){
		double dx_squa = java.lang.Math.pow(this.calcDistanceDx(n), 2),
			   dy_squa = java.lang.Math.pow(this.calcDistanceDy(n), 2);
		return java.lang.Math.sqrt(dx_squa + dy_squa);
	}

	/**
	* calcuate the force between this body and body m.
	*/
	public double calcForceExertedBy(Body m){
		double productMass = this.mass * m.mass;
		return Body.gravi_constant * productMass / java.lang.Math.pow(this.calcDistance(m), 2);
	}

	/**
	* calculate the Fx, Fy(F is exerted by body m)
	*/
	public double[] calcForceExertedByXY(Body m){
		double force = this.calcForceExertedBy(m),
			   dx = this.calcDistanceDx(m),
			   dy = this.calcDistanceDy(m),
			   distance = this.calcDistance(m);
		double[] forceXY = new double[2];
		forceXY[0] = force * dx / distance;
		forceXY[1] = force * dy / distance;
		return forceXY;
	}


	public double calcForceExertedByX(Body m){
		return calcForceExertedByXY(m)[0];
	}

	public double calcForceExertedByY(Body m){
		return calcForceExertedByXY(m)[1];
	}

	/**
	*calcuate Ftx,Fty(F is exerted by other bodys)
	*use the enhanced for loop  
	*/
	public double[] calcNetForceExertedByXY(Body[] allBodys){
		double[] netforce = new double[2];
		for(Body element : allBodys){
			if(element.equals(this)){
				continue;
			}
			netforce[0] = netforce[0] + calcForceExertedByX(element);
			netforce[1] = netforce[1] + calcForceExertedByY(element);
		} 
		return netforce;
	}

	public double calcNetForceExertedByX(Body[] allBodys){
		return calcNetForceExertedByXY(allBodys)[0];
	}

	public double calcNetForceExertedByY(Body[] allBodys){
		return calcNetForceExertedByXY(allBodys)[1];
	}

	/**
	*update the position of current body because of the force exerted by others.
	*assume the delta(pos) = v(new) * dt
	*/
	public void update(double dt, double fX, double fY){
		double aX = fX / this.mass,
			   aY = fY / this.mass;
		this.xxVel = this.xxVel + aX * dt;
		this.yyVel = this.yyVel + aY * dt;
		this.xxPos = this.xxPos + this.xxVel * dt;
		this.yyPos = this.yyPos + this.yyVel * dt;
	}
}