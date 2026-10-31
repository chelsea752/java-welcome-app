# راهنمای توضیح کد `WelcomeApp.java`

این راهنما برای آماده‌شدن برای ارائهٔ دانشگاهی نوشته شده است. شمارهٔ خط‌ها مطابق نسخهٔ فعلی فایل `WelcomeApp.java` است.

---

## ۱. برنامه در یک نگاه

برنامه یک پنجرهٔ دسکتاپ با **Java Swing** است:

- کاربر روی دکمهٔ آبی کلیک می‌کند → پیام خوش‌آمدگویی نمایش داده می‌شود.
- کاربر پنجره را می‌بندد یا دکمهٔ «بستن صفحه» را می‌زند → پیام خداحافظی نمایش داده می‌شود و برنامه خارج می‌شود.

این برنامه نمونهٔ **برنامه‌نویسی رویدادمحور (Event-Driven)** است؛ یعنی برنامه منتظر می‌ماند تا کاربر کاری انجام دهد (کلیک یا بستن پنجره) و سپس واکنش نشان می‌دهد.

---

## ۲. جریان اجرای برنامه

1. JVM متد `main` را اجرا می‌کند.
2. `main` ظاهر سیستم‌عامل را تنظیم می‌کند و ساخت پنجره را به **EDT** می‌سپارد.
3. سازندهٔ `WelcomeApp` پنجره را می‌سازد، اجزا را اضافه می‌کند و listenerها را ثبت می‌کند.
4. کاربر روی دکمه کلیک می‌کند → `ActionListener` اجرا می‌شود → `showWelcomeMessage()`.
5. کاربر پنجره را می‌بندد → `windowClosing` اجرا می‌شود → `closeWithGoodbye()`.
6. بعد از نمایش پیام خداحافظی، پنجره `dispose` و کل برنامه `System.exit` می‌شود.

---

## ۳. توضیح خط‌به‌خط

### خطوط ۱ تا ۹: importها

```java
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
```

- **خط ۱:** کلاس‌های Swing مثل `JFrame`، `JPanel`، `JButton`، `JLabel`، `JOptionPane` را وارد می‌کند.
- **خط ۲:** `EmptyBorder` برای ایجاد فاصلهٔ داخلی (padding) دور پنل‌ها.
- **خط ۳:** کلاس‌های پایهٔ گرافیک مثل `Color`، `Font`، `Graphics`، `Graphics2D`، `Dimension`، `Component` و `Cursor`.
- **خطوط ۴ و ۵:** `WindowAdapter` و `WindowEvent` برای واکنش به بستن پنجره.
- **خط ۶:** `RoundRectangle2D` برای رسم مستطیل با گوشه‌های گرد.
- **خطوط ۷ تا ۹:** `Arrays`، `HashSet` و `Set` برای نگه‌داشتن لیست فونت‌های نصب‌شده و جست‌وجوی سریع.

> نکته: `*` یعنی همهٔ کلاس‌های آن package وارد می‌شوند؛ نه اینکه همه در حافظه بارگذاری شوند.

### خطوط ۱۱ تا ۱۹: تعریف کلاس

```java
public class WelcomeApp extends JFrame {
```

- **خطوط ۱۱ تا ۱۸:** کامنت Javadoc است و در اجرا تأثیری ندارد؛ فقط توضیح می‌دهد برنامه چه کاری می‌کند.
- **خط ۱۹:** کلاس `WelcomeApp` از `JFrame` ارث‌بری می‌کند. یعنی خودِ این کلاس یک پنجرهٔ کامل با نوار عنوان، دکمه‌های کوچک/بزرگ/بستن و امکانات یک پنجره است.
- `public` یعنی از بیرون package هم قابل استفاده است.
- نام فایل باید با نام کلاس public یکی باشد: `WelcomeApp.java`.

### خطوط ۲۱ تا ۳۴: رنگ‌ها و فونت‌ها

```java
private static final Color CARD_BG = Color.WHITE;
private static final Color ACCENT = new Color(0x2F6BFF);
```

- **خطوط ۲۱ تا ۲۹:** رنگ‌های ثابت برنامه:
  - `CARD_BG`: رنگ سفید کارت.
  - `TITLE_COLOR`: رنگ تیرهٔ عنوان.
  - `TEXT_COLOR`: رنگ خاکستری متن.
  - `ACCENT`، `ACCENT_HOVER`، `ACCENT_DOWN`: سه رنگ دکمه در حالت عادی، هاور و فشرده.
  - `BADGE_BG`: رنگ پس‌زمینهٔ دایرهٔ بالای کارت.
  - `GRADIENT_TOP` و `GRADIENT_BOT`: رنگ بالا و پایین پس‌زمینهٔ گرادیانی.
