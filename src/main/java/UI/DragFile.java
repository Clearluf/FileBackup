package UI;

import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DragFile {
	public JPanel p1=new JPanel();
	public JTextField jtf=new JTextField();
	public JButton b=new JButton();
    public void drag()
    {
       new DropTarget(p1,DnDConstants.ACTION_COPY_OR_MOVE,new DropTargetAdapter()
        {
           public void drop(DropTargetDropEvent dtde)
           {
               try{
                   
                   if(dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
                    {
                       dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                       List<File>list=(List<File>)(dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor));
                       
                       String temp="";
                       for(File file:list)
                        {
                           temp+=file.getAbsolutePath();
                           jtf.setText(temp);
                           b.setEnabled(true);
                           //JOptionPane.showMessageDialog(null, temp);
                           dtde.dropComplete(true);
                           
                        }
                       
                    }
                   
                   else
                    {
                       
                       dtde.rejectDrop();
                    }
                   
               }catch(Exception e){e.printStackTrace();}
               
           }
           
           
        });
       
       
    }
}
