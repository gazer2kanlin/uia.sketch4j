package uia.sketch;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import uia.sketch.model.SketchBookTypeHelper;

/**
 * Photo panel.
 *
 * @author Kyle K. Lin
 *
 */
public class PhotoPanel extends JPanel {

    /**
     * Drag target.
     *
     * @author Kyle K. Lin
     *
     */
    public enum DragTargetType {
        PHOTO, GRID1, GRID2
    }

    private static final long serialVersionUID = -8559394957522314281L;

    private PhotoFile pf;

    private File file;

    private BufferedImage origImage;

    private Image backgroundImage;

    private final GridDrawer firstGridDrawer;

    private final GridDrawer secondGridDrawer;

    private GridDrawer selectedGridDrawer;

    private DragTargetType dragTarget;

    private Point offset;

    private double zoom;

    private int viewWidth;

    private int viewHeight;

    private SketchBoardFrame mainFrame;

    /**
     * Constructor.
     */
    public PhotoPanel() {
        this.dragTarget = DragTargetType.PHOTO;
        this.offset = new Point(0, 0);
        this.zoom = 1.0d;

        this.firstGridDrawer = new GridDrawer();
        this.firstGridDrawer.setPhotoPanel(this);
        this.secondGridDrawer = new GridDrawer(100, new Color(200, 200, 200), 0, false);
        this.secondGridDrawer.setPhotoPanel(this);
        this.selectedGridDrawer = this.firstGridDrawer;

        GridMotionListener listener = new GridMotionListener();

        setSize(900, 800);
        addMouseListener(listener);
        addMouseMotionListener(listener);
    }

    public SketchBoardFrame getMainFrame() {
        return this.mainFrame;
    }

    public void setMainFrame(SketchBoardFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    /**
     * Reload image.
     * @return
     */
    public boolean reloadImage() {
        return this.file == null ? false : loadImage(this.file);
    }

    public void clearImage() {
        this.file = null;
        this.origImage = null;
        this.backgroundImage = null;
        repaint();
    }

    /**
     * Load new image file.
     * @param fileName File with full path.
     * @return Success of not.
     */
    public boolean loadImage(PhotoFile pf) {
        this.pf = pf;
        try {
            loadImage(new File(pf.config.getPath()));
            setOffset(SketchBookTypeHelper.toPoint(pf.config.getOffset()));
            setZoom(pf.config.getZoom(), pf.config.getViewWidth(), pf.config.getViewHeight());
            return true;
        }
        catch (Exception ex) {
            return loadImage((File) null);
        }
    }

    public int getViewWidth() {
        return this.viewWidth;
    }

    public int getViewHeight() {
        return this.viewHeight;
    }

    /**
     * Load new image file.
     * @param file Image file.
     * @return Success of not.
     */
    public boolean loadImage(File file) {
        try {
            this.file = file;
            this.origImage = ImageIO.read(this.file);
            this.zoom = 1.0d;

            double ph = (double) getHeight() / this.origImage.getHeight();
            double pw = (double) getWidth() / this.origImage.getWidth();
            double zoomFix = Math.min(ph, pw);
            this.viewWidth = (int) (this.origImage.getWidth() * zoomFix);
            this.viewHeight = (int) (this.origImage.getHeight() * zoomFix);
            this.backgroundImage = this.origImage.getScaledInstance(this.viewWidth, this.viewHeight, Image.SCALE_SMOOTH);

            return true;
        }
        catch (Exception ex) {
            this.file = null;
            this.origImage = null;
            this.backgroundImage = null;
            return false;
        }
        finally {
            this.offset = new Point(0, 0);
            this.zoom = 1.0d;
            repaint();
        }
    }

    /**
     * Get offset
     * @return Offset.
     */
    public Point getOffset() {
        return this.offset;
    }

    /**
     * Set offset.
     * @param offset Offset.
     */
    public void setOffset(Point offset) {
        if (this.offset.equals(offset)) {
            return;
        }
        this.offset = offset;
        this.pf.config.setOffset(SketchBookTypeHelper.toPointString(this.offset));
        repaint();
    }

    /**
     * Get zoom.
     * @return Zoom.
     */
    public double getZoom() {
        return this.zoom;
    }

    /**
     * Set zoom.
     * @param zoom Zoom.
     */
    public void setZoom(double zoom) {
        if (this.origImage == null || this.zoom == zoom) {
            return;
        }
        this.zoom = zoom;
        this.backgroundImage = this.origImage.getScaledInstance(
                (int) (this.viewWidth * this.zoom),
                (int) (this.viewHeight * this.zoom),
                Image.SCALE_SMOOTH);
        repaint();
    }

    /**
     * Set zoom.
     * @param zoom Zoom.
     * @param viewWidth
     * @param viewHeight
     */
    public void setZoom(double zoom, int viewWidth, int viewHeight) {
        if (this.origImage == null || this.zoom == zoom) {
            return;
        }
        this.zoom = zoom;
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
        this.backgroundImage = this.origImage.getScaledInstance(
                (int) (this.viewWidth * this.zoom),
                (int) (this.viewHeight * this.zoom),
                Image.SCALE_SMOOTH);
        repaint();
    }

    /**
     * Get drag mode.
     * @return Drag mode. GRID or PHOTO.
     */
    public DragTargetType getDragMode() {
        return this.dragTarget;
    }

    /**
     * Set drag mode.
     * @param dragMode Drag mode.
     */
    public void setDragMode(DragTargetType dragMode) {
        this.dragTarget = dragMode;
    }

    /**
     * Get first grid drawer.
     * @return Grid drawer.
     */
    public GridDrawer getFirstGridDrawer() {
        return this.firstGridDrawer;
    }

    /**
     * Get second grid drawer.
     * @return Grid drawer.
     */
    public GridDrawer getSecondGridDrawer() {
        return this.secondGridDrawer;
    }

    /**
     * Get selected grid drawer.
     * @return Grid drawer.
     */
    public GridDrawer getSelectedGridDrawer() {
        return this.selectedGridDrawer;
    }

    /**
     * Set first grid drawer to be selected.
     */
    public void selectFirstGridDrawer() {
        this.selectedGridDrawer = this.firstGridDrawer;
    }

    /**
     * Set second grid drawer to be selected.
     */
    public void selectSecondGridDrawer() {
        this.selectedGridDrawer = this.secondGridDrawer;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.backgroundImage, this.offset.x, this.offset.y, this);
        this.secondGridDrawer.paint(g);
        this.firstGridDrawer.paint(g);
    }

