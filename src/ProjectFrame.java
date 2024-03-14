import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectFrame extends JFrame{
    private JTextField nameText;
    private JTextField numText;
    private JCheckBox doneCheck;
    private JTextField dateText;
    private JRadioButton radioBt1;
    private JRadioButton radioBt2;
    private JRadioButton radioBt3;
    private JButton prevBt;
    private JButton nextBt;
    private JButton saveBt;
    private JPanel panel;
    private JTextField fundsText;
    private int index;
    private BigDecimal total = new BigDecimal(0);
    private JFileChooser fc = new JFileChooser(".");
    private List<workProject> projectList = new ArrayList<>();
    private JMenuBar mainMenu = new JMenuBar();
        private JMenu projectMenu = new JMenu("Project");
            private JMenuItem addBtn = new JMenuItem("Přidej další");
        private JMenu staticticMenu = new JMenu("Statistika");
            private  JMenuItem staBtn = new JMenuItem("Statistika");
            private int myRating;



    public ProjectFrame(){

        initWindow();
        initMenu();


        projectList.add(new workProject("Weby", 2, false, LocalDate.now(), 3, new BigDecimal(10000)));
        projectList.add(new workProject("Němčina", 1, true, LocalDate.now(), 3, new BigDecimal(100)));
        projectList.add(new workProject("Matematika", 2, false, LocalDate.now(), 1, new BigDecimal(2500)));

        update();
    }
    public int getMyRating(){
        if (radioBt1.isSelected()) myRating = 1;
        if (radioBt2.isSelected()) myRating = 2;
        if (radioBt3.isSelected()) myRating = 3;
    }

    public static void main(String[] args) {
        ProjectFrame frame = new ProjectFrame();
    }
    public void initWindow(){
        setContentPane(panel);
        setVisible(true);
        setTitle("Projekty");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ButtonGroup group = new ButtonGroup();
        group.add(radioBt1);
        group.add(radioBt2);
        group.add(radioBt3);

        nextBt.addActionListener(e -> move(true));
        prevBt.addActionListener(e -> move(false));
        saveBt.addActionListener(e -> saveFile());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                save();
            }
        });
    }
    public void initMenu(){
        setJMenuBar(mainMenu);

        mainMenu.add(projectMenu);
        mainMenu.add(staticticMenu);

        projectMenu.add(addBtn);
        staticticMenu.add(staBtn);

        addBtn.addActionListener(e -> addProject());
        staBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Celkové náklady: " + totalFunds()));
    }
    public void save(){
        projectList.get(index).setName(nameText.getText());
        projectList.get(index).setCount(Integer.parseInt(numText.getText()));
        projectList.get(index).setDone(doneCheck.isSelected());
        projectList.get(index).setFunds(new BigDecimal(fundsText.getText()));
        projectList.get(index).setRating(getMyRating());
        //projectList.get(index)
    }
    public BigDecimal totalFunds(){

        for(workProject project : projectList){
            total = total.add(project.getFunds());
        }
        return total;
    }
    public void update(){
        nameText.setText(projectList.get(index).getName());
        numText.setText(String.valueOf(projectList.get(index).getCount()));
        doneCheck.setSelected(projectList.get(index).isDone());
        dateText.setText(String.valueOf(projectList.get(index).getStartDate()));

        switch (projectList.get(index).getRating()){
            case 1:
                radioBt1.setSelected(true);
            case 2:
                radioBt2.setSelected(true);
            case 3:
                radioBt3.setSelected(true);
        }
        fundsText.setText(String.valueOf(projectList.get(index).getFunds()));
        //nameText.addActionListener(e -> editable = true);


    }

    public void saveFile(){
       fc.setFileFilter(new FileNameExtensionFilter("Text", "txt"));
        int result = fc.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION){
            writer(fc.getSelectedFile());

        }
    }
    public void addProject(){
        if (isEdited()) {
            projectList.add(new workProject("---", 0, false, null, 1, null));
        } else {
            JOptionPane.showMessageDialog(this, "You need to change the current new file");
        }
    }

    public void writer(File file){
        try (Writer wr = new BufferedWriter(new FileWriter(file))){
            wr.append(String.valueOf(textToSave()));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "IOE problem found");
        }
    }
    public StringBuilder textToSave(){
        StringBuilder text = new StringBuilder();
        for (workProject project: projectList){
            text.append(project.getName()).append("#").append(project.getCount()).append("#").append(project.getFunds()).append("#").append(project.getRating()).append("#").append(project.getStartDate()).append("#").append(project.isDone()).append("\n");
        }
        return text;
    }
    public void move(boolean right){
        if (!projectList.isEmpty()){
            if (right & index+1 < projectList.size()){
                index++;

                update();
            } else if (!right & index >= 1) {
                index--;

                update();
            }
        }
    }
    public boolean isEdited() {
        return !nameText.getText().equals("---") || !numText.getText().equals("0") || !dateText.getText().equals("null") || !fundsText.getText().equals("null");

    }

}


