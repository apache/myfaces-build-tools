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
package org.apache.myfaces.custom.datascroller;

import javax.faces.FacesException;
import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.component.html.ext.HtmlPanelGroup;
import org.apache.myfaces.shared_tomahawk.util._ComponentUtils;

/**
 * A component which works together with a UIData component to allow a
 * user to view a large list of data one "page" at a time, and navigate
 * between pages.
 *  
 * @author Thomas Spiegl (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class HtmlDataScroller extends HtmlPanelGroup implements ActionSource
{
    private static final Log log = LogFactory.getLog(HtmlDataScroller.class);

    private static final String FIRST_FACET_NAME = "first";
    private static final String LAST_FACET_NAME = "last";
    private static final String NEXT_FACET_NAME = "next";
    private static final String PREVIOUS_FACET_NAME = "previous";
    private static final String FAST_FORWARD_FACET_NAME = "fastforward";
    private static final String FAST_REWIND_FACET_NAME = "fastrewind";

    private static final String TABLE_LAYOUT = "table";
    private static final String LIST_LAYOUT = "list";
    private static final String SINGLE_LIST_LAYOUT = "singleList";
    private static final String SINGLE_TABLE_LAYOUT = "singleTable";

    // just for caching the associated uidata
    private transient UIData _UIData;

    private transient Boolean _listLayout;

    private transient Boolean _singleElementLayout;

    private MethodBinding _actionListener;

    public boolean isListLayout()
    {
        if(_listLayout == null)
        {
            String layout=getLayout();
            if(layout == null || layout.equals(TABLE_LAYOUT) || layout.equals(SINGLE_TABLE_LAYOUT))
                _listLayout = Boolean.FALSE;
            else if(layout.equals(LIST_LAYOUT) || layout.equals(SINGLE_LIST_LAYOUT))
            {
                _listLayout = Boolean.TRUE;
            }
            else
            {
                log.error("Invalid layout-parameter : "+layout +" provided. Defaulting to table-layout.");
                _listLayout = Boolean.FALSE;
            }
        }

        return _listLayout.booleanValue();
    }

    public boolean isSingleElementLayout()
    {
        if(_singleElementLayout == null)
        {
            String layout=getLayout();
            if(layout == null || layout.equals(SINGLE_LIST_LAYOUT) || layout.equals(SINGLE_TABLE_LAYOUT))
                _singleElementLayout = Boolean.TRUE;
            else
                _singleElementLayout = Boolean.FALSE;
        }

        return _singleElementLayout.booleanValue();
    }

    /**
     * Catch any attempts to queue events for this component, and ensure
     * the event's phase is set appropriately. Events are expected to be
     * queued by this component's renderer.
     * <p>
     * When this component is marked "immediate", any ActionEvent will
     * be marked to fire in the "apply request values" phase. When this
     * component is not immediate the event will fire during the
     * "invoke application" phase instead.
     */
    public void queueEvent(FacesEvent event)
    {
        if (event != null && event instanceof ActionEvent)
        {
            if (isImmediate())
            {
                event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
            }
            else
            {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            }
        }
        super.queueEvent(event);
    }

    /**
     * Invoke any action listeners attached to this class.
     * <p>
     * After listener invocation, the associated UIData's properties get
     * updated:
     * <ul>
     * <li>if the user selected an absolute page# then setFirst is called with
     * uiData.getRows() * pageNumber.
     * <li>if the user selected the "first page" option then setFirst(0) is called.
     * <li>if the user selected the "previous page" option then setFirst is decremented
     * by uiData.getRows().
     * <li>if the user selected the "fast rewind" option then setFirst is decremented
     * by uiData.getRows() * fastStep.
     * <li>next, fast-forward and last options have the obvious effect.
     * </ul>
     */
    public void broadcast(FacesEvent event) throws AbortProcessingException
    {
        super.broadcast(event);

        if (event instanceof ScrollerActionEvent)
        {
            ScrollerActionEvent scrollerEvent = (ScrollerActionEvent) event;

            broadcastToActionListener(scrollerEvent);
            
            // huh? getUIData never returns null.
            UIData uiData = getUIData();
            if (uiData == null)
            {
                return;
            }

            int pageindex = scrollerEvent.getPageIndex();
            if (pageindex == -1)
            {
                String facet = scrollerEvent.getScrollerfacet();
                if (FACET_FIRST.equals(facet))
                {
                    setFirst(uiData, 0);
                }
                else if (FACET_PREVIOUS.equals(facet))
                {
                    int previous = uiData.getFirst() - uiData.getRows();
                    if (previous >= 0)
                        setFirst(uiData, previous);
                }
                else if (FACET_NEXT.equals(facet))
                {
                    int next = uiData.getFirst() + uiData.getRows();
                    if (next < uiData.getRowCount())
                        setFirst(uiData, next);
                }
                else if (FACET_FAST_FORWARD.equals(facet))
                {
                    int fastStep = getFastStep();
                    if (fastStep <= 0)
                        fastStep = 1;
                    int next = uiData.getFirst() + uiData.getRows() * fastStep;
                    int rowcount = uiData.getRowCount();
                    if (next >= rowcount)
                        next = (rowcount - 1) - ((rowcount - 1) % uiData.getRows());
                    setFirst(uiData, next);
                }
                else if (FACET_FAST_REWIND.equals(facet))
                {
                    int fastStep = getFastStep();
                    if (fastStep <= 0)
                        fastStep = 1;
                    int previous = uiData.getFirst() - uiData.getRows() * fastStep;
                    if (previous < 0)
                        previous = 0;
                    setFirst(uiData, previous);
                }
                else if (FACET_LAST.equals(facet))
                {
                    int rowcount = uiData.getRowCount();
                    int rows = uiData.getRows();
                    int delta = rowcount % rows;
                    int first = delta > 0 && delta < rows ? rowcount - delta : rowcount - rows;
                    if (first >= 0)
                    {
                        setFirst(uiData, first);
                    }
                    else
                    {
                        setFirst(uiData, 0);
                    }
                }
            }
            else
            {
                int pageCount = getPageCount();
                if (pageindex > pageCount)
                {
                    pageindex = pageCount;
                }
                if (pageindex <= 0)
                {
                    pageindex = 1;
                }
                setFirst(uiData, uiData.getRows() * (pageindex - 1));
            }
        }
    }

    protected void setFirst(UIData uiData, int value) {
        //there might be special cases where the first-property of the data-table
        //is bound to a backing bean. If this happens, the user probably wants
        //the data-scroller to update this backing-bean value - if not, you can always
        //override this method in a subclass.
        if(uiData.getValueBinding("first")!=null)
        {
            ValueBinding vb = uiData.getValueBinding("first");
            vb.setValue(getFacesContext(),new Integer(value));
        }
        else
        {
            uiData.setFirst(value);
        }
    }

    /**
     * @param event
     */
    protected void broadcastToActionListener(ScrollerActionEvent event)
    {
        FacesContext context = getFacesContext();

        MethodBinding actionListenerBinding = getActionListener();
        if (actionListenerBinding != null)
        {
            try
            {
                actionListenerBinding.invoke(context, new Object[] {event});
            }
            catch (EvaluationException e)
            {
                Throwable cause = e.getCause();
                if (cause != null && cause instanceof AbortProcessingException)
                {
                    throw (AbortProcessingException)cause;
                }
                throw e;
            }
        }
        
        ActionListener defaultActionListener
                = context.getApplication().getActionListener();
        if (defaultActionListener != null)
        {
            defaultActionListener.processAction((ActionEvent)event);
        }
    }

    /**
     * @return int
     */
    public UIData getUIData()
    {
        if (_UIData == null)
        {
            _UIData = findUIData();
        }
        return _UIData;
    }

    /**
     * @return the page index of the uidata
     */
    public int getPageIndex()
    {
        UIData uiData = getUIData();
        int rows = uiData.getRows();
        if (0 == rows)
        {
            throw new FacesException("You need to set a value to the 'rows' attribute of component '" + uiData.getClientId(getFacesContext()) + "'" );
        }

        int pageIndex;
        if (rows > 0)
        {
            pageIndex = uiData.getFirst() / rows + 1;
        }
        else
        {
            log.warn("DataTable " + uiData.getClientId(FacesContext.getCurrentInstance())
                            + " has invalid rows attribute.");
            pageIndex = 0;
        }
        if (uiData.getFirst() % rows > 0)
        {
            pageIndex++;
        }
        return pageIndex;
    }

    /**
     * @return the page count of the uidata
     */
    public int getPageCount()
    {
        UIData uiData = getUIData();
        int rows = uiData.getRows();
        int pageCount;
        if (rows > 0)
        {
            pageCount = rows <= 0 ? 1 : uiData.getRowCount() / rows;
            if (uiData.getRowCount() % rows > 0)
            {
                pageCount++;
            }
        }
        else
        {
            rows = 1;
            pageCount = 1;
        }
        return pageCount;
    }

    /**
     * @return int
     */
    public int getRowCount()
    {
        return getUIData().getRowCount();
    }

    /**
     * @return int
     */
    public int getRows()
    {
        return getUIData().getRows();
    }

    /**
     * @return int
     */
    public int getFirstRow()
    {
        return getUIData().getFirst();
    }

    /**
     * Find the UIData component associated with this scroller.
     * <p>
     * If the "for" attribute is not null then that value is used to find the
     * specified component by id. Both "relative" and "absolute" ids are allowed;
     * see method UIComponent.findComponent for details.
     * <p>
     * If the "for" attribute is not defined, then this component is expected to
     * be a child of a UIData component.
     * 
     * @throws IllegalArgumentException if an associated UIData component
     * cannot be found.
     */
    protected UIData findUIData()
    {
        String forStr = getFor();
        UIComponent forComp;
        if (forStr == null)
        {
            // DataScroller may be a child of uiData
            forComp = getParent();
        }
        else
        {
            forComp = findComponent(forStr);
        }
        if (forComp == null)
        {
            throw new IllegalArgumentException(
                    "could not find UIData referenced by attribute dataScroller@for = '"
                    + forStr + "'");
        }
        else if (!(forComp instanceof UIData))
        {
            throw new IllegalArgumentException(
                "uiComponent referenced by attribute dataScroller@for = '"
                + forStr + "' must be of type " + UIData.class.getName()
                + ", not type " + forComp.getClass().getName());
        }
        return (UIData) forComp;
    }

    public void setFirst(UIComponent first)
    {
        getFacets().put(FIRST_FACET_NAME, first);
    }

    public UIComponent getFirst()
    {
        return (UIComponent) getFacets().get(FIRST_FACET_NAME);
    }

    public void setLast(UIComponent last)
    {
        getFacets().put(LAST_FACET_NAME, last);
    }

    public UIComponent getLast()
    {
        return (UIComponent) getFacets().get(LAST_FACET_NAME);
    }

    public void setNext(UIComponent next)
    {
        getFacets().put(NEXT_FACET_NAME, next);
    }

    public UIComponent getNext()
    {
        return (UIComponent) getFacets().get(NEXT_FACET_NAME);
    }

    public void setFastForward(UIComponent previous)
    {
        getFacets().put(FAST_FORWARD_FACET_NAME, previous);
    }

    public UIComponent getFastForward()
    {
        return (UIComponent) getFacets().get(FAST_FORWARD_FACET_NAME);
    }

    public void setFastRewind(UIComponent previous)
    {
        getFacets().put(FAST_REWIND_FACET_NAME, previous);
    }

    public UIComponent getFastRewind()
    {
        return (UIComponent) getFacets().get(FAST_REWIND_FACET_NAME);
    }

    public void setPrevious(UIComponent previous)
    {
        getFacets().put(PREVIOUS_FACET_NAME, previous);
    }

    public UIComponent getPrevious()
    {
        return (UIComponent) getFacets().get(PREVIOUS_FACET_NAME);
    }

    public boolean getRendersChildren()
    {
        return true;
    }

    /**
     * @see javax.faces.component.ActionSource#getAction()
     */
    public MethodBinding getAction()
    {
        // not used
        return null;
    }

    /**
     * @see javax.faces.component.ActionSource#setAction(javax.faces.el.MethodBinding)
     */
    public void setAction(MethodBinding action)
    {
        throw new UnsupportedOperationException(
                        "defining an action is not supported. use an actionlistener");
    }

    /**
     * @see javax.faces.component.ActionSource#setActionListener(javax.faces.el.MethodBinding)
     */
    public void setActionListener(MethodBinding actionListener)
    {
        _actionListener = actionListener;
    }

    /**
     * @see javax.faces.component.ActionSource#getActionListener()
     */
    public MethodBinding getActionListener()
    {
        return _actionListener;
    }

    /**
     * @see javax.faces.component.ActionSource#addActionListener(javax.faces.event.ActionListener)
     */
    public void addActionListener(ActionListener listener)
    {
        addFacesListener(listener);
    }

    /**
     * @see javax.faces.component.ActionSource#getActionListeners()
     */
    public ActionListener[] getActionListeners()
    {
        return (ActionListener[]) getFacesListeners(ActionListener.class);
    }

    /**
     * @see javax.faces.component.ActionSource#removeActionListener(javax.faces.event.ActionListener)
     */
    public void removeActionListener(ActionListener listener)
    {
        removeFacesListener(listener);
    }

    //------------------ GENERATED CODE BEGIN (do not modify!) --------------------

    public static final String COMPONENT_TYPE = "org.apache.myfaces.HtmlDataScroller";
    public static final String COMPONENT_FAMILY = "javax.faces.Panel";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.DataScroller";
    private static final boolean DEFAULT_IMMEDIATE = false;

    private String _for = null;
    private Integer _fastStep = null;
    private String _pageIndexVar = null;
    private String _pageCountVar = null;
    private String _rowsCountVar = null;
    private String _displayedRowsCountVar = null;
    private String _firstRowIndexVar = null;
    private String _lastRowIndexVar = null;
    private String _style = null;
    private String _styleClass = null;
    private String _columnClasses = null;
    private Boolean _paginator = null;
    private Integer _paginatorMaxPages = null;
    private String _paginatorTableClass = null;
    private String _paginatorTableStyle = null;
    private String _paginatorColumnClass = null;
    private String _paginatorColumnStyle = null;
    private String _paginatorActiveColumnClass = null;
    private String _paginatorActiveColumnStyle = null;
    private Boolean _paginatorRenderLinkForActive = null;        
    private Boolean _renderFacetsIfSinglePage = null;
    private Boolean _immediate;
    private String _onclick;
    private String _ondblclick;
    private String _firstStyleClass;
    private String _lastStyleClass;
    private String _previousStyleClass;
    private String _nextStyleClass;
    private String _fastfStyleClass;
    private String _fastrStyleClass;  

    public static final String FACET_FIRST = "first".intern();
    public static final String FACET_PREVIOUS = "previous".intern();
    public static final String FACET_NEXT = "next".intern();
    public static final String FACET_LAST = "last".intern();
    public static final String FACET_FAST_FORWARD = "fastf".intern();
    public static final String FACET_FAST_REWIND = "fastr".intern();

    public HtmlDataScroller()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

    public void setFor(String forValue)
    {
        _for = forValue;
    }

    public String getFor()
    {
        if (_for != null)
            return _for;
        ValueBinding vb = getValueBinding("for");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setFastStep(int fastStep)
    {
        _fastStep = new Integer(fastStep);
    }

    public int getFastStep()
    {
        if (_fastStep != null)
            return _fastStep.intValue();
        ValueBinding vb = getValueBinding("fastStep");
        Integer v = vb != null ? (Integer) vb.getValue(getFacesContext()) : null;
        return v != null ? v.intValue() : Integer.MIN_VALUE;
    }

    public void setPageIndexVar(String pageIndexVar)
    {
        _pageIndexVar = pageIndexVar;
    }

    public String getPageIndexVar()
    {
        if (_pageIndexVar != null)
            return _pageIndexVar;
        ValueBinding vb = getValueBinding("pageIndexVar");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setPageCountVar(String pageCountVar)
    {
        _pageCountVar = pageCountVar;
    }

    public String getPageCountVar()
    {
        if (_pageCountVar != null)
            return _pageCountVar;
        ValueBinding vb = getValueBinding("pageCountVar");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setRowsCountVar(String rowsCountVar)
    {
        _rowsCountVar = rowsCountVar;
    }

    public String getRowsCountVar()
    {
        if (_rowsCountVar != null)
            return _rowsCountVar;
        ValueBinding vb = getValueBinding("rowsCountVar");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setDisplayedRowsCountVar(String displayedRowsCountVar)
    {
        _displayedRowsCountVar = displayedRowsCountVar;
    }

    public String getDisplayedRowsCountVar()
    {
        if (_displayedRowsCountVar != null)
            return _displayedRowsCountVar;
        ValueBinding vb = getValueBinding("displayedRowsCountVar");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setFirstRowIndexVar(String firstRowIndexVar)
    {
        _firstRowIndexVar = firstRowIndexVar;
    }

    public String getFirstRowIndexVar()
    {
        if (_firstRowIndexVar != null)
            return _firstRowIndexVar;
        ValueBinding vb = getValueBinding("firstRowIndexVar");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setLastRowIndexVar(String lastRowIndexVar)
    {
        _lastRowIndexVar = lastRowIndexVar;
    }

    public String getLastRowIndexVar()
    {
        if (_lastRowIndexVar != null)
            return _lastRowIndexVar;
        ValueBinding vb = getValueBinding("lastRowIndexVar");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setStyle(String style)
    {
        _style = style;
    }

    public String getStyle()
    {
        if (_style != null)
            return _style;
        ValueBinding vb = getValueBinding("style");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setStyleClass(String styleClass)
    {
        _styleClass = styleClass;
    }

    public String getStyleClass()
    {
        if (_styleClass != null)
            return _styleClass;
        ValueBinding vb = getValueBinding("styleClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setPaginator(boolean paginator)
    {
        _paginator = Boolean.valueOf(paginator);
    }

    public boolean isPaginator()
    {
        if (_paginator != null)
            return _paginator.booleanValue();
        ValueBinding vb = getValueBinding("paginator");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
        return v != null ? v.booleanValue() : false;
    }

    public void setPaginatorMaxPages(int paginatorMaxPages)
    {
        _paginatorMaxPages = new Integer(paginatorMaxPages);
    }

    public int getPaginatorMaxPages()
    {
        if (_paginatorMaxPages != null)
            return _paginatorMaxPages.intValue();
        ValueBinding vb = getValueBinding("paginatorMaxPages");
        Integer v = vb != null ? (Integer) vb.getValue(getFacesContext()) : null;
        return v != null ? v.intValue() : Integer.MIN_VALUE;
    }

    public void setPaginatorTableClass(String paginatorTableClass)
    {
        _paginatorTableClass = paginatorTableClass;
    }

    public String getPaginatorTableClass()
    {
        if (_paginatorTableClass != null)
            return _paginatorTableClass;
        ValueBinding vb = getValueBinding("paginatorTableClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setPaginatorTableStyle(String paginatorTableStyle)
    {
        _paginatorTableStyle = paginatorTableStyle;
    }

    public String getPaginatorTableStyle()
    {
        if (_paginatorTableStyle != null)
            return _paginatorTableStyle;
        ValueBinding vb = getValueBinding("paginatorTableStyle");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setPaginatorColumnClass(String paginatorColumnClass)
    {
        _paginatorColumnClass = paginatorColumnClass;
    }

    public String getPaginatorColumnClass()
    {
        if (_paginatorColumnClass != null)
            return _paginatorColumnClass;
        ValueBinding vb = getValueBinding("paginatorColumnClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setPaginatorColumnStyle(String paginatorColumnStyle)
    {
        _paginatorColumnStyle = paginatorColumnStyle;
    }

    public String getPaginatorColumnStyle()
    {
        if (_paginatorColumnStyle != null)
            return _paginatorColumnStyle;
        ValueBinding vb = getValueBinding("paginatorColumnStyle");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setPaginatorActiveColumnClass(String paginatorActiveColumnClass)
    {
        _paginatorActiveColumnClass = paginatorActiveColumnClass;
    }

    public String getPaginatorActiveColumnClass()
    {
        if (_paginatorActiveColumnClass != null)
            return _paginatorActiveColumnClass;
        ValueBinding vb = getValueBinding("paginatorActiveColumnClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setPaginatorRenderLinkForActive(boolean paginatorRenderLinkForActive)
    {
        _paginatorRenderLinkForActive = Boolean.valueOf(paginatorRenderLinkForActive);
    }

    public boolean isPaginatorRenderLinkForActive()
    {
        if (_paginatorRenderLinkForActive != null)
            return _paginatorRenderLinkForActive.booleanValue();
        ValueBinding vb = getValueBinding("paginatorRenderLinkForActive");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
        return v != null ? v.booleanValue() : true;
    }

    public void setFirstStyleClass(String firstStyleClass)
    {
        _firstStyleClass = firstStyleClass;
    }

    public String getFirstStyleClass()
    {
        if (_firstStyleClass != null)
            return _firstStyleClass;
        ValueBinding vb = getValueBinding("firstStyleClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setLastStyleClass(String lastStyleClass)
    {
        _lastStyleClass = lastStyleClass;
    }

    public String getLastStyleClass()
    {
        if (_lastStyleClass != null)
            return _lastStyleClass;
        ValueBinding vb = getValueBinding("lastStyleClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setPreviousStyleClass(String previousStyleClass)
    {
        _previousStyleClass = previousStyleClass;
    }

    public String getPreviousStyleClass()
    {
        if (_previousStyleClass != null)
            return _previousStyleClass;
        ValueBinding vb = getValueBinding("previousStyleClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setNextStyleClass(String nextStyleClass)
    {
        _nextStyleClass = nextStyleClass;
    }

    public String getNextStyleClass()
    {
        if (_nextStyleClass != null)
            return _nextStyleClass;
        ValueBinding vb = getValueBinding("nextStyleClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setFastfStyleClass(String fastfStyleClass)
    {
        _fastfStyleClass = fastfStyleClass;
    }

    public String getFastfStyleClass()
    {
        if (_fastfStyleClass != null)
            return _fastfStyleClass;
        ValueBinding vb = getValueBinding("fastfStyleClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setFastrStyleClass(String fastrStyleClass)
    {
        _fastrStyleClass = fastrStyleClass;
    }

    public String getFastrStyleClass()
    {
        if (_fastrStyleClass != null)
            return _fastrStyleClass;
        ValueBinding vb = getValueBinding("fastrStyleClass");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }


    public void setPaginatorActiveColumnStyle(String paginatorActiveColumnStyle)
    {
        _paginatorActiveColumnStyle = paginatorActiveColumnStyle;
    }

    public String getPaginatorActiveColumnStyle()
    {
        if (_paginatorActiveColumnStyle != null)
            return _paginatorActiveColumnStyle;
        ValueBinding vb = getValueBinding("paginatorActiveColumnStyle");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    public void setRenderFacetsIfSinglePage(boolean renderFacetsIfSinglePage)
    {
        _renderFacetsIfSinglePage = Boolean.valueOf(renderFacetsIfSinglePage);
    }

    public boolean isRenderFacetsIfSinglePage()
    {
        if (_renderFacetsIfSinglePage != null)
            return _renderFacetsIfSinglePage.booleanValue();
        ValueBinding vb = getValueBinding("renderFacetsIfSinglePage");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
        return v != null ? v.booleanValue() : true;
    }

    public void setImmediate(boolean immediate)
    {
        _immediate = Boolean.valueOf(immediate);
    }

    public boolean isImmediate()
    {
        if (_immediate != null)
            return _immediate.booleanValue();
        ValueBinding vb = getValueBinding("immediate");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext()) : null;
        return v != null ? v.booleanValue() : DEFAULT_IMMEDIATE;
    }

    public void setOnclick(String onclick)
    {
        _onclick = onclick;
    }

    public String getOnclick()
    {
        if(_onclick != null)
        {
            return _onclick;
        } else
        {
            javax.faces.el.ValueBinding vb = getValueBinding("onclick");
            return vb == null ? null : _ComponentUtils.getStringValue(getFacesContext(), vb);
        }
    }    

    public void setOndblclick(String ondblclick)
    {
        _ondblclick = ondblclick;
    }

    public String getOndblclick()
    {
        if(_ondblclick != null)
        {
            return _ondblclick;
        } else
        {
            javax.faces.el.ValueBinding vb = getValueBinding("ondblclick");
            return vb == null ? null : _ComponentUtils.getStringValue(getFacesContext(), vb);
        }
    }   
    
    public Object saveState(FacesContext context)
    {
        Object values[] = new Object[32];
        values[0] = super.saveState(context);
        values[1] = _for;
        values[2] = _fastStep;
        values[3] = _pageIndexVar;
        values[4] = _pageCountVar;
        values[5] = _rowsCountVar;
        values[6] = _displayedRowsCountVar;
        values[7] = _firstRowIndexVar;
        values[8] = _lastRowIndexVar;
        values[9] = _style;
        values[10] = _styleClass;
        values[11] = _columnClasses;
        values[12] = _paginator;
        values[13] = _paginatorMaxPages;
        values[14] = _paginatorTableClass;
        values[15] = _paginatorTableStyle;
        values[16] = _paginatorColumnClass;
        values[17] = _paginatorColumnStyle;
        values[18] = _paginatorActiveColumnClass;
        values[19] = _paginatorActiveColumnStyle;
        values[20] = _paginatorRenderLinkForActive;
        values[21] = _firstStyleClass;
        values[22] = _lastStyleClass;
        values[23] = _previousStyleClass;
        values[24] = _nextStyleClass;
        values[25] = _fastfStyleClass;
        values[26] = _fastrStyleClass;
        values[27] = _renderFacetsIfSinglePage;
        values[28] = _immediate;
        values[29] = _onclick;//mh
        values[30] = _ondblclick;//mh
        values[31] = saveAttachedState(context, _actionListener);
        return values;
    }

    public void restoreState(FacesContext context, Object state)
    {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        _for = (String) values[1];
        _fastStep = (Integer) values[2];
        _pageIndexVar = (String) values[3];
        _pageCountVar = (String) values[4];
        _rowsCountVar = (String) values[5];
        _displayedRowsCountVar = (String) values[6];
        _firstRowIndexVar = (String) values[7];
        _lastRowIndexVar = (String) values[8];
        _style = (String) values[9];
        _styleClass = (String) values[10];
        _columnClasses = (String) values[11];
        _paginator = (Boolean) values[12];
        _paginatorMaxPages = (Integer) values[13];
        _paginatorTableClass = (String) values[14];
        _paginatorTableStyle = (String) values[15];
        _paginatorColumnClass = (String) values[16];
        _paginatorColumnStyle = (String) values[17];
        _paginatorActiveColumnClass = (String) values[18];
        _paginatorActiveColumnStyle = (String) values[19];
        _paginatorRenderLinkForActive = (Boolean) values[20];
        _firstStyleClass = (String) values[21];
        _lastStyleClass = (String) values[22];
        _previousStyleClass = (String) values[23];
        _nextStyleClass = (String) values[24];
        _fastfStyleClass = (String) values[25];
        _fastrStyleClass = (String) values[26];
        _renderFacetsIfSinglePage = (Boolean) values[27];
        _immediate = (Boolean) values[28];
        _onclick = (String)values[29];
        _ondblclick = (String)values[30];
        _actionListener = (MethodBinding)restoreAttachedState(context, values[31]);
    }

    //------------------ GENERATED CODE END ---------------------------------------
}