- `static` یعنی بین همهٔ نمونه‌های کلاس مشترک است.
- `final` یعنی بعد از مقداردهی قابل تغییر نیست.
- `new Color(0x2F6BFF)` یک رنگ از نوع RGB می‌سازد.

```java
private static final String FONT_FAMILY = pickFontFamily();
private static final Font FONT_TITLE = new Font(FONT_FAMILY, Font.BOLD, 25);
private static final Font FONT_BODY = new Font(FONT_FAMILY, Font.PLAIN, 15);
private static final Font FONT_BUTTON = new Font(FONT_FAMILY, Font.BOLD, 16);
```

- **خط ۳۱:** `pickFontFamily()` اولین فونت مناسب فارسی را از بین فونت‌های نصب‌شده پیدا می‌کند.
- **خط ۳۲:** فونت عنوان: family، style = Bold، size = 25.
- **خط ۳۳:** فونت متن معمولی: Plain، size = 15.
- **خط ۳۴:** فونت دکمه: Bold، size = 16.
- این‌ها `static final` هستند تا فقط یک‌بار ساخته شوند و در کل برنامه استفاده شوند.

### خطوط ۳۶ تا ۵۳: سازنده (Constructor)

```java
public WelcomeApp() {
    super("برنامهٔ خوش‌آمدگویی");
```

- **خط ۳۷:** `super(...)` سازندهٔ `JFrame` را صدا می‌زند و عنوان پنجره را تنظیم می‌کند. باید اولین دستور سازنده باشد.

```java
setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
```

- **خط ۳۸:** رفتار پیش‌فرض دکمهٔ بستن را غیرفعال می‌کند. اگر این خط نبود، با زدن ✕ پنجره فوراً بسته می‌شد و پیام خداحافظی نمایش داده نمی‌شد.
- به‌جای آن، خودمان در `windowClosing` تصمیم می‌گیریم چه اتفاقی بیفتد.

```java
setContentPane(buildContent());
pack();
setSize(Math.max(getWidth(), 560), Math.max(getHeight(), 480));
setMinimumSize(new Dimension(520, 460));
setLocationRelativeTo(null);
applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
```

- **خط ۴۰:** متد `buildContent()` کل رابط کاربری را می‌سازد و به‌عنوان محتوای پنجره تنظیم می‌شود.
- **خط ۴۱:** `pack()` اندازهٔ پنجره را بر اساس اندازهٔ ترجیحی اجزا محاسبه می‌کند.
- **خط ۴۲:** با `Math.max` مطمئن می‌شویم پنجره از ۵۶۰×۴۸۰ کوچک‌تر نشود.
- **خط ۴۳:** حداقل اندازه‌ای که کاربر می‌تواند پنجره را به آن برساند.
- **خط ۴۴:** `null` یعنی پنجره در وسط صفحه نمایش قرار بگیرد.
- **خط ۴۵:** جهت اجزا را راست‌به‌چپ می‌کند؛ مناسب فارسی و چیدمان RTL.

```java
addWindowListener(new WindowAdapter() {
    @Override
    public void windowClosing(WindowEvent e) {
        closeWithGoodbye();
    }
});
```

- **خط ۴۷:** یک listener برای رویدادهای پنجره ثبت می‌کند.
- **خطوط ۴۷ تا ۵۲:** یک **کلاس ناشناس (Anonymous Inner Class)** از `WindowAdapter` می‌سازد.
- `WindowAdapter` یک کلاس آماده است که همهٔ متدهای `WindowListener` را خالی پیاده‌سازی کرده؛ بنابراین فقط `windowClosing` را override می‌کنیم.
- **خط ۵۰:** وقتی کاربر روی ✕ کلیک می‌کند، `closeWithGoodbye()` صدا زده می‌شود.

> نکته برای استاد: اینجا دو مفهوم مهم داریم: **event listener** و **anonymous class**.

### خطوط ۵۵ تا ۱۳۹: ساخت رابط کاربری (`buildContent`)

```java
private JPanel buildContent() {
```

- **خط ۵۶:** این متد یک `JPanel` می‌سازد و برمی‌گرداند؛ در سازنده به‌عنوان content pane تنظیم می‌شود.

```java
GradientPanel root = new GradientPanel();
root.setLayout(new GridBagLayout());
root.setBorder(new EmptyBorder(28, 28, 28, 28));
```

- **خط ۵۷:** `GradientPanel` یک پنل سفارشی با پس‌زمینهٔ گرادیانی است.
- **خط ۵۸:** `GridBagLayout` باعث می‌شود کارت سفید در وسط پنل قرار بگیرد.
- **خط ۵۹:** ۲۸ پیکسل فاصله از هر طرف.

```java
RoundedPanel card = new RoundedPanel(28, CARD_BG);
card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
card.setBorder(new EmptyBorder(32, 42, 28, 42));
```

