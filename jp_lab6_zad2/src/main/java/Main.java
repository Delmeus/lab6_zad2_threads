import javax.swing.*;
import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main implements Runnable {

    private final ArrayList<Thread> snailThreads = new ArrayList<>();

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

        if (main.ifArgsValid(arguments)) {
            MyFrame frame = new MyFrame(arguments);
            main.createResourcesThread(frame.getLeafPanel());
            main.createSnailsThreads(arguments[2], frame.getLeafPanel());
        }
        else {
            JFrame frame = new JFrame();
            frame.setVisible(false);
            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            JOptionPane.showMessageDialog(frame, "Niepoprawne argumenty!");
            System.exit(0);
        }

    }

    private void createResourcesThread(Leaf leaf){
        Thread thread = new Thread(() ->{
            while(true) {
                try {
                    Thread.sleep(9000);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }

                for(Thread thread1 : snailThreads) {
                    try {
                        thread1.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                synchronized (this) {
                    leaf.refreshResources();
                    leaf.repaint();
                    System.out.println("Refreshing");
                }

            }
        });
        thread.start();
    }

    private void createSnailsThreads(int numberOfSnails, Leaf leaf){

        for(int i = 0; i < numberOfSnails; i++) {

            int currentId = i;
            Thread thread = new Thread(() -> {
                while (Thread.currentThread() == snailThreads.get(currentId)) {

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                    synchronized (snailThreads) {
                        leaf.getSnails().get(currentId).move(leaf);
                        leaf.getSnails().get(currentId).eat(leaf);
                        leaf.refreshPositions();
                        leaf.repaint();
                    }
                }
            });
            thread.start();
            snailThreads.add(thread);
        }
    }

    private boolean ifArgsValid(int[] args){
        return args[0] > 0 && args[1] > 0 && args[2] > 0 && args[0] * args[1] > args[2];
    }

    @Override
    public void run() {

    }
}
