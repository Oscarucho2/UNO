package uno_clientplayer;

public class Card {
    private String color;
    private String effect;
    private String ImageURL; //Direccion de la imagen de la carta
    
    public Card(String color, String effect)
    {
        this.color = color;
        this.effect = effect;
    }
    
    public String getColor()
    {
        return this.color;
    }
    
    public String getEffect()
    {
        return this.effect;
    }
    
    public String getURL()
    {
        return this.ImageURL;
    }
    
    public void setURL(String url)
    {
        this.ImageURL = url;
    }
}
