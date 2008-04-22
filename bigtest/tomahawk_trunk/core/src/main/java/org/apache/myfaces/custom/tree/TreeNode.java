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
package org.apache.myfaces.custom.tree;

import java.util.Iterator;

/**
 * Defines the requirements for an object that can be used as a tree node for
 * {@link HtmlTree}. (inspired by javax.swing.tree.TreeNode).
 *
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller </a>
 * @version $Revision$ $Date$
 */
public interface TreeNode
{

    /**
     * @return Gets the user object of this node.
     */
    Object getUserObject();

    /**
     * Answer the child at the given index.
     */
    TreeNode getChildAt(int childIndex);

    /**
     * Answer the number of children this node contains.
     */
    int getChildCount();

    /**
     * Answer the parent of this node.
     */
    TreeNode getParent();

    /**
     * Answer the index of the given node in this node's children.
     */
    int getIndex(TreeNode node);

    /**
     * Answer true if this node allows children.
     */
    boolean getAllowsChildren();

    /**
     * Answer true if this is a leaf node.
     */
    boolean isLeaf();

    /**
     * Answer the children of the receiver. The base collection is unmodifyable.
     */
    Iterator children();

}