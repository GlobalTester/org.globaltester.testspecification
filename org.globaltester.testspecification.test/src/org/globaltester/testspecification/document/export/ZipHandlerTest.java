package org.globaltester.testspecification.document.export;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.eclipse.core.runtime.Platform;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;

public class ZipHandlerTest {

	File tempFile;
	
	@Before
	public void prepare() throws IOException{
		 tempFile = File.createTempFile("zipHandlerTestFile", "zip");
	}
	
	@Test
	public void testWithZipFile() throws IOException{
		FileOutputStream out = new FileOutputStream(tempFile);
		Bundle curBundle = Platform.getBundle("org.globaltester.core.test");
		URL archive = curBundle.getEntry("files/testArchive.zip");
		
		ZipInputStream input = null;
		ZipOutputStream zipOut = null;
		
		try {
			input = new ZipInputStream(archive.openStream());
			zipOut = ZipHandler.append(input, out);
		} finally {
			if(input != null) {
		input.close();
			}
			
			if(zipOut != null) {
		zipOut.close();
			}
		}

		ZipFile zip = null;
		
		try {
			zip = new ZipFile(tempFile);
		assertTrue("Zip should contain testFile", !zip.getEntry("testFile").isDirectory());
		assertTrue("Zip should contain testFolder", zip.getEntry("testFolder/").isDirectory());
		assertTrue("Zip should contain testFolder/testFile", !zip.getEntry("testFolder/testFile").isDirectory());
		} finally {
			if(zip != null) {
				zip.close();
			}
		}
	}
	
	@After
	public void cleanUp() throws IOException{
		tempFile.delete();
	}
}
