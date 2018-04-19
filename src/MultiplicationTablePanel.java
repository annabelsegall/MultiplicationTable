import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.time.Duration;
import java.util.Timer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MultiplicationTablePanel extends JPanel {

	private static final long serialVersionUID = 287481795432203680L;
	private int n;
	private MultiplicationTable m;
	private JTextField[][] textFields;
	private ScheduledFuture<?> task;
	private int rightOnFirst = 0;
	private int hintsleft = 3;
	private int currTheme = 0; // regular is 0, dark is 1
	private Color correct;
	private Color wrong; 
	private Color caution;
	private Color notDone;
	
	
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
	
	private JLabel setFontAndColor (JLabel j) {
		j.setForeground(Color.GRAY.darker());
		j.setFont(new Font ("Helvetica", Font.BOLD, 24));
		return j;
	}
	
	private Component setFontAndColor (Component c) {
		c.setForeground(Color.GRAY.darker());
		c.setFont(new Font ("Helvetica", Font.BOLD, 24));
		return c;
	}
	
	public void init() throws InterruptedException, ExecutionException {
		correct = Color.GREEN;
		wrong = Color.RED; 
		caution = Color.YELLOW;
		notDone = Color.WHITE;
		setLayout(new GridLayout(n+2, n+1));
		textFields = new JTextField[n][n];
		JLabel timeLabel = setFontAndColor(new JLabel("0 s",JLabel.CENTER));
		task = Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
			long ms = System.currentTimeMillis()-m.getStartTime();
			long s = (ms/1000);
			timeLabel.setText(s + " s");
		}, 1, 1, TimeUnit.SECONDS);
		add(timeLabel);
		for(int i = 0; i < n; i++) add(setFontAndColor(new JLabel(String.valueOf(i+1),JLabel.CENTER)));
		for(int i = 0; i < n; i++) {
			add(this.setFontAndColor(new JLabel(String.valueOf(i+1),JLabel.CENTER)));
			for (int j = 0; j < n; j++) {
				textFields[i][j] = (JTextField) setFontAndColor(new JTextField());
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
											j.setBackground(correct);
											rightOnFirst++;
										} else {
											j.setBackground(wrong);
										}
								} catch (Exception e2) {
									j.setBackground(caution);
								}
							}
							else {
								try {
									if(m.set(I, J, Integer.parseInt(t))) {
											j.setBackground(correct);
										} else {
											j.setBackground(wrong);
										}
								} catch (Exception e2) {
									j.setBackground(caution);
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
		Component settings = add(setFontAndColor(new JLabel("Settings",JLabel.CENTER)));
		settings.addMouseListener(new MouseListener() {
			
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
				String[] settingOptions = {"Themes"};
		        int settingType = JOptionPane.showOptionDialog(null, "What settings would you like to change?",
		                "Settings",
		                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, settingOptions, settingOptions[0]);
		        System.out.println(settingType);
		        if (settingType == 0) setTheme();
			}
		});
		Component help = add(new JLabel("Instructions",JLabel.CENTER));
		help.setFont(new Font ("Helvetica", Font.BOLD, 24));
		help.setForeground(Color.GRAY.darker());
		help.addMouseListener(new MouseListener() {
			
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
				JOptionPane.showMessageDialog(help, "How to Play: Fill in the multiplication table as fast as you can. "
						+ '\n' + "If you get stuck, you can use a hint, which will fill in one box for you. You only get 3 hints per game.");
			}
		});
		Component hints = add(this.setFontAndColor(new JLabel("I'm stuck!",JLabel.CENTER)));
		hints.addMouseListener(new MouseListener() {
			
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
				if (hintsleft > 0 ) {
					hintsleft--; 
					if (hintsleft ==1) JOptionPane.showMessageDialog(help, hintsleft + " hint left");
					else JOptionPane.showMessageDialog(help, hintsleft + " hints left");
					Integer coordinates = m.findFirstFree();
					textFields[coordinates/n][coordinates % n].setText
												(String.valueOf((coordinates/n + 1) * (coordinates%n + 1)));
					textFields[coordinates/n][coordinates % n].setBackground(correct);
				} else {
					JOptionPane.showMessageDialog(help, "No hints left to use!");
				}
			}
		});
	}



	protected void setTheme() {
		// TODO Auto-generated method stub
        	String[] themeOptions = {"Regular", "Dark"};
        	int themeType = JOptionPane.showOptionDialog(null, "What theme would you like?",
	                "Settings - Themes",
	                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, themeOptions, themeOptions[0]);
        	Color prevcorrect = correct;
        	Color prevwrong = wrong;
        	Color prevcaution= caution;
        	Color prevnotDone = notDone;
        	if (themeType == 0 && themeType != currTheme) {
        		this.setBackground(Color.WHITE);
        		currTheme = 0; 
        		correct = Color.GREEN;
        		wrong = Color.RED;
        		caution = Color.YELLOW;
        		notDone = Color.WHITE;
        	}
        	if (themeType == 1 && themeType != currTheme) {
        		this.setBackground(Color.BLACK);
        		currTheme = 1; 
        		correct = Color.GREEN.darker().darker().darker();
        		wrong = Color.RED.darker().darker().darker();
        		caution = Color.BLUE.darker().darker().darker();
        		notDone = Color.BLACK;
        	}
        	for(int i = 0 ; i < n ; i++ ) {
    			for(int j = 0 ; j < n ; j++ ) {
    				if (themeType == 0) {
    					textFields[i][j].setForeground(Color.BLACK);
    				}
    				if (themeType == 1) {
    					textFields[i][j].setForeground(Color.WHITE);
    				}
    				if (textFields[i][j].getBackground().equals(prevcorrect)) {
    					textFields[i][j].setBackground(correct); 
    				}
    				if (textFields[i][j].getBackground().equals(prevwrong)) {
    					textFields[i][j].setBackground(wrong); 
    				}
    				if (textFields[i][j].getBackground().equals(prevcaution)) {
    					textFields[i][j].setBackground(caution); 
    				}
    				if (textFields[i][j].getBackground().equals(prevnotDone)) {
    					textFields[i][j].setBackground(notDone); 
    				}
    			}
    		}
        }		
	

	
}
