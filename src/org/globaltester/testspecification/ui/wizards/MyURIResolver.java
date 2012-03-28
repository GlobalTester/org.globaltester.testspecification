package org.globaltester.testspecification.ui.wizards;

import java.io.File;
import java.io.FilenameFilter;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

public class MyURIResolver implements URIResolver {

	public File input;

	@Override
	public Source resolve(String href, String base) throws TransformerException {		
		Source result = new StreamSource(search(input.getParentFile(), href));
		return result;
	}

	private File search(File root, String path) {
		
		// get files in root and get rid of .something files
		File[] files = root.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				if (name.matches("\\..*")){
					return false;
				}
				return true;
			}
		});
		
		// get current file/folder name and rest of the path
		String name = path;
		String rest = "";
		if (path.contains("/")) {
			name = path.replaceAll("/.*", "");
			rest = path.substring(path.indexOf("/") +1, path.length());
		}
		
		// recursively search for the first occurence of the path
		if (files != null) {
			for (File file : files) {
				if (file.getName().equals(name)) {
					if (rest.length() > 0) {

						return search(file, rest);
					}
					return file;
				} else {
					File result =search(file, path); 
					if (result != null){
						return result;
					}
				}
			}
		}

		return null;
	}

}
