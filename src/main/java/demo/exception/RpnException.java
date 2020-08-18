package demo.exception;

import java.util.List;

import demo.utils.StringResourceHandler;

import java.util.ArrayList;

public class RpnException extends Exception {
  static final long serialVersionUID = 1L;

  final String name;
  final List<Object> msgArgs;

  public RpnException(String name) {
    this.name = name;
    this.msgArgs = new ArrayList<Object>();
  }

  public void addMsgArg(Object arg) {
    this.msgArgs.add(arg);
  }

  public void addMsgArg(Object arg, int index) {
    this.msgArgs.add(index, arg);
  }

  public String getMessage() {
    return StringResourceHandler.getString(this.name, this.msgArgs);
  }
}