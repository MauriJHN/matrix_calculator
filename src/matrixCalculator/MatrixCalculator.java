package matrixCalculator;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class MatrixCalculator extends JPanel implements ActionListener {

	/** where the calculator interface will appear */
	private Window window;

	/** labels */
	private JLabel rowsLabel, columnsLabel, matrixCount, operationLabel;

	/** spinners for number of matrices and dimension */
	private JSpinner rows, columns, matrixCounter;
	
	/** spinner models for each dimension spinner */
	private SpinnerNumberModel rowModel, columnModel;

	/** number of matrices */
	private int matrixNumber = 2;

	/** makes matrices' interfaces appear */
	private JButton createMatrices;

	/** combo box to select operation */
	private JComboBox<Operation> opComboBox;
	
	/** default font for the program */
	public static final Font DEFAULT_FONT = new Font("Berlin Sans FB", Font.PLAIN, 20);

	/** the list of matrices being operated */
	private LinkedList<MatrixWindow> matrixWindows;

	/** in charge of distributing the windows of the matrices */
	private WindowDistributor distributor;

	/** list of matrices that result after all values have been entered */
	private LinkedList<Matrix> rMatrices;

	/** object in charge of finding the solution of the calculation */
	private Solution solution;

	/** the operation type selected */
	private Operation operation;

	/** determines whether an operation can only be done on a single matrix */
	private boolean single = false;
	
	/** ========Matrix Calculator Constructor======== */
	public MatrixCalculator() {
		
		// initial operation will be set to Addition
		operation = Operation.ADDITION;

		// initiate all instance variables except "window"
		rowsLabel = new JLabel("Rows");
		columnsLabel = new JLabel("Columns");
		matrixCount = new JLabel("Number of Matrices");
		operationLabel = new JLabel("Operation");
		rowModel = new SpinnerNumberModel(2, 2, 20, 1);
		columnModel = new SpinnerNumberModel(2, 2, 20, 1);
		rows = new JSpinner(rowModel);
		columns = new JSpinner(columnModel);
		matrixCounter = new JSpinner(new SpinnerNumberModel(2, 2, 10, 1));
		createMatrices = new JButton("Enter");
		createMatrices.addActionListener(this);

		opComboBox = new JComboBox<Operation>(Operation.values());
		opComboBox.addActionListener(this);

		init();

	}// ========END OF CONSTRUCTOR========
	
	/** Initializes the window object for the matrix calculator */
	private void init() {
		
		// set the grid bag layout for the panel
		setLayout(new GridBagLayout());
		
		// add all components to this panel
		Window.addComponent(this, rowsLabel, 0, 0, 1, 1, Window.none, Window.center, 5);
		Window.addComponent(this, rows, 1, 0, 1, 1, Window.hor, Window.center, 5);
		Window.addComponent(this, columnsLabel, 2, 0, 1, 1, Window.none, Window.center, 5);
		Window.addComponent(this, columns, 3, 0, 1, 1, Window.hor, Window.center, 5);
		Window.addComponent(this, createMatrices, 0, 1, 1, 2, Window.both, Window.center, 5);
		Window.addComponent(this, operationLabel, 1, 1, 1, 1, Window.none, Window.center, 5);
		Window.addComponent(this, opComboBox, 1, 2, 1, 1, Window.none, Window.center, 5);
		Window.addComponent(this, matrixCount, 2, 1, 2, 1, Window.hor, Window.center, 5);
		Window.addComponent(this, matrixCounter, 2, 2, 2, 1, Window.hor, Window.center, 5);
		
		// set the theme of the calculator interface
		Window.setTheme(this, new Color(0, 40, 0), Color.WHITE, DEFAULT_FONT);
		
		// create the main frame that will contain the calculator panel
		window = new Window(this, "Matrix Calculator", Window.EXIT_ON_CLOSE, true);
		
		window.setResizable(false);
		
	}

	// executes the matrix calculator
	public static void main(String[] args) {
		new MatrixCalculator();
	}
	
	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();

		if (source == createMatrices) { // the button

			// declare the value of the matrixWindow list
			matrixWindows = new LinkedList<MatrixWindow>();

			// we want to create the number of matrices specified by the spinner
			// and then store them into the linked list and pass the link
			if(single){
				matrixNumber = 1;
			}else{
				matrixNumber = (int)matrixCounter.getValue();
			}
			
			// number of rows and columns of the matrices
			int rowNumber = (int) rows.getValue();
			int colNumber = (int) columns.getValue();

			// create the list matrix windows
			for (int i = 0; i < matrixNumber; i++) {

				// the corresponding matrix for the window
				Matrix tempMatrix = new Matrix(rowNumber, colNumber);
				matrixWindows.add(new MatrixWindow(tempMatrix));

			}
			
			// create the solution object and pass the list of displayed windows
			solution = new Solution(matrixWindows, operation);

		} else if (source == opComboBox) { // THE COMBO BOX
			
			Operation selected = (Operation)opComboBox.getSelectedItem();
			
			// check how the calculator behaves when multiplication, inverse or transpose is selected
			if(selected == Operation.MULTIPLICATION) {
				
				// reset dimension spinners with the same model
				resetDimensionSpinners(true);
				
			} else if(selected == Operation.INVERSE || selected == Operation.TRANSPOSE) {
				
				// reset the dimension spinners
				resetDimensionSpinners(false);
				
			} else {
				
				// reset the dimension spinners
				resetDimensionSpinners(false);
				
			}
			
		}

	}// END OF ACTION PERFORMED METHOD
	
	/**
	 * Resets the JSpinners for the dimension
	 * 
	 * @param same determines if the model should be the same for both JSpinners
	 */
	private void resetDimensionSpinners(boolean same){
		
		if(!same){
			
			rows.setModel(rowModel);
			columns.setModel(columnModel);
			
		} else {
			
			rows.setModel(rowModel);
			columns.setModel(rowModel);
			
		}
		
		
	}

}
