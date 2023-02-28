#### The Basic Principle of db4o

db4o is a non-intrusive persistence system that stores any complex object with one single line of code. The class schema of your application classes is analysed and adjusted in real time when objects are stored. Object-oriented querying functionality is provided through Native Queries (NQ), the ability to query the database using .NET syntax and semantics (similar in concept to LINQ/DLINQ), Query by Example (QBE) which uses prototype objects for querying and other APIs. High performance is achieved with indexed fields and by reducing database-file-internal redirections to the absolute minimum. db4o features ACID transactions, fast embedded single-user mode and multi-transactional Client/Server access, locally and through TCP, object-oriented replication, and the ObjectManager to browse database files.

[Download Object Manager Binaries to TRY](https://github.com/iboxdb/db4o-gpl-doc/tree/master/Binaries/20190916)

#### Db4o-GPL .NetStandard2.0 & Java7+ Version

Not as complexity as big **SQL** database server, not as simplicity as **iBoxDB**,

Db4o is the best database project to help you to learn how to write a database.

This adventurous oop-database project, has no news in a long time. 
here update it to .NetStandard2 & Java7. some users maybe need it.


#### DotNet Core

Use MonoDevelop to Open **Db4o-2010.sln**  or run
```
Db4objects.Db4o.Optional/dotnet publish -c Release
test/dotnet run
```
**/test** includes a dotnet core example.


#### JAVA 7
Use IDE **NetBeans 12** to Open **db4o.j/db4o-core** project
```
 Build Project will output a jar to ../db4o-gpl/db4o.j/db4o-core/dist/db4o-core.jar
 or Run File, db4o.j/db4o-core/tutorial/src/com/db4odoc/f1/Main.java 

 [user@localhost db4o-core]$ /home/user/netbeans12/extide/ant/bin/ant jar
```
db4o-core using JDK7, some test files using JDK11, the test files can be removed.


#### Xamarin

```
Db4o-Projects/*/dotnet publish -c Release //like above
Add 4 DLLs in /Output to xamarin-project

var path = Environment.GetFolderPath(Environment.SpecialFolder.Personal);
string dbpath = Path.Combine(path, "x.db");
db = Db4oEmbedded.OpenFile(dbpath);

Project Properities->Android Options->Linking->"Sdk Assemblies Only" (not "Sdk and User Assemblies").
Permission: Read/Write_External_Storage, INTERNET
```

#### Android Java

```java
// <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
// <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
// <uses-permission android:name="android.permission.INTERNET"/>

private class Async extends android.os.AsyncTask{
  @Override  
  protected Object doInBackground(Object... arg) {
    //Path 
    //android.os.Environment.getDataDirectory().getAbsolutePath()
    //		+ "/data/" + "com.example.fapp" + ""
    //DB4O Code here 
    return null;
  }
}

new Async().execute(null);
```

#### Download Assemblies Directly

Compile Source Code

#### .NET Tutorials

[Getting Started With db4o](https://dzone.com/refcardz/getting-started-db4o)

[gamlor.info](https://www.gamlor.info/wordpress/tag/db4o/)

[Object-oriented database programming with db4o](https://www.codeproject.com/articles/17946/object-oriented-database-programming-with-db4o)


#### API

##### .NET Docs  [Db4oEmbedded](https://iboxdb.github.io/db4o-gpl-doc/output/api/Db4objects.Db4o/Db4oEmbedded/)

##### JAVA Docs [Db4oEmbedded](https://iboxdb.github.io/db4o-gpl-doc/javadoc/com/db4o/Db4oEmbedded.html)

##### [About UpdateDepth](https://iboxdb.github.io/db4o-gpl-doc/output/api/Db4objects.Db4o.Config/ICommonConfiguration/69C8CF73)
 

#### Examples

#####  [.NET Example](https://github.com/iboxdb/db4o-gpl/blob/master/db4o.net/Db4odoc.Tutorial.Chapters/F1/Chapter6/ClientServerExample.cs)

##### [JAVA Example](https://github.com/iboxdb/db4o-gpl/blob/master/db4o.j/db4o-core/tutorial/src/com/db4odoc/f1/chapter6/ClientServerExample.java)

[WIKI](https://github.com/iboxdb/db4o-gpl/wiki)

**One Local Share Connection**

```java
ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded
				.newConfiguration(), DB4OFILENAME);
```

[See Session Object Container](https://github.com/iboxdb/db4o-gpl/wiki/Db4oSessions#session-containers)


**Multiple Local Connections**
```java
ServerConfiguration config = Db4oClientServer.newServerConfiguration();
//https://iboxdb.github.io/db4o-gpl-doc/javadoc/com/db4o/config/CommonConfiguration.html#updateDepth(int)
config.common().objectClass(Car.class).updateDepth(5);
//https://iboxdb.github.io/db4o-gpl-doc/javadoc/com/db4o/cs/Db4oClientServer.html#openServer(com.db4o.cs.config.ServerConfiguration,java.lang.String,int)
//Set Port = 0
ObjectServer server = Db4oClientServer.openServer(config, DB4OFILENAME, 0);
try {
  ObjectContainer client = server.openClient();
  // Do something with this clients
  client.close();
} finally {
  server.close();
}
//Open new Client for every request. Concurrent
public static void queryLocalServer(ObjectServer server) {
  ObjectContainer client = server.openClient();
  listResult(client.queryByExample(new Car("885588")));
  client.close();
}
```

**Multiple Remote Connections**
```java
//set PORT > 0
ObjectServer server = Db4oClientServer.openServer(Db4oClientServer
				.newServerConfiguration(), DB4OFILENAME, PORT);
server.grantAccess(USER, PASSWORD);
try {
  ObjectContainer client = Db4oClientServer.openClient(
    Db4oClientServer.newClientConfiguration(), "localhost",PORT, USER, PASSWORD);
  // Do something with this client, or open more clients
  client.close();
} finally {
  server.close();
}
```

**Java Lambda Index**
```java
var rs = see.query((Record r) -> r.indexField == dv);
```

#### Getting Started
```cs
public class Node
{
  [Indexed]
  public String Name;
  public Node Left;
  public Node Right;
}
    
StringBuilder sb = new StringBuilder();
var path = Environment.GetFolderPath(Environment.SpecialFolder.Personal);
path = Path.Combine(path, "t.db");
File.Delete(path);

var cf = Db4oEmbedded.NewConfiguration();
cf.Common.Add(new TransparentActivationSupport());
cf.Common.Add(new TransparentPersistenceSupport());
using (var oc = Db4oEmbedded.OpenFile(cf, path))
{
    using (var ss = oc.Ext().OpenSession())
    {
        Node root = new Node();
        root.Name = "Root";
        root.Left = new Node();
        root.Left.Name = "LEFT";
        root.Left.Left = new Node();
        root.Left.Left.Name = "LEFT.LEFT";
        ss.Store(root);
        ss.Commit();
    }
    using (var ss = oc.Ext().OpenSession())
    {
        sb.AppendLine(ss.QueryByExample(new Node { Name = "Root" }).First().Left.Left.Name);
    }
}
```

#### Object Management

**Use NetBeans-11 with Java on Linux**

```
build 'db4o.j/db4o-core' project, get db4o-core.jar
use NetBeans open db4o-gpl/admins/om.j/ObjectManager/ 
build and run
```

**Use MonoDevelop-7.8.4+ with Mono-6 on Linux**

```
Db4o-Projects/*/dotnet publish -c Release, get db4o DLLs
use MonoDevelop open  db4o-gpl/admins/om.net/OMAddin-2010.sln 
build and run
```


[See the result, images/db4o_gpl.png](https://iboxdb.github.io/db4o-gpl-doc/images/db4o_gpl.png)

![](https://iboxdb.github.io/db4o-gpl-doc/images/db4o_gpl.png)



[See the result, images/db4o_java_gpl.png](https://iboxdb.github.io/db4o-gpl-doc/images/db4o_java_gpl.png)

![](https://iboxdb.github.io/db4o-gpl-doc/images/db4o_java_gpl.png)

<br> 
<br> 

License: [GPL](https://github.com/iboxdb/db4o-gpl/blob/master/db4o.net/db4o.license/db4o.license.html) , as MySQL.


The info from the author. 
http://supportservices.actian.com/versant/default.html
```
db4o

With regret we have to announce that Actian decided not to actively pursue and promote the commercial db4o product offering for new customers any longer.

Since the db4o dual licensing model allows for the free usage of the community edition of db4o for non-commercial purposes, 
you have the opportunity to continue to use it for your non-commercial applications or
even fork it to provide support for the community.
You can find the latest installer packages including the source tarball here for Java, .NET35, .NET40

Actian will continue to provide commercial licenses and support for existing customers with active support contract.
 
```
 
