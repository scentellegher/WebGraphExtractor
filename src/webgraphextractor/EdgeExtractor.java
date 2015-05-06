package webgraphextractor;


import com.martiansoftware.jsap.JSAPException;
import it.unimi.dsi.logging.ProgressLogger;
import it.unimi.dsi.webgraph.ImmutableGraph;
import it.unimi.dsi.webgraph.LazyIntIterator;
import it.unimi.dsi.webgraph.NodeIterator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cent
 */
public class EdgeExtractor {     
    
    public static void main(String args[]) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, JSAPException, IOException {
        
        String basename = args[0];
        String urls_path = args[1];
        String outpath = args[2];
        
        ProgressLogger pl = new ProgressLogger();
        //load the graph
        ImmutableGraph graph = ImmutableGraph.loadOffline(basename, pl);
        NodeIterator nodeIterator = graph.nodeIterator();
        // get number of nodes
        pl.expectedUpdates = graph.numNodes();
        pl.start("Scanning...");
        // graphs are ordered by lexicographical URL ordering
        // node1 -> url1 ... etc etc
        //open url file
        File urls = new File(urls_path);
        BufferedReader br = new BufferedReader(new FileReader(urls));
        String line, hostname = null;
        //needed to store the url
        URL aURL = null;
        // create output file
        FileWriter fw = new FileWriter(outpath);
      
        int k = -1; // index for block_id
        String old_hostname = null;
        // wrong URL
        boolean wrongURL = false;
        //for each node
        for (int i = 0; i< graph.numNodes(); i++) {
            //get id
            int currentId = nodeIterator.nextInt();
            //get neighbors
            int degree = nodeIterator.outdegree();
            //get url
            line = br.readLine();
            // store the old hostname
            old_hostname = hostname;
            try {
                aURL = new URL(line);
            } catch (MalformedURLException ex) {
                //needed because some urls are malformed
                wrongURL=true;
                System.out.println("LINE: "+line);
                Pattern p = Pattern.compile("\\/\\/(.*?):");
                Matcher m = p.matcher(line);
                while(m.find())
                {
                    hostname=m.group(1);
                    System.out.println("HOSTNAME="+hostname);
                }   
            }
            // skip if the url is not well formatted
            if(!wrongURL){
                // from the url I need the hostname
                hostname = aURL.getHost();
            }
            wrongURL=false;
            // if the hostnames are different the node has a different domain
            if(!hostname.equals(old_hostname)) {
                k++;
            }

            LazyIntIterator succ = nodeIterator.successors();
            // write neighbors ids
            for(int j=0;j<degree;j++)
                // write on output file <block_id> <node1_id> <node2_id>
                fw.write(k +" "+currentId + " " + succ.nextInt() + "\n");
            pl.lightUpdate();
        }
        br.close();
        fw.close();
        pl.done();
    }
    
    public static void inOut() throws FileNotFoundException, IOException{
        File input = new File("/home/cent/Desktop/webgraph/india2004/block1");
        BufferedReader br = new BufferedReader(new FileReader(input));
        String line ="";
        String tmp[];
        int size;
        int vid;
        int in=0;
        int out=0;
        while((line = br.readLine())!=null){
            tmp = line.split(" ");
            size = Integer.parseInt(tmp[1]);
            for(int i=2; i< size+2; i++){
                vid = Integer.parseInt(tmp[i]);
                System.out.println("vid="+vid+" vid>0 && vid<679="+(vid>0 && vid<679));
                if(vid>0 && vid<679)
                    in++;
                else
                    out++;
            }
            System.out.println("");
        }
        System.out.println("in="+in + " out="+out);
    }
    
}







