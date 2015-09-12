package acceptance.com.drfa.messaging;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.File;
import java.util.Collection;

public class FileUtil {


    public void ensureNoReconciliationReportExists(String dirName) {
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

    public void makeDirectoryIfNotExist(String dirName) {
        File outputDir = new File(dirName);
        if (!outputDir.exists()) {
            outputDir.mkdir();
        }
    }

}
