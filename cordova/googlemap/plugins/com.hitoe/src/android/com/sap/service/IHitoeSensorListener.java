/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\I307412\\Desktop\\CTSDemo\\code\\sap\\SAPPhireDemo\\app\\src\\main\\aidl\\com\\sap\\sapphiredemo\\service\\IHitoeSensorListListener.aidl
 */
package com.sap.service;
// Declare any non-default types here with import statements

public interface IHitoeSensorListListener extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.sap.service.IHitoeSensorListListener
{
private static final java.lang.String DESCRIPTOR = "com.sap.service.IHitoeSensorListListener";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.sap.service.IHitoeSensorListListener interface,
 * generating a proxy if needed.
 */
public static com.sap.service.IHitoeSensorListListener asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.sap.service.IHitoeSensorListListener))) {
return ((com.sap.service.IHitoeSensorListListener)iin);
}
return new com.sap.service.IHitoeSensorListListener.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_OnListSensor:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.OnListSensor(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.sap.service.IHitoeSensorListListener
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
/**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
@Override public void OnListSensor(java.lang.String aString) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(aString);
mRemote.transact(Stub.TRANSACTION_OnListSensor, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_OnListSensor = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
/**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
public void OnListSensor(java.lang.String aString) throws android.os.RemoteException;
}
