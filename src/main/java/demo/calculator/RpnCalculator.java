package demo.calculator;

import java.util.List;

import demo.calculator.formatter.IFormatter;
import demo.calculator.processor.IProcessor;
import demo.calculator.formatter.RpnFormatter;
import demo.calculator.processor.RpnProcessor;
import demo.calculator.unit.RpnUnit;
import demo.entity.control.Clear;
import demo.entity.control.Control;
import demo.entity.control.Undo;

public class RpnCalculator {
  private IProcessor rpnProcessor;
  private IFormatter rpnFormatter;
  private RpnMemento rpnMemento;
  private String cache;

  public RpnCalculator() {
    rpnProcessor = new RpnProcessor();
    rpnFormatter = new RpnFormatter();
    rpnMemento = new RpnMemento();
    cache = "";
  }

  public String process(String input) {
    try {
      List<RpnUnit> cacheUnits = RpnUnit.parse(cache);
      rpnProcessor.process(cacheUnits);
      List<RpnUnit> inputUnits = RpnUnit.parse(cache + input);
      for (int i = cacheUnits.size(); i < inputUnits.size(); i++) {
        var unit = inputUnits.get(i);
        if (! (unit.getEntity() instanceof Control)) {
          var result = rpnProcessor.process(List.of(unit));
          rpnMemento.saveToDone(cache);
          cache = rpnFormatter.format(result);
        }
        else {
          if (unit.getEntity() instanceof Undo) {
            rpnProcessor.reset();
            var result = rpnProcessor.process(RpnUnit.parse(rpnMemento.getLastDone()));
            cache = rpnFormatter.format(result);
          }
          else if (unit.getEntity() instanceof Clear) {
            rpnMemento.saveToDone(cache);
            rpnProcessor.reset();
            var result = rpnProcessor.process(RpnUnit.parse(""));
            cache = rpnFormatter.format(result);
          }
        }
      }
      rpnProcessor.reset();
      return cache;
    } catch (Exception exception) {
      this.cache = "";
      return exception.getMessage();
    }
  }
}