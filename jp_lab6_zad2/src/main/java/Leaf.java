import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Leaf extends JPanel {

    private final int height;
    private final int width;

    private final int[][] resources;
    private final ArrayList<Snail> snails;

    public Leaf(int[] args){
        height = args[0];
        width = args[1];

        resources = new int[height][width];
        snails = new ArrayList<>();
        Random random = new Random();

        for(int h = 0; h < height; h++){
            for(int w = 0; w < width; w++){
                resources[h][w] = random.nextInt(11);
            }
        }

        createSnails(args[2]);


        this.setPreferredSize(new Dimension(width * 50 + 2, height * 50 + 2));

        repaint();
    }

    @Override
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for(int h = 0; h < height; h++){
            for (int w = 0; w < width; w++){
                if(resources[h][w] == 0) g2d.setColor(Color.WHITE);
                else g2d.setColor(new Color(5, 255 / resources[h][w], 5));
                g2d.fillRect(w * 50, h * 50, 50, 50);
//                if(snails[h][w] != 0){
//                    g2d.setColor(Color.RED);
//                    g2d.fillOval(w * 50, h * 50, 50, 50);
//                }
                //System.out.println("[h,w] = [" + h + "," + w + "]" + " = " + resources[h][w]);
            }
        }
        g2d.setColor(Color.RED);
        for(Snail snail : snails) g2d.fillOval(snail.getY() * 50, snail.getX() * 50, 50, 50);
    }

    public void createSnails(int numberOfSnails){
        Random random = new Random();
        ArrayList<Position> positions = new ArrayList<>();
        for(int i = 0; i < numberOfSnails; i++){
            Position position = new Position(random.nextInt(height), random.nextInt(width));
            if(!positions.contains(position)) {
                positions.add(position);
                snails.add(new Snail(position.getX(), position.getY(), (random.nextInt(3) + 1) , i));
                System.out.println(position.toString());
            }
            else i--;
        }

    }

    public int[][] getResources() {
        return resources;
    }

    public void setFoodAtCoordinates(int x, int y, int consumingSpeed){
        resources[x][y] -= consumingSpeed;
        if(resources[x][y] < 0) resources[x][y] = 0;
    }


    public ArrayList<Snail> getSnails() {
        return snails;
    }


    public void refreshResources(){
        for(int h = 0; h < height; h++){
            for(int w = 0; w < width; w++){
                if(resources[h][w] < 10) resources[h][w] += 1;
            }
        }
    }

    public int getGivenHeight() {
        return height;
    }

    public int getGivenWidth() {
        return width;
    }
}
