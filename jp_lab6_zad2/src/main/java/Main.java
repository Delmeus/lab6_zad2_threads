import java.util.ArrayList;

public class Main implements Runnable {

    private ArrayList<Thread> snailThreads = new ArrayList<>();

    //argumenty: wysokość, szerokość, liczba ślimaków
    public static void main(String[] args) {
        int[] arguments = new int[3];
        Main main = new Main();

        try {
            for(int i = 0; i < 3; i++){
                arguments[i] = Integer.parseInt(args[i]);
            }
        }catch (NumberFormatException e){
            for(int i = 0; i < 3; i++) arguments[i] = 0;
        }

        MyFrame frame = new MyFrame(arguments);
        main.createResourcesThread(frame.leafPanel);
        main.createSnailsThreads(arguments[2], frame.leafPanel);

    }

    private synchronized void createResourcesThread(Leaf leaf){
        Thread thread = new Thread(() ->{
            while(true) {
                try {
                    Thread.sleep(9000);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }

                for(Thread thread1 : snailThreads) {
                    try {
                        thread1.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                leaf.refreshResources();
                leaf.repaint();
            }
        });
        thread.start();
    }

    private synchronized void createSnailsThreads(int numberOfSnails, Leaf leaf){

        for(int i = 0; i < numberOfSnails; i++) {

            int currentId = i;
            Thread thread = new Thread(() -> {
                while (Thread.currentThread() == snailThreads.get(currentId)) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                    leaf.getSnails().get(currentId).move(leaf);
                    leaf.getSnails().get(currentId).eat(leaf);
                    leaf.repaint();
                }
            });
            thread.start();
            snailThreads.add(thread);
        }
    }

    @Override
    public void run() {

    }
}
