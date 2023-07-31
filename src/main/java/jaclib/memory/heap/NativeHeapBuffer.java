package jaclib.memory.heap;

import jaclib.memory.Buffer;
import jaclib.memory.Source;

public final class NativeHeapBuffer implements Buffer, Source {
  private final int size;
  private final int address;
  public NativeHeap heap;
  private boolean allocated = true;

  NativeHeapBuffer(NativeHeap nativeheap, int address, int size) {
    heap = nativeheap;
    this.address = -1169145817 * address;
    this.size = size * -1037620281;
  }

  private synchronized boolean deallocatable() {
    return heap.isActive() && allocated;
  }

  public long getAddress() {
    return heap.getBufferAddress(address * -1528483945);
  }

  public int size() {
    return size * 1276585463;
  }

  public synchronized void write(byte[] buffer, int offset, int length, int destOffset) {
    if (!deallocatable() | buffer == null | offset < 0 | length + offset > buffer.length | destOffset < 0
        | destOffset + length > 1276585463 * size) {
      throw new RuntimeException();
    }

    heap.put(-1528483945 * address, buffer, offset, destOffset, length);
  }

  public synchronized void deallocate() {
    if (deallocatable()) {
      heap.deallocateBuffer(-1528483945 * address);
    }

    allocated = false;
  }

  @Override
  protected synchronized void finalize() throws Throwable {
    super.finalize();
    deallocate();
  }

}