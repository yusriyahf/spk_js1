public class spk {

    // Normalisasi Nilai Matriks
    private static double[][] normalisasiMatriks(double[][] matriks, String[] kriteria) {
        double[][] normalisasiMatriks = new double[matriks.length][matriks[0].length];

        for (int j = 0; j < matriks[0].length; j++) {
            if (kriteria[j].equals("benefit")) {
                double maxSiJ = Double.MIN_VALUE;

                // Mencari Nilai Tertinggi
                for (int i = 0; i < matriks.length; i++) {
                    if (matriks[i][j] > maxSiJ) {
                        maxSiJ = matriks[i][j];
                    }
                }

                // Normalisasi menggunakan rumus benefit
                for (int i = 0; i < matriks.length; i++) {
                    normalisasiMatriks[i][j] = matriks[i][j] / maxSiJ;
                }
            } else if (kriteria[j].equals("cost")) {
                double minSij = Double.MAX_VALUE;

                // Mencari Nilai Terendah
                for (int i = 0; i < matriks.length; i++) {
                    if (matriks[i][j] < minSij) {
                        minSij = matriks[i][j];
                    }
                }

                // Normalisasi menggunakan rumus cost
                for (int i = 0; i < matriks.length; i++) {
                    normalisasiMatriks[i][j] = minSij / matriks[i][j];
                }
            }
        }

        return normalisasiMatriks;
    }

    // Hitung Bobot Matriks
    private static double[][] hitungBobotMatriksWSM(double[][] matriks, double[] bobot) {
        double[][] bobotMatriks = new double[matriks.length][matriks[0].length];
        for (int i = 0; i < matriks.length; i++) {
            for (int j = 0; j < matriks[0].length; j++) {
                bobotMatriks[i][j] = matriks[i][j] * bobot[j];
            }
        }
        return bobotMatriks;
    }

    private static double[][] hitungBobotMatriksWPM(double[][] matriks, double[] bobot) {
        double[][] bobotMatriks = new double[matriks.length][matriks[0].length];
        for (int i = 0; i < matriks.length; i++) {
            for (int j = 0; j < matriks[0].length; j++) {
                bobotMatriks[i][j] = Math.pow(matriks[i][j], bobot[j]);
            }
        }
        return bobotMatriks;
    }

    // Jumlahkan Nilai Kriteria
    private static double[] menghitungTotalWSM(double[][] matriks) {
        double[] total = new double[matriks.length];
        for (int i = 0; i < matriks.length; i++) {
            double rowSum = 0;
            for (int j = 0; j < matriks[0].length; j++) {
                rowSum += matriks[i][j];
            }
            total[i] = rowSum;
        }
        return total;
    }

    private static double[] menghitungTotalWPM(double[][] matriks) {
        double[] total = new double[matriks.length];
        for (int i = 0; i < matriks.length; i++) {
            double rowSum = 1; // Menggunakan nilai awal 1 untuk perkalian
            for (int j = 0; j < matriks[0].length; j++) {
                rowSum *= matriks[i][j];
            }
            total[i] = rowSum;
        }
        return total;
    }

    // Perankingan dan Output
    private static void cetakRanking(double[] sums) {
        for (int i = 0; i < sums.length; i++) {
            System.out.println("Apartemen " + (i + 1) + ": " + sums[i]);
        }
    }

    public static void main(String[] args) {

        // Rentang Skor
        double[][] matriksKeputusan = {
                { 2, 2, 2, 1, 3 },
                { 2, 1, 3, 2, 3 },
                { 3, 2, 2, 2, 4 }
        };
        String kriteria[] = { "benefit", "cost", "benefit", "cost", "benefit" };

        // Langkah 1: Normalisasi Nilai
        double[][] normalisasiMatriks = normalisasiMatriks(matriksKeputusan, kriteria);

        System.out.println("Normalisasi Matriks");
        for (int i = 0; i < normalisasiMatriks.length; i++) {
            for (int j = 0; j < normalisasiMatriks[i].length; j++) {
                System.out.print(normalisasiMatriks[i][j] + "\t");
            }
            System.out.println();
        }

        // Langkah 2: Hitung Bobot
        double[] bobot = { 0.3, 0.2, 0.2, 0.2, 0.1 };
        double[][] bobotMatriksWSM = hitungBobotMatriksWSM(normalisasiMatriks, bobot);
        double[][] bobotMatriksWPM = hitungBobotMatriksWPM(normalisasiMatriks, bobot);

        System.out.println();
        System.out.println("Hitung bobot WSM");
        for (int i = 0; i < bobotMatriksWSM.length; i++) {
            for (int j = 0; j < bobotMatriksWSM[i].length; j++) {
                System.out.print(bobotMatriksWSM[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Hitung bobot WPM");
        for (int i = 0; i < bobotMatriksWPM.length; i++) {
            for (int j = 0; j < bobotMatriksWPM[i].length; j++) {
                System.out.print(bobotMatriksWPM[i][j] + "\t");
            }
            System.out.println();
        }

        // // Langkah 3: Jumlahkan Nilai Kriteria
        double[] totalWSM = menghitungTotalWSM(bobotMatriksWSM);
        System.out.println();
        System.out.println("Hitung Total WSM");
        for (int i = 0; i < totalWSM.length; i++) {
            System.out.print(totalWSM[i] + "\t");
        }

        System.out.println();
        double[] totalWPM = menghitungTotalWPM(bobotMatriksWPM);
        System.out.println();
        System.out.println("Hitung Total WPM");
        for (int i = 0; i < totalWPM.length; i++) {
            System.out.print(totalWPM[i] + "\t");
        }

        // Langkah 4: Ranking
        System.out.println();
        System.out.println();
        System.out.println("Ranking WSM");
        cetakRanking(totalWSM);

        System.out.println();
        System.out.println("Ranking WPM");
        cetakRanking(totalWPM);
    }
}