- **خط ۶۱:** کارت سفید با شعاع گوشهٔ ۲۸ پیکسل.
- **خط ۶۲:** `BoxLayout` با محور `Y_AXIS` اجزا را از بالا به پایین می‌چیند.
- **خط ۶۳:** فاصلهٔ داخلی کارت: بالا ۳۲، چپ/راست ۴۲، پایین ۲۸.

```java
LogoBadge badge = new LogoBadge();
```

- **خط ۶۵:** یک کامپوننت سفارشی که دایره و تیک را رسم می‌کند.

```java
JLabel title = new JLabel("به برنامهٔ ما خوش آمدید!");
title.setFont(FONT_TITLE);
title.setForeground(TITLE_COLOR);
title.setAlignmentX(Component.CENTER_ALIGNMENT);
```

- **خط ۶۷:** یک برچسب متنی برای عنوان.
- **خط ۶۸:** فونت عنوان.
- **خط ۶۹:** رنگ متن.
- **خط ۷۰:** در چیدمان `BoxLayout`، این برچسب را وسط‌چین می‌کند.

```java
JPanel subtitle = new JPanel();
subtitle.setOpaque(false);
subtitle.setLayout(new BoxLayout(subtitle, BoxLayout.Y_AXIS));
subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
subtitle.setPreferredSize(new Dimension(340, 46));
subtitle.setMinimumSize(new Dimension(340, 46));
subtitle.setMaximumSize(new Dimension(340, 46));
```

- **خط ۷۲:** یک پنل برای نگه‌داشتن دو خط توضیح.
- **خط ۷۳:** `setOpaque(false)` یعنی پس‌زمینهٔ خودش را نقاشی نکند تا رنگ سفید کارت دیده شود.
- **خط ۷۴:** چیدمان عمودی.
- **خط ۷۵:** وسط‌چین در کارت.
- **خطوط ۷۶ تا ۷۸:** اندازهٔ ثابت ۳۴۰×۴۶. چون در `BoxLayout` ممکن است اجزا کشیده شوند، هر سه اندازه را ثابت می‌کنیم تا ظاهر به‌هم نریزد.

```java
JLabel subtitleLine1 = new JLabel("برای دریافت پیام خوش‌آمدگویی");
JLabel subtitleLine2 = new JLabel("روی دکمهٔ زیر کلیک کنید.");
```

- **خطوط ۸۰ تا ۸۸:** دو برچسب جدا برای دو خط متن. هر دو فونت، رنگ و وسط‌چین دارند.
- دلیل استفاده از دو `JLabel` به‌جای یک متن HTML: کنترل دقیق‌تر شکستن خط و جلوگیری از بدنهٔ متن.

```java
subtitle.add(Box.createVerticalGlue());
subtitle.add(subtitleLine1);
subtitle.add(Box.createVerticalStrut(2));
subtitle.add(subtitleLine2);
subtitle.add(Box.createVerticalGlue());
```

- **خط ۹۰:** `VerticalGlue` فضای خالی قابل‌انعطاف ایجاد می‌کند و محتوا را وسط نگه می‌دارد.
- **خط ۹۲:** `VerticalStrut(2)` یک فاصلهٔ ثابت ۲ پیکسلی بین دو خط است.

```java
RoundedButton welcomeButton =
        new RoundedButton("دریافت پیام خوش‌آمدگویی", ACCENT, ACCENT_HOVER, ACCENT_DOWN);
welcomeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
welcomeButton.setPreferredSize(new Dimension(300, 56));
welcomeButton.setMinimumSize(new Dimension(300, 56));
welcomeButton.setMaximumSize(new Dimension(300, 56));
welcomeButton.addActionListener(e -> showWelcomeMessage());
```

- **خطوط ۹۶ و ۹۷:** دکمهٔ سفارشی با متن و سه رنگ حالت‌های مختلف.
- **خط ۹۸:** وسط‌چین.
- **خطوط ۹۹ تا ۱۰۱:** اندازهٔ ثابت ۳۰۰×۵۶؛ دکمه در `BoxLayout` کشیده نشود.
- **خط ۱۰۲:** یک `ActionListener` به‌صورت **lambda** اضافه می‌کند. با کلیک، `showWelcomeMessage()` اجرا می‌شود.
- `e` پارامتر `ActionEvent` است؛ چون از آن استفاده نمی‌کنیم، فقط `e` نوشته شده.

```java
JButton closeButton = new JButton("بستن صفحه");
closeButton.setFont(FONT_BODY);
closeButton.setForeground(TEXT_COLOR);
closeButton.setContentAreaFilled(false);
closeButton.setBorderPainted(false);
closeButton.setFocusPainted(false);
closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
```

