package com.m3.csalgorithms.dynamic;

import java.util.ArrayList;
import java.util.List;

public class MaximumCostCombination {

    private final List<List<Pair>> _result = new ArrayList<List<Pair>>();
    private final int maxsize;

    private MaximumCostCombination(int maxvalue) {
        maxsize = maxvalue;
    }

    public static void main(String[] args) {
        MaximumCostCombination combos = new MaximumCostCombination(9);
        List<Pair> mydata = new ArrayList<Pair>();
        mydata.add(new Pair(4, 4));
        mydata.add(new Pair(3, 7));
        mydata.add(new Pair(5, 5));
        mydata.add(new Pair(2, 6));
        mydata.add(new Pair(3, 5));
        mydata.add(new Pair(2, 4));
        List<Pair> myresult = new ArrayList<Pair>();
        combos.combinations(myresult, mydata);
        for (List<Pair> seq : combos._result) {
            System.out.println(printList(seq));
        }
        Pair maxcost = combos.maximumCostSequence();
        System.out.println("Max cost is: [" + maxcost.cost + "] found in combination [ " + 
                            printList(combos._result.get(maxcost.size)) + " ]");
    }

    public Pair maximumCostSequence() {
        int maxcost = 0;
        int maxindex = -1;
        for (int ix = 0; ix < _result.size(); ix++) {
            List<Pair> pairs = _result.get(ix);
            int cost = 0;
            for (Pair pr : pairs) {
                cost += pr.cost;
            }
            if (cost > maxcost) {
                maxcost = cost;
                maxindex = ix;
            }
        }
        return new Pair(maxindex, maxcost);
    }

    public void combinations(List<Pair> target, List<Pair> data) {
        for (int ix = 0; ix < data.size(); ix++) {
            List<Pair> new_target = copyList(target, 0);
            new_target.add(data.get(ix));
            int tgtsize = totalSize(new_target);
            if (tgtsize < maxsize) {
                List<Pair> new_data = copyList(data, ix+1);
                _result.add(new_target);
                combinations(new_target, new_data);
            }
        }
    }

    public List<Pair> copyList(List<Pair> pairs, int fromindex) {
    	List<Pair> result = new ArrayList<Pair>();
        for (int ix = fromindex; ix < pairs.size(); ix ++) {
        	try {
                result.add((Pair)pairs.get(ix).clone());
        	} catch (CloneNotSupportedException ex) {
        	    // do nothing
        	}
        }
        return result;
    }

    public int totalSize(List<Pair> pairs) {
        int result = 0;
        for (Pair pr : pairs) {
            result += pr.size;
        }
        return result;
    }

    private static String printList(List<Pair> seq) {
        StringBuilder sb = new StringBuilder("[");
        boolean notfirst = false;
        for (Pair pr : seq) {
            if (notfirst)
                sb.append(", ");
            else
                notfirst = true;
            sb.append(pr.toString());
        }
        sb.append("]");
        return sb.toString();
    }

    public static class Pair implements Cloneable {
        public final int size;
        public final int cost;
        public Pair(int n, int c) {
            size = n;
            cost = c;
        }
        @Override
        public String toString() {
            return "(" + size + "," + cost + ")";
        }
        @Override
        protected Object clone() throws CloneNotSupportedException {
            return new Pair(size, cost);
        }
    }
}
