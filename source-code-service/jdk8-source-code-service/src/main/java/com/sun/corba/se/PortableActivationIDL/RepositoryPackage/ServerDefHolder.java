package com.sun.corba.se.PortableActivationIDL.RepositoryPackage;

/**
* com/sun/corba/se/PortableActivationIDL/RepositoryPackage/ServerDefHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from c:/re/workspace/8-2-build-windows-amd64-cygwin/jdk8u91/7017/corba/src/share/classes/com/sun/corba/se/PortableActivationIDL/activation.idl
* Friday, May 20, 2016 5:44:11 PM PDT
*/

public final class ServerDefHolder implements org.omg.CORBA.portable.Streamable
{
  public ServerDef value = null;

  public ServerDefHolder ()
  {
  }

  public ServerDefHolder (ServerDef initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = ServerDefHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    ServerDefHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return ServerDefHelper.type ();
  }

}
