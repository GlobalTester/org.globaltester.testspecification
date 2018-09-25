package org.globaltester.testspecification.document.export;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.globaltester.logging.legacy.logger.GtErrorLogger;
import org.globaltester.testspecification.Activator;

/**
 * This class provides facilities for exporting test specifications
 * @author mboonk
 *
 */
public class Exporter{
	
	public static final String EXTENSIONPOINT_ID = "org.globaltester.base.document.export.exportData";
	
	private static final String EXCEPTION_MESSAGE = "Error while creating ODT File.";
	
	/**
	 * This method creates an Open Office *.odt using a specification, stylesheet
	 * and an Open Office document skeleton zip file.
	 * 
	 * @param target Destination file
	 * @param testSpecification Specification file
	 * @param stylesheet Xsl stylesheet
	 * @param sourceZip Open Office document skeleton file
	 * @throws IOException
	 * @throws CoreException 
	 */
	public static void export(File target, File testSpecification, InputStream stylesheet, InputStream sourceZip, HashMap<String, Object> xslParams) throws IOException, CoreException{

		// for more sophisticated zip management TrueZip could be used
		ZipOutputStream zipOut = ZipHandler.append(new ZipInputStream(sourceZip), new FileOutputStream(target));
		ZipEntry entry = new ZipEntry("content.xml");
		// Prepare the Zip for writing the results
		zipOut.putNextEntry(entry);		
		
		// Use the static TransformerFactory.newInstance() method to instantiate
		// a TransformerFactory. The javax.xml.transform.TransformerFactory
		// system property setting determines the actual class to instantiate --
		// org.apache.xalan.transformer.TransformerImpl.
		TransformerFactory tFactory = TransformerFactory.newInstance(); //NOSONAR
		Transformer transformer;
		try { 

			// set the system id of the stylesheet to the specification root folder, to
			// resolve relative paths correctly
			StreamSource streamSource = new StreamSource(stylesheet);
			streamSource.setSystemId(testSpecification.getParentFile());
			// Use the TransformerFactory to instantiate a Transformer that will
			// work with the specified stylesheet. This method call also processes the
			// stylesheet into a compiled Templates object.
			transformer = tFactory.newTransformer(streamSource);

			if (xslParams != null) {
				for (Entry<String, Object> param : xslParams.entrySet()) {
					transformer.setParameter(param.getKey(), param.getValue());
				}
			}
			// Use the Transformer to apply the associated Templates object to
			// a XML document and write the output to a file .
			StreamSource specStreamSource = new StreamSource(testSpecification);
			
			transformer.transform(specStreamSource,
							new StreamResult(zipOut));
		} catch (TransformerException e) {
			GtErrorLogger.log(Activator.PLUGIN_ID, e);
			throw new CoreException(new Status(Status.ERROR,Activator.PLUGIN_ID, EXCEPTION_MESSAGE));
		} finally {
			zipOut.closeEntry();
			zipOut.close();
		}

		
	}
	
	


}