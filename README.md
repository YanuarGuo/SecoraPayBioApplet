#SECORA Pay Bio Documentation#


Life Cycle

* Factory state
* Card Production
* Ready to enroll
* Finger 1 enrollment in progress
* Partially enrolled
* Finger 2 enrollment in progress
* Enrollment at POS in progress
* Enrolled with PIN
* Enrolled at POS
* Biometric enabled
* Biometric disabled







Product Configuration

* Module AID for EMV Library: D276000004150100000A0011
* Module AID for Broker: A00000015108
* Module AID for Biomanager: D27600000415020000090001
* Applet module AID for Biomanager: D2760000041502000009000101







Secure Channel Protocols (SCP)

* SCP02, implementation options '15' and '55', key size TDES 128 bits
* SCP03, implementation options '10' and '00', key sizes AES 128, 192, 256 bits
* Avoid using SCP01 / SCP11
* The product supports migrating from SCP '02' to SCP '03' and vice versa by updating the default keyset with corresponding key type







Application Identifier (AID)

* AID of the ISD is ‘A000000003 000000’. The AID may be changed using the STORE DATA command as described in STORE DATA







Load File Data Block Hash (LFDBH)

* SHA 1
* SHA 256







Ciphered Load File Data Block (CLFDB)

* TDES 2 Keys bits CBC
* AES 128, 192, 256 CBC







Disable Load Functionality

* In addition, the load functionality with LFDB and CLFDB can be configured to be automatically disabled, independently of each other, when the card state is transitioned SECURED state. This configuration is possible by Infineon, during production
* APDU to disable load of LFDB is: '80 E6 40 00 0B 00 00 00 00 05 EF 03 D9 01 02 00'







APDU Command Reference

* SLJ39B\_AdminGuide, Table 6
* SLJ39B\_BioManager\_Applet\_Concept\_Specification, Section 1.3 / Section 4
* SLJ39B\_Biomanager\_Application\_User\_Guide, Section 7







JC IDE Plugin SECORA Pay Bio, provides guidance to Infineon Customers about how to install and use the biometric functions in Javacard IDE (JCIDE) plugin for SECORA™ Pay Bio (SLJ39B) products.





Biometric Manager Menu:

* Perso BioManager
* Enroll Finger 1 (captures in one go)
* Enroll Finger 2 (captures in one go)
* Capture Finger
* Abort Enrollment
* Verify Enrollment
* Enable Biometric Operation
* Erase Enrolled
* Get Enrollment State





Command and response data field (lc \& data) of Perform Biometric Operation command

START ENROLLMENT:

* P1P2: '01', Finger ID
* Command: Absent
* Response: 9000 and 2 bytes of response code followed by – ‘5B’-‘01’ – Value. Value between ‘0x00’ and ‘0x64’ indicating the percentage of estimation of the progress of the acquisition process. 6200 and 2 bytes of response code. Other SW and Data Absent





CONTINUE ENROLLMENT:

* P1P2: '03', Finger ID
* Command: Absent
* Response: 9000 and 2 bytes of response code followed by – ‘5B’-‘01’ – Value. Value between ‘0x00’ and ‘0x64’ indicating the percentage of estimation of the progress of the acquisition process. 6200 and 2 bytes of response code. Other SW and Data Absent





ABORT ENROLLMENT:

* P1P2: '02', Finger ID
* Command: Absent
* Response: 9000 and 2 bytes of response code (27 EF). 6200 and 2 bytes of response code. Other SW and Data Absent





VERIFY BIOMETRIC REFERENCE:

* P1P2: '0400'
* Command: Absent
* Response: 9000 and 5 bytes of response code indicating the matching info. 6200 and 2 bytes of response code. Other SW and Data Absent





WIPE BIOMETRIC REFERENCE:

* P1P2: '0500'
* Command: Absent
* Response: 9000/Other SW and Data Absent. 6200 and 2 bytes of response code





FINALIZE ENROLLMENT:

* P1P2: '8000'
* Command: Absent 
* Response: 9000/Other SW and Data Absent. 6200 and 2 bytes of response code





Common BioService Response Message:

