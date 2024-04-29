package models;

public enum Difficulty {
    EASY(0),
    NORMAL(1),
    IMPOSSIBLE(2),
    HOPELESS(3);
    private final int value;
    public int getValue() {
        return value;
    }
    Difficulty(int value){
        this.value = value;
    }


    public static String names(){
        StringBuilder nameList = new StringBuilder();
        for (Difficulty Difficulty : values()) {
            nameList.append(Difficulty.name()).append(", ");
        }
        return nameList.substring(0, nameList.length()-2);
    } //return строка со всеми элементами enum через запятую


}