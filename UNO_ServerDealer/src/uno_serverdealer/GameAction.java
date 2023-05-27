/*
    Clase pensada para la comunicacion entre el ServerDealer y el ClienteUNO.
    Lo que ServerDealer interpreta:
    Solo es comunicacion de que tipo de accion es:
        0 - Saltar turno
        1 - Tomar carta
        2 - Jugar Carta
    En caso 2:
        Se almacena la informacion de la carta, para enviarse junto a eso.
    
    
*/
package uno_serverdealer;

import java.io.Serializable;

public class GameAction implements Serializable{
    public int action;
    public String color;
    public String effect;
    public int penaltyvalue;
    
    public GameAction(int accion, Card envio)
    {
        action = accion;
        color = envio.getColor();
        effect = envio.getEffect();
    }
    
    public int getAccion()
    {
        return this.action;
    }
    
    public Card getCard()
    {
        Card card = new Card(color, effect);
        return card;
    }
    
    public void setCardInfo(Card card)
    {
        color = card.getColor();
        effect = card.getEffect();
    }
}
