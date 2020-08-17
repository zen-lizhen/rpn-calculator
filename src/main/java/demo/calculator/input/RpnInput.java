package demo.calculator.input;

import demo.entity.IEntity;
import demo.position.IPosition;
import demo.entity.control.Control;
import demo.entity.operand.Operand;
import demo.entity.operator.Operator;
import demo.exception.RpnException;

import java.util.StringTokenizer;
import java.util.List;
import java.util.LinkedList;

public class RpnInput implements IEntity, IPosition {
  final int position;
  final IEntity entity;

  public RpnInput(IEntity entity, int position) {
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

  public static List<RpnInput> parse(String inputs) throws RpnException {
    var rpnInputs = new LinkedList<RpnInput>();
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
        rpnInputs.add(new RpnInput(entity, position));
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