package slather.sim;

import java.util.*;

public interface Player {

    public void init(int d, int t);
    
    public Move play(Cell player_cell,
		     byte memory,
		     Set<Cell> nearby_cells,
		     Set<Pherome> nearby_pheromes);
}
