package example;
//This project using JDK7, this test file using JDK11 for test.
//If building for Java7, remove this file.

import com.db4o.*;
import com.db4o.config.ConfigScope;
import com.db4o.config.annotations.Indexed;
import com.db4o.cs.*;
import com.db4o.diagnostic.DiagnosticToConsole;
import com.db4o.query.IPredicate;
import com.db4o.query.Predicate;
import com.db4o.query.QueryComparator;
import com.db4o.ta.TransparentActivationSupport;
import com.db4o.ta.TransparentPersistenceSupport;
import java.io.File;
import java.lang.invoke.SerializedLambda;

public class db4odemo {

    public static class Node {

        public Node() {

        }
        @Indexed
        public String name;
        public Node Left;
        public Node Right;

        @Indexed
        public double Size = 99;

        public int Waht;

        public String getName() {
            return name;
        }
    }

    public static void main(String[] args) throws Exception {
        DiagnosticToConsole.Enable = false;
        String dbname = "/tmp/node.j.db";
        new File(dbname).delete();

        {
            IPredicate<Node> xx = (Node n) -> {
                return n.name.equals("Root");
            };
            var cc = xx.getClass();
            var method = cc.getDeclaredMethod("writeReplace");
            System.out.println(method.canAccess(xx));
            method.setAccessible(true);
            var o = (SerializedLambda) method.invoke(xx);

            System.out.println(o.getImplClass());
            System.out.println(o.getImplMethodName());
        }
        long internalId;
        String objectId;

        var cfg = Db4oClientServer.newServerConfiguration();
        cfg.common().diagnostic().addListener(new DiagnosticToConsole());
        cfg.common().objectClass(Node.class).cascadeOnActivate(true);
        cfg.common().objectClass(Node.class).cascadeOnUpdate(true);
        cfg.common().objectClass(Node.class).cascadeOnDelete(true);
        cfg.common().objectClass(Node.class).callConstructor(true);

        cfg.common().add(new TransparentActivationSupport());
        cfg.common().add(new TransparentPersistenceSupport());
        cfg.file().generateUUIDs(ConfigScope.GLOBALLY);

        try (var server = Db4oClientServer.openServer(cfg, dbname, 0)) {

            try (var oc = server.openClient()) {
                var root = new Node();
                root.name = "Root";

                root.Left = new Node();
                root.Left.name = "Left";

                root.Right = new Node();
                root.Right.name = "Right";

                root.Right.Right = new Node();
                root.Right.Right.name = "Right.Right";

                oc.store(root);

                oc.commit();
            }

            try (var oc = server.openClient()) {
                var metaInfo = oc.ext().storedClass(Node.class);
                // list a fields and check if they have a index
                for (var field : metaInfo.getStoredFields()) {
                    if (field.hasIndex()) {
                        System.out.println("The field '" + field.getName() + "' is indexed");
                    } else {
                        System.out.println("The field '" + field.getName() + "' isn't indexed");
                    }
                }
            }

            try (var oc = server.openClient()) {
                var qo = new Node();
                qo.name = "Root";
                for (Object o : oc.queryByExample(null)) {
                    System.out.println(o.toString());
                }

                var ns = oc.queryByExample(qo);
                var root = ns.next();
                System.out.println(root.Right.Right.name);

                internalId = oc.ext().getID(root);
                objectId = oc.ext().getObjectInfo(root).getUUID().toString();
            }

            try (var oc = server.openClient()) {
                System.out.println(objectId);
                var root = oc.ext().getByUUID(Node.class, objectId);
                System.out.println(root.Right.Right.name);
            }

            try (var oc = server.openClient()) {
                System.out.println(internalId);
                var root = oc.ext().getByID(Node.class, internalId);
                System.out.println(root.Right.Right.name);
            }

            //Use Native Query, recommended
            System.out.println("---AA---");
            try (var oc = server.openClient()) {

                try (var see = oc.ext().openSession()) {
                    String r = "Root";
                    String rr = "Right.Right";

                    var ns = see.query((Node n) -> n.Right.Right.name.equals(rr));
                    System.out.println("RAA-1: " + ns.size());

                    ns = see.query((Node n) -> n.name.equals(r));
                    System.out.println("AA-1: " + ns.size());

                    ns = oc.query(new IPredicate<Node>() {
                        @Override
                        public boolean match(Node n) {
                            return n.name.equals(r);
                        }
                    });
                    System.out.println("AA-2: " + ns.size());

                    ns = oc.query(new Predicate<Node>() {
                        @Override
                        public boolean match(Node n) {
                            return n.name.equals(r);
                        }
                    });
                    System.out.println("AA-3: " + ns.size());
                    ///////
                    ///////
                    ns = see.query((Node n) -> n.name.equals("Root"));
                    System.out.println("AA-1-C: " + ns.size());

                    ns = oc.query(new IPredicate<Node>() {
                        @Override
                        public boolean match(Node n) {
                            return n.name.equals("Root");
                        }
                    });
                    System.out.println("AA-2-C: " + ns.size());

                    ns = oc.query(new Predicate<Node>() {
                        @Override
                        public boolean match(Node n) {
                            return n.name.equals("Root");
                        }
                    });
                    System.out.println("AA-3-C: " + ns.size());

                    ns = see.query((Node n) -> n.Size > 10, (Node a, Node b) -> a.name.compareTo(b.name));
                    System.out.println("AA-10: " + ns.size());
                }
            }
            try (var oc = server.openClient()) {
                var ns = oc.query(new Predicate<Node>() {
                    @Override
                    public boolean match(Node n) {
                        return n.name.startsWith("Root");
                    }
                });
                var root = ns.get(0);
                System.out.println(root.Right.Right.name);
            }
            try (var oc = server.openClient()) {
                var ns = oc.query(new Predicate<Node>() {
                    @Override
                    public boolean match(Node n) {
                        return n.name.equals("Root");
                    }
                });
                var root = ns.get(0);
                System.out.println(root.Right.Right.name);
            }

            try (var oc = server.openClient()) {
                var ns = oc.query(new Predicate<Node>() {
                    @Override
                    public boolean match(Node n) {
                        return n.name.equals("Right.Right");
                    }
                });
                var r_r = ns.get(0);
                System.out.println(r_r.name);
            }

            // S.O.D.A. not recommended, dynamic program
            System.out.println("---BB---");
            try (var oc = server.openClient()) {
                var q = oc.query();
                q.constrain(Node.class);
                q.descend("name").constrain("Root");

                var ns = q.execute(Node.class);
                for (var n : ns) {
                    n.Right.Right.name += ".Update";
                    oc.store(n);
                }
                oc.commit();
            }
            System.out.println("---CC---");

            //Use Complex Native Query
            try (var oc = server.openClient()) {
                var ns = oc.query(new Predicate<Node>() {
                    @Override
                    public boolean match(Node n) {
                        //return n.name.startsWith("Right.Right");

                        return n.name.compareTo("Right.Right") > 0
                                && n.name.length() > "Root".length();
                    }
                });
                var r_r = ns.get(0);
                System.out.println(r_r.name);
            }

            try (var oc = server.openClient()) {
                var ns = oc.query(
                        (Node n) -> n.name.compareTo("Right.Right") > 0
                        && n.name.length() > "Root".length()
                );
                var r_r = ns.get(0);
                System.out.println(r_r.name);
            }
        }

        System.out.println("DD");
        var ecfg = Db4oEmbedded.newConfiguration();
        ecfg.common().objectClass(Node.class).cascadeOnActivate(true);
        ecfg.common().objectClass(Node.class).cascadeOnUpdate(true);
        ecfg.common().objectClass(Node.class).cascadeOnDelete(true);
        ecfg.common().objectClass(Node.class).callConstructor(true);

        ecfg.common().add(new TransparentActivationSupport());
        ecfg.common().add(new TransparentPersistenceSupport());
        ecfg.file().generateUUIDs(ConfigScope.GLOBALLY);

        ecfg.common().diagnostic().addListener(new DiagnosticToConsole());

        try (var oc = Db4oEmbedded.openFile(ecfg, dbname)) {

            System.out.println(objectId);
            var root = oc.ext().getByUUID(Node.class, objectId);
            System.out.println(root.Right.Right.name);

            System.out.println("EE");
            System.out.println(internalId);
            root = oc.ext().getByID(Node.class, internalId);
            System.out.println(root.Right.Right.name);

            try (var see = oc.ext().openSession()) {
                var ns = see.query((Node n) -> n.name.equals("Root"));

                System.out.println("AA-1: " + ns.size());
            }

            try (var see = oc.ext().openSession()) {
                var ns = see.query(new IPredicate<Node>() {
                    @Override
                    public boolean match(Node n) {
                        return n.name.equals("Root");
                    }

                });
                System.out.println("AA-2: " + ns.size());
            }

            try (var see = oc.ext().openSession()) {
                var ns = see.query(new Predicate<Node>() {
                    @Override
                    public boolean match(Node n) {
                        return n.name.equals("Root");
                    }

                });
                System.out.println("AA-3: " + ns.size());
            }

            try (var see = oc.ext().openSession()) {
                var ns = see.query(new Predicate<Node>() {
                    @Override
                    public boolean match(Node n) {
                        return n.getName().equals("Root");
                    }

                });
                System.out.println("AA-4: " + ns.size());
            }

            try (var see = oc.ext().openSession()) {
                var ns = see.query((Node n) -> n.getName().equals("Root"));
                System.out.println("AA-5: " + ns.size());
            }

            try (var see = oc.ext().openSession()) {
                var ns = see.query(new IPredicate<Node>() {
                    @Override
                    public boolean match(Node n) {
                        return n.getName().equals("Root");
                    }

                });
                System.out.println("AA-6: " + ns.size());
            }

            try (var see = oc.ext().openSession()) {
                var ns = see.query((Node n) -> "Root".equals(n.getName()));
                System.out.println("AA-7: " + ns.size());
            }

            try (var see = oc.ext().openSession()) {
                var ns = see.query((Node n) -> "Root".equals(n.name));
                System.out.println("AA-8: " + ns.size());
            }

            try (var see = oc.ext().openSession()) {
                var ns = see.query((Node n) -> n.Size > 10);
                System.out.println("AA-9: " + ns.size());
            }

            try (var see = oc.ext().openSession()) {
                var ns = see.query((Node n) -> n.Size > 10, (Node a, Node b) -> a.name.compareTo(b.name));
                System.out.println("AA-10: " + ns.size());
            }

            try (var see = oc.ext().openSession()) {
                var ns = see.query((Node n) -> n.Size > 10,
                        new QueryComparator<Node>() {
                    @Override
                    public int compare(Node a, Node b) {
                        return a.name.compareTo(b.name);
                    }

                });
                System.out.println("AA-11: " + ns.size());
            }
        }

        ecfg = Db4oEmbedded.newConfiguration();
        ecfg.common().objectClass(Node.class).cascadeOnActivate(true);
        ecfg.common().objectClass(Node.class).cascadeOnUpdate(true);
        ecfg.common().objectClass(Node.class).cascadeOnDelete(true);
        ecfg.common().objectClass(Node.class).callConstructor(true);

        ecfg.common().add(new TransparentActivationSupport());
        ecfg.common().add(new TransparentPersistenceSupport());
        ecfg.file().generateUUIDs(ConfigScope.GLOBALLY);

        try (var oc = Db4oEmbedded.openFile(ecfg, dbname)) {

            try (var see = oc.ext().openSession()) {
                System.out.println(objectId);
                var root = see.ext().getByUUID(Node.class, objectId);
                System.out.println(root.Right.Right.name);
            }

            try (var see = oc.ext().openSession()) {
                System.out.println(internalId);
                var root = see.ext().getByID(Node.class, internalId);
                System.out.println(root.Right.Right.name);
            }

        }

        System.out.println("End.");
    }

}
