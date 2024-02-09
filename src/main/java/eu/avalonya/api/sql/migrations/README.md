# Migrations

Pour créer une nouvelle migration SQL, on a besoin de créer un nouveau fichier ayant pour nom `VXX_Ajouts.Java`.

`XX` étant la nouvelle version, par exemple 03.

Cette classe Java doit extends la classe `Migration`. Il faut implémenter la fonction `execute` :

Par exemple :

```java
  import eu.avalonya.api.AvalonyaAPI;

@Override
public void execute() 
{
    AvalonyaAPI.getInstance().getLogger().info("Executing AddNewTable");
    this.execute(this.createNewTable);
}
```

Ainsi que l'attribut de classe `createNewTable` :

```java
    private String createNewTable = """
            CREATE TABLE `new_table` (
              `new_data` varchar(255) NOT NULL)
    """;
```

Pour chaque modification / ajout SQL on ajoute un attribut qu'on appelle dans la fonction `execute`.

Ensuite, il faut ajouter un valeur dand l'enum `MigrationMapping`, comme ceci :

```java
    v00_MIGRATION_TABLE(0, V00_AddMigrationTable.class),
    V01_ADD_PLAYER(1, V01_AddPlayer.class),
    V02_ADD_NEW_TABLE(2, V02_AddNewTable.class);
```

Et c'est tout bon :)