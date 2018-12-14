import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class savingsaccount_preferred {
    static int saldo;
    Lock lock1;
    Condition condition;
    int qtdePreferenciais;

    public savingsaccount_preferred(int k) {
        saldo = k;
        lock1 = new ReentrantLock();
        condition = lock1.newCondition();
    }

    void saque(int pref,int val_saque)
    {
        lock1.lock();
        // pode ter mais de um saque preferencial
        // a gente tem que fazer enquanto tiver preferencial
        // depois dos saques preferencias fazemos a ordem
        // comum

        try {
            if (pref > 0) {
                qtdePreferenciais++;
                while (val_saque < saldo)
                    condition.await();

                // indica que o saque preferencial foi feita
                qtdePreferenciais--;

            } else {
                // verifica se ainda tem em alguma outra thread
                // espera ate ela receber o sinal
                while (val_saque < saldo)
                    condition.await();
            }
            // o codigo abaixo pode ser finally
            saldo = saldo - val_saque;
            condition.signalAll();
        } catch (Exception e) {

        } finally {
            lock1.unlock();
        }

    }
    void deposito(int val_deposito)
    {
        lock1.lock();
        try{
            saldo += val_deposito;
        }catch (Exception e) {
            // ver se isso aqui esta certo
            System.out.println("Exception occurred," + e);
        }finally{
            lock1.unlock();
        }
    }
}
