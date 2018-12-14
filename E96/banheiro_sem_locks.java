public class banheiro_sem_locks {

// ser starvation-free
// e garantida pelo uso de métodos como o notifyAll (mesma justificativa que eu tinha escrito no banheiro com lock,
// as threads quando acordadas vao ter tentar de entrar e temos tambem a ideia de justica) e pelas mesmas razoes do banheiro_com_locks;
//
// ter exclusion-mutual
// a gente substitui o uso de um lock unico pelo synchronized e temos quase a mesma funcionalidade, entao a justificativa é, de novo,
// a mesma do banheiro_com_lock. A parte do porque "quase a mesma funcionalidade" pode ser explicado pelo trecho retirado do site abaixo.
//
// https://docs.oracle.com/javase/tutorial/essential/concurrency/syncmeth.html
// "First, it is not possible for two invocations of synchronized methods on the same object to interleave.
// When one thread is executing a synchronized method for an object, all other threads that invoke synchronized
// methods for the same object block (suspend execution) until the first thread is done with the object."
// Isso ai é basicamente o que fazemos quando estamos utilizando locks, signall, .... ( a abordagem do 96.1)


//1.Implement this class using synchronized, wait(), notify(), and
// notifyAll().

    // Lock lock = new ReentrantLock();
    int homens = 0;
    int mulheres = 0;
    int change = 0; // 0 homens e 1 mulheres

    public synchronized void enterMale() {
        try {
            // checar se já nao temos mulheres
            // se a gente tiver uma mulher esperar sair
            // se tiver mais de uma esperar todas
            while (mulheres > 0 || change == 1) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
            homens++;
        } catch (Exception e) {
        }
    }

    public synchronized void leaveMale() {
        homens--;
        change = 1;
        notifyAll();
    }


    public synchronized void enterFemale() {
        try {
            // checar se já nao temos homens
            // se a gente tiver um cabron esperar sair
            // se tiver mais de uma esperar todos
            while (homens > 0|| change == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {}
                mulheres++;
            }
        } catch (Exception e) {}
    }

    public synchronized void leaveFemale() {
        mulheres--;
        change = 0;
        notifyAll();
    }

}
