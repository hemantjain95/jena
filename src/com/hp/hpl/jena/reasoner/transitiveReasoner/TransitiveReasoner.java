/******************************************************************
 * File:        TransitiveReasoner.java
 * Created by:  Dave Reynolds
 * Created on:  16-Jan-03
 * 
 * (c) Copyright 2003, Hewlett-Packard Company, all rights reserved.
 * [See end of file]
 * $Id: TransitiveReasoner.java,v 1.12 2003-06-23 13:54:29 der Exp $
 *****************************************************************/
package com.hp.hpl.jena.reasoner.transitiveReasoner;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.reasoner.*;
import com.hp.hpl.jena.graph.*;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.ReasonerVocabulary;

/**
 * A  simple "reasoner" used to help with API development.
 * <p>This reasoner caches a transitive closure of the subClass and
 * subProperty graphs. The generated infGraph allows both the direct
 * and closed versions of these properties to be retrieved. The cache is
 * built when the tbox is bound in but if the final data graph
 * contains additional subProperty/subClass declarations then the
 * cache has to be rebuilt.</p>
 * <p>
 * The triples in the tbox (if present) will also be included
 * in any query. Any of tbox or data graph are allowed to be null.</p>
 * <p>
 * TODO: Add switch (in configuration code) to turn off meta-level enablement
 * of RDFS processing.</p>
 * 
 * @author <a href="mailto:der@hplb.hpl.hp.com">Dave Reynolds</a>
 * @version $Revision: 1.12 $ on $Date: 2003-06-23 13:54:29 $
 */
public class TransitiveReasoner implements Reasoner {

    /** The precomputed cache of the subClass graph */
    protected TransitiveGraphCache subClassCache;
    
    /** The precomputed cache of the subProperty graph */
    protected TransitiveGraphCache subPropertyCache;
    
    /** The graph registered as the schema, if any */
    protected Finder tbox = null;
    
    /** The direct (minimal) version of the subPropertyOf property */
    public static Node directSubPropertyOf;
    
    /** The direct (minimal) version of the subClassOf property */
    public static Node directSubClassOf;
    
    /** The normal subPropertyOf property */
    public static Node subPropertyOf;
    
    /** The normal subClassOf property */
    public static Node subClassOf;
    
    // Static initializer
    static {
        directSubPropertyOf = ReasonerRegistry.makeDirect(RDFS.subPropertyOf.getNode());
        directSubClassOf    = ReasonerRegistry.makeDirect(RDFS.subClassOf.getNode());
        subPropertyOf = RDFS.subPropertyOf.getNode();
        subClassOf = RDFS.subClassOf.getNode();
    }
    
    /** Constructor */
    public TransitiveReasoner() {
        subClassCache = new TransitiveGraphCache(directSubClassOf, subClassOf);
        subPropertyCache = new TransitiveGraphCache(directSubPropertyOf, subPropertyOf);
    }
    
    /**
     * Private constructor used by bindSchema when
     * returning a partially bound reasoner instance.
     */
    protected TransitiveReasoner(Finder tbox, 
                    TransitiveGraphCache subClassCache, 
                    TransitiveGraphCache subPropertyCache) {
        this.tbox = tbox;
        this.subClassCache = subClassCache;
        this.subPropertyCache = subPropertyCache;
    }

    /**
     * Return a description of the capabilities of this reasoner encoded in
     * RDF. These capabilities may be static or may depend on configuration
     * information supplied at construction time. May be null if there are
     * no useful capabilities registered.
     */
    public Model getCapabilities() {
        return TransitiveReasonerFactory.theInstance().getCapabilities();
    }

    /**
     * Determine whether the given property is recognized and treated specially
     * by this reasoner. This is a convenience packaging of a special case of getCapabilities.
     * @param property the property which we want to ask the reasoner about, given as a Node since
     * this is part of the SPI rather than API
     * @return true if the given property is handled specially by the reasoner.
     */
    public boolean supportsProperty(Property property) {
        ReasonerFactory rf = TransitiveReasonerFactory.theInstance();
        Model caps = rf.getCapabilities();
        Resource root = caps.getResource(rf.getURI());
        return caps.contains(root, ReasonerVocabulary.supportsP, property);
    }
     
