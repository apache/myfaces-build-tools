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

import junit.framework.TestCase;


/**
 * - .. Root (0)
 * |
 * - .. A (0:0)
 * |    |
 * |    + .. AA (0:0:0)
 * |    |
 * |    + .. AB (0:0:1)
 * |    |
 * |    + .. AC (0:0:2)
 * |         | .. aca (0:0:2:0)
 * |         | .. acb (0:0:2:1)
 * |
 * + .. B (0:1)
 * |    |
 * |    + .. BA (0:1:0)
 * |    |
 * |    + .. BB (0:1:1)
 * |
 * | .. C (0:2)
 * |
 * | .. d (0:3)
 */

public class TreeWalkerBaseTest extends TestCase
{
    private static final String DEFAULT_NODE_TYPE = "default";

    UITreeData treeData = new UITreeData();
    TreeWalker treeWalker;

    protected void setUp() throws Exception
    {
        // set up the test tree with the standard data
        TreeNodeBase Root_node = new TreeNodeBase(DEFAULT_NODE_TYPE, "Root", "Root", false);

        TreeNodeBase A_node = new TreeNodeBase(DEFAULT_NODE_TYPE, "A", "A", false);
        A_node.getChildren().add(new TreeNodeBase(DEFAULT_NODE_TYPE, "AA", "AA", false));
        A_node.getChildren().add(new TreeNodeBase(DEFAULT_NODE_TYPE, "AB", "AB", false));
        TreeNodeBase AC_node = new TreeNodeBase(DEFAULT_NODE_TYPE, "AC", "AC", false);
        AC_node.getChildren().add(new TreeNodeBase(DEFAULT_NODE_TYPE, "aca", "aca", true));
        AC_node.getChildren().add(new TreeNodeBase(DEFAULT_NODE_TYPE, "acb", "acb", true));
        A_node.getChildren().add(AC_node);
        Root_node.getChildren().add(A_node);

        TreeNodeBase B_node = new TreeNodeBase(DEFAULT_NODE_TYPE, "B", "B", false);
        B_node.getChildren().add(new TreeNodeBase(DEFAULT_NODE_TYPE, "BA", "BA", false));
        B_node.getChildren().add(new TreeNodeBase(DEFAULT_NODE_TYPE, "BB", "BB", false));
        Root_node.getChildren().add(B_node);

        TreeNodeBase C_node = new TreeNodeBase(DEFAULT_NODE_TYPE, "C", "C", false);
        Root_node.getChildren().add(C_node);

        TreeNodeBase d_node = new TreeNodeBase(DEFAULT_NODE_TYPE, "d", "d", true);
        Root_node.getChildren().add(d_node);

        treeData.setValue(Root_node);

        // setup the tree state
        TreeState treeState = treeData.getDataModel().getTreeState();
        treeState.toggleExpanded("0");
        treeState.toggleExpanded("0:0");

        // setup the tree walker
        treeWalker = treeData.getDataModel().getTreeWalker();
        treeWalker.setTree(treeData);
    }

    public void testGetRootNodeId() throws Exception
    {
        String nodeId = treeWalker.getRootNodeId();
        assertEquals("Unexpected rootId", "0", nodeId);
    }

    /**
     * Walk through all of the nodes and make sure the return value is correct as well as the current node of
     * the tree.  Continue until the walker runs out of nodes.  Since the checkState property is true only the
     * expanded nodes will be walked.
     *
     * @throws Exception
     */
    public void testNextCheckState() throws Exception
    {
        treeWalker.setCheckState(true);

        assertTrue("unxpected return value", treeWalker.next());
        assertEquals("unexpected nodeId", "0", treeData.getNodeId());
        assertEquals("unexpected node", "Root", treeData.getNode().getIdentifier());

        assertTrue("unxpected return value", treeWalker.next());
        assertEquals("unexpected nodeId", "0:0", treeData.getNodeId());
        assertEquals("unexpected node", "A", treeData.getNode().getIdentifier());

        assertTrue("unxpected return value", treeWalker.next());
        assertEquals("unexpected nodeId", "0:0:0", treeData.getNodeId());
        assertEquals("unexpected node", "AA", treeData.getNode().getIdentifier());

        assertTrue("unxpected return value", treeWalker.next());
        assertEquals("unexpected nodeId", "0:0:1", treeData.getNodeId());
        assertEquals("unexpected node", "AB", treeData.getNode().getIdentifier());

        assertTrue("unxpected return value", treeWalker.next());
        assertEquals("unexpected nodeId", "0:0:2", treeData.getNodeId());
        assertEquals("unexpected node", "AC", treeData.getNode().getIdentifier());

        assertTrue("unxpected return value", treeWalker.next());
        assertEquals("unexpected nodeId", "0:1", treeData.getNodeId());
        assertEquals("unexpected node", "B", treeData.getNode().getIdentifier());

        assertTrue("unxpected return value", treeWalker.next());
        assertEquals("unexpected nodeId", "0:2", treeData.getNodeId());
        assertEquals("unexpected node", "C", treeData.getNode().getIdentifier());

        assertTrue("unxpected return value", treeWalker.next());
        assertEquals("unexpected nodeId", "0:3", treeData.getNodeId());
        assertEquals("unexpected node", "d", treeData.getNode().getIdentifier());

        // not expecting anymore nodes
        assertFalse("unxpected return value", treeWalker.next());
    }