- **خط ۱۰۴:** یک دکمهٔ معمولی برای بستن.
- **خط ۱۰۵ و ۱۰۶:** فونت و رنگ متن.
- **خط ۱۰۷:** پس‌زمینهٔ پیش‌فرض دکمه نقاشی نشود.
- **خط ۱۰۸:** خط دور دکمه حذف شود.
- **خط ۱۰۹:** کادر فوکوس (نقطه‌چین) هنگام انتخاب با کیبورد نمایش داده نشود.
- **خط ۱۱۰:** وقتی موس روی دکمه است، شکل دست نمایش داده شود.

```java
closeButton.setPreferredSize(new Dimension(130, 30));
closeButton.setMinimumSize(new Dimension(130, 30));
closeButton.setMaximumSize(new Dimension(130, 30));
closeButton.addActionListener(e -> closeWithGoodbye());
```

- **خطوط ۱۱۲ تا ۱۱۴:** اندازهٔ ثابت ۱۳۰×۳۰.
- **خط ۱۱۵:** با کلیک، مسیر خروج با پیام خداحافظی اجرا می‌شود.

```java
JLabel hint = new JLabel("با بستن پنجره هم پیام خداحافظی نمایش داده می‌شود.");
hint.setFont(new Font(FONT_FAMILY, Font.PLAIN, 13));
hint.setForeground(new Color(0x6F7A99));
```

- **خط ۱۱۷:** متن راهنمای پایین کارت.
- **خط ۱۱۸:** فونت ۱۳ پیکسل.
- **خط ۱۱۹:** رنگ خاکستری ملایم.
- **خطوط ۱۲۱ تا ۱۲۳:** اندازهٔ ثابت ۳۴۰×۲۰.

```java
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
```

- **خطوط ۱۲۵ تا ۱۳۵:** اجزا به ترتیب به کارت اضافه می‌شوند.
- `createVerticalStrut(n)` یک فاصلهٔ ثابت و نامرئی به ارتفاع n پیکسل است.
- `BoxLayout` این اجزا را از بالا به پایین می‌چیند.

```java
root.add(card);
return root;
```

- **خط ۱۳۷:** کارت به پنل گرادیانی اضافه می‌شود؛ `GridBagLayout` آن را وسط صفحه قرار می‌دهد.
- **خط ۱۳۸:** پنل ساخته‌شده برگردانده می‌شود.

### خطوط ۱۴۱ تا ۱۴۶: پیام خوش‌آمدگویی

```java
private void showWelcomeMessage() {
    showMessage(
            "پیام خوش‌آمدگویی",
            "سلام! به برنامهٔ ما خوش آمدید.\nامیدواریم لحظات خوبی داشته باشید.");
}
```

- **خط ۱۴۲:** این متد هنگام کلیک روی دکمهٔ اصلی اجرا می‌شود.
- **خطوط ۱۴۳ تا ۱۴۵:** عنوان و متن پیام را به متد عمومی `showMessage` می‌دهد.
- `\n` یعنی خط جدید؛ در `showMessage` به `<br>` تبدیل می‌شود.

### خطوط ۱۴۸ تا ۱۵۳: پیام خداحافظی

```java
private void closeWithGoodbye() {
    showMessage("پیام خداحافظی", "خداحافظ! روز خوبی داشته باشید.");
    dispose();
    System.exit(0);
}
```

- **خط ۱۵۰:** پیام خداحافظی را نمایش می‌دهد. چون دیالوگ modal است، اجرای کد تا بستن آن متوقف می‌ماند.
- **خط ۱۵۱:** `dispose()` پنجره و منابع بومی (native) آن را آزاد می‌کند.
- **خط ۱۵۲:** `System.exit(0)` کل ماشین مجازی جاوا را با کد خروج ۰ (موفق) می‌بندد.
- تفاوت مهم: `dispose()` فقط پنجره را می‌بندد؛ ممکن است JVM همچنان زنده بماند. `System.exit` همهٔ threadها را پایان می‌دهد.

### خطوط ۱۵۵ تا ۱۷۳: نمایش دیالوگ (`showMessage`)

```java
String html = "<html><div align='center'>"
        + message.replace("\n", "<br>")
        + "</div></html>";
```

- **خطوط ۱۵۷ تا ۱۵۹:** متن را به HTML تبدیل می‌کند تا وسط‌چین باشد و خط جدید (`\n`) به `<br>` تبدیل شود.
- `JLabel` از HTML ساده پشتیبانی می‌کند.

```java
JLabel text = new JLabel(html);
text.setFont(new Font(FONT_FAMILY, Font.PLAIN, 15));
text.setForeground(TITLE_COLOR);
text.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
```

- **خط ۱۶۱:** یک برچسب با متن HTML.
- **خط ۱۶۲:** فونت ۱۵ پیکسل.
- **خط ۱۶۳:** رنگ متن.
- **خط ۱۶۴:** جهت متن را راست‌به‌چپ می‌کند.