* CB 21 : BIO\_SRV\_STATUS\_PASS 
* AB 17 : BIO\_SRV\_STATUS\_FAIL 
* 8D DE : BIO\_SRV\_REJECT\_TIMEOUT 
* 37 89 : BIO\_SRV\_REJECT\_INSUFFICIENT\_CURRENT 
* 27 10 : BIO\_SRV\_ENROLL\_ENROLLMENT\_DONE 
* A9 CE : BIO\_SRV\_ENROLL\_PROGRESS 
* 82 C3 : BIO\_SRV\_ENROLL\_ERROR 
* CA 70 : BIO\_SRV\_ENROLL\_REJECT 
* 9F F2 : BIO\_SRV\_ENROLL\_REJECT\_ALREADY\_ENROLLED 
* 43 3E : BIO\_SRV\_ENROLL\_REJECT\_FULLY\_ENROLLED 
* 27 EF : BIO\_SRV\_ENROLL\_CANCEL\_DONE 
* 82 C4 : BIO\_SRV\_ENROLL\_FINGER\_ID\_INVALID 
* 95 B3 : BIO\_SRV\_VERIFY\_MATCH 
* 69 BA : BIO\_SRV\_VERIFY\_NO\_MATCH 
* 12 3C : BIO\_SRV\_VERIFY\_NOT\_ATTEMPTED 
* 78 9A : BIO\_SRV\_FINGER\_PRESENT 
* 4C A3 : BIO\_SRV\_FINGER\_MISSING 
* EC 95 : BIO\_SRV\_FINGER\_CHECK\_FAIL 





PERSONALIZATION FLOW AND COMMANDS



**Pre-Perso Steps (Optional)**

-> Card reset 

<- ATR 



-> SELECT card manager 

<- Card manager file control information (FCI), '9000'


-> INITIALIZE UPDATE 

<- Key information and card challenge, '9000'



-> EXTERNAL AUTHENTICATE 

<- 9000



-> PUT KEY (key rotation)  

<- 9000



**Application Installation**

-> Card reset 

<- ATR 



-> SELECT card manager 

<- Card manager FCI, '9000'



-> INITIALIZE UPDATE 

<- Key information and card challenge, '9000'



-> EXTERNAL AUTHENTICATE 

<- 9000 



-> INSTALL \[for install and make selectable] Biomanager Application 

<- 9000 



-> SET STATUS (set card life cycle state to INITIALIZED) – Optional step 

<- 9000 



**Perso Steps via Biomanager Applet Selection**

-> Card reset 

<- ATR 



-> SELECT Biomanager Application 

<- 9000 



-> INITIALIZE UPDATE 

<- Key information and card challenge, '9000'



-> EXTERNAL AUTHENTICATE 

<- 9000



-> STORE DATA first DGI 

<- 9000



-> STORE DATA second DGI 

<- 9000 



\[...] 



-> STORE DATA last DGI (LAST STORE DATA) 

<- 9000 



**Perso Steps via Install for Perso**

-> Card reset 

<- ATR 



-> SELECT Secure Domain application 

<- Secure Domain application FCI, '9000'



-> INITIALIZE UPDATE 

<- Key information and card challenge, '9000'



-> EXTERNAL AUTHENTICATE 

<- 9000 



-> INSTALL \[for personalize] Biomanager Application 

<- 9000 



-> STORE DATA first DGI 

<- 9000 



-> STORE DATA second DGI 

<- 9000 



\[...] 



-> STORE DATA last DGI (LAST STORE DATA) 

<- 9000 



**Post-Perso Steps (Optional)**

-> Card reset 

<- ATR



-> SELECT card manager 

<- Card manager FCI, '9000'



-> INITIALIZE UPDATE 

<- Key information and card challenge 



-> EXTERNAL AUTHENTICATE 

<- 9000



-> SET STATUS (set card life cycle state to SECURED) 

<- 9000 



-> PUT KEY (key rotation) 

<- 9000



-> STORE DATA (CPLC)  

<- 9000 





Supported DGIs (Data Grouping Identifier)



Presence of DGI:

* O = Optional DGI
* M = Mandatory DGI



C001

