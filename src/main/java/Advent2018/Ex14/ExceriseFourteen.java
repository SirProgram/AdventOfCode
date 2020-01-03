package Advent2018.Ex14;

import utils.ExceriseBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExceriseFourteen extends ExceriseBase {

    private int numRecipes = 2;

    public void process(String path) throws IOException {
        List<String> lines = readFile(path);

        int desiredLength = Integer.parseInt(lines.get(0)) + 10;

        Recipe startingRecipe = new Recipe(3);
        startingRecipe.next = startingRecipe;
        startingRecipe.prev = startingRecipe;
        Recipe secondRecipe = new Recipe(7);
        startingRecipe.addNextRecipe(secondRecipe);

        List<Elf> elves = new ArrayList<>();
        Elf firstElf = new Elf(startingRecipe);
        elves.add(firstElf);

        Elf secondElf = new Elf(secondRecipe);
        elves.add(secondElf);

        Recipe endRecipe = secondRecipe;

        while (numRecipes < desiredLength) {
            endRecipe = createRecipe(firstElf, secondElf, endRecipe);
            moveElves(elves);
        }

        printGame(startingRecipe);
        Recipe printRecipe = startingRecipe;
        for (int i = 0; i < desiredLength - 10; i++) {
            printRecipe = printRecipe.next;
        }
        for (int j = 0; j < 10; j++) {
            System.out.print(printRecipe.score);
            printRecipe = printRecipe.next;
        }

    }

    public void processB(String path) throws IOException {
        List<String> lines = readFile(path);

        int desiredTargetNumber = Integer.parseInt(lines.get(0));

        Recipe startingRecipe = new Recipe(3);
        startingRecipe.next = startingRecipe;
        startingRecipe.prev = startingRecipe;
        Recipe secondRecipe = new Recipe(7);
        startingRecipe.addNextRecipe(secondRecipe);

        List<Elf> elves = new ArrayList<>();
        Elf firstElf = new Elf(startingRecipe);
        elves.add(firstElf);

        Elf secondElf = new Elf(secondRecipe);
        elves.add(secondElf);

        Recipe endRecipe = secondRecipe;

        boolean foundResult = false;
        while (!foundResult) {
            endRecipe = createRecipe(firstElf, secondElf, endRecipe);
            moveElves(elves);
            foundResult = lookForResult(endRecipe, desiredTargetNumber);
        }

        System.out.println(numRecipes - String.valueOf(desiredTargetNumber).length());
    }

    private boolean lookForResult(Recipe endRecipe, int targetNumber) {
        String foundNumber = "";
        Recipe currentRecipe = endRecipe;
        for (int i = 0; i < String.valueOf(targetNumber).length() + 1; i++) {
            foundNumber = currentRecipe.score + foundNumber;
            currentRecipe = currentRecipe.prev;
        }
        //System.out.println(foundNumber);
        boolean successfulSearch = foundNumber.contains(String.valueOf(targetNumber));
        if (successfulSearch) {
            System.out.println(foundNumber);
        }
        return successfulSearch;
    }

    private void moveElves(List<Elf> elves) {
        for (Elf elf : elves) {
            int elfStartingScore = elf.currentRecipe.score + 1;
            for (int i = 0; i < elfStartingScore; i++) {
                elf.currentRecipe = elf.currentRecipe.next;
            }
        }
    }

    private Recipe createRecipe(Elf firstElf, Elf secondElf, Recipe endRecipe) {
        int newRecipeScore = firstElf.currentRecipe.score + secondElf.currentRecipe.score;

        if (newRecipeScore >= 10) {
            int firstDigit = 1;
            int lastDigit = newRecipeScore % 10;

            Recipe firstNewRecipe = new Recipe(firstDigit);
            endRecipe.addNextRecipe(firstNewRecipe);

            Recipe secondNewRecipe = new Recipe(lastDigit);
            firstNewRecipe.addNextRecipe(secondNewRecipe);
            numRecipes += 2;

            return secondNewRecipe;
        } else {
            Recipe newRecipe = new Recipe(newRecipeScore);
            endRecipe.addNextRecipe(newRecipe);

            numRecipes += 1;
            return newRecipe;
        }
    }

    private void printGame(Recipe startingRecipe) {
        System.out.print(startingRecipe.score + " ");
        Recipe currentRecipe = startingRecipe.next;
        while (currentRecipe != startingRecipe) {
            System.out.print(currentRecipe.score + " ");
            currentRecipe = currentRecipe.next;
        }
        System.out.print("\n");
    }


}
