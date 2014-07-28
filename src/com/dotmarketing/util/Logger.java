/*
 *  UtilMethods.java
 *
 *  Created on March 4, 2002, 2:56 PM
 */
package com.dotmarketing.util;

import com.dotcms.repackage.tika_app.org.apache.log4j.Level;
import com.dotmarketing.beans.Host;
import com.dotmarketing.velocity.VelocityServlet;
import org.apache.velocity.context.Context;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.context.InternalContextAdapterImpl;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.WeakHashMap;

/**
 *@author     David Torres
 */
public class Logger{

	private static WeakHashMap<Class, com.dotcms.repackage.tika_app.org.apache.log4j.Logger> map = new WeakHashMap<Class, com.dotcms.repackage.tika_app.org.apache.log4j.Logger>();

	public static void clearLoggers(){
		map.clear();
	}

    public static com.dotcms.repackage.tika_app.org.apache.log4j.Logger clearLogger ( Class clazz ) {
        return map.remove( clazz );
	}
	
	public static final ThreadLocal<Context> velocityCtx = new ThreadLocal<Context>();
	/**
	 * This class is syncrozned.  It shouldn't be called. It is exposed so that 
	 * @param cl
	 * @return
	 */
	private synchronized static com.dotcms.repackage.tika_app.org.apache.log4j.Logger loadLogger(Class cl){
		if(map.get(cl) == null){
			com.dotcms.repackage.tika_app.org.apache.log4j.Logger logger = com.dotcms.repackage.tika_app.org.apache.log4j.Logger.getLogger(cl);
			map.put(cl, logger);
		}
		return map.get(cl);
	}

    public static void info(Object ob, String message) {
        Class cl = ob.getClass();
        com.dotcms.repackage.tika_app.org.apache.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
        logger.info(message);
    }

    public static void info(Class cl, String message) {
    	com.dotcms.repackage.tika_app.org.apache.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
        logger.info(message);
    }

    public static void debug(Object ob, String message) {
        Class cl = ob.getClass();
        com.dotcms.repackage.tika_app.org.apache.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
        logger.debug(message);
    }

    public static void debug(Object ob, String message, Throwable ex) {
        Class cl = ob.getClass();
        com.dotcms.repackage.tika_app.org.apache.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
        logger.debug(message, ex);
    }

    public static void debug(Class cl, String message) {
    	com.dotcms.repackage.tika_app.org.apache.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
        logger.debug(message);
    }

    public static void debug(Class cl, String message, Throwable ex) {
    	com.dotcms.repackage.tika_app.org.apache.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
        logger.debug(message, ex);
    }

    public static void error(Object ob, String message) {
        Class cl = ob.getClass();
        com.dotcms.repackage.tika_app.org.apache.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
    	velocityLogError(cl);
        logger.error(message);
    }

    public static void error(Object ob, String message, Throwable ex) {
        Class cl = ob.getClass();
        com.dotcms.repackage.tika_app.org.apache.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
    	velocityLogError(cl);
        logger.error(message, ex);
    }

    public static void error(Class cl, String message) {
    	com.dotcms.repackage.tika_app.org.apache.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
    	velocityLogError(cl);
        logger.error(message);
    }

    public static void error(Class cl, String message, Throwable ex) {
    	com.dotcms.repackage.tika_app.org.apache.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
    	velocityLogError(cl);
        logger.error(message, ex);
    }

    public static void fatal(Object ob, String message) {
        Class cl = ob.getClass();
        com.dotcms.repackage.tika_app.org.apache.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
    	
        logger.fatal(message);
    }

    public static void fatal(Object ob, String message, Throwable ex) {
        Class cl = ob.getClass();
        com.dotcms.repackage.tika_app.org.apache.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
        logger.fatal(message, ex);
    }

    public static void fatal(Class cl, String message) {
    	com.dotcms.repackage.tika_app.org.apache.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
        logger.fatal(message);
    }

    public static void fatal(Class cl, String message, Throwable ex) {
    	com.dotcms.repackage.tika_app.org.apache.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
        logger.fatal(message, ex);
    }

