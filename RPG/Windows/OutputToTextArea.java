package Windows;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.*;

public class OutputToTextArea extends OutputStream {
    private JTextArea textArea;
    private ByteArrayOutputStream buffer = new ByteArrayOutputStream();

    public OutputToTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }
    // 將輸出流連接到 JTextArea
    @Override
    public void write(int b) throws IOException {
        if(b == '\n'){
            // 當遇到換行符號時，將緩衝區的內容寫入 JTextArea
            textArea.append(buffer.toString() + "\n");
            textArea.setCaretPosition(textArea.getDocument().getLength());
            buffer.reset(); // 清空緩衝區;
        } else {
            // 將單個字節寫入緩衝區
            buffer.write(b);
        }
        // 自動滾動到底
        textArea.setCaretPosition(textArea.getDocument().getLength()); 
    }
}
