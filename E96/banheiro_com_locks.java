public class banheiro_com_locks{
// ter exclusion-mutual
// A exclusao mutua é garantida pois sempre damos lock quando alguem entra no banheiro e 
// como só temos um lock para todos os metodos, teremos exclusao mutua.
// nao podemos ter 
//
// ser starvation-free
// A gente resolve isso com o a variavel change, tentando garantir justiça, dando preferencia
// ao sexo oposto quando alguem sai do banheiro e , também, com o signalAll, onde elas , as threads,
// que estavam em espera tentam pegar a vez. 
// a parte abaixo foi tirada de :  http://concurrencyfreaks.blogspot.com/2014/07/lock-free-is-not-starvation-free.html
// there is a property named Starvation-Free which guarantees that the method will complete if the other
// threads are able to finish. This means that as long as the thread-scheduler is fair, this method will not starve,
// i.e. its execution will not be stopped by other methods. 

//1. Implement this class using locks and condition variables.

//por classes
	Lock lock = new ReentrantLock();
	Condition condition = lock.newCondition();
	int homens = 0;
	int mulheres = 0;
  int change = 0; // 0 homens e 1 mulheres
	// 
    public void enterMale()
    {
    	lock.lock();
    	try{
    		// checar se já nao temos mulheres
    		// se a gente tiver uma mulher esperar sair
    		// se tiver mais de uma esperar todas
    		// // // // while(mulheres > 0 &&turn == Sex.Female  ){}
    		while(mulheres > 0 || change == 1 ){
    			condition.await();
    		}
		  	homens++;
    	}finally{
    		lock.unlock();
    	}
    }
   	 	
   	public void leaveMale()
   	{
   		lock.lock();
   		try{
   			homens--;
        change =  1;
		  	condition.signalAll();
      }finally{
		  	lock.unlock();
		  }
   	}
   	 	
   	// publico fenimino
    public void enterFemale()
    {
    	lock.lock();
    	try{
    		// checar se já nao temos homens
    		// se a gente tiver um cabron esperar sair
    		// se tiver mais de uma esperar todos
    		// // // // while(mulheres > 0 &&turn == Sex.Female  ){}
    		while(homens > 0  || change == 0 ){
    			condition.await();
    		}
		    mulheres++;
     	}finally{
    		lock.unlock();
    	}
    }
   	
   	public void leaveFemale()
   	{
   		lock.lock();
   		try{
   			mulheres--;
        change =  0;
		  	condition.signalAll();
      }finally{
		  	lock.unlock();
		  }
   	}

}