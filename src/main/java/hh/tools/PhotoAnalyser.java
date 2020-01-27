package hh.tools;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;

public class PhotoAnalyser {

    private static Logger LOG = LoggerFactory.getLogger(PhotoAnalyser.class);

    File directory;

    public static PhotoAnalyser build(Path directory) throws FileNotFoundException {
        File fPath = directory.toFile();
        PhotoAnalyser analyser;
        if (fPath.exists() && fPath.isDirectory()) {
            analyser = new PhotoAnalyser();
            analyser.directory = fPath;
        } else {
            throw new FileNotFoundException(fPath.getAbsolutePath());
        }
        return analyser;
    }

    void searchImagesCopy(){
        showFileAndDirectory(directory,0);
    }

    private List<String> showFileAndDirectory(File dirPath, int intend) {
        String dirIntend=getTextIntend(intend);

        /*Print Directory Name*/
        LOG.debug(dirIntend+dirPath.getName()+"/");

        File[] files=dirPath.listFiles();
        /*Recursion for Directories*/
        for (File file : files) {
            if(file.isDirectory()){
                showFileAndDirectory(file, intend+1);
            }
        }

        /*Print File Name*/
        for (File file : files) {
            if(file.isFile()){
                String fileIntend=getTextIntend(intend+2);
                LOG.debug(fileIntend + file.getName());
            }
        }
        return null;
    }

    private String getTextIntend(int intend){
        StringBuilder builder=new StringBuilder();
        for (int i = 0; i < intend; i++) {
            builder.append(" |--");
        }
        return builder.toString();
    }
}
