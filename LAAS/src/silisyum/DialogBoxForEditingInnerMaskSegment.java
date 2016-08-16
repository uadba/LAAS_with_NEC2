package silisyum;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;

public class DialogBoxForEditingInnerMaskSegment extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9016107987493756122L;
	private final JPanel contentPanel = new JPanel();
	private JTextField maskSegmentName_textField;
	private Mask mask;
	private JTextField starAngle_textField;
	private JTextField stopAngle_textField;
	private JTextField numberOfPoints_textField;
	private JTextField level_textField;
	private JTextField weight_textField;
	private int selectedMaskIndex;

	/**
	 * Create the dialog.
	 * @param b 
	 * @param string 
	 * @param userInterface 
	 */
	public DialogBoxForEditingInnerMaskSegment(UserInterface _frame, String _title, boolean _modal, Mask _mask) {
		super(_frame, _title, _modal);
		mask = _mask;
		setBounds(100, 100, 450, 236);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][grow]", "[][][][][][]"));
		{
			JLabel lblMaskSegmentName = new JLabel("Mask Segment Name :");
			lblMaskSegmentName.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(lblMaskSegmentName, "cell 0 0,alignx trailing");
		}
		{
			maskSegmentName_textField = new JTextField();
			contentPanel.add(maskSegmentName_textField, "cell 1 0,growx");
			maskSegmentName_textField.setColumns(10);
		}
		{
			JLabel lblStartAngle = new JLabel("Start Angle :");
			lblStartAngle.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(lblStartAngle, "cell 0 1,growx,aligny baseline");
		}
		{
			starAngle_textField = new JTextField();
			starAngle_textField.setColumns(10);
			contentPanel.add(starAngle_textField, "cell 1 1,growx");
		}
		{
			JLabel lblStopAngle = new JLabel("Stop Angle :");
			lblStopAngle.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(lblStopAngle, "cell 0 2,alignx trailing");
		}
		{
			stopAngle_textField = new JTextField();
			stopAngle_textField.setColumns(10);
			contentPanel.add(stopAngle_textField, "cell 1 2,growx");
		}
		{
			JLabel lblNumberOfPoints = new JLabel("Number of Points :");
			lblNumberOfPoints.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(lblNumberOfPoints, "cell 0 3,alignx trailing");
		}
		{
			numberOfPoints_textField = new JTextField();
			numberOfPoints_textField.setColumns(10);
			contentPanel.add(numberOfPoints_textField, "cell 1 3,growx");
		}
		{
			JLabel lblLevel = new JLabel("Level :");
			lblLevel.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(lblLevel, "cell 0 4,alignx trailing");
		}
		{
			level_textField = new JTextField();
			level_textField.setColumns(10);
			contentPanel.add(level_textField, "cell 1 4,growx");
		}
		{
			JLabel lblWeight = new JLabel("Weight :");
			lblWeight.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(lblWeight, "cell 0 5,alignx trailing");
		}
		{
			weight_textField = new JTextField();
			weight_textField.setColumns(10);
			contentPanel.add(weight_textField, "cell 1 5,growx");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton Update_btn = new JButton("Update This Inner Mask Segment");
				Update_btn.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						String maskName = maskSegmentName_textField.getText();
						double startAngle;
						double stopAngle;
						int numberOfPoints;
						double level;
						double weight;
						try {
							startAngle = Double.parseDouble(starAngle_textField.getText());
							stopAngle = Double.parseDouble(stopAngle_textField.getText());
							numberOfPoints = Integer.parseInt(numberOfPoints_textField.getText());
							level = Double.parseDouble(level_textField.getText());
							weight = Double.parseDouble(weight_textField.getText());
						} catch (NumberFormatException e1) {
							JOptionPane.showMessageDialog(null, "Start angle, stop angle, level, and weight values must be decimal and the number of points value must be integer");
							return;
						}
						
						boolean noProblem = true;
						if(maskName == null || maskName.isEmpty()) {
							noProblem = false;
							JOptionPane.showMessageDialog(null, "Mask segment must have a name.");
						}
						
						if(startAngle >= stopAngle) {
							noProblem = false;
							JOptionPane.showMessageDialog(null, "Stop angle must be bigger than start angle.");
						}
						
						if(startAngle < 0 || startAngle >= 180) {
							noProblem = false;
							JOptionPane.showMessageDialog(null, "Start angle must be in the range of [0, 180).");							
						}
						
						if(stopAngle <= 0 || stopAngle > 180) {
							noProblem = false;
							JOptionPane.showMessageDialog(null, "Stop angle must be in the range of (0, 180].");							
						}
						
						if(numberOfPoints < 2) {
							noProblem = false;
							JOptionPane.showMessageDialog(null, "The number of points must not be smaller than two.");							
						}				
						
						if(level > 0) {
							noProblem = false;
							JOptionPane.showMessageDialog(null, "The level cannot be bigger than zero.");							
						}
						
						if(weight <= 0) {
							noProblem = false;
							JOptionPane.showMessageDialog(null, "The weight must be bigger than zero.");							
						}
						
						int numberOfInnerMaskSegments = mask.innerMaskSegments.size();
						Mask.MaskSegment innerMaskSegment;
						
						boolean itIsANewName = true;
						boolean theyAreNotOverlapped = true;
						for (int n = 0; n < (numberOfInnerMaskSegments) && noProblem; n++) {	
							//
							innerMaskSegment = mask.innerMaskSegments.get(n);
							
							if(n != selectedMaskIndex) {
								if(maskName.equals(innerMaskSegment.name)) {
									JOptionPane.showMessageDialog(null, "This segment name is already used. You should type another name for the new mask segment.");
									itIsANewName = false;
									break;
								}
								
								if(innerMaskSegment.stopAngle > startAngle && innerMaskSegment.startAngle < stopAngle) {
									JOptionPane.showMessageDialog(null, "There is an overlap between one of the mask segments in the current list and the mask segment which you want to add. Please check your start and stop angle values to avoid the overlapping.");
									theyAreNotOverlapped = false;
									break;								
								}							
							}
						}
						
						if (itIsANewName && theyAreNotOverlapped && noProblem) {
							mask.deleteInnerMaskSegments(selectedMaskIndex);
							mask.addNewInnerMaskSegments(maskName, startAngle, stopAngle, numberOfPoints, level, weight);
							setVisible(false);
						}
					}
				});
				buttonPane.add(Update_btn);
			}
		}
	}
	
	public void setTextFields(int _selectedMaskIndex, String _maskSegmentName_textField, String _starAngle_textField, String _stopAngle_textField, String _numberOfPoints_textField, String _level_textField, String _weight_textField) {		
		
		selectedMaskIndex = _selectedMaskIndex;
		maskSegmentName_textField.setText(_maskSegmentName_textField);
		starAngle_textField.setText(_starAngle_textField);
		stopAngle_textField.setText(_stopAngle_textField);
		numberOfPoints_textField.setText(_numberOfPoints_textField);
		level_textField.setText(_level_textField);
		weight_textField.setText(_weight_textField);
		
	}

}
