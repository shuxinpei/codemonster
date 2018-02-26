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
	public Button btn;//主界面上的按钮
	private static  boolean flag=true;

	/**
	 *  默认使用同一级目录下的log.txt保存按下的键
	 */
	public KeyHookUtil() {
		Read();
	}
	public KeyHookUtil(Button btn){
		this.btn=btn;
		Read();
		Platform.runLater(()->btn.setText(CodeNumber+"行"));
	}
	/**
	 * 卸载钩子
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
	 * 将键盘操作写入文件 K+对应的ASCLL码值
	 */
	public void write() throws IOException {
		try {
			FileWriter input = new FileWriter(file);
			input.write(String.valueOf(CodeNumber));
			input.write("\r\n");
			input.close();
			//System.out.println("键盘写入" + data);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
//190 点操作
//上下左右在里面

	public void run() {
		System.out.println("执行");
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
										Platform.runLater(()->btn.setText(CodeNumber+"行"));
										System.out.println("得到回车操作");
										CodeNumber++;
										write();
										ReadWirteFile.AddToday();
										}
									}
								}else if(info.vkCode==190){
									System.out.println("得到.操作");
									flag=false;
								}else if(37<=info.vkCode&&info.vkCode<=40){
									System.out.println("得到方向键操作");
								}else if(info.vkCode==113){
									flag=true;
									//暂停
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
		btn.setText("暂停记录");
		btn.getStylesheets().add("/UI/main.css");
	}
}