```java
JOptionPane pane = new JOptionPane(text, JOptionPane.INFORMATION_MESSAGE);
pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
```

- **خط ۱۶۶:** یک `JOptionPane` از نوع اطلاعاتی می‌سازد؛ آیکون اطلاعات و دکمهٔ OK به‌صورت خودکار اضافه می‌شود.
- **خط ۱۶۷:** جهت پنجرهٔ پیام را RTL می‌کند.

```java
JDialog dialog = pane.createDialog(this, title);
dialog.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
dialog.setVisible(true);
dialog.dispose();
```

- **خط ۱۶۹:** یک `JDialog` می‌سازد که به پنجرهٔ اصلی (`this`) وابسته است و در وسط آن باز می‌شود. این دیالوگ **modal** است؛ یعنی تا بسته نشود، نمی‌توان با پنجرهٔ اصلی کار کرد.
- **خط ۱۷۰:** جهت دیالوگ را RTL می‌کند.
- **خط ۱۷۱:** `setVisible(true)` دیالوگ را نشان می‌دهد و تا بسته‌شدن آن منتظر می‌ماند.
- **خط ۱۷۲:** بعد از بستن، منابع دیالوگ آزاد می‌شوند.

> سوال احتمالی: چرا از `JOptionPane.showMessageDialog` مستقیم استفاده نکردیم؟ چون می‌خواستیم فونت، جهت RTL و ظاهر پیام را دقیق کنترل کنیم.

### خطوط ۱۷۵ تا ۱۸۶: انتخاب فونت مناسب

```java
String[] preferred = {"Vazirmatn", "Segoe UI", "Tahoma", "Arial"};
Set<String> available = new HashSet<>(Arrays.asList(
        GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));
```

- **خط ۱۷۷:** ترتیب فونت‌های ترجیحی.
- **خطوط ۱۷۸ و ۱۷۹:** لیست همهٔ فونت‌های نصب‌شدهٔ سیستم را می‌گیرد و در یک `HashSet` می‌ریزد تا جست‌وجو سریع باشد.

```java
for (String family : preferred) {
    if (available.contains(family)) {
        return family;
    }
}
return Font.SANS_SERIF;
```

- **خطوط ۱۸۰ تا ۱۸۴:** اولین فونت موجود در لیست را برمی‌گرداند.
- **خط ۱۸۵:** اگر هیچ‌کدام نبود، فونت منطقی `SansSerif` جاوا استفاده می‌شود.

> این روش باعث می‌شود برنامه روی سیستم‌های مختلف (با فونت‌های متفاوت) فارسی را درست نشان دهد.

### خطوط ۱۸۸ تا ۱۹۵: متد `main`

```java
public static void main(String[] args) {
    try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception ignored) {
        // ...
    }
    SwingUtilities.invokeLater(() -> new WelcomeApp().setVisible(true));
}
```

- **خط ۱۸۸:** نقطهٔ شروع برنامه.
- **خطوط ۱۸۹ تا ۱۹۳:** ظاهر اجزا را با ظاهر سیستم‌عامل هماهنگ می‌کند. اگر پشتیبانی نشود، `catch` آن را نادیده می‌گیرد و ظاهر پیش‌فرض Swing استفاده می‌شود.
- **خط ۱۹۴:** ساخت و نمایش پنجره را به **EDT** می‌سپارد.
- `SwingUtilities.invokeLater` یک `Runnable` می‌گیرد؛ lambda پنجره را می‌سازد و `setVisible(true)` آن را نشان می‌دهد.
- چرا EDT مهم است؟ Swing thread-safe نیست؛ همهٔ کارهای رابط کاربری باید روی Event Dispatch Thread انجام شوند تا برنامه دچار مشکل نشود.

### خطوط ۲۰۱ تا ۲۱۹: `GradientPanel`

```java
private static class GradientPanel extends JPanel {
    GradientPanel() {
        setOpaque(false);
    }
```

- **خط ۲۰۲:** یک کلاس داخلی `static` که از `JPanel` ارث‌بری می‌کند.
- **خط ۲۰۴:** `setOpaque(false)` یعنی پنل پس‌زمینهٔ پیش‌فرض خودش را نقاشی نکند؛ ما خودمان گرادیان را می‌کشیم.

```java
@Override
protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
```

- **خط ۲۰۷:** `@Override` یعنی این متد، متد کلاس پدر را بازنویسی می‌کند؛ کامپایلر امضای آن را بررسی می‌کند.
- **خط ۲۰۸:** `paintComponent` توسط Swing هنگام رسم/repaint صدا زده می‌شود.
- **خط ۲۰۹:** `Graphics` را به `Graphics2D` تبدیل می‌کند و با `create()` یک کپی می‌سازد تا تنظیمات آن روی بقیهٔ رسم‌ها اثر نگذارد.
- **خطوط ۲۱۰ و ۲۱۱:** **Antialiasing** را روشن می‌کند تا لبه‌ها و منحنی‌ها نرم و بدون پله باشند.

