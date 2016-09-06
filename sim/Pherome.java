package slather.sim;

public class Pherome {

    public final Point position;
    public final int player;
    public final int max_duration;    
    private int duration = 0;
    
    public Pherome(Point position, int player, int max_duration) {
	this.position = position;
	this.player = player;
	this.max_duration = max_duration;
    }

    protected boolean step() {
	return (++duration > max_duration);
    }
}
