package DictView;

import java.awt.*;
import javax.swing.*;

public class aboutFrame extends JDialog {

    private static final long serialVersionUID = 1L;
    JLabel image;
    JTextArea aboutArea;

    String info = "   Some people like to read on a screen. Other people need the variety and artistry, the sight, smell, \nand feel of actual knowledge. We love seeing them on their shelves; we love having shelves for them.\n\n"
            + "   We love taking them along when we leave the house and stacking them by their bedsides. We love \nfinding old letters and bookmarks in them. \n\n"
            + "   We want to read in a way that offers a rich experience, more than the words only: the full offering ofadictionary. They are particular about covers, we want to surround ourselves with the definition of \ngood design.\n\n"
            + "   We are constantly expanding our content scope with new, fresh material to help further educate ourusers as our community is growing bigger every day. Our goal is to help as many people as possible with reliable,high-quality, and easy-to-use reference material.\n\n"
            + "   The leading dictionary featuring over 25,000 definitions spanning across critical definition topics. \nEach definition provides a clear and concise description of the term to help our users gain a \ncomprehensive understanding of the concept. \n\n"
            + "   CONTACT US:\n" + "   Email:Vukhanhly30@gmail.com\n" + "   Address: HUD3 TayNam LinhDam Street";

    public aboutFrame(Frame parent) {
        super(parent, "About us");
        this.setSize(888, 613);
        this.setLayout(null);
        this.setResizable(false);
        this.setModal(true);
        this.setLocationRelativeTo(null);
        image = new JLabel(new ImageIcon("assets/icon.png"));
        aboutArea = new JTextArea(info);
        aboutArea.setLineWrap(true);
        aboutArea.setEditable(false);

        image.setBounds(600, 385, 210, 210);
        aboutArea.setBounds(10, 10, 868, 380);

        this.add(image);
        this.add(aboutArea);

    }

}