* Data Element: BSOC settings
* Length: 2/3 | 1 | 1 | 1 (in order)
* Presence: O | M | M | O (in order)
* Value: ... | Re-enrollment support | Re-enrollment max count | Anti spoofing support (in order)



C002

* Data Element: Biometric enrollment config data
* Length: 1
* Presence: O
* Value: Number of sub templates to enroll for one finger



C003

* Data Element: Biometric related data
* Length: 2/3 | 1 | 1 | 1 (in order)
* Presence: M | M | M | O (in order)
* Value: ... | Biometric try limit | Biometric try counter | Number of fingers (in order)



C004

* Data Element: Biometric enrollment activation data
* Length: 10 | 8 | 1 | 1 (in order)
* Presence: M | M | M | M (in order)
* Value: ... | Enrollment PIN | Enroll PIN try limit | Enroll PIN try counter



C005

* Data Element: Wait time for enroll
* Length: 4
* Presence: O
* Value: Timer value in ms

&nbsp;

C006

* Data Element: Wait time for verify
* Length: 4
* Presence: O
* Value: Timer value in ms

 

C007

* Data Element: Applet AID whitelist
* Length: Variable Max 240
* Presence: M
* Value: Whitelisted AID that used by applet with maximum of 240 bytes

 

C008

* Data Element: Low end enrollment
* Length: 1
* Presence: O
* Value: Low end enrollment support

 

C00B

* Data Element: Template update config
* Length: 1
* Presence: O
* Value: 00 = not supported | 01-FE = Maximum number of times template is updated after enrollment on a successful biometric verification | FF = Template update happens throughout the lifetime of the card on a successful biometric verification

 

C00C

* Data Element: Low end sleeve LEDs behavior patterns
* Length: Variable
* Presence: O
* Value: Low end sleeve LEDs behavior patterns

 

C00D

* Data Element: High end sleeve configuration
* Length: 1
* Presence: O
* Value: High end sleeve configuration

 



**OPERATIONAL PHASE**



Command supported

* External auth: 84 82 xx 00 10
* Get data: 80 CA xx xx (tag number) xx 00
* Get param: 80 02 xx xx (tag number) xx xx
* Init update: 80 50 xx 00 08 00
* Low end enroll: 80 2D 00 00 xx 00
* Biometric operation: 80 2E xx xx - 00
* Put data: 80 DA xx xx (tag number) xx -
* Run test: 80 01 xx xx xx 00
* Select: 00 A4 00 00 xx 00
* Self test: 80 04 00 00 - 00
* Set param: 80 03 xx xx (tag number) xx 00
* Store data: 80/84 E2 xx xx xx 00
* Verify ePIN: 80 20 00 00 xx 00



Tags Supported (Put Data)

* DF79: Biometric try counter
* DF7A: Biometric try limit
* DF52: Waiting time for finger being presented during verification
* DF51: Waiting time for finger being presented during enrollment
* DF53: Tag 01, Length 01, Value - Number of fingers to enroll (1 or 2) | Tag 05, Length 01, Value - Number of touches required for one finger to enroll (default 12) | Tag 06, Length 01, Value - Number of enrolled subtemplates for 1st Finger | Tag 07, Length 01, Value - Number of enrolled subtemplates for 2nd Finger
* DF54: Tag 14, length 1 byte, available current 1 byte
* DF45: Tag 10, length variable, list of last error codes of BEP library
* DF46: Finger present status
* DF47: Application state
* DF48: Enrollment capability: re-enrollment supported
* DF49: Enrollment PIN try counter
* DF4A: Enrollment PIN try limit
* DF4B: Tag 04, Length 1 byte, anti spoofing support
* DF4C: Image capture configuration
* DF4D: Version info of BEP Lib and BioService, and fingerprint sensor production details
* DF4E: Internal state of BioService (include lock level)
* DF4F: Applet version
* DF55: Sensor diagnostic data
* 5F00: Tag list retrieve data of one or more data elements



---------- APDU Logs ----------



GET ENROLLMENT STATE



00A404000DD276000004150200000900010100 (Select BioManager)

9000



80CADF4700 (Get data application state)

DF47025A5A9000



80CADF7900 (Get data biometric try counter)

