// AniCursor.java

// Create an animated cursor based on a Microsoft .ani file.

package ca.mb.javajeff.anicursor;

import java.awt.*;
import java.awt.image.*;

import java.io.*;

import java.util.*;

import javax.swing.*;

import ca.mb.javajeff.cur.*;
import ca.mb.javajeff.riff.*;

public class AniCursor implements Runnable
{
   private volatile boolean finished; // Animation completion flag

   private volatile Component comp;

   private volatile Cursor [] cursors;

   private int index;

   public AniCursor (String aniName, Component comp)
      throws BadAniException, IOException
   {
      this.comp = comp;

      if (aniName == null)
      {
          cursors = new Cursor [8];
          cursors [0] = Cursor.getPredefinedCursor (Cursor.N_RESIZE_CURSOR);
          cursors [1] = Cursor.getPredefinedCursor (Cursor.NE_RESIZE_CURSOR);
          cursors [2] = Cursor.getPredefinedCursor (Cursor.E_RESIZE_CURSOR);
          cursors [3] = Cursor.getPredefinedCursor (Cursor.SE_RESIZE_CURSOR);
          cursors [4] = Cursor.getPredefinedCursor (Cursor.S_RESIZE_CURSOR);
          cursors [5] = Cursor.getPredefinedCursor (Cursor.SW_RESIZE_CURSOR);
          cursors [6] = Cursor.getPredefinedCursor (Cursor.W_RESIZE_CURSOR);
          cursors [7] = Cursor.getPredefinedCursor (Cursor.NW_RESIZE_CURSOR);
      }
      else
      {
          RIFF riff = null;
          try
          {
              riff = new RIFF (aniName);
              if (!riff.getFormType ().equals ("ACON"))
                  throw new BadAniException ("not an animated cursor file");

              ArrayList<BufferedImage> bilist;
              bilist = new ArrayList<BufferedImage> ();

              int x = 0;
              int y = 0;

              Chunk chunk;
              while ((chunk = riff.getChunk ()) != null)
                 if (chunk.name.equals ("icon"))
                 {
                     Cur c = new Cur (new ByteArrayInputStream (chunk.data));
                     x = c.getHotspotX (0);
                     y = c.getHotspotY (0);

                     BufferedImage bi = c.getImage (0);

                     // Convert a translucent alpha channel, where alpha
                     // ranges from 0 to 255 to a binary alpha channel, where
                     // alpha is either 0 or 255.

                     if (c.getNumColors (0) == 0)
                         for (int i = 0; i < bi.getHeight (); i++)
                         {
                              int [] rgb = bi.getRGB (0, i, bi.getWidth (), 1,
                                                      null, 0,
                                                      bi.getWidth ()*4);
                              for (int j = 0; j < rgb.length; j++)
                              {
                                   int alpha = (rgb [j] >> 24) & 255;
                                   if (alpha < 0x80)
                                       alpha = 0;
                                   else
                                       alpha = 255;
                                   rgb [j] &= 0x00ffffff;
                                   rgb [j] = (alpha << 24) | rgb [j];
                              }
                              bi.setRGB (0, i, bi.getWidth (), 1, rgb, 0,
                                         bi.getWidth ()*4);
                         }
                     bilist.add (bi);

                     cursors = new Cursor [bilist.size ()];

                     Toolkit toolkit = Toolkit.getDefaultToolkit ();
                     for (int i = 0; i < bilist.size (); i++)
                          cursors [i] = toolkit.
                                        createCustomCursor (bilist.get (i),
                                                            new Point (x, y),
                                                            "anicursor");
                 }
          }
          catch (BadCurResException bcre)
          {
             throw new BadAniException (bcre);
          }
          catch (BadRIFFException briffe)
          {
             throw new BadAniException (briffe);
          }
          finally
          {
             if (riff != null)
                 riff.close ();
          }
      }
   }

   public void run ()
   {
      index = 0;

      Runnable r = new Runnable ()
                   {
                        public void run ()
                        {
                           comp.setCursor (cursors [index%cursors.length]);
                        }
                   };

      while (!finished)
      {
         try
         {
             Thread.currentThread ().sleep (75);
             SwingUtilities.invokeAndWait (r);
             index++;
         }
         catch (Exception ex)
         {
         }
      }

      try
      {
          r = new Runnable ()
              {
                  public void run ()
                  {
                     comp.setCursor (Cursor.
                                     getPredefinedCursor (Cursor.
                                                          DEFAULT_CURSOR));
                  }
              };
          SwingUtilities.invokeAndWait (r);
      }
      catch (Exception ex)
      {
      }
   }

   public void start ()
   {
      finished = false;
      new Thread (this).start ();
   }

   public void stop ()
   {
      finished = true;
   }
}
