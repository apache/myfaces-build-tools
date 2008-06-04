package org.apache.myfaces.custom.focus2;

import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class TreeVisitor
{

    public static interface TreeTraversalListener
    {

        /**
         * @param context
         * @param level
         * @param component
         * @return false to stop traversal
         */
        boolean traverse(FacesContext context, int level, UIComponent component);
    }

    public static void traverseTree(FacesContext context,
            TreeTraversalListener listener, UIComponent startingFrom)
    {

        int level = 0;

        if (!listener.traverse(context, level, startingFrom))
            return;

        if (!recurseDown(context, listener, startingFrom, level + 1))
            return;

        recurseUp(context, listener, startingFrom, level - 1);
    }

    private static boolean recurseDown(FacesContext context,
            TreeTraversalListener listener, UIComponent comp, int level)
    {
        Iterator it = comp.getFacetsAndChildren();

        while (it.hasNext())
        {
            UIComponent child = (UIComponent) it.next();
            if (!listener.traverse(context, level, child))
                return false;
            if (!recurseDown(context, listener, child, level + 1))
                return false;
        }

        return true;
    }

    private static boolean recurseUp(FacesContext context,
            TreeTraversalListener listener, UIComponent comp, int level)
    {
        UIComponent parent = comp.getParent();
        if (parent != null)
        {
            Iterator siblingIt = parent.getFacetsAndChildren();

            boolean traverseNow = false;

            while (siblingIt.hasNext())
            {
                if (traverseNow)
                {
                    UIComponent nextSibling = (UIComponent) siblingIt.next();

                    if (!listener.traverse(context, level, nextSibling))
                        return false;

                    if (!recurseDown(context, listener, nextSibling, level + 1))
                        return false;
                }
                else
                {
                    UIComponent potSibling = (UIComponent) siblingIt.next();

                    if (potSibling == comp)
                    {
                        traverseNow = true;
                    }
                }
            }

            return recurseUp(context, listener, parent, level - 1);
        }
        return false;
    }
}
