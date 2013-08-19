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
