import java.util.*;

import static java.util.stream.Collectors.toSet;

public class Yatzy {

    public static int chance(int d1, int d2, int d3, int d4, int d5)
    {
        int total = d1+d2+d3+d4+d5;
        return total;
    }

    public static int yatzy(int... dice)
    {
        int sameResult = dice[0];
        for (int result : dice){
            if (sameResult != result) return 0;
        }
        return 50;
    }

    public static int ones(int d1, int d2, int d3, int d4, int d5) {
        return calcMonoValueScore(1,new int[]{d1, d2, d3, d4, d5});
    }

    public static int twos(int d1, int d2, int d3, int d4, int d5) {
        return calcMonoValueScore(2,new int[]{d1, d2, d3, d4, d5});
    }

    public static int threes(int d1, int d2, int d3, int d4, int d5) {
        return calcMonoValueScore(3,new int[]{d1, d2, d3, d4, d5});
    }

    private static int calcMonoValueScore(int target,int[] fiveDices)
    {
        return Arrays.stream(fiveDices).filter(d -> d == target).reduce(0, (carry, value) -> carry + value);
    }

    protected int[] dice;
    public Yatzy(int d1, int d2, int d3, int d4, int _5)
    {
        dice = new int[5];
        dice[0] = d1;
        dice[1] = d2;
        dice[2] = d3;
        dice[3] = d4;
        dice[4] = _5;
    }

    public int fours()
    {
        return calcMonoValueScore(4,dice);
    }

    public int fives()
    {
        return calcMonoValueScore(5,dice);
    }

    public int sixes()
    {
        return calcMonoValueScore(6,dice);
    }

    public static int score_pair(int d1, int d2, int d3, int d4, int d5)
    {
        final List<Integer> results = Arrays.asList(d1, d2, d3, d4, d5);
        orderDesc(results);
        for(int i = 0; i<4; i++)
       {
          if (results.get(i)==results.get(i+1)) return results.get(i)*2;
       }
       return 0;
    }

    private static void orderDesc(List<Integer> results)
    {
        results.sort((p1, p2) -> p1 < p2 ? 1 : -1);
    }
    private static void orderAsc(List<Integer> results)
    {
        results.sort((p1, p2) -> p1 < p2 ? -1 : 1);
    }
    public static int two_pair(int d1, int d2, int d3, int d4, int d5)
    {
        int numberOfOccurenceExpected= 2;
        final List<Integer> results = Arrays.asList(d1, d2, d3, d4, d5);
        return applyRuleOfTuples(numberOfOccurenceExpected, results);
    }

    public static int four_of_a_kind(int d1, int d2, int d3, int d4, int d5)
    {
        int numberOfOccurence= 4;
        final List<Integer> results = Arrays.asList(d1, d2, d3, d4, d5);
        return applyRuleOfTuples(numberOfOccurence, results);
    }

    public static int three_of_a_kind(int d1, int d2, int d3, int d4, int d5)
    {
        int numberOfOccurence= 3;
        final List<Integer> results = Arrays.asList(d1, d2, d3, d4, d5);
        return applyRuleOfTuples(numberOfOccurence, results);
    }

    private static int applyRuleOfTuples(int numberOfOccurenceExpected, List<Integer> results)
    {
        orderDesc(results);
        List<Integer> tuplesFounded = new ArrayList<>();
        for(int i = 0; i<results.size(); i++)
        {
            final int numberOfOccurences = countInArray(results.get(i), results);
            if (numberOfOccurences >= numberOfOccurenceExpected || numberOfOccurences == numberOfOccurenceExpected*2)
            {
                tuplesFounded.add(results.get(i));
                results = results.subList(numberOfOccurenceExpected,results.size());
            }
            else
            {
                results = results.subList(1,results.size());
            }

        }
        return tuplesFounded.stream().reduce(0,(carry,elt)->carry+(elt*numberOfOccurenceExpected));
    }

    private static int countInArray(Integer integer, List<Integer> results)
    {
        return results.stream().filter(i->i == integer).toArray().length;
    }

    public static int smallStraight(int d1, int d2, int d3, int d4, int d5)
    {
        final List<Integer> results = Arrays.asList(d1, d2, d3, d4, d5);
        orderAsc(results);
        if (results.equals(Arrays.asList(1, 2, 3, 4, 5))) return 15;
        return 0;
    }

    public static int largeStraight(int d1, int d2, int d3, int d4, int d5)
    {
        final List<Integer> results = Arrays.asList(d1, d2, d3, d4, d5);
        orderAsc(results);
        if (results.equals(Arrays.asList(2, 3, 4, 5,6))) return 20;
        return 0;
    }

    public static int fullHouse(int d1, int d2, int d3, int d4, int d5)
    {
        final List<Integer> results = Arrays.asList(d1, d2, d3, d4, d5);
        if (results.stream().collect(toSet()).size()!=2) return 0;
        if (countInArray(results.get(0), results)==2 || countInArray(results.get(0), results)==3){
            return results.stream().reduce(0,(carry,elt)->carry+elt).intValue();
        }
        else return 0;
    }
}



