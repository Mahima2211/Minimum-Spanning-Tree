package apps;

import structures.*;
import java.util.ArrayList;

public class MST {
	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) 
	{
	
		
	 PartialTreeList r = new PartialTreeList(); //step 1
		
		for (int i = 0; i<graph.vertices.length; i++)
		{ 
			Vertex V = graph.vertices[i];
			
			PartialTree T = new PartialTree(V); // step 2.2
			Vertex.Neighbor n = graph.vertices[i].neighbors;
		    MinHeap<PartialTree.Arc> P = T.getArcs();
		
		while (n != null)
		{
		
		PartialTree.Arc f = new PartialTree.Arc(graph.vertices[i], n.vertex , n.weight);
		P.insert(f);
		
		if (n.next == null )
		{
			break;
		}
		n = n.next;
		if (n.vertex == graph.vertices[i])
		    {
				n = n.next;
			}
		
		}
			
	
			
			r.append(T); //step.2.5
		}
		
		
	
		
			return r;
	}
	 

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {
		
		
		ArrayList<PartialTree.Arc> res = new ArrayList<PartialTree.Arc>(); //hey here I start
		int c = ptlist.size();
		
		while(c>1)	
		{
			
			PartialTree hello = ptlist.remove();
			if(hello==null)
			{
				break;
			}
			
			MinHeap<PartialTree.Arc> pqx = hello.getArcs();
			
			PartialTree.Arc largeP= pqx.deleteMin();
			Vertex v2 = largeP.v2;
			Vertex v1 = hello.getRoot();
			
			
			if(v1==v2 || v1==v2.parent)
			{
				largeP = pqx.deleteMin();
				v2= largeP.v2.parent;
			}
		
			res.add(largeP);
		
			PartialTree pty = ptlist.removeTreeContaining(v2);
			if(pty==null)
			{
				continue;
			}

			pty.getRoot().parent = hello.getRoot();
			
			pty.merge(hello);
			ptlist.append(pty);
		
		
			
			c--;
			

	}
	
		return res;
      }
	}
