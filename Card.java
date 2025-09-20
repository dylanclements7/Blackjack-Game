public class Card {
    int value;
    int id;
    int suit;

    public Card() {
        value = 0;
        id = 0;
        suit = 0;
    }

    public Card(int v, int i, int s) {
        value = v;
        id = i;
        suit = s;
    }

    public int getValue() {
        return value;
    }
    public int getId() {
        return id;
    }
    public int getSuit() {
        return suit;
    }
}
