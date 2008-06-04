package org.apache.myfaces.custom.ppr;

import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.lifecycle.Lifecycle;
import java.util.Iterator;

/**
 * A LifecycleFactory which just decorates the lifecycle passed in to the constructor to
 * allow interception on ppr requests.
 */
public class PPRLifecycleFactory extends LifecycleFactory
{
    private final LifecycleFactory delegate;

    private final PPRLifecycleWrapper pprLifecycle;

    public PPRLifecycleFactory(LifecycleFactory delegate)
    {
        this.delegate = delegate;

        pprLifecycle = new PPRLifecycleWrapper(this.delegate.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE));
    }
    
    public void addLifecycle(String lifecycleId, Lifecycle lifecycle)
    {
        this.delegate.addLifecycle(lifecycleId, lifecycle);
    }

    public Lifecycle getLifecycle(String lifecycleId)
    {
        if (LifecycleFactory.DEFAULT_LIFECYCLE.equals(lifecycleId))
        {
            return pprLifecycle;
        }

        return this.delegate.getLifecycle(lifecycleId);
    }

    public Iterator getLifecycleIds()
    {
        return this.delegate.getLifecycleIds();
    }
}
