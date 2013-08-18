package org.mc.okapi;

import java.awt.Toolkit;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

public class PieChart extends JFrame {

	  private static final long serialVersionUID = 1L;

	  public PieChart(String[] label,  double[] value, String applicationTitle, String chartTitle) {
	        super(applicationTitle);
	        
	        // This will create the dataset 
	        
	        DefaultPieDataset result = new DefaultPieDataset();

	        for( int i = 0; i < label.length; i++) {
	            result.setValue(label[i], value[i]);
	        }
	        PieDataset dataset = result;
	        
	        // based on the dataset we create the chart
	        JFreeChart chart = ChartFactory.createPieChart3D(chartTitle,          // chart title
		            dataset,                // data
		            true,                   // include legend
		            true,
		            false);

		        PiePlot3D plot = (PiePlot3D) chart.getPlot();
		        plot.setStartAngle(290);
		        plot.setDirection(Rotation.CLOCKWISE);
		        plot.setForegroundAlpha(0.5f);

		        
	        // we put the chart into a panel
	        ChartPanel chartPanel = new ChartPanel(chart);
	        setIconImage(Toolkit.getDefaultToolkit().getImage("src/images/ico/extra/science_32.png"));
	        // default size
	        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
	        // add it to our application
	        setContentPane(chartPanel);

	    }
	    
	    
} 