```java
g2.setPaint(new GradientPaint(
        0, 0, GRADIENT_TOP,
        0, getHeight(), GRADIENT_BOT));
g2.fillRect(0, 0, getWidth(), getHeight());
g2.dispose();
super.paintComponent(g);
```

- **خطوط ۲۱۲ تا ۲۱۴:** یک گرادیان عمودی از رنگ بالا به رنگ پایین می‌سازد.
- **خط ۲۱۵:** کل پنل را با گرادیان پر می‌کند.
- **خط ۲۱۶:** کپی `Graphics2D` را آزاد می‌کند.
- **خط ۲۱۷:** رسم پیش‌فرض پنل را صدا می‌زند. چون `opaque` برابر false است، پس‌زمینهٔ گرادیان پاک نمی‌شود.

### خطوط ۲۲۱ تا ۲۴۳: `RoundedPanel`

```java
private static class RoundedPanel extends JPanel {
    private final int radius;
    private final Color background;

    RoundedPanel(int radius, Color background) {
        this.radius = radius;
        this.background = background;
        setOpaque(false);
    }
```

- **خط ۲۲۲:** پنل سفارشی برای کارت سفید.
- **خطوط ۲۲۳ و ۲۲۴:** شعاع گوشه و رنگ پس‌زمینه.
- **خط ۲۲۶:** سازنده، مقدارها را ذخیره می‌کند.
- **خط ۲۲۹:** پس‌زمینهٔ پیش‌فرض نقاشی نشود.

```java
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
```

- **خطوط ۲۳۴ تا ۲۳۶:** آماده‌سازی گرافیک و antialiasing.
- **خط ۲۳۷:** رنگ کارت.
- **خطوط ۲۳۸ و ۲۳۹:** یک مستطیل با گوشه‌های گرد رسم و پر می‌کند. پارامترهای آخر، عرض و ارتفاع قوس گوشه‌ها هستند.
- **خط ۲۴۰:** آزادسازی کپی گرافیک.
- **خط ۲۴۱:** رسم پیش‌فرض.

> نکته: اگر `setOpaque(false)` نبود، گوشه‌های گرد با پس‌زمینهٔ مستطیلی پنل پر می‌شدند و ظاهر خراب می‌شد.

### خطوط ۲۴۵ تا ۲۹۴: `RoundedButton`

```java
private static class RoundedButton extends JButton {
    private static final int RADIUS = 16;
    private final Color baseColor;
    private final Color hoverColor;
    private final Color pressedColor;
```

- **خط ۲۴۶:** دکمهٔ سفارشی با ظاهر گرد.
- **خط ۲۴۷:** شعاع گوشه‌ها.
- **خطوط ۲۴۹ تا ۲۵۱:** سه رنگ برای حالت عادی، هاور و فشرده.

```java
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
```

- **خط ۲۵۴:** متن دکمه به سازندهٔ `JButton` می‌رود.
- **خطوط ۲۵۵ تا ۲۵۷:** ذخیرهٔ رنگ‌ها.
- **خط ۲۵۹:** فونت دکمه.
- **خط ۲۶۰:** رنگ متن سفید.
- **خط ۲۶۱:** پس‌زمینهٔ پیش‌فرض دکمه نقاشی نشود؛ خودمان رنگ می‌زنیم.
- **خط ۲۶۲:** خط دور پیش‌فرض حذف شود.
- **خط ۲۶۳:** کادر فوکوس پیش‌فرض حذف شود.
- **خط ۲۶۴:** دکمه opaque نباشد؛ پس‌زمینهٔ سفارشی ما دیده شود.
- **خط ۲۶۵:** قابلیت تشخیص حالت هاور فعال شود.
- **خط ۲۶۶:** نشانگر موس دست شود.
- **خط ۲۶۷:** فاصلهٔ داخلی متن از لبه‌ها.

```java
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
```

- **خط ۲۷۱:** بازنویسی رسم دکمه.
- **خط ۲۷۲:** کپی گرافیک.
- **خط ۲۷۳:** antialiasing.
- **خط ۲۷۶:** مدل وضعیت دکمه را می‌گیرد.
- **خط ۲۷۸:** اگر دکمه غیرفعال باشد، رنگ خاکستری.
- **خط ۲۸۰:** اگر هم فشرده است و هم `armed` (موس هنوز روی دکمه است)، رنگ فشرده.
- **خط ۲۸۲:** اگر موس روی دکمه است، رنگ هاور.
- **خط ۲۸۴:** در غیر این صورت، رنگ عادی.
- `isArmed` کمک می‌کند اگر کاربر دکمه را فشار دهد و موس را بیرون ببرد، حالت فشرده به‌درستی تشخیص داده شود.

