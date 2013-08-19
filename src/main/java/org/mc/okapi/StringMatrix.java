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

public class StringMatrix {
	public String [][] data;
	
	public String[] getRowElements(int index){
			String [] res = new String[data[0].length];
			for (int j=0;j<data[0].length;j++){
				res[j] = data[index][j];
			
			}
			return res;
	}

	public String[] getColumnElements(int index){
		String [] res = new String[data.length];
		for (int i=0;i<data.length;i++){
			res[i] = data[i][index];
		
		}
		return res;
	}
	
}
