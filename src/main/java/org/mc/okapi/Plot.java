/*
(c) INSERM U872, 2013

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation;
    version 2 of the License.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

Contact: https://github.com/nhtuong/okapi

Authors:
Hoai-Tuong Nguyen <hoai-tuong.nguyen@inserm.fr>

*/

package org.mc.okapi;

import java.awt.Color;
import java.awt.Shape;
import java.awt.Toolkit;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.ShapeUtilities;

public class Plot extends JFrame {

	  private static final long serialVersionUID = 1L;

	  public Plot(double[] X, double[] Y, String xLab, String yLab, String seriesName, String applicationTitle, String chartTitle) {
	        super(applicationTitle);
	        
	         
	        XYSeries series = new XYSeries(seriesName);
	        
	        for (int i=0;i<X.length;i++){
		        series.add(X[i], Y[i]);	        
	        }
	        // create a dataset...
	        XYSeriesCollection dataset = new XYSeriesCollection();
	        dataset.addSeries(series);

	        
	        
	        // based on the dataset we create the chart        
	        JFreeChart chart = ChartFactory.createScatterPlot(
	        		chartTitle,
	        		xLab,
	        		yLab,
	        		dataset,
	        		PlotOrientation.VERTICAL, true, rootPaneCheckingEnabled, rootPaneCheckingEnabled);
	        Shape cross = ShapeUtilities.createDiagonalCross(3, 1);
	        XYPlot xyPlot = (XYPlot) chart.getPlot();
	        xyPlot.setDomainCrosshairVisible(true);
	        xyPlot.setRangeCrosshairVisible(true);
	        XYItemRenderer renderer = xyPlot.getRenderer();
	        renderer.setSeriesShape(0, cross);
	        renderer.setSeriesPaint(0, Color.red);

		        
	        // we put the chart into a panel
	        ChartPanel chartPanel = new ChartPanel(chart);
	        setIconImage(Toolkit.getDefaultToolkit().getImage("images/ico/extra/science_32.png"));
	        
	        // default size
	        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
	        // add it to our application
	        setContentPane(chartPanel);

	    }
	    
	    
} 
