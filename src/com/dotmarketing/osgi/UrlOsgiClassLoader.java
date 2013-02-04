package com.dotmarketing.osgi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Jonathan Gamba
 * Date: 1/31/13
 */
public class UrlOsgiClassLoader extends URLClassLoader {

    Instrumentation instrumentation;
    OSGIClassTransformer transformer;

    private Collection<URL> urls;

    public UrlOsgiClassLoader ( URL url ) {
        super( new URL[]{url}, null );

        urls = new ArrayList<URL>();
        urls.add( url );
    }

    public Instrumentation getInstrumentation () {
        return instrumentation;
    }

    public OSGIClassTransformer getTransformer () {
        return transformer;
    }

    public void setInstrumentation ( Instrumentation instrumentation, OSGIClassTransformer transformer ) {
        this.instrumentation = instrumentation;
        this.transformer = transformer;
    }

    @Override
    public Class<?> loadClass ( String name ) throws ClassNotFoundException {
        try {
            return super.loadClass( name );
        } catch ( ClassNotFoundException e ) {
            return ClassLoader.getSystemClassLoader().loadClass( name );
        }
    }

    public Class<?> findClass ( String name ) throws ClassNotFoundException {
        return super.findClass( name );
    }

    @Override
    public synchronized Class loadClass ( String name, boolean resolve ) throws ClassNotFoundException {
        return super.loadClass( name, resolve );
    }

    @Override
    protected void addURL ( URL url ) {
        urls.add( url );
        super.addURL( url );
    }

    /**
     * Reload all the loaded classes of this custom class loader, by reload we mean to redefine those classes.
     *
     * @throws Exception
     */
    public void reload () throws Exception {

        for ( URL url : urls ) {

            File jarFile = new File( url.toURI() );
            JarFile jar = new JarFile( jarFile );

            Enumeration resources = jar.entries();
            while ( resources.hasMoreElements() ) {

                //We will try to redefine class by class
                JarEntry entry = (JarEntry) resources.nextElement();
                if ( !entry.isDirectory() && entry.getName().contains( ".class" ) ) {

                    String className = entry.getName().replace( "/", "." ).replace( ".class", "" );

                    //We just want to redefine loaded classes, we don't want to load something that will not be use it
                    Class currentClass = findLoadedClass( className );
                    if ( currentClass != null ) {

                        InputStream in = null;
                        ByteArrayOutputStream out = null;
                        try {
                            in = jar.getInputStream( entry );
                            out = new ByteArrayOutputStream();

                            byte[] buffer = new byte[1024];
                            int length;
                            while ( (length = in.read( buffer )) > 0 ) {
                                out.write( buffer, 0, length );
                            }
                            byte[] byteCode = out.toByteArray();

                            try {
                                //And finally redefine the class
                                ClassDefinition classDefinition = new ClassDefinition( currentClass, byteCode );
                                instrumentation.redefineClasses( classDefinition );
                            } catch ( ClassNotFoundException e ) {
                                //If the class has not been loaded we don't need to redefine it
                            }
                        } finally {
                            if ( in != null ) {
                                in.close();
                            }
                            if ( out != null ) {
                                out.close();
                            }
                        }
                    }

                }
            }

        }
    }

}