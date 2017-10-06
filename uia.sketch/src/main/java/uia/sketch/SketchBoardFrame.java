package uia.sketch;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Enumeration;
import java.util.Locale;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.FontUIResource;

/**
 *
 * @author Kyle K. Lin
 *
 */
public class SketchBoardFrame extends JFrame {

    private static final long serialVersionUID = -7449421668829108966L;

    private FileNaviPanel filePanel;

    private PhotoPanel photoPanel;

    private ControlPanel controlPanel;

    private StatusPanel statusPanel;

    private File lastFile;

    private JMenuBar menuBar;

    private JMenu fileMenu;

    private JMenuItem newMenuItem;

    private JMenuItem openMenuItem;

    private JMenuItem saveMenuItem;

    private JMenuItem saveAsMenuItem;

    private JMenuItem addMenuItem;

    private JMenuItem deleteMenuItem;

    private JMenuItem clearMenuItem;

    private JMenuItem exitMenuItem;

    private JMenu editMenu;

    private JMenuItem undoMenuItem;

    private JMenuItem redoMenuItem;

    private JMenu helpMenu;

    private JMenuItem aboutMenuItem;

    private JSplitPane splitPane;

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            if (args.length > 0) {
                Locale.setDefault(new Locale(args[0]));
                Resources.LOCALE = Locale.getDefault();
            }

            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            String fontName = "Monospace";
            int fontSize = 12;
            if (Resources.LOCALE != Locale.TAIWAN && Resources.LOCALE != Locale.TRADITIONAL_CHINESE) {
                fontName = "Tahoma";
                FontUIResource f = new FontUIResource(new Font(fontName, Font.PLAIN, fontSize));
                Enumeration<Object> keys = UIManager.getDefaults().keys();
                while (keys.hasMoreElements()) {
                    Object key = keys.nextElement();
                    Object value = UIManager.get(key);
                    if (value instanceof FontUIResource) {
                        UIManager.put(key, f);
                    }
                }
            }
        }
        catch (Exception ex) {
        }

        Resources.TITLE = Resources.getString("app.Name");

        new SketchBoardFrame().setVisible(true);
    }

    /**
     *
     */
    public SketchBoardFrame() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(Resources.getImageURL("sketch.png")));
        setSize(980, 650);
        setLocation(100, 100);
        setMinimumSize(new Dimension(980, 650));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(Resources.TITLE + " - " + Resources.getString("text.NoName"));
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowOpened(WindowEvent e) {
                windowLoaded();
            }
        });

        this.splitPane = new JSplitPane();
        getContentPane().add(this.splitPane, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel();
        rightPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        rightPanel.setLayout(new BorderLayout(0, 0));
        this.splitPane.setRightComponent(rightPanel);

        // Photo Panel
        this.photoPanel = new PhotoPanel();
        this.photoPanel.setBackground(UIManager.getColor("Button.light"));
        this.photoPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        rightPanel.add(this.photoPanel);

        // Control Panel
        this.controlPanel = new ControlPanel();
        rightPanel.add(this.controlPanel, BorderLayout.EAST);

        // Status Panel
        this.statusPanel = new StatusPanel();
        getContentPane().add(this.statusPanel, BorderLayout.SOUTH);

        // File Navigator Panel
        this.filePanel = new FileNaviPanel();
        this.filePanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        this.splitPane.setLeftComponent(this.filePanel);

        // Menu Bar
        this.menuBar = new JMenuBar();
        setJMenuBar(this.menuBar);

        // Menu Bar> File
        this.fileMenu = new JMenu(Resources.getString("menu.File"));
        this.menuBar.add(this.fileMenu);

        this.newMenuItem = new JMenuItem(Resources.getString("menu.New"));
        this.newMenuItem.addActionListener(evt -> {
            this.controlPanel.reset();
            this.photoPanel.reset();
            this.filePanel.newSketchBook();
        });
        this.fileMenu.add(this.newMenuItem);

        this.openMenuItem = new JMenuItem(Resources.getString("menu.Open"));
        this.openMenuItem.addActionListener(evt -> {
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(new FileFilter() {

                @Override
                public boolean accept(File f) {
                    return f.isDirectory() || f.getAbsolutePath().toLowerCase().endsWith(".xml");
                }

                @Override
                public String getDescription() {
                    return "Sketch book (*.xml)";
                }

            });
            fc.setCurrentDirectory(this.lastFile);
            int returnVal = fc.showOpenDialog(SketchBoardFrame.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                this.lastFile = fc.getSelectedFile();
                this.filePanel.openSketchBook(this.lastFile);
            }

        });
        this.fileMenu.add(this.openMenuItem);

        this.saveMenuItem = new JMenuItem(Resources.getString("menu.Save"));
        this.saveMenuItem.addActionListener(evt -> {
            if (this.lastFile == null) {
                saveAs();
            }
            else {
                this.filePanel.saveSketchBook(this.lastFile);
            }
        });
        this.fileMenu.add(this.saveMenuItem);

        this.saveAsMenuItem = new JMenuItem(Resources.getString("menu.SaveAs"));
        this.saveAsMenuItem.addActionListener(evt -> saveAs());
        this.fileMenu.add(this.saveAsMenuItem);
        this.fileMenu.addSeparator();

        this.addMenuItem = new JMenuItem(Resources.getString("menu.Add"));
        this.addMenuItem.addActionListener(evt -> this.filePanel.addPhoto());
        this.fileMenu.add(this.addMenuItem);

        this.deleteMenuItem = new JMenuItem(Resources.getString("menu.Delete"));
        this.deleteMenuItem.addActionListener(evt -> this.filePanel.deletePhoto());
        this.fileMenu.add(this.deleteMenuItem);

        this.clearMenuItem = new JMenuItem(Resources.getString("menu.Clear"));
        this.clearMenuItem.addActionListener(evt -> {
            this.filePanel.clearPhotos();
        });
        this.fileMenu.add(this.clearMenuItem);
        this.fileMenu.addSeparator();

        this.exitMenuItem = new JMenuItem(Resources.getString("menu.Exit"));
        this.exitMenuItem.addActionListener(evt -> SketchBoardFrame.this.dispose());
        this.fileMenu.add(this.exitMenuItem);

        this.editMenu = new JMenu(Resources.getString("menu.Edit"));
        this.menuBar.add(this.editMenu);

        this.undoMenuItem = new JMenuItem(Resources.getString("menu.Undo"));
        this.undoMenuItem.setEnabled(false);
        this.editMenu.add(this.undoMenuItem);

        this.redoMenuItem = new JMenuItem(Resources.getString("menu.Redo"));
        this.redoMenuItem.setEnabled(false);
        this.editMenu.add(this.redoMenuItem);

        // Menu Bar> Help
        this.helpMenu = new JMenu(Resources.getString("menu.Help"));
        this.menuBar.add(this.helpMenu);

        this.aboutMenuItem = new JMenuItem(Resources.getString("menu.About"));
        this.helpMenu.add(this.aboutMenuItem);

    }

    public ControlPanel getControlPanel() {
        return this.controlPanel;
    }

    public StatusPanel getStatusPanel() {
        return this.statusPanel;
    }

    public PhotoPanel getPhotoPanel() {
        return this.photoPanel;
    }

    public FileNaviPanel getFileNaviPanel() {
        return this.filePanel;
    }

    private void saveAs() {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(this.lastFile);
        fc.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getAbsolutePath().toLowerCase().endsWith(".xml");
            }

            @Override
            public String getDescription() {
                return "Sketch book (*.xml)";
            }

        });
        int returnVal = fc.showSaveDialog(SketchBoardFrame.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            this.lastFile = fc.getSelectedFile();
            this.filePanel.saveSketchBook(this.lastFile);
        }
    }

    private void windowLoaded() {
        try {
            this.controlPanel.setMainFrame(this);
            this.filePanel.setMainFrame(this);
        }
        catch (Exception ex) {

        }
    }
}
