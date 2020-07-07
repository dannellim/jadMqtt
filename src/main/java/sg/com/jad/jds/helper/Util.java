package sg.com.jad.jds.helper;

public class Util {

	private final static char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static String byteToHex(byte[] bytes, int dataLength) {
        char[] hexChars = new char[dataLength * 2];
        for (int j = 0; j < dataLength; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] hexToByte(String s) {
        byte[] data = null;
        s = s.replaceAll(" ", "");
        int len = s.length();
        data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static String asciiToHex(String str) {
        char[] chars = str.toCharArray();
        StringBuilder hex = new StringBuilder();
        for (char aChar : chars) {
            hex.append(Integer.toHexString((int) aChar));
        }
        return hex.toString();
    }
    
    public static String hexToAscii(String hexStr) {
        StringBuilder output = new StringBuilder();
        hexStr = hexStr.replaceAll(" ", "");
        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output.toString();
    }

    public static String hexToDec(String hex) {
        return String.valueOf(Integer.parseInt(hex, 16));
    }
    
    public static String decToHex(int num) {
    	return String.format("%02X", num);
    }
    
    public static int randomNumber(int min, int max) {
		int randomNum = (int) (Math.random() * (max - min + 1) + min);
		return randomNum; 
	}
}