    public static void warn(Object ob, String message) {
        Class cl = ob.getClass();
        com.dotcms.repackage.tika_app.org.apache.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
        logger.warn(message);
    }

    public static void warn(Object ob, String message, Throwable ex) {
        Class cl = ob.getClass();
        com.dotcms.repackage.tika_app.org.apache.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
        logger.warn(message, ex);
    }

    public static void warn(Class cl, String message) {
    	com.dotcms.repackage.tika_app.org.apache.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
        logger.warn(message);
    }

    public static void warn(Class cl, String message, Throwable ex) {
    	com.dotcms.repackage.tika_app.org.apache.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
        logger.warn(message, ex);
    }
    public static boolean isDebugEnabled(Class cl) {
    	com.dotcms.repackage.tika_app.org.apache.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
        return logger.isEnabledFor(Level.DEBUG);
//    	return false;
    }

    public static boolean isInfoEnabled(Class cl) {
    	com.dotcms.repackage.tika_app.org.apache.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
        return logger.isEnabledFor(Level.INFO);
//    	return false;
    }
    public static boolean isWarnEnabled(Class cl) {
    	com.dotcms.repackage.tika_app.org.apache.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
        return logger.isEnabledFor(Level.WARN);
//    	return false;
    }
    public static boolean isErrorEnabled(Class cl) {
    	com.dotcms.repackage.tika_app.org.apache.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
        return logger.isEnabledFor(Level.ERROR);
//    	return false;
    }
    
    public static com.dotcms.repackage.tika_app.org.apache.log4j.Logger getLogger(Class cl) {
    	com.dotcms.repackage.tika_app.org.apache.log4j.Logger logger = map.get(cl);
    	if(logger == null){
    		logger = loadLogger(cl);	
    	}
        return logger;
    }
    
    
    
    private static void velocityLogError(Class cl){
    	if(velocityCtx.get() != null){
    		Context ctx =  velocityCtx.get();
    		InternalContextAdapter ica =  new InternalContextAdapterImpl(ctx);
    		com.dotcms.repackage.tika_app.org.apache.log4j.Logger logger = map.get(VelocityServlet.class);
    		logger.error("#--------------------------------------------------------------------------------------");
    		logger.error("#");
    		if(ica.getCurrentMacroName() != null){
    			logger.error("# Velocity Error");
    		}

    		if(ctx.get("VTLSERVLET_URI") != null && ctx.get("host") != null ){
    			logger.error("# on url      : " + ((Host) ctx.get("host")).getHostname()  + ctx.get("VTLSERVLET_URI") );
    		}
    		else if(ctx.get("VTLSERVLET_URI") != null ){
    			logger.error("# on uri      : " + ctx.get("VTLSERVLET_URI") );
    		}
    		else if(ctx.get("host") != null){
    			logger.error("# on host     : " + ((Host) ctx.get("host")).getHostname() );
    		}
    		if(ctx.get("request") != null){
    			HttpServletRequest req  = (HttpServletRequest)ctx.get("request");
    			if(req.getAttribute("javax.servlet.forward.request_uri") != null){
    				logger.error("# on req      : " + req.getAttribute("javax.servlet.forward.request_uri") );
    			}
    			
     		}
    		if(ica.getCurrentMacroName() != null){
    			logger.error("# with macro  : #" + ica.getCurrentMacroName());
    		}
    		if(ica.getCurrentTemplateName() != null){
    			logger.error("# on template : " + normalizeTemplate(ica.getCurrentTemplateName()));
    		}
    		logger.error("#    stack:");
    		for(Object obj : ica.getTemplateNameStack()){
				logger.error("#    -- " + normalizeTemplate(obj));
    		}
    		logger.error("#");
    		logger.error("#--------------------------------------------------------------------------------------");
    	}
    	
    }
    
    
    
    private static String normalizeTemplate(Object t){
    	if(t ==null){
    		return null;
    	}
		String x = t.toString();
		x = x.replace(File.separatorChar, '/');
		x = (x.indexOf("assets") > -1) ? x.substring(x.lastIndexOf("assets"), x.length()) : x;
		x = (x.startsWith("/")) ?  x : "/" + x;

		return x;
    }
    
    
}
