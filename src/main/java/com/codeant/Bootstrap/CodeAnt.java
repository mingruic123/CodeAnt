package main.java.com.codeant.Bootstrap;

import main.java.com.codeant.index.IndexService;
import main.java.com.codeant.index.IndexTask;

/**
 * Entry point of CodeAnt when starting from command line
 */
public class CodeAnt {
    public static void main(String[] args){
        String usage = "java -jar codeant.jar"
                +      "[-f] index/search";
        String indexPath = "index";
        String docsPath = null;
        boolean create = true;
        String function = "";
        for(int i = 0; i < args.length; i++){
            if("-index".equals(args[i])){
                indexPath = args[i+1];
            }else if("-docs".equals(args[i])) {
                docsPath = args[i+1];
                i++;
            }else if("-update".equals(args[i])){
                create = false;
                i++;
            }else if("-f".equals(args[i])){
                function = args[i+1];
                i++;
            }
        }

        if(!function.equals("index") || !function.equals("search")){
            System.err.println("Usage: " + usage);
            System.exit(1);
        }

        /*
        Start a new task to index
         */
        if(function.equals("index")){
            IndexService indexService = IndexService.getIndexServiceInstance(indexPath);
            if(indexService.isStarted()){
                IndexTask newTask = new IndexTask(indexService);
                try {
                    newTask.indexItem(docsPath);
                }catch(Exception e){
                    System.err.println("");
                }
            }
            if(indexService.isStopped()){
                indexService.start();
            }
        }



    }
}
