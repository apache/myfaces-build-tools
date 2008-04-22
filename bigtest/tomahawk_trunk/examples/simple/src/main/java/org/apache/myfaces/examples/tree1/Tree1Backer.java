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
package org.apache.myfaces.examples.tree1;

import org.apache.myfaces.custom.tree.model.TreeModel;
import org.apache.myfaces.custom.tree.model.DefaultTreeModel;
import org.apache.myfaces.custom.tree.DefaultMutableTreeNode;

/**
 * @author Martin Marinschek
 */
public class Tree1Backer {

    private TreeModel treeModel;

    public TreeModel getTreeModel() {
        if (treeModel == null) {
            DefaultMutableTreeNode root = new DefaultMutableTreeNode("XY");
            DefaultMutableTreeNode a = new DefaultMutableTreeNode("A");
            root.insert(a);
            DefaultMutableTreeNode b = new DefaultMutableTreeNode("B");
            root.insert(b);
            DefaultMutableTreeNode c = new DefaultMutableTreeNode("C");
            root.insert(c);

            DefaultMutableTreeNode node = new DefaultMutableTreeNode("a1");
            a.insert(node);
            node = new DefaultMutableTreeNode("a2 ");
            a.insert(node);
            node = new DefaultMutableTreeNode("b ");
            b.insert(node);

            a = node;
            node = new DefaultMutableTreeNode("x1");
            a.insert(node);
            node = new DefaultMutableTreeNode("x2");
            a.insert(node);
            treeModel = new DefaultTreeModel(root);
        }
        return treeModel;
    }

    public void setTreeModel(TreeModel treeModel) {
        this.treeModel = treeModel;
    }
}
