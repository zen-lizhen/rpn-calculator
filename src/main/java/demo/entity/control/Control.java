package demo.entity.control;

import demo.entity.IEntity;
import demo.exception.RpnException;

public abstract class Control implements IEntity {
  final String notion;
  
  public Control(String notion) {
    this.notion = notion;
  }

  public String getNotion() {
    return this.notion;
  }

  public IEntity perform() {
    return this;
  }

  public static Control parse(String notion) throws RpnException {
    if (notion.equals(Clear.NOTION)) {
      return new Clear();
    }
    else if (notion.equals(Undo.NOTION)) {
      return new Undo();
    }
    else if (notion.equals(Redo.NOTION)) {
      return new Redo();
    }
    else {
      return null;
    }
  }
}