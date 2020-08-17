package demo.entity.control;

import org.junit.Test;

import demo.entity.IEntity;
import demo.exception.RpnException;

import static org.junit.Assert.*;

public class UndoTest {
  @Test public void testConstructor() {
    var undo = new Undo();

    assertTrue("undo should be instance of Control", undo instanceof Control);
    assertTrue("undo should be instance of IEntity", undo instanceof IEntity);
  }

  @Test public void testNotion() {
    var undo = new Undo();

    var notion = undo.getNotion();    
    assertEquals("undo should have correct notation", Undo.NOTION, notion);
  }

  @Test public void testPerform() {
    var undo = new Undo();

    assertEquals("undo should return itself when performed", undo, undo.perform());
  }

  @Test public void testParse() throws RpnException {
    var undo1 = Control.parse(Undo.NOTION);
    assertTrue("undo should be instance of Undo", undo1 instanceof Undo);

    var undo2 = Control.parse("undo");
    assertTrue("undo should be instance of Undo", undo2 instanceof Undo);
  }
}