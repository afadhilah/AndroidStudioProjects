# Skoola Marketplace

Aplikasi Marketplace Siswa dan Mahasiswa berbasis Android Native (Kotlin) dengan Clean Architecture dan Jetpack Compose.

---

## 1. Deskripsi Proyek

Skoola adalah platform marketplace digital mobile khusus untuk lingkungan sekolah dan kampus. Aplikasi ini memfasilitasi transaksi jual-beli barang bekas layak pakai (buku pelajaran, seragam, alat laboratorium, kalkulator scientific, alat tulis), penyewaan perlengkapan, serta penawaran jasa akademik/kreatif (les privat pemrograman, desain poster, jasa pembuatan PPT kelompok) antar siswa atau mahasiswa.

Proyek ini dibangun menggunakan standar pengembangan Android modern terkini (Android Modern Development Standards) untuk menghadirkan antarmuka pengguna (UI) yang premium, performa tinggi, serta arsitektur kode yang mudah dirawat (maintainable) dan diuji (testable).

---

## 2. Fitur-Fitur Utama

### A. Fitur Pembeli (Buyer Experience)
* **Autentikasi Aman**: Registrasi akun dan login dengan simulasi pengiriman dan verifikasi OTP 6 digit.
* **Halaman Utama (Home Screen)**:
  * Banners promosi interaktif (Flash Sale, Gratis Ongkir Kampus).
  * Kategori produk dinamis (Buku, Alat Tulis, Elektronik, Seragam, Jasa Les, Desain, Programming, dll.).
  * Rekomendasi produk berdasarkan relevansi sekolah/kampus terdekat.
* **Pencarian Produk**: Dilengkapi dengan pemfilteran berbasis kategori dan lokasi kampus.
* **Detail Produk (Product Detail Screen)**:
  * Galeri foto produk interaktif.
  * Informas profil penjual (Rating, Kampus Asal, Total Penjualan).
  * Ulasan dan Rating dari pembeli lain.
* **Keranjang Belanja (Cart)**: Manajemen jumlah item barang sebelum melakukan proses checkout.
* **Manajemen Alamat dan Kampus**: Penambahan dan pengelolaan alamat pengiriman serta penentuan kampus utama untuk COD atau pengantaran lokal.
* **Checkout dan Pembayaran Terintegrasi**: Pilihan metode pembayaran (Transfer Bank, E-Wallet, COD, QRIS) dengan perhitungan biaya ongkir otomatis.

### B. Fitur Penjual (Seller Experience)
* **Dashboard Penjual (Seller Dashboard)**:
  * Statistik penjualan: Grafik pendapatan kotor, jumlah pesanan tertunda, dan jumlah produk aktif.
  * Daftar pesanan masuk yang perlu diproses.
* **Unggah Produk (Upload/Add Product)**:
  * Input nama produk, deskripsi, harga, kategori, dan kondisi barang (Baru, Bekas, Jasa).
  * Pengunggahan gambar produk.

---

## 3. Arsitektur dan Teknologi Stack

### A. Pola Arsitektur
Aplikasi ini mengadopsi Clean Architecture yang dikombinasikan dengan MVVM (Model-View-ViewModel) untuk pemisahan fungsionalitas (separation of concerns) yang jelas:
* **Presentation Layer**: Berisi Jetpack Compose (UI) dan ViewModel untuk memanajemen status UI (UI State) menggunakan Kotlin StateFlow.
* **Domain Layer**: Berisi Model data murni, use cases bisnis aplikasi, serta kontrak repositori (Repository Interfaces). Layer ini murni Kotlin tanpa dependensi ke framework Android.
* **Data Layer**: Implementasi konkret repositori, Room database untuk penyimpanan lokal, Retrofit API service untuk transaksi jaringan, serta Interceptor untuk menyimulasikan data API backend (Mock API).

```
[Presentation: Jetpack Compose / ViewModels]
                   │
                   ▼
  [Domain: Use Cases / Models / Interfaces]
                   ▲
                   │
[Data: Repositories / Room DB / Retrofit Service]
```

### B. Teknologi Stack
* **Bahasa Pemrograman**: Kotlin 2.0+
* **UI Framework**: Jetpack Compose dan Material Design 3 (Sleek dark mode dan dynamic layouts)
* **Dependency Injection**: Dagger Hilt (Untuk menyediakan dependensi secara otomatis dan terstruktur)
* **Database Lokal**: Room Database (Untuk menyimpan wishlist, keranjang, dan alamat offline)
* **Koneksi Jaringan**: Retrofit dan OkHttp (Dengan Custom Interceptor untuk pemuatan mock data API)
* **Asynchronous**: Kotlin Coroutines dan Flow (Untuk manajemen thread latar belakang yang reaktif)
* **Pemuatan Gambar**: Coil (Untuk pemuatan gambar online secara asinkron dengan shimmer effect)
* **Serialization**: Kotlinx Serialization (Untuk pemrosesan format data JSON)

