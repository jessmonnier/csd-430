package com.dm;
/* Jess Monnier
 * CSD-430 Module 3
 * A class to represent a D&D party with a name and the time/day of week it meets
 * It just has variables, getters, and setters, so comments are light.
 */

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Party {
    private String name;
    private DayOfWeek meetDay;
    private LocalTime startTime;
    private List<Member> members;

    // constructor
    public Party(String name, DayOfWeek meetDay, LocalTime startTime) {
        this.name = name;
        this.meetDay = meetDay;
        this.startTime = startTime;
        this.members = new ArrayList<>();
    }

    // getters
    public String getName() { return name; }
    public DayOfWeek getMeetDay() { return meetDay; }
    public LocalTime getStartTime() { return startTime; }
    public List<Member> getMembers() { return members; }

    // setters
    public void setName(String name) { this.name = name; }
    public void setMeetDay(DayOfWeek meetDay) { this.meetDay = meetDay; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public void addMember(Member member) { this.members.add(member); }
    public void removeMember(Member member) { this.members.remove(member); }
}