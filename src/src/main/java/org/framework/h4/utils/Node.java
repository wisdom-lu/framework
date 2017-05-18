package org.framework.h4.utils;

public class Node {   
    private int id;   
    private int parentId;   
    Node(){}   
    Node(int id,int parentId){   
        this.id=id;   
       this.parentId = parentId;   
    }   
    public int getId() {   
       return id;   
    }   
   public void setId(int id) {   
       this.id = id;   
   }   
    public int getParentId() {   
       return parentId;   
    }   
    public void setParentId(int parentId) {   
        this.parentId = parentId;   
   }   
}  

