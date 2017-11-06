# PGQL: a Property Graph Query Language

PGQL is an SQL-like query language for the [property graph data model](http://pgql-lang.org/spec/1.1/#property-graph-data-model).
See:

 - PGQL website: [http://pgql-lang.org/](http://pgql-lang.org/)
 - Latest specification: [http://pgql-lang.org/spec/1.1/](http://pgql-lang.org/spec/1.1/).

## Master branch

The 'master' branch of this reposistory contains a parser for PGQL with the following features:

 - Easy-to-understand IR: Given a query string, the parser returns an easy-to-understand intermedidate representation (IR) of the query as a set of Java objects
    - see [__GraphQuery.java__](graph-query-ir/src/main/java/oracle/pgql/lang/ir/GraphQuery.java))
 - Error messages: the parser has __state-of-the-art static error message indication__, taking into account semantic information such as already-defined vertices and edges

   Example 1:

   ```sql
   SELECT n.name, o.name
     FROM g
    MATCH (n) -[e]-> (m)
   ```

   ==>

   ```
   Error(s) in line 1:

       SELECT n.name, o.name
                      ^
       Unresolved variable
   ```

   Example 2:

   ```sql
   SELECT AVG(n.age), n
     FROM g
    MATCH (n:Person)
   ```

   ==>

   ```
   Error(s) in line 1:

       SELECT AVG(n.age), n
                          ^
       Aggregation expected here since SELECT has other aggregation
   ```

   Example 3:

   ```sql
   SELECT AVG(AVG(n.age))
    FROM g
   MATCH (n:Person)
   ```

   ==>

   ```
   Error(s) in line 1:

       SELECT AVG(AVG(n.age))
                  ^^^^^^^^^^
       Nested aggregation is not allowed
   ```

 - __Pretty printing__: invoking `GraphQuery.toString()` will "pretty print" the graph query
 - __Code completion__: given a (partial) query string and a cursor position, the parser can suggest a set of code completions, including built-in functions, labels and properties. These completions can be used in e.g. a web editor.
   By providing the parser with metadata about the graph (existing properties and labels), the completions will also include label and property suggestions.

## gh-pages branch

The 'gh-pages' branch of this repository contains:

 - Source code for the website and the specification
     - [Website source code](https://github.com/oracle/pgql-lang/tree/gh-pages)
     - [PGQL 1.1 Specification source code](https://github.com/oracle/pgql-lang/blob/gh-pages/pages/pgql-1.1-spec.md)

## Build and Install the Parser

PGQL's parser can be built on Windows, Linux and Mac OS X and requires Java 1.8 or higher and Maven 3.3.9 or higher.

On Linux / Mac OS X:

 - To build and install to your local Maven repository, run `sh install.sh`

You can easily adapt the scripts to work with Windows.

## Getting Started

First, build and install PGQL like explained above. Then, execute `cd example; sh run.sh` to parse two [example](example/src/main/java/oracle/pgql/lang/example/Main.java) queries:

```java
public class Main {

  public static void main(String[] args) throws PgqlException {

    Pgql pgql = new Pgql();

    // parse query and print graph query
    PgqlResult result1 = pgql.parse("SELECT n WHERE (n) -[e]-> (m)");
    System.out.println(result1.getGraphQuery());

    // parse query with errors and print error messages
    PgqlResult result2 = pgql.parse("SELECT x, y, WHERE (n) -[e]-> (m)");
    System.out.println(result2.getErrorMessages());
  }
}
```

The AST returned by the parser is a [GraphQuery](graph-query-ir/src/main/java/oracle/pgql/lang/ir/GraphQuery.java) object. This would be the input to your query planner.

## Documentation

See [PGQL 1.1 Specification](http://pgql-lang.org/spec/1.1/).

## Development

- Download Eclipse with Spoofax 2.1.0 pre-installed [here](http://www.metaborg.org/en/latest/source/release/note/2.1.0.html)
- Import the following projects into Eclipse (`File>Import...>Maven>Existing Maven Projects>Browse...`):
    - `graph-query-ir`: Java representation of graph queries
    - `pqgl-spoofax`: Spoofax implementation of PGQL (parser + error checks)
    - `pgql-lang`: translation of Spoofax AST into `graph-query-ir`

## Contributions

PGQL is an open source project. See [Contributing](CONTRIBUTING.md) for details.

Oracle gratefully acknowledges the contributions to PGQL made by the community.
