
import java.util.ArrayList;
import java.util.Scanner;



public class DiningPhilosophers{
    
    int numP;
    
    public static void main(String[] args) {
        DiningPhilosophers app = new DiningPhilosophers();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of Philosophers: ");
        app.numP = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter the number of Hungry Philosophers: ");
        int num = sc.nextInt();
        sc.nextLine();
        ArrayList<Integer> hungry = new ArrayList();
        for (int i = 0; i < num; i++){
            System.out.print("Enter Hungry Philosopher " + (i+1) +"'s Position: ");
            hungry.add(sc.nextInt());
        }
        
        Philosopher philosopher[] = new Philosopher[app.numP];
        Fork fork[] = new Fork[app.numP];
        
        for (int i = 0; i < app.numP; i++){
            fork[i] = new Fork(i);
            if (hungry.contains(i))
                philosopher[i] = new Philosopher(i, true);
            else
                philosopher[i] = new Philosopher(i);
        }
        
        System.out.println("Which rule should apply: ");
        System.out.println("(A) Only one philosopher can eat at a time");
        System.out.println("(B) Two philosophers can eat at a time");
        String a = sc.next();
        sc.nextLine();
        
        int numMax = 0;
        
        if (a.equals("A") || a.equals("a")){
            numMax = 1;
        } else if (a.equals("B") || a.equals("b")){
            numMax = 2;
        }else{
            // do nothing
        }
        
        while (!hungry.isEmpty()){
            ArrayList<Integer> hasEaten = new ArrayList();
            int eats = 0;
            for (int i = 0; i < philosopher.length ; i++){
                if (hungry.contains(i)){
                    int min = Math.min(i, (i+1)%app.numP);
                    int max = Math.max(i, (i+1)%app.numP);
                    if (eats<numMax && philosopher[i].eat(fork[min], fork[max])){
                        eats++;
                        hasEaten.add(i);
                    }
                }
            }
            for (int i: hasEaten){
                philosopher[i].eat();
                hungry.remove((Object)i);
            }
            for (int i: hasEaten){
                philosopher[i].release();
            }
            hungry.stream().forEach((i) -> {
                System.out.println("p " + i + " is waiting");
            });
            if (hungry.isEmpty())
                break;
            System.out.println("---------------");
        }
        
    }
    
    public void eat(Philosopher p){
        
    }
    
}

class Philosopher implements Runnable{

    int id;
    boolean isHungry = false;
    Fork left;
    Fork right;
    
    Philosopher(int i){
        id = i;
    }
    
    Philosopher(int i, boolean hungry){
        this(i);
        isHungry = hungry;
    }
    
    @Override
    public void run() {
        //
    }

    public void start(Fork a, Fork b){
        if (isHungry){
            eat(a,b);
        }
        else{
            think();
        }
    }
    
    public boolean eat(Fork a, Fork b){
        if (a.pick()){
            if (b.pick()){
                left = a;
                right = b;
                System.out.println("Philosopher " + id + " Has Fork " + a.id);
                System.out.println("Philosopher " + id + " Has Fork " + b.id);
                // eat
                return true;
            }
            else{
                a.release();
                System.out.println(id + " Has Released " + a.id);
                return false;
            }
        }
        else{
            return false;
        }
    }
    
    public void eat(){
        System.out.println("Philosopher " + id + " Has eaten. ");
        
    }
    
    public void release(){
        left.release();
        System.out.println("Philosopher " + id + " Has Released " + left.id);
        right.release();
        System.out.println("Philosopher " + id + " Has Released " + right.id);
    }
    
    public void think(){
        //do nothing
    }
    
}

class Fork{
    
    int id;
    boolean status = false;
    
    Fork(int id){
        this.id = id;
    }
    
    public boolean pick(){
        if (!status){
            status = true;
            return true;
        }
        return false;
    }
    
    public boolean release(){
        status = false;
        return true;
    }
    
}