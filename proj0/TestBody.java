/**
 *  Tests the whole body up to the finishing of update method.
 */
public class TestBody{

    public static void main(String[] args) {
        checkWholeBody();
    }

    /**
    *  Checks whether or not two Doubles are equal and prints the result.
    *
    *  @param  expected    Expected double
    *  @param  actual      Double received
    *  @param  label       Label for the 'test' case
    *  @param  eps         Tolerance for the double comparison.
    */
    private static void checkEquals(double actual, double expected, String label, double eps) {
        if (Double.isNaN(actual) || Double.isInfinite(actual)) {
            System.out.println("FAIL: " + label
                    + ": Expected " + expected + " and you gave " + actual);
        } else if (Math.abs(expected - actual) <= Math.abs(eps * Math.max(expected, actual))) {
            System.out.println("PASS: " + label
                    + ": Expected " + expected + " and you gave " + actual);
        } else {
            System.out.println("FAIL: " + label
                    + ": Expected " + expected + " and you gave " + actual);
        }
    }


    /**
     *  Checks the Body class to make sure update works.
     */
    private static void checkWholeBody() {
        System.out.println("Checking the whole body up to the finishing of update method...");

        Body b1 = new Body(1.0, 1.0, 3.0, 4.0, 5.0, "jupiter.gif");
        Body b2 = new Body(2.0, 1.0, 3.0, 4.0, 4e11, "jupiter.gif");
        Body b3 = new Body(4.0, 5.0, 3.0, 4.0, 5.0, "jupiter.gif");

        System.out.println("Checking the distacne model");
        checkEquals(b1.calcDistanceDx(b2), 1.0, "calcDistanceDx", 0.01);
        checkEquals(b1.calcDistanceDy(b2), 0.0, "calcDistanceDy", 0.01);
        checkEquals(b1.calcDistance(b2), 1.0, "calcDistance", 0.01);

        System.out.println("Checking the simple force model");
        checkEquals(b1.calcForceExertedBy(b2), 133.4, "calcForceExertedBy", 0.01);
        checkEquals(b2.calcForceExertedByX(b1), -133.4, "calcForceExertedByX", 0.01);
        checkEquals(b2.calcForceExertedByY(b1), 0.0, "calcForceExertedByY", 0.01);

        System.out.println("Checking the total force model");
        Body[] bodys = {b1, b2, b3};
        checkEquals(b1.calcNetForceExertedByX(bodys), 133.4 + 4.002e-11, "calcNetForceExertedByX()", 0.01);
        checkEquals(b1.calcNetForceExertedByY(bodys), 5.336e-11, "calcNetForceExertedByX()", 0.01);

        System.out.println("Checking the update model");
        b1.update(2.0, b1.calcNetForceExertedByX(bodys), b1.calcNetForceExertedByY(bodys));

        checkEquals(b1.xxVel, 56.4, "xxVel update()", 0.01);
        checkEquals(b1.yyVel, 4.0, "yyVel update()", 0.01);
        checkEquals(b1.xxPos, 113.7, "xxPos update()", 0.01);
        checkEquals(b1.yyPos, 9.0, "yyPos update()", 0.01);
    }
}