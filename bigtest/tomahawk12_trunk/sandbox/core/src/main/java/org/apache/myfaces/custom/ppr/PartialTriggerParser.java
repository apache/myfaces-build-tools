package org.apache.myfaces.custom.ppr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Martin Marinschek
 */
public class PartialTriggerParser
{

    public List parse(String partialTriggerString)
    {

        int lastTokenEnd = 0;
        boolean subLevelMode = false;

        List partialTriggers = new ArrayList();
        PartialTrigger currentTrigger = null;

        for (int i = 0, length = partialTriggerString.length(); i <= length; i++) {
            boolean newTokenMode = false;
            boolean endTopLevelMode = false;
            boolean endSubLevelMode = false;

            if (!(i == partialTriggerString.length())) {
                char c = partialTriggerString.charAt(i);

                if (c == ',' || c == ';' || c == ' ') {
                    newTokenMode = true;
                }
                else if (c == '(') {
                    newTokenMode = true;
                    subLevelMode = true;
                    endTopLevelMode = true;
                }
                else if (c == ')') {
                    newTokenMode = true;
                    subLevelMode = false;
                    endSubLevelMode = true;
                }
            }
            else {
                newTokenMode = true;
            }

            if (newTokenMode) {
                if (((!subLevelMode && !endSubLevelMode) || endTopLevelMode) && i > lastTokenEnd) {
                    currentTrigger = new PartialTrigger();
                    partialTriggers.add(currentTrigger);
                    currentTrigger.setPartialTriggerId(partialTriggerString.substring(lastTokenEnd, i));
                }
                else if ((subLevelMode || endSubLevelMode) && i > lastTokenEnd) {
                    currentTrigger.addEventHook(partialTriggerString.substring(lastTokenEnd, i));
                }

                lastTokenEnd = i + 1;
            }
        }

        return partialTriggers;
    }


    public static class PartialTrigger
    {
        private String partialTriggerId;
        private List eventHooks;

        public void addEventHook(String eventHook)
        {
            if (eventHooks == null) {
                eventHooks = new ArrayList();
            }
            eventHooks.add(eventHook);
        }

        public String getPartialTriggerId()
        {
            return partialTriggerId;
        }

        public void setPartialTriggerId(String partialTriggerId)
        {
            this.partialTriggerId = partialTriggerId;
        }

        public List getEventHooks()
        {
            if (eventHooks == null) {
                return Collections.EMPTY_LIST;
            }

            return eventHooks;
        }
    }

    public static void main(String[] args)
    {
        PartialTriggerParser parser = new PartialTriggerParser();
        List li = parser.parse(/*", ; ,, test1id   (onkeyup,onkeydown,,,test2id test3id;test4id"*/"testid(onchange)");

        for (int i = 0; i < li.size(); i++) {
            PartialTrigger partialTrigger = (PartialTrigger) li.get(i);
            System.out.println("partialTrigger.partialTriggerId = " + partialTrigger.getPartialTriggerId());

            for (int j = 0; j < partialTrigger.getEventHooks().size(); j++) {
                String eventHook = (String) partialTrigger.getEventHooks().get(j);
                System.out.println("eventHook = " + eventHook);
            }
        }
    }

}
