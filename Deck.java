import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> deck;

    public Deck() {
        deck = new ArrayList<Card>();
        for (int i = 0; i < 4; i++) {
            for (int x = 0; x < 13; x++) {
                if (x == 0) {
                    Card card = new Card(11, i * 13, i);
                    deck.add(card);
                }
                else if (x < 10) {
                    Card card = new Card(x + 1, i * 13 + x, i);
                    deck.add(card);
                }
                else {
                    Card card = new Card(10, i * 13 + x, i);
                    deck.add(card);
                }
            }
        }
    }
    public void ShuffleDeck() {
        Collections.shuffle(deck);
    }

    public Card GetCard(int cardIndex) {
        return deck.get(cardIndex);
    }

    public Card RemoveCard(int cardIndex){
        return deck.remove(cardIndex);
    }
}