/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.sys.BComponent;
import javax.baja.sys.BComponentEvent;
import javax.baja.sys.Subscriber;

/**
 * BacnetCovSubscriber handles sending a Cov notification to any
 * Cov subscribers when the point's value changes.
 *
 * @author Craig Gemmill on 10 Sep 2004
 * @since Niagara 3 Bacnet 1.0
 */
class BacnetCovSubscriber
  extends Subscriber
{
  public void subscribe(BIBacnetCovSource export, BComponent src)
  {
    sublist.put(src, export);
    subscribe(src);
  }

  public void unsubscribe(BIBacnetCovSource export, BComponent src)
  {
    sublist.remove(src);
    unsubscribe(src);
  }

////////////////////////////////////////////////////////////////
// Subscriber
////////////////////////////////////////////////////////////////

  @Override
  public void event(BComponentEvent event)
  {
    BIBacnetCovSource export = null;
    BComponent src = event.getSourceComponent();
    try
    {
      if (event.getId() == BComponentEvent.PROPERTY_CHANGED)
      {
        export = sublist.get(src);
        if (export != null)
        {
          if (event.getSlot() == export.getOutProperty())
          {
            export.checkCov();
          }
          else if (event.getSlotName().equals("loopEnable"))
          {
            export.checkCov();
          }
        }
        else
        {
          if (logger.isLoggable(Level.FINE))
          {
            logger.fine("BacnetCovSubscriber received event for unknown component: " + src);
          }
        }
      }
    }
    catch (Exception e)
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.log(Level.FINE, "Error in BacnetCovSubscriber: src=" + src + "; export=" + export, e);
      }
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private final HashMap<BComponent, BIBacnetCovSource> sublist = new HashMap<>();
  private static final Logger logger = Logger.getLogger("bacnet.server");
}
