package HW12;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Paint extends JComponent implements Runnable {
	private Image canvas; // the canvas
	private Graphics2D graphics2D;  // this will enable drawing
	private int curX; // current mouse x coordinate
	private int curY; // current mouse y coordinate
	private int oldX; // previous mouse x coordinate
	private int oldY; // previous mouse y coordinate

	JButton blackButton; // a button to change paint color
	JButton blueButton; // a button to change paint color
	JButton redButton; // a button to change paint color
	JButton clrButton; // a button to change paint color

	JButton hexButton; 
	JButton rgbButton;


	JTextField hexText;
	JTextField rText;
	JTextField gText;
	JTextField bText;


	Paint paint; 

	/* action listener for buttons */
	ActionListener actionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == blackButton) {
				Color color = Color.BLACK;
				rText.setText(Integer.toString(color.getRed()));
				gText.setText(Integer.toString(color.getGreen()));               
				bText.setText(Integer.toString(color.getBlue()));
				hexText.setText(String.format("#%02x%02x%02x",
						color.getRed(),
						color.getGreen(),
						color.getBlue()));
				paint.black();
			}
			if (e.getSource() == blueButton) {
				Color color = Color.BLUE;
				rText.setText(Integer.toString(color.getRed()));
				gText.setText(Integer.toString(color.getGreen()));               
				bText.setText(Integer.toString(color.getBlue()));
				hexText.setText(String.format("#%02x%02x%02x",
						color.getRed(),
						color.getGreen(),
						color.getBlue()));
				paint.blue();
			}
			if (e.getSource() == redButton) {
				Color color = Color.RED;
				rText.setText(Integer.toString(color.getRed()));
				gText.setText(Integer.toString(color.getGreen()));               
				bText.setText(Integer.toString(color.getBlue()));
				hexText.setText(String.format("#%02x%02x%02x",
						color.getRed(),
						color.getGreen(),
						color.getBlue()));
				paint.red();
			}
			if (e.getSource() == clrButton) {
				hexText.setText("#");
				rText.setText("");
				gText.setText("");
				bText.setText("");
				paint.clear();
			}

		}
	};

	/* set up paint colors */
	public void black() {
		graphics2D.setPaint(Color.black);
	}

	public void blue() {
		graphics2D.setPaint(Color.blue);
	}

	public void red() {
		graphics2D.setPaint(Color.red);
	}

	public void clear() {
		canvas = createImage(getSize().width, getSize().height);
		/* this lets us draw on the image (ie. the canvas)*/
		graphics2D = (Graphics2D) canvas.getGraphics();
		/* gives us better rendering quality for the drawing lines */
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		/* set canvas to white with default paint color */
		graphics2D.setPaint(Color.white);
		graphics2D.fillRect(0, 0, getSize().width, getSize().height);
		graphics2D.setPaint(Color.black);
		repaint();
	}

	public void changeColor(Color color) {
		graphics2D.setPaint(color);
	}

	public Paint() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				/* set oldX and oldY coordinates to beginning mouse press*/
				oldX = e.getX();
				oldY = e.getY();
			}
		});


		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				/* set current coordinates to where mouse is being dragged*/
				curX = e.getX();
				curY = e.getY();

				/* draw the line between old coordinates and new ones */
				graphics2D.drawLine(oldX, oldY, curX, curY);

				/* refresh frame and reset old coordinates */
				repaint();
				oldX = curX;
				oldY = curY;

			}
		});
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Paint());
	}

	public void run() {
		/* set up JFrame */
		JFrame frame = new JFrame("Canvas");
		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout());
		paint = new Paint();
		content.add(paint, BorderLayout.CENTER);

		redButton = new JButton("Red");
		redButton.addActionListener(actionListener);
		blueButton = new JButton("Blue");
		blueButton.addActionListener(actionListener);
		blackButton = new JButton("Black");
		blackButton.addActionListener(actionListener);
		clrButton = new JButton("Clear");
		clrButton.addActionListener(actionListener);
		JPanel panel = new JPanel();
		panel.add(blueButton);
		panel.add(blackButton);
		panel.add(redButton);
		panel.add(clrButton);
		content.add(panel, BorderLayout.NORTH);

		hexButton = new JButton("Hex Color");
		rgbButton = new JButton("RGB Color");
		hexText = new JTextField("#", 10);
		rText = new JTextField(5);
		gText = new JTextField(5);
		bText = new JTextField(5);
		JPanel bottom = new JPanel();
		bottom.add(hexButton);
		bottom.add(rgbButton);
		bottom.add(hexText);
		bottom.add(rText);
		bottom.add(gText);
		bottom.add(bText);
		content.add(bottom, BorderLayout.SOUTH);

		hexButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				String hexStr = hexText.getText();
				Color color = null;
				try {
					color = Color.decode(hexStr);
				} catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null, "Not a valid Hex Value", "Error", JOptionPane.ERROR_MESSAGE);
				}
				rText.setText(Integer.toString(color.getRed()));
				gText.setText(Integer.toString(color.getGreen()));               
				bText.setText(Integer.toString(color.getBlue()));

				paint.changeColor(color);
			}
		});

		rgbButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {
				String rColor = "0";
				String gColor = "0";
				String bColor = "0";
				if (!rText.getText().isBlank()) {
					rColor = rText.getText();
				}
				if (!gText.getText().isBlank()) {
					gColor = gText.getText();
				}
				if (!bText.getText().isBlank()) {
					bColor = bText.getText();
				}

				try {
					Integer.parseInt(rColor);
					Integer.parseInt(gColor);
					Integer.parseInt(bColor);
				} catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null, "Not a valid RGB Value", "Error", JOptionPane.ERROR_MESSAGE);
				}

				int r = Integer.parseInt(rColor);
				int g = Integer.parseInt(gColor);
				int b = Integer.parseInt(bColor);

				Color color = null;
				try {
					color = new Color(r, g, b);
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(null, "Not a valid RGB Value", "Error", JOptionPane.ERROR_MESSAGE);
				}
				rText.setText(Integer.toString(color.getRed()));
				gText.setText(Integer.toString(color.getGreen()));               
				bText.setText(Integer.toString(color.getBlue()));
				hexText.setText(String.format("#%02x%02x%02x",
						color.getRed(),
						color.getGreen(),
						color.getBlue()));
				paint.changeColor(color);	
			}


		});

		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);


	}

	protected void paintComponent(Graphics g) {
		if (canvas == null) {
			canvas = createImage(getSize().width, getSize().height);
			/* this lets us draw on the image (ie. the canvas)*/
			graphics2D = (Graphics2D) canvas.getGraphics();
			/* gives us better rendering quality for the drawing lines */
			graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			/* set canvas to white with default paint color */
			graphics2D.setPaint(Color.white);
			graphics2D.fillRect(0, 0, getSize().width, getSize().height);
			graphics2D.setPaint(Color.black);
			repaint();
		}
		g.drawImage(canvas, 0, 0, null);
	}
}
