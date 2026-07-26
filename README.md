# Inventaris Barang — Jetpack Compose Android App

Aplikasi manajemen inventaris barang sederhana menggunakan **Jetpack Compose**, **Navigation Compose**, dan **SharedPreferences** untuk penyimpanan lokal.

Disiapkan oleh **Kukuh Yudhistiro, S.Kom., M.Kom.** — 2026

---

## Fitur Utama

| Fitur | Keterangan |
|-------|------------|
| Daftar Barang | LazyColumn dengan card, search, badge stok |
| Detail Barang | Info lengkap, gambar besar, tombol Edit & Hapus |
| Tambah/Edit Barang | Form input lengkap, upload gambar dari galeri |
| Penyimpanan Lokal | SharedPreferences + Gson (JSON serialization) |
| Gambar Persisten | URI + takePersistableUriPermission |
| Notifikasi | Snackbar untuk feedback aksi (tambah/edit/hapus) |
| Tema | Material3 + Dynamic Color (Android 12+) |

---

## Cara Membuka Project

1. Buka **Android Studio** (Hedgehog / Koala ke atas)
2. Pilih **File → Open**
3. Arahkan ke folder `InventarisBarang/`
4. Tunggu Gradle sync selesai
5. Klik **Run ▶** untuk menjalankan di emulator atau device

---

## Struktur Folder

```
InventarisBarang/
├── app/src/main/
│   ├── AndroidManifest.xml
│   ├── java/com/example/inventarisbarang/
│   │   ├── MainActivity.kt                    ← Entry point
│   │   ├── model/
│   │   │   └── Barang.kt                      ← Data class
│   │   ├── data/
│   │   │   └── BarangRepository.kt            ← CRUD SharedPreferences
│   │   ├── navigation/
│   │   │   └── AppNavigation.kt               ← Route definitions
│   │   ├── screen/
│   │   │   ├── DaftarBarangScreen.kt           ← Screen 1: List
│   │   │   ├── DetailBarangScreen.kt           ← Screen 2: Detail
│   │   │   └── TambahBarangScreen.kt           ← Screen 3: Form
│   │   └── ui/theme/
│   │       └── Theme.kt                       ← Material3 theme
│   └── res/
│       ├── drawable/                           ← Launcher icon vectors
│       └── values/                             ← Themes, strings, colors
├── build.gradle.kts                            ← Project-level
├── settings.gradle.kts
├── gradle.properties
└── gradle/wrapper/
    └── gradle-wrapper.properties
```

---

## Dependencies

| Library | Versi | Kegunaan |
|---------|-------|----------|
| Compose BOM | 2024.12.01 | Konsistensi versi semua Compose libs |
| Navigation Compose | 2.8.5 | Navigasi antar screen |
| Coil Compose | 2.7.0 | Load gambar dari URI secara asinkron |
| Gson | 2.11.0 | Serialisasi/deserialisasi JSON |
| Material3 | (via BOM) | UI components + theming |

---

## Teknologi & Konsep

- **Kotlin 2.1** + Compose Compiler Plugin
- **Jetpack Compose** (deklaratif UI)
- **Navigation Compose** (NavHost, NavController, NavArgument)
- **SharedPreferences** + Gson untuk penyimpanan lokal
- **ActivityResultContracts.GetContent** untuk image picker
- **takePersistableUriPermission** untuk akses gambar permanen
- **Material3 Dynamic Color** (Android 12+)
- **Snackbar** sebagai notifikasi aksi
- **AlertDialog** untuk konfirmasi hapus
