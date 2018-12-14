package monitor;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedSimpleReadWriteLock implements ReadWriteLock {
  
  int readers;
  boolean writer;
  Lock lock1;
  Lock readLock;
  Lock writeLock;
  // Condition condition;
  
  public SynchronizedSimpleReadWriteLock() {
    writer = false;
    readers = 0;
    lock1 = new ReentrantLock();
    readLock = new ReadLock();
    writeLock = new WriteLock();
    // condition = lock.newCondition();
  }
  public Lock readLock() {
    return readLock;
  }
  public Lock writeLock() {
    return writeLock;
  }
  class ReadLock implements Lock 
  {
    
    public void lock()
    {
      // https://www.baeldung.com/java-synchronized
    	//  avoid race conditions, link acima

      synchronized(lock1){
		    try {
        	while (writer) {
          		try {
          	 	lock1.await();
          		} catch (InterruptedException e) {}
          }
          readers++;
        }

      }
      
    }
    public void unlock()
    {
    	synchronized(lock1){
       	 	readers--;
       	 	if (readers == 0)	
            lock1.notifyAll();
    	}
    }
    
    public void lockInterruptibly() throws InterruptedException {
      throw new UnsupportedOperationException();
    }
    
    public boolean tryLock() {
      throw new UnsupportedOperationException();
    }
    
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
      throw new UnsupportedOperationException();
    }
    
    public Condition newCondition() {
      throw new UnsupportedOperationException();
    }
  }
  protected class WriteLock implements Lock 
  {
    public void lock() 
    {
      synchronized(lock1){
      	try {
          // so deixa o escritor qndo nao tiver mais leitor ou escritor
          // ai registra um novo
        	while (readers > 0  || writer ) { 
        	  try {
           		 lock.wait(); 
          		} catch (InterruptedException e) {}
        	}
          writer = true;
      	}
      }

    }
    public void unlock() {
      writer = false;
      lock1.notifyAll();
    }
    
    public void lockInterruptibly() throws InterruptedException {
      throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public boolean tryLock() {
      throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
      throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public Condition newCondition() {
      throw new UnsupportedOperationException("Not supported yet.");
    }
  }
  
  public void lockInterruptibly() throws InterruptedException {
    throw new UnsupportedOperationException();
  }
  
  public boolean tryLock() {
    throw new UnsupportedOperationException();
  }
  
  public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
    throw new UnsupportedOperationException();
  }
  
  public Condition newCondition() {
    throw new UnsupportedOperationException();
  }
}