```java
g2.setColor(fill);
g2.fillRoundRect(0, 0, getWidth(), getHeight(), RADIUS, RADIUS);
g2.dispose();
super.paintComponent(g);
```

- **خط ۲۸۸:** رنگ انتخابی.
- **خط ۲۸۹:** یک مستطیل گرد پر می‌کند.
- **خط ۲۹۰:** آزادسازی کپی گرافیک.
- **خط ۲۹۲:** `super.paintComponent(g)` متن دکمه را روی پس‌زمینهٔ رنگی رسم می‌کند؛ به همین دلیل بعد از پر کردن پس‌زمینه صدا زده می‌شود.

### خطوط ۲۹۶ تا ۳۲۵: `LogoBadge`

```java
private static class LogoBadge extends JComponent {
    LogoBadge() {
        Dimension size = new Dimension(66, 66);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setAlignmentX(Component.CENTER_ALIGNMENT);
    }
```

- **خط ۲۹۷:** یک کامپوننت سفارشی ساده برای رسم نشان.
- **خطوط ۲۹۹ تا ۳۰۳:** اندازهٔ ثابت ۶۶×۶۶ و وسط‌چین بودن.

```java
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
```

- **خطوط ۳۰۸ تا ۳۱۰:** آماده‌سازی گرافیک و نرم‌سازی لبه‌ها.
- **خطوط ۳۱۲ و ۳۱۳:** دایرهٔ پس‌زمینه را پر می‌کند.
- **خطوط ۳۱۵ تا ۳۱۷:** دایرهٔ آبی دور نشان را با ضخامت ۲.۵ رسم می‌کند.
- **خطوط ۳۱۹ و ۳۲۰:** قلم را با ضخامت ۴ و سرهای گرد تنظیم می‌کند.
- **خطوط ۳۲۱ و ۳۲۲:** دو خط تیک را رسم می‌کند.
- **خط ۳۲۳:** آزادسازی گرافیک.

---

## ۴. مفاهیم کلیدی که ممکن است استاد بپرسد

### ۱) Swing چیست؟
یک کتابخانهٔ رابط کاربری گرافیکی در جاوا که بخشی از JDK است و برای ساخت برنامه‌های دسکتاپ استفاده می‌شود. مزیتش این است که به کتابخانهٔ جانبی نیاز ندارد.

### ۲) JFrame چیست؟
پنجرهٔ اصلی برنامه با نوار عنوان، دکمهٔ بستن، minimize و maximize. کلاس `WelcomeApp` از آن ارث‌بری کرده است.

### ۳) تفاوت JPanel و JFrame؟
`JFrame` یک پنجرهٔ سطح‌بالا (top-level window) است؛ `JPanel` یک ظرف (container) برای گروه‌بندی اجزاست و خودش به‌تنهایی پنجره نمی‌شود.

### ۴) برنامه‌نویسی رویدادمحور یعنی چه؟
برنامه به‌جای اجرای خطی، منتظر رویدادها (کلیک، بستن پنجره، ...) می‌ماند و برای هر رویداد یک listener اجرا می‌کند. این برنامه دو رویداد اصلی دارد: `ActionEvent` و `WindowEvent`.

### ۵) چرا `DO_NOTHING_ON_CLOSE`؟
تا خودمان بستن پنجره را مدیریت کنیم و قبل از خروج، پیام خداحافظی نشان دهیم. اگر این خط نبود، پنجره بلافاصله بسته می‌شد.

### ۶) تفاوت `dispose()` و `System.exit(0)`؟
- `dispose()` منابع پنجره را آزاد می‌کند ولی ممکن است JVM همچنان اجرا شود.
- `System.exit(0)` کل برنامه را با کد خروج ۰ (موفق) پایان می‌دهد.

### ۷) چرا `SwingUtilities.invokeLater`؟
چون Swing thread-safe نیست و همهٔ تغییرات UI باید روی **EDT** انجام شوند. این متد کد را در صف رویدادهای EDT قرار می‌دهد.

### ۸) دکمه چطور به کد وصل می‌شود؟
با `addActionListener` و یک lambda. وقتی کلیک می‌شود، متد `actionPerformed` (در lambda) اجرا و `showWelcomeMessage()` صدا زده می‌شود.

### ۹) چرا دیالوگ تا زدن OK می‌ماند؟
چون `createDialog` یک دیالوگ **modal** می‌سازد. `setVisible(true)` تا بسته‌شدن آن برنمی‌گردد و کاربر نمی‌تواند با پنجرهٔ اصلی کار کند.

