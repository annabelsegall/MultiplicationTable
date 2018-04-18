import java.util.ArrayList;
import java.util.function.Consumer;

public class MultiplicationTable {
	private int state[][];
	private ArrayList<Consumer<Long>> listeners;
	private final long startTime;
	
	public long getStartTime() {
		return startTime;
	}

	public MultiplicationTable(int n) {
		state = new int[n][n];
		for(int i = 0 ; i < state.length ; i++ ) {
			for(int j = 0 ; j < state.length ; j++ ) {
				state[i][j]= -1;
			}
		}
		listeners = new ArrayList<>();
		startTime = System.currentTimeMillis();
	}
	
	public boolean isCorrect() {
		for(int i = 0 ; i < state.length ; i++ ) {
			for(int j = 0 ; j < state.length ; j++ ) {
				if (state[i][j] != (i+1)*(j+1)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean set(int i, int j, int val) {
		state[i][j] = val;
		if(isCorrect()) {
			long time = System.currentTimeMillis()-startTime;
			listeners.forEach(l -> l.accept(time));
		}
		return val == (i+1)*(j+1);
	}
	
	public void addEndOfGameListener(Consumer<Long> listener) {
		listeners.add(listener);
	}

	public boolean cellCorrect(int i, int j) {
		// TODO Auto-generated method stub
		return state[i][j] == (i+1)*(j+1);
	}

	public boolean cellZero(int i, int j) {
		// TODO Auto-generated method stub
		return state[i][j] == -1;
	}
}
