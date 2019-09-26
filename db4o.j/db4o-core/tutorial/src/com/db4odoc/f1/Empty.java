package com.db4odoc.f1;

import com.db4o.Db4oEmbedded;
import com.db4o.EmbeddedObjectContainer;
import com.db4o.ObjectContainer;
import com.db4o.ObjectServer;
import com.db4o.ObjectSet;
import com.db4o.config.ConfigScope;
import com.db4o.config.annotations.Indexed;
import com.db4o.cs.Db4oClientServer;
import com.db4o.cs.config.ServerConfiguration;
import com.db4o.diagnostic.DiagnosticToConsole;
import com.db4o.query.*;
import com.db4o.ta.TransparentActivationSupport;
import com.db4o.ta.TransparentPersistenceSupport;
import java.io.File;

public class Empty {
    //Runfile: com.db4odoc.f1.Main.java 

    public static class Node {

        @Indexed
        public String name;
        public Node Left;
        public Node Right;
    ;

    }

    public static void main(String[] args) {

        String dbname = "/tmp/node.j.db";
        new File(dbname).delete();

        ServerConfiguration cfg = Db4oClientServer.newServerConfiguration();
        cfg.common().diagnostic().addListener(new DiagnosticToConsole());
        cfg.common().objectClass(Node.class).cascadeOnActivate(true);
        cfg.common().objectClass(Node.class).cascadeOnUpdate(true);
        cfg.common().objectClass(Node.class).cascadeOnDelete(true);
        cfg.common().objectClass(Node.class).callConstructor(true);

        cfg.common().add(new TransparentActivationSupport());
        cfg.common().add(new TransparentPersistenceSupport());
        cfg.file().generateUUIDs(ConfigScope.GLOBALLY);
        try (ObjectServer server = Db4oClientServer.openServer(cfg, dbname, 0)) {

        }
        try (EmbeddedObjectContainer x = Db4oEmbedded.openFile(dbname)) {
            Node n = new Node();
            n.name = "CCC";
            x.store(n);
            x.commit();

            final String name = new String("CCC" + "") + "" + new String("");
            ObjectSet<Node> ns = x.query(new Predicate<Node>() {
                @Override
                public boolean match(Node n) {
                    return n.name.equals(name);
                }
            });
            System.out.println(ns.size());

            ns = x.query(new IPredicate<Node>() {
                @Override
                public boolean match(Node n) {
                    return n.name.equals(name);
                }
            });
            System.out.println(ns.size());

            try (ObjectContainer ses = x.ext().openSession()) {
                ns = x.query(new IPredicate<Node>() {
                    @Override
                    public boolean match(Node n) {
                        return n.name.equals("CCC");
                    }
                });
                System.out.println(ns.size());

            }
        }

    }
}
