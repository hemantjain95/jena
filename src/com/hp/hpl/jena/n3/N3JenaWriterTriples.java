/*
 * (c) Copyright 2001-2003, Hewlett-Packard Company, all rights reserved.
 * [See end of file]
 */

package com.hp.hpl.jena.n3;

//import org.apache.log4j.Logger;
import com.hp.hpl.jena.rdf.model.*;

/** A simple N3 writer - writes N3 out as triples with prefixes done.
 *  "N3 triples" - tripels with N3 abbreviations and prefixes.
 *  Very simple.  
 *
 * @author		Andy Seaborne
 * @version 	$Id: N3JenaWriterTriples.java,v 1.1 2003-06-09 14:50:11 andy_seaborne Exp $
 */



public class N3JenaWriterTriples extends N3JenaWriterCommon
{
    protected void writeModel(Model model)
    {
        alwaysAllocateBNodeLabel = true ;
        StmtIterator sIter = model.listStatements() ;
        for ( ; sIter.hasNext() ; )
        {
            Statement stmt = sIter.nextStatement() ;
            out.print( formatResource(stmt.getSubject()) ) ;
            out.print(" ") ;
            out.print( formatProperty(stmt.getPredicate()) ) ;
            out.print(" ") ;
            out.print( formatNode(stmt.getObject()) ) ;
            out.println(" .") ; 
        }
        sIter.close() ;
    }
}

/*
 *  (c) Copyright Hewlett-Packard Company 2001-2003
 *  All rights reserved.
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
