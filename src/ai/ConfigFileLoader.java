package ai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class ConfigFileLoader {

	/**
	 * Config File format: level:hiddenLayerSize:learningRate:numberOfhiddenLayers
	 * @param f
	 */
	public void loadConfigFile(String name) {
		try {
			File f = new File(name) ;
			this.mapConfig = new HashMap<String, Config>();
			this.intToMap = new ArrayList<String>();
			if(f.exists() && f.isFile()) {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
				String s = "";
				int iteration = 0;
				while( (s = br.readLine()) != null ) {
					String[] t = s.split(":");
					if ( t.length == 4 ) {
						String level = t[0];
						int hiddenLayerSize = Integer.parseInt(t[1]);
						double learningRate = Double.parseDouble(t[2]);
						int numberOfhiddenLayers = Integer.parseInt(t[3]);
						//
						Config c = new Config(level, hiddenLayerSize, numberOfhiddenLayers, learningRate);
						mapConfig.put(level, c);
						intToMap.add(level);
					}
				}
				br.close();
			}
		}
		catch (Exception e) {
			System.out.println("Config.loadConfigFile()");
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public Config get(String level) {
		try {
			if (mapConfig != null && mapConfig.containsKey(level) )
				return mapConfig.get(level);
		} 
		catch (Exception e) {
			System.out.println("ConfigFileLoader.get()");
			e.printStackTrace();
			System.exit(-1);
		}
		return null ;
	}
	
	public HashMap<String, Config> getAll() {
		return mapConfig;
	}
	
	public String getLvlFromInt(int pos)
	{
		return intToMap.get(pos);
	}
	
	public Config getConfFromInt(int pos)
	{
		return mapConfig.get(intToMap.get(pos));
	}
	
	@Override
	public String toString() {
		return "ConfigFileLoader [mapConfig=" + mapConfig + "]";
	}

	public int size() { return mapConfig.size(); }
	//FIELDS ...
	private HashMap<String, Config> mapConfig ;
	private ArrayList<String> intToMap;
}
