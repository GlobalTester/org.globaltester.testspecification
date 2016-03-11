package org.globaltester.testspecification.document.export;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.globaltester.base.resources.GtResourceHelper;


public class ZipHandler {
	/**
	 * 
	 * Returns an open ZipOutputStream already containing the contents of the source zip file
	 * 
	 * @param source
	 * @param target
	 * @return
	 * @throws IOException 
	 */
	public static ZipOutputStream append(ZipInputStream source, OutputStream target) throws IOException{
		ZipOutputStream targetStream = new ZipOutputStream(target);
        
		// read source.zip and write to append.zip
        // first, copy contents from existing zip
		ZipEntry zipEntry = null;
		while ((zipEntry = source.getNextEntry()) != null){
			// if the old zipentry is used, problems with different compressed sizes occur
			zipEntry = new ZipEntry(zipEntry.getName());
			
			targetStream.putNextEntry(zipEntry);
			if (!zipEntry.isDirectory()) {
                GtResourceHelper.copyStream(source, targetStream);
            }
            targetStream.closeEntry();
		}

        // close
        source.close();
        return targetStream;
	}
}