DF7901059000



80CADF7A00 (Get data biometric try limit)

DF7A01059000



80CADF5300 (Get data number of fingers to enroll, touches required, enrolled sub templates for 1st finger, enrolled sub templates for 2nd finger)

DF530C01010205010C0601000701009000



80CADF4800 (Get data enrollment capacity)

DF4801A59000



80CADF4B00 (Get data anti spoofing support)

DF4B030401A59000



80CADF4600 (Get data finger present status)

DF46015A9000



80CADF4E00 (Get data internal state of BioSrv include lock level)

DF4E061304FF550FF09000



80CADF5800 (Get data template update option)

DF58030801FF9000



80CADF4F00 (Get data applet version)

DF4F04010000009000





---

ENROLL IN ONE GO





00A404000DD276000004150200000900010100 (Select BioManager)

9000



80CADF4700 (Get data application state)

DF47025A5A9000



00A404000DD276000004150200000900010100 (Select BioManager)

9000



8020000008123456FFFFFFFFFF00 (Verify ePIN)

9000



80CADF5300 (Get data number of fingers to enroll, touches required, enrolled sub templates for 1st finger, enrolled sub templates for 2nd finger)

DF530C 010102 05010C 060100 070100 9000



80CADF4700 (Get data application state)

DF47025A5A9000



80CADF4600 (Get data finger present status)

DF46015A9000



every finger tap:

802E 0300 00 (Perform Biometric Operation, 0300 (03 + finger ID) = Continue enrollment)

A9 CE 5B 01 08 9000



802E030000

A9 CE 5B 01 10 9000



802E030000

A9 CE 5B 01 18 9000



802E030000

A9 CE 5B 01 20 9000



802E030000

A9 CE 5B 01 28 9000



802E030000

A9 CE 5B 01 30 9000



802E030000

A9 CE 5B 01 38 9000



802E030000

A9 CE 5B 01 40 9000



802E030000

A9 CE 5B 01 48 9000



802E030000

A9 CE 5B 01 50 9000



802E030000

A9 CE 5B 01 58 9000



802E030000

27 10 5B 01 64 9000





While the 80CADF4600 command is still returning DF46015A9000 (status code 5A, which means finger not present), the 80CADF4700 command will be sent together with 80CADF4600.



However, if the 80CADF4600 command returns DF4601A59000 (status code A5, which means finger present) for the first time, the 802E030000 command will be sent once, and the 80CADF4700 command will be stopped as long as 80CADF4600 continues to return DF4601A59000 (finger present).



When the 80CADF4600 command returns to DF46015A9000 (finger not present), the 80CADF4700 command will be sent again together with 80CADF4600.



The above process will be repeated until the 802E030000 command returns 27 10 in the first and second bytes.





---

ERASE FINGERPRINT





00A404000DD276000004150200000900010100

9000



8020000008123456FFFFFFFFFF00

9000



802E050000

9000





---

VERIFY FINGERPRINT





00A404000DD276000004150200000900010100

9000



8020000008123456FFFFFFFFFF00

9000



// Failed

802E040000

8DDE6200





// Success

802E040000

03E8FC17009000





---

ABORT ENROLL





00A404000DD276000004150200000900010100

9000



80CADF4700

DF47025AC39000



8020000008123456FFFFFFFFFF00

9000



802E020100 (ABORT COMMAND)

6985





---

MANUAL CAPTURE (ENROLL ONE BY ONE)





example 1

00A404000DD276000004150200000900010100

9000



80CADF4700

DF4702A53C9000



8020000008123456FFFFFFFFFF00

9000



802E030100

A9CE5B01189000



80CADF5300

DF530C01010205010C06010C0701039000





example 2

00A404000DD276000004150200000900010100

9000



80CADF4700

DF4702A53C9000



8020000008123456FFFFFFFFFF00

9000



802E030100/802E030000

A9CE5B01209000



80CADF5300

DF530C01010205010C06010C0701049000





---

ENABLE BIOMETRIC





00A404000DD276000004150200000900010100

9000



8020000008123456FFFFFFFFFF00

9000



802E800000

6985





Key Manager:

