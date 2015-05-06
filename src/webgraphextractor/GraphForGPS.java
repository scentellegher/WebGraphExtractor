package webgraphextractor;

//
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cent
 */
public class GraphForGPS {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        Map<Integer, Integer> vertices = new TreeMap<Integer, Integer>();
        
//        File input = new File("/home/ubuntu/datasets/india2004/indiagps");
//        FileWriter fw = new FileWriter("/home/ubuntu/datasets/india2004/india2004.gps.txt");
//        FileWriter fw2 = new FileWriter("/home/ubuntu/datasets/india2004/map");
//        File input = new File("/home/ubuntu/datasets/webbase/webbase4gps");
//        FileWriter fw = new FileWriter("/home/ubuntu/datasets/webbase/webbase2001.gps.txt");
//        FileWriter fw2 = new FileWriter("/home/ubuntu/datasets/webbase/map");
//        File input = new File("/home/ubuntu/datasets/uk2002/uk20024gps");
        File input = new File("/home/ubuntu/datasets/arabia/arabia4gps");
//        File input = new File("/home/ubuntu/datasets/uk2005/uk20054gps");
//        FileWriter fw = new FileWriter("/home/ubuntu/datasets/uk2002/uk2002.gps.txt");
//        FileWriter fw2 = new FileWriter("/home/ubuntu/datasets/uk2002/map");
//        File input = new File("/home/ubuntu/datasets/uk2007-05/uk4gps");
//        FileWriter fw = new FileWriter("/home/ubuntu/datasets/uk2007-05/uk2007.gps.txt");
//        FileWriter fw2 = new FileWriter("/home/ubuntu/datasets/uk2007-05/map");
        BufferedReader br = new BufferedReader(new FileReader(input));
        String line ="";
        String []tmp;
        String nb ="";
        String []list;
        Integer key, value;
        int i=0;
        // line -> vid nb1 nb2 ... nbN
        while((line = br.readLine())!=null){
            tmp = line.split(" ");
            vertices.put(Integer.parseInt(tmp[0]),i);
//            fw2.write(tmp[0]+" "+i+"\n");
            i++;
            if(i%100000==0)
                System.out.println(i);
        }
        System.out.println("size="+vertices.size());
        br.close();
        BufferedReader br1 = new BufferedReader(new FileReader(input));
        FileWriter fw = new FileWriter("/home/ubuntu/datasets/arabia/arabia.gps.txt");
        FileWriter fw2 = new FileWriter("/home/ubuntu/datasets/arabia/map");
//        FileWriter fw = new FileWriter("/home/ubuntu/datasets/uk2005/uk2005.gps.txt");
//        FileWriter fw2 = new FileWriter("/home/ubuntu/datasets/uk2005/map");
        for (Map.Entry<Integer, Integer> entry : vertices.entrySet()) {
            fw2.write(entry.getKey()+" "+entry.getValue()+"\n");            
        }
        
        i=0;
        while((line = br1.readLine())!=null){
            tmp = line.split(" ");
            for(int j=0; j<tmp.length ;j++){
//                System.out.print(vertices.get(Integer.parseInt(tmp[j]))+" ");
                fw.write(vertices.get(Integer.parseInt(tmp[j]))+" ");
            }
//            System.out.println("\n");
            fw.write("\n");
        }
        br1.close();
        fw.close();
        fw2.close();
    }
}
