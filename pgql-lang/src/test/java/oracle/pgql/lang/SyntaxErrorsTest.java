package oracle.pgql.lang;

import static org.junit.Assert.*;

import org.junit.Test;

public class SyntaxErrorsTest extends AbstractPgqlTest {

  @Test
  public void testBasicGraphPattern1() throws Exception {
    PgqlResult result = pgql.parse(
        "SELECT label(e1), label(e2) MATCH (n) -[e1]-> (m) -[e2:likes|dislikes|blabla]-> (o), label(e1) != label(e2)");
    assertFalse(result.isQueryValid());
    assertFalse(result.getErrorMessages() == null);
    assertFalse(result.getGraphQuery() == null);
    result.toString(); // may pretty-print syntactically/semantically incorrect query but should not produce errors
  }
}
