package aps180006; /** Breadth-first search: Object-oriented version
 *  @author rbk
 *  Version 1.0: 2018/10/16
 */

import aps180006.Graph.Vertex;
import aps180006.Graph.Edge;
import aps180006.Graph.Factory;

import java.io.File;
import java.util.*;

public class BFSOO extends Graph.GraphAlgorithm<BFSOO.BFSVertex> {
    public static final int INFINITY = Integer.MAX_VALUE;
    Vertex src;

    // Class to store information about vertices during BFS
    public static class BFSVertex implements Factory {

    	boolean seen;
	Vertex parent;
	int distance;  // distance of vertex from source
	public BFSVertex(Vertex u) {
	    seen = false;
	    parent = null;
	    distance = INFINITY;
	}
	public BFSVertex make(Vertex u) { return new BFSVertex(u); }
    }

    // code to initialize storage for vertex properties is in GraphAlgorithm class
    public BFSOO(Graph g) {
	super(g, new BFSVertex(null));
    }


    // getter and setter methods to retrieve and update vertex properties
    public boolean getSeen(Vertex u) {
	return get(u).seen;
    }

    public void setSeen(Vertex u, boolean value) {
	get(u).seen = value;
    }

    public Vertex getParent(Vertex u) {
	return get(u).parent;
    }

    public void setParent(Vertex u, Vertex p) {
	get(u).parent = p;
    }

    public int getDistance(Vertex u) {
	return get(u).distance;
    }

    public void setDistance(Vertex u, int d) {
	get(u).distance = d;
    }

    public void initialize(Vertex src) {
	for(Vertex u: g) {
	    setSeen(u, false);
	    setParent(u, null);
	    setDistance(u, INFINITY);
	}
	setDistance(src, 0);
    }

    public void setSource(Vertex src) {
	this.src = src;
    }

    public Vertex getSource() {
	return this.src;
    }
    
    // Visit a node v from u
    void visit(Vertex u, Vertex v) {
	setSeen(v, true);
	setParent(v, u);
	setDistance(v, getDistance(u)+1);
    }

    public void bfs(Vertex src) {
	setSource(src);
	initialize(src);

	Queue<Vertex> q = new LinkedList<>();
	q.add(src);
	setSeen(src, true);

	while(!q.isEmpty()) {
	    Vertex u = q.remove();
	    for(Edge e: g.incident(u)) {
		Vertex v = e.otherEnd(u);
		if(!getSeen(v)) {
		    visit(u,v);
		    q.add(v);
		}
	    }
	}
    }
    
    // Run breadth-first search algorithm on g from source src
    public static BFSOO breadthFirstSearch(Graph g, Vertex src) {
	BFSOO b = new BFSOO(g);
	b.bfs(src);
	return b;
    }

    public static BFSOO breadthFirstSearch(Graph g, int s) {
	return breadthFirstSearch(g, g.getVertex(s));
    }








	private static List<Vertex> isGraphContainsOddEdge(Graph g, boolean[] visited) {
		Edge oddEdge =null;
    	List<Vertex> oddCycleList = new ArrayList<>();
    	for(Vertex v:g) {
          if(visited[v.getName()]==false) {
			  BFSOO b = breadthFirstSearch(g, v);
			  System.out.println("vertex is " + v.getName());
			  visited[v.getName()] = true;
			 // List<Edge> edges = (List<Edge>) g.outEdges(v);
			 List<Edge> edgesArray= Arrays.asList(g.getEdgeArray());
            for(int i=0;i<edgesArray.size();i++)
				System.out.println("edges array"+edgesArray.get(i));
//			  System.out.println("edges is " + edges);
			  for (int i = 0; i < edgesArray.size(); i++) {
				  System.out.println("vertex from" + edgesArray.get(i).from);
				  System.out.println("vertex to " + edgesArray.get(i).to);
				  if (b.getDistance(edgesArray.get(i).from) != INFINITY || b.getDistance(edgesArray.get(i).to) != INFINITY){
//                 if(b.getDistance(edgesArray.get(i).from)!=INFINITY)
//				    visited[edgesArray.get(i).from.getName()] = true;
//				 if (b.getDistance(edgesArray.get(i).to)!=INFINITY)
//                      visited[edgesArray.get(i).to.getName()] = true;
					  System.out.println("distance " + b.getDistance(edgesArray.get(i).from));
				  System.out.println(("distance to " + b.getDistance(edgesArray.get(i).to)));

				  if (b.getDistance(edgesArray.get(i).from) == b.getDistance(edgesArray.get(i).to)) {
					  oddEdge = edgesArray.get(i);
					  //System.out.println("odd edge is " + oddEdge);
					  oddCycleList = oddCycle(b, oddEdge);
					  return oddCycleList;
				  }
			  }
			  }
		  }
		}

		return oddCycleList;
	}

