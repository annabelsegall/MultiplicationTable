
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Main {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		int n = -1;
		Player p = new Player();
		n = -1;
		p.first = 0;
		JFrame rootFrame = new JFrame("Multiplication Table Solver");
		n = promptForN();
		MultiplicationTable m = new MultiplicationTable(n);
		MultiplicationTablePanel multPanel = new MultiplicationTablePanel(n, m);
		m.setPanel(multPanel);
		rootFrame.add(multPanel);
		rootFrame.setSize(500, 500);
		rootFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		rootFrame.setVisible(true);
		m.addEndOfGameListener(time -> {
			System.out.println("end");
			MultiplicationTablePanel panel = m.getPanel();
			panel.stopSeconds();
			JOptionPane.showMessageDialog(rootFrame, "You took " + time + "ms" + '\n' + "You got "
					+ (panel.getRightOnFirst() + 1) + " right on the first try!");
			p.setPlaying((JOptionPane.showConfirmDialog(rootFrame, "Would you like to play again?")));
			System.out.print(p.getPlaying());
			if (p.getPlaying() == 0) {
				rootFrame.remove(multPanel);
				int n1 = promptForN();
				m.reset(n1);
				MultiplicationTablePanel newPanel;
				try {
					newPanel = new MultiplicationTablePanel(n1, m);
					m.setPanel(newPanel);
					rootFrame.add(newPanel);
				} catch (InterruptedException | ExecutionException e) {
					throw new RuntimeException(e);
				}
			}
			return (p.getPlaying() != 0);
		});
		m.addCloseListener(() -> {
			rootFrame.dispose();
			System.exit(0);
		});

	}

	public static int promptForN() {
		String in = JOptionPane.showInputDialog("Choose your size, bigger than 1");
		int n = -1;
		while (n == -1) {
			try {
				n = Integer.parseInt(in);
				if (n <= 1)
					throw new Exception();
			} catch (Exception e) {
				// TODO: handle exception
				in = JOptionPane.showInputDialog("Try again");
				if (n <= 1)
					n = -1;
			}
		}
		return n;
	}

}
