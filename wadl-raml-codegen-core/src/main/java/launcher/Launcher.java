package launcher;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.digester.PathCallParamRule;
import org.mulesoft.raml.builder.RamlBuilder;
import org.mulesoft.web.app.model.ApplicationModel;
import org.raml.emitter.RamlEmitterV2;
import org.raml.model.Raml2;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.wadl.model.builder.ApplicationBuilder;
import org.wadl.model.builder.BasicPathResolver;

public class Launcher {
    
    public static void main(String[] args){
        
        HashMap<String,String> argsMap = parseArgs(args);
        
        String inputFilePath = argsMap.get("input");        
        String outputFilePath = argsMap.get("output");
        
        File inputFile = new File(inputFilePath);
        File outputFile = new File(outputFilePath);
        
        try {
            process(inputFile, outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void process(File inputFile, File outputFile) throws Exception {
        
    	BasicPathResolver pathResolver = new BasicPathResolver(inputFile.getParentFile());
    	
        ApplicationBuilder appBuilder = new ApplicationBuilder();
        appBuilder.setPathResolver(pathResolver);
        
        RamlBuilder ramlBuilder = new RamlBuilder();
        
        Document document = buildDocument(inputFile);
        Element element = document.getDocumentElement();
        
        ApplicationModel app = appBuilder.buildApplication(element);
        Raml2 raml = ramlBuilder.buildRaml(app);
        
        RamlEmitterV2 emmitter = new RamlEmitterV2();
        emmitter.setSingle(true);
        String dump = emmitter.dump(raml);
        
        saveFile(dump,outputFile);
    }

    private static void saveFile(String dump, File file) throws Exception {
        
        if(!file.exists()){
        	file.getParentFile().mkdirs();
            file.createNewFile();
        }
        
        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        bos.write(dump.getBytes("UTF-8"));
        bos.close();
    }
    
    private static HashMap<String, String> parseArgs(String[] args) {
        HashMap<String,String> map = new HashMap<String, String>();
        for(int i = 0 ; i < args.length ; i+=2){
            String key = args[i];
            if(key.startsWith("-")){
                key=key.substring(1);
            }
            if(i<args.length-1){
                String value = args[i+1];
                map.put(key, value);
            }
        }
        return map;
    }
    

    private static Document buildDocument(File inputFile) throws Exception {
        
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        Document document = dBuilder.parse(inputFile);
        return document;
    }

}
