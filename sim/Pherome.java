package slather.sim;

public class Pherome extends GridObject{

    public final int max_duration;
    private int duration = 0;
    
    public Pherome(Point position, int player, int max_duration) {
	super(position, player);
	this.max_duration = max_duration;
    }

    protected boolean step() {
	return (++duration > max_duration);
    }

}
