package silisyum;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.commons.io.FilenameUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import javax.swing.SwingWorker;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.GridBagConstraints;
import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JTextPane;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import java.awt.FlowLayout;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JSeparator;
import javax.swing.JProgressBar;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Font;


public class UserInterface extends JFrame implements ChartMouseListener{

	private static final long serialVersionUID = 208535889565395799L;
	private static final int height = 480;
	private static final int width = 720;
	private JPanel contentPane;
	
	private XYSeries seriler;
	private XYSeries maskOuter;
	private XYSeries maskInner;
	private XYSeries convergenceSeries;
	
	private ChartPanel chartPanelPattern;
	private ChartPanel chartPanelConvergence;
    private Crosshair xCrosshair;
    private Crosshair yCrosshair;    
    
    private int numberOfElements = DefaultConfiguration.numberofElements;
    private int problemDimension;
    private double[] L = new double[3]; // amplitude, phase, and position minimum limits
    private double[] H = new double[3]; // amplitude, phase, and position maximum limits
    private boolean amplitudeIsUsed = DefaultConfiguration.amplitudeIsUsed;
    private boolean phaseIsUsed = DefaultConfiguration.phaseIsUsed;
    private boolean positionIsUsed = DefaultConfiguration.positionIsUsed;
    private Mask mask;
    private int patternGraphResolution = 721; // 4*180 + 1 = 721;
    private int populationNumber = DefaultConfiguration.populationNumber;
    private int maximumIterationNumber = DefaultConfiguration.maximumIterationNumber;
    private double F = DefaultConfiguration.F;
    private double Cr = DefaultConfiguration.Cr;
    private AntennaArray antennaArray;
    private AntennaArray antennaArrayForPresentation;
    private DifferentialEvolution differentialEvolution;
    private BestValues bestValues;
    private JTabbedPane tabbedPaneForSettings;
    private JPanel arrayParametersPanel;
    private JPanel differentialEvolutionPanel;
    private JPanel panelConvergence;
    private JPanel panelPattern;
    private JTextField costText;
    private JTextField iterationText;
    private JLabel lblNewLabel;
    private JLabel lblNewLabel_1;
    private AlgorithmExecuter algorithmExecuter;
    private JTabbedPane tabbedPaneForPlots;
    private JPanel panelPatternGraphProperties;
    private JLabel lblNewLabel_2;
    private JButton btnRescalePatternGraph;
    private JTextField arrayFactorAxisMinValue_textField;
    private JPanel panelPatternGraph;
    private JPanel panelConvergenceGraph;
    private JPanel panelConvergenceGraphProperties;
    private JPanel startStopPanel;
    private JButton startPauseButton;
    private JLabel numberOfElements_Label;
    private JTextField numberOfElements_Field;
    private JButton terminateOptimizationButton;
    private JLabel lblPopulationNumber;
    private JTextField populationNumber_textField;
    private JLabel lblMaximumIterationNumber;
    private JTextField maximumIterationNumber_textField;
    private JLabel lblF;
    private JLabel lblCr;
    private JTextField F_textField;
    private JTextField Cr_textField;
    private JPanel rightPannel;
    private JCheckBox chckbxAmplitude;
    private JLabel lblMaximumValueAmplitude;
    private JLabel lblMinimumValueAmplitude;
    private JTextField textField_maximumValueAmplitude;
    private JTextField textField_minimumValueAmplitude;
    private JCheckBox chckbxPhase;
    private JLabel lblMaximumValuePhase;
    private JLabel lblMinimumValuePhase;
    private JTextField textField_maximumValuePhase;
    private JTextField textField_minimumValuePhase;
    private JCheckBox chckbxPosition;
    private JLabel lblMaximumValuePosition;
    private JLabel lblMinimumValuePosition;
    private JTextField textField_maximumValuePosition;
    private JTextField textField_minimumValuePosition;
    private JPanel mainMessagesPanel;
    private JLabel lblMessages;
    private JTextPane messagePane;
    List<String> messagesOfErrors = new ArrayList<String>();
    private String messageToUser;
    private JPanel outerMaskPanel;
    private JTable outerTable;
    private JList<String> outerList;
    private JButton btnAddOuterMaskSegment;
    private DialogBoxForAddingOuterMaskSegment dialogBoxForAddingOuterMaskSegment;
    private DialogBoxForEditingOuterMaskSegment dialogBoxForEditingOuterMaskSegment;
    private DefaultListModel<String> listModelForOuterMaskSegments;
    private JButton btnDeleteOuterMaskSegment;
    private JPanel outerMaskSegmentOperations;
    private JButton btnEditOuterMaskSegment;
    private JLabel lblMaskSegmentNames;
    private JLabel lblSelectedMaskValues;
	private int selectedOuterMaskSegmentIndex = -1;
	private int selectedInnerMaskSegmentIndex = -1;
	private JPanel innerMaskPanel;
	private JButton btnAddInnerMaskSegment;
	private JButton btnEditInnerMaskSegment;
	private JButton btnDeleteInnerMaskSegment;
	private JPanel innerMaskSegmentOperations;
	private JLabel lblInnerMaskSegment;
	private JLabel label_1;
	private JList<String> innerList;
	private JTable innerTable;
	private DefaultListModel<String> listModelForInnerMaskSegments;
    private DialogBoxForAddingInnerMaskSegment dialogBoxForAddingInnerMaskSegment;
    private DialogBoxForEditingInnerMaskSegment dialogBoxForEditingInnerMaskSegment;
    private JLabel lblAmplitudeValues;
    private JLabel lblPhaseValues;
    private JLabel lblPositionValues;
    private JTable tableAmplitude;
    private JTable tablePhase;
    private JTable tablePosition;
    private JButton btnSetElementNumber;
    private JButton btnLoadAmplitudes;
    private JButton btnLoadPhases;
    private JButton btnLoadPositions;
    private double startTimeForPatternGraph;
    private double startTimeForConvergenceGraph;
	private int periodForPatternGraph = 0;
	private int periodForConvergenceGraph = 0;
	private JComboBox<String> comboBoxForPatternGraph;
	private JLabel lblUpdateTheGraph;
	public boolean firstOrLastPlot = false;
	private JComboBox<String> comboBoxForConvergenceGraph;
	private JLabel lblUpdateTheConvergence;
	private int unplottedIterationIndexBeginning = 0;
	private JButton btnResetAmplitudeValues;
	private JButton btnResetPhaseValues;
	private JButton btnResetDistancesTo;
	private final double arrayFactorAxisMinValueDefault = -100;
	private double arrayFactorAxisMinValue = arrayFactorAxisMinValueDefault;
	private JSeparator separator;
	private JProgressBar progressBar;
	private JButton btnShowCurrentResults;
	private JPanel panel;
	private JLabel lblAmplitudeValuesComment;
	private JLabel lblPhaseValuesComment;
	private JLabel lblPositionValuesComment;
	private String fixedOrOptimized;
	private String willBeOptimized = "<html>will be <font color=red>optimized</font></html>";
	private String areFixed = "<html>are <font color=green>fixed</font></html>";
	private JLabel lblPlotTheGraph;
	private JComboBox<String> comboBoxNumberOfPoints;
	private JPanel fileOperationsPanel;
	private JButton btnResetConfigurationToDefault;
	private JButton btnLoadConfigurationFromAFile;
	private JButton btnSaveConfigurationToAFile;
	private JButton exportPatternAsSVG;
	protected File currentDirectory = null;
	private JButton exportConvergeCurve;
	private Component verticalStrut;
	private JLabel lblConfigurationFile;
	private JLabel lblSvgImageExporting;
	private Component verticalStrut_1;
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserInterface frame = new UserInterface();
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UserInterface() {
		for(int limit=0; limit<3; limit++) {
			L[limit] = DefaultConfiguration.L[limit];
			H[limit] = DefaultConfiguration.H[limit];			
		}
		
		setTitle("Linear Antenna Array Synthesizer");		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1429, 991);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		mask = new Mask();
		dialogBoxForAddingOuterMaskSegment = new DialogBoxForAddingOuterMaskSegment(this, "Add a New Outer Mask Segment", true, mask);
		dialogBoxForEditingOuterMaskSegment = new DialogBoxForEditingOuterMaskSegment(this, "Edit a Outer Mask Segment", true, mask);
		
		dialogBoxForAddingInnerMaskSegment = new DialogBoxForAddingInnerMaskSegment(this, "Add a New Inner Mask Segment", true, mask);
		dialogBoxForEditingInnerMaskSegment = new DialogBoxForEditingInnerMaskSegment(this, "Edit a Inner Mask Segment", true, mask);
		
		createTemporaryMaskSegments();
		
		seriler = new XYSeries("Pattern", false, false);
		maskOuter = new XYSeries("Outer Mask", false, false);
		maskInner = new XYSeries("Inner Mask", false, false);
		convergenceSeries = new XYSeries("Convergence Curve");
		XYSeriesCollection veri_seti = new XYSeriesCollection(seriler);
		XYSeriesCollection veri_seti2 = new XYSeriesCollection(convergenceSeries);
		veri_seti.addSeries(maskOuter);
		veri_seti.addSeries(maskInner);
		JFreeChart grafik = ChartFactory.createXYLineChart("Antenna Array Pattern", "Angle", "Array Factor (in dB)", veri_seti);
		JFreeChart grafik2 = ChartFactory.createXYLineChart("Convergence Curve", "Iteration", "Cost", veri_seti2);
				
		XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) grafik.getXYPlot().getRenderer();
		renderer.setSeriesPaint(0, Color.red);
		renderer.setSeriesStroke(0, new BasicStroke(0.7f));
		renderer.setSeriesPaint(1, Color.blue);
		renderer.setSeriesStroke(1, new BasicStroke(0.5f));
		renderer.setSeriesPaint(2, new Color(0, 100, 0));
		renderer.setSeriesStroke(2, new BasicStroke(0.5f));
        CrosshairOverlay crosshairOverlay = new CrosshairOverlay();
        this.xCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));
        this.xCrosshair.setLabelVisible(true);
        this.yCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));
        this.yCrosshair.setLabelVisible(true);
        crosshairOverlay.addDomainCrosshair(xCrosshair);
        crosshairOverlay.addRangeCrosshair(yCrosshair);
		
		grafik.getXYPlot().getDomainAxis().setRange(0, 180); // x axis
		grafik.getXYPlot().getRangeAxis().setRange(arrayFactorAxisMinValue, 5);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		tabbedPaneForPlots = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPaneForPlots, BorderLayout.CENTER);
		
		panelPattern = new JPanel();
		tabbedPaneForPlots.addTab("Antenna Array Pattern", null, panelPattern, null);
		panelPattern.setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		panelPattern.add(panel, BorderLayout.SOUTH);
		
		panelPatternGraph = new JPanel(new GridBagLayout());
		panelPattern.add(panelPatternGraph, BorderLayout.NORTH);
		
		this.chartPanelPattern = new ChartPanel(grafik);
		GridBagConstraints gbc_chartPanelPattern = new GridBagConstraints();
		gbc_chartPanelPattern.anchor = GridBagConstraints.NORTHWEST;
		gbc_chartPanelPattern.gridx = 1;
		gbc_chartPanelPattern.gridy = 0;
		panelPatternGraph.add(chartPanelPattern, gbc_chartPanelPattern);
		panelPatternGraph.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                preserveAspectRatio(chartPanelPattern, panelPatternGraph);
            }
		});
		
		
		this.chartPanelPattern.addChartMouseListener(this);
		chartPanelPattern.addOverlay(crosshairOverlay);
		
		panelPatternGraphProperties = new JPanel();
		panelPattern.add(panelPatternGraphProperties, BorderLayout.CENTER);
		panelPatternGraphProperties.setLayout(new MigLayout("", "[grow][][][][grow]", "[23px][20px][][]"));
		
		lblUpdateTheGraph = new JLabel("Update the pattern graph ");
		panelPatternGraphProperties.add(lblUpdateTheGraph, "cell 1 1,alignx right");
		
		btnRescalePatternGraph = new JButton("Rescale Pattern Graph");
		btnRescalePatternGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grafik.getXYPlot().getDomainAxis().setRange(0, 180); // x axis
				grafik.getXYPlot().getRangeAxis().setRange(arrayFactorAxisMinValue, 5);
			}
		});
		
		comboBoxForPatternGraph = new JComboBox<String>();
		comboBoxForPatternGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBoxForPatternGraph.getSelectedIndex() == 0) periodForPatternGraph = 0;
				if(comboBoxForPatternGraph.getSelectedIndex() == 1) periodForPatternGraph = 1000;
				if(comboBoxForPatternGraph.getSelectedIndex() == 2) periodForPatternGraph = 3000;
				if(comboBoxForPatternGraph.getSelectedIndex() == 3) periodForPatternGraph = 7000;
				if(comboBoxForPatternGraph.getSelectedIndex() == 4) periodForPatternGraph = 15000;
				if(comboBoxForPatternGraph.getSelectedIndex() == 5) periodForPatternGraph = 60000;
				if(comboBoxForPatternGraph.getSelectedIndex() == 6) periodForPatternGraph = -1;
			}
		});
		comboBoxForPatternGraph.setModel(new DefaultComboBoxModel<String>(new String[] {"as often as possible", "every 1 second", "every 3 seconds", "every 7 seconds", "every 15 seconds", "every 1 minute", "only at the begining and end"}));
		panelPatternGraphProperties.add(comboBoxForPatternGraph, "cell 2 1 2 1,alignx left");
		
		lblNewLabel_2 = new JLabel("Vertical axis scale: From 0 to ");
		panelPatternGraphProperties.add(lblNewLabel_2, "cell 1 0,alignx right");
		
		arrayFactorAxisMinValue_textField = new JTextField();
		arrayFactorAxisMinValue_textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				grafik.getXYPlot().getDomainAxis().setRange(0, 180); // x axis
				
				try {
					arrayFactorAxisMinValue = Double.parseDouble(arrayFactorAxisMinValue_textField.getText());
				} catch (NumberFormatException e) {					
					arrayFactorAxisMinValue = arrayFactorAxisMinValueDefault;
					arrayFactorAxisMinValue_textField.setText(Double.toString(arrayFactorAxisMinValue));
				}
				
				if(arrayFactorAxisMinValue >= 0) {
					arrayFactorAxisMinValue = arrayFactorAxisMinValueDefault;
					arrayFactorAxisMinValue_textField.setText(Double.toString(arrayFactorAxisMinValue));
				}
				
				grafik.getXYPlot().getRangeAxis().setRange(arrayFactorAxisMinValue, 5);
			}
		});
		arrayFactorAxisMinValue_textField.setText(Double.toString(arrayFactorAxisMinValue));
		panelPatternGraphProperties.add(arrayFactorAxisMinValue_textField, "cell 2 0,alignx left");
		arrayFactorAxisMinValue_textField.setColumns(10);
		panelPatternGraphProperties.add(btnRescalePatternGraph, "cell 3 0,alignx left");
		
		lblPlotTheGraph = new JLabel("Plot the pattern graph using");
		panelPatternGraphProperties.add(lblPlotTheGraph, "cell 1 2,alignx right");
		
		comboBoxNumberOfPoints = new JComboBox<String>();
		comboBoxNumberOfPoints.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBoxNumberOfPoints.getSelectedIndex() == 0) patternGraphResolution = 1441;
				if(comboBoxNumberOfPoints.getSelectedIndex() == 1) patternGraphResolution = 721;
				if(comboBoxNumberOfPoints.getSelectedIndex() == 2) patternGraphResolution = 361;
				if(comboBoxNumberOfPoints.getSelectedIndex() == 3) patternGraphResolution = 181;
				if(comboBoxNumberOfPoints.getSelectedIndex() == 4) patternGraphResolution = -1;
				if(comboBoxNumberOfPoints.getSelectedIndex() == 5) patternGraphResolution = -2;

				if(patternGraphResolution != -1 && patternGraphResolution != -2) {
					antennaArray.numberofSamplePoints = patternGraphResolution;
					antennaArrayForPresentation.numberofSamplePoints = patternGraphResolution;
				}
		    
			    seriler.clear();
			    if(algorithmExecuter.keepIterating == true && algorithmExecuter.newStart == false) // it is running
			    	drawPlotOfPattern();
			    else if(algorithmExecuter.keepIterating == false && algorithmExecuter.newStart == false) // it is paused
			    	drawPlotOfPattern();
			    else
			    	drawPlotWithInitialParameterValues();
			}
		});
		comboBoxNumberOfPoints.setModel(new DefaultComboBoxModel<String>(new String[] {"1441 points", "721 points", "361 points", "181 points", "outer masks points", "inner masks points"}));
		panelPatternGraphProperties.add(comboBoxNumberOfPoints, "flowx,cell 2 2 2 1,alignx left");		
		
		panelConvergence = new JPanel();
		tabbedPaneForPlots.addTab("Convergence Curve of Optimization Process", null, panelConvergence, null);
		panelConvergence.setLayout(new BorderLayout(0, 0));
		
		panelConvergenceGraph = new JPanel(new GridBagLayout());
		panelConvergence.add(panelConvergenceGraph, BorderLayout.NORTH);
		
		chartPanelConvergence = new ChartPanel(grafik2);
		GridBagConstraints gbc_chartPanelConvergence = new GridBagConstraints();
		gbc_chartPanelConvergence.anchor = GridBagConstraints.NORTHWEST;
		gbc_chartPanelConvergence.gridx = 1;
		gbc_chartPanelConvergence.gridy = 0;
		panelConvergenceGraph.add(chartPanelConvergence, gbc_chartPanelConvergence);
		panelConvergenceGraph.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                preserveAspectRatio(chartPanelConvergence, panelConvergenceGraph);
            }
		});
		
		panelConvergenceGraphProperties = new JPanel();
		panelConvergence.add(panelConvergenceGraphProperties);
		panelConvergenceGraphProperties.setLayout(new MigLayout("", "[grow][183px][60px][grow]", "[][20px][20px]"));
		
		lblUpdateTheConvergence = new JLabel("Update the convergence curve graph ");
		panelConvergenceGraphProperties.add(lblUpdateTheConvergence, "cell 1 2");
		
		comboBoxForConvergenceGraph = new JComboBox<String>();
		comboBoxForConvergenceGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBoxForConvergenceGraph.getSelectedIndex() == 0) periodForConvergenceGraph = 0;
				if(comboBoxForConvergenceGraph.getSelectedIndex() == 1) periodForConvergenceGraph = 1000;
				if(comboBoxForConvergenceGraph.getSelectedIndex() == 2) periodForConvergenceGraph = 3000;
				if(comboBoxForConvergenceGraph.getSelectedIndex() == 3) periodForConvergenceGraph = 7000;
				if(comboBoxForConvergenceGraph.getSelectedIndex() == 4) periodForConvergenceGraph = 15000;
				if(comboBoxForConvergenceGraph.getSelectedIndex() == 5) periodForConvergenceGraph = 60000;
				if(comboBoxForConvergenceGraph.getSelectedIndex() == 6) periodForConvergenceGraph = -1;
			}
		});
		comboBoxForConvergenceGraph.setModel(new DefaultComboBoxModel<String>(new String[] {"as often as possible", "every 1 second", "every 3 seconds", "every 7 seconds", "every 15 seconds", "every 1 minute", "only at the begining and end"}));
		panelConvergenceGraphProperties.add(comboBoxForConvergenceGraph, "cell 2 2");
		
		lblNewLabel = new JLabel("Iteration Number:");
		panelConvergenceGraphProperties.add(lblNewLabel, "cell 1 0");
		
		iterationText = new JTextField();
		panelConvergenceGraphProperties.add(iterationText, "cell 2 0");
		iterationText.setEditable(false);
		iterationText.setColumns(20);
		
		lblNewLabel_1 = new JLabel("Cost Value:");
		panelConvergenceGraphProperties.add(lblNewLabel_1, "cell 1 1");
		
		costText = new JTextField();
		panelConvergenceGraphProperties.add(costText, "cell 2 1");
		costText.setEditable(false);
		costText.setColumns(20);
		
		rightPannel = new JPanel();
		contentPane.add(rightPannel, BorderLayout.EAST);
		rightPannel.setLayout(new BorderLayout(0, 0));
		
		startStopPanel = new JPanel();
		rightPannel.add(startStopPanel, BorderLayout.NORTH);
		startStopPanel.setLayout(new MigLayout("", "[240px,grow][240px][240px,grow]", "[][23px][][][]"));
		
		startPauseButton = new JButton("Start Optimization");
		startPauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(validateParameters()) {
					if(algorithmExecuter.keepIterating == false) {
						if(algorithmExecuter.newStart) {
							getParametersFromUserInterface();
							calculateProblemDimension();
							createMainObjects();
							seriler.clear();
							maskOuter.clear();
							maskInner.clear();
							convergenceSeries.clear();
							unplottedIterationIndexBeginning = 0;
							algorithmExecuter.newStart = false;
							algorithmExecuter.iterationHasNotCompletedYet = true;
							sendMessageToPane("<font color=#006400><b>Optimization process has been <i>started</i> successfully!</b></font>", true);
							if(isThereAnyGapInOuterMask()) {
								sendMessageToPane("<br><font color=#666600>Warning: There is at least one gap in the <i>outer mask</i>. It does not affect the optimization process adversely but it may be the sign of a bad designed mask.</font>", false);								
							}
							if(isThereAnyGapInInnerMask()) {
								sendMessageToPane("<br><font color=#666600>Warning: There is at least one gap in the <i>inner mask</i>. It does not affect the optimization process adversely but it may be the sign of a bad designed mask.</font>", false);								
							}
							makeComponentsEnable(false);
						} else {
							sendMessageToPane("<br><font color=#006400><b>Optimization process has been <i>restarted</i>.</b></font>", false);
						}
						algorithmExecuter.keepIterating = true;
						startPauseButton.setText("Pause Optimization");
						terminateOptimizationButton.setVisible(false);
						btnShowCurrentResults.setVisible(false);
						drawOuterMask();
						drawInnerMask();
						firstOrLastPlot = true;
						startTimeForPatternGraph = System.currentTimeMillis();
						startTimeForConvergenceGraph = System.currentTimeMillis();
					} else {
						algorithmExecuter.keepIterating = false;
						startPauseButton.setText("Continue Optimization");
						terminateOptimizationButton.setVisible(true);
						btnShowCurrentResults.setVisible(true);
						sendMessageToPane("<br><font color=#006400><b>Optimization process has been <i>stopped</i>.</b></font>", false);
					}
				} else {
					presentErrorMessages();
					tabbedPaneForSettings.setSelectedIndex(4);
				}
			}
		});
		
		btnShowCurrentResults = new JButton("Show Current Results");
		btnShowCurrentResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPaneForSettings.setSelectedIndex(4);
				showCurrentResults();	
			}
		});
		btnShowCurrentResults.setVisible(false);
		btnShowCurrentResults.setForeground(new Color(255, 255, 255));
		btnShowCurrentResults.setBackground(new Color(51, 153, 255));
		startStopPanel.add(btnShowCurrentResults, "cell 0 1,alignx center");
		startStopPanel.add(startPauseButton, "cell 1 1,alignx center,aligny top");
		
		terminateOptimizationButton = new JButton("Terminate Optimization");
		terminateOptimizationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				algorithmExecuter.keepIterating = false;
				algorithmExecuter.newStart = true;
				terminateOptimizationButton.setVisible(false);
				btnShowCurrentResults.setVisible(false);
				startPauseButton.setText("Start Optimization");
				sendMessageToPane("<br><font color=#006400><b>Optimization process has been </b></font> <font color=red><b><i>terminated</i></b></font> <font color=#006400><b>by the user</b></font>.", false);
				progressBar.setValue(0);
				makeComponentsEnable(true);
				showCurrentResults();
				
				setBestResultsToCurrentAntennaArray();
			}
		});
		terminateOptimizationButton.setVisible(false);
		terminateOptimizationButton.setForeground(new Color(255, 255, 255));
		terminateOptimizationButton.setBackground(new Color(255, 69, 0));
		startStopPanel.add(terminateOptimizationButton, "cell 2 1,alignx center");
		
		progressBar = new JProgressBar();
		progressBar.setForeground(new Color(51, 204, 51));
		progressBar.setToolTipText("Iteration Progress Bar");
		progressBar.setPreferredSize(new Dimension(240, 14));
		startStopPanel.add(progressBar, "cell 0 2 3 1,alignx center");
		
		separator = new JSeparator(SwingConstants.HORIZONTAL);
		separator.setPreferredSize(new Dimension(370, 2));
		startStopPanel.add(separator, "cell 0 4 3 1,alignx center");
		
		tabbedPaneForSettings = new JTabbedPane(JTabbedPane.TOP);
		rightPannel.add(tabbedPaneForSettings, BorderLayout.CENTER);
		
		arrayParametersPanel = new JPanel();
		tabbedPaneForSettings.addTab("Array Parameters", null, arrayParametersPanel, null);
		arrayParametersPanel.setLayout(new MigLayout("", "[120px][:120px:120px][120px][:120px:120px][120px][:120px:120px]", "[20px][][][][][][grow][]"));
		
		numberOfElements_Label = new JLabel("Number of Antenna Array Elements :");
		arrayParametersPanel.add(numberOfElements_Label, "cell 0 0 2 1,alignx right,aligny center");
		
		numberOfElements_Field = new JTextField();
		numberOfElements_Field.setEditable(false);
		numberOfElements_Field.setText(Integer.toString(numberOfElements));
		arrayParametersPanel.add(numberOfElements_Field, "cell 2 0,growx,aligny center");
		numberOfElements_Field.setColumns(10);
		
		btnSetElementNumber = new JButton("Set the Number of Antenna Array Elements");
		btnSetElementNumber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String s = (String)JOptionPane.showInputDialog(
	                    null,
	                    "Please enter the number of antenna array elements",
	                    "Number of Antenna Array Elements",
	                    JOptionPane.PLAIN_MESSAGE,
	                    null,
	                    null,
	                    Integer.toString(numberOfElements));
				
				if ((s != null) && (s.length() > 0)) {					
					numberOfElements_Field.setText(s);					
					createAntennaArray();
					refreshAmplitudeTable();
					refreshPhaseTable();
					refreshPositionTable();
					drawPlotWithInitialParameterValues();
				}
			}
		});
		arrayParametersPanel.add(btnSetElementNumber, "cell 3 0 3 1");
		
		lblAmplitudeValues = new JLabel("Amplitude values");
		arrayParametersPanel.add(lblAmplitudeValues, "cell 0 4,alignx right");
		
		fixedOrOptimized = (amplitudeIsUsed) ? willBeOptimized : areFixed;
		lblAmplitudeValuesComment = new JLabel(fixedOrOptimized);
		arrayParametersPanel.add(lblAmplitudeValuesComment, "cell 1 4,alignx left");
		
		lblPhaseValues = new JLabel("Phase Values");
		arrayParametersPanel.add(lblPhaseValues, "cell 2 4,alignx right");
		
		fixedOrOptimized = (phaseIsUsed) ? willBeOptimized : areFixed;
		lblPhaseValuesComment = new JLabel(fixedOrOptimized);
		arrayParametersPanel.add(lblPhaseValuesComment, "cell 3 4,alignx left");
		
		lblPositionValues = new JLabel("Position Values");
		arrayParametersPanel.add(lblPositionValues, "cell 4 4,alignx right");
		
		chckbxAmplitude = new JCheckBox("Amplitude");
		chckbxAmplitude.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshForChckbxAmplitude();
			}
		});
		chckbxAmplitude.setSelected(amplitudeIsUsed);
		arrayParametersPanel.add(chckbxAmplitude, "cell 0 1,alignx right");
		
		chckbxPhase = new JCheckBox("Phase");
		chckbxPhase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshForChckbxPhase();
			}
		});
		chckbxPhase.setSelected(phaseIsUsed);
		arrayParametersPanel.add(chckbxPhase, "cell 2 1,alignx right");
		
		chckbxPosition = new JCheckBox("Position");
		chckbxPosition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshForChckbxPosition();
			}
		});
		chckbxPosition.setSelected(positionIsUsed);
		arrayParametersPanel.add(chckbxPosition, "cell 4 1,alignx right");
		
		lblMaximumValueAmplitude = new JLabel("Maximum Value :");
		lblMaximumValueAmplitude.setHorizontalAlignment(SwingConstants.RIGHT);
		arrayParametersPanel.add(lblMaximumValueAmplitude, "cell 0 2,alignx trailing");
		
		textField_maximumValueAmplitude = new JTextField(Double.toString(H[0]));
		arrayParametersPanel.add(textField_maximumValueAmplitude, "cell 1 2,growx");
		textField_maximumValueAmplitude.setColumns(10);
		
		lblMaximumValuePhase = new JLabel("Maximum Value :");
		lblMaximumValuePhase.setHorizontalAlignment(SwingConstants.RIGHT);
		arrayParametersPanel.add(lblMaximumValuePhase, "cell 2 2,alignx trailing");
		
		textField_maximumValuePhase = new JTextField(Double.toString(H[1]));
		arrayParametersPanel.add(textField_maximumValuePhase, "cell 3 2,growx");
		textField_maximumValuePhase.setColumns(10);
		
		lblMaximumValuePosition = new JLabel("Maximum Value :");
		lblMaximumValuePosition.setHorizontalAlignment(SwingConstants.RIGHT);
		arrayParametersPanel.add(lblMaximumValuePosition, "cell 4 2,alignx trailing");
		
		textField_maximumValuePosition = new JTextField(Double.toString(H[2]));
		arrayParametersPanel.add(textField_maximumValuePosition, "cell 5 2,growx");
		textField_maximumValuePosition.setColumns(10);
		
		lblMinimumValueAmplitude = new JLabel("Minimum Value :");
		lblMinimumValueAmplitude.setHorizontalAlignment(SwingConstants.RIGHT);
		arrayParametersPanel.add(lblMinimumValueAmplitude, "cell 0 3,alignx trailing");
		
		textField_minimumValueAmplitude = new JTextField(Double.toString(L[0]));
		arrayParametersPanel.add(textField_minimumValueAmplitude, "cell 1 3,growx");
		textField_minimumValueAmplitude.setColumns(10);
		
		lblMinimumValuePhase = new JLabel("Minimum Value :");
		lblMinimumValuePhase.setHorizontalAlignment(SwingConstants.RIGHT);
		arrayParametersPanel.add(lblMinimumValuePhase, "cell 2 3,alignx trailing");
		
		textField_minimumValuePhase = new JTextField(Double.toString(L[1]));
		arrayParametersPanel.add(textField_minimumValuePhase, "cell 3 3,growx");
		textField_minimumValuePhase.setColumns(10);
		
		lblMinimumValuePosition = new JLabel("Minimum Value :");
		lblMinimumValuePosition.setHorizontalAlignment(SwingConstants.RIGHT);
		arrayParametersPanel.add(lblMinimumValuePosition, "cell 4 3,alignx trailing");
		
		textField_minimumValuePosition = new JTextField(Double.toString(L[2]));
		arrayParametersPanel.add(textField_minimumValuePosition, "cell 5 3,growx");
		textField_minimumValuePosition.setColumns(10);
		
		btnResetAmplitudeValues = new JButton("Reset Amplitude Values to Ones");
		btnResetAmplitudeValues.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int d = 0; d < numberOfElements ; d++) {
					antennaArray.amplitude[d] = 1;
				}
				refreshAmplitudeTable();
				drawPlotWithInitialParameterValues();
			}
		});
		
		fixedOrOptimized = (positionIsUsed) ? willBeOptimized : areFixed;
		lblPositionValuesComment = new JLabel(fixedOrOptimized);
		arrayParametersPanel.add(lblPositionValuesComment, "cell 5 4,alignx left");
		arrayParametersPanel.add(btnResetAmplitudeValues, "cell 0 5 2 1,alignx center");
		
		btnResetPhaseValues = new JButton("Reset Phase Values to Zeros");
		btnResetPhaseValues.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int d = 0; d < numberOfElements ; d++) {
					antennaArray.phase[d] = 0;
				}
				refreshPhaseTable();
				drawPlotWithInitialParameterValues();
			}
		});
		arrayParametersPanel.add(btnResetPhaseValues, "cell 2 5 2 1,alignx center");
		
		btnResetDistancesTo = new JButton("Reset Distances to Half-wavelength");
		btnResetDistancesTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int d = 0; d < numberOfElements ; d++) {
					antennaArray.position[d] = d*0.5;
				}
				refreshPositionTable();
				drawPlotWithInitialParameterValues();
			}
		});
		arrayParametersPanel.add(btnResetDistancesTo, "cell 4 5 2 1,alignx center");
		
		tableAmplitude = new JTable(new TableModelForAmplitude());
		JScrollPane scrollPaneForTableAmplitude = new JScrollPane(tableAmplitude);
		arrayParametersPanel.add(scrollPaneForTableAmplitude, "cell 0 6 2 1,grow");
		
		tablePhase = new JTable(new TableModelForPhase());
		JScrollPane scrollPaneForTablePhase = new JScrollPane(tablePhase);
		arrayParametersPanel.add(scrollPaneForTablePhase, "cell 2 6 2 1,grow");
		
		tablePosition = new JTable(new TableModelForPosition());
		JScrollPane scrollPaneForTablePosition = new JScrollPane(tablePosition);
		arrayParametersPanel.add(scrollPaneForTablePosition, "cell 4 6 2 1,grow");
		
		btnLoadAmplitudes = new JButton("Load Amplitude Values From a File");
		btnLoadAmplitudes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Create a file chooser
				final JFileChooser fc = new JFileChooser();
				
				if(currentDirectory != null) fc.setCurrentDirectory(currentDirectory);
				
				//In response to a button click:
				int returnVal = fc.showOpenDialog(null);
				currentDirectory = fc.getCurrentDirectory();
				
		        if (returnVal == JFileChooser.APPROVE_OPTION) {		        	
		        	List<String> lines = null;
		            File file = fc.getSelectedFile();
		            Path path = Paths.get(file.getAbsolutePath());
		            int loopLength = antennaArray.amplitude.length;
					try {
						lines = Files.readAllLines(path, StandardCharsets.UTF_8);						
						if(lines.size() < antennaArray.amplitude.length) {
							int difference = antennaArray.amplitude.length - lines.size();
							for(int addition = 0; addition < difference; addition++)
							{
								lines.add("0");
							}							
						}
						
						for (int conversion = 0; conversion < loopLength; conversion++) {
							double amplitudeFromFile = 0;							
							try {
								amplitudeFromFile = Double.parseDouble(lines.get(conversion));
							} catch (NumberFormatException ex) {
								amplitudeFromFile = 0;
							}				
							
							antennaArray.amplitude[conversion] = amplitudeFromFile;							
						}
						refreshAmplitudeTable();
						drawPlotWithInitialParameterValues();						
					} catch (IOException ex) {
						ex.printStackTrace();
					}					
		        } else {
		            // Cancelled by the user.
		        }
			}
		});
		arrayParametersPanel.add(btnLoadAmplitudes, "cell 0 7 2 1,alignx center");
		
		btnLoadPhases = new JButton("Load Phase Values From a File");
		btnLoadPhases.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Create a file chooser
				final JFileChooser fc = new JFileChooser();
				
				if(currentDirectory != null) fc.setCurrentDirectory(currentDirectory);
				
				//In response to a button click:
				int returnVal = fc.showOpenDialog(null);
				currentDirectory = fc.getCurrentDirectory();
				
		        if (returnVal == JFileChooser.APPROVE_OPTION) {		        	
		        	List<String> lines = null;
		            File file = fc.getSelectedFile();
		            Path path = Paths.get(file.getAbsolutePath());
		            int loopLength = antennaArray.phase.length;
					try {
						lines = Files.readAllLines(path, StandardCharsets.UTF_8);						
						if(lines.size() < antennaArray.phase.length) {
							int difference = antennaArray.phase.length - lines.size();
							for(int addition = 0; addition < difference; addition++)
							{
								lines.add("0");
							}							
						}
						
						for (int conversion = 0; conversion < loopLength; conversion++) {
							double phaseFromFile = 0;							
							try {
								phaseFromFile = Double.parseDouble(lines.get(conversion));
							} catch (NumberFormatException ex) {
								phaseFromFile = 0;
							}				
							
							antennaArray.phase[conversion] = phaseFromFile;							
						}
						refreshPhaseTable();
						drawPlotWithInitialParameterValues();						
					} catch (IOException ex) {
						ex.printStackTrace();
					}					
		        } else {
		            // Cancelled by the user.
		        }	
			}
		});
		arrayParametersPanel.add(btnLoadPhases, "cell 2 7 2 1,alignx center");
		
		btnLoadPositions = new JButton("Load Position Values From a File");
		btnLoadPositions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Create a file chooser
				final JFileChooser fc = new JFileChooser();
				
				if(currentDirectory != null) fc.setCurrentDirectory(currentDirectory);
				
				//In response to a button click:
				int returnVal = fc.showOpenDialog(null);
				currentDirectory = fc.getCurrentDirectory();
				
		        if (returnVal == JFileChooser.APPROVE_OPTION) {		        	
		        	List<String> lines = null;
		            File file = fc.getSelectedFile();
		            Path path = Paths.get(file.getAbsolutePath());
		            int loopLength = antennaArray.position.length;
					try {
						lines = Files.readAllLines(path, StandardCharsets.UTF_8);						
						if(lines.size() < antennaArray.position.length) {
							int difference = antennaArray.position.length - lines.size();
							for(int addition = 0; addition < difference; addition++)
							{
								lines.add("0");
							}							
						}
						
						for (int conversion = 0; conversion < loopLength; conversion++) {
							double positionFromFile = 0;							
							try {
								positionFromFile = Double.parseDouble(lines.get(conversion));
							} catch (NumberFormatException ex) {
								positionFromFile = 0;
							}				
							
							antennaArray.position[conversion] = positionFromFile;							
						}
						refreshPositionTable();
						drawPlotWithInitialParameterValues();						
					} catch (IOException ex) {
						ex.printStackTrace();
					}					
		        } else {
		            // Cancelled by the user.
		        }
			}
		});
		arrayParametersPanel.add(btnLoadPositions, "cell 4 7 2 1,alignx center");
		
		outerMaskPanel = new JPanel();
		tabbedPaneForSettings.addTab("Outer Mask", null, outerMaskPanel, null);
		outerMaskPanel.setLayout(new MigLayout("", "[180px,grow][grow]", "[][][grow]"));
		listModelForOuterMaskSegments = new DefaultListModel<String>();		
		outerList = new JList<String>(listModelForOuterMaskSegments);
		outerList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				selectedOuterMaskSegmentIndex = outerList.getSelectedIndex();				
				refreshOuterMaskSegmentDetailsTable();
			}
		});
		
		outerMaskSegmentOperations = new JPanel();
		outerMaskSegmentOperations.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Outer Mask Segment Operations", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		FlowLayout fl_outerMaskSegmentOperations = (FlowLayout) outerMaskSegmentOperations.getLayout();
		fl_outerMaskSegmentOperations.setAlignment(FlowLayout.LEFT);
		outerMaskPanel.add(outerMaskSegmentOperations, "cell 0 0 2 1,grow");
		
		btnAddOuterMaskSegment = new JButton("Add");
		btnAddOuterMaskSegment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialogBoxForAddingOuterMaskSegment.setLocationRelativeTo(dialogBoxForAddingOuterMaskSegment.getParent());
				// check the outer mask segments list
				int numberOfOuterMaskSegments = mask.outerMaskSegments.size();
				
				// if there is any segment set the latest one as reference
				Mask.MaskSegment outerMaskSegment;
				if(numberOfOuterMaskSegments != 0) {
					outerMaskSegment = mask.outerMaskSegments.get(numberOfOuterMaskSegments - 1);
					double lastSegmentStopAngle = outerMaskSegment.stopAngle;
					String lastSegmentStopAngleString = Double.toString(lastSegmentStopAngle);
					if(lastSegmentStopAngle == 180) {
						dialogBoxForAddingOuterMaskSegment.setTextFields("", "", "", "", "", "");
					} else {
						String lastSegmentLevelString = Double.toString(outerMaskSegment.level);						
						String lastSegmentWeightString = Double.toString(outerMaskSegment.weight);						
						dialogBoxForAddingOuterMaskSegment.setTextFields("", lastSegmentStopAngleString, "", "", lastSegmentLevelString, lastSegmentWeightString);						
					}
				} else {
					dialogBoxForAddingOuterMaskSegment.setTextFields("", "", "", "", "", "");
				}
				
				dialogBoxForAddingOuterMaskSegment.setVisible(true);
				refreshOuterMaskSegmentsList();
				refreshOuterMaskSegmentDetailsTable();
				drawOuterMask();
			}
		});
		outerMaskSegmentOperations.add(btnAddOuterMaskSegment);
		
		btnEditOuterMaskSegment = new JButton("Edit");
		btnEditOuterMaskSegment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedOuterMaskSegmentIndex != -1) {
					Mask.MaskSegment outerMaskSegment = mask.outerMaskSegments.get(selectedOuterMaskSegmentIndex);
					
					dialogBoxForEditingOuterMaskSegment.setTextFields(selectedOuterMaskSegmentIndex, outerMaskSegment.name, Double.toString(outerMaskSegment.startAngle),
							Double.toString(outerMaskSegment.stopAngle), Integer.toString(outerMaskSegment.numberOfPoints), Double.toString(outerMaskSegment.level), Double.toString(outerMaskSegment.weight));
					
					dialogBoxForEditingOuterMaskSegment.setLocationRelativeTo(dialogBoxForEditingOuterMaskSegment.getParent());				
					dialogBoxForEditingOuterMaskSegment.setVisible(true);
					refreshOuterMaskSegmentsList();
					refreshOuterMaskSegmentDetailsTable();
					drawOuterMask();
				}
			}
		});
		outerMaskSegmentOperations.add(btnEditOuterMaskSegment);
		
		btnDeleteOuterMaskSegment = new JButton("Delete");
		btnDeleteOuterMaskSegment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedOuterMaskSegmentIndex != -1) {
					int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to delete THIS mask?", "Warning", JOptionPane.YES_NO_OPTION);
					if (dialogResult == JOptionPane.YES_OPTION) {
						mask.deleteOuterMaskSegments(selectedOuterMaskSegmentIndex);
						refreshOuterMaskSegmentsList();
						refreshOuterMaskSegmentDetailsTable();
						drawOuterMask();
					} 
				}
			}
		});
		outerMaskSegmentOperations.add(btnDeleteOuterMaskSegment);
		
		lblMaskSegmentNames = new JLabel("Outer Mask Segment Names");
		outerMaskPanel.add(lblMaskSegmentNames, "cell 0 1,alignx center");
		
		lblSelectedMaskValues = new JLabel("Selected Mask Segment Values");
		outerMaskPanel.add(lblSelectedMaskValues, "cell 1 1,alignx center");
		outerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane outerListScroller = new JScrollPane(outerList);
		outerMaskPanel.add(outerListScroller, "cell 0 2,grow");
		
		outerTable = new JTable(new TableModelForOuterMask());
		JScrollPane scrollPaneForOuterTable = new JScrollPane(outerTable);
		outerMaskPanel.add(scrollPaneForOuterTable, "cell 1 2,grow");
		
		innerMaskPanel = new JPanel();
		tabbedPaneForSettings.addTab("Inner Mask", null, innerMaskPanel, null);
		innerMaskPanel.setLayout(new MigLayout("", "[180px,grow][grow]", "[][][grow]"));
		
		innerMaskSegmentOperations = new JPanel();
		FlowLayout flowLayout = (FlowLayout) innerMaskSegmentOperations.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		innerMaskSegmentOperations.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Inner Mask Segment Operations", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		innerMaskPanel.add(innerMaskSegmentOperations, "cell 0 0 2 1,grow");
		
		btnAddInnerMaskSegment = new JButton("Add");
		btnAddInnerMaskSegment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialogBoxForAddingInnerMaskSegment.setLocationRelativeTo(dialogBoxForAddingInnerMaskSegment.getParent());
				
				
				// check the inner mask segments list
				int numberOfInnerMaskSegments = mask.innerMaskSegments.size();
				
				// if there is any segment set the latest one as reference
				Mask.MaskSegment innerMaskSegment;
				if(numberOfInnerMaskSegments != 0) {
					innerMaskSegment = mask.innerMaskSegments.get(numberOfInnerMaskSegments - 1);
					double lastSegmentStopAngle = innerMaskSegment.stopAngle;
					String lastSegmentStopAngleString = Double.toString(lastSegmentStopAngle);
					if(lastSegmentStopAngle == 180) {
						dialogBoxForAddingInnerMaskSegment.setTextFields("", "", "", "", "", "");
					} else {
						String lastSegmentLevelString = Double.toString(innerMaskSegment.level);						
						String lastSegmentWeightString = Double.toString(innerMaskSegment.weight);						
						dialogBoxForAddingInnerMaskSegment.setTextFields("", lastSegmentStopAngleString, "", "", lastSegmentLevelString, lastSegmentWeightString);						
					}
				} else {
					dialogBoxForAddingInnerMaskSegment.setTextFields("", "", "", "", "", "");
				}				
				
				dialogBoxForAddingInnerMaskSegment.setVisible(true);
				refreshInnerMaskSegmentsList();
				refreshInnerMaskSegmentDetailsTable();
				drawInnerMask();
			}
		});
		innerMaskSegmentOperations.add(btnAddInnerMaskSegment);
		
		btnEditInnerMaskSegment = new JButton("Edit");
		btnEditInnerMaskSegment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedInnerMaskSegmentIndex != -1) {
					Mask.MaskSegment innerMaskSegment = mask.innerMaskSegments.get(selectedInnerMaskSegmentIndex);
					
					dialogBoxForEditingInnerMaskSegment.setTextFields(selectedInnerMaskSegmentIndex, innerMaskSegment.name, Double.toString(innerMaskSegment.startAngle),
							Double.toString(innerMaskSegment.stopAngle), Integer.toString(innerMaskSegment.numberOfPoints), Double.toString(innerMaskSegment.level), Double.toString(innerMaskSegment.weight));
					
					dialogBoxForEditingInnerMaskSegment.setLocationRelativeTo(dialogBoxForEditingInnerMaskSegment.getParent());				
					dialogBoxForEditingInnerMaskSegment.setVisible(true);
					refreshInnerMaskSegmentsList();
					refreshInnerMaskSegmentDetailsTable();
					drawInnerMask();
				}
			}
		});
		innerMaskSegmentOperations.add(btnEditInnerMaskSegment);
		
		btnDeleteInnerMaskSegment = new JButton("Delete");
		btnDeleteInnerMaskSegment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedInnerMaskSegmentIndex != -1) {
					int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to delete THIS mask?", "Warning", JOptionPane.YES_NO_OPTION);
					if (dialogResult == JOptionPane.YES_OPTION) {
						mask.deleteInnerMaskSegments(selectedInnerMaskSegmentIndex);
						refreshInnerMaskSegmentsList();
						refreshInnerMaskSegmentDetailsTable();
						drawInnerMask();
					}
				}
			}
		});
		innerMaskSegmentOperations.add(btnDeleteInnerMaskSegment);
		
		lblInnerMaskSegment = new JLabel("Inner Mask Segment Names");
		innerMaskPanel.add(lblInnerMaskSegment, "cell 0 1,alignx center");
		
		label_1 = new JLabel("Selected Mask Segment Values");
		innerMaskPanel.add(label_1, "cell 1 1,alignx center");
		
		listModelForInnerMaskSegments = new DefaultListModel<String>();
		innerList = new JList<String>(listModelForInnerMaskSegments);
		innerList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				selectedInnerMaskSegmentIndex = innerList.getSelectedIndex();				
				refreshInnerMaskSegmentDetailsTable();
			}
		});
		innerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane innerListScroller = new JScrollPane(innerList);
		innerMaskPanel.add(innerListScroller, "cell 0 2,grow");		
		
		innerTable = new JTable(new TableModelForInnerMask());
		JScrollPane scrollPaneForInnerTable = new JScrollPane(innerTable);
		innerMaskPanel.add(scrollPaneForInnerTable, "cell 1 2,grow");
		
		differentialEvolutionPanel = new JPanel();
		tabbedPaneForSettings.addTab("Differential Evolution", null, differentialEvolutionPanel, null);
		differentialEvolutionPanel.setLayout(new MigLayout("", "[][grow]", "[][][][]"));
		
		lblPopulationNumber = new JLabel("Population Number :");
		lblPopulationNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		differentialEvolutionPanel.add(lblPopulationNumber, "cell 0 0,alignx trailing");
		
		populationNumber_textField = new JTextField();
		populationNumber_textField.setText(Integer.toString(populationNumber));
		differentialEvolutionPanel.add(populationNumber_textField, "cell 1 0,growx");
		populationNumber_textField.setColumns(10);
		
		lblMaximumIterationNumber = new JLabel("Maximum Iteration Number :");
		lblMaximumIterationNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		differentialEvolutionPanel.add(lblMaximumIterationNumber, "cell 0 1,alignx trailing");
		
		maximumIterationNumber_textField = new JTextField();
		maximumIterationNumber_textField.setText(Integer.toString(maximumIterationNumber));
		differentialEvolutionPanel.add(maximumIterationNumber_textField, "cell 1 1,growx");
		maximumIterationNumber_textField.setColumns(10);
		
		lblF = new JLabel("Scaling Factor (F) :");
		lblF.setHorizontalAlignment(SwingConstants.RIGHT);
		differentialEvolutionPanel.add(lblF, "cell 0 2,alignx trailing");
		
		F_textField = new JTextField();
		F_textField.setText(Double.toString(F));
		F_textField.setColumns(10);
		differentialEvolutionPanel.add(F_textField, "cell 1 2,growx");
		
		lblCr = new JLabel("Crossover Rate (Cr) :");
		lblCr.setHorizontalAlignment(SwingConstants.RIGHT);
		differentialEvolutionPanel.add(lblCr, "cell 0 3,alignx trailing");
		
		Cr_textField = new JTextField();
		Cr_textField.setText(Double.toString(Cr));
		Cr_textField.setColumns(10);
		differentialEvolutionPanel.add(Cr_textField, "cell 1 3,growx");
		//table.setFillsViewportHeight(true);
		
		mainMessagesPanel = new JPanel();
		tabbedPaneForSettings.addTab("Messages", null, mainMessagesPanel, null);
		mainMessagesPanel.setLayout(new MigLayout("", "[660px,grow]", "[][grow]"));
		
		lblMessages = new JLabel("Messages");
		mainMessagesPanel.add(lblMessages, "cell 0 0");
		
		messagePane = new JTextPane();
		messagePane.setEditable(false);
		messagePane.setContentType("text/html");
		JScrollPane scrollPaneForList = new JScrollPane(messagePane);
		mainMessagesPanel.add(scrollPaneForList, "cell 0 1,grow");
		
		fileOperationsPanel = new JPanel();
		tabbedPaneForSettings.addTab("File Operations", null, fileOperationsPanel, null);
		fileOperationsPanel.setLayout(new MigLayout("", "[grow][][grow]", "[][][][][][][][][]"));
		
		btnSaveConfigurationToAFile = new JButton("Save Configuration to a File");
		btnSaveConfigurationToAFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
								
				//JFileChooser fc = new JFileChooser();
				@SuppressWarnings("serial")
				JFileChooser fc = new JFileChooser(){
				    @Override
				    public void approveSelection(){
				        File f = getSelectedFile();
				        if(f.exists() && getDialogType() == SAVE_DIALOG){
				            int result = JOptionPane.showConfirmDialog(this,"The file exists, overwrite?","Existing file",JOptionPane.YES_NO_OPTION);
				            switch(result){
				                case JOptionPane.YES_OPTION:
				                    super.approveSelection();
				                    return;
				                case JOptionPane.NO_OPTION:
				                    return;
				                case JOptionPane.CLOSED_OPTION:
				                    return;
				            }
				        }
				        super.approveSelection();
				    }     
				};
				
				if(currentDirectory != null) fc.setCurrentDirectory(currentDirectory);
				
				fc.setFileFilter(new FileNameExtensionFilter("Antenna Array Synthesizer File (*.aas)","aas"));
				
				int returnVal = fc.showSaveDialog(null);
				currentDirectory = fc.getCurrentDirectory();
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////////				
					// Fetch the all corresponding values from the user interface.
					getParametersFromUserInterface();
					// Antenna Parameters
					CurrentConfiguration cc = new CurrentConfiguration();
					cc.numberofElements = numberOfElements;
					cc.L = L;
					cc.H = H;
					cc.amplitudeIsUsed = amplitudeIsUsed;
					cc.phaseIsUsed = phaseIsUsed;
					cc.positionIsUsed = positionIsUsed;
					cc.amplitudeValues = antennaArray.amplitude;
					cc.phaseValues = antennaArray.phase;
					cc.positionValues = antennaArray.position;
					
					// For Outer Mask
					int numberOfOuterMask = mask.outerMaskSegments.size();
					cc.nameForOuter = new String[numberOfOuterMask];
					cc.startAngleForOuter = new double[numberOfOuterMask];
					cc.stopAngleForOuter = new double[numberOfOuterMask];
					cc.numberOfPointsForOuter = new int[numberOfOuterMask];
					cc.levelForOuter = new double[numberOfOuterMask];
					cc.weightForOuter = new double[numberOfOuterMask];
					for(int s=0; s<numberOfOuterMask; s++) {
						cc.nameForOuter[s] = mask.outerMaskSegments.get(s).name;
						cc.startAngleForOuter[s] = mask.outerMaskSegments.get(s).startAngle;
						cc.stopAngleForOuter[s] = mask.outerMaskSegments.get(s).stopAngle;
						cc.numberOfPointsForOuter[s] = mask.outerMaskSegments.get(s).numberOfPoints;
						cc.levelForOuter[s] = mask.outerMaskSegments.get(s).level;
						cc.weightForOuter[s] = mask.outerMaskSegments.get(s).weight;
					}
					
					// For Inner Mask
					int numberOfInnerMask = mask.innerMaskSegments.size();
					cc.nameForInner = new String[numberOfInnerMask];
					cc.startAngleForInner = new double[numberOfInnerMask];
					cc.stopAngleForInner = new double[numberOfInnerMask];
					cc.numberOfPointsForInner = new int[numberOfInnerMask];
					cc.levelForInner = new double[numberOfInnerMask];
					cc.weightForInner = new double[numberOfInnerMask];
					for(int s=0; s<numberOfInnerMask; s++) {
						cc.nameForInner[s] = mask.innerMaskSegments.get(s).name;
						cc.startAngleForInner[s] = mask.innerMaskSegments.get(s).startAngle;
						cc.stopAngleForInner[s] = mask.innerMaskSegments.get(s).stopAngle;
						cc.numberOfPointsForInner[s] = mask.innerMaskSegments.get(s).numberOfPoints;
						cc.levelForInner[s] = mask.innerMaskSegments.get(s).level;
						cc.weightForInner[s] = mask.innerMaskSegments.get(s).weight;
					}
					
					// Algorithm Parameters
					cc.populationNumber = populationNumber;
					cc.maximumIterationNumber = maximumIterationNumber;
					cc.F = F;
					cc.Cr = Cr;
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					
					File file = fc.getSelectedFile();					
					
					if (FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("aas")) {
					} else {
					    file = new File(file.toString() + ".aas");
					}
					
					try
					{						
						FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath());
						ObjectOutputStream out = new ObjectOutputStream(fileOut);
						out.writeObject(cc);
						out.close();
						fileOut.close();
					}catch(IOException i)
					{
						i.printStackTrace();
					}
				} else {
					
				}
				
			}
		});
		
		btnLoadConfigurationFromAFile = new JButton("Load Configuration from a File");
		btnLoadConfigurationFromAFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fc = new JFileChooser();
				
				fc.setFileFilter(new FileNameExtensionFilter("Antenna Array Synthesizer File (*.aas)","aas"));
				
				if(currentDirectory != null) fc.setCurrentDirectory(currentDirectory);
				
				int returnVal = fc.showOpenDialog(null);
				currentDirectory = fc.getCurrentDirectory();
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					
					CurrentConfiguration cc = null;
					
					try {
						FileInputStream fileIn = new FileInputStream(file.getAbsolutePath());
						ObjectInputStream in = new ObjectInputStream(fileIn);
						cc = (CurrentConfiguration) in.readObject();
						in.close();
						fileIn.close();		
					
						// Assign the values which come from the file to the antenna array parameters
						numberOfElements_Field.setText(Integer.toString(cc.numberofElements));
						chckbxAmplitude.setSelected(cc.amplitudeIsUsed);
						chckbxPhase.setSelected(cc.phaseIsUsed);
						chckbxPosition.setSelected(cc.positionIsUsed);
						
						textField_maximumValueAmplitude.setText(Double.toString(cc.H[0]));
						textField_maximumValuePhase.setText(Double.toString(cc.H[1]));
						textField_maximumValuePosition.setText(Double.toString(cc.H[2]));
						
						textField_minimumValueAmplitude.setText(Double.toString(cc.L[0]));
						textField_minimumValuePhase.setText(Double.toString(cc.L[1]));
						textField_minimumValuePosition.setText(Double.toString(cc.L[2]));
						
						createAntennaArray(); // It is also set the "numberOfElements" field					
						
						for(int s=0; s<cc.amplitudeValues.length; s++) {
							antennaArray.amplitude[s] = cc.amplitudeValues[s];
							antennaArray.phase[s] = cc.phaseValues[s];
							antennaArray.position[s] = cc.positionValues[s];
						}
						
						refreshForChckbxAmplitude();
						refreshForChckbxPhase();
						refreshForChckbxPosition();
						drawPlotWithInitialParameterValues();
						convergenceSeries.clear();
						
						// Assign the values which come from the file to the outer mask parameters
						mask.outerMaskSegments.clear();
						int numberOfOuterMask = cc.nameForOuter.length;
						for(int s=0; s<numberOfOuterMask; s++)
						{
							mask.addNewOuterMaskSegments(cc.nameForOuter[s], cc.startAngleForOuter[s], cc.stopAngleForOuter[s], cc.numberOfPointsForOuter[s], cc.levelForOuter[s], cc.weightForOuter[s]);
						}
						refreshOuterMaskSegmentsList();
						refreshOuterMaskSegmentDetailsTable();
						drawOuterMask();
						
						// Assign the values which come from the file to the inner mask parameters
						mask.innerMaskSegments.clear();
						int numberOfInnerMask = cc.nameForInner.length;
						for(int s=0; s<numberOfInnerMask; s++)
						{
							mask.addNewInnerMaskSegments(cc.nameForInner[s], cc.startAngleForInner[s], cc.stopAngleForInner[s], cc.numberOfPointsForInner[s], cc.levelForInner[s], cc.weightForInner[s]);
						}
						refreshInnerMaskSegmentsList();
						refreshInnerMaskSegmentDetailsTable();
						drawInnerMask();
						
						// Assign the values which come from the file to the algorithm parameters
						populationNumber_textField.setText(Integer.toString(cc.populationNumber));
						maximumIterationNumber_textField.setText(Integer.toString(cc.maximumIterationNumber));
						F_textField.setText(Double.toString(cc.F));
						Cr_textField.setText(Double.toString(cc.Cr));
						
						getParametersFromUserInterface();
						
						
					} catch (IOException i) {
						i.printStackTrace();
						return;
					} catch (ClassNotFoundException c) {
						c.printStackTrace();
						return;
					}

				} else {
					
				}
				
			}
		});
		
		btnResetConfigurationToDefault = new JButton("Reset Configuration to Default");
		btnResetConfigurationToDefault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
	            
				int result = JOptionPane.showConfirmDialog(null,"Do you want to reset the configuration to the default?","Confirmation",JOptionPane.YES_NO_OPTION);
	            
				if (result == JOptionPane.YES_OPTION) {
					for(int limit=0; limit<3; limit++) {
						L[limit] = DefaultConfiguration.L[limit];
						H[limit] = DefaultConfiguration.H[limit];			
					}
					numberOfElements = DefaultConfiguration.numberofElements;
					numberOfElements_Field.setText(Integer.toString(numberOfElements));
					chckbxAmplitude.setSelected(DefaultConfiguration.amplitudeIsUsed);
					chckbxPhase.setSelected(DefaultConfiguration.phaseIsUsed);
					chckbxPosition.setSelected(DefaultConfiguration.positionIsUsed);
					textField_maximumValueAmplitude.setText(Double.toString(H[0]));
					textField_maximumValuePhase.setText(Double.toString(H[1]));
					textField_maximumValuePosition.setText(Double.toString(H[2]));
					textField_minimumValueAmplitude.setText(Double.toString(L[0]));
					textField_minimumValuePhase.setText(Double.toString(L[1]));
					textField_minimumValuePosition.setText(Double.toString(L[2]));
					
					// Masks
					mask.outerMaskSegments.clear();
					refreshOuterMaskSegmentsList();
					refreshOuterMaskSegmentDetailsTable();
					drawOuterMask();
					mask.innerMaskSegments.clear();
					refreshInnerMaskSegmentsList();
					refreshInnerMaskSegmentDetailsTable();
					drawInnerMask();
					
					// Algorithm
					populationNumber_textField.setText(Integer.toString(DefaultConfiguration.populationNumber));
					maximumIterationNumber_textField.setText(Integer.toString(DefaultConfiguration.maximumIterationNumber));
					F_textField.setText(Double.toString(DefaultConfiguration.F));
					Cr_textField.setText(Double.toString(DefaultConfiguration.Cr));
					getParametersFromUserInterface();
					antennaArray = new AntennaArray(numberOfElements, patternGraphResolution, mask);
					antennaArray.createArrays();
					antennaArray.initializeArrays();
					refreshForChckbxAmplitude();
					refreshForChckbxPhase();
					refreshForChckbxPosition();
					drawPlotWithInitialParameterValues();
					convergenceSeries.clear();
				}
				
			}
		});
		
		verticalStrut = Box.createVerticalStrut(20);
		fileOperationsPanel.add(verticalStrut, "cell 1 0,growx");
		
		lblConfigurationFile = new JLabel("Configuration File");
		lblConfigurationFile.setFont(new Font("Tahoma", Font.BOLD, 12));
		fileOperationsPanel.add(lblConfigurationFile, "cell 1 1,alignx center");
		fileOperationsPanel.add(btnResetConfigurationToDefault, "cell 1 2,growx");
		fileOperationsPanel.add(btnLoadConfigurationFromAFile, "cell 1 3,growx");
		fileOperationsPanel.add(btnSaveConfigurationToAFile, "cell 1 4,growx");
		
		verticalStrut_1 = Box.createVerticalStrut(20);
		fileOperationsPanel.add(verticalStrut_1, "cell 1 5,alignx center");
		
		lblSvgImageExporting = new JLabel("SVG Image Exporting");
		lblSvgImageExporting.setFont(new Font("Tahoma", Font.BOLD, 12));
		fileOperationsPanel.add(lblSvgImageExporting, "cell 1 6,alignx center");
		
		exportPatternAsSVG = new JButton("Export Array Pattern as SVG");
		fileOperationsPanel.add(exportPatternAsSVG, "cell 1 7,growx");
		exportPatternAsSVG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser(){
				    /**
					 * 
					 */
					private static final long serialVersionUID = 8343735387640302868L;

					@Override
				    public void approveSelection(){
				        File f = getSelectedFile();
				        if(f.exists() && getDialogType() == SAVE_DIALOG){
				            int result = JOptionPane.showConfirmDialog(this,"The file exists, overwrite?","Existing file",JOptionPane.YES_NO_OPTION);
				            switch(result){
				                case JOptionPane.YES_OPTION:
				                    super.approveSelection();
				                    return;
				                case JOptionPane.NO_OPTION:
				                    return;
				                case JOptionPane.CLOSED_OPTION:
				                    return;
				            }
				        }
				        super.approveSelection();
				    }     
				};
				
				fc.setFileFilter(new FileNameExtensionFilter("Scalable Vector Graphics File (*.svg)","svg"));
				
				if(currentDirectory != null) fc.setCurrentDirectory(currentDirectory);
				
				int returnVal = fc.showSaveDialog(null);
				currentDirectory = fc.getCurrentDirectory();
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					
					File file = fc.getSelectedFile();
					
					if (FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("svg")) {
						
					} else {
					    file = new File(file.toString() + ".svg");
					}
					
					try {
						exportChartAsSVG(grafik, file);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		
		exportConvergeCurve = new JButton("Export Converge Curve as SVG");
		exportConvergeCurve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser(){
				    /**
					 * 
					 */
					private static final long serialVersionUID = 8343735387640302868L;

					@Override
				    public void approveSelection(){
				        File f = getSelectedFile();
				        if(f.exists() && getDialogType() == SAVE_DIALOG){
				            int result = JOptionPane.showConfirmDialog(this,"The file exists, overwrite?","Existing file",JOptionPane.YES_NO_OPTION);
				            switch(result){
				                case JOptionPane.YES_OPTION:
				                    super.approveSelection();
				                    return;
				                case JOptionPane.NO_OPTION:
				                    return;
				                case JOptionPane.CLOSED_OPTION:
				                    return;
				            }
				        }
				        super.approveSelection();
				    }     
				};
				
				fc.setFileFilter(new FileNameExtensionFilter("Scalable Vector Graphics File (*.svg)","svg"));
				
				if(currentDirectory != null) fc.setCurrentDirectory(currentDirectory);
				
				int returnVal = fc.showSaveDialog(null);
				currentDirectory = fc.getCurrentDirectory();
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					
					File file = fc.getSelectedFile();
					
					if (FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("svg")) {
					    // filename extension is suitable
					} else {
					    file = new File(file.toString() + ".svg");
					}
					
					try {
						exportChartAsSVG(grafik2, file);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		fileOperationsPanel.add(exportConvergeCurve, "cell 1 8");
		
		refreshOuterMaskSegmentsList();
		refreshInnerMaskSegmentsList();
		
		createAntennaArray();
		refreshAmplitudeTable();
		refreshPhaseTable();
		refreshPositionTable();
		
		refreshForChckbxAmplitude();
		refreshForChckbxPhase();
		refreshForChckbxPosition();
		
		drawOuterMask();
		drawInnerMask();
		drawPlotWithInitialParameterValues();		
		
		algorithmExecuter = new AlgorithmExecuter();
		algorithmExecuter.execute();
		
		comboBoxNumberOfPoints.setSelectedIndex(1); // This is here because it triggers addActionListener which runs algorithmExecuter
	}
	
	void exportChartAsSVG(JFreeChart chart, File svgFile) throws IOException {

        DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
        Document document = domImpl.createDocument(null, "svg", null);
        
        SVGGraphics2D svgGenerator = new SVGGraphics2D(document);

        Rectangle r = new Rectangle(0, 0, width, height);
        chart.draw(svgGenerator, r);
        
        OutputStream outputStream = new FileOutputStream(svgFile);
        Writer out = new OutputStreamWriter(outputStream, "UTF-8");
        svgGenerator.stream(out, true /* use css */);
        outputStream.flush();
        outputStream.close();
	}
	
	private void refreshForChckbxAmplitude() {
		amplitudeIsUsed = chckbxAmplitude.isSelected();
		lblAmplitudeValuesComment.setText((amplitudeIsUsed) ? willBeOptimized : areFixed);
		textField_maximumValueAmplitude.setEditable(amplitudeIsUsed);
		textField_minimumValueAmplitude.setEditable(amplitudeIsUsed);
		refreshAmplitudeTable();
	}
	
	private void refreshForChckbxPhase() {
		phaseIsUsed = chckbxPhase.isSelected();
		lblPhaseValuesComment.setText((phaseIsUsed) ? willBeOptimized : areFixed);
		textField_maximumValuePhase.setEditable(phaseIsUsed);
		textField_minimumValuePhase.setEditable(phaseIsUsed);
		refreshPhaseTable();
	}
	
	private void refreshForChckbxPosition() {
		positionIsUsed = chckbxPosition.isSelected();
		lblPositionValuesComment.setText((positionIsUsed) ? willBeOptimized : areFixed);
		textField_maximumValuePosition.setEditable(positionIsUsed);
		textField_minimumValuePosition.setEditable(positionIsUsed);
		refreshPositionTable();
	}
	
	protected void setBestResultsToCurrentAntennaArray() {
		int delta = 0;
		if (amplitudeIsUsed) {
			// this is for amplitudes	
			for (int index = 0; index < numberOfElements; index++) {
				antennaArray.amplitude[index] = bestValues.valuesOfBestMember[index];
			}
			delta = numberOfElements;
		}
		
		if (phaseIsUsed) {
			// this is for phases
			for (int index = 0; index < numberOfElements; index++) {
				antennaArray.phase[index] = bestValues.valuesOfBestMember[index + delta];
			}
			delta += numberOfElements;
		}
		
		if (positionIsUsed) {
			// this is for positions. It starts with 1 instead of 0
			antennaArrayForPresentation.position[0] = 0;
			for (int index = 1; index < numberOfElements; index++) {
				antennaArray.position[index] = antennaArrayForPresentation.position[index - 1] + 0.5 + bestValues.valuesOfBestMember[index + delta];
			}
		}		
	}

	protected void createAntennaArray() {
		numberOfElements = Integer.parseInt(numberOfElements_Field.getText());
		antennaArray = new AntennaArray(numberOfElements, patternGraphResolution, mask);
		antennaArrayForPresentation = new AntennaArray(numberOfElements, patternGraphResolution, mask);
	}

	//	For outer mask segment
	class TableModelForOuterMask extends AbstractTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = -3580573026741275615L;
		private String[] columnNames = {"Angle (Degree)",
                "Level (dB)",
                "Weight"};
	
	    public int getColumnCount() {
	        return columnNames.length;
	    }
	
	    public int getRowCount() {
	    	if(selectedOuterMaskSegmentIndex == -1) return 0;
	    	Mask.MaskSegment SLL_outer;
			SLL_outer = mask.outerMaskSegments.get(selectedOuterMaskSegmentIndex);
			return SLL_outer.angles.length;
	    }
	
	    public String getColumnName(int col) {
	        return columnNames[col];
	    }
	
	    public Object getValueAt(int row, int col) {
			Mask.MaskSegment SLL_outer;
			SLL_outer = mask.outerMaskSegments.get(selectedOuterMaskSegmentIndex);
			double returnedValue = 0;
			if(col == 0) returnedValue = SLL_outer.angles[row];
			if(col == 1) returnedValue = SLL_outer.levels[row];
			if(col == 2) returnedValue = SLL_outer.weights[row];
			return returnedValue;
	    }
	
	    public Class<?> getColumnClass(int c) {
	        return getValueAt(0, c).getClass();
	    }
	
	    public boolean isCellEditable(int row, int col) {
	        if (col < 1) {
	            return false;
	        } else {
	            return true;
	        }
	    }
	    public void setValueAt(Object value, int row, int col) {
			Mask.MaskSegment SLL_outer;
			SLL_outer = mask.outerMaskSegments.get(selectedOuterMaskSegmentIndex);

			if(col == 1) SLL_outer.levels[row] = (double) value;
			if(col == 2) SLL_outer.weights[row] = (double) value;
	    	
	        fireTableCellUpdated(row, col);
	        
	        drawOuterMask();
	    }
	}
	
	//	For inner mask segment
	class TableModelForInnerMask extends AbstractTableModel {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6848149317192706675L;
		private String[] columnNames = {"Angle (Degree)",
                "Level (dB)",
                "Weight"};
	
	    public int getColumnCount() {
	        return columnNames.length;
	    }
	
	    public int getRowCount() {
	    	if(selectedInnerMaskSegmentIndex == -1) return 0;
	    	Mask.MaskSegment innerMaskSegment;
			innerMaskSegment = mask.innerMaskSegments.get(selectedInnerMaskSegmentIndex);
			return innerMaskSegment.angles.length;
	    }
	
	    public String getColumnName(int col) {
	        return columnNames[col];
	    }
	
	    public Object getValueAt(int row, int col) {
			Mask.MaskSegment innerMaskSegment;
			innerMaskSegment = mask.innerMaskSegments.get(selectedInnerMaskSegmentIndex);
			double returnedValue = 0;
			if(col == 0) returnedValue = innerMaskSegment.angles[row];
			if(col == 1) returnedValue = innerMaskSegment.levels[row];
			if(col == 2) returnedValue = innerMaskSegment.weights[row];
			return returnedValue;
	    }
	
	    public Class<?> getColumnClass(int c) {
	        return getValueAt(0, c).getClass();
	    }
	
	    public boolean isCellEditable(int row, int col) {
	        if (col < 1) {
	            return false;
	        } else {
	            return true;
	        }
	    }
	    public void setValueAt(Object value, int row, int col) {
			Mask.MaskSegment innerMaskSegment;
			innerMaskSegment = mask.innerMaskSegments.get(selectedInnerMaskSegmentIndex);

			if(col == 1) innerMaskSegment.levels[row] = (double) value;
			if(col == 2) innerMaskSegment.weights[row] = (double) value;
	    	
	        fireTableCellUpdated(row, col);
	        
	        drawInnerMask();
	    }
	}
	
	//	For amplitude
	class TableModelForAmplitude extends AbstractTableModel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 4560924156431649107L;
		private String[] columnNames = {"Number",
                "Value"};
	
	    public int getColumnCount() {
	        return columnNames.length;
	    }
	
	    public int getRowCount() {
	    	int length = 0;
	    	if (antennaArray != null) length = antennaArray.amplitude.length;
	    	if (chckbxAmplitude.isSelected()) length = 0;
	    	return length;
	    }
	
	    public String getColumnName(int col) {
	        return columnNames[col];
	    }
	
	    public Object getValueAt(int row, int col) {

	    	double returnedValue = 0;

			if(col == 0) returnedValue = row;
			if(col == 1) returnedValue = antennaArray.amplitude[row];
			
			return returnedValue;
	    }
	
	    public Class<?> getColumnClass(int c) {
	        return getValueAt(0, c).getClass();
	    }
	
	    public boolean isCellEditable(int row, int col) {
	        if (col < 1) {
	            return false;
	        } else {
	            return true;
	        }
	    }
	    public void setValueAt(Object value, int row, int col) {

			if(col == 1) antennaArray.amplitude[row] = (double) value;			
	    	
	        fireTableCellUpdated(row, col);
	        drawPlotWithInitialParameterValues();
	        
	        drawOuterMask();
	    }
	}

	//	For phase
	class TableModelForPhase extends AbstractTableModel {

		/**
		 * 
		 */
		private static final long serialVersionUID = -2008679132150819237L;
		private String[] columnNames = {"Number",
                "Value"};
	
	    public int getColumnCount() {
	        return columnNames.length;
	    }
	
	    public int getRowCount() {
	    	int length = 0;
	    	if (antennaArray != null) length = antennaArray.phase.length;
	    	if (chckbxPhase.isSelected()) length = 0;
	    	return length;
	    }
	
	    public String getColumnName(int col) {
	        return columnNames[col];
	    }
	
	    public Object getValueAt(int row, int col) {

	    	double returnedValue = 0;

			if(col == 0) returnedValue = row;
			if(col == 1) returnedValue = antennaArray.phase[row];
			
			return returnedValue;
	    }
	
	    public Class<?> getColumnClass(int c) {
	        return getValueAt(0, c).getClass();
	    }
	
	    public boolean isCellEditable(int row, int col) {
	        if (col < 1) {
	            return false;
	        } else {
	            return true;
	        }
	    }
	    public void setValueAt(Object value, int row, int col) {

			if(col == 1) antennaArray.phase[row] = (double) value;
	    	
	        fireTableCellUpdated(row, col);
	        drawPlotWithInitialParameterValues();
	        
	        drawOuterMask();
	    }
	}

	//	For position
	class TableModelForPosition extends AbstractTableModel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2847289441614578433L;
		private String[] columnNames = {"Number",
                "Value"};
	
	    public int getColumnCount() {
	        return columnNames.length;
	    }
	
	    public int getRowCount() {
	    	int length = 0;
	    	if (antennaArray != null) length = antennaArray.position.length;
	    	if (chckbxPosition.isSelected()) length = 0;
	    	return length;
	    }
	
	    public String getColumnName(int col) {
	        return columnNames[col];
	    }
	
	    public Object getValueAt(int row, int col) {

	    	double returnedValue = 0;

			if(col == 0) returnedValue = row;
			if(col == 1) returnedValue = antennaArray.position[row];
			
			return returnedValue;
	    }
	
	    public Class<?> getColumnClass(int c) {
	        return getValueAt(0, c).getClass();
	    }
	
	    public boolean isCellEditable(int row, int col) {
	        if (col < 1) {
	            return false;
	        } else {
	            return true;
	        }
	    }
	    public void setValueAt(Object value, int row, int col) {

			if(col == 1) antennaArray.position[row] = (double) value;
	    	
	        fireTableCellUpdated(row, col);
	        drawPlotWithInitialParameterValues();
	        
	        drawOuterMask();
	    }
	}

	
	private void refreshAmplitudeTable() {
		TableModelForAmplitude model = (TableModelForAmplitude) tableAmplitude.getModel();
		model.fireTableDataChanged();
	}
	
	private void refreshPhaseTable() {
		TableModelForPhase model = (TableModelForPhase) tablePhase.getModel();
		model.fireTableDataChanged();
	}
	
	private void refreshPositionTable() {
		TableModelForPosition model = (TableModelForPosition) tablePosition.getModel();
		model.fireTableDataChanged();
	}

	private void refreshOuterMaskSegmentDetailsTable() {
		TableModelForOuterMask model = (TableModelForOuterMask) outerTable.getModel();
		model.fireTableDataChanged();		
	}

	private void refreshInnerMaskSegmentDetailsTable() {
		TableModelForInnerMask model = (TableModelForInnerMask) innerTable.getModel();
		model.fireTableDataChanged();		
	}
	
	private void refreshOuterMaskSegmentsList() {
		// ------------- Outer Mask --------------------
		int numberOfOuterMaskSegments = mask.outerMaskSegments.size(); 
		Mask.MaskSegment outerMaskSegment;
		
		listModelForOuterMaskSegments.removeAllElements();
		for (int n = 0; n < numberOfOuterMaskSegments; n++) {
			outerMaskSegment = mask.outerMaskSegments.get(n);
			listModelForOuterMaskSegments.addElement(outerMaskSegment.name);
		}
		
		selectedOuterMaskSegmentIndex = outerList.getSelectedIndex();
	}
	
	private void refreshInnerMaskSegmentsList() {
		// ------------- Inner Mask --------------------
		int numberOfInnerMaskSegments = mask.innerMaskSegments.size(); 
		Mask.MaskSegment innerMaskSegment;
		
		listModelForInnerMaskSegments.removeAllElements();
		for (int n = 0; n < numberOfInnerMaskSegments; n++) {
			innerMaskSegment = mask.innerMaskSegments.get(n);
			listModelForInnerMaskSegments.addElement(innerMaskSegment.name);
		}
		
		selectedInnerMaskSegmentIndex = innerList.getSelectedIndex();
	}
	
	private boolean isThereAnyGapInOuterMask() {
		boolean gapExistency = false;
		
		int numberOfOuterMaskSegments = mask.outerMaskSegments.size();
		Mask.MaskSegment outerMaskSegment;
		
		if (numberOfOuterMaskSegments > 0) {
			for (int n = 0; n < numberOfOuterMaskSegments - 1; n++) {
				outerMaskSegment = mask.outerMaskSegments.get(n);
				if (n == 0) {
					if (outerMaskSegment.startAngle != 0)
						gapExistency = true;
				}

				if (outerMaskSegment.stopAngle != mask.outerMaskSegments.get(n + 1).startAngle) {
					gapExistency = true;
				}
			}
			if (mask.outerMaskSegments.get(numberOfOuterMaskSegments - 1).stopAngle != 180) {
				gapExistency = true;
			} 
		}
		
		return gapExistency;
	}
	
	
	private boolean isThereAnyGapInInnerMask() {
		boolean gapExistency = false;
		
		int numberOfInnerMaskSegments = mask.innerMaskSegments.size();
		Mask.MaskSegment innerMaskSegment;
		
		if (numberOfInnerMaskSegments > 0) {
			for (int n = 0; n < numberOfInnerMaskSegments - 1; n++) {
				innerMaskSegment = mask.innerMaskSegments.get(n);
				if (n == 0) {
					if (innerMaskSegment.startAngle != 0)
						gapExistency = true;
				}
				
				if (innerMaskSegment.stopAngle != mask.innerMaskSegments.get(n + 1).startAngle) {
					gapExistency = true;
				}
			}
			if (mask.innerMaskSegments.get(numberOfInnerMaskSegments - 1).stopAngle != 180) {
				gapExistency = true;
			} 
		}
		
		return gapExistency;
	}
	
	
	
	private void sendMessageToPane(String additionalMessage, boolean deletePreviousMessages) {
		if (deletePreviousMessages)
			messageToUser = additionalMessage;
		else
			messageToUser += additionalMessage;
		messagePane.setText(messageToUser);
	}
	
	private void presentErrorMessages() {
		int errors = messagesOfErrors.size();
		
		String tempMessage = null;
		if (errors == 1) {
			tempMessage = "Please press <b>Start Optimization</b> button again after fixing the following error:<br><br>";
			tempMessage += "<center><b>Error</b></center>";
			tempMessage += "<ul>";			
			tempMessage += "<li>";
			tempMessage += messagesOfErrors.get(0);
			tempMessage += "</li>";
			tempMessage += "</ul>";
		} else {
			tempMessage = "Please press <b>Start Optimization</b> button again after fixing the following errors:<br><br>";
			tempMessage += "<center><b>Errors</b></center>";			
			tempMessage += "<ol>";
			for (int i = 0; i < errors; i++) {
				tempMessage += "<li>";
				tempMessage += messagesOfErrors.get(i);
				tempMessage += "</li>";
			}
			tempMessage += "</ol>";
		}

		
		sendMessageToPane(tempMessage, true);
		
		messagesOfErrors.clear();
	}
	
	private boolean validateParameters() {
		boolean parametersAreValid = true;
		
		// Amplitude, phase and position values are evaluated.
		if(amplitudeIsUsed == false && phaseIsUsed == false && positionIsUsed == false)
		{
			parametersAreValid = false;
			messagesOfErrors.add("There is not any selected antenna parameters to optimize. At least one of them (amplitude, phase or position) must be selected.");
		}
		
		// Amplitude value validation
		if(amplitudeIsUsed) {
			if( ! validateTextBoxForDouble(textField_maximumValueAmplitude.getText())) {
				parametersAreValid = false;
				messagesOfErrors.add("Maximum amplitude value at array parameters tab is not valid.");			
			}
			
			if( ! validateTextBoxForDouble(textField_minimumValueAmplitude.getText())) {
				parametersAreValid = false;
				messagesOfErrors.add("Minimum amplitude value at array parameters tab is not valid.");			
			}		
		
			if(validateTextBoxForDouble(textField_maximumValueAmplitude.getText()) && validateTextBoxForDouble(textField_minimumValueAmplitude.getText())) {
				if(Double.parseDouble(textField_maximumValueAmplitude.getText()) <= Double.parseDouble(textField_minimumValueAmplitude.getText())) {
					parametersAreValid = false;
					messagesOfErrors.add("Maximum amplitude value must be bigger than minimum amplitude value.");				
				}
				
				if(Double.parseDouble(textField_maximumValueAmplitude.getText()) < 0) {
					parametersAreValid = false;
					messagesOfErrors.add("Maximum amplitude value cannot be negative.");				
				}
				
				if(Double.parseDouble(textField_minimumValueAmplitude.getText()) < 0) {
					parametersAreValid = false;
					messagesOfErrors.add("Minimum amplitude value cannot be negative.");				
				}
			}
		}
		
		// Phase value validation
		if(phaseIsUsed) {
			if( ! validateTextBoxForDouble(textField_maximumValuePhase.getText())) {
				parametersAreValid = false;
				messagesOfErrors.add("Maximum phase value at array parameters tab is not valid.");			
			}
			
			if( ! validateTextBoxForDouble(textField_minimumValuePhase.getText())) {
				parametersAreValid = false;
				messagesOfErrors.add("Minimum phase value at array parameters tab is not valid.");			
			}
			
			if(validateTextBoxForDouble(textField_maximumValuePhase.getText()) && validateTextBoxForDouble(textField_minimumValuePhase.getText())) {
				if(Double.parseDouble(textField_maximumValuePhase.getText()) <= Double.parseDouble(textField_minimumValuePhase.getText())) {
					parametersAreValid = false;
					messagesOfErrors.add("Maximum phase value must be bigger than minimum phase value.");				
				}
			}
		}

		//  position validation
		if(positionIsUsed) {
			if( ! validateTextBoxForDouble(textField_maximumValuePosition.getText())) {
				parametersAreValid = false;
				messagesOfErrors.add("Maximum position value at array parameters tab is not valid.");			
			}
			
			if( ! validateTextBoxForDouble(textField_minimumValuePosition.getText())) {
				parametersAreValid = false;
				messagesOfErrors.add("Minimum position value at array parameters tab is not valid.");			
			}
			
			if(validateTextBoxForDouble(textField_maximumValuePosition.getText()) && validateTextBoxForDouble(textField_minimumValuePosition.getText())) {
				if(Double.parseDouble(textField_maximumValuePosition.getText()) <= Double.parseDouble(textField_minimumValuePosition.getText())) {
					parametersAreValid = false;
					messagesOfErrors.add("Maximum position value must be bigger than minimum position value.");				
				}
				
				if(Double.parseDouble(textField_maximumValuePosition.getText()) < 0 || Double.parseDouble(textField_maximumValuePosition.getText()) >= 0.5) {
					parametersAreValid = false;
					messagesOfErrors.add("Maximum position value must be in the range of [0, 0.5).");		
				}
				
				if(Double.parseDouble(textField_minimumValuePosition.getText()) > 0 || Double.parseDouble(textField_minimumValuePosition.getText()) <= -0.5) {
					parametersAreValid = false;
					messagesOfErrors.add("Minimum position value must be in the range of (-0.5, 0].");				
				}			
			}
		}
		
		// DE parameters are evaluated.
		if( ! validateTextBoxForInteger(populationNumber_textField.getText())) {
			parametersAreValid = false;
			messagesOfErrors.add("Population number value at differential evolution tab is not valid.");			
		} else {
			double value = Double.parseDouble(populationNumber_textField.getText());
			if(value < 4) {
				parametersAreValid = false;
				messagesOfErrors.add("Population number value at differential evolution tab cannot be less than four.");
			}
		}
		
		if( ! validateTextBoxForInteger(maximumIterationNumber_textField.getText())) {
			parametersAreValid = false;
			messagesOfErrors.add("Maximum iteration number value at differential evolution tab is not valid.");			
		} else {
			double value = Double.parseDouble(maximumIterationNumber_textField.getText());
			if(value < 1) {
				parametersAreValid = false;
				messagesOfErrors.add("Maximum iteration number value at differential evolution tab cannot be less than one.");
			}
		}
		
		if( ! validateTextBoxForDouble(F_textField.getText())) {
			parametersAreValid = false;
			messagesOfErrors.add("Scaling Factor (F) value at differential evolution tab is not valid.");			
		} else {
			double value = Double.parseDouble(F_textField.getText());
			if(value < 0 || value > 2) {
				parametersAreValid = false;
				messagesOfErrors.add("Scaling Factor (F) value at differential evolution tab must be in the range of [0, 2].");
			}
		}
		
		if( ! validateTextBoxForDouble(Cr_textField.getText())) {
			parametersAreValid = false;
			messagesOfErrors.add("Crossover Rate (Cr) value at differential evolution tab is not valid.");			
		} else {
			double value = Double.parseDouble(Cr_textField.getText());
			if(value < 0 || value > 1) {
				parametersAreValid = false;
				messagesOfErrors.add("Crossover Rate (Cr) value at differential evolution tab must be in the range of [0, 1].");
			}
		}
		
		return parametersAreValid;
	}

	private boolean validateTextBoxForDouble(String text) {
		
		boolean result = true;
		
		try {
			Double.parseDouble(text);
		} catch (NumberFormatException e) {
			result = false;
		}
		
		return result;
	}
	
	private boolean validateTextBoxForInteger(String text) {
		
		boolean result = true;
		
		try {
			Integer.parseInt(text);
		} catch (NumberFormatException e) {
			result = false;
		}
		
		return result;
	}

	private void getParametersFromUserInterface() {
		// DE parameters
	    populationNumber = Integer.parseInt(populationNumber_textField.getText());
	    maximumIterationNumber = Integer.parseInt(maximumIterationNumber_textField.getText());
	    F = Double.parseDouble(F_textField.getText());
	    Cr = Double.parseDouble(Cr_textField.getText());
	    
	    // Antenna array parameters
	    H[0] = Double.parseDouble(textField_maximumValueAmplitude.getText());
	    L[0] = Double.parseDouble(textField_minimumValueAmplitude.getText());
	    H[1] = Double.parseDouble(textField_maximumValuePhase.getText());
	    L[1] = Double.parseDouble(textField_minimumValuePhase.getText());
	    H[2] = Double.parseDouble(textField_maximumValuePosition.getText());
	    L[2] = Double.parseDouble(textField_minimumValuePosition.getText());	    
	}
	
	private void calculateProblemDimension() {
		problemDimension = 0;
		if (amplitudeIsUsed) problemDimension = numberOfElements;		
		if (phaseIsUsed) problemDimension += numberOfElements;		
		if (positionIsUsed) problemDimension += numberOfElements;
	}
	
	private void createMainObjects() {		
		differentialEvolution = new DifferentialEvolution(numberOfElements, populationNumber, maximumIterationNumber, F, Cr, L, H, antennaArray, antennaArrayForPresentation, mask, amplitudeIsUsed, phaseIsUsed, positionIsUsed);
	}
	
	private void preserveAspectRatio(JPanel innerPanel, JPanel container) {
        int w = container.getWidth();
        innerPanel.setPreferredSize(new Dimension(w, w*height/width));
        container.revalidate();
    }

	protected void drawPlotWithInitialParameterValues() {
		
		if(patternGraphResolution != -1 && patternGraphResolution != -2) {
			antennaArray.createPattern();
			
			for(int x=0; x<antennaArray.angle.length; x++)
			{				
				seriler.addOrUpdate(antennaArray.angle[x], antennaArray.pattern_dB[x]);
			}
			
		} else {
			
			antennaArray.createLongArrays();
			antennaArray.createPatternForOptimization();
			
			if (patternGraphResolution == -1) {
				if (antennaArray.numberOfSLLOuters > 0) {
					for(int x=0; x<antennaArray.angleForOptimization_ForOuters.length; x++)
					{				
						seriler.addOrUpdate(antennaArray.angleForOptimization_ForOuters[x], antennaArray.patternForOptimization_dB_ForOuters[x]);
					}
				}
			}
			
			if (patternGraphResolution == -2) {
				if (antennaArray.numberOfSLLInners > 0) {
					for(int x=0; x<antennaArray.angleForOptimization_ForInners.length; x++)
					{				
						seriler.addOrUpdate(antennaArray.angleForOptimization_ForInners[x], antennaArray.patternForOptimization_dB_ForInners[x]);
					}
				}
			}			
		}
		
	}
	
	protected void drawPlotOfPattern() {

		int delta = 0;
		if (amplitudeIsUsed) {
			// this is for amplitudes	
			for (int index = 0; index < numberOfElements; index++) {
				antennaArrayForPresentation.amplitude[index] = bestValues.valuesOfBestMember[index];
			}
			delta = numberOfElements;
		} else {
			for (int index = 0; index < numberOfElements; index++) {
				antennaArrayForPresentation.amplitude[index] = antennaArray.amplitude[index];
			}
		}
		
		if (phaseIsUsed) {
			// this is for phases
			for (int index = 0; index < numberOfElements; index++) {
				antennaArrayForPresentation.phase[index] = bestValues.valuesOfBestMember[index + delta];
			}
			delta += numberOfElements;
		} else {
			for (int index = 0; index < numberOfElements; index++) {
				antennaArrayForPresentation.phase[index] = antennaArray.phase[index];
			}
		}
		
		if (positionIsUsed) {
			// this is for positions. It starts with 1 instead of 0
			antennaArrayForPresentation.position[0] = 0;
			for (int index = 1; index < numberOfElements; index++) {
				antennaArrayForPresentation.position[index] = antennaArrayForPresentation.position[index - 1] + 0.5 + bestValues.valuesOfBestMember[index + delta];
			}
		} else {
			for (int index = 1; index < numberOfElements; index++) {
				antennaArrayForPresentation.position[index] = antennaArray.position[index];
			}
		}
		
		
		if(patternGraphResolution != -1 && patternGraphResolution != -2) {
			antennaArrayForPresentation.createPattern();
			
			for(int x=0; x<antennaArrayForPresentation.angle.length; x++)
			{				
				seriler.addOrUpdate(antennaArrayForPresentation.angle[x], antennaArrayForPresentation.pattern_dB[x]);
			}			
		} else {
			if (patternGraphResolution == -1) {
				if (antennaArray.numberOfSLLOuters > 0) {
					antennaArrayForPresentation.angleForOptimization_ForOuters = new double[antennaArray.angleForOptimization_ForOuters.length];
					antennaArrayForPresentation.patternForOptimization_dB_ForOuters = new double[antennaArray.angleForOptimization_ForOuters.length];
				} 
			}

			if (patternGraphResolution == -2) {
				if (antennaArray.numberOfSLLInners > 0) {
					antennaArrayForPresentation.angleForOptimization_ForInners = new double[antennaArray.angleForOptimization_ForInners.length];
					antennaArrayForPresentation.patternForOptimization_dB_ForInners = new double[antennaArray.angleForOptimization_ForInners.length];
				}
			}
			
			antennaArrayForPresentation.createPatternForOptimization();
			
			if (patternGraphResolution == -1) {
				if (antennaArray.numberOfSLLOuters > 0) {
					for(int x=0; x<antennaArrayForPresentation.angleForOptimization_ForOuters.length; x++)
					{				
						seriler.addOrUpdate(antennaArrayForPresentation.angleForOptimization_ForOuters[x], antennaArrayForPresentation.patternForOptimization_dB_ForOuters[x]);
					}
				}
			}
			
			if (patternGraphResolution == -2) {
				if (antennaArray.numberOfSLLInners > 0) {
					for(int x=0; x<antennaArrayForPresentation.angleForOptimization_ForInners.length; x++)
					{				
						seriler.addOrUpdate(antennaArrayForPresentation.angleForOptimization_ForInners[x], antennaArrayForPresentation.patternForOptimization_dB_ForInners[x]);
					}
				}
			}			
		}
	}
	
	protected void drawPlotOfConvergence() {
		int currentIterationIndex = differentialEvolution.iterationIndex;
		int index = 0;
		for(index = unplottedIterationIndexBeginning; index < currentIterationIndex; index++) {
			convergenceSeries.add(index, differentialEvolution.costValues[index]);
		}
		unplottedIterationIndexBeginning = index;		
		
		iterationText.setText(Integer.toString(differentialEvolution.iterationIndex - 1)); // we remove one from iterationIndex because this number was increased by algorithm but it does not match the current fitnessOfBestMember 
		costText.setText(Double.toString(differentialEvolution.fitnessOfBestMember));
	}

	private void drawOuterMask() {
		// ------------- Outer Mask --------------------
		int numberOfSLLOuters = mask.outerMaskSegments.size(); 
		Mask.MaskSegment SLL_outer;
		
		maskOuter.clear();
		for (int n = 0; n < numberOfSLLOuters; n++) {
			SLL_outer = mask.outerMaskSegments.get(n);
			for (int i = 0; i < SLL_outer.angles.length; i++) {
				if(i==0)
					maskOuter.addOrUpdate(SLL_outer.angles[i]+0.0000000001, SLL_outer.levels[i]);
				else
					maskOuter.addOrUpdate(SLL_outer.angles[i], SLL_outer.levels[i]);		
			}			
		}
	}

	private void drawInnerMask() {
		// ------------- Inner Mask --------------------
		int numberOfSLLInners = mask.innerMaskSegments.size(); 
		Mask.MaskSegment SLL_inner;

		maskInner.clear();
		for (int n = 0; n < numberOfSLLInners; n++) {
			SLL_inner = mask.innerMaskSegments.get(n);
			for (int i = 0; i < SLL_inner.angles.length; i++) {
				if(i==0)
					maskInner.addOrUpdate(SLL_inner.angles[i]+0.0000000001, SLL_inner.levels[i]);
				else
					maskInner.addOrUpdate(SLL_inner.angles[i], SLL_inner.levels[i]);		
			}			
		}
	}
	
	@Override
	public void chartMouseClicked(ChartMouseEvent arg0) {
		
	}

	@Override
	public void chartMouseMoved(ChartMouseEvent event) {
        Rectangle2D dataArea = this.chartPanelPattern.getScreenDataArea();
        JFreeChart chart = event.getChart();
        XYPlot plot = (XYPlot) chart.getPlot();
        ValueAxis xAxis = plot.getDomainAxis();
        double x = xAxis.java2DToValue(event.getTrigger().getX(), dataArea, 
                RectangleEdge.BOTTOM);
        double y = DatasetUtilities.findYValue(plot.getDataset(), 0, x);
        this.xCrosshair.setValue(x);
        this.yCrosshair.setValue(y);
		
	}
	
	class AlgorithmExecuter extends SwingWorker<Void, BestValues>
	{		
		private boolean keepIterating;
		private boolean newStart;
		boolean iterationHasNotCompletedYet = true;
		
		public AlgorithmExecuter() {
			keepIterating = false;
			newStart = true;
		}

		@Override
		protected Void doInBackground() throws Exception {
			while(!isCancelled())
			{
				while (keepIterating && iterationHasNotCompletedYet) {
					iterationHasNotCompletedYet = differentialEvolution.iterate();
					double[] valuesOfBestMember = new double[problemDimension];
					for (int d = 0; d < problemDimension; d++) {
						valuesOfBestMember[d] = differentialEvolution.members[d][differentialEvolution.bestMember];
					}
					if(iterationHasNotCompletedYet == false) firstOrLastPlot = true;
					publish(new BestValues(differentialEvolution.bestMember, differentialEvolution.fitnessOfBestMember, valuesOfBestMember));
				}
			}			
			return null;
		}
		
		@Override
		protected void process(List<BestValues> chunks) {
			bestValues = chunks.get(chunks.size()-1);
			int progress = (int) (100*differentialEvolution.iterationIndex / differentialEvolution.maximumIterationNumber);
			progressBar.setValue(progress);
			if(iterationHasNotCompletedYet == false)
			{	
				keepIterating = false;
				newStart = true;
				startPauseButton.setText("Start Optimization");
				sendMessageToPane("<br><font color=#006400><b>Optimization process has been <i>completed</i> successfully!</b></font>", false);
				showCurrentResults();
				makeComponentsEnable(true);
				tabbedPaneForSettings.setSelectedIndex(4);
				
				// Set the best results as the current value to the current antenna array
				setBestResultsToCurrentAntennaArray();
			}			
			
			double currentTime = System.currentTimeMillis();
			double elapsedTimeForPatternGraph = currentTime - startTimeForPatternGraph;
			if(periodForPatternGraph != -1 || firstOrLastPlot) {
				if(periodForPatternGraph == 0 || elapsedTimeForPatternGraph > periodForPatternGraph || firstOrLastPlot) {
					startTimeForPatternGraph = currentTime;
					drawPlotOfPattern();
				}
			}
			
			double elapsedTimeForConvergenceGraph = currentTime - startTimeForConvergenceGraph;
			if(periodForConvergenceGraph != -1 || firstOrLastPlot) {
				if(periodForConvergenceGraph == 0 || elapsedTimeForConvergenceGraph > periodForConvergenceGraph || firstOrLastPlot) {
					startTimeForConvergenceGraph = currentTime;
					drawPlotOfConvergence();
					firstOrLastPlot = false;
				}
			}			
		}
	}
	
	private void showCurrentResults() {
		String currentResults;
		currentResults = "<hr><b>Results:</b><br>amplitudes = [<br>";
		
		int delta = 0;

		if (amplitudeIsUsed) {
			// this is for amplitudes	
			for (int index = 0; index < numberOfElements; index++) {
				currentResults += Double.toString(bestValues.valuesOfBestMember[index]);
				currentResults += "<br>";
			}
			delta = numberOfElements;
		} else {
			for (int index = 0; index < numberOfElements; index++) {
				currentResults += Double.toString(antennaArray.amplitude[index]);
				currentResults += "<br>";
			}
		}
		currentResults += "]";
		
		currentResults += "<br><br>phases = [<br>";
		if (phaseIsUsed) {
			// this is for phases
			for (int index = 0; index < numberOfElements; index++) {
				currentResults += Double.toString(bestValues.valuesOfBestMember[index + delta]);
				currentResults += "<br>";
			}
			delta += numberOfElements;
		} else {
			for (int index = 0; index < numberOfElements; index++) {
				currentResults += Double.toString(antennaArray.phase[index]);
				currentResults += "<br>";				
			}
		}
		currentResults += "]";
		
		currentResults += "<br><br>positions = [<br>";
		currentResults += "0<br>";
		double previousElementPosition = 0;		
		if (positionIsUsed) {
			// this is for positions. It starts with 1 instead of 0			
			for (int index = 1; index < numberOfElements; index++) {
				currentResults += Double.toString(previousElementPosition + 0.5 + bestValues.valuesOfBestMember[index + delta]);
				previousElementPosition += 0.5 + bestValues.valuesOfBestMember[index + delta];
				currentResults += "<br>";
			}
		} else {
			for (int index = 1; index < numberOfElements; index++) {
				currentResults += Double.toString(antennaArray.position[index]);
				currentResults += "<br>";
			}
		}
		currentResults += "]";
		
		sendMessageToPane(currentResults, false);
		
	}
	
	private void makeComponentsEnable(boolean enabled) {
		btnSetElementNumber.setEnabled(enabled);
		
		chckbxAmplitude.setEnabled(enabled);			
		textField_maximumValueAmplitude.setEnabled(enabled);
		textField_minimumValueAmplitude.setEnabled(enabled);
		btnResetAmplitudeValues.setEnabled(enabled);
		btnLoadAmplitudes.setEnabled(enabled);
		tableAmplitude.setEnabled(enabled);

		chckbxPhase.setEnabled(enabled);
		textField_maximumValuePhase.setEnabled(enabled);
		textField_minimumValuePhase.setEnabled(enabled);
		btnResetPhaseValues.setEnabled(enabled);
		btnLoadPhases.setEnabled(enabled);
		tablePhase.setEnabled(enabled);
		
		chckbxPosition.setEnabled(enabled);
		textField_maximumValuePosition.setEnabled(enabled);
		textField_minimumValuePosition.setEnabled(enabled);
		btnResetDistancesTo.setEnabled(enabled);
		btnLoadPositions.setEnabled(enabled);
		tablePosition.setEnabled(enabled);
		
		btnAddOuterMaskSegment.setEnabled(enabled);
		btnEditOuterMaskSegment.setEnabled(enabled);
		btnDeleteOuterMaskSegment.setEnabled(enabled);
		outerTable.setEnabled(enabled);		
		
		btnAddInnerMaskSegment.setEnabled(enabled);
		btnEditInnerMaskSegment.setEnabled(enabled);
		btnDeleteInnerMaskSegment.setEnabled(enabled);
		innerTable.setEnabled(enabled);
		
		populationNumber_textField.setEnabled(enabled);
		maximumIterationNumber_textField.setEnabled(enabled);
		F_textField.setEnabled(enabled);
		Cr_textField.setEnabled(enabled);
		
		btnResetConfigurationToDefault.setEnabled(enabled);
		btnLoadConfigurationFromAFile.setEnabled(enabled);
		btnSaveConfigurationToAFile.setEnabled(enabled);
		exportPatternAsSVG.setEnabled(enabled);
		exportConvergeCurve.setEnabled(enabled);
	}
	
	private void createTemporaryMaskSegments() {
		// Mask segment testing
	}
}
