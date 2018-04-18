import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.Duration;
import java.util.Timer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MultiplicationTablePanel extends JPanel {

	private static final long serialVersionUID = 287481795432203680L;
	private int n;
	private MultiplicationTable m;
	private JTextField[][] textFields;
	private ScheduledFuture<?> task;
	private int rightOnFirst = 0;
	
	public int getRightOnFirst() {
		return rightOnFirst;
	}


	public MultiplicationTablePanel(int n, MultiplicationTable m) throws InterruptedException, ExecutionException {
		this.n = n;
		this.m = m;
		init();
	}
	
	public void stopSeconds() {
		task.cancel(true);
	}
	
	public void init() throws InterruptedException, ExecutionException {
		setLayout(new GridLayout(n+1, n+1));
		textFields = new JTextField[n][n];
		JLabel timeLabel = new JLabel("0 s",JLabel.CENTER);
		task = Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
			long ms = System.currentTimeMillis()-m.getStartTime();
			long s = (ms/1000);
			timeLabel.setText(s + " s");
		}, 1, 1, TimeUnit.SECONDS);
		add(timeLabel);
		for(int i = 0; i < n; i++) add(new JLabel(String.valueOf(i+1),JLabel.CENTER));
		for(int i = 0; i < n; i++) {
			add(new JLabel(String.valueOf(i+1),JLabel.CENTER));
			for (int j = 0; j < n; j++) {
				textFields[i][j] = new JTextField();
				textFields[i][j].addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						((JTextField)e.getSource()).grabFocus();
					}
				});
				int I = i;
				int J = j;
				textFields[i][j].addKeyListener(new KeyListener() {
					
					@Override
					public void keyTyped(KeyEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void keyReleased(KeyEvent e) {
						// TODO Auto-generated method stub
						String t = textFields[I][J].getText();
						JTextField j = (JTextField) e.getSource();
						if (m.cellCorrect(I, J)) {} 
						else {
							if (m.cellZero(I,J)) {
								try {
									if(m.set(I, J, Integer.parseInt(t))) {
											j.setBackground(Color.GREEN);
											rightOnFirst++;
										} else {
											j.setBackground(Color.RED);
										}
								} catch (Exception e2) {
									j.setBackground(Color.YELLOW);
								}
							}
							else {
								try {
									if(m.set(I, J, Integer.parseInt(t))) {
											j.setBackground(Color.GREEN);
										} else {
											j.setBackground(Color.RED);
										}
								} catch (Exception e2) {
									j.setBackground(Color.YELLOW);
								}
							}
						}
						
					}
					
					@Override
					public void keyPressed(KeyEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
				textFields[i][j].setHorizontalAlignment(JTextField.CENTER);
				add(textFields[i][j]);
			}
		}
	}

}
