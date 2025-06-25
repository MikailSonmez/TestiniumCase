package models;

public class Card {
    private String id;
    private String name;
    private String idList; // List ID where card belongs

    public Card() {}

    public Card(String name, String idList) {
        this.name = name;
        this.idList = idList;
    }


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIdList() { return idList; }
    public void setIdList(String idList) { this.idList = idList; }
}
