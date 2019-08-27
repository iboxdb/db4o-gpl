#### Db4o-GPL .NetStandard2.0 & Java7 Version

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


#### .NET Documentation

[Getting Started With db4o](https://dzone.com/refcardz/getting-started-db4o)

[gamlor.info](https://www.gamlor.info/wordpress/tag/db4o/)

[Object-oriented database programming with db4o](https://www.codeproject.com/articles/17946/object-oriented-database-programming-with-db4o)






License: [GPL](https://github.com/iboxdb/db4o-gpl/blob/master/db4o.net/db4o.license/db4o.license.html)


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
 
