package webgraphextractor;


import com.martiansoftware.jsap.JSAPException;
import it.unimi.dsi.logging.ProgressLogger;
import it.unimi.dsi.webgraph.ImmutableGraph;
import it.unimi.dsi.webgraph.LazyIntIterator;
import it.unimi.dsi.webgraph.NodeIterator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author cent
 */
public class ExtractGraph {

    public static void main(String arg[]) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, JSAPException, IOException {
        ProgressLogger pl = new ProgressLogger();
        //load the graph
//        ImmutableGraph graph = ImmutableGraph.loadOffline("/home/ubuntu/datasets/india2004/in-2004-nat", pl);
//        ImmutableGraph graph = ImmutableGraph.loadOffline("/home/ubuntu/datasets/webbase/webbase-2001-nat", pl);
        ImmutableGraph graph = ImmutableGraph.loadOffline("/home/ubuntu/datasets/arabia/arabic-2005-nat", pl);
//        ImmutableGraph graph = ImmutableGraph.loadOffline("/home/ubuntu/datasets/uk2005/uk-2005-nat", pl);
//        ImmutableGraph graph = ImmutableGraph.loadOffline("/home/ubuntu/datasets/uk2002/uk-2002-nat", pl);
//        ImmutableGraph graph = ImmutableGraph.loadOffline("/home/ubuntu/datasets/uk2007-05/uk-2007-05-nat", pl);
        // create a node iterator
        NodeIterator nodeIterator = graph.nodeIterator();
        // get number of nodes
        pl.expectedUpdates = graph.numNodes();
        pl.start("Scanning...");
        // graphs are ordered by lexicographical URL ordering
        // node1 -> url1 ... etc etc
        //open url file
//        File urls = new File("/home/ubuntu/datasets/india2004/in-2004-nat.urls");
//        File urls = new File("/home/ubuntu/datasets/webbase/webbase-2001-nat.urls");
        File urls = new File("/home/ubuntu/datasets/arabia/arabic-2005-nat.urls");
//        File urls = new File("/home/ubuntu/datasets/uk2005/uk-2005-nat.urls");
//        File urls = new File("/home/ubuntu/datasets/uk2002/uk-2002-nat.urls");
//        File urls = new File("/home/ubuntu/datasets/uk2007-05/uk-2007-05-nat.urls");

        BufferedReader br = new BufferedReader(new FileReader(urls));
        String line, hostname = null;
        //needed to store the url
        URL aURL = null;
        // create output file
//        FileWriter fw = new FileWriter("/home/ubuntu/datasets/india2004/in2004.blogel.txt");
//        FileWriter fw2 = new FileWriter("/home/ubuntu/datasets/india2004/indexes.txt");
//        FileWriter fw = new FileWriter("/home/ubuntu/datasets/webbase/webbase2001.blogel.txt");
//        FileWriter fw2 = new FileWriter("/home/ubuntu/datasets/webbase/indexes.txt");
        FileWriter fw = new FileWriter("/home/ubuntu/datasets/arabia/arabia.blogel.txt");
        FileWriter fw2 = new FileWriter("/home/ubuntu/datasets/arabia/indexes.txt");
//        FileWriter fw = new FileWriter("/home/ubuntu/datasets/uk2005/uk2005.blogel.txt");
//        FileWriter fw2 = new FileWriter("/home/ubuntu/datasets/uk2005/indexes.txt");
//        FileWriter fw = new FileWriter("/home/ubuntu/datasets/uk2002/uk2002.blogel.txt");
//        FileWriter fw2 = new FileWriter("/home/ubuntu/datasets/uk2002/indexes.txt");
//        FileWriter fw = new FileWriter("/home/ubuntu/datasets/uk2007-05/uk2007.blogel.txt");
//        FileWriter fw2 = new FileWriter("/home/ubuntu/datasets/uk2007-05/indexes.txt");
        
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
                fw2.write(i+"\n");//create indexes.txt
            }
            // write on output file <node_id> <block_id> <out_degree>
            fw.write(currentId + "\t" + k + " " + degree + " ");
            LazyIntIterator succ = nodeIterator.successors();
            // write neighbors ids
            for(int j=0;j<degree;j++)
                fw.write(succ.nextInt() + " ");
            fw.write("\n");
            pl.lightUpdate();
        }
        br.close();
        fw.close();
        fw2.close();
        pl.done();
    }

}