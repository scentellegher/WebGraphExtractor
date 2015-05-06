package webgraphextractor;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cent
 */
public class RevertNodes {
    public static void main(String[] args) throws IOException {
        Map<Integer, Integer> vertices = new HashMap<Integer, Integer>();
        
//        File input = new File("/home/ubuntu/datasets/india2004/map");
//        FileWriter fw = new FileWriter("/home/ubuntu/datasets/india2004/pagerankGPS");
        File input = new File("/home/ubuntu/datasets/ukSmall/map");
//        File input = new File("/home/ubuntu/datasets/webbase/map");
//        FileWriter fw = new FileWriter("/home/ubuntu/datasets/webbase/pagerankGPS");
//        File input = new File("/home/ubuntu/datasets/uk2007-05/map");
//        FileWriter fw = new FileWriter("/home/ubuntu/datasets/uk2007-05/pagerankGPS");
        BufferedReader br = new BufferedReader(new FileReader(input));
        String line ="";
        String []tmp;
        String []list;
        Integer key, value;
        int i=0;
        // line -> old_id new_id
        while((line = br.readLine())!=null){
            tmp = line.split(" ");
//            System.out.println("map: "+tmp[1] + "->"+tmp[0]);
            vertices.put(Integer.parseInt(tmp[1]),Integer.parseInt(tmp[0]));//reverse
//            fw2.write(tmp[0]+" "+i+"\n");
            i++;
            if(i%100000==0)
                System.out.println(i);
        }
        System.out.println("size="+vertices.size());
        br.close();
//        input = new File("/home/ubuntu/datasets/webbase/pasted");
        FileWriter fw = new FileWriter("/home/ubuntu/datasets/ukSmall/pagerankGPS");
        input = new File("/home/ubuntu/datasets/ukSmall/pasted");
//        input = new File("/home/ubuntu/datasets/uk2007-05/pasted");
//        input = new File("/home/ubuntu/outputSink/pasted");
        BufferedReader br1 = new BufferedReader(new FileReader(input));
        i=0;
        while((line = br1.readLine())!=null){
            tmp= line.split(" ");
//            System.out.println("write: "+ Integer.parseInt(tmp[0].trim()) + " " + tmp[1]);
            fw.write(vertices.get(Integer.parseInt(tmp[0].trim()))+" "+tmp[1]+"\n");
        }
        
       fw.close();
       br1.close();
//        fw2.close();
    }
    
}
