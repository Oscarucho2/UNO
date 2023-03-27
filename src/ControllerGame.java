package uno;

//import java.io.IOException;
import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
import javafx.scene.image.Image;
//import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
//import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.control.Button;

public class ControllerGame {
    @FXML
    ImageView ActiveCard;
    
    @FXML
    Button TomaCarta;
    
    private List<Card> deck;
    private int nextCardIndex;
    private List<Card> Hand;
    
    public ControllerGame()
    {
        UnoDeck();
    }
    
    
    //setActiveCard - Funcion que asigna la imagen de la carta seleccionada a la carta activa del medio.
    public void setActiveCard(MouseEvent event)
    {   
        //Obtengo el objeto de la carta y lo asigno a un handle con un cast.
        Object source = event.getSource();
        ImageView card = (ImageView)source;
        //Obtengo la URL de la imagen (direccion en la computadora que puede que sea necesaria actualizar con el deck.)
        String ImgURL = card.getImage().getUrl();
        System.out.println(ImgURL);
        ActiveCard.setImage(new Image(ImgURL));
    }
    
    public void UnoDeck(){
        deck = new ArrayList<Card>();

        String[] colors = {"RED", "YELLOW", "GREEN", "BLUE"};
        String[] effects = {"SKIP", "REVERSE", "DRAW2"};

        //Agrega las cartas de numeros
        for (String color : colors) {
            for (int i = 0; i <= 9; i++) {
                deck.add(new Card(color, String.valueOf(i)));
                if (i != 0) {
                    deck.add(new Card(color, String.valueOf(i)));
                }
            }
        }

        //Agrega las cartas de effectos con color
        for (String color : colors) {
            for (String effect : effects) {
                deck.add(new Card(color, effect));
                deck.add(new Card(color, effect));
            }
        }

        //Agrega las WILD (Comodines)
        for(int i = 0; i<=3;i++)
        {
            deck.add(new Card("WILD", "CHANGE"));
            deck.add(new Card("WILD", "DRAW4"));
        }
        
        Collections.shuffle(deck);
        nextCardIndex = 0;
    }
    
    //Metodo para poder llamar el metodo de sacar carta en el fxml
    public void Draw()
    {
        Card taken = drawACard();
        if(taken.getColor() != "No")
        {
            System.out.println(taken.getColor() + " " + taken.getEffect() + "!");
            ImageView nuevacarta = new ImageView();
            String URL = "file:/C:/Users/Jorge/OneDrive/Escritorio/UNO_Ubicua/UNO/build/classes/uno/Cartas/UNO_" + taken.getColor() + "_" + taken.getEffect() + ".png";
            nuevacarta.setImage(new Image(URL));
            System.out.println(URL);
            if((deck.size() - nextCardIndex) != 0)
                System.out.println("Quedan " + (deck.size() - nextCardIndex) + " cartas.");
            else
                System.out.println("Se ha acabado la baraja!");
        }
    }
    
    //Metodo solo para acabarme la baraja
    public void DrawUnchingo(){
        ArrayList<Card> HugeStack = drawCards(100);
        System.out.println("Quedan " + (deck.size() - nextCardIndex) + " cartas.");
    }
    
    //Toma carta
    public Card drawACard() {
        Card card = new Card("No", "Card");
        if (nextCardIndex >= deck.size()) {
            System.out.println("La baraja esta vacia!");
        }
        else{
            card = deck.get(nextCardIndex);
            nextCardIndex++;   
        }

        return card;
    }
    
    //Toma cartas (+2, +4, +n)
    public ArrayList<Card> drawCards(int count)
    {
        ArrayList<Card> Taken = new ArrayList<Card>();
        Card cardtaken;
        
        for(int i=0; i<count; i++){
            if (nextCardIndex >= deck.size()) {
                System.out.println("La baraja esta vacia!");
            }
            else{
                cardtaken = deck.get(nextCardIndex);
                nextCardIndex++;
                Taken.add(cardtaken);
            }
        }
        
        return Taken;
    }
}
