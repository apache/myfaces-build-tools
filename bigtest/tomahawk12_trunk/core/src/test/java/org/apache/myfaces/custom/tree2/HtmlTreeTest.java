/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.myfaces.custom.tree2;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * TestCase for HtmlTree
 *
 * @author Sean Schofield
 */
public class HtmlTreeTest extends AbstractTreeTestCase
{
    //private HtmlTree tree;

    /**
     * Constructor
     * @param name Test name
     */
    public HtmlTreeTest(String name)
    {
        super(name);
    }

//    public void setUp()
//    {
//        super.setUp();
//        tree = new HtmlTree();
//    }
//
//    public void tearDown()
//    {
//        super.tearDown();
//    }

    // Return the tests included in this test case.
    public static Test suite()
    {
        return new TestSuite(HtmlTreeTest.class);
    }

    /**
     * Test default values of the tree
     */
    public void testDefaults()
    {
        assertTrue("clientSideToggle default should be true", tree.isClientSideToggle());
        assertTrue("showNav default should be true", tree.isShowNav());
        assertTrue("showLines default should be true", tree.isShowLines());
        assertTrue("showRootNode default should be true", tree.isShowRootNode());
        assertTrue("preserveToggle default should be true", tree.isPreserveToggle());
    }

    /**
     *
     */
    public void testSaveAndRestore()
    {

    }
}
