package ctrl;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FiltroArchivo extends FileFilter {

	public boolean accept(File f) {
		return (f.getName().endsWith(".grafo") || f
				.isDirectory());
	}

	public String getDescription() {
		return "Sólo archivos .grafo";
	}

}
