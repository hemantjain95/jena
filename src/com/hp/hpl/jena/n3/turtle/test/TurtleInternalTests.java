/*
 * (c) Copyright 2001, 2002, 2003, 2004, 2005, 2006 Hewlett-Packard Development Company, LP
 * [See end of file]
 */

package com.hp.hpl.jena.n3.turtle.test;

import junit.framework.* ;
import java.io.* ;

import com.hp.hpl.jena.n3.turtle.TripleHandlerNull;
import com.hp.hpl.jena.n3.turtle.parser.TurtleParser;
import com.hp.hpl.jena.query.junit.TestUtils;

/**
 * @author		Andy Seaborne
 * @version 	$Id: TurtleInternalTests.java,v 1.2 2006-12-09 20:49:25 andy_seaborne Exp $
 */
public class TurtleInternalTests extends TestSuite
{
    static public TestSuite suite() {
        return new TurtleInternalTests() ;
    }
    

	static public final String QUOTE3 = "\"\"\"" ;
	static public boolean VERBOSE = false ;
	
	public TurtleInternalTests()
	{
		super("Turtle Parser Syntactic tests") ;
		
        // ---- Debug testing
        //addTest(new Test("<thing> b:px.b:py [] . ")) ;
        // if ( true ) return ;
        // ---- Debug testing

		// Make sure basic things, at least, parse.
		
		// URIs, qnames, statements, prefixes
        
        // End of statement (and whitespace)
        addTest(new Test("a:subj a:prop a:d .")) ;
        addTest(new Test("a:subj a:prop a:d . ")) ;
        addTest(new Test("a:subj a:prop a:d.")) ;
        addTest(new Test("a:subj a:prop a:d. ")) ;

        addTest(new Test("rdf: rdf:type :_.")) ;    
        addTest(new Test("@prefix start: <somewhere>.")) ;
        addTest(new Test("<http://here/subj> <http://here/prep> <http://here/obj>.")) ;     
        
		// Whitespace, comments
		addTest(new Test("a:subj\ta:prop\ta:d.\t")) ;
		addTest(new Test("       a:subj\ta:prop\ta:d.     ")) ;
		addTest(new Test("a:subj a:prop a:d.  ")) ;
		addTest(new Test("")) ;
		addTest(new Test(" #Comment")) ;
		addTest(new Test("a:subj a:prop a:d.  # Comment")) ;
		addTest(new Test("a:subj a:prop a:d.# Comment")) ;

		// Literal: strings
		addTest(new Test("a:subj a:prop 'string1'.")) ;
		addTest(new Test("a:subj a:prop \"string2\".")) ;
		addTest(new Test("a:subj a:prop '''string3'''.")) ;
		addTest(new Test("a:subj a:prop "+QUOTE3+"string3"+QUOTE3+".")) ;
		
		// Literals: datatypes
		addTest(new Test("a:subj a:prop 'string1'^^x:dt.")) ;
		addTest(new Test("a:subj a:prop 'string1'^^<uriref>.")) ;
        
        // Literals: numbers.
        addTest(new Test("a: :p 2. .")) ;
        addTest(new Test("a: :p +2. .")) ;
        addTest(new Test("a: :p -2 .")) ;
        addTest(new Test("a: :p 2e6.")) ;
        addTest(new Test("a: :p 2e-6.")) ;
        addTest(new Test("a: :p -2e-6.")) ;
        addTest(new Test("a: :p 2.0e-6.")) ;
        addTest(new Test("a: :p 2.0 .")) ;
        
//		// The "unusual" cases
//		addTest(new Test("a:subj 'prop'^^<uriref> 'string'.")) ;
//		addTest(new Test("a:subj a:prop 'string1'^^'stringDT'.")) ;
//
//		addTest(new Test("a:subj a:prop1 ?x ^^ x:dt.")) ;
//		addTest(new Test("a:subj a:prop2 ?x ^^ ?x.")) ;

		// Quotes in string
		addTest(new Test("a:subj a:prop \"\\'string2\\'\".")) ;
		addTest(new Test("a:subj a:prop \"\\\"string2\\\"\".")) ;
		addTest(new Test("a:subj a:prop '\\'string1\\'\'.")) ;
		addTest(new Test("a:subj a:prop '\\\"string1\\\"\'.")) ;
		
		addTest(new Test("a:q21 a:prop "+QUOTE3+"start\"finish"+QUOTE3+".")) ;
		addTest(new Test("a:q22 a:prop "+QUOTE3+"start\"\"finish"+QUOTE3+".")) ;
		addTest(new Test("a:q2e3 a:prop "+QUOTE3+"start\\\"\\\"\\\"finish"+QUOTE3+".")) ;
		addTest(new Test("a:q13 a:prop "+QUOTE3+"start'''finish"+QUOTE3+".")) ;
		
		addTest(new Test("a:q11 a:prop '''start'finish'''.")) ;
		addTest(new Test("a:q12 a:prop '''start''finish'''.")) ;
		addTest(new Test("a:q12 a:prop '''start\\'\\'\\'finish'''.")) ;
		addTest(new Test("a:q23 a:prop '''start\"\"\"finish'''.")) ;
		
		// Keywords and syntactic sugar
//		addTest(new Test("this a:prop x:y .")) ;
//		addTest(new Test("a:subj  a   x:y .")) ;
//		addTest(new Test("a:subj  =   x:y .")) ;
//		addTest(new Test("a:subj  =>  x:y .")) ;
//		addTest(new Test("a:subj  <=  x:y .")) ;
//		// <=> is not legal : it would mean "implies and is implied by" 
//        // addTest(new Test("a:subj  <=> x:y .")) ;
//		addTest(new Test("a:subj  >- x:y -> 'value' .")) ;
//		addTest(new Test("a:subj  >- x:y -> 'value1', 'value2' .")) ;
        
		// Not keywords
		addTest(new Test("a:subj <a>  x:y .")) ;
		addTest(new Test("<this>  a   x:y .")) ;
		addTest(new Test("@prefix has: <uri>.")) ;
		
		addTest(new Test("<>   a:prop  x:y .")) ;
		addTest(new Test("<#>  a:prop  x:y .")) ;
		
		// Object lists
		addTest(new Test("a:subj a:prop a:d, a:e.")) ;
		addTest(new Test("a:subj a:prop a:d, '123'.")) ;
		addTest(new Test("a:subj a:prop '123', a:e.")) ;
        //addTest(new Test("a:subj a:prop '123', .")) ;            // Null object list        
        //addTest(new Test("a:subj a:prop '123', '456', .")) ;     // Null object list        
        
		// Property lists
		addTest(new Test("a:subj a:p1 a:v1 ;  a:p2 a:v2 .")) ;
    	addTest(new Test("a:subj a:p1 a:v1, a:v2 ;  a:p2 a:v2 ; a:p3 'v4' ,'v5' .")) ;
        addTest(new Test("a:subj a:p1 a:v1; .")) ;                 // Null property list
        addTest(new Test("a:subj a:p1 a:v1; a:p2 a:v2; .")) ;      // Null property list
        
		
		// anon nodes
		addTest(new Test("[a:prop a:val].")) ;
		addTest(new Test("[] a:prop a:val.")) ;
		addTest(new Test("[] a:prop [].")) ;
		
		// formulae
        // The final dot (statement terminator of outer statement) is necessary
        // Inside formulae, it is not.
//        addTest(new Test("{:x :y :z} => {:x :y :z}.")) ;
//        addTest(new Test("{:x :y :z} => {:x :y :z . }.")) ;
//        addTest(new Test("{:x :y :z. } => {:x :y :z}.")) ;
        
		// Variables
//		addTest(new Test("?who ?knows ?what .")) ;
//		addTest(new Test("{?who ?knows ?what} => {'somesort' 'of' 'logic'}." )) ;
		
		// Formulae do not need the trailing '.'
//		addTest(new Test("{ this a \"string2\". } => { this a 'string1'} .")) ;
		
		// And they can have directives in.
//		addTest(new Test("{ @prefix : <a> } => { this a 'string1'} .")) ;
//		addTest(new Test("{ @prefix : <a> . a:x <b> 'c'} => { this a 'string1'} .")) ;
		
		// RDF collections
		//addTest(new Test("() .")) ;
		addTest(new Test("<here> <list> ().")) ;
		addTest(new Test(" ( a:i1 a:i2 a:i3 ) a rdf:List.")) ;
		
		// Paths
//		addTest(new Test(":x!:y <prop> [].")) ;
//		addTest(new Test(":x!:y!:z <prop> [].")) ;
//		addTest(new Test(":x^:y <prop> [].")) ;
//		addTest(new Test(":x^:y^:z <prop> [].")) ;
//		addTest(new Test("[] <prop> :x!:y^:z.")) ;
//		addTest(new Test("[] :x^:y!:z [].")) ;
        
        // Paths - using . (dot)
//        addTest(new Test(":x.:y <prop> [].")) ;
//        addTest(new Test(":x.:y.:z <prop> [].")) ;
//        addTest(new Test("[] <prop> :a.:c.")) ;
//        addTest(new Test("<thing>.:y  <prop> [].")) ;
//        addTest(new Test("x:x.<thing>.:y  <prop> [].")) ;
//        addTest(new Test("<thing>.:y^:z  <prop> [].")) ;
//        addTest(new Test(":y.<thing>.:z  <prop> [].")) ;
//        addTest(new Test("<thing> :px.:py.:pz [] . ")) ;
//        addTest(new Test("<thing> :px!:py!:pz [] . ")) ;
		
        // Paths and formulae
//        addTest(new Test("{ :a.:b.:c . }.")) ;
//        addTest(new Test("{ :a.:b.<c>.}.")) ;
        
		// Named things
//		addTest(new Test("_:anon :- [a:p a:v] .")) ;
//		addTest(new Test("<uri> :- [a:p [ a:p a:v] ] .")) ;		
//		// Named list: Not supported by cwm (as of 2001, 2002, 2003/09) but needed for printing shared 
//		addTest(new Test("_:anon :- (\"1\") .")) ;
//		// Named formulae: Not supported by cwm (as of 2001, 2002, 2003/09)
//		addTest(new Test("_:anon :- { ?a ?b ?c } .")) ;
        
        // Datatypes
        addTest(new Test("a:subj a:prop '123'^^xsd:integer .")) ;
        addTest(new Test("a:subj a:prop '123'^^<uri> .")) ;
        addTest(new Test("a:subj a:prop '<tag>text</tag>'^^rdf:XMLLiteral .")) ;
        
        // Numbers
        addTest(new Test("a:subj a:prop 123 .")) ;
        // addTest(new Test("a:subj a:prop 123.")) ; Illegal N3
        addTest(new Test("a:subj a:prop 123.1 .")) ;
        addTest(new Test("a:subj a:prop -123.1 .")) ;
        addTest(new Test("a:subj a:prop 123.1e3 .")) ;
        addTest(new Test("a:subj a:prop 123.1e-3 .")) ;
        addTest(new Test("a:subj a:prop 123.1E3 .")) ;
        addTest(new Test("a:subj a:prop 123.1E-3 .")) ;

        // Language tags
        addTest(new Test("a:subj a:prop 'text'@en .")) ;
        // Illegal in N-Triples
        //addTest(new Test("a:subj a:prop 'text'^^a:lang@en .")) ;
        //addTest(new Test("a:subj a:prop 'text'@en^^a:lang .")) ; // Can't have both
        
        // XML Literal
        addTest(new Test("a:subj a:prop '<tag>text</tag>'^^rdf:XMLLiteral .")) ; // Can't have both
//        addTest(new Test("a:subj a:prop '<tag>text</tag>'^^rdf:XMLLiteral@fr .")) ;

        //addTest(new Test("a:subj a:prop ?x^^xsd:integer .")) ; // No varibales
        //addTest(new Test("a:subj a:prop '123'^^?x .")) ;
        //addTest(new Test("a:subj a:prop ?x^^?y .")) ;
        
        // Unicode 00E9 is e-acute
        // Unicode 03B1 is alpha
        addTest(new Test("a:subj a:prop '\u00E9'.")) ;
        addTest(new Test("a:subj a:prop '\u003B1'.")) ;
        
        addTest(new Test("\u00E9:subj a:prop '\u00E9'.")) ;
        addTest(new Test("a:subj-\u00E9 a:prop '\u00E9'.")) ;
        
        addTest(new Test("\u03B1:subj a:prop '\u03B1'.")) ;
        addTest(new Test("a:subj-\u03B1 a:prop '\u03B1'.")) ;
	}
	
	static class Test extends TestCase
	{
		String testString ;
        
		Test(String s) { super(TestUtils.safeName(s)) ; testString = s ; }
		
		protected void runTest() throws Throwable
		{
            TurtleParser parser = new TurtleParser(new StringReader(testString)) ;
            parser.setTripleHandler(new TripleHandlerNull()) ;
            parser.getPrefixMapping().setNsPrefix("a", "http://host/a#") ;
            parser.getPrefixMapping().setNsPrefix("x", "http://host/a#") ;
            // Unicode 00E9 is e-acute
            // Unicode 03B1 is alpha
            parser.getPrefixMapping().setNsPrefix("\u00E9", "http://host/e-acute/") ;
            parser.getPrefixMapping().setNsPrefix("\u03B1", "http://host/alpha/") ;
            parser.getPrefixMapping().setNsPrefix("", "http://host/") ;
            parser.getPrefixMapping().setNsPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#") ;
            parser.getPrefixMapping().setNsPrefix("xsd", "http://www.w3.org/2001/XMLSchema#") ;
            parser.setBaseURI("http://base/") ;
            parser.parse() ;
            // TODO Check that's all.
		}
	}
}

/*
 *  (c) Copyright 2006 Hewlett-Packard Development Company, LP
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
