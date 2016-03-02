package org.globaltester.testspecification.document.export;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.globaltester.base.resources.GtResourceHelper;
import org.globaltester.junit.JUnitHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ExporterTest {

	private File tempDir;
	
	@Before
	public void createFolders() throws IOException{
		tempDir = File.createTempFile("hjp", "");

		// create a directory instead of the file, java 7 can create temporary directories
		// directly
		tempDir.delete();
		tempDir.mkdir();
	}
	
	@Test
	public void testExportedFileHasContentXml() throws IOException, CoreException {
		String folder = "files" + File.separator + "SuccessfullExportFiles" + File.separator;

		String plugin = "org.globaltester.core.test";
		InputStream stylesheet = FileLocator.openStream(
				Platform.getBundle(plugin), new Path("/" + folder
						+ "stylesheet.xsl"), false);
		InputStream sourcesZip = FileLocator.openStream(
				Platform.getBundle(plugin), new Path("/" + folder
						+ "sources.zip"), false);
		InputStream testSpec = FileLocator.openStream(
				Platform.getBundle(plugin), new Path("/" + folder
						+ "testSpecification.gtspec"), false);

		File tempTestSpec = new File(tempDir, "testSpecification.gtspec");
		GtResourceHelper.copyFile(testSpec, tempTestSpec);
		File target = new File(tempDir, "target.odt");
		Exporter.export(target, tempTestSpec, stylesheet, sourcesZip, null);
		
		ZipFile result = new ZipFile(target);
		ZipEntry entry = result.getEntry("content.xml");
		
		// copy entry to tempfile, because size is not necessarily known in the zip entry
		File content = new File(tempDir, "content.xml");
		GtResourceHelper.copyFile(result.getInputStream(entry), content);
		result.close();
		
		assertTrue("zip entry does not exist", entry != null);
		assertTrue("content.xml is empty", content.length() > 0);
	}
	
	@After
	public void cleanup(){
		JUnitHelper.recursiveDelete(tempDir);
	}
}
