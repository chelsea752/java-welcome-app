# برنامهٔ خوش‌آمدگویی با Java Swing

یک پنجرهٔ ساده و راست‌به‌چپ با ظاهر سفارشی، ساخته‌شده با **Java Swing** (بدون کتابخانهٔ اضافی).

![نمای برنامه](screenshot.png)

## رفتار برنامه

- **دکمهٔ «دریافت پیام خوش‌آمدگویی»** → نمایش پیام خوش‌آمدگویی
- **بستن پنجره (✕)** → نمایش پیام خداحافظی و سپس خروج
- **دکمهٔ «بستن صفحه»** → همان مسیر خروج با پیام خداحافظی

## پیش‌نیاز

- **JDK 17 یا جدیدتر** (مثلاً [Eclipse Temurin](https://adoptium.net))
- `javac` باید در `PATH` باشد یا متغیر `JAVA_HOME` به پوشهٔ JDK تنظیم شده باشد.

## اجرا

### ویندوز

روی فایل `run.bat` دوبار کلیک کنید. این فایل برنامه را کامپایل و اجرا می‌کند.

### روش دستی

```powershell
javac -encoding UTF-8 WelcomeApp.java
java WelcomeApp
```

اگر `javac` در `PATH` نیست، از مسیر کامل JDK استفاده کنید:

```powershell
$jdk = "C:\path\to\jdk"
& "$jdk\bin\javac.exe" -encoding UTF-8 WelcomeApp.java
& "$jdk\bin\java.exe" WelcomeApp
```

## ساختار پروژه

```text
WelcomeApp.java   سورس برنامه
run.bat           کامپایل و اجرای برنامه
screenshot.png    تصویر رابط کاربری
README.md         توضیحات پروژه
```

## نکات

- فایل سورس با انکودینگ **UTF-8** ذخیره شده است؛ هنگام کامپایل دستی، گزینهٔ `-encoding UTF-8` را فراموش نکنید.
- فونت به‌صورت خودکار از بین `Vazirmatn`، `Segoe UI`، `Tahoma` و `Arial` انتخاب می‌شود.
- برای تغییر متن پیام‌ها، متدهای `showWelcomeMessage()` و `closeWithGoodbye()` را ویرایش کنید.
