package com.ray;

import java.io.IOException;

public class JComm{
	public static final long RS232_BAUD_110 = 110;
	public static final long RS232_BAUD_300 = 300;
	public static final long RS232_BAUD_600 = 600;
	public static final long RS232_BAUD_1200 = 1200;
	public static final long RS232_BAUD_2400 = 2400;
	public static final long RS232_BAUD_4800 = 4800;
	public static final long RS232_BAUD_9600 = 9600;
	public static final long RS232_BAUD_14400 = 14400;
	public static final long RS232_BAUD_19200 = 19200;
	public static final long RS232_BAUD_38400 = 38400;
	public static final long RS232_BAUD_57600 = 57600;
	public static final long RS232_BAUD_115200 = 115200;
	public static final long RS232_BAUD_128000 = 128000;
	public static final long RS232_BAUD_256000 = 256000;

	public static final int RS232_DATA_5 = 5;
	public static final int RS232_DATA_6 = 6;
	public static final int RS232_DATA_7 = 7;
	public static final int RS232_DATA_8 = 8;

	public static final int RS232_PARITY_NONE = 0;
	public static final int RS232_PARITY_ODD = 1;
	public static final int RS232_PARITY_EVEN = 2;
	public static final int RS232_PARITY_MARK = 3;
	public static final int RS232_PARITY_SPACE = 4;

	public static final int RS232_STOP_1 = 0;
	public static final int RS232_STOP_1_5 = 1;
	public static final int RS232_STOP_2 = 2;

	public static final int RS232_DTR_DISABLE = 0;
	public static final int RS232_DTR_ENABLE = 1;
	public static final int RS232_DTR_HANDSHAKE = 2;

	public static final int RS232_RTS_DISABLE = 0;
	public static final int RS232_RTS_ENABLE = 1;
	public static final int RS232_RTS_HANDSHAKE = 2;
	public static final int RS232_RTS_TOGGLE = 3;

	public static final int RS232_FLOW_NONE = 0;
	public static final int RS232_FLOW_HW = 1;
	public static final int RS232_FLOW_XON_XOFF = 2;

	public static final int ERROR_NOERROR = 0;
	public static final int ERROR_NODATA = -1;
	public static final int ERROR_UNKNOWN = -2;
	public static final int ERROR_NOTOPEN = -3;
	public static final int ERROR_CANTOPEN = -4;
	public static final int ERROR_WRITE = -5;
	public static final int ERROR_READ = -6;
	public static final int ERROR_CLOSE = -7;

	private String device = "COM1";
	private long baud = RS232_BAUD_9600;
	private int stop = RS232_STOP_1;
	private int data = RS232_DATA_8;
	private int parity = RS232_PARITY_NONE;
	private int dtr = RS232_DTR_DISABLE;
	private int rts = RS232_RTS_DISABLE;
	private int flow = RS232_FLOW_NONE;
	private long handle = 0;
	private int timeout = 500;
	private boolean isOptionDirty = true;
	private boolean isTimeoutDirty = true;
	private static final String[] errors = new String[]{
		"no errors",
		"no data received",
		"unknown errors",
		"port has not open",
		"port cannot open",
		"port write errors",
		"port read errors",
		"port close errors"
	};
	public JComm(){}
	public JComm(String device){
		this.device = device;
		this.handle = 0;
	}
	public JComm(String device, long baud, int stop, int data, int parity){
		this.device = device;
		this.baud = baud;
		this.stop = stop;
		this.data = data;
		this.parity = parity;
		this.handle = 0;
	}
	public void setTimeout(int timeout){
		this.timeout = timeout;
		isTimeoutDirty = true;
	}
	public boolean isTimeoutDirty(){
		return isTimeoutDirty;
	}
	public boolean isOptionDirty(){
		return isOptionDirty;
	}
	
	public int getTimeout(){
		return timeout;
	}

	public void setDevice(String device){
		this.device = device;
		isOptionDirty = true;
	}
	public void setBaud(long baud){
		this.baud = baud;
		isOptionDirty = true;
	}
	public void setStop(int stop){
		this.stop = stop;
		isOptionDirty = true;
	}
	public void setData(int data){
		this.data = data;
		isOptionDirty = true;
	}
	public void setParity(int parity){
		this.parity = parity;
		isOptionDirty = true;
	}
	public void setDTR(int dtr){
		this.dtr = dtr;
		isOptionDirty = true;
	}
	public void setRTS(int rts){
		this.rts = rts;
		isOptionDirty = true;
	}
	public String getDevice(){
		return device;
	}
	public long getBaud(){
		return baud;
	}
	public int getData(){
		return data;
	}
	public int getParity(){
		return parity;
	}
	public int getDTR(){
		return dtr;
	}
	public int getRTS(){
		return rts;
	}
	@Override
	public void finalize() throws Throwable{
		close();
		super.finalize();
	}
	public byte[] readTimeout(int timeout) throws IOException{
		this.timeout = timeout;
		isTimeoutDirty = true;
		return read();
	}

	public byte[] read() throws IOException{
		return read(8);
	}
	
	public byte[] read(int length) throws IOException{
		byte[] bytes = new byte[length];
		if (isOptionDirty){
			setoption();
			isOptionDirty = false;
		}
		if (isTimeoutDirty){
			settimeout();
			isTimeoutDirty = false;
		}
		int ret = _read(bytes);
		if (ret < 0)
			throw new IOException(errors[-ret]);
		byte[] bs = new byte[ret];
		for (int i = 0; i != ret; ++i)
			bs[i] = bytes[i]; 
		return bs;
	}

	public int writeTimeout(byte[] bytes, int timeout) throws IOException{
		this.timeout = timeout;
		isTimeoutDirty = true;
		return write(bytes);
	}
	
	public int write(byte[] bytes) throws IOException{
		return write(bytes, bytes.length);
	}
	
	public int write(byte[] bytes, int length) throws IOException{
		if (length > bytes.length)
			length = bytes.length;
		if (isOptionDirty){
			setoption();
			isOptionDirty = false;
		}
		if (isTimeoutDirty){
			settimeout();
			isTimeoutDirty = false;
		}
		int ret = 0;
		if ((ret = _write(bytes, length)) < 0)
			throw new IOException(errors[-ret]);
		return ret;
	}
	
	private void setoption() throws IOException{
		int ret = 0;
		if ((ret = _setoption()) < 0)
			throw new IOException(errors[-ret]);
	}
	private void settimeout() throws IOException{
		int ret = 0;
		if ((ret = _settimeout(timeout)) < 0)
			throw new IOException(errors[-ret]);
	}
	public void open() throws IOException{
		int ret = 0;
		if ((ret = _open()) < 0)
			throw new IOException(errors[-ret]);
		setoption();
		settimeout();
	}
	public void close() throws IOException{
		int ret = 0;
		if ((ret = _close()) < 0)
			throw new IOException(errors[-ret]);
	}

	static{
		try{
			System.loadLibrary("JComm64");
		}catch (java.lang.UnsatisfiedLinkError e){
			try {
				System.loadLibrary("JComm32");
			} catch (java.lang.UnsatisfiedLinkError e2) {
				System.loadLibrary("JComm");
			}
		}
	}
	private native int _open();
	private native int _close();
	private native int _settimeout(int timeout);
	private native int _write(byte[] bytes, int length);
	private native int _read(byte[] bytes);
	private native int _setoption();
	public static native String[] getCommNames();
	public static void main(String[] args){
		new JComm();
	}
}