/*
 * (c) Copyright 2008 Hewlett-Packard Development Company, LP
 * All rights reserved.
 * [See end of file]
 */

package dev;

import com.hp.hpl.jena.sparql.algebra.op.OpBGP;
import com.hp.hpl.jena.sparql.algebra.op.OpFilter;
import com.hp.hpl.jena.sparql.engine.ExecutionContext;
import com.hp.hpl.jena.sparql.engine.QueryIterator;
import com.hp.hpl.jena.sparql.engine.main.OpCompiler;
import com.hp.hpl.jena.tdb.pgraph.GraphTDB;

public class OpCompilerTDB extends OpCompiler
{
    boolean isForTDB ;
    
    // A new compile object is created for each op compilation.
    // So the execCxt is changing as we go through the query-compile-excute process  
    public OpCompilerTDB(ExecutionContext execCxt)
    {
        super(execCxt) ;
        isForTDB = (execCxt.getActiveGraph() instanceof GraphTDB) ;
    }

//    // For reference, this is the standard BGP step. 
//    @Override
//    public QueryIterator compile(OpBGP opBGP, QueryIterator input)
//    {
//        BasicPattern pattern = opBGP.getPattern() ;
//        return StageBuilder.compile(pattern, input, execCxt) ;
//    }
    
    @Override
    public QueryIterator compile(OpFilter opFilter, QueryIterator input)
    {
        if ( ! isForTDB )
            return super.compile(opFilter, input) ;
        
        if ( ! OpBGP.isBGP(opFilter.getSubOp()) )
            return super.compile(opFilter, input) ;
        
        // It's (filter (bgp ...)) for TDB.
        
        return super.compile(opFilter, input) ;
    }

//    @Override
//    public QueryIterator compile(OpBGP opBGP, QueryIterator input)
//    {
//        BasicPattern pattern = opBGP.getPattern() ;
//        return StageBuilder.compile(pattern, input, execCxt) ;
//    }
    
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