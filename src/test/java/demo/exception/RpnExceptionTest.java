package demo.exception;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class RpnExceptionTest {
  static Random rand = new Random();

  @Test public void testConstructor() {
      String name = RandomStringUtils.random(rand.nextInt(100) + 1);
      var e = new RpnException(name);
      assertTrue("exception should be instance of RpnException", (e instanceof RpnException));
      assertTrue("exception's argument list should be initialized", e.msgArgs != null);
      assertTrue("exception's argument list should be empty", e.msgArgs.size() == 0);
      assertEquals("exception should have designated name", e.name, name);
  }
    
  @Test public void testAddArgs() {
    var e = new RpnException("test");
    List<Object> objs = new ArrayList<Object>();
    objs.add(Double.valueOf(rand.nextDouble()));
    objs.add(Float.valueOf(rand.nextFloat()));
    objs.add(Integer.valueOf(rand.nextInt()));
    objs.add(String.valueOf(RandomStringUtils.random(rand.nextInt(100) + 1)));
    objs.add(Long.valueOf(rand.nextLong()));
    objs.add(Boolean.valueOf(rand.nextBoolean()));
    
    for (Object obj : objs) {
      e.addMsgArg(obj);
    }
    assertTrue("exception should have identical argument list", objs.equals(e.msgArgs));

    e = new RpnException("test");
    for (Object obj : objs) {
      e.addMsgArg(obj, 0);
    }
    Collections.reverse(objs);
    assertTrue("exception should have reversed argument list when arguments are added in front", objs.equals(e.msgArgs));
  }
}
