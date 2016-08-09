package com;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * ���ĵı�λ��
 * @author hanliang
 *
 */
public class ReverseWord {

	/**
	 * �����ַ�����λ��
	 * @param s
	 * @return 
	 * 0:ż������Ҫȫ������
	 * 1:����������ֻ��1�����ַ�
	 */
	private int getLength(String s){
		if(s.length() % 2 == 0){
			return 0;
		}else{
			return 1;
		}
	}
	
	/**
	 * String�ַ���תbyte[]������
	 * @param s
	 * @return �������ַ�����
	 */
	private byte[] getBytes(String s){
		byte[] b = s.getBytes();
		for (int i = 0; i < b.length; i++) {
			for (int j = i+1; j < b.length; j++) {
				if(b[i] > b[j]){
					Byte temp = b[i];
					b[i] = b[j];
					b[j] = temp;
				}
			}
		}
		return b;
	}
	
	/**
	 * �鿴������byte[]�����У��ж��ٸ��������ֽ�
	 * @param b
	 * @return �������������е����ַ��ĸ���
	 */
	private int getSingleCounts(byte[] b){
		int singleNum = 0;
		Map<Byte,Integer> map = new HashMap<Byte,Integer>();
		for (int i = 0; i < b.length; i++) {
			if(map.containsKey(b[i])){
				map.put(b[i], map.get(b[i]) + 1);
			}else{
				map.put(b[i], 1);
			}
		}
		Iterator<Integer> it = map.values().iterator();
		while(it.hasNext()){
			if((Integer)it.next() % 2 != 0){
				singleNum += 1;
			}
		}
		return singleNum;
	}
	
	/**
	 * ��������
	 * 1���õ��ַ����ĳ���
	 * 2���ַ���ת�ֽ�����
	 * 3�������ֽ������е����ַ��ĸ���
	 * 4�����л�����֤
	 * @param s
	 */
	public void checkWord(String s){
		//get wordLength
		int wordLength = getLength(s);
		//change to byte
		byte[] b = getBytes(s);
		//get single word appear counts
		int singleCounts = getSingleCounts(b);
		//check word
		if(wordLength == 0){
			//even word : must all mapped
			if(singleCounts != 0){
				System.out.println(s + "not a reverse change word");
			}else{
				System.out.println(s + " is a reverse change word");
			}
		}else{
			//odd word : must all mapped except one single
			if(singleCounts != 1){
				System.out.println(s + "not a revarse change word");
			}else{
				System.out.println(s + " is a reverse change word");
			}
		}
	}
	
	
	public static void main(String[] args) {
		ReverseWord rw = new ReverseWord();
		String s = "asdf1ssdsa";
		rw.checkWord(s);
		String s2 = "asd12fdsa";
		rw.checkWord(s2);
		String s3 = "dfdssaa";
		rw.checkWord(s3);
		String s4 = "1221ffssaa";
		rw.checkWord(s4);
	}
}
