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
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeController;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.filters.api.FilterController;
import org.gephi.filters.api.Query;
import org.gephi.filters.api.Range;
import org.gephi.filters.plugin.graph.DegreeRangeBuilder.DegreeRangeFilter;
import org.gephi.graph.api.DirectedGraph;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.GraphView;
import org.gephi.graph.api.UndirectedGraph;
import org.gephi.io.exporter.api.ExportController;
import org.gephi.io.exporter.preview.PDFExporter;
import org.gephi.io.importer.api.Container;
import org.gephi.io.importer.api.EdgeDefault;
import org.gephi.io.importer.api.ImportController;
import org.gephi.io.processor.plugin.DefaultProcessor;
import org.gephi.layout.plugin.AutoLayout;
import org.gephi.layout.plugin.force.StepDisplacement;
import org.gephi.layout.plugin.force.yifanHu.YifanHuLayout;
import org.gephi.layout.plugin.forceAtlas.ForceAtlasLayout;
import org.gephi.layout.plugin.forceAtlas2.ForceAtlas2;
import org.gephi.layout.plugin.forceAtlas2.ForceAtlas2Builder;
import org.gephi.preview.api.PreviewController;
import org.gephi.preview.api.PreviewModel;
import org.gephi.preview.api.PreviewProperty;
import org.gephi.preview.types.EdgeColor;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.gephi.ranking.api.Ranking;
import org.gephi.ranking.api.RankingController;
import org.gephi.ranking.api.Transformer;
import org.gephi.ranking.plugin.transformer.AbstractColorTransformer;
import org.gephi.ranking.plugin.transformer.AbstractSizeTransformer;
import org.gephi.statistics.plugin.GraphDistance;
import org.openide.util.Lookup;

import com.itextpdf.text.PageSize;

public class Gephi {
	
	
	Gephi(String inFile, String outFile, String layoutType) throws IOException{

    	ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
    	pc.newProject();
    	Workspace workspace = pc.getCurrentWorkspace();
    	
    	//Get models and controllers for this new workspace - will be useful later
    	AttributeModel attributeModel = Lookup.getDefault().lookup(AttributeController.class).getModel();
    	GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
    	PreviewModel model = Lookup.getDefault().lookup(PreviewController.class).getModel();
    	ImportController importController = Lookup.getDefault().lookup(ImportController.class);
    	FilterController filterController = Lookup.getDefault().lookup(FilterController.class);
    	RankingController rankingController = Lookup.getDefault().lookup(RankingController.class);
    	 
    	//Import file       
    	Container container;
    	
    	try {
    	    File file = new File(inFile);
    	    container = importController.importFile(file);
    	    container.getLoader().setEdgeDefault(EdgeDefault.DIRECTED);   //Force DIRECTED
    	} catch (Exception eex) {
    	    eex.printStackTrace();
    	    return;
    	}
    	 
    	//Append imported data to GraphAPI
    	importController.process(container, new DefaultProcessor(), workspace);
    	 
    	//See if graph is well imported
    	DirectedGraph graph = graphModel.getDirectedGraph();
    	System.out.println("Nodes: " + graph.getNodeCount());
    	System.out.println("Edges: " + graph.getEdgeCount());
    	 
    	//Filter      
    	DegreeRangeFilter degreeFilter = new DegreeRangeFilter();
    	degreeFilter.init(graph);
    	degreeFilter.setRange(new Range(30, Integer.MAX_VALUE));     //Remove nodes with degree < 30
    	Query query = filterController.createQuery(degreeFilter);
    	GraphView view = filterController.filter(query);
    	graphModel.setVisibleView(view);    //Set the filter result as the visible view
    	 
    	//See visible graph stats
    	UndirectedGraph graphVisible = graphModel.getUndirectedGraphVisible();
    	System.out.println("Nodes: " + graphVisible.getNodeCount());
    	System.out.println("Edges: " + graphVisible.getEdgeCount());
    	 
    	
    	
    	/*YifanHuLayout layout = new YifanHuLayout(null, new StepDisplacement(1f));
    	
    	//Run YifanHuLayout for 100 passes - The layout always takes the current visible view
    	if (layoutType=="Yifan Hu"){
    		
    		System.out.println("layoutType: " + layoutType);
    		
        	layout.setGraphModel(graphModel);
        	layout.resetPropertiesValues();
        	layout.initAlgo();
        	 
        	for (int i = 0; i < 100 && layout.canAlgo(); i++) {
        	    layout.goAlgo();
        	}
        	layout.endAlgo();
    	}*/
    	
    	/*ForceAtlas2 layoutForceAtlas2 = new ForceAtlas2(new ForceAtlas2Builder());
    	
    	//if (layoutType=="Force Atlas 2"){
    		
    		System.out.println("layoutType: " + layoutType);
    		
    		layoutForceAtlas2.setGraphModel(graphModel);
    		layoutForceAtlas2.resetPropertiesValues();
    		layoutForceAtlas2.setEdgeWeightInfluence(1.0);
    		layoutForceAtlas2.setGravity(1.0);
    		layoutForceAtlas2.setScalingRatio(2.0);
    		layoutForceAtlas2.setBarnesHutTheta(1.2);
    		layoutForceAtlas2.setJitterTolerance(0.1);

    		for (int i = 0; i < 100 && layoutForceAtlas2.canAlgo(); i++) 
    			layoutForceAtlas2.goAlgo();
    	//}
    	
    	*/
    	
    	if (layoutType=="Yifan Hu")
    		DoYifanHuLayout(graphModel,1);
    	if (layoutType=="Force Atlas 2")
    		DoForceAtlasLayout(graphModel);
 
       	
    	
    	
    	//Get Centrality
    	GraphDistance distance = new GraphDistance();
    	distance.setDirected(true);
    	distance.execute(graphModel, attributeModel);
    	 
    	//Rank color by Degree
    	Ranking degreeRanking = rankingController.getModel().getRanking(Ranking.NODE_ELEMENT, Ranking.DEGREE_RANKING);
    	AbstractColorTransformer colorTransformer = (AbstractColorTransformer) rankingController.getModel().getTransformer(Ranking.NODE_ELEMENT, Transformer.RENDERABLE_COLOR);
    	colorTransformer.setColors(new Color[]{new Color(0xFEF0D9), new Color(0xB30000)});
    	rankingController.transform(degreeRanking,colorTransformer);
    	 
    	//Rank size by centrality
    	AttributeColumn centralityColumn = attributeModel.getNodeTable().getColumn(GraphDistance.BETWEENNESS);
    	Ranking centralityRanking = rankingController.getModel().getRanking(Ranking.NODE_ELEMENT, centralityColumn.getId());
    	AbstractSizeTransformer sizeTransformer = (AbstractSizeTransformer) rankingController.getModel().getTransformer(Ranking.NODE_ELEMENT, Transformer.RENDERABLE_SIZE);
    	sizeTransformer.setMinSize(3);
    	sizeTransformer.setMaxSize(10);
    	rankingController.transform(centralityRanking,sizeTransformer);
    	 
    	//Preview
    	model.getProperties().putValue(PreviewProperty.SHOW_NODE_LABELS, Boolean.TRUE);
    	model.getProperties().putValue(PreviewProperty.EDGE_COLOR, new EdgeColor(Color.GRAY));
    	model.getProperties().putValue(PreviewProperty.EDGE_THICKNESS, new Float(0.1f));
    	model.getProperties().putValue(PreviewProperty.NODE_LABEL_FONT, model.getProperties().getFontValue(PreviewProperty.NODE_LABEL_FONT).deriveFont(8));
    	 
    	//Export
    	//CreatePDFFile(workspace, outFile);
    	ExportController ec = Lookup.getDefault().lookup(ExportController.class);
    	try {
    	    ec.exportFile(new File(outFile));
    	} catch (IOException eeex) {
    	    eeex.printStackTrace();
    	    return;
    	}
	}
	
