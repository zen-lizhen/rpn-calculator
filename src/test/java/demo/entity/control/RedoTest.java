package demo.entity.control;

import org.junit.Test;

import demo.entity.IEntity;
import demo.exception.RpnException;

import static org.junit.Assert.*;

public class RedoTest {
  @Test public void testConstructor() {
    var redo = new Redo();

    assertTrue("redo should be instance of Control", redo instanceof Control);
    assertTrue("redo should be instance of IEntity", redo instanceof IEntity);
  }

  @Test public void testNotion() {
    var redo = new Redo();

    var notion = redo.getNotion();    
    assertEquals("redo should have correct notation", Redo.NOTION, notion);
  }

  @Test public void testPerform() {
    var redo = new Redo();

    assertEquals("redo should return itself when performed", redo, redo.perform());
  }

  @Test public void testParse() throws RpnException {
    var redo1 = Control.parse(Redo.NOTION);
    assertTrue("redo should be instance of Redo", redo1 instanceof Redo);

    var redo2 = Control.parse("redo");
    assertTrue("redo should be instance of Redo", redo2 instanceof Redo);
  }
}