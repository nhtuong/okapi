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
