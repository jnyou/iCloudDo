package com.sun.corba.se.PortableActivationIDL;

/**
* com/sun/corba/se/PortableActivationIDL/BadServerDefinitionHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from c:/re/workspace/8-2-build-windows-amd64-cygwin/jdk8u91/7017/corba/src/share/classes/com/sun/corba/se/PortableActivationIDL/activation.idl
* Friday, May 20, 2016 5:44:10 PM PDT
*/

public final class BadServerDefinitionHolder implements org.omg.CORBA.portable.Streamable
{
  public BadServerDefinition value = null;

  public BadServerDefinitionHolder ()
  {
  }

  public BadServerDefinitionHolder (BadServerDefinition initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = BadServerDefinitionHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    BadServerDefinitionHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return BadServerDefinitionHelper.type ();
  }

}
