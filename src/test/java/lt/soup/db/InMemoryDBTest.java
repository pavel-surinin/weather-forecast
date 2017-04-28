package lt.soup.db;

import static org.junit.Assert.*;

/**
 * Created by Pavel on 2017-04-28.
 */
public class InMemoryDBTest extends DatabaseTest{

    public InMemoryDBTest(){
        setDatabase(new InMemoryDB());
    }

}