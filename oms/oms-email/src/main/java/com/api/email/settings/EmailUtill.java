package com.api.email.settings;

import java.io.IOException;
import java.util.Map;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public final class EmailUtill {
	static String lineBreak = "\r\n";
	//public static final String TOADDRESS = ExternalConfigProperties.getProperty("debugEmail");
	public static float getTotalValue(float unitprice,long quantity){
		return unitprice*quantity;
	}

	public static String getEmailTemplateHeader(){
		return "<HTML><BODY>";
	}
	public static String getEmailTemplateFooter(){
		return "</BODY></HTML>";
	}
    
    public static String getTemplateAsString (String templateFile,Map<String,Object> map) throws IOException, TemplateException{
	    final Configuration config = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        config.setDefaultEncoding("UTF-8");
        config.setClassForTemplateLoading(EmailUtill.class, "/template/");
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER); 
        Template template = config.getTemplate(templateFile);
        String readyParsedTemplate = FreeMarkerTemplateUtils
                .processTemplateIntoString(template, map);
        readyParsedTemplate=getEmailTemplateHeader()+readyParsedTemplate+getEmailTemplateFooter();
        return readyParsedTemplate;
   }
}