* Import key SLJ39BxxxxxX
* OP Issuer SD -> Configure keys for SCP02/SCP03 -> Key type ENC/MAC/DEK -> Use SLJ39BxxxxxX
* SLJ39xxxxxX tools -> Configure keys for SCP02/SCP03 -> Key ENC/MAC/DEK -> Use SLJ39BxxxxxX
* Authenticate for SCP02/SCP03





Using Biomanager API and Broker Interface in Java Card IDE (Integrating with Applet)

1. Download libraries (global platform broker, biomanager, jcdk 3.0.5 / jcdk 3.1.0)
2. Install SLJ39B Java Card IDE plugin
3. Choose Window -> Preferences
4. In IFX Javacard, under library settings, choose  “New”
5. Setup Biomanager and Global Platform Broker library
6. After applying, close the window
7. Go to IFX Javacard -> Development environment -> New
8. Choose import settings -> SLJ39Bxxxxxx to include the pre-installed extensions and libraries for SLJ39B
9. Assign the name for the development environment to the new development environment, click “Add”
10. Choose the libraries in the development environment (add Biomanager and Global Platform Broker)
11. Once it is done, click OK
12. Apply and Close



Pre-requisite to use biomanager API in proprietary applet

* In order to use the biomanager API for inter application communication with proprietary applet, the instance AID need to register in the AID whitelist of Biomanager. This can be done via personalization of DGI C007 of Biomanager applet
* Example:
  Store Data...
  --> 84 E2 00 06 23
  C0 07 18 FF 0C 09 4F 07 **A0 00 00 00 04 10 10** FF
  0C 09 4F 07 **A0 00 00 00 03 10 10** 63 4F 10 E8 83
  5E 3D 8D
  SW: 90 00  Data:    0 Bytes  Exec Time:   231.90 ms
* Do avoid deletion of the Biomanager instance post completion of production testing, this will make the card unusable and will require reloading of the OS due to the state mismatch of the OS (BioService) and Biomanager, hence repersonalization of Biomanager is not advised.



Creating new Java Card Project with Biomanager

1. Choose File -> New -> Java Card Project
2. Assign the project name
3. Choose the development environment (Biomanager and Global Platform Broker have to be included inside the environment library)
4. Next
5. Assign the package name and AID (use whitelisted AID from DGI C007)
6. Assign the applet name and AID (use whitelisted AID from DGI C007)



Using Biometric Service with Applet

1. Personalize biomanager with whitelisted AID from the created applet
2. Load the created applet .cap file into the smart card
3. Install applet with default privilege and whitelisted instance AID
4. Under Configurator, right click the respective biomanager profile , choose “Enroll in One Go” option to enroll the finger
5. Enroll the finger as suggested by Java Card IDE
6. Once the enrollment is completed, we can test the Biomanager API with the recently created applet



Biomanager Interface Documentation

CLA: 80

INS: 01-08



INS 01

* Verify enrolled fingerprints
* C-APDU: 80 01 00 00
* R-APDU: 5A A5 (Verified) | A5 5A (Unrecognized) | 00 00 (No fingerprint tapped)



INS 02

* Get biometric try counter
* C-APDU: 80 02 00 00
* R-APDU: 05 (attempts)



INS 03

* Get number of fingers supported
* C-APDU: 80 03 00 00
* R-APDU: 02 (fingers supported)



INS 04

* Get biomanager state
* C-APDU: 80 04 00 00
* R-APDU: 5A 5A (ready to enroll) | 5A C3 (Enrollment in progress or partially enrolled) | A5 C3 (enrolled with PIN) | A5 A5 (biometrics enabled)
* Note: Return 5A 5A ketika captured fingerprint masih kosong (berjumlah 0 pada setiap number of fingers) BIO\_SRV\_STATE\_READY\_2\_ENROLL \& READY\_TO\_ENROLL, 5A C3 ketika jumlah number of fingers belum sepenuhnya dilakukan enroll dengan fingerprints user/client (3/12 fingerprints captured pada finger nomor 1 atau finger 1 fully enrolled namun finger 2 masih unenrolled) BIO\_SRV\_STATE\_PARTIALLY\_ENROLLED \& PARTIALLY\_ENROLLED, A5 C3 ketika biomanager sudah fully enrolled BIO\_SRV\_STATE FULLY ENROLLED \& ENROLLED\_WITH\_PIN, A5 A5 ketika biometric operation sudah diaktifkan (perintah enable biometric operation) BIO\_SRV\_STATE FULLY\_ENROLLED \& BIOMETRIC\_VERIFICATION\_ENABLED



