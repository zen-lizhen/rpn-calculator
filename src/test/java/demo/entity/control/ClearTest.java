package demo.entity.control;

import org.junit.Test;

import demo.entity.IEntity;
import demo.exception.RpnException;

import static org.junit.Assert.*;

public class ClearTest {
  @Test public void testConstructor() {
    var clear = new Clear();

    assertTrue("clear should be instance of Control", clear instanceof Control);
    assertTrue("clear should be instance of IEntity", clear instanceof IEntity);
  }

  @Test public void testNotion() {
    var clear = new Clear();

    var notion = clear.getNotion();    
    assertEquals("clear should have correct notation", Clear.NOTION, notion);
  }

  @Test public void testPerform() {
    var clear = new Clear();

    assertEquals("clear should return itself when performed", clear, clear.perform());
  }

  @Test public void testParse() throws RpnException {
    var clear1 = Control.parse(Clear.NOTION);
    assertTrue("clear should be instance of Clear", clear1 instanceof Clear);

    var clear2 = Control.parse("clear");
    assertTrue("clear should be instance of Clear", clear2 instanceof Clear);
  }
}