package org.wadl.model.builder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.mulesoft.web.app.model.AbstractElement;
import org.mulesoft.web.app.model.DocumentationModel;
import org.mulesoft.web.app.model.ResourceModel;
import org.raml.model.Action;
import org.raml.model.Resource;
import org.raml.model.Response;
import org.raml.model.parameter.AbstractParam;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Utils {
    
    public static List<Element> extractElements(Element parent, String tag){
        
        ArrayList<Element> list = new ArrayList<Element>();
        
        NodeList children = parent.getChildNodes();
        int length = children.getLength();
        for(int i = 0 ; i < length ;i++){            
            Node node = children.item(i);            
            if(!(node instanceof Element)){
                continue;
            }
            Element el = (Element) node;
            String tagName = el.getTagName();
            if(!tagName.equals(tag)){
                continue;
            }
            
            list.add(el);
        }        
        return list;        
    }

    public static String refinePath(String path) {
        
        if(!path.startsWith("/")){
            return "/" + path;
        }        
        return path;
    }

    public static void setParentUri(ResourceModel resource, String uri) {
        resource.setParenUri(uri);
        
        String path = resource.getPath();
        LinkedHashMap<String, ResourceModel> resources = resource.getResources();
        for(ResourceModel res : resources.values()){
            setParentUri(res, uri+path);
        }
        
    }
    
    public static void setDocumentation(AbstractElement element, Resource resource) {
    	if (element.getDoc() == null)
    		return;
        String content = element.getDoc().getContent();
        if(content.trim().isEmpty()){
            return;
        }
        resource.setDescription(content);
    }
    

    public static void setDocumentation(AbstractElement element, Action action) {
        DocumentationModel doc = element.getDoc();
        if(doc==null){
        	return;
        }
		String content = doc.getContent();
        if(content.trim().isEmpty()){
            return;
        }
        action.setDescription(content);
    }

    public static void setDocumentation(AbstractElement element, Response response) {
        String content = element.getDoc().getContent();
        if(content.trim().isEmpty()){
            return;
        }
        response.setDescription(content);
    }
    
    public static void setDocumentation(AbstractElement element, AbstractParam param) {
        String content = element.getDoc().getContent();
        if(content.trim().isEmpty()){
            return;
        }
        param.setDescription(content);
    }
    
    public static boolean isEmptyString(String str){
        return str == null || str.trim().isEmpty();
    }

	public static String stringToCamel(String rawString){
		rawString = rawString.trim();
		if (rawString.contains(" ") || rawString.contains("-") || rawString.contains("_")){
			String separator = " ";
			if(!rawString.contains(" "))
				separator = rawString.contains("-")?"-":"_";
			String [] segments = rawString.trim().toLowerCase().split(separator);
			StringBuilder camelCaseString = new StringBuilder();
			camelCaseString.append(segments[0]);
			for (int i = 1; i < segments.length; i++){
				String segment = segments[i];
				camelCaseString.append(segment.substring(0, 1).toUpperCase() + segment.substring(1));
			}
			return camelCaseString.toString();
		}
		else {
			rawString = rawString.substring(0, 1).toLowerCase() + rawString.substring(1);
			return rawString;
		}
	}
}
