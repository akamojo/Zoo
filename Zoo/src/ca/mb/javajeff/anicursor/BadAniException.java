// BadAniException.java

package ca.mb.javajeff.anicursor;

public class BadAniException extends Exception
{
   public BadAniException (String message)
   {
      super (message);
   }

   public BadAniException (Throwable throwable)
   {
      super (throwable);
   }
}
