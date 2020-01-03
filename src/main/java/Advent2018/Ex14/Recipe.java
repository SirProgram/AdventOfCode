package Advent2018.Ex14;

class Recipe {
    Recipe prev;
    Recipe next;

    int score;

    public Recipe(int score) {
        this.score = score;
    }

    public void addNextRecipe(Recipe newRecipe) {
        Recipe oldNext = next;

        oldNext.prev = newRecipe;
        this.next = newRecipe;

        newRecipe.next = oldNext;
        newRecipe.prev = this;
    }

    public void setNextRecipe(Recipe nextRecipe) {
        this.next = nextRecipe;
        nextRecipe.prev = this;
    }
}
