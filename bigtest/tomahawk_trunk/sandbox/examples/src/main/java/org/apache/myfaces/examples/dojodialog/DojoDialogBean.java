package org.apache.myfaces.examples.dojodialog;

/**
 * @author Martin Marinschek
 */
public class DojoDialogBean {

    private int progress=-1;

    public boolean isActionRunning() {
        return progress>=0&&progress<14;
    }


    public int getProgress() {
        return progress;
    }

    public String startAction() {
        Thread thread = new Thread(new Runnable() {

            public void run() {
                try {
                    for(progress=0; progress<15; progress++) {
                        Thread.sleep(900);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        thread.start();

        return null;
    }
}