---

## 4. Struktur Direktori Proyek

```
com.aflabs.skoola
├── data
│   ├── local              # Entitas database Room dan DAO (Alamat, Keranjang, Produk)
│   ├── remote             # Interface Retrofit API Service, DTO, dan MockApiInterceptor
│   └── repository         # Implementasi konkret dari domain repository kontrak
│
├── domain
│   ├── model              # Kelas data murni (User, Product, Cart, Address, Order, dll.)
│   ├── repository         # Kontrak antarmuka repositori
│   └── usecase            # Logika bisnis per fitur (GetProductsUseCase, AddToCartUseCase, dll.)
│
├── presentation
│   ├── components         # Komponen UI reusable (ProductCard, Shimmer, EmptyState, dll.)
│   ├── navigation         # Graf navigasi dan rute layar (NavGraph dan Screen)
│   ├── theme              # Konfigurasi skema warna (Material Theme, Shape, Typography)
│   ├── ui                 # Layar aplikasi Compose (Home, Auth, Cart, Checkout, Seller Dashboard)
│   └── viewmodel          # ViewModels untuk manajemen UI State
│
├── utils                  # Kelas pembantu (Result handler, extensions)
├── MainActivity.kt        # Titik masuk utama aplikasi Android
└── SkoolaApplication.kt    # Kelas Aplikasi Hilt
```

---

## 5. Skema Database Lokal (Room DB)

### A. Tabel Alamat (addresses)
* **id**: String (Primary Key) - ID Unik alamat
* **name**: String - Nama label alamat (e.g. Kos, Rumah)
* **recipientName**: String - Nama penerima paket
* **phone**: String - Nomor telepon penerima
* **detailAddress**: String - Jalan, RT/RW, Kecamatan
* **school**: String - Kampus/Sekolah asosiasi
* **isPrimary**: Boolean - Apakah alamat utama

### B. Tabel Keranjang Belanja (cart_items)
* **id**: String (Primary Key) - ID Unik item keranjang
* **productId**: String - ID Referensi ke produk
* **productName**: String - Nama produk
* **productPrice**: Long - Harga satuan
* **productImage**: String - URL Gambar
* **quantity**: Int - Jumlah barang dibeli
* **sellerId**: String - ID Penjual

---

## 6. Panduan Menjalankan Aplikasi di Perangkat Fisik

### Prasyarat
* Perangkat Android dengan Android 6.0 (API 24) atau lebih baru
* Kabel USB pendukung transfer data
* Android SDK Platform Tools terpasang pada PC
* Gradle dan Java JDK 21 sudah terkonfigurasi

### Langkah 1: Aktifkan Developer Mode dan USB Debugging
1. Buka Settings -> About phone pada perangkat Android Anda.
2. Ketuk Build number sebanyak 7 kali hingga muncul pesan bahwa mode pengembang telah aktif.
3. Kembali ke menu utama Settings -> Developer options.
4. Aktifkan opsi USB Debugging.

### Langkah 2: Hubungkan Perangkat ke PC
1. Hubungkan perangkat Android ke PC menggunakan kabel USB.
2. Pilih Allow/Izinkan pada dialog pop-up yang muncul di layar perangkat.
3. Buka PowerShell atau Command Prompt pada folder project ini dan jalankan perintah:
   ```bash
   adb devices
   ```
4. Perangkat Anda akan terdaftar dalam daftar perangkat yang terhubung.

### Langkah 3: Build dan Install Aplikasi
Untuk melakukan kompilasi dan menginstal aplikasi ke perangkat, jalankan perintah Gradle berikut:
```bash
.\gradlew.bat installDebug
```

### Langkah 4: Jalankan Aplikasi
Aplikasi dapat dibuka secara manual melalui menu perangkat dengan nama Skoola, atau dijalankan langsung dari terminal menggunakan ADB:
```bash
adb shell am start -n com.aflabs.skoola/com.aflabs.skoola.MainActivity
```

---

## 7. Perintah ADB yang Berguna

```bash
# Menampilkan daftar perangkat terhubung
adb devices

# Memasang APK secara manual
adb install .\app\build\outputs\apk\debug\app-debug.apk

# Menghapus instalasi aplikasi
adb uninstall com.aflabs.skoola

# Menghentikan paksa aplikasi
adb shell am force-stop com.aflabs.skoola

# Menghapus data aplikasi
adb shell pm clear com.aflabs.skoola
```
