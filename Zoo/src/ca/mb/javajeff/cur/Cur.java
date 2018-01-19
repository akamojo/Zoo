// Cur.java

// Handles 2-color, 16-color, 256-color, and 32-bit color images of any width
// and height.

// Handles .cur files with either BITMAPHEADERs or PNGs.

package ca.mb.javajeff.cur;

import java.awt.image.*;

import java.io.*;

import java.net.*;

import javax.imageio.*;

public class Cur
{
   private final static int FDE_OFFSET = 6; // first directory entry offset
   private final static int DE_LENGTH = 16; // directory entry length

   private final static int BMIH_LENGTH = 40; // BITMAPINFOHEADER length

   private byte [] curimage = new byte [0]; // new byte [0] facilitates read()

   private int numImages;

   private BufferedImage [] bi;

   private int [] colorCount, hotspotX, hotspotY;

   public Cur (File file) throws BadCurResException, IOException
   {
      this (file.getAbsolutePath ());
   }

   public Cur (InputStream is) throws BadCurResException, IOException
   {
      try
      {
          read (is);
          parseCURImage ();
      }
      finally
      {
          try
          {
              is.close ();
          }
          catch (IOException ioe)
          {
          }
      }
   }

   public Cur (String filename) throws BadCurResException, IOException 
   {
      this (new FileInputStream (filename));
   }

   public Cur (URL url) throws BadCurResException, IOException
   {
      this (url.openStream ());
   }

   public int getHotspotX (int index)
   {
      if (index < 0 || index >= numImages)
          throw new IllegalArgumentException ("index out of range");

      return hotspotX [index];
   }

   public int getHotspotY (int index)
   {
      if (index < 0 || index >= numImages)
          throw new IllegalArgumentException ("index out of range");

      return hotspotY [index];
   }

   public BufferedImage getImage (int index)
   {
      if (index < 0 || index >= numImages)
          throw new IllegalArgumentException ("index out of range");

     return bi [index];
   }

   public int getNumColors (int index)
   {
      if (index < 0 || index >= numImages)
          throw new IllegalArgumentException ("index out of range");

     return colorCount [index];
   }

   public int getNumImages ()
   {
      return numImages;
   }

   private int calcScanlineBytes (int width, int bitCount)
   {
      // Calculate minimum number of double-words required to store width
      // pixels where each pixel occupies bitCount bits. XOR and AND bitmaps
      // are stored such that each scanline is aligned on a double-word
      // boundary.

      return (((width*bitCount)+31)/32)*4;
   }