INS 05,

* Enable biometric verification
* C-APDU: 80 05 00 00
* R-APDU: 5A A5 (success) | 69 85 (already enabled)



INS 06,

* Reset biometric try counter
* C-APDU: 80 06 00 00
* R-APDU: 5A A5 (success)



INS 07,

* Change ePIN
* C-APDU: 80 07 00 00
* R-APDU: 5A A5 (success)



INS 08,

* Communication test
* C-APDU: 80 08 00 00
* R-APDU: 90 00 (success)





Biomanager API Documentation

The following APIs enable the interaction between BioManager applet and other registered application(s):

* CDCVMBrokerCallbackRequest
* CDCVMBrokerListener
* CDCVMBrokerCallbackRequestX



Parameter Values for the CDCVMBrokerCallbackRequest API

Example: **CDCVMBrokerCallbackRequest.requestCallback(Object, Attribute, Request)**

* Object shall be set to CDCVMBrokerListener.OBJECT\_CDCVM\_BIO\_FINGER\_DEFAULT (0x0300)
* Attribute is RFU and should be set to 0x0000
* Request shall be set to CDCVMBrokerListener.REQUEST\_CDCVM\_NEW\_VERIFICATION (0x0002)



Parameter Values for the CDCVMBrokerListener API

This section describes the parameters and values that used by the BioManager applet while invoking the **CDCVMBrokerListener.onBrokerEvent (context, object, attributes, event, environment, assurance, security, device, discretionary)** method.

* Context: 0
* Object: CDCVMBrokerListener.OBJECT\_CDCVM\_BIO\_FINGER\_DEFAULT (0x0300)
* Attributes: byte 1 0x00 | byte 2 BTL/BTC value
* Event: Biometric Verification Status based on current verification attempt:
  CDCVMBrokerListener.EVENT\_CDCVM\_EVENT\_NOT\_PERFORMED (0x0000) or,
  CDCVMBrokerListener.EVENT\_CDCVM\_EVENT\_VERIFICATION\_SUCCEEDED (0x5AA5) or,
  CDCVMBrokerListener.EVENT\_CDCVM\_EVENT\_VERIFICATION\_FAILED (0xA55A) or,
  CDCVMBrokerListener.EVENT\_CDCVM\_EVENT\_BLOCKED (0xA555) or,
  CDCVMBrokerListener.EVENT\_CDCVM\_EVENT\_NOT\_ENROLLED (0x0001)
* Environment: CDCVMBrokerListener.ENV\_CDCVM\_VERIFICATION\_SE (0x0050)
* Assurance: CDCVMBrokerListener.ASSURANCE\_CDCVM\_UNKNOWN (0x0000)
* Security: CDCVMBrokerListener.SECURITY\_CDCVM\_V2B\_XIO\_NO\_SC (0x0020)
* Device: CDCVMBrokerListener.DEVICE\_CDCVM\_CAPTURE\_UNKNOWN (0x0000)
* Discretionary: 2 bytes of score returned from BioService



Parameter Values for the CDCVMBrokerCallbackRequestX API

This section describes the parameter values used by the Registered Application while invoking the **CDCVMBrokerCallBackRequestX.requestCallbackX (request, inputBuf, inOffset, inLength, outputBuf, outOffset)** method. Note: If the request is invalid in current application state of BioManager applet, then an ISOException is thrown with status word 0x6985.



Request

Request specification as listed in table below:

