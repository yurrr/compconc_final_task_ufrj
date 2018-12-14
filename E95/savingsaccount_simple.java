public class savingsaccount_simple{

	static int saldo;
	Lock lock1;
	Condition condition;

 	public savingsaccount_simple(int k) {
   		saldo = k;
		lock1 = new ReentrantLock();
   		condition = lock.newCondition();
  	}


	void saque(int val_saque)
	{
		lock1.lock();
		try{
			while( val_saque < saldo ){
				condition.await();
			}
			saldo = saldo - val_saque;
			// condition tbm pode ser com notifyAll acredito
			condition.signalAll();
		}finally{
			lock1.unlock();
		}

	}
	void deposito(int val_deposito)
	{
		lock1.lock();
		try{
			saldo += val_deposito;	
		}catch(Exception e){
			// ver se isso aqui esta certo
         	System.out.println("Exception occurred," + e);
		}finally{
			lock1.unlock();
		}
	}
}