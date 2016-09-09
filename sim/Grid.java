package slather.sim;

import java.util.*;

public class Grid {

    ArrayList< ArrayList< HashSet<GridObject>>> grid; // for computing distances
    ArrayList<Cell> cells = new ArrayList<Cell>(); // for shuffling order of movement
    int N;
    double d;
    double side;

    class GridObjectsContainer {
	public Set<Cell> nearby_cells;
	public Set<Pherome> nearby_pheromes;
	public GridObjectsContainer() {
	    this.nearby_cells = new HashSet<Cell>();
	    this.nearby_pheromes = new HashSet<Pherome>();
	}
    }
    
    public Grid(double side, double d) {
	this.d = d;
	N = (int) Math.round(Math.ceil(side / d));
	grid = new ArrayList< ArrayList< HashSet<GridObject> > >(N);
	this.side = side;
	for (int i=0; i<N; i++) {
	    grid.add(i, new ArrayList< HashSet<GridObject> >(N));
	    for (int j=0; j<N; j++) 
		grid.get(i).add(j, new HashSet<GridObject>());
	}

    }

    public void add(GridObject q) {
	grid.get(getI(q)).get(getJ(q)).add(q);
	if (q instanceof Cell)
	    cells.add((Cell)q);
    }

    public void readd(Cell q) {
	grid.get(getI(q)).get(getJ(q)).add(q);
    }

    public void remove(Cell q) {
	grid.get(getI(q)).get(getJ(q)).remove(q);
    }

    /*public Set<Cell> nearby_cells(Cell q) {
	Set<Cell> output = new HashSet<Cell>();
	for (int i = Math.max(0, getI(q)-1); i < Math.min(N, getI(q)+1); i++) {
	    for (int j = Math.max(0, getJ(q)-1); i < Math.min(N, getJ(q)+1); j++) {
		Iterator<Cell> it = cell_grid.get(i).get(j).iterator();
		while (it.hasNext()) {
		    Cell next = it.next();
		    if (q.distance(next) < d)
			output.add(next);
		}
	    }
	}
	return output;
    }
    public Set<Pherome> nearby_pheromes(Cell q) {
	Set<Pherome> output = new HashSet<Pherome>();
	for (int i = Math.max(0, getI(q)-1); i < Math.min(N, getI(q)+1); i++) {
	    for (int j = Math.max(0, getJ(q)-1); i < Math.min(N, getJ(q)+1); j++) {
		Iterator<GridObject> it = grid.get(i).get(j).iterator();
		while (it.hasNext()) {
		    GridObject next = it.next();
		    if (q.distance(next) < d && next instanceof Pherome)
			output.add((Pherome) next);
		}
	    }
	}
	return output;
    }*/

    public ArrayList<Cell> shuffle_cells() {
	Collections.shuffle(cells);
	return cells;
    }

    public void age_pheromes() {
	for (int i = 0; i<N; i++) {
	    for (int j = 0; j<N; j++) {
		Iterator<GridObject> it = grid.get(i).get(j).iterator();
		while (it.hasNext()) {
		    GridObject next = it.next();
		    if (next instanceof Pherome) {
			Pherome casted_next = (Pherome) next;
			if (casted_next.step())
			    it.remove();
		    }
		}
	    }
	}
    }

    public GridObjectsContainer get_nearby(Cell q) {
	GridObjectsContainer output = new GridObjectsContainer();
	for (int i = Math.max(0, getI(q)-2); i < Math.min(N, getI(q)+2); i++) {
	    for (int j = Math.max(0, getJ(q)-2); j < Math.min(N, getJ(q)+2); j++) {
		Iterator<GridObject> it = grid.get(i).get(j).iterator();
		while (it.hasNext()) {
		    GridObject next = it.next();
		    if (q.distance(next) < d) {
			if (next instanceof Cell && next != q)
			    output.nearby_cells.add( (Cell)next);
			else if (next instanceof Pherome)
			    output.nearby_pheromes.add( (Pherome)next);
		    }
		}
	    }
	}
	return output;
    }

    public String cells_state() {
	StringBuffer buf = new StringBuffer();
	boolean first = true;			
	for (int i = 0; i<N; i++) {
	    for (int j = 0; j<N; j++) {
		Iterator<GridObject> it = grid.get(i).get(j).iterator();
		while (it.hasNext()) {
		    GridObject next = it.next();
		    if (next instanceof Cell) {
			if (first)
			    first = false;
			else
			    buf.append(";");
			Cell casted_next = (Cell)next;
			buf.append(next.player + "," + next.getPosition().x + "," + next.getPosition().y + "," + casted_next.getDiameter());			
		    }
		}
	    }
	}
	return buf.toString();
    }

    public String pheromes_state() {
	StringBuffer buf = new StringBuffer();
	boolean first = true;			
	for (int i = 0; i<N; i++) {
	    for (int j = 0; j<N; j++) {
		Iterator<GridObject> it = grid.get(i).get(j).iterator();
		while (it.hasNext()) {
		    GridObject next = it.next();
		    if (next instanceof Pherome) {
			if (first)
			    first = false;
			else
			    buf.append(";");
			buf.append(next.player + "," + next.getPosition().x + "," + next.getPosition().y);			
		    }
		}
	    }
	}
	return buf.toString();
    }

    private int getI(GridObject q) {
	return (int)Math.round(Math.floor(q.getPosition().x / d));
    }

    private int getJ(GridObject q) {
	return (int)Math.round(Math.floor(q.getPosition().y / d));
    }    

    
}
