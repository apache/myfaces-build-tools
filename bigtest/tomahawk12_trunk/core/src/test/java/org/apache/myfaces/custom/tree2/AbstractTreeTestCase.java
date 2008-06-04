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

import java.io.IOException;
import java.util.Stack;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.test.AbstractTomahawkViewControllerTestCase;

/**
 * An abstract test case that sets up a Tree2 for testing using the structure below.
 *
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

public class AbstractTreeTestCase extends AbstractTomahawkViewControllerTestCase
{
    static final String DEFAULT_NODE_TYPE = "default";
    static final String TREE_ID = "some_foo_tree";

    HtmlTree tree;
    TreeNodeBase rootNode;

    /**
     * Constructor
     * @param name String
     */
    public AbstractTreeTestCase(String name)
    {
        super(name);
    }

    /**
     * See abstract class
     */
    protected void setUp() throws Exception
    {
        super.setUp();

        tree = new HtmlTree();
        tree.setId(TREE_ID);

        // set up the test tree with the standard data
        rootNode = new TreeNodeBase(DEFAULT_NODE_TYPE, "Root", "Root", false);

        TreeNodeBase A_node = new TreeNodeBase(DEFAULT_NODE_TYPE, "A", "A", false);
        A_node.getChildren().add(new TreeNodeBase(DEFAULT_NODE_TYPE, "AA", "AA", false));
        A_node.getChildren().add(new TreeNodeBase(DEFAULT_NODE_TYPE, "AB", "AB", false));
        TreeNodeBase AC_node = new TreeNodeBase(DEFAULT_NODE_TYPE, "AC", "AC", false);
        AC_node.getChildren().add(new TreeNodeBase(DEFAULT_NODE_TYPE, "aca", "aca", true));
        AC_node.getChildren().add(new TreeNodeBase(DEFAULT_NODE_TYPE, "acb", "acb", true));
        A_node.getChildren().add(AC_node);
        rootNode.getChildren().add(A_node);

        TreeNodeBase B_node = new TreeNodeBase(DEFAULT_NODE_TYPE, "B", "B", false);
        B_node.getChildren().add(new TreeNodeBase(DEFAULT_NODE_TYPE, "BA", "BA", false));
        B_node.getChildren().add(new TreeNodeBase(DEFAULT_NODE_TYPE, "BB", "BB", false));
        rootNode.getChildren().add(B_node);

        TreeNodeBase C_node = new TreeNodeBase(DEFAULT_NODE_TYPE, "C", "C", false);
        rootNode.getChildren().add(C_node);

        TreeNodeBase d_node = new TreeNodeBase(DEFAULT_NODE_TYPE, "d", "d", true);
        rootNode.getChildren().add(d_node);

        tree.setValue(rootNode);
    }

    /**
     * Instead of using UIText or whatever inside the tree we use this special "simulator."
     * This way we can keep track of when the renderer is asking the node to render itself.
     */
    static final class NodeSimulator extends UIComponentBase
    {
        private static final String COMPONENT_FAMILY = "Foo";
        private Stack nodeStack = new Stack();

        public String getFamily()
        {
            return COMPONENT_FAMILY;
        }

        public void encodeBegin(FacesContext context) throws IOException
        {
            HtmlTree tree = (HtmlTree)super.getParent();
            nodeStack.push(tree.getNode());
        }

        public Stack getNodeStack()
        {
            return nodeStack;
        }
    }

    // Return the tests included in this test case.
    public static Test suite()
    {
        return (new TestSuite());
    }
}