	 public void DoYifanHuLayout(GraphModel graphModel, int minutes) {
		 

         YifanHuLayout layout = new YifanHuLayout(null, new StepDisplacement(1f));
 
 		
     	layout.setGraphModel(graphModel);
     	layout.resetPropertiesValues();
     	layout.initAlgo();
     	 
     	for (int i = 0; i < 100 && layout.canAlgo(); i++) {
     	    layout.goAlgo();
     	}
     	layout.endAlgo();
     	

	 }
	 
	 public void DoForceAtlasLayout(GraphModel graphModel) {
		ForceAtlas2 layoutForceAtlas2 = new ForceAtlas2(new ForceAtlas2Builder());
 		layoutForceAtlas2.setGraphModel(graphModel);
 		layoutForceAtlas2.resetPropertiesValues();
 		layoutForceAtlas2.setEdgeWeightInfluence(1.0);
 		layoutForceAtlas2.setGravity(1.0);
 		layoutForceAtlas2.setScalingRatio(2.0);
 		layoutForceAtlas2.setBarnesHutTheta(1.2);
 		layoutForceAtlas2.setJitterTolerance(0.1);

 		for (int i = 0; i < 100 && layoutForceAtlas2.canAlgo(); i++) 
 			layoutForceAtlas2.goAlgo();
 		
 		
	 }
	 
	 public void CreatePDFFile(Workspace workspace, String filename) throws IOException {
	        if (filename == null || "".equals(filename)) {
	            throw new IOException("Invalid file name.");
	        } else {
	            ExportController ec = Lookup.getDefault().lookup(ExportController.class);
	            PDFExporter pdfExporter = (PDFExporter) ec.getExporter("pdf");
	            pdfExporter.setPageSize(PageSize.A0);
	            pdfExporter.setWorkspace(workspace);
	            try {
	               ec.exportFile(new File(filename+".pdf"));
	            } catch (IOException ex) {
	               ex.printStackTrace();
	               return;
	            }
	        }
	    }
	 
	 
}
