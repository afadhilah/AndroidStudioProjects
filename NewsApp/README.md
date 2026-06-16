# News App

News App adalah aplikasi Android modern yang digunakan untuk menampilkan berita terkini menggunakan REST API dari NewsAPI. Proyek ini dibangun menggunakan arsitektur MVVM (Model-View-ViewModel), Kotlin Coroutines, Retrofit, StateFlow, dan Jetpack Compose untuk menyajikan antarmuka pengguna yang premium dan interaktif.

## Fitur Utama

1. Integrasi API Berita Riil
   Menggunakan API resmi dari NewsAPI untuk menyajikan berita terbaru dari berbagai sumber tanpa bergantung pada data tiruan (mock data).

2. Antarmuka Premium (Slate Theme)
   Tampilan aplikasi dirancang menggunakan palet warna Slate yang bersih dan profesional dengan dukungan mode terang (Light Mode) yang dioptimalkan untuk keterbacaan tinggi.

3. Navigasi Bawah (Bottom Navigation)
   Memudahkan perpindahan layar utama menggunakan NavigationBar Material 3 yang mencakup menu utama:
   - Home: Menampilkan daftar berita utama terpopuler saat ini dengan tajuk tanggal dinamis.
   - Search: Fitur pencarian artikel dengan filter kata kunci instan serta saran topik siap klik (seperti Teknologi, Sains, Keuangan).
   - Saved: Halaman daftar bacaan untuk menyimpan artikel pilihan secara lokal.

4. In-App WebView
   Mengatasi keterbatasan data pratinjau berita dengan menyediakan penampil halaman web artikel asli di dalam aplikasi secara langsung tanpa perlu keluar ke peramban eksternal.

## Teknologi yang Digunakan

- Kotlin sebagai bahasa pemrograman utama.
- Jetpack Compose untuk membangun antarmuka pengguna secara deklaratif.
- Retrofit & Gson Converter untuk pemrosesan permintaan jaringan HTTP dan serialisasi data JSON.
- Kotlin Coroutines & StateFlow untuk manajemen alur kerja asinkronus dan pembaruan status UI secara reaktif.
- Coil Compose untuk memuat gambar artikel secara efisien dari URL eksternal.
- Navigation Compose untuk penanganan alur navigasi antar-layar.

## Struktur Direktori Utama

```text
com.aflabs.newsapp/
│
├── data/
│   ├── api/            # Layanan Retrofit (ApiService)
│   ├── model/          # Representasi data berita (Article, NewsResponse)
│   └── repository/     # Logika pengambilan data API (NewsRepository)
│
├── navigation/         # Navigasi rute aplikasi (NavGraph)
│
├── ui/
│   ├── components/     # Komponen UI modular (NewsCard)
│   ├── screens/        # Halaman utama (HomeScreen, SearchScreen, DetailScreen, WebViewScreen)
│   └── theme/          # Konfigurasi warna, tipografi, dan tema aplikasi (Theme)
│
└── viewmodel/          # Penyimpan state UI dan logika bisnis (NewsViewModel)
```

## Persyaratan Sistem

- JDK 17 atau yang lebih baru.
- Android Studio Hedgehog (2023.1.1) atau versi di atasnya.
- Koneksi internet aktif untuk memuat berita.

## Cara Menjalankan Proyek

1. Kloning repositori ini ke penyimpanan lokal Anda.
2. Buka proyek melalui Android Studio.
3. Hubungkan perangkat fisik Android (pastikan USB Debugging aktif) atau gunakan Emulator.
4. Kompilasi dan instal proyek ke perangkat Anda menggunakan perintah berikut di terminal:

```powershell
./gradlew.bat installDebug
```

5. Jalankan aplikasi secara langsung atau mulai melalui perintah ADB:

```powershell
adb shell am start -n com.aflabs.newsapp/com.aflabs.newsapp.MainActivity
```
