package gdbd;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import es.uneatlantico.gdbd.util.StringDotsRemover;

class StringDotsRemoverTest {
	
	@Test
	void testCleanString() {
		String str = "string.string.string";
		
		assertEquals("stringstringstring", StringDotsRemover.cleanString(str));
		assertEquals("string", StringDotsRemover.cleanString("string"));
	}

}
