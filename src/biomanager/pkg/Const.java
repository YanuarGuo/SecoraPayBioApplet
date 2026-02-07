package biomanager.pkg;

public interface Const {
	final static byte CLA = (byte) 0x80;

	// INS
	final static byte VERIFY = (byte) 0x01;
	final static byte GET_COUNTER = (byte) 0x02;
	final static byte GET_SUPPORTED_FINGERS = (byte) 0x03;
	final static byte GET_STATUS = (byte) 0x04;
	final static byte ENABLE_BIOMANAGER = (byte) 0x05;
	final static byte RESET_COUNTER = (byte) 0x06;
	final static byte CHANGE_EPIN = (byte) 0x07;
}
