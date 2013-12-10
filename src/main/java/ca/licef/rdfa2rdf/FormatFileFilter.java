package ca.licef.rdfa2rdf;

import javax.swing.filechooser.FileFilter;
import java.io.File;


/**
 * Created with IntelliJ IDEA.
 * User: amiara
 * Date: 13-12-10
 */
public class FormatFileFilter extends FileFilter {

    String name;
    String extension;

    public FormatFileFilter(String name, String extension) {
        this.name = name;
        this.extension = extension;
    }

    @Override
    public boolean accept(File f) {
        String ext;
        if (f.isDirectory()) {
            return true;
        }

        ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }

        return extension.equals(ext);
    }

    public String getDescription() {
        return name + " files";
    }
}
