// RIFF.java

package ca.mb.javajeff.riff;

import java.io.*;

import java.net.*;

public class RIFF
{
   private static int CKHEADER_SIZE = 8;

   private byte [] ckheader;

   private InputStream is;

   private long riffSize;

   private String formType;

   public RIFF (File file) throws BadRIFFException, IOException
   {
      this (file.getAbsolutePath ());
   }

   public RIFF (InputStream is) throws BadRIFFException, IOException
   {
      try
      {
          this.is = is;

          ckheader = new byte [CKHEADER_SIZE];
          is.read (ckheader);

          if (ckheader [0] != 'R' || ckheader [1] != 'I' || ckheader [2] != 'F'
              || ckheader [3] != 'F')
          {
              throw new BadRIFFException ("RIFF ID expected");
          }

          riffSize = ubyte (ckheader [7]);
          riffSize <<= 8;
          riffSize |= ubyte (ckheader [6]);
          riffSize <<= 8;
          riffSize |= ubyte (ckheader [5]);
          riffSize <<= 8;
          riffSize |= ubyte (ckheader [4]);

          // Read formType field.

          is.read (ckheader, 0, 4);

          riffSize -= 4; // Account for formType field.
          if (riffSize <= 0)
              throw new BadRIFFException ("RIFF file corrupt");
          
          this.formType = new String (ckheader, 0, 4);
      }
      catch (BadRIFFException briffe)
      {
          close ();
          throw briffe;
      }
      catch (IOException ioe)
      {
          close ();
          throw ioe;
      }
   }

   public RIFF (String filename) throws BadRIFFException, IOException 
   {
      this (new FileInputStream (filename));
   }

   public RIFF (URL url) throws BadRIFFException, IOException
   {
      this (url.openStream ());
   }

   public void close ()
   {
      try
      {
         is.close ();
      }
      catch (IOException ioe)
      {
      }
   }

   public Chunk getChunk () throws BadRIFFException, IOException
   {
      if (riffSize == 0)
          return null;

      is.read (ckheader);

      riffSize -= CKHEADER_SIZE;
      if (riffSize <= 0)
          throw new BadRIFFException ("RIFF file corrupt");

      Chunk chunk = new Chunk ();
      chunk.name = new String (ckheader, 0, 4);
      chunk.size = ubyte (ckheader [7]);
      chunk.size <<= 8;
      chunk.size |= ubyte (ckheader [6]);
      chunk.size <<= 8;
      chunk.size |= ubyte (ckheader [5]);
      chunk.size <<= 8;
      chunk.size |= ubyte (ckheader [4]);

      if (!chunk.name.equals ("LIST"))
      {
          chunk.data = new byte [(int) chunk.size];
          is.read (chunk.data);

          riffSize -= chunk.size;
          if (riffSize < 0)
              throw new BadRIFFException ("RIFF file corrupt");

          // Account for padding byte on odd chunk sizes.

          if ((chunk.size & 1) != 0)
          {
              is.skip (1);

              riffSize--;
              if (riffSize < 0)
                  throw new BadRIFFException ("RIFF file corrupt");
          }

          return chunk;
      }

      // Read listType field.

      is.read (ckheader, 0, 4);

      riffSize -= 4; // Account for listType field.
      if (riffSize <= 0)
          throw new BadRIFFException ("RIFF file corrupt");

      chunk.data = new byte [4];
      chunk.data [0] = ckheader [0];
      chunk.data [1] = ckheader [1];
      chunk.data [2] = ckheader [2];
      chunk.data [3] = ckheader [3];

      return chunk;
   }

   public String getFormType ()
   {
      return formType;
   }

   private int ubyte (byte b)
   {
      return (b < 0) ? 256+b : b; // Convert byte to unsigned byte.
   }
}
