/*
  (c) Copyright 2002, Hewlett-Packard Company, all rights reserved.
  [See end of file]
  $Id: RDFClosure.java,v 1.2 2003-03-04 17:54:59 ian_dickinson Exp $
*/

package com.hp.hpl.jena.graph.compose;

import com.hp.hpl.jena.graph.*;
import com.hp.hpl.jena.util.iterator.*;

import java.util.*;

/**
	@author kers

	the essence of RDFClosure is that it adds the triple [type type Property]
	to the base model, and for every R such that a triple [x R y] appears in
	the base, the triple [R type Property] appears in the closure.
*/

public class RDFClosure extends Dyadic implements Graph 
	{
		
	/**
		we use L to hold the base graph, and R to hold the inferred
		triples.
	*/
	
	public RDFClosure( Graph base )
		{
		super( base, null );
		}
		
	public void add( Triple t )
		{
		L.add( t );
		}

	public void delete(  Triple t )
		{
		throw new RuntimeException( "RDFClosure::delete not implemented [yet]" );
		}
		
	static Node RDFtype = Node.createURI( "rdf:type" );	
	static Node RDFproperty = Node.createURI( "rdf:Property" );
	static Triple typeTriple = new Triple( RDFtype, RDFtype, RDFproperty );
	
	private boolean plausible( TripleMatch tm, Node p )
		{
		return tm.subject( p ) && tm.predicate( RDFtype ) && tm.object( RDFproperty );
		}
		
	private ClosableIterator findProperties( final Graph g, final TripleMatch tm )
		{
		ClosableIterator it = g.find( null, null, null );
		final HashMap properties = new HashMap();
		try 
			{ 
			if (tm.matches( typeTriple )) properties.put( RDFtype, typeTriple );
			while (it.hasNext()) 
				{
				Node p = ((Triple) it.next()).getPredicate();
				if (properties.containsKey( p ) == false && plausible( tm, p ))
					{			
					Triple t = new Triple( p, RDFtype, RDFproperty );
					if (tm.triple( t )) properties.put( p, t );
					}
				}
			}
		finally 
			{ it.close(); }			
		return new NiceIterator()
			{
			Iterator base = properties.values().iterator();
			public boolean hasNext() { return base.hasNext(); }			
			public Object next()  { return base.next(); }
			};	
		}

	public ExtendedIterator find( TripleMatch m ) 
		{
		if (m.predicate( RDFtype ) && m.object( RDFproperty ))
			{
			return L.find( m ).andThen( findProperties( L, m ) );	
			}
		else
			return L.find( m );
		}
		

	}

/*
    (c) Copyright Hewlett-Packard Company 2002
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions
    are met:

    1. Redistributions of source code must retain the above copyright
       notice, this list of conditions and the following disclaimer.

    2. Redistributions in binary form must reproduce the above copyright
       notice, this list of conditions and the following disclaimer in the
       documentation and/or other materials provided with the distribution.

    3. The name of the author may not be used to endorse or promote products
       derived from this software without specific prior written permission.

    THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
    IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
    OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
    IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
    NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
    DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
    THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
    (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
    THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