	private static List<Vertex> oddCycle(BFSOO b, Edge oddEdge) {
		if (oddEdge == null)
			System.out.println("does not contain odd cycle");

		System.out.println("odd edge is " + oddEdge);
//			   System.out.println(oddEdge);
//		System.out.println("oddEgde from " + oddEdge.from);
//		System.out.println("oddEdge to" + oddEdge.to);
		Vertex uParent = b.getParent(oddEdge.from);
		Vertex vParent = b.getParent(oddEdge.to);

		System.out.println("uParent " + uParent);
		System.out.println("vParent" + vParent);
		List<Vertex> oddCycleList = new ArrayList();
		if(vParent.equals(uParent))
		{
			oddCycleList.add(oddEdge.from);
			oddCycleList.add(oddEdge.to);
			oddCycleList.add(uParent);
			return  oddCycleList;
		}
		oddCycleList.add(oddEdge.from);
		oddCycleList.add(oddEdge.to);
		oddCycleList.add(uParent);
		oddCycleList.add(vParent);
		if (vParent.equals(uParent)) {
			for (int i = 0; i < oddCycleList.size(); i++) {
				System.out.println(oddCycleList.get(i));
			}
		}
		while (!(uParent.equals(vParent))) {
			uParent = b.getParent(uParent);
			vParent = b.getParent(vParent);
			if(!(uParent.equals(vParent))) {
				oddCycleList.add(uParent);
				oddCycleList.add(vParent);
			}
			else
				oddCycleList.add(uParent);
				System.out.println("in while loop");
		}
		//System.out.println("odd cycle list ");
//		for (int i = 0; i < oddCycleList.size(); i++)
//			System.out.print(oddCycleList.get(i));
		   return oddCycleList;
	}


	public static void main(String[] args) throws Exception {
	//String string = "6 6   1 2 3   1 3 9   1 5 5   2 4 6   3 4 8   4 5 7 1";
		String string = "8 7   1 2 3   2 4 9   1 3 5   4 5 7   6 7 7   7 8 8   8 6 5 1";
		//String string = "7 5   1 2 3   2 4 9   1 3 5   4 5 7   6 7 1 1";
	//String string = "5 5   1 2 6   1 3 4   1 5 8   2 4 6   4 5 6   3 4 8 1";
	Scanner in;
	// If there is a command line argument, use it as file from which
	// input is read, otherwise use input from string.
	in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);
	// Read graph from input
	Graph g = Graph.readGraph(in,false);

	int s = in.nextInt();
	System.out.println("s is "+s);
   boolean isOdd=false;
   System.out.println("number of vertices "+g.n);
   boolean[] visited = new boolean[g.n+1];

	// Call breadth-first search
	BFSOO b = breadthFirstSearch(g, s);


		List<Vertex> graphContainsOddEdge = isGraphContainsOddEdge(g,visited);
		if(graphContainsOddEdge.size()==0)
			System.out.println("There is no odd length cycle it is a bipartite graph");
		System.out.println(graphContainsOddEdge.size());
		System.out.println("oddlist is ");
        for (int i=0;i<graphContainsOddEdge.size();i++)
			System.out.print(graphContainsOddEdge.get(i));

		g.printGraph(false);

	System.out.println("Output of BFS:\nNode\tDist\tParent\n----------------------");
	for(Vertex u: g) {
	    if(b.getDistance(u) == INFINITY) {
		System.out.println(u + "\tInf\t--");
	    } else {
		System.out.println(u + "\t" + b.getDistance(u) + "\t" + b.getParent(u));
	    }
	}
    }


}

/* Sample run:
______________________________________________
aps180006.Graph: n: 7, m: 8, directed: true, Edge weights: false
1 :  (1,2) (1,3)
2 :  (2,4)
3 :  (3,4)
4 :  (4,5)
5 :  (5,1)
6 :  (6,7)
7 :  (7,6)
______________________________________________
Output of BFS:
Node	Dist	Parent
----------------------
1	0	null
2	1	1
3	1	1
4	2	2
5	3	4
6	Inf	--
7	Inf	--
*/
