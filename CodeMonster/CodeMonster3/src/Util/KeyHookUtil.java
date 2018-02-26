package Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HMODULE;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.HHOOK;
import com.sun.jna.platform.win32.WinUser.KBDLLHOOKSTRUCT;
import com.sun.jna.platform.win32.WinUser.LowLevelKeyboardProc;
import com.sun.jna.platform.win32.WinUser.MSG;

import application.Main;
import javafx.application.Platform;
import javafx.scene.control.Button;

public class KeyHookUtil extends Thread {
	public volatile boolean quit = false;
	public HHOOK hhk;
	public LowLevelKeyboardProc keyboardHook;
	public final User32 lib = User32.INSTANCE;
	public HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);
	public File file=new File("log.txt");
	public static int CodeNumber;
	public Button btn;//�������ϵİ�ť
	private static  boolean flag=true;

	/**
	 *  Ĭ��ʹ��ͬһ��Ŀ¼�µ�log.txt���水�µļ�
	 */
	public KeyHookUtil() {
		Read();
	}
	public KeyHookUtil(Button btn){
		this.btn=btn;
		Read();
		Platform.runLater(()->btn.setText(CodeNumber+"��"));
	}
	/**
	 * ж�ع���
	 */
	public void stophook() {
		quit = true;
		lib.UnhookWindowsHookEx(hhk);
	}

	public void Read(){
		try {
			BufferedReader buf=new BufferedReader(new FileReader(file));
			String a=buf.readLine();
			CodeNumber=Integer.valueOf(a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * �����̲���д���ļ� K+��Ӧ��ASCLL��ֵ
	 */
	public void write() throws IOException {
		try {
			FileWriter input = new FileWriter(file);
			input.write(String.valueOf(CodeNumber));
			input.write("\r\n");
			input.close();
			//System.out.println("����д��" + data);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
//190 �����
//��������������

	public void run() {
		System.out.println("ִ��");
		while (!quit) {
			keyboardHook = new LowLevelKeyboardProc() {
				public LRESULT callback(int nCode, WPARAM wParam, KBDLLHOOKSTRUCT info) {
					if (nCode >= 0) {
						switch (wParam.intValue()) {
						case WinUser.WM_KEYUP:
							try {
								if(info.vkCode==13){
									if(Main.CountNumber%2==1){
										if(flag){
										Platform.runLater(()->btn.setText(CodeNumber+"��"));
										System.out.println("�õ��س�����");
										CodeNumber++;
										write();
										ReadWirteFile.AddToday();
										}
									}
								}else if(info.vkCode==190){
									System.out.println("�õ�.����");
									flag=false;
								}else if(37<=info.vkCode&&info.vkCode<=40){
									System.out.println("�õ����������");
								}else if(info.vkCode==113){
									flag=true;
									//��ͣ
									Main.CountNumber++;
									if(Main.CountNumber%2==1){
										Platform.runLater(()->Start());
									}else{
										Platform.runLater(()->Stop());
									}
								}else{
									flag=true;
								}
								//System.err.println("in callback, key=" + info.vkCode + " " + (char) (info.vkCode));
							} catch (IOException e) {
								e.printStackTrace();
							}
							break;
						case WinUser.WM_KEYDOWN:
							break;
						case WinUser.WM_SYSKEYUP:
							break;
						case WinUser.WM_SYSKEYDOWN:
							if (info.vkCode == 81) {
								quit = true;
							}
							break;
						}
					}
					Pointer ptr = info.getPointer();
					long peer = Pointer.nativeValue(ptr);
					return lib.CallNextHookEx(hhk, nCode, wParam, new LPARAM(peer));
				}
			};
			hhk = lib.SetWindowsHookEx(WinUser.WH_KEYBOARD_LL, keyboardHook, hMod, 0);
			// This bit never returns from GetMessage
			int result;
			MSG msg = new MSG();
			while ((result = lib.GetMessage(msg, null, 0, 0)) != 0) {
				if (result == -1) {
					System.err.println("error in get message");
					break;
				} else {
					System.err.println("got message");
					lib.TranslateMessage(msg);
					lib.DispatchMessage(msg);
				}
			}
		}
	}
	public void Start(){
		btn.setId("Record");
		btn.getStylesheets().add("/UI/main.css");
	}
	public void Stop(){
		btn.setId("notRecord");
		btn.setText("��ͣ��¼");
		btn.getStylesheets().add("/UI/main.css");
	}
}


