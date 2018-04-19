import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MultiplicationTable {
	private int state[][];
	private ArrayList<Function<Long,Boolean>> listeners;
	private ArrayList<Runnable> closeListeners;
	private MultiplicationTablePanel panel;
	private long startTime;
	
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
		closeListeners = new ArrayList<>();
		startTime = System.currentTimeMillis();
	}
	public void reset(int n) {
		state = new int[n][n];
		startTime = System.currentTimeMillis();
		for(int i = 0 ; i < state.length ; i++ ) {
			for(int j = 0 ; j < state.length ; j++ ) {
				state[i][j]= -1;
			}
		}
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
			System.out.println("correct");
			long time = System.currentTimeMillis()-startTime;
			boolean end = false;
			for (Function<Long, Boolean> list : listeners) {
				if (list.apply(time)) end = true;
			}
			if (end) {
				for (Runnable close : closeListeners) {
					close.run();
				}
			}
		}
		return val == (i+1)*(j+1);
	}
	
	public void addEndOfGameListener(Function<Long,Boolean> listener) {
		listeners.add(listener);
	}
	
	public void addCloseListener(Runnable listener) {
		closeListeners.add(listener);
	}
	

	public boolean cellCorrect(int i, int j) {
		// TODO Auto-generated method stub
		return state[i][j] == (i+1)*(j+1);
	}

	public boolean cellZero(int i, int j) {
		// TODO Auto-generated method stub
		return state[i][j] == -1;
	}
	
	public int findFirstFree() {
		for(int i = 0 ; i < state.length ; i++ ) {
			for(int j = 0 ; j < state.length ; j++ ) {
				if (state[i][j] != (i+1)*(j+1)) {
					set(i, j, (i+1)*(j+1));
					return state.length * i + j;
				}
			}
		}
		return 1; 
	}

	public MultiplicationTablePanel getPanel() {
		return panel;
	}

	public void setPanel(MultiplicationTablePanel panel) {
		this.panel = panel;
	}
}
