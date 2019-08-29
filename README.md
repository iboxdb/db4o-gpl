#### Db4o-GPL .NetStandard2.0 & Java7+ Version

Not as complexity as big sql database server, not as simplicity as iBoxDB,

Db4o is the best database project to help you to learn how to write a database.

This oop-database project, has no news in a long time. 
here update it to .NetStandard2 & Java7. some users maybe need it.


#### DotNet Core

Use IDE to Open **Db4o-2010.sln**  or run
```
Db4objects.Db4o/dotnet publish
Db4objects.Db4o.Linq/dotnet publish
Db4objects.Db4o.CS/dotnet publish
test/dotnet run
```
**/test** includes a dotnet core example.


#### JAVA 7
Use IDE **NetBeans 11** to Open **db4o.j/db4o-core** project
```
 Build Project will output a jar to ../db4o-gpl/db4o.j/db4o-core/dist/db4o-core.jar
 or Run File, db4o.j/db4o-core/tutorial/src/com/db4odoc/f1/Main.java 
```

#### Xamarin

```
Db4o-Projects/dotnet publish -c Release //like above
Add 4 DLLs in /Output to xamarin-project

var path = Environment.GetFolderPath(Environment.SpecialFolder.Personal);
string dbpath = Path.Combine(path, "x.db");
db = Db4oEmbedded.OpenFile(dbpath);

Project Properities->Android Options->Linking->"Sdk Assemblies Only" (not "Sdk and User Assemblies").
Permission: Read/Write_External_Storage
```

#### Download Assemblies Directly

[.NETStandard2](https://github.com/iboxdb/db4o-gpl/tree/master/db4o.net/Output/netstandard2.0)

[JAVA JAR](https://github.com/iboxdb/db4o-gpl/tree/master/db4o.j/db4o-core/dist) 

[Nuget db4o-core](https://www.nuget.org/packages/db4o-core/)


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

**One Local Share Connection**
```java
ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded
				.newConfiguration(), DB4OFILENAME);
```

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
  ObjectContainer client1 = server.openClient();
  ObjectContainer client2 = server.openClient();
  // Do something with this clients
  client.close();
  client1.close();
  client2.close();
} finally {
  server.close();
}
public static void queryLocalServer(ObjectServer server) {
  ObjectContainer client = server.openClient();
  listResult(client.queryByExample(new Car(null)));
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
 
