/*
    Clase con el objetivo de interactuar con ServerDealer.
    Aqui el jugador recibe su mano y procede a hacer su turno.

    Tentativo:  
        -Con hilos sincronizados lograr el efecto de penalizar un jugador por no decir uno.
*/

package uno_clientplayer;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;



public class ClientPlayer {
    private List<Card> Hand;
    private int playerID;
    private String serverIP = "10.1.1.1";
    private int port = 6060;
    private Socket socket;
    public GameAction actionSend;
    public GameAction actionRecieve;
    private Card active;
    
    
    public ClientPlayer() throws IOException, ClassNotFoundException
    {
        socket = new Socket(serverIP, port);
        Hand = new ArrayList();
        WaitTurn();
    }
    
    //2 recibir, 1 enviar
    
    private void WaitTurn() throws IOException, ClassNotFoundException
    {
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream()) {
            @Override
            protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
                String className = desc.getName();
                try {
                    return Class.forName(className);
                } catch (ClassNotFoundException ex) {
                    // Handle class not found exception
                    return super.resolveClass(desc);
                }
            }
        };
        
        actionRecieve = (GameAction)inputStream.readObject();
        System.out.println(actionRecieve.color + "/" + actionRecieve.effect + "/" + actionRecieve.action + "/" + actionRecieve.penaltyvalue);
        if(actionRecieve.action == 2)
        {
            if(actionRecieve.penaltyvalue > 1)
            {
                do
                {
                    Hand.add(actionRecieve.getCard());
                    inputStream = new ObjectInputStream(socket.getInputStream());
                    actionRecieve = (GameAction) inputStream.readObject();
                }while(actionRecieve.penaltyvalue > 1);
            }
            else
            {
                Hand.add(actionRecieve.getCard());
            }
            WaitTurn();
        }
        if(actionRecieve.action == 1)
            MyTurn();
    }
    
    private void MyTurn() throws IOException, ClassNotFoundException
    {
        System.out.println("Carta activa: " + active.getColor() + "," + active.getEffect());
        printCards(Hand);
        
        if(playableCards())
        {
            System.out.println("Escribe el indice de la carta que quieres jugar: ");
            Scanner input = new Scanner(System.in);
            int id_Card = input.nextInt();
            while(!Hand.get(id_Card).getColor().equals(active.getColor()) || !Hand.get(id_Card).getEffect().equals(active.getEffect()) || checkDrawEffect(Hand.get(id_Card)) == false)
            {
                System.out.println("Carta activa: " + active.getColor() + "," + active.getEffect());
                System.out.println("Escribe el indice de la carta que concuerde con la carta activa: ");
                id_Card = input.nextInt();
            }

            Card play = playCard(id_Card);
            actionSend = new GameAction(2, play);
        }
        else
        {
            System.out.println("No hay cartas jugables, tomaras una carta y pasaras turno.");
            Card noCard = new Card("No", "Card");
            actionSend = new GameAction(1, noCard);
        }
        
        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(actionSend);
        outputStream.flush();
        
        WaitTurn();
    }
    
    private boolean playableCards(){
        boolean playable = false;
        
        for(Card c: Hand){
            if(c.getColor().equals(active.getColor()) || c.getEffect().equals(active.getEffect()) || checkDrawEffect(c))
                playable = true;
        }
        
        return playable;
    }
    
    private boolean checkDrawEffect(Card c)
    {
        boolean valid = false;
        
        if( ( "DRAW2".equals(active.getEffect()) || "DRAW4".equals(active.getEffect()) ) && ( "DRAW2".equals(c.getEffect()) || "DRAW4".equals(c.getEffect()) ) )
            valid = true;
            
        return valid;
    }
    
    private Card playCard(int indx)
    {
        Card play = Hand.get(indx);
        
        Hand.remove(play);
        
        return play;
    }
    
    private void printCards(List<Card> Hand)
    {
        String info;
        int n=0;
        for(Card c: Hand)
        {
            info = n + ". " + c.getEffect() + " " + c.getColor();
            System.out.println(info);
        }
    }
    
    
}
