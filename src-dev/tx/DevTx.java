package tx;


public class DevTx
{
    // BlobStream.
    //   Include "read() == read next"
    // TestByteArray and up - exception tests.
    
    
    // Run tests with BlockMgrFactory.tracked on.
    // Test with overlapping iterators.
    
    // test for block managers and tracking.
    
    // Ref counting get/release.
    // pom.xml - exclude resources/log4j.properies
    
    // Tidy up 
    //   See HACK (BPTreeNode)
    //   See [TxTDB:PATCH-UP]
    //   See FREE
    //   See [ITER]
    
    // Caching pages across actions sequences. e.g BPT root block.
    // Iterators
    //   Cache in transaction so forgettable?
    //   Iterator tracking replaces epoch mechanism?
    //   (No - it's a step of iterator that complains, not the update.) 
    
    // ---- ---- ---- ----

    // Other:
    //   Sort out IndexBulder/IndexFactory/(IndexMaker in test)
    
    
    /*
     * Iterator tracking
     *   End transaction => close all open iterators.
     *   Need transaction - at least something to attach for tracking.
     *     ==> Add "transaction txn" to all RangeIndex operations.  Default null -> no transaction.
     *     OR
     *     ==> Add to B+Tree   .setTransaction(Transaction txn) 
     *   End transaction -> close block managers -> checking? 
     *   
     * Recycle DatasetGraphTx objects.  Setup - set PageView
     *   better setup.
     */

    /*
     * Layers:
     *   DatasetGraph
     *   Indexes
     *   Pages
     *   Blocks
     *   Storage = FileAccess (a sequence of blocks) 
     */
    
    /* 
     * Fast B+Tree creation: wrap an existsing BPTree with another that switches the block managers only.
     *    BPTree.attach with warpping BlockMgrs.
     *    Delay creation of some things?
     * Cache root block.
     * Setup
     *   Transaction start: grab alloc id.
     */
    
    // TDB 0.8.10 is rev 8718; TxTDB forked at 8731
    // Diff of SF ref 8718 to Apache cross over applied. (src/ only)
    // Now Apache: rev 1124661 
}
