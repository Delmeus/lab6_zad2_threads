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


    public void move(Leaf leaf){
        int highestFoodAmount = leaf.getResources()[x][y];
        int tempX = x, tempY = y;
        ArrayList<Position> possiblePosition = new ArrayList<>();
        possiblePosition.add(new Position(x, y));

        if(x == 0){
            if(y == 0){
                for(int h = 0; h < 2; h++){
                    for(int w = 0; w < 2; w++){
                        if(leaf.getResources()[h][w] >= highestFoodAmount && canMove(leaf, h, w)){
                            highestFoodAmount = leaf.getResources()[h][w];
                            tempX = h;
                            tempY = w;
                            compareToPossiblePositions(h, w, leaf, possiblePosition);
                        }
                    }
                }
            }
            else if(y == leaf.getGivenWidth() - 1){
                for(int h = 0; h < 2; h++){
                    for(int w = leaf.getGivenWidth() - 1; w > leaf.getGivenWidth() - 3; w--){
                        if(leaf.getResources()[h][w] >= highestFoodAmount && canMove(leaf, h, w)){
                            highestFoodAmount = leaf.getResources()[h][w];
                            tempX = h;
                            tempY = w;
                            compareToPossiblePositions(h, w, leaf, possiblePosition);
                        }
                    }
                }
            }
            else {
                for(int h = 0; h < 2; h++){
                    for(int w = y - 1; w < y + 2; w++){
                        if(leaf.getResources()[h][w] >= highestFoodAmount && canMove(leaf, h, w)){
                            highestFoodAmount = leaf.getResources()[h][w];
                            tempX = h;
                            tempY = w;
                            compareToPossiblePositions(h, w, leaf, possiblePosition);
                        }
                    }
                }
            }
        }

        else if(x == leaf.getGivenHeight() - 1){
            if(y == 0){
                for(int h = leaf.getGivenHeight() - 1; h > leaf.getGivenHeight() - 3; h--){
                    for(int w = 0; w < 2; w++){
                        if(leaf.getResources()[h][w] >= highestFoodAmount && canMove(leaf, h, w)){
                            highestFoodAmount = leaf.getResources()[h][w];
                            tempX = h;
                            tempY = w;
                            compareToPossiblePositions(h, w, leaf, possiblePosition);
                        }
                    }
                }
            }
            else if(y == leaf.getGivenWidth() - 1){
                for(int h = leaf.getGivenHeight() - 1; h > leaf.getGivenHeight() - 3; h--){
                    for(int w = leaf.getGivenWidth() - 1; w > leaf.getGivenWidth() - 3; w--){
                        if(leaf.getResources()[h][w] >= highestFoodAmount && canMove(leaf, h, w)){
                            highestFoodAmount = leaf.getResources()[h][w];
                            tempX = h;
                            tempY = w;
                            compareToPossiblePositions(h, w, leaf, possiblePosition);
                        }
                    }
                }
            }
            else {
                for(int h = leaf.getGivenHeight() - 1; h > leaf.getGivenHeight() - 3; h--){
                    for(int w = y - 1; w < y + 2; w++){
                        if(leaf.getResources()[h][w] >= highestFoodAmount && canMove(leaf, h, w)){
                            highestFoodAmount = leaf.getResources()[h][w];
                            tempX = h;
                            tempY = w;
                            compareToPossiblePositions(h, w, leaf, possiblePosition);
                        }
                    }
                }
            }
        }

        else if (y == 0){
            for(int h = x; h < x + 2; h++){
                for(int w = 0; w < 2; w++){
                    if(leaf.getResources()[h][w] >= highestFoodAmount && canMove(leaf, h, w)){
                        highestFoodAmount = leaf.getResources()[h][w];
                        tempX = h;
                        tempY = w;
                        compareToPossiblePositions(h, w, leaf, possiblePosition);
                    }
                }
            }
        }

        else if (y == leaf.getGivenWidth() - 1){
            for(int h = x; h < x + 2; h++){
                for(int w = leaf.getGivenWidth() - 1; w > leaf.getGivenWidth() - 3; w--){
                    if(leaf.getResources()[h][w] >= highestFoodAmount && canMove(leaf, h, w)){
                        highestFoodAmount = leaf.getResources()[h][w];
                        tempX = h;
                        tempY = w;
                        compareToPossiblePositions(h, w, leaf, possiblePosition);
                    }
                }
            }
        }

        else {
            for(int h = x - 1; h < x + 2; h++){
                for(int w = y - 1; w < y + 2; w++){
                    if(leaf.getResources()[h][w] >= highestFoodAmount && canMove(leaf, h, w)){
                        highestFoodAmount = leaf.getResources()[h][w];
                        tempX = h;
                        tempY = w;
                        compareToPossiblePositions(h, w, leaf, possiblePosition);
                    }
                }
            }
        }

        if(possiblePosition.size() > 1){
            Random random = new Random();
            int randomIndex = random.nextInt(possiblePosition.size());
            tempX = possiblePosition.get(randomIndex).getX();
            tempY = possiblePosition.get(randomIndex).getY();
        }

        leaf.getSnails().get(id).setX(tempX);
        leaf.getSnails().get(id).setY(tempY);
        x = tempX;
        y = tempY;

    }

    public boolean canMove(Leaf leaf, int x, int y) {
        if(x >= 0 && y >= 0 && x < leaf.getGivenHeight() && y < leaf.getGivenWidth()){
            for(Snail snail : leaf.getSnails()){
                if(snail.getX() == x && snail.getY() == y && snail.getId() != this.getId()){
                    return false;
                }
            }
            return true;
        }
        else return false;
    }

    private void compareToPossiblePositions(int h, int w, Leaf leaf, ArrayList<Position> possiblePosition){
        if(leaf.getResources()[h][w] > leaf.getResources()[possiblePosition.get(0).getX()][possiblePosition.get(0).getY()]){
            possiblePosition.clear();
            possiblePosition.add(new Position(h, w));
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }
}
