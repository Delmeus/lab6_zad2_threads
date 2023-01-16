import java.util.ArrayList;
import java.util.Random;

public class Snail {

    private int x;
    private int y;
    private final int consumingSpeed;
    private final int id;

    public Snail(int x, int y, int consumingSpeed, int id){
        this.x = x;
        this.y = y;
        this.consumingSpeed = consumingSpeed;
        this.id = id;
    }

    public void eat(Leaf leaf){
        leaf.setFoodAtCoordinates(x, y, consumingSpeed);
    }


    public synchronized void move(Leaf leaf){

        ArrayList<Position> possiblePosition = checkAdjacentBoxes(leaf);

        if(!possiblePosition.isEmpty()) {
            int sameFoodPlaces = 1;
            int highestFoodAmount = possiblePosition.get(0).getFood();

            for (int i = 0; i < possiblePosition.size() - 1; i++) {
                if (possiblePosition.get(i).getFood() == highestFoodAmount && possiblePosition.get(i).getFood() == possiblePosition.get(i + 1).getFood()) sameFoodPlaces++;
            }

            if (sameFoodPlaces > 1) {
                Random random = new Random();
                int index = random.nextInt(sameFoodPlaces);
                x = possiblePosition.get(index).getX();
                y = possiblePosition.get(index).getY();
            } else {
                x = possiblePosition.get(0).getX();
                y = possiblePosition.get(0).getY();
            }
        }

        System.out.println("Snail " + id + " decided to go to " + "x = " + x + ", y = " + y);
    }

    private synchronized ArrayList<Position> checkAdjacentBoxes(Leaf leaf){
        ArrayList<Position> PossiblePositions = new ArrayList<>();
        for(int i = x - 1; i <= x+1; i++){
            for (int j = y - 1; j <= y+1; j++){
                if(i >= 0 && i < leaf.getGivenHeight() && j >=0 && j < leaf.getGivenWidth()){
                    Position position = new Position(i,j, leaf.getResources()[i][j]);
                    if(leaf.isPositionFree(i, j, this)) PossiblePositions.add(position);
                }
            }
        }

        if(PossiblePositions.size() > 1) {
            for (int i = 0; i < PossiblePositions.size() - 1; i++) {
                for (int j = 0; j < PossiblePositions.size() - 1; j++) {
                    if (PossiblePositions.get(j).getFood() < PossiblePositions.get(j + 1).getFood()) {
                        Position p = PossiblePositions.get(j);
                        PossiblePositions.set(j, PossiblePositions.get(j + 1));
                        PossiblePositions.set(j + 1, p);
                    }
                }
            }
        }
        return PossiblePositions ;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
