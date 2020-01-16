package de.tr7zw.annotations;

import java.io.Serializable;

interface MethodRefrence extends Serializable{  
    void callable();
}  

interface MethodRefrence1<T> extends Serializable{  
    void callable(T obj);
}  

interface MethodRefrence2<T, Z> extends Serializable{  
    void callable(T obj, Z obj2);
}  