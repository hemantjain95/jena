/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hp.hpl.jena.tdb.base.file;

import java.io.File;

import com.hp.hpl.jena.tdb.sys.Names;

/** 
 *  Wrapper for a file system directory; can create filenames in that directory.
 *  Enforces some simple consistency policies and provides a
 *  "typed string" for a filename to reduce errors.
 */   
 
public class Location
{
    static String pathSeparator = File.separator ;  // Or just "/"
    
//    // Filename bashing moved to FileOps.
//    private static Location dirname(String filename)
//    {
//        filename = filename.replace('\'', '/') ;
//        int i = filename.lastIndexOf('/') ;
//        if ( i == filename.length()-1 )
//            return new Location(filename) ;
//        String dirname = filename.substring(0, i) ; // Exclude final /
//        return new Location(dirname) ;
//    }
//    
//    private static Location ensureDirectory(String dirname)
//    {
//        File file = new File(dirname) ;
//        
//        if ( ! file.exists() )
//        {
//            if ( ! file.mkdirs() )
//                throw new FileException("Failed to create directory: "+file.getAbsolutePath()) ;
//        }
//        if ( ! file.isDirectory() )
//            throw new FileException("Not a directory: "+file.getAbsolutePath()) ;
//        Location loc = new Location() ;
//        loc.setPathname(dirname) ;
//        return loc ;
//    }
    
    private String pathname ;
    private MetaFile metafile = null ; 
    private boolean isMem = false ;
    
    static Location mem = new Location() ;
    // Every mem()is a fresh location and importantly fresh metadata.
    static public Location mem() { return new Location(); } 
    
    private Location()
    {
        pathname = Names.memName+pathSeparator ;
        isMem = true ;
        metafile = new MetaFile(Names.memName, Names.memName) ;
    }
    
    public Location(String rootname)
    { 
        super() ;
        if ( rootname.equals(Names.memName) )
        {
            isMem = true ;
            pathname = Names.memName + pathSeparator  ;
            metafile = new MetaFile(Names.memName, Names.memName) ;
            return ;
        }
        
        // Prefer "/"
        rootname = rootname.replace('\\', '/') ;
        File file = new File(rootname) ;
        
        if ( ! file.exists() )
        {
            file.mkdir() ;
            //throw new FileException("Not found: "+file.getAbsolutePath()) ;
        }
        else if ( ! file.isDirectory() )
            throw new FileException("Not a directory: "+file.getAbsolutePath()) ;

        pathname = file.getAbsolutePath() ;
        if ( ! pathname.endsWith(File.separator) && !pathname.endsWith(pathSeparator) )
            pathname = pathname + pathSeparator ;
        
        // Metafilename for a directory.
        String metafileName = getPath(Names.directoryMetafile, Names.extMeta) ;
        
        metafile = new MetaFile("Location: "+rootname, metafileName) ;
    }        

    public String getDirectoryPath()    { return pathname ; }
    public MetaFile getMetaFile()       { return metafile ; }
    public boolean isMem()              { return isMem ; }
    
    public Location getSubLocation(String dirname)
    {
        String newName = pathname+dirname ;
        File file = new File(newName) ;
        if ( file.exists() && ! file.isDirectory() )
            throw new FileException("Existing file: "+file.getAbsolutePath()) ;
        if ( ! file.exists() )
            file.mkdir() ;
        
        return new Location(newName) ;
    }

    public String getSubDirectory(String dirname)
    {
        return getSubLocation(dirname).getDirectoryPath() ;
    }

    /** Return an absolute filename where relative names are resolved from the location */ 
    public String absolute(String filename, String extension)
    { 
        return (extension == null) ? absolute(filename) : absolute(filename+"."+extension) ;
    }
    
    /** Return an absolute filename where relative names are resolved from the location */ 
    public String absolute(String filename)
    {
        File f = new File(filename) ;
        // Location relative.
        if ( ! f.isAbsolute() )
            filename = pathname+filename ;
        return filename ;
    }
 
    /** Does the location exist (and it a directory, and is accessible) */
    public boolean exists()
    { 
        File f = new File(getDirectoryPath()) ;
        return f.exists() && f.isDirectory() && f.canRead() ;
    }
    
    public boolean exists(String filename) { return exists(filename, null) ; }
    
    public boolean exists(String filename, String ext)
    {
        String fn = getPath(filename, ext) ;
        File f = new File(fn) ;
        return f.exists() ;
    }

    /** Return the name of the file relative to this location */ 
    public String getPath(String filename)
    {
        return getPath(filename, null) ;
    }
    
    /** Return the name of the file, and extension, relative to this location */ 
    public String getPath(String filename, String ext)
    {
        check(filename, ext) ;
        if ( ext == null )
            return pathname+filename ;
        return pathname+filename+"."+ext ;
    }

    private void check(String filename, String ext)
    {
        if ( filename == null )
            throw new FileException("Location: null filename") ;
        if ( filename.contains("/") || filename.contains("\\") )
            throw new FileException("Illegal file component name: "+filename) ;
        if ( filename.contains(".") && ext != null )
            throw new FileException("Filename has an extension: "+filename) ;
        if ( ext != null )
        {
            if ( ext.contains(".") )
                throw new FileException("Extension has an extension: "+filename) ;
        }
    }
    
    @Override
    public int hashCode()
    {
        if ( isMem )
            return 37 ;
        final int prime = 31 ;
        int result = 1 ;
        result = prime * result + ((pathname == null) ? 0 : pathname.hashCode()) ;
        return result ;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true ;
        if (obj == null) return false ;
        if (getClass() != obj.getClass()) return false ;
        Location other = (Location)obj ;
        if ( isMem && other.isMem ) return true ;
        if ( isMem && ! other.isMem ) return false ; 
        if ( ! isMem && other.isMem ) return false ; 
        
        if (pathname == null)
        {
            if (other.pathname != null) return false ;
        } else
            if (!pathname.equals(other.pathname)) return false ;
        return true ;
    }

    @Override
    public String toString() { return "location:"+pathname ; }
}
