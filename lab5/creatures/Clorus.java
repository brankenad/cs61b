package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static huglife.HugLifeUtils.randomEntry;

public class Clorus extends Creature {

    private int r;
    private int g;
    private int b;

    public Clorus(double e) {
        super("clorus");
        r = 34;
        g = 0;
        b = 231;
        energy = e;
    }

    public Clorus() {
        this(1);
    }

    @Override
    public Color color() {
        return new Color(r, g, b);
    }

    @Override
    public void move() {
        energy -= 0.03;
        if (energy <= 0.0) {
            energy = 0.0;
        }
    }

    @Override
    public void stay() {
        energy -= 0.01;
        if (energy <= 0) {
            energy = 0;
        }
    }

    @Override
    public void attack(Creature c) {
        energy += c.energy();
    }

    @Override
    public Creature replicate() {
        energy *= 0.5;
        Clorus newClorus = new Clorus(energy);
        return newClorus;
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> isPlip = new ArrayDeque<>();

        for (Direction key : neighbors.keySet()) {
            if (neighbors.get(key).name().equals("empty")) {
                emptyNeighbors.add(key);
            } else if (neighbors.get(key).name().equals("plip")) {
                isPlip.add(key);
            }
        }

        if (emptyNeighbors.size() == 0) {
            /*
             * Rule 1
             * If there are no empty squares, the Clorus will STAY,
             * even if there are Plips nearby they could attack since plip squares do not count as empty squares
             */
            return new Action(Action.ActionType.STAY);
        } else if (isPlip.size() != 0) {
            /* Rule 2
             * Otherwise, if any Plips are seen, the Clorus will ATTACK one of them randomly.
             */
            return new Action(Action.ActionType.ATTACK, randomEntry(isPlip));
        } else if (energy >= 1) {
            /*
             * Otherwise, if the Clorus has energy greater than or equal to one,
             * it will REPLICATE to a random empty square.
             */
            return new Action(Action.ActionType.REPLICATE,randomEntry(emptyNeighbors));
        }
        /*
         * Otherwise, the Clorus will MOVE to a random empty square.
         */
        return new Action(Action.ActionType.MOVE, randomEntry(emptyNeighbors));
    }

}
