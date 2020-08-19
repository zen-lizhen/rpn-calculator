package demo.calculator.unit;

import demo.entity.IEntity;
import demo.position.IPosition;
import demo.entity.control.Control;
import demo.entity.operand.Operand;
import demo.entity.operator.Operator;
import demo.exception.RpnException;

import java.util.StringTokenizer;
import java.util.List;
import java.util.LinkedList;

public class RpnUnit implements IEntity, IPosition, Cloneable {
  final int position;
  final IEntity entity;

  public RpnUnit(IEntity entity, int position) {
    this.entity = entity;
    this.position = position;
  }

  public int getPosition() {
    return position;
  }

  public String getNotion() {
    return this.entity.getNotion();
  }

  public IEntity perform() {
    return this.entity.perform();
  }

  public IEntity getEntity() {
    return entity;
  }

  @Override
  public Object clone() {
    IEntity entity = null;
    try {
      entity = RpnUnit.parseEntity(this.entity.getNotion());
    } catch (RpnException e) {
      ; // should never throw exception
    }
    var position = this.position;
    return new RpnUnit(entity, position);
  }

  public static List<RpnUnit> parse(String inputs) throws RpnException {
    var rpnInputs = new LinkedList<RpnUnit>();
    if (inputs == null) {
      return rpnInputs;
    }
    var st = new StringTokenizer(inputs);
    var idx = 0;
    while (st.hasMoreTokens()) {
      var tok = st.nextToken();
      var position = idx;
      idx += tok.length() + 1;
      try {
        var entity = parseEntity(tok);
        rpnInputs.add(new RpnUnit(entity, position));
      }
      catch (RpnException rpnException) {
        rpnException.addMsgArg(position);
        throw rpnException;
      }
    }
    return rpnInputs;
  }

  private static IEntity parseEntity(String notion) throws RpnException {
    IEntity entity = null;
    try {
      var control = Control.parse(notion);
      var operator = Operator.parse(notion);
      if (control == null && operator == null) {
        entity = Operand.parse(notion);
      }
      else {
        entity = control != null ? control : operator;
      }
    } catch (NumberFormatException numberFormatException) {
      var rpnException = new RpnException("UnknownEntity");
      rpnException.addMsgArg(notion);
      throw rpnException;
    }
    return entity;
  }
}