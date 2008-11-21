/*
 * (c) Copyright 2008 Hewlett-Packard Development Company, LP
 * All rights reserved.
 * [See end of file]
 */

package com.hp.hpl.jena.tdb.base.loader;

import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFErrorHandler;
import com.hp.hpl.jena.rdf.model.RDFReader;
import com.hp.hpl.jena.shared.JenaException;

/** Jena reader interface to the bulk loader */
public class NTriplesReader2 implements RDFReader
{
    @Override
    public void read(Model model, String url)
    { 
        InputStream in ;

        try { in = (new URL(url)).openStream() ; }
        catch (Exception e) { throw new JenaException(e); }
        NodeTupleReader.read(model.getGraph(), in, url) ;
    }

    @Override
    public void read(Model model, Reader r, String base)
    { 
        NodeTupleReader.read(model.getGraph(), r, base) ;
    }

    @Override
    public void read(Model model, InputStream r, String base)
    { 
        NodeTupleReader.read(model.getGraph(), r, base) ;
    }

    public Object setProperty(String propName, Object propValue)
    {
        throw new UnsupportedOperationException("BulkReader.setProperty") ;
    }

    // Jena reader interface
    public RDFErrorHandler setErrorHandler(RDFErrorHandler errHandler)
    {
        throw new UnsupportedOperationException("BulkReader.setErrorHandler") ;
    }
}

/*
 * (c) Copyright 2008 Hewlett-Packard Development Company, LP
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */