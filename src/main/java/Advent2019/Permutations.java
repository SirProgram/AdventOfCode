package Advent2019;

import java.util.ArrayList;
import java.util.List;

public class Permutations {

    static List<List<Integer>> permutations(java.util.List<Integer> arr) {
        List<List<Integer>> perms = new ArrayList<>();
        Permutations.permute(arr, 0, perms);
        return perms;
    }

    private static List<List<Integer>> permute(List<Integer> arr, int k, List<List<Integer>> permutations) {
        for (int i = k; i < arr.size(); i++) {
            java.util.Collections.swap(arr, i, k);
            permute(arr, k + 1, permutations);
            java.util.Collections.swap(arr, k, i);
        }
        if (k == arr.size() - 1) {
            System.out.println(java.util.Arrays.toString(arr.toArray()));
            permutations.add(new ArrayList<>(arr));
        }

        return permutations;
    }
}
