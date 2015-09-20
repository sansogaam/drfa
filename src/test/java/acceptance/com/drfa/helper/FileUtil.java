package acceptance.com.drfa.helper;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Collection;

public class FileUtil {

    private static Logger LOG = Logger.getLogger(FileUtil.class);

    public void ensureNoFileExistsInDirectory(String dirName) {
        makeDirectoryIfNotExist(dirName);
        File outputDir = new File(dirName);
        try {
            FileUtils.cleanDirectory(outputDir);
        } catch (Exception e) {
        }
    }

    public Collection getAllFiles(String dirName) {
        File outputDir = new File(dirName);
        return FileUtils.listFiles(outputDir, new WildcardFileFilter("*"), new WildcardFileFilter("*"));
    }

    public boolean fileExists(String dirName) {
        return getAllFiles(dirName).size() > 1;
    }

    public void makeDirectoryIfNotExist(String dirName) {
        File outputDir = new File(dirName);
        if (!outputDir.exists()) {
            LOG.info(String.format("Creating directory %s", dirName));
            outputDir.mkdir();
        }else{
            LOG.info(String.format("Directory already exist %s", dirName));
        }
    }

}