* REQUEST\_GET\_CURRENT\_BTL: 0x0101
* REQUEST\_GET\_CURRENT\_BTC: 0x0102
* REQUEST\_GET\_ENROLLMENT\_STATUS: 0x0103
* REQUEST\_GET\_BIO\_MANAGER\_STATE: 0x0104
* REQUEST\_GET\_BIO\_NUM\_OF\_FINGERS: 0x0105
* REQUEST\_IS\_RE\_ENROLLMENT\_SUPPORTED: 0x0106
* REQUEST\_GET\_BIO\_SERVICE\_DATA: 0x0107
* REQUEST\_UPDATE\_BIO\_MANAGER\_BTL: 0x0201
* REQUEST\_UPDATE\_BIO\_MANAGER\_EPIN: 0x0202
* REQUEST\_UPDATE\_BIO\_MANAGER\_EPTL: 0x0203
* REQUEST\_UPDATE\_BIO\_MANAGER\_VERIFY\_WAIT\_TIME: 0x0204
* REQUEST\_UPDATE\_RE\_ENROLLMENT\_REMAINING\_COUNTER: 0x0205
* REQUEST\_UNBLOCK\_BIO\_MANAGER: 0x0206
* REQUEST\_BLOCK\_BIO\_MANAGER: 0x0207
* REQUEST\_ERASE\_BIO\_MANAGER\_DATA: 0x0208
* REQUEST\_BIO\_CAPTURE\_TEMPLATE: 0x0209
* REQUEST\_BIO\_FINALIZE\_TEMPLATE: 0x020A
* REQUEST\_ENABLE\_BIOMETRICS: 0x020B
* REQUEST\_UNBLOCK\_EPIN: 0x020C
* REQUEST\_RESET\_BTC: 0x020D
* REQUEST\_BIO\_POS\_INVALIDATE: 0x020E



InputBuf

Input buffer (byte array) containing input data to be sent to BioManager



InOffset

Start offset within input buffer



InLength

Length of input data



OutputBuf

Output buffer (byte array) containing the data to be received from BioManager



OutOffset

Start offset within output buffer



Return Value

* Length of output data in output buffer when data is requested from BioManager applet
* 0x5AA5 when no output data is requested



Parameter mapping with Request IDs

This section provides the mapping of each parameter to be passed to invoke requestCallbackX method. Note: Any parameter where it is mentioned as ‘Present’ indicates that parameter is mandatory for the request id. Otherwise, the BioManager applet throws an ISOException with status word 0x6985. The parameters with values ‘null’ and ‘0’ are not interpreted by the BioManager applet even if the Registered Application passes different values.



REQUEST\_GET\_CURRENT\_BTL:

* InputBuf: null
* InOffset: 0
* InLength: 0
* OutputBuf: Buffer having minimum of 1 byte available from 'OutOffset'
* OufOffset: Present
* Return Value: Length of BTL = 0x01



REQUEST\_GET\_CURRENT\_BTC:

* InputBuf: null
* InOffset: 0
* InLength: 0
* OutputBuf: Buffer having minimum of 1 byte available from 'OutOffset'
* OufOffset: Present
* Return Value: Length of BTC = 0x01



REQUEST\_GET\_ENROLLMENT\_STATUS:

* InputBuf: null
* InOffset: 0
* InLength: 0
* OutputBuf: Buffer having minimum of 1 byte available from 'OutOffset'
* OufOffset: Present
* Return Value: Length of Enrollment status = 0x01



REQUEST\_GET\_BIO\_MANAGER\_STATE:

* InputBuf: null
* InOffset: 0
* InLength: 0
* OutputBuf: Buffer having minimum of 2 byte available from 'OutOffset'
* OufOffset: Present
* Return Value: Length of Biomanager = 0x02



REQUEST\_GET\_BIO\_NUM\_OF\_FINGERS:

* InputBuf: null
* InOffset: 0
* InLength: 0
* OutputBuf: Buffer having minimum of 1 byte available from 'OutOffset'
* OufOffset: Present
* Return Value: Length of Number of Fingers value = 0x01



REQUEST\_IS\_RE\_ENROLLMENT\_SUPPORTED:

* InputBuf: null
* InOffset: 0
* InLength: 0
* OutputBuf: Buffer having minimum of 1 byte available from 'OutOffset'
* OufOffset: Present
* Return Value: 0x5A - No, 0xA5 - Yes



REQUEST\_GET\_BIO\_SERVICE\_DATA:

