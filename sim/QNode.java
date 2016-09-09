package slather.sim;

import java.util.*;

public class QNode {

    public Set<Cell> members;
    public QNode parent;
    public QNode top = null;
    public QNode bottom = null;
    public QNode left = null;
    public QNode right = null;
    public double size;
    public double x;
    public double y;

    public QNode(QNode parent, double x, double y, double size) {
	this.parent = parent;
	this.size = size;
	this.x = x;
	this.y = y;
	members = new HashSet<Cell>();	
    }

    public boolean isLeaf() {
	return (top == null);
    }

    public boolean contains(Cell q) {
	return ( Math.abs(q.x - x) < size/2 && Math.abs(q.y - y) < size/2 );
    }

    public void add_cell(Cell q) {
	members.add(q);
    }

    public void remove_cell(Cell q) {
	members.remove(q);
    }


    // move cell q by a vector v. return true if the cell is no longer in this subquadrant and remove the cell from the member set, otherwise return false
    public boolean move_cell(Cell q, Cell v, Set<Pherome> pheromes, Set<Cell> cells) {
	if (!members.remove(q))
	    throw new RuntimeException("Tree missing element.");
	Cell new_cell = q.move(v);
    }

}
