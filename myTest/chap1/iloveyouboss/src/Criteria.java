import java.util.ArrayList;
import java.util.List;
import java.util.*;
public class Criteria implements Iterable<Criterion>{
    private List<Criterion>crieria = new ArrayList<>();
    public void add(Criterion criterion){
        crieria.add(criterion);
    }

    @Override
    public Iterator<Criterion> iterator() {
        return crieria.iterator();
    }
    public int arithmeticMean(){
        return 0;
    }
    public double geometricMean(int[] numbers){
        int totalProduct = Arrays.stream(numbers).reduce(1, (product, number)-> product*number);
        return Math.pow(totalProduct, 1.0/numbers.length);
    }
}
