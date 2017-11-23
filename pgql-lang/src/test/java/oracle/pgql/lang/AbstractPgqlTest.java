package oracle.pgql.lang;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class AbstractPgqlTest {

  protected static Pgql pgql;

  @BeforeClass
  public static void setUp() throws Exception {
    pgql = new Pgql();
  }

  @AfterClass
  public static void tearDown() {
    pgql.close();
  }
}
