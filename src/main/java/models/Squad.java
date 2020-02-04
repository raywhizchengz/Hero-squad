package models;

import java.util.ArrayList;
import java.util.Objects;

public class Squad {
    private String name;
    private String cause;
    private int maxSize;
    private int id;
    private static ArrayList<Squad> instances = new ArrayList<>();


    public Squad(String name,String cause,int maxSize){
        this.name=name;
        this.cause=cause;
        this.maxSize=maxSize;
        instances.add(this);
        this.id=instances.size();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Squad squad = (Squad) o;
        return maxSize == squad.maxSize &&
                id == squad.id &&
                Objects.equals(name, squad.name) &&
                Objects.equals(cause, squad.cause) ;

    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cause, maxSize, id);
    }

    public String getName() {
        return name;
    }

    public String getCause() {
        return cause;
    }

    public int getMaxSize() {
        return maxSize;
    }
    public static ArrayList<Squad> getAllSquads(){
        return  instances;
    }
    public  static  void clearAll(){
        instances.clear();
    }
    public static Squad getSquadById(int id){
        return instances.get(id-1);
    }
    public void  deleteSquadById(){
        instances.remove(id-1);
    }
    public void update(String name,String cause,int maxSize){
        this.name=name;
        this.cause=cause;
        this.maxSize=maxSize;
    }
    //    public static void add(Squad squad){
//        instances.add(squad);
//    }
    public int getId(){
        return this.id;

    }


}