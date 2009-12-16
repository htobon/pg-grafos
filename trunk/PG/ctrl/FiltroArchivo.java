package ctrl;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FiltroArchivo extends FileFilter {

	public boolean accept(File f) {

		if (f.isDirectory()) {
			return true;
		}

		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			String extension = s.substring(i + 1).toLowerCase();
			if (extension.equals(".grafo")) {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

	public String getDescription() {
		return "Sólo archivos .grafo";
	}

}
