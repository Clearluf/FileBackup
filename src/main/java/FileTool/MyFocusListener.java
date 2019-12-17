package FileTool;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public  class MyFocusListener implements FocusListener {
    String info;
    JTextField jtf;
    public MyFocusListener(String info, JTextField jtf) {
        this.info = info;
        this.jtf = jtf;
    }
    public void focusGained(FocusEvent e) {//��ý����ʱ��,�����ʾ����
        String temp = jtf.getText();
        if(temp.equals(info)){
            jtf.setText("");
        }
    }
    public void focusLost(FocusEvent e) {//ʧȥ�����ʱ��,�ж����Ϊ��,����ʾ��ʾ����
        String temp = jtf.getText();
        if(temp.equals("")){
            jtf.setText(info);
        }
    }
}