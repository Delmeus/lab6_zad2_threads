import javax.swing.*;

public class MyFrame extends JFrame {

    Leaf leafPanel;
    JPanel mainPanel;

    public MyFrame(int[] args){

        mainPanel = new JPanel();
        leafPanel = new Leaf(args);

        mainPanel.add(leafPanel);

        this.add(mainPanel);
        this.pack();
        this.setTitle("Zadanie 2");
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public Leaf getLeafPanel() {
        return leafPanel;
    }
}
