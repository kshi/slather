package slather.sim;

import java.util.*;

public class Cell extends GridObject{

    public static final int move_dist = 1;
    public static final int reproduce_size = 2;
    private double diameter;
    protected byte memory; // simulator will give you this directly, this is declared protected so you don't access other people's memory

    public Cell(Point position, int player, double diameter) {
	super(position, player);
	this.diameter = diameter;
    }

    public double distance(GridObject other) {
	return diameter/2 + super.distance(other);
    }

    public Cell(Point position, int player) {
	this(position, player, 1);
    }   

    public Point getPosition() {
	return position;
    }

    public double getDiameter() {
	return diameter;
    }

    // called by simulator
    protected void move(Point vector, Set<Pherome> pheromes, Set<Cell> cells) {
	if (vector.norm() > move_dist + 0.000000001)
	    //return;
	    throw new IllegalArgumentException("Cell cannot move more than " + move_dist + " per turn.");
	Point new_position = position.move(vector);
	Iterator<Pherome> pherome_it = pheromes.iterator();	
	while (pherome_it.hasNext()) {
	    Pherome next = pherome_it.next();
	    if (new_position.distance(next.getPosition()) < 0.5*diameter && player != next.player) {
		return;
	    }
	}
	Iterator<Cell> cell_it = cells.iterator();
	while (cell_it.hasNext()) {
	    Cell next = cell_it.next();
	    if (new_position.distance(next.getPosition()) < 0.5*diameter + 0.5*next.getDiameter() + 0.0001) {
		return;
	    }
	}
	position = new_position;	
    }

    
    // grow by 1% or max possible without colliding
    protected void step(Set<Pherome> pheromes, Set<Cell> cells) {
	Iterator<Pherome> pherome_it = pheromes.iterator();
	double new_diameter = diameter * 1.01;
	while (pherome_it.hasNext()) {
	    Pherome next = pherome_it.next();
	    if (next.player != player)
		new_diameter = Math.min(new_diameter, 2*distance(next));
	}
	Iterator<Cell> cell_it = cells.iterator();
	while (cell_it.hasNext()) {
	    Cell next = cell_it.next();
	    new_diameter = Math.min(new_diameter, 2*(distance(next) - 0.5*next.getDiameter()));
	}
	if (new_diameter > 2)
	    new_diameter = 2.0000001;
	diameter = Math.max(new_diameter, diameter);
    }
    protected void halve() {
	diameter = diameter / 2;
    }
    protected Pherome secrete(int max_duration) {
	return new Pherome(position, player, max_duration);
    }

}
