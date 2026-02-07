package biomanager.pkg;

import javacard.framework.*;
import org.globalplatform.GPSystem;
import org.globalplatform.GlobalService;
import org.globalplatform.broker.*;
import com.ifx.javacard.applets.biometry.biomanager.*;

public class BiomanagerInterface extends Applet implements CDCVMBrokerListener, Shareable, Const {
	short MATCHING_STATUS = 0;
	short MATCHING_SCORE = 0;
	CDCVMBrokerCallbackRequest BIOMANAGER_HANDLE;
	
	
	public static void install(byte[] bArray, short bOffset, byte bLength){
		new BiomanagerInterface(bArray, bOffset, bLength);
	}
	
	
	protected BiomanagerInterface(byte[] bArray,short bOffset,byte bLength) {
		register();
	}

	
	public void process(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		
		if(selectingApplet()) {
			return;
		}
		
		if ((byte)(buffer[ISO7816.OFFSET_CLA]&0xFF) != (byte)CLA) {
			ISOException.throwIt(ISO7816.SW_CLA_NOT_SUPPORTED); 
	 	}
		
		getBioManagerHandle();
		switch(buffer[ISO7816.OFFSET_INS]) 
		{ 
			case VERIFY:
				processVerifyFingerprint(apdu);
				return;	
			
			case GET_COUNTER: 
				processGetCounter(apdu);
				return;
			
			case GET_SUPPORTED_FINGERS: 
				processGetSupportedFingers(apdu);
				return;
			
			case GET_STATUS: 
				processGetBiomanagerStatus(apdu);
				return;
			
			case ENABLE_BIOMANAGER: 
				processEnableBiomanager(apdu);
				return;
			
			case RESET_COUNTER:
				processResetCounter(apdu);
				return;
			
			case CHANGE_EPIN:
				processChangeEPIN(apdu);
				return;
			
			case (byte)0x08:	
				ISOException.throwIt(ISO7816.SW_NO_ERROR);
				return;
			
			default:
				ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
	}
	

	public void processVerifyFingerprint(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		
		BIOMANAGER_HANDLE.requestCallback(OBJECT_CDCVM_BIO_FINGER_DEFAULT, (short)0, CDCVMBrokerCallbackRequest.REQUEST_CDCVM_NEW_VERIFICATION);
		Util.setShort(buffer, (short)0, MATCHING_STATUS);
		apdu.setOutgoingAndSend((short) 0, (short)2);
	}
	
	
	public void processGetCounter(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		short outLen = 0;
		
		outLen = ((CDCVMBrokerCallbackRequestX)BIOMANAGER_HANDLE).requestCallbackX(CDCVMBrokerCallbackRequestX.REQUEST_GET_CURRENT_BTC, buffer, ISO7816.OFFSET_P2, (short)(buffer[ISO7816.OFFSET_LC] + 2), buffer, (short)0);
		apdu.setOutgoingAndSend((short) 0, outLen);
	}
	
	
	public void processGetSupportedFingers(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		short outLen = 0;
		
		outLen = ((CDCVMBrokerCallbackRequestX)BIOMANAGER_HANDLE).requestCallbackX(CDCVMBrokerCallbackRequestX.REQUEST_GET_BIO_NUM_OF_FINGERS, buffer, ISO7816.OFFSET_P2, (short)(buffer[ISO7816.OFFSET_LC] + 2), buffer, (short)0);
		apdu.setOutgoingAndSend((short) 0, outLen);
	}
	
	
	public void processGetBiomanagerStatus(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		short outLen = 0;
		
		outLen = ((CDCVMBrokerCallbackRequestX)BIOMANAGER_HANDLE).requestCallbackX(CDCVMBrokerCallbackRequestX.REQUEST_GET_BIO_MANAGER_STATE, buffer, ISO7816.OFFSET_P2, (short)(buffer[ISO7816.OFFSET_LC] + 2), buffer, (short)0);
		apdu.setOutgoingAndSend((short) 0, outLen);
	}
	
	
	public void processEnableBiomanager(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		short outLen = 0;
		
		outLen = ((CDCVMBrokerCallbackRequestX)BIOMANAGER_HANDLE).requestCallbackX(CDCVMBrokerCallbackRequestX.REQUEST_ENABLE_BIOMETRICS, null, (short)0, (short)(0), null, (short)0);
		Util.setShort(buffer, (short)0, outLen);
		apdu.setOutgoingAndSend((short) 0, (short)2);
	}
	
	
	public void processResetCounter(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		short outLen = 0;
		
		outLen = ((CDCVMBrokerCallbackRequestX)BIOMANAGER_HANDLE).requestCallbackX(CDCVMBrokerCallbackRequestX.REQUEST_RESET_BTC, null, (short)0, (short)(0), null, (short)0);
		Util.setShort(buffer, (short)0, outLen);
		apdu.setOutgoingAndSend((short) 0, (short)2);
	}
	
	
	public void processChangeEPIN(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		short outLen = 0;
		
		if(buffer[ISO7816.OFFSET_LC]!= 0x08) 
		{
			ISOException.throwIt(ISO7816.SW_CONDITIONS_NOT_SATISFIED);
		}
		if (BIOMANAGER_HANDLE != null) 
		{
			outLen = ((CDCVMBrokerCallbackRequestX)BIOMANAGER_HANDLE).requestCallbackX(CDCVMBrokerCallbackRequestX.REQUEST_UPDATE_BIO_MANAGER_EPIN, buffer, ISO7816.OFFSET_CDATA, (short)8, null, (byte)0x00);
			Util.setShort(buffer, (short)0, outLen);
			apdu.setOutgoingAndSend((short) 0, (short)2);
		}
	}
	
	
	public void onBrokerEvent(short context, short object, short attributes, short event, short environment, short assurance, short security, short device, short discretionary) {
		MATCHING_STATUS = event;
		MATCHING_SCORE = discretionary;
	}


	private void getBioManagerHandle() {
		if(BIOMANAGER_HANDLE == null) { 
			GlobalService service = GPSystem.getService((AID)null, CDCVMBrokerCallbackRequest.SERVICE_BROKER_CDCVM);
			if (service != null) {
				BIOMANAGER_HANDLE = (CDCVMBrokerCallbackRequest)service.getServiceInterface(GPSystem.getRegistryEntry(null), (short)0x00, null,(short)0 , (short)0);	
			}
			else{ 
				ISOException.throwIt(ISO7816.SW_FUNC_NOT_SUPPORTED);	
			}
		}
	}
	
	
	public Shareable getShareableInterfaceObject(AID clientAID, byte b_parameter){
		return this;
	}
}