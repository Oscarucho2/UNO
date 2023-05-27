/*
    Clase donde se controlara el ritmo del juego, que cartas tienen que jugadores, los turnos, y los efectos aplicados en el juego.
*/

package uno_serverdealer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerDealer {
 
    private List<Card> deck;
    private int nextCardIndex;
    private List<List<Card>> PlayersHand = new ArrayList();
    private int NPlayers;
    private int PenaltyDraw;
    private Card ActiveCard;
    private boolean clockwise;
    private boolean skip;
    private boolean Victory;
    private int playerTurn;
    private int port = 6060;
    private List<Socket> clientSockets = new ArrayList();
    public GameAction actionSend;
    public GameAction actionRecieve;
    private Socket playerSocket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    
    
    //Settea todo el juego.
    public ServerDealer(int N) throws IOException, ClassNotFoundException{
        Victory = false;
        ServerSocket serversocket = new ServerSocket(port);
        clockwise = true;
        PenaltyDraw = 0;
        NPlayers = N;
        
        //Iniciar Mazo
        UnoDeck();
        
        
        //Crear los sockets de los N jugadores.
        while(clientSockets.size() < NPlayers){
            Socket clientSocket = serversocket.accept();
            clientSockets.add(clientSocket);
            System.out.println("Player connected: " + clientSocket);
        }
        
        //Repartir cartas por N jugadores
        List<Card> newHand;
        int left;
        for(int i = 0; i<NPlayers; i++){
            newHand = new ArrayList<>();
            newHand.addAll(DrawNCards(5));
            PlayersHand.add(newHand);
            playerSocket = clientSockets.get(i);
            left = 5;
            for(Card c: newHand){
                actionSend = new GameAction(2,c);
                actionSend.penaltyvalue = left;
                System.out.println(actionSend.color + "/" + actionSend.effect + "/" + actionSend.action + "/" + actionSend.penaltyvalue);
                outputStream = new ObjectOutputStream(playerSocket.getOutputStream());
                outputStream.writeObject(actionSend);
                outputStream.flush();
                left--;
            }
        }
                
        //Iniciar Juego
        ActiveCard = drawACard();
        String activeEffect = ActiveCard.getEffect();
        String activeColor = ActiveCard.getColor();
        while("SKIP".equals(activeEffect) || "REVERSE".equals(activeEffect) || "DRAW2".equals(activeEffect) || "WILD".equals(activeColor))
        {
            deck.add(ActiveCard);
            ActiveCard = drawACard();
            activeEffect = ActiveCard.getEffect();
            activeColor = ActiveCard.getColor();
        }
        
        Game();
    }
    
    private void Game() throws IOException, ClassNotFoundException
    {
        playerTurn = 0;
        Card reciever;
        Card sender;
        List<Card> drawnCards;
        while(Victory == false)
        {
            if(skip == false){
                playerSocket = clientSockets.get(playerTurn);
                if(PenaltyDraw > 1 && hasDrawEffect(PlayersHand.get(playerTurn)) == false )
                {
                    drawnCards = DrawNCards(PenaltyDraw);
                    PlayersHand.get(playerTurn).addAll(drawnCards);
                    actionSend.penaltyvalue = PenaltyDraw;
                    
                    for(Card c: drawnCards){
                        actionSend.color = c.getColor();
                        actionSend.effect = c.getEffect();
                        
                        outputStream = new ObjectOutputStream(playerSocket.getOutputStream());
                        outputStream.writeObject(actionSend);
                        outputStream.flush();
                        
                        actionSend.penaltyvalue--;
                    }
                    
                    PenaltyDraw = 0;
                }
                else
                {
                    actionSend.color = ActiveCard.getColor();
                    actionSend.effect = ActiveCard.getEffect();
                    outputStream = new ObjectOutputStream(playerSocket.getOutputStream());
                    outputStream.writeObject(actionSend);
                    outputStream.flush();
                    inputStream = new ObjectInputStream(playerSocket.getInputStream());
                    try {
                        actionRecieve = (GameAction) inputStream.readObject();
                        reciever = new Card(actionRecieve.color, actionRecieve.effect);
                        switch(actionRecieve.action){
                        case 1 -> {
                            sender = drawACard();
                            actionSend.setCardInfo(sender);
                            outputStream = new ObjectOutputStream(playerSocket.getOutputStream());
                            outputStream.writeObject(actionSend);
                            outputStream.flush();
                        }
                        case 2 -> {
                            setCard(reciever);
                            PlayersHand.get(playerTurn).remove(reciever);
                        }
                        default -> {
                        }
                    }
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ServerDealer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            else
            {
                skip = false;
            }
            
            advanceTurn();
        }
    }
    
    private boolean hasDrawEffect(List<Card> hand)
    {
        boolean effectactive = false;
        
        for(Card c: hand)
        {
            if("DRAW2".equals(c.getEffect()) || "DRAW4".equals(c.getEffect()))
            {
                effectactive = true;
                break;
            }
        }
        
        return effectactive;
    }
    
    private void advanceTurn()
    {
        if(clockwise)
            if(playerTurn == NPlayers-1)
                playerTurn = 0;
            else
                playerTurn++;
        else
        {
            if(playerTurn == 0)
                playerTurn = NPlayers;
            playerTurn--;
        }
    }
    
    private void UnoDeck(){
        deck = new ArrayList<>();

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

    public void Draw()
    {
        Card taken = drawACard();
        if(taken.getColor() != "No")
        {
            System.out.println(taken.getColor() + " " + taken.getEffect() + "!");
            //ImageView nuevacarta = new ImageView();
            String URL = "file:/C:/Users/Jorge/OneDrive/Escritorio/UNO_Ubicua/UNO/build/classes/uno/Cartas/UNO_" + taken.getColor() + "_" + taken.getEffect() + ".png";
            //nuevacarta.setImage(new Image(URL));
            System.out.println(URL);
            if((deck.size() - nextCardIndex) != 0)
                System.out.println("Quedan " + (deck.size() - nextCardIndex) + " cartas.");
            else
                System.out.println("Se ha acabado la baraja!");
        }
    }
    
    private Card drawACard() {
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
    
    private ArrayList<Card> DrawNCards(int N){
        ArrayList<Card> DrawStack = drawCards(N);
        System.out.println("Quedan " + (deck.size() - nextCardIndex) + " cartas.");
        return DrawStack;
    }
    
    private ArrayList<Card> drawCards(int count)
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
    
    private void PlayerDraw(List<Card> Hand, int N)
    { 
        if(N > 1)
        {
            Hand.addAll(DrawNCards(N));
        }
        else
        {
            Card taken = drawACard();
            if(!"No".equals(taken.getColor()))
                Hand.add(taken);
            else
                System.out.println("Ya no quedan cartas.");
        }
    }
    
    private Card PlayerSendCard(List<Card> Hand, Card info)
    {
        Card SentCard;
        
        SentCard = Hand.get(Hand.indexOf(info));
        Hand.remove(info);
        
        if("CHANGE".equals(SentCard.getEffect()) || "DRAW4".equals(SentCard.getEffect()))
        {
            //Rutina para elegir color.
            System.out.println("Rutina de cambio de color.");
        }
        
        return SentCard;
    }
    
    private void setCard(Card Obtained)
    {
        ActiveCard = Obtained;
        switch(ActiveCard.getEffect())
        {
            case "DRAW2" -> PenaltyDraw += 2;
            case "DRAW4" -> PenaltyDraw += 4;
            case "REVERSE" -> {clockwise = !clockwise;
                               if(NPlayers == 2){ skip = true; } }
            case "SKIP" -> skip = true;
        }
    }
    
    private void printCards(List<Card> Hand)
    {
        String info;
        int n=1;
        for(Card c: Hand)
        {
            info = n + ". " + c.getEffect() + " " + c.getColor();
            System.out.println(info);
        }
    }
    
    
}
