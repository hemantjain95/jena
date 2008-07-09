/*
 * (c) Copyright 2008 Hewlett-Packard Development Company, LP
 * All rights reserved.
 * [See end of file]
 */

package opt;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.sparql.algebra.Op;
import com.hp.hpl.jena.sparql.algebra.TransformCopy;
import com.hp.hpl.jena.sparql.algebra.op.OpBGP;
import com.hp.hpl.jena.sparql.algebra.op.OpFilter;
import com.hp.hpl.jena.sparql.algebra.op.OpSequence;
import com.hp.hpl.jena.sparql.algebra.op.OpTable;
import com.hp.hpl.jena.sparql.core.BasicPattern;
import com.hp.hpl.jena.sparql.expr.Expr;
import com.hp.hpl.jena.sparql.expr.ExprList;
import com.hp.hpl.jena.sparql.util.VarUtils;

/** Rewrite an algebra expression to put filters as close to their bound variables in a BGP.
 *  Works on (filter (BGP ...) )
 *  
 * @author Andy Seaborne
 */

public class TransformFilterPlacement extends TransformCopy
{
    static boolean doFilterPlacement = true ;
    
    public TransformFilterPlacement()
    { }
    
    // **** Replacement for FilterPlacement.
    public Op transform(OpFilter opFilter, Op x)
    {
        if ( ! doFilterPlacement )
            return super.transform(opFilter, x) ;
        
        // (filter (BGP ...) ) only. 
        if ( ! ( x instanceof OpBGP ) )
            return super.transform(opFilter, x) ;
        
        // Can also move filter around for OpSequence and OpJoin 
        // see OpCompile.compile(OpFilter opFilter,...)
        
        BasicPattern pattern = ((OpBGP)x).getPattern() ;
        
        // Destructive use of exprs - copy it.
        ExprList exprs = new ExprList(opFilter.getExprs()) ;
        // Variables in scope as we move through the BGP
        Set patternVarsScope = new HashSet() ;

        // Any filters that depend on no variables. 
        Op op = insertAnyFilter(exprs, patternVarsScope, null) ;
        
        // Loop on triples in BGP
        for ( Iterator iter = pattern.getList().listIterator() ; iter.hasNext() ; )
        {
            Triple triple = (Triple)iter.next();
            OpBGP opBGP = getBGP(op) ;
            if ( opBGP == null )
            {
                // Last thing was not a BGP (so it likely to be a filter)
                // Need to pass the results from that into the next triple.
                // Which is a join and sequence is a special case of join
                // which always evaluates by passing results of the early
                // part into the next element of the sequence.
                
                opBGP = new OpBGP() ;    
                op = OpSequence.create(op, opBGP) ;
            }
            
            opBGP.getPattern().add(triple) ;
            // Update varaibles in scope.
            VarUtils.addVarsFromTriple(patternVarsScope, triple) ;
            
            // Attempt to place any filters
            op = insertAnyFilter(exprs, patternVarsScope, op) ;
        } 
        //remaining expr
        op = buildFilter(exprs, op) ;
        
        // Punt - for now.
        return op ;
        
    }
    
    /** For any expression now in scope, wrpe the op with a filter */
    private Op insertAnyFilter(ExprList exprs, Set patternVarsScope, Op op)
    {
        for ( Iterator iter = exprs.iterator() ; iter.hasNext() ; )
        {
            Expr expr = (Expr)iter.next() ;
            // Cache
            Set exprVars = expr.getVarsMentioned() ;
            if ( patternVarsScope.containsAll(exprVars) )
            {
                if ( op == null )
                    op = OpTable.unit() ;
                op = OpFilter.filter(expr, op) ;
                iter.remove() ;
            }
        }
        return op ;
    }
    
    /** Find the current OpBGP, or return null. */ 
    private static OpBGP getBGP(Op op)
    {
        if ( op instanceof OpBGP )
            return (OpBGP)op ;
        
        if ( op instanceof OpSequence )
        {
            // Is last in OpSequnce an BGP?
            OpSequence opSeq = (OpSequence)op ;
            List x = opSeq.getElements() ;
            if ( x.size() > 0 )
            {                
                Op opTop = (Op)x.get(x.size()-1) ;
                if ( opTop instanceof OpBGP )
                    return (OpBGP)opTop ;
                // Drop through
            }
        }
        // Can't find.
        return null ;
    }

    /** Place expressions around an Op */ 
    private static Op buildFilter(ExprList exprs, Op op)
    {
        if ( exprs.isEmpty() )
            return op ;
    
        for ( Iterator iter = exprs.iterator() ; iter.hasNext() ; )
        {
            Expr expr = (Expr)iter.next() ;
            if ( op == null )
                op = OpTable.unit() ;
            op = OpFilter.filter(expr, op) ;
            iter.remove();
        }
        return op ;
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