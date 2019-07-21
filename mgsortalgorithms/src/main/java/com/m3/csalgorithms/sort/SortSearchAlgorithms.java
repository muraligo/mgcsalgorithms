package com.m3.csalgorithms.sort;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SortSearchAlgorithms {
    private static final Logger _LOG = LoggerFactory.getLogger(SortSearchAlgorithms.class);

    private static String _envname = null;
    private static final String[] _SORT_ALGO_NAMES = { "QUICK", "INSERTION", "BUBBLE", 
                                                       "BUCKET", "COUNTING", "MERGE" };

    public static void main(String[] args) {
        String argenvvalue = null;
        String argconfigpthstr = null;
        OperationGroup argoperationgroup = null;
        String argalgostr = null;
        for (int ix = 0; ix < args.length; ix++) {
            if (args[ix].startsWith("--environment")) {
                argenvvalue = fetchArgumentValue("--environment", args[ix]);
            } else if (args[ix].startsWith("--config")) {
                argconfigpthstr = fetchArgumentValue("--config", args[ix]);
            } else if (args[ix].startsWith("--sort")) {
                argoperationgroup = OperationGroup.SORT;
            } else if (args[ix].startsWith("--search")) {
                argoperationgroup = OperationGroup.SEARCH;
            } else if (args[ix].startsWith("--algorithm")) {
                argalgostr = fetchArgumentValue("--algorithm", args[ix]);
            } else {
                _LOG.error("SortSearchAlgorithms: ERROR invalid argument [" + args[ix] + " at " + ix + "].");
            }
        }
        // TODO Ensure arguments exist and are valid paths
        if (argconfigpthstr == null || argconfigpthstr.isEmpty()) {
            _LOG.error("SortSearchAlgorithms: ERROR Empty or invalid configuration path parameter");
            System.exit(-1);
        }
        _envname = (argenvvalue != null) ? argenvvalue.trim().toLowerCase() : "dev";
        _LOG.debug("Environment is [" + _envname + "]");

        if (argoperationgroup == OperationGroup.SORT) {
            if (argalgostr != null && !argalgostr.isBlank()) {
                argalgostr = argalgostr.strip().toUpperCase();
                List<String> listofsortalgonames = Arrays.asList(_SORT_ALGO_NAMES);
                if (listofsortalgonames.contains(argalgostr)) {
                    // TODO i-t-e through and call the appropriate algorithm
                	if ("QUICK".equalsIgnoreCase(argalgostr)) {
                        int[] unsorted = { 6, 5, 3, 1, 8, 7, 2, 4 }; // unsorted integer array
                        QuickSort algorithm = new QuickSort();
                        algorithm.sort(unsorted);
                        _LOG.info("SortSearchAlgorithms: Sorted array is [" + printArray(unsorted) + "]");
                	} else if ("INSERTION".equalsIgnoreCase(argalgostr)) {
                	    int[] unsorted = { 32, 23, 45, 87, 92, 31, 19 };
                	    InsertionSort algorithm = new InsertionSort();
                	    algorithm.insertionSort(unsorted);
                        _LOG.info("SortSearchAlgorithms: Sorted array is [" + printArray(unsorted) + "]");
                	} else if ("BUBBLE".equalsIgnoreCase(argalgostr)) {
                	    int[] unsorted = { 20, 12, 45, 19, 92, 55 };
                	    BubbleSort algorithm = new BubbleSort();
                	    algorithm.bubbleSort(unsorted);
                        _LOG.info("SortSearchAlgorithms: Sorted array is [" + printArray(unsorted) + "]");
                	}
                } else {
                    _LOG.error("SortSearchAlgorithms: ERROR Specified algorithm [" + argalgostr + "] not found or not supported");
                    System.exit(-1);
                }
            }
        }
    }

    private enum OperationGroup {
        SORT,
        SEARCH;
    }

    private static String fetchArgumentValue(String argname, String argraw) {
        String argvalue = null;
        int valix = argraw.indexOf("=");
        if (valix > 0) {
            valix++;
            argvalue = argraw.substring(valix);
        }
        return argvalue;
    }

    private static String printArray(int[] thevalues) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < thevalues.length; i++) {
            sb.append(Integer.toString(thevalues[i]));
            sb.append(", ");
        }
        return sb.toString();
    }

}
