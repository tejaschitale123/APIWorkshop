package api.workshop.advanced;

import java.io.File;
import java.util.Base64;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

public class FileHelper {
    @SneakyThrows
    public static String getFileContent (final String path) {
        final byte[] fileContent = FileUtils.readFileToByteArray (new File (path));
        return Base64.getEncoder ()
            .encodeToString (fileContent);
    }

    private FileHelper () {
        // Helper class.
    }
}
