package creatures;

import huglife.*;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;

import static org.junit.Assert.*;

public class TestClorus {

    @Test
    public void testBasics() {
        Clorus c = new Clorus(3);
        Color color = new Color(34, 0, 231);
        assertEquals(3, c.energy(), 0.01);
        assertEquals(color, c.color());
        c.move();
        assertEquals(2.97, c.energy(), 0.01);
        c.attack(new Plip(1.5));
        assertEquals(4.47, c.energy(), 0.01);
        c.stay();
        assertEquals(4.46, c.energy(), 0.01);
        Clorus c2 = (Clorus)c.replicate();
        assertEquals(2.23, c.energy(), 0.01);
        assertEquals(2.23, c2.energy(), 0.01);
    }

    @Test
    public void testChooseAction() {
        //test Rule 1.
        Clorus c = new Clorus(3);
        HashMap<Direction, Occupant> noEmpty = new HashMap<>();
        noEmpty.put(Direction.TOP, new Plip());
        noEmpty.put(Direction.BOTTOM, new Plip());
        noEmpty.put(Direction.LEFT, new Plip());
        noEmpty.put(Direction.RIGHT, new Plip());
        Action actual = c.chooseAction(noEmpty);
        Action expected = new Action(Action.ActionType.STAY);
        assertEquals(actual, expected);

        // test Rule 2.
        HashMap<Direction,Occupant> hasPlip = new HashMap<>();
        hasPlip.put(Direction.TOP, new Plip());
        hasPlip.put(Direction.BOTTOM, new Empty());
        hasPlip.put(Direction.LEFT, new Plip());
        hasPlip.put(Direction.RIGHT, new Plip());
        Action action1 = new Action(Action.ActionType.ATTACK, Direction.TOP);
        Action action2 = new Action(Action.ActionType.ATTACK, Direction.LEFT);
        Action action3 = new Action(Action.ActionType.ATTACK, Direction.RIGHT);
        int t = 0, b = 0, l = 0, r = 0;
        for (int i = 0; i < 1000; i += 1) {
            Action action = c.chooseAction(hasPlip);
            if (action.equals(action1)) {
                t += 1;
            } else if (action.equals(action2)) {
                l += 1;
            } else if (action.equals(action3)) {
                r += 1;
            } else {
                b += 1;
            }
        }
        assertEquals(b, 0);
        assertEquals(0.33, (double)t / (double) (t + l + r), 0.1);
        assertEquals(0.33, (double)l / (double) (t + l + r), 0.1);
        assertEquals(0.33, (double)r / (double) (t + l + r), 0.1);
        assertEquals(t + l + r, 1000);

        // Test Rule 3.
        HashMap<Direction,Occupant> needReplicate = new HashMap<>();
        needReplicate.put(Direction.TOP, new Impassible());
        needReplicate.put(Direction.BOTTOM, new Empty());
        needReplicate.put(Direction.LEFT, new Impassible());
        needReplicate.put(Direction.RIGHT, new Empty());
        Action action4 = new Action(Action.ActionType.REPLICATE, Direction.BOTTOM);
        Action action5 = new Action(Action.ActionType.REPLICATE, Direction.RIGHT);
        int top = 0,
            bottom = 0,
            left = 0,
            right = 0;
        for (int i = 0; i < 1000; i += 1) {
            Action actual2 = c.chooseAction(needReplicate);
            if (actual2.equals(action4)) {
                bottom += 1;
            } else if (actual2.equals(action5)) {
                right += 1;
            } else {
                top += 1;
                right += 1;
            }
        }
        assertEquals(0, top);
        assertEquals(0, left);
        assertEquals(0.5, (double)bottom / (double)(bottom + right), 0.1);
        assertEquals(0.5,(double)right / (double)(bottom + right), 0.1);
        assertEquals(1000, bottom + right);
    }
}
