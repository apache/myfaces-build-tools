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
 * --
 * $Id$
 */
package org.apache.myfaces.custom.roundeddiv;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Transparency;
import java.awt.geom.Arc2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

/**
 * Class that generates rounded images
 * 
 * @author Andrew Robinson (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class RoundedBorderGenerator
{
    public final static int SECTION_TOPLEFT = 1;
    public final static int SECTION_TOP = 2;
    public final static int SECTION_TOPRIGHT = 3;
    public final static int SECTION_LEFT = 4;
    public final static int SECTION_CENTER = 5;
    public final static int SECTION_RIGHT = 6;
    public final static int SECTION_BOTTOMLEFT = 7;
    public final static int SECTION_BOTTOM = 8;
    public final static int SECTION_BOTTOMRIGHT = 9;
    
    private final static int CORNER_DEGREES = 3;

    private transient BufferedImage cachedImage;
    private int borderWidth;
    private int cornerRadius;
    private Color color;
    private Color background;
    private Color borderColor;
    private Dimension size;
    private boolean inverse;

    /**
     * Constructor 
     * 
     * @param borderColor Border color
     * @param borderWidth Border width
     * @param cornerRadius Corner radius
     * @param color Foreground color
     * @param background Background color
     * @param size Image size
     * @param inverse if the 3D border should be inverse (depressed look)
     */
    public RoundedBorderGenerator(Color borderColor, int borderWidth,
            int cornerRadius, Color color, Color background, Dimension size,
            boolean inverse)
    {
        this.borderWidth = borderWidth;
        this.cornerRadius = cornerRadius;
        this.color = color;
        this.background = background;
        this.borderColor = borderColor;
        this.size = size;
        this.inverse = inverse;
    }

    public void dispose()
    {
        cachedImage = null;
    }

    /**
     * @return the inverse
     */
    public boolean isInverse()
    {
        return this.inverse;
    }

    /**
     * @return the background
     */
    public Color getBackground()
    {
        return this.background;
    }

    /**
     * @return the borderColor
     */
    public Color getBorderColor()
    {
        return this.borderColor;
    }

    /**
     * @return the borderWidth
     */
    public int getBorderWidth()
    {
        return this.borderWidth;
    }

    /**
     * @return the color
     */
    public Color getColor()
    {
        return this.color;
    }

    /**
     * @return the cornerRadius
     */
    public int getCornerRadius()
    {
        return this.cornerRadius;
    }

    /**
     * @return the size
     */
    public Dimension getSize()
    {
        return this.size;
    }

    public BufferedImage createImageSection(int section)
    {
        BufferedImage img = createImage();

        int max = Math.max(borderWidth, cornerRadius);

        int x, y;
        switch (section)
        {
        case SECTION_CENTER:
            x = y = max;
            break;
        case SECTION_TOPLEFT:
            x = y = 0;
            break;
        case SECTION_TOP:
            x = max;
            y = 0;
            break;
        case SECTION_TOPRIGHT:
            x = img.getWidth() - max;
            y = 0;
            break;
        case SECTION_RIGHT:
            x = img.getWidth() - max;
            y = max;
            break;
        case SECTION_BOTTOMRIGHT:
            x = img.getWidth() - max;
            y = img.getHeight() - max;
            break;
        case SECTION_BOTTOM:
            x = max;
            y = img.getHeight() - max;
            break;
        case SECTION_BOTTOMLEFT:
            x = 0;
            y = img.getHeight() - max;
            break;
        case SECTION_LEFT:
        default:
            x = 0;
            y = max;
            break;
        }

        return img.getSubimage(x, y, max, max);
    }

    public BufferedImage createImage()
    {
        if (cachedImage != null)
            return cachedImage;

        // create the canvas image
        int w, h;
        if (size != null)
        {
            w = (int) size.getWidth();
            h = (int) size.getHeight();
        }
        else
        {
            h = w = Math.max(borderWidth, cornerRadius) * 3;
        }

        BufferedImage canvas = new BufferedImage(w, h,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = canvas.createGraphics();
        Graphics2D g2 = null;

        try
        {
            // either paint the background of the image, or set it to transparent
            paintBackground(g, w, h);

            RoundRectangle2D shape = new RoundRectangle2D.Float(0f, 0f, w, h,
                    cornerRadius * 2, cornerRadius * 2);

            BufferedImage shapedImage = createImageForShape(g, shape);
            g2 = shapedImage.createGraphics();

            fillForeground(g2, shape);

            // 3D or 2D?
            if (borderColor == null)
                create3DImage(g2, shape);
            else
                create2DImage(g2, shape);

            g.drawImage(shapedImage, 0, 0, null);

            cachedImage = canvas;
            return canvas;
        }
        finally
        {
            if (g2 != null)
                try
                {
                    g2.dispose();
                }
                catch (Exception ex)
                {
                }
            g.dispose();
        }
    }

    private BufferedImage createImageForShape(Graphics2D g,
            RoundRectangle2D shape)
    {
        int w = shape.getBounds().width;
        int h = shape.getBounds().height;

        BufferedImage img = g.getDeviceConfiguration().createCompatibleImage(w,
                h, Transparency.TRANSLUCENT);
        Graphics2D g2 = img.createGraphics();

        try
        {
            // Clear the image so all pixels have zero alpha
            g2.setComposite(AlphaComposite.Clear);
            g2.fillRect(0, 0, w, h);

            // Render our clip shape into the image. Note that we enable
            // antialiasing to achieve the soft clipping effect. Try
            // commenting out the line that enables antialiasing, and
            // you will see that you end up with the usual hard clipping.
            g2.setComposite(AlphaComposite.Src);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fill(shape);
        }
        finally
        {
            g2.dispose();
        }

        return img;
    }

    private void paintBackground(Graphics2D g, int width, int height)
    {
        // treat a null background as fully transparent
        if (background == null)
        {
            BufferedImage img = g.getDeviceConfiguration()
                    .createCompatibleImage(width, height,
                            Transparency.TRANSLUCENT);
            Graphics2D g2 = img.createGraphics();

            // Clear the image so all pixels have zero alpha
            g2.setComposite(AlphaComposite.Clear);
            g2.fillRect(0, 0, width, height);

            g2.dispose();
        }
        else
        {
            g.setColor(background);
            g.fillRect(0, 0, width, height);
        }
    }

    private void fillForeground(Graphics2D g, RoundRectangle2D shape)
    {
        // use anti-aliasing, looks like garbage without it
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setComposite(AlphaComposite.SrcAtop);
        g.setPaint(color);
        g.fill(shape);
    }

    /**
     * Create a 3D lighting effect using the "computer standard" light from the upper left
     */
    private void create3DImage(Graphics2D g, RoundRectangle2D shape)
    {
        int w = (int) shape.getWidth();
        int h = (int) shape.getHeight();

        // Create a 3D lighting effect on the border of the rectangle
        float factor = .3f;
        Color lighter = lighter(color, factor);
        Color darker = darker(color, factor);

        // top-left
        g.setClip(new Polygon(new int[] { 0, w, 0 }, new int[] { 0, 0, h }, 3));
        paint3DBorder(g, inverse ? darker : lighter, shape);

        // bottom-right
        g.setClip(new Polygon(new int[] { 0, w, w }, new int[] { h, 0, h }, 3));
        paint3DBorder(g, inverse ? lighter : darker, shape);

        // create a transition at the top-right and bottom-left colors to blend
        // where the darker & lighter colors meet
        g.setClip(null);
        int corner = Math.max(borderWidth, cornerRadius);
        paint3DBorderTransition(g, inverse ? darker : lighter,
                inverse ? lighter : darker, w - corner * 2, 0, true);
        paint3DBorderTransition(g, inverse ? darker : lighter,
                inverse ? lighter : darker, 0, h - corner * 2, false);
        g.setClip(null);
    }

    private void paint3DBorderTransition(Graphics2D g, Color c1, Color c2,
            int x, int y, boolean upperLeft)
    {
        // starting with a double-width stroke, use an algorithm that stays closer to the
        // middle color at first and then increasingly approaches outside (lighter or darker)
        // towards the end to simulate the light increasing or fading at the edges
        // (the image will clip any content outside of its bounds, so half of each stroke is lost)
        int width = borderWidth * 2;
        int size = Math.max(borderWidth, cornerRadius);
        
        int startAngle = upperLeft ? 0 : 180;
        
        for (int i = 0; i < 90; i += CORNER_DEGREES)
        {
            Color outerColor = combineColors(c1, c2, (upperLeft ? i : 90 - i) / 90f);
            g.setClip(new Arc2D.Float(x, y, size * 2, size * 2,
                    startAngle + i, CORNER_DEGREES, Arc2D.PIE));
            paint3DBorder(g, outerColor, new Arc2D.Float(x, y, size * 2, size * 2,
                    startAngle + i, CORNER_DEGREES, Arc2D.OPEN));
        }
    }

    private void paint3DBorder(Graphics2D g, Color c, Shape shape)
    {
        // starting with a double-width stroke, use an algorithm that stays closer to the
        // middle color at first and then increasingly approaches outside (lighter or darker)
        // towards the end to simulate the light increasing or fading at the edges
        // (the image will clip any content outside of its bounds, so half of each stroke is lost)
        int width = borderWidth * 2;
        for (float i = 0; i <= width; i += 1)
        {
            float percent = (float) Math.pow((i / width), 3);
            g.setPaint(combineColors(c, color, percent));
            g.setStroke(new BasicStroke(width - i));
            g.draw(shape);
        }
    }

    private void create2DImage(Graphics2D g, RoundRectangle2D shape)
    {
        if (borderWidth == 0)
            return;
        // put the border inside the shape
        RoundRectangle2D borderShape = new RoundRectangle2D.Float(0f, 0f,
                (float) shape.getWidth() - 1, (float) shape.getHeight() - 1,
                cornerRadius * 2, cornerRadius * 2);

        g.setStroke(new BasicStroke(borderWidth));
        g.setColor(borderColor);
        g.draw(borderShape);
    }

    /**
     * Averages the red, green, blue and alpha of two colors
     */
    private Color combineColors(Color c1, Color c2, float weight)
    {
        float r = c1.getRed() * weight + c2.getRed() * (1 - weight);
        float g = c1.getGreen() * weight + c2.getGreen() * (1 - weight);
        float b = c1.getBlue() * weight + c2.getBlue() * (1 - weight);
        float a = c1.getAlpha() * weight + c2.getAlpha() * (1 - weight);
        return new Color((int) r, (int) g, (int) b, (int) a);
    }

    /**
     * Similar to {@link Color#brighter()}, but this method lightens by bringing the color 
     * closer to white (not just making it brighter) by using a custom factor (instead of 0.7)
     * 
     * @param c the color to lighten
     * @param factor the factor to use (between 0 and 1)
     * @return the lighter color
     */
    private Color lighter(Color c, float factor)
    {
        return new Color(Math
                .min((int) (Math.max(c.getRed(), 50) / factor), 255), Math.min(
                (int) (Math.max(c.getGreen(), 50) / factor), 255), Math.min(
                (int) (Math.max(c.getBlue(), 50) / factor), 255));
    }

    /**
     * Like {@link Color#darker()}, this method darkens a color, but with a custom factor 
     * (instead of 0.7)
     * 
     * @param c the color to darken
     * @param factor the factor to use (between 0 and 1)
     * @return the darker color
     */
    private Color darker(Color c, float factor)
    {
        return new Color(Math.max((int) (c.getRed() * factor), 0), Math.max(
                (int) (c.getGreen() * factor), 0), Math.max(
                (int) (c.getBlue() * factor), 0));
    }

    public static void main(String[] args)
    {
        FileOutputStream fos = null;

        try
        {
            File file;
            if (args.length == 1)
            {
                file = new File(args[0]);
            }
            else
            {
                file = File.createTempFile("tmp_", ".png");
            }

            RoundedBorderGenerator rbg = new RoundedBorderGenerator(null, 10,
                    10, Color.GRAY, null, new Dimension(120,
                            60), false);
            fos = new FileOutputStream(file);

            ImageIO.write(rbg.createImage(), "png", fos);

            System.out.println("written to " + file.getAbsolutePath());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            System.exit(1);
        }
        finally
        {
            if (fos != null)
            {
                try
                {
                    fos.close();
                }
                catch (Exception ex)
                {
                }
            }
        }
    }
}