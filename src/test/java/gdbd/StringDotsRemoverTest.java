package gdbd;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import util.uneatlantico.es.StringDotsRemover;

class StringDotsRemoverTest {
	
	@Test
	void testCleanString() {
		String str = "string.string.string";
		
		assertEquals("stringstringstring", StringDotsRemover.cleanString(str));
		assertEquals("string", StringDotsRemover.cleanString("string"));
	}

}
