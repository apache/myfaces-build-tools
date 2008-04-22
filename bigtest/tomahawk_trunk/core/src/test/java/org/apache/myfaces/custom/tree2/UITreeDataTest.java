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

import javax.faces.event.ActionEvent;
import javax.faces.component.html.HtmlCommandLink;

/**
 * Test case for {@link UITreeData}.
 */
public class UITreeDataTest extends AbstractTreeTestCase
{
    /**
     * Constructor
     * @param name String
     */
    public UITreeDataTest(String name)
    {
        super(name);
    }

    /**
     * See abstract class
     */
    protected void setUp() throws Exception
    {
        super.setUp();
    }

    /**
     * Tests the selection of a specific node using both server side and client side
     * toggle options.
     *
     * @throws Exception
     */
    public void testNodeSelected() throws Exception
    {
    	tree.setClientSideToggle(false);
        // tree.getAttributes().put(JSFAttr.CLIENT_SIDE_TOGGLE, Boolean.FALSE);

        ActionEvent event = new ActionEvent(new HtmlCommandLink());

        // set the node to be selected
        tree.setNodeId("0:1:0");
        tree.setNodeSelected(event);

        assertTrue("Node 0:1:0 should be selected", tree.isNodeSelected());

    	tree.setClientSideToggle(true);
        // tree.getAttributes().put(JSFAttr.CLIENT_SIDE_TOGGLE, Boolean.TRUE);

        // set the node to be selected
        tree.setNodeId("0:1:0");
        tree.setNodeSelected(event);

        assertTrue("Node 0:1:0 should be selected", tree.isNodeSelected());
    }

    /**
     * Tests programatic selection of a node.  (See MYFACES-717)
     * @throws Exception
     */
    public void testProgramaticSelection() throws Exception
    {
        TreeModel treeModel = tree.getDataModel();
        TreeState treeState = treeModel.getTreeState();
        treeState.setSelected("0:3");

        treeModel.setTreeState(treeState);

        tree.setValue(treeModel);
        tree.setNodeId("0:3");
        assertTrue("Node 0:3 should be selected", tree.isNodeSelected());
    }

    /**
     * Tests the ability to expand all nodes at once.  (See TOMAHAWK-436)
     * @throws Exception
     */
    public void testExpandAll() throws Exception
    {
        tree.expandAll();

        tree.setNodeId("0");
        assertTrue("Node O should be expanded", tree.isNodeExpanded());

        tree.setNodeId("0:0");
        assertTrue("Node O:0 should be expanded", tree.isNodeExpanded());

        tree.setNodeId("0:0:0");
        assertTrue("Node O:0:0 should be expanded", tree.isNodeExpanded());

        tree.setNodeId("0:0:1");
        assertTrue("Node O:0:1 should be expanded", tree.isNodeExpanded());

        tree.setNodeId("0:0:2");
        assertTrue("Node O:0:2 should be expanded", tree.isNodeExpanded());

        tree.setNodeId("0:0:2:0");
        assertTrue("Node O:0:2:0 should be expanded", tree.isNodeExpanded());

        tree.setNodeId("0:0:2:1");
        assertTrue("Node O:0:2:1 should be expanded", tree.isNodeExpanded());

        tree.setNodeId("0:1");
        assertTrue("Node O:1 should be expanded", tree.isNodeExpanded());

        tree.setNodeId("0:1:0");
        assertTrue("Node O:1:0 should be expanded", tree.isNodeExpanded());

        tree.setNodeId("0:1:1");
        assertTrue("Node O:1:1 should be expanded", tree.isNodeExpanded());

        tree.setNodeId("0:2");
        assertTrue("Node O:2 should be expanded", tree.isNodeExpanded());

        tree.setNodeId("0:3");
        assertTrue("Node O:3 should be expanded", tree.isNodeExpanded());
    }

    /**
     * Tests the ability to collapse all nodes at once.  (See TOMAHAWK-27)
     * @throws Exception
     */
    public void testCollapseAll() throws Exception
    {
        // expand a few nodes to start off with
        tree.expandPath(new String[] {"0", "0:0", "0:0:1"});
        tree.expandPath(new String[] {"0", "0:1", "0:1:0"});

        tree.collapseAll();

        tree.setNodeId("0");
        assertFalse("Node O should not be expanded", tree.isNodeExpanded());

        tree.setNodeId("0:0");
        assertFalse("Node O:0 should not be expanded", tree.isNodeExpanded());

        tree.setNodeId("0:0:1");
        assertFalse("Node O:0:1 should not be expanded", tree.isNodeExpanded());

        tree.setNodeId("0:1");
        assertFalse("Node O:1 should not be expanded", tree.isNodeExpanded());

        tree.setNodeId("0:1:0");
        assertFalse("Node O:1:0 should not be expanded", tree.isNodeExpanded());
    }

    /**
     * Its possible that a facet will be empty on the decode if it contained only EL text.
     * So its wrong to throw an exception when this is the case.  (See TOMAHAWK-510)
     *
     * @throws Exception
     */
    public void testEmptyFacet() throws Exception
    {
        tree.processDecodes(facesContext);

    }

    public static Test suite()
    {
        return new TestSuite(UITreeDataTest.class);
    }
}
