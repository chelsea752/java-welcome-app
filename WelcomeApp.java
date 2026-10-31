import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * برنامهٔ خوش‌آمدگویی با Java Swing
 *
 * رفتار برنامه:
 *  - کلیک روی دکمهٔ اصلی  → نمایش پیام خوش‌آمدگویی
 *  - بستن پنجره (✕)      → نمایش پیام خداحافظی و خروج
 *  - دکمهٔ «بستن صفحه»    → همان مسیر خروج با پیام خداحافظی
 */
public class WelcomeApp extends JFrame {

    private static final Color CARD_BG       = Color.WHITE;
    private static final Color TITLE_COLOR   = new Color(0x1B2559);
    private static final Color TEXT_COLOR    = new Color(0x5A6688);
    private static final Color ACCENT        = new Color(0x2F6BFF);
    private static final Color ACCENT_HOVER  = new Color(0x1E56E0);
    private static final Color ACCENT_DOWN   = new Color(0x1746BC);
    private static final Color BADGE_BG      = new Color(0xE8F0FF);
    private static final Color GRADIENT_TOP  = new Color(0xEAF1FF);
    private static final Color GRADIENT_BOT  = new Color(0xC9D9FF);

    private static final String FONT_FAMILY = pickFontFamily();
    private static final Font FONT_TITLE  = new Font(FONT_FAMILY, Font.BOLD, 25);
    private static final Font FONT_BODY   = new Font(FONT_FAMILY, Font.PLAIN, 15);
    private static final Font FONT_BUTTON = new Font(FONT_FAMILY, Font.BOLD, 16);