    /**
     * Walk through all of the nodes and make sure the return value is correct as well as the current node of
     * the tree.  Continue until the walker runs out of nodes.  Sets the checkState property to false so all of
     * the nodes will be walked.
     *
     * @throws Exception
     */
    public void testNextNoCheckState() throws Exception
    {
        // walk through all of the nodes and make sure the return value is correct until we run out of
        // nodes
        treeWalker.setCheckState(false);

        assertTrue("unxpected return value", treeWalker.next());
        assertEquals("unexpected nodeId", "0", treeData.getNodeId());
        assertEquals("unexpected node", "Root", treeData.getNode().getIdentifier());


        assertTrue("unxpected return value", treeWalker.next());
        assertEquals("unexpected nodeId", "0:0", treeData.getNodeId());
        assertEquals("unexpected node", "A", treeData.getNode().getIdentifier());

        assertTrue("unxpected return value", treeWalker.next());
        assertEquals("unexpected nodeId", "0:0:0", treeData.getNodeId());
        assertEquals("unexpected node", "AA", treeData.getNode().getIdentifier());

        assertTrue("unxpected return value", treeWalker.next());
        assertEquals("unexpected nodeId", "0:0:1", treeData.getNodeId());
        assertEquals("unexpected node", "AB", treeData.getNode().getIdentifier());

        assertTrue("unxpected return value", treeWalker.next());
        assertEquals("unexpected nodeId", "0:0:2", treeData.getNodeId());
        assertEquals("unexpected node", "AC", treeData.getNode().getIdentifier());

        assertTrue("unxpected return value", treeWalker.next());
        assertEquals("unexpected nodeId", "0:0:2:0", treeData.getNodeId());
        assertEquals("unexpected node", "aca", treeData.getNode().getIdentifier());

        assertTrue("unxpected return value", treeWalker.next());
        assertEquals("unexpected nodeId", "0:0:2:1", treeData.getNodeId());
        assertEquals("unexpected node", "acb", treeData.getNode().getIdentifier());

        assertTrue("unxpected return value", treeWalker.next());
        assertEquals("unexpected nodeId", "0:1", treeData.getNodeId());
        assertEquals("unexpected node", "B", treeData.getNode().getIdentifier());

        assertTrue("unxpected return value", treeWalker.next());
        assertEquals("unexpected nodeId", "0:1:0", treeData.getNodeId());
        assertEquals("unexpected node", "BA", treeData.getNode().getIdentifier());

        assertTrue("unxpected return value", treeWalker.next());
        assertEquals("unexpected nodeId", "0:1:1", treeData.getNodeId());
        assertEquals("unexpected node", "BB", treeData.getNode().getIdentifier());

        assertTrue("unxpected return value", treeWalker.next());
        assertEquals("unexpected nodeId", "0:2", treeData.getNodeId());
        assertEquals("unexpected node", "C", treeData.getNode().getIdentifier());

        assertTrue("unxpected return value", treeWalker.next());
        assertEquals("unexpected nodeId", "0:3", treeData.getNodeId());
        assertEquals("unexpected node", "d", treeData.getNode().getIdentifier());

        // not expecting anymore nodes
        assertFalse("unxpected return value", treeWalker.next());
    }

    /**
     * Walk through the tree.  Then call reset.  Make sure the tree is walked through again
     * from the beginning.  This can be tested by running some of the other tests twice with
     * a call to reset in between.
     */
    public void testReset()
    {
        try
        {
            testNextCheckState();
            treeWalker.reset();
            testNextCheckState();
        }
        catch (Exception e)
        {
            fail("Unable to successfuly check the next method twice with a reset");
        }
    }
}