* InputBuf: Buffer containing tag value of BioSrv data
* InOffset: Present
* InLength: Present
* OutputBuf: Buffer having sufficient bytes available to store output data from ‘OutOffset’
* OufOffset: Present
* Return Value: Length of response data



REQUEST\_UPDATE\_BIO\_MANAGER\_BTL:

* InputBuf: Buffer containing BTL value
* InOffset: Present
* InLength: Length of BTL = 0x01
* OutputBuf: null
* OufOffset: 0
* Return Value: 0x5AA5 - Success



REQUEST\_UPDATE\_BIO\_MANAGER\_EPIN:

* InputBuf: Buffer containing EPIN value
* InOffset: Present
* InLength: Length of EPIN = 0x01
* OutputBuf: null
* OufOffset: 0
* Return Value: 0x5AA5 - Success



REQUEST\_UPDATE\_BIO\_MANAGER\_EPTL:

* InputBuf: Buffer containing EPTL value
* InOffset: Present
* InLength: Length of EPTL = 0x01
* OutputBuf: null
* OufOffset: 0
* Return Value: 0x5AA5 - Success



REQUEST\_UPDATE\_BIO\_MANAGER\_VERIFY\_WAIT\_TIME:

* InputBuf: Buffer containing value of Wait time for Verify
* InOffset: Present
* InLength: Length of Wait time for Verify = 0x04
* OutputBuf: null
* OufOffset: 0
* Return Value: 0x5AA5 - Success



REQUEST\_UPDATE\_RE\_ENROLLMENT\_REMAINING\_COUNTER:

* InputBuf: Buffer containing value of Enrollment remaining counter
* InOffset: Present
* InLength: Length of Wait time for Enrollment remaining counter = 0x01
* OutputBuf: null
* OufOffset: 0
* Return Value: 0x5AA5 - Success, 0xA55A - Failure



REQUEST\_UNBLOCK\_BIO\_MANAGER:

* InputBuf: null
* InOffset: 0
* InLength: 0
* OutputBuf: null
* OufOffset: 0
* Return Value: 0x5AA5 - Success



REQUEST\_BLOCK\_BIO\_MANAGER:

* InputBuf: null
* InOffset: 0
* InLength: 0
* OutputBuf: null
* OufOffset: 0
* Return Value: 0x5AA5 - Success



REQUEST\_ERASE\_BIO\_MANAGER\_DATA:

* InputBuf: null
* InOffset: 0
* InLength: 0
* OutputBuf: null
* OufOffset: 0
* Return Value: 0x5AA5 - Success



REQUEST\_BIO\_CAPTURE\_TEMPLATE:

* InputBuf: Buffer containing input structure for capture template
* InOffset: Present
* InLength: Present
* OutputBuf: Buffer having 0x11 bytes available to store output data from ‘OutOffset’
* OufOffset: Present
* Return Value: Response length = 0x11



REQUEST\_BIO\_FINALIZE\_TEMPLATE:

* InputBuf: Buffer containing input structure for finalize template
* InOffset: Present
* InLength: Present
* OutputBuf: Buffer having atleast sufficient space available to store output data from ‘OutOffset’
* OufOffset: Present
* Return Value: 0x5AA5 - Success



REQUEST\_ENABLE\_BIOMETRICS:

* InputBuf: null
* InOffset: 0
* InLength: 0
* OutputBuf: null
* OufOffset: 0
* Return Value: 0x5AA5 - Success



REQUEST\_UNBLOCK\_EPIN:

* InputBuf: null
* InOffset: 0
* InLength: 0
* OutputBuf: null
* OufOffset: 0
* Return Value: 0x5AA5 - Success



REQUEST\_RESET\_BTC:

* InputBuf: null
* InOffset: 0
* InLength: 0
* OutputBuf: null
* OufOffset: 0
* Return Value: 0x5AA5 - Success



REQUEST\_BIO\_POS\_INVALIDATE:

* InputBuf: Buffer containing input structure for POS invalidate
* InOffset: Present
* InLength: Present
* OutputBuf: Buffer having atleast sufficient space available to store output data from ‘OutOffset’
* OufOffset: Present
* Return Value: 0x5AA5 - Success
