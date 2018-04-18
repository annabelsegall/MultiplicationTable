
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
		while ((p.getPlaying() == 0) || (p.first ==1)) {
			n = -1;
			p.first = 0; 
			JFrame rootFrame = new JFrame("Multiplication Table Solver");
			String in = JOptionPane.showInputDialog("Choose your size");
			while (n == -1) {
				try {
					n = Integer.parseInt(in);
				} catch (Exception e) {
					// TODO: handle exception
					in = JOptionPane.showInputDialog("Try again");
				}
			}
			MultiplicationTable m = new MultiplicationTable(n);
			MultiplicationTablePanel multPanel = new MultiplicationTablePanel(n,m);
			rootFrame.add(multPanel);
			rootFrame.setSize(500, 500);
			rootFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			rootFrame.setVisible(true);
			CountDownLatch RoundDoneLatch = new CountDownLatch(1);
			m.addEndOfGameListener(time -> {
				multPanel.stopSeconds();
				JOptionPane.showMessageDialog(rootFrame, "You took " + time + "ms" + '\n' + "You got "+  (multPanel.getRightOnFirst()+ 1) +" right on the first try!");
				p.setPlaying((JOptionPane.showConfirmDialog(rootFrame, "Would you like to play again?")));
				System.out.print(p.getPlaying());
				rootFrame.dispose();
				RoundDoneLatch.countDown();
			});
			boolean RoundDone = RoundDoneLatch.await(1000,TimeUnit.SECONDS);
			
		}
		
	}

}
