import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

class PowerLine {
	String cityA;
	String cityB;

	public PowerLine(String cityA, String cityB) {
		this.cityA = cityA;
		this.cityB = cityB;
	}
}

class Edge{
	ArrayList<Integer> arr;
	ArrayList<Integer> ind;

	public Edge(){
		arr = new ArrayList<>();
		ind = new ArrayList<>();
	}

	public Edge(ArrayList<Integer> arr, ArrayList<Integer> ind){
		this.arr = arr;
		this.ind = ind;
	}
}

// Students can define new classes here

public class PowerGrid {
	int numCities;
	int numLines;
	String[] cityNames;
	PowerLine[] powerLines;

	// Students can define private variables here
	private ArrayList<Edge> ed;
	private int[] vis;
	private int[] dep;
	private int[] hp;
	public HashMap<String, Integer> mp = new HashMap<>();

	public PowerGrid(String filename) throws Exception {
		File file = new File(filename);
		BufferedReader br = new BufferedReader(new FileReader(file));
		numCities = Integer.parseInt(br.readLine());
		numLines = Integer.parseInt(br.readLine());
		cityNames = new String[numCities];
		ed = new ArrayList<>();
		vis = new int[numCities+1];
		dep = new int[numCities+1];
		hp = new int[numCities+1];
		for (int i = 0; i < numCities; i++) {
			cityNames[i] = br.readLine();
			mp.put(cityNames[i], i);
			ed.add(new Edge());
			vis[i] = 0;
			dep[i] = 1000*1000*1000;
		}
		ed.add(new Edge());
		vis[numCities]=0;
		dep[numCities] = 1000*1000*1000;
		powerLines = new PowerLine[numLines];
		for (int i = 0; i < numLines; i++) {
			String[] line = br.readLine().split(" ");
			powerLines[i] = new PowerLine(line[0], line[1]);
		}

		// TO be completed by students
		for (int i=0; i<numLines; i++){
			String cA = powerLines[i].cityA, cB = powerLines[i].cityB;
			ed.get(mp.get(cA)).arr.add(mp.get(cB));
			ed.get(mp.get(cA)).ind.add(i);
			ed.get(mp.get(cB)).arr.add(mp.get(cA));
			ed.get(mp.get(cB)).ind.add(i);
		}
	}

	public int min(int a, int b){
		return (a<b ? a : b);
	}

	public void dfs(int i, int p, ArrayList<PowerLine> ans){
		vis[i]++;
		hp[i] = dep[i];
		System.out.println(i+" cur ");
		for (int cur=0; cur<ed.get(i).arr.size(); cur++){
			int x = ed.get(i).arr.get(cur);
			if (vis[x]==0){
				dep[x] = dep[i]+1;
				dfs(x, i, ans);
				if (hp[x]>dep[i]){
					System.out.println(x+ " can "+i+" "+hp[x]+" "+hp[i]+ " "+dep[i]);
					ans.add(powerLines[ed.get(i).ind.get(cur)]);
				}
			}
			if (x!=p){
				hp[i] = min(hp[i], hp[x]);
			}
		}
		hp[i] = min(hp[i], dep[i]);
		System.out.println(ans.size());
	}

	public ArrayList<PowerLine> criticalLines() {
		/*
		 * Implement an efficient algorithm to compute the critical transmission lines
		 * in the power grid.
		 
		 * Expected running time: O(m + n), where n is the number of cities and m is the
		 * number of transmission lines.
		 */
		ArrayList<PowerLine> hu = new ArrayList<>();
		dep[0] = 0;
		for (int i=0; i<numCities; i++){
			System.out.print(i+"  --  ");
			for (int cur=0; cur<ed.get(i).arr.size(); cur++){
				int x = ed.get(i).arr.get(cur);
				System.out.print(x+" ");
			}
			System.out.println();
		}
		dfs(0, -1, hu);
		for (int x:hp){
			System.out.print(x+" ");
		}
		System.out.println();
		for (int x:dep){
			System.out.print(x+" ");
		}
		System.out.println();

		return hu;
	}

	public void preprocessImportantLines() {
		/*
		 * Implement an efficient algorithm to preprocess the power grid and build
		 * required data structures which you will use for the numImportantLines()
		 * method. This function is called once before calling the numImportantLines()
		 * method. You might want to define new classes and data structures for this
		 * method.
		 
		 * Expected running time: O(n * logn), where n is the number of cities.
		 */
		return;
	}

	public int numImportantLines(String cityA, String cityB) {
		/*
		 * Implement an efficient algorithm to compute the number of important
		 * transmission lines between two cities. Calls to numImportantLines will be
		 * made only after calling the preprocessImportantLines() method once.
		 
		 * Expected running time: O(logn), where n is the number of cities.
		 */
		return 0;
	}
}