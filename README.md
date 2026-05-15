# Minimalist Launcher

Launcher Android super minimalis yang fokus pada kecepatan, layar OLED, dan efisiensi.

## Fitur Utama
- **OLED Optimized:** Menggunakan True Black (`#000000`) untuk menghemat baterai dan tampilan elegan.
- **Super Fast Search:** Pencarian instan begitu Anda mengetik.
- **Smart Auto-Launch:** Aplikasi otomatis terbuka jika hasil pencarian menyisakan hanya 1 aplikasi. Contoh: Ketik "tik", jika hanya ada TikTok, aplikasi langsung terbuka tanpa perlu menekan Enter.
- **Gestur Notifikasi:** Swipe ke bawah di area kosong untuk membuka panel notifikasi.
- **Web Fallback:** Jika aplikasi tidak ditemukan, menekan Enter akan mencari di Google.
- **GitHub Ready:** Dilengkapi dengan GitHub Actions untuk build APK otomatis.

## Cara Instalasi
1. Clone repository ini.
2. Buka di Android Studio.
3. Build dan install ke perangkat Anda.
4. Set sebagai Launcher default di Pengaturan HP.

## Saran Fitur Tambahan (Untuk Masa Depan)
- **Hide Apps:** Fitur untuk menyembunyikan bloatware.
- **Icon Pack Support:** Dukungan untuk mengganti icon.
- **Custom Shortcuts:** Menambahkan shortcut khusus di layar utama.
- **Double Tap to Sleep:** Ketuk dua kali untuk mematikan layar.

## Struktur Project
- `app/src/main/java/com/minimalist/launcher/MainActivity.kt`: UI & Gesture handling.
- `app/src/main/java/com/minimalist/launcher/LauncherViewModel.kt`: Logika pencarian & auto-launch.
- `.github/workflows/build.yml`: CI/CD configuration.
