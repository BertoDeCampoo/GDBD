package gdbd;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import es.uneatlantico.gdbd.persistence.SQLiteManager;

class SQLiteTest {
	
	SQLiteManager mgr = new SQLiteManager(SQLiteManager.Default_Filename);

	@Test
	void test() {
		List<String> lista = mgr.getServers();
	}

}
