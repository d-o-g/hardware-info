package jaclib.ping;

public abstract class IcmpService implements Runnable {

  protected IcmpService() {

  }

  public static native boolean available();

  public native void e();

  public native void o();

  public native void quit();

  public native void run();

  protected abstract void notify(int dummy);

  protected abstract void notify(int point, int type, int flags);

}