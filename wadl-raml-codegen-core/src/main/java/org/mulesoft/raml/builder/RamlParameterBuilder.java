package org.mulesoft.raml.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mulesoft.web.app.model.ParameterModel;
import org.raml.model.ParamType;
import org.raml.model.parameter.AbstractParam;
import org.raml.model.parameter.FormParameter;
import org.raml.model.parameter.Header;
import org.raml.model.parameter.QueryParameter;
import org.wadl.model.builder.Utils;

public class RamlParameterBuilder {
    
    public FormParameter buildFormParameter(ParameterModel paramModel){
        FormParameter param = new FormParameter();
        extractData(param,paramModel);
        return param;
    }

    public QueryParameter buildQueryParameter(ParameterModel paramModel){
        QueryParameter param = new QueryParameter();
        extractData(param,paramModel);
        return param;
    }

    public Header buildHeader(ParameterModel paramModel) {
        
        Header header = new Header();
        extractData(header,paramModel);
        return header;
    }
    
    private void extractData(AbstractParam param, ParameterModel paramModel) {
        
        Utils.setDocumentation(paramModel, param);
        
        String defaultValue = paramModel.getDefaultValue();
        if(!Utils.isEmptyString(defaultValue)){
            param.setDefaultValue(defaultValue);
        }
        
        String fixedValue = paramModel.getFixedValue();
        if(!Utils.isEmptyString(fixedValue)){
            param.setEnumeration(Arrays.asList(fixedValue));
        }
        
        String type = paramModel.getType();
        if(!Utils.isEmptyString(type)){
            try{
                ParamType paramType = Enum.valueOf(ParamType.class, type.toUpperCase());
                if(paramType!=null && ParamType.STRING!=paramType){
                    param.setType(paramType);
                }
            }
            catch(Exception e){
                
            }
        }
        
        List<String> options = paramModel.getOptions();
        if(options!= null && ! options.isEmpty()){
            param.setEnumeration(new ArrayList<String>(options));
        }
        
        
    }

}
