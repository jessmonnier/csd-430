package com.dm;
/* Jess Monnier
 * CSD-430 Module 3
 * A class to represent a member of a D&D party, including:
 * Name, Phone Number, Character Name, Class, Race, and the 6 core stats
 * It just has variables, getters, and setters, so comments are light.
 */

public class Member {
    private final int id;
    private String name;
    private String characterName;
    private String characterClass;
    private String characterRace;
    private String phoneNumber;
    private int strength;
    private int intelligence;
    private int charisma;
    private int constitution;
    private int dexterity;
    private int wisdom;

    // constructor
    public Member(int id, String name, String characterName, String characterClass, 
                  String characterRace, String phoneNumber, int strength, int intelligence, 
                  int charisma, int constitution, int dexterity, int wisdom) {
        this.id = id;
        this.name = name;
        this.characterName = characterName;
        this.characterClass = characterClass;
        this.characterRace = characterRace;
        this.phoneNumber = phoneNumber;
        this.strength = strength;
        this.intelligence = intelligence;
        this.charisma = charisma;
        this.constitution = constitution;
        this.dexterity = dexterity;
        this.wisdom = wisdom;
    }

    // getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getCharacterName() { return characterName; }
    public String getCharacterClass() { return characterClass; }
    public String getCharacterRace() { return characterRace; }
    public String getPhoneNumber() { return phoneNumber; }
    public int getStrength() { return strength; }
    public int getIntelligence() { return intelligence; }
    public int getCharisma() { return charisma; }
    public int getConstitution() { return constitution; }
    public int getDexterity() { return dexterity; }
    public int getWisdom() { return wisdom; }

    // setters
    public void setName(String name) { this.name = name; }
    public void setCharacterName(String characterName) { this.characterName = characterName; }
    public void setCharacterClass(String characterClass) { this.characterClass = characterClass; }
    public void setCharacterRace(String characterRace) { this.characterRace = characterRace; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setStrength(int strength) { this.strength = strength; }
    public void setIntelligence(int intelligence) { this.intelligence = intelligence; }
    public void setCharisma(int charisma) { this.charisma = charisma; }
    public void setConstitution(int constitution) { this.constitution = constitution; }
    public void setDexterity(int dexterity) { this.dexterity = dexterity; }
    public void setWisdom(int wisdom) { this.wisdom = wisdom; }
}