    /**
     * Extracts all of the subClass and subProperty declarations from
     * the given schema/tbox and caches the resultant graphs.
     * It can only be used once, can't stack up multiple tboxes this way.
     * This limitation could be lifted - the only difficulty is the need to
     * reprocess all the earlier tboxes if a new subPropertyOf subPropertyOf
     * subClassOf is discovered.
     * @param tbox schema containing the property and class declarations
     */
    public Reasoner bindSchema(Graph tbox) throws ReasonerException {
        return bindSchema(new FGraph(tbox));
    }
     
    /**
     * Extracts all of the subClass and subProperty declarations from
     * the given schema/tbox and caches the resultant graphs.
     * It can only be used once, can't stack up multiple tboxes this way.
     * This limitation could be lifted - the only difficulty is the need to
     * reprocess all the earlier tboxes if a new subPropertyOf subPropertyOf
     * subClassOf is discovered.
     * @param tbox schema containing the property and class declarations
     */
    public Reasoner bindSchema(Model tbox) throws ReasonerException {
        return bindSchema(new FGraph(tbox.getGraph()));
    }
    
     
    /**
     * Extracts all of the subClass and subProperty declarations from
     * the given schema/tbox and caches the resultant graphs.
     * It can only be used once, can't stack up multiple tboxes this way.
     * This limitation could be lifted - the only difficulty is the need to
     * reprocess all the earlier tboxes if a new subPropertyOf subPropertyOf
     * subClassOf is discovered.
     * @param tbox schema containing the property and class declarations
     */
    Reasoner bindSchema(Finder tbox) throws ReasonerException {
        if (this.tbox != null) {
            throw new ReasonerException("Attempt to bind multiple rulesets - disallowed for now");
        }
        TransitiveGraphCache sCc = new TransitiveGraphCache(directSubClassOf, subClassOf);
        TransitiveGraphCache sPc = new TransitiveGraphCache(directSubPropertyOf, subPropertyOf);
        TransitiveEngine.cacheSubProp(tbox, sPc);
        TransitiveEngine.cacheSubClass(tbox, sPc, sCc);
        
        return new TransitiveReasoner(tbox, sCc, sPc);
    }
    
    /**
     * Attach the reasoner to a set of RDF ddata to process.
     * The reasoner may already have been bound to specific rules or ontology
     * axioms (encoded in RDF) through earlier bindRuleset calls.
     * @param data the RDF data to be processed, some reasoners may restrict
     * the range of RDF which is legal here (e.g. syntactic restrictions in OWL).
     * @return an inference graph through which the data+reasoner can be queried.
     * @throws ReasonerException if the data is ill-formed according to the
     * constraints imposed by this reasoner.
     */
    public InfGraph bind(Graph data) throws ReasonerException {
        return new TransitiveInfGraph(data, this);
    }   
   
    /**
     * Switch on/off drivation logging.
     * If set to true then the InfGraph created from the bind operation will start
     * life with recording of derivations switched on. This is currently only of relevance
     * to rule-based reasoners.
     * <p>
     * Default - false.
     */
    public void setDerivationLogging(boolean logOn) {
        // Irrelevant to this reasoner
    }
    
    /**
     * Set a configuration paramter for the reasoner. In the case of the this
     * reasoner there are no configuration parameters and this method is simply 
     * here to meet the interfaces specification
     * 
     * @param parameterUri the uri identifying the parameter to be changed
     * @param value the new value for the parameter, typically this is a wrapped
     * java object like Boolean or Integer.
     */
    public void setParameter(String parameterUri, Object value) {
        throw new IllegalParameterException(parameterUri);
    }
    
    /**
     * Accessor used during infgraph construction - return the cached
     * version of the subProperty lattice.
     */
    public TransitiveGraphCache getSubPropertyCache() {
        return subPropertyCache;
    }
    
    /**
     * Accessor used during infgraph construction - return the cached
     * version of the subClass lattice.
     */
    public TransitiveGraphCache getSubClassCache() {
        return subClassCache;
    }
    
    /**
     * Accessor used during infgraph construction - return the partially
     * bound tbox, if any.
     */
    public Finder getTbox() {
        return tbox;
    }
    
}

/*
    (c) Copyright Hewlett-Packard Company 2003
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

