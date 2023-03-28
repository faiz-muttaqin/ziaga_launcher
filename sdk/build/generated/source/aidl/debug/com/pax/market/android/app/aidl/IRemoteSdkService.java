/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\Users\\faizm\\Documents\\GitHub\\Artajasa_Launcher\\sdk\\src\\main\\aidl\\com\\pax\\market\\android\\app\\aidl\\IRemoteSdkService.aidl
 */
package com.pax.market.android.app.aidl;
public interface IRemoteSdkService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.pax.market.android.app.aidl.IRemoteSdkService
{
private static final java.lang.String DESCRIPTOR = "com.pax.market.android.app.aidl.IRemoteSdkService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.pax.market.android.app.aidl.IRemoteSdkService interface,
 * generating a proxy if needed.
 */
public static com.pax.market.android.app.aidl.IRemoteSdkService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.pax.market.android.app.aidl.IRemoteSdkService))) {
return ((com.pax.market.android.app.aidl.IRemoteSdkService)iin);
}
return new com.pax.market.android.app.aidl.IRemoteSdkService.Stub.Proxy(obj);
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
case TRANSACTION_getBaseTerminalInfo:
{
data.enforceInterface(DESCRIPTOR);
com.pax.market.android.app.sdk.dto.TerminalInfo _result = this.getBaseTerminalInfo();
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.pax.market.android.app.aidl.IRemoteSdkService
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
@Override public com.pax.market.android.app.sdk.dto.TerminalInfo getBaseTerminalInfo() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.pax.market.android.app.sdk.dto.TerminalInfo _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getBaseTerminalInfo, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.pax.market.android.app.sdk.dto.TerminalInfo.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_getBaseTerminalInfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public com.pax.market.android.app.sdk.dto.TerminalInfo getBaseTerminalInfo() throws android.os.RemoteException;
}
