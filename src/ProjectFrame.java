import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
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
    private int index;
    private JFileChooser fc = new JFileChooser(".");
    private List<workProject> projectList = new ArrayList<>();
    private JMenuBar mainMenu = new JMenuBar();
        private JMenu projectMenu = new JMenu("Project");
            private JMenuItem addBtn = new JMenuItem("Přidej další");
        private JMenu staticticMenu = new JMenu("Statistika");
            private  JMenuItem staBtn = new JMenuItem("Statistika");

    private boolean addable;
    public ProjectFrame(){

        initWindow();
        initMenu();


        projectList.add(new workProject("Weby", 2, false, LocalDate.now(), 3, new BigDecimal(10000)));
        projectList.add(new workProject("Němčina", 1, true, LocalDate.now(), 3, new BigDecimal(100)));
        projectList.add(new workProject("Matematika", 2, false, LocalDate.now(), 1, new BigDecimal(2500)));

        update();
    }

    public static void main(String[] args) {
        ProjectFrame frame = new ProjectFrame();
    }
    public void initWindow(){
        setContentPane(panel);
        setVisible(true);
        setTitle("Projekty");
        setSize(400, 500);

        ButtonGroup group = new ButtonGroup();
        group.add(radioBt1);
        group.add(radioBt2);
        group.add(radioBt3);

        nextBt.addActionListener(e -> move(true));
        prevBt.addActionListener(e -> move(false));
        saveBt.addActionListener(e -> saveFile());
    }
    public void initMenu(){
        setJMenuBar(mainMenu);

        mainMenu.add(projectMenu);
        mainMenu.add(staticticMenu);

        projectMenu.add(addBtn);
        staticticMenu.add(staBtn);

        addBtn.addActionListener(e -> addProject());
        staBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Celkové náklady" + projectList.get(index).getFunds()));
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
        projectList.add(new workProject("---",0, false, null, 1, null));
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
}
