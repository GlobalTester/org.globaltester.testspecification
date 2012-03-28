package org.globaltester.testspecification.ui.wizards;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.globaltester.core.resources.GtResourceHelper;

public class OOExporter {
	private static final String FOLDER_PREFIX = "hjp_gt_";
	private static final String TMP_ODT = "tmp.odt";
	
	public static void export(File target, File source) throws IOException{
		String tempdir = System.getProperty("java.io.tmpdir");
		//tempdir = System.getProperty("user.dir");
		// create random foldername
		
		int randomPart = (int) Math.ceil(Math.random()*1000);
		String tempFolderName = FOLDER_PREFIX + randomPart;
		// create temporary folder
		File temp = new File(tempdir + tempFolderName);
		//File temp = new File(tempdir + File.separator);
		temp.mkdir();
		File gen = new File(temp, "gen");
		File input = new File(temp, "input");
		File stylesheetF = new File(temp, "stylesheets");
		File stylesheet = new File(stylesheetF, "testspec_OO.xsl");
		gen.mkdir();
		File content = new File(gen, "content.xml");
		File specification = new File(input, "testSpecification.xml");
		
		// copy files from project
		
		GtResourceHelper.copyPluginContent2TempLocation("org.globaltester.testspecification.ui", temp);
		GtResourceHelper.copyFiles(new File(source.getParent()), input);

		// convert xml

		// Use the static TransformerFactory.newInstance() method to instantiate
		// a TransformerFactory. The javax.xml.transform.TransformerFactory
		// system property setting determines the actual class to instantiate --
		// org.apache.xalan.transformer.TransformerImpl.
		TransformerFactory tFactory = TransformerFactory.newInstance();

		Transformer transformer;
		try { 
			// Use the TransformerFactory to instantiate a Transformer that will
			// work with the stylesheet you specify. This method call also processes the
			// stylesheet into a compiled Templates object.

			StreamSource streamSource = new StreamSource(
					stylesheet);
			transformer = tFactory.newTransformer(streamSource);
			// FIXME MBK find reason for strange path resolving behaviour
			MyURIResolver uriResolver = new MyURIResolver();
			uriResolver.input = specification;
			transformer.setURIResolver(uriResolver);
			
			// Use the Transformer to apply the associated Templates object to
			// an XML document and write the output to a file .
			StreamSource specStreamSource = new StreamSource(specification);
			transformer.transform(specStreamSource,
							new StreamResult(
									content));
			//System.setProperty("user.dir", oldWorkingDir);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// run ant task (copy and zip files to odt)

		Project project = new Project();
		project.init();

		File buildFile = new File(temp, "build.xml");
		ProjectHelper.configureProject(project, buildFile);
		project.setProperty("oo.src.dir", "OO_sources");
		project.setProperty("oo.src.gen.dir", "gen");
		project.setProperty("oo.dest.dir", "oo_files");
		project.setProperty("oo.dest.name", TMP_ODT);
		project.setProperty("oo.target", target.toString());
		
		try {
			project.executeTarget("generateOO");
		} catch (BuildException e){
			// TODO errorlogging
		}
	}
}