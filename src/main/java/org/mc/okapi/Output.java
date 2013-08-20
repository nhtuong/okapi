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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class Output extends JFrame {

	private JPanel contentPane;

	public JTextArea display;

	/**
	 * Create the frame.
	 */
	public Output(String content) {
		JPanel middlePanel = new JPanel ();
	    middlePanel.setBorder ( new TitledBorder ( new EtchedBorder (), "Output message" ) );
	    setIconImage(Toolkit.getDefaultToolkit().getImage("images/ico/extra/science_32.png"));
	    // create the middle panel components

	    display = new JTextArea ( 16, 58 );
	    //display.setEditable ( false ); // set textArea non-editable
	    display.setLineWrap(true);
	    //display.setWrapStyleWord(true);
	    
	    display.append(content);
	    JScrollPane scroll = new JScrollPane ( display );
	    scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );

	    //Add Textarea in to middle panel
	    middlePanel.add(scroll );

	    // My code
	    JFrame frame = new JFrame ();
	    frame.add(middlePanel );
	    frame.pack();
	    frame.setLocationRelativeTo ( null );
	    frame.setResizable(false);
	    frame.setVisible(true);

		

	}

}