    public WelcomeApp() {
        super("برنامهٔ خوش‌آمدگویی");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        setContentPane(buildContent());
        pack();
        setSize(Math.max(getWidth(), 560), Math.max(getHeight(), 480));
        setMinimumSize(new Dimension(520, 460));
        setLocationRelativeTo(null);
        applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeWithGoodbye();
            }
        });
    }

    /** ساخت کل رابط کاربری. */
    private JPanel buildContent() {
        GradientPanel root = new GradientPanel();
        root.setLayout(new GridBagLayout());
        root.setBorder(new EmptyBorder(28, 28, 28, 28));

        RoundedPanel card = new RoundedPanel(28, CARD_BG);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(32, 42, 28, 42));

        LogoBadge badge = new LogoBadge();

        JLabel title = new JLabel("به برنامهٔ ما خوش آمدید!");
        title.setFont(FONT_TITLE);
        title.setForeground(TITLE_COLOR);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel subtitle = new JPanel();
        subtitle.setOpaque(false);
        subtitle.setLayout(new BoxLayout(subtitle, BoxLayout.Y_AXIS));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setPreferredSize(new Dimension(340, 46));
        subtitle.setMinimumSize(new Dimension(340, 46));
        subtitle.setMaximumSize(new Dimension(340, 46));

        JLabel subtitleLine1 = new JLabel("برای دریافت پیام خوش‌آمدگویی");
        subtitleLine1.setFont(FONT_BODY);
        subtitleLine1.setForeground(TEXT_COLOR);
        subtitleLine1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLine2 = new JLabel("روی دکمهٔ زیر کلیک کنید.");
        subtitleLine2.setFont(FONT_BODY);
        subtitleLine2.setForeground(TEXT_COLOR);
        subtitleLine2.setAlignmentX(Component.CENTER_ALIGNMENT);

        subtitle.add(Box.createVerticalGlue());
        subtitle.add(subtitleLine1);
        subtitle.add(Box.createVerticalStrut(2));
        subtitle.add(subtitleLine2);
        subtitle.add(Box.createVerticalGlue());

        RoundedButton welcomeButton =
                new RoundedButton("دریافت پیام خوش‌آمدگویی", ACCENT, ACCENT_HOVER, ACCENT_DOWN);
        welcomeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeButton.setPreferredSize(new Dimension(300, 56));
        welcomeButton.setMinimumSize(new Dimension(300, 56));
        welcomeButton.setMaximumSize(new Dimension(300, 56));
        welcomeButton.addActionListener(e -> showWelcomeMessage());

        JButton closeButton = new JButton("بستن صفحه");
        closeButton.setFont(FONT_BODY);
        closeButton.setForeground(TEXT_COLOR);
        closeButton.setContentAreaFilled(false);
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeButton.setPreferredSize(new Dimension(130, 30));
        closeButton.setMinimumSize(new Dimension(130, 30));
        closeButton.setMaximumSize(new Dimension(130, 30));
        closeButton.addActionListener(e -> closeWithGoodbye());

        JLabel hint = new JLabel("با بستن پنجره هم پیام خداحافظی نمایش داده می‌شود.");
        hint.setFont(new Font(FONT_FAMILY, Font.PLAIN, 13));
        hint.setForeground(new Color(0x6F7A99));
        hint.setAlignmentX(Component.CENTER_ALIGNMENT);
        hint.setPreferredSize(new Dimension(340, 20));
        hint.setMinimumSize(new Dimension(340, 20));
        hint.setMaximumSize(new Dimension(340, 20));

        card.add(badge);
        card.add(Box.createVerticalStrut(14));
        card.add(title);
        card.add(Box.createVerticalStrut(10));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(26));
        card.add(welcomeButton);
        card.add(Box.createVerticalStrut(12));
        card.add(closeButton);
        card.add(Box.createVerticalStrut(12));
        card.add(hint);

        root.add(card);
        return root;
    }

    /** پیام خوش‌آمدگویی هنگام کلیک روی دکمه. */
    private void showWelcomeMessage() {
        showMessage(
                "پیام خوش‌آمدگویی",
                "سلام! به برنامهٔ ما خوش آمدید.\nامیدواریم لحظات خوبی داشته باشید.");
    }

    /** پیام خداحافظی و سپس بستن برنامه. */
    private void closeWithGoodbye() {
        showMessage("پیام خداحافظی", "خداحافظ! روز خوبی داشته باشید.");
        dispose();
        System.exit(0);
    }

    /** نمایش یک گفت‌وگوی راست‌به‌چپ با فونت مناسب فارسی. */
    private void showMessage(String title, String message) {
        String html = "<html><div align='center'>"
                + message.replace("\n", "<br>")
                + "</div></html>";

        JLabel text = new JLabel(html);
        text.setFont(new Font(FONT_FAMILY, Font.PLAIN, 15));
        text.setForeground(TITLE_COLOR);
        text.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        JOptionPane pane = new JOptionPane(text, JOptionPane.INFORMATION_MESSAGE);
        pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        JDialog dialog = pane.createDialog(this, title);
        dialog.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        dialog.setVisible(true);
        dialog.dispose();
    }

    /** انتخاب اولین فونت در دسترس که از فارسی پشتیبانی می‌کند. */
    private static String pickFontFamily() {
        String[] preferred = {"Vazirmatn", "Segoe UI", "Tahoma", "Arial"};
        Set<String> available = new HashSet<>(Arrays.asList(
                GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));
        for (String family : preferred) {
            if (available.contains(family)) {
                return family;
            }
        }
        return Font.SANS_SERIF;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // در صورت پشتیبانی نشدن، ظاهر پیش‌فرض Swing استفاده می‌شود.
        }
        SwingUtilities.invokeLater(() -> new WelcomeApp().setVisible(true));
    }

    // ---------------------------------------------------------------------
    // اجزای گرافیکی سفارشی
    // ---------------------------------------------------------------------

    /** پس‌زمینهٔ گرادیانی ملایم. */
    private static class GradientPanel extends JPanel {
        GradientPanel() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setPaint(new GradientPaint(
                    0, 0, GRADIENT_TOP,
                    0, getHeight(), GRADIENT_BOT));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
            super.paintComponent(g);
        }
    }

    /** کارت سفید با گوشه‌های گرد. */
    private static class RoundedPanel extends JPanel {
        private final int radius;
        private final Color background;

        RoundedPanel(int radius, Color background) {
            this.radius = radius;
            this.background = background;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(background);
            g2.fill(new RoundRectangle2D.Double(
                    0, 0, getWidth(), getHeight(), radius, radius));
            g2.dispose();
            super.paintComponent(g);
        }
    }

    /** دکمهٔ آبی با گوشه‌های گرد و تغییر رنگ هنگام هاور/کلیک. */
    private static class RoundedButton extends JButton {
        private static final int RADIUS = 16;

        private final Color baseColor;
        private final Color hoverColor;
        private final Color pressedColor;

        RoundedButton(String text, Color baseColor, Color hoverColor, Color pressedColor) {
            super(text);
            this.baseColor = baseColor;
            this.hoverColor = hoverColor;
            this.pressedColor = pressedColor;

            setFont(FONT_BUTTON);
            setForeground(Color.WHITE);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setOpaque(false);
            setRolloverEnabled(true);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setBorder(new EmptyBorder(14, 28, 14, 28));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            ButtonModel model = getModel();
            Color fill;
            if (!isEnabled()) {
                fill = new Color(0xB9C3DB);
            } else if (model.isPressed() && model.isArmed()) {
                fill = pressedColor;
            } else if (model.isRollover()) {
                fill = hoverColor;
            } else {
                fill = baseColor;
            }

            g2.setColor(fill);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), RADIUS, RADIUS);
            g2.dispose();

            super.paintComponent(g);
        }
    }

    /** نشان دایره‌ای با تیک، بالای کارت. */
    private static class LogoBadge extends JComponent {
        LogoBadge() {
            Dimension size = new Dimension(66, 66);
            setPreferredSize(size);
            setMinimumSize(size);
            setMaximumSize(size);
            setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(BADGE_BG);
            g2.fillOval(1, 1, 63, 63);

            g2.setColor(ACCENT);
            g2.setStroke(new BasicStroke(2.5f));
            g2.drawOval(2, 2, 61, 61);

            g2.setStroke(new BasicStroke(4f,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.drawLine(21, 34, 30, 43);
            g2.drawLine(30, 43, 46, 24);
            g2.dispose();
        }
    }
}
