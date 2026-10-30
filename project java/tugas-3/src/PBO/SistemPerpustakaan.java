package PBO;

import java.util.Scanner;

public class SistemPerpustakaan {

    static class Buku {
        String kode;
        String judul;
        String penulis;
        int stok;
        int dipinjam;

        Buku(String kode, String judul, String penulis, int stok) {
            this.kode    = kode;
            this.judul   = judul;
            this.penulis = penulis;
            this.stok    = stok;
            this.dipinjam = 0;
        }

        @Override
        public String toString() {
            return kode + " | " + judul + " | " + penulis + " | Stok: " + stok;
        }
    }

    static Buku[] daftarBuku = new Buku[50];
    static int jumlahBuku   = 0;
    static Scanner sc       = new Scanner(System.in);

    static void tampilkanBuku() {
        System.out.println("\nDAFTAR BUKU");
        if (jumlahBuku == 0) {
            System.out.println("Belum ada buku.");
            return;
        }
        for (int i = 0; i < jumlahBuku; i++) {
            System.out.println(daftarBuku[i]);
        }
    }

    static void tambahBuku() {
        if (jumlahBuku >= 50) {
            System.out.println("Kapasitas penuh (50 buku).");
            return;
        }

        String kode;
        while (true) {
            System.out.print("Kode buku: ");
            kode = sc.nextLine().trim().toUpperCase();

            if (!kode.matches("\\d+")) {
                System.out.println("Kode harus berupa angka. Masukkan ulang.");
                continue;
            }

            boolean duplikat = false;
            for (int i = 0; i < jumlahBuku; i++) {
                if (daftarBuku[i].kode.equalsIgnoreCase(kode)) {
                    duplikat = true;
                    break;
                }
            }
            if (duplikat) {
                System.out.println("Kode sudah ada. Masukkan kode lain.");
                continue;
            }
            break;
        }

        System.out.print("Judul   : ");
        String judul = sc.nextLine().trim();

        System.out.print("Penulis : ");
        String penulis = sc.nextLine().trim();

        int stok = -1;
        while (stok < 0) {
            System.out.print("Stok    : ");
            try {
                stok = Integer.parseInt(sc.nextLine().trim());
                if (stok < 0) System.out.println("Stok tidak boleh negatif.");
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid.");
            }
        }

        daftarBuku[jumlahBuku++] = new Buku(kode, judul, penulis, stok);
        System.out.println("Buku berhasil ditambahkan.");
    }

    static void cariBuku() {
        if (jumlahBuku == 0) {
            System.out.println("Belum ada buku. Tambahkan buku terlebih dahulu.");
            return;
        }

        System.out.print("Masukkan judul/kode yang dicari: ");
        String keyword = sc.nextLine().trim().toLowerCase();
        boolean ditemukan = false;

        for (int i = 0; i < jumlahBuku; i++) {
            if (daftarBuku[i].kode.toLowerCase().contains(keyword)
                    || daftarBuku[i].judul.toLowerCase().contains(keyword)) {
                System.out.println(daftarBuku[i]);
                ditemukan = true;
            }
        }

        if (!ditemukan) System.out.println("Buku tidak ditemukan.");
    }

    static void pinjamBuku() {
        if (jumlahBuku == 0) {
            System.out.println("Belum ada buku. Tambahkan buku terlebih dahulu.");
            return;
        }

        int idx = -1;
        String kode;
        while (idx == -1) {
            System.out.print("Kode buku yang dipinjam (0 untuk batal): ");
            kode = sc.nextLine().trim().toUpperCase();
            if ("0".equals(kode)) return;

            for (int i = 0; i < jumlahBuku; i++) {
                if (daftarBuku[i].kode.equals(kode)) {
                    idx = i;
                    break;
                }
            }
            if (idx == -1) System.out.println("Kode tidak ditemukan. Coba lagi.");
        }

        if (daftarBuku[idx].stok <= 0) {
            System.out.println("Stok habis. Tidak bisa dipinjam.");
            return;
        }
        daftarBuku[idx].stok--;
        daftarBuku[idx].dipinjam++;
        System.out.println("Peminjaman berhasil.");
    }

    static void kembalikanBuku() {
        boolean adaPinjaman = false;
        for (int i = 0; i < jumlahBuku; i++) {
            if (daftarBuku[i].dipinjam > 0) {
                adaPinjaman = true;
                break;
            }
        }
        if (!adaPinjaman) {
            System.out.println("Tidak ada buku yang sedang dipinjam.");
            return;
        }

        int idx = -1;
        String kode;
        while (idx == -1) {
            System.out.print("Kode buku yang dikembalikan (0 untuk batal): ");
            kode = sc.nextLine().trim().toUpperCase();
            if ("0".equals(kode)) return;

            for (int i = 0; i < jumlahBuku; i++) {
                if (daftarBuku[i].kode.equals(kode)) {
                    idx = i;
                    break;
                }
            }
            if (idx == -1) System.out.println("Kode tidak ditemukan. Coba lagi.");
        }
        if (daftarBuku[idx].dipinjam <= 0) {
            System.out.println("Tidak ada pinjaman aktif untuk buku ini.");
            return;
        }
        daftarBuku[idx].stok++;
        daftarBuku[idx].dipinjam--;
        System.out.println("Pengembalian berhasil.");
    }

    public static void main(String[] args) {
        int pilihan;
        do {
            System.out.println("\n=== SISTEM PERPUSTAKAAN ===");
            System.out.println("1. Tampilkan buku");
            System.out.println("2. Tambah buku");
            System.out.println("3. Cari buku");
            System.out.println("4. Pinjam buku");
            System.out.println("5. Kembalikan buku");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");

            try {
                pilihan = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                pilihan = -1;
            }

            switch (pilihan) {
                case 1: tampilkanBuku();    break;
                case 2: tambahBuku();       break;
                case 3: cariBuku();         break;
                case 4: pinjamBuku();       break;
                case 5: kembalikanBuku();   break;
                case 0: System.out.println("Terima kasih."); break;
                default: System.out.println("Pilihan tidak valid.");
            }
        } while (pilihan != 0);

        sc.close();
    }
}