   private void parseCURImage () throws BadCurResException, IOException
   {
      // Check resource type field.

      if (curimage [2] != 2 || curimage [3] != 0)
          throw new BadCurResException ("Not a CUR resource");

      numImages = ubyte (curimage [5]);
      numImages <<= 8;
      numImages |= curimage [4];

      bi = new BufferedImage [numImages];

      colorCount = new int [numImages];
      hotspotX = new int [numImages];
      hotspotY = new int [numImages];

      for (int i = 0; i < numImages; i++)
      {
           int width = ubyte (curimage [FDE_OFFSET+i*DE_LENGTH]);

           int height = ubyte (curimage [FDE_OFFSET+i*DE_LENGTH+1]);

           colorCount [i] = ubyte (curimage [FDE_OFFSET+i*DE_LENGTH+2]);

           hotspotX [i] = ubyte (curimage [FDE_OFFSET+i*DE_LENGTH+5]);
           hotspotX [i] <<= 8;
           hotspotX [i] |= ubyte (curimage [FDE_OFFSET+i*DE_LENGTH+4]);

           hotspotY [i] = ubyte (curimage [FDE_OFFSET+i*DE_LENGTH+7]);
           hotspotY [i] <<= 8;
           hotspotY [i] |= ubyte (curimage [FDE_OFFSET+i*DE_LENGTH+6]);

           int bytesInRes = ubyte (curimage [FDE_OFFSET+i*DE_LENGTH+11]);
           bytesInRes <<= 8;
           bytesInRes |= ubyte (curimage [FDE_OFFSET+i*DE_LENGTH+10]);
           bytesInRes <<= 8;
           bytesInRes |= ubyte (curimage [FDE_OFFSET+i*DE_LENGTH+9]);
           bytesInRes <<= 8;
           bytesInRes |= ubyte (curimage [FDE_OFFSET+i*DE_LENGTH+8]);

           int imageOffset = ubyte (curimage [FDE_OFFSET+i*DE_LENGTH+15]);
           imageOffset <<= 8;
           imageOffset |= ubyte (curimage [FDE_OFFSET+i*DE_LENGTH+14]);
           imageOffset <<= 8;
           imageOffset |= ubyte (curimage [FDE_OFFSET+i*DE_LENGTH+13]);
           imageOffset <<= 8;
           imageOffset |= ubyte (curimage [FDE_OFFSET+i*DE_LENGTH+12]);

           if (curimage [imageOffset] == 40 &&
               curimage [imageOffset+1] == 0 &&
               curimage [imageOffset+2] == 0 &&
               curimage [imageOffset+3] == 0)
           {
               // BITMAPINFOHEADER detected

               int _width = ubyte (curimage [imageOffset+7]);
               _width <<= 8;
               _width |= ubyte (curimage [imageOffset+6]);
               _width <<= 8;
               _width |= ubyte (curimage [imageOffset+5]);
               _width <<= 8;
               _width |= ubyte (curimage [imageOffset+4]);

               // If width is 0 (for 256 pixels or higher), _width contains
               // actual width.

               if (width == 0)
                   width = _width;

               int _height = ubyte (curimage [imageOffset+11]);
               _height <<= 8;
               _height |= ubyte (curimage [imageOffset+10]);
               _height <<= 8;
               _height |= ubyte (curimage [imageOffset+9]);
               _height <<= 8;
               _height |= ubyte (curimage [imageOffset+8]);

               // If height is 0 (for 256 pixels or higher), _height contains
               // actual height times 2.

               if (height == 0)
                   height = _height >> 1; // Divide by 2.

               int planes = ubyte (curimage [imageOffset+13]);
               planes <<= 8;
               planes |= ubyte (curimage [imageOffset+12]);

               int bitCount = ubyte (curimage [imageOffset+15]);
               bitCount <<= 8;
               bitCount |= ubyte (curimage [imageOffset+14]);

               // If colorCount [i] is 0, the number of colors is determined
               // from the planes and bitCount values. For example, the number
               // of colors is 256 when planes is 1 and bitCount is 8. Leave
               // colorCount [i] set to 0 when planes is 1 and bitCount is 32.

               if (colorCount [i] == 0)
               {
                   if (planes == 1 && bitCount == 1)
                       colorCount [i] = 2;
                   else
                   if (planes == 1 && bitCount == 4)
                       colorCount [i] = 16;
                   else
                   if (planes == 1 && bitCount == 8)
                       colorCount [i] = 256;
                   else
                   if (planes != 1 && bitCount != 32)
                       throw new BadCurResException ("invalid color count");
               }

               bi [i] = new BufferedImage (width, height,
                                           BufferedImage.TYPE_INT_ARGB);

               // Parse image to image buffer.

               int colorTableOffset = imageOffset+BMIH_LENGTH;

               if (colorCount [i] == 2)
               {
                   int xorImageOffset = colorTableOffset+2*4;

                   int scanlineBytes = calcScanlineBytes (width, 1);
                   int andImageOffset = xorImageOffset+scanlineBytes*height;

                   int [] masks = { 128, 64, 32, 16, 8, 4, 2, 1 };

                   for (int row = 0; row < height; row++)
                        for (int col = 0; col < width; col++)
                        {
                             int index;

                             if ((ubyte (curimage [xorImageOffset+row*
                                                   scanlineBytes+col/8])
                                 & masks [col%8]) != 0)
                                 index = 1;
                             else
                                 index = 0;

                             int rgb = 0;
                             rgb |= (ubyte (curimage [colorTableOffset+index*4
                                                      +2]));
                             rgb <<= 8;
                             rgb |= (ubyte (curimage [colorTableOffset+index*4
                                                      +1]));
                             rgb <<= 8;
                             rgb |= (ubyte (curimage [colorTableOffset+index*
                                                      4]));

                             if ((ubyte (curimage [andImageOffset+row*
                                                   scanlineBytes+col/8])
                                 & masks [col%8]) != 0)
                                 bi [i].setRGB (col, height-1-row, rgb);
                             else
                                 bi [i].setRGB (col, height-1-row,
                                                0xff000000 | rgb);
                        }
               }
               else
               if (colorCount [i] == 16)
               {
                   int xorImageOffset = colorTableOffset+16*4;

                   int scanlineBytes = calcScanlineBytes (width, 4);
                   int andImageOffset = xorImageOffset+scanlineBytes*height;

                   int [] masks = { 128, 64, 32, 16, 8, 4, 2, 1 };

                   for (int row = 0; row < height; row++)
                        for (int col = 0; col < width; col++)
                        {
                             int index;
                             if ((col & 1) == 0) // even
                             {
                                 index = ubyte (curimage [xorImageOffset+row*
                                                          scanlineBytes+col/2]);
                                 index >>= 4;
                             }
                             else
                             {
                                 index = ubyte (curimage [xorImageOffset+row*
                                                          scanlineBytes+col/2])
                                                &15;
                             }

                             int rgb = 0;
                             rgb |= (ubyte (curimage [colorTableOffset+index*4
                                                      +2]));
                             rgb <<= 8;
                             rgb |= (ubyte (curimage [colorTableOffset+index*4
                                                      +1]));
                             rgb <<= 8;
                             rgb |= (ubyte (curimage [colorTableOffset+index*
                                                      4]));

                             if ((ubyte (curimage [andImageOffset+row*
                                                   calcScanlineBytes (width, 1)
                                                   +col/8]) & masks [col%8])
                                 != 0)
                                 bi [i].setRGB (col, height-1-row, rgb);
                             else
                                 bi [i].setRGB (col, height-1-row,
                                                0xff000000 | rgb);
                        }
               }
               else
               if (colorCount [i] == 256)
               {
                   int xorImageOffset = colorTableOffset+256*4;

                   int scanlineBytes = calcScanlineBytes (width, 8);
                   int andImageOffset = xorImageOffset+scanlineBytes*height;

                   int [] masks = { 128, 64, 32, 16, 8, 4, 2, 1 };

                   for (int row = 0; row < height; row++)
                        for (int col = 0; col < width; col++)
                        {
                             int index;
                             index = ubyte (curimage [xorImageOffset+row*
                                                      scanlineBytes+col]);

                             int rgb = 0;
                             rgb |= (ubyte (curimage [colorTableOffset+index*4
                                                      +2]));
                             rgb <<= 8;
                             rgb |= (ubyte (curimage [colorTableOffset+index*4
                                                      +1]));
                             rgb <<= 8;
                             rgb |= (ubyte (curimage [colorTableOffset+index*4
                                                      ]));

                             if ((ubyte (curimage [andImageOffset+row*
                                                   calcScanlineBytes (width, 1)
                                                   +col/8]) & masks [col%8])
                                 != 0)
                                 bi [i].setRGB (col, height-1-row, rgb);
                             else
                                 bi [i].setRGB (col, height-1-row,
                                                0xff000000 | rgb);
                        }
               }
               else
               if (colorCount [i] == 0)
               {
                   int scanlineBytes = calcScanlineBytes (width, 32);

                   for (int row = 0; row < height; row++)
                        for (int col = 0; col < width; col++)
                        {
                             int rgb = ubyte (curimage [colorTableOffset+row*
                                                        scanlineBytes+col*4+3]);
                             rgb <<= 8;
                             rgb |= ubyte (curimage [colorTableOffset+row*
                                                     scanlineBytes+col*4+2]);
                             rgb <<= 8;
                             rgb |= ubyte (curimage [colorTableOffset+row*
                                                     scanlineBytes+col*4+1]);
                             rgb <<= 8;
                             rgb |= ubyte (curimage [colorTableOffset+row*
                                                     scanlineBytes+col*4]);

                             bi [i].setRGB (col, height-1-row, rgb);
                        }
               }
           }
           else
           if (ubyte (curimage [imageOffset]) == 0x89 &&
               curimage [imageOffset+1] == 0x50 &&
               curimage [imageOffset+2] == 0x4e &&
               curimage [imageOffset+3] == 0x47 &&
               curimage [imageOffset+4] == 0x0d &&
               curimage [imageOffset+5] == 0x0a &&
               curimage [imageOffset+6] == 0x1a &&
               curimage [imageOffset+7] == 0x0a)
           {
               // PNG detected

               ByteArrayInputStream bais;
               bais = new ByteArrayInputStream (curimage, imageOffset,
                                                bytesInRes); 
               bi [i] = ImageIO.read (bais);
           }
           else
               throw new BadCurResException ("BITMAPINFOHEADER or PNG "+
                                             "expected");
      }

      curimage = null; // This array can now be garbage collected.
   }

   private void read (InputStream is) throws IOException
   {
      int bytesToRead;
      while ((bytesToRead = is.available ()) != 0)
      {
         byte [] curimage2 = new byte [curimage.length+bytesToRead];
         System.arraycopy (curimage, 0, curimage2, 0, curimage.length);
         is.read (curimage2, curimage.length, bytesToRead);         
         curimage = curimage2;
      }
   }

   private int ubyte (byte b)
   {
      return (b < 0) ? 256+b : b; // Convert byte to unsigned byte.
   }
}