### ۱۰) چرا از Layout Manager استفاده کردیم؟
برای چیدمان خودکار و واکنش‌گرا. `GridBagLayout` کارت را وسط صفحه نگه می‌دارد و `BoxLayout` اجزا را عمودی می‌چیند. اگر مختصات ثابت (`setBounds`) استفاده می‌کردیم، با تغییر اندازهٔ پنجره ظاهر خراب می‌شد.

### ۱۱) چرا `setOpaque(false)`؟
تا کامپوننت پس‌زمینهٔ مستطیلی خودش را نقاشی نکند و پس‌زمینهٔ والد (کارت سفید یا گرادیان) دیده شود. برای گوشه‌های گرد و شفافیت لازم است.

### ۱۲) چرا `g.create()` و بعد `g2.dispose()`؟
`create()` یک کپی از تنظیمات گرافیکی می‌سازد تا تغییر رنگ، قلم و antialiasing روی رسم‌های بعدی اثر نگذارد. `dispose()` آن کپی را آزاد می‌کند.

### ۱۳) چرا `paintComponent` را override کردیم؟
برای رسم سفارشی. Swing هنگام نمایش یا repaint، این متد را صدا می‌زند. برای شکل‌های گرادیان، گوشهٔ گرد، دکمهٔ رنگی و نشان، از آن استفاده کردیم.

### ۱۴) چرا کلاس‌های داخلی `static` هستند؟
چون به فیلدهای نمونهٔ (instance) کلاس بیرونی نیاز ندارند و فقط از رنگ‌ها و فونت‌های `static` استفاده می‌کنند. `static` کردن آن‌ها از ایجاد وابستگی اضافی جلوگیری می‌کند.

### ۱۵) چرا فونت‌ها `static final` هستند؟
چون یک‌بار ساخته می‌شوند و در همهٔ اجزا استفاده می‌شوند؛ هم بهینه است و هم تغییر تصادفی‌شان ممکن نیست.

### ۱۶) `pack()` چه کار می‌کند؟
اندازهٔ پنجره را بر اساس اندازهٔ ترجیحی محتوا تنظیم می‌کند. بعد از آن با `setSize` حداقل اندازهٔ دلخواه را اعمال کردیم.

### ۱۷) `@Override` چه فایده‌ای دارد؟
به کامپایلر می‌گوید این متد باید متدی از کلاس پدر یا interface را بازنویسی کند. اگر امضا اشتباه باشد، خطای کامپایل می‌دهد و از باگ جلوگیری می‌شود.

### ۱۸) چطور متن فارسی راست‌به‌چپ شده؟
دو عامل:
1. `ComponentOrientation.RIGHT_TO_LEFT` برای جهت چیدمان و ترازبندی.
2. خود Java از الگوریتم Bidi و shaping فونت برای نمایش حروف فارسی استفاده می‌کند.

---

## ۵. جمله‌های آماده برای ارائه

- «این برنامه یک نمونهٔ برنامهٔ رویدادمحور با Java Swing است.»
- «پنجره از کلاس JFrame ارث‌بری می‌کند و رابط کاربری آن به‌صورت برنامه‌نویسی‌شده (programmatic) ساخته شده است.»
- «برای زیبایی ظاهر، سه کامپوننت سفارشی داریم: پنل گرادیانی، کارت گوشه‌گرد و دکمهٔ گوشه‌گرد.»
- «رویداد کلیک با ActionListener و رویداد بستن پنجره با WindowAdapter مدیریت می‌شود.»
- «پیام‌ها با JOptionPane و به‌صورت دیالوگ modal نمایش داده می‌شوند.»
- «قبل از خروج، پیام خداحافظی نشان داده می‌شود و سپس با dispose و System.exit برنامه بسته می‌شود.»
- «رابط کاربری راست‌به‌چپ است و فونت مناسب فارسی به‌صورت خودکار انتخاب می‌شود.»

---

## ۶. خلاصهٔ یک‌خطی هر بخش

| بخش | وظیفه |
|---|---|
| importها | وارد کردن کلاس‌های لازم |
| رنگ‌ها و فونت‌ها | تنظیم ظاهر ثابت برنامه |
| سازنده | ساخت پنجره، اندازه، جهت RTL و listener بستن |
| `buildContent` | ساخت کارت، عنوان، دکمه‌ها و راهنما |
| `showWelcomeMessage` | واکنش به کلیک دکمهٔ اصلی |
| `closeWithGoodbye` | نمایش خداحافظی و خروج |
| `showMessage` | ساخت و نمایش دیالوگ اطلاعاتی |
| `pickFontFamily` | انتخاب فونت مناسب فارسی |
| `main` | شروع برنامه روی EDT |
| `GradientPanel` | پس‌زمینهٔ گرادیانی |
| `RoundedPanel` | کارت سفید با گوشهٔ گرد |
| `RoundedButton` | دکمه با رنگ‌های هاور/کلیک |
| `LogoBadge` | رسم دایره و تیک |
