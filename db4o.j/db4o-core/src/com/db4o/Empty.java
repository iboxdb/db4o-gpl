package com.db4o;

import com.db4o.config.annotations.Indexed;
import java.io.File;

public class Empty {
    //Runfile: com.db4odoc.f1.Main.java 
/*
    public static class Node {

        @Indexed
        public String name;
        public Node Left;
        public Node Right; ;
    }
*/
    public static void main(String[] args) {
/*
        String dbname = "/tmp/node.j.db";
        new File(dbname).delete();
        try (EmbeddedObjectContainer x = Db4oEmbedded.openFile(dbname)) {
            Node n = new Node();
            n.name = "CCC";
            x.store(n);
            x.commit();
        }
*/
    }
}