    /**
     * Grid motion listener implementation.
     *
     * @author Kyle K. Lin
     *
     */
    class GridMotionListener extends MouseAdapter implements MouseMotionListener {

        private Point pt;

        private int x;

        private int y;

        @Override
        public void mouseClicked(MouseEvent evt) {
            if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
                // lock photo?
            }
        }

        @Override
        public void mousePressed(MouseEvent evt) {
            if (PhotoPanel.this.selectedGridDrawer == null) {
                return;
            }

            this.pt = evt.getPoint();
            if (PhotoPanel.this.dragTarget != DragTargetType.PHOTO) {
                this.x = PhotoPanel.this.selectedGridDrawer.getOffset().x;
                this.y = PhotoPanel.this.selectedGridDrawer.getOffset().y;
            }
            else {
                this.x = PhotoPanel.this.offset.x;
                this.y = PhotoPanel.this.offset.y;
            }
            PhotoPanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseReleased(MouseEvent evt) {
            this.pt = null;
            PhotoPanel.this.setCursor(Cursor.getDefaultCursor());
        }

        @Override
        public void mouseDragged(MouseEvent evt) {
            if (this.pt == null || PhotoPanel.this.selectedGridDrawer == null) {
                return;
            }

            int ox = (this.x + evt.getX() - this.pt.x);
            int oy = (this.y + evt.getY() - this.pt.y);
            if (PhotoPanel.this.dragTarget != DragTargetType.PHOTO) {
                PhotoPanel.this.selectedGridDrawer.setOffset(new Point(ox, oy));
            }
            else {
                PhotoPanel.this.setOffset(new Point(ox, oy));
            }
        }

        @Override
        public void mouseMoved(MouseEvent evt) {
        }
    }
}
