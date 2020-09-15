package creatures;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

/** Tests the plip class
 *  @authr FIXME
 */

public class TestPlip {

    @Test
    public void testBasics() {
        Plip p = new Plip(2);
        assertEquals(2, p.energy(), 0.01);
        assertEquals(new Color(99, 255, 76), p.color());
        p.move();
        assertEquals(1.85, p.energy(), 0.01);
        p.move();
        assertEquals(1.70, p.energy(), 0.01);
        p.stay();
        assertEquals(1.90, p.energy(), 0.01);
        p.stay();
        assertEquals(2.00, p.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        // TODO
    }

    @Test
    public void testChoose() {

        // No empty adjacent spaces; stay.
        Plip p = new Plip(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual = p.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);


        // Energy >= 1; replicate towards an empty space.
        p = new Plip(1.2);
        HashMap<Direction, Occupant> topEmpty = new HashMap<Direction, Occupant>();
        topEmpty.put(Direction.TOP, new Empty());
        topEmpty.put(Direction.BOTTOM, new Impassible());
        topEmpty.put(Direction.LEFT, new Impassible());
        topEmpty.put(Direction.RIGHT, new Impassible());

        actual = p.chooseAction(topEmpty);
        expected = new Action(Action.ActionType.REPLICATE, Direction.TOP);

        assertEquals(expected, actual);


        // Energy >= 1; replicate towards an empty space.
        p = new Plip(1.2);
        HashMap<Direction, Occupant> allEmpty = new HashMap<Direction, Occupant>();
        allEmpty.put(Direction.TOP, new Empty());
        allEmpty.put(Direction.BOTTOM, new Empty());
        allEmpty.put(Direction.LEFT, new Empty());
        allEmpty.put(Direction.RIGHT, new Empty());

        actual = p.chooseAction(allEmpty);
        Action unexpected = new Action(Action.ActionType.STAY);

        assertNotEquals(unexpected, actual);


        // Energy < 1; stay.
        p = new Plip(.99);

        actual = p.chooseAction(allEmpty);
        expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);


        // Energy < 1; stay.
        p = new Plip(.99);

        actual = p.chooseAction(topEmpty);
        expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);


        // We don't have Cloruses yet, so we can't test behavior for when they are nearby right now.
        // Will move to any available empty square with probability 50%.
        p = new Plip(2)
        HashMap<Direction, Occupant> hasCloruses = new HashMap<>();
        hasCloruses.add(Direction.TOP, new Empty());
        hasCloruses.add(Direction.BOTTOM, new Cloruese(2));
        hasCloruses.add(Direction.LEFT, new Impassible());
        hasCloruses.add(Direction.RIGHT, new Empty());
        Action action1 = new Action(Action.ActionType.STAY);
        Action action2 = new Action(Action.ActionType.MOVE, Direction.TOP);
        Action action3 = new Action(Action.ActionType.MOVE, Direction.RIGHT);
        int s = 0,
        	top = 0,
        	bottom =0,
        	left = 0,
        	right = 0;

        for (int i = 0; i < 1000; i += 1) {
        	Action actual = p.chooseAction(hasCloruses);
        	if (actual.equals(STAY)) {
        		s += 1;
        	} else if (actual.equals(TOP)) {
        		top += 1;
        	} else if (actual.equals(RIGHT)) {
        		right += 1;
        	} else {
        		bottom += 1;
        		left += 1;
        	}
        }

        assertEquals(0.5, (double)s / (double)1000, 0.1);
        assertEquals(0.5, (double) (top + right) / double(1000), 0.1);
        assertEquals(0, bottom);
        assertEquals(0, left);
        assertEquals(1000, s + top + right);
    }
}
